package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.List;

public class HTWholeProcessClaimBean extends BaseBean {

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




    /*-----------------------合同关联字段start------------------------*/

    /**
     * 合同编号
     */
    private String contractNum;

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
     * 合同类型（1：单价合同 2：总价合同 3：其他合同）
     */
    private String contractClass;

    /**
     * 含税金额
     */
    private Double withTaxMoney;
    /*---------------------合同关联字段end-------------------------*/


    /**
     * 子集(供excel导出使用)
     */
    private List<HTWholeProcessClaimBean> childList;

    /**
     * 当前等级(供excel导出使用)
     */
    private String currentLevel;
    /*-----------------------get/set方法------------------------*/

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Double getDeclareAmount() {
        return declareAmount;
    }

    public void setDeclareAmount(Double declareAmount) {
        this.declareAmount = declareAmount;
    }

    public Double getHanttenAmount() {
        return hanttenAmount;
    }

    public void setHanttenAmount(Double hanttenAmount) {
        this.hanttenAmount = hanttenAmount;
    }

    public Double getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(Double confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public Double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(Double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getTreatyType() {
        return treatyType;
    }

    public void setTreatyType(String treatyType) {
        this.treatyType = treatyType;
    }

    public String getUndertakeType() {
        return undertakeType;
    }

    public void setUndertakeType(String undertakeType) {
        this.undertakeType = undertakeType;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractClass() {
        return contractClass;
    }

    public void setContractClass(String contractClass) {
        this.contractClass = contractClass;
    }

    public Double getWithTaxMoney() {
        return withTaxMoney;
    }

    public void setWithTaxMoney(Double withTaxMoney) {
        this.withTaxMoney = withTaxMoney;
    }

    public List<HTWholeProcessClaimBean> getChildList() {
        return childList;
    }

    public void setChildList(List<HTWholeProcessClaimBean> childList) {
        this.childList = childList;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }
}
