package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

public class HTWholeProcessEstimateBean extends BaseBean {

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
    private Date compileDate;

    /**
     * 概算名称/版本
     */
    private String estimateName;

    /**
     * 建筑面积
     */
    private Double floorSpace;

    /**
     * 概算费用
     */
    private Double estimatedCost;

    /**
     * 概算单方
     */
    private Double estimationExParte;

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



    /*------------------------get/set方法------------------------*/

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

    public Date getCompileDate() {
        return compileDate;
    }

    public void setCompileDate(Date compileDate) {
        this.compileDate = compileDate;
    }

    public String getEstimateName() {
        return estimateName;
    }

    public void setEstimateName(String estimateName) {
        this.estimateName = estimateName;
    }

    public Double getFloorSpace() {
        return floorSpace;
    }

    public void setFloorSpace(Double floorSpace) {
        this.floorSpace = floorSpace;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Double getEstimationExParte() {
        return estimationExParte;
    }

    public void setEstimationExParte(Double estimationExParte) {
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
