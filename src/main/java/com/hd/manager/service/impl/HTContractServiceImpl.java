package com.hd.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.enums.WriteContractEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbContractBaseInfoEntityMapper;
import com.hd.common.dao.TbContractEntityMapper;
import com.hd.common.dao.TbContractSaveMaterialRelationEntityMapper;
import com.hd.common.dao.TbProjectEntityMapper;
import com.hd.common.entity.*;
import com.hd.common.service.CommonService;
import com.hd.common.util.*;
import com.hd.common.util.word.WordTemplate;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.dao.HTContractDao;
import com.hd.manager.dao.HTRequestMenuDao;
import com.hd.manager.dao.HTTaskDao;
import com.hd.manager.dao.HTUserQuestionRecordRelationDao;
import com.hd.manager.dao.bean.*;
import com.hd.manager.service.HTContractProcessService;
import com.hd.manager.service.HTContractService;
import com.hd.manager.service.HTMessageService;
import com.hd.manager.vo.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 设置-合同 业务层 实现类
 *
 * @author jwl
 * Created in 2019/7/17 15:31
 */
@Service
public class HTContractServiceImpl extends BaseServiceImpl implements HTContractService {

    /**
     * 合同dao 持久层资源
     */
    @Resource
    private HTContractDao htContractDao;

    /**
     * 任务dao 持久层资源
     */
    @Resource
    private HTTaskDao htTaskDao;

    /**
     * 请款单dao 持久层资源
     */
    @Resource
    private HTRequestMenuDao htRequestMenuDao;

    /**
     * 合同表mapper 持久层资源
     */
    @Resource
    private TbContractEntityMapper tbContractEntityMapper;

    /**
     *  问题记录Dao
     */
    @Resource
    private HTUserQuestionRecordRelationDao htUserQuestionRecordRelationDao;

    @Resource
    private HTContractProcessService contractProcessService;

    /**
     *  合同归档文件实体mapper
     */
    @Resource
    private TbContractSaveMaterialRelationEntityMapper tbContractSaveMaterialRelationEntityMapper;

    /**
     * 通用service
     */
    @Resource
    private CommonService commonService;

    /**
     * 消息service
     */
    @Resource
    private HTMessageService htMessageService;

    /**
     * 项目service
     */
    @Resource
    private TbProjectEntityMapper tbProjectEntityMapper;

    /**
     * 项目基本信息
     */
    @Resource
    private TbContractBaseInfoEntityMapper tbContractBaseInfoEntityMapper;

