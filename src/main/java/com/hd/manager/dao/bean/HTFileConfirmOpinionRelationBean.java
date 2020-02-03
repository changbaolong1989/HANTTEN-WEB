package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

public class HTFileConfirmOpinionRelationBean extends BaseBean {
    /**
     * 主键ID
     */
    private String confirmOpinionId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 招标控制价
     */
    private Integer tenderSumLimit;

    /**
     * 采购人
     */
    private String purchaser;

    /**
     * 招标代理机构
     */
    private String agentOrg;

    /**
     * 是否同意(0:否 1:是)
     */
    private String isAgree;

    /**
     * 审核意见
     */
    private String approveOpinion;

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
     * 主键ID
     * @return confirm_opinion_id 主键ID
     */
    public String getConfirmOpinionId() {
        return confirmOpinionId;
    }

    /**
     * 主键ID
     * @param confirmOpinionId 主键ID
     */
    public void setConfirmOpinionId(String confirmOpinionId) {
        this.confirmOpinionId = confirmOpinionId == null ? null : confirmOpinionId.trim();
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
     * tenderSumLimit
     *
     * @return tenderSumLimit 项目描述（略）
     */
    public Integer getTenderSumLimit() {
        return tenderSumLimit;
    }

    /**
     * tenderSumLimit
     *
     * @param tenderSumLimit 项目描述（略）
     */
    public void setTenderSumLimit(Integer tenderSumLimit) {
        this.tenderSumLimit = tenderSumLimit;
    }

    /**
     * 采购人
     * @return purchaser 采购人
     */
    public String getPurchaser() {
        return purchaser;
    }

    /**
     * 采购人
     * @param purchaser 采购人
     */
    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser == null ? null : purchaser.trim();
    }

    /**
     * 招标代理机构
     * @return agent_org 招标代理机构
     */
    public String getAgentOrg() {
        return agentOrg;
    }

    /**
     * 招标代理机构
     * @param agentOrg 招标代理机构
     */
    public void setAgentOrg(String agentOrg) {
        this.agentOrg = agentOrg == null ? null : agentOrg.trim();
    }

    /**
     * 是否同意(0:否 1:是)
     * @return is_agree 是否同意(0:否 1:是)
     */
    public String getIsAgree() {
        return isAgree;
    }

    /**
     * 是否同意(0:否 1:是)
     * @param isAgree 是否同意(0:否 1:是)
     */
    public void setIsAgree(String isAgree) {
        this.isAgree = isAgree == null ? null : isAgree.trim();
    }

    /**
     * 审核意见
     * @return approve_opinion 审核意见
     */
    public String getApproveOpinion() {
        return approveOpinion;
    }

    /**
     * 审核意见
     * @param approveOpinion 审核意见
     */
    public void setApproveOpinion(String approveOpinion) {
        this.approveOpinion = approveOpinion == null ? null : approveOpinion.trim();
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