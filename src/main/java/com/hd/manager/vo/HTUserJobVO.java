package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;
/*
 *用户所属职位实体VO*
 */
public class HTUserJobVO extends BaseVO {
    //职位ID
    private String jobId ;
    //部门-职位名称
    private String departmentJobName;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getDepartmentJobName() {
        return departmentJobName;
    }

    public void setDepartmentJobName(String departmentJobName) {
        this.departmentJobName = departmentJobName;
    }

}
