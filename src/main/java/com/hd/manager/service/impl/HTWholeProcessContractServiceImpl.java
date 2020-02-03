package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProContractEntityMapper;
import com.hd.common.dao.TbProTenderEntityMapper;
import com.hd.common.entity.TbProContractEntity;
import com.hd.common.entity.TbProContractEntityExample;
import com.hd.common.entity.TbProTenderEntity;
import com.hd.common.entity.TbProTenderEntityExample;
import com.hd.common.util.*;
import com.hd.export.bean.ExcelData;
import com.hd.manager.dao.HTWholeProcessContractDao;
import com.hd.manager.dao.bean.HTWholeProcessContractBean;
import com.hd.manager.service.HTWholeProcessContractService;
import com.hd.manager.vo.HTWholeProcessContractVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class HTWholeProcessContractServiceImpl extends BaseServiceImpl implements HTWholeProcessContractService {

    @Resource
    private TbProContractEntityMapper contractDao;

    @Resource
    private TbProTenderEntityMapper tenderDao;

    @Resource
    private HTWholeProcessContractDao htWholeProcessContractDao;

    /**
     * 查询列表数据
     */
    @Override
    public Map<String, Object> excSearchProjectAssessPeriodList(HTWholeProcessContractVO paramVO) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HTWholeProcessContractBean paramBean = (HTWholeProcessContractBean) ObjectReflectUtils.convertObj(paramVO, HTWholeProcessContractBean.class);
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            TbProContractEntityExample example = new TbProContractEntityExample();
            example.createCriteria().andProjectIdEqualTo(paramVO.getProjectId());
            example.setOrderByClause(" treaty_type, undertake_type, contract_name");
            example.setLimitStart(paramBean.getPageNumber());
            example.setLimitEnd(paramBean.getPageSize());
            long count = contractDao.countByExample(example);
            List<TbProContractEntity> list = contractDao.selectByExample(example);
            resultMap.put(Constant.DATA_LIST_KEY, list);
            resultMap.put(Constant.DATA_COUNT_KEY, count);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    @Override
    public void saveWholeProcessContract(HTWholeProcessContractVO paramVO) throws LogicException, SystemException {
        TbProContractEntity paramBean = (TbProContractEntity) ObjectReflectUtils.convertObj(paramVO, TbProContractEntity.class);
        TbProContractEntityExample example = new TbProContractEntityExample();
        TbProContractEntityExample.Criteria criteria = example.createCriteria();
        criteria.andContractNumEqualTo(paramBean.getContractNum());
        criteria.andProjectIdEqualTo(paramBean.getProjectId());
        if (contractDao.countByExample(example) > 0) {
            throw new LogicException(MessageConstant.MSG_ERROR_WHOLE_PROCESS_CONTRACT_0001);
        }

        paramBean.setContractId(UUID.randomUUID().toString());
        contractDao.insertSelective(paramBean);
        this.savePredictCloseValue(paramBean);
    }

    @Override
    public void savePredictCloseValue(TbProContractEntity entity){
        TbProContractEntity paramEntity = contractDao.selectByPrimaryKey(entity.getContractId());
        Double result = paramEntity.getWithTaxMoney()+
                (null==paramEntity.getUncalibratedPrice()?0:paramEntity.getUncalibratedPrice())+
                (null==paramEntity.getTemporaryAmount()?0:paramEntity.getTemporaryAmount())+
                (null==paramEntity.getAlterValue()?0:paramEntity.getAlterValue())+
                (null==paramEntity.getDrawingAdjust()?0:paramEntity.getDrawingAdjust())+
                (null==paramEntity.getSignelAdjust()?0:paramEntity.getSignelAdjust())+
                (null==paramEntity.getMaterialAdjust()?0:paramEntity.getMaterialAdjust())+
                (null==paramEntity.getPeopleAdjust()?0:paramEntity.getPeopleAdjust())+
                (null==paramEntity.getOtherThing()?0:paramEntity.getOtherThing())+
                (null==paramEntity.getTaxAdjust()?0:paramEntity.getTaxAdjust());
        TbProContractEntity resultEntity = new TbProContractEntity();
        resultEntity.setContractId(entity.getContractId());
        resultEntity.setPredictCloseValue(result);
        contractDao.updateByPrimaryKeySelective(resultEntity);
    }


    @Override
    public Map<String, Object> excSearchTenderInfo(HTWholeProcessContractVO paramVO) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String projectId = paramVO.getProjectId();
            TbProTenderEntityExample example = new TbProTenderEntityExample();
            example.createCriteria().andProjectIdEqualTo(projectId);
            List<TbProTenderEntity> list = tenderDao.selectByExample(example);
            resultMap.put(Constant.DATA_LIST_KEY, list);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    @Override
    public void updateWholeProcessContract(HTWholeProcessContractVO paramVO) throws LogicException, SystemException {
        TbProContractEntity proContract = (TbProContractEntity) ObjectReflectUtils.convertObj(paramVO, TbProContractEntity.class);
        TbProContractEntityExample example = new TbProContractEntityExample();
        example.createCriteria().andContractIdEqualTo(proContract.getContractId());
        contractDao.updateByExample(proContract, example);
        this.savePredictCloseValue(proContract);
    }

    @Override
    public List<Map<String, Object>> getExportData(String projectId) {
        List<Map<String, Object>> dataList = htWholeProcessContractDao.searchExportData(projectId);
        return ExcelUtils.changeStructure(dataList);
    }

}
