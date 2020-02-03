package com.hd.processes.listener.tender;

import com.hd.common.dao.TbTenderTaskEntityMapper;
import com.hd.common.dao.TbTenderTaskHisTaskInstEntityMapper;
import com.hd.common.entity.TbTenderTaskEntity;
import com.hd.common.entity.TbTenderTaskHisTaskInstEntity;
import com.hd.common.util.*;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTTenderTaskVO;
import com.hd.processes.listener.abs.AbsActTaskListener;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ListenerTenderSuper extends AbsActTaskListener {

    static final String IS_DONE = "1";

    //是否是撤回状态
    static final String IS_RECALL = "1";

    @Resource
    private TbTenderTaskHisTaskInstEntityMapper tenderTaskHisDao;

    /**
     * 保存审批记录
     *
     * @param delegateExecution 执行对象
     * @param approveTime       审批时间
     */
    @Override
    public void saveApproveRecord(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables) {
        String historyId = UUID.randomUUID().toString();
        HTTenderTaskVO tenderTaskVo = (HTTenderTaskVO) variables.get(WorkFlowConstant.KEY_TENDER_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS));
        String rejectReason = Constant.NOT_APPROVED.equals(approvalStatus) ? StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_REJECT_REASON)) : "";
        TbTenderTaskHisTaskInstEntity tenderTaskHisTaskInst = new TbTenderTaskHisTaskInstEntity();
        tenderTaskHisTaskInst.setHistoryId(historyId);
        tenderTaskHisTaskInst.setTenderTaskId(tenderTaskVo.getTenderTaskId());
        tenderTaskHisTaskInst.setProcessInstId(delegateExecution.getProcessInstanceId());
        tenderTaskHisTaskInst.setTaskKey(delegateExecution.getCurrentActivityId());
        tenderTaskHisTaskInst.setIsPass(approvalStatus);
        tenderTaskHisTaskInst.setRejectReason(rejectReason);
        tenderTaskHisTaskInst.setApproveUserId(tenderTaskVo.getLoginUserInfo().getLoginUserId());
        tenderTaskHisTaskInst.setApproveTime(approveTime);
        tenderTaskHisDao.insertSelective(tenderTaskHisTaskInst);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        activitiListenVO.setHistoryRecordId(historyId);
    }

    public void exeApprove(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables, HTTenderTaskVO tenderTaskVO) throws Exception {
        this.saveApproveRecord(delegateExecution, approveTime, variables);
        this.updateBizData(tenderTaskVO, TbTenderTaskEntity.class, TbTenderTaskEntityMapper.class);
    }

}
