package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTWholeProcessContractBean;

import java.util.List;
import java.util.Map;

public interface HTWholeProcessCostDao extends BaseDao {

    List<HTWholeProcessContractBean> searchCostList(HTWholeProcessContractBean param);

    List<Map<String, Object>> searchExportData(String projectId);

}
