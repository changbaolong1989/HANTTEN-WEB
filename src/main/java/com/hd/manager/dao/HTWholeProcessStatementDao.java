package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTProStatementBean;

import java.util.List;
import java.util.Map;

public interface HTWholeProcessStatementDao extends BaseDao {

    List<HTProStatementBean> searchWholeProcessStatement(HTProStatementBean param);

    List<Map<String, Object>> searchExportData(String projectId);

}
