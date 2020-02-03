package com.hd.processes.listener.abs;

import com.hd.base.exception.NoDelegationException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.base.vo.BaseVO;
import com.hd.common.entity.TbJobProcessNodeRelationEntity;
import com.hd.common.entity.TbUserJobRelation;
import com.hd.common.entity.TbUserJobRelationExample;
import com.hd.common.service.CommonCacheService;
import com.hd.common.util.*;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.dao.HTUserJobRelationDao;
import com.hd.processes.listener.factory.VerifyObject;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工作流任务监听抽象类
 */
@Service
public abstract class AbsActTaskListener extends BaseServiceImpl implements Listener {

    //是否可以撤回
    private static final String NOT_CAN_RECALL = "0";

    //是否是撤回状态
    private static final String IS_RECALL = "1";

    protected static final String IS_NOT_DEL_FLAG = "0";

    @Resource
    private HTUserJobRelationDao userJobRelationDao;

    @Resource
    private CommonCacheService commonCacheService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    /**
     * 根据部门设置委托人
     */
    @Override
    public void setDelegationUsersByDept(DelegateTask delegateTask, String departmentId, String processDictId) throws NoDelegationException {
        //流程节点Key
        String nodeKey = delegateTask.getTaskDefinitionKey();
        //通过节点Key，部门ID查询到所有已经配置的节点信息
        List<TbJobProcessNodeRelationEntity> proNodeRelationList = commonCacheService.getProcessNodeCache(processDictId, nodeKey, departmentId);
        if (noDelegationUser(proNodeRelationList))
            throw new NoDelegationException(MessageConstant.MSG_INFO_PROCESS_NO_USER);
        //根据配置节点信息分配任务
        List<String> jobIdList = proNodeRelationList.stream().map(TbJobProcessNodeRelationEntity::getJobId).collect(Collectors.toList());
        TbUserJobRelationExample example = new TbUserJobRelationExample();
        TbUserJobRelationExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdIn(jobIdList);
        List<TbUserJobRelation> userJobRelationList = userJobRelationDao.selectIncumbentUser(example);
        if (noDelegationUser(userJobRelationList))
            throw new NoDelegationException(MessageConstant.MSG_INFO_PROCESS_NO_USER);
        List<String> delegateUsers = userJobRelationList.stream().map(TbUserJobRelation::getUserId).collect(Collectors.toList());
        delegateTask.addCandidateUsers(delegateUsers);
    }

    /**
     * 异常处理
     *
     * @param activitiListenVO 监听VO
     */
    @Override
    public void eHandle(CommonActivitiListenVO activitiListenVO) {
        activitiListenVO.setRollBack(true);
        activitiListenVO.setDepartmentId(null);
        activitiListenVO.setHistoryRecordId(null);
        activitiListenVO.setIsRecall(null);
    }

