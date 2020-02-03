package com.hd.processes.listener.factory;

import com.hd.base.enums.TenderEnum;
import com.hd.common.dao.TbTenderTaskEntityMapper;
import com.hd.common.entity.TbTenderTaskEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.manager.vo.HTTenderTaskVO;
import com.hd.processes.listener.tender.ListenerTenderGmv;
import com.hd.processes.listener.tender.ListenerTenderMv;
import com.hd.processes.listener.tender.ListenerTenderQv;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class TenderFactory implements VerifyObjectFactory {

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    private static final TenderEnum TENDER_MANAGER_VERIFY = TenderEnum.ManagerVerify;
    private static final TenderEnum TENDER_QUALITY_VERIFY = TenderEnum.QualityVerify;
    private static final TenderEnum TENDER_GEN_MANAGER_VERIFY = TenderEnum.GenerManagerVerify;
    private static final TenderEnum TENDER_END_EVENT = TenderEnum.EndEvent;

    /**
     * 一级审批任务监听
     */
    @Override
    public VerifyObject produceMvTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTenderTaskVO tenderTaskVO = (HTTenderTaskVO) variables.get(WorkFlowConstant.KEY_TENDER_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(TENDER_MANAGER_VERIFY.getNodeSort());
        verifyObject.setBaseVO(tenderTaskVO);
        verifyObject.setSubmitNode(TENDER_QUALITY_VERIFY.getNodeKey());
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setEntityClass(TbTenderTaskEntity.class);
        verifyObject.setDaoClass(TbTenderTaskEntityMapper.class);
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
        HTTenderTaskVO tenderTaskVo = (HTTenderTaskVO) variables.get(WorkFlowConstant.KEY_TENDER_VO);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTenderMv.class);
        verifyObject.setBaseVO(tenderTaskVo);
        verifyObject.setRecallSubmitNode(TENDER_MANAGER_VERIFY.getNodeKey());
        verifyObject.setSubmitNode(TENDER_MANAGER_VERIFY.getNodeKey());
        verifyObject.setEntityClass(TbTenderTaskEntity.class);
        verifyObject.setDaoClass(TbTenderTaskEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批任务监听
     */
    @Override
    public VerifyObject produceQvTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTenderTaskVO tenderTaskVO = (HTTenderTaskVO) variables.get(WorkFlowConstant.KEY_TENDER_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(TENDER_QUALITY_VERIFY.getNodeSort());
        String departmentId = TENDER_QUALITY_VERIFY.getDepartmentId();//质检部部门ID
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(TENDER_GEN_MANAGER_VERIFY.getNodeKey());
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(tenderTaskVO);
        verifyObject.setEntityClass(TbTenderTaskEntity.class);
        verifyObject.setDaoClass(TbTenderTaskEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批执行监听
     */
    @Override
    public VerifyObject produceQvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        HTTenderTaskVO tenderTaskVO = (HTTenderTaskVO) variables.get(WorkFlowConstant.KEY_TENDER_VO);
        String submitNode = TENDER_QUALITY_VERIFY.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = TENDER_GEN_MANAGER_VERIFY.getNodeKey();
        }
        tenderTaskVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTenderQv.class);
        verifyObject.setRecallSubmitNode(TENDER_MANAGER_VERIFY.getNodeKey());
        verifyObject.setBaseVO(tenderTaskVO);
        verifyObject.setEntityClass(TbTenderTaskEntity.class);
        verifyObject.setDaoClass(TbTenderTaskEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批任务监听
     */
    @Override
    public VerifyObject produceGmvTask(DelegateTask delegateTask) {
        String submitNode = TENDER_END_EVENT.getNodeKey();
        VerifyObject verifyObject = new VerifyObject();
        String nodeSort = StringToolUtils.convertNullObjectToString(TENDER_GEN_MANAGER_VERIFY.getNodeSort());
        String departmentId = TENDER_GEN_MANAGER_VERIFY.getDepartmentId();//质检部部门ID
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTenderTaskVO tenderTaskVO = (HTTenderTaskVO) variables.get(WorkFlowConstant.KEY_TENDER_VO);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(tenderTaskVO);
        verifyObject.setEntityClass(TbTenderTaskEntity.class);
        verifyObject.setDaoClass(TbTenderTaskEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批执行监听
     */
    @Override
    public VerifyObject produceGmvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        String submitNode = TENDER_END_EVENT.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.NOT_APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = TENDER_GEN_MANAGER_VERIFY.getNodeKey();
        }
        HTTenderTaskVO tenderTaskVO = (HTTenderTaskVO) variables.get(WorkFlowConstant.KEY_TENDER_VO);
        tenderTaskVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTenderGmv.class);
        verifyObject.setRecallSubmitNode(TENDER_MANAGER_VERIFY.getNodeKey());
        verifyObject.setBaseVO(tenderTaskVO);
        verifyObject.setEntityClass(TbTenderTaskEntity.class);
        verifyObject.setDaoClass(TbTenderTaskEntityMapper.class);
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
