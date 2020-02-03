package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

public class HTWholeProcessChangeStandingBookVO extends BaseVO {

    private String projectId;
    private String contractId;
    private String contractNum;
    private String treatyType;
    private String undertakeType;
    private String contractName;
    private String contractClass;
    private String withTaxMoney;
    private String sumDeclareAmount;
    private String sumHanttenAmount;
    private String perHanttenAmount;
    private String confirmed;
    private String estimatedAmount;

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

    public String getWithTaxMoney() {
        return withTaxMoney;
    }

    public void setWithTaxMoney(String withTaxMoney) {
        this.withTaxMoney = withTaxMoney;
    }

    public String getSumDeclareAmount() {
        return sumDeclareAmount;
    }

    public void setSumDeclareAmount(String sumDeclareAmount) {
        this.sumDeclareAmount = sumDeclareAmount;
    }

    public String getSumHanttenAmount() {
        return sumHanttenAmount;
    }

    public void setSumHanttenAmount(String sumHanttenAmount) {
        this.sumHanttenAmount = sumHanttenAmount;
    }

    public String getPerHanttenAmount() {
        return perHanttenAmount;
    }

    public void setPerHanttenAmount(String perHanttenAmount) {
        this.perHanttenAmount = perHanttenAmount;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(String estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

}
