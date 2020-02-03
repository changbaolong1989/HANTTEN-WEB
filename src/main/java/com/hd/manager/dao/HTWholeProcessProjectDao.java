package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTProPeriodBean;

import java.util.List;

public interface HTWholeProcessProjectDao extends BaseDao {
    //查询项目阶段列表
    List<HTProPeriodBean> excSearchProPeriodList();
}
