package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.io.Serializable;
import java.util.List;

/**
 * 项目管理VO
 *
 * @author jwl
 * Created in 2019/7/9 15:31
 */
public class HTProjectVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -4874979888783256948L;

    /**
     * 职位id
     */
    private String jobId;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 公司名称缩写
     */
    private String condense;

    /**
     * 人员姓名
     */
    private String userName;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 项目编号
     */
    private String projectNum;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 合同名称
     */
    private String contractName;

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
    private List<HTProjectVO> htProjectVOList;

    /**
     * 合同数
     */
    private String contractCount;

    /**
     * 项目状态
     */
    private String projectSts;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 预计结束时间
     */
    private String predictEndDate;

    /**
     * 已收入
     */
    private String incomeReceived;

    /**
     * 合同金额
     */
    private String contractAmount;

    /**
     * 已收入汇总
     */
    private String incomeReceivedSum;

    /**
     * 合同金额汇总
     */
    private String contractAmountSum;

    /**
     * 负责人
     */
    private String dutyPeople;

    /**
     * 项目编号前缀
     */
    private String projectNumPrefix;

    /**
     * 部门简写
     */
    private String abbr;

    /**
     * 咨询合同签订时间
     */
    private String contractDate;

    /**
     * 时间数组
     */
    private List<String> timeList;

    /**
     * 工作类型
     */
    private String jobType;

    /**
     * 人员身份
     */
    private String userType;

    /**
     * 任务名称
     */
    private String taskName;


    /**
     * 咨询类型阶段
     */
    private String counselTypePeriod;

    /**
     * 合同列表信息
     */
    private List<HTProjectVO> contractList;

    /**
     * 合作合同（0：否 1：是）
     */
    private String cooperateContract;

    /**
     * 合作公司
     */
    private String cooperateCompany;

    /**
     * 咨询报告编号
     */
    private String reportNum;

    /**
     * 签发时间
     */
    private String approveTime;

    /**
     * 归档编号
     */
    private String fileNumber;

    /**
     * 成果文件名称
     */
    private String taskMaterialName;

    /**
     * 模板类型
     */
    private String formworkType;

    /**
     * 项目作废原因
     */
    private String projectIsInvalidFlag;

    /**
     * 作废原因
     */
    private String invalidReason;

    /**
     * 任务ID
     */
    private String taskId;

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
     * cooperateContract
     *
     * @return cooperateContract 项目描述（略）
     */
    public String getCooperateContract() {
        return cooperateContract;
    }

    /**
     * cooperateContract
     *
     * @param cooperateContract 项目描述（略）
     */
    public void setCooperateContract(String cooperateContract) {
        this.cooperateContract = cooperateContract;
    }

    /**
     * contractList
     *
     * @return contractList 项目描述（略）
     */
    public List<HTProjectVO> getContractList() {
        return contractList;
    }

    /**
     * contractList
     *
     * @param contractList 项目描述（略）
     */
    public void setContractList(List<HTProjectVO> contractList) {
        this.contractList = contractList;
    }

    /**
     * contractAmountSum
     *
     * @return contractAmountSum 项目描述（略）
     */
    public String getContractAmountSum() {
        return contractAmountSum;
    }

    /**
     * contractAmountSum
     *
     * @param contractAmountSum 项目描述（略）
     */
    public void setContractAmountSum(String contractAmountSum) {
        this.contractAmountSum = contractAmountSum;
    }

    /**
     * incomeReceivedSum
     *
     * @return incomeReceivedSum 项目描述（略）
     */
    public String getIncomeReceivedSum() {
        return incomeReceivedSum;
    }

    /**
     * incomeReceivedSum
     *
     * @param incomeReceivedSum 项目描述（略）
     */
    public void setIncomeReceivedSum(String incomeReceivedSum) {
        this.incomeReceivedSum = incomeReceivedSum;
    }

    /**
     * timeList
     *
     * @return timeList 项目描述（略）
     */
    public List<String> getTimeList() {
        return timeList;
    }

    /**
     * timeList
     *
     * @param timeList 项目描述（略）
     */
    public void setTimeList(List<String> timeList) {
        this.timeList = timeList;
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
     * contractDate
     *
     * @return contractDate 项目描述（略）
     */
    public String getContractDate() {
        return contractDate;
    }

    /**
     * contractDate
     *
     * @param contractDate 项目描述（略）
     */
    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
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
     * projectNumPrefix
     *
     * @return projectNumPrefix 项目描述（略）
     */
    public String getProjectNumPrefix() {
        return projectNumPrefix;
    }

    /**
     * projectNumPrefix
     *
     * @param projectNumPrefix 项目描述（略）
     */
    public void setProjectNumPrefix(String projectNumPrefix) {
        this.projectNumPrefix = projectNumPrefix;
    }

    /**
     * 部门id+用户id
     */
    private String departmentIdAndUserId;

    /**
     * departmentIdAndUserId
     *
     * @return departmentIdAndUserId 项目描述（略）
     */
    public String getDepartmentIdAndUserId() {
        return departmentIdAndUserId;
    }

    /**
     * departmentIdAndUserId
     *
     * @param departmentIdAndUserId 项目描述（略）
     */
    public void setDepartmentIdAndUserId(String departmentIdAndUserId) {
        this.departmentIdAndUserId = departmentIdAndUserId;
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
     * predictEndDate
     *
     * @return predictEndDate 项目描述（略）
     */
    public String getPredictEndDate() {
        return predictEndDate;
    }

    /**
     * predictEndDate
     *
     * @param predictEndDate 项目描述（略）
     */
    public void setPredictEndDate(String predictEndDate) {
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
     * htProjectVOList
     *
     * @return htProjectVOList 项目描述（略）
     */
    public List<HTProjectVO> getHtProjectVOList() {
        return htProjectVOList;
    }

    /**
     * htProjectVOList
     *
     * @param htProjectVOList 项目描述（略）
     */
    public void setHtProjectVOList(List<HTProjectVO> htProjectVOList) {
        this.htProjectVOList = htProjectVOList;
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

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCounselTypePeriod() {
        return counselTypePeriod;
    }

    public void setCounselTypePeriod(String counselTypePeriod) {
        this.counselTypePeriod = counselTypePeriod;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getCooperateCompany() {
        return cooperateCompany;
    }

    public void setCooperateCompany(String cooperateCompany) {
        this.cooperateCompany = cooperateCompany;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public String getProjectIsInvalidFlag() {
        return projectIsInvalidFlag;
    }

    public void setProjectIsInvalidFlag(String projectIsInvalidFlag) {
        this.projectIsInvalidFlag = projectIsInvalidFlag;
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
}
