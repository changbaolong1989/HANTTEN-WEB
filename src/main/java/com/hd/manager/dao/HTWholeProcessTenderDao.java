package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTWholeProcessEstimateBean;
import com.hd.manager.dao.bean.HTWholeProcessTenderBean;

import java.util.List;

public interface HTWholeProcessTenderDao extends BaseDao {
    //查询项目招标阶段列表
    List<HTWholeProcessTenderBean> excSearchProTenderPeriodList(HTWholeProcessTenderBean paramBean);
    //查询项目招标阶段列表总数
    Integer excSearchProTenderPeriodListCount(HTWholeProcessTenderBean paramBean);
    //查询项目清标阶段列表
    List<HTWholeProcessTenderBean> excSearchProCleanTenderPeriodList(HTWholeProcessTenderBean paramBean);
    //查询项目招清标段列表总数
    Integer excSearchProCleanTenderPeriodListCount(HTWholeProcessTenderBean paramBean);
    //查询项目招标/清标详情
    HTWholeProcessTenderBean excSearchProTenderPeriodInfoByPrimaryKey(String assessId);
    /**
     *重复名/编号清标信息个数(数据校验)
     */
    Integer excCheckProTenderCountByProperty(HTWholeProcessTenderBean paramBean);
    /**
     * 修改清标信息
     */
    void updateWholeProcessCleanTender(HTWholeProcessTenderBean paramBean);

    //查询项目招标阶段列表(供导出使用)
    List<HTWholeProcessTenderBean> excSearchProTenderPeriodListForExport(HTWholeProcessTenderBean paramBean);

    //查询项目清标阶段列表(供导出使用)
    List<HTWholeProcessTenderBean> excSearchProjectCleanTenderPeriodListForExport(HTWholeProcessTenderBean paramBean);

}
