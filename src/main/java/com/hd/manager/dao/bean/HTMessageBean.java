package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

public class HTMessageBean extends BaseBean {

    /**
     * 信息ID
     */
    private String messageId;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 接收消息人
     */
    private String acceptUserId;

    /**
     * 是否阅读(0:否,1:是)
     */
    private String isRead;


    /*--------------------------get/set------------------------------*/

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
