package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbProLetterEntityMapper;
import com.hd.common.entity.TbProLetterEntity;
import com.hd.common.util.*;
import com.hd.manager.dao.HTWholeProcessLetterDao;
import com.hd.manager.dao.bean.HTWholeProcessLetterBean;
import com.hd.manager.service.HTWholeProcessLetterService;
import com.hd.manager.vo.HTWholeProcessLetterVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 全过程发出信函登记表阶段 业务层
 *
 * @author zjy
 * Created in 2019/9/9 14:53
 */
@Service
public class HTWholeProcessLetterServiceImpl extends BaseServiceImpl implements HTWholeProcessLetterService {

    @Resource
    private TbProLetterEntityMapper tbProLetterEntityMapper;

    /**
     * dao 持久层资源
     */
    @Resource
    private HTWholeProcessLetterDao htWholeProcessLetterDao;

    /**
     * 查询列表
     *
     * @return 发出信函登记表 列表信息
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchLetterPeriodList(HTWholeProcessLetterVO paramVO) throws SystemException {
        // 结果Map
        Map<String, Object> resultPageMap = new HashMap<>();

        try {

            HTWholeProcessLetterBean paramBean = new HTWholeProcessLetterBean();

            // 设置项目ID
            paramBean.setProjectId(StringToolUtils.convertNullObjectToString(paramVO.getProjectId()));

            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            // 查询列表
            List<HTWholeProcessLetterBean> letterList = htWholeProcessLetterDao.excSearchLetterPeriodList(paramBean);

            // 查询个数
            int letterCount = htWholeProcessLetterDao.excSearchLetterPeriodCount(paramBean);

            HTWholeProcessLetterVO tmpVO = null;

            List<HTWholeProcessLetterVO> retLetterList = new ArrayList<>();

            // Bean 转化 VO
            for (HTWholeProcessLetterBean tmpBean : letterList) {
                tmpVO = new HTWholeProcessLetterVO();
                // 设置ID
                tmpVO.setLetterId(StringToolUtils.convertNullObjectToString(tmpBean.getLetterId()));
                // 设置日期
                tmpVO.setEditDate(DateToolUtils.convertDateFormat(tmpBean.getEditDate(), DateToolUtils.yyyy_MM_dd));
                // 设置文件名称
                tmpVO.setFileName(StringToolUtils.isNull(tmpBean.getFileName())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getFileName()));
                // 设置文件编号
                tmpVO.setFileNumber(StringToolUtils.isNull(tmpBean.getFileNumber())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getFileNumber()));
                // 设置发文形式
                tmpVO.setSendType(StringToolUtils.isNull(tmpBean.getSendType())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getSendType()));
                // 设置收件单位
                tmpVO.setReceiveUnit(StringToolUtils.isNull(tmpBean.getReceiveUnit())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getReceiveUnit()));
                // 设置收件人
                tmpVO.setReceivePeople(StringToolUtils.isNull(tmpBean.getReceivePeople())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getReceivePeople()));
                // 设置文件形式
                tmpVO.setFileType(StringToolUtils.isNull(tmpBean.getFileType())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getFileType()));
                // 设置份数
                tmpVO.setCopyNum(StringToolUtils.isNull(tmpBean.getCopyNum())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getCopyNum()));
                // 设置备注
                tmpVO.setRemark(StringToolUtils.isNull(tmpBean.getRemark())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getRemark()));
                // 设置信函文件路径
                tmpVO.setLetterFilePath(StringToolUtils.isNull(tmpBean.getLetterFilePath())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getLetterFilePath()));
                // 设置信函文件真实名称
                tmpVO.setLetterFileName(StringToolUtils.isNull(tmpBean.getLetterFileName())?"-":StringToolUtils.convertNullObjectToString(tmpBean.getLetterFileName()));

                retLetterList.add(tmpVO);
            }

            resultPageMap.put(Constant.DATA_LIST_KEY, retLetterList);

            resultPageMap.put(Constant.DATA_COUNT_KEY, letterCount);
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultPageMap;

    }

    /**
     * 查询详情
     *
     * @return 发出信函登记表 详情
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTWholeProcessLetterVO excSearchProjectLetterPeriodInfo(HTWholeProcessLetterVO paramVO) throws SystemException {
        try {

            //设置项目ID
            TbProLetterEntity tbProLetterEntity = tbProLetterEntityMapper.selectByPrimaryKey(paramVO.getLetterId());

            // 转化VO
            HTWholeProcessLetterVO htWholeProcessLetterVO = (HTWholeProcessLetterVO) ObjectReflectUtils.convertObj(tbProLetterEntity, HTWholeProcessLetterVO.class);
            htWholeProcessLetterVO.setCopyNum(this.getNumberStr(htWholeProcessLetterVO.getCopyNum()));
            return htWholeProcessLetterVO;

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
    public void excSaveWholeProcessLetter(HTWholeProcessLetterVO paramVO, MultipartFile file) throws LogicException, SystemException {
        try {
            Date currentDate = new Date();

            TbProLetterEntity tbProLetterEntity = new TbProLetterEntity();

            // 设置项目ID
            tbProLetterEntity.setProjectId(StringToolUtils.convertNullObjectToString(paramVO.getProjectId()));

            // 设置日期
            tbProLetterEntity.setEditDate(DateToolUtils.convertDateFormat(paramVO.getEditDate(), DateToolUtils.yyyy_MM_dd));

            // 设置文件名称
            tbProLetterEntity.setFileName(StringToolUtils.convertNullObjectToString(paramVO.getFileName()));

            // 设置文件编号
            tbProLetterEntity.setFileNumber(StringToolUtils.convertNullObjectToString(paramVO.getFileNumber()));

            // 设置发文形式
            tbProLetterEntity.setSendType(StringToolUtils.convertNullObjectToString(paramVO.getSendType()));

            // 设置收件单位
            tbProLetterEntity.setReceiveUnit(StringToolUtils.convertNullObjectToString(paramVO.getReceiveUnit()));

            // 设置收件人
            tbProLetterEntity.setReceivePeople(StringToolUtils.convertNullObjectToString(paramVO.getReceivePeople()));

            // 设置文件形式
            tbProLetterEntity.setFileType(StringToolUtils.convertNullObjectToString(paramVO.getFileType()));

            // 设置份数
            tbProLetterEntity.setCopyNum(StringToolUtils.convertNullObjectToString(paramVO.getCopyNum()));

            //备注
            tbProLetterEntity.setRemark(StringToolUtils.convertNullObjectToString(paramVO.getRemark()));

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
            filePath = Constant.FILE_SEPARATE + "wholeProcess" + Constant.FILE_SEPARATE + "letter" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
            //自定义模板真实名称
            tbProLetterEntity.setLetterFileName(realFileName);
            //自定义模板文件路径
            tbProLetterEntity.setLetterFilePath(filePath);
            // 上传文件
            boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
            //上传失败时抛出异常
            if (!isUploadSuccess) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0011);
            }

            //创建时间
            tbProLetterEntity.setCreateDate(currentDate);
            //修改时间
            tbProLetterEntity.setUpdateDate(currentDate);
            //创建人
            tbProLetterEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改人
            tbProLetterEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //是否删除
            tbProLetterEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

            tbProLetterEntityMapper.insertSelective(tbProLetterEntity);

        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 修改信息
     *
     * @param paramVO 新增信息
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUpdateWholeProcessLetter(HTWholeProcessLetterVO paramVO, MultipartFile file) throws LogicException, SystemException {
        try {
            Date currentDate = new Date();

            TbProLetterEntity tbProLetterEntity = new TbProLetterEntity();

            // 设置ID
            tbProLetterEntity.setLetterId(StringToolUtils.convertNullObjectToString(paramVO.getLetterId()));

            // 设置日期
            tbProLetterEntity.setEditDate(DateToolUtils.convertDateFormat(paramVO.getEditDate(), DateToolUtils.yyyy_MM_dd));

            // 设置文件名称
            tbProLetterEntity.setFileName(StringToolUtils.convertNullObjectToString(paramVO.getFileName()));

            // 设置文件编号
            tbProLetterEntity.setFileNumber(StringToolUtils.convertNullObjectToString(paramVO.getFileNumber()));

            // 设置发文形式
            tbProLetterEntity.setSendType(StringToolUtils.convertNullObjectToString(paramVO.getSendType()));

            // 设置收件单位
            tbProLetterEntity.setReceiveUnit(StringToolUtils.convertNullObjectToString(paramVO.getReceiveUnit()));

            // 设置收件人
            tbProLetterEntity.setReceivePeople(StringToolUtils.convertNullObjectToString(paramVO.getReceivePeople()));

            // 设置文件形式
            tbProLetterEntity.setFileType(StringToolUtils.convertNullObjectToString(paramVO.getFileType()));

            // 设置份数
            tbProLetterEntity.setCopyNum(StringToolUtils.convertNullObjectToString(paramVO.getCopyNum()));

            //备注
            tbProLetterEntity.setRemark(StringToolUtils.convertNullObjectToString(paramVO.getRemark()));

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
                filePath = Constant.FILE_SEPARATE + "wholeProcess" + Constant.FILE_SEPARATE + "letter" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                //自定义模板真实名称
                tbProLetterEntity.setLetterFileName(realFileName);
                //自定义模板文件路径
                tbProLetterEntity.setLetterFilePath(filePath);
                // 上传文件
                boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
                //上传失败时抛出异常
                if (!isUploadSuccess) {
                    throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0011);
                }
            }

            //修改时间
            tbProLetterEntity.setUpdateDate(currentDate);
            //修改人
            tbProLetterEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            tbProLetterEntityMapper.updateByPrimaryKeySelective(tbProLetterEntity);

        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询项目发出信函登记表列表
     *
     * @return
     */
    @Override
    public List<HTWholeProcessLetterVO> excSearchProjectLetterPeriodListForExport(HTWholeProcessLetterVO paramVO) throws SystemException {
        try {
            HTWholeProcessLetterBean paramBean = new HTWholeProcessLetterBean();
            //设置项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            //查询项目发出信函登记表列表
            List<HTWholeProcessLetterBean> htWholeProcessLetterBeanList = htWholeProcessLetterDao.excSearchProLetterPeriodListForExport(paramBean);
            List<HTWholeProcessLetterVO> htWholeProcessLetterVOList = new ArrayList<>();
            //遍历转换格式
            for (HTWholeProcessLetterBean htWholeProcessLetterBean:htWholeProcessLetterBeanList){
                //转换格式
                HTWholeProcessLetterVO  htWholeProcessLetterVO = (HTWholeProcessLetterVO) ObjectReflectUtils.convertObj(htWholeProcessLetterBean, HTWholeProcessLetterVO.class);
                htWholeProcessLetterVO.setEditDate(DateToolUtils.convertDateFormat(htWholeProcessLetterBean.getEditDate(),DateToolUtils.yyyy_MM_dd));
                String tmpFileType = htWholeProcessLetterBean.getFileType();
                htWholeProcessLetterVO.setFileType(tmpFileType.equals("1")?"快递":(tmpFileType.equals("2")?"手递":(tmpFileType.equals("3")?"电邮":(tmpFileType.equals("4")?"传真":StringToolUtils.STRING_EMPTY))));
                String tmpSendType = htWholeProcessLetterBean.getSendType();
                htWholeProcessLetterVO.setSendType(tmpSendType.equals("1")?"纸版":(tmpSendType.equals("2")?"电子版":(tmpSendType.equals("3")?"纸版和电子版":StringToolUtils.STRING_EMPTY)));
                htWholeProcessLetterVOList.add(htWholeProcessLetterVO);
            }
            return htWholeProcessLetterVOList;
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
