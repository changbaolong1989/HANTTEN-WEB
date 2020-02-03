package com.hd.common.entity;

import java.util.Date;

public class TbTaskEntity {
    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 咨询报告编号
     */
    private String reportNum;

    /**
     * 成果文件份数
     */
    private String reportCopies;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 合同类型ID
     */
    private String contractTypeId;

    /**
     * 咨询类型ID
     */
    private String counselTypeId;

    /**
     * 档案编号
     */
    private String fileNumber;

    /**
     * 模板类型
     */
    private String formworkType;

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
     * 审核状态 0：未通过 1：通过 2：审批中
     */
    private String verifyState;

    /**
     * 当前流程状态
     */
    private String currentState;

    /**
     * 提交节点
     */
    private String submitNode;

    /**
     * 是否可以撤回（0：否 1：是）
     */
    private String isCanRecall;

    /**
     * 是否已经通过重新编辑申请（0：否 1：是）
     */
    private String isPassReedit;

    /**
     * 是否已经提交重新编辑申请（0：否 1：是）
     */
    private String isSubmitReedit;

    /**
     * 自定义文件路径
     */
    private String taskMaterialPath;

    /**
     * 自定义文件真实名称
     */
    private String taskMaterialName;

    /**
     * 任务文件类型
     */
    private String taskType;

    /**
     * 是否草稿
     */
    private String isDraftFlg;

    /**
     * 草稿保存人Id
     */
    private String addDraftUserId;

    /**
     * 所属高管（0：王总 1：肖总）
     */
    private String seniorExecutive;

    /**
     * 模板内容
     */
    private String formworkContent;

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
     * 任务名称
     * @return task_name 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 任务名称
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    /**
     * 咨询报告编号
     * @return report_num 咨询报告编号
     */
    public String getReportNum() {
        return reportNum;
    }

    /**
     * 咨询报告编号
     * @param reportNum 咨询报告编号
     */
    public void setReportNum(String reportNum) {
        this.reportNum = reportNum == null ? null : reportNum.trim();
    }

    /**
     * 成果文件份数
     * @return report_copies 成果文件份数
     */
    public String getReportCopies() {
        return reportCopies;
    }

    /**
     * 成果文件份数
     * @param reportCopies 成果文件份数
     */
    public void setReportCopies(String reportCopies) {
        this.reportCopies = reportCopies == null ? null : reportCopies.trim();
    }

    /**
     * 合同ID
     * @return contract_id 合同ID
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * 合同ID
     * @param contractId 合同ID
     */
    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    /**
     * 合同类型ID
     * @return contract_type_id 合同类型ID
     */
    public String getContractTypeId() {
        return contractTypeId;
    }

    /**
     * 合同类型ID
     * @param contractTypeId 合同类型ID
     */
    public void setContractTypeId(String contractTypeId) {
        this.contractTypeId = contractTypeId == null ? null : contractTypeId.trim();
    }

    /**
     * 咨询类型ID
     * @return counsel_type_id 咨询类型ID
     */
    public String getCounselTypeId() {
        return counselTypeId;
    }

    /**
     * 咨询类型ID
     * @param counselTypeId 咨询类型ID
     */
    public void setCounselTypeId(String counselTypeId) {
        this.counselTypeId = counselTypeId == null ? null : counselTypeId.trim();
    }

    /**
     * 档案编号
     * @return file_number 档案编号
     */
    public String getFileNumber() {
        return fileNumber;
    }

