package com.hd.common.entity;

import java.util.Date;

public class TbTenderTaskUser {
    /**
     * 主键ID
     */
    private String relationId;

    /**
     * 投标任务ID
     */
    private String tenderTaskId;

    /**
     * 执行人ID
     */
    private String userId;

    /**
     * 是否是主负责人ID(0：否  1：是)
     */
    private String isMainDuty;

    /**
     * 是否已办（0：否 1：是）
     */
    private String isDone;

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
     * 投标任务ID
     * @return tender_task_id 投标任务ID
     */
    public String getTenderTaskId() {
        return tenderTaskId;
    }

    /**
     * 投标任务ID
     * @param tenderTaskId 投标任务ID
     */
    public void setTenderTaskId(String tenderTaskId) {
        this.tenderTaskId = tenderTaskId == null ? null : tenderTaskId.trim();
    }

    /**
     * 执行人ID
     * @return user_id 执行人ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 执行人ID
     * @param userId 执行人ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 是否是主负责人ID(0：否  1：是)
     * @return is_main_duty 是否是主负责人ID(0：否  1：是)
     */
    public String getIsMainDuty() {
        return isMainDuty;
    }

    /**
     * 是否是主负责人ID(0：否  1：是)
     * @param isMainDuty 是否是主负责人ID(0：否  1：是)
     */
    public void setIsMainDuty(String isMainDuty) {
        this.isMainDuty = isMainDuty == null ? null : isMainDuty.trim();
    }

    /**
     * 是否已办（0：否 1：是）
     * @return is_done 是否已办（0：否 1：是）
     */
    public String getIsDone() {
        return isDone;
    }

    /**
     * 是否已办（0：否 1：是）
     * @param isDone 是否已办（0：否 1：是）
     */
    public void setIsDone(String isDone) {
        this.isDone = isDone == null ? null : isDone.trim();
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