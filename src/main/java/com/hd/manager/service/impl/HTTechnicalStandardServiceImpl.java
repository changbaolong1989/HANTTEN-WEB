package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbTechnicalStandardEntityMapper;
import com.hd.common.entity.TbTechnicalStandardEntity;
import com.hd.common.entity.TbTechnicalStandardEntityExample;
import com.hd.common.util.*;
import com.hd.manager.dao.HTTechnicalStandardDao;
import com.hd.manager.dao.bean.HTTechnicalStandardBean;
import com.hd.manager.service.HTTechnicalStandardService;
import com.hd.manager.vo.HTTechnicalStandardVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.*;

/**
 * 设置-技术标准管理 业务层 实现类
 *
 * @author jwl
 * Created in 2019/7/5 15:31
 */
@Service
public class HTTechnicalStandardServiceImpl extends BaseServiceImpl implements HTTechnicalStandardService {

    /**
     * 技术标准dao 持久层资源
     */
    @Resource
    private HTTechnicalStandardDao htTechnicalStandardDao;

    /**
     * 技术标准entity 持久层资源
     */
    @Resource
    private TbTechnicalStandardEntityMapper tbTechnicalStandardEntityMapper;

    /**
     * 查询技术标准列表数据
     *
     * @return 技术标准列表数据
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchTechnicalStandardList(HTTechnicalStandardVO paramVO) throws SystemException {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            HTTechnicalStandardBean paramBean = new HTTechnicalStandardBean();

            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            // 查询技术标准列表数据的总条数
            int retCount = htTechnicalStandardDao.excSearchTechnicalStandardListCount(paramBean);

            // 查询技术标准列表数据
            List<HTTechnicalStandardBean> technicalStandardList = htTechnicalStandardDao.excSearchTechnicalStandardList(paramBean);

            HTTechnicalStandardVO htTechnicalStandardVO = null;

            // 集合
            List<HTTechnicalStandardVO> retTechnicalStandardList = new ArrayList<>();

            // Bean 转化成 VO
            for (HTTechnicalStandardBean htTechnicalStandardBean : technicalStandardList) {

                htTechnicalStandardVO = new HTTechnicalStandardVO();

                // 设置技术标准ID
                htTechnicalStandardVO.setStandardId(StringToolUtils.convertNullObjectToString(
                        htTechnicalStandardBean.getStandardId()));

                // 设置技术标准名称
                htTechnicalStandardVO.setStandardName(StringToolUtils.convertNullObjectToString(
                        htTechnicalStandardBean.getStandardName()));

                // 附件路径
                htTechnicalStandardVO.setMaterialPath(StringToolUtils.convertNullObjectToString(
                        htTechnicalStandardBean.getMaterialPath()));

                // 附件名称
                htTechnicalStandardVO.setMaterialName(StringToolUtils.convertNullObjectToString(
                        htTechnicalStandardBean.getMaterialName()));

                // 设置发布人
                htTechnicalStandardVO.setUserName(StringToolUtils.convertNullObjectToString(
                        htTechnicalStandardBean.getUserName()));

                // 设置发布时间
                htTechnicalStandardVO.setReleaseDate(DateToolUtils.convertDateFormat(
                        htTechnicalStandardBean.getReleaseDate(),DateToolUtils.yyyy_MM_dd));

                retTechnicalStandardList.add(htTechnicalStandardVO);

            }

            resultMap.put(Constant.DATA_LIST_KEY, retTechnicalStandardList);

            resultMap.put(Constant.DATA_COUNT_KEY, retCount);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    /**
     * 添加技术标准信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excSaveTechnicalStandardInfo(HTTechnicalStandardVO paramVO, MultipartFile signPathFile) throws LogicException, SystemException {
        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        try {

            // =================================查询公告名称是否重复 start========================================

            TbTechnicalStandardEntityExample tbTechnicalStandardEntityExample = new TbTechnicalStandardEntityExample();

            TbTechnicalStandardEntityExample.Criteria criteria = tbTechnicalStandardEntityExample.createCriteria();

            criteria.andStandardNameEqualTo(paramVO.getStandardName());

            List<TbTechnicalStandardEntity> entityList = tbTechnicalStandardEntityMapper.selectByExample(tbTechnicalStandardEntityExample);

            if(entityList.size() > NumberToolUtils.DEFAULT_INT_ZERO){
                // 有重复，抛出异常(技术标准名称重复，请重新填写！)
                throw new LogicException(MessageConstant.MSG_ERROR_TECHNICAL_STANDARD_0001);
            }

            // =================================查询公告名称是否重复 end========================================

            // 生成uuid
            String tempRandomUUIDStr = UUID.randomUUID().toString();

            // 文件上传后的路径
            String signPathFilePath = null;

            // 自定义上传文件的文件名称（公告id+.后缀名）
            String tempFileNameStr = null;

            //========================= 添加技术标准信息  start ========================

            TbTechnicalStandardEntity tbTechnicalStandardEntity = new TbTechnicalStandardEntity();

            // 设置技术标准id
            tbTechnicalStandardEntity.setStandardId(tempRandomUUIDStr);

            // 设置技术标准名称
            tbTechnicalStandardEntity.setStandardName(StringToolUtils.convertNullObjectToString(paramVO.getStandardName()));

            // 如果附件不为空，则上传附件，附件名称和附件路径
            if(null != signPathFile){
                // 源文件名
                String signPathFileName = signPathFile.getOriginalFilename();

                // 文件上传后的路径
                signPathFilePath = Constant.FILE_SEPARATE+"technicalStandard"
                        + Constant.FILE_SEPARATE+ tempRandomUUIDStr + Constant.FILE_SEPARATE;

                // 自定义上传文件的文件名称（公告id+.后缀名）
                tempFileNameStr = tempRandomUUIDStr + signPathFileName.substring(signPathFileName.lastIndexOf("."));

                // 设置附件路径
                tbTechnicalStandardEntity.setMaterialPath(signPathFilePath + tempFileNameStr);

                // 设置附件名称
                tbTechnicalStandardEntity.setMaterialName(StringToolUtils.convertNullObjectToString(signPathFileName));

            }

            // 设置发布时间
            tbTechnicalStandardEntity.setReleaseDate(tempCurrentDate);

            // 设置创建时间
            tbTechnicalStandardEntity.setCreateDate(tempCurrentDate);

            // 设置修改时间
            tbTechnicalStandardEntity.setUpdateDate(tempCurrentDate);

            // 设置发布人id
            tbTechnicalStandardEntity.setReleasePeople(paramVO.getLoginUserInfo().getLoginUserId());

            // 设置创建人id
            tbTechnicalStandardEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 设置修改人id
            tbTechnicalStandardEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 是否删除
            tbTechnicalStandardEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

            // 添加技术标准信息
            htTechnicalStandardDao.excInsertTechnicalStandardInfo(tbTechnicalStandardEntity);

            //========================= 添加技术标准信息  end ========================

            //========================= 上传文件  start ========================

            // 如果附件不为空，则上传附件，附件名称和附件路径
            if(null != signPathFile){

                // 上传文件
                boolean isUploadSignSuccess = FileUtils.uploadFile(signPathFilePath + tempFileNameStr, signPathFile);

                if (!isUploadSignSuccess) {
                    // MSG_ERROR_PUBLIC_NOTICE_0001 上传文件失败！
                    throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0001);
                }

            }

            //========================= 上传文件  end ========================


        } catch (LogicException lex) {
            throw  lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询技术标准详细信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTTechnicalStandardVO excSearchTechnicalStandardInfo(HTTechnicalStandardVO paramVO) throws SystemException {

        HTTechnicalStandardVO retTechnicalStandardVO = new HTTechnicalStandardVO();

        try{

            HTTechnicalStandardBean paramBean = new  HTTechnicalStandardBean();

            // 设置技术标准ID
            paramBean.setStandardId(StringToolUtils.convertNullObjectToString(paramVO.getStandardId()));

            // 查询技术标准详情
            HTTechnicalStandardBean htTechnicalStandardBean = htTechnicalStandardDao
                    .excSearchTechnicalStandardInfo(paramBean);

            if(null != htTechnicalStandardBean){

                // 设置技术标准ID
                retTechnicalStandardVO.setStandardId(StringToolUtils.convertNullObjectToString(
                        htTechnicalStandardBean.getStandardId()));

                // 设置技术标准名称
                retTechnicalStandardVO.setStandardName(StringToolUtils.convertNullObjectToString(
                        htTechnicalStandardBean.getStandardName()));

                // 设置附件名称
                retTechnicalStandardVO.setMaterialName(StringToolUtils.convertNullObjectToString(
                        htTechnicalStandardBean.getMaterialName()));

                // 设置修改时间
                retTechnicalStandardVO.setUpdateDate(DateToolUtils
                        .convertDateFormat(htTechnicalStandardBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            }

        }catch (Exception e){
            throw new SystemException(e,MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retTechnicalStandardVO;
    }

    /**
     * 更改技术标准信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdateTechnicalStandardInfo(HTTechnicalStandardVO paramVO, MultipartFile signPathFile) throws LogicException, SystemException {

        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        try {

            TbTechnicalStandardEntity retEntity = tbTechnicalStandardEntityMapper.selectByPrimaryKey(
                    StringToolUtils.convertNullObjectToString(paramVO.getStandardId()));

            // =================================查询公告名称是否重复 start========================================

            TbTechnicalStandardEntityExample tbTechnicalStandardEntityExample = new TbTechnicalStandardEntityExample();

            TbTechnicalStandardEntityExample.Criteria criteria = tbTechnicalStandardEntityExample.createCriteria();

            criteria.andStandardNameEqualTo(paramVO.getStandardName());

            criteria.andStandardIdNotEqualTo(paramVO.getStandardId());

            List<TbTechnicalStandardEntity> entityList = tbTechnicalStandardEntityMapper.selectByExample(tbTechnicalStandardEntityExample);

            if(entityList.size() > NumberToolUtils.DEFAULT_INT_ZERO){
                // 有重复，抛出异常(技术标准名称重复，请重新填写！)
                throw new LogicException(MessageConstant.MSG_ERROR_TECHNICAL_STANDARD_0001);
            }

            // =================================查询公告名称是否重复 end========================================

            //========================= 查询修改时间是否相等  start ========================

            // 修改时间
            String updateDate = DateToolUtils.convertDateFormat(
                    retEntity.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO);

            // 有重复，抛出异常(数据已被修改，请确认后重新提交！)
            if (!paramVO.getUpdateDate().equals(updateDate)) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMPANY_0004);
            }

            //========================= 查询修改时间是否相等  end ========================

            //========================= 修改技术标准信息  start ========================

            // 文件上传后的路径
            String signPathFilePath = null;

            // 自定义上传文件的文件名称（公告id+.后缀名）
            String tempFileNameStr = null;

            TbTechnicalStandardEntity tbTechnicalStandardEntity = new TbTechnicalStandardEntity();

            // 设置技术标准id
            tbTechnicalStandardEntity.setStandardId(StringToolUtils.convertNullObjectToString(paramVO.getStandardId()));

            // 设置技术标准名称
            tbTechnicalStandardEntity.setStandardName(StringToolUtils.convertNullObjectToString(paramVO.getStandardName()));

            // 设置修改时间
            tbTechnicalStandardEntity.setUpdateDate(tempCurrentDate);

            // 设置修改人id
            tbTechnicalStandardEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 如果附件不为空，则上传附件，附件名称和附件路径
            if(null != signPathFile){

                // 源文件名
                String signPathFileName = signPathFile.getOriginalFilename();

                // 文件上传后的路径
                signPathFilePath = Constant.FILE_SEPARATE + "technicalStandard"
                        + Constant.FILE_SEPARATE + paramVO.getStandardId() + Constant.FILE_SEPARATE;

                // 自定义上传文件的文件名称（公告id+.后缀名）
                tempFileNameStr = paramVO.getStandardId() + signPathFileName.substring(signPathFileName.lastIndexOf("."));

                // 设置附件路径
                tbTechnicalStandardEntity.setMaterialPath(signPathFilePath + tempFileNameStr);

                // 设置附件名称
                tbTechnicalStandardEntity.setMaterialName(StringToolUtils.convertNullObjectToString(signPathFileName));

            }

            // 修改技术标准信息
            tbTechnicalStandardEntityMapper.updateByPrimaryKeySelective(tbTechnicalStandardEntity);

            //========================= 修改技术标准信息  end ========================

            // 如果附件不为空，则上传附件，附件名称和附件路径
            if(null != signPathFile){

                //========================= 删除原有文件  start ========================

                // 如果查询到该路径，则删除文件
                if(null != retEntity.getMaterialPath()){

                    // 获取文件路径
                    String tempPathStr = retEntity.getMaterialPath().substring(NumberToolUtils.INT_ZERO,retEntity.getMaterialPath().lastIndexOf("/"));

                    // 删除文件
                    FileUtils.deleteFile(tempPathStr);

                }

                //========================= 删除原有文件  end ========================

                //========================= 上传文件  start ========================

                // 上传文件
                boolean isUploadSignSuccess = FileUtils.uploadFile(signPathFilePath+tempFileNameStr, signPathFile);

                if (!isUploadSignSuccess) {
                    // MSG_ERROR_PUBLIC_NOTICE_0001 上传文件失败！
                    throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0001);
                }

                //========================= 上传文件  end ========================

            }

        } catch (LogicException lex) {
            throw  lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 删除技术标准信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excDeleteTechnicalStandardInfo(HTTechnicalStandardVO paramVO) throws SystemException {

        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        try {

            //========================= 修改技术标准信息  start ========================

            TbTechnicalStandardEntity tbTechnicalStandardEntity = new TbTechnicalStandardEntity();

            // 设置公告id
            tbTechnicalStandardEntity.setStandardId(StringToolUtils.convertNullObjectToString(paramVO.getStandardId()));

            // 设置是否删除状态
            tbTechnicalStandardEntity.setIsDelFlg(StringToolUtils.STRING_ONE);

            // 设置修改时间
            tbTechnicalStandardEntity.setUpdateDate(tempCurrentDate);

            // 设置修改人id
            tbTechnicalStandardEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 添加用户信息
            tbTechnicalStandardEntityMapper.updateByPrimaryKeySelective(tbTechnicalStandardEntity);

            //========================= 修改技术标准信息  end ========================

            //========================= 删除原有文件  start ========================

            TbTechnicalStandardEntity retEntity = tbTechnicalStandardEntityMapper.selectByPrimaryKey(paramVO.getStandardId());

            // 如果查询到该路径，则删除文件
            if(null != retEntity){

                // 获取文件路径
                String tempPathStr = retEntity.getMaterialPath().substring(NumberToolUtils.INT_ZERO,retEntity.getMaterialPath().lastIndexOf("/"));

                // 删除文件
                FileUtils.deleteFile(tempPathStr);

            }

            //========================= 删除原有文件  end ========================

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0009 删除失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0009);
        }
    }
}
