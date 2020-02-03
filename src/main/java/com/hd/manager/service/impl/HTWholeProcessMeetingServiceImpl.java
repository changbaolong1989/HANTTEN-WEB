package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProMeetingEntityMapper;
import com.hd.common.entity.TbProMeetingEntity;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessMeetingDao;
import com.hd.manager.dao.bean.HTWholeProcessMeetingBean;
import com.hd.manager.service.HTWholeProcessMeetingService;
import com.hd.manager.vo.HTWholeProcessMeetingVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * 全过程会议纪要 业务层
 *
 * @author zjy
 * Created in 2019/9/9 14:53
 */
@Service
public class HTWholeProcessMeetingServiceImpl extends BaseServiceImpl implements HTWholeProcessMeetingService {

    @Resource
    private TbProMeetingEntityMapper tbProMeetingEntityMapper;

    /**
     * dao 持久层资源
     */
    @Resource
    private HTWholeProcessMeetingDao htWholeProcessMeetingDao;

    /**
     * 查询列表
     *
     * @return 发出信函登记表 列表信息
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchMeetingPeriodList(HTWholeProcessMeetingVO paramVO) throws SystemException {
        // 结果Map
        Map<String, Object> resultPageMap = new HashMap<>();

        try {

            HTWholeProcessMeetingBean paramBean = new HTWholeProcessMeetingBean();

            // 设置项目ID
            paramBean.setProjectId(StringToolUtils.convertNullObjectToString(paramVO.getProjectId()));

            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            // 查询列表
            List<HTWholeProcessMeetingBean> meetingList = htWholeProcessMeetingDao.excSearchMeetingPeriodList(paramBean);

            // 查询个数
            int meetingCount = htWholeProcessMeetingDao.excSearchMeetingPeriodCount(paramBean);

            HTWholeProcessMeetingVO tmpVO = null;

            List<HTWholeProcessMeetingVO> retMeetingList = new ArrayList<>();

            // Bean 转化 VO
            for (HTWholeProcessMeetingBean tmpBean : meetingList) {
                tmpVO = new HTWholeProcessMeetingVO();
                // 设置ID
                tmpVO.setMeetingId(StringToolUtils.convertNullObjectToString(tmpBean.getMeetingId()));
                // 设置日期
                tmpVO.setEditDate(DateToolUtils.convertDateFormat(tmpBean.getEditDate(), DateToolUtils.yyyy_MM_dd));
                // 设置地点
                tmpVO.setAddress(StringToolUtils.isNull(tmpBean.getAddress())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getAddress()));
                // 设置会议主题
                tmpVO.setMeetingTheme(StringToolUtils.isNull(tmpBean.getMeetingTheme())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getMeetingTheme()));
                // 设置内容摘要
                tmpVO.setContentSummary(StringToolUtils.isNull(tmpBean.getContentSummary())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getContentSummary()));
                // 设置参与单位
                tmpVO.setParticipatoryUnit(StringToolUtils.isNull(tmpBean.getParticipatoryUnit())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getParticipatoryUnit()));
                // 设置备注
                tmpVO.setRemark(StringToolUtils.isNull(tmpBean.getRemark())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getRemark()));
                // 设置会议文件路径
                tmpVO.setMeetingFilePath(StringToolUtils.isNull(tmpBean.getMeetingFilePath())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getMeetingFilePath()));
                // 设置会议文件真实名称
                tmpVO.setMeetingFileName(StringToolUtils.isNull(tmpBean.getMeetingFileName())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getMeetingFileName()));

                retMeetingList.add(tmpVO);
            }

            resultPageMap.put(Constant.DATA_LIST_KEY, retMeetingList);

            resultPageMap.put(Constant.DATA_COUNT_KEY, meetingCount);
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultPageMap;
    }

    /**
     * 查询详情
     *
     * @return 发出信函登记表 列表信息
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTWholeProcessMeetingVO excSearchProjectMeetingPeriodInfo(HTWholeProcessMeetingVO paramVO) throws SystemException {
        try {

            //设置项目ID
            TbProMeetingEntity tbProMeetingEntity = tbProMeetingEntityMapper.selectByPrimaryKey(paramVO.getMeetingId());

            // 转化VO
            return tbProMeetingEntity == null ? null : (HTWholeProcessMeetingVO) ObjectReflectUtils.convertObj(tbProMeetingEntity, HTWholeProcessMeetingVO.class);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 新增信息
     *
     * @param paramVO 新增信息
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excSaveWholeProcessMeeting(HTWholeProcessMeetingVO paramVO, MultipartFile file) throws LogicException, SystemException {
        try {
            Date currentDate = new Date();

            TbProMeetingEntity tbProMeetingEntity = new TbProMeetingEntity();

            // 设置项目ID
            tbProMeetingEntity.setProjectId(StringToolUtils.convertNullObjectToString(paramVO.getProjectId()));

            // 设置日期
            tbProMeetingEntity.setEditDate(DateToolUtils.convertDateFormat(paramVO.getEditDate(), DateToolUtils.yyyy_MM_dd));

            // 设置地点
            tbProMeetingEntity.setAddress(StringToolUtils.convertNullObjectToString(paramVO.getAddress()));

            // 设置会议主题
            tbProMeetingEntity.setMeetingTheme(StringToolUtils.convertNullObjectToString(paramVO.getMeetingTheme()));

            // 设置内容摘要
            tbProMeetingEntity.setContentSummary(StringToolUtils.convertNullObjectToString(paramVO.getContentSummary()));

            // 设置参与单位
            tbProMeetingEntity.setParticipatoryUnit(StringToolUtils.convertNullObjectToString(paramVO.getParticipatoryUnit()));

            //备注
            tbProMeetingEntity.setRemark(StringToolUtils.convertNullObjectToString(paramVO.getRemark()));

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
            filePath = Constant.FILE_SEPARATE + "wholeProcess" + Constant.FILE_SEPARATE + "meeting" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
            //自定义模板真实名称
            tbProMeetingEntity.setMeetingFileName(realFileName);
            //自定义模板文件路径
            tbProMeetingEntity.setMeetingFilePath(filePath);
            // 上传文件
            boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
            //上传失败时抛出异常
            if (!isUploadSuccess) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0011);
            }

            //创建时间
            tbProMeetingEntity.setCreateDate(currentDate);
            //修改时间
            tbProMeetingEntity.setUpdateDate(currentDate);
            //创建人
            tbProMeetingEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改人
            tbProMeetingEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //是否删除
            tbProMeetingEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

            tbProMeetingEntityMapper.insertSelective(tbProMeetingEntity);

        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 修改信息
     *
     * @param paramVO 修改信息
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUpdateWholeProcessMeeting(HTWholeProcessMeetingVO paramVO, MultipartFile file) throws LogicException, SystemException {
        try {
            Date currentDate = new Date();

            TbProMeetingEntity tbProMeetingEntity = new TbProMeetingEntity();

            // 设置ID
            tbProMeetingEntity.setMeetingId(StringToolUtils.convertNullObjectToString(paramVO.getMeetingId()));

            // 设置日期
            tbProMeetingEntity.setEditDate(DateToolUtils.convertDateFormat(paramVO.getEditDate(), DateToolUtils.yyyy_MM_dd));

            // 设置地点
            tbProMeetingEntity.setAddress(StringToolUtils.convertNullObjectToString(paramVO.getAddress()));

            // 设置会议主题
            tbProMeetingEntity.setMeetingTheme(StringToolUtils.convertNullObjectToString(paramVO.getMeetingTheme()));

            // 设置内容摘要
            tbProMeetingEntity.setContentSummary(StringToolUtils.convertNullObjectToString(paramVO.getContentSummary()));

            // 设置参与单位
            tbProMeetingEntity.setParticipatoryUnit(StringToolUtils.convertNullObjectToString(paramVO.getParticipatoryUnit()));

            //备注
            tbProMeetingEntity.setRemark(StringToolUtils.convertNullObjectToString(paramVO.getRemark()));

            /*----------------文件上传信息-----------------*/
            if (null != file) {
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
                filePath = Constant.FILE_SEPARATE + "wholeProcess" + Constant.FILE_SEPARATE + "meeting" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                //自定义模板真实名称
                tbProMeetingEntity.setMeetingFileName(realFileName);
                //自定义模板文件路径
                tbProMeetingEntity.setMeetingFilePath(filePath);
                // 上传文件
                boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
                //上传失败时抛出异常
                if (!isUploadSuccess) {
                    throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0011);
                }
            }

            //修改时间
            tbProMeetingEntity.setUpdateDate(currentDate);
            //修改人
            tbProMeetingEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            tbProMeetingEntityMapper.updateByPrimaryKeySelective(tbProMeetingEntity);

        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询信息（导出）
     *
     * @param paramVO 参数
     * @throws SystemException 不可预估的异常
     */
    @Override
    public List<HTWholeProcessMeetingVO> excSearchProjectMeetingPeriodListForExport(HTWholeProcessMeetingVO paramVO) throws SystemException{
        try {
            HTWholeProcessMeetingBean paramBean = new HTWholeProcessMeetingBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            //查询项目发出信函登记表列表
            List<HTWholeProcessMeetingBean> htWholeProcessMeetingBeanList = htWholeProcessMeetingDao.excSearchProMeetingPeriodListForExport(paramBean);
            List<HTWholeProcessMeetingVO> htWholeProcessMeetingVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessMeetingBean htWholeProcessMeetingBean:htWholeProcessMeetingBeanList){
                //转换格式
                HTWholeProcessMeetingVO  htWholeProcessMeetingVO = (HTWholeProcessMeetingVO) ObjectReflectUtils.convertObj(htWholeProcessMeetingBean, HTWholeProcessMeetingVO.class);
                htWholeProcessMeetingVO.setEditDate(DateToolUtils.convertDateFormat(htWholeProcessMeetingBean.getEditDate(),DateToolUtils.yyyy_MM_dd));
                htWholeProcessMeetingVOList.add(htWholeProcessMeetingVO);
            }
            return htWholeProcessMeetingVOList;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


}
