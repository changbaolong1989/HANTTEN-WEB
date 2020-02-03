package com.hd.common.util.word.XWPFHandler;

/**
 * Created by jwl on 2019/9/26.
 */
public class BidderModel {

    /**
     * 投标人名称
     */
    private String bidderName;

    /**
     * 排名次序
     */
    private String rankingOrder;

    /**
     * 评标得分
     */
    private String bidEvaluation;

    /**
     * bidderName
     *
     * @return bidderName 项目描述（略）
     */
    public String getBidderName() {
        return bidderName;
    }

    /**
     * bidderName
     *
     * @param bidderName 项目描述（略）
     */
    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    /**
     * rankingOrder
     *
     * @return rankingOrder 项目描述（略）
     */
    public String getRankingOrder() {
        return rankingOrder;
    }

    /**
     * rankingOrder
     *
     * @param rankingOrder 项目描述（略）
     */
    public void setRankingOrder(String rankingOrder) {
        this.rankingOrder = rankingOrder;
    }

    /**
     * bidEvaluation
     *
     * @return bidEvaluation 项目描述（略）
     */
    public String getBidEvaluation() {
        return bidEvaluation;
    }

    /**
     * bidEvaluation
     *
     * @param bidEvaluation 项目描述（略）
     */
    public void setBidEvaluation(String bidEvaluation) {
        this.bidEvaluation = bidEvaluation;
    }
}
