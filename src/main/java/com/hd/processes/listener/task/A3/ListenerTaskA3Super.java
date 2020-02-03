package com.hd.processes.listener.task.A3;

import com.hd.common.dao.TbTaskPeriodHisTaskInstMapper;
import com.hd.common.dao.TbTaskPeriodRelationEntityMapper;
import com.hd.common.entity.TbTaskPeriodHisTaskInst;
import com.hd.common.entity.TbTaskPeriodRelationEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTTaskPeriodVO;
import com.hd.processes.listener.abs.AbsActTaskListener;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ListenerTaskA3Super extends AbsActTaskListener {

    //是否是撤回状态
    static final String IS_RECALL = "1";

    @Resource
    private TbTaskPeriodHisTaskInstMapper taskPeriodHisDao;

    public void exeApprove(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables, HTTaskPeriodVO taskPeriodVO) throws Exception {
        saveApproveRecord(delegateExecution, approveTime, variables);
        updateBizData(taskPeriodVO, TbTaskPeriodRelationEntity.class, TbTaskPeriodRelationEntityMapper.class);
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
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_APPROVAL_STATUS));
        String rejectReason = Constant.NOT_APPROVED.equals(approvalStatus) ? StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_REJECT_REASON)) : "";
        TbTaskPeriodHisTaskInst taskPeriodHisTaskInst = new TbTaskPeriodHisTaskInst();
        taskPeriodHisTaskInst.setHistoryId(historyId);
        taskPeriodHisTaskInst.setTaskId(taskPeriodVO.getTaskId());
        taskPeriodHisTaskInst.setTaskPeriodId(taskPeriodVO.getRelationId());
        taskPeriodHisTaskInst.setProcessInstId(delegateExecution.getProcessInstanceId());
        taskPeriodHisTaskInst.setTaskKey(delegateExecution.getCurrentActivityId());
        taskPeriodHisTaskInst.setIsPass(approvalStatus);
        taskPeriodHisTaskInst.setRejectReason(rejectReason);
        taskPeriodHisTaskInst.setApproveUserId(taskPeriodVO.getLoginUserInfo().getLoginUserId());
        taskPeriodHisTaskInst.setApproveTime(approveTime);
        taskPeriodHisTaskInst.setIsDelFlg(IS_NOT_DEL_FLAG);
        taskPeriodHisDao.insertSelective(taskPeriodHisTaskInst);
        CommonActivitiListenVO activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        activitiListenVO.setHistoryRecordId(historyId);
    }

}
