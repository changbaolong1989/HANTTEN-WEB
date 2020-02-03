package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.io.Serializable;
import java.util.List;

/**
 * 任务VO
 *
 * @author jwl
 * Created in 2019/7/22 15:31
 */
public class HTTaskArchiveVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1185538299644926100L;

    /**
     * 主键ID
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
    private String approveTime;

    /**
     * 流程完成时间
     */
    private String completeTime;

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
     * 序号
     */
    private String orderNumber;

    /**
     * 档案编号
     */
    private String fileNumber;

    /**
     * serialVersionUID
     *
     * @return serialVersionUID 项目描述（略）
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
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
     * orderNumber
     *
     * @return orderNumber 项目描述（略）
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * orderNumber
     *
     * @param orderNumber 项目描述（略）
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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
    @Override
    public String getApplyUserId() {
        return applyUserId;
    }

    /**
     * applyUserId
     *
     * @param applyUserId 项目描述（略）
     */
    @Override
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
    @Override
    public String getDelegateTaskKey() {
        return delegateTaskKey;
    }

    /**
     * delegateTaskKey
     *
     * @param delegateTaskKey 项目描述（略）
     */
    @Override
    public void setDelegateTaskKey(String delegateTaskKey) {
        this.delegateTaskKey = delegateTaskKey;
    }

    /**
     * delegateTaskId
     *
     * @return delegateTaskId 项目描述（略）
     */
    @Override
    public String getDelegateTaskId() {
        return delegateTaskId;
    }

    /**
     * delegateTaskId
     *
     * @param delegateTaskId 项目描述（略）
     */
    @Override
    public void setDelegateTaskId(String delegateTaskId) {
        this.delegateTaskId = delegateTaskId;
    }

    /**
     * approveUserId
     *
     * @return approveUserId 项目描述（略）
     */
    @Override
    public String getApproveUserId() {
        return approveUserId;
    }

    /**
     * approveUserId
     *
     * @param approveUserId 项目描述（略）
     */
    @Override
    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId;
    }

    /**
     * approveUserName
     *
     * @return approveUserName 项目描述（略）
     */
    @Override
    public String getApproveUserName() {
        return approveUserName;
    }

    /**
     * approveUserName
     *
     * @param approveUserName 项目描述（略）
     */
    @Override
    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    /**
     * approveTime
     *
     * @return approveTime 项目描述（略）
     */
    @Override
    public String getApproveTime() {
        return approveTime;
    }

    /**
     * approveTime
     *
     * @param approveTime 项目描述（略）
     */
    @Override
    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * completeTime
     *
     * @return completeTime 项目描述（略）
     */
    public String getCompleteTime() {
        return completeTime;
    }

    /**
     * completeTime
     *
     * @param completeTime 项目描述（略）
     */
    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * currentState
     *
     * @return currentState 项目描述（略）
     */
    @Override
    public String getCurrentState() {
        return currentState;
    }

    /**
     * currentState
     *
     * @param currentState 项目描述（略）
     */
    @Override
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    /**
     * verifyState
     *
     * @return verifyState 项目描述（略）
     */
    @Override
    public String getVerifyState() {
        return verifyState;
    }

    /**
     * verifyState
     *
     * @param verifyState 项目描述（略）
     */
    @Override
    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState;
    }

    /**
     * submitNode
     *
     * @return submitNode 项目描述（略）
     */
    @Override
    public String getSubmitNode() {
        return submitNode;
    }

    /**
     * submitNode
     *
     * @param submitNode 项目描述（略）
     */
    @Override
    public void setSubmitNode(String submitNode) {
        this.submitNode = submitNode;
    }

    /**
     * isCanRecall
     *
     * @return isCanRecall 项目描述（略）
     */
    @Override
    public String getIsCanRecall() {
        return isCanRecall;
    }

    /**
     * isCanRecall
     *
     * @param isCanRecall 项目描述（略）
     */
    @Override
    public void setIsCanRecall(String isCanRecall) {
        this.isCanRecall = isCanRecall;
    }

    public String getTaskArchiveId() {
        return taskArchiveId;
    }

    public void setTaskArchiveId(String taskArchiveId) {
        this.taskArchiveId = taskArchiveId;
    }
}
