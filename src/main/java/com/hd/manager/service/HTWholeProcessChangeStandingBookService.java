package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTWholeProcessChangeStandingBookVO;

import java.util.List;
import java.util.Map;

public interface HTWholeProcessChangeStandingBookService extends BaseService {

    Map<String, Object> excSearchChangeStandingBook(HTWholeProcessChangeStandingBookVO param) throws LogicException, SystemException;

    List<Map<String, Object>> getExportData(String projectId);

}
