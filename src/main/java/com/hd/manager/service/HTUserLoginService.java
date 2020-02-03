package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.dao.bean.HTMenuBean;
import com.hd.manager.vo.HTMenuVO;
import com.hd.manager.vo.HTUserVO;

import java.util.List;
import java.util.Map;

public interface HTUserLoginService extends BaseService {

    /**
     * 用户登录
     */
    public HTUserVO userLogin (HTUserVO paramVo) throws LogicException, SystemException;

    /**
     * 查询角色下菜单列表
     */
    public List<HTMenuVO> searchMenuList(HTUserVO paramVo)  throws LogicException, SystemException;

    /**
     * 查询用户所属部门ID集合
     */
    public List<String> searchDepartmentIds(HTUserVO paramVo)  throws LogicException, SystemException;

    /**
     * 查询用户所属职位ID集合
     */
    public List<String> searchJobIds(HTUserVO paramVo)  throws LogicException, SystemException;
}
