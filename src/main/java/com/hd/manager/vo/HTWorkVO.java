package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.util.List;

public class HTWorkVO extends BaseVO {

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 投标任务ID
     */
    private String tenderTaskId;
    /**
     * 投标任务名称
     */
    private String tenderTaskName;
    /**
     * 投标任务概况
     */
    private String tenderTaskContent;

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
     * 审核状态
     0：未通过
     1：通过
     2：审批中
     */
    private String verifyState;

    /**
     * 是否可以撤回
     */
    private String isCanRecall;


    /**
     * 提交节点
     */
    private String submitNode;

    /**
     * 主提审人ID
     */
    private String applyUserId;

    /**
     * 主提审人NAME
     */
    private String applyUserName;

    /**
     * 审批时间
     */
    private String approveTime;


    /**
     * 审批时间
     */
    private String completeTime;


    /**
     * 投标任务序号
     */
    private String sortNum;
    /**
     * 投标任务状态(0：未开标 1；未中标 2：已中标)
     */
    private String tenderTaskSts;
    /**
     * 开标时间
     */
    private String startDate;

    private List<HTTenderTaskUserVO> userList;

    /**
     * 成员名称
     */
    private String userName;

    /**
     * 主负责人ID
     */
    private String mainLeaderId;

    /**
     * 查询类型
     */
    private String searchType;

    /**
     * 实体类型(0:合同,1投标任务,2项目)
     */
    private String entityType;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目编号
     */
    private String projectNum;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 项目结束人
     */
    private String endProjectUserName;

    /**
     * 项目结束时间
     */
    private String endDate;

    /**
     * 高管用户ID
     */
    private String seniorManagerId;

    /**
     * 高管用户名称
     */
    private String seniorManagerName;

    /**
     * 合同当前状态名称
     */
    private String currentStateName;

    /**
     * 当前的待办信息是否显示通过按钮
     */
    private String isPass;

    /**
     *  数据来源
     */
    private String sourceType;

    /**
     * 编纂人ID
     */
    private String compilationUserId;

    /**
     * 编纂人名称
     */
    private String compilationUserName;

    /**
     * 流转人标识
     */
    private String checkSign;




    /**
     * 合同名称
     */
    private String projectName;
    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同创建时间
     */
    private String contractCreateDate;

    /**
     * 合同创建人
     */
    private String contractCreateUserName;

    /**
     * 最后办理时间
     */
    private String handleDate;

    /**
     * 合作公司
     */
    private String cooperateCompany;

    /**
     * 业务类型
     */
    private String counselTypePeriod;

    /**
     * 审批时长
     */
    private String duration;

    /**
     * 咨询类别
     */
    private String counselType;


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
     * endProjectUserName
     *
     * @return endProjectUserName 项目描述（略）
     */
    public String getEndProjectUserName() {
        return endProjectUserName;
    }

    /**
     * endProjectUserName
     *
     * @param endProjectUserName 项目描述（略）
     */
    public void setEndProjectUserName(String endProjectUserName) {
        this.endProjectUserName = endProjectUserName;
    }

    /**
     * createUserName
     *
     * @return createUserName 项目描述（略）
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * createUserName
     *
     * @param createUserName 项目描述（略）
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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
     * tenderTaskName
     *
     * @return tenderTaskName 项目描述（略）
     */
    public String getTenderTaskName() {
        return tenderTaskName;
    }

    /**
     * tenderTaskName
     *
     * @param tenderTaskName 项目描述（略）
     */
    public void setTenderTaskName(String tenderTaskName) {
        this.tenderTaskName = tenderTaskName;
    }

    /**
     * tenderTaskContent
     *
     * @return tenderTaskContent 项目描述（略）
     */
    public String getTenderTaskContent() {
        return tenderTaskContent;
    }

