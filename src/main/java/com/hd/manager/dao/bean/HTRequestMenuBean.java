package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;


/**
 * 请款单Bean
 *
 * @author LH
 * Created in 2019/8/1 13:00
 */
public class HTRequestMenuBean extends BaseBean {
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
    private String contractId;;
    /**
     * 合同编号
     */
    private String contractNum;;
    /**
     * 项目编号
     */
    private String projectNum;
    /**
     * 项目ID
     */
    private String projectId;
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
     *委托任务ID
     */
    private String delegateTaskId;
    /**
     *提审人ID
     */
    private String applyUserId;
    /**
     *提审人
     */
    private String applyUserName;
    /**
     *审批人ID
     */
    private String approveUserId;
    /**
     *审批人
     */
    private String approveUserName;
    /**
     *审批时间
     */
    private Date approveTime;
    /**
     *流程完成时间
     */
    private Date completeTime;
    /**
     *审核状态
     * 0：未通过
     * 1：通过
     * 2：审批中
     */
    private String verifyState;

    private String currentState;
    /**
     *提交节点
     */
    private String submitNode;
    /**
     *是否可以撤回（0：否 1：是）
     */
    private String isCanRecall;
    /**
     *创建任务时间
     */
    private Date createTaskDate;
    /**
     *创建任务人员ID
     */
    private String createTaskUserId;
    /**
     *高管用户ID
     */
    private String seniorManagerId;
    /**
     *请款单类型（0：自定义1：模板）
     */
    private String requestType;
    /**
     *模板请款单类型
     */
    private String frameworkType;
    /**
     *模板请款单(以json形式存储)
     */
    private String frameworkContent;
    /**
     *自定义模板路径
     */
    private String requestFilePath;
    /**
     *自定义模板真实文件名称
     */
    private String requestFileName;

    /**
     *发票申请单路径
     */
    private String requestNoteFilePath;

    /**
     *发票申请单真实文件名称
     */
    private String requestNoteFileName;

    /**
     *财务上传发票路径
     */
    private String requestInvoicePath;

    /**
     *财务上传发票名称
     */
    private String requestInvoiceName;

    /**
     * 发票金额
     */
    private String invoiceAmount;
    /**
     * 发票文件路路径
     */
    private String invoiceFilePath;
    /**
     * 发票文件真实名称
     */
    private String invoiceFileName;
    /**
     * 打款凭证金额
     */
    private String paymentAmount;
    /**
     * 打款凭证文件路路径
     */
    private String paymentFilePath;
    /**
     * 打款凭证文件真实名称
     */
    private String paymentFileName;

    /**
     * 部门ID(业务线)
     */
    private String departmentId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 提审时间
     */
    private Date applyTime;

    /**
     * openId
     */
    private String openId;

    /**
     * 发票申请单备注
     */
    private String invoiceApplyRemark;

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

    /*---------------------------get/set------------------------------*/

    public String getRequestMenuId() {
        return requestMenuId;
    }

    public void setRequestMenuId(String requestMenuId) {
        this.requestMenuId = requestMenuId;
    }

    public String getRequestMenuName() {
        return requestMenuName;
    }

    public void setRequestMenuName(String requestMenuName) {
        this.requestMenuName = requestMenuName;
    }

    public String getRequestMenuNum() {
        return requestMenuNum;
    }

    public void setRequestMenuNum(String requestMenuNum) {
        this.requestMenuNum = requestMenuNum;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(Double requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getDelegateTaskKey() {
        return delegateTaskKey;
    }

    public void setDelegateTaskKey(String delegateTaskKey) {
        this.delegateTaskKey = delegateTaskKey;
    }

    public String getDelegateTaskId() {
        return delegateTaskId;
    }

    public void setDelegateTaskId(String delegateTaskId) {
        this.delegateTaskId = delegateTaskId;
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

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState;
    }

    public String getSubmitNode() {
        return submitNode;
    }

    public void setSubmitNode(String submitNode) {
        this.submitNode = submitNode;
    }

    public String getIsCanRecall() {
        return isCanRecall;
    }

    public void setIsCanRecall(String isCanRecall) {
        this.isCanRecall = isCanRecall;
    }

    public Date getCreateTaskDate() {
        return createTaskDate;
    }

    public void setCreateTaskDate(Date createTaskDate) {
        this.createTaskDate = createTaskDate;
    }

    public String getCreateTaskUserId() {
        return createTaskUserId;
    }

    public void setCreateTaskUserId(String createTaskUserId) {
        this.createTaskUserId = createTaskUserId;
    }

    public String getSeniorManagerId() {
        return seniorManagerId;
    }

    public void setSeniorManagerId(String seniorManagerId) {
        this.seniorManagerId = seniorManagerId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getFrameworkType() {
        return frameworkType;
    }

    public void setFrameworkType(String frameworkType) {
        this.frameworkType = frameworkType;
    }

    public String getFrameworkContent() {
        return frameworkContent;
    }

    public void setFrameworkContent(String frameworkContent) {
        this.frameworkContent = frameworkContent;
    }

    public String getRequestFilePath() {
        return requestFilePath;
    }

    public void setRequestFilePath(String requestFilePath) {
        this.requestFilePath = requestFilePath;
    }

    public String getRequestFileName() {
        return requestFileName;
    }

    public void setRequestFileName(String requestFileName) {
        this.requestFileName = requestFileName;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceFilePath() {
        return invoiceFilePath;
    }

    public void setInvoiceFilePath(String invoiceFilePath) {
        this.invoiceFilePath = invoiceFilePath;
    }

    public String getInvoiceFileName() {
        return invoiceFileName;
    }

    public void setInvoiceFileName(String invoiceFileName) {
        this.invoiceFileName = invoiceFileName;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentFilePath() {
        return paymentFilePath;
    }

    public void setPaymentFilePath(String paymentFilePath) {
        this.paymentFilePath = paymentFilePath;
    }

    public String getPaymentFileName() {
        return paymentFileName;
    }

    public void setPaymentFileName(String paymentFileName) {
        this.paymentFileName = paymentFileName;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }


    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRequestNoteFilePath() {
        return requestNoteFilePath;
    }

    public void setRequestNoteFilePath(String requestNoteFilePath) {
        this.requestNoteFilePath = requestNoteFilePath;
    }

    public String getRequestNoteFileName() {
        return requestNoteFileName;
    }

    public void setRequestNoteFileName(String requestNoteFileName) {
        this.requestNoteFileName = requestNoteFileName;
    }

    public String getRequestInvoicePath() {
        return requestInvoicePath;
    }

    public void setRequestInvoicePath(String requestInvoicePath) {
        this.requestInvoicePath = requestInvoicePath;
    }

    public String getRequestInvoiceName() {
        return requestInvoiceName;
    }

    public void setRequestInvoiceName(String requestInvoiceName) {
        this.requestInvoiceName = requestInvoiceName;
    }

    public String getInvoiceApplyRemark() {
        return invoiceApplyRemark;
    }

    public void setInvoiceApplyRemark(String invoiceApplyRemark) {
        this.invoiceApplyRemark = invoiceApplyRemark;
    }
}
