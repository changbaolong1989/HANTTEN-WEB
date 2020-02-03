package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProStatementEntityMapper;
import com.hd.common.entity.TbProStatementEntity;
import com.hd.common.entity.TbProStatementEntityExample;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessStatementDao;
import com.hd.manager.dao.bean.HTProStatementBean;
import com.hd.manager.service.HTWholeProcessStatementService;
import com.hd.manager.vo.HTProStatementVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HTWholeProcessStatementServiceImpl extends BaseServiceImpl implements HTWholeProcessStatementService {

    @Resource
    private TbProStatementEntityMapper statementDao;

    @Resource
    private HTWholeProcessStatementDao htStatementDao;


    @Override
    public Map<String, Object> excSearchWholeProcessStatement(HTProStatementVO paramVO) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HTProStatementBean paramBean = (HTProStatementBean) ObjectReflectUtils.convertObj(paramVO, HTProStatementBean.class);
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            TbProStatementEntityExample example = new TbProStatementEntityExample();
            example.createCriteria().andProjectIdEqualTo(paramBean.getProjectId());
            long count = statementDao.countByExample(example);
            paramBean.setOrderByClause(" T1.treaty_type, T1.undertake_type, T1.contract_name ");
            List<HTProStatementBean> list = htStatementDao.searchWholeProcessStatement(paramBean);
            resultMap.put(Constant.DATA_LIST_KEY, list);
            resultMap.put(Constant.DATA_COUNT_KEY, count);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    @Override
    public void saveStatement(HTProStatementVO paramVO) throws LogicException, SystemException {
        TbProStatementEntity paramBean = (TbProStatementEntity) ObjectReflectUtils.convertObj(paramVO, TbProStatementEntity.class);
        paramBean.setStatementId(UUID.randomUUID().toString());
        statementDao.insertSelective(paramBean);
    }

    @Override
    public void updateStatement(HTProStatementVO paramVO) throws LogicException, SystemException {
        TbProStatementEntity alterDetailEntity = (TbProStatementEntity) ObjectReflectUtils.convertObj(paramVO, TbProStatementEntity.class);
        TbProStatementEntityExample example = new TbProStatementEntityExample();
        example.createCriteria().andStatementIdEqualTo(alterDetailEntity.getStatementId());
        statementDao.updateByExample(alterDetailEntity, example);
    }

    @Override
    public List<Map<String, Object>> getExportData(String projectId) {
        return ExcelUtils.changeStructure(htStatementDao.searchExportData(projectId));
    }

}

