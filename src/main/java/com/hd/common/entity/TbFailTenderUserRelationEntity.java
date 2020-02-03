package com.hd.common.entity;

import java.util.Date;

public class TbFailTenderUserRelationEntity {
    /**
     * 关联ID
     */
    private String relationId;

    /**
     * 落标通知书ID
     */
    private String noticeId;

    /**
     * 投标人名称
     */
    private String tenderUserName;

    /**
     * 排名次序
     */
    private String sort;

    /**
     * 评标得分
     */
    private String bidScore;

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
     * 关联ID
     * @return relation_id 关联ID
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * 关联ID
     * @param relationId 关联ID
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    /**
     * 落标通知书ID
     * @return notice_id 落标通知书ID
     */
    public String getNoticeId() {
        return noticeId;
    }

    /**
     * 落标通知书ID
     * @param noticeId 落标通知书ID
     */
    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId == null ? null : noticeId.trim();
    }

    /**
     * 投标人名称
     * @return tender_user_name 投标人名称
     */
    public String getTenderUserName() {
        return tenderUserName;
    }

    /**
     * 投标人名称
     * @param tenderUserName 投标人名称
     */
    public void setTenderUserName(String tenderUserName) {
        this.tenderUserName = tenderUserName == null ? null : tenderUserName.trim();
    }

    /**
     * 排名次序
     * @return sort 排名次序
     */
    public String getSort() {
        return sort;
    }

    /**
     * 排名次序
     * @param sort 排名次序
     */
    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    /**
     * 评标得分
     * @return bid_score 评标得分
     */
    public String getBidScore() {
        return bidScore;
    }

    /**
     * 评标得分
     * @param bidScore 评标得分
     */
    public void setBidScore(String bidScore) {
        this.bidScore = bidScore == null ? null : bidScore.trim();
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