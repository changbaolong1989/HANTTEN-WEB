package com.hd.manager.vo;

import java.util.List;
import java.util.Map;


/**
 * ElementTree通用回传VO
 *
 * @author LH
 * Created in 2019/7/13 13:00
 */
public class ElementTreeVO {
    /**
     * 唯一标识
     */
    private String id;
    /**
     * 展示名称
     */
    private String label;

    /**
     * 数据类别(用以区分在同一层级下需要展示不同的信息时)
     */
    private String objType;

    /**
     * 单元格展示样式标识(excel导出)
     */
    private String cellStyle;

    /**
     * 单元格展示宽高(excel导出)
     */
    private String cellWidthHeight;

    /**
     * 单元格起始行数(excel导出)
     */
    private String startRowNum;

    /**
     * 合并单元格行规格(单元格占多少行)(excel导出)
     */
    private String cellRows;

    /**
     * 单元格起始列数(excel导出)
     */
    private String startColumnNum;

    /**
     * 合并单元格列规格(单元格占多少列)(excel导出)
     */
    private String cellColumns;

    /**
     * 菜单展示Flag(0:不展示菜单/1:展示菜单)
     */
    private String showTool;

    /**
     * 子集
     */
    private List<ElementTreeVO> children;

    /**
     * 任务概述
     */
    private String taskContent;

    /**
     * 开标时间
     */
    private String startDate;

    /**
     * 工作内容说明
     */
    private String periodDesc;

    /**
     * 阶段类型
     */
    private String periodType;

    /**
     * 当前节点等级
     */
    private String treeNodeLevel;

    /**
     * 是否可以撤回（0：否 1：是）
     */
    private String isCanRecall;

    /**
     * 页面展示内容
     */
    private String pageContent;

    /**
     * 审核状态
     * 0：未通过
     * 1：通过
     * 2：审批中
     */
    private String verifyState;

    /**
     * 投标任务状态(0：未开标 1；未中标 2：已中标)
     */
    private String tenderTaskSts;

    /**
     * 任务阶段关联id
     */
    private String relationId;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 项目状态
     */
    private String projectSts;

    /**
     * 当前登录人是否是主要负责人标识
     */
    private String isMainHeadFlag;

    /**
     * 是否忽略
     */
    private String isIgnore;

    /**
     * 是否完成
     */
    private String isFinish;

    /**
     * 拥有权限信息
     */
    private Map<String,Object> powerInfo;

    /**
     * 咨询类别id
     */
    private String counselTypeId;

    /**
     * 隐藏菜单标识 0：不隐藏  1：隐藏
     */
    private String hideMenuFlg;

    /**
     * 是否提了重新编辑申请
     */
    private String isSubmitReedit;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 有合同提交审核（0：否 1：是）
     */
    private String hasContractApply;

    /**
     * 咨询类别名称
     */
    private String counselTypeName;

    /**
     * 是否作废标识
     */
    private String isInvalidFlag;

    /**
     * 当前流程状态
     */
    private String currentState;

    /**
     * 审核时间
     */
    private String approveTime;

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
     * currentState
     *
     * @return currentState 项目描述（略）
     */
    public String getCurrentState() {
        return currentState;
    }

    /**
     * currentState
     *
     * @param currentState 项目描述（略）
     */
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    /**
     * counselTypeName
     *
     * @return counselTypeName 项目描述（略）
     */
    public String getCounselTypeName() {
        return counselTypeName;
    }

    /**
     * counselTypeName
     *
     * @param counselTypeName 项目描述（略）
     */
    public void setCounselTypeName(String counselTypeName) {
        this.counselTypeName = counselTypeName;
    }

    /**
     * hasContractApply
     *
     * @return hasContractApply 项目描述（略）
     */
    public String getHasContractApply() {
        return hasContractApply;
    }

    /**
     * hasContractApply
     *
     * @param hasContractApply 项目描述（略）
     */
    public void setHasContractApply(String hasContractApply) {
        this.hasContractApply = hasContractApply;
    }

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
     * projectId
     *
     * @return projectId 项目描述（略）
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * projectId
     *
     * @param projectId 项目描述（略）
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * isSubmitReedit
     *
     * @return isSubmitReedit 项目描述（略）
     */
    public String getIsSubmitReedit() {
        return isSubmitReedit;
    }

    /**
     * isSubmitReedit
     *
     * @param isSubmitReedit 项目描述（略）
     */
    public void setIsSubmitReedit(String isSubmitReedit) {
        this.isSubmitReedit = isSubmitReedit;
    }

    /**
     * hideMenuFlg
     *
     * @return hideMenuFlg 项目描述（略）
     */
    public String getHideMenuFlg() {
        return hideMenuFlg;
    }

