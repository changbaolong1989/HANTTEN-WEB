package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

public class HTWholeProcessAssessBean extends BaseBean {

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
    private Date compileDate;

    /**
     * 估算名称/版本
     */
    private String assessName;

    /**
     * 建筑面积
     */
    private Double floorSpace;

    /**
     * 估算费用
     */
    private Double estimatedCost;

    /**
     * 估算单方
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
     * 估算文件路径
     */
    private String assessFilePath;

    /**
     * 估算文件真实路径
     */
    private String assessFileName;



    /*------------------------get/set方法------------------------*/
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

    public Date getCompileDate() {
        return compileDate;
    }

    public void setCompileDate(Date compileDate) {
        this.compileDate = compileDate;
    }

    public String getAssessName() {
        return assessName;
    }

    public void setAssessName(String assessName) {
        this.assessName = assessName;
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
