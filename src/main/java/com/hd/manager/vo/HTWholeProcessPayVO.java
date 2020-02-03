package com.hd.manager.vo;

import com.hd.base.bean.BaseBean;
import com.hd.base.vo.BaseVO;

import java.util.List;

public class HTWholeProcessPayVO extends BaseVO {

    /**
     * 付款ID
     */
    private String payId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 期数（0：预付款 1：第一期 2：第二器 。。。。20：第二十器）
     */
    private String periodNum;

    /**
     * 本期产值
     */
    private String currentValue;

    /**
     * 本期应付
     */
    private String currentPayment;

    /**
     * 日期
     */
    private String editDate;

    private List<HTWholeProcessPayVO> childList;

    private String currentLevel;


    /*-------------合同关联字段start--------------*/

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 合同分类（1：工程类合同、2：采购类合同、3：服务类合同、4：土地类合同、5：其他类合同）
     */
    private String treatyType;

    /**
     * 合同分类名称（1：工程类合同、2：采购类合同、3：服务类合同、4：土地类合同、5：其他类合同）
     */
    private String treatyTypeName;

    /**
     * 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    private String undertakeType;

    /**
     * 承包方式名称（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    private String undertakeTypeName;

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
    private String withTaxMoney;

    /**
     * 合同单位
     */
    private String contractUnit;
    /*-----------------合同关联字段end----------------*/

    /*-------------------全过程动态成本管理表关联字段Start----------------------*/

    /**
     * 预计结算估值
     */
    private String predictCloseValue;
    /*-------------------全过程动态成本管理表关联字段End----------------------*/



    /*-------------------------get/set方法-----------------------------*/


    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
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

    public String getPeriodNum() {
        return periodNum;
    }

    public void setPeriodNum(String periodNum) {
        this.periodNum = periodNum;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getCurrentPayment() {
        return currentPayment;
    }

    public void setCurrentPayment(String currentPayment) {
        this.currentPayment = currentPayment;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
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

    /**
     * withTaxMoney
     *
     * @return withTaxMoney 项目描述（略）
     */
    public String getWithTaxMoney() {
        return withTaxMoney;
    }

    /**
     * withTaxMoney
     *
     * @param withTaxMoney 项目描述（略）
     */
    public void setWithTaxMoney(String withTaxMoney) {
        this.withTaxMoney = withTaxMoney;
    }

    public String getContractUnit() {
        return contractUnit;
    }

    public void setContractUnit(String contractUnit) {
        this.contractUnit = contractUnit;
    }

    public String getPredictCloseValue() {
        return predictCloseValue;
    }

    public void setPredictCloseValue(String predictCloseValue) {
        this.predictCloseValue = predictCloseValue;
    }

    public List<HTWholeProcessPayVO> getChildList() {
        return childList;
    }

    public void setChildList(List<HTWholeProcessPayVO> childList) {
        this.childList = childList;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getTreatyTypeName() {
        return treatyTypeName;
    }

    public void setTreatyTypeName(String treatyTypeName) {
        this.treatyTypeName = treatyTypeName;
    }

    public String getUndertakeTypeName() {
        return undertakeTypeName;
    }

    public void setUndertakeTypeName(String undertakeTypeName) {
        this.undertakeTypeName = undertakeTypeName;
    }
}
