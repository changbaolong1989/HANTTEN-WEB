package com.hd.processes.listener.writeContract;

import com.hd.base.enums.WriteContractEnum;
import com.hd.common.dao.TbContractEntityMapper;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTContractVO;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 编撰合同任务监听
 * 归档节点监听
 * Archive
 */
@Service
public class ListenerContractArc extends ListenerContractSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -3886840499968485033L;

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
        String userDeptId = WriteContractEnum.Archive.getDepartmentId();//行政部部门ID
        String processDictId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_PROCESS_DICT_TYPE));//流程字典ID
        //更新业务数据
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        contractVO.setDelegateTaskKey(nodeKey);
        contractVO.setDelegateTaskId(delegateTaskId);
        contractVO.setSubmitNode(WriteContractEnum.EndEvent.getNodeKey());
        contractVO.setCurrentState(StringToolUtils.convertNullObjectToString(WriteContractEnum.Archive.getNodeSort()));
        setDelegation(delegateTask, activitiListenVO, contractVO, TbContractEntity.class, TbContractEntityMapper.class, userDeptId, processDictId);
    }

}
