package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

/**
 * 任务归档Bean
 *
 * @author jwl
 * Created in 2019/8/23 15:31
 */
public class HTTaskArchiveBean extends BaseBean {

    /**
     * 任务归档id
     */
    private String taskArchiveId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 主提审人ID
     */
    private String applyUserId;

    /**
     * 主提审人名称
     */
    private String applyUserName;

    /**
     * 流程实例id
     */
    private String processInstId;

    /**
     * 委托任务key
     */
    private String delegateTaskKey;

    /**
     * 委托任务id
     */
    private String delegateTaskId;

    /**
     * 提审人id
     */
    private String approveUserId;

    /**
     * 提审人
     */
    private String approveUserName;

    /**
     * 提审时间
     */
    private Date approveTime;

    /**
     * 流程完成时间
     */
    private Date completeTime;

    /**
     * 当前流程状态
     */
    private String currentState;

    /**
     * 审核状态 0：未通过 1：通过 2：审批中
     */
    private String verifyState;

    /**
     * 下一节点
     */
    private String submitNode;

    /**
     * 能否撤回
     */
    private String isCanRecall;

    /**
     * 档案编号
     */
    private String fileNumber;

    /**
     * 项目编号
     */
    private String projectNum;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 提审时间
     */
    private Date applyTime;

    /**
     * openid
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

    /**
     * projectId
     *
     * @return projectId 项目描述（略）
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * projectId
     *
     * @param projectId 项目描述（略）
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * taskName
     *
     * @return taskName 项目描述（略）
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * taskName
     *
     * @param taskName 项目描述（略）
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * projectNum
     *
     * @return projectNum 项目描述（略）
     */
    public String getProjectNum() {
        return projectNum;
    }

    /**
     * projectNum
     *
     * @param projectNum 项目描述（略）
     */
    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    /**
     * contractNum
     *
     * @return contractNum 项目描述（略）
     */
    public String getContractNum() {
        return contractNum;
    }

    /**
     * contractNum
     *
     * @param contractNum 项目描述（略）
     */
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    /**
     * taskArchiveId
     *
     * @return taskArchiveId 项目描述（略）
     */
    public String getTaskArchiveId() {
        return taskArchiveId;
    }

    /**
     * taskArchiveId
     *
     * @param taskArchiveId 项目描述（略）
     */
    public void setTaskArchiveId(String taskArchiveId) {
        this.taskArchiveId = taskArchiveId;
    }

    /**
     * fileNumber
     *
     * @return fileNumber 项目描述（略）
     */
    public String getFileNumber() {
        return fileNumber;
    }

    /**
     * fileNumber
     *
     * @param fileNumber 项目描述（略）
     */
    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    /**
     * taskId
     *
     * @return taskId 项目描述（略）
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * taskId
     *
     * @param taskId 项目描述（略）
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * departmentId
     *
     * @return departmentId 项目描述（略）
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * departmentId
     *
     * @param departmentId 项目描述（略）
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * applyUserId
     *
     * @return applyUserId 项目描述（略）
     */
    public String getApplyUserId() {
        return applyUserId;
    }

    /**
     * applyUserId
     *
     * @param applyUserId 项目描述（略）
     */
    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    /**
     * applyUserName
     *
     * @return applyUserName 项目描述（略）
     */
    public String getApplyUserName() {
        return applyUserName;
    }

    /**
     * applyUserName
     *
     * @param applyUserName 项目描述（略）
     */
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    /**
     * processInstId
     *
     * @return processInstId 项目描述（略）
     */
    public String getProcessInstId() {
        return processInstId;
    }

    /**
     * processInstId
     *
     * @param processInstId 项目描述（略）
     */
    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    /**
     * delegateTaskKey
     *
     * @return delegateTaskKey 项目描述（略）
     */
    public String getDelegateTaskKey() {
        return delegateTaskKey;
    }

    /**
     * delegateTaskKey
     *
     * @param delegateTaskKey 项目描述（略）
     */
    public void setDelegateTaskKey(String delegateTaskKey) {
        this.delegateTaskKey = delegateTaskKey;
    }

    /**
     * delegateTaskId
     *
     * @return delegateTaskId 项目描述（略）
     */
    public String getDelegateTaskId() {
        return delegateTaskId;
    }

    /**
     * delegateTaskId
     *
     * @param delegateTaskId 项目描述（略）
     */
    public void setDelegateTaskId(String delegateTaskId) {
        this.delegateTaskId = delegateTaskId;
    }

    /**
     * approveUserId
     *
     * @return approveUserId 项目描述（略）
     */
    public String getApproveUserId() {
        return approveUserId;
    }

    /**
     * approveUserId
     *
     * @param approveUserId 项目描述（略）
     */
    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId;
    }

    /**
     * approveUserName
     *
     * @return approveUserName 项目描述（略）
     */
    public String getApproveUserName() {
        return approveUserName;
    }

    /**
     * approveUserName
     *
     * @param approveUserName 项目描述（略）
     */
    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    /**
     * approveTime
     *
     * @return approveTime 项目描述（略）
     */
    public Date getApproveTime() {
        return approveTime;
    }

    /**
     * approveTime
     *
     * @param approveTime 项目描述（略）
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * completeTime
     *
     * @return completeTime 项目描述（略）
     */
    public Date getCompleteTime() {
        return completeTime;
    }

    /**
     * completeTime
     *
     * @param completeTime 项目描述（略）
     */
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * currentState
     *
     * @return currentState 项目描述（略）
     */
    public String getCurrentState() {
        return currentState;
    }

    /**
     * currentState
     *
     * @param currentState 项目描述（略）
     */
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    /**
     * verifyState
     *
     * @return verifyState 项目描述（略）
     */
    public String getVerifyState() {
        return verifyState;
    }

    /**
     * verifyState
     *
     * @param verifyState 项目描述（略）
     */
    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState;
    }

    /**
     * submitNode
     *
     * @return submitNode 项目描述（略）
     */
    public String getSubmitNode() {
        return submitNode;
    }

    /**
     * submitNode
     *
     * @param submitNode 项目描述（略）
     */
    public void setSubmitNode(String submitNode) {
        this.submitNode = submitNode;
    }

    /**
     * isCanRecall
     *
     * @return isCanRecall 项目描述（略）
     */
    public String getIsCanRecall() {
        return isCanRecall;
    }

    /**
     * isCanRecall
     *
     * @param isCanRecall 项目描述（略）
     */
    public void setIsCanRecall(String isCanRecall) {
        this.isCanRecall = isCanRecall;
    }
}
