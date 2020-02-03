package com.hd.common.entity;

public class TbDictDepartmentEntity {
    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门ID
     * @return department_id 部门ID
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * 部门ID
     * @param departmentId 部门ID
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    /**
     * 部门名称
     * @return department_name 部门名称
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * 部门名称
     * @param departmentName 部门名称
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }
}