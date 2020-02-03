package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;
import com.hd.manager.vo.HTTenderTaskUserVO;

import java.util.Date;
import java.util.List;

public class HTTenderTaskBean extends BaseBean {

    /**
     * 投标任务ID
     */
    private String tenderTaskId;

    /**
     * 投标任务名称
     */
    private String tenderTaskName;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 投标任务概况
     */
    private String tenderTaskContent;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 投标任务状态(0：未开标 1；未中标 2：已中标)
     */
    private String tenderTaskSts;

    /**
     * 开标时间
     */
    private Date startDate;

    /**
     * 流程状态(0：待审核 1：审核中 2：审核完成)
     */
    private String processSts;

    /**
     * 主负责人ID
     */
    private String mainLeaderId;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 是否通过
     * 1：已通过
     * 0：未通过
     */
    private String isPass;

    /**
     * 审核结果(0：已驳回 1：已通过)
     */
    private String applyResult;

    /**
     *成员ID
     */
    private String userId;

    /**
     *成员名称
     */
    private String userName;

    /**
     *委托任务ID
     */
    private String delegateTaskId;

    /**
     *提交节点
     */
    private String submitNode;

    /**
     * 投标任务序号
     */
    private String sortNum;

    /**
     * 委托任务Key
     */
    private String delegateTaskKey;

    /**
     * 流程当前状态
     */
    private String currentState;

    /**
     * 审核状态
     * 0：未通过
     * 1：通过
     * 2：审批中
     */
    private String verifyState;

    /**
     * 是否可以撤回（0：否 1：是）
     */
    private String isCanRecall;

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
    private String approveTime;


    /**
     * 审批时间
     */
    private String completeTime;

    /**
     * 投标任务ID集合
     */
    private List<String> tenderTaskIds;

    /**
     * 关联人员
     */
    private List<HTTenderTaskUserVO> userList;

    /**
     * 提审时间
     */
    private Date applyTime;

    /**
     * openId
     */
    private String openId;

    /**
     * openId
     *
     * @return openId 项目描述（略）
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * openId
     *
     * @param openId 项目描述（略）
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * applyTime
     *
     * @return applyTime 项目描述（略）
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * applyTime
     *
     * @param applyTime 项目描述（略）
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getTenderTaskId() {
        return tenderTaskId;
    }

    public void setTenderTaskId(String tenderTaskId) {
        this.tenderTaskId = tenderTaskId;
    }

    public String getTenderTaskName() {
        return tenderTaskName;
    }

    public void setTenderTaskName(String tenderTaskName) {
        this.tenderTaskName = tenderTaskName;
    }

    public String getTenderTaskContent() {
        return tenderTaskContent;
    }

    public void setTenderTaskContent(String tenderTaskContent) {
        this.tenderTaskContent = tenderTaskContent;
    }

    public String getTenderTaskSts() {
        return tenderTaskSts;
    }

    public void setTenderTaskSts(String tenderTaskSts) {
        this.tenderTaskSts = tenderTaskSts;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getProcessSts() {
        return processSts;
    }

    public void setProcessSts(String processSts) {
        this.processSts = processSts;
    }

    public String getApplyResult() {
        return applyResult;
    }

    public void setApplyResult(String applyResult) {
        this.applyResult = applyResult;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public List<HTTenderTaskUserVO> getUserList() {
        return userList;
    }

    public void setUserList(List<HTTenderTaskUserVO> userList) {
        this.userList = userList;
    }

    public String getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState;
    }

    public String getIsCanRecall() {
        return isCanRecall;
    }

    public void setIsCanRecall(String isCanRecall) {
        this.isCanRecall = isCanRecall;
    }

    public String getDelegateTaskId() {
        return delegateTaskId;
    }

    public void setDelegateTaskId(String delegateTaskId) {
        this.delegateTaskId = delegateTaskId;
    }

    public String getSubmitNode() {
        return submitNode;
    }

    public void setSubmitNode(String submitNode) {
        this.submitNode = submitNode;
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

    public String getDelegateTaskKey() {
        return delegateTaskKey;
    }

    public void setDelegateTaskKey(String delegateTaskKey) {
        this.delegateTaskKey = delegateTaskKey;
    }

    public List<String> getTenderTaskIds() {
        return tenderTaskIds;
    }

    public void setTenderTaskIds(List<String> tenderTaskIds) {
        this.tenderTaskIds = tenderTaskIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getMainLeaderId() {
        return mainLeaderId;
    }

    public void setMainLeaderId(String mainLeaderId) {
        this.mainLeaderId = mainLeaderId;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
}
