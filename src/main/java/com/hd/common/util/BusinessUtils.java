package com.hd.common.util;


import com.hd.base.bean.BaseBean;
import com.hd.base.exception.LogicException;
import com.hd.base.vo.BaseVO;


import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务工具类
 *
 * @author JLF
 */
public final class BusinessUtils {

    /**
     * 私有构造函数
     */
    private BusinessUtils() {
    }

    /**
     * 判断时间戳字符串paramTimeMillisStr 是否于Date型参数paramDate转化得到的时间戳相等，如果不相等，抛出异常
     *
     * @param paramTimeMillisStr 时间戳字符串
     * @param paramDate          日期
     * @throws LogicException MSG_ERROR_COMMON_0005 数据已被修改，请确认后重新提交！
     */
    public static void checkDataIsNotBeModifyByUpdateDate(String paramTimeMillisStr
            , Date paramDate) throws LogicException {

        // 临时变量：通过paramDate得到的时间戳字符串
        String tmpTimeMillisFromDate = DateToolUtils.convertDateToTimeMillisStr(paramDate);
        // 临时变量：通过paramTimeMillisStr得到的时间戳字符串
        String tmpTimeMillisFromStr = StringToolUtils
                .convertNullObjectToString(paramTimeMillisStr, StringToolUtils.STRING_ZERO);

        // 如果两个时间戳值不等，抛出异常，终止操作
        if (!tmpTimeMillisFromDate.equals(tmpTimeMillisFromStr)) {
            // MSG_ERROR_COMMON_0005 数据已被修改，请确认后重新提交！
            throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
        }
    }

    /**
     * 通过VO 中的 pageNum 、pageSize 计算得到 分页查询需要的 开始、结束RowNum
     *
     * @param paramVO   参数VO
     * @param paramBean 参数Bean
     */
    public static <VO_TYPE extends BaseVO, BEAN_TYPE extends BaseBean> BEAN_TYPE computeAndPutRowNumFromVOToBean(
            VO_TYPE paramVO, BEAN_TYPE paramBean) {

        // 临时变量：页码
        Integer tmpPageNumberInt = NumberToolUtils.convertNullToInteger(paramVO.getPageNumber());
        // 临时变量：单页数据条数
        Integer tmpPageSizeInt = NumberToolUtils.convertNullToInteger(paramVO.getPageSize());

        // 设置当前页开始rownum编号
        paramBean.setPageNumber((tmpPageNumberInt - 1) * tmpPageSizeInt);
        // 设置当前页结束rownum编号
        paramBean.setPageSize(tmpPageSizeInt);

        return paramBean;
    }

    /**
     * 整形人民币 分 转为 元 字符串（保留两位）
     *
     * @param paramCentPrice 参数 单位为分的金额
     * @return 单位为元的金额字符串
     */
    public static String convertCentToYuanPriceStr(Long paramCentPrice) {

        // 结果金额字符串（单位为分）
        String resultYuanPriceStr;

        // 分为单位的BigDecimal 型金额
        BigDecimal tmpCentPriceBigDecimal = NumberToolUtils.convertNullToBigDecimal(paramCentPrice);

        // 元为单位的BigDecimal 型金额(两位小数，四舍五入)
        BigDecimal tmpYuanPriceBigDecimal = tmpCentPriceBigDecimal
                .divide(
                        NumberToolUtils.convertNullToBigDecimal(NumberToolUtils.DOUBLE_HUNDRED),
                        NumberToolUtils.INT_SCALE_FLOAT,
                        BigDecimal.ROUND_HALF_UP
                );

        // 结果 金额字符串（单位为元）
        resultYuanPriceStr = NumberToolUtils.convertNumberToStringWithHalfUp(
                tmpYuanPriceBigDecimal,
                NumberToolUtils.INT_SCALE_FLOAT
        );

        return resultYuanPriceStr;
    }


    /**
     * 数据逻辑删除flg状态转换 0-->启用 | 1-->停用 | 其他-->''
     *
     * @param isDelFlg
     * @return
     */
    public static String convertDelFlgToStateStr(String isDelFlg) {

        String retStr = "";
        if ("0".equals(isDelFlg)) {
            retStr = "启用";
        } else if ("1".equals(isDelFlg)) {
            retStr = "停用";
        } else {
            retStr = "";
        }

        return retStr;
    }

    /**
     * 工作内容类型 转换文字
     *
     * @param workContentTypeInt 工作内容类型
     * @return 工作内容类型文字
     */
    public static String convertWorkContentTypeToStateStr(Integer workContentTypeInt) {

        String tempWorkContentTypeValueStr = "";
        switch (workContentTypeInt) {
            // 编制
            case 0:
                tempWorkContentTypeValueStr = "编制";
                break;
            // 审核
            case 1:
                tempWorkContentTypeValueStr = "审核";
                break;
            // 调整
            case 2:
                tempWorkContentTypeValueStr = "调整";
                break;
            default:
                break;
        }

        return tempWorkContentTypeValueStr;
    }


