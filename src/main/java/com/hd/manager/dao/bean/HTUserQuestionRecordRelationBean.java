package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

public class HTUserQuestionRecordRelationBean extends BaseBean {

    /**
     * 唯一标识
     */
    private String relationId;

    /**
     * 审核记录ID
     */
    private String recordId;

    /**
     * 人员ID
     */
    private String userId;

    /**
     * 人员名称
     */
    private String userName;

    /**
     * 问题类型ID
     */
    private String questionId;

    /**
     * 问题名称
     */
    private String questionName;

    /**
     * 错误次数
     */
    private String questionNum;

    /**
     * 来源类型（0：合同 1：普通任务 2：投标任务 ）
     */
    private String sourceType;

    /**
     * 合同名称
     */
    private String businessName;

    /**
     * 合同编号
     */
    private String businessNum;

    /**
     * 审批时间
     */
    private Date approveTime;

    /**
     * 审批人
     */
    private String approveUserId;

    /**
     * 审批人名称
     */
    private String approveUserName;

    /**
     * 个人签名
     */
    private String signPath;

    /**
     * 扣分
     */
    private String grade;
    /*-----------------------get/set方法------------------------*/

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
     * recordId
     *
     * @return recordId 项目描述（略）
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * recordId
     *
     * @param recordId 项目描述（略）
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
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
     * questionId
     *
     * @return questionId 项目描述（略）
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * questionId
     *
     * @param questionId 项目描述（略）
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    /**
     * questionName
     *
     * @return questionName 项目描述（略）
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     * questionName
     *
     * @param questionName 项目描述（略）
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * questionNum
     *
     * @return questionNum 项目描述（略）
     */
    public String getQuestionNum() {
        return questionNum;
    }

    /**
     * questionNum
     *
     * @param questionNum 项目描述（略）
     */
    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    /**
     * sourceType
     *
     * @return sourceType 项目描述（略）
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * sourceType
     *
     * @param sourceType 项目描述（略）
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId;
    }

    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(String businessNum) {
        this.businessNum = businessNum;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getSignPath() {
        return signPath;
    }

    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }
}
