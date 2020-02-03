package com.hd.processes.listener.cashApply;

import com.hd.base.enums.CashApplyEnum;
import com.hd.common.dao.TbRequestMenuEntityMapper;
import com.hd.common.entity.TbRequestMenuEntity;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTRequestMenuVO;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 请款任务监听
 * 财务开具发票节点监听
 * FinanceInvoice
 */
@Service
public class ListenerCashApplyFi extends ListenerCashApplySuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -5572187244135984874L;

    @Resource
    private TaskService taskService;


    @Override
    public void notify(DelegateExecution delegateExecution) {

    }

    @Override
    public void notify(DelegateTask delegateTask) {
        String userDeptId = CashApplyEnum.FinanceInvoice.getDepartmentId();//财务部部门ID
        //委托任务的节点ID
        String delegateTaskId = delegateTask.getId();
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        String processDictId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_PROCESS_DICT_TYPE));//流程字典ID
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        String nodeKey = delegateTask.getTaskDefinitionKey();
        //更新业务数据
        HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
        requestMenuVO.setDelegateTaskKey(nodeKey);
        requestMenuVO.setDelegateTaskId(delegateTaskId);
        requestMenuVO.setSubmitNode(CashApplyEnum.IssueInvoice.getNodeKey());
        requestMenuVO.setCurrentState(StringToolUtils.convertNullObjectToString(CashApplyEnum.FinanceInvoice.getNodeSort()));
        setDelegation(delegateTask, activitiListenVO, requestMenuVO, TbRequestMenuEntity.class, TbRequestMenuEntityMapper.class, userDeptId, processDictId);
    }

}
