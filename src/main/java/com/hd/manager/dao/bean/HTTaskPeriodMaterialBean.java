package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

/**
 * 任务阶段文件Bean
 *
 * @author jwl
 * Created in 2019/7/29 15:31
 */
public class HTTaskPeriodMaterialBean extends BaseBean {

    /**
     * 关联ID
     */
    private String relationId;

    /**
     * 任务和阶段的关联ID
     */
    private String taskPeriodId;

    /**
     * 文件路径
     */
    private String materialPath;

    /**
     * 文件实际名称
     */
    private String materialName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 是否提审（0：否 1：是）
     */
    private String isReview;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 文件类型ID
     */
    private String fileTypeId;
    /**
     * 文件类型名称
     */
    private String fileTypeName;

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
     * taskId
     *
     * @return taskId 项目描述（略）
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * taskId
     *
     * @param taskId 项目描述（略）
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * userName
     *
     * @return userName 项目描述（略）
     */
    public String getUserName() {
        return userName;
    }

    /**
     * userName
     *
     * @param userName 项目描述（略）
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * relationId
     *
     * @return relationId 项目描述（略）
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * relationId
     *
     * @param relationId 项目描述（略）
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    /**
     * taskPeriodId
     *
     * @return taskPeriodId 项目描述（略）
     */
    public String getTaskPeriodId() {
        return taskPeriodId;
    }

    /**
     * taskPeriodId
     *
     * @param taskPeriodId 项目描述（略）
     */
    public void setTaskPeriodId(String taskPeriodId) {
        this.taskPeriodId = taskPeriodId;
    }

    /**
     * materialPath
     *
     * @return materialPath 项目描述（略）
     */
    public String getMaterialPath() {
        return materialPath;
    }

    /**
     * materialPath
     *
     * @param materialPath 项目描述（略）
     */
    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
    }

    /**
     * materialName
     *
     * @return materialName 项目描述（略）
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * materialName
     *
     * @param materialName 项目描述（略）
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    /**
     * fileType
     *
     * @return fileType 项目描述（略）
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * fileType
     *
     * @param fileType 项目描述（略）
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * isReview
     *
     * @return isReview 项目描述（略）
     */
    public String getIsReview() {
        return isReview;
    }

    /**
     * isReview
     *
     * @param isReview 项目描述（略）
     */
    public void setIsReview(String isReview) {
        this.isReview = isReview;
    }
}
