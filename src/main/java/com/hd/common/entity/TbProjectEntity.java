package com.hd.common.entity;

import java.util.Date;

public class TbProjectEntity {
    /**
     * 
     */
    private String projectId;

    /**
     * 
     */
    private String projectNum;

    /**
     * 
     */
    private String projectName;

    /**
     * 
     */
    private String companyId;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 项目结束时间
     */
    private Date endDate;

    /**
     * 项目结束人
     */
    private String endProjectUserId;

    /**
     * 关闭备注
     */
    private String closeRemark;

    /**
     * 项目状态(0.未完成 1.已完成(项目关闭也视为已完成))
     */
    private String projectSts;

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
     * 咨询类型阶段
     */
    private String counselTypePeriod;

    /**
     * 作废标识
     */
    private String isInvalidFlag;

    /**
     * 作废理由
     */
    private String invalidReason;

    /**
     * 
     * @return project_id 
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 
     * @param projectId 
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * 
     * @return project_num 
     */
    public String getProjectNum() {
        return projectNum;
    }

    /**
     * 
     * @param projectNum 
     */
    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum == null ? null : projectNum.trim();
    }

    /**
     * 
     * @return project_name 
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 
     * @param projectName 
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * 
     * @return company_id 
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 
     * @param companyId 
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    /**
     * 开始时间
     * @return start_date 开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 开始时间
     * @param startDate 开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 项目结束时间
     * @return end_date 项目结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 项目结束时间
     * @param endDate 项目结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 项目结束人
     * @return end_project_user_id 项目结束人
     */
    public String getEndProjectUserId() {
        return endProjectUserId;
    }

    /**
     * 项目结束人
     * @param endProjectUserId 项目结束人
     */
    public void setEndProjectUserId(String endProjectUserId) {
        this.endProjectUserId = endProjectUserId == null ? null : endProjectUserId.trim();
    }

    /**
     * 关闭备注
     * @return close_remark 关闭备注
     */
    public String getCloseRemark() {
        return closeRemark;
    }

    /**
     * 关闭备注
     * @param closeRemark 关闭备注
     */
    public void setCloseRemark(String closeRemark) {
        this.closeRemark = closeRemark == null ? null : closeRemark.trim();
    }

    /**
     * 项目状态(0.未完成 1.已完成(项目关闭也视为已完成))
     * @return project_sts 项目状态(0.未完成 1.已完成(项目关闭也视为已完成))
     */
    public String getProjectSts() {
        return projectSts;
    }

    /**
     * 项目状态(0.未完成 1.已完成(项目关闭也视为已完成))
     * @param projectSts 项目状态(0.未完成 1.已完成(项目关闭也视为已完成))
     */
    public void setProjectSts(String projectSts) {
        this.projectSts = projectSts == null ? null : projectSts.trim();
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

    /**
     * 咨询类型阶段
     * @return counsel_type_period 咨询类型阶段
     */
    public String getCounselTypePeriod() {
        return counselTypePeriod;
    }

    /**
     * 咨询类型阶段
     * @param counselTypePeriod 咨询类型阶段
     */
    public void setCounselTypePeriod(String counselTypePeriod) {
        this.counselTypePeriod = counselTypePeriod == null ? null : counselTypePeriod.trim();
    }

    /**
     * 作废标识
     * @return is_invalid_flag 作废标识
     */
    public String getIsInvalidFlag() {
        return isInvalidFlag;
    }

    /**
     * 作废标识
     * @param isInvalidFlag 作废标识
     */
    public void setIsInvalidFlag(String isInvalidFlag) {
        this.isInvalidFlag = isInvalidFlag == null ? null : isInvalidFlag.trim();
    }

    /**
     * 作废理由
     * @return invalid_reason 作废理由
     */
    public String getInvalidReason() {
        return invalidReason;
    }

    /**
     * 作废理由
     * @param invalidReason 作废理由
     */
    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason == null ? null : invalidReason.trim();
    }
}