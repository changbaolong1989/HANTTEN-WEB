package com.hd.common.entity;

import java.util.Date;

public class TbFileConfirmOpinionRelationEntity {
    /**
     * 主键ID
     */
    private String confirmOpinionId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 招标控制价
     */
    private Double tenderSumLimit;

    /**
     * 采购人
     */
    private String purchaser;

    /**
     * 招标代理机构
     */
    private String agentOrg;

    /**
     * 是否同意(0:否 1:是)
     */
    private String isAgree;

    /**
     * 审核意见
     */
    private String approveOpinion;

    /**
     * 日期
     */
    private Date editDate;

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
     * 主键ID
     * @return confirm_opinion_id 主键ID
     */
    public String getConfirmOpinionId() {
        return confirmOpinionId;
    }

    /**
     * 主键ID
     * @param confirmOpinionId 主键ID
     */
    public void setConfirmOpinionId(String confirmOpinionId) {
        this.confirmOpinionId = confirmOpinionId == null ? null : confirmOpinionId.trim();
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
     * 招标控制价
     * @return tender_sum_limit 招标控制价
     */
    public Double getTenderSumLimit() {
        return tenderSumLimit;
    }

    /**
     * 招标控制价
     * @param tenderSumLimit 招标控制价
     */
    public void setTenderSumLimit(Double tenderSumLimit) {
        this.tenderSumLimit = tenderSumLimit;
    }

    /**
     * 采购人
     * @return purchaser 采购人
     */
    public String getPurchaser() {
        return purchaser;
    }

    /**
     * 采购人
     * @param purchaser 采购人
     */
    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser == null ? null : purchaser.trim();
    }

    /**
     * 招标代理机构
     * @return agent_org 招标代理机构
     */
    public String getAgentOrg() {
        return agentOrg;
    }

    /**
     * 招标代理机构
     * @param agentOrg 招标代理机构
     */
    public void setAgentOrg(String agentOrg) {
        this.agentOrg = agentOrg == null ? null : agentOrg.trim();
    }

    /**
     * 是否同意(0:否 1:是)
     * @return is_agree 是否同意(0:否 1:是)
     */
    public String getIsAgree() {
        return isAgree;
    }

    /**
     * 是否同意(0:否 1:是)
     * @param isAgree 是否同意(0:否 1:是)
     */
    public void setIsAgree(String isAgree) {
        this.isAgree = isAgree == null ? null : isAgree.trim();
    }

    /**
     * 审核意见
     * @return approve_opinion 审核意见
     */
    public String getApproveOpinion() {
        return approveOpinion;
    }

    /**
     * 审核意见
     * @param approveOpinion 审核意见
     */
    public void setApproveOpinion(String approveOpinion) {
        this.approveOpinion = approveOpinion == null ? null : approveOpinion.trim();
    }

    /**
     * 日期
     * @return edit_date 日期
     */
    public Date getEditDate() {
        return editDate;
    }

    /**
     * 日期
     * @param editDate 日期
     */
    public void setEditDate(Date editDate) {
        this.editDate = editDate;
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