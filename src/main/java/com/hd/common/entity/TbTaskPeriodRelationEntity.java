package com.hd.common.entity;

import java.util.Date;

public class TbTaskPeriodRelationEntity {
    /**
     * 
     */
    private String relationId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 阶段
     */
    private String periodId;

    /**
     * 任务流程实例ID
     */
    private String taskProcessInstId;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 委托任务Key
     */
    private String delegateTaskKey;

    /**
     * 委托任务ID
     */
    private String delegateTaskId;

    /**
     * 提审人ID
     */
    private String applyUserId;

    /**
     * 提审人
     */
    private String applyUserName;

    /**
     * 提审时间
     */
    private Date applyTime;

    /**
     * 审批人ID
     */
    private String approveUserId;

    /**
     * 审批人
     */
    private String approveUserName;

    /**
     * 审批时间
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
     * 审核状态 0：未通过 1：通过 2：审批中
     */
    private String verifyState;

    /**
     * 提交节点
     */
    private String submitNode;

    /**
     * 是否可以撤回（0：否 1：是）
     */
    private String isCanRecall;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改人
     */
    private String updateUserId;

    /**
     * 是否删除
     */
    private String isDelFlg;

    /**
     * 是否是成果文件阶段（0：否 1：是）
     */
    private String isResultFile;

    /**
     * 是否忽略（0：否  1：是）
     */
    private String isIgnore;

    /**
     * 是否完成（0：否  1：是）
     */
    private String isFinish;

    /**
     * 
     * @return relation_id 
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * 
     * @param relationId 
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    /**
     * 任务ID
     * @return task_id 任务ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务ID
     * @param taskId 任务ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * 阶段
     * @return period_id 阶段
     */
    public String getPeriodId() {
        return periodId;
    }

    /**
     * 阶段
     * @param periodId 阶段
     */
    public void setPeriodId(String periodId) {
        this.periodId = periodId == null ? null : periodId.trim();
    }

    /**
     * 任务流程实例ID
     * @return task_process_inst_id 任务流程实例ID
     */
    public String getTaskProcessInstId() {
        return taskProcessInstId;
    }

    /**
     * 任务流程实例ID
     * @param taskProcessInstId 任务流程实例ID
     */
    public void setTaskProcessInstId(String taskProcessInstId) {
        this.taskProcessInstId = taskProcessInstId == null ? null : taskProcessInstId.trim();
    }

    /**
     * 流程实例ID
     * @return process_inst_id 流程实例ID
     */
    public String getProcessInstId() {
        return processInstId;
    }

