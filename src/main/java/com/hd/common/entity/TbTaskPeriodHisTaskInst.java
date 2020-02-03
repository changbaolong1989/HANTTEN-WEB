package com.hd.common.entity;

import java.util.Date;

public class TbTaskPeriodHisTaskInst {
    /**
     * history_id
     */
    private String historyId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务和阶段的关联ID
     */
    private String taskPeriodId;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 任务Key
     */
    private String taskKey;

    /**
     * 是否通过
     */
    private String isPass;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 审批人
     */
    private String approveUserId;

    /**
     * 审批时间
     */
    private Date approveTime;

    /**
     * 是否是成果文件阶段（0：否 1：是）
     */
    private String isResultFile;

    /**
     * 是否删除
     */
    private String isDelFlg;

    /**
     * history_id
     * @return history_id history_id
     */
    public String getHistoryId() {
        return historyId;
    }

    /**
     * history_id
     * @param historyId history_id
     */
    public void setHistoryId(String historyId) {
        this.historyId = historyId == null ? null : historyId.trim();
    }

    /**
     * 任务ID
     * @return task_id 任务ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务ID
     * @param taskId 任务ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * 任务和阶段的关联ID
     * @return task_period_id 任务和阶段的关联ID
     */
    public String getTaskPeriodId() {
        return taskPeriodId;
    }

    /**
     * 任务和阶段的关联ID
     * @param taskPeriodId 任务和阶段的关联ID
     */
    public void setTaskPeriodId(String taskPeriodId) {
        this.taskPeriodId = taskPeriodId == null ? null : taskPeriodId.trim();
    }

    /**
     * 流程实例ID
     * @return process_inst_id 流程实例ID
     */
    public String getProcessInstId() {
        return processInstId;
    }

    /**
     * 流程实例ID
     * @param processInstId 流程实例ID
     */
    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId == null ? null : processInstId.trim();
    }

    /**
     * 任务Key
     * @return task_key 任务Key
     */
    public String getTaskKey() {
        return taskKey;
    }

    /**
     * 任务Key
     * @param taskKey 任务Key
     */
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey == null ? null : taskKey.trim();
    }

    /**
     * 是否通过
     * @return is_pass 是否通过
     */
    public String getIsPass() {
        return isPass;
    }

    /**
     * 是否通过
     * @param isPass 是否通过
     */
    public void setIsPass(String isPass) {
        this.isPass = isPass == null ? null : isPass.trim();
    }

    /**
     * 拒绝原因
     * @return reject_reason 拒绝原因
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * 拒绝原因
     * @param rejectReason 拒绝原因
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }

    /**
     * 审批人
     * @return approve_user_id 审批人
     */
    public String getApproveUserId() {
        return approveUserId;
    }

    /**
     * 审批人
     * @param approveUserId 审批人
     */
    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId == null ? null : approveUserId.trim();
    }

    /**
     * 审批时间
     * @return approve_time 审批时间
     */
    public Date getApproveTime() {
        return approveTime;
    }

    /**
     * 审批时间
     * @param approveTime 审批时间
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * 是否是成果文件阶段（0：否 1：是）
     * @return is_result_file 是否是成果文件阶段（0：否 1：是）
     */
    public String getIsResultFile() {
        return isResultFile;
    }

    /**
     * 是否是成果文件阶段（0：否 1：是）
     * @param isResultFile 是否是成果文件阶段（0：否 1：是）
     */
    public void setIsResultFile(String isResultFile) {
        this.isResultFile = isResultFile == null ? null : isResultFile.trim();
    }

    /**
     * 是否删除
     * @return is_del_flg 是否删除
     */
    public String getIsDelFlg() {
        return isDelFlg;
    }

    /**
     * 是否删除
     * @param isDelFlg 是否删除
     */
    public void setIsDelFlg(String isDelFlg) {
        this.isDelFlg = isDelFlg == null ? null : isDelFlg.trim();
    }
}