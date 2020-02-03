package com.hd.common.entity;

import java.util.Date;

public class TbJobEntity {
    /**
     * 职位ID
     */
    private String jobId;

    /**
     * 职位名称
     */
    private String jobName;

    /**
     * 所属部门ID
     */
    private String involvedDepartment;

    /**
     * 父级职位ID
     */
    private String partentJobId;

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
     * 职位级
     */
    private String jobLevel;

    /**
     * 
     */
    private String sort;

    /**
     * 职位ID
     * @return job_id 职位ID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 职位ID
     * @param jobId 职位ID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    /**
     * 职位名称
     * @return job_name 职位名称
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 职位名称
     * @param jobName 职位名称
     */
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    /**
     * 所属部门ID
     * @return involved_department 所属部门ID
     */
    public String getInvolvedDepartment() {
        return involvedDepartment;
    }

    /**
     * 所属部门ID
     * @param involvedDepartment 所属部门ID
     */
    public void setInvolvedDepartment(String involvedDepartment) {
        this.involvedDepartment = involvedDepartment == null ? null : involvedDepartment.trim();
    }

    /**
     * 父级职位ID
     * @return partent_job_id 父级职位ID
     */
    public String getPartentJobId() {
        return partentJobId;
    }

    /**
     * 父级职位ID
     * @param partentJobId 父级职位ID
     */
    public void setPartentJobId(String partentJobId) {
        this.partentJobId = partentJobId == null ? null : partentJobId.trim();
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
     * 职位级
     * @return job_level 职位级
     */
    public String getJobLevel() {
        return jobLevel;
    }

    /**
     * 职位级
     * @param jobLevel 职位级
     */
    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel == null ? null : jobLevel.trim();
    }

    /**
     * 
     * @return sort 
     */
    public String getSort() {
        return sort;
    }

    /**
     * 
     * @param sort 
     */
    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }
}