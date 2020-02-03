package com.hd.common.entity;

import java.util.Date;

public class TbRequestMenuHisTaskInstEntity {
    /**
     * 主键
     */
    private String historyId;

    /**
     * 请款单ID
     */
    private String requestMenuId;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 任务Key
     */
    private String taskKey;

    /**
     * 是否通过 1：已通过 0：未通过
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
     * 主键
     * @return history_id 主键
     */
    public String getHistoryId() {
        return historyId;
    }

    /**
     * 主键
     * @param historyId 主键
     */
    public void setHistoryId(String historyId) {
        this.historyId = historyId == null ? null : historyId.trim();
    }

    /**
     * 请款单ID
     * @return request_menu_id 请款单ID
     */
    public String getRequestMenuId() {
        return requestMenuId;
    }

    /**
     * 请款单ID
     * @param requestMenuId 请款单ID
     */
    public void setRequestMenuId(String requestMenuId) {
        this.requestMenuId = requestMenuId == null ? null : requestMenuId.trim();
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
     * 是否通过 1：已通过 0：未通过
     * @return is_pass 是否通过 1：已通过 0：未通过
     */
    public String getIsPass() {
        return isPass;
    }

    /**
     * 是否通过 1：已通过 0：未通过
     * @param isPass 是否通过 1：已通过 0：未通过
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
}