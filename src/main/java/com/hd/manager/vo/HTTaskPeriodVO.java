package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.io.Serializable;
import java.util.List;

public class HTTaskPeriodVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -5794318791561175949L;
    /**
     * 阶段ID
     */
    private String periodId;

    /**
     *父级阶段ID
     */
    private String parentPeriodId;

    /**
     *阶段名称
     */
    private String periodName;

    /**
     *合同类型
     */
    private String contractTypeId;

    /**
     *咨询类型
     */
    private String counselTypeId;

    /**
     *部门ID
     */
    private String dictDepartmentId;

    /**
     * 提审时间
     */
    private String applyTime;

    /**
     *级别
     */
    private String periodLevel;

    /**
     *工作内容说明
     */
    private String periodDesc;

    /**
     *阶段排序
     */
    private String sort;

    /**
     *页面展示内容(A1,A2,A3...A12)
     */
    private String pageContent;

    /**
     *部门类型
     */
    private String departmentName;

    /**
     * 任务阶段关联id
     */
    private String relationId;

    /**
     * 关联文件主键ID
     */
    private List<String> materialIds;

    /**
     * 审批状态
     */
    private String isPass;

    /**
     * 拒绝理由
     */
    private String rejectReason;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务阶段id
     */
    private String taskPeriodId;

    /**
     * 任务Key
     */
    private String taskKey;

    /**
     * 历史id(主键)
     */
    private String historyId;

    /**
     *  任务审核记录
     */
    private List<HTTaskPeriodVO> htTaskPeriodVOList;

    /**
     *  审核记录和人员和问题类型
     */
    private List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 任务流程实例ID
     */
    private String taskProcessInstId;

    /**
     * 提审人
     */
    private String applyUserName;

    /**
     * 流程完成时间
     */
    private String completeTime;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 是否是主负责人
     */
    private String isMainDuty;

    /**
     *  人员列表
     */
    private List<HTTaskPeriodVO> userList;

    /**
     * 档案编号
     */
    private String fileNumber;

    /**
     * 阶段类型
     */
    private String periodType;

    /**
     * 重新编辑申请id
     */
    private String taskReeditId;

    /**
     * 是否提交了重新编辑申请
     */
    private String isSubmitReedit;

    /**
     * 签名路径
     */
    private String signPath;

    /**
     * 是否忽略
     */
    private String isIgnore;

    /**
     * 是否完成
     */
    private String isFinish;

    /**
     * isFinish
     *
     * @return isFinish 项目描述（略）
     */
    public String getIsFinish() {
        return isFinish;
    }

    /**
     * isFinish
     *
     * @param isFinish 项目描述（略）
     */
    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    /**
     * isIgnore
     *
     * @return isIgnore 项目描述（略）
     */
    public String getIsIgnore() {
        return isIgnore;
    }

    /**
     * isIgnore
     *
     * @param isIgnore 项目描述（略）
     */
    public void setIsIgnore(String isIgnore) {
        this.isIgnore = isIgnore;
    }

    /**
     * signPath
     *
     * @return signPath 项目描述（略）
     */
    public String getSignPath() {
        return signPath;
    }

    /**
     * signPath
     *
     * @param signPath 项目描述（略）
     */
    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }

    /**
     * userList
     *
     * @return userList 项目描述（略）
     */
    public List<HTTaskPeriodVO> getUserList() {
        return userList;
    }

    /**
     * userList
     *
     * @param userList 项目描述（略）
     */
    public void setUserList(List<HTTaskPeriodVO> userList) {
        this.userList = userList;
    }

    /**
     * taskReeditId
     *
     * @return taskReeditId 项目描述（略）
     */
    public String getTaskReeditId() {
        return taskReeditId;
    }

    /**
     * taskReeditId
     *
     * @param taskReeditId 项目描述（略）
     */
    public void setTaskReeditId(String taskReeditId) {
        this.taskReeditId = taskReeditId;
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
     * serialVersionUID
     *
     * @return serialVersionUID 项目描述（略）
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * fileNumber
     *
     * @return fileNumber 项目描述（略）
     */
    public String getFileNumber() {
        return fileNumber;
    }

    /**
     * fileNumber
     *
     * @param fileNumber 项目描述（略）
     */
    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
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
     * isMainDuty
     *
     * @return isMainDuty 项目描述（略）
     */
    public String getIsMainDuty() {
        return isMainDuty;
    }

    /**
     * isMainDuty
     *
     * @param isMainDuty 项目描述（略）
     */
    public void setIsMainDuty(String isMainDuty) {
        this.isMainDuty = isMainDuty;
    }

    /**
     * completeTime
     *
     * @return completeTime 项目描述（略）
     */
    public String getCompleteTime() {
        return completeTime;
    }

    /**
     * completeTime
     *
     * @param completeTime 项目描述（略）
     */
    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
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
     * applyUserName
     *
     * @return applyUserName 项目描述（略）
     */
    public String getApplyUserName() {
        return applyUserName;
    }

    /**
     * applyUserName
     *
     * @param applyUserName 项目描述（略）
     */
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    /**
     * htTaskPeriodVOList
     *
     * @return htTaskPeriodVOList 项目描述（略）
     */
    public List<HTTaskPeriodVO> getHtTaskPeriodVOList() {
        return htTaskPeriodVOList;
    }

    /**
     * htTaskPeriodVOList
     *
     * @param htTaskPeriodVOList 项目描述（略）
     */
    public void setHtTaskPeriodVOList(List<HTTaskPeriodVO> htTaskPeriodVOList) {
        this.htTaskPeriodVOList = htTaskPeriodVOList;
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
     * 审核记录和人员和问题类型关联
     */
    List<HTUserQuestionRecordRelationVO> userQuestionRecordRelationList;

    /**
     * userQuestionRecordRelationList
     *
     * @return userQuestionRecordRelationList 项目描述（略）
     */
    public List<HTUserQuestionRecordRelationVO> getUserQuestionRecordRelationList() {
        return userQuestionRecordRelationList;
    }

    /**
     * userQuestionRecordRelationList
     *
     * @param userQuestionRecordRelationList 项目描述（略）
     */
    public void setUserQuestionRecordRelationList(List<HTUserQuestionRecordRelationVO> userQuestionRecordRelationList) {
        this.userQuestionRecordRelationList = userQuestionRecordRelationList;
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
     * materialIds
     *
     * @return materialIds 项目描述（略）
     */
    public List<String> getMaterialIds() {
        return materialIds;
    }

    /**
     * materialIds
     *
     * @param materialIds 项目描述（略）
     */
    public void setMaterialIds(List<String> materialIds) {
        this.materialIds = materialIds;
    }

    /**
     * periodId
     *
     * @return periodId 项目描述（略）
     */
    public String getPeriodId() {
        return periodId;
    }

    /**
     * periodId
     *
     * @param periodId 项目描述（略）
     */
    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    /**
     * parentPeriodId
     *
     * @return parentPeriodId 项目描述（略）
     */
    public String getParentPeriodId() {
        return parentPeriodId;
    }

    /**
     * parentPeriodId
     *
     * @param parentPeriodId 项目描述（略）
     */
    public void setParentPeriodId(String parentPeriodId) {
        this.parentPeriodId = parentPeriodId;
    }

    /**
     * periodName
     *
     * @return periodName 项目描述（略）
     */
    public String getPeriodName() {
        return periodName;
    }

    /**
     * periodName
     *
     * @param periodName 项目描述（略）
     */
    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    /**
     * contractTypeId
     *
     * @return contractTypeId 项目描述（略）
     */
    public String getContractTypeId() {
        return contractTypeId;
    }

    /**
     * contractTypeId
     *
     * @param contractTypeId 项目描述（略）
     */
    public void setContractTypeId(String contractTypeId) {
        this.contractTypeId = contractTypeId;
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

    /**
     * dictDepartmentId
     *
     * @return dictDepartmentId 项目描述（略）
     */
    public String getDictDepartmentId() {
        return dictDepartmentId;
    }

    /**
     * dictDepartmentId
     *
     * @param dictDepartmentId 项目描述（略）
     */
    public void setDictDepartmentId(String dictDepartmentId) {
        this.dictDepartmentId = dictDepartmentId;
    }

    /**
     * periodLevel
     *
     * @return periodLevel 项目描述（略）
     */
    public String getPeriodLevel() {
        return periodLevel;
    }

    /**
     * periodLevel
     *
     * @param periodLevel 项目描述（略）
     */
    public void setPeriodLevel(String periodLevel) {
        this.periodLevel = periodLevel;
    }

    /**
     * periodDesc
     *
     * @return periodDesc 项目描述（略）
     */
    public String getPeriodDesc() {
        return periodDesc;
    }

    /**
     * periodDesc
     *
     * @param periodDesc 项目描述（略）
     */
    public void setPeriodDesc(String periodDesc) {
        this.periodDesc = periodDesc;
    }

    /**
     * sort
     *
     * @return sort 项目描述（略）
     */
    public String getSort() {
        return sort;
    }

    /**
     * sort
     *
     * @param sort 项目描述（略）
     */
    public void setSort(String sort) {
        this.sort = sort;
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
     * departmentName
     *
     * @return departmentName 项目描述（略）
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * departmentName
     *
     * @param departmentName 项目描述（略）
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getTaskProcessInstId() {
        return taskProcessInstId;
    }

    public void setTaskProcessInstId(String taskProcessInstId) {
        this.taskProcessInstId = taskProcessInstId;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getIsSubmitReedit() {
        return isSubmitReedit;
    }

    public void setIsSubmitReedit(String isSubmitReedit) {
        this.isSubmitReedit = isSubmitReedit;
    }
}
