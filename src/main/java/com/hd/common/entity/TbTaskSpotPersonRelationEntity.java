package com.hd.common.entity;

import java.util.Date;

public class TbTaskSpotPersonRelationEntity {
    /**
     * 
     */
    private String relationId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 现场人员姓名
     */
    private String userName;

    /**
     * 入场时间
     */
    private Date startDate;

    /**
     * 离场时间
     */
    private Date endDate;

    /**
     * 工作内容
     */
    private String workContent;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改人
     */
    private String updateUserId;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 是否删除
     */
    private String isDelFlg;

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
     * 任务ID
     * @return task_id 任务ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务ID
     * @param taskId 任务ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * 现场人员姓名
     * @return user_name 现场人员姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 现场人员姓名
     * @param userName 现场人员姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 入场时间
     * @return start_date 入场时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 入场时间
     * @param startDate 入场时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 离场时间
     * @return end_date 离场时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 离场时间
     * @param endDate 离场时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 工作内容
     * @return work_content 工作内容
     */
    public String getWorkContent() {
        return workContent;
    }

    /**
     * 工作内容
     * @param workContent 工作内容
     */
    public void setWorkContent(String workContent) {
        this.workContent = workContent == null ? null : workContent.trim();
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