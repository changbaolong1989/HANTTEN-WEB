package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;
import java.util.List;

public class HTRequestMenuHisTaskInstBean extends BaseBean {

    /**
     * 主键
     */
    private String historyId;

    /**
     *请款单ID
     */
    private String requestMenuId;

    /**
     *流程实例ID
     */
    private String processInstId;

    /**
     *任务Key
     */
    private String taskKey;

    /**
     *是否通过
     * 1：已通过
     * 0：未通过
     */
    private String isPass;

    /**
     *拒绝原因
     */
    private String rejectReason;

    /**
     *审批人
     */
    private String approveUserId;

    /**
     *审批人名称
     */
    private String approveUserName;

    /**
     *审批人签名文件路径
     */
    private String signPath;

    /**
     *审批时间
     */
    private Date approveTime;

    /**
     * 子集
     */
    private List<HTRequestMenuHisTaskInstBean> htRequestMenuHisTaskInstList;

    /**
     *  审核记录和人员和问题类型
     */
    private List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList;


    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getRequestMenuId() {
        return requestMenuId;
    }

    public void setRequestMenuId(String requestMenuId) {
        this.requestMenuId = requestMenuId;
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

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public List<HTRequestMenuHisTaskInstBean> getHtRequestMenuHisTaskInstList() {
        return htRequestMenuHisTaskInstList;
    }

    public void setHtRequestMenuHisTaskInstList(List<HTRequestMenuHisTaskInstBean> htRequestMenuHisTaskInstList) {
        this.htRequestMenuHisTaskInstList = htRequestMenuHisTaskInstList;
    }

    public List<HTUserQuestionRecordRelationBean> getHtUserQuestionRecordRelationBeanList() {
        return htUserQuestionRecordRelationBeanList;
    }

    public void setHtUserQuestionRecordRelationBeanList(List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList) {
        this.htUserQuestionRecordRelationBeanList = htUserQuestionRecordRelationBeanList;
    }
}
