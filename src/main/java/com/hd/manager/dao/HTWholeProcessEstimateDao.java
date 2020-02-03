package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTWholeProcessAssessBean;
import com.hd.manager.dao.bean.HTWholeProcessEstimateBean;

import java.util.List;

public interface HTWholeProcessEstimateDao extends BaseDao {
    //查询项目概算阶段列表
    List<HTWholeProcessEstimateBean> excSearchProEstimatePeriodList(HTWholeProcessEstimateBean paramBean);
    //查询项目概算阶段列表
    Integer excSearchProEstimatePeriodListCount(HTWholeProcessEstimateBean paramBean);
    //查询项目概算详情
    HTWholeProcessEstimateBean excSearchProEstimatePeriodInfoByPrimaryKey(String assessId);

    List<HTWholeProcessEstimateBean> excSearchProEstimatePeriodListForExport(HTWholeProcessEstimateBean paramBean);
}
