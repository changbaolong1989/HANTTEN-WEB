package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

/**
 * 落标通知书和投标人的关联表 VO
 *
 * @author jwl
 * Created in 2019/9/24 11:30
 */
public class HTFailTenderUserRelationVO extends BaseVO {
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

}