package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

/**
 * 全过程（发出信函登记表）Bean
 *
 * @author zjy
 * Created in 2019/9/9 11:30
 */
public class HTWholeProcessLetterBean extends BaseBean {

    /**
     * 表ID
     */
    private String letterId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 日期
     */
    private Date editDate;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件编号
     */
    private String fileNumber;

    /**
     * 发文形式
     */
    private String sendType;

    /**
     * 收件单位
     */
    private String receiveUnit;

    /**
     * 收件人
     */
    private String receivePeople;

    /**
     * 文件形式
     */
    private String fileType;

    /**
     * 份数
     */
    private String copyNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 信函文件路径
     */
    private String letterFilePath;

    /**
     * 信函文件真实名
     */
    private String letterFileName;

    /**
     * letterId
     *
     * @return letterId 项目描述（略）
     */
    public String getLetterId() {
        return letterId;
    }

    /**
     * letterId
     *
     * @param letterId 项目描述（略）
     */
    public void setLetterId(String letterId) {
        this.letterId = letterId;
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
    public Date getEditDate() {
        return editDate;
    }

    /**
     * editDate
     *
     * @param editDate 项目描述（略）
     */
    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    /**
     * fileName
     *
     * @return fileName 项目描述（略）
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * fileName
     *
     * @param fileName 项目描述（略）
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * fileNumber
     *
     * @return fileNumber 项目描述（略）
     */
    public String getFileNumber() {
        return fileNumber;
    }

    /**
     * fileNumber
     *
     * @param fileNumber 项目描述（略）
     */
    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    /**
     * sendType
     *
     * @return sendType 项目描述（略）
     */
    public String getSendType() {
        return sendType;
    }

    /**
     * sendType
     *
     * @param sendType 项目描述（略）
     */
    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    /**
     * receiveUnit
     *
     * @return receiveUnit 项目描述（略）
     */
    public String getReceiveUnit() {
        return receiveUnit;
    }

    /**
     * receiveUnit
     *
     * @param receiveUnit 项目描述（略）
     */
    public void setReceiveUnit(String receiveUnit) {
        this.receiveUnit = receiveUnit;
    }

    /**
     * receivePeople
     *
     * @return receivePeople 项目描述（略）
     */
    public String getReceivePeople() {
        return receivePeople;
    }

    /**
     * receivePeople
     *
     * @param receivePeople 项目描述（略）
     */
    public void setReceivePeople(String receivePeople) {
        this.receivePeople = receivePeople;
    }

    /**
     * fileType
     *
     * @return fileType 项目描述（略）
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * fileType
     *
     * @param fileType 项目描述（略）
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * copyNum
     *
     * @return copyNum 项目描述（略）
     */
    public String getCopyNum() {
        return copyNum;
    }

    /**
     * copyNum
     *
     * @param copyNum 项目描述（略）
     */
    public void setCopyNum(String copyNum) {
        this.copyNum = copyNum;
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
     * letterFilePath
     *
     * @return letterFilePath 项目描述（略）
     */
    public String getLetterFilePath() {
        return letterFilePath;
    }

    /**
     * letterFilePath
     *
     * @param letterFilePath 项目描述（略）
     */
    public void setLetterFilePath(String letterFilePath) {
        this.letterFilePath = letterFilePath;
    }

    /**
     * letterFileName
     *
     * @return letterFileName 项目描述（略）
     */
    public String getLetterFileName() {
        return letterFileName;
    }

    /**
     * letterFileName
     *
     * @param letterFileName 项目描述（略）
     */
    public void setLetterFileName(String letterFileName) {
        this.letterFileName = letterFileName;
    }
}
