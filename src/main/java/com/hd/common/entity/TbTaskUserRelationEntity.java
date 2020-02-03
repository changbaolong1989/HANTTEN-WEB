package com.hd.common.entity;

import java.util.Date;

public class TbTaskUserRelationEntity {
    /**
     * 
     */
    private String relationId;

    /**
     * 
     */
    private String taskId;

    /**
     * 
     */
    private String userId;

    /**
     * 是否是编写成果物的人(0：否 1：是）
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
     * 执行时间
     */
    private Date executeDate;

    /**
     * 实际执行工时
     */
    private Integer actualHour;

    /**
     * 备注
     */
    private String remarks;

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
     * 是否已完成(0：否 1：是)
     */
    private String isDone;

    /**
     * 自定义工时
     */
    private Integer customizeHour;

    /**
     * 工时原因
     */
    private String hourReason;

    /**
     * 
     * @return relation_id 
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * 
     * @param relationId 
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    /**
     * 
     * @return task_id 
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 
     * @param taskId 
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * 
     * @return user_id 
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId 
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 是否是编写成果物的人(0：否 1：是）
     * @return is_main_duty 是否是编写成果物的人(0：否 1：是）
     */
    public String getIsMainDuty() {
        return isMainDuty;
    }

    /**
     * 是否是编写成果物的人(0：否 1：是）
     * @param isMainDuty 是否是编写成果物的人(0：否 1：是）
     */
    public void setIsMainDuty(String isMainDuty) {
        this.isMainDuty = isMainDuty == null ? null : isMainDuty.trim();
    }

    /**
     * 任务说明
     * @return task_content 任务说明
     */
    public String getTaskContent() {
        return taskContent;
    }

    /**
     * 任务说明
     * @param taskContent 任务说明
     */
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent == null ? null : taskContent.trim();
    }

    /**
     * 开始时间
     * @return start_date 开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 开始时间
     * @param startDate 开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 完成时间
     * @return end_date 完成时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 完成时间
     * @param endDate 完成时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 所需工时
     * @return need_hour 所需工时
     */
    public Integer getNeedHour() {
        return needHour;
    }

    /**
     * 所需工时
     * @param needHour 所需工时
     */
    public void setNeedHour(Integer needHour) {
        this.needHour = needHour;
    }

    /**
     * 执行时间
     * @return execute_date 执行时间
     */
    public Date getExecuteDate() {
        return executeDate;
    }

    /**
     * 执行时间
     * @param executeDate 执行时间
     */
    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

    /**
     * 实际执行工时
     * @return actual_hour 实际执行工时
     */
    public Integer getActualHour() {
        return actualHour;
    }

    /**
     * 实际执行工时
     * @param actualHour 实际执行工时
     */
    public void setActualHour(Integer actualHour) {
        this.actualHour = actualHour;
    }

    /**
     * 备注
     * @return remarks 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

    /**
     * 是否已完成(0：否 1：是)
     * @return is_done 是否已完成(0：否 1：是)
     */
    public String getIsDone() {
        return isDone;
    }

    /**
     * 是否已完成(0：否 1：是)
     * @param isDone 是否已完成(0：否 1：是)
     */
    public void setIsDone(String isDone) {
        this.isDone = isDone == null ? null : isDone.trim();
    }

    /**
     * 自定义工时
     * @return customize_hour 自定义工时
     */
    public Integer getCustomizeHour() {
        return customizeHour;
    }

    /**
     * 自定义工时
     * @param customizeHour 自定义工时
     */
    public void setCustomizeHour(Integer customizeHour) {
        this.customizeHour = customizeHour;
    }

    /**
     * 工时原因
     * @return hour_reason 工时原因
     */
    public String getHourReason() {
        return hourReason;
    }

    /**
     * 工时原因
     * @param hourReason 工时原因
     */
    public void setHourReason(String hourReason) {
        this.hourReason = hourReason == null ? null : hourReason.trim();
    }
}