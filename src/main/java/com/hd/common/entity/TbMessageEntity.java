package com.hd.common.entity;

import java.util.Date;

public class TbMessageEntity {
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
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改人
     */
    private String updateUserId;

    /**
     * 是否删除
     */
    private String isDelFlg;

    /**
     * 信息ID
     * @return message_id 信息ID
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * 信息ID
     * @param messageId 信息ID
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    /**
     * 消息内容
     * @return message_content 消息内容
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * 消息内容
     * @param messageContent 消息内容
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    /**
     * 接收消息人
     * @return accept_user_id 接收消息人
     */
    public String getAcceptUserId() {
        return acceptUserId;
    }

    /**
     * 接收消息人
     * @param acceptUserId 接收消息人
     */
    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId == null ? null : acceptUserId.trim();
    }

    /**
     * 是否阅读(0:否,1:是)
     * @return is_read 是否阅读(0:否,1:是)
     */
    public String getIsRead() {
        return isRead;
    }

    /**
     * 是否阅读(0:否,1:是)
     * @param isRead 是否阅读(0:否,1:是)
     */
    public void setIsRead(String isRead) {
        this.isRead = isRead == null ? null : isRead.trim();
    }

    /**
     * 创建时间
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 创建人
     * @return create_user_id 创建人
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     * @param createUserId 创建人
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    /**
     * 修改时间
     * @return update_date 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 修改时间
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 修改人
     * @return update_user_id 修改人
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 修改人
     * @param updateUserId 修改人
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * 是否删除
     * @return is_del_flg 是否删除
     */
    public String getIsDelFlg() {
        return isDelFlg;
    }

    /**
     * 是否删除
     * @param isDelFlg 是否删除
     */
    public void setIsDelFlg(String isDelFlg) {
        this.isDelFlg = isDelFlg == null ? null : isDelFlg.trim();
    }
}