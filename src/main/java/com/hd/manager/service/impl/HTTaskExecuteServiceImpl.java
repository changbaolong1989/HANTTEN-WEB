package com.hd.manager.service.impl;


import com.hd.base.enums.EngineeingTypeEnum;
import com.hd.base.enums.TaskA3Enum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.*;

import com.hd.common.entity.*;
import com.hd.common.service.CommonService;
import com.hd.common.util.*;
import com.hd.common.util.word.WordTemplate;
import com.hd.manager.dao.HTTaskExecuteDao;
import com.hd.manager.dao.HTTenderTaskHisTaskInstDao;
import com.hd.manager.dao.bean.HTTableFiveRelationBean;
import com.hd.manager.dao.bean.HTTaskExecuteBean;
import com.hd.manager.dao.bean.HTTaskPeriodBean;
import com.hd.manager.dao.bean.HTTenderTaskHisTaskInstBean;
import com.hd.manager.service.HTTaskExecuteService;
import com.hd.manager.vo.HTTableFiveRelationVO;
import com.hd.manager.vo.HTTaskExecuteVO;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 任务执行 业务层 实现类
 *
 * @author Dongzx
 * Created in 2019/8/13 10:48
 */
@Service
public class HTTaskExecuteServiceImpl extends BaseServiceImpl implements HTTaskExecuteService {

    /**
     * 任务dao 持久层资源
     */
    @Resource
    private HTTaskExecuteDao htTaskExecuteDao;

    /**
     * 任务mapper
     */
    @Resource
    private TbTaskEntityMapper tbTaskEntityMapper;

    /**
     * 请款单mapper
     */
    @Resource
    private TbRequestMenuEntityMapper tbRequestMenuEntityMapper;

    /**
     * 合同mapper
     */
    @Resource
    private TbContractEntityMapper tbContractEntityMapper;

    /**
     * 项目mapper
     */
    @Resource
    private TbProjectEntityMapper tbProjectEntityMapper;

    /**
     * 投标任务dao 持久层资源
     */
    @Resource
    private HTTenderTaskHisTaskInstDao htTenderTaskHisTaskInstDao;

    /**
     * 咨询记录A8 dao 持久层资源
     */
    @Resource
    private TbTableEightRelationEntityMapper tbTableEightRelationEntityMapper;

    /**
     * 咨询记录A5 dao 持久层资源
     */
    @Resource
    private TbTableFiveRelationEntityMapper tbTableFiveRelationEntityMapper;

    /**
     * 通用service
     */
    @Resource
    private CommonService commonService;

    @Value("${file.request.rootpath}")
    private String fileRootPath;

    /**
     * 招标文件专家论证意见表和任务ID的关联表mapper
     */
    @Resource
    private TbExpertOpinionRelationEntityMapper tbExpertOpinionRelationEntityMapper;

    /**
     * 落标通知书和任务关联表mapper
     */
    @Resource
    private TbFailTenderNoticeRelationEntityMapper tbFailTenderNoticeRelationEntityMapper;

    /**
     * 落标通知书和投标人的关联表mapper
     */
    @Resource
    private TbFailTenderUserRelationEntityMapper tbFailTenderUserRelationEntityMapper;

    /**
     * 招标文件确认意见表和任务的关联表mapper
     */
    @Resource
    private TbFileConfirmOpinionRelationEntityMapper tbFileConfirmOpinionRelationEntityMapper;


