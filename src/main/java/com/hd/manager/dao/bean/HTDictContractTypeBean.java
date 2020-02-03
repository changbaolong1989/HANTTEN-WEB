package com.hd.manager.dao.bean;


import com.hd.base.bean.BaseBean;

/**
 * 合同类型字典实体
 */
public class HTDictContractTypeBean extends BaseBean {

    //合同类型ID
    private String contractTypeId;
    //合同类型名称
    private String contractTypeName;
    //部门ID
    private String departmentId;

/*---------------------------get/set----------------------------------*/

    public String getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(String contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
