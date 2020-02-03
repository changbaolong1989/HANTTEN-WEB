package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

public class HTWholeProcessChangeStandingBookBean extends BaseBean {

    private String projectId;
    private String contractId;
    private String contractNum;
    private String treatyType;
    private String undertakeType;
    private String contractName;
    private String contractClass;
    private Double withTaxMoney;
    private Double sumDeclareAmount;
    private Double sumHanttenAmount;
    private String perHanttenAmount;
    private Double confirmed;
    private Double estimatedAmount;

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

    public Double getSumDeclareAmount() {
        return sumDeclareAmount;
    }

    public void setSumDeclareAmount(Double sumDeclareAmount) {
        this.sumDeclareAmount = sumDeclareAmount;
    }

    public Double getSumHanttenAmount() {
        return sumHanttenAmount;
    }

    public void setSumHanttenAmount(Double sumHanttenAmount) {
        this.sumHanttenAmount = sumHanttenAmount;
    }

    public String getPerHanttenAmount() {
        return perHanttenAmount;
    }

    public void setPerHanttenAmount(String perHanttenAmount) {
        this.perHanttenAmount = perHanttenAmount;
    }

    public Double getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Double confirmed) {
        this.confirmed = confirmed;
    }

    public Double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(Double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }
}
