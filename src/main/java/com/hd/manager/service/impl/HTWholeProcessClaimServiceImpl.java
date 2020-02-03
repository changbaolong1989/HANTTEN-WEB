package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProContractEntityMapper;
import com.hd.common.dao.TbProcessClaimMapper;
import com.hd.common.entity.TbProContractEntity;
import com.hd.common.entity.TbProClaimEntity;
import com.hd.common.entity.TbProContractEntityExample;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessClaimDao;
import com.hd.manager.dao.bean.HTWholeProcessClaimBean;
import com.hd.manager.service.HTWholeProcessClaimService;
import com.hd.manager.vo.HTWholeProcessClaimVO;
import com.hd.manager.vo.HTWholeProcessContractVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 全过程索赔台账 业务层 实现类
 *
 * @author LH
 * Created in 2019/9/11 15:31
 */
@Service
public class HTWholeProcessClaimServiceImpl extends BaseServiceImpl implements HTWholeProcessClaimService {




    /**
     * 索赔台账dao 持久层资源
     */
    @Resource
    private HTWholeProcessClaimDao htWholeProcessClaimDao;



    /**
     * 索赔台账Mapper 持久层资源
     */
    @Resource
    private TbProcessClaimMapper tbProClaimMapper;


    /**
     *
     */
    @Resource
    private TbProContractEntityMapper tbProContractEntityMapper;


