package com.hd.common.vo;

import java.io.Serializable;

/**
 * 工作流监听器通用VO
 */
public class CommonActivitiListenVO implements Serializable {

    private static final long serialVersionUID = -4607974101129328517L;

    private boolean isRollBack;

    private String historyRecordId;

    private String departmentId;

    private String isRecall;

    private String exceptionMsg;

    public boolean isRollBack() {
        return isRollBack;
    }

    public void setRollBack(boolean rollBack) {
        isRollBack = rollBack;
    }

    public String getHistoryRecordId() {
        return historyRecordId;
    }

    public void setHistoryRecordId(String historyRecordId) {
        this.historyRecordId = historyRecordId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getIsRecall() {
        return isRecall;
    }

    public void setIsRecall(String isRecall) {
        this.isRecall = isRecall;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

}
