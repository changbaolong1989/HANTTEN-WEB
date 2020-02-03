package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTWholeProcessClaimBean;

import java.util.List;

public interface HTWholeProcessClaimDao extends BaseDao {
    //查询项目索赔台账阶段列表
    List<HTWholeProcessClaimBean> excSearchProClaimPeriodList(HTWholeProcessClaimBean paramBean);
    //查询项目索赔台账阶段列表总数
    Integer excSearchProClaimPeriodListCount(HTWholeProcessClaimBean paramBean);
    //查询项目索赔台账详情
    HTWholeProcessClaimBean excSearchProClaimPeriodInfoByPrimaryKey(String ClaimId);
    //查询项目索赔台账阶段列表(供excel导出使用)
    List<HTWholeProcessClaimBean> excSearchProClaimPeriodListForExport(HTWholeProcessClaimBean paramBean);
}
