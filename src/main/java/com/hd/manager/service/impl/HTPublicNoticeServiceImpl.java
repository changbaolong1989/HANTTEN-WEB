package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbPublicNoticeEntityMapper;
import com.hd.common.entity.TbPublicNoticeEntity;
import com.hd.common.entity.TbPublicNoticeEntityExample;
import com.hd.common.util.*;
import com.hd.manager.dao.HTPublicNoticeDao;
import com.hd.manager.dao.bean.HTPublicNoticeBean;
import com.hd.manager.service.HTPublicNoticeService;
import com.hd.manager.vo.HTPublicNoticeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * 设置-公告管理 业务层 实现类
 *
 * @author jwl
 * Created in 2019/7/3 15:31
 */
@Service
public class HTPublicNoticeServiceImpl extends BaseServiceImpl implements HTPublicNoticeService {

    /**
     * 公告dao 持久层资源
     */
    @Resource
    private HTPublicNoticeDao htPublicNoticeDao;

    /**
     * 公告entity 持久层资源
     */
    @Resource
    private TbPublicNoticeEntityMapper tbPublicNoticeEntityMapper;

    /**
     * 查询公告列表数据
     *
     * @return 公告列表数据
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchPublicNoticeList(HTPublicNoticeVO paramVO) throws SystemException {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            HTPublicNoticeBean paramBean = new HTPublicNoticeBean();

            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            // 查询公告列表数据的总条数
            int retCount = htPublicNoticeDao.excSearchPublicNoticeListCount(paramBean);

            // 查询公告列表数据
            List<HTPublicNoticeBean> publicNoticeList = htPublicNoticeDao.excSearchPublicNoticeList(paramBean);

            HTPublicNoticeVO htPublicNoticeVO = null;

            // 集合
            List<HTPublicNoticeVO> retPublicNoticeList = new ArrayList<>();

            // Bean 转化成 VO
            for (HTPublicNoticeBean htPublicNoticeBean : publicNoticeList) {

                htPublicNoticeVO = new HTPublicNoticeVO();

                // 设置公告ID
                htPublicNoticeVO.setNoticeId(StringToolUtils.convertNullObjectToString(
                        htPublicNoticeBean.getNoticeId()));

                // 设置公告名称
                htPublicNoticeVO.setNoticeName(StringToolUtils.convertNullObjectToString(
                        htPublicNoticeBean.getNoticeName()));

                // 附件路径
                htPublicNoticeVO.setMaterialPath(StringToolUtils.convertNullObjectToString(
                        htPublicNoticeBean.getMaterialPath()));

                // 附件名称
                htPublicNoticeVO.setMaterialName(StringToolUtils.convertNullObjectToString(
                        htPublicNoticeBean.getMaterialName()));

                // 设置发布人
                htPublicNoticeVO.setUserName(StringToolUtils.convertNullObjectToString(
                        htPublicNoticeBean.getUserName()));

                // 设置发布时间
                htPublicNoticeVO.setReleaseDate(DateToolUtils.convertDateFormat(
                        htPublicNoticeBean.getReleaseDate(),DateToolUtils.yyyy_MM_dd));

                retPublicNoticeList.add(htPublicNoticeVO);

            }

            resultMap.put(Constant.DATA_LIST_KEY, retPublicNoticeList);

            resultMap.put(Constant.DATA_COUNT_KEY, retCount);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    /**
     * 添加公告信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excSavePublicNoticeInfo(HTPublicNoticeVO paramVO, MultipartFile signPathFile) throws LogicException, SystemException {
        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        try {

            // =================================查询公告名称是否重复 start========================================

            TbPublicNoticeEntityExample tbPublicNoticeEntityExample = new TbPublicNoticeEntityExample();

            TbPublicNoticeEntityExample.Criteria criteria = tbPublicNoticeEntityExample.createCriteria();

            criteria.andNoticeNameEqualTo(paramVO.getNoticeName());

            List<TbPublicNoticeEntity> entityList = tbPublicNoticeEntityMapper.selectByExample(tbPublicNoticeEntityExample);

            if(entityList.size() > NumberToolUtils.DEFAULT_INT_ZERO){
                // 有重复，抛出异常(公告名称重复，请重新填写！)
                throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0002);
            }

            // =================================查询公告名称是否重复 end========================================

            // 生成uuid
            String tempRandomUUIDStr = UUID.randomUUID().toString();

            // 文件上传后的路径
            String signPathFilePath = null;

            // 自定义上传文件的文件名称（公告id+.后缀名）
            String tempFileNameStr = null;

            //========================= 添加公告信息  start ========================

            TbPublicNoticeEntity tbPublicNoticeEntity = new TbPublicNoticeEntity();

            // 设置公告id
            tbPublicNoticeEntity.setNoticeId(tempRandomUUIDStr);

            // 设置公告名称
            tbPublicNoticeEntity.setNoticeName(StringToolUtils.convertNullObjectToString(paramVO.getNoticeName()));

            // 如果附件不为空，则上传附件，附件名称和附件路径
            if(null != signPathFile){
                // 源文件名
                String signPathFileName = signPathFile.getOriginalFilename();

                // 文件上传后的路径
                signPathFilePath = Constant.FILE_SEPARATE+"publicNotice"
                        + Constant.FILE_SEPARATE+ tempRandomUUIDStr + Constant.FILE_SEPARATE;

                // 自定义上传文件的文件名称（公告id+.后缀名）
                tempFileNameStr = tempRandomUUIDStr + signPathFileName.substring(signPathFileName.lastIndexOf("."));

                // 设置附件路径
                tbPublicNoticeEntity.setMaterialPath(signPathFilePath + tempFileNameStr);

                // 设置附件名称
                tbPublicNoticeEntity.setMaterialName(StringToolUtils.convertNullObjectToString(signPathFileName));

            }

            // 设置发布时间
            tbPublicNoticeEntity.setReleaseDate(tempCurrentDate);

            // 设置创建时间
            tbPublicNoticeEntity.setCreateDate(tempCurrentDate);

            // 设置修改时间
            tbPublicNoticeEntity.setUpdateDate(tempCurrentDate);

            // 设置发布人id
            tbPublicNoticeEntity.setReleasePeople(paramVO.getLoginUserInfo().getLoginUserId());

            // 设置创建人id
            tbPublicNoticeEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 设置修改人id
            tbPublicNoticeEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 是否删除
            tbPublicNoticeEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

            // 添加公告信息
            htPublicNoticeDao.excInsertPublicNoticeInfo(tbPublicNoticeEntity);

            //========================= 添加公告信息  end ========================

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
     * 查询公告详细信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTPublicNoticeVO excSearchPublicNoticeInfo(HTPublicNoticeVO paramVO) throws SystemException {

        HTPublicNoticeVO retPublicNoticeVO = new HTPublicNoticeVO();

        try{

            HTPublicNoticeBean paramBean = new  HTPublicNoticeBean();

            // 设置公告ID
            paramBean.setNoticeId(StringToolUtils.convertNullObjectToString(paramVO.getNoticeId()));

            // 查询公告详情
            HTPublicNoticeBean htPublicNoticeBean = htPublicNoticeDao.excSearchPublicNoticeInfo(paramBean);

            if(null != htPublicNoticeBean){

                // 设置公告ID
                retPublicNoticeVO.setNoticeId(StringToolUtils
                        .convertNullObjectToString(htPublicNoticeBean.getNoticeId()));

                // 设置公告名称
                retPublicNoticeVO.setNoticeName(StringToolUtils
                        .convertNullObjectToString(htPublicNoticeBean.getNoticeName()));

                // 设置附件名称
                retPublicNoticeVO.setMaterialName(StringToolUtils
                        .convertNullObjectToString(htPublicNoticeBean.getMaterialName()));

                // 设置修改时间
                retPublicNoticeVO.setUpdateDate(DateToolUtils
                        .convertDateFormat(htPublicNoticeBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            }

        }catch (Exception e){
            throw new SystemException(e,MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retPublicNoticeVO;
    }

    /**
     * 更改公告信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdatePublicNoticeInfo(HTPublicNoticeVO paramVO, MultipartFile signPathFile) throws LogicException, SystemException {

        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        try {

            TbPublicNoticeEntity retEntity = tbPublicNoticeEntityMapper.selectByPrimaryKey(paramVO.getNoticeId());

            // =================================查询公告名称是否重复 start========================================

            TbPublicNoticeEntityExample tbPublicNoticeEntityExample = new TbPublicNoticeEntityExample();

            TbPublicNoticeEntityExample.Criteria criteria = tbPublicNoticeEntityExample.createCriteria();

            criteria.andNoticeNameEqualTo(paramVO.getNoticeName());

            criteria.andNoticeIdNotEqualTo(paramVO.getNoticeId());

            List<TbPublicNoticeEntity> entityList = tbPublicNoticeEntityMapper.selectByExample(tbPublicNoticeEntityExample);

            if(entityList.size() > NumberToolUtils.DEFAULT_INT_ZERO){
                // 有重复，抛出异常(公告名称重复，请重新填写！)
                throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0002);
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

            //========================= 修改公告信息  start ========================

            // 文件上传后的路径
            String signPathFilePath = null;

            // 自定义上传文件的文件名称（公告id+.后缀名）
            String tempFileNameStr = null;

            TbPublicNoticeEntity tbPublicNoticeEntity = new TbPublicNoticeEntity();

            // 设置公告id
            tbPublicNoticeEntity.setNoticeId(StringToolUtils.convertNullObjectToString(paramVO.getNoticeId()));

            // 设置公告名称
            tbPublicNoticeEntity.setNoticeName(StringToolUtils.convertNullObjectToString(paramVO.getNoticeName()));

            // 设置修改时间
            tbPublicNoticeEntity.setUpdateDate(tempCurrentDate);

            // 设置修改人id
            tbPublicNoticeEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 如果附件不为空，则上传附件，附件名称和附件路径
            if(null != signPathFile){

                // 源文件名
                String signPathFileName = signPathFile.getOriginalFilename();

                // 文件上传后的路径
                signPathFilePath = Constant.FILE_SEPARATE + "publicNotice"
                        + Constant.FILE_SEPARATE + paramVO.getNoticeId() + Constant.FILE_SEPARATE;

                // 自定义上传文件的文件名称（公告id+.后缀名）
                tempFileNameStr = paramVO.getNoticeId() + signPathFileName.substring(signPathFileName.lastIndexOf("."));

                // 设置附件路径
                tbPublicNoticeEntity.setMaterialPath(signPathFilePath + tempFileNameStr);

                // 设置附件名称
                tbPublicNoticeEntity.setMaterialName(StringToolUtils.convertNullObjectToString(signPathFileName));

            }

            // 修改公告信息
            tbPublicNoticeEntityMapper.updateByPrimaryKeySelective(tbPublicNoticeEntity);

            //========================= 修改公告信息  end ========================

            // 如果附件不为空，则上传附件，附件名称和附件路径
            if(null != signPathFile){

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
     * 删除公告信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excDeletePublicNoticeInfo(HTPublicNoticeVO paramVO) throws SystemException {

        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        try {

            //========================= 修改公告信息  start ========================

            TbPublicNoticeEntity tbPublicNoticeEntity = new TbPublicNoticeEntity();

            // 设置公告id
            tbPublicNoticeEntity.setNoticeId(StringToolUtils.convertNullObjectToString(paramVO.getNoticeId()));

            // 设置是否删除状态
            tbPublicNoticeEntity.setIsDelFlg(StringToolUtils.STRING_ONE);

            // 设置修改时间
            tbPublicNoticeEntity.setUpdateDate(tempCurrentDate);

            // 设置修改人id
            tbPublicNoticeEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 添加用户信息
            tbPublicNoticeEntityMapper.updateByPrimaryKeySelective(tbPublicNoticeEntity);

            //========================= 修改公告信息  end ========================

            //========================= 删除原有文件  start ========================

            TbPublicNoticeEntity retEntity = tbPublicNoticeEntityMapper.selectByPrimaryKey(paramVO.getNoticeId());

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
