package com.hd.common.entity;

import java.util.Date;

public class TbTableTwoRelationEntity {
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
    private Date planStartDate;

    /**
     * 计划完成时间
     */
    private Date planEndDate;

    /**
     * 实际完成时间
     */
    private Date actualStartDate;

    /**
     * 实际完成时间
     */
    private Date actualEndDate;

    /**
     * 延迟原因
     */
    private String delayReason;

    /**
     * 咨询主要结论
     */
    private String mainResult;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改人
     */
    private String updateUserId;

    /**
     * 是否删除
     */
    private String isDelFlg;

    /**
     * 主键ID
     * @return relation_id 主键ID
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * 主键ID
     * @param relationId 主键ID
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    /**
     * 任务和阶段ID
     * @return task_id 任务和阶段ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务和阶段ID
     * @param taskId 任务和阶段ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * 成果文件编号
     * @return result_file_number 成果文件编号
     */
    public String getResultFileNumber() {
        return resultFileNumber;
    }

    /**
     * 成果文件编号
     * @param resultFileNumber 成果文件编号
     */
    public void setResultFileNumber(String resultFileNumber) {
        this.resultFileNumber = resultFileNumber == null ? null : resultFileNumber.trim();
    }

    /**
     * 项目概况
     * @return project_condition 项目概况
     */
    public String getProjectCondition() {
        return projectCondition;
    }

    /**
     * 项目概况
     * @param projectCondition 项目概况
     */
    public void setProjectCondition(String projectCondition) {
        this.projectCondition = projectCondition == null ? null : projectCondition.trim();
    }

    /**
     * 发包人
     * @return employer 发包人
     */
    public String getEmployer() {
        return employer;
    }

    /**
     * 发包人
     * @param employer 发包人
     */
    public void setEmployer(String employer) {
        this.employer = employer == null ? null : employer.trim();
    }

    /**
     * 项目中标人
     * @return bid_person 项目中标人
     */
    public String getBidPerson() {
        return bidPerson;
    }

    /**
     * 项目中标人
     * @param bidPerson 项目中标人
     */
    public void setBidPerson(String bidPerson) {
        this.bidPerson = bidPerson == null ? null : bidPerson.trim();
    }

    /**
     * 计划开始时间
     * @return plan_start_date 计划开始时间
     */
    public Date getPlanStartDate() {
        return planStartDate;
    }

    /**
     * 计划开始时间
     * @param planStartDate 计划开始时间
     */
    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    /**
     * 计划完成时间
     * @return plan_end_date 计划完成时间
     */
    public Date getPlanEndDate() {
        return planEndDate;
    }

    /**
     * 计划完成时间
     * @param planEndDate 计划完成时间
     */
    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    /**
     * 实际完成时间
     * @return actual_start_date 实际完成时间
     */
    public Date getActualStartDate() {
        return actualStartDate;
    }

    /**
     * 实际完成时间
     * @param actualStartDate 实际完成时间
     */
    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    /**
     * 实际完成时间
     * @return actual_end_date 实际完成时间
     */
    public Date getActualEndDate() {
        return actualEndDate;
    }

    /**
     * 实际完成时间
     * @param actualEndDate 实际完成时间
     */
    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    /**
     * 延迟原因
     * @return delay_reason 延迟原因
     */
    public String getDelayReason() {
        return delayReason;
    }

    /**
     * 延迟原因
     * @param delayReason 延迟原因
     */
    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason == null ? null : delayReason.trim();
    }

    /**
     * 咨询主要结论
     * @return main_result 咨询主要结论
     */
    public String getMainResult() {
        return mainResult;
    }

    /**
     * 咨询主要结论
     * @param mainResult 咨询主要结论
     */
    public void setMainResult(String mainResult) {
        this.mainResult = mainResult == null ? null : mainResult.trim();
    }

    /**
     * 创建时间
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 创建人
     * @return create_user_id 创建人
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     * @param createUserId 创建人
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    /**
     * 修改时间
     * @return update_date 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 修改时间
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 修改人
     * @return update_user_id 修改人
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 修改人
     * @param updateUserId 修改人
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * 是否删除
     * @return is_del_flg 是否删除
     */
    public String getIsDelFlg() {
        return isDelFlg;
    }

    /**
     * 是否删除
     * @param isDelFlg 是否删除
     */
    public void setIsDelFlg(String isDelFlg) {
        this.isDelFlg = isDelFlg == null ? null : isDelFlg.trim();
    }
}