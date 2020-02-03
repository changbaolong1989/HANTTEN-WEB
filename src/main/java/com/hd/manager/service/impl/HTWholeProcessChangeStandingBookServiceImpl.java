package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessChangeStandingBookDao;
import com.hd.manager.dao.bean.HTWholeProcessChangeStandingBookBean;
import com.hd.manager.service.HTWholeProcessChangeStandingBookService;
import com.hd.manager.vo.HTWholeProcessChangeStandingBookVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HTWholeProcessChangeStandingBookServiceImpl extends BaseServiceImpl implements HTWholeProcessChangeStandingBookService {

    @Resource
    private HTWholeProcessChangeStandingBookDao changeStandingBookDao;

    @Override
    public Map<String, Object> excSearchChangeStandingBook(HTWholeProcessChangeStandingBookVO param) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        HTWholeProcessChangeStandingBookBean bean = (HTWholeProcessChangeStandingBookBean) ObjectReflectUtils.convertObj(param, HTWholeProcessChangeStandingBookBean.class);
        bean = BusinessUtils.computeAndPutRowNumFromVOToBean(param, bean);
        try {
            int count = changeStandingBookDao.countChangeStandingBook(bean);
            List<HTWholeProcessChangeStandingBookBean> list = changeStandingBookDao.searchChangeStandingBook(bean);
            resultMap.put(Constant.DATA_LIST_KEY, list);
            resultMap.put(Constant.DATA_COUNT_KEY, count);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> getExportData(String projectId) {
        return ExcelUtils.changeStructure(changeStandingBookDao.searchExportData(projectId));
    }
}
