package com.hd.common.service;

import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.dao.bean.HTDictCounselTypeBean;
import com.hd.manager.vo.HTDictCounselTypeVO;

import java.util.List;

public interface CommonDictCounselTypeService extends BaseService {
    /**
     * 根据合同类型ID查询咨询类别状态列表
     * @param contractTypeId 合同类型ID
     * @return 咨询类别状态列表
     */
    public List<HTDictCounselTypeVO> findDictCounselTypeListByContractTypeId (String contractTypeId);

    /**
     * 根据部门ID查询任务阶段信息
     * @param htDictCounselTypeBean 部门ID、所属高管
     * @return 任务阶段信息列表
     * @throws SystemException 系统异常
     */
    List<HTDictCounselTypeVO> excSearchTaskPeriodListByDepartmentId(HTDictCounselTypeBean htDictCounselTypeBean) throws SystemException;
}
