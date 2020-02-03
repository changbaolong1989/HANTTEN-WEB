package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTDictContractTypeBean;

import java.util.List;

public interface HTDictContractTypeDao extends BaseDao {

    /**
     * 根据部门ID查询合同类型列表
     * @return 合同类型列表
     */
    List<HTDictContractTypeBean> findDictContractTypeListByDepartmentId(String departmentId);
}