    /**
     * tenderTaskContent
     *
     * @param tenderTaskContent 项目描述（略）
     */
    public void setTenderTaskContent(String tenderTaskContent) {
        this.tenderTaskContent = tenderTaskContent;
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
     * sortNum
     *
     * @return sortNum 项目描述（略）
     */
    public String getSortNum() {
        return sortNum;
    }

    /**
     * sortNum
     *
     * @param sortNum 项目描述（略）
     */
    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * tenderTaskSts
     *
     * @return tenderTaskSts 项目描述（略）
     */
    public String getTenderTaskSts() {
        return tenderTaskSts;
    }

    /**
     * tenderTaskSts
     *
     * @param tenderTaskSts 项目描述（略）
     */
    public void setTenderTaskSts(String tenderTaskSts) {
        this.tenderTaskSts = tenderTaskSts;
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
     * userList
     *
     * @return userList 项目描述（略）
     */
    public List<HTTenderTaskUserVO> getUserList() {
        return userList;
    }

    /**
     * userList
     *
     * @param userList 项目描述（略）
     */
    public void setUserList(List<HTTenderTaskUserVO> userList) {
        this.userList = userList;
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
     * mainLeaderId
     *
     * @return mainLeaderId 项目描述（略）
     */
    public String getMainLeaderId() {
        return mainLeaderId;
    }

    /**
     * mainLeaderId
     *
     * @param mainLeaderId 项目描述（略）
     */
    public void setMainLeaderId(String mainLeaderId) {
        this.mainLeaderId = mainLeaderId;
    }

    /**
     * searchType
     *
     * @return searchType 项目描述（略）
     */
    public String getSearchType() {
        return searchType;
    }

    /**
     * searchType
     *
     * @param searchType 项目描述（略）
     */
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    /**
     * entityType
     *
     * @return entityType 项目描述（略）
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * entityType
     *
     * @param entityType 项目描述（略）
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
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

    public String getSeniorManagerId() {
        return seniorManagerId;
    }

    public void setSeniorManagerId(String seniorManagerId) {
        this.seniorManagerId = seniorManagerId;
    }

    public String getSeniorManagerName() {
        return seniorManagerName;
    }

    public void setSeniorManagerName(String seniorManagerName) {
        this.seniorManagerName = seniorManagerName;
    }

    public String getCurrentStateName() {
        return currentStateName;
    }

    public void setCurrentStateName(String currentStateName) {
        this.currentStateName = currentStateName;
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
     * sourceType
     *
     * @return sourceType 项目描述（略）
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * sourceType
     *
     * @param sourceType 项目描述（略）
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getCompilationUserId() {
        return compilationUserId;
    }

    public void setCompilationUserId(String compilationUserId) {
        this.compilationUserId = compilationUserId;
    }

    public String getCompilationUserName() {
        return compilationUserName;
    }

    public void setCompilationUserName(String compilationUserName) {
        this.compilationUserName = compilationUserName;
    }

    public String getCheckSign() {
        return checkSign;
    }

    public void setCheckSign(String checkSign) {
        this.checkSign = checkSign;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractCreateDate() {
        return contractCreateDate;
    }

    public void setContractCreateDate(String contractCreateDate) {
        this.contractCreateDate = contractCreateDate;
    }

    public String getContractCreateUserName() {
        return contractCreateUserName;
    }

    public void setContractCreateUserName(String contractCreateUserName) {
        this.contractCreateUserName = contractCreateUserName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }

    public String getCooperateCompany() {
        return cooperateCompany;
    }

    public void setCooperateCompany(String cooperateCompany) {
        this.cooperateCompany = cooperateCompany;
    }

    public String getCounselTypePeriod() {
        return counselTypePeriod;
    }

    public void setCounselTypePeriod(String counselTypePeriod) {
        this.counselTypePeriod = counselTypePeriod;
    }

    /**
     * duration
     *
     * @return duration 项目描述（略）
     */
    public String getDuration() {
        return duration;
    }

    /**
     * duration
     *
     * @param duration 项目描述（略）
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * counselType
     *
     * @return counselType 项目描述（略）
     */
    public String getCounselType() {
        return counselType;
    }

    /**
     * counselType
     *
     * @param counselType 项目描述（略）
     */
    public void setCounselType(String counselType) {
        this.counselType = counselType;
    }
}
