package com.hd.common.entity;

import java.util.Date;

public class TbRequestMenuEntity {
    /**
     * 请款单ID
     */
    private String requestMenuId;

    /**
     * 请款单名称
     */
    private String requestMenuName;

    /**
     * 档案编号
     */
    private String requestMenuNum;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 请款金额
     */
    private Double requestAmount;

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
     * 流程当前状态
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
     * 创建任务时间
     */
    private Date createTaskDate;

    /**
     * 创建任务人员ID
     */
    private String createTaskUserId;

    /**
     * 高管用户ID
     */
    private String seniorManagerId;

    /**
     * 请款单类型（0：自定义1：模板）
     */
    private String requestType;

    /**
     * 模板请款单类型
     */
    private String frameworkType;

    /**
     * 自定义模板路径
     */
    private String requestFilePath;

    /**
     * 自定义模板真实文件名称
     */
    private String requestFileName;

    /**
     * 发票文件路路径
     */
    private String invoiceFilePath;

    /**
     * 发票文件真实名称
     */
    private String invoiceFileName;

    /**
     * 打款凭证文件路路径
     */
    private String paymentFilePath;

    /**
     * 打款凭证文件真实名称
     */
    private String paymentFileName;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改人
     */
    private String updateUserId;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 是否删除
     */
    private String isDelFlg;

    /**
     * 发票金额
     */
    private Double invoiceAmount;

    /**
     * 打款凭证金额
     */
    private Double paymentAmount;

    /**
     * 部门ID（业务线ID）
     */
    private String departmentId;

    /**
     * 发票申请单路径
     */
    private String requestNoteFilePath;

    /**
     * 发票申请单真实名称
     */
    private String requestNoteFileName;

    /**
     * 发票申请单备注
     */
    private String requestNoteRemark;

    /**
     * 财务部上传发票路径
     */
    private String requestInvoicePath;

    /**
     * 财务部上传发票名称
     */
    private String requestInvoiceName;

    /**
     * 模板请款单(以json形式存储)
     */
    private String frameworkContent;

    /**
     * 请款单ID
     * @return request_menu_id 请款单ID
     */
    public String getRequestMenuId() {
        return requestMenuId;
    }

    /**
     * 请款单ID
     * @param requestMenuId 请款单ID
     */
    public void setRequestMenuId(String requestMenuId) {
        this.requestMenuId = requestMenuId == null ? null : requestMenuId.trim();
    }

    /**
     * 请款单名称
     * @return request_menu_name 请款单名称
     */
    public String getRequestMenuName() {
        return requestMenuName;
    }

    /**
     * 请款单名称
     * @param requestMenuName 请款单名称
     */
    public void setRequestMenuName(String requestMenuName) {
        this.requestMenuName = requestMenuName == null ? null : requestMenuName.trim();
    }

    /**
     * 档案编号
     * @return request_menu_num 档案编号
     */
    public String getRequestMenuNum() {
        return requestMenuNum;
    }

