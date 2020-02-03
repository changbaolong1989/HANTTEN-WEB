package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTRoleBean;

import java.util.List;

public interface HTRoleDao  extends BaseDao {


    /**
     * 新增角色
     * @return 影响条数
     */
    Integer insertRoleInfo(HTRoleBean paramBean);

    /**
     * 修改角色
     * @return 影响条数
     */
    Integer updateRoleInfo(HTRoleBean paramBean);

    /**
     * 删除角色
     * @return 影响条数
     */
    Integer deleteRoleInfo(HTRoleBean paramBean);

    /**
     * 查询角色列表
     * @return 角色列表
     */
    List<HTRoleBean> findRoleList(HTRoleBean paramBean);

    /**
     * 查询角色详情
     * @return 角色详情
     */
    HTRoleBean findRoleInfo(HTRoleBean paramBean);
    /**
     * 查询角色总数
     * @return 角色数量
     */
    Integer findAllRoleListCount (HTRoleBean paramBean);

    /**
     * 查询角色名称是否重复
     * @return 重复角色数量
     */
    Integer findRoleCountByRoleName (HTRoleBean paramBean);

    /**
     * 查询角色与用户关联数量
     * @return 重复角色与用户关联数量
     */
    Integer searchRoleAndUserRelationCountByRoleId (HTRoleBean paramBean);
}
