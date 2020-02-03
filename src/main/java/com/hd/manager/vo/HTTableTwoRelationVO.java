package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.util.Date;

/**
 * 任务阶段和2的关联VO
 *
 * @author bx
 * Created in 2019/8/19 10:32
 */
public class HTTableTwoRelationVO extends BaseVO {

     /**
     * 主键ID
     */
    private String relationId;

    /**
     * 任务和阶段ID
     */
    private String taskId;

    /**
     * 成果文件编号
     */
    private String resultFileNumber;

    /**
     * 项目概况
     */
    private String projectCondition;

    /**
     * 发包人
     */
    private String employer;

    /**
     * 项目中标人
     */
    private String bidPerson;

    /**
     * 计划开始时间
     */
    private String planStartDate;

    /**
     * 计划完成时间
     */
    private String planEndDate;

    /**
     * 计划时间数组
     */
    private String[] planDateArr;

    /**
     * 实际完成时间
     */
    private String actualStartDate;

    /**
     * 实际完成时间
     */
    private String actualEndDate;

    /**
     * 延迟原因
     */
    private String delayReason;

    /**
     * 咨询主要结论
     */
    private String mainResult;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目委托单位
     */
    private String trusterName;

    /**
     * 合同签订时间
     */
    private String contractDate;

    /**
     * 项目类别
     */
    private String counselBusinessType;

    /**
     * 项目负责人
     */
    private String dutyPeople;

    /**
     * 项目负责人签字时间
     */
    private String approveTime;

    /**
     * 成果文件编号
     */
    private String reportNum;

    /**
     * 项目编号
     */
    private String projectNum;

    /**
     * 提审时间
     */
    private String applyTime;

    /**
     * 提审人路径
     */
    private String applyUserSignPath;

    /**
     * 创建人路径
     */
    private String createUserSignPath;

    /**
     * applyTime
     *
     * @return applyTime 项目描述（略）
     */
    @Override
    public String getApplyTime() {
        return applyTime;
    }

    /**
     * applyTime
     *
     * @param applyTime 项目描述（略）
     */
    @Override
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * applyUserSignPath
     *
     * @return applyUserSignPath 项目描述（略）
     */
    public String getApplyUserSignPath() {
        return applyUserSignPath;
    }

    /**
     * applyUserSignPath
     *
     * @param applyUserSignPath 项目描述（略）
     */
    public void setApplyUserSignPath(String applyUserSignPath) {
        this.applyUserSignPath = applyUserSignPath;
    }

    /**
     * createUserSignPath
     *
     * @return createUserSignPath 项目描述（略）
     */
    public String getCreateUserSignPath() {
        return createUserSignPath;
    }

