package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

public class HTJobPowerRelationBean extends BaseBean {

    /**
     *关联ID
     */
    private String relationId;

    /**
     *职位ID
     */
    private String jobId;

    /**
     *职位名称
     */
    private String jobName;

    /**
     *权限ID
     */
    private String powerId;

    /**
     *权限名称
     */
    private String powerName;

    /*-----------------------get/set方法--------------------------*/
    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }
}
