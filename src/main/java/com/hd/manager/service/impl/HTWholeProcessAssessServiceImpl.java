package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProcessAssessMapper;
import com.hd.common.entity.TbProAssessEntity;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessAssessDao;
import com.hd.manager.dao.bean.HTWholeProcessAssessBean;
import com.hd.manager.service.HTWholeProcessAssessService;
import com.hd.manager.vo.HTWholeProcessAssessVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 全过程估算列表 业务层 实现类
 *
 * @author LH
 * Created in 2019/9/3 15:31
 */
@Service
public class HTWholeProcessAssessServiceImpl extends BaseServiceImpl implements HTWholeProcessAssessService {




    /**
     * 估算dao 持久层资源
     */
    @Resource
    private HTWholeProcessAssessDao htWholeProcessAssessDao;



    /**
     * 估算Mapper 持久层资源
     */
    @Resource
    private TbProcessAssessMapper tbProAssessMapper;




    /**
     * 查询项目估算阶段列表
     */
    @Override
    public Map<String,Object> excSearchProjectAssessPeriodList(HTWholeProcessAssessVO paramVO)  throws LogicException,SystemException {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            HTWholeProcessAssessBean paramBean = new HTWholeProcessAssessBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //查询项目估算阶段列表
            List<HTWholeProcessAssessBean> htWholeProcessAssessBeanList = htWholeProcessAssessDao.excSearchProAssessPeriodList(paramBean);
            //查询项目估算阶段列表总条数
            Integer count = htWholeProcessAssessDao.excSearchProAssessPeriodListCount(paramBean);
            List<HTWholeProcessAssessVO> htWholeProcessAssessVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessAssessBean htWholeProcessAssessBean:htWholeProcessAssessBeanList){
                //转换格式
                HTWholeProcessAssessVO  htWholeProcessAssessVO = (HTWholeProcessAssessVO) ObjectReflectUtils.convertObj(htWholeProcessAssessBean, HTWholeProcessAssessVO.class);
                htWholeProcessAssessVO.setFloorSpace(this.getNumberStr(htWholeProcessAssessVO.getFloorSpace()));
                htWholeProcessAssessVO.setEstimatedCost(this.getNumberStr(htWholeProcessAssessVO.getEstimatedCost()));
                htWholeProcessAssessVO.setEstimationExParte(this.getNumberStr(htWholeProcessAssessVO.getEstimationExParte()));
                htWholeProcessAssessVO.setCompileDate(DateToolUtils.convertDateFormat(htWholeProcessAssessBean.getCompileDate(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessAssessVOList.add(htWholeProcessAssessVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY,htWholeProcessAssessVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 查询项目估算阶段列表(供导出使用)
     */
    @Override
    public List<HTWholeProcessAssessVO> excSearchProjectAssessPeriodListForExport(HTWholeProcessAssessVO paramVO)  throws LogicException,SystemException {
        try {
            HTWholeProcessAssessBean paramBean = new HTWholeProcessAssessBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            //查询项目估算阶段列表
            List<HTWholeProcessAssessBean> htWholeProcessAssessBeanList = htWholeProcessAssessDao.excSearchProAssessPeriodListForExport(paramBean);
            List<HTWholeProcessAssessVO> htWholeProcessAssessVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessAssessBean htWholeProcessAssessBean:htWholeProcessAssessBeanList){
                //转换格式
                HTWholeProcessAssessVO  htWholeProcessAssessVO = (HTWholeProcessAssessVO) ObjectReflectUtils.convertObj(htWholeProcessAssessBean, HTWholeProcessAssessVO.class);
                htWholeProcessAssessVO.setFloorSpace(this.getNumberStr(htWholeProcessAssessVO.getFloorSpace()));
                htWholeProcessAssessVO.setEstimatedCost(this.getNumberStr(htWholeProcessAssessVO.getEstimatedCost()));
                htWholeProcessAssessVO.setEstimationExParte(this.getNumberStr(htWholeProcessAssessVO.getEstimationExParte()));
                htWholeProcessAssessVO.setCompileDate(DateToolUtils.convertDateFormat(htWholeProcessAssessBean.getCompileDate(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessAssessVOList.add(htWholeProcessAssessVO);
            }
            return htWholeProcessAssessVOList;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询项目估算信息详情
     */
    @Override
    public HTWholeProcessAssessVO excSearchProjectAssessPeriodInfo(HTWholeProcessAssessVO paramVO)  throws LogicException,SystemException {
        try {
            HTWholeProcessAssessBean paramBean = new HTWholeProcessAssessBean();
            //设置项目ID
            HTWholeProcessAssessBean htWholeProcessAssessBean = htWholeProcessAssessDao.excSearchProAssessPeriodInfoByPrimaryKey(paramVO.getAssessId());
            //格式转换
            HTWholeProcessAssessVO  htWholeProcessAssessVO = (HTWholeProcessAssessVO) ObjectReflectUtils.convertObj(htWholeProcessAssessBean, HTWholeProcessAssessVO.class);
            htWholeProcessAssessVO.setFloorSpace(this.getNumberStr(htWholeProcessAssessVO.getFloorSpace()));
            htWholeProcessAssessVO.setEstimatedCost(this.getNumberStr(htWholeProcessAssessVO.getEstimatedCost()));
            htWholeProcessAssessVO.setEstimationExParte(this.getNumberStr(htWholeProcessAssessVO.getEstimationExParte()));
            htWholeProcessAssessVO.setCompileDate(DateToolUtils.convertDateFormat(htWholeProcessAssessBean.getCompileDate(),DateToolUtils.yyyy_MM_dd));
            return htWholeProcessAssessVO;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 修改估算信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUpdateWholeProcessAssess(HTWholeProcessAssessVO paramVO , MultipartFile file)  throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            TbProAssessEntity paramEntity = new TbProAssessEntity();
            //估算唯一标识
            paramEntity.setAssessId(paramVO.getAssessId());
            //编制日期
            paramEntity.setCompileDate(DateToolUtils.convertDateFormat(paramVO.getCompileDate(),DateToolUtils.yyyy_MM_dd));
            //估算名称/版本
            paramEntity.setAssessName(StringToolUtils.convertNullObjectToString(paramVO.getAssessName()));
            //建筑面积
            paramEntity.setFloorSpace(NumberToolUtils.convertNullToDouble(paramVO.getFloorSpace()));
            //估算费用
            if (!StringToolUtils.isNull(paramVO.getEstimatedCost())){
                paramEntity.setEstimatedCost(NumberToolUtils.convertNullToDouble(paramVO.getEstimatedCost()));
            }
            //估算单方
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
                filePath = Constant.FILE_SEPARATE + "wholeProcess" +Constant.FILE_SEPARATE + "assess" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                //自定义模板真实名称
                paramEntity.setAssessFileName(realFileName);
                //自定义模板文件路径
                paramEntity.setAssessFilePath(filePath);
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
            tbProAssessMapper.updateByPrimaryKeySelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }




    /**
     * 新增估算信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertWholeProcessAssess(HTWholeProcessAssessVO paramVO , MultipartFile file)  throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            TbProAssessEntity paramEntity = new TbProAssessEntity();
            //编制日期
            paramEntity.setCompileDate(DateToolUtils.convertDateFormat(paramVO.getCompileDate(),DateToolUtils.yyyy_MM_dd));
            //估算名称/版本
            paramEntity.setAssessName(StringToolUtils.convertNullObjectToString(paramVO.getAssessName()));
            //项目ID
            paramEntity.setProjectId(paramVO.getProjectId());
            //建筑面积
            paramEntity.setFloorSpace(NumberToolUtils.convertNullToDouble(paramVO.getFloorSpace()));
            //估算费用
            if (!StringToolUtils.isNull(paramVO.getEstimatedCost())){
                paramEntity.setEstimatedCost(NumberToolUtils.convertNullToDouble(paramVO.getEstimatedCost()));
            }
            //估算单方
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
                filePath = Constant.FILE_SEPARATE + "wholeProcess" +Constant.FILE_SEPARATE + "assess" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                //自定义模板真实名称
                paramEntity.setAssessFileName(realFileName);
                //自定义模板文件路径
                paramEntity.setAssessFilePath(filePath);
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
            tbProAssessMapper.insertSelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
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
