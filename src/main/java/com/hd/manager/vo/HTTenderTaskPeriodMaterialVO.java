package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

public class HTTenderTaskPeriodMaterialVO extends BaseVO {

    /**
     * 关联ID
     */
    private String relationId;

    /**
     * 投标任务和阶段的关联ID
     */
    private String tenderTaskPeriodId;

    /**
     * 投标任务ID
     */
    private String tenderTaskId;

    /**
     * 文件路径
     */
    private String materialPath;

    /**
     * 文件实际名称
     */
    private String materialName;

    /**
     * 创建人名称
     */
    private String userName;

    /**
     * 文件类型
     */
    private String fileTypeId;

    /**
     * 是否提审（0：否 1：是）
     */
    private String isReview;

    /**
     * 是否可删（0：否 1：是）
     */
    private String isCanDel;

    /**
     *阶段类型（0：普通阶段 1：收集资料 2：A3审核阶段  3：成果阶段）
     */
    private String periodType;

    /*-----------------------get/set方法--------------------------*/

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getTenderTaskPeriodId() {
        return tenderTaskPeriodId;
    }

    public void setTenderTaskPeriodId(String tenderTaskPeriodId) {
        this.tenderTaskPeriodId = tenderTaskPeriodId;
    }

    public String getMaterialPath() {
        return materialPath;
    }

    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(String fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public String getIsReview() {
        return isReview;
    }

    public void setIsReview(String isReview) {
        this.isReview = isReview;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTenderTaskId() {
        return tenderTaskId;
    }

    public void setTenderTaskId(String tenderTaskId) {
        this.tenderTaskId = tenderTaskId;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getIsCanDel() {
        return isCanDel;
    }

    public void setIsCanDel(String isCanDel) {
        this.isCanDel = isCanDel;
    }
}
