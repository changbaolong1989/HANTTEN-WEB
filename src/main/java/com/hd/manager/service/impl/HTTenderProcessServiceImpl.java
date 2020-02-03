package com.hd.manager.service.impl;

import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.base.vo.BaseVO;
import com.hd.common.dao.TbTenderTaskEntityMapper;
import com.hd.common.dao.TbUserEntityMapper;
import com.hd.common.entity.TbTenderTaskEntity;
import com.hd.common.entity.TbTenderTaskEntityExample;
import com.hd.common.entity.TbUserEntity;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.service.HTTenderProcessService;
import com.hd.manager.vo.HTTenderTaskVO;
import com.hd.manager.vo.HTUserVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("htTenderProcessServiceImpl")
public class HTTenderProcessServiceImpl extends BaseServiceImpl implements HTTenderProcessService {

    /**
     * log对象
     */
    private static final Logger logger = LoggerFactory.getLogger(HTTenderProcessServiceImpl.class);

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private TbTenderTaskEntityMapper tenderTaskDao;

    @Resource
    private TbUserEntityMapper userDao;

    //投标枚举
    private static final ProcessDictEnum TENDER_ENUM = ProcessDictEnum.Tender;

    //投标流程Key
    private static final String TENDER_PROCESS_KEY = TENDER_ENUM.getProcessKey();

    /**
     * 启动工作流
     *
     * @param variables 流程变量
     * @return 流程实例对象
     * @throws SystemException 系统异常
     */
    @Override
    public ProcessInstance excStart(Map<String, Object> variables) throws LogicException, SystemException {
        ProcessInstance processInstance;
        //1.启动流程
        HTTenderTaskVO tenderTaskVo = (HTTenderTaskVO) variables.get(WorkFlowConstant.KEY_TENDER_VO);
        String bizKey = tenderTaskVo.getTenderTaskId();//业务主键
        variables.put(WorkFlowConstant.KEY_MAIN_LEADER_USER, tenderTaskVo.getMainLeaderId());//指定主负责人
        variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, tenderTaskVo.getVerifyState());//设置审批状态
        processInstance = runtimeService.startProcessInstanceByKey(TENDER_PROCESS_KEY, bizKey);//启动投标流程
        //2.创建投标项目
        String processInstanceId = processInstance.getId();//流程实例ID
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();//创建项目
        String taskId = task.getId();
        commonActivitiService.excCompleteTask(taskId, variables);
        logger.info("创建投标项目流程已经启动。流程实例ID为：" + processInstanceId + "。任务ID为：" + taskId);
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
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, TENDER_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, taskId, variables);
        logger.info("投标流程任务办理完成。任务ID为：" + taskId);
    }

    /**
     * 撤回任务
     *
     * @param vo 投标任务对象
     * @throws SystemException 系统异常
     */
    @Override
    public void excRecallTask(Object vo) throws SystemException {
        if (vo instanceof HTTenderTaskVO) {
            HTTenderTaskVO tenderTaskVO = (HTTenderTaskVO) vo;
            String taskId = tenderTaskVO.getDelegateTaskId();
            Map<String, Object> variables = new HashMap<>();
            variables.put(WorkFlowConstant.KEY_TENDER_VO, tenderTaskVO);
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, Constant.NOT_APPROVED);
            variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, tenderTaskVO.getDelegateTaskKey());
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            commonActivitiListenVO.setDepartmentId(Constant.DEPT_ID_SC);
            commonActivitiListenVO.setIsRecall(Constant.IS_CAN_RECALL_ENABLE);
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, tenderTaskVO.getLoginUserInfo());
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            this.excHandleTask(((HTTenderTaskVO) vo).getLoginUserInfo().getLoginUserId(), variables);
            //删除历史
            historyService.deleteHistoricTaskInstance(taskId);
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
     * 办理任务
     *
     * @param variables 变量
     * @return 历史ID
     * @throws SystemException 系统异常
     */
    @Override
    public String excHandleTask(String userId, Map<String, Object> variables) throws LogicException, SystemException {
        HTTenderTaskVO tenderTaskVo = (HTTenderTaskVO) variables.get(WorkFlowConstant.KEY_TENDER_VO);
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, TENDER_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, tenderTaskVo.getDelegateTaskId(), variables);
        logger.info("投标流程任务办理完成。任务ID为：" + tenderTaskVo.getDelegateTaskId());
        return ((CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO)).getHistoryRecordId();
    }

    /**
     * 改派
     *
     * @param tenderTaskVO 投标VO
     * @throws SystemException 系统异常
     */
    @Override
    public void excReassignment(HTTenderTaskVO tenderTaskVO) throws SystemException {
        String tenderTaskId = tenderTaskVO.getTenderTaskId();
        TbTenderTaskEntity tenderTaskEntity = tenderTaskDao.selectByPrimaryKey(tenderTaskId);
        String delegateTaskId = tenderTaskEntity.getDelegateTaskId();
        String oldUser = tenderTaskEntity.getApplyUserId();
        String newUser = tenderTaskVO.getApplyUserId();
        if (oldUser.equals(newUser)) return;
        if (StringUtils.isBlank(newUser)) throw new SystemException();
        taskService.deleteCandidateUser(delegateTaskId, oldUser);
        taskService.addCandidateUser(delegateTaskId, newUser);
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        CommonActivitiListenVO commonActivitiListen = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        commonActivitiListen.setDepartmentId(Constant.DEPT_ID_SC);
        taskService.setVariable(delegateTaskId, WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListen);
        TbUserEntity user = userDao.selectByPrimaryKey(newUser);
        tenderTaskEntity.setApplyUserId(user.getUserId());
        tenderTaskEntity.setApplyUserName(user.getUserName());
        TbTenderTaskEntityExample example = new TbTenderTaskEntityExample();
        example.createCriteria().andTenderTaskIdEqualTo(tenderTaskId);
        tenderTaskDao.updateByExampleSelective(tenderTaskEntity, example);
        taskService.setVariable(delegateTaskId, WorkFlowConstant.KEY_MAIN_LEADER_USER, newUser);
        taskService.setVariable(delegateTaskId, WorkFlowConstant.KEY_DELEGATE_USER, newUser);
        taskService.setVariable(delegateTaskId, WorkFlowConstant.KEY_PRE_HANDLER, tenderTaskVO.getLoginUserInfo());
        taskService.setVariable(delegateTaskId, WorkFlowConstant.KEY_HANDLE_TIME, new Date());
        logger.info("任务改派完成。从用户:[ " + oldUser + " ] 改派到用户：[ " + newUser + " ] ");
    }
}
