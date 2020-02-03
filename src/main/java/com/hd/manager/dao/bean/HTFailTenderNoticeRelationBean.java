package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

public class HTFailTenderNoticeRelationBean extends BaseBean {
    /**
     * 落标通知书ID
     */
    private String noticeId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 投标单位名称
     */
    private String tenderUnitName;

    /**
     * 采购代理机构
     */
    private String agentOrg;

    /**
     * 日期
     */
    private Date editDate;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目编号
     */
    private String projectNum;

    /**
     * 任务名称
     */
    private String taskName;

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
     * 落标通知书ID
     * @return notice_id 落标通知书ID
     */
    public String getNoticeId() {
        return noticeId;
    }

    /**
     * 落标通知书ID
     * @param noticeId 落标通知书ID
     */
    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId == null ? null : noticeId.trim();
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
     * 投标单位名称
     * @return tender_unit_name 投标单位名称
     */
    public String getTenderUnitName() {
        return tenderUnitName;
    }

    /**
     * 投标单位名称
     * @param tenderUnitName 投标单位名称
     */
    public void setTenderUnitName(String tenderUnitName) {
        this.tenderUnitName = tenderUnitName == null ? null : tenderUnitName.trim();
    }

    /**
     * 采购代理机构
     * @return agent_org 采购代理机构
     */
    public String getAgentOrg() {
        return agentOrg;
    }

    /**
     * 采购代理机构
     * @param agentOrg 采购代理机构
     */
    public void setAgentOrg(String agentOrg) {
        this.agentOrg = agentOrg == null ? null : agentOrg.trim();
    }

    /**
     * 日期
     * @return edit_date 日期
     */
    public Date getEditDate() {
        return editDate;
    }

    /**
     * 日期
     * @param editDate 日期
     */
    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

}