    @Override
    public Workbook excGenerateTaskExecuteExeclInfo(HTTaskExecuteVO paramVO) {

        ByteArrayOutputStream byteArrayOut = null;

        BufferedImage bufferImg = null;

        HSSFClientAnchor anchor = null;
        try {

            //模板目录
            String tempTempletLoadStr =  "/contract/" + "A1~A10 分析（市场部只看A3）.xls";
            // 获取模板Excel文件
            InputStream tempInputStream = new ClassPathResource(tempTempletLoadStr).getInputStream();
            // 读取Excel文件的内容
            Workbook tmpWorkbook = WorkbookFactory.create(tempInputStream);

            // 投放任务id
            String tempTenderTaskIdStr = paramVO.getTenderTaskId();

            // 如果 投标任务id 不为空 则查询投标任务A3的审核记录 反之 查询所有任务阶段A3的审核记录
            if (StringToolUtils.isNull(tempTenderTaskIdStr)){

                // 参数bean
                HTTaskExecuteBean paramBean = new HTTaskExecuteBean();

                // 任务id
                paramBean.setTaskId(paramVO.getTaskId());

                // 查询任务以及合同详细信息
                HTTaskExecuteBean retBean =  htTaskExecuteDao.getTaskExecuteDetailInfo(paramBean);

                // 合同编号
                String tempContractNumStr = StringToolUtils
                        .convertNullObjectToString(retBean.getContractNum());

                // 项目类型
                String tempCounselBusinessTypeStr = StringToolUtils.STRING_EMPTY;

                // 如果有项目类型 则将项目类型 转换为文字描述
                if (retBean.getCounselBusinessType() != null){

                    tempCounselBusinessTypeStr = BusinessUtils
                            .convertCounselBusinessTypeToStateStr(NumberToolUtils.convertNullToInteger(retBean.getCounselBusinessType()));
                }

                // 项目名称
                String tempProjectNameStr = StringToolUtils.convertNullObjectToString(retBean.getProjectName());

                // 项目编号
                String tempProjectNumStr = StringToolUtils
                        .convertNullObjectToString(retBean.getProjectNum());

                // 合同签订时间
                String tempContractDateStr = DateToolUtils
                        .convertDateFormat(retBean.getContractDate(),DateToolUtils.YYYY_MM_DD_CHINESE);

                // 委托人
                String tempTrusterNameStr = StringToolUtils
                        .convertNullObjectToString(retBean.getTrusterName());

                // 负责人
                String tempDutyPeopleStr = StringToolUtils
                        .convertNullObjectToString(retBean.getDutyPeople());

                // 委托方负责人
                String tempTrusterDutyUserStr = StringToolUtils
                        .convertNullObjectToString(retBean.getTrusterDutyUser());

                // 投资额
                String tempInvestedAmountStr = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToDouble(retBean.getInvestedAmount()) * 10000,NumberToolUtils.INT_SCALE_FLOAT);

                // 合同金额
                String tempContractAmountStr = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retBean.getContractAmount(),NumberToolUtils.INT_SCALE_FLOAT);

                // 部门id
                String tempDepartmentIdStr = StringToolUtils
                        .convertNullObjectToString(retBean.getDepartmentId());

                // 合同类型名称
                String tempContractTypeNameStr = StringToolUtils
                        .convertNullObjectToString(retBean.getContractTypeName());

                // 取得模板所在sheet（第一个sheet）A1
                Sheet tmpA1Sheet = tmpWorkbook.getSheetAt(0);


                // 第三行 第二列 合同编号
                Cell tempA1ContractNumCell = tmpA1Sheet.getRow(2).getCell(1);
                // 设置单元格为文本格式
                tempA1ContractNumCell.setCellType(CellType.STRING);
                tempA1ContractNumCell.setCellValue(retBean.getProjectNum()+"-"+tempContractNumStr);

                // 第三行 第六列 项目类别
                Cell tempA1CounselBusinessTypeCell = tmpA1Sheet.getRow(2).getCell(5);
                // 设置单元格为文本格式
                tempA1CounselBusinessTypeCell.setCellType(CellType.STRING);
                tempA1CounselBusinessTypeCell.setCellValue(tempContractTypeNameStr);

                // 第四行 第二列 项目名称
                Cell tempA1ProjectNameCell = tmpA1Sheet.getRow(3).getCell(1);
                // 设置单元格为文本格式
                tempA1ProjectNameCell.setCellType(CellType.STRING);
                tempA1ProjectNameCell.setCellValue(tempProjectNameStr);

                // 第四行 第六列 成果文件编号
                Cell tempA1ReportNumA1Cell = tmpA1Sheet.getRow(3).getCell(5);
                // 设置单元格为文本格式
                tempA1ReportNumA1Cell.setCellType(CellType.STRING);
                tempA1ReportNumA1Cell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getReportNumA1()));

                // 第五行 第六列 成果文件份数
                Cell tempA1FileNumA1Cell = tmpA1Sheet.getRow(4).getCell(5);
                // 设置单元格为文本格式
                tempA1FileNumA1Cell.setCellType(CellType.STRING);
                tempA1FileNumA1Cell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getReportCopies()));

                // 编制人 签名图片地址
                String tempSignPathBZ = retBean.getSignPathBZ();

                // 存档人 签名图片地址
                String tempSignPathCD = retBean.getSignPathCD();

                // 任务创建人 签名图片地址
                String tempCreateUserSignPathA2 = retBean.getCreateUserSignPathA2();

                // 编制人签字时间
                String tempApplyTimeBZStr = DateToolUtils.convertDateFormat(retBean
                        .getApplyTimeBZ(),DateToolUtils.yyyy_MM_dd);

                //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
                HSSFPatriarch patriarchA1 = (HSSFPatriarch) tmpA1Sheet.createDrawingPatriarch();

                if (!StringToolUtils.isNull(tempSignPathBZ)){
                    // 插入编制人 签名图片
                    byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(fileRootPath + tempSignPathBZ));
                    ImageIO.write(bufferImg, "png", byteArrayOut);
                    //anchor主要用于设置图片的属性
                    anchor = new HSSFClientAnchor(10, 10, 1023, 255,(short) 1, 7, (short) 1, 7);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    //插入图片
                    patriarchA1.createPicture(anchor, tmpWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));


                    // 第八行 第四列 编制人签字时间
                    Cell tempA1ApplyTimeBZCell = tmpA1Sheet.getRow(7).getCell(3);
                    // 设置单元格为文本格式
                    tempA1ApplyTimeBZCell.setCellType(CellType.STRING);
                    tempA1ApplyTimeBZCell.setCellValue(tempApplyTimeBZStr);
                }


                if(retBean.getSignPathSH() != null){
                    // 插入审核人  签名图片
                    byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(fileRootPath + retBean.getSignPathSH()));
                    ImageIO.write(bufferImg, "png", byteArrayOut);
                    //anchor主要用于设置图片的属性
                    anchor = new HSSFClientAnchor(10, 10, 1023, 255,(short) 1, 8, (short) 1, 8);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    //插入图片
                    patriarchA1.createPicture(anchor, tmpWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

                    // 第九行 第四列 审核人签字时间
                    Cell tempA1ApplyTimeSHCell = tmpA1Sheet.getRow(8).getCell(3);
                    // 设置单元格为文本格式
                    tempA1ApplyTimeSHCell.setCellType(CellType.STRING);
                    tempA1ApplyTimeSHCell.setCellValue(DateToolUtils.convertDateFormat(retBean.getApproveTimeSH(),DateToolUtils.yyyy_MM_dd));
                }

                if(retBean.getSignPathZL() != null){
                    // 插入质量控制人 签名图片
                    byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(fileRootPath + retBean.getSignPathZL()));
                    ImageIO.write(bufferImg, "png", byteArrayOut);
                    //anchor主要用于设置图片的属性
                    anchor = new HSSFClientAnchor(10, 10, 1023, 255,(short) 1, 9, (short) 1, 9);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    //插入图片
                    patriarchA1.createPicture(anchor, tmpWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));


                    // 第十行 第四列 审核人签字时间
                    Cell tempA1ApplyTimeZLCell = tmpA1Sheet.getRow(9).getCell(3);
                    // 设置单元格为文本格式
                    tempA1ApplyTimeZLCell.setCellType(CellType.STRING);
                    tempA1ApplyTimeZLCell.setCellValue(DateToolUtils.convertDateFormat(retBean.getApproveTimeZL(),DateToolUtils.yyyy_MM_dd));
                }

                if(retBean.getSignPathSD() != null) {
                    // 插入审定人 签名图片
                    byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(fileRootPath + retBean.getSignPathSD()));
                    ImageIO.write(bufferImg, "png", byteArrayOut);
                    //anchor主要用于设置图片的属性
                    anchor = new HSSFClientAnchor(10, 10, 1023, 255,(short) 1, 10, (short) 1, 10);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    //插入图片
                    patriarchA1.createPicture(anchor, tmpWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

                    // 第十一行 第四列 审定人签字时间
                    Cell tempA1ApplyTimeSDCell = tmpA1Sheet.getRow(10).getCell(3);
                    // 设置单元格为文本格式
                    tempA1ApplyTimeSDCell.setCellType(CellType.STRING);
                    tempA1ApplyTimeSDCell.setCellValue(DateToolUtils.convertDateFormat(retBean.getApproveTimeSD(),DateToolUtils.yyyy_MM_dd));
                }

                if(retBean.getSignPathZJL() != null) {
                    // 插入总经理 签名图片
                    byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(fileRootPath + retBean.getSignPathZJL()));
                    ImageIO.write(bufferImg, "png", byteArrayOut);
                    //anchor主要用于设置图片的属性
                    anchor = new HSSFClientAnchor(10, 10, 1023, 255,(short) 1, 11, (short) 1, 11);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    //插入图片
                    patriarchA1.createPicture(anchor, tmpWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

                    // 第十二行 第四列 总经理签字时间
                    Cell tempA1ApplyTimeZJLCell = tmpA1Sheet.getRow(11).getCell(3);
                    // 设置单元格为文本格式
                    tempA1ApplyTimeZJLCell.setCellType(CellType.STRING);
                    tempA1ApplyTimeZJLCell.setCellValue(DateToolUtils.convertDateFormat(retBean.getApproveTimeZJL(),DateToolUtils.yyyy_MM_dd));
                }

                if (tempSignPathCD != null){
                    // 插入存档人 签名图片
                    byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(fileRootPath + tempSignPathCD));
                    ImageIO.write(bufferImg, "png", byteArrayOut);
                    //anchor主要用于设置图片的属性
                    anchor = new HSSFClientAnchor(10, 10, 1023, 255,(short) 1, 12, (short) 1, 12);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    //插入图片
                    patriarchA1.createPicture(anchor, tmpWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

                    // 第十二行 第四列 存档人签字时间
                    Cell tempA1ApplyTimeCDCell = tmpA1Sheet.getRow(12).getCell(3);
                    // 设置单元格为文本格式
                    tempA1ApplyTimeCDCell.setCellType(CellType.STRING);
                    tempA1ApplyTimeCDCell.setCellValue(DateToolUtils.convertDateFormat(retBean
                            .getApplyTimeCD(),DateToolUtils.yyyy_MM_dd));
                }

                if(retBean.getSignPathZJ() != null){
                    // 插入质量控制人 签名图片
                    byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(fileRootPath + retBean.getSignPathZJ()));
                    ImageIO.write(bufferImg, "png", byteArrayOut);
                    //anchor主要用于设置图片的属性
                    anchor = new HSSFClientAnchor(10, 10, 1023, 255,(short) 1, 13, (short) 1, 13);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    //插入图片
                    patriarchA1.createPicture(anchor, tmpWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));


                    // 第十三行 第四列 审核人签字时间
                    Cell tempA1ApplyTimeZJCell = tmpA1Sheet.getRow(13).getCell(3);
                    // 设置单元格为文本格式
                    tempA1ApplyTimeZJCell.setCellType(CellType.STRING);
                    tempA1ApplyTimeZJCell.setCellValue(DateToolUtils.convertDateFormat(retBean.getApproveTimeZJ(),DateToolUtils.yyyy_MM_dd));
                }


                // TODO 存档人 图片 时间
                // TODO 存档延迟原因 图片 时间

                // 取得模板所在sheet（第二个sheet）A2
                Sheet tmpA2Sheet = tmpWorkbook.getSheetAt(1);

                // 第三行 第二列 合同编号
                Cell tempA2ContractNumCell = tmpA2Sheet.getRow(2).getCell(1);
                // 设置单元格为文本格式
                tempA2ContractNumCell.setCellType(CellType.STRING);
                tempA2ContractNumCell.setCellValue(tempContractNumStr);


                // 第三行 第六列 合同签订时间
                Cell tempA2ContractDateCell = tmpA2Sheet.getRow(2).getCell(5);
                // 设置单元格为文本格式
                tempA2ContractDateCell.setCellType(CellType.STRING);
                tempA2ContractDateCell.setCellValue(tempContractDateStr);

                // 第五行 第二列 项目名称
                Cell tempA2ProjectNameCell = tmpA2Sheet.getRow(4).getCell(1);
                // 设置单元格为文本格式
                tempA2ProjectNameCell.setCellType(CellType.STRING);
                tempA2ProjectNameCell.setCellValue(tempProjectNameStr);

                // 第五行 第六列 项目类别
                Cell tempA2CounselBusinessTypeCell = tmpA2Sheet.getRow(4).getCell(5);
                // 设置单元格为文本格式
                tempA2CounselBusinessTypeCell.setCellType(CellType.STRING);
                tempA2CounselBusinessTypeCell.setCellValue(tempCounselBusinessTypeStr);

                // 第六行 第六列 成果文件编号
                Cell tempA2ReportNumA2Cell = tmpA2Sheet.getRow(5).getCell(5);
                // 设置单元格为文本格式
                tempA2ReportNumA2Cell.setCellType(CellType.STRING);
                tempA2ReportNumA2Cell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getReportNumA2()));

                // 第八行 第二列 项目委托单位
                Cell tempA2TrusterNameCell = tmpA2Sheet.getRow(7).getCell(1);
                // 设置单元格为文本格式
                tempA2TrusterNameCell.setCellType(CellType.STRING);
                tempA2TrusterNameCell.setCellValue(tempTrusterNameStr);

                // 第九行 第二列 项目概况
                Cell tempA2ProjectConditionCell = tmpA2Sheet.getRow(8).getCell(1);
                // 设置单元格为文本格式
                tempA2ProjectConditionCell.setCellType(CellType.STRING);
                tempA2ProjectConditionCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getProjectCondition()));

                // 第十一行 第二列 发包人
                Cell tempA2EmployerA2Cell = tmpA2Sheet.getRow(10).getCell(1);
                // 设置单元格为文本格式
                tempA2EmployerA2Cell.setCellType(CellType.STRING);
                tempA2EmployerA2Cell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getEmployerA2()));

                // 第十二行 第二列 项目中标人
                Cell tempA2BidPersonCell = tmpA2Sheet.getRow(11).getCell(1);
                // 设置单元格为文本格式
                tempA2BidPersonCell.setCellType(CellType.STRING);
                tempA2BidPersonCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getBidPerson()));

                // 计划开始时间
                String tempPlanStartDateStr = DateToolUtils
                        .convertDateFormat(retBean.getPlanStartDate(),DateToolUtils.YYYYMMDD_SPOT);

                // 计划结束时间
                String tempPlanEndDateStr = DateToolUtils
                        .convertDateFormat(retBean.getPlanEndDate(),DateToolUtils.YYYYMMDD_SPOT);

                // 第十三行 第二列 计划完成时间
                Cell tempA2PlanDateCell = tmpA2Sheet.getRow(12).getCell(1);
                // 设置单元格为文本格式
                tempA2PlanDateCell.setCellType(CellType.STRING);
                tempA2PlanDateCell.setCellValue(tempPlanStartDateStr + " - " + tempPlanEndDateStr);

                // 实际开始时间
                String tempActualStartDateStr = DateToolUtils
                        .convertDateFormat(retBean.getActualStartDate(),DateToolUtils.YYYYMMDD_SPOT);

                // 实际结束时间
                String tempActualEndDateStr = DateToolUtils
                        .convertDateFormat(retBean.getActualEndDate(),DateToolUtils.YYYYMMDD_SPOT);

                // 第十四行 第二列 实际完成时间
                Cell tempA2ActualDateCell = tmpA2Sheet.getRow(13).getCell(1);
                // 设置单元格为文本格式
                tempA2ActualDateCell.setCellType(CellType.STRING);
                tempA2ActualDateCell.setCellValue(tempActualStartDateStr + " - " + tempActualEndDateStr);

                // 第十五行 第二列 延迟原因
                Cell tempA2DelayReasonCell = tmpA2Sheet.getRow(14).getCell(1);
                // 设置单元格为文本格式
                tempA2DelayReasonCell.setCellType(CellType.STRING);
                tempA2DelayReasonCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getDelayReason()));

                // 第十六行 第二列 咨询主要结论
                Cell tempA2MainResultCell = tmpA2Sheet.getRow(15).getCell(1);
                // 设置单元格为文本格式
                tempA2MainResultCell.setCellType(CellType.STRING);
                tempA2MainResultCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getMainResult()));

                // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
                HSSFPatriarch patriarchA2 = (HSSFPatriarch) tmpA2Sheet.createDrawingPatriarch();

                // 第十九行 第二列 咨询人员
                // 如果主提审人签名不为空，则设置主提审人签名
                if (!StringToolUtils.isNull(tempSignPathBZ)){
                    // 插入编制人 签名图片
                    byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(fileRootPath + tempSignPathBZ));
                    ImageIO.write(bufferImg, "png", byteArrayOut);
                    //anchor主要用于设置图片的属性
                    anchor = new HSSFClientAnchor(10, 10, 1023, 255,(short) 1, 18, (short) 1, 18);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    //插入图片
                    patriarchA2.createPicture(anchor, tmpWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
                }

                // 第十九行 第五列 时间
                Cell tempA2ApplyTimeBZ1Cell = tmpA2Sheet.getRow(18).getCell(4);
                // 设置单元格为文本格式
                tempA2ApplyTimeBZ1Cell.setCellType(CellType.STRING);
                tempA2ApplyTimeBZ1Cell.setCellValue(DateToolUtils.convertDateFormat(retBean.getApplyTimeBZ(),DateToolUtils.yyyy_MM_dd));

                // 第二十行 第二列 项目负责人
                // 如果任务创建人签名不为空，则设置任务创建人签名
                if (!StringToolUtils.isNull(tempCreateUserSignPathA2)){
                    // 插入编制人 签名图片
                    byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(fileRootPath + tempCreateUserSignPathA2));
                    ImageIO.write(bufferImg, "png", byteArrayOut);
                    //anchor主要用于设置图片的属性
                    anchor = new HSSFClientAnchor(10, 10, 1023, 255,(short) 1, 19, (short) 1, 19);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    //插入图片
                    patriarchA2.createPicture(anchor, tmpWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
                }

                // 第二十行 第五列 时间
                Cell tempA2ApplyTimeBZ2Cell = tmpA2Sheet.getRow(19).getCell(4);
                // 设置单元格为文本格式
                tempA2ApplyTimeBZ2Cell.setCellType(CellType.STRING);
                tempA2ApplyTimeBZ2Cell.setCellValue(DateToolUtils.convertDateFormat(retBean.getApplyTimeBZ(),DateToolUtils.yyyy_MM_dd));


                // 取得模板所在sheet（第四个sheet）A4
                Sheet tmpA4Sheet = tmpWorkbook.getSheetAt(3);

                // 第三行 第二列 合同编号
                Cell tempA4ContractNumCell = tmpA4Sheet.getRow(2).getCell(1);
                // 设置单元格为文本格式
                tempA4ContractNumCell.setCellType(CellType.STRING);
                tempA4ContractNumCell.setCellValue(tempContractNumStr);

                // 第四行 第二列 项目名称
                Cell tempA4ProjectNameCell = tmpA4Sheet.getRow(3).getCell(1);
                // 设置单元格为文本格式
                tempA4ProjectNameCell.setCellType(CellType.STRING);
                tempA4ProjectNameCell.setCellValue(tempProjectNameStr);

                // 第五行 第二列 业务类别
                Cell tempA4CounselBusinessTypeCell = tmpA4Sheet.getRow(4).getCell(1);
                // 设置单元格为文本格式
                tempA4CounselBusinessTypeCell.setCellType(CellType.STRING);
                tempA4CounselBusinessTypeCell.setCellValue(tempCounselBusinessTypeStr);

                // 第七行 第二列 委托方
                Cell tempA4TrusterNameCell = tmpA4Sheet.getRow(6).getCell(1);
                // 设置单元格为文本格式
                tempA4TrusterNameCell.setCellType(CellType.STRING);
                tempA4TrusterNameCell.setCellValue(tempTrusterNameStr);

                // 第七行 第四列 经手人
                Cell tempA4TrusterDutyUserCell = tmpA4Sheet.getRow(6).getCell(3);
                // 设置单元格为文本格式
                tempA4TrusterDutyUserCell.setCellType(CellType.STRING);
                tempA4TrusterDutyUserCell.setCellValue(tempTrusterDutyUserStr);

                // 第九行 第二列 签收方
                Cell tempA4TrusterDutyUser2Cell = tmpA4Sheet.getRow(8).getCell(1);
                // 设置单元格为文本格式
                tempA4TrusterDutyUser2Cell.setCellType(CellType.STRING);
                tempA4TrusterDutyUser2Cell.setCellValue(tempTrusterDutyUserStr);

                // 经手人
                String tempOperatorNameStr = retBean.getOperatorName();

                // 第九行 第四列 经手人
                Cell tempA4OperatorNameCell = tmpA4Sheet.getRow(8).getCell(3);
                // 设置单元格为文本格式
                tempA4OperatorNameCell.setCellType(CellType.STRING);
                tempA4OperatorNameCell.setCellValue(tempOperatorNameStr);

                if(retBean.getDepartmentId().equals(Constant.DEPT_ID_ZJ)){
                    paramBean.setPeriodId("033aec1d-9e6e-11e9-a036-0e411fd5cf01");
                }else if(retBean.getDepartmentId().equals(Constant.DEPT_ID_ZBDL)){
                    paramBean.setPeriodId("033aec1d-9e6e-11e9-a036-0e411fd5cf14");
                }

                // 根据taskId 和 收集资料id 获取 该任务的阶段关联id
                HTTaskExecuteBean htTaskExecuteBean = htTaskExecuteDao.getRelationByTaskId(paramBean);
                paramBean.setRelationId(htTaskExecuteBean.getRelationId());
                // 查询A4 收集资料文件列表
                List<HTTaskExecuteBean> retA4MaterialBeanList = htTaskExecuteDao.searchA4MaterialInfo(paramBean);


                HTTaskExecuteBean tempBean = null;

                Cell tempCell = null;

                Row tempRow = null;
                // 根据收集资料文件列表 动态画execl表格
                for (int i=0;i< retA4MaterialBeanList.size();i++){

                    tempBean = retA4MaterialBeanList.get(i);

                    //  表格前面有13行数据 从第14行开始画
                    tempRow = tmpA4Sheet.createRow(i + 13);

                    CellStyle cellStyleA4 = tmpWorkbook.createCellStyle();

                    // 设置单元格边框 THIN 代表薄边框
                    cellStyleA4.setBorderTop(BorderStyle.THIN);
                    cellStyleA4.setBorderBottom(BorderStyle.THIN);
                    cellStyleA4.setBorderLeft(BorderStyle.THIN);
                    cellStyleA4.setBorderRight(BorderStyle.THIN);
                    // 居中
                    cellStyleA4.setAlignment(HorizontalAlignment.CENTER);
                    cellStyleA4.setVerticalAlignment(VerticalAlignment.CENTER);
                    // 字体
                    Font font = tmpWorkbook.createFont();
                    // 10号字
                    font.setFontHeight((short) (10 * 20));
                    // 字体
                    font.setFontName("黑体");
                    // 设置字体
                    cellStyleA4.setFont(font);

                    // 序号
                    tempCell = tempRow.createCell(0);
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(i+1);
                    tempCell.setCellStyle(cellStyleA4);

                    //  文件名称
                    tempCell = tempRow.createCell(1);
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(tempBean.getMaterialName());
                    tempCell.setCellStyle(cellStyleA4);

                    // 文件类型
                    tempCell = tempRow.createCell(2);
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(tempBean.getFileTypeName());
                    tempCell.setCellStyle(cellStyleA4);

                    // 原件/复印件
                    tempCell = tempRow.createCell(3);
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(tempBean.getCopyFile());
                    tempCell.setCellStyle(cellStyleA4);

                    // 份数
                    tempCell = tempRow.createCell(4);
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(tempBean.getNum());
                    tempCell.setCellStyle(cellStyleA4);

                    // 提交人
                    tempCell = tempRow.createCell(5);
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(tempBean.getUserName());
                    tempCell.setCellStyle(cellStyleA4);

                    // 提交时间
                    tempCell = tempRow.createCell(6);
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(DateToolUtils.convertDateFormat(tempBean.getCreateDate(), DateToolUtils.yyyy_MM_dd));
                    tempCell.setCellStyle(cellStyleA4);

                    // 接收人
                    tempCell = tempRow.createCell(7);
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(tempBean.getRecipient());
                    tempCell.setCellStyle(cellStyleA4);

                    // 接收时间
                    tempCell = tempRow.createCell(8);
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(DateToolUtils.convertDateFormat(tempBean.getRecipientDate(), DateToolUtils.yyyy_MM_dd));
                    tempCell.setCellStyle(cellStyleA4);

                    // 备注
                    tempCell = tempRow.createCell(9);
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(tempBean.getRemark());
                    tempCell.setCellStyle(cellStyleA4);

                }

                // 取得模板所在sheet（第五个sheet）A5
                Sheet tmpA5Sheet = tmpWorkbook.getSheetAt(4);

                // 第二行 第二列 项目名称
                Cell tempA5ProjectNameCell = tmpA5Sheet.getRow(1).getCell(1);
                // 设置单元格为文本格式
                tempA5ProjectNameCell.setCellType(CellType.STRING);
                tempA5ProjectNameCell.setCellValue(tempProjectNameStr);

                // 第二行 第六列 编制人
                Cell tempA5OperatorNameCell = tmpA5Sheet.getRow(1).getCell(5);
                // 设置单元格为文本格式
                tempA5OperatorNameCell.setCellType(CellType.STRING);
                tempA5OperatorNameCell.setCellValue(tempOperatorNameStr);

                // 第三行 第六列 编制时间
                Cell tempA5ApplyTimeCell = tmpA5Sheet.getRow(2).getCell(5);
                // 设置单元格为文本格式
                tempA5ApplyTimeCell.setCellType(CellType.STRING);
                tempA5ApplyTimeCell.setCellValue(tempApplyTimeBZStr);

                // 第四行 第二列 咨询业务简述
                Cell tempA5CounselDescCell = tmpA5Sheet.getRow(3).getCell(1);
                // 设置单元格为文本格式
                tempA5CounselDescCell.setCellType(CellType.STRING);
                tempA5CounselDescCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getCounselDesc()));

                // 第六行 第二列 建设规模
                Cell tempA5ConstructScaleCell = tmpA5Sheet.getRow(5).getCell(1);
                // 设置单元格为文本格式
                tempA5ConstructScaleCell.setCellType(CellType.STRING);
                tempA5ConstructScaleCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getConstructScale()));

                // 第六行 第六列 投资额
                Cell tempA5InvestedAmountCell = tmpA5Sheet.getRow(5).getCell(5);
                // 设置单元格为文本格式
                tempA5InvestedAmountCell.setCellType(CellType.STRING);
                tempA5InvestedAmountCell.setCellValue(tempInvestedAmountStr);

                // 第七行 第二列 咨询内容与重点
                Cell tempA5CounselContentCell = tmpA5Sheet.getRow(6).getCell(1);
                // 设置单元格为文本格式
                tempA5CounselContentCell.setCellType(CellType.STRING);
                tempA5CounselContentCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getCounselContent()));

                // 第七行 第二列 组长
                Cell tempA5CounselGroupCell = tmpA5Sheet.getRow(7).getCell(2);
                // 设置单元格为文本格式
                tempA5CounselGroupCell.setCellType(CellType.STRING);
                tempA5CounselGroupCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getCounselGroup()));

                // 第七行 第五列 专业负责人
                Cell tempA5CounselDutyCell = tmpA5Sheet.getRow(7).getCell(4);
                // 设置单元格为文本格式
                tempA5CounselDutyCell.setCellType(CellType.STRING);
                tempA5CounselDutyCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getCounselDuty()));

                // 第九行 第三列 成员
                Cell tempA5tMemberCell = tmpA5Sheet.getRow(8).getCell(2);
                // 设置单元格为文本格式
                tempA5tMemberCell.setCellType(CellType.STRING);
                tempA5tMemberCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getMemberList()));

                // 第十行 第二列 咨询业务小组分工
                Cell tempA5tDivideWorkCell = tmpA5Sheet.getRow(9).getCell(1);
                // 设置单元格为文本格式
                tempA5tDivideWorkCell.setCellType(CellType.STRING);
                tempA5tDivideWorkCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getDivideWork()));

                // 是否为成果文件（0：否 1：是）
                String tempIsResultFile = StringToolUtils.STRING_ZERO;
                // 取得模板所在sheet
                Sheet tmpA6Sheet;
                Sheet tmpA7Sheet;
                Sheet tmpA8Sheet;
                // 根据部门id 判断造价还是招标
                // 如果是造价则删除第6、8、10、12、13sheet页 反之 则是招标 删除5、7、9sheet页
                if (Constant.DEPT_ID_ZJ.equals(tempDepartmentIdStr)){

                    // 造价的为成果文件
                    tempIsResultFile = StringToolUtils.STRING_ONE;

                    // 删除第6、8、10、12、13sheet页 关于招标的sheet
                    tmpWorkbook.removeSheetAt(13);
                    tmpWorkbook.removeSheetAt(12);
                    tmpWorkbook.removeSheetAt(10);
                    tmpWorkbook.removeSheetAt(8);
                    tmpWorkbook.removeSheetAt(6);

                    // 获取A6所在sheet页
                    tmpA6Sheet = tmpWorkbook.getSheetAt(5);

                    // 第二行 第三列 项目名称
                    Cell tempA6ProjectNameCell = tmpA6Sheet.getRow(1).getCell(2);
                    // 设置单元格为文本格式
                    tempA6ProjectNameCell.setCellType(CellType.STRING);
                    tempA6ProjectNameCell.setCellValue(tempProjectNameStr);

                    // 第二行 第五列 咨询合同编号
                    Cell tempA6ContractNumCell = tmpA6Sheet.getRow(1).getCell(4);
                    // 设置单元格为文本格式
                    tempA6ContractNumCell.setCellType(CellType.STRING);
                    tempA6ContractNumCell.setCellValue(tempContractNumStr);

                    // 查询A6 造价 咨询记录列表
                    List<HTTaskExecuteBean> retA6ZJConsultRecordList = htTaskExecuteDao.searchA6ConsultRecordInfoZJ(paramBean);

                    CellStyle cellStyle = tmpWorkbook.createCellStyle();

                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                    // 字体
                    Font font = tmpWorkbook.createFont();

                    // 10号字
                    font.setFontHeight((short) (10 * 20));
                    // 字体
                    font.setFontName("黑体");

                    // 设置字体
                    cellStyle.setFont(font);

                    for (int i = 0;i<retA6ZJConsultRecordList.size();i++){

                        tempBean = retA6ZJConsultRecordList.get(i);

                        //  表格前面有三行数据 从第四行开始画
                        tempRow = tmpA6Sheet.createRow(i + 3);

                        // 从第四行开始画
                        // 序号
                        tempCell = tempRow.createCell(0);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue(StringToolUtils.convertNullObjectToString(tempBean.getSortNum()));

                        //  咨询意见
                        tempCell = tempRow.createCell(1);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue(tempBean.getCounselAdvise());
                        tempCell.setCellStyle(cellStyle);

                        // 承包人回复意见
                        tempCell = tempRow.createCell(3);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue(tempBean.getResponseAdvise());
                        tempCell.setCellStyle(cellStyle);

                        CellRangeAddress region;
                        region = new CellRangeAddress(tempRow.getRowNum(), tempRow.getRowNum(), 1, 2);
                        tmpA6Sheet.addMergedRegion(region);
                        region = new CellRangeAddress(tempRow.getRowNum(), tempRow.getRowNum(), 3, 4);
                        tmpA6Sheet.addMergedRegion(region);


                    }

                    // A7 所在sheet 页
                    tmpA7Sheet = tmpWorkbook.getSheetAt(6);

                    // 查询造价指标分析
                    HTTaskExecuteBean retA7ZJAchievementInfoBean = htTaskExecuteDao.searchA7AchievementInfoZJ(paramBean);

                    if(retA7ZJAchievementInfoBean != null){
                        CellStyle cellStyleA7 = tmpWorkbook.createCellStyle();

                        //  cellStyle.setAlignment(HorizontalAlignment.CENTER);
                        // cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                        // 设置单元格边框 THIN 代表薄边框
                        cellStyleA7.setBorderTop(BorderStyle.THIN);
                        cellStyleA7.setBorderBottom(BorderStyle.THIN);
                        cellStyleA7.setBorderLeft(BorderStyle.THIN);
                        cellStyleA7.setBorderRight(BorderStyle.THIN);

                        // 字体
                        Font fontA7 = tmpWorkbook.createFont();

                        // 10号字
                        fontA7.setFontHeight((short) (10 * 20));
                        // 字体
                        fontA7.setFontName("黑体");

                        // 设置字体
                        cellStyleA7.setFont(fontA7);

                        // 第三行 第三列 咨询合同编号
                        Cell tempA7ContractNumCell = tmpA7Sheet.getRow(2).getCell(2);
                        // 设置单元格为文本格式
                        tempA7ContractNumCell.setCellType(CellType.STRING);
                        tempA7ContractNumCell.setCellValue(tempContractNumStr);

                        // 第四行 第三列 项目名称
                        Cell tempA7ProjectNameCell = tmpA7Sheet.getRow(3).getCell(2);
                        // 设置单元格为文本格式
                        tempA7ProjectNameCell.setCellType(CellType.STRING);
                        tempA7ProjectNameCell.setCellValue(tempProjectNameStr);

                        // 第五行 第三列 工程类别
                        Cell tempA7EngineeingTypeCell = tmpA7Sheet.getRow(4).getCell(2);
                        // 设置单元格为文本格式
                        tempA7EngineeingTypeCell.setCellType(CellType.STRING);
                        tempA7EngineeingTypeCell.setCellValue(EngineeingTypeEnum.getTypeName(retA7ZJAchievementInfoBean.getEngineeingType()));

                        // 第六行 第三列合同价格
                        Cell tempA7ContractAmountCell = tmpA7Sheet.getRow(5).getCell(2);
                        // 设置单元格为文本格式
                        tempA7ContractAmountCell.setCellType(CellType.STRING);
                        tempA7ContractAmountCell.setCellValue(tempContractAmountStr);

                        // 第七行 第三列 结算价格
                        Cell tempA7SettlePriceCell = tmpA7Sheet.getRow(6).getCell(2);
                        // 设置单元格为文本格式
                        tempA7SettlePriceCell.setCellType(CellType.STRING);
                        tempA7SettlePriceCell.setCellValue(NumberToolUtils
                                .convertNumberToFormatStringWithHalfUp(retA7ZJAchievementInfoBean.getSettlePrice(),NumberToolUtils.INT_SCALE_FLOAT));


                        // 第八行 第三列 结算价超出合同价的原因
                        Cell tempA7SettleReasonCell = tmpA7Sheet.getRow(7).getCell(2);
                        // 设置单元格为文本格式
                        tempA7SettleReasonCell.setCellType(CellType.STRING);
                        tempA7SettleReasonCell.setCellValue(StringToolUtils.convertNullObjectToString(retA7ZJAchievementInfoBean.getSettleReason()));

                        // 单位工程造价指标
                        List<HTTaskExecuteBean> tempA7ZJBeanList =  retA7ZJAchievementInfoBean.getHtTaskExecuteList();

                        for (int i=0;i<tempA7ZJBeanList.size();i++){


                            tempBean = tempA7ZJBeanList.get(i);

                            //  表格前面有十二行数据 从第十三行开始画
                            tempRow = tmpA7Sheet.createRow(i + 12);

                            // 从第十三行开始画
                            // 序号
                            tempCell = tempRow.createCell(0);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(StringToolUtils.convertNullObjectToString(tempBean.getSortNum()));
                            tempCell.setCellStyle(cellStyleA7);

                            // 单位工程名称
                            tempCell = tempRow.createCell(1);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(tempBean.getUnitName());
                            tempCell.setCellStyle(cellStyleA7);

                            // 建设规模
                            tempCell = tempRow.createCell(2);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(tempBean.getConstructScale());
                            tempCell.setCellStyle(cellStyleA7);

                            // 造价
                            tempCell = tempRow.createCell(3);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(NumberToolUtils
                                    .convertNumberToFormatStringWithHalfUp(tempBean.getConstructCost(),NumberToolUtils.INT_SCALE_FLOAT));
                            tempCell.setCellStyle(cellStyleA7);

                            // 单位造价
                            tempCell = tempRow.createCell(4);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(NumberToolUtils
                                    .convertNumberToFormatStringWithHalfUp(tempBean.getUnitCost(),NumberToolUtils.INT_SCALE_FLOAT));
                            tempCell.setCellStyle(cellStyleA7);

                            // 造价比例
                            tempCell = tempRow.createCell(5);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(tempBean.getCostRatio());
                            tempCell.setCellStyle(cellStyleA7);
                        }
                    }



                    // 获取 A8 所在sheet页
                    tmpA8Sheet = tmpWorkbook.getSheetAt(7);

                    // 第二行 第二列 项目名称
                    Cell tempA8ProjectNameCell = tmpA8Sheet.getRow(1).getCell(1);
                    // 设置单元格为文本格式
                    tempA8ProjectNameCell.setCellType(CellType.STRING);
                    tempA8ProjectNameCell.setCellValue(tempProjectNameStr);


                    // 第二行 第四列 咨询合同编号
                    Cell tempA8ContractNumCell = tmpA8Sheet.getRow(1).getCell(3);
                    // 设置单元格为文本格式
                    tempA8ContractNumCell.setCellType(CellType.STRING);
                    tempA8ContractNumCell.setCellValue(tempContractNumStr);


                    // 第三行 第二列 发包人
                    Cell tempA8EmployerA8Cell = tmpA8Sheet.getRow(2).getCell(1);
                    // 设置单元格为文本格式
                    tempA8EmployerA8Cell.setCellType(CellType.STRING);
                    tempA8EmployerA8Cell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getEmployerA8()));

                    // 第四行 第二列 承包人
                    Cell tempA8ContractorCell = tmpA8Sheet.getRow(3).getCell(1);
                    // 设置单元格为文本格式
                    tempA8ContractorCell.setCellType(CellType.STRING);
                    tempA8ContractorCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getContractor()));

                    // 第五行 第二列 承包人
                    Cell tempA8SupervisorCell = tmpA8Sheet.getRow(4).getCell(1);
                    // 设置单元格为文本格式
                    tempA8SupervisorCell.setCellType(CellType.STRING);
                    tempA8SupervisorCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getSupervisor()));

                    // 第六行 第一列 现场勘查记录
                    Cell tempA8ProspectRecordCell = tmpA8Sheet.getRow(5).getCell(0);
                    // 设置单元格为文本格式
                    tempA8ProspectRecordCell.setCellType(CellType.STRING);
                    tempA8ProspectRecordCell.setCellValue("现场勘查记录：" + StringToolUtils.convertNullObjectToString(retBean.getProspectRecord()));

                    // 项目监理部
                } else if (Constant.DEPT_ID_XMJL.equals(tempDepartmentIdStr)) {
                    // 删除第12、13sheet页 关于招标的sheet
                    tmpWorkbook.removeSheetAt(13);
                    tmpWorkbook.removeSheetAt(12);
                } else {
                    // 删除第5、7、9sheet页 关于造价的sheet
                    tmpWorkbook.removeSheetAt(9);
                    tmpWorkbook.removeSheetAt(7);
                    tmpWorkbook.removeSheetAt(5);

                    // 获取A6 所在 sheet页
                    tmpA6Sheet = tmpWorkbook.getSheetAt(5);

                    CellStyle cellStyleA6 = tmpWorkbook.createCellStyle();
                    // 设置单元格边框 THIN 代表薄边框
                    cellStyleA6.setBorderTop(BorderStyle.THIN);
                    cellStyleA6.setBorderBottom(BorderStyle.THIN);
                    cellStyleA6.setBorderLeft(BorderStyle.THIN);
                    cellStyleA6.setBorderRight(BorderStyle.THIN);

                    cellStyleA6.setAlignment(HorizontalAlignment.CENTER);
                    cellStyleA6.setVerticalAlignment(VerticalAlignment.CENTER);

                    // 字体
                    Font font = tmpWorkbook.createFont();

                    // 10号字
                    font.setFontHeight((short) (10 * 20));
                    // 字体
                    font.setFontName("宋体");

                    // 设置字体
                    cellStyleA6.setFont(font);

                    // 第四行 第二列 项目名称
                    Cell tempA6ProjectNameCell = tmpA6Sheet.getRow(3).getCell(1);
                    // 设置单元格为文本格式
                    tempA6ProjectNameCell.setCellType(CellType.STRING);
                    tempA6ProjectNameCell.setCellValue(tempProjectNameStr);

                    // 第四行 第六列 资料最后上传时间
                    Cell tempA6ApplyTimeBZCell = tmpA6Sheet.getRow(3).getCell(5);
                    // 设置单元格为文本格式
                    tempA6ApplyTimeBZCell.setCellType(CellType.STRING);
                    tempA6ApplyTimeBZCell.setCellValue(tempApplyTimeBZStr);


                    // 查询A6 造价 咨询记录列表
                    List<HTTaskExecuteBean> retA6ZBConsultRecordList = htTaskExecuteDao.searchA6ConsultRecordInfoZB(paramBean);


                    for (int i = 0;i<retA6ZBConsultRecordList.size();i++){

                        tempBean = retA6ZBConsultRecordList.get(i);

                        //  表格前面有 六行数据 从第七行开始画
                        tempRow = tmpA6Sheet.createRow(i + 6);

                        // 从第七行开始画
                        // 序号
                        tempCell = tempRow.createCell(0);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue(StringToolUtils.convertNullObjectToString(tempBean.getSortNum()));
                        tempCell.setCellStyle(cellStyleA6);
                        //  工程名称
                        tempCell = tempRow.createCell(1);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue(tempBean.getEngineeringName());
                        tempCell.setCellStyle(cellStyleA6);

                        // 招标方式
                        tempCell = tempRow.createCell(2);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue(tempBean.getTenderMethod());
                        tempCell.setCellStyle(cellStyleA6);

                        // 招标形式
                        tempCell = tempRow.createCell(3);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue(tempBean.getTenderType());
                        tempCell.setCellStyle(cellStyleA6);

                        // 招标平台
                        tempCell = tempRow.createCell(4);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue(tempBean.getTenderPlatform());
                        tempCell.setCellStyle(cellStyleA6);

                        // 投标人资格要求
                        tempCell = tempRow.createCell(5);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue(tempBean.getInvestorRequire());
                        tempCell.setCellStyle(cellStyleA6);

                    }

                    // 获取A7 所在sheet 页
                    tmpA7Sheet = tmpWorkbook.getSheetAt(6);

                    // 第三行 第二列 项目名称
                    Cell tempA7ProjectNameCell = tmpA7Sheet.getRow(2).getCell(1);
                    // 设置单元格为文本格式
                    tempA7ProjectNameCell.setCellType(CellType.STRING);
                    tempA7ProjectNameCell.setCellValue(tempProjectNameStr);


                    // 第三行 第六列 资料最后上传时间
                    Cell tempA7ApplyTimeBZCell = tmpA7Sheet.getRow(2).getCell(5);
                    // 设置单元格为文本格式
                    tempA7ApplyTimeBZCell.setCellType(CellType.STRING);
                    tempA7ApplyTimeBZCell.setCellValue(tempApplyTimeBZStr);

                    // 查询招标工作进度计划
                    List<HTTaskExecuteBean> retA7ZBAchievementInfoBeanList = htTaskExecuteDao.searchA7ConsultRecordInfoZB(paramBean);

                    Row tempOutRow;
                    Row tempInRow;
                    int tempRowNumInt = 0;
                    for (HTTaskExecuteBean tempEachBean : retA7ZBAchievementInfoBeanList){

                        //  表格前面有五行数据 从第六行开始画
                        tempOutRow = tmpA7Sheet.createRow(tempRowNumInt + 5);

                        tempCell = tempOutRow.createCell(0);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue(StringToolUtils.convertNullObjectToString(tempEachBean.getTenderTypeContent()));

                        // 合并单元格
                        CellRangeAddress region;
                        region = new CellRangeAddress(tempOutRow.getRowNum(), tempOutRow.getRowNum(), 0, 5);
                        tmpA7Sheet.addMergedRegion(region);

                        tempRowNumInt ++;

                        for (HTTaskExecuteBean tempInnerBean : tempEachBean.getHtTaskExecuteList()){

                            // 当前应该画表格的行数
                            tempInRow = tmpA7Sheet.createRow(tempRowNumInt + 5);

                            // 序号
                            tempCell = tempInRow.createCell(0);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(StringToolUtils.convertNullObjectToString(tempInnerBean.getSortNum()));

                            // 工作流程
                            tempCell = tempInRow.createCell(1);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(BusinessUtils.convertWorkflowTypeToStateStr(NumberToolUtils.convertNullToInteger(tempInnerBean.getWorkflow())));

                            // 节点
                            // 开始时间
                            String tempStartDateStr = DateToolUtils
                                    .convertDateFormat(tempInnerBean.getStartDate(),DateToolUtils.YYYY_MM_DD_CHINESE);

                            // 结束时间
                            String tempEndDateStr = DateToolUtils
                                    .convertDateFormat(tempInnerBean.getEndDate(),DateToolUtils.YYYY_MM_DD_CHINESE);

                            tempCell = tempInRow.createCell(2);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(tempStartDateStr + " - " + tempEndDateStr);

                            // 时间
                            tempCell = tempInRow.createCell(3);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(tempInnerBean.getDays() + "天");

                            // 涉及部门
                            tempCell = tempInRow.createCell(4);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(tempInnerBean.getInvolvedDepartment());

                            // 备注
                            tempCell = tempInRow.createCell(5);
                            tempCell.setCellType(CellType.STRING);
                            tempCell.setCellValue(tempInnerBean.getRemark());


                            tempRowNumInt++;
                        }


                    }

                    // 获取A8 所在sheet 页
                    tmpA8Sheet = tmpWorkbook.getSheetAt(7);

                    // 第二行 第三列 项目名称
                    Cell tempA8ProjectNameCell = tmpA8Sheet.getRow(1).getCell(2);
                    // 设置单元格为文本格式
                    tempA8ProjectNameCell.setCellType(CellType.STRING);
                    tempA8ProjectNameCell.setCellValue(tempProjectNameStr);

                    // 第二行 第四列 达成意见一致时间
                    Cell tempA8ConsensusTimeCell = tmpA8Sheet.getRow(1).getCell(3);
                    // 设置单元格为文本格式
                    tempA8ConsensusTimeCell.setCellType(CellType.STRING);
                    tempA8ConsensusTimeCell.setCellValue(DateToolUtils
                            .convertDateFormat(retBean.getConsensusTime(),DateToolUtils.yyyy_MM_dd));

                    paramBean.setRelationId(retBean.getRelationId());

                    // 查询A8 招标 咨询工作记录
                   List<HTTaskExecuteBean> retA8ZBConsultRecordList = htTaskExecuteDao.searchA8ConsultRecordZB(paramBean);


                   for (int i=0;i<retA8ZBConsultRecordList.size();i++){
                       tempBean = retA8ZBConsultRecordList.get(i);

                       //  表格前面有 三行数据 从第四行开始画
                       tempRow = tmpA8Sheet.createRow(i + 3);

                       // 序号
                       tempCell = tempRow.createCell(0);
                       tempCell.setCellType(CellType.STRING);
                       tempCell.setCellValue(StringToolUtils.convertNullObjectToString(tempBean.getSortNum()));

                       // 咨询意见
                       tempCell = tempRow.createCell(1);
                       tempCell.setCellType(CellType.STRING);
                       tempCell.setCellValue(StringToolUtils.convertNullObjectToString(tempBean.getCounselAdvise()));

                       // 委托方回复意见
                       tempCell = tempRow.createCell(3);
                       tempCell.setCellType(CellType.STRING);
                       tempCell.setCellValue(StringToolUtils.convertNullObjectToString(tempBean.getAnswerAdvise()));
                   }
               }
                // 重命名A6、A7、A8
                tmpWorkbook.setSheetName(5,"A6");
                tmpWorkbook.setSheetName(6,"A7");
                tmpWorkbook.setSheetName(7,"A8");


               // A9 所在 sheet页
                Sheet tmpA9Sheet = tmpWorkbook.getSheetAt(8);

                // 第四行 第三列 项目委托单位
                Cell tempA9TrusterNameCell = tmpA9Sheet.getRow(3).getCell(2);
                // 设置单元格为文本格式
                tempA9TrusterNameCell.setCellType(CellType.STRING);
                tempA9TrusterNameCell.setCellValue(tempTrusterNameStr);


                // 第四行 第六列 合同编号
                Cell tempA9ContractNumCell = tmpA9Sheet.getRow(3).getCell(5);
                // 设置单元格为文本格式
                tempA9ContractNumCell.setCellType(CellType.STRING);
                tempA9ContractNumCell.setCellValue(tempContractNumStr);

                // 第五行 第三列 项目名称
                Cell tempA9ProjectNameCell = tmpA9Sheet.getRow(4).getCell(2);
                // 设置单元格为文本格式
                tempA9ProjectNameCell.setCellType(CellType.STRING);
                tempA9ProjectNameCell.setCellValue(tempProjectNameStr);


                // 第五行 第六列 服务内容
                Cell tempA9CounselBusinessTypeCell = tmpA9Sheet.getRow(4).getCell(5);
                // 设置单元格为文本格式
                tempA9CounselBusinessTypeCell.setCellType(CellType.STRING);
                tempA9CounselBusinessTypeCell.setCellValue(tempCounselBusinessTypeStr);

                // 如果是招标则添加第12、13sheet页
                if (Constant.DEPT_ID_ZBDL.equals(tempDepartmentIdStr)){

                    // ==================================== 专家论证意见表 start =====================================

                    TbExpertOpinionRelationEntityExample tbExpertOpinionRelationEntityExample = new TbExpertOpinionRelationEntityExample();
                    TbExpertOpinionRelationEntityExample.Criteria criteria = tbExpertOpinionRelationEntityExample.createCriteria();
                    criteria.andTaskIdEqualTo(paramVO.getTaskId());
                    List<TbExpertOpinionRelationEntity> tbExpertOpinionRelationEntityExampleList = tbExpertOpinionRelationEntityMapper.selectByExample(tbExpertOpinionRelationEntityExample);

                    // 第三行 第二列 采购人
                    String tempPurchaserStr = StringToolUtils.STRING_EMPTY;
                    // 第四行 第二列 采购代理机构
                    String tempAgentOrgStr = StringToolUtils.STRING_EMPTY;
                    // 第五行 第四列 采购类别
                    String tempPurchaserTypeStr = StringToolUtils.STRING_EMPTY;
                    // 第六行 第二列 专家论证意见
                    String tempExpertOpinionStr = StringToolUtils.STRING_EMPTY;
                    // 第七行 第二列 论证专家签字
                    String tempExpertSignStr = StringToolUtils.STRING_EMPTY;
                    // 第七行 第四列 论证时间
                    String tempProveTimeStr = StringToolUtils.STRING_EMPTY;
                    // 第八行 第二列 备注
                    String tempRemarkStr = StringToolUtils.STRING_EMPTY;

                    // 如果有数据
                    if(!ListToolUtils.isEmpty(tbExpertOpinionRelationEntityExampleList)){
                        TbExpertOpinionRelationEntity tbExpertOpinionRelationEntity = tbExpertOpinionRelationEntityExampleList.get(0);
                        // 第三行 第二列 采购人
                        tempPurchaserStr = StringToolUtils
                                .convertNullObjectToString(tbExpertOpinionRelationEntity.getPurchaser());
                        // 第四行 第二列 采购代理机构
                        tempAgentOrgStr = StringToolUtils
                                .convertNullObjectToString(tbExpertOpinionRelationEntity.getAgentOrg());
                        // 第五行 第四列 采购类别
                        tempPurchaserTypeStr = StringToolUtils
                                .convertNullObjectToString(tbExpertOpinionRelationEntity.getPurchaserType());
                        // 第六行 第二列 专家论证意见
                        tempExpertOpinionStr = StringToolUtils
                                .convertNullObjectToString(tbExpertOpinionRelationEntity.getExpertOpinion());
                        // 第七行 第二列 论证专家签字
                        tempExpertSignStr = StringToolUtils
                                .convertNullObjectToString(tbExpertOpinionRelationEntity.getExpertSign());
                        // 第七行 第四列 论证时间
                        tempProveTimeStr = DateToolUtils.convertDateFormat(
                                tbExpertOpinionRelationEntity.getProveTime(),DateToolUtils.YYYY_MM_DD_CHINESE);
                        // 第八行 第二列 备注
                        tempRemarkStr = StringToolUtils
                                .convertNullObjectToString(tbExpertOpinionRelationEntity.getRemark());
                    }

                    // 专家论证 所在 sheet页
                    Sheet tmpExpertOpinionSheet = tmpWorkbook.getSheetAt(9);

                    // 第二行 第二列 项目名称
                    Cell tempProjectNameCell = tmpExpertOpinionSheet.getRow(1).getCell(1);
                    // 设置单元格为文本格式
                    tempProjectNameCell.setCellType(CellType.STRING);
                    tempProjectNameCell.setCellValue(tempProjectNameStr);

                    // 第三行 第二列 采购人
                    Cell tempPurchaserCell = tmpExpertOpinionSheet.getRow(2).getCell(1);
                    // 设置单元格为文本格式
                    tempPurchaserCell.setCellType(CellType.STRING);
                    tempPurchaserCell.setCellValue(tempPurchaserStr);

                    // 第四行 第二列 采购代理机构
                    Cell tempAgentOrgCell = tmpExpertOpinionSheet.getRow(3).getCell(1);
                    // 设置单元格为文本格式
                    tempAgentOrgCell.setCellType(CellType.STRING);
                    tempAgentOrgCell.setCellValue(tempAgentOrgStr);

                    // 第五行 第二列 项目编号
                    Cell tempProjectNumCell = tmpExpertOpinionSheet.getRow(4).getCell(1);
                    // 设置单元格为文本格式
                    tempProjectNumCell.setCellType(CellType.STRING);
                    tempProjectNumCell.setCellValue(tempProjectNumStr);

                    // 第五行 第四列 采购类别
                    Cell tempPurchaserTypeCell = tmpExpertOpinionSheet.getRow(4).getCell(3);
                    // 设置单元格为文本格式
                    tempPurchaserTypeCell.setCellType(CellType.STRING);
                    tempPurchaserTypeCell.setCellValue(tempPurchaserTypeStr);

                    // 第六行 第二列 专家论证意见
                    Cell tempExpertOpinionCell = tmpExpertOpinionSheet.getRow(5).getCell(1);
                    // 设置单元格为文本格式
                    tempExpertOpinionCell.setCellType(CellType.STRING);
                    tempExpertOpinionCell.setCellValue(tempExpertOpinionStr);

                    // 第七行 第二列 论证专家签字
                    Cell tempExpertSignCell = tmpExpertOpinionSheet.getRow(6).getCell(1);
                    // 设置单元格为文本格式
                    tempExpertSignCell.setCellType(CellType.STRING);
                    tempExpertSignCell.setCellValue(tempExpertSignStr);

                    // 第七行 第四列 论证时间
                    Cell tempProveTimeCell = tmpExpertOpinionSheet.getRow(6).getCell(3);
                    // 设置单元格为文本格式
                    tempProveTimeCell.setCellType(CellType.STRING);
                    tempProveTimeCell.setCellValue(tempProveTimeStr);

                    // 第八行 第二列 备注
                    Cell tempRemarkCell = tmpExpertOpinionSheet.getRow(7).getCell(1);
                    // 设置单元格为文本格式
                    tempRemarkCell.setCellType(CellType.STRING);
                    tempRemarkCell.setCellValue(tempRemarkStr);

                    // ==================================== 专家论证意见表 end =====================================

                    // ==================================== 招标文件确认意见表 start =====================================

                    TbFileConfirmOpinionRelationEntityExample tbFileConfirmOpinionRelationEntityExample = new TbFileConfirmOpinionRelationEntityExample();
                    TbFileConfirmOpinionRelationEntityExample.Criteria criteria2 = tbFileConfirmOpinionRelationEntityExample.createCriteria();
                    criteria2.andTaskIdEqualTo(paramVO.getTaskId());
                    List<TbFileConfirmOpinionRelationEntity> tbFileConfirmOpinionRelationEntityList =
                            tbFileConfirmOpinionRelationEntityMapper.selectByExample(tbFileConfirmOpinionRelationEntityExample);

                    // 第四行 第二列 招标控制价
                    String tempTenderSumLimitStr2 = StringToolUtils.STRING_EMPTY;
                    // 第五行 第二列 采购人
                    String tempPurchaserStr2 = StringToolUtils.STRING_EMPTY;
                    // 第六行 第二列 招标代理机构
                    String tempAgentOrgStr2 = StringToolUtils.STRING_EMPTY;

                    // 如果有数据
                    if(!ListToolUtils.isEmpty(tbFileConfirmOpinionRelationEntityList)){
                        TbFileConfirmOpinionRelationEntity tbFileConfirmOpinionRelationEntity = tbFileConfirmOpinionRelationEntityList.get(0);
                        // 第四行 第二列 招标控制价
                        tempTenderSumLimitStr2 = NumberToolUtils.convertNumberToFormatStringWithHalfUp(
                                tbFileConfirmOpinionRelationEntity.getTenderSumLimit(),2);
                        // 第五行 第二列 采购人
                        tempPurchaserStr2 = StringToolUtils
                                .convertNullObjectToString(tbFileConfirmOpinionRelationEntity.getPurchaser());
                        // 第六行 第二列 招标代理机构
                        tempAgentOrgStr2 = StringToolUtils
                                .convertNullObjectToString(tbFileConfirmOpinionRelationEntity.getAgentOrg());

                    }

                    // 招标文件确认意见表 所在 sheet页
                    Sheet tmpConfirmOpinionSheet = tmpWorkbook.getSheetAt(10);

                    // 第二行 第二列 项目名称
                    Cell tempProjectNameCell2 = tmpConfirmOpinionSheet.getRow(1).getCell(1);
                    // 设置单元格为文本格式
                    tempProjectNameCell2.setCellType(CellType.STRING);
                    tempProjectNameCell2.setCellValue(tempProjectNameStr);

                    // 第三行 第二列 项目编号
                    Cell tempProjectNumCell2 = tmpConfirmOpinionSheet.getRow(2).getCell(1);
                    // 设置单元格为文本格式
                    tempProjectNumCell2.setCellType(CellType.STRING);
                    tempProjectNumCell2.setCellValue(tempProjectNumStr);

                    // 第四行 第二列 招标控制价
                    Cell tempTenderSumLimitCell2 = tmpConfirmOpinionSheet.getRow(3).getCell(1);
                    // 设置单元格为文本格式
                    tempTenderSumLimitCell2.setCellType(CellType.STRING);
                    tempTenderSumLimitCell2.setCellValue("￥"+tempTenderSumLimitStr2+"万元");

                    // 第五行 第二列 采购人
                    Cell tempPurchaserCell2 = tmpConfirmOpinionSheet.getRow(4).getCell(1);
                    // 设置单元格为文本格式
                    tempPurchaserCell2.setCellType(CellType.STRING);
                    tempPurchaserCell2.setCellValue(tempPurchaserStr2);

                    // 第四行 第二列 招标代理机构
                    Cell tempAgentOrgCell2 = tmpConfirmOpinionSheet.getRow(5).getCell(1);
                    // 设置单元格为文本格式
                    tempAgentOrgCell2.setCellType(CellType.STRING);
                    tempAgentOrgCell2.setCellValue(tempAgentOrgStr2);

                    // ==================================== 招标文件确认意见表 end =====================================
                }

                CellStyle cellStyleA3 = tmpWorkbook.createCellStyle();
                // 自动换行
                cellStyleA3.setWrapText(true);

                cellStyleA3.setAlignment(HorizontalAlignment.LEFT);
                cellStyleA3.setVerticalAlignment(VerticalAlignment.CENTER);

                // 字体
                Font font = tmpWorkbook.createFont();
                // 10号字
                font.setFontHeight((short) (10 * 20));
                // 字体
                font.setFontName("黑体");
                // 设置字体
                cellStyleA3.setFont(font);

                // 画图的顶级管理器，
                HSSFPatriarch tempPatriarch;

                paramBean.setIsResultFile(tempIsResultFile);

                List<HTTaskPeriodBean> retA3AuditRecordList = htTaskExecuteDao.excSearchHTTaskHisTaskInstInfoList(paramBean);

                HTTaskPeriodBean tempTaskPeriodBean = null;

                Sheet tempThisSheet;

                // 循环画A3
                for (int i=0;i<retA3AuditRecordList.size();i++){
                    // 复制第3个sheet页 A3 的样式
                    tempThisSheet = tmpWorkbook.cloneSheet(2);

                    // 设置当前sheet页 所在位置
                    int tempIndex = 2 + (i + 1);

                    tempPatriarch = (HSSFPatriarch) tempThisSheet.createDrawingPatriarch();

                    tempTaskPeriodBean = retA3AuditRecordList.get(i);

                    // 任务阶段名称
                    String tempPeriodNameStr = StringToolUtils.convertNullObjectToString(tempTaskPeriodBean.getPeriodName()).replace("/","-");

                    // 当前sheet页名称 默认按招标的名称来
                    String tempThisSheetNameStr = "A3（" + tempPeriodNameStr + "）";

                    // 如果是造价 则名称就为A3
                    if (Constant.DEPT_ID_ZJ.equals(tempDepartmentIdStr)){
                        tempThisSheetNameStr =  "A3";
                    }

                    if(Constant.DEPT_ID_ZJ.equals(tempDepartmentIdStr)){// 造价
                        tmpWorkbook.setSheetName(8 + (i + 1),tempThisSheetNameStr);// 重命名当前sheet页
                    } else if (Constant.DEPT_ID_ZBDL.equals(tempDepartmentIdStr)){// 招标代理
                        tmpWorkbook.setSheetName(10 + (i + 1),tempThisSheetNameStr);// 重命名当前sheet页
                    } else {// 其他（项目监理）
                        tmpWorkbook.setSheetName(11 + (i + 1),tempThisSheetNameStr);// 重命名当前sheet页
                    }

                    tmpWorkbook.setSheetOrder(tempThisSheetNameStr,tempIndex);


                    // 第二行 第二列 项目名称
                    tempCell = tempThisSheet.getRow(1).getCell(1);
                    // 设置单元格为文本格式
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(tempProjectNameStr);


                    // 第二行 第四列 合同编号
                    tempCell = tempThisSheet.getRow(1).getCell(3);
                    // 设置单元格为文本格式
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(tempContractNumStr);

                    // 第三行 第四列 成果文件编号
                    tempCell = tempThisSheet.getRow(2).getCell(3);
                    // 设置单元格为文本格式
                    tempCell.setCellType(CellType.STRING);
                    tempCell.setCellValue(StringToolUtils.convertNullObjectToString(retBean.getReportNumA3()));


                    // 查询任务结算单 所有审核
                    List<HTTaskPeriodBean> retTaskPeriodAuditLevelList = tempTaskPeriodBean.getHtTaskPeriodAuditLevelList();

                    for (HTTaskPeriodBean tempEachTaskPeriodBean : retTaskPeriodAuditLevelList){

                        // 初审日期
                        String tempFirstAuditDateStr = StringToolUtils.STRING_EMPTY;

                        // 执行日期
                        String tempExecDateStr = StringToolUtils.STRING_EMPTY;

                        // 通过日期
                        String tempPassDateStr = StringToolUtils.STRING_EMPTY;

                        // 复合意见
                        StringBuilder tempAdviseStringBuilder = new StringBuilder();

                        // 执行结果
                        String tempExecResultStr = StringToolUtils.STRING_EMPTY;

                        // 复合人图片地址
                        String tempSignPathStr = StringToolUtils.STRING_EMPTY;

                        // 审核意见List
                        List<HTTaskPeriodBean> retHTTaskPeriodBeanList = tempEachTaskPeriodBean.getHTTaskPeriodBeanList();

                        HTTaskPeriodBean tempHTTaskPeriodBean = null;

                        for (int k=0; k<retHTTaskPeriodBeanList.size();k++){

                            tempHTTaskPeriodBean = retHTTaskPeriodBeanList.get(k);

                            // 初审日期赋值  找最后一次审核时间即可 由于是按时间正序排序 最后一个 就是初审日期
                            tempFirstAuditDateStr = DateToolUtils.convertDateFormat(tempHTTaskPeriodBean.getApproveTime(),DateToolUtils.yyyy_MM_dd);

                            // 序号
                            tempAdviseStringBuilder.append(k+1).append("、");

                            // 判断是否是通过审核
                            if (StringToolUtils.STRING_ONE.equals(tempHTTaskPeriodBean.getIsPass())){
                                // 拼接已确认
                                tempAdviseStringBuilder.append("通过");
                                // 执行日期
                                tempExecDateStr = DateToolUtils.convertDateFormat(tempHTTaskPeriodBean.getApproveTime(),DateToolUtils.yyyy_MM_dd);
                                // 通过日期
                                tempPassDateStr = DateToolUtils.convertDateFormat(tempHTTaskPeriodBean.getApproveTime(),DateToolUtils.yyyy_MM_dd);
                                // 复合人图片地址
                                tempSignPathStr = StringToolUtils.convertNullObjectToString(tempHTTaskPeriodBean.getSignPath());

                            }else {

                                // 拼接拒绝原因
                                tempAdviseStringBuilder.append(StringToolUtils.convertNullObjectToString(tempHTTaskPeriodBean.getRejectReason()));
                            }
                            tempAdviseStringBuilder.append("\r\n");


                            // 如果首次提审就通过 填写符号"-"
                            if (retHTTaskPeriodBeanList.size() == 1 && StringToolUtils.STRING_ONE.equals(tempHTTaskPeriodBean.getIsPass())){

                                tempExecResultStr = "-";

                                // 驳回后审核通过填写已修改
                            }else if (retHTTaskPeriodBeanList.size() > 1 && StringToolUtils.STRING_ONE.equals(tempHTTaskPeriodBean.getIsPass())){

                                tempExecResultStr = "已修改";

                            }

                        }


                        int tempAuditLevelRowInt = 0;

                        int tempDateRowInt = 0;
                        // 如果是一级审核 就画第四行
                        if (TaskA3Enum.ManagerVerify.getNodeKey().equals(tempEachTaskPeriodBean.getTaskKey())){

                            tempAuditLevelRowInt = 4;

                            tempDateRowInt = 5;

                        }else if(TaskA3Enum.QualityVerify.getNodeKey().equals(tempEachTaskPeriodBean.getTaskKey())){

                            tempAuditLevelRowInt = 6;

                            tempDateRowInt = 7;

                        }else {
                            tempAuditLevelRowInt = 8;

                            tempDateRowInt = 9;

                        }

                        // 复核意见
                        tempCell = tempThisSheet.getRow(tempAuditLevelRowInt).getCell(1);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellStyle(cellStyleA3);
                        tempCell.setCellValue(tempAdviseStringBuilder.toString());


                        // 执行结果
                        tempCell = tempThisSheet.getRow(tempAuditLevelRowInt).getCell(2);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue(tempExecResultStr);


                        // 如果签字图片不为空  证明已经审核通过 所以插入图片
                        if (!StringToolUtils.isNull(tempSignPathStr)){

                            // 插入复核人 签名图片
                            byteArrayOut = new ByteArrayOutputStream();
                            bufferImg = ImageIO.read(new File(fileRootPath + tempSignPathStr));
                            ImageIO.write(bufferImg, "png", byteArrayOut);
                            //anchor主要用于设置图片的属性
                            anchor = new HSSFClientAnchor(10, 10, 1023, 255,(short) 3, tempAuditLevelRowInt, (short) 3, tempAuditLevelRowInt);
                            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                            //插入图片
                            tempPatriarch.createPicture(anchor, tmpWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

                        }

                        // 初审时间
                        tempCell = tempThisSheet.getRow(tempDateRowInt).getCell(1);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue("初审日期：" + tempFirstAuditDateStr);

                        // 执行日期
                        tempCell = tempThisSheet.getRow(tempDateRowInt).getCell(2);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue("执行日期：" + tempExecDateStr);

                        // 通过日期
                        tempCell = tempThisSheet.getRow(tempDateRowInt).getCell(3);
                        tempCell.setCellType(CellType.STRING);
                        tempCell.setCellValue("通过日期：" + tempPassDateStr);

                    }
                }

                // 如果存在A3 则删除A3sheet页
                if (retA3AuditRecordList.size() > 0){
                    // 删除A3 sheet模板页
                    tmpWorkbook.removeSheetAt(2);
                }


            }else {

                HTTenderTaskHisTaskInstBean paramTenderTaskBean = new HTTenderTaskHisTaskInstBean();

                // 设置投标任务id
                paramTenderTaskBean.setTenderTaskId(tempTenderTaskIdStr);

                // 查询投标任务A3审核记录记录
                List<HTTenderTaskHisTaskInstBean> retTenderTaskBeanList = htTenderTaskHisTaskInstDao.excSearchHTTenderTaskHisTaskInstInfoList(paramTenderTaskBean);

                Sheet tempA3Sheet = tmpWorkbook.getSheetAt(2);

                CellStyle cellStyleA3 = tmpWorkbook.createCellStyle();
                // 自动换行
                cellStyleA3.setWrapText(true);

                cellStyleA3.setAlignment(HorizontalAlignment.LEFT);
                cellStyleA3.setVerticalAlignment(VerticalAlignment.CENTER);

                // 字体
                Font font = tmpWorkbook.createFont();
                // 10号字
                font.setFontHeight((short) (10 * 20));
                // 字体
                font.setFontName("黑体");
                // 设置字体
                cellStyleA3.setFont(font);

                // 画图的顶级管理器
                HSSFPatriarch tempPatriarch = (HSSFPatriarch) tempA3Sheet.createDrawingPatriarch();
                HTTenderTaskHisTaskInstBean tempTenderTaskBean = null;
                for (int j=0;j<retTenderTaskBeanList.size();j++){

                    tempTenderTaskBean = retTenderTaskBeanList.get(j);

                    // 如果是签发 跳出本次循环
                    if (TaskA3Enum.Signer.getNodeKey().equals(tempTenderTaskBean.getTaskKey())){

                        continue;
                    }

                    // 初审日期
                    String tempFirstAuditDateStr = StringToolUtils.STRING_EMPTY;

                    // 执行日期
                    String tempExecDateStr = StringToolUtils.STRING_EMPTY;

                    // 通过日期
                    String tempPassDateStr = StringToolUtils.STRING_EMPTY;

                    // 复合意见
                    StringBuilder tempAdviseStringBuilder = new StringBuilder();

                    // 执行结果
                    String tempExecResultStr = StringToolUtils.STRING_EMPTY;

                    // 复合人图片地址
                    String tempSignPathStr = StringToolUtils.STRING_EMPTY;

                    // 审核意见List
                    List<HTTenderTaskHisTaskInstBean> retHTTenderTaskBeanList = tempTenderTaskBean.getHTTenderTaskHisTaskInstBeanList();

                    HTTenderTaskHisTaskInstBean tempHTTenderTaskHisTaskInstBean = null;

                    for (int k=0; k<retHTTenderTaskBeanList.size();k++){

                        tempHTTenderTaskHisTaskInstBean = retHTTenderTaskBeanList.get(k);

                        // 初审日期赋值  找最后一次审核时间即可 由于是按时间正序排序 最后一个 就是初审日期
                        tempFirstAuditDateStr = DateToolUtils.convertDateFormat(tempHTTenderTaskHisTaskInstBean.getApproveTime(),DateToolUtils.yyyy_MM_dd);

                        // 序号
                        tempAdviseStringBuilder.append(k+1).append("、");

                        // 判断是否是通过审核
                        if (StringToolUtils.STRING_ONE.equals(tempHTTenderTaskHisTaskInstBean.getIsPass())){
                            // 拼接已确认
                            tempAdviseStringBuilder.append("通过");
                            // 执行日期
                            tempExecDateStr = DateToolUtils.convertDateFormat(tempHTTenderTaskHisTaskInstBean.getApproveTime(),DateToolUtils.yyyy_MM_dd);
                            // 通过日期
                            tempPassDateStr = DateToolUtils.convertDateFormat(tempHTTenderTaskHisTaskInstBean.getApproveTime(),DateToolUtils.yyyy_MM_dd);
                            // 复合人图片地址
                            tempSignPathStr = StringToolUtils.convertNullObjectToString(tempHTTenderTaskHisTaskInstBean.getSignPath());

                        }else {

                            // 拼接拒绝原因
                            tempAdviseStringBuilder.append(StringToolUtils.convertNullObjectToString(tempHTTenderTaskHisTaskInstBean.getRejectReason()));
                        }
                        tempAdviseStringBuilder.append("\r\n");


                        // 如果首次提审就通过 填写符号"-"
                        if (retHTTenderTaskBeanList.size() == 1 && StringToolUtils.STRING_ONE.equals(tempHTTenderTaskHisTaskInstBean.getIsPass())){

                            tempExecResultStr = "-";

                            // 驳回后审核通过填写已修改
                        }else if (retHTTenderTaskBeanList.size() > 1 && StringToolUtils.STRING_ONE.equals(tempHTTenderTaskHisTaskInstBean.getIsPass())){

                            tempExecResultStr = "已修改";

                        }

                    }


                    int tempAuditLevelRowInt = 0;

                    int tempDateRowInt = 0;

                    // 如果是一级审核 就画第四行
                    if (TaskA3Enum.ManagerVerify.getNodeKey().equals(tempTenderTaskBean.getTaskKey())){

                        tempAuditLevelRowInt = 4;

                        tempDateRowInt = 5;

                    }else if(TaskA3Enum.QualityVerify.getNodeKey().equals(tempTenderTaskBean.getTaskKey())){

                        tempAuditLevelRowInt = 6;

                        tempDateRowInt = 7;

                    }else {
                        tempAuditLevelRowInt = 8;

                        tempDateRowInt = 9;

                    }
                    Cell tempA3Cell;
                    // 复核意见
                    tempA3Cell = tempA3Sheet.getRow(tempAuditLevelRowInt).getCell(1);
                    tempA3Cell.setCellType(CellType.STRING);
                    tempA3Cell.setCellStyle(cellStyleA3);
                    tempA3Cell.setCellValue(tempAdviseStringBuilder.toString());


                    // 执行结果
                    tempA3Cell = tempA3Sheet.getRow(tempAuditLevelRowInt).getCell(2);
                    tempA3Cell.setCellType(CellType.STRING);
                    tempA3Cell.setCellValue(tempExecResultStr);


                    // 如果签字图片不为空  证明已经审核通过 所以插入图片
                    if (!StringToolUtils.isNull(tempSignPathStr)){

                        // 插入复核人 签名图片
                        byteArrayOut = new ByteArrayOutputStream();

                        bufferImg = ImageIO.read(new File(fileRootPath+tempSignPathStr));

                        ImageIO.write(bufferImg, "png", byteArrayOut);

                        //anchor主要用于设置图片的属性
                        anchor = new HSSFClientAnchor(10, 10, 1023, 255,(short) 3, tempAuditLevelRowInt, (short) 3, tempAuditLevelRowInt);

                        anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);

                        //插入图片
                        tempPatriarch.createPicture(anchor, tmpWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

                    }

                    // 初审时间
                    tempA3Cell = tempA3Sheet.getRow(tempDateRowInt).getCell(1);
                    tempA3Cell.setCellType(CellType.STRING);
                    tempA3Cell.setCellValue("初审日期：" + tempFirstAuditDateStr);

                    // 执行日期
                    tempA3Cell = tempA3Sheet.getRow(tempDateRowInt).getCell(2);
                    tempA3Cell.setCellType(CellType.STRING);
                    tempA3Cell.setCellValue("执行日期：" + tempExecDateStr);

                    // 通过日期
                    tempA3Cell = tempA3Sheet.getRow(tempDateRowInt).getCell(3);
                    tempA3Cell.setCellType(CellType.STRING);
                    tempA3Cell.setCellValue("通过日期：" + tempPassDateStr);

                }

                // 删除多余sheet
                tmpWorkbook.removeSheetAt(13);
                tmpWorkbook.removeSheetAt(12);
                tmpWorkbook.removeSheetAt(11);
                tmpWorkbook.removeSheetAt(10);
                tmpWorkbook.removeSheetAt(9);
                tmpWorkbook.removeSheetAt(8);
                tmpWorkbook.removeSheetAt(7);
                tmpWorkbook.removeSheetAt(6);
                tmpWorkbook.removeSheetAt(5);
                tmpWorkbook.removeSheetAt(4);
                tmpWorkbook.removeSheetAt(3);
                tmpWorkbook.removeSheetAt(1);
                tmpWorkbook.removeSheetAt(0);

            }

            return tmpWorkbook;

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

        }

        return null;
    }

    /**
     * 查询A4信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskExecuteVO excSearchA4Info(HTTaskExecuteVO paramVO) throws SystemException {

        HTTaskExecuteVO retVO = new HTTaskExecuteVO();
        try {
            // 临时变量：任务id
            String tempTaskIdStr = paramVO.getTaskId();
            // 临时变量：任务和阶段关联id
            String tempRelationIdStr = paramVO.getRelationId();

            // 如果任务id不为空
            if (!StringToolUtils.isNull(tempTaskIdStr)) {

                HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
                // 任务id
                paramSearchean.setTaskId(tempTaskIdStr);
                // 任务和阶段关联id
                paramSearchean.setRelationId(tempRelationIdStr);

                // 查询A4信息
                HTTaskExecuteBean resultBean = htTaskExecuteDao.getTaskExecuteDetailInfo(paramSearchean);
                // 查询A4文件列表信息
                List<HTTaskExecuteBean> resultBeanList = htTaskExecuteDao.searchA4MaterialInfo(paramSearchean);

                List<HTTaskExecuteVO> retVOList = new ArrayList<>();

                HTTaskExecuteVO tempVO = null;
                // 如果有数据，则把bean->vo
                if (null != resultBean) {
                    // 合同编号
                    retVO.setContractNum(StringToolUtils.convertNullObjectToString(
                            resultBean.getProjectNum()+"-"+resultBean.getContractNum()));

                    // 项目名称
                    retVO.setProjectName(StringToolUtils.convertNullObjectToString(resultBean.getProjectName()));

                    // 业务类别
                    String tempCounselBusinessType = "";
                    if(!StringToolUtils.isNull(resultBean.getCounselBusinessType())){
                        tempCounselBusinessType = BusinessUtils.convertCounselBusinessTypeToStateStr(
                                NumberToolUtils.convertNullToInteger(resultBean.getCounselBusinessType()));
                    }
                    retVO.setCounselBusinessType(tempCounselBusinessType);

                    // 委托方
                    retVO.setTrusterName(StringToolUtils.convertNullObjectToString(resultBean.getTrusterName()));
                    // 签收方
                    retVO.setTrusterDutyUser(StringToolUtils.convertNullObjectToString(resultBean.getTrusterDutyUser()));
                    // 经手人
                    retVO.setOperatorName(StringToolUtils.convertNullObjectToString(resultBean.getOperatorName()));

                    // 如果结果集不为空
                    if (null != resultBeanList && resultBeanList.size() > 0) {

                        for (HTTaskExecuteBean tempEachBean : resultBeanList) {

                            tempVO = new HTTaskExecuteVO();
                            // 主键id
                            tempVO.setRelationId(StringToolUtils.convertNullObjectToString(tempEachBean.getRelationId()));
                            // 文件名
                            tempVO.setMaterialName(StringToolUtils.convertNullObjectToString(tempEachBean.getMaterialName()));
                            // 设置备注
                            tempVO.setRemark(StringToolUtils.convertNullObjectToString(tempEachBean.getRemark()));
                            // 文件类型
                            tempVO.setFileTypeName(StringToolUtils.convertNullObjectToString(tempEachBean.getFileTypeName()));
                            // 提交人
                            tempVO.setUserName(StringToolUtils.convertNullObjectToString(tempEachBean.getUserName()));
                            // 提交时间
                            tempVO.setCreateDate(DateToolUtils.convertDateFormat(tempEachBean.getCreateDate(), DateToolUtils.yyyy_MM_dd));
                            // 原件/复印件
                            tempVO.setCopyFile(StringToolUtils.convertNullObjectToString(tempEachBean.getCopyFile()));
                            // 份数
                            tempVO.setNum(StringToolUtils.convertNullObjectToString(tempEachBean.getNum()));
                            // 接收人
                            tempVO.setRecipient(StringToolUtils.convertNullObjectToString(tempEachBean.getRecipient()));
                            // 接收时间
                            tempVO.setRecipientDate(DateToolUtils.convertDateFormat(tempEachBean.getRecipientDate(), DateToolUtils.yyyy_MM_dd));

                            retVOList.add(tempVO);
                        }
                        retVO.setTaskMaterialList(retVOList);
                    }
                }

            }
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retVO;
    }

    /**
     * 查询A5信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskExecuteVO excSearchA5Info(HTTaskExecuteVO paramVO) throws SystemException {

        HTTaskExecuteVO retVO = new HTTaskExecuteVO();
        try {

            // 查询参数bean
            HTTaskExecuteBean paramSearchTaskbean = new HTTaskExecuteBean();
            // 设置关系id
            paramSearchTaskbean.setRelationId(StringToolUtils.convertNullObjectToString(paramVO.getRelationId()));
            // 查询任务id
            HTTaskExecuteBean resultTaskBean = htTaskExecuteDao.searchTaskIdByRelationId(paramSearchTaskbean);

            // 临时变量：任务id
            String tempTaskIdStr = resultTaskBean.getTaskId();

            HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
            // 如果任务id不为空
            if (!StringToolUtils.isNull(tempTaskIdStr)) {
                // 任务id
                paramSearchean.setTaskId(tempTaskIdStr);
                // 查询A5信息
                HTTaskExecuteBean resultBean = htTaskExecuteDao.getTaskExecuteDetailInfo(paramSearchean);

                // 如果有数据，则把bean->vo
                if (null != resultBean) {
                    // 项目名称
                    retVO.setProjectName(StringToolUtils.convertNullObjectToString(resultBean.getProjectName()));
                    // 编制人
                    retVO.setOperatorName(StringToolUtils.convertNullObjectToString(resultBean.getOperatorName()));
                    // 编制时间
                    retVO.setApplyTimeBZ(DateToolUtils.convertDateFormat(resultBean.getApplyTimeBZ(),DateToolUtils.yyyy_MM_dd));
                    // 咨询业务描述
                    retVO.setCounselDesc(StringToolUtils.convertNullObjectToString(resultBean.getCounselDesc()));
                    // 建设规模
                    retVO.setConstructScale(StringToolUtils.convertNullObjectToString(resultBean.getConstructScale()));
                    // 投资额
                    retVO.setInvestedAmount(StringToolUtils.convertNullObjectToString(resultBean.getInvestedAmount()));
                    // 咨询内容与重点
                    retVO.setCounselContent(StringToolUtils.convertNullObjectToString(resultBean.getCounselContent()));
                    // 组长
                    retVO.setCounselGroup(StringToolUtils.convertNullObjectToString(resultBean.getCounselGroup()));
                    // 专业负责人
                    retVO.setCounselDuty(StringToolUtils.convertNullObjectToString(resultBean.getCounselDuty()));
                    // 成员
                    retVO.setMemberList(StringToolUtils.convertNullObjectToString(resultBean.getMemberList()));
                    // 咨询业务小组分工
                    retVO.setDivideWork(StringToolUtils.convertNullObjectToString(resultBean.getDivideWork()));
                }
            }
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retVO;
    }

    /**
     * 添加【招标部-执行任务(咨询记录A8)】信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertA8ZbdlInfo(HTTaskExecuteVO paramVO) throws SystemException {

        try {
            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();
            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(paramVO.getLoginUserInfo().getLoginUserId());
            // 临时变量：达成意见一致时间
            Date tempConsensusTime = null;

            // 查询参数bean
            HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
            // 设置关系id
            paramSearchean.setRelationId(StringToolUtils.convertNullObjectToString(paramVO.getRelationId()));
            // 查询任务id
            HTTaskExecuteBean resultTaskBean = htTaskExecuteDao.searchTaskIdByRelationId(paramSearchean);
            // 临时变量：任务id
            String tempTaskIdStr = resultTaskBean.getTaskId();

            if (!StringToolUtils.isNull(tempTaskIdStr)) {
                // A8关联表主键id
                String tmpRelationId = UUID.randomUUID().toString();

                // 如果任务id不为空
                HTTaskExecuteBean deleteRelationBean = new HTTaskExecuteBean();
                // 任务Id
                deleteRelationBean.setTaskId(tempTaskIdStr);
                // 删除招标代理部A8的扩展表
                htTaskExecuteDao.deleteEightZbdlRelationByTaskId(deleteRelationBean);

                // 招标内容列表参数beanList
                List<HTTaskExecuteBean> paramOpinionBeanList = new ArrayList<>();

                // 临时变量：咨询记录列表
                List<HTTaskExecuteVO> tempOpinionVOList = paramVO.getOpinionList();
                // 如果咨询记录列表不为空
                if (CollectionUtils.isNotEmpty(tempOpinionVOList)) {
                    String tempExpandId = "";
                    HTTaskExecuteBean paramOptionBean = null;
                    int tempSortNum = 0;
                    for (HTTaskExecuteVO tempEachOptionVO : tempOpinionVOList) {
                        tempSortNum = tempSortNum + 1;
                        paramOptionBean = new HTTaskExecuteBean();
                        // A8招标代理扩展关联表id
                        tempExpandId = UUID.randomUUID().toString();
                        paramOptionBean.setExpandId(tempExpandId);
                        // 关系id
                        paramOptionBean.setRelationId(tmpRelationId);
                        // 序号
                        paramOptionBean.setSortNum(tempSortNum);
                        // 咨询意见
                        paramOptionBean.setCounselAdvise(tempEachOptionVO.getCounselAdvise());
                        // 委托方回复意见
                        paramOptionBean.setAnswerAdvise(tempEachOptionVO.getAnswerAdvise());
                        // 创建人
                        paramOptionBean.setCreateUserId(tempLoginUserIdStr);
                        // 创建时间
                        paramOptionBean.setCreateDate(tempCurrentDate);
                        // 删除标识（0：未删除）
                        paramOptionBean.setIsDelFlg(StringToolUtils.STRING_ZERO);

                        // 装入招标内容集合中
                        paramOpinionBeanList.add(paramOptionBean);
                    }

                }

                // 如果不为空，插入A8拓展表
                if (CollectionUtils.isNotEmpty(paramOpinionBeanList)) {
                    // 批量插入招标代理部A8的扩展表
                    htTaskExecuteDao.insertEightZbdlRelationList(paramOpinionBeanList);
                }

                // 删除咨询记录A8的关联表
                htTaskExecuteDao.deleteEightRelationByTaskId(deleteRelationBean);

                // A8关联表
                HTTaskExecuteBean insertRelationBean = new HTTaskExecuteBean();
                insertRelationBean.setRelationId(tmpRelationId);
                // A8关联表任务id
                insertRelationBean.setTaskId(tempTaskIdStr);
                // 达成意见一致时间
                tempConsensusTime = DateToolUtils.convertDateFormat(paramVO.getConsensusTime(), DateToolUtils.yyyy_MM_dd);
                insertRelationBean.setConsensusTime(tempConsensusTime);
                // 创建人
                insertRelationBean.setCreateUserId(tempLoginUserIdStr);
                // 创建时间
                insertRelationBean.setCreateDate(tempCurrentDate);
                // 是否禁用flg
                insertRelationBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
                // 插入咨询记录A8的关联表
                htTaskExecuteDao.insertEightRelation(insertRelationBean);

            }
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 添加【A5】信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertA5Info(HTTaskExecuteVO paramVO) throws SystemException {

        try {
            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();
            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(paramVO.getLoginUserInfo().getLoginUserId());

            // 查询参数bean
            HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
            // 设置关系id
            paramSearchean.setRelationId(StringToolUtils.convertNullObjectToString(paramVO.getRelationId()));
            // 查询任务id
            HTTaskExecuteBean resultTaskBean = htTaskExecuteDao.searchTaskIdByRelationId(paramSearchean);
            // 临时变量：任务id
            String tempTaskIdStr = resultTaskBean.getTaskId();

            if (!StringToolUtils.isNull(tempTaskIdStr)) {

                // 如果任务id不为空
                HTTaskExecuteBean deleteRelationBean = new HTTaskExecuteBean();
                // 任务Id
                deleteRelationBean.setTaskId(tempTaskIdStr);
                // 删除A5关联表
                htTaskExecuteDao.deleteFiveRelationByTaskId(deleteRelationBean);

                TbTableFiveRelationEntity insertEntity = new TbTableFiveRelationEntity();

                // 任务id
                insertEntity.setTaskId(tempTaskIdStr);
                // 咨询业务简述
                insertEntity.setCounselDesc(paramVO.getCounselDesc());
                // 建设规模
                insertEntity.setConstructScale(paramVO.getConstructScale());
                // 咨询内容与重点
                insertEntity.setCounselContent(paramVO.getCounselContent());
                // 组长
                insertEntity.setCounselGroup(paramVO.getCounselGroup());
                // 专业负责人
                insertEntity.setCounselDuty(paramVO.getCounselDuty());
                // 咨询业务小组分工
                insertEntity.setDivideWork(paramVO.getDivideWork());
                // 创建人
                insertEntity.setCreateUserId(tempLoginUserIdStr);
                // 创建时间
                insertEntity.setCreateDate(tempCurrentDate);
                // 删除标识（0：未删除）
                insertEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);

                // 插入A5关联表数据
                tbTableFiveRelationEntityMapper.insertSelective(insertEntity);

             }
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }



    /**
     * 添加【造价部-执行任务(修改完善A6)】信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertA6Info(HTTaskExecuteVO paramVO) throws SystemException {

        try {
            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();
            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(paramVO.getLoginUserInfo().getLoginUserId());

            // 查询参数bean
            HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
            // 设置关系id
            paramSearchean.setRelationId(StringToolUtils.convertNullObjectToString(paramVO.getRelationId()));
            // 查询任务id
            HTTaskExecuteBean resultTaskBean = htTaskExecuteDao.searchTaskIdByRelationId(paramSearchean);
            // 临时变量：任务id
            String tempTaskIdStr = resultTaskBean.getTaskId();

            if (!StringToolUtils.isNull(tempTaskIdStr)) {

                // 如果任务id不为空
                HTTaskExecuteBean deleteRelationBean = new HTTaskExecuteBean();
                // 任务Id
                deleteRelationBean.setTaskId(tempTaskIdStr);
                // 删除A6关联表
                htTaskExecuteDao.deleteSixRelationByTaskId(deleteRelationBean);

                // A6列表参数beanList
                List<HTTaskExecuteBean> paramUnitProjectBeanList = new ArrayList<>();

                // 临时变量：咨询记录列表
                List<HTTaskExecuteVO> tempUnitProjectVOList = paramVO.getUnitProjectList();
                // 如果咨询记录列表不为空
                if (CollectionUtils.isNotEmpty(tempUnitProjectVOList)) {
                    String tmpRelationId = "";
                    HTTaskExecuteBean paramUnitProjectBean = null;
                    int tempSortNum = 0;
                    for (HTTaskExecuteVO tempEachUnitProjectVO : tempUnitProjectVOList) {
                        // A8关联表主键id
                        tmpRelationId = UUID.randomUUID().toString();
                        tempSortNum = tempSortNum + 1;
                        paramUnitProjectBean = new HTTaskExecuteBean();
                        // A6招标代理扩展关联表id
                        // 关系id
                        paramUnitProjectBean.setRelationId(tmpRelationId);
                        // 任务id
                        paramUnitProjectBean.setTaskId(tempTaskIdStr);
                        // 序号
                        paramUnitProjectBean.setSortNum(tempSortNum);
                        // 咨询意见
                        paramUnitProjectBean.setCounselAdvise(tempEachUnitProjectVO.getCounselAdvise());
                        // 承包人回复意见
                        paramUnitProjectBean.setResponseAdvise(tempEachUnitProjectVO.getResponseAdvise());
                        // 创建人
                        paramUnitProjectBean.setCreateUserId(tempLoginUserIdStr);
                        // 创建时间
                        paramUnitProjectBean.setCreateDate(tempCurrentDate);
                        // 删除标识（0：未删除）
                        paramUnitProjectBean.setIsDelFlg(StringToolUtils.STRING_ZERO);

                        // A6集合中
                        paramUnitProjectBeanList.add(paramUnitProjectBean);
                    }

                }

                if (CollectionUtils.isNotEmpty(paramUnitProjectBeanList)) {
                    // 批量插入A6关联表
                    htTaskExecuteDao.insertSixRelationList(paramUnitProjectBeanList);
                }

            }
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 添加【招标部-执行任务(拟定招标规划A6)】信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertA6ZbdlInfo(HTTaskExecuteVO paramVO) throws SystemException {

        try {
            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();
            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(paramVO.getLoginUserInfo().getLoginUserId());

            // 查询参数bean
            HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
            // 设置关系id
            paramSearchean.setRelationId(StringToolUtils.convertNullObjectToString(paramVO.getRelationId()));
            // 查询任务id
            HTTaskExecuteBean resultTaskBean = htTaskExecuteDao.searchTaskIdByRelationId(paramSearchean);
            // 临时变量：任务id
            String tempTaskIdStr = resultTaskBean.getTaskId();

            if (!StringToolUtils.isNull(tempTaskIdStr)) {

                // 如果任务id不为空
                HTTaskExecuteBean deleteRelationBean = new HTTaskExecuteBean();
                // 任务Id
                deleteRelationBean.setTaskId(tempTaskIdStr);
                // 删除A6关联表
                htTaskExecuteDao.deleteSixRelationByTaskId(deleteRelationBean);

                // A6列表参数beanList
                List<HTTaskExecuteBean> paramUnitProjectBeanList = new ArrayList<>();

                // 临时变量：咨询记录列表
                List<HTTaskExecuteVO> tempUnitProjectVOList = paramVO.getUnitProjectList();
                // 如果咨询记录列表不为空
                if (CollectionUtils.isNotEmpty(tempUnitProjectVOList)) {
                    String tmpRelationId = "";
                    HTTaskExecuteBean paramUnitProjectBean = null;
                    int tempSortNum = 0;
                    for (HTTaskExecuteVO tempEachUnitProjectVO : tempUnitProjectVOList) {
                        // A8关联表主键id
                        tmpRelationId = UUID.randomUUID().toString();
                        tempSortNum = tempSortNum + 1;
                        paramUnitProjectBean = new HTTaskExecuteBean();
                        // A6招标代理扩展关联表id
                        // 关系id
                        paramUnitProjectBean.setRelationId(tmpRelationId);
                        // 任务id
                        paramUnitProjectBean.setTaskId(tempTaskIdStr);
                        // 序号
                        paramUnitProjectBean.setSortNum(tempSortNum);
                        // 工程名称
                        paramUnitProjectBean.setEngineeringName(tempEachUnitProjectVO.getEngineeringName());
                        // 招标方式
                        paramUnitProjectBean.setTenderMethod(tempEachUnitProjectVO.getTenderMethod());
                        // 招标形式
                        paramUnitProjectBean.setTenderType(tempEachUnitProjectVO.getTenderType());
                        // 招标平台
                        paramUnitProjectBean.setTenderPlatform(tempEachUnitProjectVO.getTenderPlatform());
                        // 招标人资格要求
                        paramUnitProjectBean.setInvestorRequire(tempEachUnitProjectVO.getInvestorRequire());
                        // 创建人
                        paramUnitProjectBean.setCreateUserId(tempLoginUserIdStr);
                        // 创建时间
                        paramUnitProjectBean.setCreateDate(tempCurrentDate);
                        // 删除标识（0：未删除）
                        paramUnitProjectBean.setIsDelFlg(StringToolUtils.STRING_ZERO);

                        // A6集合中
                        paramUnitProjectBeanList.add(paramUnitProjectBean);
                    }

                }

                if (CollectionUtils.isNotEmpty(paramUnitProjectBeanList)) {
                    // 批量插入A6关联表
                    htTaskExecuteDao.insertSixZbdlRelationList(paramUnitProjectBeanList);
                }

            }
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 添加【造价部-执行任务(踏勘现场A8)】信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertA8Info(HTTaskExecuteVO paramVO) throws SystemException {

        try {
            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();
            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(paramVO.getLoginUserInfo().getLoginUserId());

            // 查询参数bean
            HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
            // 设置关系id
            paramSearchean.setRelationId(StringToolUtils.convertNullObjectToString(paramVO.getRelationId()));
            // 查询任务id
            HTTaskExecuteBean resultTaskBean = htTaskExecuteDao.searchTaskIdByRelationId(paramSearchean);
            // 临时变量：任务id
            String tempTaskIdStr = resultTaskBean.getTaskId();

            if (!StringToolUtils.isNull(tempTaskIdStr)) {
                // 如果任务id不为空
                HTTaskExecuteBean deleteRelationBean = new HTTaskExecuteBean();
                // 任务Id
                deleteRelationBean.setTaskId(tempTaskIdStr);
                // 删除咨询记录A8的关联表
                htTaskExecuteDao.deleteEightRelationByTaskId(deleteRelationBean);

                // A8关联表
                TbTableEightRelationEntity insertEntity = new TbTableEightRelationEntity();
                // A8关联表任务id
                insertEntity.setTaskId(tempTaskIdStr);
                // 发包人
                insertEntity.setEmployer(paramVO.getEmployerA8());
                // 承包人
                insertEntity.setContractor(paramVO.getContractor());
                // 监理人
                insertEntity.setSupervisor(paramVO.getSupervisor());
                // 现场勘查记录
                insertEntity.setProspectRecord(paramVO.getProspectRecord());
                // 创建人
                insertEntity.setCreateUserId(tempLoginUserIdStr);
                // 创建时间
                insertEntity.setCreateDate(tempCurrentDate);
                // 是否禁用flg
                insertEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
                // 插入咨询记录A8的关联表
                tbTableEightRelationEntityMapper.insertSelective(insertEntity);

            }
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }



    /**
     * 检索【A8】信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskExecuteVO excSearchA8Info(HTTaskExecuteVO paramVO) throws SystemException {

        HTTaskExecuteVO retVO = new HTTaskExecuteVO();
        try {
            // 查询参数bean
            HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
            // 设置关系id
            paramSearchean.setRelationId(StringToolUtils.convertNullObjectToString(paramVO.getRelationId()));
            // 查询任务id
            HTTaskExecuteBean resultTaskBean = htTaskExecuteDao.searchTaskIdByRelationId(paramSearchean);

            List<HTTaskExecuteVO> retOpinionVOList = new ArrayList<>();

            // 如果任务id不为空
            if (null != resultTaskBean && !StringToolUtils.isNull(resultTaskBean.getTaskId())) {
                // 任务id
                paramSearchean.setTaskId(resultTaskBean.getTaskId());
                // 查询A8基本信息
                HTTaskExecuteBean resultBean = htTaskExecuteDao.getTaskExecuteDetailInfo(paramSearchean);
                // 查询咨询记录A8列表
                List<HTTaskExecuteBean> resultBeanList = htTaskExecuteDao.searchA8ConsultRecordZB(paramSearchean);

                // 如果有数据，则把bean->vo
                if (null != resultBean) {
                    // 项目名称
                    retVO.setProjectName(StringToolUtils.convertNullObjectToString(resultBean.getProjectName()));
                    // 达成意见一致时间
                    retVO.setConsensusTime(DateToolUtils.convertDateFormat(resultBean.getConsensusTime(),DateToolUtils.yyyy_MM_dd));
                    // 发包人
                    retVO.setEmployerA8(StringToolUtils.convertNullObjectToString(resultBean.getEmployerA8()));
                    // 承包人
                    retVO.setContractor(StringToolUtils.convertNullObjectToString(resultBean.getContractor()));
                    // 监理人
                    retVO.setSupervisor(StringToolUtils.convertNullObjectToString(resultBean.getSupervisor()));
                    // 现场勘查记录
                    retVO.setProspectRecord(StringToolUtils.convertNullObjectToString(resultBean.getProspectRecord()));
                    // 咨询合同编号
                    retVO.setContractNum(StringToolUtils.convertNullObjectToString(
                            resultBean.getProjectNum()+"-"+resultBean.getContractNum()));

                    // 咨询记录A8列表不为空
                    if (CollectionUtils.isNotEmpty(resultBeanList)) {
                        HTTaskExecuteVO tempOpinionVO = null;

                        for (HTTaskExecuteBean tempEachBean : resultBeanList) {
                            tempOpinionVO = new HTTaskExecuteVO();
                            // 序号
                            tempOpinionVO.setSortNum(StringToolUtils.convertNullObjectToString(tempEachBean.getSortNum()));
                            // 咨询意见
                            tempOpinionVO.setCounselAdvise(StringToolUtils.convertNullObjectToString(tempEachBean.getCounselAdvise()));
                            // 委托方回复意见
                            tempOpinionVO.setAnswerAdvise(StringToolUtils.convertNullObjectToString(tempEachBean.getAnswerAdvise()));
                            retOpinionVOList.add(tempOpinionVO);
                        }
                    }
                }

                // 咨询记录列表
                retVO.setOpinionList(retOpinionVOList);
            }
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retVO;
    }

    /**
     * 检索【A6】信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskExecuteVO excSearchA6Info(HTTaskExecuteVO paramVO) throws SystemException {

        HTTaskExecuteVO retVO = new HTTaskExecuteVO();
        try {
            // 查询参数bean
            HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
            // 设置关系id
            paramSearchean.setRelationId(StringToolUtils.convertNullObjectToString(paramVO.getRelationId()));
            // 查询任务id
            HTTaskExecuteBean resultTaskBean = htTaskExecuteDao.searchTaskIdByRelationId(paramSearchean);

            List<HTTaskExecuteVO> retUnitProjectList = new ArrayList<>();

            // 如果任务id不为空
            if (null != resultTaskBean && !StringToolUtils.isNull(resultTaskBean.getTaskId())) {
                // 任务id
                paramSearchean.setTaskId(resultTaskBean.getTaskId());
                // 查询A6基本信息
                HTTaskExecuteBean resultBean = htTaskExecuteDao.getTaskExecuteDetailInfo(paramSearchean);
                // 查询咨询记录A6列表
                List<HTTaskExecuteBean> resultBeanList = htTaskExecuteDao.searchA6Record(paramSearchean);

                // 如果有数据，则把bean->vo
                if (null != resultBean) {
                    // 项目名称
                    retVO.setProjectName(StringToolUtils.convertNullObjectToString(resultBean.getProjectName()));
                    // 提审时间
                    retVO.setApplyTimeBZ(DateToolUtils.convertDateFormat(resultBean.getApplyTimeBZ(),DateToolUtils.yyyy_MM_dd));
                    // 咨询合同编号
                    retVO.setContractNum(StringToolUtils.convertNullObjectToString(
                            resultBean.getProjectNum()+"-"+resultBean.getContractNum()));

                    // 咨询记录A8列表不为空
                    if (CollectionUtils.isNotEmpty(resultBeanList)) {
                        HTTaskExecuteVO tempOpinionVO = null;

                        for (HTTaskExecuteBean tempEachBean : resultBeanList) {
                            tempOpinionVO = new HTTaskExecuteVO();
                            // 序号
                            tempOpinionVO.setSortNum(StringToolUtils.convertNullObjectToString(tempEachBean.getSortNum()));
                            // 咨询意见
                            tempOpinionVO.setCounselAdvise(StringToolUtils.convertNullObjectToString(tempEachBean.getCounselAdvise()));
                            // 承包人回复意见
                            tempOpinionVO.setResponseAdvise(StringToolUtils.convertNullObjectToString(tempEachBean.getResponseAdvise()));
                            // 工程名称
                            tempOpinionVO.setEngineeringName(StringToolUtils.convertNullObjectToString(tempEachBean.getEngineeringName()));
                            // 招标方式
                            tempOpinionVO.setTenderMethod(StringToolUtils.convertNullObjectToString(tempEachBean.getTenderMethod()));
                            // 招标形式
                            tempOpinionVO.setTenderType(StringToolUtils.convertNullObjectToString(tempEachBean.getTenderType()));
                            // 招标平台
                            tempOpinionVO.setTenderPlatform(StringToolUtils.convertNullObjectToString(tempEachBean.getTenderPlatform()));
                            // 投资人资格要求
                            tempOpinionVO.setInvestorRequire(StringToolUtils.convertNullObjectToString(tempEachBean.getInvestorRequire()));
                            retUnitProjectList.add(tempOpinionVO);
                        }
                    }
                }

                // A6列表
                retVO.setUnitProjectList(retUnitProjectList);
            }
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retVO;
    }


    /**
     * 添加A7招标信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertA7ZbdlInfo(HTTaskExecuteVO paramVO) throws SystemException {

        try {
            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();
            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(paramVO.getLoginUserInfo().getLoginUserId());
            // 临时变量：资料最后上传开始时间
            Date tempStartDateDate = null;
            // 临时变量：资料最后上传结束时间
            Date tempEndDateDate = null;
            // 临时变量：资料最后上传时间数组
            String[] tempTimeArr = paramVO.getTimeArr();
            // 如果资料最后上传时间不为空
            if (null != tempTimeArr && tempTimeArr.length > 1) {
                // 资料最后上传开始时间
                tempStartDateDate = DateToolUtils.convertDateFormat(tempTimeArr[0], DateToolUtils.yyyy_MM_dd);
                // 资料最后上传结束时间
                tempEndDateDate = DateToolUtils.convertDateFormat(tempTimeArr[1], DateToolUtils.yyyy_MM_dd);
            }
            // 临时变量：节点开始时间
            Date tempNodeStartDateDate = null;
            // 临时变量：节点结束时间
            Date tempNodeEndDateDate = null;
            // 主表UUID
            String opinionUUID = "";
            // 拓展表UUID
            String flowUUID = "";
            // 临时变量：任务id
            String tempTaskIdStr = paramVO.getTaskId();

            // 如果任务id不为空
            if (!StringToolUtils.isNull(tempTaskIdStr)) {
                // 查询参数bean
                HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
                // 任务id
                paramSearchean.setTaskId(tempTaskIdStr);
                // 招标内容列表参数beanList
                List<HTTaskExecuteBean> paramOpinionBeanList = new ArrayList<>();
                // 工作流程及信息列表参数beanList
                List<HTTaskExecuteBean> paramFlowBeanList = new ArrayList<>();

                // 临时变量：招标内容列表
                List<HTTaskExecuteVO> tempOpinionVOList = paramVO.getOpinionList();
                // 临时变量：工作流程及信息列表
                List<HTTaskExecuteVO> tempFlowVOList = null;

                HTTaskExecuteBean paramOpinionBean = null;
                // 如果招标内容列表不为空
                if (null != tempOpinionVOList && tempOpinionVOList.size() > 0) {

                    for (HTTaskExecuteVO tempEachOptionVO : tempOpinionVOList) {

                        paramOpinionBean = new HTTaskExecuteBean();
                        // A7关联表uuid
                        opinionUUID = UUID.randomUUID().toString();
                        // 关系id
                        paramOpinionBean.setRelationId(opinionUUID);
                        // 任务id
                        paramOpinionBean.setTaskId(tempTaskIdStr);
                        // 招标类型
                        paramOpinionBean.setTenderTypeContent(tempEachOptionVO.getTenderTypeContent());
                        // 资料最后上传时间(开始时间)
                        paramOpinionBean.setStartDate(tempStartDateDate);
                        // 资料最后上传时间(结束时间)
                        paramOpinionBean.setEndDate(tempEndDateDate);
                        // 创建人
                        paramOpinionBean.setCreateUserId(tempLoginUserIdStr);
                        // 创建时间
                        paramOpinionBean.setCreateDate(tempCurrentDate);
                        // 删除标识（0：未删除）
                        paramOpinionBean.setIsDelFlg(StringToolUtils.STRING_ZERO);

                        // 工作流程及信息集合
                        tempFlowVOList = tempEachOptionVO.getFlowList();
                        // 如果工作流程及信息列表不为空
                        if (null != tempFlowVOList && tempFlowVOList.size() > 0) {
                            // 序号
                            int index = 1;

                            HTTaskExecuteBean tempEachFlowBean = null;

                            for (HTTaskExecuteVO tempFlowEachVO : tempFlowVOList) {

                                tempEachFlowBean = new HTTaskExecuteBean();
                                // A7拓展表uuid
                                flowUUID = UUID.randomUUID().toString();
                                // 扩展主键id
                                tempEachFlowBean.setExpandId(flowUUID);
                                // A7关联表id
                                tempEachFlowBean.setRelationId(opinionUUID);
                                // 序号
                                tempEachFlowBean.setSortNum(index);
                                // 工作流程
                                tempEachFlowBean.setWorkflow(tempFlowEachVO.getWorkflow());
                                // 天数
                                int tempDays = 0;
                                // 临时变量：节点时间数组
                                String[] tempNodeArr = tempFlowEachVO.getNodeArr();
                                // 如果节点时间不为空
                                if (null != tempTimeArr && tempTimeArr.length > 0) {
                                    // 节点开始时间
                                    tempNodeStartDateDate = DateToolUtils.convertDateFormat(tempNodeArr[0], DateToolUtils.yyyy_MM_dd);
                                    // 节点结束时间
                                    tempNodeEndDateDate = DateToolUtils.convertDateFormat(tempNodeArr[1], DateToolUtils.yyyy_MM_dd);
                                    // 如果节点开始时间与节点结束时间不为空
                                    if (null != tempNodeStartDateDate && null != tempNodeEndDateDate) {
                                        // 天数 = 节点结束时间 - 节点开始时间
                                        tempDays = DateToolUtils.differentDaysByMillisecond(tempNodeStartDateDate, tempNodeEndDateDate);
                                    }
                                }
                                // 天数
                                tempEachFlowBean.setDays(tempDays);
                                // 开始时间（节点）
                                tempEachFlowBean.setStartDate(tempNodeStartDateDate);
                                // 结束时间（节点）
                                tempEachFlowBean.setEndDate(tempNodeEndDateDate);
                                // 涉及部门
                                tempEachFlowBean.setInvolvedDepartment(tempFlowEachVO.getInvolvedDepartment());
                                // 备注
                                tempEachFlowBean.setRemark(tempFlowEachVO.getRemark());
                                // 创建人
                                tempEachFlowBean.setCreateUserId(tempLoginUserIdStr);
                                // 创建时间
                                tempEachFlowBean.setCreateDate(tempCurrentDate);
                                // 删除标识（0：未删除）
                                tempEachFlowBean.setIsDelFlg(StringToolUtils.STRING_ZERO);
                                // 装入工作流程及信息集合中
                                paramFlowBeanList.add(tempEachFlowBean);

                                index++;
                            }
                        }
                        // 装入招标内容集合中
                        paramOpinionBeanList.add(paramOpinionBean);
                    }
                } else {

                    paramOpinionBean = new HTTaskExecuteBean();
                    // A7关联表uuid
                    opinionUUID = UUID.randomUUID().toString();
                    // 关系id
                    paramOpinionBean.setRelationId(opinionUUID);
                    // 任务id
                    paramOpinionBean.setTaskId(tempTaskIdStr);
                    // 资料最后上传时间(开始时间)
                    paramOpinionBean.setStartDate(tempStartDateDate);
                    // 资料最后上传时间(结束时间)
                    paramOpinionBean.setEndDate(tempEndDateDate);
                    // 创建人
                    paramOpinionBean.setCreateUserId(tempLoginUserIdStr);
                    // 创建时间
                    paramOpinionBean.setCreateDate(tempCurrentDate);
                    // 删除标识（0：未删除）
                    paramOpinionBean.setIsDelFlg(StringToolUtils.STRING_ZERO);
                    // 装入招标内容集合中
                    paramOpinionBeanList.add(paramOpinionBean);
                }
                // 删除招标代理部A7的扩展表
                htTaskExecuteDao.deleteSevenZbdlRelationByTaskId(paramSearchean);
                // 删除任务阶段和A7的关联表
                htTaskExecuteDao.deleteSevenRelationByTaskId(paramSearchean);
                // 批量插入任务阶段和A7的关联表
                htTaskExecuteDao.insertSevenRelationList(paramOpinionBeanList);
                // 如果不为空，插入A7拓展表
                if (null != paramFlowBeanList && paramFlowBeanList.size() > 0) {
                    // 批量插入招标代理部A7的扩展表
                    htTaskExecuteDao.insertSevenZbdlRelationList(paramFlowBeanList);
                }
            }
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询A7招标信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskExecuteVO excSearchA7ZbdlInfo(HTTaskExecuteVO paramVO) throws SystemException {

        HTTaskExecuteVO retVO = new HTTaskExecuteVO();
        try {
            // 资料最后上传开始时间
            String tempStartDateStr = "";
            // 资料最后上传结束时间
            String tempEndDateStr = "";
            // 临时变量：任务id
            String tempTaskIdStr = paramVO.getTaskId();

            // 查询参数bean
            HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();

            List<HTTaskExecuteVO> retOpinionVOList = new ArrayList<>();

            HTTaskExecuteVO tempOptionVO = null;

            // 如果任务id不为空
            if (!StringToolUtils.isNull(tempTaskIdStr)) {
                // 任务id
                paramSearchean.setTaskId(tempTaskIdStr);
                // 查询A7基本信息
                HTTaskExecuteBean resultBean = htTaskExecuteDao.getTaskExecuteDetailInfo(paramSearchean);
                // 查询招标工作进度计划A7列表
                List<HTTaskExecuteBean> resultBeanList = htTaskExecuteDao.searchA7ConsultRecordInfoZB(paramSearchean);

                // 如果有数据，则把bean->vo
                if (null != resultBean) {
                    // 项目名称
                    retVO.setProjectName(StringToolUtils.convertNullObjectToString(resultBean.getProjectName()));
                    // A7列表不为空
                    if (null != resultBeanList && resultBeanList.size() > 0) {
                        // 资料最后上传开始时间
                        tempStartDateStr = DateToolUtils.convertDateFormat(resultBeanList.get(0).getStartDate(), DateToolUtils.yyyy_MM_dd);
                        // 资料最后上传结束时间
                        tempEndDateStr = DateToolUtils.convertDateFormat(resultBeanList.get(0).getEndDate(), DateToolUtils.yyyy_MM_dd);

                        List<HTTaskExecuteVO> retFlowVOList = null;

                        HTTaskExecuteVO tempFlowVO = null;

                        for (HTTaskExecuteBean tempEachBean : resultBeanList) {

                            retFlowVOList = new ArrayList<>();

                            tempOptionVO = new HTTaskExecuteVO();
                            // 投标类型
                            tempOptionVO.setTenderTypeContent(StringToolUtils.convertNullObjectToString(tempEachBean.getTenderTypeContent()));
                            // 如果工作流程及信息集合不为空
                            if (CollectionUtils.isNotEmpty(tempEachBean.getHtTaskExecuteList())) {

                                for (HTTaskExecuteBean tempFlowEachBean : tempEachBean.getHtTaskExecuteList()) {

                                    tempFlowVO = new HTTaskExecuteVO();
                                    // 关系id
                                    tempFlowVO.setRelationId(StringToolUtils.convertNullObjectToString(tempEachBean.getRelationId()));
                                    // 序号
                                    tempFlowVO.setSortNum(StringToolUtils.convertNullObjectToString(tempFlowEachBean.getSortNum()));
                                    // 工作流程
                                    tempFlowVO.setWorkflow(StringToolUtils.convertNullObjectToString(tempFlowEachBean.getWorkflow()));
                                    // 工作流程名称
                                    tempFlowVO.setWorkflowName(BusinessUtils.convertWorkflowTypeToStateStr(NumberToolUtils.convertNullToInteger(tempFlowEachBean.getWorkflow())));
                                    // 临时变量：节点时间数组
                                    String[] tempNodeArr = {DateToolUtils.convertDateFormat(tempFlowEachBean.getStartDate(), DateToolUtils.yyyy_MM_dd), DateToolUtils.convertDateFormat(tempFlowEachBean.getEndDate(), DateToolUtils.yyyy_MM_dd)};
                                    // 节点时间数组
                                    tempFlowVO.setNodeArr(tempNodeArr);
                                    // 时间
                                    tempFlowVO.setDays(StringToolUtils.convertNullObjectToString(tempFlowEachBean.getDays()));
                                    // 涉及部门
                                    tempFlowVO.setInvolvedDepartment(StringToolUtils.convertNullObjectToString(tempFlowEachBean.getInvolvedDepartment()));
                                    // 备注
                                    tempFlowVO.setRemark((StringToolUtils.convertNullObjectToString(tempFlowEachBean.getRemark())));

                                    retFlowVOList.add(tempFlowVO);
                                }
                            }
                            tempOptionVO.setFlowList(retFlowVOList);

                            retOpinionVOList.add(tempOptionVO);
                        }
                        // 资料最后上传时间数组
                        String[] tempTimeArr = {tempStartDateStr, tempEndDateStr};
                        // 资料最后上传时间数组
                        retVO.setTimeArr(tempTimeArr);
                        // 资料最后上传开始时间
                        retVO.setStartDate(tempStartDateStr);
                        // 资料最后上传结束时间
                        retVO.setEndDate(tempEndDateStr);
                    }

                    // 查询A7招标列表，用于列表页展示
                    List<HTTaskExecuteBean> resultShowBeanList = htTaskExecuteDao.searchA7ZbdlInfo(paramSearchean);
                    // 获取A7招标展示列表
                    List<HTTaskExecuteVO> showVOList = getA7ZbdlShowList(resultShowBeanList);
                    // A7招标展示集合
                    retVO.setShowList(showVOList);
                }
            }
            // 招标内容列表
            retVO.setOpinionList(retOpinionVOList);

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retVO;
    }

    /**
     * 获取A7招标展示列表
     *
     * @param resultBeanList
     * @return
     */
    List<HTTaskExecuteVO> getA7ZbdlShowList(List<HTTaskExecuteBean> resultBeanList) {

        List<HTTaskExecuteVO> retVOList = new ArrayList<>();

        // 查询的A7招标列表不为空
        if (CollectionUtils.isNotEmpty(resultBeanList)) {

            HTTaskExecuteVO tempVO = null;
            // 遍历集合
            for (HTTaskExecuteBean tempEachBean : resultBeanList) {

                tempVO = new HTTaskExecuteVO();
                // 关系id
                tempVO.setRelationId(StringToolUtils.convertNullObjectToString(tempEachBean.getRelationId()));
                // 招标类型
                tempVO.setTenderTypeContent(StringToolUtils.convertNullObjectToString(tempEachBean.getTenderTypeContent()));
                // 序号
                tempVO.setSortNum(StringToolUtils.convertNullObjectToString(tempEachBean.getSortNum()));
                // 工作流程
                tempVO.setWorkflow(StringToolUtils.convertNullObjectToString(tempEachBean.getWorkflow()));
                // 工作流程名称
                tempVO.setWorkflowName(BusinessUtils.convertWorkflowTypeToStateStr(NumberToolUtils.convertNullToInteger(tempEachBean.getWorkflow())));
                // 开始时间（节点）
                tempVO.setStartDate(DateToolUtils.convertDateFormat(tempEachBean.getStartDate(), DateToolUtils.yyyy_MM_dd));
                // 结束时间（节点）
                tempVO.setEndDate(DateToolUtils.convertDateFormat(tempEachBean.getEndDate(), DateToolUtils.yyyy_MM_dd));
                // 时间
                tempVO.setDays(StringToolUtils.convertNullObjectToString(tempEachBean.getDays()));
                // 涉及部门
                tempVO.setInvolvedDepartment(StringToolUtils.convertNullObjectToString(tempEachBean.getInvolvedDepartment()));
                // 备注
                tempVO.setRemark((StringToolUtils.convertNullObjectToString(tempEachBean.getRemark())));

                if (null != tempEachBean.getWorkflow()) {
                    retVOList.add(tempVO);
                }
            }
        }
        return retVOList;
    }

    /**
     * 添加A7造价信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertA7ZJInfo(HTTaskExecuteVO paramVO) throws SystemException {

        try {
            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();
            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(paramVO.getLoginUserInfo().getLoginUserId());
            // 主表UUID
            String uuid = "";
            // 拓展表UUID
            String unitUUID = "";

            // 查询参数bean
            HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
            // 设置关系id
            paramSearchean.setRelationId(StringToolUtils.convertNullObjectToString(paramVO.getRelationId()));
            // 查询任务id及合同id
            HTTaskExecuteBean resultBean = htTaskExecuteDao.searchTaskIdByRelationId(paramSearchean);
            // 任务id
            String tempTaskIdStr = resultBean.getTaskId();
            // 合同id
            String tempContractIdStr = resultBean.getContractId();
            // 工程类别数组
            String[] tempEngineeingTypeArr = paramVO.getEngineeingTypeArr();
            // 工程类别
            String tempEngineeingTypeStr = "";
            // 如果任务id不为
            if (!StringToolUtils.isNull(tempTaskIdStr)) {
                // 任务id
                paramSearchean.setTaskId(tempTaskIdStr);

                HTTaskExecuteBean paramBean = new HTTaskExecuteBean();

                // UUID
                uuid = UUID.randomUUID().toString();
                // 关系id
                paramBean.setRelationId(uuid);
                // 任务id
                paramBean.setTaskId(tempTaskIdStr);
                // 合同id
                paramBean.setContractId(tempContractIdStr);
                // 合同金额
                paramBean.setContractAmount(paramVO.getContractAmount());
                // 结算价格
                paramBean.setSettlePrice(NumberToolUtils.convertNullToDouble(paramVO.getSettlePrice()));
                // 结算价超出合同价的原因
                paramBean.setSettleReason(StringToolUtils.convertNullObjectToString(paramVO.getSettleReason()));
                // 创建人
                paramBean.setCreateUserId(tempLoginUserIdStr);
                // 创建时间
                paramBean.setCreateDate(tempCurrentDate);
                // 修改人
                paramBean.setUpdateUserId(tempLoginUserIdStr);
                // 修改时间
                paramBean.setUpdateDate(tempCurrentDate);
                // 删除标识（0：未删除）
                paramBean.setIsDelFlg(StringToolUtils.STRING_ZERO);

                // 如果数组不为空，并且
                if (null != tempEngineeingTypeArr && tempEngineeingTypeArr.length > 0) {
                    // 工程类别
                    tempEngineeingTypeStr = StringUtils.join(tempEngineeingTypeArr, ",");
                }
                // 工程类别
                paramBean.setEngineeingType(tempEngineeingTypeStr);

                // 单位工程造价指标集合beanList
                List<HTTaskExecuteBean> paramBeanList = new ArrayList<>();
                // 临时变量：单位工程造价指标集合
                List<HTTaskExecuteVO> tempVOList = paramVO.getUnitProjectList();
                // 如果单位工程造价指标集合不为空
                if (CollectionUtils.isNotEmpty(tempVOList)) {

                    HTTaskExecuteBean paramUnitBean = null;
                    // 序号
                    int index = 1;

                    for (HTTaskExecuteVO tempEachVO : tempVOList) {

                        // 扩展表UUID
                        unitUUID = UUID.randomUUID().toString();

                        paramUnitBean = new HTTaskExecuteBean();
                        // 扩展主键id
                        paramUnitBean.setExpandId(unitUUID);
                        // 招标A7的主键id
                        paramUnitBean.setRelationId(uuid);
                        // 序号
                        paramUnitBean.setSortNum(index);
                        // 单位工程名称
                        paramUnitBean.setUnitName(tempEachVO.getUnitName());
                        // 建设规模
                        paramUnitBean.setConstructScale(tempEachVO.getConstructScale());
                        // 造价
                        paramUnitBean.setConstructCost(NumberToolUtils.convertNullToDouble(tempEachVO.getConstructCost()));
                        // 单价
                        paramUnitBean.setUnitCost(NumberToolUtils.convertNullToDouble(tempEachVO.getUnitCost()));
                        // 造价比例
                        paramUnitBean.setCostRatio(NumberToolUtils.convertNullToDouble(tempEachVO.getCostRatio()));
                        // 创建人
                        paramUnitBean.setCreateUserId(tempLoginUserIdStr);
                        // 创建时间
                        paramUnitBean.setCreateDate(tempCurrentDate);
                        // 删除标识（0：未删除）
                        paramUnitBean.setIsDelFlg(StringToolUtils.STRING_ZERO);
                        // 单位工程造价指标集合
                        paramBeanList.add(paramUnitBean);

                        index++;
                    }
                }
                // 删除招标代理部A7的扩展表
                htTaskExecuteDao.deleteSevenZbdlRelationByTaskId(paramSearchean);
                // 删除任务阶段和A7的关联表
                htTaskExecuteDao.deleteSevenRelationByTaskId(paramSearchean);
                // 插入成果文件A7表
                htTaskExecuteDao.insertSevenRelationInfo(paramBean);
                // 修改合同金额
                //htTaskExecuteDao.updateContractAmount(paramBean);

                if (CollectionUtils.isNotEmpty(paramBeanList)) {
                    // 批量插入成果文件A7的扩展表
                    htTaskExecuteDao.insertSevenZJRelationList(paramBeanList);
                }

            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询A7造价信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskExecuteVO excSearchA7ZJInfo(HTTaskExecuteVO paramVO) throws SystemException {

        // 返回VO
        HTTaskExecuteVO retVO = new HTTaskExecuteVO();
        try {
            // 临时变量：工程类别
            String tempEngineeingTypeStr = "";
            // 临时变量：工程类别数组
            String[] tempEngineeingTypeArr = {};
            // 临时变量：任务id
            String tempTaskIdStr = paramVO.getTaskId();

            // 如果任务id不为空，查询A7造价信息
            if (!StringToolUtils.isNull(tempTaskIdStr)) {
                // 查询参数bean
                HTTaskExecuteBean paramSearchean = new HTTaskExecuteBean();
                // 任务id
                paramSearchean.setTaskId(tempTaskIdStr);
                // 查询A7造价基本信息
                HTTaskExecuteBean resultBean = htTaskExecuteDao.getTaskExecuteDetailInfo(paramSearchean);
                // 查询A7造价关联信息
                HTTaskExecuteBean resultUnitBean = htTaskExecuteDao.searchA7AchievementInfoZJ(paramSearchean);

                // 如果A7造价基本信息不为空，则把bean->vo
                if (null != resultBean) {
                    // 合同编号
                    retVO.setContractNum(StringToolUtils.convertNullObjectToString(resultBean.getProjectNum()+"-"+resultBean.getContractNum()));
                    // 项目名称
                    retVO.setProjectName(StringToolUtils.convertNullObjectToString(resultBean.getProjectName()));
                }

                HTTaskExecuteVO tempVO = null;
                // 如果A7造价关联信息不为空，则把bean->vo
                if (null != resultBean && null != resultUnitBean) {
                    // 合同金额
                    retVO.setContractAmount(this.getNumberStr(StringToolUtils.convertNullObjectToString(resultUnitBean.getContractPrice())));
                    // 结算价格
                    retVO.setSettlePrice(this.getNumberStr(StringToolUtils.convertNullObjectToString(resultUnitBean.getSettlePrice())));
                    // 结算价超出合同价的原因（若有）
                    retVO.setSettleReason(StringToolUtils.convertNullObjectToString(resultUnitBean.getSettleReason()));
                    // 工程类别
                    tempEngineeingTypeStr = resultUnitBean.getEngineeingType();
                    // 如果工程类别不为空
                    if (!StringToolUtils.isNull(tempEngineeingTypeStr)) {
                        // 工程类别数组
                        tempEngineeingTypeArr = tempEngineeingTypeStr.split(",");
                    }
                    // 工程类别数组
                    retVO.setEngineeingTypeArr(tempEngineeingTypeArr);
                    // 工程类别名称
                    retVO.setEngineeingTypeName(EngineeingTypeEnum.getTypeName(tempEngineeingTypeStr));

                    // 如果A7造价关联列表不为空
                    if (null != resultUnitBean.getHtTaskExecuteList() && resultUnitBean.getHtTaskExecuteList().size() > 0) {

                        List<HTTaskExecuteVO> retVOList = new ArrayList<>();

                        for (HTTaskExecuteBean tempEachBean : resultUnitBean.getHtTaskExecuteList()) {

                            tempVO = new HTTaskExecuteVO();
                            // 关系id
                            tempVO.setRelationId(StringToolUtils.convertNullObjectToString(tempEachBean.getRelationId()));
                            // 序号
                            tempVO.setSortNum(StringToolUtils.convertNullObjectToString(tempEachBean.getSortNum()));
                            // 单位工程名称
                            tempVO.setUnitName(StringToolUtils.convertNullObjectToString(tempEachBean.getUnitName()));
                            // 建设规模
                            tempVO.setConstructScale(StringToolUtils.convertNullObjectToString(tempEachBean.getConstructScale()));
                            // 造价
                            tempVO.setConstructCost(NumberToolUtils.convertNumberToFormatStringWithHalfUp(tempEachBean.getConstructCost(), NumberToolUtils.INT_SCALE_FLOAT));
                            // 单位造价
                            tempVO.setUnitCost(NumberToolUtils.convertNumberToFormatStringWithHalfUp(tempEachBean.getUnitCost(), NumberToolUtils.INT_SCALE_FLOAT));
                            // 造价比例
                            tempVO.setCostRatio(StringToolUtils.convertNullObjectToString(tempEachBean.getCostRatio()));

                            retVOList.add(tempVO);
                        }
                        retVO.setUnitProjectList(retVOList);
                    }
                }
            }
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retVO;
    }

    /**
     * 查询任务模板信息
     *
     * @param paramVO 任务id
     * @throws SystemException 不可预估的异常
     */
    @Override
    public Map<String, Object> getTaskExecuteTemplateInfo(HTTaskExecuteVO paramVO) throws SystemException{

        Map<String, Object> retMap = new HashedMap();

        TbTaskEntity retTaskEntity = tbTaskEntityMapper.selectByPrimaryKey(paramVO.getTaskId());// 根据任务id查询任务信息

        // 如果查到任务模板信息则将信息传到map里
        if (retTaskEntity != null){

            // 任务成果文件模板json串
            String tempFormworkJsonStr = retTaskEntity.getFormworkContent();

            // 如果json 串不为空则转成map格式
            if (!StringToolUtils.isNull(tempFormworkJsonStr)){

                // json转成map
                retMap = InterfaceUtils.jsonToObject(tempFormworkJsonStr,Map.class);
            }

            TbContractEntity retContractEntity =
                    tbContractEntityMapper.selectByPrimaryKey(retTaskEntity.getContractId());// 根据合同id  查询合同信息

            // 项目编号
            String tempProjectNumStr = StringToolUtils.STRING_EMPTY;

            // 项目地点
            String tempProjectAddressStr = StringToolUtils.STRING_EMPTY;

            // 合同编号
            String tempContractNumStr = StringToolUtils.STRING_EMPTY;

            // 如果查到合同信息 则 设置合同编号
            if (retContractEntity != null){
                // 合同编号
                tempContractNumStr = StringToolUtils.convertNullObjectToString(retContractEntity.getContractNum());
                // 项目地点
                tempProjectAddressStr = StringToolUtils.convertNullObjectToString(retContractEntity.getProjectAddress());
                // 根据项目id  查询项目信息
                TbProjectEntity retProjectEntity = tbProjectEntityMapper
                        .selectByPrimaryKey(retContractEntity.getProjectId());

                // 如果查到项目信息 则 设置项目编号
                if (retProjectEntity != null){
                    tempProjectNumStr = retProjectEntity.getProjectNum();
                }

            }

            retMap.put("contractNum",tempProjectNumStr + "-" + tempContractNumStr);

            // 模板类型
            String tempFormworkTypeStr = retTaskEntity.getFormworkType();

            retMap.put("taskFormworkType",tempFormworkTypeStr);

            Integer tempFormworkTypeInt  = NumberToolUtils.convertNullToInteger(tempFormworkTypeStr);

            // 模板名称
            String tempTaskTemplateNameStr = BusinessUtils.convertTaskTemplateTypeToStateStr(tempFormworkTypeInt);
            //0：编制概算 1：编制估算 2：编制结算 3：编制决算
            //4：编制清标报告 5：招标控制价模版 6：招标清单模版（分包、承包） 7：招标控制价模版（总包）
            //8：预算编制模板 9：概算审核 10：估算审核 11：结算审核
            //12：预算审核 13：合同审核意见（未招标） 14：合同审核意见（招标） 15：进度款审核意见
            //16：变更审核意见 17：预算审核意见
            //18：招标代理 19：评标书面报告

            // 如果成果文件模板为 编制概算
            if ("0".equals(tempFormworkTypeStr)){

                // 编制依据
                String tempInput9Str = "1." + retMap.get("input9");

                retMap.put("input9",tempInput9Str);

                // 建筑工程人工费
                String tempInput14Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input14"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input14",tempInput14Str);

                // 装饰工程人工费
                String tempInput15Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input15"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input15",tempInput15Str);


                // 安装工程人工费
                String tempInput16Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input16"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input16",tempInput16Str);


                // 编制估算
            }else if ("1".equals(tempFormworkTypeStr)){



                // 编制结算
            }else if ("2".equals(tempFormworkTypeStr)){

                // 结算类型数字
                Integer tempStatementTypeInt = NumberToolUtils
                        .convertNullToInteger(retMap.get("input11"));

                // 转成对应文字
                String tempStatementType = BusinessUtils
                        .convertSatementTypeToStateStr(tempStatementTypeInt);

                retMap.put("input11",tempStatementType);

                // 编制决算
            }else if ("3".equals(tempFormworkTypeStr)){

                // 编制清标报告
            }else if ("4".equals(tempFormworkTypeStr)){


                // 控制价
                String tempInput13Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input13"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input13",tempInput13Str);


                // 中标价低于控制价
                String tempInput14Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input14"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input14",tempInput14Str);

                // 招标控制价模版
            }else if ("5".equals(tempFormworkTypeStr)){
                // 编制依据
                String tempInput9Str = "1." + retMap.get("input9");

                retMap.put("input9",tempInput9Str);

                // 建筑工程人工费
                String tempInput12Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input12",tempInput12Str);

                // 装饰工程人工费
                String tempInput13Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input13"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input13",tempInput13Str);


                // 安装工程人工费
                String tempInput14Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input14"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input14",tempInput14Str);


                // 清单项目计价说明
                String tempInput17Str = "4." + retMap.get("input17");

                retMap.put("input17",tempInput17Str);


                // 招标清单模版（分包、承包）
            }else if ("6".equals(tempFormworkTypeStr)){
                // 编制依据
                String tempInput9Str = "1." + retMap.get("input9");

                retMap.put("input9",tempInput9Str);


                // 清单项目计价说明
                String tempInput13Str = "4." + retMap.get("input13");

                retMap.put("input13",tempInput13Str);
                // 招标控制价模版（总包）
            }else if ("7".equals(tempFormworkTypeStr)){
                // 编制依据
                String tempInput9Str = "1." + retMap.get("input9");

                retMap.put("input9",tempInput9Str);

                // 清单项目计价说明
                String tempInput12Str = "4." + retMap.get("input12");

                retMap.put("input12",tempInput12Str);
                // 预算编制模板
            }else if ("8".equals(tempFormworkTypeStr)){
                // 编制依据
                String tempInput9Str = "1." + retMap.get("input9");

                retMap.put("input9",tempInput9Str);

                // 清单项目计价说明
                String tempInput19Str = "5." + retMap.get("input19");

                retMap.put("input19",tempInput19Str);

                // 建筑工程人工费
                String tempInput13Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input13"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input13",tempInput13Str);


                // 装饰工程人工费
                String tempInput14Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input14"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input14",tempInput14Str);

                // 安装工程人工费
                String tempInput15Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input15"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input15",tempInput15Str);


                // 概算审核
            }else if ("9".equals(tempFormworkTypeStr)){

                // 审核依据 第一条
                String tempInput17Str = "1." + retMap.get("input17")+ ";";

                retMap.put("input17",tempInput17Str);

                // 审核依据 第四条
                String tempInput19Str = "4." + retMap.get("input19")+ ";";

                retMap.put("input19",tempInput19Str);

                // 送审价格
                String tempInput10Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input10"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input10",tempInput10Str);


                // 审定价格
                String tempInput11Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input11"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input11",tempInput11Str);

                // 审减价格
                String tempInput12Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input12",tempInput12Str);


                // ------工程费用
                // 送审价格
                String tempInput20Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input20"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input20",tempInput20Str);


                // 审定价格
                String tempInput21Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input21"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input21",tempInput21Str);

                // 审减价格
                String tempInput22Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input22"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input22",tempInput22Str);

                // ------其他费用

                // 送审价格
                String tempInput24Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input24"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input24",tempInput24Str);


                // 审定价格
                String tempInput25Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input25"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input25",tempInput25Str);

                // 审减价格
                String tempInput26Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input26"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input26",tempInput26Str);

                // ------工程预备费

                // 送审价格
                String tempInput28Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input28"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input28",tempInput28Str);


                // 审定价格
                String tempInput29Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input29"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input29",tempInput29Str);

                // 审减价格
                String tempInput30Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input30"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input30",tempInput30Str);

                // 估算审核
            }else if ("10".equals(tempFormworkTypeStr)){

                // 审核依据 第四条
                String tempInput18Str = "4." + retMap.get("input18")+ ";";

                retMap.put("input18",tempInput18Str);


                // 投资估算工程造价
                String tempInput10Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input10")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input10",tempInput10Str);


                // 审定工程造价
                String tempInput11Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input11")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input11",tempInput11Str);

                // 审减金额
                String tempInput12Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input12")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input12",tempInput12Str);


                // 工程费用：送审
                String tempInput21Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input21")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input21",tempInput21Str);


                // 工程费用：审定
                String tempInput22Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input22")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input22",tempInput22Str);

                // 工程费用：审减
                String tempInput23Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input23")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input23",tempInput23Str);


                // 其他费用：送审
                String tempInput25Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input25"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input25",tempInput25Str);


                // 其他费用：审定
                String tempInput26Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input26"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input26",tempInput26Str);

                // 其他费用：审减
                String tempInput27Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input27"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input27",tempInput27Str);


                // 工程预备费：送审
                String tempInput29Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input29")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input29",tempInput29Str);


                // 工程预备费：审定
                String tempInput30Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input30")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input30",tempInput30Str);

                // 工程预备费：审减
                String tempInput31Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input31")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input31",tempInput31Str);




                // 结算审核
            } else if ("11".equals(tempFormworkTypeStr)) {

                // 项目地点(从合同表中获取)
                retMap.put("input51",tempProjectAddressStr);

                // 预算审核
            }else if ("12".equals(tempFormworkTypeStr)){

                // 送审价格
                String tempInput10Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input10"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input10",tempInput10Str);


                // 审定价格
                String tempInput11Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input11"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input11",tempInput11Str);

                // 审减价格
                String tempInput12Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input12",tempInput12Str);

                // ------工程费用

                //  送审价格
                String tempInput19Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input19"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input19",tempInput19Str);

                // 审定价格
                String tempInput20Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input20"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input20",tempInput20Str);


                //  审减价格
                String tempInput21Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input21"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input21",tempInput21Str);

                // 合同审核意见（未招标）
            }else if ("13".equals(tempFormworkTypeStr)){

                //  合同金额
                String tempInput8Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input8"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input8",tempInput8Str);



                // 合同审核意见（招标）
            }else if ("14".equals(tempFormworkTypeStr)){

                //  合同金额
                String tempInput7Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input7"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input7",tempInput7Str);

                // 进度款审核意见
            }else if ("15".equals(tempFormworkTypeStr)){

                // 送审价格
                String tempInput10Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input10"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input10",tempInput10Str);


                // 审定价格
                String tempInput11Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input11"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input11",tempInput11Str);

                // 审减价格
                String tempInput12Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input12",tempInput12Str);

                // 累计已支付金额
                String tempInput13Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input13"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input13",tempInput13Str);

                // 是否有附件 0：没有，1：有
                String tempInput14Str = StringToolUtils.convertNullObjectToString(retMap.get("input14"));

                String tempInput15Str = StringToolUtils.STRING_EMPTY;

                // 如果有附件则 拼接上附件信息
                if ("1".equals(tempInput14Str)){

                    tempInput15Str = "附件：" + StringToolUtils.convertNullObjectToString(retMap.get("input15"));

                }
                retMap.put("input15",tempInput15Str);

                // 是否抄送 0：没有，1：有
                String tempInput16Str = StringToolUtils.convertNullObjectToString(retMap.get("input16"));

                // 抄送公司
                String tempInput17Str = StringToolUtils.STRING_EMPTY;
                // 抄送人
                String tempInput18Str = StringToolUtils.STRING_EMPTY;

                // 如果有附件则 拼接上附件信息
                if ("1".equals(tempInput16Str)){

                    tempInput17Str = "抄送：" + StringToolUtils.convertNullObjectToString(retMap.get("input17"));

                    tempInput18Str = StringToolUtils.convertNullObjectToString(retMap.get("input18"));

                }
                retMap.put("input17",tempInput17Str);

                retMap.put("input18",tempInput18Str);

                // 变更审核意见
            }else if ("16".equals(tempFormworkTypeStr)){

                // 送审价格
                String tempInput10Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input10"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input10",tempInput10Str);


                // 审定价格
                String tempInput11Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input11"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input11",tempInput11Str);

                // 审减价格
                String tempInput12Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input12",tempInput12Str);


                // 是否有附件 0：没有，1：有
                String tempInput14Str = StringToolUtils.convertNullObjectToString(retMap.get("input14"));

                String tempInput15Str = StringToolUtils.STRING_EMPTY;

                // 如果有附件则 拼接上附件信息
                if ("1".equals(tempInput14Str)){

                    tempInput15Str = "附件：" + StringToolUtils.convertNullObjectToString(retMap.get("input15"));

                }
                retMap.put("input15",tempInput15Str);

                // 是否抄送 0：没有，1：有
                String tempInput16Str = StringToolUtils.convertNullObjectToString(retMap.get("input16"));

                // 抄送公司
                String tempInput17Str = StringToolUtils.STRING_EMPTY;
                // 抄送人
                String tempInput18Str = StringToolUtils.STRING_EMPTY;

                // 如果有附件则 拼接上附件信息
                if ("1".equals(tempInput16Str)){

                    tempInput17Str = "抄送：" + StringToolUtils.convertNullObjectToString(retMap.get("input17"));

                    tempInput18Str = StringToolUtils.convertNullObjectToString(retMap.get("input18"));

                }
                retMap.put("input17",tempInput17Str);

                retMap.put("input18",tempInput18Str);

                // 招标代理、评标书面报告
            }else if ("18".equals(tempFormworkTypeStr) || "19".equals(tempFormworkTypeStr)){

                // 日期
                String tempDateStr = DateToolUtils.convertDateFormat(DateToolUtils.convertDateFormat(
                        retMap.get("date").toString(),DateToolUtils.yyyy_MM_dd),DateToolUtils.YYYY_MM_DD_CHINESE);

                retMap.put("date",tempDateStr);

            }else {

                // 送审价格
                String tempInput10Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input10"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input10",tempInput10Str);


                // 审定价格
                String tempInput11Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input11"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input11",tempInput11Str);

                // 审减价格
                String tempInput12Str = NumberToolUtils
                        .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                retMap.put("input12",tempInput12Str);

                // 是否有附件 0：没有，1：有
                String tempInput14Str = StringToolUtils.convertNullObjectToString(retMap.get("input14"));

                String tempInput15Str = StringToolUtils.STRING_EMPTY;

                // 如果有附件则 拼接上附件信息
                if ("1".equals(tempInput14Str)){

                    tempInput15Str = "附件：" + StringToolUtils.convertNullObjectToString(retMap.get("input15"));

                }
                retMap.put("input15",tempInput15Str);

                // 是否抄送 0：没有，1：有
                String tempInput16Str = StringToolUtils.convertNullObjectToString(retMap.get("input16"));

                // 抄送公司
                String tempInput17Str = StringToolUtils.STRING_EMPTY;
                // 抄送人
                String tempInput18Str = StringToolUtils.STRING_EMPTY;

                // 如果有附件则 拼接上附件信息
                if ("1".equals(tempInput16Str)){

                    tempInput17Str = "抄送：" + StringToolUtils.convertNullObjectToString(retMap.get("input17"));

                    tempInput18Str = StringToolUtils.convertNullObjectToString(retMap.get("input18"));

                }
                retMap.put("input17",tempInput17Str);

                retMap.put("input18",tempInput18Str);

            }

            retMap.put("taskTemplateName",tempTaskTemplateNameStr);

        }


        return retMap;
    }

    /**
     * 查询请款单模板信息
     *
     * @param paramVO 请款单id
     * @throws SystemException 不可预估的异常
     */
    @Override
    public Map<String, Object> getRequestMenuTemplateInfo(HTTaskExecuteVO paramVO) throws SystemException {
        Map<String, Object> retMap = new HashedMap();

        // 根据请款单id查询请款单信息
        TbRequestMenuEntity tbRequestMenuEntity = tbRequestMenuEntityMapper
                .selectByPrimaryKey(paramVO.getRequestMenuId());

        // 如果查到任务模板信息则将信息传到map里
        if (tbRequestMenuEntity != null){

            // 任务成果文件模板json串
            String tempFormworkJsonStr = tbRequestMenuEntity.getFrameworkContent();

            // 如果json 串不为空则转成map格式
            if (!StringToolUtils.isNull(tempFormworkJsonStr)){

                // json转成map
                retMap = InterfaceUtils.jsonToObject(tempFormworkJsonStr,Map.class);
            }

        }


        return retMap;
    }

    /**
     * 依据成果文件id 查询并生成 模板成果文件
     *
     * @param paramVO 成果信息
     * @param paramOS 输出流
     * @throws LogicException
     * @throws SystemException
     */
    @Override
    public void writeTaskExecuteWordStreamById(HTTaskExecuteVO paramVO, ByteArrayOutputStream paramOS) throws LogicException, SystemException {

        try {
            // 查询任务基本信息、模板是哪个、并把信息放到map中
            Map<String, Object> resultTaskExcuteMap = this.getTaskExecuteTemplateInfo(paramVO);

            // 取得模板名称
            String tempTaskTemplateNameStr = (String) resultTaskExcuteMap.get("taskTemplateName");

            InputStream tempInputStream = new ClassPathResource("/task/" + tempTaskTemplateNameStr).getInputStream();

            WordTemplate wordTemplate = new WordTemplate(tempInputStream);

            // 替换模板中的变量
            wordTemplate.replaceTag(resultTaskExcuteMap);

            //数据写入到输出流
            wordTemplate.write(paramOS);

            tempInputStream.close();

        }catch (Exception ex){
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }

    }

    /**
     * 依据请款单id 查询并生成 模板请款单文件
     *
     * @param paramVO 请款单信息
     * @param paramOS 输出流
     * @throws LogicException
     * @throws SystemException
     */
    @Override
    public void writeRequestMenuWordStreamById(HTTaskExecuteVO paramVO, ByteArrayOutputStream paramOS) throws LogicException, SystemException {

        try {
            // 查询请款单信息并把信息放到map中
            Map<String, Object> resultRequestMenuMap = this.getRequestMenuTemplateInfo(paramVO);

            // 取得模板
            InputStream tempInputStream = new ClassPathResource("/requestMenu/2019HT请款申请模板.docx").getInputStream();

            WordTemplate wordTemplate = new WordTemplate(tempInputStream);

            // 替换模板中的变量
            wordTemplate.replaceTag(resultRequestMenuMap);

            //数据写入到输出流
            wordTemplate.write(paramOS);

            tempInputStream.close();

        }catch (Exception ex){
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
