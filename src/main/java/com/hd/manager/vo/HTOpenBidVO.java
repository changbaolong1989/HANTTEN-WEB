package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

/**
 * 开标、评标 VO
 *
 * @author zjy
 * Created in 2020/1/15 11:31
 */
public class HTOpenBidVO extends BaseVO {

    /**
     * 主键ID
     */
    private String bidId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 开标时间
     */
    private String bidTime;

    /**
     * 评标时间
     */
    private String evaluationTime;


    /**
     * bidTime
     *
     * @return bidTime 项目描述（略）
     */
    public String getBidTime() {
        return bidTime;
    }

    /**
     * bidTime
     *
     * @param bidTime 项目描述（略）
     */
    public void setBidTime(String bidTime) {
        this.bidTime = bidTime;
    }

    /**
     * evaluationTime
     *
     * @return evaluationTime 项目描述（略）
     */
    public String getEvaluationTime() {
        return evaluationTime;
    }

    /**
     * evaluationTime
     *
     * @param evaluationTime 项目描述（略）
     */
    public void setEvaluationTime(String evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    /**
     * bidId
     *
     * @return bidId 项目描述（略）
     */
    public String getBidId() {
        return bidId;
    }

    /**
     * bidId
     *
     * @param bidId 项目描述（略）
     */
    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    /**
     * taskId
     *
     * @return taskId 项目描述（略）
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * taskId
     *
     * @param taskId 项目描述（略）
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
