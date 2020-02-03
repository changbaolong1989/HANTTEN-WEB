package com.hd.processes.listener.task.reedit;

import com.hd.common.dao.TbTaskReeditEntityMapper;
import com.hd.common.dao.TbTaskReeditHisTaskInstMapper;
import com.hd.common.entity.TbTaskReeditEntity;
import com.hd.common.entity.TbTaskReeditHisTaskInst;
import com.hd.common.util.Constant;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTTaskReeditVO;
import com.hd.processes.listener.abs.AbsActTaskListener;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ListenerTaskReeditSuper extends AbsActTaskListener {

    //是否是撤回状态
    static final String IS_NOT_RECALL = "0";

    @Resource
    private TbTaskReeditHisTaskInstMapper taskReeditHisDao;

    public void exeApprove(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables, HTTaskReeditVO taskReeditVO) throws Exception {
        saveApproveRecord(delegateExecution, approveTime, variables);
        updateBizData(taskReeditVO, TbTaskReeditEntity.class, TbTaskReeditEntityMapper.class);
    }

    /**
     * 保存审批记录
     *
     * @param delegateExecution 执行对象
     * @param approveTime       审批时间
     */
    @Override
    public void saveApproveRecord(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables) {
        String historyId = UUID.randomUUID().toString();
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS));
        String rejectReason = Constant.NOT_APPROVED.equals(approvalStatus) ? StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_REJECT_REASON)) : "";
        TbTaskReeditHisTaskInst taskReeditHisTaskInst = new TbTaskReeditHisTaskInst();
        taskReeditHisTaskInst.setHistoryId(historyId);
        taskReeditHisTaskInst.setTaskId(taskReeditVO.getTaskId());
        taskReeditHisTaskInst.setTaskReeditId(taskReeditVO.getReeditId());
        taskReeditHisTaskInst.setProcessInstId(delegateExecution.getProcessInstanceId());
        taskReeditHisTaskInst.setTaskKey(delegateExecution.getCurrentActivityId());
        taskReeditHisTaskInst.setIsPass(approvalStatus);
        taskReeditHisTaskInst.setRejectReason(rejectReason);
        taskReeditHisTaskInst.setApproveUserId(taskReeditVO.getLoginUserInfo().getLoginUserId());
        taskReeditHisTaskInst.setApproveTime(approveTime);
        taskReeditHisTaskInst.setIsDelFlg(IS_NOT_DEL_FLAG);
        taskReeditHisDao.insertSelective(taskReeditHisTaskInst);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        activitiListenVO.setHistoryRecordId(historyId);
    }

}
