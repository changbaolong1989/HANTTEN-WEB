package com.hd.data.entity;

import java.util.Date;

public class OldDataJointRecord {
    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 
     */
    private Date createDate;

    /**
     * 
     */
    private String oldDeptId;

    /**
     * 
     */
    private String deptName;

    /**
     * 
     */
    private String oldProjectId;

    /**
     * 合同ID
     * @return contract_id 合同ID
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * 合同ID
     * @param contractId 合同ID
     */
    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    /**
     * 
     * @return create_date 
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 
     * @param createDate 
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 
     * @return old_dept_id 
     */
    public String getOldDeptId() {
        return oldDeptId;
    }

    /**
     * 
     * @param oldDeptId 
     */
    public void setOldDeptId(String oldDeptId) {
        this.oldDeptId = oldDeptId == null ? null : oldDeptId.trim();
    }

    /**
     * 
     * @return dept_name 
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 
     * @param deptName 
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    /**
     * 
     * @return old_project_id 
     */
    public String getOldProjectId() {
        return oldProjectId;
    }

    /**
     * 
     * @param oldProjectId 
     */
    public void setOldProjectId(String oldProjectId) {
        this.oldProjectId = oldProjectId == null ? null : oldProjectId.trim();
    }
}