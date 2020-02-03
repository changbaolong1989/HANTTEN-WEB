package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

public class HTWholeProcessContractBean extends BaseBean {

    private String serialNumber;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 招标ID
     */
    private String tenderId;

    /**
     * 合同分类（1：工程类合同、2：采购类合同、3：服务类合同、4：土地类合同、5：其他类合同）
     */
    private String treatyType;

    /**
     * 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    private String undertakeType;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 合同单位
     */
    private String contractUnit;

    /**
     * 计划工期
     */
    private String planDate;

    /**
     * 合同类型（1：单价合同 2：总价合同 3：其他合同）
     */
    private String contractClass;

    /**
     * 重计量状态（1：无需重计量 2：重计量未开始）
     */
    private String measureSts;

    /**
     * 目标成本
     */
    private Double targetCost;

    /**
     * 除税金额（%）
     */
    private Double exciseMoney;

    /**
     * 税率（%）
     */
    private Double taxRatio;

    /**
     * 税金
     */
    private Double taxMoney;

    /**
     * 含税金额
     */
    private Double withTaxMoney;

    /**
     * 备注
     */
    private String remark;

    /**
     * 动态成本备注
     */
    private String dynamicRemark;

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
     * 未定标金额总价
     */
    private Double uncalibratedPrice;

    /**
     * 暂定量
     */
    private Double temporaryAmount;

    /**
     * 变更估值
     */
    private Double alterValue;

    /**
     * 图差数量调整
     */
    private Double drawingAdjust;

    /**
     * 暂估单价调整
     */
    private Double signelAdjust;

    /**
     * 材料市场调整
     */
    private Double materialAdjust;

    /**
     * 人工市场调整（增值税）
     */
    private Double peopleAdjust;

    /**
     * 其他
     */
    private Double otherThing;

    /**
     * 税金调整
     */
    private Double taxAdjust;

    /**
     * 合同全额总计
     */
    private Double totalPrice;

    /**
     * 预计结算估值
     */
    private Double predictCloseValue;

    private String orderByClause;

    /**
     * 合同ID
     *
     * @return contract_id 合同ID
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * 合同ID
     *
     * @param contractId 合同ID
     */
    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    /**
     * 项目ID
     *
     * @return project_id 项目ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 项目ID
     *
     * @param projectId 项目ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * 招标ID
     *
     * @return tender_id 招标ID
     */
    public String getTenderId() {
        return tenderId;
    }

    /**
     * 招标ID
     *
     * @param tenderId 招标ID
     */
    public void setTenderId(String tenderId) {
        this.tenderId = tenderId == null ? null : tenderId.trim();
    }

    /**
     * 合同分类（1：工程类合同、2：采购类合同、3：服务类合同、4：土地类合同、5：其他类合同）
     *
     * @return treaty_type 合同分类（1：工程类合同、2：采购类合同、3：服务类合同、4：土地类合同、5：其他类合同）
     */
    public String getTreatyType() {
        return treatyType;
    }

    /**
     * 合同分类（1：工程类合同、2：采购类合同、3：服务类合同、4：土地类合同、5：其他类合同）
     *
     * @param treatyType 合同分类（1：工程类合同、2：采购类合同、3：服务类合同、4：土地类合同、5：其他类合同）
     */
    public void setTreatyType(String treatyType) {
        this.treatyType = treatyType == null ? null : treatyType.trim();
    }

    /**
     * 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     *
     * @return undertake_type 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    public String getUndertakeType() {
        return undertakeType;
    }

    /**
     * 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     *
     * @param undertakeType 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    public void setUndertakeType(String undertakeType) {
        this.undertakeType = undertakeType == null ? null : undertakeType.trim();
    }

    /**
     * 合同名称
     *
     * @return contract_name 合同名称
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * 合同名称
     *
     * @param contractName 合同名称
     */
    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    /**
     * 合同编号
     *
     * @return contract_num 合同编号
     */
    public String getContractNum() {
        return contractNum;
    }

