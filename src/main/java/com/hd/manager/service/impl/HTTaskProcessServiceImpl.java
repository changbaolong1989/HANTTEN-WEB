package com.hd.manager.service.impl;

import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.base.vo.BaseVO;
import com.hd.common.dao.TbTaskEntityMapper;
import com.hd.common.dao.TbTaskPeriodRelationEntityMapper;
import com.hd.common.dao.TbTaskReeditEntityMapper;
import com.hd.common.dao.TbUserEntityMapper;
import com.hd.common.entity.*;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.service.HTTaskProcessService;
import com.hd.manager.vo.HTTaskVO;
import com.hd.manager.vo.HTUserVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HTTaskProcessServiceImpl extends BaseServiceImpl implements HTTaskProcessService {

    //枚举
    private static final ProcessDictEnum TASK_ENUM = ProcessDictEnum.Task;

    //流程Key
    private static final String TASK_PROCESS_KEY = TASK_ENUM.getProcessKey();

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private HistoryService historyService;

    @Resource
    private TaskService taskService;

    @Resource
    private TbTaskEntityMapper taskDao;

    @Resource
    private TbTaskPeriodRelationEntityMapper taskPeriodDao;

    @Resource
    private TbTaskReeditEntityMapper taskReeditDao;

    @Resource
    private TbUserEntityMapper userDao;

    /**
     * 办理任务
     *
     * @param variables 变量
     * @return 历史ID
     * @throws SystemException 系统异常
     */
    @Override
    public String excHandleTask(String userId, Map<String, Object> variables) throws LogicException, SystemException {
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, TASK_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, taskVO.getDelegateTaskId(), variables);
        logger.info("任务_成果文件审批流程任务办理完成。任务ID为：" + taskVO.getDelegateTaskId());
        return ((CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO)).getHistoryRecordId();
    }

    /**
     * 改派
     */
    @Override
    public void excReassignment(BaseVO baseVO) throws SystemException {
        if (baseVO instanceof HTTaskVO) {
            HTTaskVO taskVO = (HTTaskVO) baseVO;
            String taskId = taskVO.getTaskId();
            String departmentId = taskVO.getDepartmentId();
            //改派成果文件
            TbTaskEntity taskEntity = taskDao.selectByPrimaryKey(taskId);
            String taskDelegateTaskId = taskEntity.getDelegateTaskId();
            String oldUser = taskEntity.getApplyUserId();
            String newUser = taskVO.getApplyUserId();
            if (oldUser.equals(newUser)) return;
            if (StringUtils.isBlank(newUser)) throw new SystemException();
            taskService.deleteCandidateUser(taskDelegateTaskId, oldUser);
            taskService.addCandidateUser(taskDelegateTaskId, newUser);
            Map<String, Object> taskVariables = taskService.getVariables(taskDelegateTaskId);
            CommonActivitiListenVO taskCommonActivitiListen = (CommonActivitiListenVO) taskVariables.get(WorkFlowConstant.KEY_LISTENER_VO);
            taskCommonActivitiListen.setDepartmentId(departmentId);
            taskService.setVariable(taskDelegateTaskId, WorkFlowConstant.KEY_LISTENER_VO, taskCommonActivitiListen);
            TbUserEntity user = userDao.selectByPrimaryKey(newUser);
            String newUserId = user.getUserId();
            String newUserName = user.getUserName();
            taskEntity.setApplyUserId(newUserId);
            taskEntity.setApplyUserName(newUserName);
            TbTaskEntityExample taskExample = new TbTaskEntityExample();
            taskExample.createCriteria().andTaskIdEqualTo(taskId);
            taskDao.updateByExampleSelective(taskEntity, taskExample);
            taskService.setVariable(taskDelegateTaskId, WorkFlowConstant.KEY_MAIN_LEADER_USER, newUser);
            taskService.setVariable(taskDelegateTaskId, WorkFlowConstant.KEY_DELEGATE_USER, newUser);
            taskService.setVariable(taskDelegateTaskId, WorkFlowConstant.KEY_PRE_HANDLER, taskVO.getLoginUserInfo());
            taskService.setVariable(taskDelegateTaskId, WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            //改派A3
            TbTaskPeriodRelationEntityExample taskPeriodExample = new TbTaskPeriodRelationEntityExample();
            taskPeriodExample.createCriteria().andTaskIdEqualTo(taskId);
            List<TbTaskPeriodRelationEntity> taskPeriodList = taskPeriodDao.selectByExample(taskPeriodExample);
            for (TbTaskPeriodRelationEntity taskPeriod : taskPeriodList) {
                String taskPeriodDelegateTaskId = taskPeriod.getDelegateTaskId();
                taskService.deleteCandidateUser(taskPeriodDelegateTaskId, oldUser);
                //taskService.addCandidateUser(taskPeriodDelegateTaskId, newUser);这里只改数据不派人
                Map<String, Object> taskPeriodVariables = taskService.getVariables(taskPeriodDelegateTaskId);
                CommonActivitiListenVO taskPeriodCommonActivitiListen = (CommonActivitiListenVO) taskPeriodVariables.get(WorkFlowConstant.KEY_LISTENER_VO);
                taskPeriodCommonActivitiListen.setDepartmentId(departmentId);
                taskService.setVariable(taskPeriodDelegateTaskId, WorkFlowConstant.KEY_LISTENER_VO, taskPeriodCommonActivitiListen);
                taskService.setVariable(taskPeriodDelegateTaskId, WorkFlowConstant.KEY_MAIN_LEADER_USER, newUser);
                taskService.setVariable(taskPeriodDelegateTaskId, WorkFlowConstant.KEY_DELEGATE_USER, newUser);
                taskService.setVariable(taskDelegateTaskId, WorkFlowConstant.KEY_PRE_HANDLER, taskVO.getLoginUserInfo());
                taskService.setVariable(taskDelegateTaskId, WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            }
            TbTaskPeriodRelationEntity taskPeriod = new TbTaskPeriodRelationEntity();
            taskPeriod.setApplyUserId(newUserId);
            taskPeriod.setApplyUserName(newUserName);
            taskPeriodDao.updateByExampleSelective(taskPeriod, taskPeriodExample);
            //改派重新编辑
            TbTaskReeditEntityExample taskReeditExample = new TbTaskReeditEntityExample();
            taskReeditExample.createCriteria().andTaskIdEqualTo(taskId);
            List<TbTaskReeditEntity> taskReeditList = taskReeditDao.selectByExample(taskReeditExample);
            if (taskReeditList != null && !taskReeditList.isEmpty()) {
                TbTaskReeditEntity taskReedit = taskReeditList.get(0);
                String taskReeditDelegateId = taskReedit.getDelegateTaskId();
                taskService.deleteCandidateUser(taskReeditDelegateId, oldUser);
                //taskService.addCandidateUser(taskReeditDelegateId, newUser);
                Map<String, Object> taskReeditVariables = taskService.getVariables(taskReeditDelegateId);
                CommonActivitiListenVO taskReeditCommonActivitiListen = (CommonActivitiListenVO) taskReeditVariables.get(WorkFlowConstant.KEY_LISTENER_VO);
                taskReeditCommonActivitiListen.setDepartmentId(departmentId);
                taskService.setVariable(taskReeditDelegateId, WorkFlowConstant.KEY_LISTENER_VO, taskReeditCommonActivitiListen);
                taskService.setVariable(taskReeditDelegateId, WorkFlowConstant.KEY_MAIN_LEADER_USER, newUser);
                taskService.setVariable(taskReeditDelegateId, WorkFlowConstant.KEY_DELEGATE_USER, newUser);
                taskService.setVariable(taskDelegateTaskId, WorkFlowConstant.KEY_PRE_HANDLER, taskVO.getLoginUserInfo());
                taskService.setVariable(taskDelegateTaskId, WorkFlowConstant.KEY_HANDLE_TIME, new Date());
                logger.info("任务改派完成。从用户:[ " + oldUser + " ] 改派到用户：[ " + newUser + " ] ");
            }
            TbTaskReeditEntity taskReedit = new TbTaskReeditEntity();
            taskReedit.setApplyUserId(newUserId);
            taskReedit.setApplyUserName(newUserName);
            taskReeditDao.updateByExampleSelective(taskReedit, taskReeditExample);
        } else {
            logger.error("类型异常");
            throw new SystemException();
        }

    }

    /**
     * 查询页面类型
     */
    @Override
    public String excQueryPageType(HTUserVO userVO, String bizDeptId, String processDictId, BaseVO vo, List<String> executorUsers) {
        return commonActivitiService.excQueryPageType(userVO, bizDeptId, processDictId, vo, executorUsers);
    }

    /**
     * 启动工作流
     *
     * @param variables 流程变量
     * @return 流程实例对象
     * @throws SystemException 系统异常
     */
    @Override
    public ProcessInstance excStart(Map<String, Object> variables) throws SystemException {
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        String bizKey = taskVO.getTaskId();//业务主键
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TASK_PROCESS_KEY, bizKey, variables);//启动流程
        //转换流程ID
        String processInstanceId = processInstance.getId();
        taskVO.setProcessInstId(processInstanceId);
        TbTaskEntity taskEntity = (TbTaskEntity) ObjectReflectUtils.convertObj(taskVO, TbTaskEntity.class);
        taskDao.updateByPrimaryKeySelective(taskEntity);
        logger.info("任务_成果文件审批流程已经启动。流程实例ID为：" + processInstance.getId());
        return processInstance;
    }

    /**
     * 办理任务
     *
     * @param taskId    任务ID
     * @param variables 流程变量
     * @throws SystemException 系统异常
     */
    @Override
    public void excHandleTask(String userId, String taskId, Map<String, Object> variables) throws LogicException, SystemException {
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, TASK_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, taskId, variables);
        logger.info("任务_成果文件审批流程任务办理完成。任务ID为：" + taskId);
    }

    /**
     * 撤回任务
     *
     * @param vo VO
     * @throws SystemException 系统异常
     */
    @Override
    public void excRecallTask(Object vo) throws SystemException {
        if (vo instanceof HTTaskVO) {
            HTTaskVO taskVO = (HTTaskVO) vo;
            String taskId = taskVO.getDelegateTaskId();
            Map<String, Object> variables = new HashMap<>();
            variables.put(WorkFlowConstant.KEY_TASK_VO, taskVO);
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, Constant.NOT_APPROVED);
            variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, taskVO.getDelegateTaskKey());
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            commonActivitiListenVO.setDepartmentId(taskVO.getDepartmentId());
            commonActivitiListenVO.setIsRecall(Constant.IS_CAN_RECALL_ENABLE);
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, taskVO.getLoginUserInfo());
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            this.excHandleTask(((HTTaskVO) vo).getLoginUserInfo().getLoginUserId(), variables);
            //删除历史
            historyService.deleteHistoricTaskInstance(taskId);
        } else {
            logger.error("类型异常");
            throw new SystemException();
        }
    }

}
