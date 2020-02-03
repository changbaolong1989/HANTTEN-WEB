package com.hd.common.entity;

import java.util.Date;

public class TbProEstimateEntity {
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
     * 
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
     * 概算文件
     */
    private String estimateFilePath;

    /**
     * 概算文件真实名称
     */
    private String estimateFileName;

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
     * 概算ID
     * @return estimate_id 概算ID
     */
    public String getEstimateId() {
        return estimateId;
    }

    /**
     * 概算ID
     * @param estimateId 概算ID
     */
    public void setEstimateId(String estimateId) {
        this.estimateId = estimateId == null ? null : estimateId.trim();
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
     * 概算名称/版本
     * @return estimate_name 概算名称/版本
     */
    public String getEstimateName() {
        return estimateName;
    }

    /**
     * 概算名称/版本
     * @param estimateName 概算名称/版本
     */
    public void setEstimateName(String estimateName) {
        this.estimateName = estimateName == null ? null : estimateName.trim();
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
     * 
     * @return estimated_cost 
     */
    public Double getEstimatedCost() {
        return estimatedCost;
    }

    /**
     * 
     * @param estimatedCost 
     */
    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    /**
     * 概算单方
     * @return estimation_ex_parte 概算单方
     */
    public Double getEstimationExParte() {
        return estimationExParte;
    }

    /**
     * 概算单方
     * @param estimationExParte 概算单方
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
     * 概算文件
     * @return estimate_file_path 概算文件
     */
    public String getEstimateFilePath() {
        return estimateFilePath;
    }

    /**
     * 概算文件
     * @param estimateFilePath 概算文件
     */
    public void setEstimateFilePath(String estimateFilePath) {
        this.estimateFilePath = estimateFilePath == null ? null : estimateFilePath.trim();
    }

    /**
     * 概算文件真实名称
     * @return estimate_file_name 概算文件真实名称
     */
    public String getEstimateFileName() {
        return estimateFileName;
    }

    /**
     * 概算文件真实名称
     * @param estimateFileName 概算文件真实名称
     */
    public void setEstimateFileName(String estimateFileName) {
        this.estimateFileName = estimateFileName == null ? null : estimateFileName.trim();
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