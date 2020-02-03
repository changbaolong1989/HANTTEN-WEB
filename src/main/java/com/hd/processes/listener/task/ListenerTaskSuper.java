package com.hd.processes.listener.task;

import com.hd.common.dao.TbTaskEntityMapper;
import com.hd.common.dao.TbTaskPeriodHisTaskInstMapper;
import com.hd.common.entity.TbTaskEntity;
import com.hd.common.entity.TbTaskPeriodHisTaskInst;
import com.hd.common.util.Constant;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTTaskVO;
import com.hd.processes.listener.abs.AbsActTaskListener;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ListenerTaskSuper extends AbsActTaskListener {

    static final String IS_DONE = "1";

    //是否是撤回状态
    static final String IS_RECALL = "1";

    //成果文件审批记录标志
    private static final String IS_RESULT_FILE = "1";

    @Resource
    private TbTaskPeriodHisTaskInstMapper taskPeriodHisDao;

    public void exeApprove(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables, HTTaskVO taskVO) throws Exception {
        saveApproveRecord(delegateExecution, approveTime, variables);
        updateBizData(taskVO, TbTaskEntity.class, TbTaskEntityMapper.class);
    }

    /**
     * 保存审批历史记录
     */
    @Override
    public void saveApproveRecord(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables) {
        String historyId = UUID.randomUUID().toString();
        HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS));
        String rejectReason = Constant.NOT_APPROVED.equals(approvalStatus) ? StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_REJECT_REASON)) : "";
        TbTaskPeriodHisTaskInst taskPeriodHisTaskInst = new TbTaskPeriodHisTaskInst();
        taskPeriodHisTaskInst.setHistoryId(historyId);
        taskPeriodHisTaskInst.setTaskId(taskVO.getTaskId());
        taskPeriodHisTaskInst.setTaskPeriodId(taskVO.getRelationId());
        taskPeriodHisTaskInst.setProcessInstId(delegateExecution.getProcessInstanceId());
        taskPeriodHisTaskInst.setTaskKey(delegateExecution.getCurrentActivityId());
        taskPeriodHisTaskInst.setIsPass(approvalStatus);
        taskPeriodHisTaskInst.setRejectReason(rejectReason);
        taskPeriodHisTaskInst.setApproveUserId(taskVO.getLoginUserInfo().getLoginUserId());
        taskPeriodHisTaskInst.setApproveTime(approveTime);
        taskPeriodHisTaskInst.setIsResultFile(IS_RESULT_FILE);
        taskPeriodHisTaskInst.setIsDelFlg(IS_NOT_DEL_FLAG);
        taskPeriodHisDao.insertSelective(taskPeriodHisTaskInst);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        activitiListenVO.setHistoryRecordId(historyId);
    }
}
