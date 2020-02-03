package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.io.Serializable;

public class HTRequestFundsVO extends BaseVO implements Serializable {

    /**
     * 流程实例ID
     */
    private String processInstId;
    /**
     * 请款单ID
     */
    private String requestMenuId;
    /**
     * 主负责人ID
     */
    private String mainLeaderId;
    /**
     * 审核状态
     0：未通过
     1：通过
     2：审批中
     */
    private String verifyState;
    /**
     * 委托任务ID
     */
    private String delegateTaskId;
    /**
     * 委托任务KEY
     */
    private String delegateTaskKey;
    /**
     * 是否可以撤回
     */
    private String isCanRecall;
    /**
     * 提交节点
     */
    private String submitNode;
    /**
     * 审批人
     */
    private String approveUserId;

    private String approveUserName;

    /**
     * 审批时间
     */
    private String approveTime;
    /**
     * 主提审人ID
     */
    private String applyUserId;
    /**
     * 主提审人NAME
     */
    private String applyUserName;
    /**
     * 审批时间
     */
    private String completeTime;


    public String getRequestMenuId() {
        return requestMenuId;
    }

    public void setRequestMenuId(String requestMenuId) { this.requestMenuId = requestMenuId; }

    public String getMainLeaderId() {
        return mainLeaderId;
    }

    public void setMainLeaderId(String mainLeaderId) { this.mainLeaderId = mainLeaderId; }

    public String getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState;
    }

    public String getDelegateTaskId() {
        return delegateTaskId;
    }

    public void setDelegateTaskId(String delegateTaskId) {
        this.delegateTaskId = delegateTaskId;
    }

    public String getIsCanRecall() {
        return isCanRecall;
    }

    public void setIsCanRecall(String isCanRecall) {
        this.isCanRecall = isCanRecall;
    }

    public String getSubmitNode() {
        return submitNode;
    }

    public void setSubmitNode(String submitNode) {
        this.submitNode = submitNode;
    }

    public String getDelegateTaskKey() {
        return delegateTaskKey;
    }

    public void setDelegateTaskKey(String delegateTaskKey) {
        this.delegateTaskKey = delegateTaskKey;
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

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getCompleteTime() { return completeTime; }

    public void setCompleteTime(String completeTime) { this.completeTime = completeTime; }
}
