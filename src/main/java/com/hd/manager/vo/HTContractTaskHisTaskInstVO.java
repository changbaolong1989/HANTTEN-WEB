package com.hd.manager.vo;

import java.util.List;

public class HTContractTaskHisTaskInstVO {

    /**
     * 唯一标识
     */
    private String historyId;

    /**
     * 合同任务ID
     */
    private String contractTaskId;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 任务Key
     */
    private String taskKey;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 是否通过
     * 1：已通过
     * 0：未通过
     */
    private String isPass;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 审批人
     */
    private String approveUserId;

    /**
     * 审批人名称
     */
    private String approveUserName;

    /**
     * 审批时间
     */
    private String approveTime;

    /**
     *  投标任务审核记录
     */
    private List<HTContractTaskHisTaskInstVO> HTContractTaskHisTaskInstVOList;

    /**
     *  审核记录和人员和问题类型
     */
    private List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList;

    /*--------------------get/set方法------------------------*/

    /**
     * historyId
     *
     * @return historyId 项目描述（略）
     */
    public String getHistoryId() {
        return historyId;
    }

    /**
     * historyId
     *
     * @param historyId 项目描述（略）
     */
    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    /**
     * contractTaskId
     *
     * @return contractTaskId 项目描述（略）
     */
    public String getContractTaskId() {
        return contractTaskId;
    }

    /**
     * contractTaskId
     *
     * @param contractTaskId 项目描述（略）
     */
    public void setContractTaskId(String contractTaskId) {
        this.contractTaskId = contractTaskId;
    }

    /**
     * processInstId
     *
     * @return processInstId 项目描述（略）
     */
    public String getProcessInstId() {
        return processInstId;
    }

    /**
     * processInstId
     *
     * @param processInstId 项目描述（略）
     */
    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    /**
     * taskKey
     *
     * @return taskKey 项目描述（略）
     */
    public String getTaskKey() {
        return taskKey;
    }

    /**
     * taskKey
     *
     * @param taskKey 项目描述（略）
     */
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
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
     * isPass
     *
     * @return isPass 项目描述（略）
     */
    public String getIsPass() {
        return isPass;
    }

    /**
     * isPass
     *
     * @param isPass 项目描述（略）
     */
    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    /**
     * rejectReason
     *
     * @return rejectReason 项目描述（略）
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * rejectReason
     *
     * @param rejectReason 项目描述（略）
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    /**
     * approveUserId
     *
     * @return approveUserId 项目描述（略）
     */
    public String getApproveUserId() {
        return approveUserId;
    }

    /**
     * approveUserId
     *
     * @param approveUserId 项目描述（略）
     */
    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId;
    }

    /**
     * approveUserName
     *
     * @return approveUserName 项目描述（略）
     */
    public String getApproveUserName() {
        return approveUserName;
    }

    /**
     * approveUserName
     *
     * @param approveUserName 项目描述（略）
     */
    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    /**
     * approveTime
     *
     * @return approveTime 项目描述（略）
     */
    public String getApproveTime() {
        return approveTime;
    }

    /**
     * approveTime
     *
     * @param approveTime 项目描述（略）
     */
    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * HTContractTaskHisTaskInstVOList
     *
     * @return HTContractTaskHisTaskInstVOList 项目描述（略）
     */
    public List<HTContractTaskHisTaskInstVO> getHTContractTaskHisTaskInstVOList() {
        return HTContractTaskHisTaskInstVOList;
    }

    /**
     * HTContractTaskHisTaskInstVOList
     *
     * @param HTContractTaskHisTaskInstVOList 项目描述（略）
     */
    public void setHTContractTaskHisTaskInstVOList(List<HTContractTaskHisTaskInstVO> HTContractTaskHisTaskInstVOList) {
        this.HTContractTaskHisTaskInstVOList = HTContractTaskHisTaskInstVOList;
    }

    /**
     * htUserQuestionRecordRelationVOList
     *
     * @return htUserQuestionRecordRelationVOList 项目描述（略）
     */
    public List<HTUserQuestionRecordRelationVO> getHtUserQuestionRecordRelationVOList() {
        return htUserQuestionRecordRelationVOList;
    }

    /**
     * htUserQuestionRecordRelationVOList
     *
     * @param htUserQuestionRecordRelationVOList 项目描述（略）
     */
    public void setHtUserQuestionRecordRelationVOList(List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList) {
        this.htUserQuestionRecordRelationVOList = htUserQuestionRecordRelationVOList;
    }
}