    /**
     * 档案编号
     * @param fileNumber 档案编号
     */
    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber == null ? null : fileNumber.trim();
    }

    /**
     * 模板类型
     * @return formwork_type 模板类型
     */
    public String getFormworkType() {
        return formworkType;
    }

    /**
     * 模板类型
     * @param formworkType 模板类型
     */
    public void setFormworkType(String formworkType) {
        this.formworkType = formworkType == null ? null : formworkType.trim();
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
     * 是否已经通过重新编辑申请（0：否 1：是）
     * @return is_pass_reedit 是否已经通过重新编辑申请（0：否 1：是）
     */
    public String getIsPassReedit() {
        return isPassReedit;
    }

    /**
     * 是否已经通过重新编辑申请（0：否 1：是）
     * @param isPassReedit 是否已经通过重新编辑申请（0：否 1：是）
     */
    public void setIsPassReedit(String isPassReedit) {
        this.isPassReedit = isPassReedit == null ? null : isPassReedit.trim();
    }

    /**
     * 是否已经提交重新编辑申请（0：否 1：是）
     * @return is_submit_reedit 是否已经提交重新编辑申请（0：否 1：是）
     */
    public String getIsSubmitReedit() {
        return isSubmitReedit;
    }

    /**
     * 是否已经提交重新编辑申请（0：否 1：是）
     * @param isSubmitReedit 是否已经提交重新编辑申请（0：否 1：是）
     */
    public void setIsSubmitReedit(String isSubmitReedit) {
        this.isSubmitReedit = isSubmitReedit == null ? null : isSubmitReedit.trim();
    }

    /**
     * 自定义文件路径
     * @return task_material_path 自定义文件路径
     */
    public String getTaskMaterialPath() {
        return taskMaterialPath;
    }

    /**
     * 自定义文件路径
     * @param taskMaterialPath 自定义文件路径
     */
    public void setTaskMaterialPath(String taskMaterialPath) {
        this.taskMaterialPath = taskMaterialPath == null ? null : taskMaterialPath.trim();
    }

    /**
     * 自定义文件真实名称
     * @return task_material_name 自定义文件真实名称
     */
    public String getTaskMaterialName() {
        return taskMaterialName;
    }

    /**
     * 自定义文件真实名称
     * @param taskMaterialName 自定义文件真实名称
     */
    public void setTaskMaterialName(String taskMaterialName) {
        this.taskMaterialName = taskMaterialName == null ? null : taskMaterialName.trim();
    }

    /**
     * 任务文件类型
     * @return task_type 任务文件类型
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * 任务文件类型
     * @param taskType 任务文件类型
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType == null ? null : taskType.trim();
    }

    /**
     * 是否草稿
     * @return is_draft_flg 是否草稿
     */
    public String getIsDraftFlg() {
        return isDraftFlg;
    }

    /**
     * 是否草稿
     * @param isDraftFlg 是否草稿
     */
    public void setIsDraftFlg(String isDraftFlg) {
        this.isDraftFlg = isDraftFlg == null ? null : isDraftFlg.trim();
    }

    /**
     * 草稿保存人Id
     * @return add_draft_user_id 草稿保存人Id
     */
    public String getAddDraftUserId() {
        return addDraftUserId;
    }

    /**
     * 草稿保存人Id
     * @param addDraftUserId 草稿保存人Id
     */
    public void setAddDraftUserId(String addDraftUserId) {
        this.addDraftUserId = addDraftUserId == null ? null : addDraftUserId.trim();
    }

    /**
     * 所属高管（0：王总 1：肖总）
     * @return senior_executive 所属高管（0：王总 1：肖总）
     */
    public String getSeniorExecutive() {
        return seniorExecutive;
    }

    /**
     * 所属高管（0：王总 1：肖总）
     * @param seniorExecutive 所属高管（0：王总 1：肖总）
     */
    public void setSeniorExecutive(String seniorExecutive) {
        this.seniorExecutive = seniorExecutive == null ? null : seniorExecutive.trim();
    }

    /**
     * 模板内容
     * @return formwork_content 模板内容
     */
    public String getFormworkContent() {
        return formworkContent;
    }

    /**
     * 模板内容
     * @param formworkContent 模板内容
     */
    public void setFormworkContent(String formworkContent) {
        this.formworkContent = formworkContent == null ? null : formworkContent.trim();
    }
}