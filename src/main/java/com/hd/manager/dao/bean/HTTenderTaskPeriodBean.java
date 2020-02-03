package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

public class HTTenderTaskPeriodBean extends BaseBean {
    /**
     * 关联ID
     */
    private String relationId;
    /**
     * 投标任务ID
     */
    private String tenderTaskId;

    /**
     * 投标任务名称
     */
    private String periodName;

    /**
     * 任务阶段ID
     */
    private String periodId;

    /**
     * 阶段类型（0：普通阶段 1：收集资料 2：A3审核阶段  3：成果阶段）
     */
    private String periodType;

    /**
     * 工作步骤说明
     */
    private String periodDesc;

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

    public String getPeriodDesc() {
        return periodDesc;
    }

    public void setPeriodDesc(String periodDesc) {
        this.periodDesc = periodDesc;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}
