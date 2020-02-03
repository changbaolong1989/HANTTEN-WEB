package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTMenuBean;
import com.hd.manager.dao.bean.HTUserBean;

import java.util.List;

public interface HTUserLoginDao extends BaseDao {
    /**
     * 用户登录
     */
    List<HTUserBean> checkUserLogin (HTUserBean paramBean);
    /**
     * 查询用户关联菜单权限
     */
    List<HTMenuBean> searchMenuListByUserId(String userId);
    /*
    * 查询用户所属部门ID集合
    *
    */
    List<String> searchDepartmentIds(String userId);
    /*
     * 查询用户所属职位ID集合
     *
     */
    List<String> searchJobIds(String userId);

}
