package com.hd.processes.listener.factory;

import com.hd.base.enums.WriteContractEnum;
import com.hd.common.dao.TbContractEntityMapper;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.manager.vo.HTContractVO;
import com.hd.processes.listener.writeContract.ListenerContractGmv;
import com.hd.processes.listener.writeContract.ListenerContractMv;
import com.hd.processes.listener.writeContract.ListenerContractQv;
import com.hd.processes.listener.writeContract.ListenerContractSign;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ContractFactory implements VerifyObjectFactory {

    private static final WriteContractEnum CONTRACT_QUALITY_VERIFY = WriteContractEnum.QualityVerify;
    private static final WriteContractEnum CONTRACT_MANAGER_VERIFY = WriteContractEnum.ManagerVerify;
    private static final WriteContractEnum CONTRACT_GENER_MANAGER_VERIFY = WriteContractEnum.GenerManagerVerify;
    private static final WriteContractEnum CONTRACT_SIGNER = WriteContractEnum.Signer;
    private static final WriteContractEnum CONTRACT_SEAL = WriteContractEnum.Seal;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    /**
     * 一级审批任务监听
     */
    @Override
    public VerifyObject produceMvTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(CONTRACT_MANAGER_VERIFY.getNodeSort());
        verifyObject.setBaseVO(contractVO);
        verifyObject.setSubmitNode(CONTRACT_QUALITY_VERIFY.getNodeKey());
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setEntityClass(TbContractEntity.class);
        verifyObject.setDaoClass(TbContractEntityMapper.class);
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
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerContractMv.class);
        verifyObject.setBaseVO(contractVO);
        verifyObject.setRecallSubmitNode(CONTRACT_MANAGER_VERIFY.getNodeKey());
        verifyObject.setSubmitNode(CONTRACT_MANAGER_VERIFY.getNodeKey());
        verifyObject.setEntityClass(TbContractEntity.class);
        verifyObject.setDaoClass(TbContractEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批任务监听
     */
    @Override
    public VerifyObject produceQvTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(CONTRACT_QUALITY_VERIFY.getNodeSort());
        String departmentId = CONTRACT_QUALITY_VERIFY.getDepartmentId();//质检部部门ID
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(CONTRACT_GENER_MANAGER_VERIFY.getNodeKey());
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(contractVO);
        verifyObject.setEntityClass(TbContractEntity.class);
        verifyObject.setDaoClass(TbContractEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批执行监听
     */
    @Override
    public VerifyObject produceQvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        String submitNode = CONTRACT_QUALITY_VERIFY.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = CONTRACT_GENER_MANAGER_VERIFY.getNodeKey();
        }
        contractVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerContractQv.class);
        verifyObject.setRecallSubmitNode(CONTRACT_MANAGER_VERIFY.getNodeKey());
        verifyObject.setBaseVO(contractVO);
        verifyObject.setEntityClass(TbContractEntity.class);
        verifyObject.setDaoClass(TbContractEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批任务监听
     */
    @Override
    public VerifyObject produceGmvTask(DelegateTask delegateTask) {
        String submitNode = CONTRACT_SIGNER.getNodeKey();
        VerifyObject verifyObject = new VerifyObject();
        String nodeSort = StringToolUtils.convertNullObjectToString(CONTRACT_GENER_MANAGER_VERIFY.getNodeSort());
        String departmentId = CONTRACT_GENER_MANAGER_VERIFY.getDepartmentId();//质检部部门ID
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(contractVO);
        verifyObject.setEntityClass(TbContractEntity.class);
        verifyObject.setDaoClass(TbContractEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批执行监听
     */
    @Override
    public VerifyObject produceGmvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        String submitNode = CONTRACT_SIGNER.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.NOT_APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = CONTRACT_GENER_MANAGER_VERIFY.getNodeKey();
        }
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        contractVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerContractGmv.class);
        verifyObject.setRecallSubmitNode(CONTRACT_MANAGER_VERIFY.getNodeKey());
        verifyObject.setBaseVO(contractVO);
        verifyObject.setEntityClass(TbContractEntity.class);
        verifyObject.setDaoClass(TbContractEntityMapper.class);
        return verifyObject;
    }

    /**
     * 签发任务监听
     */
    @Override
    public VerifyObject produceSignTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        String departmentId = contractVO.getDepartmentId();
        String nodeSort = StringToolUtils.convertNullObjectToString(CONTRACT_SIGNER.getNodeSort());
        String submitNode = CONTRACT_SEAL.getNodeKey();
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(contractVO);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setEntityClass(TbContractEntity.class);
        verifyObject.setDaoClass(TbContractEntityMapper.class);
        return verifyObject;
    }

    /**
     * 签发执行监听
     */
    @Override
    public VerifyObject produceSignExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        String submitNode = CONTRACT_SEAL.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.NOT_APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = CONTRACT_SIGNER.getNodeKey();
        }
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerContractSign.class);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setBaseVO(contractVO);
        return verifyObject;
    }

}
