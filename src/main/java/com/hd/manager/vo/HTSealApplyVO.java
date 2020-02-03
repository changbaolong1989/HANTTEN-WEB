package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

public class HTSealApplyVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -2143649628163969236L;

    /**
     * 业务Key
     */
    private String recordId;

    /**
     * 流程实例ID
     */
    private String processInstId;

    private String departmentId;

    /**
     * 委托任务ID
     */
    private String delegateTaskId;

    /**
     * 申请原因
     */
    private String applyReason;

    /**
     * 申请时间
     */
    @DateTimeFormat
    private String applyTime;

    /**
     * 申请人
     */
    private String applyUserName;

    /**
     * 章类型
     */
    private String sealTypeId;

    /**
     * 章类型
     */
    private String sealTypeText;

    /**
     * 是否骑缝章
     */
    private String isPagingSeal;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 附件地址
     */
    private String attachmentPath;

    /**
     * 流程完成时间
     */
    @DateTimeFormat
    private String completeTime;

    /**
     * 驳回原因
     */
    private String rejectReason;

    /**
     * 审核人ID
     */
    private String assignUserId;

    /**
     * 是否是历史待办
     */
    private String isHistory;

    /**
     * 是否显示查看原因
     */
    private String isViewReason;

    /**
     * 是否已盖章
     */
    private String isSignatured;

    /**
     * 指定办理人
     */
    private String delegateUser;

    /**
     * 业务Key
     *
     * @return record_id 业务Key
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 业务Key
     *
     * @param recordId 业务Key
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    /**
     * 流程实例ID
     *
     * @return process_inst_id 流程实例ID
     */
    public String getProcessInstId() {
        return processInstId;
    }

    /**
     * 流程实例ID
     *
     * @param processInstId 流程实例ID
     */
    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId == null ? null : processInstId.trim();
    }

    /**
     * 委托任务ID
     *
     * @return delegate_task_id 委托任务ID
     */
    public String getDelegateTaskId() {
        return delegateTaskId;
    }

    /**
     * 委托任务ID
     *
     * @param delegateTaskId 委托任务ID
     */
    public void setDelegateTaskId(String delegateTaskId) {
        this.delegateTaskId = delegateTaskId == null ? null : delegateTaskId.trim();
    }

    /**
     * 申请原因
     *
     * @return apply_reason 申请原因
     */
    public String getApplyReason() {
        return applyReason;
    }

    /**
     * 申请原因
     *
     * @param applyReason 申请原因
     */
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason == null ? null : applyReason.trim();
    }

    /**
     * 申请时间
     *
     * @return apply_time 申请时间
     */
    public String getApplyTime() {
        return applyTime;
    }

    /**
     * 申请时间
     *
     * @param applyTime 申请时间
     */
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 申请人
     *
     * @return apply_user_name 申请人
     */
    public String getApplyUserName() {
        return applyUserName;
    }

    /**
     * 申请人
     *
     * @param applyUserName 申请人
     */
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName == null ? null : applyUserName.trim();
    }

    /**
     * 章类型
     *
     * @return seal__type_id 章类型
     */
    public String getSealTypeId() {
        return sealTypeId;
    }

    /**
     * 章类型
     *
     * @param sealTypeId 章类型
     */
    public void setSealTypeId(String sealTypeId) {
        this.sealTypeId = sealTypeId == null ? null : sealTypeId.trim();
    }

    /**
     * 是否骑缝章
     *
     * @return is_paging_seal 是否骑缝章
     */
    public String getIsPagingSeal() {
        return isPagingSeal;
    }

    /**
     * 是否骑缝章
     *
     * @param isPagingSeal 是否骑缝章
     */
    public void setIsPagingSeal(String isPagingSeal) {
        this.isPagingSeal = isPagingSeal;
    }

    /**
     * 附件名称
     *
     * @return attachment_name 附件名称
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * 附件名称
     *
     * @param attachmentName 附件名称
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName == null ? null : attachmentName.trim();
    }

    /**
     * 附件地址
     *
     * @return attachment_path 附件地址
     */
    public String getAttachmentPath() {
        return attachmentPath;
    }

    /**
     * 附件地址
     *
     * @param attachmentPath 附件地址
     */
    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath == null ? null : attachmentPath.trim();
    }

    /**
     * 流程完成时间
     *
     * @return complete_time 流程完成时间
     */
    public String getCompleteTime() {
        return completeTime;
    }

    /**
     * 流程完成时间
     *
     * @param completeTime 流程完成时间
     */
    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * 驳回原因
     *
     * @return reject_reason 驳回原因
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * 驳回原因
     *
     * @param rejectReason 驳回原因
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }

    public String getAssignUserId() {
        return assignUserId;
    }

    public void setAssignUserId(String assignUserId) {
        this.assignUserId = assignUserId;
    }

    public String getSealTypeText() {
        return sealTypeText;
    }

    public void setSealTypeText(String sealTypeText) {
        this.sealTypeText = sealTypeText;
    }

    public String getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(String isHistory) {
        this.isHistory = isHistory;
    }

    public String getIsViewReason() {
        return isViewReason;
    }

    public void setIsViewReason(String isViewReason) {
        this.isViewReason = isViewReason;
    }

    public String getIsSignatured() {
        return isSignatured;
    }

    public void setIsSignatured(String isSignatured) {
        this.isSignatured = isSignatured;
    }

    public String getDelegateUser() {
        return delegateUser;
    }

    public void setDelegateUser(String delegateUser) {
        this.delegateUser = delegateUser;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