    /**
     * hideMenuFlg
     *
     * @param hideMenuFlg 项目描述（略）
     */
    public void setHideMenuFlg(String hideMenuFlg) {
        this.hideMenuFlg = hideMenuFlg;
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
     * projectSts
     *
     * @return projectSts 项目描述（略）
     */
    public String getProjectSts() {
        return projectSts;
    }

    /**
     * projectSts
     *
     * @param projectSts 项目描述（略）
     */
    public void setProjectSts(String projectSts) {
        this.projectSts = projectSts;
    }

    /**
     * contractId
     *
     * @return contractId 项目描述（略）
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * contractId
     *
     * @param contractId 项目描述（略）
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
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
     * id
     *
     * @return id 项目描述（略）
     */
    public String getId() {
        return id;
    }

    /**
     * id
     *
     * @param id 项目描述（略）
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * label
     *
     * @return label 项目描述（略）
     */
    public String getLabel() {
        return label;
    }

    /**
     * label
     *
     * @param label 项目描述（略）
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * showTool
     *
     * @return showTool 项目描述（略）
     */
    public String getShowTool() {
        return showTool;
    }

    /**
     * showTool
     *
     * @param showTool 项目描述（略）
     */
    public void setShowTool(String showTool) {
        this.showTool = showTool;
    }

    /**
     * children
     *
     * @return children 项目描述（略）
     */
    public List<ElementTreeVO> getChildren() {
        return children;
    }

    /**
     * children
     *
     * @param children 项目描述（略）
     */
    public void setChildren(List<ElementTreeVO> children) {
        this.children = children;
    }

    /**
     * taskContent
     *
     * @return taskContent 项目描述（略）
     */
    public String getTaskContent() {
        return taskContent;
    }

    /**
     * taskContent
     *
     * @param taskContent 项目描述（略）
     */
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    /**
     * startDate
     *
     * @return startDate 项目描述（略）
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * startDate
     *
     * @param startDate 项目描述（略）
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
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
     * treeNodeLevel
     *
     * @return treeNodeLevel 项目描述（略）
     */
    public String getTreeNodeLevel() {
        return treeNodeLevel;
    }

    /**
     * treeNodeLevel
     *
     * @param treeNodeLevel 项目描述（略）
     */
    public void setTreeNodeLevel(String treeNodeLevel) {
        this.treeNodeLevel = treeNodeLevel;
    }

    /**
     * isCanRecall
     *
     * @return isCanRecall 项目描述（略）
     */
    public String getIsCanRecall() {
        return isCanRecall;
    }

    /**
     * isCanRecall
     *
     * @param isCanRecall 项目描述（略）
     */
    public void setIsCanRecall(String isCanRecall) {
        this.isCanRecall = isCanRecall;
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
     * tenderTaskSts
     *
     * @return tenderTaskSts 项目描述（略）
     */
    public String getTenderTaskSts() {
        return tenderTaskSts;
    }

    /**
     * tenderTaskSts
     *
     * @param tenderTaskSts 项目描述（略）
     */
    public void setTenderTaskSts(String tenderTaskSts) {
        this.tenderTaskSts = tenderTaskSts;
    }

    public String getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getIsMainHeadFlag() {
        return isMainHeadFlag;
    }

    public void setIsMainHeadFlag(String isMainHeadFlag) {
        this.isMainHeadFlag = isMainHeadFlag;
    }

    public Map<String, Object> getPowerInfo() {
        return powerInfo;
    }

    public void setPowerInfo(Map<String, Object> powerInfo) {
        this.powerInfo = powerInfo;
    }

    public String getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(String cellStyle) {
        this.cellStyle = cellStyle;
    }

    public String getCellWidthHeight() {
        return cellWidthHeight;
    }

    public void setCellWidthHeight(String cellWidthHeight) {
        this.cellWidthHeight = cellWidthHeight;
    }

    public String getCellRows() {
        return cellRows;
    }

    public void setCellRows(String cellRows) {
        this.cellRows = cellRows;
    }

    public String getCellColumns() {
        return cellColumns;
    }

    public void setCellColumns(String cellColumns) {
        this.cellColumns = cellColumns;
    }

    public String getStartRowNum() {
        return startRowNum;
    }

    public void setStartRowNum(String startRowNum) {
        this.startRowNum = startRowNum;
    }

    public String getStartColumnNum() {
        return startColumnNum;
    }

    public void setStartColumnNum(String startColumnNum) {
        this.startColumnNum = startColumnNum;
    }

    public String getIsInvalidFlag() {
        return isInvalidFlag;
    }

    public void setIsInvalidFlag(String isInvalidFlag) {
        this.isInvalidFlag = isInvalidFlag;
    }
}
