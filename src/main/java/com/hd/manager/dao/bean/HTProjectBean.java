package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;
import java.util.Date;
import java.util.List;

/**
 * 项目管理Bean
 *
 * @author jwl
 * Created in 2019/7/9 15:31
 */
public class HTProjectBean extends BaseBean {

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
     * 用户名
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
     * 高管部门
     */
    private String ggDepartmentId;

    /**
     * 合同id
     */
    private String contractId;

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
     * 开始时间
     */
    private Date startDate;

    /**
     * 合同开始时间
     */
    private Date contractDate;

    /**
     * 预计结束时间
     */
    private Date predictEndDate;

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
     * 结束时间
     */
    private Date endDate;

    /**
     * 负责人
     */
    private String dutyPeople;

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
     * 阶段类型
     */
    private String periodType;

    /**
     * 页面展示内容
     */
    private String pageContent;

    /**
     * 阶段说明
     */
    private String periodDesc;

    /**
     * 任务阶段关联id
     */
    private String relationId;

    /**
     * 工作类型
     */
    private String jobType;

    /**
     * 人员身份
     */
    private String userType;

    /**
     *咨询业务类型（0：造价咨询 1：招标代理 2：项目管理(监理)）
     */
    private String counselBusinessType;

    /**
     *咨询类型阶段
     */
    private String counselTypePeriod;

    /**
     * 审核状态
     */
    private String verifyState;

    /**
     * 是否忽略
     */
    private String isIgnore;

    /**
     * 是否完成
     */
    private String isFinish;

    /**
     * 部门id集合
     */
    private List<String> departmentIdList;

    /**
     * 咨询类别id
     */
    private String counselTypeId;

    /**
     * 咨询类别名称
     */
    private String counselTypeName;

    /**
     * 是否提了重新编辑申请
     */
    private String isSubmitReedit;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 主提审人id
     */
    private String applyUserId;

    /**
     * 合作公司
     */
    private String cooperateCompany;

    /**
     * 提审时间
     */
    private Date applyTime;

    /**
     * 请款单id
     */
    private String requestMenuId;

    /**
     * 请款单名称
     */
    private String requestMenuName;

    /**
     * 作废标识
     */
    private String isInvalidFlag;

    /**
     * 项目作废标识
     */
    private String projectIsInvalidFlag;

    /**
     * 作废原因
     */
    private String invalidReason;

    /**
     * 合同列表
     */
    private List<HTProjectBean> htContractBeanList;

    /**
     * 任务列表
     */
    private List<HTProjectBean> htTaskBeanList;

    /**
     * 任务阶段列表
     */
    private List<HTProjectBean> htTaskPeriodBeanList;

    /**
     * 请款单列表
     */
    private List<HTProjectBean> htRequestMenuBeanList;

    /**
     * 咨询报告编号
     */
    private String reportNum;

    /**
     * 签发时间
     */
    private Date approveTime;

    /**
     * 归档编号
     */
    private String fileNumber;

    /**
     * 模板类型
     */
    private String formworkType;

    /**
     * 成果文件名称
     */
    private String taskMaterialName;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 当前流程状态
     */
    private String currentState;

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
     * requestMenuId
     *
     * @return requestMenuId 项目描述（略）
     */
    public String getRequestMenuId() {
        return requestMenuId;
    }

    /**
     * requestMenuId
     *
     * @param requestMenuId 项目描述（略）
     */
    public void setRequestMenuId(String requestMenuId) {
        this.requestMenuId = requestMenuId;
    }

    /**
     * requestMenuName
     *
     * @return requestMenuName 项目描述（略）
     */
    public String getRequestMenuName() {
        return requestMenuName;
    }

    /**
     * requestMenuName
     *
     * @param requestMenuName 项目描述（略）
     */
    public void setRequestMenuName(String requestMenuName) {
        this.requestMenuName = requestMenuName;
    }

