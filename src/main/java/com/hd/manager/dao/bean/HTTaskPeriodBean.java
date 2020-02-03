package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;
import java.util.Date;
import java.util.List;

public class HTTaskPeriodBean extends BaseBean {

    /**
     * 任务阶段关联id
     */
    private String relationId;

    /**
     * 阶段ID
     */
    private String periodId;

    /**
     *父级阶段ID
     */
    private String parentPeriodId;

    /**
     *阶段名称
     */
    private String periodName;

    /**
     *合同类型
     */
    private String contractTypeId;

    /**
     *咨询类型
     */
    private String counselTypeId;

    /**
     *部门ID
     */
    private String dictDepartmentId;

    /**
     *级别
     */
    private String periodLevel;

    /**
     *工作内容说明
     */
    private String periodDesc;

    /**
     *阶段排序
     */
    private String sort;

    /**
     *页面展示内容(A1,A2,A3...A12)
     */
    private String pageContent;

    /**
     *部门类型
     */
    private String departmentName;

    /**
     * 跳转节点
     */
    private String submitNode;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 委托任务Key
     */
    private String delegateTaskKey;

    /**
     * 委托任务Id
     */
    private String delegateTaskId;

    /**
     * 提审人id
     */
    private String applyUserId;

    /**
     * 提审人
     */
    private String applyUserName;

    /**
     * 审批人id
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
     * 审核状态
     * 0：未通过
     * 1：通过
     * 2：审批中
     */
    private String verifyState;

    /**
     * 是否可以撤回（0：否 1：是）
     */
    private String isCanRecall;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 审批状态
     */
    private String isPass;

    /**
     * 拒绝理由
     */
    private String rejectReason;

    /**
     * 任务阶段id
     */
    private String taskPeriodId;

    /**
     * 任务Key
     */
    private String taskKey;

    /**
     *  签名图片路径
     */
    private String signPath;

    /**
     *  投标任务审核记录
     */
    private List<HTTaskPeriodBean> HTTaskPeriodBeanList;

    /**
     *  投标任务审核级别list
     */
    private List<HTTaskPeriodBean> htTaskPeriodAuditLevelList;

    /**
     *  审核记录和人员和问题类型
     */
    private List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList;

    /**
     * 历史id(主键)
     */
    private String historyId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 是否是主负责人
     */
    private String isMainDuty;

    /**
     * 任务流程实例ID
     */
    private String taskProcessInstId;

    /**
     * 当前流程状态
     */
    private String currentState;

    /**
     * 是否提交重新编辑申请
     */
    private String isSubmitReedit;

    /**
     * 重新编辑申请id
     */
    private String taskReeditId;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 项目编号
     */
    private String projectNum;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 职位名称
     */
    private String jobName;

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
     * 阶段类型
     */
    private String periodType;

    /**
     * 是否忽略
     */
    private String isIgnore;

    /**
     * 是否完成
     */
    private String isFinish;

    /**
     * 项目状态
     */
    private String projectSts;

    /**
     * periodType
     *
     * @return periodType 项目描述（略）
     */
    public String getPeriodType() {
        return periodType;
    }

    /**
     * periodType
     *
     * @param periodType 项目描述（略）
     */
    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    /**
     * isIgnore
     *
     * @return isIgnore 项目描述（略）
     */
    public String getIsIgnore() {
        return isIgnore;
    }

    /**
     * isIgnore
     *
     * @param isIgnore 项目描述（略）
     */
    public void setIsIgnore(String isIgnore) {
        this.isIgnore = isIgnore;
    }

    /**
     * isFinish
     *
     * @return isFinish 项目描述（略）
     */
    public String getIsFinish() {
        return isFinish;
    }

    /**
     * isFinish
     *
     * @param isFinish 项目描述（略）
     */
    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    /**
     * projectSts
     *
     * @return projectSts 项目描述（略）
     */
    public String getProjectSts() {
        return projectSts;
    }

    /**
     * projectSts
     *
     * @param projectSts 项目描述（略）
     */
    public void setProjectSts(String projectSts) {
        this.projectSts = projectSts;
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
     * signPath
     *
     * @return signPath 项目描述（略）
     */
    public String getSignPath() {
        return signPath;
    }

    /**
     * signPath
     *
     * @param signPath 项目描述（略）
     */
    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }

    /**
     * taskReeditId
     *
     * @return taskReeditId 项目描述（略）
     */
    public String getTaskReeditId() {
        return taskReeditId;
    }

