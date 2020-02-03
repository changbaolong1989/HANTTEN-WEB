package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

/**
 *
 */
public class HTWholeProcessEstimateVO extends BaseVO {

    /**
     * 概算ID
     */
    private String estimateId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 编制日期
     */
    private String compileDate;

    /**
     * 概算名称/版本
     */
    private String estimateName;

    /**
     * 建筑面积
     */
    private String floorSpace;

    /**
     * 概算费用
     */
    private String estimatedCost;

    /**
     * 概算单方
     */
    private String estimationExParte;

    /**
     * 费用范围
     */
    private String costRange;

    /**
     * 备注
     */
    private String remark;

    /**
     * 概算文件路径
     */
    private String estimateFilePath;

    /**
     * 概算文件真实路径
     */
    private String estimateFileName;



    /*-------------------------------get/set方法----------------------------------*/

    public String getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(String estimateId) {
        this.estimateId = estimateId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCompileDate() {
        return compileDate;
    }

    public void setCompileDate(String compileDate) {
        this.compileDate = compileDate;
    }

    public String getEstimateName() {
        return estimateName;
    }

    public void setEstimateName(String estimateName) {
        this.estimateName = estimateName;
    }

    public String getFloorSpace() {
        return floorSpace;
    }

    public void setFloorSpace(String floorSpace) {
        this.floorSpace = floorSpace;
    }

    public String getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(String estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getEstimationExParte() {
        return estimationExParte;
    }

    public void setEstimationExParte(String estimationExParte) {
        this.estimationExParte = estimationExParte;
    }

    public String getCostRange() {
        return costRange;
    }

    public void setCostRange(String costRange) {
        this.costRange = costRange;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEstimateFilePath() {
        return estimateFilePath;
    }

    public void setEstimateFilePath(String estimateFilePath) {
        this.estimateFilePath = estimateFilePath;
    }

    public String getEstimateFileName() {
        return estimateFileName;
    }

    public void setEstimateFileName(String estimateFileName) {
        this.estimateFileName = estimateFileName;
    }
}
