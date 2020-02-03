package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;
import com.hd.manager.dao.bean.HTDictQuestionBean;

import java.util.List;

/**
 * 错误类型VO
 *
 * @author jwl
 * Created in 2019/7/16 15:31
 */
public class HTDictQuestionVO extends BaseVO {

    /**
     * 问题ID
     */
    private String questionId;

    /**
     * 问题名称
     */
    private String questionName;

    /**
     * 扣分
     */
    private String grade;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 考核分类
     */
    private String examineType;

    /**
     * 关联id
     */
    private String relationId;

    /**
     * 记录id
     */
    private String recordId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 问题编号
     */
    private String questionNum;

    /**
     * 来源类型
     */
    private String sourceType;

    /**
     * 错误关联列表
     */
    private List<HTDictQuestionVO> htDictQuestionList;

    /**
     * 问题列表
     */
    private List<HTDictQuestionVO> questionList;

    /**
     * 类型id
     */
    private String examineTypeId;

    /**
     * 类型名称
     */
    private String examineTypeName;

    /**
     * examineTypeId
     *
     * @return examineTypeId 项目描述（略）
     */
    public String getExamineTypeId() {
        return examineTypeId;
    }

    /**
     * examineTypeId
     *
     * @param examineTypeId 项目描述（略）
     */
    public void setExamineTypeId(String examineTypeId) {
        this.examineTypeId = examineTypeId;
    }

    /**
     * examineTypeName
     *
     * @return examineTypeName 项目描述（略）
     */
    public String getExamineTypeName() {
        return examineTypeName;
    }

    /**
     * examineTypeName
     *
     * @param examineTypeName 项目描述（略）
     */
    public void setExamineTypeName(String examineTypeName) {
        this.examineTypeName = examineTypeName;
    }

    /**
     * questionList
     *
     * @return questionList 项目描述（略）
     */
    public List<HTDictQuestionVO> getQuestionList() {
        return questionList;
    }

    /**
     * questionList
     *
     * @param questionList 项目描述（略）
     */
    public void setQuestionList(List<HTDictQuestionVO> questionList) {
        this.questionList = questionList;
    }

    /**
     * htDictQuestionList
     *
     * @return htDictQuestionList 项目描述（略）
     */
    public List<HTDictQuestionVO> getHtDictQuestionList() {
        return htDictQuestionList;
    }

    /**
     * htDictQuestionList
     *
     * @param htDictQuestionList 项目描述（略）
     */
    public void setHtDictQuestionList(List<HTDictQuestionVO> htDictQuestionList) {
        this.htDictQuestionList = htDictQuestionList;
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
     * grade
     *
     * @return grade 项目描述（略）
     */
    public String getGrade() {
        return grade;
    }

    /**
     * grade
     *
     * @param grade 项目描述（略）
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * operateType
     *
     * @return operateType 项目描述（略）
     */
    public String getOperateType() {
        return operateType;
    }

    /**
     * operateType
     *
     * @param operateType 项目描述（略）
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
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
     * examineType
     *
     * @return examineType 项目描述（略）
     */
    public String getExamineType() {
        return examineType;
    }

    /**
     * examineType
     *
     * @param examineType 项目描述（略）
     */
    public void setExamineType(String examineType) {
        this.examineType = examineType;
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
}