    /**
     * taskReeditId
     *
     * @param taskReeditId 项目描述（略）
     */
    public void setTaskReeditId(String taskReeditId) {
        this.taskReeditId = taskReeditId;
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
     * taskProcessInstId
     *
     * @return taskProcessInstId 项目描述（略）
     */
    public String getTaskProcessInstId() {
        return taskProcessInstId;
    }

    /**
     * taskProcessInstId
     *
     * @param taskProcessInstId 项目描述（略）
     */
    public void setTaskProcessInstId(String taskProcessInstId) {
        this.taskProcessInstId = taskProcessInstId;
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
     * HTTaskPeriodBeanList
     *
     * @return HTTaskPeriodBeanList 项目描述（略）
     */
    public List<HTTaskPeriodBean> getHTTaskPeriodBeanList() {
        return HTTaskPeriodBeanList;
    }

    /**
     * HTTaskPeriodBeanList
     *
     * @param HTTaskPeriodBeanList 项目描述（略）
     */
    public void setHTTaskPeriodBeanList(List<HTTaskPeriodBean> HTTaskPeriodBeanList) {
        this.HTTaskPeriodBeanList = HTTaskPeriodBeanList;
    }

    /**
     * htUserQuestionRecordRelationBeanList
     *
     * @return htUserQuestionRecordRelationBeanList 项目描述（略）
     */
    public List<HTUserQuestionRecordRelationBean> getHtUserQuestionRecordRelationBeanList() {
        return htUserQuestionRecordRelationBeanList;
    }

    /**
     * htUserQuestionRecordRelationBeanList
     *
     * @param htUserQuestionRecordRelationBeanList 项目描述（略）
     */
    public void setHtUserQuestionRecordRelationBeanList(List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList) {
        this.htUserQuestionRecordRelationBeanList = htUserQuestionRecordRelationBeanList;
    }

    /**
     * taskPeriodId
     *
     * @return taskPeriodId 项目描述（略）
     */
    public String getTaskPeriodId() {
        return taskPeriodId;
    }

    /**
     * taskPeriodId
     *
     * @param taskPeriodId 项目描述（略）
     */
    public void setTaskPeriodId(String taskPeriodId) {
        this.taskPeriodId = taskPeriodId;
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
     * parentPeriodId
     *
     * @return parentPeriodId 项目描述（略）
     */
    public String getParentPeriodId() {
        return parentPeriodId;
    }

    /**
     * parentPeriodId
     *
     * @param parentPeriodId 项目描述（略）
     */
    public void setParentPeriodId(String parentPeriodId) {
        this.parentPeriodId = parentPeriodId;
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
     * dictDepartmentId
     *
     * @return dictDepartmentId 项目描述（略）
     */
    public String getDictDepartmentId() {
        return dictDepartmentId;
    }

    /**
     * dictDepartmentId
     *
     * @param dictDepartmentId 项目描述（略）
     */
    public void setDictDepartmentId(String dictDepartmentId) {
        this.dictDepartmentId = dictDepartmentId;
    }

    /**
     * periodLevel
     *
     * @return periodLevel 项目描述（略）
     */
    public String getPeriodLevel() {
        return periodLevel;
    }

    /**
     * periodLevel
     *
     * @param periodLevel 项目描述（略）
     */
    public void setPeriodLevel(String periodLevel) {
        this.periodLevel = periodLevel;
    }

    /**
     * periodDesc
     *
     * @return periodDesc 项目描述（略）
     */
    public String getPeriodDesc() {
        return periodDesc;
    }

    /**
     * periodDesc
     *
     * @param periodDesc 项目描述（略）
     */
    public void setPeriodDesc(String periodDesc) {
        this.periodDesc = periodDesc;
    }

    /**
     * sort
     *
     * @return sort 项目描述（略）
     */
    public String getSort() {
        return sort;
    }

    /**
     * sort
     *
     * @param sort 项目描述（略）
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * pageContent
     *
     * @return pageContent 项目描述（略）
     */
    public String getPageContent() {
        return pageContent;
    }

    /**
     * pageContent
     *
     * @param pageContent 项目描述（略）
     */
    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    /**
     * departmentName
     *
     * @return departmentName 项目描述（略）
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * departmentName
     *
     * @param departmentName 项目描述（略）
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
     * htTaskPeriodAuditLevelList
     *
     * @return htTaskPeriodAuditLevelList 项目描述（略）
     */
    public List<HTTaskPeriodBean> getHtTaskPeriodAuditLevelList() {
        return htTaskPeriodAuditLevelList;
    }

    /**
     * htTaskPeriodAuditLevelList
     *
     * @param htTaskPeriodAuditLevelList 项目描述（略）
     */
    public void setHtTaskPeriodAuditLevelList(List<HTTaskPeriodBean> htTaskPeriodAuditLevelList) {
        this.htTaskPeriodAuditLevelList = htTaskPeriodAuditLevelList;
    }

}
