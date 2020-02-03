package com.hd.processes.listener.task.reedit;

import com.hd.base.enums.TaskReeditEnum;
import com.hd.common.dao.TbTaskEntityMapper;
import com.hd.common.dao.TbTaskReeditEntityMapper;
import com.hd.common.entity.TbTaskEntity;
import com.hd.common.entity.TbTaskReeditEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTTaskReeditVO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 任务_重新编辑申请审批监听
 * 提交审核节点监听
 * SubmitVerify
 */
@Service
public class ListenerTaskReeditSv extends ListenerTaskReeditSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 5074441194065268664L;

    private static final String IS_SUBMIT_REEDIT = "1";

    private CommonActivitiListenVO activitiListenVO;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TbTaskEntityMapper taskDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        //更新业务数据
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
            taskReeditVO.setApplyUserId(taskReeditVO.getLoginUserInfo().getLoginUserId());
            taskReeditVO.setApplyUserName(taskReeditVO.getLoginUserInfo().getUserName());
            taskReeditVO.setIsCanRecall(IS_NOT_RECALL);
            taskReeditVO.setVerifyState(Constant.APPROVING);
            taskReeditVO.setApplyTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            updateBizData(taskReeditVO, TbTaskReeditEntity.class, TbTaskReeditEntityMapper.class);
            TbTaskEntity taskEntity = new TbTaskEntity();
            taskEntity.setTaskId(taskReeditVO.getTaskId());
            taskEntity.setIsSubmitReedit(IS_SUBMIT_REEDIT);
            taskDao.updateByPrimaryKeySelective(taskEntity);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        //委托任务的节点ID
        String delegateTaskId = delegateTask.getId();
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            //流程节点Key
            String nodeKey = delegateTask.getTaskDefinitionKey();
            //更新业务数据
            HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
            taskReeditVO.setProcessInstId(delegateTask.getProcessInstanceId());
            taskReeditVO.setDelegateTaskKey(nodeKey);
            taskReeditVO.setDelegateTaskId(delegateTaskId);
            taskReeditVO.setCurrentState(StringToolUtils.convertNullObjectToString(TaskReeditEnum.SubmitVerify.getNodeSort()));
            updateBizData(taskReeditVO, TbTaskReeditEntity.class, TbTaskReeditEntityMapper.class);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

}
