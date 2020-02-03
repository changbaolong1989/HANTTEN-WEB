package com.hd.common.entity;

import java.util.Date;

public class TbFailTenderNoticeRelationEntity {
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