    /**
     * htContractBeanList
     *
     * @return htContractBeanList 项目描述（略）
     */
    public List<HTProjectBean> getHtContractBeanList() {
        return htContractBeanList;
    }

    /**
     * htContractBeanList
     *
     * @param htContractBeanList 项目描述（略）
     */
    public void setHtContractBeanList(List<HTProjectBean> htContractBeanList) {
        this.htContractBeanList = htContractBeanList;
    }

    /**
     * htTaskBeanList
     *
     * @return htTaskBeanList 项目描述（略）
     */
    public List<HTProjectBean> getHtTaskBeanList() {
        return htTaskBeanList;
    }

    /**
     * htTaskBeanList
     *
     * @param htTaskBeanList 项目描述（略）
     */
    public void setHtTaskBeanList(List<HTProjectBean> htTaskBeanList) {
        this.htTaskBeanList = htTaskBeanList;
    }

    /**
     * htTaskPeriodBeanList
     *
     * @return htTaskPeriodBeanList 项目描述（略）
     */
    public List<HTProjectBean> getHtTaskPeriodBeanList() {
        return htTaskPeriodBeanList;
    }

    /**
     * htTaskPeriodBeanList
     *
     * @param htTaskPeriodBeanList 项目描述（略）
     */
    public void setHtTaskPeriodBeanList(List<HTProjectBean> htTaskPeriodBeanList) {
        this.htTaskPeriodBeanList = htTaskPeriodBeanList;
    }

    /**
     * htRequestMenuBeanList
     *
     * @return htRequestMenuBeanList 项目描述（略）
     */
    public List<HTProjectBean> getHtRequestMenuBeanList() {
        return htRequestMenuBeanList;
    }

    /**
     * htRequestMenuBeanList
     *
     * @param htRequestMenuBeanList 项目描述（略）
     */
    public void setHtRequestMenuBeanList(List<HTProjectBean> htRequestMenuBeanList) {
        this.htRequestMenuBeanList = htRequestMenuBeanList;
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
     * ggDepartmentId
     *
     * @return ggDepartmentId 项目描述（略）
     */
    public String getGgDepartmentId() {
        return ggDepartmentId;
    }

    /**
     * ggDepartmentId
     *
     * @param ggDepartmentId 项目描述（略）
     */
    public void setGgDepartmentId(String ggDepartmentId) {
        this.ggDepartmentId = ggDepartmentId;
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
     * departmentIdList
     *
     * @return departmentIdList 项目描述（略）
     */
    public List<String> getDepartmentIdList() {
        return departmentIdList;
    }

    /**
     * departmentIdList
     *
     * @param departmentIdList 项目描述（略）
     */
    public void setDepartmentIdList(List<String> departmentIdList) {
        this.departmentIdList = departmentIdList;
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
     * jobType
     *
     * @return jobType 项目描述（略）
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * jobType
     *
     * @param jobType 项目描述（略）
     */
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    /**
     * userType
     *
     * @return userType 项目描述（略）
     */
    public String getUserType() {
        return userType;
    }

    /**
     * userType
     *
     * @param userType 项目描述（略）
     */
    public void setUserType(String userType) {
        this.userType = userType;
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
     * contractName
     *
     * @return contractName 项目描述（略）
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * contractName
     *
     * @param contractName 项目描述（略）
     */
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    /**
     * cooperateCompany
     *
     * @return cooperateCompany 项目描述（略）
     */
    public String getCooperateCompany() {
        return cooperateCompany;
    }

    /**
     * cooperateCompany
     *
     * @param cooperateCompany 项目描述（略）
     */
    public void setCooperateCompany(String cooperateCompany) {
        this.cooperateCompany = cooperateCompany;
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

    public String getProjectIsInvalidFlag() {
        return projectIsInvalidFlag;
    }

    public void setProjectIsInvalidFlag(String projectIsInvalidFlag) {
        this.projectIsInvalidFlag = projectIsInvalidFlag;
    }
}
