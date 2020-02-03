package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTWholeProcessChangeStandingBookBean;

import java.util.List;
import java.util.Map;

public interface HTWholeProcessChangeStandingBookDao extends BaseDao {


    List<HTWholeProcessChangeStandingBookBean> searchChangeStandingBook(HTWholeProcessChangeStandingBookBean param);

    int countChangeStandingBook(HTWholeProcessChangeStandingBookBean param);

    List<Map<String, Object>> searchExportData(String projectId);

}
