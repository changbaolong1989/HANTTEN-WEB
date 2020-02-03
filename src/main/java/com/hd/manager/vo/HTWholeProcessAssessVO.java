package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

/**
 *
 */
public class HTWholeProcessAssessVO extends BaseVO {

    /**
     * 估算ID
     */
    private String assessId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 编制日期
     */
    private String compileDate;

    /**
     * 估算名称/版本
     */
    private String assessName;

    /**
     * 建筑面积
     */
    private String floorSpace;

    /**
     * 估算费用
     */
    private String estimatedCost;

    /**
     * 估算单方
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
     * 估算文件路径
     */
    private String assessFilePath;

    /**
     * 估算文件真实路径
     */
    private String assessFileName;



    /*-------------------------------get/set方法----------------------------------*/
    public String getAssessId() {
        return assessId;
    }

    public void setAssessId(String assessId) {
        this.assessId = assessId;
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

    public String getAssessName() {
        return assessName;
    }

    public void setAssessName(String assessName) {
        this.assessName = assessName;
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

    public String getAssessFilePath() {
        return assessFilePath;
    }

    public void setAssessFilePath(String assessFilePath) {
        this.assessFilePath = assessFilePath;
    }

    public String getAssessFileName() {
        return assessFileName;
    }

    public void setAssessFileName(String assessFileName) {
        this.assessFileName = assessFileName;
    }
}
