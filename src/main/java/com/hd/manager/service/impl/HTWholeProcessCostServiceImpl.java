package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProContractEntityMapper;
import com.hd.common.entity.TbProContractEntity;
import com.hd.common.entity.TbProContractEntityExample;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessCostDao;
import com.hd.manager.dao.bean.HTWholeProcessContractBean;
import com.hd.manager.service.HTWholeProcessContractService;
import com.hd.manager.service.HTWholeProcessCostService;
import com.hd.manager.vo.HTWholeProcessContractVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HTWholeProcessCostServiceImpl extends BaseServiceImpl implements HTWholeProcessCostService {

    @Resource
    private TbProContractEntityMapper contractDao;
    @Resource
    private HTWholeProcessContractService contractService;

    @Resource
    private HTWholeProcessCostDao costDao;

    @Override
    public Map<String, Object> excSearchCostList(HTWholeProcessContractVO paramVO) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HTWholeProcessContractBean paramBean = (HTWholeProcessContractBean) ObjectReflectUtils.convertObj(paramVO, HTWholeProcessContractBean.class);
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            TbProContractEntityExample example = new TbProContractEntityExample();
            example.createCriteria().andProjectIdEqualTo(paramBean.getProjectId());
            long count = contractDao.countByExample(example);
            paramBean.setOrderByClause(" T.treaty_type, T.undertake_type, T.contract_num ");
            List<HTWholeProcessContractBean> list = costDao.searchCostList(paramBean);
            resultMap.put(Constant.DATA_LIST_KEY, list);
            resultMap.put(Constant.DATA_COUNT_KEY, count);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    @Override
    public void updateStatement(HTWholeProcessContractVO paramVO) throws LogicException, SystemException {
        TbProContractEntity contractEntity = (TbProContractEntity) ObjectReflectUtils.convertObj(paramVO, TbProContractEntity.class);
        TbProContractEntityExample example = new TbProContractEntityExample();
        example.createCriteria().andContractIdEqualTo(contractEntity.getContractId());
        contractDao.updateByExampleSelective(contractEntity, example);
        contractService.savePredictCloseValue(contractEntity);
    }

    @Override
    public List<Map<String, Object>> getExportData(String projectId) {
        return ExcelUtils.changeStructure(costDao.searchExportData(projectId));
    }
}