    /**
     * 合同编号
     *
     * @param contractNum 合同编号
     */
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum == null ? null : contractNum.trim();
    }

    /**
     * 合同单位
     *
     * @return contract_unit 合同单位
     */
    public String getContractUnit() {
        return contractUnit;
    }

    /**
     * 合同单位
     *
     * @param contractUnit 合同单位
     */
    public void setContractUnit(String contractUnit) {
        this.contractUnit = contractUnit == null ? null : contractUnit.trim();
    }

    /**
     * 计划工期
     *
     * @return plan_date 计划工期
     */
    public String getPlanDate() {
        return planDate;
    }

    /**
     * 计划工期
     *
     * @param planDate 计划工期
     */
    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    /**
     * 合同类型（1：单价合同 2：总价合同 3：其他合同）
     *
     * @return contract_class 合同类型（1：单价合同 2：总价合同 3：其他合同）
     */
    public String getContractClass() {
        return contractClass;
    }

    /**
     * 合同类型（1：单价合同 2：总价合同 3：其他合同）
     *
     * @param contractClass 合同类型（1：单价合同 2：总价合同 3：其他合同）
     */
    public void setContractClass(String contractClass) {
        this.contractClass = contractClass == null ? null : contractClass.trim();
    }

    /**
     * 重计量状态（1：无需重计量 2：重计量未开始）
     *
     * @return measure_sts 重计量状态（1：无需重计量 2：重计量未开始）
     */
    public String getMeasureSts() {
        return measureSts;
    }

    /**
     * 重计量状态（1：无需重计量 2：重计量未开始）
     *
     * @param measureSts 重计量状态（1：无需重计量 2：重计量未开始）
     */
    public void setMeasureSts(String measureSts) {
        this.measureSts = measureSts == null ? null : measureSts.trim();
    }

    /**
     * 目标成本
     *
     * @return target_cost 目标成本
     */
    public Double getTargetCost() {
        return targetCost;
    }

    /**
     * 目标成本
     *
     * @param targetCost 目标成本
     */
    public void setTargetCost(Double targetCost) {
        this.targetCost = targetCost;
    }

    /**
     * 除税金额（%）
     *
     * @return excise_money 除税金额（%）
     */
    public Double getExciseMoney() {
        return exciseMoney;
    }

    /**
     * 除税金额（%）
     *
     * @param exciseMoney 除税金额（%）
     */
    public void setExciseMoney(Double exciseMoney) {
        this.exciseMoney = exciseMoney;
    }

    /**
     * 税率（%）
     *
     * @return tax_ratio 税率（%）
     */
    public Double getTaxRatio() {
        return taxRatio;
    }

    /**
     * 税率（%）
     *
     * @param taxRatio 税率（%）
     */
    public void setTaxRatio(Double taxRatio) {
        this.taxRatio = taxRatio;
    }

    /**
     * 税金
     *
     * @return tax_money 税金
     */
    public Double getTaxMoney() {
        return taxMoney;
    }

    /**
     * 税金
     *
     * @param taxMoney 税金
     */
    public void setTaxMoney(Double taxMoney) {
        this.taxMoney = taxMoney;
    }

    /**
     * 含税金额
     *
     * @return with_tax_money 含税金额
     */
    public Double getWithTaxMoney() {
        return withTaxMoney;
    }

    /**
     * 含税金额
     *
     * @param withTaxMoney 含税金额
     */
    public void setWithTaxMoney(Double withTaxMoney) {
        this.withTaxMoney = withTaxMoney;
    }

    /**
     * 备注
     *
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 创建时间
     *
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 创建人
     *
     * @return create_user_id 创建人
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     *
     * @param createUserId 创建人
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    /**
     * 修改时间
     *
     * @return update_date 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 修改人
     *
     * @return update_user_id 修改人
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 修改人
     *
     * @param updateUserId 修改人
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * 是否删除
     *
     * @return is_del_flg 是否删除
     */
    public String getIsDelFlg() {
        return isDelFlg;
    }

    /**
     * 是否删除
     *
     * @param isDelFlg 是否删除
     */
    public void setIsDelFlg(String isDelFlg) {
        this.isDelFlg = isDelFlg == null ? null : isDelFlg.trim();
    }

    /**
     * 未定标金额总价
     * @return uncalibrated_price 未定标金额总价
     */
    public Double getUncalibratedPrice() {
        return uncalibratedPrice;
    }

    /**
     * 未定标金额总价
     * @param uncalibratedPrice 未定标金额总价
     */
    public void setUncalibratedPrice(Double uncalibratedPrice) {
        this.uncalibratedPrice = uncalibratedPrice;
    }

    /**
     * 暂定量
     * @return temporary_amount 暂定量
     */
    public Double getTemporaryAmount() {
        return temporaryAmount;
    }

    /**
     * 暂定量
     * @param temporaryAmount 暂定量
     */
    public void setTemporaryAmount(Double temporaryAmount) {
        this.temporaryAmount = temporaryAmount;
    }

    /**
     * 变更估值
     * @return alter_value 变更估值
     */
    public Double getAlterValue() {
        return alterValue;
    }

    /**
     * 变更估值
     * @param alterValue 变更估值
     */
    public void setAlterValue(Double alterValue) {
        this.alterValue = alterValue;
    }

    /**
     * 图差数量调整
     * @return drawing_adjust 图差数量调整
     */
    public Double getDrawingAdjust() {
        return drawingAdjust;
    }

    /**
     * 图差数量调整
     * @param drawingAdjust 图差数量调整
     */
    public void setDrawingAdjust(Double drawingAdjust) {
        this.drawingAdjust = drawingAdjust;
    }

    /**
     * 暂估单价调整
     * @return signel_adjust 暂估单价调整
     */
    public Double getSignelAdjust() {
        return signelAdjust;
    }

    /**
     * 暂估单价调整
     * @param signelAdjust 暂估单价调整
     */
    public void setSignelAdjust(Double signelAdjust) {
        this.signelAdjust = signelAdjust;
    }

    /**
     * 材料市场调整
     * @return material_adjust 材料市场调整
     */
    public Double getMaterialAdjust() {
        return materialAdjust;
    }

    /**
     * 材料市场调整
     * @param materialAdjust 材料市场调整
     */
    public void setMaterialAdjust(Double materialAdjust) {
        this.materialAdjust = materialAdjust;
    }

    /**
     * 人工市场调整（增值税）
     * @return people_adjust 人工市场调整（增值税）
     */
    public Double getPeopleAdjust() {
        return peopleAdjust;
    }

    /**
     * 人工市场调整（增值税）
     * @param peopleAdjust 人工市场调整（增值税）
     */
    public void setPeopleAdjust(Double peopleAdjust) {
        this.peopleAdjust = peopleAdjust;
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
     * 税金调整
     * @return tax_adjust 税金调整
     */
    public Double getTaxAdjust() {
        return taxAdjust;
    }

    /**
     * 税金调整
     * @param taxAdjust 税金调整
     */
    public void setTaxAdjust(Double taxAdjust) {
        this.taxAdjust = taxAdjust;
    }

    /**
     * 合同全额总计
     * @return total_price 合同全额总计
     */
    public Double getTotalPrice() {
        return totalPrice;
    }

    /**
     * 合同全额总计
     * @param totalPrice 合同全额总计
     */
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 预计结算估值
     * @return predict_close_value 预计结算估值
     */
    public Double getPredictCloseValue() {
        return predictCloseValue;
    }

    /**
     * 预计结算估值
     * @param predictCloseValue 预计结算估值
     */
    public void setPredictCloseValue(Double predictCloseValue) {
        this.predictCloseValue = predictCloseValue;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getDynamicRemark() {
        return dynamicRemark;
    }

    public void setDynamicRemark(String dynamicRemark) {
        this.dynamicRemark = dynamicRemark;
    }
}
