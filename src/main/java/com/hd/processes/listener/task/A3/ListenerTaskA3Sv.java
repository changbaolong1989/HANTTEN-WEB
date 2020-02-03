package com.hd.processes.listener.task.A3;

import com.hd.base.enums.TaskA3Enum;
import com.hd.common.dao.TbTaskPeriodRelationEntityMapper;
import com.hd.common.entity.TbTaskPeriodRelationEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTTaskPeriodVO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 任务_A3审批监听
 * 提交审核节点监听
 * SubmitVerify
 */
@Service
public class ListenerTaskA3Sv extends ListenerTaskA3Super implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -7076776640793711380L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        //更新业务数据
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
            taskPeriodVO.setApplyUserId(taskPeriodVO.getLoginUserInfo().getLoginUserId());
            taskPeriodVO.setApplyUserName(taskPeriodVO.getLoginUserInfo().getUserName());
            taskPeriodVO.setIsCanRecall(IS_RECALL);
            taskPeriodVO.setVerifyState(Constant.APPROVING);
            taskPeriodVO.setApplyTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            updateBizData(taskPeriodVO, TbTaskPeriodRelationEntity.class, TbTaskPeriodRelationEntityMapper.class);
            variables.put(WorkFlowConstant.KEY_DELEGATE_USER, taskPeriodVO.getLoginUserInfo().getLoginUserId());
            runtimeService.setVariables(exeId, variables);
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
            if (variables.containsKey(WorkFlowConstant.KEY_DELEGATE_USER) && StringUtils.isNotBlank(StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_DELEGATE_USER)))) {
                String delegateUser = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_DELEGATE_USER));
                delegateTask.addCandidateUser(delegateUser);
            }
            //更新业务数据
            HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
            taskPeriodVO.setTaskProcessInstId(taskPeriodVO.getTaskProcessInstId());
            taskPeriodVO.setProcessInstId(delegateTask.getProcessInstanceId());
            taskPeriodVO.setDelegateTaskKey(nodeKey);
            taskPeriodVO.setDelegateTaskId(delegateTaskId);
            taskPeriodVO.setCurrentState(StringToolUtils.convertNullObjectToString(TaskA3Enum.SubmitVerify.getNodeSort()));
            updateBizData(taskPeriodVO, TbTaskPeriodRelationEntity.class, TbTaskPeriodRelationEntityMapper.class);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

}
