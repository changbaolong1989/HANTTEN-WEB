package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

public class HTProPeriodVO extends BaseVO {

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
    private String sort;



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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
