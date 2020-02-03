package com.hd.processes.listener.task;

import com.hd.base.enums.TaskEnum;
import com.hd.common.dao.TbTaskEntityMapper;
import com.hd.common.entity.TbTaskEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTTaskVO;
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
 * 任务_成果文件审批监听
 * 归档审批节点监听
 * ArchiveVerify
 */
@Service
public class ListenerTaskAv extends ListenerTaskSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 7043771065970230119L;

    //是否可以撤回
    private static final String NOT_CAN_RECALL = "0";

    private CommonActivitiListenVO activitiListenVO;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    private static final TaskEnum TASK_ARCHIVE_VERIFY = TaskEnum.ArchiveVerify;
    private static final TaskEnum TASK_END_EVENT = TaskEnum.EndEvent;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
            String submitNode = TASK_END_EVENT.getNodeKey();
            if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.NOT_APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
                submitNode = TASK_ARCHIVE_VERIFY.getNodeKey();
            }
            taskVO.setSubmitNode(submitNode);
            setDepartmentId(variables, taskVO, activitiListenVO);
            Date approveTime = new Date();
            taskVO.setApproveTime(DateToolUtils.convertDateFormat(approveTime, DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            taskVO.setIsCanRecall(NOT_CAN_RECALL);
            exeApprove(delegateExecution, approveTime, variables, taskVO);
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
        String nodeKey = delegateTask.getTaskDefinitionKey();
        String userDeptId = TASK_ARCHIVE_VERIFY.getDepartmentId();
        String processDictId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_PROCESS_DICT_TYPE));//流程字典ID
        //更新业务数据
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        taskVO.setDelegateTaskKey(nodeKey);
        taskVO.setDelegateTaskId(delegateTaskId);
        taskVO.setSubmitNode(TASK_END_EVENT.getNodeKey());
        taskVO.setCurrentState(StringToolUtils.convertNullObjectToString(TASK_ARCHIVE_VERIFY.getNodeSort()));
        setDelegation(delegateTask, activitiListenVO, taskVO, TbTaskEntity.class, TbTaskEntityMapper.class, userDeptId, processDictId);
    }
}
