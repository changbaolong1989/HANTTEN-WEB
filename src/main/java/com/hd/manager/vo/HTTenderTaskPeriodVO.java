package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.util.List;

public class HTTenderTaskPeriodVO extends BaseVO {
    /**
     * 关联ID
     */
    private String relationId;
    /**
     * 投标任务ID
     */
    private String tenderTaskId;

    /**
     * 跳转节点
     */
    private String submitNode;
    /**
     * 投标任务名称
     */
    private String periodName;
    /**
     * 任务阶段ID
     */
    private String periodId;
    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 关联文件主键ID
     */
    private List<String> materialIds;
    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getTenderTaskId() {
        return tenderTaskId;
    }

    public void setTenderTaskId(String tenderTaskId) {
        this.tenderTaskId = tenderTaskId;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public List<String> getMaterialIds() {
        return materialIds;
    }

    public void setMaterialIds(List<String> materialIds) {
        this.materialIds = materialIds;
    }

    public String getSubmitNode() {
        return submitNode;
    }

    public void setSubmitNode(String submitNode) {
        this.submitNode = submitNode;
    }
}
