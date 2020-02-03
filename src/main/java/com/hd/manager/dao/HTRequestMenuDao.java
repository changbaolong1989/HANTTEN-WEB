package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTRequestMenuBean;

import java.util.List;

public interface HTRequestMenuDao extends BaseDao {

    /**
     * 查询请款单列表
     */
    List<HTRequestMenuBean> excSearchRequestMenuListByProperty(HTRequestMenuBean paramBean);

    /**
     * 查询请款单个数
     */
    Integer excSearchRequestMenuListCountByProperty(HTRequestMenuBean paramBean);

    /**
     * 查询请款单重名个数
     */
    Integer excCheckRequestMenuListCountByProperty(HTRequestMenuBean paramBean);

    /**
     * 查询请款单详情
     */
    HTRequestMenuBean excSearchRequestMenuInfoById(HTRequestMenuBean paramBean);

    /**
     * 通过合同ID查询请款单
     * @param contractId
     * @return
     */
    List<String> excSearchRequestMenuIdByContractId(String contractId);
}
