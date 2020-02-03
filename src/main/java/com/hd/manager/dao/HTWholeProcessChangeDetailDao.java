package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTProAlterDetaiBean;

import java.util.List;
import java.util.Map;

public interface HTWholeProcessChangeDetailDao extends BaseDao {

    List<HTProAlterDetaiBean> searchChangeDetailList(HTProAlterDetaiBean param);

    List<Map<String, Object>> searchExportData(String projectId);

}
