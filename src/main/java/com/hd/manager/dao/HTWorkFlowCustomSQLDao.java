package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

public interface HTWorkFlowCustomSQLDao extends BaseDao {

    List<Map<String, Object>> getTask(String userId);

}
