package com.hd.common.entity;

import java.util.Date;

public class TbProLetterEntity {
    /**
     * ID
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
     * ID
     * @return letter_id ID
     */
    public String getLetterId() {
        return letterId;
    }

    /**
     * ID
     * @param letterId ID
     */
    public void setLetterId(String letterId) {
        this.letterId = letterId == null ? null : letterId.trim();
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
     * 文件名称
     * @return file_name 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 文件名称
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 文件编号
     * @return file_number 文件编号
     */
    public String getFileNumber() {
        return fileNumber;
    }

    /**
     * 文件编号
     * @param fileNumber 文件编号
     */
    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber == null ? null : fileNumber.trim();
    }

    /**
     * 发文形式
     * @return send_type 发文形式
     */
    public String getSendType() {
        return sendType;
    }

    /**
     * 发文形式
     * @param sendType 发文形式
     */
    public void setSendType(String sendType) {
        this.sendType = sendType == null ? null : sendType.trim();
    }

    /**
     * 收件单位
     * @return receive_unit 收件单位
     */
    public String getReceiveUnit() {
        return receiveUnit;
    }

    /**
     * 收件单位
     * @param receiveUnit 收件单位
     */
    public void setReceiveUnit(String receiveUnit) {
        this.receiveUnit = receiveUnit == null ? null : receiveUnit.trim();
    }

    /**
     * 收件人
     * @return receive_people 收件人
     */
    public String getReceivePeople() {
        return receivePeople;
    }

    /**
     * 收件人
     * @param receivePeople 收件人
     */
    public void setReceivePeople(String receivePeople) {
        this.receivePeople = receivePeople == null ? null : receivePeople.trim();
    }

    /**
     * 文件形式
     * @return file_type 文件形式
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 文件形式
     * @param fileType 文件形式
     */
    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    /**
     * 份数
     * @return copy_num 份数
     */
    public String getCopyNum() {
        return copyNum;
    }

    /**
     * 份数
     * @param copyNum 份数
     */
    public void setCopyNum(String copyNum) {
        this.copyNum = copyNum == null ? null : copyNum.trim();
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
     * 信函文件路径
     * @return letter_file_path 信函文件路径
     */
    public String getLetterFilePath() {
        return letterFilePath;
    }

    /**
     * 信函文件路径
     * @param letterFilePath 信函文件路径
     */
    public void setLetterFilePath(String letterFilePath) {
        this.letterFilePath = letterFilePath == null ? null : letterFilePath.trim();
    }

    /**
     * 信函文件真实名
     * @return letter_file_name 信函文件真实名
     */
    public String getLetterFileName() {
        return letterFileName;
    }

    /**
     * 信函文件真实名
     * @param letterFileName 信函文件真实名
     */
    public void setLetterFileName(String letterFileName) {
        this.letterFileName = letterFileName == null ? null : letterFileName.trim();
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