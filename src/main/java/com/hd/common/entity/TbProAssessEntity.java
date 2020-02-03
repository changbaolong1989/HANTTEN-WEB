package com.hd.common.entity;

import java.util.Date;

public class TbProAssessEntity {
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
     * 估算ID
     * @return assess_id 估算ID
     */
    public String getAssessId() {
        return assessId;
    }

    /**
     * 估算ID
     * @param assessId 估算ID
     */
    public void setAssessId(String assessId) {
        this.assessId = assessId == null ? null : assessId.trim();
    }

    /**
     * 项目ID
     * @return project_id 项目ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 项目ID
     * @param projectId 项目ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * 编制日期
     * @return compile_date 编制日期
     */
    public Date getCompileDate() {
        return compileDate;
    }

    /**
     * 编制日期
     * @param compileDate 编制日期
     */
    public void setCompileDate(Date compileDate) {
        this.compileDate = compileDate;
    }

    /**
     * 估算名称/版本
     * @return assess_name 估算名称/版本
     */
    public String getAssessName() {
        return assessName;
    }

    /**
     * 估算名称/版本
     * @param assessName 估算名称/版本
     */
    public void setAssessName(String assessName) {
        this.assessName = assessName == null ? null : assessName.trim();
    }

    /**
     * 建筑面积
     * @return floor_space 建筑面积
     */
    public Double getFloorSpace() {
        return floorSpace;
    }

    /**
     * 建筑面积
     * @param floorSpace 建筑面积
     */
    public void setFloorSpace(Double floorSpace) {
        this.floorSpace = floorSpace;
    }

    /**
     * 估算费用
     * @return estimated_cost 估算费用
     */
    public Double getEstimatedCost() {
        return estimatedCost;
    }

    /**
     * 估算费用
     * @param estimatedCost 估算费用
     */
    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    /**
     * 估算单方
     * @return estimation_ex_parte 估算单方
     */
    public Double getEstimationExParte() {
        return estimationExParte;
    }

    /**
     * 估算单方
     * @param estimationExParte 估算单方
     */
    public void setEstimationExParte(Double estimationExParte) {
        this.estimationExParte = estimationExParte;
    }

    /**
     * 费用范围
     * @return cost_range 费用范围
     */
    public String getCostRange() {
        return costRange;
    }

    /**
     * 费用范围
     * @param costRange 费用范围
     */
    public void setCostRange(String costRange) {
        this.costRange = costRange == null ? null : costRange.trim();
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 估算文件路径
     * @return assess_file_path 估算文件路径
     */
    public String getAssessFilePath() {
        return assessFilePath;
    }

    /**
     * 估算文件路径
     * @param assessFilePath 估算文件路径
     */
    public void setAssessFilePath(String assessFilePath) {
        this.assessFilePath = assessFilePath == null ? null : assessFilePath.trim();
    }

    /**
     * 估算文件真实路径
     * @return assess_file_name 估算文件真实路径
     */
    public String getAssessFileName() {
        return assessFileName;
    }

    /**
     * 估算文件真实路径
     * @param assessFileName 估算文件真实路径
     */
    public void setAssessFileName(String assessFileName) {
        this.assessFileName = assessFileName == null ? null : assessFileName.trim();
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