    /**
     * 合同模板类型 转换文字
     *
     * @param contractTemplateInt 合同模板类型
     * @return 合同模板类型文字
     */
    public static String convertContractTemplateTypeToStateStr(Integer contractTemplateInt) {

        String tempContractTemplateTypeValueStr = "";
        switch (contractTemplateInt) {
            // 造价合同
            case 0:
                tempContractTemplateTypeValueStr = "2019HT造价咨询合同.docx";
                break;
            // 招标代理合同
            case 1:
                tempContractTemplateTypeValueStr = "2019HT招标代理合同.docx";
                break;
            // 工程管理咨询服务
            case 2:
                tempContractTemplateTypeValueStr = "2019HT工程管理咨询服务合同.docx";
                break;
            // 监理合同
            case 3:
                tempContractTemplateTypeValueStr = "2019HT监理合同.docx";
                break;
            default:
                break;
        }

        return tempContractTemplateTypeValueStr;
    }


    /**
     * 咨询业务类型 转换文字
     *
     * @param counselBusinessType 咨询业务类型
     * @return 咨询业务类型文字
     */
    public static String convertCounselBusinessTypeToStateStr(Integer counselBusinessType) {

        String tempCounselBusinessTypeValueStr = "";
        switch (counselBusinessType) {
            // 造价咨询
            case 0:
                tempCounselBusinessTypeValueStr = "造价咨询";
                break;
            // 招标代理
            case 1:
                tempCounselBusinessTypeValueStr = "招标代理";
                break;
            // 项目管理(监理)
            case 2:
                tempCounselBusinessTypeValueStr = "项目管理(监理)";
                break;
            default:
                break;
        }

        return tempCounselBusinessTypeValueStr;
    }


    /**
     * 成果文件模板类型 转换文字
     *
     * @param taskTemplateInt 合同模板类型
     * @return 成果文件模板类型文字
     */
    public static String convertTaskTemplateTypeToStateStr(Integer taskTemplateInt) {

        String tempTaskTemplateTypeValueStr = "";
        switch (taskTemplateInt) {
            // 2019HT概算编制.docx
            case 0:
                tempTaskTemplateTypeValueStr = "2019HT概算编制.docx";
                break;
            // 2019HT估算编制.docx
            case 1:
                tempTaskTemplateTypeValueStr = "2019HT估算编制.docx";
                break;
            // 2019HT结算编制.docx
            case 2:
                tempTaskTemplateTypeValueStr = "2019HT结算编制.docx";
                break;
            // 2019HT决算编制.docx
            case 3:
                tempTaskTemplateTypeValueStr = "2019HT决算编制.docx";
                break;
            // 2019HT清标报告编制.docx
            case 4:
                tempTaskTemplateTypeValueStr = "2019HT清标报告编制.docx";
                break;
            // 2019HT招标控制价.docx
            case 5:
                tempTaskTemplateTypeValueStr = "2019HT招标控制价.docx";
                break;
            // 2019HT招标清单编制（分包 承包）.docx
            case 6:
                tempTaskTemplateTypeValueStr = "2019HT招标清单编制（分包 承包）.docx";
                break;
            // 2019HT招标清单编制（总包）.docx
            case 7:
                tempTaskTemplateTypeValueStr = "2019HT招标清单编制（总包）.docx";
                break;
            // 2019HT预算编制.docx
            case 8:
                tempTaskTemplateTypeValueStr = "2019HT预算编制.docx";
                break;
            // 2019HT概算审核报告.docx
            case 9:
                tempTaskTemplateTypeValueStr = "2019HT概算审核报告.docx";
                break;
            // 2019HT估算审核报告.docx
            case 10:
                tempTaskTemplateTypeValueStr = "2019HT估算审核报告.docx";
                break;
            // 2019HT结算审核报告.docx
            case 11:
                tempTaskTemplateTypeValueStr = "2019HT结算审核报告.docx";
                break;
            // 2019HT预算审核报告.docx
            case 12:
                tempTaskTemplateTypeValueStr = "2019HT预算审核报告.docx";
                break;
            // 2019HT合同审核意见 - 未经过招标.docx
            case 13:
                tempTaskTemplateTypeValueStr = "2019HT合同审核意见 - 未经过招标.docx";
                break;
            // 2019HT合同审核意见 - 招标.docx
            case 14:
                tempTaskTemplateTypeValueStr = "2019HT合同审核意见 - 招标.docx";
                break;
            // 2019HT进度款审核意见.docx
            case 15:
                tempTaskTemplateTypeValueStr = "2019HT进度款审核意见.docx";
                break;
            // 2019HT变更审核意见.docx
            case 16:
                tempTaskTemplateTypeValueStr = "2019HT变更审核意见.docx";
                break;
            // 2019HT预算审核意见.docx
            case 17:
                tempTaskTemplateTypeValueStr = "2019HT预算审核意见.docx";
                break;
            // 2019HT招标代理工作报告.docx
            case 18:
                tempTaskTemplateTypeValueStr = "2019HT招标代理工作报告.docx";
                break;
            // 2019HT评标书面报告（公开）.docx
            case 19:
                tempTaskTemplateTypeValueStr = "2019HT评标书面报告（公开）.docx";
                break;
            default:
                break;
        }

        return tempTaskTemplateTypeValueStr;
    }

