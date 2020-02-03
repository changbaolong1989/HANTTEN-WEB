package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

/**
 * 角色Bean
 *
 * @author LH
 * Created in 2019/7/5 13:00
 */
public class HTRoleBean extends BaseBean {

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
     * 角色权限
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

    public String getMenuNames() {
        return menuNames;
    }

    public void setMenuNames(String menuNames) {
        this.menuNames = menuNames;
    }
}
