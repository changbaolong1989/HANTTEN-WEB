package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;
import com.hd.manager.vo.HTTenderTaskUserVO;

import java.util.Date;
import java.util.List;

public class HTWorkBean extends BaseBean {

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
     * 投标任务状态(0：未开标 1；未中标 2：已中标)
     */
    private String tenderTaskSts;

    /**
     * 开标时间(投标任务)/预计收款时间(合同)/开始时间(项目管理)
     */
    private Date startDate;

    /**
     * 流程状态(0：待审核 1：审核中 2：审核完成)
     */
    private String processSts;

    /**
     * 主负责人ID
     */
    private String mainLeaderId;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 是否通过
     * 1：已通过
     * 0：未通过
     */
    private String isPass;

    /**
     * 审核结果(0：已驳回 1：已通过)
     */
    private String applyResult;

    /**
     *成员ID(投标任务)/用户id(项目管理)
     */
    private String userId;

    /**
     *成员名称(投标任务)/用户名(项目管理)
     */
    private String userName;

    /**
     *委托任务ID
     */
    private String delegateTaskId;

    /**
     *提交节点
     */
    private String submitNode;

    /**
     * 投标任务序号
     */
    private String sortNum;

    /**
     * 委托任务Key
     */
    private String delegateTaskKey;
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
     * 投标任务ID集合
     */
    private List<String> tenderTaskIds;

    /**
     * 关联人员
     */
    private List<HTTenderTaskUserVO> userList;


    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 其他属性
     */
    private String projectTarget;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 咨询业务类型（0：造价咨询 1：招标代理 2：项目管理(监理)）
     */
    private String counselBusinessType;

    /**
     * 业务类型
     */
    private String counselTypePeriod;

    /**
     * 项目地点
     */
    private String projectAddress;

    /**
     * 投资金额
     */
    private String investedAmount;

    /**
     * 是否暂定(投资金额)
     */
    private String isProvisional;

    /**
     * 委托人
     */
    private String trusterName;

    /**
     * 委托人性质
     */
    private String trusterType;

    /**
     * 委托方负责人
     */
    private String trusterDutyUser;

    /**
     * 委托方负责人电话
     */
    private String trusterDutyPhone;

    /**
     * 咨询人
     */
    private String consultUser;

    /**
     * 第三方（合作单位）
     */
    private String thirdParty;

    /**
     * 拟定合同负责人(合同)/负责人(项目管理)
     */
    private String dutyPeople;

    /**
     * 拟定人
     */
    private String planner;

    /**
     * 合同性质
     */
    private String contractNature;

    /**
     * 确定方式
     */
    private String determinateMethod;


    /**
     * 拟定合同金额(合同)/合同金额(项目管理)
     */
    private String contractAmount;

    /**
     * 咨询合同签订日期(合同)/合同开始时间(项目管理)
     */
    private Date contractDate;

    /**
     * 拟定合同金额暂定
     */
    private String isValueProvisional;

    /**
     * 其他情况
     */
    private String otherThings;

    /**
     * 合同类型
     */
    private String contractType;

    /**
     * 模板合同类型
     */
    private String formworkType;

    /**
     * 文件路径
     */
    private String contractFilePath;

    /**
     * 文件名称
     */
    private String contractFileName;

    /**
     * 预计收款id
     */
    private String predictRequestId;

    /**
     * 收款金额
     */
    private String receivedAmount;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 公司名称缩写
     */
    private String condense;

    /**
     * 职位id
     */
    private String jobId;

    /**
     * 职位名称
     */
    private String jobName;

    /**
     * 部门名称
     */
    private String departmentName;


    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 合同状态
     */
    private String contractSts;

    /**
     * 分配人id
     */
    private String deliveryUserId;

    /**
     * 项目列表
     */
    private List<HTProjectBean> htProjectBeanList;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目编号
     */
    private String projectNum;

    /**
     * 合同数
     */
    private String contractCount;

    /**
     * 项目状态
     */
    private String projectSts;

    /**
     * 预计结束时间
     */
    private Date predictEndDate;

    /**
     * 已收入
     */
    private String incomeReceived;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 造价部id
     */
    private String deptIdZj;

    /**
     * 招标代理部id
     */
    private String deptIdZbdl;

