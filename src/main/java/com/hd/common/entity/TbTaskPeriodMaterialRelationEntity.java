package com.hd.common.entity;

import java.util.Date;

public class TbTaskPeriodMaterialRelationEntity {
    /**
     * 关联ID
     */
    private String relationId;

    /**
     * 任务ID
     */
    private String taskPeriodId;

    /**
     * 资料路径
     */
    private String materialPath;

    /**
     * 资料真实名称
     */
    private String materialName;

    /**
     * 是否提审（0：否 1：是）
     */
    private String isReview;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否是成果文件（0：否，1：是）
     */
    private String isResultFile;

    /**
     * 附件/复印件
     */
    private String copyFile;

    /**
     * 份数
     */
    private String num;

    /**
     * 接收人
     */
    private String recipient;

    /**
     * 接收时间
     */
    private Date recipientDate;

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
     * 关联ID
     * @return relation_id 关联ID
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * 关联ID
     * @param relationId 关联ID
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    /**
     * 任务ID
     * @return task_period_id 任务ID
     */
    public String getTaskPeriodId() {
        return taskPeriodId;
    }

    /**
     * 任务ID
     * @param taskPeriodId 任务ID
     */
    public void setTaskPeriodId(String taskPeriodId) {
        this.taskPeriodId = taskPeriodId == null ? null : taskPeriodId.trim();
    }

    /**
     * 资料路径
     * @return material_path 资料路径
     */
    public String getMaterialPath() {
        return materialPath;
    }

    /**
     * 资料路径
     * @param materialPath 资料路径
     */
    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath == null ? null : materialPath.trim();
    }

    /**
     * 资料真实名称
     * @return material_name 资料真实名称
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * 资料真实名称
     * @param materialName 资料真实名称
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    /**
     * 是否提审（0：否 1：是）
     * @return is_review 是否提审（0：否 1：是）
     */
    public String getIsReview() {
        return isReview;
    }

    /**
     * 是否提审（0：否 1：是）
     * @param isReview 是否提审（0：否 1：是）
     */
    public void setIsReview(String isReview) {
        this.isReview = isReview == null ? null : isReview.trim();
    }

    /**
     * 文件类型
     * @return file_type 文件类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 文件类型
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
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
     * 是否是成果文件（0：否，1：是）
     * @return is_result_file 是否是成果文件（0：否，1：是）
     */
    public String getIsResultFile() {
        return isResultFile;
    }

    /**
     * 是否是成果文件（0：否，1：是）
     * @param isResultFile 是否是成果文件（0：否，1：是）
     */
    public void setIsResultFile(String isResultFile) {
        this.isResultFile = isResultFile == null ? null : isResultFile.trim();
    }

    /**
     * 附件/复印件
     * @return copy_file 附件/复印件
     */
    public String getCopyFile() {
        return copyFile;
    }

    /**
     * 附件/复印件
     * @param copyFile 附件/复印件
     */
    public void setCopyFile(String copyFile) {
        this.copyFile = copyFile == null ? null : copyFile.trim();
    }

    /**
     * 份数
     * @return num 份数
     */
    public String getNum() {
        return num;
    }

    /**
     * 份数
     * @param num 份数
     */
    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    /**
     * 接收人
     * @return recipient 接收人
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * 接收人
     * @param recipient 接收人
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient == null ? null : recipient.trim();
    }

    /**
     * 接收时间
     * @return recipient_date 接收时间
     */
    public Date getRecipientDate() {
        return recipientDate;
    }

    /**
     * 接收时间
     * @param recipientDate 接收时间
     */
    public void setRecipientDate(Date recipientDate) {
        this.recipientDate = recipientDate;
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