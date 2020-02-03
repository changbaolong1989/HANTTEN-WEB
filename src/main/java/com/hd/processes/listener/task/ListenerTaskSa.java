package com.hd.processes.listener.task;

import com.hd.base.enums.TaskEnum;
import com.hd.common.dao.TbTaskEntityMapper;
import com.hd.common.entity.TbTaskEntity;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTTaskVO;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 任务_成果文件审批监听
 * 提交归档节点监听
 * SubmitArchive
 */
@Service
public class ListenerTaskSa extends ListenerTaskSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -2407747529539112090L;

    @Resource
    private TaskService taskService;

    @Override
    public void notify(DelegateExecution delegateExecution) {

    }

    @Override
    public void notify(DelegateTask delegateTask) {
        //委托任务的节点ID
        String delegateTaskId = delegateTask.getId();
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            String nodeKey = delegateTask.getTaskDefinitionKey();
            HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
            String delegateUser = StringToolUtils.convertNullObjectToString(taskVO.getApplyUserId());
            delegateTask.addCandidateUser(delegateUser);
            //更新业务数据
            taskVO.setDelegateTaskKey(nodeKey);
            taskVO.setDelegateTaskId(delegateTaskId);
            taskVO.setSubmitNode(TaskEnum.ArchiveVerify.getNodeKey());
            taskVO.setCurrentState(StringToolUtils.convertNullObjectToString(TaskEnum.SubmitArchive.getNodeSort()));
            updateBizData(taskVO, TbTaskEntity.class, TbTaskEntityMapper.class);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }
}
