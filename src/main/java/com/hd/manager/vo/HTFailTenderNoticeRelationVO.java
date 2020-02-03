package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.util.List;

/**
 * 落标通知书和任务关联表 VO
 *
 * @author jwl
 * Created in 2019/9/24 11:30
 */
public class HTFailTenderNoticeRelationVO extends BaseVO {
    /**
     * 落标通知书ID
     */
    private String noticeId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 投标单位名称
     */
    private String tenderUnitName;

    /**
     * 采购代理机构
     */
    private String agentOrg;

    /**
     * 日期
     */
    private String editDate;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目编号
     */
    private String projectNum;

    /**
     * projectName
     *
     * @return projectName 项目描述（略）
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * projectName
     *
     * @param projectName 项目描述（略）
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * projectNum
     *
     * @return projectNum 项目描述（略）
     */
    public String getProjectNum() {
        return projectNum;
    }

    /**
     * projectNum
     *
     * @param projectNum 项目描述（略）
     */
    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    /**
     * 投标人列表
     */
    private List<HTFailTenderUserRelationVO> bidderList;

    /**
     * bidderList
     *
     * @return bidderList 项目描述（略）
     */
    public List<HTFailTenderUserRelationVO> getBidderList() {
        return bidderList;
    }

    /**
     * bidderList
     *
     * @param bidderList 项目描述（略）
     */
    public void setBidderList(List<HTFailTenderUserRelationVO> bidderList) {
        this.bidderList = bidderList;
    }

    /**
     * noticeId
     *
     * @return noticeId 项目描述（略）
     */
    public String getNoticeId() {
        return noticeId;
    }

    /**
     * noticeId
     *
     * @param noticeId 项目描述（略）
     */
    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
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
     * tenderUnitName
     *
     * @return tenderUnitName 项目描述（略）
     */
    public String getTenderUnitName() {
        return tenderUnitName;
    }

    /**
     * tenderUnitName
     *
     * @param tenderUnitName 项目描述（略）
     */
    public void setTenderUnitName(String tenderUnitName) {
        this.tenderUnitName = tenderUnitName;
    }

    /**
     * agentOrg
     *
     * @return agentOrg 项目描述（略）
     */
    public String getAgentOrg() {
        return agentOrg;
    }

    /**
     * agentOrg
     *
     * @param agentOrg 项目描述（略）
     */
    public void setAgentOrg(String agentOrg) {
        this.agentOrg = agentOrg;
    }

    /**
     * editDate
     *
     * @return editDate 项目描述（略）
     */
    public String getEditDate() {
        return editDate;
    }

    /**
     * editDate
     *
     * @param editDate 项目描述（略）
     */
    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }
}