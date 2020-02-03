package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.util.List;

/**
 *
 */
public class HTWholeProcessTenderVO extends BaseVO {

    /**
     *招标ID
     */
    private String tenderId;

    /**
     *项目ID
     */
    private String projectId;

    /**
     *招标编号
     */
    private String tenderNum;

    /**
     *合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
     */
    private String treatyType;

    /**
     * 合约分类名称
     */
    private String treatyTypeName;

    /**
     *承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    private String undertakeType;

    /**
     * 承包方式名称
     */
    private String undertakeTypeName;

    /**
     *招标名称
     */
    private String tenderName;

    /**
     *计划开始时间
     */
    private String planStartTime;

    /**
     *计划结束时间
     */
    private String planEndTime;

    /**
     *合同类型（1：单价合同，2：总价合同，3：其他）
     */
    private String contractType;

    /**
     *采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
     */
    private String purchaserWay;

    /**
     *目标成本
     */
    private String targetCost;

    /**
     *文件类型（1：招标控制价 2：招标标的 3：其他）
     */
    private String fileType;

    /**
     *除税金额（%）
     */
    private String exciseMoney;

    /**
     *税率（%）
     */
    private String taxRatio;

    /**
     *税金
     */
    private String taxMoney;

    /**
     *含税金额
     */
    private String withTaxMoney;

    /**
     *备注
     */
    private String remark;

    /**
     *招标文件路径
     */
    private String tenderFilePath;

    /**
     *招标文件真实名称
     */
    private String tenderFileName;

    /**
     *接受资料时间
     */
    private String acceptFileTime;

    /**
     *发送报告时间
     */
    private String sendReportTime;

    /**
     *报告真实路径
     */
    private String reportPath;

    /**
     *报告真实名称
     */
    private String reportName;

    /**
     * 子集(供excel导出使用)
     */
    private List<HTWholeProcessTenderVO> childList;

    /**
     * 当前等级(供excel导出使用)
     */
    private String currentLevel;


    /*-----------------------get/set方法-----------------------------*/

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTenderNum() {
        return tenderNum;
    }

    public void setTenderNum(String tenderNum) {
        this.tenderNum = tenderNum;
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

    public String getTenderName() {
        return tenderName;
    }

    public void setTenderName(String tenderName) {
        this.tenderName = tenderName;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getPurchaserWay() {
        return purchaserWay;
    }

    public void setPurchaserWay(String purchaserWay) {
        this.purchaserWay = purchaserWay;
    }

    public String getTargetCost() {
        return targetCost;
    }

    public void setTargetCost(String targetCost) {
        this.targetCost = targetCost;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getExciseMoney() {
        return exciseMoney;
    }

    public void setExciseMoney(String exciseMoney) {
        this.exciseMoney = exciseMoney;
    }

    public String getTaxRatio() {
        return taxRatio;
    }

    public void setTaxRatio(String taxRatio) {
        this.taxRatio = taxRatio;
    }

    public String getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(String taxMoney) {
        this.taxMoney = taxMoney;
    }

    public String getWithTaxMoney() {
        return withTaxMoney;
    }

    public void setWithTaxMoney(String withTaxMoney) {
        this.withTaxMoney = withTaxMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTenderFilePath() {
        return tenderFilePath;
    }

    public void setTenderFilePath(String tenderFilePath) {
        this.tenderFilePath = tenderFilePath;
    }

    public String getTenderFileName() {
        return tenderFileName;
    }

    public void setTenderFileName(String tenderFileName) {
        this.tenderFileName = tenderFileName;
    }

    public String getAcceptFileTime() {
        return acceptFileTime;
    }

    public void setAcceptFileTime(String acceptFileTime) {
        this.acceptFileTime = acceptFileTime;
    }

    public String getSendReportTime() {
        return sendReportTime;
    }

    public void setSendReportTime(String sendReportTime) {
        this.sendReportTime = sendReportTime;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public List<HTWholeProcessTenderVO> getChildList() {
        return childList;
    }

    public void setChildList(List<HTWholeProcessTenderVO> childList) {
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
