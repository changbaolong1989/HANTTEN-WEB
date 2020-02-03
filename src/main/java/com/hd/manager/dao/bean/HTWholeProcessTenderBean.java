package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class HTWholeProcessTenderBean extends BaseBean {

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
     *承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    private String undertakeType;

    /**
     *招标名称
     */
    private String tenderName;

    /**
     *计划开始时间
     */
    private Date planStartTime;

    /**
     *计划结束时间
     */
    private Date planEndTime;

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
    private Double targetCost;

    /**
     *文件类型（1：招标控制价 2：招标标的 3：其他）
     */
    private String fileType;

    /**
     *除税金额（%）
     */
    private Double exciseMoney;

    /**
     *税率（%）
     */
    private Double taxRatio;

    /**
     *税金
     */
    private Double taxMoney;

    /**
     *含税金额
     */
    private Double withTaxMoney;

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
    private Date acceptFileTime;

    /**
     *发送报告时间
     */
    private Date sendReportTime;

    /**
     *报告真实路径
     */
    private String reportPath;

    /**
     *报告真实名称
     */
    private String reportName;

    /**
     * 子集(供Excel导出使用)
     */
    List<HTWholeProcessTenderBean> childList;

    /**
     * 当前等级(供Excel导出使用)
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

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
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

    public Double getTargetCost() {
        return targetCost;
    }

    public void setTargetCost(Double targetCost) {
        this.targetCost = targetCost;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Double getExciseMoney() {
        return exciseMoney;
    }

    public void setExciseMoney(Double exciseMoney) {
        this.exciseMoney = exciseMoney;
    }

    public Double getTaxRatio() {
        return taxRatio;
    }

    public void setTaxRatio(Double taxRatio) {
        this.taxRatio = taxRatio;
    }

    public Double getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(Double taxMoney) {
        this.taxMoney = taxMoney;
    }

    public Double getWithTaxMoney() {
        return withTaxMoney;
    }

    public void setWithTaxMoney(Double withTaxMoney) {
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

    public Date getAcceptFileTime() {
        return acceptFileTime;
    }

    public void setAcceptFileTime(Date acceptFileTime) {
        this.acceptFileTime = acceptFileTime;
    }

    public Date getSendReportTime() {
        return sendReportTime;
    }

    public void setSendReportTime(Date sendReportTime) {
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

    public List<HTWholeProcessTenderBean> getChildList() {
        return childList;
    }

    public void setChildList(List<HTWholeProcessTenderBean> childList) {
        this.childList = childList;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }
}
