package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;


/**
 * 咨询类别Bean
 *
 * @author lh
 * Created in 2019/11/21 10:18
 */
public class HTDictProjectCounselTypeBean extends BaseBean {

    /**
     * 咨询类别ID
     */
    private String counselTypeId;

    /**
     * 咨询类别名称
     */
    private String counselTypeName;

    /**
     * 序号
     */
    private String sortNum;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;


    /*------------------------------get/set-----------------------------------*/

    public String getCounselTypeId() {
        return counselTypeId;
    }

    public void setCounselTypeId(String counselTypeId) {
        this.counselTypeId = counselTypeId;
    }

    public String getCounselTypeName() {
        return counselTypeName;
    }

    public void setCounselTypeName(String counselTypeName) {
        this.counselTypeName = counselTypeName;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

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
}
