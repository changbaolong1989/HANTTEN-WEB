package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTDictCounselTypeBean;

import java.util.List;

public interface HTDictCounselTypeDao extends BaseDao {
    /**
     * 根据合同类型ID查询咨询类别状态列表
     * @return 咨询类别状态列表
     */
    List<HTDictCounselTypeBean> findDictCounselTypeListByContractTypeId(String contractTypeId);

    /**
     * 根据部门ID查询任务阶段信息
     * @return 任务阶段信息列表
     */
    List<HTDictCounselTypeBean> excSearchTaskPeriodListByDepartmentId(HTDictCounselTypeBean htDictCounselTypeBean);
}
