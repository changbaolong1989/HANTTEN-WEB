package com.hd.processes.listener.sealApply;

import com.hd.base.exception.NoDelegationException;
import com.hd.common.entity.TbApplySealRecordEntity;
import com.hd.common.util.*;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.dao.HTSealApplyDao;
import com.hd.manager.vo.HTSealApplyVO;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 请章任务监听
 * 部门经理审批节点监听
 * DepartmentManagerVerify
 */
@Service
public class ListenerSealApplyDmv extends ListenerSealApplySuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -166303012174688506L;

    @Resource
    private HTSealApplyDao sealApplyDao;

    @Resource
    private TaskService taskService;

    @Override
    public void notify(DelegateExecution delegateExecution) {

    }

    @Override
    @Transactional
    public void notify(DelegateTask delegateTask) {
        //委托任务的节点ID
        String delegateTaskId = delegateTask.getId();
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            //流程节点Key
            String nodeKey = delegateTask.getTaskDefinitionKey();
            String userDeptId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_DEPARTMENT_ID));
            String processDictId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_PROCESS_DICT_TYPE));
            //根据部门设置委托人
            setDelegationUsersByDept(delegateTask, userDeptId, processDictId);
            //更新业务数据
            HTSealApplyVO sealApplyVO = (HTSealApplyVO) variables.get(WorkFlowConstant.KEY_SEAL_APPLY_VO);
            Date tmpCurrentDate = getCurrentDate();// 临时变量：当前时间
            TbApplySealRecordEntity applySealRecordEntity = (TbApplySealRecordEntity) ObjectReflectUtils.convertObj(sealApplyVO, TbApplySealRecordEntity.class);
            applySealRecordEntity.setDelegateTaskId(delegateTaskId);
            applySealRecordEntity.setApplyTime(tmpCurrentDate);
            applySealRecordEntity.setDelegateTaskKey(nodeKey);
            applySealRecordEntity.setVerifyState(Constant.APPROVING);
            sealApplyDao.saveOrUpdateRecord(applySealRecordEntity);
        } catch (NoDelegationException e) {
            activitiListenVO.setExceptionMsg(MessageConstant.MSG_INFO_PROCESS_NO_USER);
            eHandle(activitiListenVO);
        }
    }

}
