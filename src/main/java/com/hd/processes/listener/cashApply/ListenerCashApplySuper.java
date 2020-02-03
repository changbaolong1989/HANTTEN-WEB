package com.hd.processes.listener.cashApply;

import com.hd.common.dao.TbRequestMenuEntityMapper;
import com.hd.common.dao.TbRequestMenuHisTaskInstEntityMapper;
import com.hd.common.entity.TbRequestMenuEntity;
import com.hd.common.entity.TbRequestMenuHisTaskInstEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTRequestMenuVO;
import com.hd.processes.listener.abs.AbsActTaskListener;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ListenerCashApplySuper extends AbsActTaskListener {

    static final String IS_DONE = "1";

    //是否是撤回状态
    static final String IS_RECALL = "1";

    @Resource
    private TbRequestMenuHisTaskInstEntityMapper requestMenuHisDao;

    /**
     * 保存审批历史记录
     */
    @Override
    public void saveApproveRecord(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables) {
        String historyId = UUID.randomUUID().toString();
        HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS));
        String rejectReason = Constant.NOT_APPROVED.equals(approvalStatus) ? StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_REJECT_REASON)) : "";
        TbRequestMenuHisTaskInstEntity requestMenuHisEntity = new TbRequestMenuHisTaskInstEntity();
        requestMenuHisEntity.setHistoryId(historyId);
        requestMenuHisEntity.setRequestMenuId(requestMenuVO.getRequestMenuId());
        requestMenuHisEntity.setProcessInstId(delegateExecution.getProcessInstanceId());
        requestMenuHisEntity.setTaskKey(delegateExecution.getCurrentActivityId());
        requestMenuHisEntity.setIsPass(approvalStatus);
        requestMenuHisEntity.setRejectReason(rejectReason);
        requestMenuHisEntity.setApproveUserId(requestMenuVO.getLoginUserInfo().getLoginUserId());
        requestMenuHisEntity.setApproveTime(approveTime);
        requestMenuHisDao.insertSelective(requestMenuHisEntity);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        activitiListenVO.setHistoryRecordId(historyId);
    }

    public void exeApprove(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables, HTRequestMenuVO requestMenuVO) throws Exception {
        this.saveApproveRecord(delegateExecution, approveTime, variables);
        this.updateBizData(requestMenuVO, TbRequestMenuEntity.class, TbRequestMenuEntityMapper.class);
    }


}
