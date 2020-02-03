package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

/**
 * 开标、评标信息Bean
 *
 * @author zjy
 * Created in 2020/1/15 14:00
 */
public class HTOpenBidBean extends BaseBean {

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
    private Date bidTime;

    /**
     * 评标时间
     */
    private Date evaluationTime;

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

    /**
     * bidTime
     *
     * @return bidTime 项目描述（略）
     */
    public Date getBidTime() {
        return bidTime;
    }

    /**
     * bidTime
     *
     * @param bidTime 项目描述（略）
     */
    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    /**
     * evaluationTime
     *
     * @return evaluationTime 项目描述（略）
     */
    public Date getEvaluationTime() {
        return evaluationTime;
    }

    /**
     * evaluationTime
     *
     * @param evaluationTime 项目描述（略）
     */
    public void setEvaluationTime(Date evaluationTime) {
        this.evaluationTime = evaluationTime;
    }
}
