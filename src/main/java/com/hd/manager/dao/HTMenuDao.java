package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTMenuBean;
import com.hd.manager.dao.bean.HTRoleBean;

import java.util.List;

/**
 * 功能菜单 持久层接口
 *
 * @author lihai
 * Created in 2019/7/3 14:37
 */
public interface HTMenuDao extends BaseDao {

    /**
     * 查询角色相关功能菜单列表
     * @return 功能菜单列表
     */
    List<HTMenuBean> findMenuListByRoleId(HTRoleBean paramBean);
    /**
     * 删除角色相关功能菜单列表
     * @return 影响条数
     */
    int deleteRoleMenuRelationListByRoleId(HTRoleBean paramBean);
    /**
     * 批量新增角色与功能菜单关联
     * @return 影响条数
     */
    int insertRoleMenuRelationList(List<HTMenuBean> paramBean);
    /**
     * 查询所有功能菜单列表
     * @return 功能菜单列表
     */
    List<HTMenuBean> findAllMenuList();
}
