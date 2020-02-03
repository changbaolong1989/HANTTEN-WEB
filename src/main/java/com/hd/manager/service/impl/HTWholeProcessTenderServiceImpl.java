package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProTenderEntityMapper;
import com.hd.common.entity.TbProTenderEntity;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessTenderDao;
import com.hd.manager.dao.bean.HTWholeProcessTenderBean;
import com.hd.manager.service.HTWholeProcessTenderService;
import com.hd.manager.vo.HTWholeProcessTenderVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * 全过程招标/清标列表 业务层 实现类
 *
 * @author LH
 * Created in 2019/9/3 15:31
 */
@Service
public class HTWholeProcessTenderServiceImpl extends BaseServiceImpl implements HTWholeProcessTenderService {




    /**
     * 招标/清标dao 持久层资源
     */
    @Resource
    private HTWholeProcessTenderDao htWholeProcessTenderDao;



    /**
     * 招标/清标Mapper 持久层资源
     */
    @Resource
    private TbProTenderEntityMapper tbProTenderMapper;




    /**
     * 查询项目招标阶段列表
     */
    @Override
    public Map<String,Object> excSearchProjectTenderPeriodList(HTWholeProcessTenderVO paramVO)  throws LogicException,SystemException {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            HTWholeProcessTenderBean paramBean = new HTWholeProcessTenderBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //查询项目招标阶段列表
            List<HTWholeProcessTenderBean> htWholeProcessTenderBeanList = htWholeProcessTenderDao.excSearchProTenderPeriodList(paramBean);
            //查询项目招标阶段列表
            Integer count = htWholeProcessTenderDao.excSearchProTenderPeriodListCount(paramBean);
            List<HTWholeProcessTenderVO> htWholeProcessTenderVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessTenderBean htWholeProcessTenderBean:htWholeProcessTenderBeanList){
                //转换格式
                HTWholeProcessTenderVO  htWholeProcessTenderVO = (HTWholeProcessTenderVO) ObjectReflectUtils.convertObj(htWholeProcessTenderBean, HTWholeProcessTenderVO.class);
                htWholeProcessTenderVO.setPlanStartTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getPlanStartTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVO.setPlanEndTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getPlanEndTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVO.setAcceptFileTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getAcceptFileTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVO.setSendReportTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getSendReportTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVOList.add(htWholeProcessTenderVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY,htWholeProcessTenderVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询项目清标阶段列表
     */
    @Override
    public Map<String,Object> excSearchProjectCleanTenderPeriodList(HTWholeProcessTenderVO paramVO)  throws LogicException,SystemException {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            HTWholeProcessTenderBean paramBean = new HTWholeProcessTenderBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //查询项目清标阶段列表
            List<HTWholeProcessTenderBean> htWholeProcessTenderBeanList = htWholeProcessTenderDao.excSearchProCleanTenderPeriodList(paramBean);
            //查询项目清标阶段列表
            Integer count = htWholeProcessTenderDao.excSearchProCleanTenderPeriodListCount(paramBean);
            List<HTWholeProcessTenderVO> htWholeProcessTenderVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessTenderBean htWholeProcessTenderBean:htWholeProcessTenderBeanList){
                //转换格式
                HTWholeProcessTenderVO  htWholeProcessTenderVO = (HTWholeProcessTenderVO) ObjectReflectUtils.convertObj(htWholeProcessTenderBean, HTWholeProcessTenderVO.class);
                htWholeProcessTenderVO.setPlanStartTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getPlanStartTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVO.setPlanEndTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getPlanEndTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVO.setAcceptFileTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getAcceptFileTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVO.setSendReportTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getSendReportTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVOList.add(htWholeProcessTenderVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY,htWholeProcessTenderVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询项目招标/清标信息详情
     */
    @Override
    public HTWholeProcessTenderVO excSearchProjectTenderPeriodInfo(HTWholeProcessTenderVO paramVO)  throws LogicException,SystemException {
        try {
            HTWholeProcessTenderBean paramBean = new HTWholeProcessTenderBean();
            //设置项目ID
            HTWholeProcessTenderBean htWholeProcessTenderBean = htWholeProcessTenderDao.excSearchProTenderPeriodInfoByPrimaryKey(paramVO.getTenderId());
            //格式转换
            HTWholeProcessTenderVO  htWholeProcessTenderVO = (HTWholeProcessTenderVO) ObjectReflectUtils.convertObj(htWholeProcessTenderBean, HTWholeProcessTenderVO.class);
            htWholeProcessTenderVO.setPlanStartTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getPlanStartTime(),DateToolUtils.yyyy_MM_dd));
            htWholeProcessTenderVO.setPlanEndTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getPlanEndTime(),DateToolUtils.yyyy_MM_dd));
            htWholeProcessTenderVO.setAcceptFileTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getAcceptFileTime(),DateToolUtils.yyyy_MM_dd));
            htWholeProcessTenderVO.setSendReportTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getSendReportTime(),DateToolUtils.yyyy_MM_dd));
            return htWholeProcessTenderVO;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 修改招标信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUpdateWholeProcessTender(HTWholeProcessTenderVO paramVO , MultipartFile file)  throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            HTWholeProcessTenderBean paramBean = new HTWholeProcessTenderBean();
            //招标编号
            paramBean.setTenderNum(paramVO.getTenderNum());
            //招标ID
            paramBean.setTenderId(paramVO.getTenderId());
            //校验个数
            Integer CheckNumCount = htWholeProcessTenderDao.excCheckProTenderCountByProperty(paramBean);
            //校验招标编号是否重复
            if (CheckNumCount>0){
                throw new LogicException(MessageConstant.MSG_ERROR_PROCESS_PAY_0002);
            }
            paramBean = new HTWholeProcessTenderBean();
            //招标名称
            paramBean.setTenderName(paramVO.getTenderName());
            //招标ID
            paramBean.setTenderId(paramVO.getTenderId());
            //校验个数
            Integer CheckNameCount = htWholeProcessTenderDao.excCheckProTenderCountByProperty(paramBean);
            //校验招标名称个数
            if (CheckNameCount>0){
                throw new LogicException(MessageConstant.MSG_ERROR_PROCESS_PAY_0003);
            }
            TbProTenderEntity paramEntity = new TbProTenderEntity();
            //招标唯一标识
            paramEntity.setTenderId(paramVO.getTenderId());
            //招标编号
            paramEntity.setTenderNum(paramVO.getTenderNum());
            //合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
            paramEntity.setTreatyType(paramVO.getTreatyType());
            //承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
            paramEntity.setUndertakeType(paramVO.getUndertakeType());
            //招标名称
            paramEntity.setTenderName(paramVO.getTenderName());
            //计划开始时间
            paramEntity.setPlanStartTime(DateToolUtils.convertDateFormat(paramVO.getPlanStartTime(),DateToolUtils.yyyy_MM_dd));
            //计划开始时间
            paramEntity.setPlanEndTime(DateToolUtils.convertDateFormat(paramVO.getPlanEndTime(),DateToolUtils.yyyy_MM_dd));
            //合同类型（1：单价合同，2：总价合同，3：其他）
            paramEntity.setContractType(paramVO.getContractType());
            //采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
            paramEntity.setPurchaserWay(paramVO.getPurchaserWay());
            //目标成本
            if (!StringToolUtils.isNull(paramVO.getTargetCost())){
                paramEntity.setTargetCost(NumberToolUtils.convertNullToDouble(paramVO.getTargetCost()));
            }
            //文件类型（1：招标控制价 2：招标标的 3：其他）
            paramEntity.setFileType(paramVO.getFileType());
            //除税金额（%）
            if (!StringToolUtils.isNull(paramVO.getExciseMoney())){
                paramEntity.setExciseMoney(NumberToolUtils.convertNullToDouble(paramVO.getExciseMoney()));
            }
            //税率（%）
            if (!StringToolUtils.isNull(paramVO.getTaxRatio())){
                paramEntity.setTaxRatio(NumberToolUtils.convertNullToDouble(paramVO.getTaxRatio()));
            }
            //税金
            if (!StringToolUtils.isNull(paramVO.getTaxMoney())){
                paramEntity.setTaxMoney(NumberToolUtils.convertNullToDouble(paramVO.getTaxMoney()));
            }
            //含税金额
            if (!StringToolUtils.isNull(paramVO.getWithTaxMoney())){
                paramEntity.setWithTaxMoney(NumberToolUtils.convertNullToDouble(paramVO.getWithTaxMoney()));
            }
            //备注
            paramEntity.setRemark(StringToolUtils.convertNullObjectToString(paramVO.getRemark()));
            //接受资料时间
            paramEntity.setAcceptFileTime(DateToolUtils.convertDateFormat(paramVO.getAcceptFileTime(),DateToolUtils.yyyy_MM_dd));
            //发送报告时间
            paramEntity.setSendReportTime(DateToolUtils.convertDateFormat(paramVO.getSendReportTime(),DateToolUtils.yyyy_MM_dd));
            if (null != file){
                /*----------------文件上传信息-----------------*/
                String uuid = UUID.randomUUID().toString();
                // 文件上传后的路径
                String filePath = "";
                // 文件名
                String realFileName = file.getOriginalFilename();
                // 获取文件的后缀名
                String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
                // 获取文件路径
                String fileName = uuid + suffixName;
                // 文件上传后的路径
                filePath = Constant.FILE_SEPARATE + "wholeProcess" +Constant.FILE_SEPARATE + "tender" +Constant.FILE_SEPARATE + "tender"  + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                //招标文件真实名称
                paramEntity.setTenderFileName(realFileName);
                //招标文件路径
                paramEntity.setTenderFilePath(filePath);
                // 上传文件
                boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
                //上传文件失败时抛出异常
                if (!isUploadSuccess){
                    throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0011);
                }
            }
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            tbProTenderMapper.updateByPrimaryKeySelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 修改清标信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUpdateWholeProcessCleanTender(HTWholeProcessTenderVO paramVO , MultipartFile file)  throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            HTWholeProcessTenderBean paramBean = new HTWholeProcessTenderBean();
            //招标唯一标识
            paramBean.setTenderId(paramVO.getTenderId());
            //接受资料时间
            paramBean.setAcceptFileTime(DateToolUtils.convertDateFormat(paramVO.getAcceptFileTime(),DateToolUtils.yyyy_MM_dd));
            //发送报告时间
            paramBean.setSendReportTime(DateToolUtils.convertDateFormat(paramVO.getSendReportTime(),DateToolUtils.yyyy_MM_dd));
            if (null != file){
                /*----------------文件上传信息-----------------*/
                String uuid = UUID.randomUUID().toString();
                // 文件上传后的路径
                String filePath = "";
                // 文件名
                String realFileName = file.getOriginalFilename();
                // 获取文件的后缀名
                String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
                // 获取文件路径
                String fileName = uuid + suffixName;
                // 文件上传后的路径
                filePath = Constant.FILE_SEPARATE + "wholeProcess" +Constant.FILE_SEPARATE + "tender" + Constant.FILE_SEPARATE + "clean"+ Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                //报告真实名称
                paramBean.setReportName(realFileName);
                //报告真实路径
                paramBean.setReportPath(filePath);
                // 上传文件
                boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
                //上传文件失败时抛出异常
                if (!isUploadSuccess){
                    throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0011);
                }
            }
            //修改时间
            paramBean.setUpdateDate(currentDate);
            //修改人
            paramBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            htWholeProcessTenderDao.updateWholeProcessCleanTender(paramBean);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }


    /**
     * 新增招标/清标信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertWholeProcessTender(HTWholeProcessTenderVO paramVO , MultipartFile file)  throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            HTWholeProcessTenderBean paramBean = new HTWholeProcessTenderBean();
            //招标名称
            paramBean.setTenderName(paramVO.getTenderName());
            //项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            //校验个数
            Integer CheckNameCount = htWholeProcessTenderDao.excCheckProTenderCountByProperty(paramBean);
            //校验招标名称个数
            if (CheckNameCount>0){
                throw new LogicException(MessageConstant.MSG_ERROR_PROCESS_PAY_0003);
            }
            TbProTenderEntity paramEntity = new TbProTenderEntity();
            //项目ID
            paramEntity.setProjectId(paramVO.getProjectId());
            //招标编号
            paramEntity.setTenderNum(paramVO.getTenderNum());
            //合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
            paramEntity.setTreatyType(paramVO.getTreatyType());
            //承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
            paramEntity.setUndertakeType(paramVO.getUndertakeType());
            //招标名称
            paramEntity.setTenderName(paramVO.getTenderName());
            //计划开始时间
            paramEntity.setPlanStartTime(DateToolUtils.convertDateFormat(paramVO.getPlanStartTime(),DateToolUtils.yyyy_MM_dd));
            //计划开始时间
            paramEntity.setPlanEndTime(DateToolUtils.convertDateFormat(paramVO.getPlanEndTime(),DateToolUtils.yyyy_MM_dd));
            //合同类型（1：单价合同，2：总价合同，3：其他）
            paramEntity.setContractType(paramVO.getContractType());
            //采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
            paramEntity.setPurchaserWay(paramVO.getPurchaserWay());
            //目标成本
            if (!StringToolUtils.isNull(paramVO.getTargetCost())){
                paramEntity.setTargetCost(NumberToolUtils.convertNullToDouble(paramVO.getTargetCost()));
            }
            //文件类型（1：招标控制价 2：招标标的 3：其他）
            paramEntity.setFileType(paramVO.getFileType());
            //除税金额（%）
            if (!StringToolUtils.isNull(paramVO.getExciseMoney())){
                paramEntity.setExciseMoney(NumberToolUtils.convertNullToDouble(paramVO.getExciseMoney()));
            }
            //税率（%）
            if (!StringToolUtils.isNull(paramVO.getTaxRatio())){
                paramEntity.setTaxRatio(NumberToolUtils.convertNullToDouble(paramVO.getTaxRatio()));
            }
            //税金
            if (!StringToolUtils.isNull(paramVO.getTaxMoney())){
                paramEntity.setTaxMoney(NumberToolUtils.convertNullToDouble(paramVO.getTaxMoney()));
            }
            //含税金额
            if (!StringToolUtils.isNull(paramVO.getWithTaxMoney())){
                paramEntity.setWithTaxMoney(NumberToolUtils.convertNullToDouble(paramVO.getWithTaxMoney()));
            }
            //备注
            paramEntity.setRemark(StringToolUtils.convertNullObjectToString(paramVO.getRemark()));
            //接受资料时间
            paramEntity.setAcceptFileTime(DateToolUtils.convertDateFormat(paramVO.getAcceptFileTime(),DateToolUtils.yyyy_MM_dd));
            //发送报告时间
            paramEntity.setSendReportTime(DateToolUtils.convertDateFormat(paramVO.getSendReportTime(),DateToolUtils.yyyy_MM_dd));
            if (null != file){
                /*----------------文件上传信息-----------------*/
                String uuid = UUID.randomUUID().toString();
                // 文件上传后的路径
                String filePath = "";
                // 文件名
                String realFileName = file.getOriginalFilename();
                // 获取文件的后缀名
                String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
                // 获取文件路径
                String fileName = uuid + suffixName;
                // 文件上传后的路径
                filePath = Constant.FILE_SEPARATE + "wholeProcess" +Constant.FILE_SEPARATE + "tender" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                //招标文件真实名称
                paramEntity.setTenderFileName(realFileName);
                //招标文件路径
                paramEntity.setTenderFilePath(filePath);
                // 上传文件
                boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
                //上传文件失败时抛出异常
                if (!isUploadSuccess){
                    throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0011);
                }
            }
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            tbProTenderMapper.insertSelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }



    @Override
    public List<HTWholeProcessTenderVO> excSearchProjectTenderPeriodListForExport(HTWholeProcessTenderVO paramVO) {
        try {
            HTWholeProcessTenderBean paramBean = new HTWholeProcessTenderBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            //查询项目合约规划列表
            List<HTWholeProcessTenderBean> htWholeProcessTenderBeanList = htWholeProcessTenderDao.excSearchProTenderPeriodListForExport(paramBean);
            List<HTWholeProcessTenderVO> htWholeProcessTreatyVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessTenderBean htWholeProcessTenderBean:htWholeProcessTenderBeanList){
                HTWholeProcessTenderVO htWholeProcessTenderVO = new HTWholeProcessTenderVO();
                // 合约类型 1：工程类合同、2：采购类合同、3：服务类合同、4：土地类合同、5：其他类合同
                String tmpTreatyType = StringToolUtils.convertNullObjectToString(htWholeProcessTenderBean.getTreatyType());
                htWholeProcessTenderVO.setTreatyTypeName(tmpTreatyType.equals("1")?"工程类合同":(tmpTreatyType.equals("2")?"采购类合同":(tmpTreatyType.equals("3")?"服务类合同":(tmpTreatyType.equals("4")?"土地类合同":(tmpTreatyType.equals("5")?"其他类合同":"-")))));
                htWholeProcessTenderVO.setTreatyType(tmpTreatyType);
                htWholeProcessTenderVO.setCurrentLevel(Constant.FIRST_LEVEL_MENU_FLAG);
                List<HTWholeProcessTenderBean> undertakeTypeBeanList = htWholeProcessTenderBean.getChildList();
                List<HTWholeProcessTenderVO> undertakeTypeVOList = new ArrayList<>();
                for (HTWholeProcessTenderBean undertakeTypeBean:undertakeTypeBeanList){
                    HTWholeProcessTenderVO undertakeTypeVO = new HTWholeProcessTenderVO();
                    if (tmpTreatyType.equals("1")){
                        // 承包方式 1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业
                        String tmpUndertakeType = StringToolUtils.convertNullObjectToString(undertakeTypeBean.getUndertakeType());
                        undertakeTypeVO.setUndertakeTypeName(tmpUndertakeType.equals("2")?"发包人发包专业合同":(tmpUndertakeType.equals("3")?"发包人自行发包专业":(tmpUndertakeType.equals("1")?"施工总承包":"-")));
                        undertakeTypeVO.setUndertakeType(tmpUndertakeType);
                    }
                    undertakeTypeVO.setTreatyType(tmpTreatyType);
                    undertakeTypeVO.setCurrentLevel(Constant.SECOND_LEVEL_MENU_FLAG);
                    List<HTWholeProcessTenderBean> childBeanList = undertakeTypeBean.getChildList();
                    List<HTWholeProcessTenderVO> childVOList = new ArrayList<>();
                    for (HTWholeProcessTenderBean childDataBean:childBeanList){
                        //转换格式
                        HTWholeProcessTenderVO  childDataVO = (HTWholeProcessTenderVO) ObjectReflectUtils.convertObj(childDataBean, HTWholeProcessTenderVO.class);
                        childDataVO.setPlanStartTime(DateToolUtils.convertDateFormat(childDataBean.getPlanStartTime(),DateToolUtils.YYYY_MM_DD_CHINESE));
                        childDataVO.setPlanEndTime(DateToolUtils.convertDateFormat(childDataBean.getPlanEndTime(),DateToolUtils.YYYY_MM_DD_CHINESE));
                        // 合同类型（1：单价合同，2：总价合同，3：其他）
                        String tmpContractType = StringToolUtils.convertNullObjectToString(childDataBean.getContractType());
                        childDataVO.setContractType(tmpContractType.equals("1")?"单价合同":(tmpContractType.equals("2")?"总价合同":(tmpContractType.equals("3")?"其他":"-")));
                        // 采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
                        String tmpPurchaserWay = StringToolUtils.convertNullObjectToString(childDataBean.getPurchaserWay());
                        childDataVO.setPurchaserWay(tmpPurchaserWay.equals("1")?"公开招标":(tmpPurchaserWay.equals("2")?"内部组织招标":(tmpPurchaserWay.equals("3")?"联合招标":(tmpPurchaserWay.equals("4")?"直接委托":(tmpPurchaserWay.equals("5")?"竞争性谈判":"-")))));
                        //文件类型(1：招标控制价 2：招标标的 3：其他)
                        String tmpFileType = StringToolUtils.convertNullObjectToString(childDataBean.getFileType());
                        childDataVO.setFileType(tmpPurchaserWay.equals("1")?"招标控制价":(tmpPurchaserWay.equals("2")?"招标标的":(tmpPurchaserWay.equals("3")?"其他":"-")));
                        childDataVO.setCurrentLevel(Constant.THIRD_LEVEL_MENU_FLAG);
                        childVOList.add(childDataVO);
                    }
                    undertakeTypeVO.setChildList(childVOList);
                    undertakeTypeVOList.add(undertakeTypeVO);
                    htWholeProcessTenderVO.setChildList(undertakeTypeVOList);
                }
                htWholeProcessTreatyVOList.add(htWholeProcessTenderVO);
            }
            return htWholeProcessTreatyVOList;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 查询项目清标阶段列表
     */
    @Override
    public List<HTWholeProcessTenderVO> excSearchProjectCleanTenderPeriodListForExport(HTWholeProcessTenderVO paramVO)  throws LogicException,SystemException {
        List<HTWholeProcessTenderVO> htWholeProcessTenderVOList = new ArrayList<>();
        try {
            HTWholeProcessTenderBean paramBean = new HTWholeProcessTenderBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            //查询项目清标阶段列表
            List<HTWholeProcessTenderBean> htWholeProcessTenderBeanList = htWholeProcessTenderDao.excSearchProjectCleanTenderPeriodListForExport(paramBean);
            //查询项目清标阶段列表
            Integer count = htWholeProcessTenderDao.excSearchProCleanTenderPeriodListCount(paramBean);
            //遍历转换格式
            for (HTWholeProcessTenderBean htWholeProcessTenderBean:htWholeProcessTenderBeanList){
                //转换格式
                HTWholeProcessTenderVO  htWholeProcessTenderVO = (HTWholeProcessTenderVO) ObjectReflectUtils.convertObj(htWholeProcessTenderBean, HTWholeProcessTenderVO.class);
                // 合同类型（1：单价合同，2：总价合同，3：其他）
                String tmpContractType = StringToolUtils.convertNullObjectToString(htWholeProcessTenderVO.getContractType());
                htWholeProcessTenderVO.setContractType(tmpContractType.equals("1")?"单价合同":(tmpContractType.equals("2")?"总价合同":(tmpContractType.equals("3")?"其他":"-")));
                htWholeProcessTenderVO.setPlanStartTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getPlanStartTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVO.setPlanEndTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getPlanEndTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVO.setAcceptFileTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getAcceptFileTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVO.setSendReportTime(DateToolUtils.convertDateFormat(htWholeProcessTenderBean.getSendReportTime(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessTenderVOList.add(htWholeProcessTenderVO);
            }
            return htWholeProcessTenderVOList;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


}
