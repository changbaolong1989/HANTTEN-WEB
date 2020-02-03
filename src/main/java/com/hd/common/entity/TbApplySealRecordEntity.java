package com.hd.common.entity;

import java.util.Date;

public class TbApplySealRecordEntity {
    /**
     * 业务Key
     */
    private String recordId;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 委托任务Key
     */
    private String delegateTaskKey;

    /**
     * 委托任务ID
     */
    private String delegateTaskId;

    /**
     * 申请人ID
     */
    private String applyUserId;

    /**
     * 申请原因
     */
    private String applyReason;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 申请人
     */
    private String applyUserName;

    /**
     * 审批人
     */
    private String approveUserName;

    /**
     * 审批人ID
     */
    private String approveUserId;

    /**
     * 审批时间
     */
    private Date approveTime;

    /**
     * 流程完成时间
     */
    private Date completeTime;

    /**
     * 审核状态 0：未通过 1：通过 2：审批中
     */
    private String verifyState;

    /**
     * 驳回原因
     */
    private String rejectReason;

    /**
     * 章类型
     */
    private String sealTypeId;

    /**
     * 是否骑缝章
     */
    private Integer isPagingSeal;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 附件地址
     */
    private String attachmentPath;

    /**
     * 是否已盖章
     */
    private String isSignatured;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改人
     */
    private String updateUserId;

    /**
     * 是否删除
     */
    private String isDelFlg;

    /**
     * 业务Key
     * @return record_id 业务Key
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 业务Key
     * @param recordId 业务Key
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
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
     * 委托任务Key
     * @return delegate_task_key 委托任务Key
     */
    public String getDelegateTaskKey() {
        return delegateTaskKey;
    }

    /**
     * 委托任务Key
     * @param delegateTaskKey 委托任务Key
     */
    public void setDelegateTaskKey(String delegateTaskKey) {
        this.delegateTaskKey = delegateTaskKey == null ? null : delegateTaskKey.trim();
    }

    /**
     * 委托任务ID
     * @return delegate_task_id 委托任务ID
     */
    public String getDelegateTaskId() {
        return delegateTaskId;
    }

    /**
     * 委托任务ID
     * @param delegateTaskId 委托任务ID
     */
    public void setDelegateTaskId(String delegateTaskId) {
        this.delegateTaskId = delegateTaskId == null ? null : delegateTaskId.trim();
    }

    /**
     * 申请人ID
     * @return apply_user_id 申请人ID
     */
    public String getApplyUserId() {
        return applyUserId;
    }

    /**
     * 申请人ID
     * @param applyUserId 申请人ID
     */
    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId == null ? null : applyUserId.trim();
    }

    /**
     * 申请原因
     * @return apply_reason 申请原因
     */
    public String getApplyReason() {
        return applyReason;
    }

    /**
     * 申请原因
     * @param applyReason 申请原因
     */
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason == null ? null : applyReason.trim();
    }

    /**
     * 申请时间
     * @return apply_time 申请时间
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * 申请时间
     * @param applyTime 申请时间
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 申请人
     * @return apply_user_name 申请人
     */
    public String getApplyUserName() {
        return applyUserName;
    }

    /**
     * 申请人
     * @param applyUserName 申请人
     */
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName == null ? null : applyUserName.trim();
    }

    /**
     * 审批人
     * @return approve_user_name 审批人
     */
    public String getApproveUserName() {
        return approveUserName;
    }

    /**
     * 审批人
     * @param approveUserName 审批人
     */
    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName == null ? null : approveUserName.trim();
    }

    /**
     * 审批人ID
     * @return approve_user_id 审批人ID
     */
    public String getApproveUserId() {
        return approveUserId;
    }

    /**
     * 审批人ID
     * @param approveUserId 审批人ID
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
     * 流程完成时间
     * @return complete_time 流程完成时间
     */
    public Date getCompleteTime() {
        return completeTime;
    }

    /**
     * 流程完成时间
     * @param completeTime 流程完成时间
     */
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * 审核状态 0：未通过 1：通过 2：审批中
     * @return verify_state 审核状态 0：未通过 1：通过 2：审批中
     */
    public String getVerifyState() {
        return verifyState;
    }

    /**
     * 审核状态 0：未通过 1：通过 2：审批中
     * @param verifyState 审核状态 0：未通过 1：通过 2：审批中
     */
    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState == null ? null : verifyState.trim();
    }

    /**
     * 驳回原因
     * @return reject_reason 驳回原因
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * 驳回原因
     * @param rejectReason 驳回原因
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }

    /**
     * 章类型
     * @return seal__type_id 章类型
     */
    public String getSealTypeId() {
        return sealTypeId;
    }

    /**
     * 章类型
     * @param sealTypeId 章类型
     */
    public void setSealTypeId(String sealTypeId) {
        this.sealTypeId = sealTypeId == null ? null : sealTypeId.trim();
    }

    /**
     * 是否骑缝章
     * @return is_paging_seal 是否骑缝章
     */
    public Integer getIsPagingSeal() {
        return isPagingSeal;
    }

    /**
     * 是否骑缝章
     * @param isPagingSeal 是否骑缝章
     */
    public void setIsPagingSeal(Integer isPagingSeal) {
        this.isPagingSeal = isPagingSeal;
    }

    /**
     * 附件名称
     * @return attachment_name 附件名称
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * 附件名称
     * @param attachmentName 附件名称
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName == null ? null : attachmentName.trim();
    }

    /**
     * 附件地址
     * @return attachment_path 附件地址
     */
    public String getAttachmentPath() {
        return attachmentPath;
    }

    /**
     * 附件地址
     * @param attachmentPath 附件地址
     */
    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath == null ? null : attachmentPath.trim();
    }

    /**
     * 是否已盖章
     * @return is_signatured 是否已盖章
     */
    public String getIsSignatured() {
        return isSignatured;
    }

    /**
     * 是否已盖章
     * @param isSignatured 是否已盖章
     */
    public void setIsSignatured(String isSignatured) {
        this.isSignatured = isSignatured == null ? null : isSignatured.trim();
    }

    /**
     * 创建时间
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 创建人
     * @return create_user_id 创建人
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     * @param createUserId 创建人
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    /**
     * 修改时间
     * @return update_date 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 修改时间
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 修改人
     * @return update_user_id 修改人
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 修改人
     * @param updateUserId 修改人
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
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