package com.hd.common.entity;

import java.util.Date;

public class TbUserScheduleRecordEntity {
    /**
     * 
     */
    private String relationId;

    /**
     * 任务和用户关联ID
     */
    private String taskUserId;

    /**
     * 当前时间
     */
    private Date nowDate;

    /**
     * 实际工时
     */
    private Integer actualHour;

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
     * 任务和用户关联ID
     * @return task_user_id 任务和用户关联ID
     */
    public String getTaskUserId() {
        return taskUserId;
    }

    /**
     * 任务和用户关联ID
     * @param taskUserId 任务和用户关联ID
     */
    public void setTaskUserId(String taskUserId) {
        this.taskUserId = taskUserId == null ? null : taskUserId.trim();
    }

    /**
     * 当前时间
     * @return now_date 当前时间
     */
    public Date getNowDate() {
        return nowDate;
    }

    /**
     * 当前时间
     * @param nowDate 当前时间
     */
    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    /**
     * 实际工时
     * @return actual_hour 实际工时
     */
    public Integer getActualHour() {
        return actualHour;
    }

    /**
     * 实际工时
     * @param actualHour 实际工时
     */
    public void setActualHour(Integer actualHour) {
        this.actualHour = actualHour;
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