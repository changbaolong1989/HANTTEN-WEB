package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.io.Serializable;

public class HTTaskReeditVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 7297456953566031824L;

    /**
     * 主键
     */
    private String reeditId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 流程实例ID
     */
    private String processInstId;

    private String departmentId;

    /**
     * 提审人
     */
    private String applyUserName;

    /**
     * 流程完成时间
     */
    private String completeTime;

    /**
     * 关联id
     */
    private String relationId;

    /**
     * 审批状态
     */
    private String isPass;

    /**
     * 拒绝理由
     */
    private String rejectReason;

    /**
     * relationId
     *
     * @return relationId 项目描述（略）
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * relationId
     *
     * @param relationId 项目描述（略）
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    /**
     * serialVersionUID
     *
     * @return serialVersionUID 项目描述（略）
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * 主键
     *
     * @return reedit_id 主键
     */
    public String getReeditId() {
        return reeditId;
    }

    /**
     * 主键
     *
     * @param reeditId 主键
     */
    public void setReeditId(String reeditId) {
        this.reeditId = reeditId == null ? null : reeditId.trim();
    }

    /**
     * 任务ID
     *
     * @return task_id 任务ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务ID
     *
     * @param taskId 任务ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * 流程实例ID
     *
     * @return process_inst_id 流程实例ID
     */
    public String getProcessInstId() {
        return processInstId;
    }

    /**
     * 流程实例ID
     *
     * @param processInstId 流程实例ID
     */
    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId == null ? null : processInstId.trim();
    }

    /**
     * 提审人
     *
     * @return apply_user_name 提审人
     */
    public String getApplyUserName() {
        return applyUserName;
    }

    /**
     * 提审人
     *
     * @param applyUserName 提审人
     */
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName == null ? null : applyUserName.trim();
    }

    /**
     * 流程完成时间
     *
     * @return complete_time 流程完成时间
     */
    public String getCompleteTime() {
        return completeTime;
    }

    /**
     * 流程完成时间
     *
     * @param completeTime 流程完成时间
     */
    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
