package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTJobBean;
import com.hd.manager.dao.bean.HTPowerBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HTPowerDao extends BaseDao {


    /**
     * 查询用户所拥有权限
     *
     * @return 权限列表
     */
    public List<HTPowerBean> excSearchLoginUserPowerList(@Param(value = "jobIds") List<String> jobIds);


    /**
     * 查询用户当前模块下所拥有权限
     *
     * @return 权限列表
     */
    public List<HTPowerBean> excSearchUserWorkPowerList(HTPowerBean paramBean);


    /*
     * 根据参数查询职位信息
     *
     */
    List<HTJobBean> searchJobInfoByProperties(HTJobBean paramBean);
}
