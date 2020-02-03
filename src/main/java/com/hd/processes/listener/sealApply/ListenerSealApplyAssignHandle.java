package com.hd.processes.listener.sealApply;

import com.hd.common.entity.TbApplySealRecordEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
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
 * 请章任务监听(指定办理人)
 * 办理人节点节点监听
 * AssignHandleVerify
 */
@Service
public class ListenerSealApplyAssignHandle extends ListenerSealApplySuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 2818109702266361194L;

    @Resource
    private TaskService taskService;

    @Resource
    private HTSealApplyDao sealApplyDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
    }

    @Override
    @Transactional
    public void notify(DelegateTask delegateTask) {
        //设置委托人
        String delegateTaskId = delegateTask.getId();//委托任务的节点ID
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            //流程节点Key
            String nodeKey = delegateTask.getTaskDefinitionKey();
            String delegateUser = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_DELEGATE_USER));
            delegateTask.addCandidateUser(delegateUser);
            HTSealApplyVO sealApplyVO = (HTSealApplyVO) variables.get(WorkFlowConstant.KEY_SEAL_APPLY_VO);
            //更新业务数据
            Date tmpCurrentDate = getCurrentDate();// 临时变量：当前时间
            TbApplySealRecordEntity applySealRecordEntity = (TbApplySealRecordEntity) ObjectReflectUtils.convertObj(sealApplyVO, TbApplySealRecordEntity.class);
            applySealRecordEntity.setApplyTime(tmpCurrentDate);
            applySealRecordEntity.setDelegateTaskId(delegateTaskId);
            applySealRecordEntity.setDelegateTaskKey(nodeKey);
            applySealRecordEntity.setVerifyState(Constant.APPROVING);
            sealApplyDao.saveOrUpdateRecord(applySealRecordEntity);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

}
