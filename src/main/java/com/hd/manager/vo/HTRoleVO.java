package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.util.List;

/**
 * 角色VO
 *
 * @author LH
 * Created in 2019/7/5 11:00
 */
public class HTRoleVO extends BaseVO {

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 功能菜单列表
     */
    private List<String> menuVOList;

    /**
     * 功能菜单列表(列表展示使用)
     */
    private String menuNames;

    /*-----------------------------get/set ------------------------------方法*/
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public List<String> getMenuVOList() {
        return menuVOList;
    }

    public void setMenuVOList(List<String> menuVOList) {
        this.menuVOList = menuVOList;
    }

    public String getMenuNames() {
        return menuNames;
    }

    public void setMenuNames(String menuNames) {
        this.menuNames = menuNames;
    }
}
