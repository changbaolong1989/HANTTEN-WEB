package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbCompanyEntityMapper;
import com.hd.common.entity.TbCompanyEntity;
import com.hd.common.util.*;
import com.hd.manager.dao.HTCompanyDao;
import com.hd.manager.dao.bean.HTCompanyBean;
import com.hd.manager.service.HTCompanyService;
import com.hd.manager.vo.HTCompanyVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;

/**
 * 设置-公司管理 业务层 实现类
 *
 * @author jwl
 * Created in 2019/7/3 15:31
 */
@Service
public class HTCompanyServiceImpl extends BaseServiceImpl implements HTCompanyService {

    /**
     * 公司dao 持久层资源
     */
    @Resource
    private HTCompanyDao htCompanyDao;

    /**
     * 公司entity 持久层资源
     */
    @Resource
    private TbCompanyEntityMapper tbCompanyEntityMapper;

    /**
     * 查询公司列表数据
     *
     * @return 公司列表数据
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchCompanyList(HTCompanyVO paramVO) throws SystemException {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            HTCompanyBean paramBean = new HTCompanyBean();

            // 设置公司名称
            paramBean.setCompanyName(StringToolUtils.convertNullObjectToString(paramVO.getCompanyName()));

            // 设置公司缩写
            paramBean.setCondense(StringToolUtils.convertNullObjectToString(paramVO.getCondense()));

            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            // 查询公司列表数据的总条数
            int retCount = htCompanyDao.excSearchCompanyListCount(paramBean);

            // 查询公司列表数据
            List<HTCompanyBean> companyList = htCompanyDao.excSearchCompanyList(paramBean);

            HTCompanyVO htCompanyVO = null;

            // 集合
            List<HTCompanyVO> retCompanyList = new ArrayList<>();

            // Bean 转化成 VO
            for (HTCompanyBean htCompanyBean : companyList) {

                htCompanyVO = new HTCompanyVO();

                // 设置公司ID
                htCompanyVO.setCompanyId(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getCompanyId()));

                // 设置公司名称
                htCompanyVO.setCompanyName(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getCompanyName()));

                // 设置公司名称缩写
                htCompanyVO.setCondense(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getCondense()));

                // 设置开户行
                htCompanyVO.setBankName(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getBankName()));

                // 设置账号
                htCompanyVO.setBankAccount(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getBankAccount()));

                // 设置邮编
                htCompanyVO.setPostCode(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getPostCode()));

                // 设置电话
                htCompanyVO.setPhoneNum(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getPhoneNum()));

                retCompanyList.add(htCompanyVO);

            }

            resultMap.put(Constant.DATA_LIST_KEY, retCompanyList);

            resultMap.put(Constant.DATA_COUNT_KEY, retCount);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    /**
     * 添加公司信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excSaveCompanyInfo(HTCompanyVO paramVO) throws LogicException, SystemException {
        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        try {

            HTCompanyBean paramBean = new HTCompanyBean();

            //========================= 查询公司名称是否重复  start ========================

            // 设置公司名称
            paramBean.setCompanyName(StringToolUtils.convertNullObjectToString(paramVO.getCompanyName()));

            // 查询公司名称是否重复
            int companyNameCount = htCompanyDao.excSearchCompanyName(paramBean);

            // 有重复，抛出异常(该公司名称已存在，请修改后重新提交！)
            if (companyNameCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMPANY_0001);
            }

            //========================= 查询公司名称是否重复  end ========================

            //========================= 查询公司缩写是否重复  start ========================

            // 设置公司缩写
            paramBean.setCondense(StringToolUtils.convertNullObjectToString(paramVO.getCondense()));

            // 查询公司缩写是否重复
            int condenseCount = htCompanyDao.excSearchCondense(paramBean);

            // 有重复，抛出异常(该公司缩写已存在，请修改后重新提交！)
            if (condenseCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMPANY_0002);
            }

            //========================= 查询公司缩写是否重复  end ========================

            //========================= 添加公司信息  start ========================

            TbCompanyEntity tbCompanyEntity = new TbCompanyEntity();

            // 设置公司名称
            tbCompanyEntity.setCompanyName(StringToolUtils.convertNullObjectToString(paramVO.getCompanyName()));

            // 设置公司缩写
            tbCompanyEntity.setCondense(StringToolUtils.convertNullObjectToString(paramVO.getCondense()));

            // 设置公司地址
            tbCompanyEntity.setCompanyAddress(StringToolUtils.convertNullObjectToString(paramVO.getCompanyAddress()));

            // 设置开户行
            tbCompanyEntity.setBankName(StringToolUtils.convertNullObjectToString(paramVO.getBankName()));

            // 设置账号
            tbCompanyEntity.setBankAccount(StringToolUtils.convertNullObjectToString(paramVO.getBankAccount()));

            // 设置邮编
            tbCompanyEntity.setPostcode(StringToolUtils.convertNullObjectToString(paramVO.getPostCode()));

            // 设置电话(开票用)
            tbCompanyEntity.setPhoneNum(StringToolUtils.convertNullObjectToString(paramVO.getPhoneNum()));

            // 设置电话(对外联系用）
            tbCompanyEntity.setPhoneForeign(StringToolUtils.convertNullObjectToString(paramVO.getPhoneForeign()));

            // 设置传真
            tbCompanyEntity.setFaxNum(StringToolUtils.convertNullObjectToString(paramVO.getFaxNum()));

            // 设置邮箱
            tbCompanyEntity.setEmail(StringToolUtils.convertNullObjectToString(paramVO.getEmail()));

            // 设置公司地址
            tbCompanyEntity.setCompanyAddress(StringToolUtils.convertNullObjectToString(paramVO.getCompanyAddress()));

            // 设置不是合作公司标识
            tbCompanyEntity.setIsCooperateCompany(StringToolUtils.STRING_ZERO);

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

            // 添加公司信息
            tbCompanyEntityMapper.insertSelective(tbCompanyEntity);

            //========================= 添加公司信息  end ========================


        } catch (LogicException lex) {
            throw  lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询公司详细信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTCompanyVO excSearchCompanyInfo(HTCompanyVO paramVO) throws SystemException {

        HTCompanyVO retCompanyVO = new HTCompanyVO();

        try{

            HTCompanyBean paramBean = new  HTCompanyBean();

            // 设置公司ID
            paramBean.setCompanyId(StringToolUtils.convertNullObjectToString(paramVO.getCompanyId()));

            // 查询公司详情
            HTCompanyBean htCompanyBean = htCompanyDao.excSearchCompanyInfo(paramBean);

            if(null != htCompanyBean){

                // 设置公司ID
                retCompanyVO.setCompanyId(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getCompanyId()));

                // 设置公司名称
                retCompanyVO.setCompanyName(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getCompanyName()));

                // 设置公司名称缩写
                retCompanyVO.setCondense(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getCondense()));

                // 设置开户行
                retCompanyVO.setBankName(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getBankName()));

                // 设置账号
                retCompanyVO.setBankAccount(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getBankAccount()));

                // 设置邮编
                retCompanyVO.setPostCode(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getPostCode()));

                // 设置电话(开票用)
                retCompanyVO.setPhoneNum(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getPhoneNum()));

                // 设置电话(对外联系用)
                retCompanyVO.setPhoneForeign(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getPhoneForeign()));

                // 设置传真
                retCompanyVO.setFaxNum(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getFaxNum()));

                // 设置邮箱
                retCompanyVO.setEmail(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getEmail()));

                // 设置公司地址
                retCompanyVO.setCompanyAddress(StringToolUtils.convertNullObjectToString(
                        htCompanyBean.getCompanyAddress()));

                // 设置修改时间
                retCompanyVO.setUpdateDate(DateToolUtils.convertDateFormat(
                        htCompanyBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            }

        }catch (Exception e){
            throw new SystemException(e,MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retCompanyVO;
    }

    /**
     * 更改公司信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdateCompanyInfo(HTCompanyVO paramVO) throws LogicException, SystemException {

        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        try {

            //========================= 查询该公司是否已经和项目绑定  start ========================

            TbCompanyEntity tbCompanyEntity = new TbCompanyEntity();

            // 设置公司id
            tbCompanyEntity.setCompanyId(StringToolUtils.convertNullObjectToString(paramVO.getCompanyId()));

            // 查询该公司是否已经和项目绑定
            int count = htCompanyDao.excSearchCompanyIdFromProject(tbCompanyEntity);

            // 有重复，抛出异常(该公司信息已经与项目绑定，不能修改！)
            if (count > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMPANY_0003);
            }

            //========================= 查询该公司是否已经和项目绑定  end ========================

            //========================= 查询公司名称是否重复  start ========================

            HTCompanyBean paramBean = new HTCompanyBean();

            // 设置公司id
            paramBean.setCompanyId(StringToolUtils.convertNullObjectToString(paramVO.getCompanyId()));

            // 设置公司名称
            paramBean.setCompanyName(StringToolUtils.convertNullObjectToString(paramVO.getCompanyName()));

            // 查询公司名称是否重复
            int companyNameCount = htCompanyDao.excSearchCompanyName(paramBean);

            // 有重复，抛出异常(该公司名称已存在，请修改后重新提交！)
            if (companyNameCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMPANY_0001);
            }

            //========================= 查询公司名称是否重复  end ========================

            //========================= 查询公司缩写是否重复  start ========================

            // 设置公司缩写
            paramBean.setCondense(StringToolUtils.convertNullObjectToString(paramVO.getCondense()));

            // 查询公司缩写是否重复
            int condenseCount = htCompanyDao.excSearchCondense(paramBean);

            // 有重复，抛出异常(该公司缩写已存在，请修改后重新提交！)
            if (condenseCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMPANY_0002);
            }

            //========================= 查询公司缩写是否重复  end ========================

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

            // 设置公司名称
            tbCompanyEntity.setCompanyName(StringToolUtils.convertNullObjectToString(paramVO.getCompanyName()));

            // 设置公司缩写
            tbCompanyEntity.setCondense(StringToolUtils.convertNullObjectToString(paramVO.getCondense()));

            // 设置公司地址
            tbCompanyEntity.setCompanyAddress(StringToolUtils.convertNullObjectToString(paramVO.getCompanyAddress()));

            // 设置开户行
            tbCompanyEntity.setBankName(StringToolUtils.convertNullObjectToString(paramVO.getBankName()));

            // 设置账号
            tbCompanyEntity.setBankAccount(StringToolUtils.convertNullObjectToString(paramVO.getBankAccount()));

            // 设置邮编
            tbCompanyEntity.setPostcode(StringToolUtils.convertNullObjectToString(paramVO.getPostCode()));

            // 设置公司地址
            tbCompanyEntity.setCompanyAddress(StringToolUtils.convertNullObjectToString(paramVO.getCompanyAddress()));

            // 设置电话(开票用)
            tbCompanyEntity.setPhoneNum(StringToolUtils.convertNullObjectToString(paramVO.getPhoneNum()));

            // 设置电话(对外联系用）
            tbCompanyEntity.setPhoneForeign(StringToolUtils.convertNullObjectToString(paramVO.getPhoneForeign()));

            // 设置传真
            tbCompanyEntity.setFaxNum(StringToolUtils.convertNullObjectToString(paramVO.getFaxNum()));

            // 设置邮箱
            tbCompanyEntity.setEmail(StringToolUtils.convertNullObjectToString(paramVO.getEmail()));

            // 设置修改时间
            tbCompanyEntity.setUpdateDate(tempCurrentDate);

            // 设置修改人id
            tbCompanyEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 添加用户信息
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
