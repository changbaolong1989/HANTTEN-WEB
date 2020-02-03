package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTWholeProcessPayBean;

import java.util.List;

public interface HTWholeProcessPayDao extends BaseDao {
    //查询项目付款台账阶段列表
    List<HTWholeProcessPayBean> excSearchProPayPeriodList(HTWholeProcessPayBean paramBean);
    //查询项目付款台账阶段列表总数
    Integer excSearchProPayPeriodListCount(HTWholeProcessPayBean paramBean);
    //查询项目付款台账详情
    HTWholeProcessPayBean excSearchProPayPeriodInfoByPrimaryKey(String PayId);
    //查询项目付款台账数量(数据校验)
    Integer excSearchProPayPeriodCountByProperty(HTWholeProcessPayBean paramBean);
    // 查询列表（导出）
    List<HTWholeProcessPayBean> excSearchProPayPeriodListForExport(HTWholeProcessPayBean paramBean);
    // 查询所含期数（导出）
    List<String> excSearchProPayPeriodNumListForExport(HTWholeProcessPayBean paramBean);
}