    /**
     * 项目监理部id
     */
    private String deptIdXmjl;

    /**
     * 简写
     */
    private String abbr;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 阶段id
     */
    private String periodId;

    /**
     * 阶段名称
     */
    private String periodName;

    /**
     * 合同id列表
     */
    private List<String> contractIdList;

    /**
     * 合同和投标任务idList
     */
    private List<String> htIdList;

    /**
     * 合同当前状态名称
     */
    private String currentStateName;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 项目结束人
     */
    private String endProjectUserName;

    /**
     * 高管用户ID
     */
    private String seniorManagerId;

    /**
     * 高管用户名称
     */
    private String seniorManagerName;

    /**
     * 数据来源
     */
    private String sourceType;

    /*
     *扭转人表示
     */
    private String checkSign;

    private String businessType;

    private String count;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同创建时间
     */
    private Date contractCreateDate;

    /**
     * 合同创建人
     */
    private String contractCreateUserName;

    /**
     * 合作公司
     */
    private String cooperateCompany;

    /**
     * 审批时长
     */
    private long duration;

    /**
     * 咨询类别
     */
    private String counselType;



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
     * processSts
     *
     * @return processSts 项目描述（略）
     */
    public String getProcessSts() {
        return processSts;
    }

    /**
     * processSts
     *
     * @param processSts 项目描述（略）
     */
    public void setProcessSts(String processSts) {
        this.processSts = processSts;
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
     * applyResult
     *
     * @return applyResult 项目描述（略）
     */
    public String getApplyResult() {
        return applyResult;
    }

    /**
     * applyResult
     *
     * @param applyResult 项目描述（略）
     */
    public void setApplyResult(String applyResult) {
        this.applyResult = applyResult;
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
     * approveTime
     *
     * @return approveTime 项目描述（略）
     */
    public String getApproveTime() {
        return approveTime;
    }

    /**
     * approveTime
     *
     * @param approveTime 项目描述（略）
     */
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
     * tenderTaskIds
     *
     * @return tenderTaskIds 项目描述（略）
     */
    public List<String> getTenderTaskIds() {
        return tenderTaskIds;
    }

    /**
     * tenderTaskIds
     *
     * @param tenderTaskIds 项目描述（略）
     */
    public void setTenderTaskIds(List<String> tenderTaskIds) {
        this.tenderTaskIds = tenderTaskIds;
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
     * projectType
     *
     * @return projectType 项目描述（略）
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * projectType
     *
     * @param projectType 项目描述（略）
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    /**
     * projectTarget
     *
     * @return projectTarget 项目描述（略）
     */
    public String getProjectTarget() {
        return projectTarget;
    }

    /**
     * projectTarget
     *
     * @param projectTarget 项目描述（略）
     */
    public void setProjectTarget(String projectTarget) {
        this.projectTarget = projectTarget;
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
     * counselBusinessType
     *
     * @return counselBusinessType 项目描述（略）
     */
    public String getCounselBusinessType() {
        return counselBusinessType;
    }

    /**
     * counselBusinessType
     *
     * @param counselBusinessType 项目描述（略）
     */
    public void setCounselBusinessType(String counselBusinessType) {
        this.counselBusinessType = counselBusinessType;
    }

    /**
     * counselTypePeriod
     *
     * @return counselTypePeriod 项目描述（略）
     */
    public String getCounselTypePeriod() {
        return counselTypePeriod;
    }

    /**
     * counselTypePeriod
     *
     * @param counselTypePeriod 项目描述（略）
     */
    public void setCounselTypePeriod(String counselTypePeriod) {
        this.counselTypePeriod = counselTypePeriod;
    }

    /**
     * projectAddress
     *
     * @return projectAddress 项目描述（略）
     */
    public String getProjectAddress() {
        return projectAddress;
    }

    /**
     * projectAddress
     *
     * @param projectAddress 项目描述（略）
     */
    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    /**
     * investedAmount
     *
     * @return investedAmount 项目描述（略）
     */
    public String getInvestedAmount() {
        return investedAmount;
    }

    /**
     * investedAmount
     *
     * @param investedAmount 项目描述（略）
     */
    public void setInvestedAmount(String investedAmount) {
        this.investedAmount = investedAmount;
    }

    /**
     * isProvisional
     *
     * @return isProvisional 项目描述（略）
     */
    public String getIsProvisional() {
        return isProvisional;
    }

    /**
     * isProvisional
     *
     * @param isProvisional 项目描述（略）
     */
    public void setIsProvisional(String isProvisional) {
        this.isProvisional = isProvisional;
    }

    /**
     * trusterName
     *
     * @return trusterName 项目描述（略）
     */
    public String getTrusterName() {
        return trusterName;
    }

    /**
     * trusterName
     *
     * @param trusterName 项目描述（略）
     */
    public void setTrusterName(String trusterName) {
        this.trusterName = trusterName;
    }

    /**
     * trusterType
     *
     * @return trusterType 项目描述（略）
     */
    public String getTrusterType() {
        return trusterType;
    }

    /**
     * trusterType
     *
     * @param trusterType 项目描述（略）
     */
    public void setTrusterType(String trusterType) {
        this.trusterType = trusterType;
    }

    /**
     * trusterDutyUser
     *
     * @return trusterDutyUser 项目描述（略）
     */
    public String getTrusterDutyUser() {
        return trusterDutyUser;
    }

    /**
     * trusterDutyUser
     *
     * @param trusterDutyUser 项目描述（略）
     */
    public void setTrusterDutyUser(String trusterDutyUser) {
        this.trusterDutyUser = trusterDutyUser;
    }

    /**
     * trusterDutyPhone
     *
     * @return trusterDutyPhone 项目描述（略）
     */
    public String getTrusterDutyPhone() {
        return trusterDutyPhone;
    }

    /**
     * trusterDutyPhone
     *
     * @param trusterDutyPhone 项目描述（略）
     */
    public void setTrusterDutyPhone(String trusterDutyPhone) {
        this.trusterDutyPhone = trusterDutyPhone;
    }

    /**
     * consultUser
     *
     * @return consultUser 项目描述（略）
     */
    public String getConsultUser() {
        return consultUser;
    }

    /**
     * consultUser
     *
     * @param consultUser 项目描述（略）
     */
    public void setConsultUser(String consultUser) {
        this.consultUser = consultUser;
    }

    /**
     * thirdParty
     *
     * @return thirdParty 项目描述（略）
     */
    public String getThirdParty() {
        return thirdParty;
    }

    /**
     * thirdParty
     *
     * @param thirdParty 项目描述（略）
     */
    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }

    /**
     * dutyPeople
     *
     * @return dutyPeople 项目描述（略）
     */
    public String getDutyPeople() {
        return dutyPeople;
    }

    /**
     * dutyPeople
     *
     * @param dutyPeople 项目描述（略）
     */
    public void setDutyPeople(String dutyPeople) {
        this.dutyPeople = dutyPeople;
    }

    /**
     * planner
     *
     * @return planner 项目描述（略）
     */
    public String getPlanner() {
        return planner;
    }

    /**
     * planner
     *
     * @param planner 项目描述（略）
     */
    public void setPlanner(String planner) {
        this.planner = planner;
    }

    /**
     * contractNature
     *
     * @return contractNature 项目描述（略）
     */
    public String getContractNature() {
        return contractNature;
    }

    /**
     * contractNature
     *
     * @param contractNature 项目描述（略）
     */
    public void setContractNature(String contractNature) {
        this.contractNature = contractNature;
    }

    /**
     * determinateMethod
     *
     * @return determinateMethod 项目描述（略）
     */
    public String getDeterminateMethod() {
        return determinateMethod;
    }

    /**
     * determinateMethod
     *
     * @param determinateMethod 项目描述（略）
     */
    public void setDeterminateMethod(String determinateMethod) {
        this.determinateMethod = determinateMethod;
    }

    /**
     * contractAmount
     *
     * @return contractAmount 项目描述（略）
     */
    public String getContractAmount() {
        return contractAmount;
    }

    /**
     * contractAmount
     *
     * @param contractAmount 项目描述（略）
     */
    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    /**
     * contractDate
     *
     * @return contractDate 项目描述（略）
     */
    public Date getContractDate() {
        return contractDate;
    }

    /**
     * contractDate
     *
     * @param contractDate 项目描述（略）
     */
    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    /**
     * isValueProvisional
     *
     * @return isValueProvisional 项目描述（略）
     */
    public String getIsValueProvisional() {
        return isValueProvisional;
    }

    /**
     * isValueProvisional
     *
     * @param isValueProvisional 项目描述（略）
     */
    public void setIsValueProvisional(String isValueProvisional) {
        this.isValueProvisional = isValueProvisional;
    }

    /**
     * otherThings
     *
     * @return otherThings 项目描述（略）
     */
    public String getOtherThings() {
        return otherThings;
    }

    /**
     * otherThings
     *
     * @param otherThings 项目描述（略）
     */
    public void setOtherThings(String otherThings) {
        this.otherThings = otherThings;
    }

    /**
     * contractType
     *
     * @return contractType 项目描述（略）
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * contractType
     *
     * @param contractType 项目描述（略）
     */
    public void setContractType(String contractType) {
        this.contractType = contractType;
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
     * contractFilePath
     *
     * @return contractFilePath 项目描述（略）
     */
    public String getContractFilePath() {
        return contractFilePath;
    }

    /**
     * contractFilePath
     *
     * @param contractFilePath 项目描述（略）
     */
    public void setContractFilePath(String contractFilePath) {
        this.contractFilePath = contractFilePath;
    }

    /**
     * contractFileName
     *
     * @return contractFileName 项目描述（略）
     */
    public String getContractFileName() {
        return contractFileName;
    }

    /**
     * contractFileName
     *
     * @param contractFileName 项目描述（略）
     */
    public void setContractFileName(String contractFileName) {
        this.contractFileName = contractFileName;
    }

    /**
     * predictRequestId
     *
     * @return predictRequestId 项目描述（略）
     */
    public String getPredictRequestId() {
        return predictRequestId;
    }

    /**
     * predictRequestId
     *
     * @param predictRequestId 项目描述（略）
     */
    public void setPredictRequestId(String predictRequestId) {
        this.predictRequestId = predictRequestId;
    }

    /**
     * receivedAmount
     *
     * @return receivedAmount 项目描述（略）
     */
    public String getReceivedAmount() {
        return receivedAmount;
    }

    /**
     * receivedAmount
     *
     * @param receivedAmount 项目描述（略）
     */
    public void setReceivedAmount(String receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    /**
     * companyId
     *
     * @return companyId 项目描述（略）
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * companyId
     *
     * @param companyId 项目描述（略）
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * condense
     *
     * @return condense 项目描述（略）
     */
    public String getCondense() {
        return condense;
    }

    /**
     * condense
     *
     * @param condense 项目描述（略）
     */
    public void setCondense(String condense) {
        this.condense = condense;
    }

    /**
     * jobId
     *
     * @return jobId 项目描述（略）
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * jobId
     *
     * @param jobId 项目描述（略）
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
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
     * contractSts
     *
     * @return contractSts 项目描述（略）
     */
    public String getContractSts() {
        return contractSts;
    }

    /**
     * contractSts
     *
     * @param contractSts 项目描述（略）
     */
    public void setContractSts(String contractSts) {
        this.contractSts = contractSts;
    }

    /**
     * deliveryUserId
     *
     * @return deliveryUserId 项目描述（略）
     */
    public String getDeliveryUserId() {
        return deliveryUserId;
    }

    /**
     * deliveryUserId
     *
     * @param deliveryUserId 项目描述（略）
     */
    public void setDeliveryUserId(String deliveryUserId) {
        this.deliveryUserId = deliveryUserId;
    }

    /**
     * htProjectBeanList
     *
     * @return htProjectBeanList 项目描述（略）
     */
    public List<HTProjectBean> getHtProjectBeanList() {
        return htProjectBeanList;
    }

    /**
     * htProjectBeanList
     *
     * @param htProjectBeanList 项目描述（略）
     */
    public void setHtProjectBeanList(List<HTProjectBean> htProjectBeanList) {
        this.htProjectBeanList = htProjectBeanList;
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
     * contractCount
     *
     * @return contractCount 项目描述（略）
     */
    public String getContractCount() {
        return contractCount;
    }

    /**
     * contractCount
     *
     * @param contractCount 项目描述（略）
     */
    public void setContractCount(String contractCount) {
        this.contractCount = contractCount;
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
     * predictEndDate
     *
     * @return predictEndDate 项目描述（略）
     */
    public Date getPredictEndDate() {
        return predictEndDate;
    }

    /**
     * predictEndDate
     *
     * @param predictEndDate 项目描述（略）
     */
    public void setPredictEndDate(Date predictEndDate) {
        this.predictEndDate = predictEndDate;
    }

    /**
     * incomeReceived
     *
     * @return incomeReceived 项目描述（略）
     */
    public String getIncomeReceived() {
        return incomeReceived;
    }

    /**
     * incomeReceived
     *
     * @param incomeReceived 项目描述（略）
     */
    public void setIncomeReceived(String incomeReceived) {
        this.incomeReceived = incomeReceived;
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
     * deptIdZj
     *
     * @return deptIdZj 项目描述（略）
     */
    public String getDeptIdZj() {
        return deptIdZj;
    }

    /**
     * deptIdZj
     *
     * @param deptIdZj 项目描述（略）
     */
    public void setDeptIdZj(String deptIdZj) {
        this.deptIdZj = deptIdZj;
    }

    /**
     * deptIdZbdl
     *
     * @return deptIdZbdl 项目描述（略）
     */
    public String getDeptIdZbdl() {
        return deptIdZbdl;
    }

    /**
     * deptIdZbdl
     *
     * @param deptIdZbdl 项目描述（略）
     */
    public void setDeptIdZbdl(String deptIdZbdl) {
        this.deptIdZbdl = deptIdZbdl;
    }

    /**
     * deptIdXmjl
     *
     * @return deptIdXmjl 项目描述（略）
     */
    public String getDeptIdXmjl() {
        return deptIdXmjl;
    }

    /**
     * deptIdXmjl
     *
     * @param deptIdXmjl 项目描述（略）
     */
    public void setDeptIdXmjl(String deptIdXmjl) {
        this.deptIdXmjl = deptIdXmjl;
    }

    /**
     * abbr
     *
     * @return abbr 项目描述（略）
     */
    public String getAbbr() {
        return abbr;
    }

    /**
     * abbr
     *
     * @param abbr 项目描述（略）
     */
    public void setAbbr(String abbr) {
        this.abbr = abbr;
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
     * contractIdList
     *
     * @return contractIdList 项目描述（略）
     */
    public List<String> getContractIdList() {
        return contractIdList;
    }

    /**
     * contractIdList
     *
     * @param contractIdList 项目描述（略）
     */
    public void setContractIdList(List<String> contractIdList) {
        this.contractIdList = contractIdList;
    }

    /**
     * htIdList
     *
     * @return htIdList 项目描述（略）
     */
    public List<String> getHtIdList() {
        return htIdList;
    }

    /**
     * htIdList
     *
     * @param htIdList 项目描述（略）
     */
    public void setHtIdList(List<String> htIdList) {
        this.htIdList = htIdList;
    }

    /**
     * currentStateName
     *
     * @return currentStateName 项目描述（略）
     */
    public String getCurrentStateName() {
        return currentStateName;
    }

    /**
     * currentStateName
     *
     * @param currentStateName 项目描述（略）
     */
    public void setCurrentStateName(String currentStateName) {
        this.currentStateName = currentStateName;
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

    public String getCheckSign() {
        return checkSign;
    }

    public void setCheckSign(String checkSign) {
        this.checkSign = checkSign;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Date getContractCreateDate() {
        return contractCreateDate;
    }

    public void setContractCreateDate(Date contractCreateDate) {
        this.contractCreateDate = contractCreateDate;
    }

    public String getContractCreateUserName() {
        return contractCreateUserName;
    }

    public void setContractCreateUserName(String contractCreateUserName) {
        this.contractCreateUserName = contractCreateUserName;
    }

    public String getCooperateCompany() {
        return cooperateCompany;
    }

    public void setCooperateCompany(String cooperateCompany) {
        this.cooperateCompany = cooperateCompany;
    }

    /**
     * duration
     *
     * @return duration 项目描述（略）
     */
    public long getDuration() {
        return duration;
    }

    /**
     * duration
     *
     * @param duration 项目描述（略）
     */
    public void setDuration(long duration) {
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
