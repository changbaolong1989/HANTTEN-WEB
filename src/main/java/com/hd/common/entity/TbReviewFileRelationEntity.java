package com.hd.common.entity;

import java.util.Date;

public class TbReviewFileRelationEntity {
    /**
     * 文件ID(主键ID)
     */
    private String fileId;

    /**
     * 审核意见文件路径
     */
    private String reviewFilePath;

    /**
     * 审核意见文件路径
     */
    private String reviewFileName;

    /**
     * 业务ID(合同id、任务id)
     */
    private String businessId;

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
     * 文件ID(主键ID)
     * @return file_id 文件ID(主键ID)
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 文件ID(主键ID)
     * @param fileId 文件ID(主键ID)
     */
    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    /**
     * 审核意见文件路径
     * @return review_file_path 审核意见文件路径
     */
    public String getReviewFilePath() {
        return reviewFilePath;
    }

    /**
     * 审核意见文件路径
     * @param reviewFilePath 审核意见文件路径
     */
    public void setReviewFilePath(String reviewFilePath) {
        this.reviewFilePath = reviewFilePath == null ? null : reviewFilePath.trim();
    }

    /**
     * 审核意见文件路径
     * @return review_file_name 审核意见文件路径
     */
    public String getReviewFileName() {
        return reviewFileName;
    }

    /**
     * 审核意见文件路径
     * @param reviewFileName 审核意见文件路径
     */
    public void setReviewFileName(String reviewFileName) {
        this.reviewFileName = reviewFileName == null ? null : reviewFileName.trim();
    }

    /**
     * 业务ID(合同id、任务id)
     * @return business_id 业务ID(合同id、任务id)
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * 业务ID(合同id、任务id)
     * @param businessId 业务ID(合同id、任务id)
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
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