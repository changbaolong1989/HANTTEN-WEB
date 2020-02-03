package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.common.entity.TbProContractEntity;
import com.hd.manager.vo.HTWholeProcessContractVO;

import java.util.List;
import java.util.Map;

public interface HTWholeProcessContractService extends BaseService {

    /**
     * 查询列表数据
     */
    Map<String, Object> excSearchProjectAssessPeriodList(HTWholeProcessContractVO paramVO) throws LogicException, SystemException;

    void saveWholeProcessContract(HTWholeProcessContractVO paramVO) throws LogicException, SystemException;

    Map<String, Object> excSearchTenderInfo(HTWholeProcessContractVO paramVO) throws LogicException, SystemException;

    void updateWholeProcessContract(HTWholeProcessContractVO paramVO) throws LogicException, SystemException;

    List<Map<String, Object>> getExportData(String projectId);

    void savePredictCloseValue(TbProContractEntity paramEntity);


}
