package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

/**
 * 审核意见文件表Bean
 */
public class HTReviewFileRelationBean extends BaseBean {

    /**
     *
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
}
