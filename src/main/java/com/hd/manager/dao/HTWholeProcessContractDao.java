package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.entity.TbContractSaveMaterialRelationEntity;
import com.hd.manager.dao.bean.HTContractBean;
import com.hd.manager.dao.bean.HTContractTaskHisTaskInstBean;

import java.util.List;
import java.util.Map;

public interface HTWholeProcessContractDao extends BaseDao {

    List<Map<String, Object>> searchExportData(String projectId);

}
