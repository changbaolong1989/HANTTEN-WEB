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
public class HTTaskVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -3293062862694561946L;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 合同类型id
     */
    private String contractTypeId;

    /**
     * 咨询类型id
     */
    private String counselTypeId;

    /**
     * 关联id
     */
    private String relationId;

    /**
     * 提审时间
     */
    private String applyTime;

    /**
     * 人员id
     */
    private String userId;

    /**
     * 是否是主负责人  0：否   1：是
     */
    private String isMainDuty;

    /**
     * 任务说明
     */
    private String taskContent;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 完成时间
     */
    private String endDate;

    private String completeTime;

    /**
     * 所需工时
     */
    private String needHour;

    /**
     * 阶段id
     */
    private String periodId;

    /**
     * 指定人员列表
     */
    private List<HTTaskVO> userList;

    /**
     * 任务列表
     */
    private List<HTTaskVO> taskList;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 合同类型名称
     */
    private String contractTypeName;

    /**
     * 咨询类型名称
     */
    private String counselTypeName;

    /**
     * 执行人列表
     */
    private List<HTTaskVO> smallForm;

    /**
     * 执行人数据
     */
    private List<HTTaskVO> tableData;

    /**
     * 实际工时
     */
    private String actualHour;

    /**
     * 实际工时总和
     */
    private String actualHourSum;

    /**
     * 任务和用户关联ID
     */
    private String taskUserId;

    /**
     * 当前时间
     */
    private String nowDate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 项目编号
     */
    private String projectNum;

    /**
     * 唯一标识
     */
    private String historyId;

    /**
     * 投标任务ID
     */
    private String tenderTaskId;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 任务Key
     */
    private String taskKey;

    /**
     * 是否通过
     * 1：已通过
     * 0：未通过
     */
    private String isPass;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 审批人
     */
    private String approveUserId;


    /**
     * 审批人名称
     */
    private String approveUserName;

    /**
     * 审批时间
     */
    private String approveTime;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 模板类型
     */
    private String formworkType;

    /**
     * 合同信息
     */
    private String formworkContent;

    /**
     *  投标任务审核记录
     */
    private List<HTTenderTaskHisTaskInstVO> HTTenderTaskHisTaskInstVOList;

    /**
     *  审核记录和人员和问题类型
     */
    private List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 主提审人名称
     */
    private String applyUserName;

    /**
     * 重新编辑申请id
     */
    private String reeditId;

    /**
     * 自定义文件路径
     */
    private String taskMaterialPath;

    /**
     * 自定义文件真实名称
     */
    private String taskMaterialName;

    /**
     * 审核记录和人员和问题类型关联
     */
    List<HTUserQuestionRecordRelationVO> userQuestionRecordRelationList;

    /**
     * 下一节点
     */
    private String submitNode;

    /**
     * 能否撤回
     */
    private String isCanRecall;

    /**
     * 委托任务key
     */
    private String delegateTaskKey;

    /**
     * 委托任务id
     */
    private String delegateTaskId;

    /**
     * 当前流程状态
     */
    private String currentState;

    /**
     * 审核状态 0：未通过 1：通过 2：审批中
     */
    private String verifyState;

    /**
     * 是否已经提交重新编辑申请（0：否 1：是）
     */
    private String isSubmitReedit;

    /**
     * 是否已经通过重新编辑申请（0：否 1：是）
     */
    private String isPassReedit;

    /**
     * 档案编号
     */
    private String fileNumber;

    /**
     * 咨询报告编号
     */
    private String reportNum;

    /**
     * 咨询报告份数
     */
    private String reportCopies;

    /**
     * 关联文件主键ID
     */
    private List<String> materialIds;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 高亮显示标识
     */
    private String highlight;

    /**
     * 阶段名称
     */
    private String periodName;

    /**
     * 自定义工时
     */
    private String customizeHour;

    /**
     * 工时原因
     */
    private String hourReason;

    /**
     * 所属高管
     */
    private String seniorExecutive;

    /**
     * seniorExecutive
     *
     * @return seniorExecutive 项目描述（略）
     */
    public String getSeniorExecutive() {
        return seniorExecutive;
    }

    /**
     * seniorExecutive
     *
     * @param seniorExecutive 项目描述（略）
     */
    public void setSeniorExecutive(String seniorExecutive) {
        this.seniorExecutive = seniorExecutive;
    }

    /**
     * reportCopies
     *
     * @return reportCopies 项目描述（略）
     */
    public String getReportCopies() {
        return reportCopies;
    }

    /**
     * reportCopies
     *
     * @param reportCopies 项目描述（略）
     */
    public void setReportCopies(String reportCopies) {
        this.reportCopies = reportCopies;
    }

    /**
     * periodName
     *
     * @return periodName 项目描述（略）
     */
    public String getPeriodName() {
        return periodName;
    }

    /**
     * periodName
     *
     * @param periodName 项目描述（略）
     */
    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    /**
     * highlight
     *
     * @return highlight 项目描述（略）
     */
    public String getHighlight() {
        return highlight;
    }

    /**
     * highlight
     *
     * @param highlight 项目描述（略）
     */
    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    /**
     * serialVersionUID
     *
     * @return serialVersionUID 项目描述（略）
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * projectName
     *
     * @return projectName 项目描述（略）
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * projectName
     *
     * @param projectName 项目描述（略）
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * materialIds
     *
     * @return materialIds 项目描述（略）
     */
    public List<String> getMaterialIds() {
        return materialIds;
    }

    /**
     * materialIds
     *
     * @param materialIds 项目描述（略）
     */
    public void setMaterialIds(List<String> materialIds) {
        this.materialIds = materialIds;
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
     * isSubmitReedit
     *
     * @return isSubmitReedit 项目描述（略）
     */
    public String getIsSubmitReedit() {
        return isSubmitReedit;
    }

    /**
     * isSubmitReedit
     *
     * @param isSubmitReedit 项目描述（略）
     */
    public void setIsSubmitReedit(String isSubmitReedit) {
        this.isSubmitReedit = isSubmitReedit;
    }

    /**
     * isPassReedit
     *
     * @return isPassReedit 项目描述（略）
     */
    public String getIsPassReedit() {
        return isPassReedit;
    }

    /**
     * isPassReedit
     *
     * @param isPassReedit 项目描述（略）
     */
    public void setIsPassReedit(String isPassReedit) {
        this.isPassReedit = isPassReedit;
    }

    /**
     * applyTime
     *
     * @return applyTime 项目描述（略）
     */
    public String getApplyTime() {
        return applyTime;
    }

    /**
     * applyTime
     *
     * @param applyTime 项目描述（略）
     */
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
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
     * reportNum
     *
     * @return reportNum 项目描述（略）
     */
    public String getReportNum() {
        return reportNum;
    }

    /**
     * reportNum
     *
     * @param reportNum 项目描述（略）
     */
    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }

    /**
     * userQuestionRecordRelationList
     *
     * @return userQuestionRecordRelationList 项目描述（略）
     */
    public List<HTUserQuestionRecordRelationVO> getUserQuestionRecordRelationList() {
        return userQuestionRecordRelationList;
    }

    /**
     * userQuestionRecordRelationList
     *
     * @param userQuestionRecordRelationList 项目描述（略）
     */
    public void setUserQuestionRecordRelationList(List<HTUserQuestionRecordRelationVO> userQuestionRecordRelationList) {
        this.userQuestionRecordRelationList = userQuestionRecordRelationList;
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
     * historyId
     *
     * @return historyId 项目描述（略）
     */
    public String getHistoryId() {
        return historyId;
    }

    /**
     * historyId
     *
     * @param historyId 项目描述（略）
     */
    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    /**
     * tenderTaskId
     *
     * @return tenderTaskId 项目描述（略）
     */
    public String getTenderTaskId() {
        return tenderTaskId;
    }

    /**
     * tenderTaskId
     *
     * @param tenderTaskId 项目描述（略）
     */
    public void setTenderTaskId(String tenderTaskId) {
        this.tenderTaskId = tenderTaskId;
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
     * taskKey
     *
     * @return taskKey 项目描述（略）
     */
    public String getTaskKey() {
        return taskKey;
    }

    /**
     * taskKey
     *
     * @param taskKey 项目描述（略）
     */
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    /**
     * isPass
     *
     * @return isPass 项目描述（略）
     */
    public String getIsPass() {
        return isPass;
    }

    /**
     * isPass
     *
     * @param isPass 项目描述（略）
     */
    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    /**
     * rejectReason
     *
     * @return rejectReason 项目描述（略）
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * rejectReason
     *
     * @param rejectReason 项目描述（略）
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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
     * taskMaterialPath
     *
     * @return taskMaterialPath 项目描述（略）
     */
    public String getTaskMaterialPath() {
        return taskMaterialPath;
    }

    /**
     * taskMaterialPath
     *
     * @param taskMaterialPath 项目描述（略）
     */
    public void setTaskMaterialPath(String taskMaterialPath) {
        this.taskMaterialPath = taskMaterialPath;
    }

    /**
     * taskMaterialName
     *
     * @return taskMaterialName 项目描述（略）
     */
    public String getTaskMaterialName() {
        return taskMaterialName;
    }

    /**
     * taskMaterialName
     *
     * @param taskMaterialName 项目描述（略）
     */
    public void setTaskMaterialName(String taskMaterialName) {
        this.taskMaterialName = taskMaterialName;
    }

    /**
     * reeditId
     *
     * @return reeditId 项目描述（略）
     */
    public String getReeditId() {
        return reeditId;
    }

    /**
     * reeditId
     *
     * @param reeditId 项目描述（略）
     */
    public void setReeditId(String reeditId) {
        this.reeditId = reeditId;
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
     * HTTenderTaskHisTaskInstVOList
     *
     * @return HTTenderTaskHisTaskInstVOList 项目描述（略）
     */
    public List<HTTenderTaskHisTaskInstVO> getHTTenderTaskHisTaskInstVOList() {
        return HTTenderTaskHisTaskInstVOList;
    }

    /**
     * HTTenderTaskHisTaskInstVOList
     *
     * @param HTTenderTaskHisTaskInstVOList 项目描述（略）
     */
    public void setHTTenderTaskHisTaskInstVOList(List<HTTenderTaskHisTaskInstVO> HTTenderTaskHisTaskInstVOList) {
        this.HTTenderTaskHisTaskInstVOList = HTTenderTaskHisTaskInstVOList;
    }

    /**
     * htUserQuestionRecordRelationVOList
     *
     * @return htUserQuestionRecordRelationVOList 项目描述（略）
     */
    public List<HTUserQuestionRecordRelationVO> getHtUserQuestionRecordRelationVOList() {
        return htUserQuestionRecordRelationVOList;
    }

    /**
     * htUserQuestionRecordRelationVOList
     *
     * @param htUserQuestionRecordRelationVOList 项目描述（略）
     */
    public void setHtUserQuestionRecordRelationVOList(List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList) {
        this.htUserQuestionRecordRelationVOList = htUserQuestionRecordRelationVOList;
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
     * remarks
     *
     * @return remarks 项目描述（略）
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * remarks
     *
     * @param remarks 项目描述（略）
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * nowDate
     *
     * @return nowDate 项目描述（略）
     */
    public String getNowDate() {
        return nowDate;
    }

    /**
     * nowDate
     *
     * @param nowDate 项目描述（略）
     */
    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    /**
     * taskUserId
     *
     * @return taskUserId 项目描述（略）
     */
    public String getTaskUserId() {
        return taskUserId;
    }

    /**
     * taskUserId
     *
     * @param taskUserId 项目描述（略）
     */
    public void setTaskUserId(String taskUserId) {
        this.taskUserId = taskUserId;
    }

    /**
     * actualHour
     *
     * @return actualHour 项目描述（略）
     */
    public String getActualHour() {
        return actualHour;
    }

    /**
     * actualHour
     *
     * @param actualHour 项目描述（略）
     */
    public void setActualHour(String actualHour) {
        this.actualHour = actualHour;
    }

    /**
     * actualHourSum
     *
     * @return actualHourSum 项目描述（略）
     */
    public String getActualHourSum() {
        return actualHourSum;
    }

    /**
     * actualHourSum
     *
     * @param actualHourSum 项目描述（略）
     */
    public void setActualHourSum(String actualHourSum) {
        this.actualHourSum = actualHourSum;
    }

    /**
     * tableData
     *
     * @return tableData 项目描述（略）
     */
    public List<HTTaskVO> getTableData() {
        return tableData;
    }

    /**
     * tableData
     *
     * @param tableData 项目描述（略）
     */
    public void setTableData(List<HTTaskVO> tableData) {
        this.tableData = tableData;
    }

    /**
     * smallForm
     *
     * @return smallForm 项目描述（略）
     */
    public List<HTTaskVO> getSmallForm() {
        return smallForm;
    }

    /**
     * smallForm
     *
     * @param smallForm 项目描述（略）
     */
    public void setSmallForm(List<HTTaskVO> smallForm) {
        this.smallForm = smallForm;
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
     * contractTypeName
     *
     * @return contractTypeName 项目描述（略）
     */
    public String getContractTypeName() {
        return contractTypeName;
    }

    /**
     * contractTypeName
     *
     * @param contractTypeName 项目描述（略）
     */
    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    /**
     * counselTypeName
     *
     * @return counselTypeName 项目描述（略）
     */
    public String getCounselTypeName() {
        return counselTypeName;
    }

    /**
     * counselTypeName
     *
     * @param counselTypeName 项目描述（略）
     */
    public void setCounselTypeName(String counselTypeName) {
        this.counselTypeName = counselTypeName;
    }

    /**
     * userName
     *
     * @return userName 项目描述（略）
     */
    public String getUserName() {
        return userName;
    }

    /**
     * userName
     *
     * @param userName 项目描述（略）
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * taskList
     *
     * @return taskList 项目描述（略）
     */
    public List<HTTaskVO> getTaskList() {
        return taskList;
    }

    /**
     * taskList
     *
     * @param taskList 项目描述（略）
     */
    public void setTaskList(List<HTTaskVO> taskList) {
        this.taskList = taskList;
    }

    /**
     * userList
     *
     * @return userList 项目描述（略）
     */
    public List<HTTaskVO> getUserList() {
        return userList;
    }

    /**
     * userList
     *
     * @param userList 项目描述（略）
     */
    public void setUserList(List<HTTaskVO> userList) {
        this.userList = userList;
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
     * contractId
     *
     * @return contractId 项目描述（略）
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * contractId
     *
     * @param contractId 项目描述（略）
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    /**
     * contractTypeId
     *
     * @return contractTypeId 项目描述（略）
     */
    public String getContractTypeId() {
        return contractTypeId;
    }

    /**
     * contractTypeId
     *
     * @param contractTypeId 项目描述（略）
     */
    public void setContractTypeId(String contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    /**
     * counselTypeId
     *
     * @return counselTypeId 项目描述（略）
     */
    public String getCounselTypeId() {
        return counselTypeId;
    }

    /**
     * counselTypeId
     *
     * @param counselTypeId 项目描述（略）
     */
    public void setCounselTypeId(String counselTypeId) {
        this.counselTypeId = counselTypeId;
    }

    /**
     * relationId
     *
     * @return relationId 项目描述（略）
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * relationId
     *
     * @param relationId 项目描述（略）
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    /**
     * userId
     *
     * @return userId 项目描述（略）
     */
    public String getUserId() {
        return userId;
    }

    /**
     * userId
     *
     * @param userId 项目描述（略）
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * isMainDuty
     *
     * @return isMainDuty 项目描述（略）
     */
    public String getIsMainDuty() {
        return isMainDuty;
    }

    /**
     * isMainDuty
     *
     * @param isMainDuty 项目描述（略）
     */
    public void setIsMainDuty(String isMainDuty) {
        this.isMainDuty = isMainDuty;
    }

    /**
     * taskContent
     *
     * @return taskContent 项目描述（略）
     */
    public String getTaskContent() {
        return taskContent;
    }

    /**
     * taskContent
     *
     * @param taskContent 项目描述（略）
     */
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    /**
     * startDate
     *
     * @return startDate 项目描述（略）
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * startDate
     *
     * @param startDate 项目描述（略）
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * endDate
     *
     * @return endDate 项目描述（略）
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * endDate
     *
     * @param endDate 项目描述（略）
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * needHour
     *
     * @return needHour 项目描述（略）
     */
    public String getNeedHour() {
        return needHour;
    }

    /**
     * needHour
     *
     * @param needHour 项目描述（略）
     */
    public void setNeedHour(String needHour) {
        this.needHour = needHour;
    }

    /**
     * periodId
     *
     * @return periodId 项目描述（略）
     */
    public String getPeriodId() {
        return periodId;
    }

    /**
     * periodId
     *
     * @param periodId 项目描述（略）
     */
    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    /**
     * taskType
     *
     * @return taskType 项目描述（略）
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * taskType
     *
     * @param taskType 项目描述（略）
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * formworkType
     *
     * @return formworkType 项目描述（略）
     */
    public String getFormworkType() {
        return formworkType;
    }

    /**
     * formworkType
     *
     * @param formworkType 项目描述（略）
     */
    public void setFormworkType(String formworkType) {
        this.formworkType = formworkType;
    }

    /**
     * formworkContent
     *
     * @return formworkContent 项目描述（略）
     */
    public String getFormworkContent() {
        return formworkContent;
    }

    /**
     * formworkContent
     *
     * @param formworkContent 项目描述（略）
     */
    public void setFormworkContent(String formworkContent) {
        this.formworkContent = formworkContent;
    }

    public String getCustomizeHour() {
        return customizeHour;
    }

    public void setCustomizeHour(String customizeHour) {
        this.customizeHour = customizeHour;
    }

    public String getHourReason() {
        return hourReason;
    }

    public void setHourReason(String hourReason) {
        this.hourReason = hourReason;
    }
}
