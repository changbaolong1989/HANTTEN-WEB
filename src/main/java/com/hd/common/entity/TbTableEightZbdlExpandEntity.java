package com.hd.common.entity;

import java.util.Date;

public class TbTableEightZbdlExpandEntity {
    /**
     * 招标A8的主键ID
     */
    private String relationId;

    /**
     * 扩展主键ID
     */
    private String expandId;

    /**
     * 序号
     */
    private Integer sortNum;

    /**
     * 咨询意见
     */
    private String counselAdvise;

    /**
     * 委托方回复意见
     */
    private String answerAdvise;

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
     * 招标A8的主键ID
     * @return relation_id 招标A8的主键ID
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * 招标A8的主键ID
     * @param relationId 招标A8的主键ID
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    /**
     * 扩展主键ID
     * @return expand_id 扩展主键ID
     */
    public String getExpandId() {
        return expandId;
    }

    /**
     * 扩展主键ID
     * @param expandId 扩展主键ID
     */
    public void setExpandId(String expandId) {
        this.expandId = expandId == null ? null : expandId.trim();
    }

    /**
     * 序号
     * @return sort_num 序号
     */
    public Integer getSortNum() {
        return sortNum;
    }

    /**
     * 序号
     * @param sortNum 序号
     */
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * 咨询意见
     * @return counsel_advise 咨询意见
     */
    public String getCounselAdvise() {
        return counselAdvise;
    }

    /**
     * 咨询意见
     * @param counselAdvise 咨询意见
     */
    public void setCounselAdvise(String counselAdvise) {
        this.counselAdvise = counselAdvise == null ? null : counselAdvise.trim();
    }

    /**
     * 委托方回复意见
     * @return answer_advise 委托方回复意见
     */
    public String getAnswerAdvise() {
        return answerAdvise;
    }

    /**
     * 委托方回复意见
     * @param answerAdvise 委托方回复意见
     */
    public void setAnswerAdvise(String answerAdvise) {
        this.answerAdvise = answerAdvise == null ? null : answerAdvise.trim();
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