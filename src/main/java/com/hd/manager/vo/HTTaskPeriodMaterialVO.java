package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

/**
 * 任务阶段文件VO
 *
 * @author jwl
 * Created in 2019/7/29 15:31
 */
public class HTTaskPeriodMaterialVO extends BaseVO {

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
     * 是否可以删除（0：不可以  1：可以）
     */
    private String isCanDel;

    /**
     * 成果文件阶段
     */
    private String periodType;

    /**
     * A3阶段
     */
    private String pageContent;

    /**
     * 备注
     */
    private String remark;

    /**
     * 附件/复印件
     */
    private String copyFile;

    /**
     * 份数
     */
    private String num;

    /**
     * 接收人
     */
    private String recipient;

    /**
     * 接收时间
     */
    private String recipientDate;

    /**
     * copyFile
     *
     * @return copyFile 项目描述（略）
     */
    public String getCopyFile() {
        return copyFile;
    }

    /**
     * copyFile
     *
     * @param copyFile 项目描述（略）
     */
    public void setCopyFile(String copyFile) {
        this.copyFile = copyFile;
    }

    /**
     * num
     *
     * @return num 项目描述（略）
     */
    public String getNum() {
        return num;
    }

    /**
     * num
     *
     * @param num 项目描述（略）
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * recipient
     *
     * @return recipient 项目描述（略）
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * recipient
     *
     * @param recipient 项目描述（略）
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * recipientDate
     *
     * @return recipientDate 项目描述（略）
     */
    public String getRecipientDate() {
        return recipientDate;
    }

    /**
     * recipientDate
     *
     * @param recipientDate 项目描述（略）
     */
    public void setRecipientDate(String recipientDate) {
        this.recipientDate = recipientDate;
    }

    /**
     * remark
     *
     * @return remark 项目描述（略）
     */
    public String getRemark() {
        return remark;
    }

    /**
     * remark
     *
     * @param remark 项目描述（略）
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * periodType
     *
     * @return periodType 项目描述（略）
     */
    public String getPeriodType() {
        return periodType;
    }

    /**
     * periodType
     *
     * @param periodType 项目描述（略）
     */
    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    /**
     * pageContent
     *
     * @return pageContent 项目描述（略）
     */
    public String getPageContent() {
        return pageContent;
    }

    /**
     * pageContent
     *
     * @param pageContent 项目描述（略）
     */
    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
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
