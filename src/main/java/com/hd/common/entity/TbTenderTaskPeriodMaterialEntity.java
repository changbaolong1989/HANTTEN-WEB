package com.hd.common.entity;

import java.util.Date;

public class TbTenderTaskPeriodMaterialEntity {
    /**
     * 
     */
    private String relationId;

    /**
     * 投标任务和阶段的关联表
     */
    private String tenderTaskPeriodId;

    /**
     * 文件路径
     */
    private String materialPath;

    /**
     * 文件实际名称
     */
    private String materialName;

    /**
     * 文件类型
     */
    private String fileTypeId;

    /**
     * 是否提审（0：否 1：是）
     */
    private String isReview;

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
     * 投标任务和阶段的关联表
     * @return tender_task_period_id 投标任务和阶段的关联表
     */
    public String getTenderTaskPeriodId() {
        return tenderTaskPeriodId;
    }

    /**
     * 投标任务和阶段的关联表
     * @param tenderTaskPeriodId 投标任务和阶段的关联表
     */
    public void setTenderTaskPeriodId(String tenderTaskPeriodId) {
        this.tenderTaskPeriodId = tenderTaskPeriodId == null ? null : tenderTaskPeriodId.trim();
    }

    /**
     * 文件路径
     * @return material_path 文件路径
     */
    public String getMaterialPath() {
        return materialPath;
    }

    /**
     * 文件路径
     * @param materialPath 文件路径
     */
    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath == null ? null : materialPath.trim();
    }

    /**
     * 文件实际名称
     * @return material_name 文件实际名称
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * 文件实际名称
     * @param materialName 文件实际名称
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    /**
     * 文件类型
     * @return file_type_id 文件类型
     */
    public String getFileTypeId() {
        return fileTypeId;
    }

    /**
     * 文件类型
     * @param fileTypeId 文件类型
     */
    public void setFileTypeId(String fileTypeId) {
        this.fileTypeId = fileTypeId == null ? null : fileTypeId.trim();
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