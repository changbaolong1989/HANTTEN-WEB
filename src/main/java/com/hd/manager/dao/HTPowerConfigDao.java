package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTJobBean;
import com.hd.manager.dao.bean.HTJobPowerRelationBean;
import com.hd.manager.dao.bean.HTPowerBean;

import java.util.List;

/**
 * 职位权限Dao
 *
 * @author lihai
 * Created in 2019/10/9 13:05
 **/

public interface HTPowerConfigDao extends BaseDao {


    /**
     * 查询职位列表
     * @return 职位列表
     */
    public List<HTJobBean> excSearchJobList(HTJobBean paramBean);
    /**
     * 查询职位列表总个数
     * @return 职位列表
     */
    public Integer excSearchJobListCount(HTJobBean paramBean);


    /**
     * 查询职位权限信息
     * @return 职位权限详情
     */
    public HTJobBean excSearchJobInfo(HTJobBean paramVO);

    /**
     * 查询所有权限列表
     * @return 所有权限列表
     */
    public List<HTPowerBean> excSearchAllPowerList();


    /**
     * 批量新增职位权限
     */
    public void excInsertPowers(List<HTJobPowerRelationBean> list);

    /**
     * 批量删除职位权限
     */
    public void excDeletePowers(String jobId);

    /**
     * 查询所有部门
     * @return 所有部门列表
     */
    public List<HTJobBean> excSearchAllDepartments();
}
