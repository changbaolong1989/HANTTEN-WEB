package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTProAlterDetailVO;

import java.util.List;
import java.util.Map;

public interface HTWholeProcessChangeDetailService extends BaseService {

    Map<String, Object> excSearchChangeDetailList(HTProAlterDetailVO paramVO) throws LogicException, SystemException;

    void saveChangeDetail(HTProAlterDetailVO paramVO) throws LogicException, SystemException;

    Map<String, Object> excSearchContractInfo(HTProAlterDetailVO paramVO) throws LogicException, SystemException;

    void updateChangeDetail(HTProAlterDetailVO paramVO) throws LogicException, SystemException;

    List<Map<String, Object>> getExportData(String projectId);
}

