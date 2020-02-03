package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

public class HTProPeriodBean extends BaseBean {

    /**
     *项目阶段ID
     */
    private String projectPeriodId;

    /**
     *项目阶段名称
     */
    private String projectPeriodName;

    /**
     *父级阶段ID
     */
    private String projectPeriodParentId;

    /**
     *项目阶段跳转地址
     */
    private String projectPeriodUrl;

    /**
     *项目阶段层级
     */
    private String projectPeriodLevel;

    /**
     *是否可以点击(0：否 1：是)
     */
    private String isClick;

    /**
     *排序
     */
    private Integer sort;



    /*---------------------get/set方法-------------------------*/

    public String getProjectPeriodId() {
        return projectPeriodId;
    }

    public void setProjectPeriodId(String projectPeriodId) {
        this.projectPeriodId = projectPeriodId;
    }

    public String getProjectPeriodName() {
        return projectPeriodName;
    }

    public void setProjectPeriodName(String projectPeriodName) {
        this.projectPeriodName = projectPeriodName;
    }

    public String getProjectPeriodParentId() {
        return projectPeriodParentId;
    }

    public void setProjectPeriodParentId(String projectPeriodParentId) {
        this.projectPeriodParentId = projectPeriodParentId;
    }

    public String getProjectPeriodLevel() {
        return projectPeriodLevel;
    }

    public void setProjectPeriodLevel(String projectPeriodLevel) {
        this.projectPeriodLevel = projectPeriodLevel;
    }

    public String getIsClick() {
        return isClick;
    }

    public void setIsClick(String isClick) {
        this.isClick = isClick;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getProjectPeriodUrl() {
        return projectPeriodUrl;
    }

    public void setProjectPeriodUrl(String projectPeriodUrl) {
        this.projectPeriodUrl = projectPeriodUrl;
    }
}
