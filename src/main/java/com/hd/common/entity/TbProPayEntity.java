package com.hd.common.entity;

import java.util.Date;

public class TbProPayEntity {
    /**
     * 付款ID
     */
    private String payId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 
     */
    private String contractId;

    /**
     * 期数（0：预付款 1：第一期 2：第二器 。。。。20：第二十器）
     */
    private String periodNum;

    /**
     * 本期产值
     */
    private Double currentValue;

    /**
     * 本期应付
     */
    private Double currentPayment;

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
     * 付款ID
     * @return pay_id 付款ID
     */
    public String getPayId() {
        return payId;
    }

    /**
     * 付款ID
     * @param payId 付款ID
     */
    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
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
     * 
     * @return contract_id 
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * 
     * @param contractId 
     */
    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    /**
     * 期数（0：预付款 1：第一期 2：第二器 。。。。20：第二十器）
     * @return period_num 期数（0：预付款 1：第一期 2：第二器 。。。。20：第二十器）
     */
    public String getPeriodNum() {
        return periodNum;
    }

    /**
     * 期数（0：预付款 1：第一期 2：第二器 。。。。20：第二十器）
     * @param periodNum 期数（0：预付款 1：第一期 2：第二器 。。。。20：第二十器）
     */
    public void setPeriodNum(String periodNum) {
        this.periodNum = periodNum == null ? null : periodNum.trim();
    }

    /**
     * 本期产值
     * @return current_value 本期产值
     */
    public Double getCurrentValue() {
        return currentValue;
    }

    /**
     * 本期产值
     * @param currentValue 本期产值
     */
    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * 本期应付
     * @return current_payment 本期应付
     */
    public Double getCurrentPayment() {
        return currentPayment;
    }

    /**
     * 本期应付
     * @param currentPayment 本期应付
     */
    public void setCurrentPayment(Double currentPayment) {
        this.currentPayment = currentPayment;
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