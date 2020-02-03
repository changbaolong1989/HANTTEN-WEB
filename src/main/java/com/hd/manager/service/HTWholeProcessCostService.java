package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTWholeProcessContractVO;

import java.util.List;
import java.util.Map;

public interface HTWholeProcessCostService extends BaseService {

    Map<String, Object> excSearchCostList(HTWholeProcessContractVO paramVO) throws LogicException, SystemException;

    void updateStatement(HTWholeProcessContractVO paramVO) throws LogicException, SystemException;

    List<Map<String, Object>> getExportData(String projectId);

}
