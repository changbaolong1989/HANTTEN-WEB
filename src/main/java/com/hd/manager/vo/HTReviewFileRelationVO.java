package com.hd.manager.vo;


/**
 * 审核意见文件表VO
 *
 */
public class HTReviewFileRelationVO {


    /**
     *文件ID
     */
    private String fileId;

    /**
     *审核意见文件路径
     */
    private String reviewFilePath;

    /**
     *审核意见文件名称
     */
    private String reviewFileName;

    /**
     *业务ID(合同id、任务id)
     */
    private String businessId;
    /*-----------------------get/set--------------------------*/


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getReviewFilePath() {
        return reviewFilePath;
    }

    public void setReviewFilePath(String reviewFilePath) {
        this.reviewFilePath = reviewFilePath;
    }

    public String getReviewFileName() {
        return reviewFileName;
    }

    public void setReviewFileName(String reviewFileName) {
        this.reviewFileName = reviewFileName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
