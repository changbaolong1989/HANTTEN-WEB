package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;

public interface HTTenderTaskPeriodMaterialDao extends BaseDao {

    /**
     * 删除投标文件通过投标任务和阶段的关联ID
     */
    public void excDeleteTenderTaskPeriodMaterialsByTenderTaskPeriodId(String tenderTaskPeriodId);
}
