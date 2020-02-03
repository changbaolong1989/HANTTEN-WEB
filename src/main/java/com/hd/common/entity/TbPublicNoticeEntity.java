package com.hd.common.entity;

import java.util.Date;

public class TbPublicNoticeEntity {
    /**
     * 公告ID
     */
    private String noticeId;

    /**
     * 公告名称
     */
    private String noticeName;

    /**
     * 发布人
     */
    private String releasePeople;

    /**
     * 发布时间
     */
    private Date releaseDate;

    /**
     * 附件名称
     */
    private String materialName;

    /**
     * 附件路径
     */
    private String materialPath;

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
     * 公告ID
     * @return notice_id 公告ID
     */
    public String getNoticeId() {
        return noticeId;
    }

    /**
     * 公告ID
     * @param noticeId 公告ID
     */
    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId == null ? null : noticeId.trim();
    }

    /**
     * 公告名称
     * @return notice_name 公告名称
     */
    public String getNoticeName() {
        return noticeName;
    }

    /**
     * 公告名称
     * @param noticeName 公告名称
     */
    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName == null ? null : noticeName.trim();
    }

    /**
     * 发布人
     * @return release_people 发布人
     */
    public String getReleasePeople() {
        return releasePeople;
    }

    /**
     * 发布人
     * @param releasePeople 发布人
     */
    public void setReleasePeople(String releasePeople) {
        this.releasePeople = releasePeople == null ? null : releasePeople.trim();
    }

    /**
     * 发布时间
     * @return release_date 发布时间
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * 发布时间
     * @param releaseDate 发布时间
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * 附件名称
     * @return material_name 附件名称
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * 附件名称
     * @param materialName 附件名称
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    /**
     * 附件路径
     * @return material_path 附件路径
     */
    public String getMaterialPath() {
        return materialPath;
    }

    /**
     * 附件路径
     * @param materialPath 附件路径
     */
    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath == null ? null : materialPath.trim();
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