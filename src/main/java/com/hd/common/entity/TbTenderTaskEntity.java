package com.hd.common.entity;

import java.util.Date;

public class TbTenderTaskEntity {
    /**
     * 投标任务ID
     */
    private String tenderTaskId;

    /**
     * 序号(系统生成，永远是当前列表中最大的序号+1)
     */
    private Integer sortNum;

    /**
     * 部门ID
     */
    private String departmentId;

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
     * 流程当前状态
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
     * 投标任务名称
     */
    private String tenderTaskName;

    /**
     * 投标任务概况
     */
    private String tenderTaskContent;

    /**
     * 投标任务状态(0：未开标 1：未中标 2：已中标)
     */
    private String tenderTaskSts;

    /**
     * 开标时间
     */
    private Date startDate;

    /**
     * 是否可以撤回（0：否 1：是）
     */
    private String isCanRecall;

    /**
     * 创建任务时间
     */
    private Date createTaskDate;

    /**
     * 创建任务人员ID
     */
    private String createTaskUserId;

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
     * 投标任务ID
     * @return tender_task_id 投标任务ID
     */
    public String getTenderTaskId() {
        return tenderTaskId;
    }

    /**
     * 投标任务ID
     * @param tenderTaskId 投标任务ID
     */
    public void setTenderTaskId(String tenderTaskId) {
        this.tenderTaskId = tenderTaskId == null ? null : tenderTaskId.trim();
    }

    /**
     * 序号(系统生成，永远是当前列表中最大的序号+1)
     * @return sort_num 序号(系统生成，永远是当前列表中最大的序号+1)
     */
    public Integer getSortNum() {
        return sortNum;
    }

    /**
     * 序号(系统生成，永远是当前列表中最大的序号+1)
     * @param sortNum 序号(系统生成，永远是当前列表中最大的序号+1)
     */
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * 部门ID
     * @return department_id 部门ID
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * 部门ID
     * @param departmentId 部门ID
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
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
     * 流程当前状态
     * @return current_state 流程当前状态
     */
    public String getCurrentState() {
        return currentState;
    }

    /**
     * 流程当前状态
     * @param currentState 流程当前状态
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
     * 投标任务名称
     * @return tender_task_name 投标任务名称
     */
    public String getTenderTaskName() {
        return tenderTaskName;
    }

    /**
     * 投标任务名称
     * @param tenderTaskName 投标任务名称
     */
    public void setTenderTaskName(String tenderTaskName) {
        this.tenderTaskName = tenderTaskName == null ? null : tenderTaskName.trim();
    }

    /**
     * 投标任务概况
     * @return tender_task_content 投标任务概况
     */
    public String getTenderTaskContent() {
        return tenderTaskContent;
    }

    /**
     * 投标任务概况
     * @param tenderTaskContent 投标任务概况
     */
    public void setTenderTaskContent(String tenderTaskContent) {
        this.tenderTaskContent = tenderTaskContent == null ? null : tenderTaskContent.trim();
    }

    /**
     * 投标任务状态(0：未开标 1：未中标 2：已中标)
     * @return tender_task_sts 投标任务状态(0：未开标 1：未中标 2：已中标)
     */
    public String getTenderTaskSts() {
        return tenderTaskSts;
    }

    /**
     * 投标任务状态(0：未开标 1：未中标 2：已中标)
     * @param tenderTaskSts 投标任务状态(0：未开标 1：未中标 2：已中标)
     */
    public void setTenderTaskSts(String tenderTaskSts) {
        this.tenderTaskSts = tenderTaskSts == null ? null : tenderTaskSts.trim();
    }

    /**
     * 开标时间
     * @return start_date 开标时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 开标时间
     * @param startDate 开标时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
     * 创建任务时间
     * @return create_task_date 创建任务时间
     */
    public Date getCreateTaskDate() {
        return createTaskDate;
    }

    /**
     * 创建任务时间
     * @param createTaskDate 创建任务时间
     */
    public void setCreateTaskDate(Date createTaskDate) {
        this.createTaskDate = createTaskDate;
    }

    /**
     * 创建任务人员ID
     * @return create_task_user_id 创建任务人员ID
     */
    public String getCreateTaskUserId() {
        return createTaskUserId;
    }

    /**
     * 创建任务人员ID
     * @param createTaskUserId 创建任务人员ID
     */
    public void setCreateTaskUserId(String createTaskUserId) {
        this.createTaskUserId = createTaskUserId == null ? null : createTaskUserId.trim();
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
}