    /**
     * 结算方式类型 转换文字
     *
     * @param statementType 结算方式类型
     * @return 结算方式类型文字
     */
    public static String convertSatementTypeToStateStr(Integer statementType) {

        String tempStatementValueStr = "";
        switch (statementType) {
            // 单价结算
            case 1:
                tempStatementValueStr = "固定单价结算";
                break;
            // 固定总价结算
            case 2:
                tempStatementValueStr = "固定总价结算";
                break;
            // 据实结算
            case 3:
                tempStatementValueStr = "据实结算";
                break;
            default:
                break;
        }

        return tempStatementValueStr;
    }

    /**
     * 工作流程 转换文字
     *
     * @param workflowType 工作流程类型
     * @return 咨询业务类型文字
     */
    public static String convertWorkflowTypeToStateStr(Integer workflowType) {

        String tempCounselBusinessTypeValueStr = "";
        switch (workflowType) {
            // 发设计招标公告（道桥）
            case 1:
                tempCounselBusinessTypeValueStr = "发设计招标公告（道桥）";
                break;
            // 现场报名
            case 2:
                tempCounselBusinessTypeValueStr = "现场报名";
                break;
            // 领取资格预审文件
            case 3:
                tempCounselBusinessTypeValueStr = "领取资格预审文件";
                break;
            // 递交资格预审文件
            case 4:
                tempCounselBusinessTypeValueStr = "递交资格预审文件";
                break;
            // 资格预审评标抽取专家
            case 5:
                tempCounselBusinessTypeValueStr = "资格预审评标抽取专家";
                break;
            // 资格预审评标
            case 6:
                tempCounselBusinessTypeValueStr = "资格预审评标";
                break;
            // 资格预审评标结果备案
            case 7:
                tempCounselBusinessTypeValueStr = "资格预审评标结果备案";
                break;
            // 招标文件备案
            case 8:
                tempCounselBusinessTypeValueStr = "招标文件备案";
                break;
            // 领取招标文件
            case 9:
                tempCounselBusinessTypeValueStr = "领取招标文件";
                break;
            // 编制投标文件
            case 10:
                tempCounselBusinessTypeValueStr = "编制投标文件";
                break;
            // 抽取评标专家
            case 11:
                tempCounselBusinessTypeValueStr = "抽取评标专家";
                break;
            // 开标、评标
            case 12:
                tempCounselBusinessTypeValueStr = "开标、评标";
                break;
            // 评标结果公示
            case 13:
                tempCounselBusinessTypeValueStr = "评标结果公示";
                break;
            // 发中标通知书
            case 14:
                tempCounselBusinessTypeValueStr = "发中标通知书";
                break;
            // 签订合同
            case 15:
                tempCounselBusinessTypeValueStr = "签订合同";
                break;
            // 设计出图时间
            case 16:
                tempCounselBusinessTypeValueStr = "设计出图时间";
                break;
            default:
                break;
        }
        return tempCounselBusinessTypeValueStr;
    }

    public static String convertDuration(long duration) {

        String DateTimes = null;

        duration = duration / 1000;
        long days = duration / (60 * 60 * 24);
        long hours = (duration % (60 * 60 * 24)) / (60 * 60);
        long minutes = (duration % (60 * 60)) / 60;
        long seconds = duration % 60;
        if (days > 0) {
            DateTimes = days + "天" + hours + "小时" + minutes + "分钟"
                    + seconds + "秒";
        } else if (hours > 0) {
            DateTimes = hours + "小时" + minutes + "分钟"
                    + seconds + "秒";
        } else if (minutes > 0) {
            DateTimes = minutes + "分钟"
                    + seconds + "秒";
        } else {
            DateTimes = seconds + "秒";
        }

        return DateTimes;
    }
}