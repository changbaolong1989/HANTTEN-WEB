package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.service.CommonCacheService;
import com.hd.common.util.BusinessUtils;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.MessageConstant;
import com.hd.manager.dao.HTReportFormDao;
import com.hd.manager.dao.bean.HTAchievementReportFormBean;
import com.hd.manager.dao.bean.HTSyntheticalReportFormBean;
import com.hd.manager.dao.bean.HTUserQuestionRecordRelationBean;
import com.hd.manager.service.HTReportFormService;
import com.hd.manager.vo.HTAchievementReportFormVO;
import com.hd.manager.vo.HTSyntheticalReportFormVO;
import com.hd.manager.vo.HTUserQuestionRecordRelationVO;
import org.apache.http.client.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class HTReportFormServiceImpl extends BaseServiceImpl implements HTReportFormService {

    /**
     * 报表Dao(持久)层
     */
    @Resource
    private HTReportFormDao htReportFormDao;

    /**
     * 通用缓存Service
     */
    @Resource
    private CommonCacheService commonCacheService;

    /**
     * 查询绩效报表
     */
    @Override
    public Map<String, Object> excSearchUserAchievementsReportForm(HTAchievementReportFormVO paramVO) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HTAchievementReportFormBean paramBean = new HTAchievementReportFormBean();
            // 月份
            paramBean.setSearchDate(paramVO.getSearchDate());

            // 登陆用户ID
            String loginUserId = paramVO.getLoginUserInfo().getLoginUserId();

            //查询用户所属业务线有哪些
            List<Map<String, Object>> userInfoCache = commonCacheService.getUserInfoCache(loginUserId);

            // 判断是否查看全部人员绩效标识
            boolean isAllAchievementsflag = false;
            // 查看登陆用户是否有行政部的权限，如果有，则显示全部人员的绩效列表，如果没有，则显示个人的绩效列表
            for (Map<String, Object> map : userInfoCache) {
                if(Constant.DEPT_ID_XZ.equals(map.get("involved_department"))){
                    isAllAchievementsflag = true;
                }
            }

            // 不需要查看全部人员绩效时，就查看个人绩效
            if (!isAllAchievementsflag){
                // 登陆用户ID
                paramBean.setUserId(loginUserId);
            }

            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            List<HTAchievementReportFormBean> htAchievementReportFormBeanList = htReportFormDao.excSearchUserAchievementsReportForm(paramBean);
            List<HTAchievementReportFormVO> htAchievementReportFormVOList = new ArrayList<>();
            for (HTAchievementReportFormBean htAchievementReportFormBean : htAchievementReportFormBeanList) {
                HTAchievementReportFormVO htAchievementReportFormVO = new HTAchievementReportFormVO();
                //成员ID
                htAchievementReportFormVO.setUserId(htAchievementReportFormBean.getUserId());
                //名称
                htAchievementReportFormVO.setUserName(htAchievementReportFormBean.getUserName());
                //扣分合计
                htAchievementReportFormVO.setSumNum(htAchievementReportFormBean.getSumNum());
                //扣分合同编撰数
                htAchievementReportFormVO.setContractCountNum(htAchievementReportFormBean.getContractCountNum());
                //扣分任务数
                htAchievementReportFormVO.setTaskCountNum(htAchievementReportFormBean.getTaskCountNum());
                //扣分请款单数
                htAchievementReportFormVO.setRequestMenuCountNum(htAchievementReportFormBean.getRequestMenuCountNum());
                htAchievementReportFormVOList.add(htAchievementReportFormVO);
            }
            Integer count = htReportFormDao.excSearchUserAchievementsReportFormCount(paramBean);
            resultMap.put(Constant.DATA_LIST_KEY, htAchievementReportFormVOList);
            resultMap.put(Constant.DATA_COUNT_KEY, count);
            return resultMap;
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 成员扣分记录列表信息查询
     */
    @Override
    public Map<String, Object> excSearchHTUserQuestionRecordRelationList(HTAchievementReportFormVO paramVO) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HTAchievementReportFormBean paramBean = new HTAchievementReportFormBean();
            //成员ID
            paramBean.setUserId(paramVO.getUserId());
            //查询日期
            paramBean.setSearchDate(paramVO.getSearchDate());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList = htReportFormDao.excSearchHTUserQuestionRecordRelationList(paramBean);
            Integer dataCount = htReportFormDao.excSearchHTUserQuestionRecordRelationCount(paramBean);
            List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList = new ArrayList<>();
            //格式转换
            for (HTUserQuestionRecordRelationBean htUserQuestionRecordRelationBean : htUserQuestionRecordRelationBeanList) {
                HTUserQuestionRecordRelationVO htUserQuestionRecordRelationVO = new HTUserQuestionRecordRelationVO();
                //合同编码
                htUserQuestionRecordRelationVO.setBusinessNum(htUserQuestionRecordRelationBean.getBusinessNum());
                //合同名称
                htUserQuestionRecordRelationVO.setBusinessName(htUserQuestionRecordRelationBean.getBusinessName());
                //唯一标识
                htUserQuestionRecordRelationVO.setRelationId(htUserQuestionRecordRelationBean.getRelationId());
                //业务id
                htUserQuestionRecordRelationVO.setBusinessId(htUserQuestionRecordRelationBean.getBusinessId());
                //来源类型
                htUserQuestionRecordRelationVO.setSourceType(htUserQuestionRecordRelationBean.getSourceType());
                //审批时间
                htUserQuestionRecordRelationVO.setApproveTime(DateToolUtils.convertDateFormat(
                        htUserQuestionRecordRelationBean.getApproveTime(), DateToolUtils.yyyy_MM_dd));
                //审批人
                htUserQuestionRecordRelationVO.setApproveUserId(htUserQuestionRecordRelationBean.getApproveUserId());
                //审批人名称
                htUserQuestionRecordRelationVO.setApproveUserName(htUserQuestionRecordRelationBean.getApproveUserName());
                //人员ID
                htUserQuestionRecordRelationVO.setUserId(htUserQuestionRecordRelationBean.getUserId());
                //人员名称
                htUserQuestionRecordRelationVO.setUserName(htUserQuestionRecordRelationBean.getUserName());
                //问题类型ID
                htUserQuestionRecordRelationVO.setQuestionId(htUserQuestionRecordRelationBean.getQuestionId());
                //问题名称
                htUserQuestionRecordRelationVO.setQuestionName(htUserQuestionRecordRelationBean.getQuestionName());
                //错误次数
                htUserQuestionRecordRelationVO.setQuestionNum(htUserQuestionRecordRelationBean.getQuestionNum());
                //扣分
                htUserQuestionRecordRelationVO.setGrade(htUserQuestionRecordRelationBean.getGrade());
                htUserQuestionRecordRelationVOList.add(htUserQuestionRecordRelationVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY, htUserQuestionRecordRelationVOList);
            resultMap.put(Constant.DATA_COUNT_KEY, dataCount);
            return resultMap;
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 综合统计记录列表信息
     */
    @Override
    public Map<String, Object> excSearchSyntheticalReportFormList(HTSyntheticalReportFormVO paramVO) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Date currentDate = new Date();
            HTSyntheticalReportFormBean paramBean = new HTSyntheticalReportFormBean();
            paramBean.setDepartmentId(paramVO.getDepartmentId());
            //查询日期
            if (DateToolUtils.convertDateFormat(currentDate, DateToolUtils.yyyy).equals(paramVO.getSearchDate())) {
                paramBean.setSearchDate(DateToolUtils.convertDateFormat(currentDate, DateToolUtils.yyyy_MM_dd));
            } else {
                paramBean.setSearchDate(paramVO.getSearchDate() + "-12-31");
            }
            List<HTSyntheticalReportFormBean> syntheticalReportFormBeanList = htReportFormDao.excSearchSyntheticalReportFormList(paramBean);
            //年度累计新增合同数量初始值
            int sumAddContractCount = 0;
            //年度累计完成合同数量初始值
            int sumDoneContractCount = 0;
            //年度累计请款金额初始值
            int sumRequestTotalAmount = 0;
            //年度累计到款金额初始值
            int sumPaymentTotalAmount = 0;
            //年度累计未到金额初始值
            int sumDifferenceValue = 0;
            List<HTSyntheticalReportFormVO> syntheticalReportFormVOList = new ArrayList<>();
            //数据转换
            for (HTSyntheticalReportFormBean htSyntheticalReportFormBean : syntheticalReportFormBeanList) {
                HTSyntheticalReportFormVO htSyntheticalReportFormVO = new HTSyntheticalReportFormVO();
                //时间月份
                htSyntheticalReportFormVO.setGroupMonths(htSyntheticalReportFormBean.getGroupMonths());
                //新增合同数量
                htSyntheticalReportFormVO.setAddContractCount(htSyntheticalReportFormBean.getAddContractCount());
                //完成合同数量
                htSyntheticalReportFormVO.setDoneContractCount(htSyntheticalReportFormBean.getDoneContractCount());
                //请款金额
                htSyntheticalReportFormVO.setRequestTotalAmount(htSyntheticalReportFormBean.getRequestTotalAmount());
                //到款金额
                htSyntheticalReportFormVO.setPaymentTotalAmount(htSyntheticalReportFormBean.getPaymentTotalAmount());
                //未到金额
                htSyntheticalReportFormVO.setDifferenceValue(htSyntheticalReportFormBean.getDifferenceValue());
                sumAddContractCount += Integer.parseInt(htSyntheticalReportFormBean.getAddContractCount());
                String sumAddContractCountStr = sumAddContractCount + "";
                //年度累计新增合同数量
                htSyntheticalReportFormVO.setSumAddContractCount(sumAddContractCountStr + "");
                sumDoneContractCount += Integer.parseInt(htSyntheticalReportFormBean.getDoneContractCount());
                String sumDoneContractCountStr = sumDoneContractCount + "";
                //年度累计完成合同数量
                htSyntheticalReportFormVO.setSumDoneContractCount(sumDoneContractCountStr + "");
                sumRequestTotalAmount += Integer.parseInt(htSyntheticalReportFormBean.getRequestTotalAmount());
                String sumRequestTotalAmountStr = sumRequestTotalAmount + "";
                //年度累计请款金额
                htSyntheticalReportFormVO.setSumRequestTotalAmount(sumRequestTotalAmountStr + "");
                sumPaymentTotalAmount += Integer.parseInt(htSyntheticalReportFormBean.getPaymentTotalAmount());
                String sumPaymentTotalAmountStr = sumPaymentTotalAmount + "";
                //年度累计到款金额
                htSyntheticalReportFormVO.setSumPaymentTotalAmount(sumPaymentTotalAmountStr + "");
                sumDifferenceValue += Integer.parseInt(htSyntheticalReportFormBean.getDifferenceValue());
                String sumDifferenceValueStr = sumDifferenceValue + "";
                //年度累计未到金额
                htSyntheticalReportFormVO.setSumDifferenceValue(sumDifferenceValueStr + "");
                syntheticalReportFormVOList.add(htSyntheticalReportFormVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY, syntheticalReportFormVOList);
            return resultMap;
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }
}
