package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTProStatementVO;

import java.util.List;
import java.util.Map;

public interface HTWholeProcessStatementService extends BaseService {

    Map<String, Object> excSearchWholeProcessStatement(HTProStatementVO paramVO) throws LogicException, SystemException;

    void saveStatement(HTProStatementVO paramVO) throws LogicException, SystemException;

    void updateStatement(HTProStatementVO paramVO) throws LogicException, SystemException;

    List<Map<String, Object>> getExportData(String projectId);

}
