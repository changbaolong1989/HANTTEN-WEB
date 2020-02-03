package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.io.Serializable;
import java.util.List;

/**
 * 请款单VO
 *
 * @author LH
 * Created in 2019/8/1 13:00
 */
public class HTRequestMenuVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -8645101840225909084L;

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
     * 项目编号
     */
    private String projectNum;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 请款金额
     */
    private String requestAmount;

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
    private String approveTime;

    /**
     *流程完成时间
     */
    private String completeTime;

    /**
     *审核状态
     * 0：未通过
     * 1：通过
     * 2：审批中
     */
    private String verifyState;

    /**
     *提交节点
     */
    private String submitNode;

    private String rejectReason;

    /**
     *是否可以撤回（0：否 1：是）
     */
    private String isCanRecall;

    /**
     *创建任务时间
     */
    private String createTaskDate;

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
     * 发票申请单文件信息
     */
    private String requestNoteFiles;

    /**
     * 发票申请单备注
     */
    private String invoiceApplyRemark;

    /**
     * 预计合同总金额
     */
    private String predictTotal;

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
     * 财务部上传发票路径
     */
    private String requestInvoicePath;

    /**
     * 财务部上传发票名称
     */
    private String requestInvoiceName;

    /**
     * 部门Id
     */
    private String departmentId;

    /**
     * 审批状态通用(0：未通过,1：通过,2：审批中)
     */
    private String isPass;

    /**
     * 创建人名称
     */
    private String createUserName;

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
     * 审核记录和人员和问题类型关联
     */
    private List<HTUserQuestionRecordRelationVO> userQuestionRecordRelationList;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(String requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    @Override
    public String getDelegateTaskKey() {
        return delegateTaskKey;
    }

    @Override
    public void setDelegateTaskKey(String delegateTaskKey) {
        this.delegateTaskKey = delegateTaskKey;
    }

    public String getDelegateTaskId() {
        return delegateTaskId;
    }

    public void setDelegateTaskId(String delegateTaskId) {
        this.delegateTaskId = delegateTaskId;
    }

    @Override
    public String getApplyUserId() {
        return applyUserId;
    }

    @Override
    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    @Override
    public String getApproveUserId() {
        return approveUserId;
    }

    @Override
    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId;
    }

    @Override
    public String getApproveUserName() {
        return approveUserName;
    }

    @Override
    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    @Override
    public String getApproveTime() {
        return approveTime;
    }

    @Override
    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    @Override
    public String getVerifyState() {
        return verifyState;
    }

    @Override
    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState;
    }

    @Override
    public String getSubmitNode() {
        return submitNode;
    }

    @Override
    public void setSubmitNode(String submitNode) {
        this.submitNode = submitNode;
    }

    @Override
    public String getIsCanRecall() {
        return isCanRecall;
    }

    @Override
    public void setIsCanRecall(String isCanRecall) {
        this.isCanRecall = isCanRecall;
    }

    public String getCreateTaskDate() {
        return createTaskDate;
    }

    public void setCreateTaskDate(String createTaskDate) {
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

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getPredictTotal() {
        return predictTotal;
    }

    public void setPredictTotal(String predictTotal) {
        this.predictTotal = predictTotal;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public List<HTUserQuestionRecordRelationVO> getUserQuestionRecordRelationList() {
        return userQuestionRecordRelationList;
    }

    public void setUserQuestionRecordRelationList(List<HTUserQuestionRecordRelationVO> userQuestionRecordRelationList) {
        this.userQuestionRecordRelationList = userQuestionRecordRelationList;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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

    public String getRequestNoteFiles() {
        return requestNoteFiles;
    }

    public void setRequestNoteFiles(String requestNoteFiles) {
        this.requestNoteFiles = requestNoteFiles;
    }

    public String getInvoiceApplyRemark() {
        return invoiceApplyRemark;
    }

    public void setInvoiceApplyRemark(String invoiceApplyRemark) {
        this.invoiceApplyRemark = invoiceApplyRemark;
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
}
