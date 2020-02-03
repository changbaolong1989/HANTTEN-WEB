package com.hd.common.entity;

import java.util.Date;

public class TbProStatementEntity {
    /**
     * 结算ID
     */
    private String statementId;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 
     */
    private String projectId;

    /**
     * 结算金额
     */
    private Double statementAmount;

    /**
     * 图纸方案
     */
    private Double drawingScheme;

    /**
     * 变更洽商
     */
    private Double alterDiscuss;

    /**
     * 签证
     */
    private Double visa;

    /**
     * 材料设配调差
     */
    private Double adjustPrice;

    /**
     * 物价波动调差
     */
    private Double surgePrice;

    /**
     * 总包服务费
     */
    private Double serviceFee;

    /**
     * 税金调差
     */
    private Double taxPrice;

    /**
     * 其他
     */
    private Double otherThing;

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
     * 结算ID
     * @return statement_id 结算ID
     */
    public String getStatementId() {
        return statementId;
    }

    /**
     * 结算ID
     * @param statementId 结算ID
     */
    public void setStatementId(String statementId) {
        this.statementId = statementId == null ? null : statementId.trim();
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
     * 
     * @return project_id 
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 
     * @param projectId 
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * 结算金额
     * @return statement_amount 结算金额
     */
    public Double getStatementAmount() {
        return statementAmount;
    }

    /**
     * 结算金额
     * @param statementAmount 结算金额
     */
    public void setStatementAmount(Double statementAmount) {
        this.statementAmount = statementAmount;
    }

    /**
     * 图纸方案
     * @return drawing_scheme 图纸方案
     */
    public Double getDrawingScheme() {
        return drawingScheme;
    }

    /**
     * 图纸方案
     * @param drawingScheme 图纸方案
     */
    public void setDrawingScheme(Double drawingScheme) {
        this.drawingScheme = drawingScheme;
    }

    /**
     * 变更洽商
     * @return alter_discuss 变更洽商
     */
    public Double getAlterDiscuss() {
        return alterDiscuss;
    }

    /**
     * 变更洽商
     * @param alterDiscuss 变更洽商
     */
    public void setAlterDiscuss(Double alterDiscuss) {
        this.alterDiscuss = alterDiscuss;
    }

    /**
     * 签证
     * @return visa 签证
     */
    public Double getVisa() {
        return visa;
    }

    /**
     * 签证
     * @param visa 签证
     */
    public void setVisa(Double visa) {
        this.visa = visa;
    }

    /**
     * 材料设配调差
     * @return adjust_price 材料设配调差
     */
    public Double getAdjustPrice() {
        return adjustPrice;
    }

    /**
     * 材料设配调差
     * @param adjustPrice 材料设配调差
     */
    public void setAdjustPrice(Double adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    /**
     * 物价波动调差
     * @return surge_price 物价波动调差
     */
    public Double getSurgePrice() {
        return surgePrice;
    }

    /**
     * 物价波动调差
     * @param surgePrice 物价波动调差
     */
    public void setSurgePrice(Double surgePrice) {
        this.surgePrice = surgePrice;
    }

    /**
     * 总包服务费
     * @return service_fee 总包服务费
     */
    public Double getServiceFee() {
        return serviceFee;
    }

    /**
     * 总包服务费
     * @param serviceFee 总包服务费
     */
    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    /**
     * 税金调差
     * @return tax_price 税金调差
     */
    public Double getTaxPrice() {
        return taxPrice;
    }

    /**
     * 税金调差
     * @param taxPrice 税金调差
     */
    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }

    /**
     * 其他
     * @return other_thing 其他
     */
    public Double getOtherThing() {
        return otherThing;
    }

    /**
     * 其他
     * @param otherThing 其他
     */
    public void setOtherThing(Double otherThing) {
        this.otherThing = otherThing;
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