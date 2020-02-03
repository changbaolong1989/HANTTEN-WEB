package com.hd.common.entity;

import java.util.Date;

public class TbUserCustomTime {
    /**
     * 自定义任务ID
     */
    private String customTaskId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 完成时间
     */
    private Date completeTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 预计占用工时
     */
    private Integer predictTime;

    /**
     * 已分配工时
     */
    private Integer allocatedTime;

    /**
     * 今日工时分配
     */
    private Integer todayTime;

    /**
     * 原因
     */
    private String reason;

    /**
     * 自定义任务ID
     * @return custom_task_id 自定义任务ID
     */
    public String getCustomTaskId() {
        return customTaskId;
    }

    /**
     * 自定义任务ID
     * @param customTaskId 自定义任务ID
     */
    public void setCustomTaskId(String customTaskId) {
        this.customTaskId = customTaskId == null ? null : customTaskId.trim();
    }

    /**
     * 用户ID
     * @return user_id 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 任务名称
     * @return task_name 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 任务名称
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    /**
     * 完成时间
     * @return complete_time 完成时间
     */
    public Date getCompleteTime() {
        return completeTime;
    }

    /**
     * 完成时间
     * @param completeTime 完成时间
     */
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 预计占用工时
     * @return predict_time 预计占用工时
     */
    public Integer getPredictTime() {
        return predictTime;
    }

    /**
     * 预计占用工时
     * @param predictTime 预计占用工时
     */
    public void setPredictTime(Integer predictTime) {
        this.predictTime = predictTime;
    }

    /**
     * 已分配工时
     * @return allocated_time 已分配工时
     */
    public Integer getAllocatedTime() {
        return allocatedTime;
    }

    /**
     * 已分配工时
     * @param allocatedTime 已分配工时
     */
    public void setAllocatedTime(Integer allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    /**
     * 今日工时分配
     * @return today_time 今日工时分配
     */
    public Integer getTodayTime() {
        return todayTime;
    }

    /**
     * 今日工时分配
     * @param todayTime 今日工时分配
     */
    public void setTodayTime(Integer todayTime) {
        this.todayTime = todayTime;
    }

    /**
     * 原因
     * @return reason 原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 原因
     * @param reason 原因
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }
}