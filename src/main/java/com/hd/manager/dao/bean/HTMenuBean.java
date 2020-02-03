package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

public class HTMenuBean extends BaseBean {

    /**
    * 功能清单ID
    */
    private String menuId;

    /**
     * 功能清单名称
     */
    private String menuName;

    /**
     * 菜单类型：1业务请求菜单；2中间节点菜单
     */
    private Integer menuType;

    /**
     * 菜单位置：1上；2下；3左；4右
     */
    private Integer menuPosition;

    /**
     * 菜单图标URL
     */
    private String menuIconUrl;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 菜单顺序
     */
    private String orderNum;

    /**
     * 父级菜单ID
     */
    private String parentMenuId;

    /**
     * 角色ID(用以解决关联表操作)
     */
    private String roleId;


    /*--------------------------get/set 方法-----------------------------------*/

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuPosition() {
        return menuPosition;
    }

    public void setMenuPosition(Integer menuPosition) {
        this.menuPosition = menuPosition;
    }

    public String getMenuIconUrl() {
        return menuIconUrl;
    }

    public void setMenuIconUrl(String menuIconUrl) {
        this.menuIconUrl = menuIconUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
