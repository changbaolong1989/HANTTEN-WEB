package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

/**
 * 任务和归档文件关联Bean
 *
 * @author jwl
 * Created in 2019/8/8 15:31
 */
public class HTTaskSaveMaterialBean extends BaseBean {

    /**
     * 关联ID
     */
    private String relationId;

    /**
     * 任务ID
     */
    private String taskId;

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
     * 是否可以删除（0：不可以  1：可以）
     */
    private String isCanDel;

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
     * isCanDel
     *
     * @return isCanDel 项目描述（略）
     */
    public String getIsCanDel() {
        return isCanDel;
    }

    /**
     * isCanDel
     *
     * @param isCanDel 项目描述（略）
     */
    public void setIsCanDel(String isCanDel) {
        this.isCanDel = isCanDel;
    }
}
