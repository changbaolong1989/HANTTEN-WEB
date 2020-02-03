package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.util.List;

public class HTJobVO extends BaseVO {

    /**
     * 职位ID
     */
    private String jobId;

    /**
     *职位名称
     */
    private String jobName;

    /**
     *所属部门ID
     */
    private String departmentId;

    /**
     *所属部门名称
     */
    private String departmentName;

    /**
     *父级职位ID
     */
    private String parentJobId;

    /**
     *职位级
     */
    private String jobLevel;

    /**
     *排序号
     */
    private String sort;

    /**
     *全部部门ID
     */
    private String parentIds;

    /**
     * 关联权限
     */
    private List<HTJobPowerRelationVO> htJobPowerRelationVOList;

    /**
     * 关联权限名称集合
     */
    private String powerNames;



    /*-----------------------get/set方法------------------------------*/
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getParentJobId() {
        return parentJobId;
    }

    public void setParentJobId(String parentJobId) {
        this.parentJobId = parentJobId;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<HTJobPowerRelationVO> getHtJobPowerRelationVOList() {
        return htJobPowerRelationVOList;
    }

    public void setHtJobPowerRelationVOList(List<HTJobPowerRelationVO> htJobPowerRelationVOList) {
        this.htJobPowerRelationVOList = htJobPowerRelationVOList;
    }

    public String getPowerNames() {
        return powerNames;
    }

    public void setPowerNames(String powerNames) {
        this.powerNames = powerNames;
    }
}
