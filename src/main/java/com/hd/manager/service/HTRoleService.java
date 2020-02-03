package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.dao.bean.HTMenuBean;
import com.hd.manager.dao.bean.HTRoleBean;
import com.hd.manager.vo.HTMenuVO;
import com.hd.manager.vo.HTRoleVO;

import java.util.List;
import java.util.Map;

public interface HTRoleService extends BaseService {

    /**
     * 新增角色
     * @return 影响条数
     */
    public void insertRoleInfo (HTRoleVO paramVO) throws LogicException, SystemException;

    /**
     * 修改角色
     * @return 影响条数
     */
    public void updateRoleInfo(HTRoleVO paramVO) throws LogicException, SystemException;

    /**
     * 删除角色
     * @return 影响条数
     */
    public void deleteRoleInfo(HTRoleVO paramVO) throws LogicException, SystemException;

    /**
     * 查询角色列表
     * @return 角色列表
     */
    public Map<String,Object> findRoleList(HTRoleVO paramVO) throws LogicException, SystemException;


    /**
     * 查询角色信息
     * @return 角色详情
     */
    public HTRoleVO findRoleInfo(HTRoleVO paramVO) throws LogicException, SystemException;


    /**
     * 查询所有功能菜单列表
     * @return 所有功能菜单列表
     */
    public Map<String,Object> findAllMenuList() throws LogicException, SystemException;

}