    /**
     * createUserSignPath
     *
     * @param createUserSignPath 项目描述（略）
     */
    public void setCreateUserSignPath(String createUserSignPath) {
        this.createUserSignPath = createUserSignPath;
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
     * relationId.
     *
     * @return relationId 项目描述（略）
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * relationId.
     *
     * @param relationId 项目描述（略）
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    /**
     * taskId.
     *
     * @return taskId 项目描述（略）
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * taskId.
     *
     * @param taskId 项目描述（略）
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * resultFileNumber.
     *
     * @return resultFileNumber 项目描述（略）
     */
    public String getResultFileNumber() {
        return resultFileNumber;
    }

    /**
     * resultFileNumber.
     *
     * @param resultFileNumber 项目描述（略）
     */
    public void setResultFileNumber(String resultFileNumber) {
        this.resultFileNumber = resultFileNumber;
    }

    /**
     * projectCondition.
     *
     * @return projectCondition 项目描述（略）
     */
    public String getProjectCondition() {
        return projectCondition;
    }

    /**
     * projectCondition.
     *
     * @param projectCondition 项目描述（略）
     */
    public void setProjectCondition(String projectCondition) {
        this.projectCondition = projectCondition;
    }

    /**
     * employer.
     *
     * @return employer 项目描述（略）
     */
    public String getEmployer() {
        return employer;
    }

    /**
     * employer.
     *
     * @param employer 项目描述（略）
     */
    public void setEmployer(String employer) {
        this.employer = employer;
    }

    /**
     * bidPerson.
     *
     * @return bidPerson 项目描述（略）
     */
    public String getBidPerson() {
        return bidPerson;
    }

    /**
     * bidPerson.
     *
     * @param bidPerson 项目描述（略）
     */
    public void setBidPerson(String bidPerson) {
        this.bidPerson = bidPerson;
    }

    /**
     * planStartDate.
     *
     * @return planStartDate 项目描述（略）
     */
    public String getPlanStartDate() {
        return planStartDate;
    }

    /**
     * planStartDate.
     *
     * @param planStartDate 项目描述（略）
     */
    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    /**
     * planEndDate.
     *
     * @return planEndDate 项目描述（略）
     */
    public String getPlanEndDate() {
        return planEndDate;
    }

    /**
     * planEndDate.
     *
     * @param planEndDate 项目描述（略）
     */
    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    /**
     * actualStartDate.
     *
     * @return actualStartDate 项目描述（略）
     */
    public String getActualStartDate() {
        return actualStartDate;
    }

    /**
     * actualStartDate.
     *
     * @param actualStartDate 项目描述（略）
     */
    public void setActualStartDate(String actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    /**
     * actualEndDate.
     *
     * @return actualEndDate 项目描述（略）
     */
    public String getActualEndDate() {
        return actualEndDate;
    }

    /**
     * actualEndDate.
     *
     * @param actualEndDate 项目描述（略）
     */
    public void setActualEndDate(String actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    /**
     * delayReason.
     *
     * @return delayReason 项目描述（略）
     */
    public String getDelayReason() {
        return delayReason;
    }

    /**
     * delayReason.
     *
     * @param delayReason 项目描述（略）
     */
    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason;
    }

    /**
     * mainResult.
     *
     * @return mainResult 项目描述（略）
     */
    public String getMainResult() {
        return mainResult;
    }

    /**
     * mainResult.
     *
     * @param mainResult 项目描述（略）
     */
    public void setMainResult(String mainResult) {
        this.mainResult = mainResult;
    }

    /**
     * contractNum.
     *
     * @return contractNum 项目描述（略）
     */
    public String getContractNum() {
        return contractNum;
    }

    /**
     * contractNum.
     *
     * @param contractNum 项目描述（略）
     */
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    /**
     * projectName.
     *
     * @return projectName 项目描述（略）
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * projectName.
     *
     * @param projectName 项目描述（略）
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * trusterName.
     *
     * @return trusterName 项目描述（略）
     */
    public String getTrusterName() {
        return trusterName;
    }

    /**
     * trusterName.
     *
     * @param trusterName 项目描述（略）
     */
    public void setTrusterName(String trusterName) {
        this.trusterName = trusterName;
    }

    /**
     * contractDate.
     *
     * @return contractDate 项目描述（略）
     */
    public String getContractDate() {
        return contractDate;
    }

    /**
     * contractDate.
     *
     * @param contractDate 项目描述（略）
     */
    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    /**
     * counselBusinessType.
     *
     * @return counselBusinessType 项目描述（略）
     */
    public String getCounselBusinessType() {
        return counselBusinessType;
    }

    /**
     * counselBusinessType.
     *
     * @param counselBusinessType 项目描述（略）
     */
    public void setCounselBusinessType(String counselBusinessType) {
        this.counselBusinessType = counselBusinessType;
    }

    /**
     * dutyPeople.
     *
     * @return dutyPeople 项目描述（略）
     */
    public String getDutyPeople() {
        return dutyPeople;
    }

    /**
     * dutyPeople.
     *
     * @param dutyPeople 项目描述（略）
     */
    public void setDutyPeople(String dutyPeople) {
        this.dutyPeople = dutyPeople;
    }

    /**
     * approveTime.
     *
     * @return approveTime 项目描述（略）
     */
    @Override
    public String getApproveTime() {
        return approveTime;
    }

    /**
     * approveTime.
     *
     * @param approveTime 项目描述（略）
     */
    @Override
    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * reportNum.
     *
     * @return reportNum 项目描述（略）
     */
    public String getReportNum() {
        return reportNum;
    }

    /**
     * reportNum.
     *
     * @param reportNum 项目描述（略）
     */
    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }

    /**
     * planDateArr
     *
     * @return planDateArr 项目描述（略）
     */
    public String[] getPlanDateArr() {
        return planDateArr;
    }

    /**
     * planDateArr
     *
     * @param planDateArr 项目描述（略）
     */
    public void setPlanDateArr(String[] planDateArr) {
        this.planDateArr = planDateArr;
    }
}