    /**
     * 查询项目索赔台账阶段列表
     */
    @Override
    public Map<String,Object> excSearchProjectClaimPeriodList(HTWholeProcessClaimVO paramVO)  throws LogicException,SystemException {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            HTWholeProcessClaimBean paramBean = new HTWholeProcessClaimBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //查询项目索赔台账阶段列表
            List<HTWholeProcessClaimBean> htWholeProcessClaimBeanList = htWholeProcessClaimDao.excSearchProClaimPeriodList(paramBean);
            //查询项目索赔台账阶段列表
            Integer count = htWholeProcessClaimDao.excSearchProClaimPeriodListCount(paramBean);
            List<HTWholeProcessClaimVO> htWholeProcessClaimVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessClaimBean htWholeProcessClaimBean:htWholeProcessClaimBeanList){
                //转换格式
                HTWholeProcessClaimVO  htWholeProcessClaimVO = (HTWholeProcessClaimVO) ObjectReflectUtils.convertObj(htWholeProcessClaimBean, HTWholeProcessClaimVO.class);
                htWholeProcessClaimVOList.add(htWholeProcessClaimVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY,htWholeProcessClaimVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 查询项目索赔台账信息详情
     */
    @Override
    public HTWholeProcessClaimVO excSearchProjectClaimPeriodInfo(HTWholeProcessClaimVO paramVO)  throws LogicException,SystemException {
        try {
            HTWholeProcessClaimBean paramBean = new HTWholeProcessClaimBean();
            //设置项目ID
            HTWholeProcessClaimBean htWholeProcessClaimBean = htWholeProcessClaimDao.excSearchProClaimPeriodInfoByPrimaryKey(paramVO.getClaimId());
            //格式转换
            HTWholeProcessClaimVO  htWholeProcessClaimVO = (HTWholeProcessClaimVO) ObjectReflectUtils.convertObj(htWholeProcessClaimBean, HTWholeProcessClaimVO.class);
            return htWholeProcessClaimVO;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 修改索赔台账信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUpdateWholeProcessClaim(HTWholeProcessClaimVO paramVO)  throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            TbProClaimEntity paramEntity = new TbProClaimEntity();
            //合约ID
            paramEntity.setClaimId(paramVO.getClaimId());
            //合同ID
            paramEntity.setContractId(paramVO.getContractId());
            //申报金额
            paramEntity.setDeclareAmount(NumberToolUtils.convertNullToDouble(paramVO.getDeclareAmount()));
            //汉腾审核金额
            paramEntity.setHanttenAmount(NumberToolUtils.convertNullToDouble(paramVO.getHanttenAmount()));
            //已确认金额
            paramEntity.setConfirmAmount(NumberToolUtils.convertNullToDouble(paramVO.getConfirmAmount()));
            //预估金额
            paramEntity.setEstimatedAmount(NumberToolUtils.convertNullToDouble(paramVO.getEstimatedAmount()));
            //备注
            paramEntity.setRemark(paramVO.getRemark());
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            tbProClaimMapper.updateByPrimaryKeySelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }




    /**
     * 新增索赔台账信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertWholeProcessClaim(HTWholeProcessClaimVO paramVO)  throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            TbProClaimEntity paramEntity = new TbProClaimEntity();
            //项目ID
            paramEntity.setProjectId(paramVO.getProjectId());
            //合同ID
            paramEntity.setContractId(paramVO.getContractId());
            //申报金额
            paramEntity.setDeclareAmount(NumberToolUtils.convertNullToDouble(paramVO.getDeclareAmount()));
            //汉腾审核金额
            paramEntity.setHanttenAmount(NumberToolUtils.convertNullToDouble(paramVO.getHanttenAmount()));
            //已确认金额
            paramEntity.setConfirmAmount(NumberToolUtils.convertNullToDouble(paramVO.getConfirmAmount()));
            //预估金额
            paramEntity.setEstimatedAmount(NumberToolUtils.convertNullToDouble(paramVO.getEstimatedAmount()));
            //备注
            paramEntity.setRemark(paramVO.getRemark());
            //创建时间
            paramEntity.setCreateDate(currentDate);
            //创建人
            paramEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            tbProClaimMapper.insertSelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }


    /**
     * 查询合同列表信息
     *
     * @return
     */
    @Override
    public List<HTWholeProcessContractVO> excSearchContractList(HTWholeProcessContractVO paramVO)  throws LogicException,SystemException{

        TbProContractEntityExample example = new TbProContractEntityExample();
        example.createCriteria().andProjectIdEqualTo(paramVO.getProjectId());

        List<TbProContractEntity> contractEntityList = tbProContractEntityMapper.selectByExample(example);
        List<HTWholeProcessContractVO> resultList = new ArrayList<>();
        //格式转换
        for (TbProContractEntity tbProContractEntity : contractEntityList ){
            HTWholeProcessContractVO htWholeProcessContractVO = new HTWholeProcessContractVO();
            //合同ID
            htWholeProcessContractVO.setContractId(tbProContractEntity.getContractId());
            //合同名称
            htWholeProcessContractVO.setContractName(tbProContractEntity.getContractName());
            //合同编号
            htWholeProcessContractVO.setContractNum(tbProContractEntity.getContractNum());
            //合同类型
            htWholeProcessContractVO.setContractClass(tbProContractEntity.getContractClass());
            //合同分类
            htWholeProcessContractVO.setTreatyType(tbProContractEntity.getTreatyType());

            htWholeProcessContractVO.setUndertakeType(tbProContractEntity.getUndertakeType());

            htWholeProcessContractVO.setContractUnit(tbProContractEntity.getContractUnit());
            resultList.add(htWholeProcessContractVO);
        }
        return resultList;
    }


    /**
     *
     */

    @Override
    public List<HTWholeProcessClaimVO> excSearchProjectClaimPeriodListForExport(HTWholeProcessClaimVO paramVO) throws LogicException,SystemException{
        try {
            HTWholeProcessClaimBean paramBean = new HTWholeProcessClaimBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            //查询项目合约规划列表
            List<HTWholeProcessClaimBean> htWholeProcessClaimBeanList = htWholeProcessClaimDao.excSearchProClaimPeriodListForExport(paramBean);
            List<HTWholeProcessClaimVO> htWholeProcessTreatyVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessClaimBean htWholeProcessClaimBean:htWholeProcessClaimBeanList){
                HTWholeProcessClaimVO htWholeProcessClaimVO = new HTWholeProcessClaimVO();
                // 合约类型 1：工程类合同、2：采购类合同、3：服务类合同、4：土地类合同、5：其他类合同
                String tmpTreatyType = StringToolUtils.convertNullObjectToString(htWholeProcessClaimBean.getTreatyType());
                htWholeProcessClaimVO.setTreatyTypeName(tmpTreatyType.equals("1")?"工程类合同":(tmpTreatyType.equals("2")?"采购类合同":(tmpTreatyType.equals("3")?"服务类合同":(tmpTreatyType.equals("4")?"土地类合同":(tmpTreatyType.equals("5")?"其他类合同":"-")))));
                htWholeProcessClaimVO.setTreatyType(tmpTreatyType);
                htWholeProcessClaimVO.setCurrentLevel(Constant.FIRST_LEVEL_MENU_FLAG);
                List<HTWholeProcessClaimBean> undertakeTypeBeanList = htWholeProcessClaimBean.getChildList();
                List<HTWholeProcessClaimVO> undertakeTypeVOList = new ArrayList<>();
                for (HTWholeProcessClaimBean undertakeTypeBean:undertakeTypeBeanList){
                    HTWholeProcessClaimVO undertakeTypeVO = new HTWholeProcessClaimVO();
                    if (tmpTreatyType.equals("1")){
                        // 承包方式 1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业
                        String tmpUndertakeType = StringToolUtils.convertNullObjectToString(undertakeTypeBean.getUndertakeType());
                        undertakeTypeVO.setUndertakeTypeName(tmpUndertakeType.equals("2")?"发包人发包专业合同":(tmpUndertakeType.equals("3")?"发包人自行发包专业":(tmpUndertakeType.equals("1")?"施工总承包":"-")));
                        undertakeTypeVO.setUndertakeType(tmpUndertakeType);
                    }
                    undertakeTypeVO.setTreatyType(tmpTreatyType);
                    undertakeTypeVO.setCurrentLevel(Constant.SECOND_LEVEL_MENU_FLAG);
                    List<HTWholeProcessClaimBean> childBeanList = undertakeTypeBean.getChildList();
                    List<HTWholeProcessClaimVO> childVOList = new ArrayList<>();
                    for (HTWholeProcessClaimBean childDataBean:childBeanList){
                        //转换格式
                        HTWholeProcessClaimVO  childDataVO = (HTWholeProcessClaimVO) ObjectReflectUtils.convertObj(childDataBean, HTWholeProcessClaimVO.class);
                       // 合同类型（1：单价合同，2：总价合同，3：其他）
                        String tmpContractType = StringToolUtils.convertNullObjectToString(childDataBean.getContractClass());
                        childDataVO.setContractClass(tmpContractType.equals("1")?"单价合同":(tmpContractType.equals("2")?"总价合同":(tmpContractType.equals("3")?"其他":"-")));
                        childDataVO.setWithTaxMoney(this.getNumberStr(StringToolUtils.isNull(childDataVO.getWithTaxMoney())?"0.0":childDataVO.getWithTaxMoney()));
                        childDataVO.setHanttenAmount(this.getNumberStr(StringToolUtils.isNull(childDataVO.getHanttenAmount())?"0.0":childDataVO.getHanttenAmount()));
                        childDataVO.setDeclareAmount(this.getNumberStr(StringToolUtils.isNull(childDataVO.getDeclareAmount())?"0.0":childDataVO.getDeclareAmount()));
                        childDataVO.setEstimatedAmount(this.getNumberStr(StringToolUtils.isNull(childDataVO.getEstimatedAmount())?"0.0":childDataVO.getEstimatedAmount()));
                        childDataVO.setConfirmAmount(this.getNumberStr(childDataVO.getConfirmAmount()));
                        childDataVO.setCurrentLevel(Constant.THIRD_LEVEL_MENU_FLAG);
                        childVOList.add(childDataVO);
                    }
                    undertakeTypeVO.setChildList(childVOList);
                    undertakeTypeVOList.add(undertakeTypeVO);
                    htWholeProcessClaimVO.setChildList(undertakeTypeVOList);
                }
                htWholeProcessTreatyVOList.add(htWholeProcessClaimVO);
            }
            return htWholeProcessTreatyVOList;
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
        BigDecimal bd = new BigDecimal(str);
        return bd.toPlainString();
    }
}
