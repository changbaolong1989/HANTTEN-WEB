package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProcessTreatyMapper;
import com.hd.common.entity.TbProTreatyEntity;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessTreatyDao;
import com.hd.manager.dao.bean.HTWholeProcessTreatyBean;
import com.hd.manager.service.HTWholeProcessTreatyService;
import com.hd.manager.vo.HTWholeProcessTreatyVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 全过程合约规划列表 业务层 实现类
 *
 * @author LH
 * Created in 2019/9/3 15:31
 */
@Service
public class HTWholeProcessTreatyServiceImpl extends BaseServiceImpl implements HTWholeProcessTreatyService {




    /**
     * 合约规划dao 持久层资源
     */
    @Resource
    private HTWholeProcessTreatyDao htWholeProcessTreatyDao;



    /**
     * 合约规划Mapper 持久层资源
     */
    @Resource
    private TbProcessTreatyMapper tbProTreatyMapper;




    /**
     * 查询项目合约规划阶段列表
     */
    @Override
    public Map<String,Object> excSearchProjectTreatyPeriodList(HTWholeProcessTreatyVO paramVO)  throws LogicException,SystemException {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            HTWholeProcessTreatyBean paramBean = new HTWholeProcessTreatyBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //查询项目合约规划阶段列表
            List<HTWholeProcessTreatyBean> htWholeProcessTreatyBeanList = htWholeProcessTreatyDao.excSearchProTreatyPeriodList(paramBean);
            //查询项目合约规划阶段列表
            Integer count = htWholeProcessTreatyDao.excSearchProTreatyPeriodListCount(paramBean);
            List<HTWholeProcessTreatyVO> htWholeProcessTreatyVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessTreatyBean htWholeProcessTreatyBean:htWholeProcessTreatyBeanList){
                //转换格式
                HTWholeProcessTreatyVO  htWholeProcessTreatyVO = (HTWholeProcessTreatyVO) ObjectReflectUtils.convertObj(htWholeProcessTreatyBean, HTWholeProcessTreatyVO.class);
                htWholeProcessTreatyVOList.add(htWholeProcessTreatyVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY,htWholeProcessTreatyVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 查询项目合约规划信息详情
     */
    @Override
    public HTWholeProcessTreatyVO excSearchProjectTreatyPeriodInfo(HTWholeProcessTreatyVO paramVO)  throws LogicException,SystemException {
        try {
            HTWholeProcessTreatyBean paramBean = new HTWholeProcessTreatyBean();
            //设置项目ID
            HTWholeProcessTreatyBean htWholeProcessTreatyBean = htWholeProcessTreatyDao.excSearchProTreatyPeriodInfoByPrimaryKey(paramVO.getTreatyId());
            //格式转换
            HTWholeProcessTreatyVO  htWholeProcessTreatyVO = (HTWholeProcessTreatyVO) ObjectReflectUtils.convertObj(htWholeProcessTreatyBean, HTWholeProcessTreatyVO.class);
            return htWholeProcessTreatyVO;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 修改合约规划信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUpdateWholeProcessTreaty(HTWholeProcessTreatyVO paramVO)  throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            TbProTreatyEntity paramEntity = new TbProTreatyEntity();
            //合约ID
            paramEntity.setTreatyId(paramVO.getTreatyId());
            //合约编号
            paramEntity.setTreatyNum(paramVO.getTreatyNum());
            //合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
            paramEntity.setTreatyType(paramVO.getTreatyType());
            if (paramVO.getTreatyType().equals("1")){
                //承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
                paramEntity.setUndertakeType(paramVO.getUndertakeType());
            }else {
                paramEntity.setUndertakeType("");
            }
            //合约名称
            paramEntity.setTreatyName(paramVO.getTreatyName());
            //合约内容及范围
            paramEntity.setTreatyContent(paramVO.getTreatyContent());
            //目标成本
            paramEntity.setTargetCost(NumberToolUtils.convertNullToDouble(paramVO.getTargetCost()));
            //合同类型（1：单价合同，2：总价合同，3：其他）
            paramEntity.setContractType(paramVO.getContractType());
            //合同确定方式（1：总承包，2：指定供货，3：指定分包，4：甲供，5：直接发包，6：三方协议）
            paramEntity.setContractWay(paramVO.getContractWay());
            //采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
            paramEntity.setPurchaserWay(paramVO.getPurchaserWay());
            //备注
            paramEntity.setRemark(paramVO.getRemark());
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            tbProTreatyMapper.updateByPrimaryKeySelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }




    /**
     * 新增合约规划信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertWholeProcessTreaty(HTWholeProcessTreatyVO paramVO)  throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            TbProTreatyEntity paramEntity = new TbProTreatyEntity();
            //项目ID
            paramEntity.setProjectId(paramVO.getProjectId());
            //合约编号
            paramEntity.setTreatyNum(paramVO.getTreatyNum());
            //合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
            paramEntity.setTreatyType(paramVO.getTreatyType());
            //承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
            paramEntity.setUndertakeType(paramVO.getUndertakeType());
            //合约名称
            paramEntity.setTreatyName(paramVO.getTreatyName());
            //合约内容及范围
            paramEntity.setTreatyContent(paramVO.getTreatyContent());
            //目标成本
            paramEntity.setTargetCost(NumberToolUtils.convertNullToDouble(paramVO.getTargetCost()));
            //合同类型（1：单价合同，2：总价合同，3：其他）
            paramEntity.setContractType(paramVO.getContractType());
            //合同确定方式（1：总承包，2：指定供货，3：指定分包，4：甲供，5：直接发包，6：三方协议）
            paramEntity.setContractWay(paramVO.getContractWay());
            //采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
            paramEntity.setPurchaserWay(paramVO.getPurchaserWay());
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
            tbProTreatyMapper.insertSelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    @Override
    public List<HTWholeProcessTreatyVO> excSearchProjectTreatyPeriodListForExport(HTWholeProcessTreatyVO paramVO) {
        try {
            HTWholeProcessTreatyBean paramBean = new HTWholeProcessTreatyBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            //查询项目合约规划列表
            List<HTWholeProcessTreatyBean> htWholeProcessTreatyBeanList = htWholeProcessTreatyDao.excSearchProTreatyPeriodListForExport(paramBean);
            List<HTWholeProcessTreatyVO> htWholeProcessTreatyVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessTreatyBean htWholeProcessTreatyBean:htWholeProcessTreatyBeanList){
                HTWholeProcessTreatyVO htWholeProcessTreatyVO = new HTWholeProcessTreatyVO();
                // 合约类型 1：工程类合同、2：采购类合同、3：服务类合同、4：土地类合同、5：其他类合同
                String tmpTreatyType = StringToolUtils.convertNullObjectToString(htWholeProcessTreatyBean.getTreatyType());
                htWholeProcessTreatyVO.setTreatyTypeName(tmpTreatyType.equals("1")?"工程类合同":(tmpTreatyType.equals("2")?"采购类合同":(tmpTreatyType.equals("3")?"服务类合同":(tmpTreatyType.equals("4")?"土地类合同":(tmpTreatyType.equals("5")?"其他类合同":"-")))));
                htWholeProcessTreatyVO.setTreatyType(tmpTreatyType);
                htWholeProcessTreatyVO.setCurrentLevel(Constant.FIRST_LEVEL_MENU_FLAG);
                List<HTWholeProcessTreatyBean> undertakeTypeBeanList = htWholeProcessTreatyBean.getChildList();
                List<HTWholeProcessTreatyVO> undertakeTypeVOList = new ArrayList<>();
                for (HTWholeProcessTreatyBean undertakeTypeBean:undertakeTypeBeanList){
                    HTWholeProcessTreatyVO undertakeTypeVO = new HTWholeProcessTreatyVO();
                    if (tmpTreatyType.equals("1")){
                        // 承包方式 1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业
                        String tmpUndertakeType = StringToolUtils.convertNullObjectToString(undertakeTypeBean.getUndertakeType());
                        undertakeTypeVO.setUndertakeTypeName(tmpUndertakeType.equals("2")?"发包人发包专业合同":(tmpUndertakeType.equals("3")?"发包人自行发包专业":(tmpUndertakeType.equals("1")?"施工总承包":"-")));
                        undertakeTypeVO.setUndertakeType(tmpUndertakeType);
                    }
                    undertakeTypeVO.setTreatyType(tmpTreatyType);
                    undertakeTypeVO.setCurrentLevel(Constant.SECOND_LEVEL_MENU_FLAG);
                    List<HTWholeProcessTreatyBean> childBeanList = undertakeTypeBean.getChildList();
                    List<HTWholeProcessTreatyVO> childVOList = new ArrayList<>();
                    for (HTWholeProcessTreatyBean childDataBean:childBeanList){
                        //转换格式
                        HTWholeProcessTreatyVO  childDataVO = (HTWholeProcessTreatyVO) ObjectReflectUtils.convertObj(childDataBean, HTWholeProcessTreatyVO.class);
                        // 合同类型（1：单价合同，2：总价合同，3：其他）
                        String tmpContractType = StringToolUtils.convertNullObjectToString(childDataBean.getContractType());
                        childDataVO.setContractType(tmpContractType.equals("1")?"单价合同":(tmpContractType.equals("2")?"总价合同":(tmpContractType.equals("3")?"其他":"-")));
                        // 合同确定方式（1：总承包，2：指定供货，3：指定分包，4：甲供，5：直接发包，6：三方协议）
                        String tmpContractWay = StringToolUtils.convertNullObjectToString(childDataBean.getContractWay());
                        childDataVO.setContractWay(tmpContractWay.equals("1")?"总承包":(tmpContractWay.equals("2")?"指定供货":(tmpContractWay.equals("3")?"指定分包":(tmpContractWay.equals("4")?"甲供":(tmpContractWay.equals("5")?"直接发包":(tmpContractWay.equals("6")?"三方协议":"-"))))));
                        // 采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
                        String tmpPurchaserWay = StringToolUtils.convertNullObjectToString(childDataBean.getPurchaserWay());
                        childDataVO.setPurchaserWay(tmpPurchaserWay.equals("1")?"公开招标":(tmpPurchaserWay.equals("2")?"内部组织招标":(tmpPurchaserWay.equals("3")?"联合招标":(tmpPurchaserWay.equals("4")?"直接委托":(tmpPurchaserWay.equals("5")?"竞争性谈判":"-")))));
                        childDataVO.setCurrentLevel(Constant.THIRD_LEVEL_MENU_FLAG);
                        childVOList.add(childDataVO);
                    }
                    undertakeTypeVO.setChildList(childVOList);
                    undertakeTypeVOList.add(undertakeTypeVO);
                    htWholeProcessTreatyVO.setChildList(undertakeTypeVOList);
                }
                htWholeProcessTreatyVOList.add(htWholeProcessTreatyVO);
            }
            return htWholeProcessTreatyVOList;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }
}
