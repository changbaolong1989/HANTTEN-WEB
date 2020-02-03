package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.util.List;

public class HTRequestMenuHisTaskInstVO extends BaseVO {

    /**
     * 主键
     */
    private String historyId;

    /**
     *请款单ID
     */
    private String requestMenuId;

    /**
     *流程实例ID
     */
    private String processInstId;

    /**
     *任务Key
     */
    private String taskKey;

    /**
     *是否通过
     * 1：已通过
     * 0：未通过
     */
    private String isPass;

    /**
     *拒绝原因
     */
    private String rejectReason;

    /**
     *审批人
     */
    private String approveUserId;

    /**
     *审批人名称
     */
    private String approveUserName;

    /**
     *审批人签名文件路径
     */
    private String signPath;

    /**
     *审批时间
     */
    private String approveTime;

    /**
     * 子集
     */
    private List<HTRequestMenuHisTaskInstVO> htRequestMenuHisTaskInstList;

    /**
     *  审核记录和人员和问题类型
     */
    private List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList;


    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getRequestMenuId() {
        return requestMenuId;
    }

    public void setRequestMenuId(String requestMenuId) {
        this.requestMenuId = requestMenuId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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

    public String getSignPath() {
        return signPath;
    }

    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public List<HTRequestMenuHisTaskInstVO> getHtRequestMenuHisTaskInstList() {
        return htRequestMenuHisTaskInstList;
    }

    public void setHtRequestMenuHisTaskInstList(List<HTRequestMenuHisTaskInstVO> htRequestMenuHisTaskInstList) {
        this.htRequestMenuHisTaskInstList = htRequestMenuHisTaskInstList;
    }

    public List<HTUserQuestionRecordRelationVO> getHtUserQuestionRecordRelationVOList() {
        return htUserQuestionRecordRelationVOList;
    }

    public void setHtUserQuestionRecordRelationVOList(List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList) {
        this.htUserQuestionRecordRelationVOList = htUserQuestionRecordRelationVOList;
    }
}
