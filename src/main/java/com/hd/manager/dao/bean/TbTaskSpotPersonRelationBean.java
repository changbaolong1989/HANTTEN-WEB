package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;
import com.hd.base.vo.BaseVO;

import java.util.Date;

public class TbTaskSpotPersonRelationBean extends BaseBean {
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
}