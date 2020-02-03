package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

public class HTDictFileTypeVO extends BaseVO {
    /**
     * 文件类型ID
     */
    private String fileTypeId;
    /**
     * 文件类型名称
     */
    private String fileTypeName;
    /**
     * 所属部门
     */
    private String departmentId;
    /**
     * 咨询类别ID
     */
    private String counselTypeId;
    /*-------------------------get/set方法-----------------------------*/

    /**
     * fileTypeId
     *
     * @return fileTypeId 项目描述（略）
     */
    public String getFileTypeId() {
        return fileTypeId;
    }

    /**
     * fileTypeId
     *
     * @param fileTypeId 项目描述（略）
     */
    public void setFileTypeId(String fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    /**
     * fileTypeName
     *
     * @return fileTypeName 项目描述（略）
     */
    public String getFileTypeName() {
        return fileTypeName;
    }

    /**
     * fileTypeName
     *
     * @param fileTypeName 项目描述（略）
     */
    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    /**
     * departmentId
     *
     * @return departmentId 项目描述（略）
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * departmentId
     *
     * @param departmentId 项目描述（略）
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * counselTypeId
     *
     * @return counselTypeId 项目描述（略）
     */
    public String getCounselTypeId() {
        return counselTypeId;
    }

    /**
     * counselTypeId
     *
     * @param counselTypeId 项目描述（略）
     */
    public void setCounselTypeId(String counselTypeId) {
        this.counselTypeId = counselTypeId;
    }
}
