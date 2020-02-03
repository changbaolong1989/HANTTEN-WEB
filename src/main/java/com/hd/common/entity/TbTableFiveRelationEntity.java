package com.hd.common.entity;

import java.util.Date;

public class TbTableFiveRelationEntity {
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
     * 主键ID
     * @return relation_id 主键ID
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * 主键ID
     * @param relationId 主键ID
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    /**
     * 任务和阶段ID
     * @return task_id 任务和阶段ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务和阶段ID
     * @param taskId 任务和阶段ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * 咨询业务简述
     * @return counsel_desc 咨询业务简述
     */
    public String getCounselDesc() {
        return counselDesc;
    }

    /**
     * 咨询业务简述
     * @param counselDesc 咨询业务简述
     */
    public void setCounselDesc(String counselDesc) {
        this.counselDesc = counselDesc == null ? null : counselDesc.trim();
    }

    /**
     * 建设规模
     * @return construct_scale 建设规模
     */
    public String getConstructScale() {
        return constructScale;
    }

    /**
     * 建设规模
     * @param constructScale 建设规模
     */
    public void setConstructScale(String constructScale) {
        this.constructScale = constructScale == null ? null : constructScale.trim();
    }

    /**
     * 咨询内容与重点
     * @return counsel_content 咨询内容与重点
     */
    public String getCounselContent() {
        return counselContent;
    }

    /**
     * 咨询内容与重点
     * @param counselContent 咨询内容与重点
     */
    public void setCounselContent(String counselContent) {
        this.counselContent = counselContent == null ? null : counselContent.trim();
    }

    /**
     * 组长
     * @return counsel_group 组长
     */
    public String getCounselGroup() {
        return counselGroup;
    }

    /**
     * 组长
     * @param counselGroup 组长
     */
    public void setCounselGroup(String counselGroup) {
        this.counselGroup = counselGroup == null ? null : counselGroup.trim();
    }

    /**
     * 专业负责人
     * @return counsel_duty 专业负责人
     */
    public String getCounselDuty() {
        return counselDuty;
    }

    /**
     * 专业负责人
     * @param counselDuty 专业负责人
     */
    public void setCounselDuty(String counselDuty) {
        this.counselDuty = counselDuty == null ? null : counselDuty.trim();
    }

    /**
     *  咨询业务小组分工
     * @return divide_work  咨询业务小组分工
     */
    public String getDivideWork() {
        return divideWork;
    }

    /**
     *  咨询业务小组分工
     * @param divideWork  咨询业务小组分工
     */
    public void setDivideWork(String divideWork) {
        this.divideWork = divideWork == null ? null : divideWork.trim();
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