package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;
import com.hd.manager.vo.HTJobPowerRelationVO;

import java.util.List;

/**
 *用户所属职位实体bean
 */
public class HTJobBean extends BaseBean {

    /**
     * 职位ID
     */
    private String jobId ;

    /**
     *职位名称
     */
    private String jobName;

    /**
     * 部门-职位名称
     */
    private String departmentJobName;

    /**
     * 职位等级
     */
    private String jobLevel;

    /**
     * 所属部门
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
     *排序号
     */
    private String sort;

    /**
     *全部部门ID
     */
    private String parentIds;

    /**
     * 所属成员
     */
    private String userId;

    /**
     * 关联权限
     */
    private List<HTJobPowerRelationBean> htJobPowerRelationBeanList;

    /**
     * 关联权限名称集合
     */
    private String powerNames;




    /*----------------------------get/set-----------------------------*/

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getDepartmentJobName() {
        return departmentJobName;
    }

    public void setDepartmentJobName(String departmentJobName) {
        this.departmentJobName = departmentJobName;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getParentJobId() {
        return parentJobId;
    }

    public void setParentJobId(String parentJobId) {
        this.parentJobId = parentJobId;
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

    public List<HTJobPowerRelationBean> getHtJobPowerRelationBeanList() {
        return htJobPowerRelationBeanList;
    }

    public void setHtJobPowerRelationBeanList(List<HTJobPowerRelationBean> htJobPowerRelationBeanList) {
        this.htJobPowerRelationBeanList = htJobPowerRelationBeanList;
    }

    public String getPowerNames() {
        return powerNames;
    }

    public void setPowerNames(String powerNames) {
        this.powerNames = powerNames;
    }
}
