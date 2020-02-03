package com.hd.processes.listener.factory;

import com.hd.base.enums.TaskA3Enum;
import com.hd.common.dao.TbTaskPeriodRelationEntityMapper;
import com.hd.common.entity.TbTaskPeriodRelationEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.manager.vo.HTTaskPeriodVO;
import com.hd.processes.listener.task.A3.ListenerTaskA3Gmv;
import com.hd.processes.listener.task.A3.ListenerTaskA3Mv;
import com.hd.processes.listener.task.A3.ListenerTaskA3Qv;
import com.hd.processes.listener.task.A3.ListenerTaskA3Sign;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class TaskA3Factory implements VerifyObjectFactory {

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    private static final TaskA3Enum TASK_A3_MANAGER_VERIFY = TaskA3Enum.ManagerVerify;
    private static final TaskA3Enum TASK_A3_QUALITY_VERIFY = TaskA3Enum.QualityVerify;
    private static final TaskA3Enum TASK_A3_GENER_MANAGER_VERIFY = TaskA3Enum.GenerManagerVerify;
    private static final TaskA3Enum TASK_A3_SIGNER = TaskA3Enum.Signer;
    private static final TaskA3Enum TASK_A3_END_EVENT = TaskA3Enum.EndEvent;

    /**
     * 一级审批任务监听
     */
    @Override
    public VerifyObject produceMvTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_A3_MANAGER_VERIFY.getNodeSort());
        verifyObject.setBaseVO(taskPeriodVO);
        verifyObject.setSubmitNode(TASK_A3_QUALITY_VERIFY.getNodeKey());
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setEntityClass(TbTaskPeriodRelationEntity.class);
        verifyObject.setDaoClass(TbTaskPeriodRelationEntityMapper.class);
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
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskA3Mv.class);
        verifyObject.setBaseVO(taskPeriodVO);
        verifyObject.setRecallSubmitNode(TASK_A3_MANAGER_VERIFY.getNodeKey());
        verifyObject.setSubmitNode(TASK_A3_MANAGER_VERIFY.getNodeKey());
        verifyObject.setEntityClass(TbTaskPeriodRelationEntity.class);
        verifyObject.setDaoClass(TbTaskPeriodRelationEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批任务监听
     */
    @Override
    public VerifyObject produceQvTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_A3_QUALITY_VERIFY.getNodeSort());
        String submitNode = TASK_A3_GENER_MANAGER_VERIFY.getNodeKey();
        String departmentId = TASK_A3_QUALITY_VERIFY.getDepartmentId();//质检部部门ID
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(taskPeriodVO);
        verifyObject.setEntityClass(TbTaskPeriodRelationEntity.class);
        verifyObject.setDaoClass(TbTaskPeriodRelationEntityMapper.class);
        return verifyObject;
    }

    /**
     * 二级审批执行监听
     */
    @Override
    public VerifyObject produceQvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        String submitNode = TASK_A3_QUALITY_VERIFY.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = TASK_A3_GENER_MANAGER_VERIFY.getNodeKey();
        }
        taskPeriodVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskA3Qv.class);
        verifyObject.setRecallSubmitNode(TASK_A3_MANAGER_VERIFY.getNodeKey());
        verifyObject.setBaseVO(taskPeriodVO);
        verifyObject.setEntityClass(TbTaskPeriodRelationEntity.class);
        verifyObject.setDaoClass(TbTaskPeriodRelationEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批任务监听
     */
    @Override
    public VerifyObject produceGmvTask(DelegateTask delegateTask) {
        String submitNode = TASK_A3_SIGNER.getNodeKey();
        VerifyObject verifyObject = new VerifyObject();
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_A3_GENER_MANAGER_VERIFY.getNodeSort());
        String departmentId = TASK_A3_GENER_MANAGER_VERIFY.getDepartmentId();//质检部部门ID
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(taskPeriodVO);
        verifyObject.setEntityClass(TbTaskPeriodRelationEntity.class);
        verifyObject.setDaoClass(TbTaskPeriodRelationEntityMapper.class);
        return verifyObject;
    }

    /**
     * 三级审批执行监听
     */
    @Override
    public VerifyObject produceGmvExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        String submitNode = TASK_A3_SIGNER.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.NOT_APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = TASK_A3_GENER_MANAGER_VERIFY.getNodeKey();
        }
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        taskPeriodVO.setSubmitNode(submitNode);
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskA3Gmv.class);
        verifyObject.setRecallSubmitNode(TASK_A3_MANAGER_VERIFY.getNodeKey());
        verifyObject.setBaseVO(taskPeriodVO);
        verifyObject.setEntityClass(TbTaskPeriodRelationEntity.class);
        verifyObject.setDaoClass(TbTaskPeriodRelationEntityMapper.class);
        return verifyObject;
    }

    /**
     * 签发任务监听
     */
    @Override
    public VerifyObject produceSignTask(DelegateTask delegateTask) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = taskService.getVariables(delegateTask.getId());
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        String departmentId = taskPeriodVO.getDepartmentId();
        String nodeSort = StringToolUtils.convertNullObjectToString(TASK_A3_SIGNER.getNodeSort());
        String submitNode = TASK_A3_END_EVENT.getNodeKey();
        verifyObject.setDelegateTask(delegateTask);
        verifyObject.setDepartmentId(departmentId);
        verifyObject.setBaseVO(taskPeriodVO);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setCurrentState(nodeSort);
        verifyObject.setEntityClass(TbTaskPeriodRelationEntity.class);
        verifyObject.setDaoClass(TbTaskPeriodRelationEntityMapper.class);
        return verifyObject;
    }

    /**
     * 签发执行监听
     */
    @Override
    public VerifyObject produceSignExe(DelegateExecution delegateExecution) {
        VerifyObject verifyObject = new VerifyObject();
        Map<String, Object> variables = runtimeService.getVariables(delegateExecution.getId());
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        String submitNode = TASK_A3_END_EVENT.getNodeKey();
        if (variables.containsKey(WorkFlowConstant.KEY_APPROVAL_STATUS) && Constant.NOT_APPROVED.equals(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS))) {
            submitNode = TASK_A3_SIGNER.getNodeKey();
        }
        verifyObject.setDelegateExecution(delegateExecution);
        verifyObject.setReflectMethodClass(ListenerTaskA3Sign.class);
        verifyObject.setSubmitNode(submitNode);
        verifyObject.setBaseVO(taskPeriodVO);
        return verifyObject;
    }

}
