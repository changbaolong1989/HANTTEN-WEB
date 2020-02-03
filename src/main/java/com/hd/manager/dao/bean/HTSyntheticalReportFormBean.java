package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

public class HTSyntheticalReportFormBean extends BaseBean {

    /**
     *时间
     */
    private String groupMonths;

    /**
     *新增合同数量
     */
    private String addContractCount;

    /**
     * 年度累计新增合同数量
     */
    private String sumAddContractCount;

    /**
     * 完成合同数量
     */
    private String doneContractCount;

    /**
     *年度累计完成合同数量
     */
    private String sumDoneContractCount;

    /**
     *请款金额
     */
    private String requestTotalAmount;

    /**
     *年度累计请款金额
     */
    private String sumRequestTotalAmount;

    /**
     *到款金额
     */
    private String paymentTotalAmount;

    /**
     *年度累计到款金额
     */
    private String sumPaymentTotalAmount;

    /**
     *未到金额
     */
    private String differenceValue;

    /**
     *年度累计未到金额
     */
    private String sumDifferenceValue;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 搜索时间
     */
    private String searchDate;

    /*----------------------------------get/set方法--------------------------------------*/


    public String getGroupMonths() {
        return groupMonths;
    }

    public void setGroupMonths(String groupMonths) {
        this.groupMonths = groupMonths;
    }

    public String getAddContractCount() {
        return addContractCount;
    }

    public void setAddContractCount(String addContractCount) {
        this.addContractCount = addContractCount;
    }

    public String getSumAddContractCount() {
        return sumAddContractCount;
    }

    public void setSumAddContractCount(String sumAddContractCount) {
        this.sumAddContractCount = sumAddContractCount;
    }

    public String getDoneContractCount() {
        return doneContractCount;
    }

    public void setDoneContractCount(String doneContractCount) {
        this.doneContractCount = doneContractCount;
    }

    public String getSumDoneContractCount() {
        return sumDoneContractCount;
    }

    public void setSumDoneContractCount(String sumDoneContractCount) {
        this.sumDoneContractCount = sumDoneContractCount;
    }

    public String getRequestTotalAmount() {
        return requestTotalAmount;
    }

    public void setRequestTotalAmount(String requestTotalAmount) {
        this.requestTotalAmount = requestTotalAmount;
    }

    public String getSumRequestTotalAmount() {
        return sumRequestTotalAmount;
    }

    public void setSumRequestTotalAmount(String sumRequestTotalAmount) {
        this.sumRequestTotalAmount = sumRequestTotalAmount;
    }

    public String getPaymentTotalAmount() {
        return paymentTotalAmount;
    }

    public void setPaymentTotalAmount(String paymentTotalAmount) {
        this.paymentTotalAmount = paymentTotalAmount;
    }

    public String getSumPaymentTotalAmount() {
        return sumPaymentTotalAmount;
    }

    public void setSumPaymentTotalAmount(String sumPaymentTotalAmount) {
        this.sumPaymentTotalAmount = sumPaymentTotalAmount;
    }

    public String getDifferenceValue() {
        return differenceValue;
    }

    public void setDifferenceValue(String differenceValue) {
        this.differenceValue = differenceValue;
    }

    public String getSumDifferenceValue() {
        return sumDifferenceValue;
    }

    public void setSumDifferenceValue(String sumDifferenceValue) {
        this.sumDifferenceValue = sumDifferenceValue;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }
}
