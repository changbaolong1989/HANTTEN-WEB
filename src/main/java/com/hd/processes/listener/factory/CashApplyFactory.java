package com.hd.processes.listener.factory;

import com.hd.base.enums.CashApplyEnum;
import com.hd.common.dao.TbRequestMenuEntityMapper;
import com.hd.common.entity.TbRequestMenuEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.manager.vo.HTRequestMenuVO;
import com.hd.processes.listener.cashApply.ListenerCashApplyGmv;
import com.hd.processes.listener.cashApply.ListenerCashApplyMv;
import com.hd.processes.listener.cashApply.ListenerCashApplyQv;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CashApplyFactory implements VerifyObjectFactory {

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    private static final CashApplyEnum CASH_MANAGER_VERIFY = CashApplyEnum.ManagerVerify;
    private static final CashApplyEnum CASH_QUALITY_VERIFY = CashApplyEnum.QualityVerify;
    private static final CashApplyEnum CASH_GEN_MANAGER_VERIFY = CashApplyEnum.GenerManagerVerify;
    private static final CashApplyEnum CASH_ISSUE_INVOICE = CashApplyEnum.IssueInvoice;

    /**
     * 一级审批任务监听
     */
    @Override
    public VerifyObject produceMvTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(CASH_MANAGER_VERIFY.getNodeSort());
        verifyObject.setBaseVO(requestMenuVO);
        verifyObject.setSubmitNode(CASH_QUALITY_VERIFY.getNodeKey());
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setEntityClass(TbRequestMenuEntity.class);
        verifyObject.setDaoClass(TbRequestMenuEntityMapper.class);
        return verifyObject;
    }

    /**
     * 一级审批执行监听
     */
    @Override
    public VerifyObject produceMvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);
        HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerCashApplyMv.class);
        verifyObject.setBaseVO(requestMenuVO);
        verifyObject.setRecallSubmitNode(CASH_MANAGER_VERIFY.getNodeKey());
        verifyObject.setSubmitNode(CASH_MANAGER_VERIFY.getNodeKey());
        verifyObject.setEntityClass(TbRequestMenuEntity.class);
        verifyObject.setDaoClass(TbRequestMenuEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批任务监听
     */
    @Override
    public VerifyObject produceQvTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(CASH_QUALITY_VERIFY.getNodeSort());
        String departmentId = CASH_QUALITY_VERIFY.getDepartmentId();//质检部部门ID
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(CASH_GEN_MANAGER_VERIFY.getNodeKey());
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(requestMenuVO);
        verifyObject.setEntityClass(TbRequestMenuEntity.class);
        verifyObject.setDaoClass(TbRequestMenuEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批执行监听
     */
    @Override
    public VerifyObject produceQvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
        String submitNode = CASH_QUALITY_VERIFY.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = CASH_GEN_MANAGER_VERIFY.getNodeKey();
        }
        requestMenuVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerCashApplyQv.class);
        verifyObject.setRecallSubmitNode(CASH_MANAGER_VERIFY.getNodeKey());
        verifyObject.setBaseVO(requestMenuVO);
        verifyObject.setEntityClass(TbRequestMenuEntity.class);
        verifyObject.setDaoClass(TbRequestMenuEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批任务监听
     */
    @Override
    public VerifyObject produceGmvTask(DelegateTask delegateTask) {
        //2020.1.15 edit 请款流程增加了财务开具发票节点
        String submitNode = CashApplyEnum.FinanceInvoice.getNodeKey();
        VerifyObject verifyObject = new VerifyObject();
        String nodeSort = StringToolUtils.convertNullObjectToString(CASH_GEN_MANAGER_VERIFY.getNodeSort());
        String departmentId = CASH_GEN_MANAGER_VERIFY.getDepartmentId();//质检部部门ID
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(requestMenuVO);
        verifyObject.setEntityClass(TbRequestMenuEntity.class);
        verifyObject.setDaoClass(TbRequestMenuEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批执行监听
     */
    @Override
    public VerifyObject produceGmvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        String submitNode = CASH_ISSUE_INVOICE.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.NOT_APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = CASH_GEN_MANAGER_VERIFY.getNodeKey();
        }
        HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
        requestMenuVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerCashApplyGmv.class);
        verifyObject.setRecallSubmitNode(CASH_MANAGER_VERIFY.getNodeKey());
        verifyObject.setBaseVO(requestMenuVO);
        verifyObject.setEntityClass(TbRequestMenuEntity.class);
        verifyObject.setDaoClass(TbRequestMenuEntityMapper.class);
        return verifyObject;
    }

    /**
     * 签发任务监听
     */
    @Override
    public VerifyObject produceSignTask(DelegateTask delegateTask) {
        return null;
    }

    /**
     * 签发执行监听
     */
    @Override
    public VerifyObject produceSignExe(DelegateExecution delegateExecution) {
        return null;
    }
}