    /**
     * 更新业务数据
     *
     * @param baseVO baseVO
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void updateBizData(BaseVO baseVO, Class<T> clz, Class<?> daoClz) throws ReflectiveOperationException {
        T entity = (T) ObjectReflectUtils.convertObj(baseVO, clz);
        Method updateByPrimaryKeySelective = daoClz.getMethod("updateByPrimaryKeySelective", clz);
        updateByPrimaryKeySelective.invoke(SpringUtils.getBean(daoClz), entity);
    }

    /**
     * 设置部门ID
     *
     * @param variables        变量
     * @param baseVO           baseVo
     * @param activitiListenVO 监听vo
     * @throws Exception 异常
     */
    @Override
    public void setDepartmentId(Map<String, Object> variables, BaseVO baseVO, CommonActivitiListenVO activitiListenVO) throws Exception {
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.NOT_APPROVED.equals(StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS)))) {
            Method getDepartmentId = baseVO.getClass().getMethod("getDepartmentId");
            String departmentId = StringToolUtils.convertNullObjectToString(getDepartmentId.invoke(baseVO));
            if (StringUtils.isNotBlank(departmentId)) {
                activitiListenVO.setDepartmentId(departmentId);
            } else {
                throw new Exception("合同所属部门为空！");
            }
        }
    }


    /**
     * 一级审核执行
     */
    @Override
    public CommonActivitiListenVO managerExeListener(VerifyObject verifyObject) throws Exception {
        DelegateExecution delegateExecution = verifyObject.getDelegateExecution();
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            BaseVO vo = verifyObject.getBaseVO();
            String recallSubmitNode = verifyObject.getRecallSubmitNode();
            String submitNode = verifyObject.getSubmitNode();
            Class<?> entityClass = verifyObject.getEntityClass();
            Class<?> daoClass = verifyObject.getDaoClass();
            Class<?> reflectMethodClass = verifyObject.getReflectMethodClass();
            if (IS_RECALL.equals(activitiListenVO.getIsRecall())) {//撤回
                //更新业务数据
                doRecall(vo, recallSubmitNode);
                updateBizData(vo, entityClass, daoClass);
                activitiListenVO.setIsRecall(null);
            } else {
                Method exeApproveMethod = reflectMethodClass.getMethod("exeApprove", DelegateExecution.class, java.util.Date.class, java.util.Map.class, vo.getClass());
                Date approveTime = new Date();
                vo.setApproveTime(DateToolUtils.convertDateFormat(approveTime, DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                vo.setSubmitNode(submitNode);
                vo.setIsCanRecall(NOT_CAN_RECALL);
                //执行审批
                exeApproveMethod.invoke(SpringUtils.getBean(reflectMethodClass), delegateExecution, approveTime, variables, vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        return activitiListenVO;
    }

    /**
     * 一级审核监听
     *
     * @return activitiListenVO
     */
    @Override
    public CommonActivitiListenVO managerTaskListener(VerifyObject verifyObject) {
        DelegateTask delegateTask = verifyObject.getDelegateTask();
        String delegateTaskId = delegateTask.getId();
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        BaseVO vo = verifyObject.getBaseVO();
        String nodeKey = delegateTask.getTaskDefinitionKey();
        String submitNode = verifyObject.getSubmitNode();
        String currentState = verifyObject.getCurrentState();
        Class<?> entityClass = verifyObject.getEntityClass();
        Class<?> daoClass = verifyObject.getDaoClass();
        String userDeptId = activitiListenVO.getDepartmentId();
        String processDictId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_PROCESS_DICT_TYPE));//流程字典ID
        //更新业务数据
        vo.setDelegateTaskKey(nodeKey);
        vo.setDelegateTaskId(delegateTaskId);
        vo.setSubmitNode(submitNode);
        vo.setVerifyState(Constant.APPROVING);
        vo.setCurrentState(currentState);
        setDelegation(delegateTask, activitiListenVO, vo, entityClass, daoClass, userDeptId, processDictId);
        return activitiListenVO;
    }

    /**
     * 二级/三级审核监听
     *
     * @param verifyObject 任务对象
     */
    @Override
    public CommonActivitiListenVO qualityTaskListener(VerifyObject verifyObject) {
        DelegateTask delegateTask = verifyObject.getDelegateTask();
        String delegateTaskId = delegateTask.getId();
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        String departmentId = verifyObject.getDepartmentId();
        String submitNode = verifyObject.getSubmitNode();
        String currentState = verifyObject.getCurrentState();
        BaseVO vo = verifyObject.getBaseVO();
        Class<?> entityClass = verifyObject.getEntityClass();
        Class<?> daoClass = verifyObject.getDaoClass();
        //流程节点Key
        String nodeKey = delegateTask.getTaskDefinitionKey();
        String processDictId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_PROCESS_DICT_TYPE));//流程字典ID
        //更新业务数据
        vo.setDelegateTaskKey(nodeKey);
        vo.setDelegateTaskId(delegateTaskId);
        vo.setSubmitNode(submitNode);
        vo.setCurrentState(currentState);
        setDelegation(delegateTask, activitiListenVO, vo, entityClass, daoClass, departmentId, processDictId);
        return activitiListenVO;
    }

    /**
     * 二级/三级审核执行
     *
     * @param verifyObject 执行对象
     */
    @Override
    public CommonActivitiListenVO qualityExeListener(VerifyObject verifyObject) throws Exception {
        DelegateExecution delegateExecution = verifyObject.getDelegateExecution();
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            BaseVO vo = verifyObject.getBaseVO();
            String recallSubmitNode = verifyObject.getRecallSubmitNode();
            Class<?> entityClass = verifyObject.getEntityClass();
            Class<?> daoClass = verifyObject.getDaoClass();
            Class<?> reflectMethodClass = verifyObject.getReflectMethodClass();
            if (IS_RECALL.equals(activitiListenVO.getIsRecall())) {//撤回
                //更新业务数据
                doRecall(vo, recallSubmitNode);
                updateBizData(vo, entityClass, daoClass);
                activitiListenVO.setIsRecall(null);
            } else {
                setDepartmentId(variables, vo, activitiListenVO);
                Date approveTime = new Date();
                vo.setApproveTime(DateToolUtils.convertDateFormat(approveTime, DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                vo.setIsCanRecall(NOT_CAN_RECALL);
                Method exeApproveMethod = reflectMethodClass.getMethod("exeApprove", DelegateExecution.class, java.util.Date.class, java.util.Map.class, vo.getClass());
                //执行审批
                exeApproveMethod.invoke(SpringUtils.getBean(reflectMethodClass), delegateExecution, approveTime, variables, vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        return activitiListenVO;
    }

    /**
     * 签发执行监听
     */
    @Override
    public CommonActivitiListenVO signExeListener(VerifyObject verifyObject) throws Exception {
        DelegateExecution delegateExecution = verifyObject.getDelegateExecution();
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            BaseVO vo = verifyObject.getBaseVO();
            String submitNode = verifyObject.getSubmitNode();
            Class<?> reflectMethodClass = verifyObject.getReflectMethodClass();
            vo.setSubmitNode(submitNode);
            setDepartmentId(variables, vo, activitiListenVO);
            Date approveTime = new Date();
            vo.setApproveTime(DateToolUtils.convertDateFormat(approveTime, DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            vo.setIsCanRecall(NOT_CAN_RECALL);
            Method exeApproveMethod = reflectMethodClass.getMethod("exeApprove", DelegateExecution.class, java.util.Date.class, java.util.Map.class, vo.getClass());
            exeApproveMethod.invoke(SpringUtils.getBean(reflectMethodClass), delegateExecution, approveTime, variables, vo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        return activitiListenVO;
    }

    /**
     * 签发节点监听
     */
    @Override
    public CommonActivitiListenVO signTaskListener(VerifyObject verifyObject) {
        DelegateTask delegateTask = verifyObject.getDelegateTask();
        String delegateTaskId = delegateTask.getId();
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        String departmentId = verifyObject.getDepartmentId();
        String submitNode = verifyObject.getSubmitNode();
        String currentState = verifyObject.getCurrentState();
        BaseVO vo = verifyObject.getBaseVO();
        Class<?> entityClass = verifyObject.getEntityClass();
        Class<?> daoClass = verifyObject.getDaoClass();
        String nodeKey = delegateTask.getTaskDefinitionKey();
        String userDeptId = StringToolUtils.convertNullObjectToString(departmentId);//用户部门ID
        String processDictId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_PROCESS_DICT_TYPE));//流程字典ID
        //更新业务数据
        vo.setDelegateTaskKey(nodeKey);
        vo.setDelegateTaskId(delegateTaskId);
        vo.setSubmitNode(submitNode);
        vo.setCurrentState(currentState);
        setDelegation(delegateTask, activitiListenVO, vo, entityClass, daoClass, userDeptId, processDictId);
        return activitiListenVO;
    }

    private boolean noDelegationUser(List<?> var) {
        return var == null || var.isEmpty();
    }

    private void doRecall(BaseVO vo, String recallSubmitNode) {
        vo.setApproveTime(null);
        vo.setApproveUserId(null);
        vo.setApproveUserName(null);
        vo.setSubmitNode(recallSubmitNode);
        vo.setIsCanRecall(NOT_CAN_RECALL);
    }

    protected void setDelegation(DelegateTask delegateTask, CommonActivitiListenVO activitiListenVO, BaseVO vo, Class<?> entityClass, Class<?> daoClass, String userDeptId, String processDictId) {
        try {
            //根据部门设置委托人
            setDelegationUsersByDept(delegateTask, userDeptId, processDictId);
            updateBizData(vo, entityClass, daoClass);
        } catch (NoDelegationException e) {
            activitiListenVO.setExceptionMsg(MessageConstant.MSG_INFO_PROCESS_NO_USER);
            eHandle(activitiListenVO);
        } catch (ReflectiveOperationException e) {
            eHandle(activitiListenVO);
        }
    }

}
