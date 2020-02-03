package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.io.Serializable;
import java.util.List;

public class HTTenderTaskVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -7673358736315824106L;

    /**
     * 投标任务ID
     */
    private String tenderTaskId;

    /**
     * 投标任务名称
     */
    private String tenderTaskName;

    /**
     * 投标任务概况
     */
    private String tenderTaskContent;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 主提审人NAME
     */
    private String applyUserName;


    /**
     * 审批时间
     */
    private String completeTime;


    /**
     * 投标任务序号
     */
    private String sortNum;
    /**
     * 投标任务状态(0：未开标 1；未中标 2：已中标)
     */
    private String tenderTaskSts;
    /**
     * 开标时间
     */
    private String startDate;

    private List<HTTenderTaskUserVO> userList;

    /**
     * 部门Id
     */
    private String departmentId;

    /**
     * 成员名称
     */
    private String userName;

    /**
     * 主负责人ID
     */
    private String mainLeaderId;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 是否通过
     * 1：已通过
     * 0：未通过
     */
    private String isPass;

    /**
     * 审核记录和人员和问题类型关联
     */
    private List<HTUserQuestionRecordRelationVO> userQuestionRecordRelationList;

    /**
     * 阶段类型（0：普通阶段 1：收集资料 2：A3审核阶段  3：成果阶段）
     */
    private String periodType;


    public String getTenderTaskId() {
        return tenderTaskId;
    }

    public void setTenderTaskId(String tenderTaskId) {
        this.tenderTaskId = tenderTaskId;
    }

    public String getTenderTaskName() {
        return tenderTaskName;
    }

    public void setTenderTaskName(String tenderTaskName) {
        this.tenderTaskName = tenderTaskName;
    }

    public String getTenderTaskContent() {
        return tenderTaskContent;
    }

    public void setTenderTaskContent(String tenderTaskContent) {
        this.tenderTaskContent = tenderTaskContent;
    }

    public String getTenderTaskSts() {
        return tenderTaskSts;
    }

    public void setTenderTaskSts(String tenderTaskSts) {
        this.tenderTaskSts = tenderTaskSts;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public List<HTTenderTaskUserVO> getUserList() {
        return userList;
    }

    public void setUserList(List<HTTenderTaskUserVO> userList) {
        this.userList = userList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public String getMainLeaderId() {
        return mainLeaderId;
    }

    public void setMainLeaderId(String mainLeaderId) {
        this.mainLeaderId = mainLeaderId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public List<HTUserQuestionRecordRelationVO> getUserQuestionRecordRelationList() {
        return userQuestionRecordRelationList;
    }

    public void setUserQuestionRecordRelationList(List<HTUserQuestionRecordRelationVO> userQuestionRecordRelationList) {
        this.userQuestionRecordRelationList = userQuestionRecordRelationList;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}
