package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProAlterDetailEntityMapper;
import com.hd.common.dao.TbProContractEntityMapper;
import com.hd.common.entity.TbProAlterDetailEntity;
import com.hd.common.entity.TbProAlterDetailEntityExample;
import com.hd.common.entity.TbProContractEntity;
import com.hd.common.entity.TbProContractEntityExample;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessChangeDetailDao;
import com.hd.manager.dao.bean.HTProAlterDetaiBean;
import com.hd.manager.service.HTWholeProcessChangeDetailService;
import com.hd.manager.vo.HTProAlterDetailVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HTWholeProcessChangeDetailServiceImpl extends BaseServiceImpl implements HTWholeProcessChangeDetailService {

    @Resource
    private TbProAlterDetailEntityMapper alterDetailDao;

    @Resource
    private TbProContractEntityMapper contractDao;

    @Resource
    private HTWholeProcessChangeDetailDao changeDetailDao;

    @Override
    public Map<String, Object> excSearchChangeDetailList(HTProAlterDetailVO paramVO) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HTProAlterDetaiBean paramBean = (HTProAlterDetaiBean) ObjectReflectUtils.convertObj(paramVO, HTProAlterDetaiBean.class);
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            TbProAlterDetailEntityExample example = new TbProAlterDetailEntityExample();
            example.createCriteria().andProjectIdEqualTo(paramBean.getProjectId());
            long count = alterDetailDao.countByExample(example);
            paramBean.setOrderByClause(" professional_id ");
            List<HTProAlterDetaiBean> list = changeDetailDao.searchChangeDetailList(paramBean);
            resultMap.put(Constant.DATA_LIST_KEY, list);
            resultMap.put(Constant.DATA_COUNT_KEY, count);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    @Override
    public void saveChangeDetail(HTProAlterDetailVO paramVO) throws LogicException, SystemException {
        TbProAlterDetailEntity paramBean = (TbProAlterDetailEntity) ObjectReflectUtils.convertObj(paramVO, TbProAlterDetailEntity.class);
        paramBean.setAlterId(UUID.randomUUID().toString());
        alterDetailDao.insertSelective(paramBean);
    }

    @Override
    public Map<String, Object> excSearchContractInfo(HTProAlterDetailVO paramVO) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String projectId = paramVO.getProjectId();
            TbProContractEntityExample example = new TbProContractEntityExample();
            example.createCriteria().andProjectIdEqualTo(projectId);
            List<TbProContractEntity> list = contractDao.selectByExample(example);
            resultMap.put(Constant.DATA_LIST_KEY, list);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    @Override
    public void updateChangeDetail(HTProAlterDetailVO paramVO) throws LogicException, SystemException {
        TbProAlterDetailEntity alterDetailEntity = (TbProAlterDetailEntity) ObjectReflectUtils.convertObj(paramVO, TbProAlterDetailEntity.class);
        TbProAlterDetailEntityExample example = new TbProAlterDetailEntityExample();
        example.createCriteria().andAlterIdEqualTo(alterDetailEntity.getAlterId());
        alterDetailDao.updateByExample(alterDetailEntity, example);
    }

    @Override
    public List<Map<String, Object>> getExportData(String projectId) {
        List<Map<String, Object>> maps = changeDetailDao.searchExportData(projectId);
        for (int i = 0; i < maps.size(); i++) {
//            String sts = (String) maps.get(i).get("sts");
//            maps.get(i).put("sts",StringToolUtils.isNull(sts)?"":
//                    sts.equals("A")?"业主审批金额":
//                            sts.equals("B")?"汉腾与承包商已核对且无争议的变更洽商金额":
//                                    sts.equals("C")?"汉腾与承包商已核对且有争议的变更洽商金额":
//                                            sts.equals("D")?"汉腾已审核完毕尚未与申报单位核对金额":
//                                                    sts.equals("E")?"汉腾正在审核的变更金额":
//                                                            sts.equals("F")?"承包商申报资料不全或尚未申报费用":
//                                                                    sts.equals("G")?"预计会发生，但无变更资料":"");
            String alterAnalyse = (String) maps.get(i).get("alter_analyse");
            maps.get(i).put("alter_analyse",StringToolUtils.isNull(alterAnalyse)?"":alterAnalyse.equals("1")?"调差":
                    alterAnalyse.equals("2")?"业主要求":alterAnalyse.equals("3")?"设计要求":
                            alterAnalyse.equals("4")?"法规要求":alterAnalyse.equals("5")?"设计/合同错误或不足":
                                    alterAnalyse.equals("6")?"现场不可预见得情况":alterAnalyse.equals("7")?"深化设计/协调":
                                            alterAnalyse.equals("8")?"一般修改":alterAnalyse.equals("9")?"其他":"");
        }
        return ExcelUtils.assesChangeDetail(maps);
    }

}
