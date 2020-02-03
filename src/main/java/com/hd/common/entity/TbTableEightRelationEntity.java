package com.hd.common.entity;

import java.util.Date;

public class TbTableEightRelationEntity {
    /**
     * 
     */
    private String relationId;

    /**
     * 任务和阶段iD
     */
    private String taskId;

    /**
     * 现场勘探记录
     */
    private String prospectRecord;

    /**
     * 达成意见一致时间
     */
    private Date consensusTime;

    /**
     * 发包人
     */
    private String employer;

    /**
     * 承包人
     */
    private String contractor;

    /**
     * 监理人
     */
    private String supervisor;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改人
     */
    private String updateUserId;

    /**
     * 是否删除
     */
    private String isDelFlg;

    /**
     * 
     * @return relation_id 
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * 
     * @param relationId 
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    /**
     * 任务和阶段iD
     * @return task_id 任务和阶段iD
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务和阶段iD
     * @param taskId 任务和阶段iD
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * 现场勘探记录
     * @return prospect_record 现场勘探记录
     */
    public String getProspectRecord() {
        return prospectRecord;
    }

    /**
     * 现场勘探记录
     * @param prospectRecord 现场勘探记录
     */
    public void setProspectRecord(String prospectRecord) {
        this.prospectRecord = prospectRecord == null ? null : prospectRecord.trim();
    }

    /**
     * 达成意见一致时间
     * @return consensus_time 达成意见一致时间
     */
    public Date getConsensusTime() {
        return consensusTime;
    }

    /**
     * 达成意见一致时间
     * @param consensusTime 达成意见一致时间
     */
    public void setConsensusTime(Date consensusTime) {
        this.consensusTime = consensusTime;
    }

    /**
     * 发包人
     * @return employer 发包人
     */
    public String getEmployer() {
        return employer;
    }

    /**
     * 发包人
     * @param employer 发包人
     */
    public void setEmployer(String employer) {
        this.employer = employer == null ? null : employer.trim();
    }

    /**
     * 承包人
     * @return contractor 承包人
     */
    public String getContractor() {
        return contractor;
    }

    /**
     * 承包人
     * @param contractor 承包人
     */
    public void setContractor(String contractor) {
        this.contractor = contractor == null ? null : contractor.trim();
    }

    /**
     * 监理人
     * @return supervisor 监理人
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * 监理人
     * @param supervisor 监理人
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor == null ? null : supervisor.trim();
    }

    /**
     * 创建时间
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 创建人
     * @return create_user_id 创建人
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     * @param createUserId 创建人
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    /**
     * 修改时间
     * @return update_date 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 修改时间
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 修改人
     * @return update_user_id 修改人
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 修改人
     * @param updateUserId 修改人
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * 是否删除
     * @return is_del_flg 是否删除
     */
    public String getIsDelFlg() {
        return isDelFlg;
    }

    /**
     * 是否删除
     * @param isDelFlg 是否删除
     */
    public void setIsDelFlg(String isDelFlg) {
        this.isDelFlg = isDelFlg == null ? null : isDelFlg.trim();
    }
}