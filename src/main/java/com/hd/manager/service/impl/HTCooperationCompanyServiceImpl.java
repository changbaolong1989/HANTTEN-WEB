package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbCompanyEntityMapper;
import com.hd.common.entity.TbCompanyEntity;
import com.hd.common.util.*;
import com.hd.manager.dao.HTCompanyDao;
import com.hd.manager.dao.HTCooperationCompanyDao;
import com.hd.manager.dao.bean.HTCompanyBean;
import com.hd.manager.dao.bean.HTCooperationCompanyBean;
import com.hd.manager.service.HTCompanyService;
import com.hd.manager.service.HTCooperationCompanyService;
import com.hd.manager.vo.HTCompanyVO;
import com.hd.manager.vo.HTCooperationCompanyVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 设置-合作公司管理 业务层 实现类
 *
 * @author jwl
 * Created in 2019/7/3 15:31
 */
@Service
public class HTCooperationCompanyServiceImpl extends BaseServiceImpl implements HTCooperationCompanyService {

    /**
     * 合作公司dao 持久层资源
     */
    @Resource
    private HTCooperationCompanyDao htCooperationCompanyDao;

    /**
     * 合作公司entity 持久层资源
     */
    @Resource
    private TbCompanyEntityMapper tbCompanyEntityMapper;

