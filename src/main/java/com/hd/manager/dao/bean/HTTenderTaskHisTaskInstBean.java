package com.hd.manager.dao.bean;

import java.util.Date;
import java.util.List;

public class HTTenderTaskHisTaskInstBean {

    /**
     * 唯一标识
     */
    private String historyId;

    /**
     * 投标任务ID
     */
    private String tenderTaskId;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 任务Key
     */
    private String taskKey;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 是否通过
     * 1：已通过
     * 0：未通过
     */
    private String isPass;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 审批人
     */
    private String approveUserId;



    /**
     * 审批人签名路径
     */
    private String signPath;

    /**
     * 审批人名称
     */
    private String approveUserName;

    /**
     * 审批时间
     */
    private Date approveTime;



    /**
     *  投标任务审核记录
     */
    private List<HTTenderTaskHisTaskInstBean> HTTenderTaskHisTaskInstBeanList;

    /**
     *  审核记录和人员和问题类型
     */
    private List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList;

    /*--------------------get/set方法------------------------*/


    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getTenderTaskId() {
        return tenderTaskId;
    }

    public void setTenderTaskId(String tenderTaskId) {
        this.tenderTaskId = tenderTaskId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public List<HTTenderTaskHisTaskInstBean> getHTTenderTaskHisTaskInstBeanList() {
        return HTTenderTaskHisTaskInstBeanList;
    }

    public void setHTTenderTaskHisTaskInstBeanList(List<HTTenderTaskHisTaskInstBean> HTTenderTaskHisTaskInstBeanList) {
        this.HTTenderTaskHisTaskInstBeanList = HTTenderTaskHisTaskInstBeanList;
    }

    public List<HTUserQuestionRecordRelationBean> getHtUserQuestionRecordRelationBeanList() {
        return htUserQuestionRecordRelationBeanList;
    }

    public void setHtUserQuestionRecordRelationBeanList(List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList) {
        this.htUserQuestionRecordRelationBeanList = htUserQuestionRecordRelationBeanList;
    }

    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    public String getSignPath() {
        return signPath;
    }

    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }
}