    /**
     * 转换数据格式为json
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public String mapConvertToJson(HTContractVO paramVO) throws LogicException, SystemException {

        Map<String, Object> retMap = new HashedMap();

        try {

            // 如果是自定义的时候，则抛出异常
            if (!StringToolUtils.STRING_ONE.equals(paramVO.getContractType())){
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0006);
            }

            // 合同类型
            String type = paramVO.getFormworkType();

            // 没有选择合同类型
            if(StringToolUtils.isNull(type)){
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0017);
            }

            // 根据合同id查询合同信息
            TbContractEntity retContractEntity = tbContractEntityMapper
                    .selectByPrimaryKey(paramVO.getContractId());

            // 模板合同json串
            String tempFormWorkJsonStr = paramVO.getFormworkContent();

            // 如果json 串不为空则转成map格式
            if (!StringToolUtils.isNull(tempFormWorkJsonStr)){

                // json转成map
                retMap = InterfaceUtils.jsonToObject(tempFormWorkJsonStr,Map.class);
            }

            // 委托人名称
            String tempTrusterNameStr = paramVO.getTrusterName();

            retMap.put("trusterName",tempTrusterNameStr);

            // 扉页咨询人
            String tempTitlePageConsultantStr = paramVO.getTitlePageConsultant();

            retMap.put("titlePageConsultant",tempTitlePageConsultantStr);

            // 项目编号+ 合同编号
            String tempContractNumberStr = paramVO.getProjectNum() + "-" + retContractEntity.getContractNum();

            retMap.put("contractNumber",tempContractNumberStr);

            // 项目名称
            String tempProjectNameStr = paramVO.getProjectName();

            retMap.put("projectName",tempProjectNameStr);

            // 项目地点
            String tempProjectAddressStr = paramVO.getProjectAddress();

            retMap.put("projectAddress",tempProjectAddressStr);

            // 人民币符号
            String tempRmbStr = Constant.CONTRACT_TEMPLATE_RMB;

            // 字符串 暂定
            String tempProvisionalStr = Constant.CONTRACT_TEMPLATE_PROVISIONAL_STR;

            // 投资金额
            String tempInvestedAmountStr = tempRmbStr + paramVO.getInvestedAmount();

            // 投资金额是否暂定
            String tempIsProvisionalStr = paramVO.getIsProvisional();

            // 如果是否暂定标识为暂定则 投资金额 前面加上 “暂定”
            if ("1".equals(tempIsProvisionalStr)){

                tempInvestedAmountStr = tempProvisionalStr + tempInvestedAmountStr;
            }

            retMap.put("investedAmount",tempInvestedAmountStr);

            // 拟定合同金额
            BigDecimal tempContractAmountBigDecimal = NumberToolUtils
                    .convertNullToBigDecimal(paramVO.getContractAmount());

            // 将拟定合同金额 转换为金钱格式并保留两位小数
            String tempContractAmountStr = tempRmbStr + NumberToolUtils
                    .convertNumberToFormatStringWithHalfUp(tempContractAmountBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

            // 将拟定合同金额 转换成大写
            String tempContractAmountUpperStr = NumberToCN.number2CNMontrayUnit(tempContractAmountBigDecimal);

            // 拟定合同金额是否暂定
            String tempIsValueProvisionalStr = paramVO.getIsValueProvisional();

            // 如果是否暂定标识为暂定则 拟定合同金额 前面加上 “暂定”
            if ("1".equals(tempIsValueProvisionalStr)){

                tempContractAmountStr = tempProvisionalStr + tempContractAmountStr;

                tempContractAmountUpperStr = tempProvisionalStr + tempContractAmountUpperStr;
            }

            retMap.put("contractAmount",tempContractAmountStr);

            retMap.put("contractAmountUpper",tempContractAmountUpperStr);

            // 咨询合同签订日期
            String tempContractDateStr = DateToolUtils.convertDateFormat(
                    DateToolUtils.convertDateFormat(
                            paramVO.getContractDate(),DateToolUtils.yyyy_MM_dd),DateToolUtils.yyyy_MM_dd);

            // 如果日期不为空
            if(!StringToolUtils.STRING_EMPTY.equals(tempContractDateStr)){
                // 截取咨询合同签订日期 年
                String tempYearStr = tempContractDateStr.substring(0,4);

                retMap.put("contractYear",tempYearStr);

                // 截取咨询合同签订日期月
                String tempMonthStr = tempContractDateStr.substring(5,7);

                retMap.put("contractMonth",tempMonthStr);

                // 截取咨询合同签订日期日
                String tempDayStr = tempContractDateStr.substring(8,10);

                retMap.put("contractDay",tempDayStr);
            }

            // 委托方负责人
            String tempTrusterDutyUserStr = paramVO.getTrusterDutyUser();

            retMap.put("trusterDutyUser",tempTrusterDutyUserStr);

            // 委托方负责人电话
            String tempTrusterDutyPhoneStr = paramVO.getTrusterDutyPhone();

            retMap.put("trusterDutyPhone",tempTrusterDutyPhoneStr);

            // 当前年月
            String tempCurrentYearMonthStr = DateToolUtils
                    .convertDateFormat(new Date(), DateToolUtils.YYYY_MM_CHINESE);

            retMap.put("currentYearMonth",tempCurrentYearMonthStr);

            // 模板类型
            String tempFormworkTypeStr = paramVO.getFormworkType();

            // 模板类型数字
            int tempFormworkTypeInt = NumberToolUtils.convertNullToInteger(tempFormworkTypeStr);

            retMap.put("formworkType",tempFormworkTypeStr);

            // 合同模板名称
            String tempContractTemplateNameStr = BusinessUtils.convertContractTemplateTypeToStateStr(tempFormworkTypeInt);

            //0：造价咨询合同 1：建设工程招标代理合同 2：工程管理咨询服务合同 3：监理合同
            // 如果合同模板为 造价咨询合同
            if (Constant.CONTRACT_COST_CONSULTATION_VAL.equals(tempFormworkTypeStr)){

                // 支付方式
                String tempPaymentMethodStr = (String) retMap.get("paymentMethod");

                // 支付内容
                String tempPaymentInfoStr = (String) retMap.get("paymentInfo");

                // 如果为一次性支付 支付内容为 咨询成果报告出具xxx日内支付
                if ("0".equals(tempPaymentMethodStr)){

                    tempPaymentInfoStr = "咨询成果报告出具"+ tempPaymentInfoStr +"日内一次性支付";

                }
                retMap.put("paymentInfo",tempPaymentInfoStr);

                // 总份数
                BigDecimal tempTotalContractCount = NumberToolUtils.convertNullToBigDecimal(retMap.get("totalContractCount"));
                // 委托人份数
                BigDecimal tempTrusterContractCount = NumberToolUtils.convertNullToBigDecimal(retMap.get("trusterContractCount"));
                // 咨询人份数
                BigDecimal tempConsultantContractCount = NumberToolUtils.convertNullToBigDecimal(retMap.get("consultantContractCount"));

                // 总份数转成大写
                String tempTotalContractCountStr = NumberToCN.numberToCNUpper(tempTotalContractCount);
                retMap.put("totalContractCount",tempTotalContractCountStr);
                // 委托人份数转成大写
                String tempTrusterContractCountStr = NumberToCN.numberToCNUpper(tempTrusterContractCount);
                retMap.put("trusterContractCount",tempTrusterContractCountStr);
                // 咨询人份数转成大写
                String tempConsultantContractCountStr = NumberToCN.numberToCNUpper(tempConsultantContractCount);
                retMap.put("consultantContractCount",tempConsultantContractCountStr);


                // 建设工程招标代理合同
            }else if (Constant.CONTRACT_BIDDING_AGENT_VAL.equals(tempFormworkTypeStr)){

                // 代理内容flg
                String tempAgentContentFlgStr = (String) retMap.get("agentContent");

                // 代理内容
                String tempAgentContentStr = "无";

                // 代理内容+ "、"
                String tempAgentContentAddDunStr = StringToolUtils.STRING_EMPTY;

                // 如果代理内容flg是 1 则默认代理内容 等于 造价咨询
                if ("1".equals(tempAgentContentFlgStr)){

                    tempAgentContentAddDunStr = "造价咨询、";
                    tempAgentContentStr = "造价咨询";
                }
                retMap.put("agentContentAddDun",tempAgentContentAddDunStr);

                retMap.put("agentContent",tempAgentContentStr);

                // 工程管理咨询服务合同
            }else if (Constant.CONTRACT_CONSULTING_SERVICE_VAL.equals(tempFormworkTypeStr)){

                // 设计阶段服务报酬
                BigDecimal tempDesignPayBigDecimal = NumberToolUtils
                        .convertNullToBigDecimal(retMap.get("designPay"));
                // 设计阶段服务报酬 是否暂定
                String tempIsDesignPayProvisionalStr = (String) retMap.get("isDesignPayProvisional");

                // 设计阶段服务报酬
                String tempDesignPayStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(tempDesignPayBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

                // 如果是否暂定标识为暂定则 设计阶段服务报酬 前面加上 “暂定”
                if ("1".equals(tempIsDesignPayProvisionalStr)){

                    tempDesignPayStr = tempProvisionalStr + tempDesignPayStr;
                }
                retMap.put("designPay",tempDesignPayStr);


                // 施工阶段服务报酬
                BigDecimal tempConstructionPayBigDecimal = NumberToolUtils
                        .convertNullToBigDecimal(retMap.get("constructionPay"));
                // 施工阶段服务报酬 是否暂定
                String tempIsConstructionPayProvisionalStr = (String) retMap.get("isConstructionPayProvisional");

                // 施工阶段服务报酬
                String tempConstructionPayStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(tempConstructionPayBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

                // 如果是否暂定标识为暂定则 施工阶段服务报酬 前面加上 “暂定”
                if ("1".equals(tempIsConstructionPayProvisionalStr)){

                    tempConstructionPayStr = tempProvisionalStr + tempConstructionPayStr;
                }
                retMap.put("constructionPay",tempConstructionPayStr);


                // 结算阶段服务报酬
                BigDecimal tempSettlementPayBigDecimal = NumberToolUtils
                        .convertNullToBigDecimal(retMap.get("settlementPay"));
                // 结算阶段服务报酬 是否暂定
                String tempIsSettlementPayProvisionalStr = (String) retMap.get("isSettlementPayProvisional");

                // 结算阶段服务报酬
                String tempSettlementPayStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(tempSettlementPayBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

                // 如果是否暂定标识为暂定则 结算阶段服务报酬 前面加上 “暂定”
                if ("1".equals(tempIsSettlementPayProvisionalStr)){

                    tempSettlementPayStr = tempProvisionalStr + tempSettlementPayStr;
                }
                retMap.put("settlementPay",tempSettlementPayStr);

                // 合计
                BigDecimal tempTotalPriceBigDecimal = tempDesignPayBigDecimal.add(tempConstructionPayBigDecimal).add(tempSettlementPayBigDecimal);
                // 合计 字符串
                String tempTotalPriceStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(tempTotalPriceBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

                // 合计 大写
                String tempTotalPriceUpperStr = NumberToCN.number2CNMontrayUnit(tempTotalPriceBigDecimal);

                // 设计阶段服务报酬、施工阶段服务报酬、结算阶段服务报酬 如果有一个为暂停那么总额就为暂定
                if ("1".equals(tempIsDesignPayProvisionalStr) || "1".equals(tempIsConstructionPayProvisionalStr) || "1".equals(tempIsSettlementPayProvisionalStr)){

                    tempTotalPriceUpperStr = tempProvisionalStr + tempTotalPriceUpperStr;

                    tempTotalPriceStr = tempProvisionalStr + tempTotalPriceStr;
                }
                retMap.put("totalPriceUpper",tempTotalPriceUpperStr);
                retMap.put("totalPrice",tempTotalPriceStr);



                // 预付款付费额
                String tempAdvancePaymentStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("advancePayment"), NumberToolUtils.INT_SCALE_FLOAT);

                // 预付款付费额 是否暂定
                String tempIsAdvancePaymentProvisionalStr = (String) retMap.get("isAdvancePaymentProvisional");

                // 如果是否暂定标识为暂定则 预付款付费额 前面加上 “暂定”
                if ("1".equals(tempIsAdvancePaymentProvisionalStr)){

                    tempAdvancePaymentStr = tempProvisionalStr + tempAdvancePaymentStr;
                }
                retMap.put("advancePayment",tempAdvancePaymentStr);

                // 进度款付费额
                String tempProgressPaymentStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("progressPayment"), NumberToolUtils.INT_SCALE_FLOAT);

                // 进度款付费额 是否暂定
                String tempIsProgressPaymentProvisionalStr = (String) retMap.get("isProgressPaymentProvisional");

                // 如果是否暂定标识为暂定则 进度款付费额 前面加上 “暂定”
                if ("1".equals(tempIsProgressPaymentProvisionalStr)){

                    tempProgressPaymentStr = tempProvisionalStr + tempProgressPaymentStr;
                }
                retMap.put("progressPayment",tempProgressPaymentStr);

                // 余款付费额
                String tempBalanceStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("balance"), NumberToolUtils.INT_SCALE_FLOAT);

                // 余款付费额 是否暂定
                String tempIsBalanceProvisionalStr = (String) retMap.get("isBalanceProvisional");

                // 如果是否暂定标识为暂定则 余款付费额 前面加上 “暂定”
                if ("1".equals(tempIsBalanceProvisionalStr)){

                    tempBalanceStr = tempProvisionalStr + tempBalanceStr;
                }
                retMap.put("balance",tempBalanceStr);

                // 监理合同
            }else {
                // 签约酬金
                BigDecimal  tempSignAmountBigDecimal = NumberToolUtils.convertNullToBigDecimal(retMap.get("signAmount"));

                // 签约酬金 是否暂定
                String tempIsSignAmountProvisionalStr = (String) retMap.get("isSignAmountProvisional");

                // 转成金钱格式
                String tempSignAmountStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(tempSignAmountBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

                // 签约酬金 大写
                String tempSignAmountUpperStr = NumberToCN.number2CNMontrayUnit(tempSignAmountBigDecimal);

                // 如果是否暂定标识为暂定则 签约酬金 前面加上 “暂定”
                if ("1".equals(tempIsSignAmountProvisionalStr)){

                    tempSignAmountStr = tempProvisionalStr + tempSignAmountStr;

                    tempSignAmountUpperStr = tempProvisionalStr + tempSignAmountUpperStr;
                }
                retMap.put("signAmount",tempSignAmountStr);

                retMap.put("signAmountUpper",tempSignAmountUpperStr);

                // 工程概算投资额或建筑安装工程费
                BigDecimal tempInstalCostBigDecimal = NumberToolUtils.convertNullToBigDecimal(retMap.get("instalCost"));

                // 工程概算投资额或建筑安装工程费 金钱格式
                String tempInstalCostStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(tempInstalCostBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);;

                // 工程概算投资额或建筑安装工程费 是否暂定
                String tempIsInstalCostProvisionalStr = (String) retMap.get("isInstalCostProvisional");

                // 如果是否暂定标识为暂定则 工程概算投资额或建筑安装工程费 前面加上 “暂定”
                if ("1".equals(tempIsInstalCostProvisionalStr)){

                    tempInstalCostStr = tempProvisionalStr + tempInstalCostStr;
                }

                retMap.put("instalCost",tempInstalCostStr);


            }
            retMap.put("contractTemplateName",tempContractTemplateNameStr);


        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }

        return JSON.toJSONString(retMap);

    }

    /**
     * 查询合同详细信息
     *
     * @return 合同详细信息
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public HTContractVO excSearchContractDetailInfo(HTContractVO paramVO) throws SystemException {

        HTContractVO htContractVO = new HTContractVO();

        try {
            // 取得合同id
            String tmpContractIdStr = paramVO.getContractId();
            // 根据合同id取得合同详细信息
            TbContractEntity retTbContractEntity = tbContractEntityMapper.selectByPrimaryKey(tmpContractIdStr);

            if (retTbContractEntity != null) {
                // 合同信息存在时
                // 合同基本信息
                // 项目名称
                htContractVO.setProjectName(retTbContractEntity.getProjectName());
                // 咨询业务类型
                htContractVO.setCounselBusinessType(retTbContractEntity.getCounselBusinessType());
                // 咨询类型阶段
                htContractVO.setCounselTypePeriod(retTbContractEntity.getCounselTypePeriod());
                // 合同盖章类型
                htContractVO.setContractSealType(retTbContractEntity.getContractSealType());
                // 修改时间
                htContractVO.setUpdateDate(DateToolUtils.convertDateFormat(retTbContractEntity.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

                // 项目地点
                htContractVO.setProjectAddress(retTbContractEntity.getProjectAddress());

                // 项目类别(多checkbox)
                List<HTContractVO> tempProjectTypeList = new ArrayList<>();
                HTContractVO tempHTContractProjectTypeVO = null;

                List<HTContractVO> tempProjectFeatureList = new ArrayList<>();
                HTContractVO tempHTContractFeatureVO = null;
                HTContractBean paramSearchBean = new HTContractBean();
                // 合同id
                paramSearchBean.setContractId(tmpContractIdStr);
                // 检索项目类别列表
                List<HTContractBean> tmpHTProjectTypeRelationsList = htContractDao.excSelectContractProjectTypeRelations(paramSearchBean);
                // 如果项目类别不为空，项目属性json转为对象
                if (!ListToolUtils.isEmpty(tmpHTProjectTypeRelationsList)) {
                    //遍历 委托人性质
                    for (HTContractBean htContractBean : tmpHTProjectTypeRelationsList) {
                        // 初始化项目类别VO 并设置 项目类别
                        tempHTContractProjectTypeVO = new HTContractVO();
                        tempHTContractProjectTypeVO.setProjectType(htContractBean.getProjectType());
                        // 保存项目类别列表数据
                        tempProjectTypeList.add(tempHTContractProjectTypeVO);

                        // 初始化项目属性VO 并设置 项目类别
                        tempHTContractFeatureVO = new HTContractVO();
                        // 初始化项目属性VO 并设置 项目类别
                        tempHTContractFeatureVO =  InterfaceUtils.jsonToObject (htContractBean.getProjectTarget(), HTContractVO.class);
                        // 保存项目属性列表数据
                        tempProjectFeatureList.add(tempHTContractFeatureVO);
                    }
                }

                // 设置项目类别数据
                htContractVO.setProjectTypeList(tempProjectTypeList);

                // 设置项目属性数据
                htContractVO.setProjectFeatureList(tempProjectFeatureList)  ;

                // 项目类别(多checkbox)
                htContractVO.setProjectType(retTbContractEntity.getProjectType());
                // 投资金额
                htContractVO.setInvestedAmount(retTbContractEntity.getInvestedAmount());
                // 投资金额暂定
                htContractVO.setIsProvisional(retTbContractEntity.getIsProvisional());

                // 如果返回的数据不为空，则把数据拆分成list
                if(!StringToolUtils.isNull(retTbContractEntity.getProjectType())){
                    // 项目性质(多checkbox  "0":新建  "1":扩建  "2":改造)
                    htContractVO.setProjectNatureList(convertSplitStringToList(retTbContractEntity.getProjectType()));
                }

                // 委托人
                htContractVO.setTrusterName(retTbContractEntity.getTrusterName());

                // 如果返回的数据不为空，则把数据拆分成list
                if(!StringToolUtils.isNull(retTbContractEntity.getTrusterType())){
                    // 委托人性质(多checkbox  "0":政府部门  "1":国企    "2":上市公司   "3":私企   "4":其他)
                    htContractVO.setTrusterTypeList(convertSplitStringToList(retTbContractEntity.getTrusterType()));
                }

                // 委托方负责人
                htContractVO.setTrusterDutyUser(retTbContractEntity.getTrusterDutyUser());
                // 委托方负责人电话
                htContractVO.setTrusterDutyPhone(retTbContractEntity.getTrusterDutyPhone());
                // 咨询人
                htContractVO.setConsultUser(retTbContractEntity.getConsultUser());
                // 第三方（合作单位）
                htContractVO.setThirdParty(retTbContractEntity.getThirdParty());
                // 扉页咨询人
                htContractVO.setTitlePageConsultant(retTbContractEntity.getTitlePageConsultant());
                // 拟定合同负责人
                htContractVO.setDutyPeople(retTbContractEntity.getDutyPeople());
                // 拟定人
                htContractVO.setPlanner(retTbContractEntity.getPlanner());

                // 如果返回的数据不为空，则把数据拆分成list
                if(!StringToolUtils.isNull(retTbContractEntity.getProjectType())){
                    // 合同性质(多checkbox    "0": 双方合同    "1": 三方合同    "2": 补充合同   "3": 单独核算   "4": 非单独核算)
                    htContractVO.setContractNatureList(convertSplitStringToList(retTbContractEntity.getContractNature()));
                }

                // 如果返回的数据不为空，则把数据拆分成list
                if(!StringToolUtils.isNull(retTbContractEntity.getDeterminateMethod())){
                    // 确定方式(多checkbox   "0":招标   "1":财政委托    "2":审计委托   "3":商务洽谈   "4":战略合作   "5":法院委托   "6":其他 )
                    htContractVO.setDeterminateMethodList(convertSplitStringToList(retTbContractEntity.getDeterminateMethod()));
                }

                // 拟定合同金额
                htContractVO.setContractAmount(retTbContractEntity.getContractAmount());
                // 暂定
                htContractVO.setIsValueProvisional(retTbContractEntity.getIsValueProvisional());
                // 咨询合同签订日期
                htContractVO.setContractDate(DateToolUtils.convertDateFormat(retTbContractEntity.getContractDate(), DateToolUtils.yyyy_MM_dd));
                // 预计收款时间(多条)
                // 其他情况
                htContractVO.setOtherThings(retTbContractEntity.getOtherThings());
                // 合作公司
                htContractVO.setCooperateCompany(retTbContractEntity.getCooperateCompany());
                // 合同类型(自定义合同，模板合同)
                htContractVO.setContractType(retTbContractEntity.getContractType());
                // 合同模板类型("0":造价咨询合同，"1":建设工程招标代理合同，"2":工程管理咨询服务合同，"3":监理合同)
                htContractVO.setFormworkType(retTbContractEntity.getFormworkType());
                // 模板内容
                htContractVO.setFormworkContent(retTbContractEntity.getFormworkContent());


                // 预计收款时间（多条数据）
                HTContractBean paramSearchPredictRequestBean = new HTContractBean();
                // 合同id
                paramSearchPredictRequestBean.setContractId(tmpContractIdStr);
                // 检索预计收款时间列表
                List<HTContractBean> tmpPredictRequestRelationsList = htContractDao.excSelectPredictRequestRelations(paramSearchPredictRequestBean);

                List<HTContractVO> retPredictRequestRelationsList = new ArrayList<>();
                HTContractVO tempVO;
                if(tmpPredictRequestRelationsList != null){
                    for(HTContractBean tempHTContractBean :tmpPredictRequestRelationsList){
                        tempVO = new HTContractVO();
                        // 收款日期
                        tempVO.setStartDate(DateToolUtils.convertDateFormat(tempHTContractBean.getStartDate(), DateToolUtils.yyyy_MM_dd));
                        // 收款金额
                        tempVO.setReceivedAmount(StringToolUtils.convertNullObjectToString(tempHTContractBean.getReceivedAmount()));
                        // 是否暂定
                        tempVO.setIsProvisional(StringToolUtils.convertNullObjectToString(tempHTContractBean.getIsProvisional()));
                        retPredictRequestRelationsList.add(tempVO);
                    }
                }
                // 设置预计收款时间列表
                htContractVO.setPredictRequestDateList(retPredictRequestRelationsList);

                //
                int tempFormworkTypeInt = NumberToolUtils.convertNullToInteger(retTbContractEntity.getFormworkType());
                // 合同模板内容
                htContractVO.setFormworkContent(retTbContractEntity.getFormworkContent());

                // 根据合同id 查询项目编号
                HTContractBean retBean = htContractDao.getContractInfoByContractId(tmpContractIdStr);

                // 项目编号
                String tempProjectNum = StringToolUtils.STRING_EMPTY;

                // 如果查到项目编号 则赋值
                if (retBean != null){

                    tempProjectNum = retBean.getProjectNum();
                }

                // 合同文件名称
                String tempContractFileNameStr = StringToolUtils.STRING_EMPTY;

                // 合同文件路径
                String tempContractFilePathStr = StringToolUtils.STRING_EMPTY;

                // onlyOffice使用的下载地址
                String tempOfficePath = null;

                // 如果合同类型为自定义合同 则取得自定义合同名称和路径
                if (retTbContractEntity.getContractType() == null || "0".equals(retTbContractEntity.getContractType())){

                    // 自定义合同真实名字
                    tempContractFileNameStr = StringToolUtils.convertNullObjectToString(retTbContractEntity.getContractFileName());

                    // 自定义合同路径
                    tempContractFilePathStr = StringToolUtils.convertNullObjectToString(retTbContractEntity.getContractFilePath());

                    tempOfficePath = Constant.COMMON_MODULE_URL+"/downloadFile?filePathName="+
                            tempContractFilePathStr+"&realFileName="+tempContractFileNameStr;

                    // 如果合同类型为模板合同 则取得模板合同名称和生成路径
                } else if ("1".equals(retTbContractEntity.getContractType())){

                    // 合同模板名称
                    tempContractFileNameStr = BusinessUtils.convertContractTemplateTypeToStateStr(tempFormworkTypeInt);

                    // 合同模板生成路径
                    tempContractFilePathStr = "/contract/downLoad/downLoadContractWord";

                    tempOfficePath = "/contract/downLoad/downLoadContractWord?contractId=" + tmpContractIdStr + "&projectNum=" + tempProjectNum;


                }

                // 文件名称
                htContractVO.setContractFileName(tempContractFileNameStr);
                htContractVO.setMaterialName(tempContractFileNameStr);

                // 文件路径
                htContractVO.setContractFilePath(tempContractFilePathStr);

                //给onlyOffice使用的绝对路径
                htContractVO.setReviewFilePath(
                        OnlyOfficeUtils.getOnlyOfficeSaveCallbackUrl());
                //给onlyOffice使用的真实文件名称
                htContractVO.setReviewFileName(tempContractFileNameStr);
                //给onlyOffice使用的唯一标识
                htContractVO.setOnlyOfficeEditKey(OnlyOfficeUtils.generateOnlyOfficeEditKey());
                //给onlyOffice使用的下载地址
                htContractVO.setOnlyOfficeAbsDownLoadPath(
                        OnlyOfficeUtils.getFileDownloadAbsoluteUrl(tempOfficePath));


            }

        } catch (LogicException lex) {
            throw lex;
        }catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htContractVO;
    }

    /**
     * 修改合同信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void updateContractInfo(HTContractVO paramVO, MultipartFile signPathFile) throws LogicException, SystemException {

        try {

            HTContractBean contractBean = htContractDao.getContractInfoByContractId(paramVO.getContractId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(contractBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            // =====================================判断项目是否作废 start=========================================

            // 判断项目是否结束
            commonService.excIsInvalidProject(contractBean.getProjectId());

            // =====================================判断项目是否作废 end=========================================
            //对比更新时间,如果时间不同，说明此条数据已经被提交审核
            if (!paramVO.getUpdateDate().equals(DateToolUtils.convertDateFormat(
                    contractBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO))) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }

            //=========================  修改合同信息 start ========================

            TbContractEntity tbContractEntity = new TbContractEntity();

            // 临时变量：合同id
            String tempContractIdStr = StringToolUtils
                    .convertNullObjectToString(paramVO.getContractId());

            // 设置合同id
            tbContractEntity.setContractId(tempContractIdStr);

            // 设置项目名称
            tbContractEntity.setProjectName(StringToolUtils
                    .convertNullObjectToString(paramVO.getProjectName()));

            // 设置咨询业务类型（0：造价咨询 1：招标代理 2：项目管理(监理)）
            tbContractEntity.setCounselBusinessType(StringToolUtils
                    .convertNullObjectToString(paramVO.getCounselBusinessType()));

            // 合同盖章类型(1.委托代理人章2.法定代表人章)
            tbContractEntity.setContractSealType(StringToolUtils
                    .convertNullObjectToString(paramVO.getContractSealType()));

            // 当咨询业务类型为造价咨询和招标代理，设置咨询类型阶段
            if(StringToolUtils.STRING_ZERO.equals(paramVO.getCounselBusinessType())
                    || StringToolUtils.STRING_ONE.equals(paramVO.getCounselBusinessType())){
                // 设置咨询类型阶段
                tbContractEntity.setCounselTypePeriod(StringToolUtils
                        .convertNullObjectToString(paramVO.getCounselTypePeriod()));
            }

            // 设置项目地点
            tbContractEntity.setProjectAddress(StringToolUtils
                    .convertNullObjectToString(paramVO.getProjectAddress()));

            // 设置投资金额
            tbContractEntity.setInvestedAmount(StringToolUtils
                    .convertNullObjectToString(paramVO.getInvestedAmount()));

            // 设置投资金额是否暂定
            tbContractEntity.setIsProvisional(StringToolUtils
                    .convertNullObjectToString(paramVO.getIsProvisional()));

            // 临时变量：项目性质
            String tempProjectNatureStr = "";

            // 设置项目性质
            List<String> projectNatureList = paramVO.getProjectNatureList();

            //遍历 项目性质
            if(projectNatureList!=null){
                for (String projectNature : projectNatureList) {
                    tempProjectNatureStr = tempProjectNatureStr.concat("@"+projectNature+"@,");
                }
            }

            // 如果项目性质不为空 则不需要去掉“，”
            if (!StringToolUtils.isNull(tempProjectNatureStr)){

                tempProjectNatureStr = tempProjectNatureStr.substring(NumberToolUtils.INT_ZERO,tempProjectNatureStr.lastIndexOf(","));
            }
            tbContractEntity.setProjectType(tempProjectNatureStr);

            // 设置委托人
            tbContractEntity.setTrusterName(StringToolUtils
                    .convertNullObjectToString(paramVO.getTrusterName()));

            // 临时变量：委托人性质
            String tempTrusterTypeStr = "";

            // 设置委托人性质
            List<String> trusterTypeList = paramVO.getTrusterTypeList();

            //遍历 委托人性质
            if(trusterTypeList!=null){
                for (String trusterType : trusterTypeList) {
                    tempTrusterTypeStr = tempTrusterTypeStr.concat("@"+trusterType+"@,");
                }
            }

            // 如果委托人性质不为空 则不需要去掉“，”
            if (!StringToolUtils.isNull(tempTrusterTypeStr)){

                tempTrusterTypeStr = tempTrusterTypeStr.substring(NumberToolUtils.INT_ZERO,tempTrusterTypeStr.lastIndexOf(","));
            }
            tbContractEntity.setTrusterType(tempTrusterTypeStr);

            // 设置委托方负责人
            tbContractEntity.setTrusterDutyUser(StringToolUtils
                    .convertNullObjectToString(paramVO.getTrusterDutyUser()));

            // 设置委托方负责人电话
            tbContractEntity.setTrusterDutyPhone(StringToolUtils
                    .convertNullObjectToString(paramVO.getTrusterDutyPhone()));

            // 设置咨询人
            tbContractEntity.setConsultUser(StringToolUtils
                    .convertNullObjectToString(paramVO.getConsultUser()));

            // 设置第三方（合作单位）
            tbContractEntity.setThirdParty(StringToolUtils
                    .convertNullObjectToString(paramVO.getThirdParty()));

            // 设置扉页咨询人
            tbContractEntity.setTitlePageConsultant(StringToolUtils
                    .convertNullObjectToString(paramVO.getTitlePageConsultant()));

            // 设置拟定合同负责人
            tbContractEntity.setDutyPeople(StringToolUtils
                    .convertNullObjectToString(paramVO.getDutyPeople()));

            // 设置拟定人
            tbContractEntity.setPlanner(StringToolUtils
                    .convertNullObjectToString(paramVO.getPlanner()));

            // 临时变量：合同性质
            String tempContractNatureStr = "";

            // 设置合同性质
            List<String> contractNatureList = paramVO.getContractNatureList();

            //遍历 合同性质
            if(contractNatureList!=null){
                for (String contractNature : contractNatureList) {
                    tempContractNatureStr = tempContractNatureStr.concat("@"+contractNature+"@,");
                }
            }

            // 如果合同性质不为空 则不需要去掉“，”
            if (!StringToolUtils.isNull(tempContractNatureStr)){

                tempContractNatureStr = tempContractNatureStr.substring(NumberToolUtils.INT_ZERO,tempContractNatureStr.lastIndexOf(","));
            }

            tbContractEntity.setContractNature(tempContractNatureStr);

            // 临时变量：确定方式
            String tempDeterminateMethodStr = "";

            // 设置确定方式
            List<String> determinateMethodList = paramVO.getDeterminateMethodList();

            //遍历 确定方式
            if(determinateMethodList!=null){
                for (String determinateMethod : determinateMethodList) {
                    tempDeterminateMethodStr = tempDeterminateMethodStr.concat("@"+determinateMethod+"@,");
                }
            }

            // 如果确定方式不为空 则不需要去掉“，”
            if (!StringToolUtils.isNull(tempDeterminateMethodStr)){

                tempDeterminateMethodStr = tempDeterminateMethodStr.substring(NumberToolUtils.INT_ZERO,tempDeterminateMethodStr.lastIndexOf(","));
            }
            tbContractEntity.setDeterminateMethod(tempDeterminateMethodStr);


            // 设置拟定合同金额
            tbContractEntity.setContractAmount(StringToolUtils
                    .convertNullObjectToString(paramVO.getContractAmount()));

            // 设置拟定合同金额暂定
            tbContractEntity.setIsValueProvisional(StringToolUtils
                    .convertNullObjectToString(paramVO.getIsValueProvisional()));

            // 设置咨询合同签订日期
            // 如果咨询合同签订日期不为空
            if (!StringToolUtils.isNull(paramVO.getContractDate())) {
                //将开始时间赋值给bean
                tbContractEntity.setContractDate(DateToolUtils.convertDateFormat(
                        paramVO.getContractDate(), DateToolUtils.yyyy_MM_dd));
            }

            // 设置其他情况
            tbContractEntity.setOtherThings(StringToolUtils
                    .convertNullObjectToString(paramVO.getOtherThings()));
            // 设置合作公司
            tbContractEntity.setCooperateCompany(StringToolUtils
                    .convertNullObjectToString(paramVO.getCooperateCompany()));

            // 设置合同类型
            tbContractEntity.setContractType(StringToolUtils
                    .convertNullObjectToString(paramVO.getContractType()));

            // 文件上传后的路径
            String signPathFilePath = null;

            // 自定义上传文件的文件名称（公告id+.后缀名）
            String tempFileNameStr = null;

            // 临时变量：合同类型
            String tempContractTypeStr = paramVO.getContractType();

            // 当合同类型为自定义合同时，并且附件不为空，则上传附件，附件名称和附件路径
            if(StringToolUtils.STRING_ZERO.equals(tempContractTypeStr) && null != signPathFile){

                // 源文件名
                String signPathFileName = signPathFile.getOriginalFilename();

                // 文件上传后的路径
                signPathFilePath = Constant.FILE_SEPARATE+"contract"
                        + Constant.FILE_SEPARATE+ tempContractIdStr + Constant.FILE_SEPARATE;

                // 自定义上传文件的文件名称（uuid+.后缀名）
                tempFileNameStr = UUID.randomUUID().toString() + signPathFileName
                        .substring(signPathFileName.lastIndexOf("."));

                // 设置附件路径
                tbContractEntity.setContractFilePath(signPathFilePath + tempFileNameStr);

                // 设置附件名称
                tbContractEntity.setContractFileName(StringToolUtils
                        .convertNullObjectToString(signPathFileName));

            // 如果为模板合同时，设置咨询类型阶段
            }else if(StringToolUtils.STRING_ONE.equals(tempContractTypeStr)){
                // 设置模板合同类型
                tbContractEntity.setFormworkType(StringToolUtils
                        .convertNullObjectToString(paramVO.getFormworkType()));

                tbContractEntity.setFormworkContent(paramVO.getFormworkContent());
            }

            //启动工作流
            TbContractEntity contractEntity = tbContractEntityMapper.selectByPrimaryKey(tempContractIdStr);
            contractEntity.setSubmitNode(paramVO.getSubmitNode());
            HTContractVO contractVO = (HTContractVO) ObjectReflectUtils.convertObj(contractEntity, HTContractVO.class);
            contractVO.setLoginUserInfo(paramVO.getLoginUserInfo());
            Map<String, Object> variable = new HashMap<>();
            variable.put(WorkFlowConstant.KEY_CONTRACT_VO, contractVO);
            variable.put(WorkFlowConstant.KEY_SUBMIT_NODE, contractVO.getSubmitNode());
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            String departmentId = StringUtils.isBlank(WriteContractEnum.getDeptId(contractVO.getSubmitNode())) ? contractVO.getDepartmentId() : WriteContractEnum.getDeptId(contractVO.getSubmitNode());
            commonActivitiListenVO.setDepartmentId(departmentId);
            variable.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
            variable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            contractProcessService.excHandleTask(contractVO.getLoginUserInfo().getLoginUserId(),contractEntity.getDelegateTaskId(), variable);

            // 修改时间
            tbContractEntity.setUpdateDate(getCurrentDate());
            // 修改人id
            tbContractEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            tbContractEntityMapper.updateByPrimaryKeySelective(tbContractEntity);

            //=========================  修改合同信息 end ========================

            //========================= 操作预计收款时间  start ========================

            // 删除预计收款时间
            htContractDao.excDeleteContractPredictRequestRelations(tbContractEntity);

            // 如果列表不为空，则批量插入预计收款时间,并添加消息数据
            if(!ListToolUtils.isEmpty(paramVO.getPredictRequestDateList())){
                // 批量插入预计收款时间
                this.excInsertContractPredictRequestRelations(paramVO);

                // 添加消息数据
                htMessageService.excInsertMessageInfo();
            }

            //========================= 操作预计收款时间  end ========================

            //========================= 操作合同和项目类别指标  start ========================

            // 删除合同和项目类别指标列表数据
            htContractDao.excDeleteContractProjectTypeRelations(tbContractEntity);

            // 如果列表不为空，则批量插入合同和项目类别指标
            if(!ListToolUtils.isEmpty(paramVO.getProjectTypeList()))
            this.excInsertContractProjectTypeRelations(paramVO);

            //========================= 操作合同和项目类别指标  end ========================

            //========================= 上传文件  start ========================

            // 如果附件不为空，则上传附件，附件名称和附件路径
            if(StringToolUtils.STRING_ZERO.equals(tempContractTypeStr) && null != signPathFile){

                // 上传文件
                boolean isUploadSignSuccess = FileUtils.uploadFile(signPathFilePath + tempFileNameStr, signPathFile);

                if (!isUploadSignSuccess) {
                    // MSG_ERROR_PUBLIC_NOTICE_0003 上传文件失败！
                    throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0001);
                }

            }

            //========================= 上传文件  end ========================


            HTReviewFileRelationBean htReviewFileRelationBean = new HTReviewFileRelationBean();
            //事务唯一标识
            htReviewFileRelationBean.setBusinessId(paramVO.getContractId());

            // ================================================ 插入消息信息 start =====================================================

            Date currentDate = getCurrentDate();

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(currentDate,DateToolUtils.yyyy_MM_dd);

            HTContractBean htContractBean = new HTContractBean();
            // 设置当前登录用户id
            htContractBean.setCurrentUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置部门id
            htContractBean.setDepartmentId(contractEntity.getDepartmentId());
            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            // 职位和查询得到的人员id
            HTTaskBean returnBean = commonService.excSearchUserIdForMessage(paramVO.getSubmitNode(),contractEntity.getDepartmentId());

            // 消息内容
            String tempMessageContentStr = "由"+htContractBeanList.get(0).getJobName()+"("+paramVO.getLoginUserInfo().getUserName()+")"+applyTime+"发起的"
                    +paramVO.getProjectNum()+"项目"
                    +paramVO.getProjectNum()+"-"+contractEntity.getContractNum()+"合同该由您审核了";

            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置是否阅读 0：否    1：是
            tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
            // 设置创建时间
            tbMessageEntity.setCreateDate(currentDate);
            // 设置创建人id
            tbMessageEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置修改时间
            tbMessageEntity.setUpdateDate(currentDate);
            // 设置修改人id
            tbMessageEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置是否删除  0：否    1：是
            tbMessageEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);

            // 如果该职位有人员，则遍历人员信息，添加消息
            if(!ListToolUtils.isEmpty(returnBean.getHtTaskBeanList())){
                // 遍历人员信息
                for(HTTaskBean taskBean:returnBean.getHtTaskBeanList()){
                    // 设置接收消息人
                    tbMessageEntity.setAcceptUserId(taskBean.getUserId());
                    // 插入消息信息
                    commonService.excInsertMessageInfo(tbMessageEntity);

                    // 微信公众号推送模板消息
                    // loginUserId 当前登录用户id
                    // openId 用户唯一标识
                    // acceptUserName 接收人名称
                    // messageContent 消息内容
                    commonService.sendTemplateMessageToWeChat(paramVO.getLoginUserInfo().getLoginUserId()
                            ,taskBean.getOpenId(),taskBean.getUserName(),tempMessageContentStr);
                }
            }

        } catch (LogicException lex) {
            throw  lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            e.printStackTrace();
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 添加和修改项目基本情况信息
     * @param paramVO 合同VO
     * @return 基本详情id
     * @throws SystemException 系统异常
     */
    @Override
    @Transactional
    public String updateBaseContractInfo(HTContractVO paramVO) throws SystemException {
        // 基本详情id
        String baseId = StringToolUtils.STRING_EMPTY;
        try {
            // 临时变量：当前时间
            Date currentDate = getCurrentDate();
            // 临时变量：合同id
            String contractId = paramVO.getContractId();
            // 临时变量：项目名称
            String projectName = paramVO.getProjectName();
            // 临时变量：咨询业务类型（0：造价咨询 1：招标代理 2：项目管理(监理)）
            String counselBusinessType = paramVO.getCounselBusinessType();
            // 临时变量：合同盖章类型(1.委托代理人章2.法定代表人章)
            String contractSealType = paramVO.getContractSealType();
            // 临时变量：咨询类型阶段
            String counselTypePeriod = paramVO.getCounselTypePeriod();
            // 临时变量：项目地点
            String projectAddress = paramVO.getProjectAddress();
            // 临时变量：投资金额
            String investedAmount = paramVO.getInvestedAmount();
            // 临时变量：投资金额是否暂定
            String isProvisional = paramVO.getIsProvisional();
            // 设置项目性质
            List<String> projectNatureList = paramVO.getProjectNatureList();
            // 临时变量：委托人
            String trusterName = paramVO.getTrusterName();
            // 设置委托人性质
            List<String> trusterTypeList = paramVO.getTrusterTypeList();
            // 临时变量：委托方负责人
            String trusterDutyUser = paramVO.getTrusterDutyUser();
            // 临时变量：委托方负责人电话
            String trusterDutyPhone = paramVO.getTrusterDutyPhone();
            // 临时变量：咨询人
            String consultUser = paramVO.getConsultUser();
            // 临时变量：第三方（合作单位）
            String thirdParty = paramVO.getThirdParty();
            // 临时变量：扉页咨询人
            String titlePageConsultant = paramVO.getTitlePageConsultant();
            // 临时变量：拟定合同负责人
            String dutyPeople = paramVO.getDutyPeople();
            // 临时变量：拟定人
            String planner = paramVO.getPlanner();
            // 设置合同性质
            List<String> contractNatureList = paramVO.getContractNatureList();
            // 设置确定方式
            List<String> determinateMethodList = paramVO.getDeterminateMethodList();
            // 临时变量：拟定合同金额
            String contractAmount = paramVO.getContractAmount();
            // 临时变量：拟定合同金额暂定
            String isValueProvisional = paramVO.getIsValueProvisional();
            // 临时变量：咨询合同签订日期
            String contractDate = paramVO.getContractDate();
            // 临时变量：其他情况
            String otherThings = paramVO.getOtherThings();
            // 临时变量：登录人
            String userId = paramVO.getLoginUserInfo().getLoginUserId();
            // 临时变量：预计收款时间
            List<HTContractVO> predictRequestDateList = paramVO.getPredictRequestDateList();
            // 临时变量：合同类型
            List<HTContractVO> projectTypeList = paramVO.getProjectTypeList();
            // 临时变量：合作公司
            String cooperateCompany = paramVO.getCooperateCompany();

            TbContractEntity tbContractEntity = new TbContractEntity();

            // 设置项目名称
            tbContractEntity.setProjectName(StringToolUtils.convertNullObjectToString(projectName));
            // 设置咨询业务类型（0：造价咨询 1：招标代理 2：项目管理(监理)）
            tbContractEntity.setCounselBusinessType(StringToolUtils.convertNullObjectToString(counselBusinessType));
            // 合同盖章类型(1.委托代理人章2.法定代表人章)
            tbContractEntity.setContractSealType(StringToolUtils.convertNullObjectToString(contractSealType));

            // 当咨询业务类型为造价咨询和招标代理，设置咨询类型阶段
            if(StringToolUtils.STRING_ZERO.equals(counselBusinessType) || StringToolUtils.STRING_ONE.equals(counselBusinessType)){
                // 设置咨询类型阶段
                tbContractEntity.setCounselTypePeriod(StringToolUtils.convertNullObjectToString(counselTypePeriod));
            }

            // 设置项目地点
            tbContractEntity.setProjectAddress(StringToolUtils.convertNullObjectToString(projectAddress));
            // 设置投资金额
            tbContractEntity.setInvestedAmount(StringToolUtils.convertNullObjectToString(investedAmount));
            // 设置投资金额是否暂定
            tbContractEntity.setIsProvisional(StringToolUtils.convertNullObjectToString(isProvisional));

            // 临时变量：项目性质
            String tempProjectNatureStr = "";
            // 如果项目性质列表不为空，则循环拼接成字符串
            if(projectNatureList!=null){
                for (String projectNature : projectNatureList) {
                    tempProjectNatureStr = tempProjectNatureStr.concat("@"+projectNature+"@,");
                }
            }
            // 如果项目性质不为空 则不需要去掉“，”
            if (!StringToolUtils.isNull(tempProjectNatureStr)){
                tempProjectNatureStr = tempProjectNatureStr.substring(NumberToolUtils.INT_ZERO,tempProjectNatureStr.lastIndexOf(","));
            }
            tbContractEntity.setProjectType(tempProjectNatureStr);

            // 设置委托人
            tbContractEntity.setTrusterName(StringToolUtils.convertNullObjectToString(trusterName));

            // 临时变量：委托人性质
            String tempTrusterTypeStr = "";
            // 如果委托人性质列表不为空，则循环拼接字符串
            if(trusterTypeList!=null){
                for (String trusterType : trusterTypeList) {
                    tempTrusterTypeStr = tempTrusterTypeStr.concat("@"+trusterType+"@,");
                }
            }
            // 如果委托人性质不为空 则不需要去掉“，”
            if (!StringToolUtils.isNull(tempTrusterTypeStr)){
                tempTrusterTypeStr = tempTrusterTypeStr.substring(NumberToolUtils.INT_ZERO,tempTrusterTypeStr.lastIndexOf(","));
            }
            tbContractEntity.setTrusterType(tempTrusterTypeStr);

            // 设置委托方负责人
            tbContractEntity.setTrusterDutyUser(StringToolUtils.convertNullObjectToString(trusterDutyUser));
            // 设置委托方负责人电话
            tbContractEntity.setTrusterDutyPhone(StringToolUtils.convertNullObjectToString(trusterDutyPhone));
            // 设置咨询人
            tbContractEntity.setConsultUser(StringToolUtils.convertNullObjectToString(consultUser));
            // 设置第三方（合作单位）
            tbContractEntity.setThirdParty(StringToolUtils.convertNullObjectToString(thirdParty));
            // 设置扉页咨询人
            tbContractEntity.setTitlePageConsultant(StringToolUtils.convertNullObjectToString(titlePageConsultant));
            // 设置拟定合同负责人
            tbContractEntity.setDutyPeople(StringToolUtils.convertNullObjectToString(dutyPeople));
            // 设置拟定人
            tbContractEntity.setPlanner(StringToolUtils.convertNullObjectToString(planner));

            // 临时变量：合同性质
            String tempContractNatureStr = "";
            // 如果合同性质列表不为空，则循环拼接字符串
            if(contractNatureList!=null){
                for (String contractNature : contractNatureList) {
                    tempContractNatureStr = tempContractNatureStr.concat("@"+contractNature+"@,");
                }
            }
            // 如果合同性质不为空 则不需要去掉“，”
            if (!StringToolUtils.isNull(tempContractNatureStr)){
                tempContractNatureStr = tempContractNatureStr.substring(NumberToolUtils.INT_ZERO,tempContractNatureStr.lastIndexOf(","));
            }
            tbContractEntity.setContractNature(tempContractNatureStr);

            // 临时变量：确定方式
            String tempDeterminateMethodStr = "";
            // 如果确定方式列表不为空，则循环拼接字符串
            if(determinateMethodList!=null){
                for (String determinateMethod : determinateMethodList) {
                    tempDeterminateMethodStr = tempDeterminateMethodStr.concat("@"+determinateMethod+"@,");
                }
            }
            // 如果确定方式不为空 则不需要去掉“，”
            if (!StringToolUtils.isNull(tempDeterminateMethodStr)){
                tempDeterminateMethodStr = tempDeterminateMethodStr.substring(NumberToolUtils.INT_ZERO,tempDeterminateMethodStr.lastIndexOf(","));
            }
            tbContractEntity.setDeterminateMethod(tempDeterminateMethodStr);

            // 设置拟定合同金额
            tbContractEntity.setContractAmount(StringToolUtils.convertNullObjectToString(contractAmount));
            // 设置拟定合同金额暂定
            tbContractEntity.setIsValueProvisional(StringToolUtils.convertNullObjectToString(isValueProvisional));

            // 如果咨询合同签订日期不为空，则设置咨询合同签订日期
            if (!StringToolUtils.isNull(contractDate)) {
                //将开始时间赋值给bean
                tbContractEntity.setContractDate(DateToolUtils.convertDateFormat(contractDate, DateToolUtils.yyyy_MM_dd));
            }

            // 设置其他情况
            tbContractEntity.setOtherThings(StringToolUtils.convertNullObjectToString(otherThings));
            // 设置合作公司
            tbContractEntity.setCooperateCompany(StringToolUtils.convertNullObjectToString(cooperateCompany));
            // 设置合同类型
            tbContractEntity.setContractType(StringToolUtils.convertNullObjectToString(paramVO.getContractType()));
            // 创建时间
            tbContractEntity.setCreateDate(currentDate);
            // 创建人id
            tbContractEntity.setCreateUserId(userId);
            // 修改时间
            tbContractEntity.setUpdateDate(currentDate);
            // 修改人id
            tbContractEntity.setUpdateUserId(userId);

            // 格式转换 Bean->VO
            TbContractBaseInfoEntity tbContractBaseInfoEntity = (TbContractBaseInfoEntity) ObjectReflectUtils.convertObj(tbContractEntity, TbContractBaseInfoEntity.class);

            // 合同id
            tbContractBaseInfoEntity.setContractId(contractId);
            // 查询该合同下是否新建了基本信息
            TbContractBaseInfoEntityExample tbContractBaseInfoEntityExample = new TbContractBaseInfoEntityExample();
            TbContractBaseInfoEntityExample.Criteria criteria1 = tbContractBaseInfoEntityExample.createCriteria();
            criteria1.andContractIdEqualTo(contractId);
            List<TbContractBaseInfoEntity> tbContractBaseInfoEntityList = tbContractBaseInfoEntityMapper.selectByExample(tbContractBaseInfoEntityExample);

            // 修改 如果没有这个基本信息的数据，则添加一条，如果有，则修改
            if(!ListToolUtils.isEmpty(tbContractBaseInfoEntityList)){
                // 主键id
                tbContractBaseInfoEntity.setBaseId(tbContractBaseInfoEntityList.get(0).getBaseId());
                baseId = tbContractBaseInfoEntityList.get(0).getBaseId();
                tbContractBaseInfoEntityMapper.updateByPrimaryKeySelective(tbContractBaseInfoEntity);

                // 添加
            } else {
                tbContractBaseInfoEntityMapper.insertSelective(tbContractBaseInfoEntity);
                baseId = tbContractBaseInfoEntity.getBaseId();

                // 查询该基本信息关联的合同，同步基本信息和合同信息数据
                TbContractEntityExample tbContractEntityExample = new TbContractEntityExample();
                TbContractEntityExample.Criteria criteria2 = tbContractEntityExample.createCriteria();
                criteria2.andContractIdEqualTo(contractId);
                List<TbContractEntity> tbContractEntityList = tbContractEntityMapper.selectByExample(tbContractEntityExample);

                // 循环合同信息，把基本信息同步进去
                for (TbContractEntity returnEntity:tbContractEntityList){

                    //=========================  修改合同信息 start ========================
                    // 设置合同id
                    tbContractEntity.setContractId(StringToolUtils.convertNullObjectToString(returnEntity.getContractId()));
                    // 设置合同id
                    paramVO.setContractId(StringToolUtils.convertNullObjectToString(returnEntity.getContractId()));
                    tbContractEntityMapper.updateByPrimaryKeySelective(tbContractEntity);
                    //=========================  修改合同信息 end ========================

                    //========================= 操作预计收款时间  start ========================
                    // 删除预计收款时间
                    htContractDao.excDeleteContractPredictRequestRelations(tbContractEntity);
                    // 如果列表不为空，则批量插入预计收款时间,并添加消息数据
                    if(!ListToolUtils.isEmpty(predictRequestDateList)){
                        // 批量插入预计收款时间
                        this.excInsertContractPredictRequestRelations(paramVO);
                    }
                    //========================= 操作预计收款时间  end ========================

                    //========================= 操作合同和项目类别指标  start ========================
                    // 删除合同和项目类别指标列表数据
                    htContractDao.excDeleteContractProjectTypeRelations(tbContractEntity);
                    // 如果列表不为空，则批量插入合同和项目类别指标
                    if(!ListToolUtils.isEmpty(projectTypeList)){
                        this.excInsertContractProjectTypeRelations(paramVO);
                    }
                    //========================= 操作合同和项目类别指标  end ========================
                }
            }

            // 设置合同id(基本信息表无合同id,所以传入基本信息id代替)
            tbContractEntity.setContractId(baseId);
            // 设置合同id(基本信息表无合同id,所以传入基本信息id代替)
            paramVO.setContractId(baseId);

            //========================= 操作预计收款时间  start ========================
            // 删除预计收款时间
            htContractDao.excDeleteContractPredictRequestRelations(tbContractEntity);
            // 如果列表不为空，则批量插入预计收款时间,并添加消息数据
            if(!ListToolUtils.isEmpty(predictRequestDateList)){
                // 批量插入预计收款时间
                this.excInsertContractPredictRequestRelations(paramVO);
            }
            //========================= 操作预计收款时间  end ========================

            //========================= 操作合同和项目类别指标  start ========================
            // 删除合同和项目类别指标列表数据
            htContractDao.excDeleteContractProjectTypeRelations(tbContractEntity);
            // 如果列表不为空，则批量插入合同和项目类别指标
            if(!ListToolUtils.isEmpty(projectTypeList)){
                this.excInsertContractProjectTypeRelations(paramVO);
            }
            //========================= 操作合同和项目类别指标  end ========================
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
        return baseId;
    }

    /**
     * 查询项目基本情况信息
     * @param paramVO 合同VO
     * @return 基本信息详细信息
     * @throws SystemException 系统异常
     */
    @Override
    @Transactional
    public HTContractVO excSearchBaseContractDetailInfo(HTContractVO paramVO) throws SystemException {
        // 合同VO
        HTContractVO htContractVO = new HTContractVO();
        try {
            // 取得合同id
            String contractId = paramVO.getContractId();
            // 根据合同id查询项目基本信息
            TbContractBaseInfoEntityExample tbContractBaseInfoEntityExample = new TbContractBaseInfoEntityExample();
            TbContractBaseInfoEntityExample.Criteria criteria = tbContractBaseInfoEntityExample.createCriteria();
            criteria.andContractIdEqualTo(contractId);
            List<TbContractBaseInfoEntity> tbContractBaseInfoEntityList = tbContractBaseInfoEntityMapper.selectByExample(tbContractBaseInfoEntityExample);

            // 如果存在项目基本信息，则把信息放入VO中
            if (!ListToolUtils.isEmpty(tbContractBaseInfoEntityList)) {
                // 基本信息对象
                TbContractBaseInfoEntity tbContractBaseInfoEntity = tbContractBaseInfoEntityList.get(0);

                // 项目名称
                htContractVO.setProjectName(tbContractBaseInfoEntity.getProjectName());
                // 咨询业务类型
                htContractVO.setCounselBusinessType(tbContractBaseInfoEntity.getCounselBusinessType());
                // 咨询类型阶段
                htContractVO.setCounselTypePeriod(tbContractBaseInfoEntity.getCounselTypePeriod());
                // 合同盖章类型
                htContractVO.setContractSealType(tbContractBaseInfoEntity.getContractSealType());
                // 修改时间
                htContractVO.setUpdateDate(DateToolUtils.convertDateFormat(tbContractBaseInfoEntity.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                // 项目地点
                htContractVO.setProjectAddress(tbContractBaseInfoEntity.getProjectAddress());

                // 项目类别(多checkbox)
                List<HTContractVO> tempProjectTypeList = new ArrayList<>();
                HTContractVO tempHTContractProjectTypeVO = null;
                List<HTContractVO> tempProjectFeatureList = new ArrayList<>();
                HTContractVO tempHTContractFeatureVO = null;

                HTContractBean paramSearchBean = new HTContractBean();
                // 合同id（拿基本信息id代替）
                paramSearchBean.setContractId(tbContractBaseInfoEntity.getBaseId());
                // 检索项目类别列表
                List<HTContractBean> tmpHTProjectTypeRelationsList = htContractDao.excSelectContractProjectTypeRelations(paramSearchBean);
                // 如果项目类别不为空，项目属性json转为对象
                if (!ListToolUtils.isEmpty(tmpHTProjectTypeRelationsList)) {
                    //遍历 委托人性质
                    for (HTContractBean htContractBean : tmpHTProjectTypeRelationsList) {
                        // 初始化项目类别VO 并设置 项目类别
                        tempHTContractProjectTypeVO = new HTContractVO();
                        tempHTContractProjectTypeVO.setProjectType(htContractBean.getProjectType());
                        // 保存项目类别列表数据
                        tempProjectTypeList.add(tempHTContractProjectTypeVO);

                        // 初始化项目属性VO 并设置 项目类别
                        tempHTContractFeatureVO = new HTContractVO();
                        // 初始化项目属性VO 并设置 项目类别
                        tempHTContractFeatureVO =  InterfaceUtils.jsonToObject (htContractBean.getProjectTarget(), HTContractVO.class);
                        // 保存项目属性列表数据
                        tempProjectFeatureList.add(tempHTContractFeatureVO);
                    }
                }

                // 设置项目类别数据
                htContractVO.setProjectTypeList(tempProjectTypeList);
                // 设置项目属性数据
                htContractVO.setProjectFeatureList(tempProjectFeatureList);
                // 项目类别(多checkbox)
                htContractVO.setProjectType(tbContractBaseInfoEntity.getProjectType());
                // 投资金额
                htContractVO.setInvestedAmount(tbContractBaseInfoEntity.getInvestedAmount());
                // 投资金额暂定
                htContractVO.setIsProvisional(tbContractBaseInfoEntity.getIsProvisional());

                // 如果返回的数据不为空，则把数据拆分成list
                if(!StringToolUtils.isNull(tbContractBaseInfoEntity.getProjectType())){
                    // 项目性质(多checkbox  "0":新建  "1":扩建  "2":改造)
                    htContractVO.setProjectNatureList(convertSplitStringToList(tbContractBaseInfoEntity.getProjectType()));
                }

                // 如果返回的数据不为空，则把数据拆分成list
                if(!StringToolUtils.isNull(tbContractBaseInfoEntity.getTrusterType())){
                    // 委托人性质(多checkbox  "0":政府部门  "1":国企    "2":上市公司   "3":私企   "4":其他)
                    htContractVO.setTrusterTypeList(convertSplitStringToList(tbContractBaseInfoEntity.getTrusterType()));
                }

                // 委托人
                htContractVO.setTrusterName(tbContractBaseInfoEntity.getTrusterName());
                // 委托方负责人
                htContractVO.setTrusterDutyUser(tbContractBaseInfoEntity.getTrusterDutyUser());
                // 委托方负责人电话
                htContractVO.setTrusterDutyPhone(tbContractBaseInfoEntity.getTrusterDutyPhone());
                // 咨询人
                htContractVO.setConsultUser(tbContractBaseInfoEntity.getConsultUser());
                // 第三方（合作单位）
                htContractVO.setThirdParty(tbContractBaseInfoEntity.getThirdParty());
                // 扉页咨询人
                htContractVO.setTitlePageConsultant(tbContractBaseInfoEntity.getTitlePageConsultant());
                // 拟定合同负责人
                htContractVO.setDutyPeople(tbContractBaseInfoEntity.getDutyPeople());
                // 拟定人
                htContractVO.setPlanner(tbContractBaseInfoEntity.getPlanner());

                // 如果返回的数据不为空，则把数据拆分成list
                if(!StringToolUtils.isNull(tbContractBaseInfoEntity.getProjectType())){
                    // 合同性质(多checkbox    "0": 双方合同    "1": 三方合同    "2": 补充合同   "3": 单独核算   "4": 非单独核算)
                    htContractVO.setContractNatureList(convertSplitStringToList(tbContractBaseInfoEntity.getContractNature()));
                }

                // 如果返回的数据不为空，则把数据拆分成list
                if(!StringToolUtils.isNull(tbContractBaseInfoEntity.getDeterminateMethod())){
                    // 确定方式(多checkbox   "0":招标   "1":财政委托    "2":审计委托   "3":商务洽谈   "4":战略合作   "5":法院委托   "6":其他 )
                    htContractVO.setDeterminateMethodList(convertSplitStringToList(tbContractBaseInfoEntity.getDeterminateMethod()));
                }

                // 拟定合同金额
                htContractVO.setContractAmount(tbContractBaseInfoEntity.getContractAmount());
                // 暂定
                htContractVO.setIsValueProvisional(tbContractBaseInfoEntity.getIsValueProvisional());
                // 咨询合同签订日期
                htContractVO.setContractDate(DateToolUtils.convertDateFormat(tbContractBaseInfoEntity.getContractDate(), DateToolUtils.yyyy_MM_dd));
                // 其他情况
                htContractVO.setOtherThings(tbContractBaseInfoEntity.getOtherThings());
                // 合作公司
                htContractVO.setCooperateCompany(tbContractBaseInfoEntity.getCooperateCompany());
                // 合同类型
                htContractVO.setContractType(tbContractBaseInfoEntity.getContractType());

                // 预计收款时间（多条数据）
                HTContractBean paramSearchPredictRequestBean = new HTContractBean();
                // 合同id（拿基本信息id代替）
                paramSearchPredictRequestBean.setContractId(tbContractBaseInfoEntity.getBaseId());
                // 检索预计收款时间列表
                List<HTContractBean> tmpPredictRequestRelationsList = htContractDao.excSelectPredictRequestRelations(paramSearchPredictRequestBean);

                List<HTContractVO> retPredictRequestRelationsList = new ArrayList<>();
                HTContractVO tempVO;
                if(tmpPredictRequestRelationsList != null){
                    for(HTContractBean tempHTContractBean :tmpPredictRequestRelationsList){
                        tempVO = new HTContractVO();
                        // 收款日期
                        tempVO.setStartDate(DateToolUtils.convertDateFormat(tempHTContractBean.getStartDate(), DateToolUtils.yyyy_MM_dd));
                        // 收款金额
                        tempVO.setReceivedAmount(StringToolUtils.convertNullObjectToString(tempHTContractBean.getReceivedAmount()));
                        // 是否暂定
                        tempVO.setIsProvisional(StringToolUtils.convertNullObjectToString(tempHTContractBean.getIsProvisional()));
                        retPredictRequestRelationsList.add(tempVO);
                    }
                }
                // 设置预计收款时间列表
                htContractVO.setPredictRequestDateList(retPredictRequestRelationsList);
            }
        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htContractVO;
    }

    /**
     * 查询合同详细信息
     *
     * @param paramVO 合同id
     * @return 合同信息
     */
    @Override
    public Map<String, Object> getContractDetailInfoByContractId(HTContractVO paramVO) {

        Map<String, Object> retMap = new HashedMap();

        // 根据合同id查询合同信息
        TbContractEntity retContractEntity = tbContractEntityMapper
                .selectByPrimaryKey(paramVO.getContractId());

        // 如果查到合同信息则将信息传到map里
        if (retContractEntity != null){

            // 根据项目id查询项目信息
            TbProjectEntity tbProjectEntity = tbProjectEntityMapper
                    .selectByPrimaryKey(retContractEntity.getProjectId());

            // 模板合同json串
            String tempFormworkJsonStr = retContractEntity.getFormworkContent();

            // 如果json 串不为空则转成map格式
            if (!StringToolUtils.isNull(tempFormworkJsonStr)){

                // json转成map
                retMap = InterfaceUtils.jsonToObject(tempFormworkJsonStr,Map.class);
            }

            // 委托人名称
            String tempTrusterNameStr = retContractEntity.getTrusterName();

            retMap.put("trusterName",tempTrusterNameStr);

            // 扉页咨询人
            String tempTitlePageConsultantStr = retContractEntity.getTitlePageConsultant();

            retMap.put("titlePageConsultant",tempTitlePageConsultantStr);

            // 项目编号+ 合同编号
            String tempContractNumberStr = tbProjectEntity.getProjectNum() + "-" + retContractEntity.getContractNum();

            retMap.put("contractNumber",tempContractNumberStr);

            // 项目名称
            String tempProjectNameStr = retContractEntity.getProjectName();

            retMap.put("projectName",tempProjectNameStr);

            // 项目地点
            String tempProjectAddressStr = retContractEntity.getProjectAddress();

            retMap.put("projectAddress",tempProjectAddressStr);

            // 人民币符号
            String tempRmbStr = Constant.CONTRACT_TEMPLATE_RMB;

            // 字符串 暂定
            String tempProvisionalStr = Constant.CONTRACT_TEMPLATE_PROVISIONAL_STR;

            // 投资金额
            String tempInvestedAmountStr = tempRmbStr + retContractEntity.getInvestedAmount();

            // 投资金额是否暂定
            String tempIsProvisionalStr = retContractEntity.getIsProvisional();



            // 如果是否暂定标识为暂定则 投资金额 前面加上 “暂定”
            if ("1".equals(tempIsProvisionalStr)){

                tempInvestedAmountStr = tempProvisionalStr + tempInvestedAmountStr;
            }

            retMap.put("investedAmount",tempInvestedAmountStr);

            // 拟定合同金额
            BigDecimal tempContractAmountBigDecimal = NumberToolUtils
                    .convertNullToBigDecimal(retContractEntity.getContractAmount());

            // 将拟定合同金额 转换为金钱格式并保留两位小数
            String tempContractAmountStr = tempRmbStr + NumberToolUtils
                    .convertNumberToFormatStringWithHalfUp(tempContractAmountBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

            // 将拟定合同金额 转换成大写
            String tempContractAmountUpperStr = NumberToCN.number2CNMontrayUnit(tempContractAmountBigDecimal);

            // 拟定合同金额是否暂定
            String tempIsValueProvisionalStr = retContractEntity.getIsValueProvisional();

            // 如果是否暂定标识为暂定则 拟定合同金额 前面加上 “暂定”
            if ("1".equals(tempIsValueProvisionalStr)){

                tempContractAmountStr = tempProvisionalStr + tempContractAmountStr;

                tempContractAmountUpperStr = tempProvisionalStr + tempContractAmountUpperStr;
            }

            retMap.put("contractAmount",tempContractAmountStr);

            retMap.put("contractAmountUpper",tempContractAmountUpperStr);

            // 咨询合同签订日期
            String tempContractDateStr = DateToolUtils
                    .convertDateFormat(retContractEntity.getContractDate(), DateToolUtils.yyyy_MM_dd);

            // 截取咨询合同签订日期 年
            String tempYearStr = tempContractDateStr.substring(0,4);

            retMap.put("contractYear",tempYearStr);

            // 截取咨询合同签订日期月
            String tempMonthStr = tempContractDateStr.substring(5,7);

            retMap.put("contractMonth",tempMonthStr);

            // 截取咨询合同签订日期日
            String tempDayStr = tempContractDateStr.substring(8,10);

            retMap.put("contractDay",tempDayStr);

            // 委托方负责人
            String tempTrusterDutyUserStr = retContractEntity.getTrusterDutyUser();

            retMap.put("trusterDutyUser",tempTrusterDutyUserStr);

            // 委托方负责人电话
            String tempTrusterDutyPhoneStr = retContractEntity.getTrusterDutyPhone();

            retMap.put("trusterDutyPhone",tempTrusterDutyPhoneStr);

            // 当前年月
            String tempCurrentYearMonthStr = DateToolUtils
                    .convertDateFormat(new Date(), DateToolUtils.YYYY_MM_CHINESE);

            retMap.put("currentYearMonth",tempCurrentYearMonthStr);

            // 模板类型
            String tempFormworkTypeStr = retContractEntity.getFormworkType();

            // 模板类型数字
            int tempFormworkTypeInt = NumberToolUtils.convertNullToInteger(retContractEntity.getFormworkType());

            retMap.put("formworkType",tempFormworkTypeStr);

            // 合同模板名称
            String tempContractTemplateNameStr = BusinessUtils.convertContractTemplateTypeToStateStr(tempFormworkTypeInt);

            //0：造价咨询合同 1：建设工程招标代理合同 2：工程管理咨询服务合同 3：监理合同
            // 如果合同模板为 造价咨询合同
            if (Constant.CONTRACT_COST_CONSULTATION_VAL.equals(tempFormworkTypeStr)){

                // 支付方式
                String tempPaymentMethodStr = (String) retMap.get("paymentMethod");

                // 支付内容
                String tempPaymentInfoStr = (String) retMap.get("paymentInfo");

                // 如果为一次性支付 支付内容为 咨询成果报告出具xxx日内支付
                if ("0".equals(tempPaymentMethodStr)){

                    tempPaymentInfoStr = "咨询成果报告出具"+ tempPaymentInfoStr +"日内一次性支付";

                }
                retMap.put("paymentInfo",tempPaymentInfoStr);

                // 总份数
                BigDecimal tempTotalContractCount = NumberToolUtils.convertNullToBigDecimal(retMap.get("totalContractCount"));
                // 委托人份数
                BigDecimal tempTrusterContractCount = NumberToolUtils.convertNullToBigDecimal(retMap.get("trusterContractCount"));
                // 咨询人份数
                BigDecimal tempConsultantContractCount = NumberToolUtils.convertNullToBigDecimal(retMap.get("consultantContractCount"));

                // 总份数转成大写
                String tempTotalContractCountStr = NumberToCN.numberToCNUpper(tempTotalContractCount);
                retMap.put("totalContractCount",tempTotalContractCountStr);
                // 委托人份数转成大写
                String tempTrusterContractCountStr = NumberToCN.numberToCNUpper(tempTrusterContractCount);
                retMap.put("trusterContractCount",tempTrusterContractCountStr);
                // 咨询人份数转成大写
                String tempConsultantContractCountStr = NumberToCN.numberToCNUpper(tempConsultantContractCount);
                retMap.put("consultantContractCount",tempConsultantContractCountStr);


             // 建设工程招标代理合同
            }else if (Constant.CONTRACT_BIDDING_AGENT_VAL.equals(tempFormworkTypeStr)){

                // 代理内容flg
                String tempAgentContentFlgStr = (String) retMap.get("agentContent");

                // 代理内容
                String tempAgentContentStr = "无";

                // 代理内容+ "、"
                String tempAgentContentAddDunStr = StringToolUtils.STRING_EMPTY;

                // 如果代理内容flg是 1 则默认代理内容 等于 造价咨询
                if ("1".equals(tempAgentContentFlgStr)){

                    tempAgentContentAddDunStr = "造价咨询、";
                    tempAgentContentStr = "造价咨询";
                }
                retMap.put("agentContentAddDun",tempAgentContentAddDunStr);

                retMap.put("agentContent",tempAgentContentStr);

            // 工程管理咨询服务合同
            }else if (Constant.CONTRACT_CONSULTING_SERVICE_VAL.equals(tempFormworkTypeStr)){

                // 设计阶段服务报酬
                BigDecimal tempDesignPayBigDecimal = NumberToolUtils
                        .convertNullToBigDecimal(retMap.get("designPay"));
                // 设计阶段服务报酬 是否暂定
                String tempIsDesignPayProvisionalStr = (String) retMap.get("isDesignPayProvisional");

                // 设计阶段服务报酬
                String tempDesignPayStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(tempDesignPayBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

                // 如果是否暂定标识为暂定则 设计阶段服务报酬 前面加上 “暂定”
                if ("1".equals(tempIsDesignPayProvisionalStr)){

                    tempDesignPayStr = tempProvisionalStr + tempDesignPayStr;
                }
                retMap.put("designPay",tempDesignPayStr);


                // 施工阶段服务报酬
                BigDecimal tempConstructionPayBigDecimal = NumberToolUtils
                        .convertNullToBigDecimal(retMap.get("constructionPay"));
                // 施工阶段服务报酬 是否暂定
                String tempIsConstructionPayProvisionalStr = (String) retMap.get("isConstructionPayProvisional");

                // 施工阶段服务报酬
                String tempConstructionPayStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(tempConstructionPayBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

                // 如果是否暂定标识为暂定则 施工阶段服务报酬 前面加上 “暂定”
                if ("1".equals(tempIsConstructionPayProvisionalStr)){

                    tempConstructionPayStr = tempProvisionalStr + tempConstructionPayStr;
                }
                retMap.put("constructionPay",tempConstructionPayStr);


                // 结算阶段服务报酬
                BigDecimal tempSettlementPayBigDecimal = NumberToolUtils
                        .convertNullToBigDecimal(retMap.get("settlementPay"));
                // 结算阶段服务报酬 是否暂定
                String tempIsSettlementPayProvisionalStr = (String) retMap.get("isSettlementPayProvisional");

                // 结算阶段服务报酬
                String tempSettlementPayStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(tempSettlementPayBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

                // 如果是否暂定标识为暂定则 结算阶段服务报酬 前面加上 “暂定”
                if ("1".equals(tempIsSettlementPayProvisionalStr)){

                    tempSettlementPayStr = tempProvisionalStr + tempSettlementPayStr;
                }
                retMap.put("settlementPay",tempSettlementPayStr);

                // 合计
                BigDecimal tempTotalPriceBigDecimal = tempDesignPayBigDecimal.add(tempConstructionPayBigDecimal).add(tempSettlementPayBigDecimal);
                // 合计 字符串
                String tempTotalPriceStr = tempRmbStr + NumberToolUtils
                       .convertNumberToFormatStringWithHalfUp(tempTotalPriceBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

                // 合计 大写
                String tempTotalPriceUpperStr = NumberToCN.number2CNMontrayUnit(tempTotalPriceBigDecimal);

                // 设计阶段服务报酬、施工阶段服务报酬、结算阶段服务报酬 如果有一个为暂停那么总额就为暂定
                if ("1".equals(tempIsDesignPayProvisionalStr) || "1".equals(tempIsConstructionPayProvisionalStr) || "1".equals(tempIsSettlementPayProvisionalStr)){

                    tempTotalPriceUpperStr = tempProvisionalStr + tempTotalPriceUpperStr;

                    tempTotalPriceStr = tempProvisionalStr + tempTotalPriceStr;
                }
                retMap.put("totalPriceUpper",tempTotalPriceUpperStr);
                retMap.put("totalPrice",tempTotalPriceStr);



                // 预付款付费额
                String tempAdvancePaymentStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("advancePayment"), NumberToolUtils.INT_SCALE_FLOAT);

                // 预付款付费额 是否暂定
                String tempIsAdvancePaymentProvisionalStr = (String) retMap.get("isAdvancePaymentProvisional");

                // 如果是否暂定标识为暂定则 预付款付费额 前面加上 “暂定”
                if ("1".equals(tempIsAdvancePaymentProvisionalStr)){

                    tempAdvancePaymentStr = tempProvisionalStr + tempAdvancePaymentStr;
                }
                retMap.put("advancePayment",tempAdvancePaymentStr);

                // 进度款付费额
                String tempProgressPaymentStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("progressPayment"), NumberToolUtils.INT_SCALE_FLOAT);

                // 进度款付费额 是否暂定
                String tempIsProgressPaymentProvisionalStr = (String) retMap.get("isProgressPaymentProvisional");

                // 如果是否暂定标识为暂定则 进度款付费额 前面加上 “暂定”
                if ("1".equals(tempIsProgressPaymentProvisionalStr)){

                    tempProgressPaymentStr = tempProvisionalStr + tempProgressPaymentStr;
                }
                retMap.put("progressPayment",tempProgressPaymentStr);

                // 余款付费额
                String tempBalanceStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("balance"), NumberToolUtils.INT_SCALE_FLOAT);

                // 余款付费额 是否暂定
                String tempIsBalanceProvisionalStr = (String) retMap.get("isBalanceProvisional");

                // 如果是否暂定标识为暂定则 余款付费额 前面加上 “暂定”
                if ("1".equals(tempIsBalanceProvisionalStr)){

                    tempBalanceStr = tempProvisionalStr + tempBalanceStr;
                }
                retMap.put("balance",tempBalanceStr);

            // 监理合同
            }else {
                // 签约酬金
                BigDecimal  tempSignAmountBigDecimal = NumberToolUtils.convertNullToBigDecimal(retMap.get("signAmount"));

                // 签约酬金 是否暂定
                String tempIsSignAmountProvisionalStr = (String) retMap.get("isSignAmountProvisional");

                // 转成金钱格式
                String tempSignAmountStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(tempSignAmountBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);

                // 签约酬金 大写
                String tempSignAmountUpperStr = NumberToCN.number2CNMontrayUnit(tempSignAmountBigDecimal);

                // 如果是否暂定标识为暂定则 签约酬金 前面加上 “暂定”
                if ("1".equals(tempIsSignAmountProvisionalStr)){

                    tempSignAmountStr = tempProvisionalStr + tempSignAmountStr;

                    tempSignAmountUpperStr = tempProvisionalStr + tempSignAmountUpperStr;
                }
                retMap.put("signAmount",tempSignAmountStr);

                retMap.put("signAmountUpper",tempSignAmountUpperStr);

                // 工程概算投资额或建筑安装工程费
                BigDecimal tempInstalCostBigDecimal = NumberToolUtils.convertNullToBigDecimal(retMap.get("instalCost"));

                // 工程概算投资额或建筑安装工程费 金钱格式
                String tempInstalCostStr = tempRmbStr + NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(tempInstalCostBigDecimal, NumberToolUtils.INT_SCALE_FLOAT);;

                // 工程概算投资额或建筑安装工程费 是否暂定
                String tempIsInstalCostProvisionalStr = (String) retMap.get("isInstalCostProvisional");

                // 如果是否暂定标识为暂定则 工程概算投资额或建筑安装工程费 前面加上 “暂定”
                if ("1".equals(tempIsInstalCostProvisionalStr)){

                    tempInstalCostStr = tempProvisionalStr + tempInstalCostStr;
                }

                retMap.put("instalCost",tempInstalCostStr);


            }
            retMap.put("contractTemplateName",tempContractTemplateNameStr);

        }

        return retMap;
    }

    /**
     * 审核合同
     * @param paramVO 合同信息
     * @throws SystemException 系统异常
     */
    @Override
    @Transactional
    public void excAuditingHTContract(HTContractVO paramVO) throws SystemException,LogicException {
        Date tempCurrentDate = getCurrentDate();
        try {

            // =====================================判断驳回原因和选择错误是否为空 start=========================================

            // 如果是驳回，并且驳回原因或者选择错误不能为空
            if (Constant.NOT_APPROVED.equals(paramVO.getIsPass()) && ListToolUtils.isEmpty(paramVO.getUserQuestionRecordRelationList())) {
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0014);
            }

            // =====================================判断驳回原因和选择错误是否为空 start=========================================

            HTContractBean contractBean = htContractDao.getContractInfoByContractId(paramVO.getContractId());

            //对比更新时间,如果时间不同，说明此条数据已经被提交审核
            if (!paramVO.getUpdateDate().equals(DateToolUtils.convertDateFormat(
                    contractBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO))) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }

            HTContractBean htContractBean = htContractDao.getContractInfoByContractId(paramVO.getContractId());
            //是否通过
            htContractBean.setIsPass(paramVO.getIsPass());
            //意见
            htContractBean.setRejectReason(paramVO.getRejectReason());
            //下一节点
            htContractBean.setSubmitNode(paramVO.getSubmitNode());
            Map<String, Object> variables = new HashMap<>();
            HTContractVO contractVO = (HTContractVO) ObjectReflectUtils.convertObj(htContractBean, HTContractVO.class);
            //登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            //登录人信息
            contractVO.setLoginUserInfo(userInfo);
            //审批人ID
            contractVO.setApproveUserId(userInfo.getLoginUserId());
            //审批人名称
            contractVO.setApproveUserName(userInfo.getUserName());
            //启动工作流
            variables.put(WorkFlowConstant.KEY_CONTRACT_VO, contractVO);
            String isPass = contractVO.getIsPass();
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, isPass);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            if (Constant.NOT_APPROVED.equals(isPass)) {
                variables.put(WorkFlowConstant.KEY_REJECT_REASON, contractVO.getRejectReason());
                variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, contractVO.getDelegateTaskKey());
                commonActivitiListenVO.setDepartmentId(htContractBean.getDepartmentId());
            } else {
                //2019.09.17需求变更：项目监理部合同审批时只有2级审核
                if (WriteContractEnum.QualityVerify.getNodeKey().equals(htContractBean.getDelegateTaskKey()) && Constant.DEPT_ID_XMJL.equals(htContractBean.getDepartmentId())) {
                    commonActivitiListenVO.setDepartmentId(Constant.DEPT_ID_XMJL);
                    variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, WriteContractEnum.Signer.getNodeKey());
                } else {
                    String departmentId = StringUtils.isBlank(WriteContractEnum.getDeptId(contractVO.getSubmitNode())) ? contractVO.getDepartmentId() : WriteContractEnum.getDeptId(contractVO.getSubmitNode());
                    commonActivitiListenVO.setDepartmentId(departmentId);
                    variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, contractVO.getSubmitNode());
                }
            }
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            String historyRecordId = contractProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);
            if (null!= paramVO.getUserQuestionRecordRelationList()&&paramVO.getUserQuestionRecordRelationList().size()>0 ){
                //批量插入审核记录和人员和问题类型关联
                List<HTUserQuestionRecordRelationVO> relationVOList = paramVO.getUserQuestionRecordRelationList();
                List<HTUserQuestionRecordRelationBean>  relationBeanList = new ArrayList<>();
                Date currentDate = new Date();
                for (HTUserQuestionRecordRelationVO htUserQuestionRecordRelationVO:relationVOList){
                    HTUserQuestionRecordRelationBean htUserQuestionRecordRelationBean = new HTUserQuestionRecordRelationBean();
                    //唯一标识
                    htUserQuestionRecordRelationBean.setRelationId(UUID.randomUUID().toString());
                    //历史id
                    htUserQuestionRecordRelationBean.setRecordId(historyRecordId);
                    //人员ID
                    htUserQuestionRecordRelationBean.setUserId(htUserQuestionRecordRelationVO.getUserId());
                    //问题ID
                    htUserQuestionRecordRelationBean.setQuestionId(htUserQuestionRecordRelationVO.getQuestionId());
                    //问题个数
                    htUserQuestionRecordRelationBean.setQuestionNum(htUserQuestionRecordRelationVO.getQuestionNum());
                    //来源
                    htUserQuestionRecordRelationBean.setSourceType(Constant.CONTRACT_SOURCE_TYPE);
                    //业务ID
                    htUserQuestionRecordRelationBean.setBusinessId(paramVO.getContractId());
                    //创建时间
                    htUserQuestionRecordRelationBean.setCreateDate(currentDate);
                    //创建人
                    htUserQuestionRecordRelationBean.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                    //修改时间
                    htUserQuestionRecordRelationBean.setUpdateDate(currentDate);
                    //修改人
                    htUserQuestionRecordRelationBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                    //是否删除
                    htUserQuestionRecordRelationBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
                    relationBeanList.add(htUserQuestionRecordRelationBean);
                }
                htUserQuestionRecordRelationDao.excInsertUserQuestionRecordRelations(relationBeanList);
            }

            // 业务ID(合同ID)
            paramVO.setBusinessId(paramVO.getContractId());

            // 创建时间
            paramVO.setCreateDate(DateToolUtils.convertDateFormat(tempCurrentDate,DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            // 创建人
            paramVO.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 修改时间
            paramVO.setUpdateDate(DateToolUtils.convertDateFormat(tempCurrentDate,DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            // 修改人
            paramVO.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 审核结果字符串
            String tempAuditResultStr = "通过";

            // 如果是驳回
            if (Constant.NOT_APPROVED.equals(isPass)) {

                String uuid = UUID.randomUUID().toString();

                // 文件上传后的路径
                String filePath = "";

                if(!StringToolUtils.isNull(paramVO.getReviewFileName())){
                    // 文件名
                    String realFileName = paramVO.getReviewFileName();

                    // 获取文件的后缀名
                    String suffixName = realFileName.substring(realFileName.lastIndexOf("."));

                    // 获取文件路径
                    String fileName = uuid + suffixName;

                    // 文件上传后的路径
                    filePath = Constant.FILE_SEPARATE + "review" + Constant.FILE_SEPARATE + "contract" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;

                    //文件地址
                    paramVO.setReviewFilePath(filePath);

                    // 获取文件的真实名称
                    paramVO.setReviewFileName(paramVO.getReviewFileName());

                    //通知OnlyOffice documentServer 保存文件
                    OnlyOfficeUtils.notifyOnlyOfficeToSave(paramVO);
                }

                tempAuditResultStr = "驳回";

            }

            // 修改合同信息
            TbContractEntity tbContractEntity = new TbContractEntity();
            tbContractEntity.setContractId(paramVO.getContractId());
            tbContractEntity.setUpdateDate(getCurrentDate());
            tbContractEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            tbContractEntityMapper.updateByPrimaryKeySelective(tbContractEntity);

            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(htContractBean.getApplyTime(),DateToolUtils.yyyy_MM_dd);

            // 当前审核节点
            String delegateTaskKey = htContractBean.getDelegateTaskKey();

            // 职位名称
            String jobName = "";

            switch (delegateTaskKey){
                // 部门经理
                case "ManagerVerify":
                    jobName = WriteContractEnum.ManagerVerify.getJobName();
                    break;

                // 质控部组员
                case "QualityVerify":
                    jobName = WriteContractEnum.QualityVerify.getJobName();
                    break;

                // 质控部副总经理
                case "GenerManagerVerify":
                    jobName = WriteContractEnum.GenerManagerVerify.getJobName();
                    break;

                // 签发人
                case "Signer":
                    jobName = WriteContractEnum.Signer.getJobName();
                    break;

                default:
                    break;
            }

            String tempMessageContentStr = "您在 "+applyTime+" 发起的 "
                    +htContractBean.getProjectNum()+"项目"
                    +htContractBean.getProjectNum()+"-"+htContractBean.getContractNum()+"合同 申请 ，被 "
                    +jobName+"("+userInfo.getUserName()+")审核并"+tempAuditResultStr;

            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(htContractBean.getApplyUserId());
            // 设置是否阅读 0：否    1：是
            tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
            // 设置创建时间
            tbMessageEntity.setCreateDate(tempCurrentDate);
            // 设置创建人id
            tbMessageEntity.setCreateUserId(userInfo.getLoginUserId());
            // 设置修改时间
            tbMessageEntity.setUpdateDate(tempCurrentDate);
            // 设置修改人id
            tbMessageEntity.setUpdateUserId(userInfo.getLoginUserId());
            // 设置是否删除  0：否    1：是
            tbMessageEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);
            // 插入消息信息
            commonService.excInsertMessageInfo(tbMessageEntity);

            // 微信公众号推送模板消息
            // loginUserId 当前登录用户id
            // openId 用户唯一标识
            // acceptUserName 接收人名称
            // messageContent 消息内容
            commonService.sendTemplateMessageToWeChat(paramVO.getLoginUserInfo().getLoginUserId()
                    ,htContractBean.getOpenId(),htContractBean.getApplyUserName(),tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     *  查询合同信息
     * @param paramVO 合同id
     * @return 合同信息
     */
    @Override
    public HTContractVO excSearchContractDetailInfoById(HTContractVO paramVO) {
        try {
            //查询合同详情
            HTContractBean htContractBean = htContractDao.getContractInfoByContractId(paramVO.getContractId());
            /*格式转换*/
            HTContractVO htContractVO = new HTContractVO();
            //合同ID
            htContractVO.setContractId(htContractBean.getContractId());

            //任务Key
            htContractVO.setDelegateTaskKey(htContractBean.getDelegateTaskKey());
            //委托任务ID
            htContractVO.setDelegateTaskId(htContractBean.getDelegateTaskId());
            //提交节点
            htContractVO.setSubmitNode(htContractBean.getSubmitNode());
            //法务信息
            htContractVO.setLegalOpinion(htContractBean.getLegalOpinion());
            //修改时间
            htContractVO.setUpdateDate(DateToolUtils.convertDateFormat(htContractBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            // 审批状态
            htContractVO.setVerifyState(htContractBean.getVerifyState());
            // 通过时间
            htContractVO.setApproveTime(htContractBean.getApproveTime());

            // 合同类型
            htContractVO.setContractType(htContractBean.getContractType());

            // 合同文件名称
            String tempContractFileNameStr = StringToolUtils.STRING_EMPTY;

            // 合同文件路径
            String tempContractFilePathStr = StringToolUtils.STRING_EMPTY;

            // 如果合同类型为自定义合同 则取得自定义合同名称和路径
            if ("0".equals(htContractBean.getContractType())){

                // 自定义合同真实名字
                tempContractFileNameStr = StringToolUtils.convertNullObjectToString(htContractBean.getContractFileName());

                // 自定义合同路径
                tempContractFilePathStr = StringToolUtils.convertNullObjectToString(htContractBean.getContractFilePath());

                // 如果合同类型为模板合同 则取得模板合同名称和生成路径
                // 如果模板类型不为空
            } else if("1".equals(htContractBean.getContractType()) && !StringToolUtils.isNull(htContractBean.getFormworkType())){
                    int tempFormworkTypeInt = NumberToolUtils.convertNullToInteger(htContractBean.getFormworkType());//模板类型
                    tempContractFileNameStr = BusinessUtils.convertContractTemplateTypeToStateStr(tempFormworkTypeInt);// 合同模板名称
                    tempContractFilePathStr = "/contract/downLoad/downLoadContractWord";// 合同模板生成路径
            }

            // 文件名称
            htContractVO.setContractFileName(tempContractFileNameStr);

            // 文件路径
            htContractVO.setContractFilePath(tempContractFilePathStr);

            // 项目编号

            htContractVO.setProjectNum(htContractBean.getProjectNum());
            List<HTContractVO> userVOList = new ArrayList<>();

            HTContractVO tempVO = new HTContractVO();

            //成员ID
            tempVO.setUserId(htContractBean.getApplyUserId());
            //成员名称
            tempVO.setUserName(htContractBean.getApplyUserName());

            userVOList.add(tempVO);

            htContractVO.setUserList(userVOList);
            return htContractVO;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     *  合同确认盖章
     * @param paramVO 合同id
     */
    @Override
    @Transactional
    public void excContractConfirmSeal(HTContractVO paramVO) throws LogicException,SystemException {
        try {
            //查询合同详情
            HTContractBean htContractBean = htContractDao.getContractInfoByContractId(paramVO.getContractId());
            //对比更新时间,如果时间不同，说明此条数据已经被提交审核
            if (!paramVO.getUpdateDate().equals(DateToolUtils.convertDateFormat(
                    htContractBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO))) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            Map<String, Object> variables = new HashMap<>();
            HTContractVO contractVO = (HTContractVO) ObjectReflectUtils.convertObj(htContractBean, HTContractVO.class);
            //登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            //登录人信息
            contractVO.setLoginUserInfo(userInfo);
            //审批人ID
            contractVO.setApproveUserId(userInfo.getLoginUserId());
            //审批人名称
            contractVO.setApproveUserName(userInfo.getUserName());
            //启动工作流
            variables.put(WorkFlowConstant.KEY_CONTRACT_VO, contractVO);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            String departmentId = StringUtils.isBlank(WriteContractEnum.getDeptId(contractVO.getSubmitNode())) ? contractVO.getDepartmentId() : WriteContractEnum.getDeptId(contractVO.getSubmitNode());
            commonActivitiListenVO.setDepartmentId(departmentId);
            variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, contractVO.getSubmitNode());
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            contractProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);

            // 修改合同信息
            TbContractEntity tbContractEntity = new TbContractEntity();
            tbContractEntity.setContractId(paramVO.getContractId());
            tbContractEntity.setUpdateDate(getCurrentDate());
            tbContractEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            tbContractEntityMapper.updateByPrimaryKeySelective(tbContractEntity);

            // ================================================ 插入消息信息 start =====================================================

            Date tempCurrentDate = getCurrentDate();

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(htContractBean.getApplyTime(),DateToolUtils.yyyy_MM_dd);

            // 设置当前登录用户id
            htContractBean.setCurrentUserId(userInfo.getLoginUserId());
            // 设置部门id
            htContractBean.setDepartmentId(Constant.DEPT_ID_XZ);

            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            String tempMessageContentStr = "您在 "+applyTime+" 发起的 "
                    +htContractBean.getProjectNum()+"项目"
                    +htContractBean.getProjectNum()+"-"+htContractBean.getContractNum()+"合同 申请 ，被 "
                    +htContractBeanList.get(0).getJobName()+"("+userInfo.getUserName()+")确认盖章了";
            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(htContractBean.getApplyUserId());
            // 设置是否阅读 0：否    1：是
            tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
            // 设置创建时间
            tbMessageEntity.setCreateDate(tempCurrentDate);
            // 设置创建人id
            tbMessageEntity.setCreateUserId(userInfo.getLoginUserId());
            // 设置修改时间
            tbMessageEntity.setUpdateDate(tempCurrentDate);
            // 设置修改人id
            tbMessageEntity.setUpdateUserId(userInfo.getLoginUserId());
            // 设置是否删除  0：否    1：是
            tbMessageEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);
            // 插入消息信息
            commonService.excInsertMessageInfo(tbMessageEntity);

            // 微信公众号推送模板消息
            // loginUserId 当前登录用户id
            // openId 用户唯一标识
            // acceptUserName 接收人名称
            // messageContent 消息内容
            commonService.sendTemplateMessageToWeChat(paramVO.getLoginUserInfo().getLoginUserId()
                    ,htContractBean.getOpenId(),htContractBean.getApplyUserName(),tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 确认归档
     * @param paramVO 合同id
     */
    @Override
    public void excContractSubmitArchive(HTContractVO paramVO)throws LogicException,SystemException {

        Date tempCurrentDate = getCurrentDate();
        try {
            //查询合同详情
            HTContractBean htContractBean = htContractDao.getContractInfoByContractId(paramVO.getContractId());

            //对比更新时间,如果时间不同，说明此条数据已经被提交审核
            if (!paramVO.getUpdateDate().equals(DateToolUtils.convertDateFormat(
                    htContractBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO))) {
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }

            Map<String, Object> variables = new HashMap<>();

            HTContractVO contractVO = (HTContractVO) ObjectReflectUtils.convertObj(htContractBean, HTContractVO.class);
            //登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();

            //登录人信息
            contractVO.setLoginUserInfo(userInfo);

            //审批人ID
            contractVO.setApproveUserId(userInfo.getLoginUserId());

            //审批人名称
            contractVO.setApproveUserName(userInfo.getUserName());

            //启动工作流
            variables.put(WorkFlowConstant.KEY_CONTRACT_VO, contractVO);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            String departmentId = StringUtils.isBlank(WriteContractEnum.getDeptId(contractVO.getSubmitNode())) ? contractVO.getDepartmentId() : WriteContractEnum.getDeptId(contractVO.getSubmitNode());
            commonActivitiListenVO.setDepartmentId(departmentId);
            variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, contractVO.getSubmitNode());
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            contractProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);

            TbContractEntity updateTbContractEntity = new TbContractEntity();

            // 合同id
            updateTbContractEntity.setContractId(paramVO.getContractId());

            // 档案编号
            updateTbContractEntity.setFileNumber(paramVO.getFileNumber());

            // 更新时间
            updateTbContractEntity.setUpdateDate(tempCurrentDate);

            // 更新人id
            updateTbContractEntity.setUpdateUserId(userInfo.getLoginUserId());

            // 更新合同档案号
            tbContractEntityMapper.updateByPrimaryKeySelective(updateTbContractEntity);


            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(htContractBean.getApplyTime(),DateToolUtils.yyyy_MM_dd);

            // 设置当前登录用户id
            htContractBean.setCurrentUserId(userInfo.getLoginUserId());
            // 设置部门id
            htContractBean.setDepartmentId(Constant.DEPT_ID_XZ);

            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            String tempMessageContentStr = "您在 "+applyTime+" 发起的 "
                    +htContractBean.getProjectNum()+"项目"
                    +htContractBean.getProjectNum()+"-"+htContractBean.getContractNum()+"合同 申请 ，被 "
                    +htContractBeanList.get(0).getJobName()+"("+userInfo.getUserName()+")确认归档了";
            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(htContractBean.getApplyUserId());
            // 设置是否阅读 0：否    1：是
            tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
            // 设置创建时间
            tbMessageEntity.setCreateDate(tempCurrentDate);
            // 设置创建人id
            tbMessageEntity.setCreateUserId(userInfo.getLoginUserId());
            // 设置修改时间
            tbMessageEntity.setUpdateDate(tempCurrentDate);
            // 设置修改人id
            tbMessageEntity.setUpdateUserId(userInfo.getLoginUserId());
            // 设置是否删除  0：否    1：是
            tbMessageEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);
            // 插入消息信息
            commonService.excInsertMessageInfo(tbMessageEntity);

            // 微信公众号推送模板消息
            // loginUserId 当前登录用户id
            // openId 用户唯一标识
            // acceptUserName 接收人名称
            // messageContent 消息内容
            commonService.sendTemplateMessageToWeChat(paramVO.getLoginUserInfo().getLoginUserId()
                    ,htContractBean.getOpenId(),htContractBean.getApplyUserName(),tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 添加归档文件信息
     */
    @Override
    @Transactional
    public void excInsertContractArchiveFileInfo(HTContractVO paramVO, MultipartFile[] file) throws SystemException {
        try {

            Date currentDate = getCurrentDate();

            TbContractSaveMaterialRelationEntity tbContractSaveMaterialRelationEntity = new TbContractSaveMaterialRelationEntity();

            // 登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(
                    paramVO.getLoginUserInfo().getLoginUserId());

            // 循环上传文件
            for (int i = 0; i < file.length; i++) {
                // uuid 用于拼接文件路径
                String uuid = UUID.randomUUID().toString();

                // 文件上传后的路径
                String filePath = "";

                // 文件名
                String realFileName = file[i].getOriginalFilename();

                // 获取文件的后缀名
                String suffixName = realFileName.substring(realFileName.lastIndexOf("."));

                // 获取文件路径
                String fileName = uuid + suffixName;

                // 文件上传后的路径
                filePath = Constant.FILE_SEPARATE + "contract" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;

                // 文件名称
                tbContractSaveMaterialRelationEntity.setMaterialName(realFileName);

                // 文件地址
                tbContractSaveMaterialRelationEntity.setMaterialPath(filePath);

                // 设置主键id
                tbContractSaveMaterialRelationEntity.setRelationId(
                        StringToolUtils.convertNullObjectToString(uuid));

                //合同ID
                tbContractSaveMaterialRelationEntity.setContractId(
                        StringToolUtils.convertNullObjectToString(paramVO.getContractId()));

                //是否删除
                tbContractSaveMaterialRelationEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

                //创建人
                tbContractSaveMaterialRelationEntity.setCreateUserId(tempLoginUserIdStr);

                //创建时间
                tbContractSaveMaterialRelationEntity.setCreateDate(currentDate);

                //修改人
                tbContractSaveMaterialRelationEntity.setUpdateUserId(tempLoginUserIdStr);

                //修改时间
                tbContractSaveMaterialRelationEntity.setUpdateDate(currentDate);

                //添加文件信息
                htContractDao.excInsertContractArchiveFileRelationInfo(tbContractSaveMaterialRelationEntity);

                // 上传文件
                boolean isUploadSignSuccess = FileUtils.uploadFile(filePath, file[i]);

                if (!isUploadSignSuccess) {
                    // MSG_ERROR_PUBLIC_NOTICE_0001 上传文件失败！
                    throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0001);
                }

            }

        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     *  删除归档文件
     * @param paramVO 文件路径
     */
    @Override
    @Transactional
    public void excDeleteContractArchiveFileInfo(HTContractVO paramVO) {
        try {

            String materialPath = paramVO.getMaterialPath();

            //删除文件信息
            tbContractSaveMaterialRelationEntityMapper.deleteByPrimaryKey(paramVO.getRelationId());

            //删除真实文件
            FileUtils.deleteFile(materialPath);

        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }


    }

    /**
     *  查询归档文件集合
     * @param paramVO 合同id
     * @return 归档文件集合
     */
    @Override
    public List<HTContractVO> excSearchContractArchiveFileListList(HTContractVO paramVO) {
        try {

            HTContractBean paramBean = new HTContractBean();

            // 合同ID
            paramBean.setContractId(StringToolUtils
                    .convertNullObjectToString(paramVO.getContractId()));

            // 查询归档文件信息列表
            List<HTContractBean> htTaskPeriodMaterialBeanList =
                    htContractDao.searchContractArchiveFileList(paramBean);

            List<HTContractVO> htContractVOList = new ArrayList<>();

            for (HTContractBean htContractBean : htTaskPeriodMaterialBeanList) {

                HTContractVO htContractVO = new HTContractVO();

                // 记录id
                htContractVO.setRelationId(StringToolUtils
                        .convertNullObjectToString(htContractBean.getRelationId()));

                // 文件名称
                htContractVO.setMaterialName(StringToolUtils
                        .convertNullObjectToString(htContractBean.getMaterialName()));

                // 文件地址
                htContractVO.setMaterialPath(StringToolUtils
                        .convertNullObjectToString(htContractBean.getMaterialPath()));

                htContractVOList.add(htContractVO);

            }

            return htContractVOList;

        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }
    /**
     * 查询合同到达哪一步了
     * @param paramVO 合同id
     * @throws SystemException 系统异常
     */
    @Override
    public String excGetHTContractSts(HTContractVO paramVO) throws SystemException {
        try {
            HTContractBean htContractBean = htContractDao.getContractInfoByContractId(paramVO.getContractId());

            //登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();

            // 登录用户id
            String tempUserId = userInfo.getLoginUserId();

            // 流程 -- 合同
            String tempProcessKey = ProcessDictEnum.WriteContract.getProcessDictId();

            HTContractVO contractVO = (HTContractVO) ObjectReflectUtils.convertObj(htContractBean, HTContractVO.class);

            // 项目是否关闭
            contractVO.setProjectIsClosed(htContractBean.getProjectSts());

            // 返回合同状态
            TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(contractVO.getContractId());
            contractVO.setIsInvalidFlag(tbContractEntity.getIsInvalidFlag());
            return contractProcessService.excQueryPageType(userInfo, htContractBean.getDepartmentId(),  tempProcessKey, contractVO, null);

        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     *  撤回
     * @param paramVO 合同id
     */
    @Override
    @Transactional
    public void excRevokeContractInfo(HTContractVO paramVO) throws LogicException,SystemException {
        try {
            //查询合同详情
            HTContractBean htContractBean = htContractDao.getContractInfoByContractId(paramVO.getContractId());

            HTContractVO contractVO = (HTContractVO) ObjectReflectUtils.convertObj(htContractBean, HTContractVO.class);

            //登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();

            // 登录用户id
            String tempUserId = userInfo.getLoginUserId();

            // 流程 -- 合同
            String tempProcessKey = ProcessDictEnum.WriteContract.getProcessDictId();
            //登录人信息
            contractVO.setLoginUserInfo(userInfo);
            //审批人ID
            contractVO.setApproveUserId(userInfo.getLoginUserId());
            //审批人名称
            contractVO.setApproveUserName(userInfo.getUserName());

            contractVO.setIsPass(Constant.NOT_APPROVED);

            // 项目是否关闭
            contractVO.setProjectIsClosed(htContractBean.getProjectSts());

            TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(contractVO.getContractId());
            contractVO.setIsInvalidFlag(tbContractEntity.getIsInvalidFlag());
            // 查询当前节点
            String tempTypeFlgStr = contractProcessService.excQueryPageType(userInfo,htContractBean.getDepartmentId(), ProcessDictEnum.WriteContract.getProcessDictId(), contractVO, null);
                    // 判断当前合同节点是否可以撤回


            // 如果当前节点不是20 撤回节点  证明合同已被审核不能撤回 抛出异常终止操作
            if (!"20".equals(tempTypeFlgStr)){

                // 该合同已被审核，不能撤回！
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0010);
            }
            //启动工作流
            contractProcessService.excRecallTask(contractVO);

        } catch (LogicException lex){
            throw lex;
        }catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     *  查询合同列表信息(供法务使用)
     * @param paramVO 合同编码
     */
    @Override
    public Map<String, Object> excSearchContractList(HTContractVO paramVO) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HTContractBean paramBean = new HTContractBean();
            paramBean.setContractNum(paramVO.getContractNum());
            paramBean.setDepartmentId(paramVO.getDepartmentId());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //合同列表信息
            List<HTContractBean> htContractBeanList =  htContractDao.excSearchLegalAffairContractList(paramBean);
            List<HTContractVO> htContractVOList = new ArrayList<>();
            //格式转换
            for (HTContractBean htContractBean:htContractBeanList){
                HTContractVO htContractVO = new HTContractVO();
                //合同ID
                htContractVO.setContractId(htContractBean.getContractId());
                //合同编码
                htContractVO.setContractNum(htContractBean.getContractNum());

                // 如果合同类型为自定义合同 则取得自定义合同名称和路径
                String tempContractFileNameStr = StringToolUtils.STRING_EMPTY;;
                String tempContractFilePathStr = StringToolUtils.STRING_EMPTY;;
                if ("0".equals(htContractBean.getContractType())){

                    // 自定义合同真实名字
                    tempContractFileNameStr = StringToolUtils.convertNullObjectToString(htContractBean.getContractFileName());

                    // 自定义合同路径
                    tempContractFilePathStr = StringToolUtils.convertNullObjectToString(htContractBean.getContractFilePath());

                    // 如果合同类型为模板合同 则取得模板合同名称和生成路径
                } else if("1".equals(htContractBean.getContractType())){

                    // 合同模板名称
                    tempContractFileNameStr = BusinessUtils.convertContractTemplateTypeToStateStr(NumberToolUtils.
                            convertNullToInteger(htContractBean.getFormworkType()));

                    // 合同模板生成路径
                    tempContractFilePathStr = "/contract/downLoad/downLoadContractWord?contractId="+
                            htContractBean.getContractId()+"&projectNum="+htContractBean.getProjectNum();
                }
                //自定义合同名称
                htContractVO.setContractFileName(tempContractFileNameStr);
                //自定义合同路径
                htContractVO.setContractFilePath(tempContractFilePathStr);
                //法务信息
                htContractVO.setLegalOpinion(htContractBean.getLegalOpinion());
                //部门ID
                htContractVO.setDepartmentId(htContractBean.getDepartmentId());
                //部门名称
                htContractVO.setDepartmentName(htContractBean.getDepartmentName());
                //合同类型
                htContractVO.setContractType(htContractBean.getContractType());
                htContractVOList.add(htContractVO);
            }
            Integer count =  htContractDao.excSearchLegalAffairContractCount(paramBean);
            resultMap.put(Constant.DATA_LIST_KEY, htContractVOList);
            resultMap.put(Constant.DATA_COUNT_KEY, count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     *  添加法务信息(供法务使用)
     * @param paramVO 合同编码
     */
    @Override
    @Transactional
    public void excInsertLegalOpinion(HTContractVO paramVO) throws LogicException, SystemException {
        try {
            TbContractEntity tbContractEntity = new TbContractEntity();
            //合同ID
            tbContractEntity.setContractId(paramVO.getContractId());
            //法务建议
            tbContractEntity.setLegalOpinion(paramVO.getLegalOpinion());
            //修改法务信息
            tbContractEntityMapper.updateByPrimaryKeySelective(tbContractEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     *  查询合同对应的法务信息
     * @param paramVO 合同id
     * @return 合同信息
     */
    @Override
    public HTContractVO excSearchLegalOpinionInfoById(HTContractVO paramVO)  throws LogicException, SystemException {
        try {
            //查询合同详情
            HTContractBean htContractBean = htContractDao.getContractInfoByContractId(paramVO.getContractId());
            /*格式转换*/
            HTContractVO htContractVO = new HTContractVO();
            //合同ID
            htContractVO.setContractId(htContractBean.getContractId());
            //法务信息
            htContractVO.setLegalOpinion(htContractBean.getLegalOpinion());
            return htContractVO;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 依据合同id 查询并生成 模板合同文件
     *
     * @param paramVO 合同信息
     * @param paramOS 输出流
     * @throws LogicException
     * @throws SystemException
     */
    @Override
    public void writeContractWordStreamById(HTContractVO paramVO, ByteArrayOutputStream paramOS) throws LogicException, SystemException {

        try {
            // 查询合同基本信息、合同模板是哪个、并把信息放到map中
            Map<String, Object> resultContractMap = this.getContractDetailInfoByContractId(paramVO);

            // 取得模板名称
            String tempContractTemplateNameStr = (String) resultContractMap.get("contractTemplateName");

            InputStream tempInputStream = new ClassPathResource("/contract/" + tempContractTemplateNameStr).getInputStream();

            WordTemplate wordTemplate = new WordTemplate(tempInputStream);

            // 替换合同中的变量
            wordTemplate.replaceTag(resultContractMap);

            //数据写入到输出流
            wordTemplate.write(paramOS);

            tempInputStream.close();

        }catch (Exception ex){
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 批量插入预计收款时间
     */
    public void excInsertContractPredictRequestRelations(HTContractVO paramVO) {

        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        // 临时变量：当前登录用户id
        String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(
                paramVO.getLoginUserInfo().getLoginUserId());

        // 获取预计收款时间列表
        List<HTContractVO> htPredictRequestDateList = paramVO.getPredictRequestDateList();

        // 集合
        List<HTContractBean> htContractBeanList = new ArrayList<>();

        // Bean 转化成 VO
        if(!ListToolUtils.isEmpty(htPredictRequestDateList))
            for (HTContractVO htContractVO : htPredictRequestDateList) {

                HTContractBean htContractBean = new HTContractBean();

                // 设置预计收款id
                htContractBean.setPredictRequestId(StringToolUtils
                        .convertNullObjectToString(UUID.randomUUID().toString()));

                // 设置合同id
                htContractBean.setContractId(StringToolUtils
                        .convertNullObjectToString(paramVO.getContractId()));

                // 设置收款时间
                htContractBean.setStartDate(DateToolUtils.convertDateFormat(
                        htContractVO.getStartDate(), DateToolUtils.yyyy_MM_dd));

                // 设置收款金额
                htContractBean.setReceivedAmount(NumberToolUtils
                        .convertNullToDouble(htContractVO.getReceivedAmount()));

                // 是否暂定
                htContractBean.setIsProvisional(StringToolUtils
                        .convertNullObjectToString(htContractVO.getIsProvisional()));

                // 是否删除
                htContractBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

                // 设置创建时间
                htContractBean.setCreateDate(tempCurrentDate);

                // 设置修改时间
                htContractBean.setUpdateDate(tempCurrentDate);

                // 设置创建人id
                htContractBean.setCreateUserId(tempLoginUserIdStr);

                // 设置修改人id
                htContractBean.setUpdateUserId(tempLoginUserIdStr);

                htContractBeanList.add(htContractBean);

            }

        // 批量插入预计收款时间
        htContractDao.excInsertContractPredictRequestRelations(htContractBeanList);

    }

    /**
     * 批量插入合同和项目类别指标
     */
    public void excInsertContractProjectTypeRelations(HTContractVO paramVO) {

        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        // 临时变量：当前登录用户id
        String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(
                paramVO.getLoginUserInfo().getLoginUserId());

        // 获取项目类型列表
        List<HTContractVO> htProjectTypeList = paramVO.getProjectTypeList();

        // 集合
        List<HTContractBean> htContractBeanList = new ArrayList<>();

        // Bean 转化成 VO
        if(!ListToolUtils.isEmpty(htProjectTypeList))
            for (HTContractVO htContractVO : htProjectTypeList) {

                // 临时变量：项目类型
                String tempProjectTypeStr = StringToolUtils
                        .convertNullObjectToString(htContractVO.getProjectType());

                HTContractBean htContractBean = new HTContractBean();

                // 设置合同id
                htContractBean.setContractId(StringToolUtils
                        .convertNullObjectToString(paramVO.getContractId()));

                // 设置项目类型
                htContractBean.setProjectType(tempProjectTypeStr);

                // 是否删除
                htContractBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

                // 设置创建时间
                htContractBean.setCreateDate(tempCurrentDate);

                // 设置修改时间
                htContractBean.setUpdateDate(tempCurrentDate);

                // 设置创建人id
                htContractBean.setCreateUserId(tempLoginUserIdStr);

                // 设置修改人id
                htContractBean.setUpdateUserId(tempLoginUserIdStr);

                // 获取项目特征列表
                List<HTContractVO> projectFeatureList = htContractVO.getProjectFeatureList();

                if(!ListToolUtils.isEmpty(projectFeatureList))
                    for (HTContractVO projectFeatureVO : projectFeatureList) {

                        Map<String,Object> projectMap = new HashMap<>();

                        // 占地面积
                        String tempLandAreaStr = null;
                        // 建筑面积
                        String tempFloorAreaStr = null;
                        // 檐高
                        String tempEavesHeightStr = null;
                        // 建筑控制高度
                        String tempBuildingControlHeightStr = null;
                        // 其他
                        String tempOthersStr = null;

                        switch (tempProjectTypeStr){
                            // 住宅
                            case Constant.PROJECT_TYPE_ZERO:

                                // 占地面积
                                tempLandAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getLandArea());
                                // 建筑面积
                                tempFloorAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getFloorArea());
                                // 檐高
                                tempEavesHeightStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getEavesHeight());
                                // 建筑控制高度
                                tempBuildingControlHeightStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getBuildingControlHeight());
                                // 容积率
                                String tempVolumeRatioStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getVolumeRatio());
                                // 住宅类别
                                String tempHomeTypeStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getHomeType());

                                // 占地面积
                                projectMap.put("landArea",tempLandAreaStr);
                                // 建筑面积
                                projectMap.put("floorArea",tempFloorAreaStr);
                                // 檐高
                                projectMap.put("eavesHeight",tempEavesHeightStr);
                                // 建筑控制高度
                                projectMap.put("buildingControlHeight",tempBuildingControlHeightStr);
                                // 容积率
                                projectMap.put("volumeRatio",tempVolumeRatioStr);
                                // 住宅类别
                                projectMap.put("homeType",tempHomeTypeStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            // 商业建筑
                            case Constant.PROJECT_TYPE_ONE:

                                // 占地面积
                                tempLandAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getLandArea());
                                // 建筑面积
                                tempFloorAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getFloorArea());
                                // 檐高
                                tempEavesHeightStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getEavesHeight());
                                // 建筑控制高度
                                tempBuildingControlHeightStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getBuildingControlHeight());

                                // 占地面积
                                projectMap.put("landArea",tempLandAreaStr);
                                // 建筑面积
                                projectMap.put("floorArea",tempFloorAreaStr);
                                // 檐高
                                projectMap.put("eavesHeight",tempEavesHeightStr);
                                // 建筑控制高度
                                projectMap.put("buildingControlHeight",tempBuildingControlHeightStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            // 酒店及休闲娱乐
                            case Constant.PROJECT_TYPE_TWO:

                                // 占地面积
                                tempLandAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getLandArea());
                                // 建筑面积
                                tempFloorAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getFloorArea());
                                // 檐高
                                tempEavesHeightStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getEavesHeight());
                                // 建筑控制高度
                                tempBuildingControlHeightStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getBuildingControlHeight());
                                // 房间数量
                                String tempRoomNumStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getRoomNum());

                                // 占地面积
                                projectMap.put("landArea",tempLandAreaStr);
                                // 建筑面积
                                projectMap.put("floorArea",tempFloorAreaStr);
                                // 檐高
                                projectMap.put("eavesHeight",tempEavesHeightStr);
                                // 建筑控制高度
                                projectMap.put("buildingControlHeight",tempBuildingControlHeightStr);
                                // 房间数量
                                projectMap.put("roomNum",tempRoomNumStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            // 工业建筑
                            case Constant.PROJECT_TYPE_THREE:

                                // 占地面积
                                tempLandAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getLandArea());
                                // 建筑面积
                                tempFloorAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getFloorArea());
                                // 檐高
                                tempEavesHeightStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getEavesHeight());
                                // 层数
                                String tempLevelStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getLevel());
                                // 跨度
                                String tempSpanStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getSpan());
                                // 生产状况
                                String tempProduceConditionStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getProduceCondition());

                                // 占地面积
                                projectMap.put("landArea",tempLandAreaStr);
                                // 建筑面积
                                projectMap.put("floorArea",tempFloorAreaStr);
                                // 檐高
                                projectMap.put("eavesHeight",tempEavesHeightStr);
                                // 层数
                                projectMap.put("level",tempLevelStr);
                                // 跨度
                                projectMap.put("span",tempSpanStr);
                                // 生产状况
                                projectMap.put("produceCondition",tempProduceConditionStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            // 教育
                            case Constant.PROJECT_TYPE_FOUR:

                                // 占地面积
                                tempLandAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getLandArea());
                                // 建筑面积
                                tempFloorAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getFloorArea());
                                // 绿化率
                                String tempMakeGreenRatioStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getMakeGreenRatio());
                                // 绿化等级
                                String tempGreenLevelStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getGreenLevel());

                                // 占地面积
                                projectMap.put("landArea",tempLandAreaStr);
                                // 建筑面积
                                projectMap.put("floorArea",tempFloorAreaStr);
                                // 绿化率
                                projectMap.put("makeGreenRatio",tempMakeGreenRatioStr);
                                // 绿化等级
                                projectMap.put("greenLevel",tempGreenLevelStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            // 文化及体育设施
                            case Constant.PROJECT_TYPE_FIVE:

                                // 占地面积
                                tempLandAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getLandArea());
                                // 建筑面积
                                tempFloorAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getFloorArea());
                                // 绿化率
                                String tempPersionCapacityStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getPersionCapacity());

                                // 占地面积
                                projectMap.put("landArea",tempLandAreaStr);
                                // 建筑面积
                                projectMap.put("floorArea",tempFloorAreaStr);
                                // 绿化率
                                projectMap.put("persionCapacity",tempPersionCapacityStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            // 电力
                            case Constant.PROJECT_TYPE_SIX:

                                // 占地面积
                                tempLandAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getLandArea());
                                // 建筑面积
                                tempFloorAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getFloorArea());
                                // 装机容量
                                String tempInstallationCapacityStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getInstallationCapacity());
                                // 发电机变压器参数数量
                                String tempTransformersNumStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getTransformersNum());
                                // 线缆敷设长度
                                String tempCableLayingLengthStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getCableLayingLength());
                                // 线缆敷设方式
                                String tempCableLayingMethodStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getCableLayingMethod());
                                // 输电量
                                String tempTransmissionCapacityStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getTransmissionCapacity());

                                // 占地面积
                                projectMap.put("landArea",tempLandAreaStr);
                                // 建筑面积
                                projectMap.put("floorArea",tempFloorAreaStr);
                                // 装机容量
                                projectMap.put("installationCapacity",tempInstallationCapacityStr);
                                // 发电机变压器参数数量
                                projectMap.put("transformersNum",tempTransformersNumStr);
                                // 线缆敷设长度
                                projectMap.put("cableLayingLength",tempCableLayingLengthStr);
                                // 线缆敷设方式
                                projectMap.put("cableLayingMethod",tempCableLayingMethodStr);
                                // 输电量
                                projectMap.put("transmissionCapacity",tempTransmissionCapacityStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            // 铁路、道路及桥梁建设
                            case Constant.PROJECT_TYPE_SEVEN:

                                // 等级
                                String tempGradeStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getGrade());
                                // 全线（桥）长度
                                String tempBridgeLengthStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getBridgeLength());
                                // 设计速度
                                String tempDesignSpeedStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getDesignSpeed());
                                // 轨道类型
                                String tempOrbitalTypeStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getOrbitalType());
                                // 桥梁类型
                                String tempBridgeTypeStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getBridgeType());

                                // 等级
                                projectMap.put("grade",tempGradeStr);
                                // 全线（桥）长度
                                projectMap.put("bridgeLength",tempBridgeLengthStr);
                                // 设计速度
                                projectMap.put("designSpeed",tempDesignSpeedStr);
                                // 轨道类型
                                projectMap.put("orbitalType",tempOrbitalTypeStr);
                                // 桥梁类型
                                projectMap.put("bridgeType",tempBridgeTypeStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            // 水务
                            case Constant.PROJECT_TYPE_EIGHT:

                                // 正常运用水位
                                String tempNormalOperatingWaterLevelStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getNormalOperatingWaterLevel());
                                // 总库容
                                String tempTotalStorageCapacityStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getTotalStorageCapacity());
                                // 最大坝高
                                String tempMaximumDamHeightStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getMaximumDamHeight());
                                // 电站装机容量
                                String tempInstalledCapacityOfPowerStationStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getInstalledCapacityOfPowerStation());

                                // 正常运用水位
                                projectMap.put("normalOperatingWaterLevel",tempNormalOperatingWaterLevelStr);
                                // 总库容
                                projectMap.put("totalStorageCapacity",tempTotalStorageCapacityStr);
                                // 最大坝高
                                projectMap.put("maximumDamHeight",tempMaximumDamHeightStr);
                                // 电站装机容量
                                projectMap.put("installedCapacityOfPowerStation",tempInstalledCapacityOfPowerStationStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            // 公用事业及城市基础设施
                            case Constant.PROJECT_TYPE_NINE:

                                // 占地面积
                                tempLandAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getLandArea());
                                // 建筑面积
                                tempFloorAreaStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getFloorArea());
                                // 其他（长度/规模/范围等）
                                tempOthersStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getOthers());

                                // 占地面积
                                projectMap.put("landArea",tempLandAreaStr);
                                // 建筑面积
                                projectMap.put("floorArea",tempFloorAreaStr);
                                // 其他（长度/规模/范围等）
                                projectMap.put("others",tempOthersStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            // 水运、交通
                            case Constant.PROJECT_TYPE_TEN:

                                // 港口（码头）吞吐量
                                String tempPortThroughputStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getPortThroughput());
                                // 航道（闸机）通过量
                                String tempChannelThroughputStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getChannelThroughput());
                                // 主要货物的流量流向
                                String tempFlowDirectionStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getFlowDirection());
                                // 分航线运量
                                String tempBranchLineVolumeStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getBranchLineVolume());
                                // 船舶流量
                                String tempShipFlowStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getShipFlow());
                                // 集疏方式
                                String tempGatheringAndDistributingWaysStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getGatheringAndDistributingWays());

                                // 港口（码头）吞吐量
                                projectMap.put("portThroughput",tempPortThroughputStr);
                                // 航道（闸机）通过量
                                projectMap.put("channelThroughput",tempChannelThroughputStr);
                                // 主要货物的流量流向
                                projectMap.put("flowDirection",tempFlowDirectionStr);
                                // 分航线运量
                                projectMap.put("branchLineVolume",tempBranchLineVolumeStr);
                                // 船舶流量
                                projectMap.put("shipFlow",tempShipFlowStr);
                                // 集疏方式
                                projectMap.put("gatheringAndDistributingWays",tempGatheringAndDistributingWaysStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            // 其他
                            case Constant.PROJECT_TYPE_ELEVEN:

                                // 其他
                                tempOthersStr = StringToolUtils
                                        .convertNullObjectToString(projectFeatureVO.getOthers());

                                // 其他
                                projectMap.put("others",tempOthersStr);

                                // 设置其他属性
                                htContractBean.setProjectTarget(JSON.toJSONString(projectMap));

                                break;

                            default:
                                break;
                        }
                    }

                htContractBeanList.add(htContractBean);

            }

        // 批量插入合同和项目类别指标列表数据
        htContractDao.excInsertContractProjectTypeRelations(htContractBeanList);

    }

    /**
     * 查询合同审核意见信息
     *
     * @return 审核合同意见信息
     */
    @Override
    public List<HTContractTaskHisTaskInstVO> excSearchHTContractTaskHisTaskInst(HTContractTaskHisTaskInstVO paramVO) throws LogicException, SystemException {

        try {
            HTContractTaskHisTaskInstBean paramBean = new HTContractTaskHisTaskInstBean();
            paramBean.setContractTaskId(paramVO.getContractTaskId());
            //查询审核意见信息
            List<HTContractTaskHisTaskInstBean> htContractTaskHisTaskInstBeanList = htContractDao.excSearchHTContractTaskHisTaskInstInfoList(paramBean);
            List<HTContractTaskHisTaskInstVO> resultList = new ArrayList<>();
            /*格式转换*/
            for (HTContractTaskHisTaskInstBean htContractTaskHisTaskInstBean : htContractTaskHisTaskInstBeanList) {

                HTContractTaskHisTaskInstVO htContractTaskHisTaskInstVO = new HTContractTaskHisTaskInstVO();
                // 任务Key
                htContractTaskHisTaskInstVO.setTaskKey(htContractTaskHisTaskInstBean.getTaskKey());
                // 唯一标识
                htContractTaskHisTaskInstVO.setHistoryId(htContractTaskHisTaskInstBean.getHistoryId());
                // 合同任务ID
                htContractTaskHisTaskInstVO.setContractTaskId(htContractTaskHisTaskInstBean.getContractTaskId());
                // 审批人
                htContractTaskHisTaskInstVO.setApproveUserId(htContractTaskHisTaskInstBean.getApproveUserId());
                // 审批人名称
                htContractTaskHisTaskInstVO.setApproveUserName(htContractTaskHisTaskInstBean.getApproveUserName());
                List<HTContractTaskHisTaskInstBean> childHisTaskInstBeanList = htContractTaskHisTaskInstBean.getHTContractTaskHisTaskInstBeanList();
                List<HTContractTaskHisTaskInstVO> childHisTaskInstVOList = new ArrayList<>();
                for (HTContractTaskHisTaskInstBean childHisTaskInstBean : childHisTaskInstBeanList) {
                    HTContractTaskHisTaskInstVO childHisTaskInstVO = new HTContractTaskHisTaskInstVO();
                    // 任务Key
                    childHisTaskInstVO.setTaskKey(childHisTaskInstBean.getTaskKey());
                    // 唯一标识
                    childHisTaskInstVO.setHistoryId(childHisTaskInstBean.getHistoryId());
                    // 合同任务ID
                    childHisTaskInstVO.setContractTaskId(childHisTaskInstBean.getContractTaskId());
                    // 拒绝原因
                    childHisTaskInstVO.setRejectReason(childHisTaskInstBean.getRejectReason());
                    // 审批人
                    childHisTaskInstVO.setApproveUserId(childHisTaskInstBean.getApproveUserId());
                    // 审批人名称
                    childHisTaskInstVO.setApproveUserName(childHisTaskInstBean.getApproveUserName());
                    // 审批时间
                    childHisTaskInstVO.setApproveTime(DateToolUtils.convertDateFormat(childHisTaskInstBean.getApproveTime(), DateToolUtils.yyyy_MM_dd));
                    // 审批人名称
                    childHisTaskInstVO.setIsPass(childHisTaskInstBean.getIsPass());
                    // 成员问题关联数据
                    List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList = childHisTaskInstBean.getHtUserQuestionRecordRelationBeanList();
                    List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList = new ArrayList<>();
                    for (HTUserQuestionRecordRelationBean htUserQuestionRecordRelationBean : htUserQuestionRecordRelationBeanList) {
                        HTUserQuestionRecordRelationVO htUserQuestionRecordRelationVO = new HTUserQuestionRecordRelationVO();
                        // 唯一标识
                        htUserQuestionRecordRelationVO.setRelationId(htUserQuestionRecordRelationBean.getRelationId());
                        // 审核记录ID
                        htUserQuestionRecordRelationVO.setRecordId(htUserQuestionRecordRelationBean.getRecordId());
                        // 问题类型ID
                        htUserQuestionRecordRelationVO.setQuestionId(htUserQuestionRecordRelationBean.getQuestionId());
                        // 问题名称
                        htUserQuestionRecordRelationVO.setQuestionName(htUserQuestionRecordRelationBean.getQuestionName());
                        // 错误次数
                        htUserQuestionRecordRelationVO.setQuestionNum(htUserQuestionRecordRelationBean.getQuestionNum());
                        // 来源类型（0：合同 1：普通任务 2：投标任务 ）
                        htUserQuestionRecordRelationVO.setSourceType(htUserQuestionRecordRelationBean.getSourceType());
                        // 人员ID
                        htUserQuestionRecordRelationVO.setUserId(htUserQuestionRecordRelationBean.getUserId());
                        // 人员名称
                        htUserQuestionRecordRelationVO.setUserName(htUserQuestionRecordRelationBean.getUserName());
                        htUserQuestionRecordRelationVOList.add(htUserQuestionRecordRelationVO);
                    }
                    childHisTaskInstVO.setHtUserQuestionRecordRelationVOList(htUserQuestionRecordRelationVOList);
                    childHisTaskInstVOList.add(childHisTaskInstVO);
                }
                htContractTaskHisTaskInstVO.setHTContractTaskHisTaskInstVOList(childHisTaskInstVOList);
                resultList.add(htContractTaskHisTaskInstVO);
            }
            return resultList;
        } catch (LogicException lex) {
            throw lex;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 将字符串转换为数据（字符串分割格式："@0@,@1@,@2@"）
     */
    private List<String> convertSplitStringToList(String paramStr){
        List<String> retList = new ArrayList<>();
        String[] tempSplitList = StringToolUtils.convertNullObjectToString(paramStr).replace("@","").split(",");
        for(String tempSplitItem :tempSplitList){
            retList.add(tempSplitItem);
        }
        return retList;
    }


    /**
     * 合同作废
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excInvalidContract(HTContractVO paramVO) throws LogicException, SystemException  {
        Date currentDate = new Date();
        try {

            /*------------------------------校验 start--------------------------------------*/
            TbContractEntity contractEntity = tbContractEntityMapper.selectByPrimaryKey(paramVO.getContractId());
            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(contractEntity.getProjectId());

            // =====================================判断项目是否结束 end=========================================
            HTContractBean paramBean = new HTContractBean();
            paramBean.setCooperateContract(contractEntity.getCooperateContract());
            //通过合作合同字段查询信息
            List<HTContractBean> htContractBeanList = htContractDao.excSearchContractListByProperty(paramBean);
            paramBean = new HTContractBean();
            String messageFlag = null;
            //判断是不是跨部门合作合同
            if (htContractBeanList.size()==NumberToolUtils.DEFAULT_INT_ONE){
                //合同已通过签发阶段，不能作废！
                messageFlag = MessageConstant.MSG_ERROR_PROJECT_0013;
            }else {
                //当前合作合同已有部门通过签发阶段，不能作废！
                messageFlag = MessageConstant.MSG_ERROR_PROJECT_0014;
            }
            //校验是否合同已通过签发
            for (HTContractBean htContractBean:
                 htContractBeanList) {
                //判断合同是否已通过签发阶段
                if (NumberToolUtils.convertNullToInteger(htContractBean.getCurrentState())>NumberToolUtils.DEFAULT_INT_FIVE&&!StringToolUtils.isNull(htContractBean.getApplyUserId())){
                    throw new LogicException(messageFlag);
                }
            }
            /*------------------------------校验 end--------------------------------------*/
            /*--------------------------------作废合同Start------------------------------------------*/


            List<String> businessIds = new ArrayList<>();
            List<String> contractIds = new ArrayList<>();
            //批量作废
            for (HTContractBean htContractBean:
                    htContractBeanList) {

                TbContractEntity paramEntity = new TbContractEntity();
                //项目ID
                paramEntity.setContractId(StringToolUtils.convertNullObjectToString(htContractBean.getContractId()));
                //作废标示
                paramEntity.setIsInvalidFlag(StringToolUtils.convertNullObjectToString(Constant.IS_INVALID));
                //作废理由
                paramEntity.setInvalidReason(StringToolUtils.convertNullObjectToString(paramVO.getInvalidReason()));
                //修改时间
                paramEntity.setUpdateDate(currentDate);
                //修改人ID
                paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                //修改合同(作废合同)
                tbContractEntityMapper.updateByPrimaryKeySelective(paramEntity);
                //需要删除的删除人员扣分业务主键集合
                contractIds.add(htContractBean.getContractId());
            }
            //任务ID结果集(摘出来是为了不使用3重for循环)
            List<String> taskIds = new ArrayList<>();
            for(String contractId:contractIds){
                //通过合同查询所有任务
                taskIds.addAll(htTaskDao.excSearchAllTaskIdByContractId(contractId));
                //通过合同ID查询请款单
                List<String> RequestMenuIds = htRequestMenuDao.excSearchRequestMenuIdByContractId(contractId);
                businessIds.addAll(RequestMenuIds);
            }
            businessIds.addAll(taskIds);
            for (String taskId:taskIds){
                TbTaskPeriodRelationEntity paramEntity = new TbTaskPeriodRelationEntity();
                paramEntity.setTaskId(taskId);
                //通过任务查询所有任务A3阶段
                List<TbTaskPeriodRelationEntity> tbTaskPeriodRelationEntities = htTaskDao.excSearchAllA3PeriodInfoByTaskId(paramEntity);
                for (TbTaskPeriodRelationEntity tbTaskPeriodRelationEntity:
                        tbTaskPeriodRelationEntities) {
                    businessIds.add(tbTaskPeriodRelationEntity.getRelationId());
                }
            }
            businessIds.addAll(contractIds);
            //批量删除人员扣分信息
            delUserQuestionRecord(businessIds);
            /*置空释放空间*/
            businessIds = null;
            taskIds = null;
            /*--------------------------------作废合同End------------------------------------------*/
            paramBean.setProjectId(contractEntity.getProjectId());
            paramBean.setContractId(contractEntity.getContractId());
            paramBean.setContractIdList(contractIds);
            //查询项目下合同情况
            List<HTContractBean> htContractBeans = htContractDao.excSearchContractListByProperty(paramBean);
            //是否作废项目校验标识
            boolean invalidProjectFlag = true;
            //查看项目下合同情况
            for (HTContractBean htContractBean:
            htContractBeans) {
                //是否有未作废合同
                if (!StringToolUtils.STRING_ONE.equals(htContractBean.getIsInvalidFlag())){
                    //将作废项目校验标识置false(不执行项目作废操作)
                    invalidProjectFlag = false;
                    //跳出
                    break;
                }
            }
            //判断是否需要执行作废项目操作
            if (invalidProjectFlag){
                /*--------------------------------作废合同Start------------------------------------------*/
                TbProjectEntity projectEntity = new TbProjectEntity();
                //项目ID
                projectEntity.setProjectId(StringToolUtils.convertNullObjectToString(contractEntity.getProjectId()));
                //作废标示
                projectEntity.setIsInvalidFlag(StringToolUtils.convertNullObjectToString(Constant.IS_INVALID));
                //修改时间
                projectEntity.setUpdateDate(currentDate);
                //修改人ID
                projectEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                //修改项目(作废项目)
                tbProjectEntityMapper.updateByPrimaryKeySelective(projectEntity);
                /*--------------------------------作废合同End------------------------------------------*/
            }

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 批量删除人员扣分信息
     * @param businessIds 业务主键
     * @throws LogicException
     * @throws SystemException
     */
    private void delUserQuestionRecord(List<String> businessIds) {
        if (null!=businessIds&&businessIds.size()>0){
            //批量删除人员扣分信息
            htUserQuestionRecordRelationDao.deleteUserQuestionRecord(businessIds);
        }
    }
}