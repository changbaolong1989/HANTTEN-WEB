package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

public class HTUserFileRelationVO extends BaseVO {

    /*
     *文件关联ID
     */
    private String fileRelationId;

    /*
     *文件路径
     */
    private String fileUrl;

    public String getFileRelationId() {
        return fileRelationId;
    }

    public void setFileRelationId(String fileRelationId) {
        this.fileRelationId = fileRelationId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
