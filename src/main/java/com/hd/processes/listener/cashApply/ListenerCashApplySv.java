package com.hd.processes.listener.cashApply;

import com.hd.base.enums.CashApplyEnum;
import com.hd.base.exception.LogicException;
import com.hd.common.dao.TbRequestMenuEntityMapper;
import com.hd.common.entity.TbRequestMenuEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTRequestMenuVO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 请款任务监听
 * 提交审核节点监听
 * SubmitVerify
 */
@Service
public class ListenerCashApplySv extends ListenerCashApplySuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 6406967555148053871L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            //更新业务数据
            HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
            requestMenuVO.setApplyUserId(requestMenuVO.getLoginUserInfo().getLoginUserId());
            requestMenuVO.setApplyUserName(requestMenuVO.getLoginUserInfo().getUserName());
            requestMenuVO.setIsCanRecall(IS_RECALL);
            requestMenuVO.setApplyTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            requestMenuVO.setVerifyState(Constant.APPROVING);
            updateBizData(requestMenuVO, TbRequestMenuEntity.class, TbRequestMenuEntityMapper.class);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        //委托任务的节点ID
        String delegateTaskId = delegateTask.getId();
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            //流程节点Key
            String nodeKey = delegateTask.getTaskDefinitionKey();
            HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
            String mainLeaderUser = StringToolUtils.convertNullObjectToString(taskService.getVariable(delegateTaskId, WorkFlowConstant.KEY_DELEGATE_USER));
            //分配执行人,针对请款单特殊处理：撤回时灭有待办
            if (!(StringUtils.isNotBlank(requestMenuVO.getApplyUserId()) && StringUtils.isBlank(requestMenuVO.getApproveUserId()))) {
                if (StringUtils.isBlank(mainLeaderUser)) throw new LogicException("无委托人异常");
                delegateTask.addCandidateUser(mainLeaderUser);
            }
            //更新业务数据
            requestMenuVO.setProcessInstId(delegateTask.getProcessInstanceId());
            requestMenuVO.setDelegateTaskKey(nodeKey);
            requestMenuVO.setDelegateTaskId(delegateTaskId);
            requestMenuVO.setApplyUserId(mainLeaderUser);
            requestMenuVO.setCurrentState(StringToolUtils.convertNullObjectToString(CashApplyEnum.SubmitVerify.getNodeSort()));
            updateBizData(requestMenuVO, TbRequestMenuEntity.class, TbRequestMenuEntityMapper.class);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

}
