package com.hd.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hd.manager.vo.HTUserVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * VO 基类
 * @author JLF
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseVO {

    /**
     * 当前页码
     */
    private String pageNumber;

    /**
     * 每页显示数据条数
     */
    private String pageSize;

    /**
     * 记录总条数
     */
    private String totalRecordCount;

    /**
     * 逻辑删除标识位
     */
    private String isDelFlg;

    /**
     * 创建用户编号
     */
    private String createUserId;

    /**
     * 创建日期
     */
    @DateTimeFormat
    private String createDate;

    /**
     * 更新用户编号
     */
    private String updateUserId;

    /**
     * 更新日期
     */
    @DateTimeFormat
    private String updateDate;

    /**
     * 当前登录 用户信息
     */
    private HTUserVO loginUserInfo;

    /**
     * 可以跳转至 修改页面 （0：不可以，1：可以）
     */
    private String canGotoModifyPage;

    /**
     * 可以跳转至 详情页面 （0：不可以，1：可以）
     */
    private String canGotoDetailPage;

    //----------------------------------------流程相关属性--------------------------------------------//

    /**
     * 主提审人ID
     */
    private String applyUserId;

    /**
     * 委托任务ID
     */
    private String delegateTaskId;

    /**
     * 委托任务Key
     */
    private String delegateTaskKey;

    /**
     * 是否可以撤回
     */
    private String isCanRecall;

    /**
     * 审核状态
     */
    private String verifyState;

    /**
     * 审批时间
     */
    @DateTimeFormat
    private String approveTime;

    /**
     * 审批人
     */
    private String approveUserId;
    private String approveUserName;

    /**
     * 提交节点
     */
    private String submitNode;

    /**
     * 流程当前状态
     */
    private String currentState;

    private String applyTime;

    //----------------------------------------流程相关属性--------------------------------------------//

    private String projectIsClosed;

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ OnlyOffice相关属性 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 文件ID(主键ID)
     */
    private String fileId;

    /**
     * 审核意见文件路径
     */
    private String reviewFilePath;

    /**
     * 审核意见文件路径
     */
    private String reviewFileName;

    /**
     * 业务ID(合同id、任务id、请款单id)
     */
    private String businessId;

    /**
     * onlyOffice 編輯的Key
     */
    private String onlyOfficeEditKey;

    /**
     *下载绝对路径(供onlyOffice使用)
     */
    private String onlyOfficeAbsDownLoadPath;

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ OnlyOffice相关属性 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /**
     * 预览类型
     */
    private String type;

    /**
     * 作废标识
     */
    private String isInvalidFlag;

    /**
     * type
     *
     * @return type 项目描述（略）
     */
    public String getType() {
        return type;
    }

    /**
     * type
     *
     * @param type 项目描述（略）
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * onlyOfficeEditKey
     *
     * @return onlyOfficeEditKey 项目描述（略）
     */
    public String getOnlyOfficeEditKey() {
        return onlyOfficeEditKey;
    }

    /**
     * onlyOfficeEditKey
     *
     * @param onlyOfficeEditKey 项目描述（略）
     */
    public void setOnlyOfficeEditKey(String onlyOfficeEditKey) {
        this.onlyOfficeEditKey = onlyOfficeEditKey;
    }

    /**
     * fileId
     *
     * @return fileId 项目描述（略）
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * fileId
     *
     * @param fileId 项目描述（略）
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * reviewFilePath
     *
     * @return reviewFilePath 项目描述（略）
     */
    public String getReviewFilePath() {
        return reviewFilePath;
    }

    /**
     * reviewFilePath
     *
     * @param reviewFilePath 项目描述（略）
     */
    public void setReviewFilePath(String reviewFilePath) {
        this.reviewFilePath = reviewFilePath;
    }

    /**
     * reviewFileName
     *
     * @return reviewFileName 项目描述（略）
     */
    public String getReviewFileName() {
        return reviewFileName;
    }

    /**
     * reviewFileName
     *
     * @param reviewFileName 项目描述（略）
     */
    public void setReviewFileName(String reviewFileName) {
        this.reviewFileName = reviewFileName;
    }

    /**
     * businessId
     *
     * @return businessId 项目描述（略）
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * businessId
     *
     * @param businessId 项目描述（略）
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * pageNumber
     *
     * @return pageNumber 项目描述（略）
     */
    public String getPageNumber() {
        return pageNumber;
    }

    /**
     * pageNumber
     *
     * @param pageNumber 项目描述（略）
     */
    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * pageSize
     *
     * @return pageSize 项目描述（略）
     */
    public String getPageSize() {
        return pageSize;
    }

    /**
     * pageSize
     *
     * @param pageSize 项目描述（略）
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * totalRecordCount
     *
     * @return totalRecordCount 项目描述（略）
     */
    public String getTotalRecordCount() {
        return totalRecordCount;
    }

    /**
     * totalRecordCount
     *
     * @param totalRecordCount 项目描述（略）
     */
    public void setTotalRecordCount(String totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }

    /**
     * isDelFlg
     *
     * @return isDelFlg 项目描述（略）
     */
    public String getIsDelFlg() {
        return isDelFlg;
    }

    /**
     * isDelFlg
     *
     * @param isDelFlg 项目描述（略）
     */
    public void setIsDelFlg(String isDelFlg) {
        this.isDelFlg = isDelFlg;
    }

    /**
     * createUserId
     *
     * @return createUserId 项目描述（略）
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * createUserId
     *
     * @param createUserId 项目描述（略）
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * createDate
     *
     * @return createDate 项目描述（略）
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * createDate
     *
     * @param createDate 项目描述（略）
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * updateUserId
     *
     * @return updateUserId 项目描述（略）
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * updateUserId
     *
     * @param updateUserId 项目描述（略）
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * updateDate
     *
     * @return updateDate 项目描述（略）
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * updateDate
     *
     * @param updateDate 项目描述（略）
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * canGotoModifyPage
     *
     * @return canGotoModifyPage 项目描述（略）
     */
    public String getCanGotoModifyPage() {
        return canGotoModifyPage;
    }

    /**
     * canGotoModifyPage
     *
     * @param canGotoModifyPage 项目描述（略）
     */
    public void setCanGotoModifyPage(String canGotoModifyPage) {
        this.canGotoModifyPage = canGotoModifyPage;
    }

    /**
     * canGotoDetailPage
     *
     * @return canGotoDetailPage 项目描述（略）
     */
    public String getCanGotoDetailPage() {
        return canGotoDetailPage;
    }

    /**
     * canGotoDetailPage
     *
     * @param canGotoDetailPage 项目描述（略）
     */
    public void setCanGotoDetailPage(String canGotoDetailPage) {
        this.canGotoDetailPage = canGotoDetailPage;
    }

    /**
     * loginUserInfo
     *
     * @return loginUserInfo 项目描述（略）
     */
    public HTUserVO getLoginUserInfo() {
        return loginUserInfo;
    }

    /**
     * loginUserInfo
     *
     * @param loginUserInfo 项目描述（略）
     */
    public void setLoginUserInfo(HTUserVO loginUserInfo) {
        this.loginUserInfo = loginUserInfo;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getDelegateTaskKey() {
        return delegateTaskKey;
    }

    public void setDelegateTaskKey(String delegateTaskKey) {
        this.delegateTaskKey = delegateTaskKey;
    }

    public String getIsCanRecall() {
        return isCanRecall;
    }

    public void setIsCanRecall(String isCanRecall) {
        this.isCanRecall = isCanRecall;
    }

    public String getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
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

    public String getSubmitNode() {
        return submitNode;
    }

    public void setSubmitNode(String submitNode) {
        this.submitNode = submitNode;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getProjectIsClosed() {
        return projectIsClosed;
    }

    public void setProjectIsClosed(String projectIsClosed) {
        this.projectIsClosed = projectIsClosed;
    }

    public String getDelegateTaskId() {
        return delegateTaskId;
    }

    public void setDelegateTaskId(String delegateTaskId) {
        this.delegateTaskId = delegateTaskId;
    }

    public String getOnlyOfficeAbsDownLoadPath() {
        return onlyOfficeAbsDownLoadPath;
    }

    public void setOnlyOfficeAbsDownLoadPath(String onlyOfficeAbsDownLoadPath) {
        this.onlyOfficeAbsDownLoadPath = onlyOfficeAbsDownLoadPath;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getIsInvalidFlag() {
        return isInvalidFlag;
    }

    public void setIsInvalidFlag(String isInvalidFlag) {
        this.isInvalidFlag = isInvalidFlag;
    }
}
