package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

public class HTUserQuestionRecordRelationVO extends BaseVO {

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
     * 扣分
     */
    private String grade;
    /*-----------------------get/set方法------------------------*/

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(String businessNum) {
        this.businessNum = businessNum;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
