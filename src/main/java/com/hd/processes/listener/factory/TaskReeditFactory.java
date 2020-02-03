package com.hd.processes.listener.factory;

import com.hd.base.enums.TaskReeditEnum;
import com.hd.common.dao.TbTaskReeditEntityMapper;
import com.hd.common.entity.TbTaskReeditEntity;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.manager.vo.HTTaskReeditVO;
import com.hd.processes.listener.task.reedit.ListenerTaskReeditGmv;
import com.hd.processes.listener.task.reedit.ListenerTaskReeditMv;
import com.hd.processes.listener.task.reedit.ListenerTaskReeditQv;
import com.hd.processes.listener.task.reedit.ListenerTaskReeditSign;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class TaskReeditFactory implements VerifyObjectFactory {

    private static final TaskReeditEnum TASK_REEDIT_MANAGER_VERIFY = TaskReeditEnum.ManagerVerify;
    private static final TaskReeditEnum TASK_REEDIT_QUALITY_VERIFY = TaskReeditEnum.QualityVerify;
    private static final TaskReeditEnum TASK_REEDIT_GENER_MANAGER_VERIFY = TaskReeditEnum.GenerManagerVerify;
    private static final TaskReeditEnum TASK_REEDIT_SIGNER = TaskReeditEnum.Signer;
    private static final TaskReeditEnum TASK_REEDIT_END_EVENT = TaskReeditEnum.EndEvent;

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
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_REEDIT_MANAGER_VERIFY.getNodeSort());
        verifyObject.setBaseVO(taskReeditVO);
        verifyObject.setSubmitNode(TASK_REEDIT_QUALITY_VERIFY.getNodeKey());
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setEntityClass(TbTaskReeditEntity.class);
        verifyObject.setDaoClass(TbTaskReeditEntityMapper.class);
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
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskReeditMv.class);
        verifyObject.setBaseVO(taskReeditVO);
        verifyObject.setSubmitNode(TASK_REEDIT_MANAGER_VERIFY.getNodeKey());
        verifyObject.setEntityClass(TbTaskReeditEntity.class);
        verifyObject.setDaoClass(TbTaskReeditEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批任务监听
     */
    @Override
    public VerifyObject produceQvTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_REEDIT_QUALITY_VERIFY.getNodeSort());
        String submitNode = TASK_REEDIT_GENER_MANAGER_VERIFY.getNodeKey();
        String departmentId = TASK_REEDIT_QUALITY_VERIFY.getDepartmentId();//质检部部门ID
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(taskReeditVO);
        verifyObject.setEntityClass(TbTaskReeditEntity.class);
        verifyObject.setDaoClass(TbTaskReeditEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批执行监听
     */
    @Override
    public VerifyObject produceQvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        String submitNode = TASK_REEDIT_GENER_MANAGER_VERIFY.getNodeKey();
        taskReeditVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskReeditQv.class);
        verifyObject.setBaseVO(taskReeditVO);
        verifyObject.setEntityClass(TbTaskReeditEntity.class);
        verifyObject.setDaoClass(TbTaskReeditEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批任务监听
     */
    @Override
    public VerifyObject produceGmvTask(DelegateTask delegateTask) {
        String submitNode = TASK_REEDIT_SIGNER.getNodeKey();
        VerifyObject verifyObject = new VerifyObject();
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_REEDIT_GENER_MANAGER_VERIFY.getNodeSort());
        String departmentId = TASK_REEDIT_GENER_MANAGER_VERIFY.getDepartmentId();//质检部部门ID
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(taskReeditVO);
        verifyObject.setEntityClass(TbTaskReeditEntity.class);
        verifyObject.setDaoClass(TbTaskReeditEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批执行监听
     */
    @Override
    public VerifyObject produceGmvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        String submitNode = TASK_REEDIT_SIGNER.getNodeKey();
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        taskReeditVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskReeditGmv.class);
        verifyObject.setBaseVO(taskReeditVO);
        verifyObject.setEntityClass(TbTaskReeditEntity.class);
        verifyObject.setDaoClass(TbTaskReeditEntityMapper.class);
        return verifyObject;
    }

    /**
     * 签发任务监听
     */
    @Override
    public VerifyObject produceSignTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        String departmentId = taskReeditVO.getDepartmentId();
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_REEDIT_SIGNER.getNodeSort());
        String submitNode = TASK_REEDIT_END_EVENT.getNodeKey();
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(taskReeditVO);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setEntityClass(TbTaskReeditEntity.class);
        verifyObject.setDaoClass(TbTaskReeditEntityMapper.class);
        return verifyObject;
    }

    /**
     * 签发执行监听
     */
    @Override
    public VerifyObject produceSignExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        String submitNode = TASK_REEDIT_END_EVENT.getNodeKey();
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskReeditSign.class);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setBaseVO(taskReeditVO);
        return verifyObject;
    }
}
