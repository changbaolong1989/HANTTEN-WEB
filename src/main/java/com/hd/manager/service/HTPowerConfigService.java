package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTJobPowerRelationVO;
import com.hd.manager.vo.HTJobVO;
import com.hd.manager.vo.HTPowerVO;

import java.util.List;
import java.util.Map;

/**
 * 职位权限Service
 *
 * @author lihai
 * Created in 2019/10/9 13:03
 **/

public interface HTPowerConfigService extends BaseService {


    /**
     * 查询职位列表
     * @return 职位列表
     */
    public Map<String,Object> excSearchJobList(HTJobVO paramVO) throws LogicException, SystemException;


    /**
     * 查询职位权限信息
     * @return 职位权限详情
     */
    public HTJobVO excSearchJobInfo(HTJobVO paramVO) throws LogicException, SystemException;


    /**
     * 查询所有权限列表
     * @return 所有权限列表
     */
    public List<HTPowerVO> excSearchAllPowerList() throws LogicException, SystemException;

    /**
     * 批量修改职位权限
     */
    public void excUpdatePowers( HTJobVO paramVO) throws LogicException, SystemException;


    /**
     * 查询所有部门
     * @return 所有部门
     */
    public List<HTJobVO> excSearchAllDepartments() throws LogicException, SystemException;
}