    /**
     * 流程实例ID
     * @param processInstId 流程实例ID
     */
    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId == null ? null : processInstId.trim();
    }

    /**
     * 委托任务Key
     * @return delegate_task_key 委托任务Key
     */
    public String getDelegateTaskKey() {
        return delegateTaskKey;
    }

    /**
     * 委托任务Key
     * @param delegateTaskKey 委托任务Key
     */
    public void setDelegateTaskKey(String delegateTaskKey) {
        this.delegateTaskKey = delegateTaskKey == null ? null : delegateTaskKey.trim();
    }

    /**
     * 委托任务ID
     * @return delegate_task_id 委托任务ID
     */
    public String getDelegateTaskId() {
        return delegateTaskId;
    }

    /**
     * 委托任务ID
     * @param delegateTaskId 委托任务ID
     */
    public void setDelegateTaskId(String delegateTaskId) {
        this.delegateTaskId = delegateTaskId == null ? null : delegateTaskId.trim();
    }

    /**
     * 提审人ID
     * @return apply_user_id 提审人ID
     */
    public String getApplyUserId() {
        return applyUserId;
    }

    /**
     * 提审人ID
     * @param applyUserId 提审人ID
     */
    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId == null ? null : applyUserId.trim();
    }

    /**
     * 提审人
     * @return apply_user_name 提审人
     */
    public String getApplyUserName() {
        return applyUserName;
    }

    /**
     * 提审人
     * @param applyUserName 提审人
     */
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName == null ? null : applyUserName.trim();
    }

    /**
     * 提审时间
     * @return apply_time 提审时间
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * 提审时间
     * @param applyTime 提审时间
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 审批人ID
     * @return approve_user_id 审批人ID
     */
    public String getApproveUserId() {
        return approveUserId;
    }

    /**
     * 审批人ID
     * @param approveUserId 审批人ID
     */
    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId == null ? null : approveUserId.trim();
    }

    /**
     * 审批人
     * @return approve_user_name 审批人
     */
    public String getApproveUserName() {
        return approveUserName;
    }

    /**
     * 审批人
     * @param approveUserName 审批人
     */
    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName == null ? null : approveUserName.trim();
    }

    /**
     * 审批时间
     * @return approve_time 审批时间
     */
    public Date getApproveTime() {
        return approveTime;
    }

    /**
     * 审批时间
     * @param approveTime 审批时间
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * 流程完成时间
     * @return complete_time 流程完成时间
     */
    public Date getCompleteTime() {
        return completeTime;
    }

    /**
     * 流程完成时间
     * @param completeTime 流程完成时间
     */
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * 当前流程状态
     * @return current_state 当前流程状态
     */
    public String getCurrentState() {
        return currentState;
    }

    /**
     * 当前流程状态
     * @param currentState 当前流程状态
     */
    public void setCurrentState(String currentState) {
        this.currentState = currentState == null ? null : currentState.trim();
    }

    /**
     * 审核状态 0：未通过 1：通过 2：审批中
     * @return verify_state 审核状态 0：未通过 1：通过 2：审批中
     */
    public String getVerifyState() {
        return verifyState;
    }

    /**
     * 审核状态 0：未通过 1：通过 2：审批中
     * @param verifyState 审核状态 0：未通过 1：通过 2：审批中
     */
    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState == null ? null : verifyState.trim();
    }

    /**
     * 提交节点
     * @return submit_node 提交节点
     */
    public String getSubmitNode() {
        return submitNode;
    }

    /**
     * 提交节点
     * @param submitNode 提交节点
     */
    public void setSubmitNode(String submitNode) {
        this.submitNode = submitNode == null ? null : submitNode.trim();
    }

    /**
     * 是否可以撤回（0：否 1：是）
     * @return is_can_recall 是否可以撤回（0：否 1：是）
     */
    public String getIsCanRecall() {
        return isCanRecall;
    }

    /**
     * 是否可以撤回（0：否 1：是）
     * @param isCanRecall 是否可以撤回（0：否 1：是）
     */
    public void setIsCanRecall(String isCanRecall) {
        this.isCanRecall = isCanRecall == null ? null : isCanRecall.trim();
    }

    /**
     * 创建时间
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 创建人
     * @return create_user_id 创建人
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     * @param createUserId 创建人
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    /**
     * 修改时间
     * @return update_date 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 修改时间
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 修改人
     * @return update_user_id 修改人
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 修改人
     * @param updateUserId 修改人
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * 是否删除
     * @return is_del_flg 是否删除
     */
    public String getIsDelFlg() {
        return isDelFlg;
    }

    /**
     * 是否删除
     * @param isDelFlg 是否删除
     */
    public void setIsDelFlg(String isDelFlg) {
        this.isDelFlg = isDelFlg == null ? null : isDelFlg.trim();
    }

    /**
     * 是否是成果文件阶段（0：否 1：是）
     * @return is_result_file 是否是成果文件阶段（0：否 1：是）
     */
    public String getIsResultFile() {
        return isResultFile;
    }

    /**
     * 是否是成果文件阶段（0：否 1：是）
     * @param isResultFile 是否是成果文件阶段（0：否 1：是）
     */
    public void setIsResultFile(String isResultFile) {
        this.isResultFile = isResultFile == null ? null : isResultFile.trim();
    }

    /**
     * 是否忽略（0：否  1：是）
     * @return is_ignore 是否忽略（0：否  1：是）
     */
    public String getIsIgnore() {
        return isIgnore;
    }

    /**
     * 是否忽略（0：否  1：是）
     * @param isIgnore 是否忽略（0：否  1：是）
     */
    public void setIsIgnore(String isIgnore) {
        this.isIgnore = isIgnore == null ? null : isIgnore.trim();
    }

    /**
     * 是否完成（0：否  1：是）
     * @return is_finish 是否完成（0：否  1：是）
     */
    public String getIsFinish() {
        return isFinish;
    }

    /**
     * 是否完成（0：否  1：是）
     * @param isFinish 是否完成（0：否  1：是）
     */
    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish == null ? null : isFinish.trim();
    }
}