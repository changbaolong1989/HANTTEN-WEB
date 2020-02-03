package com.hd.common.entity;

import java.util.Date;

public class TbProMeetingEntity {
    /**
     * 会议纪要ID
     */
    private String meetingId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 日期
     */
    private Date editDate;

    /**
     * 地点
     */
    private String address;

    /**
     * 会议主题
     */
    private String meetingTheme;

    /**
     * 内容摘要
     */
    private String contentSummary;

    /**
     * 参与单位
     */
    private String participatoryUnit;

    /**
     * 备注
     */
    private String remark;

    /**
     * 会议文件路径
     */
    private String meetingFilePath;

    /**
     * 会议文件真实名称
     */
    private String meetingFileName;

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
     * 会议纪要ID
     * @return meeting_id 会议纪要ID
     */
    public String getMeetingId() {
        return meetingId;
    }

    /**
     * 会议纪要ID
     * @param meetingId 会议纪要ID
     */
    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId == null ? null : meetingId.trim();
    }

    /**
     * 项目ID
     * @return project_id 项目ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 项目ID
     * @param projectId 项目ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
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
     * 地点
     * @return address 地点
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地点
     * @param address 地点
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 会议主题
     * @return meeting_theme 会议主题
     */
    public String getMeetingTheme() {
        return meetingTheme;
    }

    /**
     * 会议主题
     * @param meetingTheme 会议主题
     */
    public void setMeetingTheme(String meetingTheme) {
        this.meetingTheme = meetingTheme == null ? null : meetingTheme.trim();
    }

    /**
     * 内容摘要
     * @return content_summary 内容摘要
     */
    public String getContentSummary() {
        return contentSummary;
    }

    /**
     * 内容摘要
     * @param contentSummary 内容摘要
     */
    public void setContentSummary(String contentSummary) {
        this.contentSummary = contentSummary == null ? null : contentSummary.trim();
    }

    /**
     * 参与单位
     * @return participatory_unit 参与单位
     */
    public String getParticipatoryUnit() {
        return participatoryUnit;
    }

    /**
     * 参与单位
     * @param participatoryUnit 参与单位
     */
    public void setParticipatoryUnit(String participatoryUnit) {
        this.participatoryUnit = participatoryUnit == null ? null : participatoryUnit.trim();
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 会议文件路径
     * @return meeting_file_path 会议文件路径
     */
    public String getMeetingFilePath() {
        return meetingFilePath;
    }

    /**
     * 会议文件路径
     * @param meetingFilePath 会议文件路径
     */
    public void setMeetingFilePath(String meetingFilePath) {
        this.meetingFilePath = meetingFilePath == null ? null : meetingFilePath.trim();
    }

    /**
     * 会议文件真实名称
     * @return meeting_file_name 会议文件真实名称
     */
    public String getMeetingFileName() {
        return meetingFileName;
    }

    /**
     * 会议文件真实名称
     * @param meetingFileName 会议文件真实名称
     */
    public void setMeetingFileName(String meetingFileName) {
        this.meetingFileName = meetingFileName == null ? null : meetingFileName.trim();
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