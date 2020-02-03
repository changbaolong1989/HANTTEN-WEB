package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;
import com.hd.manager.vo.HTTenderTaskUserVO;

import java.util.Date;
import java.util.List;

public class HTTableFiveRelationBean extends BaseBean {

    /**
     * 主键ID
     */
    private String relationId;

    /**
     * 任务和阶段ID
     */
    private String taskId;

    /**
     * 咨询业务简述
     */
    private String counselDesc;

    /**
     * 建设规模
     */
    private String constructScale;

    /**
     * 咨询内容与重点
     */
    private String counselContent;

    /**
     * 组长
     */
    private String counselGroup;

    /**
     * 专业负责人
     */
    private String counselDuty;

    /**
     *  咨询业务小组分工
     */
    private String divideWork;

    /**
     * 工程名称
     */
    private String projectName;

    /**
     * 编制人
     */
    private String compileUserName;

    /**
     * 投资额
     */
    private String investedAmount;

    /**
     * 咨询业务小组成员
     */
    private String counselGroupMem;

    /**
     * 编制时间
     */
    private Date compileDate;

    /**
     * relationId.
     *
     * @return relationId 项目描述（略）
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * relationId.
     *
     * @param relationId 项目描述（略）
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    /**
     * taskId.
     *
     * @return taskId 项目描述（略）
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * taskId.
     *
     * @param taskId 项目描述（略）
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * counselDesc.
     *
     * @return counselDesc 项目描述（略）
     */
    public String getCounselDesc() {
        return counselDesc;
    }

    /**
     * counselDesc.
     *
     * @param counselDesc 项目描述（略）
     */
    public void setCounselDesc(String counselDesc) {
        this.counselDesc = counselDesc;
    }

    /**
     * constructScale.
     *
     * @return constructScale 项目描述（略）
     */
    public String getConstructScale() {
        return constructScale;
    }

    /**
     * constructScale.
     *
     * @param constructScale 项目描述（略）
     */
    public void setConstructScale(String constructScale) {
        this.constructScale = constructScale;
    }

    /**
     * counselContent.
     *
     * @return counselContent 项目描述（略）
     */
    public String getCounselContent() {
        return counselContent;
    }

    /**
     * counselContent.
     *
     * @param counselContent 项目描述（略）
     */
    public void setCounselContent(String counselContent) {
        this.counselContent = counselContent;
    }

    /**
     * counselGroup.
     *
     * @return counselGroup 项目描述（略）
     */
    public String getCounselGroup() {
        return counselGroup;
    }

    /**
     * counselGroup.
     *
     * @param counselGroup 项目描述（略）
     */
    public void setCounselGroup(String counselGroup) {
        this.counselGroup = counselGroup;
    }

    /**
     * counselDuty.
     *
     * @return counselDuty 项目描述（略）
     */
    public String getCounselDuty() {
        return counselDuty;
    }

    /**
     * counselDuty.
     *
     * @param counselDuty 项目描述（略）
     */
    public void setCounselDuty(String counselDuty) {
        this.counselDuty = counselDuty;
    }

    /**
     * divideWork.
     *
     * @return divideWork 项目描述（略）
     */
    public String getDivideWork() {
        return divideWork;
    }

    /**
     * divideWork.
     *
     * @param divideWork 项目描述（略）
     */
    public void setDivideWork(String divideWork) {
        this.divideWork = divideWork;
    }

    /**
     * projectName.
     *
     * @return projectName 项目描述（略）
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * projectName.
     *
     * @param projectName 项目描述（略）
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * compileUserName.
     *
     * @return compileUserName 项目描述（略）
     */
    public String getCompileUserName() {
        return compileUserName;
    }

    /**
     * compileUserName.
     *
     * @param compileUserName 项目描述（略）
     */
    public void setCompileUserName(String compileUserName) {
        this.compileUserName = compileUserName;
    }

    /**
     * investedAmount.
     *
     * @return investedAmount 项目描述（略）
     */
    public String getInvestedAmount() {
        return investedAmount;
    }

    /**
     * investedAmount.
     *
     * @param investedAmount 项目描述（略）
     */
    public void setInvestedAmount(String investedAmount) {
        this.investedAmount = investedAmount;
    }

    /**
     * counselGroupMem.
     *
     * @return counselGroupMem 项目描述（略）
     */
    public String getCounselGroupMem() {
        return counselGroupMem;
    }

    /**
     * counselGroupMem.
     *
     * @param counselGroupMem 项目描述（略）
     */
    public void setCounselGroupMem(String counselGroupMem) {
        this.counselGroupMem = counselGroupMem;
    }

    /**
     * compileDate.
     *
     * @return compileDate 项目描述（略）
     */
    public Date getCompileDate() {
        return compileDate;
    }

    /**
     * compileDate.
     *
     * @param compileDate 项目描述（略）
     */
    public void setCompileDate(Date compileDate) {
        this.compileDate = compileDate;
    }
}
