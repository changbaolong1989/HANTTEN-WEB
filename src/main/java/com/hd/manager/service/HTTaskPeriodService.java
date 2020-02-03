package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTTaskPeriodVO;

import java.util.Map;

public interface HTTaskPeriodService extends BaseService {
    /**
     * 查询工作内容说明列表
     * @return 部门列表
     */
    public Map<String,Object> excSearchTaskPeriodList (HTTaskPeriodVO paramVO) throws LogicException, SystemException;

    /**
     * 修改工作内容说明信息
     *
     */
    public void excUpdateTaskPeriodInfo(HTTaskPeriodVO paramVO) throws LogicException, SystemException;


    /**
     * 查询所有部门类型
     * @return 所有部门类型
     */
    public Map<String,Object> excSearchAllDictDepartmentList () throws LogicException, SystemException;
}
