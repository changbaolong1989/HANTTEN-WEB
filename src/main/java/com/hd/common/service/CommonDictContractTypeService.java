package com.hd.common.service;

import com.hd.base.service.BaseService;
import com.hd.manager.dao.bean.HTDictContractTypeBean;
import com.hd.manager.vo.HTDictContractTypeVO;

import java.util.List;

public interface CommonDictContractTypeService extends BaseService {
    /**
     * 根据部门ID查询合同类型列表
     * @param departmentId 部门ID
     * @return 合同类型列表
     */
    public List<HTDictContractTypeVO> findDictContractTypeListByDepartmienId (String departmentId);
}
