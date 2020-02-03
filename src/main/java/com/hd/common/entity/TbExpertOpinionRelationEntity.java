package com.hd.common.entity;

import java.util.Date;

public class TbExpertOpinionRelationEntity {
    /**
     * 主键ID
     */
    private String expertOpinionId;

    /**
     * 
     */
    private String taskId;

    /**
     * 采购人
     */
    private String purchaser;

    /**
     * 采购代理机构
     */
    private String agentOrg;

    /**
     * 采购类别
     */
    private String purchaserType;

    /**
     * 专家论证意见
     */
    private String expertOpinion;

    /**
     * 论证专家签字
     */
    private String expertSign;

    /**
     * 备注
     */
    private String remark;

    /**
     * 论证时间
     */
    private Date proveTime;

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
     * @return expert_opinion_id 主键ID
     */
    public String getExpertOpinionId() {
        return expertOpinionId;
    }

    /**
     * 主键ID
     * @param expertOpinionId 主键ID
     */
    public void setExpertOpinionId(String expertOpinionId) {
        this.expertOpinionId = expertOpinionId == null ? null : expertOpinionId.trim();
    }

    /**
     * 
     * @return task_id 
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 
     * @param taskId 
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
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
     * 采购代理机构
     * @return agent_org 采购代理机构
     */
    public String getAgentOrg() {
        return agentOrg;
    }

    /**
     * 采购代理机构
     * @param agentOrg 采购代理机构
     */
    public void setAgentOrg(String agentOrg) {
        this.agentOrg = agentOrg == null ? null : agentOrg.trim();
    }

    /**
     * 采购类别
     * @return purchaser_type 采购类别
     */
    public String getPurchaserType() {
        return purchaserType;
    }

    /**
     * 采购类别
     * @param purchaserType 采购类别
     */
    public void setPurchaserType(String purchaserType) {
        this.purchaserType = purchaserType == null ? null : purchaserType.trim();
    }

    /**
     * 专家论证意见
     * @return expert_opinion 专家论证意见
     */
    public String getExpertOpinion() {
        return expertOpinion;
    }

    /**
     * 专家论证意见
     * @param expertOpinion 专家论证意见
     */
    public void setExpertOpinion(String expertOpinion) {
        this.expertOpinion = expertOpinion == null ? null : expertOpinion.trim();
    }

    /**
     * 论证专家签字
     * @return expert_sign 论证专家签字
     */
    public String getExpertSign() {
        return expertSign;
    }

    /**
     * 论证专家签字
     * @param expertSign 论证专家签字
     */
    public void setExpertSign(String expertSign) {
        this.expertSign = expertSign == null ? null : expertSign.trim();
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 论证时间
     * @return prove_time 论证时间
     */
    public Date getProveTime() {
        return proveTime;
    }

    /**
     * 论证时间
     * @param proveTime 论证时间
     */
    public void setProveTime(Date proveTime) {
        this.proveTime = proveTime;
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