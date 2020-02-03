package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTDepartmentBean;

import java.util.List;

public interface HTDepartmentDao extends BaseDao {


    /**
     * 查询部门列表
     * @return 部门列表
     */
    List<HTDepartmentBean> findDepartmentList(HTDepartmentBean paramBean);

    /**
     * 查询部门列表数据的总条数
     * @return 总条数
     */
    Integer searchDepartmentListCount(HTDepartmentBean paramBean);

    /**
     * 修改部门信息
     *
     */
    void updateDepartmentInfo(HTDepartmentBean paramBean);

    /**
     * 查询部门信息
     *
     */
    HTDepartmentBean searchDepartmentInfo(HTDepartmentBean paramBean);
	
	/**
     * 获取上级部门信息
     * @param departmentId 部门ID
     * @return 部门信息
     */
    List<HTDepartmentBean> querySuperDept(String departmentId);


    /**
     * 获取下级部门
     * @param departmentId 部门ID
     * @return 部门信息
     */
    List<HTDepartmentBean> querySubLevelDept(String departmentId);
}
