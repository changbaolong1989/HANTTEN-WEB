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
 * 任务_成果文件监听
 * 归档节点监听
 * Archive
 */
@Service
public class ListenerTaskArc extends ListenerTaskSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 8038045801965348279L;

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
        String nodeKey = delegateTask.getTaskDefinitionKey();
        String userDeptId = TaskEnum.Archive.getDepartmentId();//行政部部门ID
        String processDictId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_PROCESS_DICT_TYPE));//流程字典ID
        //更新业务数据
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        taskVO.setDelegateTaskKey(nodeKey);
        taskVO.setDelegateTaskId(delegateTaskId);
        taskVO.setSubmitNode(TaskEnum.EndEvent.getNodeKey());
        taskVO.setCurrentState(StringToolUtils.convertNullObjectToString(TaskEnum.Archive.getNodeSort()));
        setDelegation(delegateTask, activitiListenVO, taskVO, TbTaskEntity.class, TbTaskEntityMapper.class, userDeptId, processDictId);
    }
}
