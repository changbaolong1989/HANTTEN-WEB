package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

/**
 * 全过程（会议纪要） VO
 *
 * @author zjy
 * Created in 2019/9/9 11:30
 */
public class HTWholeProcessMeetingVO  extends BaseVO {

    /**
     * ID
     */
    private String meetingId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 日期
     */
    private String editDate;

    /**
     * 地点
     */
    private String address;

    /**
     *  会议主题
     */
    private String meetingTheme;

    /**
     *  内容摘要
     */
    private String contentSummary;

    /**
     *  参与单位
     */
    private String participatoryUnit;

    /**
     *  备注
     */
    private String remark;

    /**
     *  会议文件路径
     */
    private String meetingFilePath;

    /**
     *  会议文件路径
     */
    private String meetingFileName;

    /**
     * meetingId
     *
     * @return meetingId 项目描述（略）
     */
    public String getMeetingId() {
        return meetingId;
    }

    /**
     * meetingId
     *
     * @param meetingId 项目描述（略）
     */
    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    /**
     * projectId
     *
     * @return projectId 项目描述（略）
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * projectId
     *
     * @param projectId 项目描述（略）
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * editDate
     *
     * @return editDate 项目描述（略）
     */
    public String getEditDate() {
        return editDate;
    }

    /**
     * editDate
     *
     * @param editDate 项目描述（略）
     */
    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    /**
     * address
     *
     * @return address 项目描述（略）
     */
    public String getAddress() {
        return address;
    }

    /**
     * address
     *
     * @param address 项目描述（略）
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * meetingTheme
     *
     * @return meetingTheme 项目描述（略）
     */
    public String getMeetingTheme() {
        return meetingTheme;
    }

    /**
     * meetingTheme
     *
     * @param meetingTheme 项目描述（略）
     */
    public void setMeetingTheme(String meetingTheme) {
        this.meetingTheme = meetingTheme;
    }

    /**
     * contentSummary
     *
     * @return contentSummary 项目描述（略）
     */
    public String getContentSummary() {
        return contentSummary;
    }

    /**
     * contentSummary
     *
     * @param contentSummary 项目描述（略）
     */
    public void setContentSummary(String contentSummary) {
        this.contentSummary = contentSummary;
    }

    /**
     * participatoryUnit
     *
     * @return participatoryUnit 项目描述（略）
     */
    public String getParticipatoryUnit() {
        return participatoryUnit;
    }

    /**
     * participatoryUnit
     *
     * @param participatoryUnit 项目描述（略）
     */
    public void setParticipatoryUnit(String participatoryUnit) {
        this.participatoryUnit = participatoryUnit;
    }

    /**
     * remark
     *
     * @return remark 项目描述（略）
     */
    public String getRemark() {
        return remark;
    }

    /**
     * remark
     *
     * @param remark 项目描述（略）
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * meetingFilePath
     *
     * @return meetingFilePath 项目描述（略）
     */
    public String getMeetingFilePath() {
        return meetingFilePath;
    }

    /**
     * meetingFilePath
     *
     * @param meetingFilePath 项目描述（略）
     */
    public void setMeetingFilePath(String meetingFilePath) {
        this.meetingFilePath = meetingFilePath;
    }

    /**
     * meetingFileName
     *
     * @return meetingFileName 项目描述（略）
     */
    public String getMeetingFileName() {
        return meetingFileName;
    }

    /**
     * meetingFileName
     *
     * @param meetingFileName 项目描述（略）
     */
    public void setMeetingFileName(String meetingFileName) {
        this.meetingFileName = meetingFileName;
    }
}