    /**
     * 档案编号
     * @param requestMenuNum 档案编号
     */
    public void setRequestMenuNum(String requestMenuNum) {
        this.requestMenuNum = requestMenuNum == null ? null : requestMenuNum.trim();
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
     * 开始时间
     * @return start_date 开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 开始时间
     * @param startDate 开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 结束时间
     * @return end_date 结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 结束时间
     * @param endDate 结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 请款金额
     * @return request_amount 请款金额
     */
    public Double getRequestAmount() {
        return requestAmount;
    }

    /**
     * 请款金额
     * @param requestAmount 请款金额
     */
    public void setRequestAmount(Double requestAmount) {
        this.requestAmount = requestAmount;
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
     * 高管用户ID
     * @return senior_manager_id 高管用户ID
     */
    public String getSeniorManagerId() {
        return seniorManagerId;
    }

    /**
     * 高管用户ID
     * @param seniorManagerId 高管用户ID
     */
    public void setSeniorManagerId(String seniorManagerId) {
        this.seniorManagerId = seniorManagerId == null ? null : seniorManagerId.trim();
    }

    /**
     * 请款单类型（0：自定义1：模板）
     * @return request_type 请款单类型（0：自定义1：模板）
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * 请款单类型（0：自定义1：模板）
     * @param requestType 请款单类型（0：自定义1：模板）
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType == null ? null : requestType.trim();
    }

    /**
     * 模板请款单类型
     * @return framework_type 模板请款单类型
     */
    public String getFrameworkType() {
        return frameworkType;
    }

    /**
     * 模板请款单类型
     * @param frameworkType 模板请款单类型
     */
    public void setFrameworkType(String frameworkType) {
        this.frameworkType = frameworkType == null ? null : frameworkType.trim();
    }

    /**
     * 自定义模板路径
     * @return request_file_path 自定义模板路径
     */
    public String getRequestFilePath() {
        return requestFilePath;
    }

    /**
     * 自定义模板路径
     * @param requestFilePath 自定义模板路径
     */
    public void setRequestFilePath(String requestFilePath) {
        this.requestFilePath = requestFilePath == null ? null : requestFilePath.trim();
    }

    /**
     * 自定义模板真实文件名称
     * @return request_file_name 自定义模板真实文件名称
     */
    public String getRequestFileName() {
        return requestFileName;
    }

    /**
     * 自定义模板真实文件名称
     * @param requestFileName 自定义模板真实文件名称
     */
    public void setRequestFileName(String requestFileName) {
        this.requestFileName = requestFileName == null ? null : requestFileName.trim();
    }

    /**
     * 发票文件路路径
     * @return invoice_file_path 发票文件路路径
     */
    public String getInvoiceFilePath() {
        return invoiceFilePath;
    }

    /**
     * 发票文件路路径
     * @param invoiceFilePath 发票文件路路径
     */
    public void setInvoiceFilePath(String invoiceFilePath) {
        this.invoiceFilePath = invoiceFilePath == null ? null : invoiceFilePath.trim();
    }

    /**
     * 发票文件真实名称
     * @return invoice_file_name 发票文件真实名称
     */
    public String getInvoiceFileName() {
        return invoiceFileName;
    }

    /**
     * 发票文件真实名称
     * @param invoiceFileName 发票文件真实名称
     */
    public void setInvoiceFileName(String invoiceFileName) {
        this.invoiceFileName = invoiceFileName == null ? null : invoiceFileName.trim();
    }

    /**
     * 打款凭证文件路路径
     * @return payment_file_path 打款凭证文件路路径
     */
    public String getPaymentFilePath() {
        return paymentFilePath;
    }

    /**
     * 打款凭证文件路路径
     * @param paymentFilePath 打款凭证文件路路径
     */
    public void setPaymentFilePath(String paymentFilePath) {
        this.paymentFilePath = paymentFilePath == null ? null : paymentFilePath.trim();
    }

    /**
     * 打款凭证文件真实名称
     * @return payment_file_name 打款凭证文件真实名称
     */
    public String getPaymentFileName() {
        return paymentFileName;
    }

    /**
     * 打款凭证文件真实名称
     * @param paymentFileName 打款凭证文件真实名称
     */
    public void setPaymentFileName(String paymentFileName) {
        this.paymentFileName = paymentFileName == null ? null : paymentFileName.trim();
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
     * 发票金额
     * @return invoice_amount 发票金额
     */
    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * 发票金额
     * @param invoiceAmount 发票金额
     */
    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * 打款凭证金额
     * @return payment_amount 打款凭证金额
     */
    public Double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * 打款凭证金额
     * @param paymentAmount 打款凭证金额
     */
    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * 部门ID（业务线ID）
     * @return department_id 部门ID（业务线ID）
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * 部门ID（业务线ID）
     * @param departmentId 部门ID（业务线ID）
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    /**
     * 发票申请单路径
     * @return request_note_file_path 发票申请单路径
     */
    public String getRequestNoteFilePath() {
        return requestNoteFilePath;
    }

    /**
     * 发票申请单路径
     * @param requestNoteFilePath 发票申请单路径
     */
    public void setRequestNoteFilePath(String requestNoteFilePath) {
        this.requestNoteFilePath = requestNoteFilePath == null ? null : requestNoteFilePath.trim();
    }

    /**
     * 发票申请单真实名称
     * @return request_note_file_name 发票申请单真实名称
     */
    public String getRequestNoteFileName() {
        return requestNoteFileName;
    }

    /**
     * 发票申请单真实名称
     * @param requestNoteFileName 发票申请单真实名称
     */
    public void setRequestNoteFileName(String requestNoteFileName) {
        this.requestNoteFileName = requestNoteFileName == null ? null : requestNoteFileName.trim();
    }

    /**
     * 发票申请单备注
     * @return request_note_remark 发票申请单备注
     */
    public String getRequestNoteRemark() {
        return requestNoteRemark;
    }

    /**
     * 发票申请单备注
     * @param requestNoteRemark 发票申请单备注
     */
    public void setRequestNoteRemark(String requestNoteRemark) {
        this.requestNoteRemark = requestNoteRemark == null ? null : requestNoteRemark.trim();
    }

    /**
     * 财务部上传发票路径
     * @return request_invoice_path 财务部上传发票路径
     */
    public String getRequestInvoicePath() {
        return requestInvoicePath;
    }

    /**
     * 财务部上传发票路径
     * @param requestInvoicePath 财务部上传发票路径
     */
    public void setRequestInvoicePath(String requestInvoicePath) {
        this.requestInvoicePath = requestInvoicePath == null ? null : requestInvoicePath.trim();
    }

    /**
     * 财务部上传发票名称
     * @return request_invoice_name 财务部上传发票名称
     */
    public String getRequestInvoiceName() {
        return requestInvoiceName;
    }

    /**
     * 财务部上传发票名称
     * @param requestInvoiceName 财务部上传发票名称
     */
    public void setRequestInvoiceName(String requestInvoiceName) {
        this.requestInvoiceName = requestInvoiceName == null ? null : requestInvoiceName.trim();
    }

    /**
     * 模板请款单(以json形式存储)
     * @return framework_content 模板请款单(以json形式存储)
     */
    public String getFrameworkContent() {
        return frameworkContent;
    }

    /**
     * 模板请款单(以json形式存储)
     * @param frameworkContent 模板请款单(以json形式存储)
     */
    public void setFrameworkContent(String frameworkContent) {
        this.frameworkContent = frameworkContent == null ? null : frameworkContent.trim();
    }
}