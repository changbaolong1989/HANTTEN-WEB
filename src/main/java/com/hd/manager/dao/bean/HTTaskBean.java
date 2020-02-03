package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * 任务Bean
 *
 * @author jwl
 * Created in 2019/7/22 15:31
 */
public class HTTaskBean extends BaseBean {

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
    private Date startDate;

    /**
     * 完成时间
     */
    private Date endDate;

    /**
     * 所需工时
     */
    private Integer needHour;

    /**
     * 阶段id
     */
    private String periodId;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * openid
     */
    private String openId;

    /**
     * accessToken
     */
    private Date accessToken;

    /**
     * 保存accessToken的时间
     */
    private Date saveAccessTokenDate;

    /**
     * 合同类型名称
     */
    private String contractTypeName;

    /**
     * 咨询类型名称
     */
    private String counselTypeName;

    /**
     * 当前时间
     */
    private Date nowDate;

    /**
     * 实际工时
     */
    private Integer actualHour;

    /**
     * 实际工时总和
     */
    private Integer actualHourSum;

    /**
     * 任务和用户关联ID
     */
    private String taskUserId;

    /**
     * 项目编号
     */
    private String projectNum;

    /**
     * 主提审人ID
     */
    private String applyUserId;

    /**
     * 主提审人名称
     */
    private String applyUserName;

    /**
     * 是否通过
     */
    private String isPass;

    /**
     * 意见
     */
    private String rejectReason;

    /**
     * 下一节点
     */
    private String submitNode;

    /**
     * 能否撤回
     */
    private String isCanRecall;

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
     * 是否已经提交重新编辑申请（0：否 1：是）
     */
    private String isSubmitReedit;

    /**
     * 是否已经通过重新编辑申请（0：否 1：是）
     */
    private String isPassReedit;

    /**
     * 提审时间
     */
    private Date applyTime;

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
     * 项目名称
     */
    private String projectName;

    /**
     * 当前时间
     */
    private Date currentDate;

    /**
     * 任务文件类型
     */
    private String taskType;

    /**
     * 模板类型
     */
    private String formworkType;

    /**
     * 模板内容
     */
    private String formworkContent;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 职位名称
     */
    private String jobName;

    /**
     * 职位名称集合
     */
    private List<String> list;

    /**
     * 任务集合
     */
    private List<HTTaskBean> htTaskBeanList;

    /**
     * 自定义工时
     */
    private Integer customizeHour;

    /**
     * 工时原因
     */
    private String hourReason;

    /**
     * 是否是草稿
     */
    private String isDraftFlg;

    /**
     * 草稿保存人
     */
    private String addDraftUserId;

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
     * accessToken
     *
     * @return accessToken 项目描述（略）
     */
    public Date getAccessToken() {
        return accessToken;
    }

    /**
     * accessToken
     *
     * @param accessToken 项目描述（略）
     */
    public void setAccessToken(Date accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * saveAccessTokenDate
     *
     * @return saveAccessTokenDate 项目描述（略）
     */
    public Date getSaveAccessTokenDate() {
        return saveAccessTokenDate;
    }

    /**
     * saveAccessTokenDate
     *
     * @param saveAccessTokenDate 项目描述（略）
     */
    public void setSaveAccessTokenDate(Date saveAccessTokenDate) {
        this.saveAccessTokenDate = saveAccessTokenDate;
    }

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
     * jobName
     *
     * @return jobName 项目描述（略）
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * jobName
     *
     * @param jobName 项目描述（略）
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * htTaskBeanList
     *
     * @return htTaskBeanList 项目描述（略）
     */
    public List<HTTaskBean> getHtTaskBeanList() {
        return htTaskBeanList;
    }

    /**
     * htTaskBeanList
     *
     * @param htTaskBeanList 项目描述（略）
     */
    public void setHtTaskBeanList(List<HTTaskBean> htTaskBeanList) {
        this.htTaskBeanList = htTaskBeanList;
    }

    /**
     * list
     *
     * @return list 项目描述（略）
     */
    public List<String> getList() {
        return list;
    }

    /**
     * list
     *
     * @param list 项目描述（略）
     */
    public void setList(List<String> list) {
        this.list = list;
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
     * currentDate
     *
     * @return currentDate 项目描述（略）
     */
    public Date getCurrentDate() {
        return currentDate;
    }

    /**
     * currentDate
     *
     * @param currentDate 项目描述（略）
     */
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
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
     * 是否为成果文件阶段
     */
    private String isResultFile;

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
    public Integer getActualHour() {
        return actualHour;
    }

    /**
     * actualHour
     *
     * @param actualHour 项目描述（略）
     */
    public void setActualHour(Integer actualHour) {
        this.actualHour = actualHour;
    }

    /**
     * actualHourSum
     *
     * @return actualHourSum 项目描述（略）
     */
    public Integer getActualHourSum() {
        return actualHourSum;
    }

    /**
     * actualHourSum
     *
     * @param actualHourSum 项目描述（略）
     */
    public void setActualHourSum(Integer actualHourSum) {
        this.actualHourSum = actualHourSum;
    }

    /**
     * nowDate
     *
     * @return nowDate 项目描述（略）
     */
    public Date getNowDate() {
        return nowDate;
    }

    /**
     * nowDate
     *
     * @param nowDate 项目描述（略）
     */
    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
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
     * needHour
     *
     * @return needHour 项目描述（略）
     */
    public Integer getNeedHour() {
        return needHour;
    }

    /**
     * needHour
     *
     * @param needHour 项目描述（略）
     */
    public void setNeedHour(Integer needHour) {
        this.needHour = needHour;
    }

    /**
     * startDate
     *
     * @return startDate 项目描述（略）
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * startDate
     *
     * @param startDate 项目描述（略）
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * endDate
     *
     * @return endDate 项目描述（略）
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * endDate
     *
     * @param endDate 项目描述（略）
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
     * isResultFile
     *
     * @return isResultFile 项目描述（略）
     */
    public String getIsResultFile() {
        return isResultFile;
    }

    /**
     * isResultFile
     *
     * @param isResultFile 项目描述（略）
     */
    public void setIsResultFile(String isResultFile) {
        this.isResultFile = isResultFile;
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

    public Integer getCustomizeHour() {
        return customizeHour;
    }

    public void setCustomizeHour(Integer customizeHour) {
        this.customizeHour = customizeHour;
    }

    public String getHourReason() {
        return hourReason;
    }

    public void setHourReason(String hourReason) {
        this.hourReason = hourReason;
    }

    public String getIsDraftFlg() {
        return isDraftFlg;
    }

    public void setIsDraftFlg(String isDraftFlg) {
        this.isDraftFlg = isDraftFlg;
    }

    public String getAddDraftUserId() {
        return addDraftUserId;
    }

    public void setAddDraftUserId(String addDraftUserId) {
        this.addDraftUserId = addDraftUserId;
    }
}