    /**
     * 查询合作公司列表数据
     *
     * @return 合作公司列表数据
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchCooperationCompanyList(HTCooperationCompanyVO paramVO) throws SystemException {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            HTCooperationCompanyBean paramBean = new HTCooperationCompanyBean();

            // 设置合作公司名称
            paramBean.setCompanyName(StringToolUtils.convertNullObjectToString(paramVO.getCompanyName()));

            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            // 查询合作公司列表数据的总条数
            int retCount = htCooperationCompanyDao.excSearchCooperationCompanyListCount(paramBean);

            // 查询合作公司列表数据
            List<HTCooperationCompanyBean> cooperationCompanyList = htCooperationCompanyDao.excSearchCooperationCompanyList(paramBean);

            HTCooperationCompanyVO htCooperationCompanyVO = null;

            // 集合
            List<HTCooperationCompanyVO> retCooperationCompanyList = new ArrayList<>();

            // Bean 转化成 VO
            for (HTCooperationCompanyBean htCooperationCompanyBean : cooperationCompanyList) {

                htCooperationCompanyVO = new HTCooperationCompanyVO();

                // 设置合作公司ID
                htCooperationCompanyVO.setCompanyId(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getCompanyId()));

                // 设置合作公司名称
                htCooperationCompanyVO.setCompanyName(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getCompanyName()));

                // 设置序号
                htCooperationCompanyVO.setSerialNum(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getSerialNum()));

                // 设置地址
                htCooperationCompanyVO.setCompanyAddress(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getCompanyAddress()));

                // 设置负责人
                htCooperationCompanyVO.setChargePeople(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getChargePeople()));

                // 设置电话
                htCooperationCompanyVO.setPhoneNum(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getPhoneNum()));

                retCooperationCompanyList.add(htCooperationCompanyVO);

            }

            resultMap.put(Constant.DATA_LIST_KEY, retCooperationCompanyList);

            resultMap.put(Constant.DATA_COUNT_KEY, retCount);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    /**
     * 添加合作公司信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excSaveCooperationCompanyInfo(HTCooperationCompanyVO paramVO) throws LogicException, SystemException {
        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        try {

            HTCooperationCompanyBean paramBean = new HTCooperationCompanyBean();

            //========================= 查询合作公司名称是否重复  start ========================

            // 设置合作公司名称
            paramBean.setCompanyName(StringToolUtils.convertNullObjectToString(paramVO.getCompanyName()));

            // 查询合作公司名称是否重复
            int cooperationCompanyCount = htCooperationCompanyDao.excSearchCooperationCompanyName(paramBean);

            // 有重复，抛出异常(该合作公司名称已存在，请修改后重新提交！)
            if (cooperationCompanyCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_COOPERATION_COMPANY_0001);
            }

            //========================= 查询合作公司名称是否重复  end ========================

            //========================= 查询合作公司序号是否重复  start ========================

            // 设置合作公司序号
            paramBean.setSerialNum(StringToolUtils.convertNullObjectToString(paramVO.getSerialNum()));

            // 查询合作公司序号是否重复
            int serialNumCount = htCooperationCompanyDao.excSearchSerialNum(paramBean);

            // 有重复，抛出异常(该合作公司序号已存在，请修改后重新提交！)
            if (serialNumCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_COOPERATION_COMPANY_0002);
            }

            //========================= 查询合作公司序号是否重复  end ========================

            //========================= 添加合作公司信息  start ========================

            TbCompanyEntity tbCompanyEntity = new TbCompanyEntity();

            // 设置合作公司名称
            tbCompanyEntity.setCompanyName(StringToolUtils.convertNullObjectToString(paramVO.getCompanyName()));

            // 设置序号
            tbCompanyEntity.setSerialNum(StringToolUtils.convertNullObjectToString(paramVO.getSerialNum()));

            // 设置公司地址
            tbCompanyEntity.setCompanyAddress(StringToolUtils.convertNullObjectToString(paramVO.getCompanyAddress()));

            // 设置负责人
            tbCompanyEntity.setChargePeople(StringToolUtils.convertNullObjectToString(paramVO.getChargePeople()));

            // 设置电话
            tbCompanyEntity.setPhoneNum(StringToolUtils.convertNullObjectToString(paramVO.getPhoneNum()));

            // 设置是合作公司标识
            tbCompanyEntity.setIsCooperateCompany(StringToolUtils.STRING_ONE);

            // 设置创建时间
            tbCompanyEntity.setCreateDate(tempCurrentDate);

            // 设置修改时间
            tbCompanyEntity.setUpdateDate(tempCurrentDate);

            // 设置创建人id
            tbCompanyEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 设置修改人id
            tbCompanyEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 是否删除
            tbCompanyEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

            // 添加合作公司信息
            tbCompanyEntityMapper.insertSelective(tbCompanyEntity);

            //========================= 添加合作公司信息  end ========================

        } catch (LogicException lex) {
            throw  lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询合作公司详细信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTCooperationCompanyVO excSearchCooperationCompanyInfo(HTCooperationCompanyVO paramVO) throws SystemException {

        HTCooperationCompanyVO retCooperationCompanyVO = new HTCooperationCompanyVO();

        try{

            HTCooperationCompanyBean paramBean = new  HTCooperationCompanyBean();

            // 设置合作公司ID
            paramBean.setCompanyId(StringToolUtils.convertNullObjectToString(paramVO.getCompanyId()));

            // 查询合作公司详情
            HTCooperationCompanyBean htCooperationCompanyBean = htCooperationCompanyDao.excSearchCooperationCompanyInfo(paramBean);

            if(null != htCooperationCompanyBean){

                // 设置合作公司ID
                retCooperationCompanyVO.setCompanyId(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getCompanyId()));

                // 设置合作公司名称
                retCooperationCompanyVO.setCompanyName(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getCompanyName()));

                // 设置序号
                retCooperationCompanyVO.setSerialNum(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getSerialNum()));

                // 设置地址
                retCooperationCompanyVO.setCompanyAddress(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getCompanyAddress()));

                // 设置负责人
                retCooperationCompanyVO.setChargePeople(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getChargePeople()));

                // 设置电话
                retCooperationCompanyVO.setPhoneNum(StringToolUtils.convertNullObjectToString(
                        htCooperationCompanyBean.getPhoneNum()));

                // 设置修改时间
                retCooperationCompanyVO.setUpdateDate(DateToolUtils.convertDateFormat(
                        htCooperationCompanyBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            }

        }catch (Exception e){
            throw new SystemException(e,MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retCooperationCompanyVO;
    }

    /**
     * 更改合作公司信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdateCooperationCompanyInfo(HTCooperationCompanyVO paramVO) throws LogicException, SystemException {

        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        try {

            //========================= 查询合作公司名称是否重复  start ========================

            HTCooperationCompanyBean paramBean = new HTCooperationCompanyBean();

            // 设置合作公司id
            paramBean.setCompanyId(StringToolUtils.convertNullObjectToString(paramVO.getCompanyId()));

            // 设置合作公司名称
            paramBean.setCompanyName(StringToolUtils.convertNullObjectToString(paramVO.getCompanyName()));

            // 查询合作公司名称是否重复
            int cooperationCompanyCount = htCooperationCompanyDao.excSearchCooperationCompanyName(paramBean);

            // 有重复，抛出异常(该合作公司名称已存在，请修改后重新提交！)
            if (cooperationCompanyCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_COOPERATION_COMPANY_0001);
            }

            //========================= 查询合作公司名称是否重复  end ========================

            //========================= 查询合作公司序号是否重复  start ========================

            // 设置合作公司序号
            paramBean.setSerialNum(StringToolUtils.convertNullObjectToString(paramVO.getSerialNum()));

            // 查询合作公司序号是否重复
            int serialNumCount = htCooperationCompanyDao.excSearchSerialNum(paramBean);

            // 有重复，抛出异常(该合作公司序号已存在，请修改后重新提交！)
            if (serialNumCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_COOPERATION_COMPANY_0002);
            }

            //========================= 查询合作公司序号是否重复  end ========================

            //========================= 查询修改时间是否相等  start ========================

            // 查询公司详情
            TbCompanyEntity retEntity = tbCompanyEntityMapper.selectByPrimaryKey(paramVO.getCompanyId());

            // 修改时间
            String updateDate = DateToolUtils.convertDateFormat(
                    retEntity.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO);

            // 有重复，抛出异常(数据已被修改，请确认后重新提交！)
            if (!paramVO.getUpdateDate().equals(updateDate)) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMPANY_0004);
            }

            //========================= 查询修改时间是否相等  end ========================

            //========================= 修改公司信息  start ========================

            TbCompanyEntity tbCompanyEntity = new TbCompanyEntity();

            // 设置合作公司id
            tbCompanyEntity.setCompanyId(StringToolUtils.convertNullObjectToString(paramVO.getCompanyId()));

            // 设置合作公司名称
            tbCompanyEntity.setCompanyName(StringToolUtils.convertNullObjectToString(paramVO.getCompanyName()));

            // 设置序号
            tbCompanyEntity.setSerialNum(StringToolUtils.convertNullObjectToString(paramVO.getSerialNum()));

            // 设置公司地址
            tbCompanyEntity.setCompanyAddress(StringToolUtils.convertNullObjectToString(paramVO.getCompanyAddress()));

            // 设置负责人
            tbCompanyEntity.setChargePeople(StringToolUtils.convertNullObjectToString(paramVO.getChargePeople()));

            // 设置电话
            tbCompanyEntity.setPhoneNum(StringToolUtils.convertNullObjectToString(paramVO.getPhoneNum()));

            // 设置修改时间
            tbCompanyEntity.setUpdateDate(tempCurrentDate);

            // 设置修改人id
            tbCompanyEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 修改合作公司信息
            tbCompanyEntityMapper.updateByPrimaryKeySelective(tbCompanyEntity);

            //========================= 修改公司信息  end ========================

        } catch (LogicException lex) {
            throw  lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }
}
