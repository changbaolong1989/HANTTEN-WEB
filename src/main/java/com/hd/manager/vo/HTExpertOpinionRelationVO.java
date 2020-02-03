package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

/**
 * 招标文件专家论证意见表和任务ID的关联表 VO
 *
 * @author jwl
 * Created in 2019/9/24 11:30
 */
public class HTExpertOpinionRelationVO extends BaseVO {

    /**
     * 主键ID
     */
    private String expertOpinionId;

    /**
     *
     */
    private String taskId;

    /**
     * 采购人
     */
    private String purchaser;

    /**
     * 采购代理机构
     */
    private String agentOrg;

    /**
     * 采购类别
     */
    private String purchaserType;

    /**
     * 专家论证意见
     */
    private String expertOpinion;

    /**
     * 论证专家签字
     */
    private String expertSign;

    /**
     * 备注
     */
    private String remark;

    /**
     * 论证时间
     */
    private String proveTime;

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
     * expertOpinionId
     *
     * @return expertOpinionId 项目描述（略）
     */
    public String getExpertOpinionId() {
        return expertOpinionId;
    }

    /**
     * expertOpinionId
     *
     * @param expertOpinionId 项目描述（略）
     */
    public void setExpertOpinionId(String expertOpinionId) {
        this.expertOpinionId = expertOpinionId;
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
     * purchaser
     *
     * @return purchaser 项目描述（略）
     */
    public String getPurchaser() {
        return purchaser;
    }

    /**
     * purchaser
     *
     * @param purchaser 项目描述（略）
     */
    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
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
     * purchaserType
     *
     * @return purchaserType 项目描述（略）
     */
    public String getPurchaserType() {
        return purchaserType;
    }

    /**
     * purchaserType
     *
     * @param purchaserType 项目描述（略）
     */
    public void setPurchaserType(String purchaserType) {
        this.purchaserType = purchaserType;
    }

    /**
     * expertOpinion
     *
     * @return expertOpinion 项目描述（略）
     */
    public String getExpertOpinion() {
        return expertOpinion;
    }

    /**
     * expertOpinion
     *
     * @param expertOpinion 项目描述（略）
     */
    public void setExpertOpinion(String expertOpinion) {
        this.expertOpinion = expertOpinion;
    }

    /**
     * expertSign
     *
     * @return expertSign 项目描述（略）
     */
    public String getExpertSign() {
        return expertSign;
    }

    /**
     * expertSign
     *
     * @param expertSign 项目描述（略）
     */
    public void setExpertSign(String expertSign) {
        this.expertSign = expertSign;
    }

    /**
     * remark
     *
     * @return remark 项目描述（略）
     */
    public String getRemark() {
        return remark;
    }

    /**
     * remark
     *
     * @param remark 项目描述（略）
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * proveTime
     *
     * @return proveTime 项目描述（略）
     */
    public String getProveTime() {
        return proveTime;
    }

    /**
     * proveTime
     *
     * @param proveTime 项目描述（略）
     */
    public void setProveTime(String proveTime) {
        this.proveTime = proveTime;
    }
}