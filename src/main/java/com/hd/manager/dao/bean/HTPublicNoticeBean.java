package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

/**
 * 公告Bean
 *
 * @author jwl
 * Created in 2019/7/3 15:31
 */
public class HTPublicNoticeBean extends BaseBean {

    /**
     * 公告ID
     */
    private String noticeId;

    /**
     * 公告名称
     */
    private String noticeName;

    /**
     * 发布人名称
     */
    private String userName;

    /**
     * 发布时间
     */
    private Date releaseDate;

    /**
     * 附件路径
     */
    private String materialPath;

    /**
     * 附件名称
     */
    private String materialName;

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

    /**
     * noticeId
     *
     * @return noticeId 项目描述（略）
     */
    public String getNoticeId() {
        return noticeId;
    }

    /**
     * noticeId
     *
     * @param noticeId 项目描述（略）
     */
    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * noticeName
     *
     * @return noticeName 项目描述（略）
     */
    public String getNoticeName() {
        return noticeName;
    }

    /**
     * noticeName
     *
     * @param noticeName 项目描述（略）
     */
    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
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
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * releaseDate
     *
     * @param releaseDate 项目描述（略）
     */
    public void setReleaseDate(Date releaseDate) {
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
}
