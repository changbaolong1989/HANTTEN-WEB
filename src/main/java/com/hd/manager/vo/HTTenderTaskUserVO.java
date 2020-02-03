package com.hd.manager.vo;

import java.io.Serializable;

public class HTTenderTaskUserVO implements Serializable {
    private static final long serialVersionUID = 6912121180431256371L;

    /**
     * 关联ID
     */
    private String relationId;

    /**
     * 投标任务ID
     */
    private String tenderTaskId;

    /**
     * 成员ID
     */
    private String userId;

    /**
     * 成员名称
     */
    private String userName;

    /**
     * 是否是主负责人
     */
    private String isMainDuty;

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getTenderTaskId() {
        return tenderTaskId;
    }

    public void setTenderTaskId(String tenderTaskId) {
        this.tenderTaskId = tenderTaskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsMainDuty() {
        return isMainDuty;
    }

    public void setIsMainDuty(String isMainDuty) {
        this.isMainDuty = isMainDuty;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
