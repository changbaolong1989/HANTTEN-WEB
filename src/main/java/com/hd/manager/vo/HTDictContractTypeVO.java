package com.hd.manager.vo;


import com.hd.base.vo.BaseVO;

/**
 * 合同类型字典实体
 */
public class HTDictContractTypeVO extends BaseVO {

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
