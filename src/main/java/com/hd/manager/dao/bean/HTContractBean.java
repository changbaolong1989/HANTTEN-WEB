package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * 合同Bean
 *
 * @author jwl
 * Created in 2019/7/17 15:31
 */
public class HTContractBean extends BaseBean {

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
     * 咨询类型阶段
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
     * 拟定合同负责人
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
     * 拟定合同金额
     */
    private String contractAmount;

    /**
     * 咨询合同签订日期
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
    private Double receivedAmount;

    /**
     * 预计收款时间
     */
    private Date startDate;


    /**
     * 审批时间
     */
    private String approveTime;

    /**
     * 审批人
     */
    private String approveUserName;

    /**
     * 审批人ID
     */
    private String approveUserId;

    /**
     * 提审人
     */
    private String applyUserName;

    /**
     * 提审人id
     */
    private String applyUserId;

    /**
     * 提交节点
     */
    private String submitNode;

    /**
     * 委托任务ID
     */
    private String delegateTaskId;

    /**
     * 委托任务Key
     */
    private String delegateTaskKey;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 是否可以撤回（0：否 1：是）
     */
    private String isCanRecall;

    /**
     * 审批状态(0.未通过 1.通过 2.审核中 3,已分配)
     */
    private String verifyState;

    /**
     * 流程完成时间
     */
    private String completeTime;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 职位ID
     */
    private String jobId;

    /**
     * 部门ID(根据部门ID可以查询签发人)
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 职位名称
     */
    private String jobName;

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
     *  主键id
     */
    private String relationId;

    /**
     *  归档文件名称
     */
    private String materialName;

    /**
     * 归档文件路径
     */
    private String materialPath;

    /**
     *  项目是否关闭
     */
    private String projectSts;

    /**
     *  当前流程节点
     */
    private String currentState;

    /**
     *  项目编号
     */
    private String projectNum;

    /**
     *  合同编号
     */
    private String contractNum;

    /**
     * 法务意见
     */
    private String legalOpinion;

    /**
     * 当前时间
     */
    private Date currentDate;

    /**
     * 提审时间
     */
    private Date applyTime;

    /**
     * openid
     */
    private String openId;

    /**
     * 合同盖章类型(1.委托代理人章2.法定代表人章)
     */
    private String contractSealType;

    /**
     * 合作合同
     */
    private String cooperateContract;

    /**
     * 是否作废标识
     */
    private String isInvalidFlag;

    /**
     * 作废原因
     */
    private String invalidReason;

    /**
     * 合同ID数据集
     */
    private List<String> contractIdList;
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
     * receivedAmount
     *
     * @return receivedAmount 项目描述（略）
     */
    public Double getReceivedAmount() {
        return receivedAmount;
    }

    /**
     * receivedAmount
     *
     * @param receivedAmount 项目描述（略）
     */
    public void setReceivedAmount(Double receivedAmount) {
        this.receivedAmount = receivedAmount;
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
     * 预计收款时间
     */
    private List<HTContractBean> predictRequestDateList;

    /**
     * predictRequestDateList
     *
     * @return predictRequestDateList 项目描述（略）
     */
    public List<HTContractBean> getPredictRequestDateList() {
        return predictRequestDateList;
    }

    /**
     * predictRequestDateList
     *
     * @param predictRequestDateList 项目描述（略）
     */
    public void setPredictRequestDateList(List<HTContractBean> predictRequestDateList) {
        this.predictRequestDateList = predictRequestDateList;
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
     * materialName
     *
     * @return materialName 项目描述（略）
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * materialName
     *
     * @param materialName 项目描述（略）
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    /**
     * materialPath
     *
     * @return materialPath 项目描述（略）
     */
    public String getMaterialPath() {
        return materialPath;
    }

    /**
     * materialPath
     *
     * @param materialPath 项目描述（略）
     */
    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
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

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getLegalOpinion() {
        return legalOpinion;
    }

    public void setLegalOpinion(String legalOpinion) {
        this.legalOpinion = legalOpinion;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getContractSealType() {
        return contractSealType;
    }

    public void setContractSealType(String contractSealType) {
        this.contractSealType = contractSealType;
    }

    public String getCooperateContract() {
        return cooperateContract;
    }

    public void setCooperateContract(String cooperateContract) {
        this.cooperateContract = cooperateContract;
    }

    public String getIsInvalidFlag() {
        return isInvalidFlag;
    }

    public void setIsInvalidFlag(String isInvalidFlag) {
        this.isInvalidFlag = isInvalidFlag;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public List<String> getContractIdList() {
        return contractIdList;
    }

    public void setContractIdList(List<String> contractIdList) {
        this.contractIdList = contractIdList;
    }
}
