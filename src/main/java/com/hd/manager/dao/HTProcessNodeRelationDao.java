package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTJobProcessNodeRelationBean;

import java.util.List;
import java.util.Map;

public interface HTProcessNodeRelationDao extends BaseDao {

    List<HTJobProcessNodeRelationBean> getProcessNodeRelation(HTJobProcessNodeRelationBean paramBean);

    int countProcessNodeRelation(HTJobProcessNodeRelationBean paramBean);

    List<Map<String, Object>> getProcessSelect();

    List<Map<String, Object>> getDepartmentSelect();

    List<Map<String, Object>> getJobSelect(HTJobProcessNodeRelationBean paramBean);

    List<Map<String, Object>> getNodeSelect(HTJobProcessNodeRelationBean paramBean);

}
