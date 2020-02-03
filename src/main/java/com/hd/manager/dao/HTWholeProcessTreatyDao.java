package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTWholeProcessAssessBean;
import com.hd.manager.dao.bean.HTWholeProcessTreatyBean;

import java.util.List;

/**
 * 合约规划dao 持久层资源
 */
public interface HTWholeProcessTreatyDao extends BaseDao {
    //查询项目合约规划阶段列表
    List<HTWholeProcessTreatyBean> excSearchProTreatyPeriodList(HTWholeProcessTreatyBean paramBean);
    //查询项目合约规划阶段列表总数
    Integer excSearchProTreatyPeriodListCount(HTWholeProcessTreatyBean paramBean);
    //查询项目合约规划详情
    HTWholeProcessTreatyBean excSearchProTreatyPeriodInfoByPrimaryKey(String treatyId);

    List<HTWholeProcessTreatyBean> excSearchProTreatyPeriodListForExport(HTWholeProcessTreatyBean paramBean);
}
