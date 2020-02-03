package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

public class HTAchievementReportFormBean extends BaseBean {

    /**
     * 成员ID
     */
    private String userId;

    /**
     * 名称
     */
    private String userName;

    /**
     * 扣分合计
     */
    private String sumNum;

    /**
     * 扣分合同编撰数
     */
    private String contractCountNum;

    /**
     * 扣分任务数
     */
    private String taskCountNum;

    /**
     * 扣分请款单数
     */
    private String requestMenuCountNum;

    /**
     * 个人签名
     */
    private String signPath;

    /**
     * 时间
     */
    private String searchDate;
    /*------------------------------get/set-----------------------------------*/

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSumNum() {
        return sumNum;
    }

    public void setSumNum(String sumNum) {
        this.sumNum = sumNum;
    }

    public String getContractCountNum() {
        return contractCountNum;
    }

    public void setContractCountNum(String contractCountNum) {
        this.contractCountNum = contractCountNum;
    }

    public String getTaskCountNum() {
        return taskCountNum;
    }

    public void setTaskCountNum(String taskCountNum) {
        this.taskCountNum = taskCountNum;
    }

    public String getRequestMenuCountNum() {
        return requestMenuCountNum;
    }

    public void setRequestMenuCountNum(String requestMenuCountNum) {
        this.requestMenuCountNum = requestMenuCountNum;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getSignPath() {
        return signPath;
    }

    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }
}
