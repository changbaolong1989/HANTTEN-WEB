package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProcessEstimateMapper;
import com.hd.common.entity.TbProEstimateEntity;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessEstimateDao;
import com.hd.manager.dao.bean.HTWholeProcessEstimateBean;
import com.hd.manager.service.HTWholeProcessEstimateService;
import com.hd.manager.vo.HTWholeProcessEstimateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 全过程概算列表 业务层 实现类
 *
 * @author LH
 * Created in 2019/9/3 15:31
 */
@Service
public class HTWholeProcessEstimateServiceImpl extends BaseServiceImpl implements HTWholeProcessEstimateService {




    /**
     * 概算dao 持久层资源
     */
    @Resource
    private HTWholeProcessEstimateDao htWholeProcessEstimateDao;



    /**
     * 概算Mapper 持久层资源
     */
    @Resource
    private TbProcessEstimateMapper tbProEstimateMapper;




    /**
     * 查询项目概算阶段列表
     */
    @Override
    public Map<String,Object> excSearchProjectEstimatePeriodList(HTWholeProcessEstimateVO paramVO)  throws LogicException,SystemException {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            HTWholeProcessEstimateBean paramBean = new HTWholeProcessEstimateBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //查询项目概算阶段列表
            List<HTWholeProcessEstimateBean> htWholeProcessEstimateBeanList = htWholeProcessEstimateDao.excSearchProEstimatePeriodList(paramBean);
            //查询项目概算阶段列表
            Integer count = htWholeProcessEstimateDao.excSearchProEstimatePeriodListCount(paramBean);
            List<HTWholeProcessEstimateVO> htWholeProcessEstimateVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessEstimateBean htWholeProcessEstimateBean:htWholeProcessEstimateBeanList){
                //转换格式
                HTWholeProcessEstimateVO  htWholeProcessEstimateVO = (HTWholeProcessEstimateVO) ObjectReflectUtils.convertObj(htWholeProcessEstimateBean, HTWholeProcessEstimateVO.class);
                htWholeProcessEstimateVO.setFloorSpace(this.getNumberStr(htWholeProcessEstimateVO.getFloorSpace()));
                htWholeProcessEstimateVO.setEstimatedCost(this.getNumberStr(htWholeProcessEstimateVO.getEstimatedCost()));
                htWholeProcessEstimateVO.setEstimationExParte(this.getNumberStr(htWholeProcessEstimateVO.getEstimationExParte()));
                htWholeProcessEstimateVO.setCompileDate(DateToolUtils.convertDateFormat(htWholeProcessEstimateBean.getCompileDate(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessEstimateVOList.add(htWholeProcessEstimateVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY,htWholeProcessEstimateVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 查询项目概算信息详情
     */
    @Override
    public HTWholeProcessEstimateVO excSearchProjectEstimatePeriodInfo(HTWholeProcessEstimateVO paramVO)  throws LogicException,SystemException {
        try {
            HTWholeProcessEstimateBean paramBean = new HTWholeProcessEstimateBean();
            //设置项目ID
            HTWholeProcessEstimateBean htWholeProcessEstimateBean = htWholeProcessEstimateDao.excSearchProEstimatePeriodInfoByPrimaryKey(paramVO.getEstimateId());
            //格式转换
            HTWholeProcessEstimateVO  htWholeProcessEstimateVO = (HTWholeProcessEstimateVO) ObjectReflectUtils.convertObj(htWholeProcessEstimateBean, HTWholeProcessEstimateVO.class);
            htWholeProcessEstimateVO.setFloorSpace(this.getNumberStr(htWholeProcessEstimateVO.getFloorSpace()));
            htWholeProcessEstimateVO.setEstimatedCost(this.getNumberStr(htWholeProcessEstimateVO.getEstimatedCost()));
            htWholeProcessEstimateVO.setEstimationExParte(this.getNumberStr(htWholeProcessEstimateVO.getEstimationExParte()));
            htWholeProcessEstimateVO.setCompileDate(DateToolUtils.convertDateFormat(htWholeProcessEstimateBean.getCompileDate(),DateToolUtils.yyyy_MM_dd));
            return htWholeProcessEstimateVO;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 修改概算信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUpdateWholeProcessEstimate(HTWholeProcessEstimateVO paramVO , MultipartFile file)  throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            TbProEstimateEntity paramEntity = new TbProEstimateEntity();
            //概算唯一标识
            paramEntity.setEstimateId(paramVO.getEstimateId());
            //编制日期
            paramEntity.setCompileDate(DateToolUtils.convertDateFormat(paramVO.getCompileDate(),DateToolUtils.yyyy_MM_dd));
            //概算名称/版本
            paramEntity.setEstimateName(StringToolUtils.convertNullObjectToString(paramVO.getEstimateName()));
            //建筑面积
            paramEntity.setFloorSpace(NumberToolUtils.convertNullToDouble(paramVO.getFloorSpace()));
            //概算费用
            if (!StringToolUtils.isNull(paramVO.getEstimatedCost())){
                paramEntity.setEstimatedCost(NumberToolUtils.convertNullToDouble(paramVO.getEstimatedCost()));
            }
            //概算单方
            if (!StringToolUtils.isNull(paramVO.getEstimationExParte())){
                paramEntity.setEstimationExParte(NumberToolUtils.convertNullToDouble(paramVO.getEstimationExParte()));
            }
            //费用范围
            paramEntity.setCostRange(StringToolUtils.convertNullObjectToString(paramVO.getCostRange()));
            //备注
            paramEntity.setRemark(StringToolUtils.convertNullObjectToString(paramVO.getRemark()));
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
                filePath = Constant.FILE_SEPARATE + "wholeProcess" +Constant.FILE_SEPARATE + "estimate" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                //自定义模板真实名称
                paramEntity.setEstimateFileName(realFileName);
                //自定义模板文件路径
                paramEntity.setEstimateFilePath(filePath);
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
            tbProEstimateMapper.updateByPrimaryKeySelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }




    /**
     * 新增概算信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertWholeProcessEstimate(HTWholeProcessEstimateVO paramVO , MultipartFile file)  throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            TbProEstimateEntity paramEntity = new TbProEstimateEntity();
            //编制日期
            paramEntity.setCompileDate(DateToolUtils.convertDateFormat(paramVO.getCompileDate(),DateToolUtils.yyyy_MM_dd));
            //概算名称/版本
            paramEntity.setEstimateName(StringToolUtils.convertNullObjectToString(paramVO.getEstimateName()));
            //项目ID
            paramEntity.setProjectId(paramVO.getProjectId());
            //建筑面积
            paramEntity.setFloorSpace(NumberToolUtils.convertNullToDouble(paramVO.getFloorSpace()));
            //概算费用
            if (!StringToolUtils.isNull(paramVO.getEstimatedCost())){
                paramEntity.setEstimatedCost(NumberToolUtils.convertNullToDouble(paramVO.getEstimatedCost()));
            }
            //概算单方
            if (!StringToolUtils.isNull(paramVO.getEstimationExParte())){
                paramEntity.setEstimationExParte(NumberToolUtils.convertNullToDouble(paramVO.getEstimationExParte()));
            }
            //费用范围
            paramEntity.setCostRange(StringToolUtils.convertNullObjectToString(paramVO.getCostRange()));
            //备注
            paramEntity.setRemark(StringToolUtils.convertNullObjectToString(paramVO.getRemark()));
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
                filePath = Constant.FILE_SEPARATE + "wholeProcess" +Constant.FILE_SEPARATE + "Estimate" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                //自定义模板真实名称
                paramEntity.setEstimateFileName(realFileName);
                //自定义模板文件路径
                paramEntity.setEstimateFilePath(filePath);
                // 上传文件
                boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
                //上传失败时抛出异常
                if (!isUploadSuccess){
                    throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0011);
                }
            }else {//未上传文件时抛出异常
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0010);
            }
            //创建时间
            paramEntity.setCreateDate(currentDate);
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //创建人
            paramEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //是否删除
            paramEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
            tbProEstimateMapper.insertSelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    @Override
    public List<HTWholeProcessEstimateVO> excSearchProjectEstimatePeriodListForExport(HTWholeProcessEstimateVO paramVO) {
        try {
            HTWholeProcessEstimateBean paramBean = new HTWholeProcessEstimateBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            //查询项目发出信函登记表列表
            List<HTWholeProcessEstimateBean> htWholeProcessEstimateBeanList = htWholeProcessEstimateDao.excSearchProEstimatePeriodListForExport(paramBean);
            List<HTWholeProcessEstimateVO> htWholeProcessEstimateVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessEstimateBean htWholeProcessEstimateBean:htWholeProcessEstimateBeanList){
                //转换格式
                HTWholeProcessEstimateVO  htWholeProcessEstimateVO = (HTWholeProcessEstimateVO) ObjectReflectUtils.convertObj(htWholeProcessEstimateBean, HTWholeProcessEstimateVO.class);
                htWholeProcessEstimateVO.setFloorSpace(this.getNumberStr(htWholeProcessEstimateVO.getFloorSpace()));
                htWholeProcessEstimateVO.setEstimatedCost(this.getNumberStr(htWholeProcessEstimateVO.getEstimatedCost()));
                htWholeProcessEstimateVO.setEstimationExParte(this.getNumberStr(htWholeProcessEstimateVO.getEstimationExParte()));
                htWholeProcessEstimateVO.setCompileDate(DateToolUtils.convertDateFormat(htWholeProcessEstimateBean.getCompileDate(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessEstimateVOList.add(htWholeProcessEstimateVO);
            }
            return htWholeProcessEstimateVOList;
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
