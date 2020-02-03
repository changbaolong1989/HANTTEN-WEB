package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

public class HTMessageVO extends BaseVO {

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

    /**
     * 标题提示语
     */
    private String title;

    /**
     * 接收消息人名称
     */
    private String acceptUserName;

    /**
     * 当前时间
     */
    private String currentDate;

    /**
     * openid
     */
    private String openId;

    /**
     * token
     */
    private String token;

    /**
     * 模板id
     */
    private String templateId;

    // ==========================================================================

    private String touser;

    private String template_id;

    private HTMessageVO data;



    /**
     * templateId
     *
     * @return templateId 项目描述（略）
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * templateId
     *
     * @param templateId 项目描述（略）
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * token
     *
     * @return token 项目描述（略）
     */
    public String getToken() {
        return token;
    }

    /**
     * token
     *
     * @param token 项目描述（略）
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * openId
     *
     * @return openId 项目描述（略）
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * openId
     *
     * @param openId 项目描述（略）
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * messageId
     *
     * @return messageId 项目描述（略）
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * messageId
     *
     * @param messageId 项目描述（略）
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * messageContent
     *
     * @return messageContent 项目描述（略）
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * messageContent
     *
     * @param messageContent 项目描述（略）
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * acceptUserId
     *
     * @return acceptUserId 项目描述（略）
     */
    public String getAcceptUserId() {
        return acceptUserId;
    }

    /**
     * acceptUserId
     *
     * @param acceptUserId 项目描述（略）
     */
    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    /**
     * isRead
     *
     * @return isRead 项目描述（略）
     */
    public String getIsRead() {
        return isRead;
    }

    /**
     * isRead
     *
     * @param isRead 项目描述（略）
     */
    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    /**
     * title
     *
     * @return title 项目描述（略）
     */
    public String getTitle() {
        return title;
    }

    /**
     * title
     *
     * @param title 项目描述（略）
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * acceptUserName
     *
     * @return acceptUserName 项目描述（略）
     */
    public String getAcceptUserName() {
        return acceptUserName;
    }

    /**
     * acceptUserName
     *
     * @param acceptUserName 项目描述（略）
     */
    public void setAcceptUserName(String acceptUserName) {
        this.acceptUserName = acceptUserName;
    }

    /**
     * currentDate
     *
     * @return currentDate 项目描述（略）
     */
    public String getCurrentDate() {
        return currentDate;
    }

    /**
     * currentDate
     *
     * @param currentDate 项目描述（略）
     */
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
