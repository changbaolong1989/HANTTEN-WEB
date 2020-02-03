package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProcessPayMapper;
import com.hd.common.entity.TbProPayEntity;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessPayDao;
import com.hd.manager.dao.bean.HTWholeProcessPayBean;
import com.hd.manager.service.HTWholeProcessPayService;
import com.hd.manager.vo.HTWholeProcessPayVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 全过程付款台账列表 业务层 实现类
 *
 * @author LH
 * Created in 2019/9/11 15:31
 */
@Service
public class HTWholeProcessPayServiceImpl extends BaseServiceImpl implements HTWholeProcessPayService {




    /**
     * 付款台账dao 持久层资源
     */
    @Resource
    private HTWholeProcessPayDao htWholeProcessPayDao;



    /**
     * 付款台账Mapper 持久层资源
     */
    @Resource
    private TbProcessPayMapper tbProPayMapper;




    /**
     * 查询项目付款台账阶段列表
     */
    @Override
    public Map<String,Object> excSearchProjectPayPeriodList(HTWholeProcessPayVO paramVO)  throws LogicException,SystemException {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            HTWholeProcessPayBean paramBean = new HTWholeProcessPayBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //查询项目付款台账阶段列表
            List<HTWholeProcessPayBean> htWholeProcessPayBeanList = htWholeProcessPayDao.excSearchProPayPeriodList(paramBean);
            //查询项目付款台账阶段列表
            Integer count = htWholeProcessPayDao.excSearchProPayPeriodListCount(paramBean);
            List<HTWholeProcessPayVO> htWholeProcessPayVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessPayBean htWholeProcessPayBean:htWholeProcessPayBeanList){
                //转换格式
                HTWholeProcessPayVO  htWholeProcessPayVO = (HTWholeProcessPayVO) ObjectReflectUtils.convertObj(htWholeProcessPayBean, HTWholeProcessPayVO.class);
                htWholeProcessPayVO.setEditDate(DateToolUtils.convertDateFormat(htWholeProcessPayBean.getEditDate(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessPayVOList.add(htWholeProcessPayVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY,htWholeProcessPayVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 查询项目付款台账信息详情
     */
    @Override
    public HTWholeProcessPayVO excSearchProjectPayPeriodInfo(HTWholeProcessPayVO paramVO)  throws LogicException,SystemException {
        try {
            HTWholeProcessPayBean paramBean = new HTWholeProcessPayBean();
            //设置项目ID
            HTWholeProcessPayBean htWholeProcessPayBean = htWholeProcessPayDao.excSearchProPayPeriodInfoByPrimaryKey(paramVO.getPayId());
            //格式转换
            HTWholeProcessPayVO  htWholeProcessPayVO = (HTWholeProcessPayVO) ObjectReflectUtils.convertObj(htWholeProcessPayBean, HTWholeProcessPayVO.class);
            return htWholeProcessPayVO;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     *  查询所含期数（导出）
     */
    @Override
    public List<String> excSearchProPayPeriodNumListForExport(HTWholeProcessPayVO paramVO)  throws LogicException,SystemException {
        try {
            HTWholeProcessPayBean paramBean = new HTWholeProcessPayBean();
            paramBean.setProjectId(paramVO.getProjectId());
            //设置项目ID
            List<String> periodNumList = htWholeProcessPayDao.excSearchProPayPeriodNumListForExport(paramBean);
            return periodNumList;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 修改付款台账信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUpdateWholeProcessPay(HTWholeProcessPayVO paramVO)  throws LogicException,SystemException {
        try {
            HTWholeProcessPayBean htWholeProcessPayBean = new HTWholeProcessPayBean();
            //合同ID
            htWholeProcessPayBean.setContractId(paramVO.getContractId());
            //期数
            htWholeProcessPayBean.setPeriodNum(paramVO.getPeriodNum());
            //唯一标识
            htWholeProcessPayBean.setPayId(paramVO.getPayId());
            Integer count = htWholeProcessPayDao.excSearchProPayPeriodCountByProperty(htWholeProcessPayBean);
            if (count>0){
                throw new LogicException(MessageConstant.MSG_ERROR_PROCESS_PAY_0001);
            }
            Date currentDate = new Date();

            TbProPayEntity paramEntity = new TbProPayEntity();
            //合约ID
            paramEntity.setPayId(paramVO.getPayId());
            //合同ID
            paramEntity.setContractId(paramVO.getContractId());
            //期数
            paramEntity.setPeriodNum(paramVO.getPeriodNum());
            //本期产值
            paramEntity.setCurrentValue(NumberToolUtils.convertNullToDouble(paramVO.getCurrentValue()));
            //本期应付
            paramEntity.setCurrentPayment(NumberToolUtils.convertNullToDouble(paramVO.getCurrentPayment()));
            //日期
            paramEntity.setEditDate(DateToolUtils.convertDateFormat(paramVO.getEditDate(),DateToolUtils.yyyy_MM_dd));
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            tbProPayMapper.updateByPrimaryKeySelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }




    /**
     * 新增付款台账信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertWholeProcessPay(HTWholeProcessPayVO paramVO)  throws LogicException,SystemException {
        try {
            HTWholeProcessPayBean htWholeProcessPayBean = new HTWholeProcessPayBean();
            htWholeProcessPayBean.setContractId(paramVO.getContractId());
            htWholeProcessPayBean.setPeriodNum(paramVO.getPeriodNum());
            Integer count = htWholeProcessPayDao.excSearchProPayPeriodCountByProperty(htWholeProcessPayBean);
            if (count>0){
                throw new LogicException(MessageConstant.MSG_ERROR_PROCESS_PAY_0001);
            }
            Date currentDate = new Date();
            TbProPayEntity paramEntity = new TbProPayEntity();
            //项目ID
            paramEntity.setProjectId(paramVO.getProjectId());
            //合同ID
            paramEntity.setContractId(paramVO.getContractId());
            //期数
            paramEntity.setPeriodNum(paramVO.getPeriodNum());
            //本期产值
            paramEntity.setCurrentValue(NumberToolUtils.convertNullToDouble(paramVO.getCurrentValue()));
            //本期应付
            paramEntity.setCurrentPayment(NumberToolUtils.convertNullToDouble(paramVO.getCurrentPayment()));
            //日期
            paramEntity.setEditDate(DateToolUtils.convertDateFormat(paramVO.getEditDate(),DateToolUtils.yyyy_MM_dd));
            //创建时间
            paramEntity.setCreateDate(currentDate);
            //创建人
            paramEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //是否删除
            paramEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
            tbProPayMapper.insertSelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询列表（导出）
     *
     * @return
     */
    @Override
    public List<HTWholeProcessPayVO> excSearchProjectPayPeriodListForExport(HTWholeProcessPayVO paramVO) throws SystemException {
        try {
            HTWholeProcessPayBean paramBean = new HTWholeProcessPayBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            //查询项目合约规划列表
            List<HTWholeProcessPayBean> htWholeProcessPayBeanList = htWholeProcessPayDao.excSearchProPayPeriodListForExport(paramBean);
            List<HTWholeProcessPayVO> htWholeProcessPayVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessPayBean htWholeProcessPayBean:htWholeProcessPayBeanList){
                HTWholeProcessPayVO htWholeProcessPayVO = new HTWholeProcessPayVO();
                // 合约类型 1：工程类合同、2：采购类合同、3：服务类合同、4：土地类合同、5：其他类合同
                String tmpTreatyType = StringToolUtils.convertNullObjectToString(htWholeProcessPayBean.getTreatyType());
                htWholeProcessPayVO.setTreatyTypeName(tmpTreatyType.equals("1")?"工程类合同":(tmpTreatyType.equals("2")?"采购类合同":(tmpTreatyType.equals("3")?"服务类合同":(tmpTreatyType.equals("4")?"土地类合同":(tmpTreatyType.equals("5")?"其他类合同":"-")))));
                htWholeProcessPayVO.setTreatyType(tmpTreatyType);
                htWholeProcessPayVO.setCurrentLevel(Constant.FIRST_LEVEL_MENU_FLAG);
                List<HTWholeProcessPayBean> undertakeTypeBeanList = htWholeProcessPayBean.getChildList();
                List<HTWholeProcessPayVO> undertakeTypeVOList = new ArrayList<>();
                for (HTWholeProcessPayBean undertakeTypeBean:undertakeTypeBeanList){
                    HTWholeProcessPayVO undertakeTypeVO = new HTWholeProcessPayVO();
                    if (tmpTreatyType.equals("1")){
                        // 承包方式 1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业
                        String tmpUndertakeType = StringToolUtils.convertNullObjectToString(undertakeTypeBean.getUndertakeType());
                        undertakeTypeVO.setUndertakeTypeName(tmpUndertakeType.equals("2")?"发包人发包专业合同":(tmpUndertakeType.equals("3")?"发包人自行发包专业":(tmpUndertakeType.equals("1")?"施工总承包":"-")));
                        undertakeTypeVO.setUndertakeType(tmpUndertakeType);
                    }
                    undertakeTypeVO.setTreatyType(tmpTreatyType);
                    undertakeTypeVO.setCurrentLevel(Constant.SECOND_LEVEL_MENU_FLAG);
                    List<HTWholeProcessPayBean> childBeanList = undertakeTypeBean.getChildList();
                    List<HTWholeProcessPayVO> childVOList = new ArrayList<>();
                    for (HTWholeProcessPayBean childDataBean:childBeanList){
                        //转换格式
                        HTWholeProcessPayVO  childDataVO = new HTWholeProcessPayVO();
                        childDataVO.setContractId(childDataBean.getContractId());
                        childDataVO.setContractNum(childDataBean.getContractNum());
                        childDataVO.setContractName(childDataBean.getContractName());
                        childDataVO.setContractUnit(childDataBean.getContractUnit());
                        childDataVO.setPredictCloseValue(childDataBean.getPredictCloseValue());
                        // 合同类型（1：单价合同，2：总价合同，3：其他）
                        String tmpContractType = StringToolUtils.convertNullObjectToString(childDataBean.getContractClass());
                        childDataVO.setContractClass(tmpContractType.equals("1")?"单价合同":(tmpContractType.equals("2")?"总价合同":(tmpContractType.equals("3")?"其他":"-")));
                        childDataVO.setWithTaxMoney(this.getNumberStr(null == childDataBean.getWithTaxMoney()?"0":String.valueOf(childDataBean.getWithTaxMoney())));
                        childDataVO.setCurrentLevel(Constant.THIRD_LEVEL_MENU_FLAG);
                        List<HTWholeProcessPayBean> periodBeanList = childDataBean.getChildList();
                        List<HTWholeProcessPayVO> periodVOList = new ArrayList<>();
                        for (HTWholeProcessPayBean periodBean:periodBeanList){
                            //转换格式
                            HTWholeProcessPayVO  periodVO = new HTWholeProcessPayVO();
                            periodVO.setPayId(periodBean.getPayId());
                            periodVO.setPeriodNum(periodBean.getPeriodNum());
                            periodVO.setCurrentValue(this.getNumberStr(null == periodBean.getCurrentValue()?"0":String.valueOf(periodBean.getCurrentValue())));
                            periodVO.setCurrentPayment(this.getNumberStr(null == periodBean.getCurrentPayment()?"0":String.valueOf(periodBean.getCurrentPayment())));
                            periodVO.setEditDate(DateToolUtils.convertDateFormat(periodBean.getEditDate(),DateToolUtils.YYYY_MM_DD_CHINESE));
                            periodVOList.add(periodVO);
                        }
                        childDataVO.setChildList(periodVOList);
                        childVOList.add(childDataVO);
                    }
                    undertakeTypeVO.setChildList(childVOList);
                    undertakeTypeVOList.add(undertakeTypeVO);
                    htWholeProcessPayVO.setChildList(undertakeTypeVOList);
                }
                htWholeProcessPayVOList.add(htWholeProcessPayVO);
            }
            return htWholeProcessPayVOList;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 将科学计数法的字符串传入
     * @param str
     * @return 返回String类型
     */
    private String getNumberStr(String str){
        String result = "";
        if (!StringToolUtils.isNull(str)){
            BigDecimal bd = new BigDecimal(str);
            result = bd.toPlainString();
        }
        return result;
    }
}
