package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

/**
 * 技术标准VO
 *
 * @author jwl
 * Created in 2019/7/5 11:00
 */
public class HTTechnicalStandardVO extends BaseVO {

    /**
     * 技术标准ID
     */
    private String standardId;

    /**
     * 技术标准名称
     */
    private String standardName;

    /**
     * 发布人名称
     */
    private String userName;

    /**
     * 发布时间
     */
    private String releaseDate;

    /**
     * 附件路径
     */
    private String materialPath;

    /**
     * 附件名称
     */
    private String materialName;

    /**
     * standardId
     *
     * @return standardId 项目描述（略）
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * standardId
     *
     * @param standardId 项目描述（略）
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * standardName
     *
     * @return standardName 项目描述（略）
     */
    public String getStandardName() {
        return standardName;
    }

    /**
     * standardName
     *
     * @param standardName 项目描述（略）
     */
    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    /**
     * userName
     *
     * @return userName 项目描述（略）
     */
    public String getUserName() {
        return userName;
    }

    /**
     * userName
     *
     * @param userName 项目描述（略）
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * releaseDate
     *
     * @return releaseDate 项目描述（略）
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * releaseDate
     *
     * @param releaseDate 项目描述（略）
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * materialPath
     *
     * @return materialPath 项目描述（略）
     */
    public String getMaterialPath() {
        return materialPath;
    }

    /**
     * materialPath
     *
     * @param materialPath 项目描述（略）
     */
    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
    }

    /**
     * materialName
     *
     * @return materialName 项目描述（略）
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * materialName
     *
     * @param materialName 项目描述（略）
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
}