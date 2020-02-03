package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTProPeriodBean;
import com.hd.manager.dao.bean.HTWholeProcessAssessBean;

import java.util.List;

public interface HTWholeProcessAssessDao extends BaseDao {
    //查询项目估算阶段列表
    List<HTWholeProcessAssessBean> excSearchProAssessPeriodList(HTWholeProcessAssessBean paramBean);
    //查询项目估算阶段列表(供导出使用)
    List<HTWholeProcessAssessBean> excSearchProAssessPeriodListForExport(HTWholeProcessAssessBean paramBean);
    //查询项目估算阶段列表
    Integer excSearchProAssessPeriodListCount(HTWholeProcessAssessBean paramBean);
    //查询项目估算详情
    HTWholeProcessAssessBean excSearchProAssessPeriodInfoByPrimaryKey(String assessId);
}
