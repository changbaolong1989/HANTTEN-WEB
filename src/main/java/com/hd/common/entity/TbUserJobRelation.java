package com.hd.common.entity;

public class TbUserJobRelation {
    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String jobId;

    /**
     * 
     * @return user_id 
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId 
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 
     * @return job_id 
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 
     * @param jobId 
     */
    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }
}