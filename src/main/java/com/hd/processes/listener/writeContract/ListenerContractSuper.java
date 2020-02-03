package com.hd.processes.listener.writeContract;

import com.hd.common.dao.TbContractEntityMapper;
import com.hd.common.dao.TbContractHisTaskInstEntityMapper;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.entity.TbContractHisTaskInstEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTContractVO;
import com.hd.processes.listener.abs.AbsActTaskListener;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ListenerContractSuper extends AbsActTaskListener {

    //是否是撤回状态
    static final String IS_RECALL = "1";

    @Resource
    private TbContractHisTaskInstEntityMapper contractHisDao;

    /**
     * 保存审批记录
     *
     * @param delegateExecution 执行对象
     * @param approveTime       审批时间
     */
    @Override
    public void saveApproveRecord(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables) {
        String historyId = UUID.randomUUID().toString();
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS));
        String rejectReason = Constant.NOT_APPROVED.equals(approvalStatus) ? StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_REJECT_REASON)) : "";
        TbContractHisTaskInstEntity contractHisTaskInst = new TbContractHisTaskInstEntity();
        contractHisTaskInst.setHistoryId(historyId);
        contractHisTaskInst.setContractTaskId(contractVO.getContractId());
        contractHisTaskInst.setProcessInstId(delegateExecution.getProcessInstanceId());
        contractHisTaskInst.setTaskKey(delegateExecution.getCurrentActivityId());
        contractHisTaskInst.setIsPass(approvalStatus);
        contractHisTaskInst.setRejectReason(rejectReason);
        contractHisTaskInst.setApproveUserId(contractVO.getLoginUserInfo().getLoginUserId());
        contractHisTaskInst.setApproveTime(approveTime);
        contractHisDao.insertSelective(contractHisTaskInst);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        activitiListenVO.setHistoryRecordId(historyId);
    }

    public void exeApprove(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables, HTContractVO contractVO) throws Exception {
        saveApproveRecord(delegateExecution, approveTime, variables);
        updateBizData(contractVO, TbContractEntity.class, TbContractEntityMapper.class);
    }


}
