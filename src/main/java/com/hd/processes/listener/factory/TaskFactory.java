package com.hd.processes.listener.factory;

import com.hd.base.enums.TaskEnum;
import com.hd.common.dao.TbTaskEntityMapper;
import com.hd.common.entity.TbTaskEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.manager.vo.HTTaskVO;
import com.hd.processes.listener.task.ListenerTaskGmv;
import com.hd.processes.listener.task.ListenerTaskMv;
import com.hd.processes.listener.task.ListenerTaskQv;
import com.hd.processes.listener.task.ListenerTaskSign;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class TaskFactory implements VerifyObjectFactory {

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    private static final TaskEnum TASK_MANAGER_VERIFY = TaskEnum.ManagerVerify;
    private static final TaskEnum TASK_QUALITY_VERIFY = TaskEnum.QualityVerify;
    private static final TaskEnum TASK_GENER_MANAGER_VERIFY = TaskEnum.GenerManagerVerify;
    private static final TaskEnum TASK_SIGNER = TaskEnum.Signer;
    private static final TaskEnum TASK_SUBMIT_ARCHIVE = TaskEnum.SubmitArchive;

    /**
     * 一级审批任务监听
     */
    @Override
    public VerifyObject produceMvTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_MANAGER_VERIFY.getNodeSort());
        verifyObject.setBaseVO(taskVO);
        verifyObject.setSubmitNode(TASK_QUALITY_VERIFY.getNodeKey());
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setEntityClass(TbTaskEntity.class);
        verifyObject.setDaoClass(TbTaskEntityMapper.class);
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
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskMv.class);
        verifyObject.setBaseVO(taskVO);
        verifyObject.setRecallSubmitNode(TASK_MANAGER_VERIFY.getNodeKey());
        verifyObject.setSubmitNode(TASK_MANAGER_VERIFY.getNodeKey());
        verifyObject.setEntityClass(TbTaskEntity.class);
        verifyObject.setDaoClass(TbTaskEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批任务监听
     */
    @Override
    public VerifyObject produceQvTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_QUALITY_VERIFY.getNodeSort());
        String submitNode = TASK_GENER_MANAGER_VERIFY.getNodeKey();
        String departmentId = TASK_QUALITY_VERIFY.getDepartmentId();//质检部部门ID
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(taskVO);
        verifyObject.setEntityClass(TbTaskEntity.class);
        verifyObject.setDaoClass(TbTaskEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批执行监听
     */
    @Override
    public VerifyObject produceQvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        String submitNode = TASK_QUALITY_VERIFY.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = TASK_GENER_MANAGER_VERIFY.getNodeKey();
        }
        taskVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskQv.class);
        verifyObject.setRecallSubmitNode(TASK_MANAGER_VERIFY.getNodeKey());
        verifyObject.setBaseVO(taskVO);
        verifyObject.setEntityClass(TbTaskEntity.class);
        verifyObject.setDaoClass(TbTaskEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批任务监听
     */
    @Override
    public VerifyObject produceGmvTask(DelegateTask delegateTask) {
        String submitNode = TASK_SIGNER.getNodeKey();
        VerifyObject verifyObject = new VerifyObject();
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_GENER_MANAGER_VERIFY.getNodeSort());
        String departmentId = TASK_GENER_MANAGER_VERIFY.getDepartmentId();//质检部部门ID
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(taskVO);
        verifyObject.setEntityClass(TbTaskEntity.class);
        verifyObject.setDaoClass(TbTaskEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批执行监听
     */
    @Override
    public VerifyObject produceGmvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        String submitNode = TASK_SIGNER.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.NOT_APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = TASK_GENER_MANAGER_VERIFY.getNodeKey();
        }
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        taskVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskGmv.class);
        verifyObject.setRecallSubmitNode(TASK_MANAGER_VERIFY.getNodeKey());
        verifyObject.setBaseVO(taskVO);
        verifyObject.setEntityClass(TbTaskEntity.class);
        verifyObject.setDaoClass(TbTaskEntityMapper.class);
        return verifyObject;
    }

    /**
     * 签发任务监听
     */
    @Override
    public VerifyObject produceSignTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        String departmentId = taskVO.getDepartmentId();
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_SIGNER.getNodeSort());
        String submitNode = TASK_SUBMIT_ARCHIVE.getNodeKey();
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(taskVO);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setEntityClass(TbTaskEntity.class);
        verifyObject.setDaoClass(TbTaskEntityMapper.class);
        return verifyObject;
    }

    /**
     * 签发执行监听
     */
    @Override
    public VerifyObject produceSignExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        String submitNode = TASK_SUBMIT_ARCHIVE.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.NOT_APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = TASK_SIGNER.getNodeKey();
        }
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskSign.class);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setBaseVO(taskVO);
        return verifyObject;
    }
}
