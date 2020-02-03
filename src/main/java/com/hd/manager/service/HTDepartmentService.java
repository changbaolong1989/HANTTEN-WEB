package com.hd.manager.service;


import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTDepartmentVO;

import java.util.List;
import java.util.Map;

public interface HTDepartmentService extends BaseService {

    /**
     * 查询部门列表
     * @return 部门列表
     */
    public Map<String,Object> searchDepartMentList (HTDepartmentVO paramVO) throws LogicException, SystemException;

    /**
     * 修改部门名称
     *
     */
    public void updateDepartmentInfo(HTDepartmentVO paramVO) throws LogicException, SystemException;
}
