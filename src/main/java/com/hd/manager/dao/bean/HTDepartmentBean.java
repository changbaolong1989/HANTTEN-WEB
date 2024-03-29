package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;


public class HTDepartmentBean extends BaseBean {

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     *部门名称
     */
    private String departmentName;

    /**
     *部门按钮状态
     */
    private String departmentBtnFlag;

    /**
     *层级
     */
    private String departmentLevel;

    /**
     *所属业务类型
     */
    private String bussinessType;

    /**
     *父级ID
     */
    private String parentDepartmentId;

    /**
     *父级名称
     */
    private String parentDepartmentName;

    /**
     *部门职位
     */
    private String departmentJob;

    /**
     * 用户ID
     */
    private String userId;


    /*----------------------get/set方法--------------------------------*/

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentLevel() {
        return departmentLevel;
    }

    public void setDepartmentLevel(String departmentLevel) {
        this.departmentLevel = departmentLevel;
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getParentDepartmentId() {
        return parentDepartmentId;
    }

    public void setParentDepartmentId(String parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }

    public String getParentDepartmentName() {
        return parentDepartmentName;
    }

    public void setParentDepartmentName(String parentDepartmentName) {
        this.parentDepartmentName = parentDepartmentName;
    }

    public String getDepartmentJob() {
        return departmentJob;
    }

    public void setDepartmentJob(String departmentJob) {
        this.departmentJob = departmentJob;
    }

    /**
     * userId
     *
     * @return userId 项目描述（略）
     */
    public String getUserId() {
        return userId;
    }

    /**
     * userId
     *
     * @param userId 项目描述（略）
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepartmentBtnFlag() {
        return departmentBtnFlag;
    }

    public void setDepartmentBtnFlag(String departmentBtnFlag) {
        this.departmentBtnFlag = departmentBtnFlag;
    }
}
