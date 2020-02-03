package com.hd.common.entity;

import java.util.Date;

public class TbProClaimEntity {
    /**
     * 索赔台账ID
     */
    private String claimId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 申报金额
     */
    private Double declareAmount;

    /**
     * 汉腾审核金额
     */
    private Double hanttenAmount;

    /**
     * 已确认金额
     */
    private Double confirmAmount;

    /**
     * 预估金额
     */
    private Double estimatedAmount;

    /**
     * 备注
     */
    private String remark;

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
     * 索赔台账ID
     * @return claim_id 索赔台账ID
     */
    public String getClaimId() {
        return claimId;
    }

    /**
     * 索赔台账ID
     * @param claimId 索赔台账ID
     */
    public void setClaimId(String claimId) {
        this.claimId = claimId == null ? null : claimId.trim();
    }

    /**
     * 项目ID
     * @return project_id 项目ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 项目ID
     * @param projectId 项目ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * 合同ID
     * @return contract_id 合同ID
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * 合同ID
     * @param contractId 合同ID
     */
    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    /**
     * 申报金额
     * @return declare_amount 申报金额
     */
    public Double getDeclareAmount() {
        return declareAmount;
    }

    /**
     * 申报金额
     * @param declareAmount 申报金额
     */
    public void setDeclareAmount(Double declareAmount) {
        this.declareAmount = declareAmount;
    }

    /**
     * 汉腾审核金额
     * @return hantten_amount 汉腾审核金额
     */
    public Double getHanttenAmount() {
        return hanttenAmount;
    }

    /**
     * 汉腾审核金额
     * @param hanttenAmount 汉腾审核金额
     */
    public void setHanttenAmount(Double hanttenAmount) {
        this.hanttenAmount = hanttenAmount;
    }

    /**
     * 已确认金额
     * @return confirm_amount 已确认金额
     */
    public Double getConfirmAmount() {
        return confirmAmount;
    }

    /**
     * 已确认金额
     * @param confirmAmount 已确认金额
     */
    public void setConfirmAmount(Double confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    /**
     * 预估金额
     * @return estimated_amount 预估金额
     */
    public Double getEstimatedAmount() {
        return estimatedAmount;
    }

    /**
     * 预估金额
     * @param estimatedAmount 预估金额
     */
    public void setEstimatedAmount(Double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
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