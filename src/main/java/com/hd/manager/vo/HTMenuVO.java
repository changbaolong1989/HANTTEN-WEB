package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.io.Serializable;
import java.util.List;

public class HTMenuVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 877782196528181885L;

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
    private String menuType;

    /**
     * 菜单位置：1上；2下；3左；4右
     */
    private String menuPosition;

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
     * 子级菜单列表
     */
    private List<HTMenuVO> childrenList;


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

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuPosition() {
        return menuPosition;
    }

    public void setMenuPosition(String menuPosition) {
        this.menuPosition = menuPosition;
    }

    public List<HTMenuVO> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<HTMenuVO> childrenList) {
        this.childrenList = childrenList;
    }
}
