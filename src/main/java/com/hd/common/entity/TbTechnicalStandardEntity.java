package com.hd.common.entity;

import java.util.Date;

public class TbTechnicalStandardEntity {
    /**
     * 技术标准ID
     */
    private String standardId;

    /**
     * 技术标准名称
     */
    private String standardName;

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
     * 技术标准ID
     * @return standard_id 技术标准ID
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 技术标准ID
     * @param standardId 技术标准ID
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId == null ? null : standardId.trim();
    }

    /**
     * 技术标准名称
     * @return standard_name 技术标准名称
     */
    public String getStandardName() {
        return standardName;
    }

    /**
     * 技术标准名称
     * @param standardName 技术标准名称
     */
    public void setStandardName(String standardName) {
        this.standardName = standardName == null ? null : standardName.trim();
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