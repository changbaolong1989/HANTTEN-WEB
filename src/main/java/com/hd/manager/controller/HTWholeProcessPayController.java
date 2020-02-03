package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.*;
import com.hd.export.service.impl.ExcelExport;
import com.hd.manager.service.HTProjectService;
import com.hd.manager.service.HTWholeProcessPayService;
import com.hd.manager.vo.ElementTreeVO;
import com.hd.manager.vo.HTProjectVO;
import com.hd.manager.vo.HTWholeProcessPayVO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全过程(付款台账)Controller
 *
 * @author LH
 * Created in 2019/9/11 14:29
 */
@Controller
@RequestMapping("/wholeProcess/pay")
@CrossOrigin(allowCredentials = "true")
public class HTWholeProcessPayController extends BaseController {

    /**
     * 项目付款台账阶段service
     */
    @Resource
    HTWholeProcessPayService htWholeProcessPayService;

    /**
     * 项目service
     */
    @Resource
    HTProjectService htProjectService;

    @Resource
    private ExcelExport excelExport;





    /**
     * 查询付款台账列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectPayPeriodList")
    public Map<String,Object> searchProjectPayPeriodList(@RequestBody HTWholeProcessPayVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htWholeProcessPayService.excSearchProjectPayPeriodList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询付款台账详情
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectPayPeriodInfo")
    public Map<String,Object> searchProjectPayPeriodInfo(@RequestBody HTWholeProcessPayVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_VO_KEY,htWholeProcessPayService.excSearchProjectPayPeriodInfo(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 新增项目付款台账信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/insert/insertWholeProcessPay")
    public Map<String,Object> insertWholeProcessPay(@RequestBody HTWholeProcessPayVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessPayService.excInsertWholeProcessPay(paramVO);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 修改项目付款台账信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/modify/updateWholeProcessPay")
    public Map<String,Object> updateWholeProcessPay(@RequestBody HTWholeProcessPayVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessPayService.excUpdateWholeProcessPay(paramVO);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 导出
     */
    @RequestMapping(value = "/export/exportPayData", method = RequestMethod.GET)
    public String exportPerformanceReport(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String realFileName = request.getParameter("realFileName");
        response.reset();
        // 解决中文显示不出来的问题
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(realFileName, "UTF-8") + ";filename=" + URLEncoder.encode(realFileName, "UTF-8"));
        //获取参数projectId值
        String projectId = request.getParameter("projectId");
        HTProjectVO htProjectVO = new HTProjectVO();
        htProjectVO.setProjectId(projectId);
        HTProjectVO projectVO = htProjectService.excSearchProjectInfo(htProjectVO);
        HTWholeProcessPayVO paramVO = new HTWholeProcessPayVO();
        paramVO.setProjectId(projectId);
        List<String> payPeriodNumList = htWholeProcessPayService.excSearchProPayPeriodNumListForExport(paramVO);
        //查询估算数据
        List<HTWholeProcessPayVO> wholeProcessPayVOList = htWholeProcessPayService.excSearchProjectPayPeriodListForExport(paramVO);
        //序号
        String index = "";
        //格式转换
        List<Map<String, Object>> dataList = new ArrayList<>();
        this.ListChangeUtil(wholeProcessPayVOList, dataList,index);
        //对应属性

        String[] columnNamesFront = {"sortNum","contractNum","contractName","contractUnit","predictCloseValue","withTaxMoney"};
        String[] periodNumList = this.changeColumnNames(payPeriodNumList);
        String[] columnNamesBack = {"sumCurrentValue","sumCurrentValue/withTaxMoney","sumCurrentPayment","sumCurrentPayment/withTaxMoney",""};
//        String[] columnNames = (String[]) ArrayUtils.addAll(columnNamesFront, periodNumList, columnNamesBack);
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < columnNamesFront.length; i++) {
            resultList.add(columnNamesFront[i]);
        }
        for (int i = 0; i < periodNumList.length; i++) {
            resultList.add(periodNumList[i]);
        }
        for (int i = 0; i < columnNamesBack.length; i++) {
            resultList.add(columnNamesBack[i]);
        }
        String[] columnNames = new String[resultList.size()];
        for (int i = 0; i < columnNames.length ; i++) {
            columnNames[i] = resultList.get(i);
        }
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/pay.xls");
        Workbook workbook = ExcelUtils.readExcel(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        this.changeTitle(sheet, payPeriodNumList, "6",workbook);
        ExcelUtils.creatExcel(workbook,0,0,projectVO.getProjectNum()+"项目付款台账",1,columnNames,dataList,5);
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return "导出文件失败";
        }
        return "导出文件成功";
    }

    /**
     * 改变Excel表头方法
     */
    public Sheet changeTitle(Sheet sheet ,List<String> periods,String startColumn, Workbook workbook){
        Row dateTitle = sheet.getRow(1);
        Row oneLevelTitle = sheet.getRow(2);
        int startColumnNum = Integer.parseInt(startColumn);
        for (int i=0;i<periods.size();i++){
            Row twoLevelTitle = sheet.getRow(3);
            ElementTreeVO period = this.getDIYPeriod(periods.get(i), startColumnNum);
            for (ElementTreeVO content:period.getChildren()){
                Row threeLevelTitle = sheet.getRow(4);
                Cell oneLevelTitleCell = oneLevelTitle.createCell(startColumnNum);
                oneLevelTitleCell.setCellStyle(ExcelUtils.getTitleStyle(workbook));
                oneLevelTitleCell.setCellValue("期数");
                Cell twoLevelTitleCell = twoLevelTitle.createCell(startColumnNum);
                twoLevelTitleCell.setCellStyle(ExcelUtils.getTitleStyle(workbook));
                twoLevelTitleCell.setCellValue(period.getLabel());
                Cell cell = threeLevelTitle.createCell(startColumnNum);
                cell.setCellStyle(ExcelUtils.getTitleStyle(workbook));
                cell.setCellValue(content.getLabel());
                startColumnNum++;
            }
            sheet.addMergedRegion(new CellRangeAddress(3, 3, startColumnNum-period.getChildren().size(),startColumnNum-1));//合并
        }
        if (Integer.parseInt(startColumn)!=startColumnNum){
            sheet.addMergedRegion(new CellRangeAddress(2, 2, Integer.parseInt(startColumn),startColumnNum-1));//合并
        }
        Cell sumCell = oneLevelTitle.createCell(startColumnNum);
        sumCell.setCellValue("截至当期累计");
        sheet.addMergedRegion(new CellRangeAddress(2, 3 , startColumnNum,startColumnNum+3));//合并
        sumCell.setCellStyle(ExcelUtils.getTitleStyle(workbook));
        Cell remarkCell = oneLevelTitle.createCell(startColumnNum+4);
        remarkCell.setCellValue("备注");
        sheet.addMergedRegion(new CellRangeAddress(2, 3 , startColumnNum+4,startColumnNum+4));//合并
        remarkCell.setCellStyle(ExcelUtils.getTitleStyle(workbook));
        Row threeLevelTitle = sheet.getRow(4);
        Cell sumCurrentValue = threeLevelTitle.createCell(startColumnNum);
        sumCurrentValue.setCellStyle(ExcelUtils.getTitleStyle(workbook));
        sumCurrentValue.setCellValue("产值（元）");
        Cell sumCurrentValuePercent = threeLevelTitle.createCell(startColumnNum+1);
        sumCurrentValuePercent.setCellStyle(ExcelUtils.getTitleStyle(workbook));
        sumCurrentValuePercent.setCellValue("产值/合同金额（%）");
        Cell sumCurrentPayment = threeLevelTitle.createCell(startColumnNum+2);
        sumCurrentPayment.setCellStyle(ExcelUtils.getTitleStyle(workbook));
        sumCurrentPayment.setCellValue("支付（元）");
        Cell sumCurrentPaymentPercent = threeLevelTitle.createCell(startColumnNum+3);
        sumCurrentPaymentPercent.setCellStyle(ExcelUtils.getTitleStyle(workbook));
        sumCurrentPaymentPercent.setCellValue("支付/合同金额（%）");
        threeLevelTitle.createCell(startColumnNum+5);
        for (int i = Integer.parseInt(startColumn); i < startColumnNum+5; i++) {
            dateTitle.createCell(i);
        }
        return sheet;
    }

    /**
     * 改变表头字段对应方法
     */
    private String[] changeColumnNames(List<String> payPeriodNumList){
        List<String> columnNames = new ArrayList<>();
        for (int i=0;i<payPeriodNumList.size();i++){
            String periodNum = payPeriodNumList.get(i);
            if("0".equals(periodNum)) {
                columnNames.add("0_currentPayment");
                columnNames.add("0_editDate");
            }else {
                columnNames.add(periodNum+"_currentValue");
                columnNames.add(periodNum+"_currentPayment");
                columnNames.add(periodNum+"_editDate");
            }
        }
        String[] array = new String[columnNames.size()];
        for (int i = 0; i < columnNames.size(); i++) {
            array[i] = columnNames.get(i);
        }
        return array;

    }

    /**
     * 添加个性化期数表头方法
     */
    private ElementTreeVO getDIYPeriod(String periodNum,Integer startColumnNum){
        ElementTreeVO period = new ElementTreeVO();
        String[] chineseNum = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十",
                "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十" };
        if("0".equals(periodNum)) {
            period.setLabel("预付款");
            period.setStartRowNum("3");
            period.setStartColumnNum(startColumnNum.toString());
            period.setCellRows("1");
            period.setCellColumns("2");
            period.setChildren(this.getDIYPeriodContents(periodNum, startColumnNum.toString()));
        }else{
            period.setLabel("第"+chineseNum[Integer.parseInt(periodNum)]+"期");
            period.setStartRowNum("3");
            period.setStartColumnNum(startColumnNum.toString());
            period.setCellRows("1");
            period.setCellColumns("3");
            period.setChildren(this.getDIYPeriodContents(periodNum,startColumnNum.toString()));
        }
        return period;
    }

    /**
     * 添加个性化期数内容表头方法
     */
    private List<ElementTreeVO> getDIYPeriodContents(String periodNum,String startColumnNum){
        List<ElementTreeVO> periodContents = new ArrayList<>();
        ElementTreeVO currentValue = new ElementTreeVO();
        currentValue.setLabel("本期产值（元）");
        currentValue.setStartRowNum("4");
        currentValue.setCellRows("1");
        currentValue.setCellColumns("1");
        ElementTreeVO currentPayment = new ElementTreeVO();
        currentPayment.setLabel("本期应付（元）");
        currentPayment.setStartRowNum("4");
        currentPayment.setCellRows("1");
        currentPayment.setCellColumns("1");
        ElementTreeVO editDate = new ElementTreeVO();
        editDate.setLabel("日期（元）");
        editDate.setStartRowNum("4");
        editDate.setCellRows("1");
        editDate.setCellColumns("1");
        if("0".equals(periodNum)) {
            currentPayment.setStartColumnNum(startColumnNum);
            periodContents.add(currentPayment);
            editDate.setStartColumnNum(startColumnNum + currentPayment.getCellRows());
            periodContents.add(editDate);
        }else {
            currentValue.setStartColumnNum(startColumnNum);
            periodContents.add(currentValue);
            currentPayment.setStartColumnNum(startColumnNum+currentValue.getCellRows());
            periodContents.add(currentPayment);
            editDate.setStartColumnNum(startColumnNum+currentValue.getCellRows()+currentPayment.getCellRows());
            periodContents.add(editDate);
        }
        return periodContents;
    }

    /**
     * 格式转换
     */
    private List<Map<String, Object>> ListChangeUtil(List<HTWholeProcessPayVO>  wholeProcessPayVOList,List<Map<String, Object>> dataList,String index) {
        //格式转换
        for (int i=0;i<wholeProcessPayVOList.size();i++){
            boolean saveFlag = true;
            StringBuffer sortNum = new StringBuffer(index);
            //当是一级标题时不进行序号累加(后面加点)或者工程类合同的二级标题时不进行序号累加(后面加点)
            if (wholeProcessPayVOList.get(i).getCurrentLevel().equals(Constant.SECOND_LEVEL_MENU_FLAG)
                    &&!wholeProcessPayVOList.get(i).getTreatyType().equals("1")
                    ||wholeProcessPayVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                saveFlag = false;
            }
            if(saveFlag||wholeProcessPayVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                Map<String, Object> resultMap = this.toPayMap(wholeProcessPayVOList.get(i),sortNum.append(i+1).toString());
                dataList.add(resultMap);
            }
            if (wholeProcessPayVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                sortNum = new StringBuffer("");
            }
            List<HTWholeProcessPayVO> childList = wholeProcessPayVOList.get(i).getChildList();
            if (!ListToolUtils.isEmpty(childList)&&!Constant.THIRD_LEVEL_MENU_FLAG.equals(wholeProcessPayVOList.get(i).getCurrentLevel())){
                if (saveFlag){
                    sortNum.append(".");
                }
                dataList = this.ListChangeUtil(childList,dataList,sortNum.toString());
            }
        }
        return dataList;
    }
    


    /**
     * 格式转换
     */
    private Map<String, Object> toPayMap(HTWholeProcessPayVO tempVO,String index) {
        DecimalFormat decimalFormat=new DecimalFormat("#.##");
        Map<String, Object> map = new HashMap<>();
        /*----------------------将Object对象转换成Map<key,value>------------------------*/
        //序号
        map.put("sortNum", index);
        //校验是否是一级(需要展示合并单元格的合约分类)
        if (tempVO.getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
            map.put(ExcelUtils.SUB_LEVEL_CELL_TYPE, ExcelUtils.SUB_LEVEL_CELL_TYPE_TEXT);
            map.put(ExcelUtils.SUB_LEVEL_CELL_TEXT,tempVO.getTreatyTypeName());
        }
        //招标编号
        map.put("contractNum", StringToolUtils.isNull(tempVO.getContractNum())?"":tempVO.getContractNum());
        //招标名称
        map.put("contractName", StringToolUtils.isNull(tempVO.getContractName())?"":tempVO.getContractName());
        if (!StringToolUtils.isNull(tempVO.getUndertakeTypeName())){
            map.put("contractName", tempVO.getUndertakeTypeName());
        }
        if (tempVO.getCurrentLevel().equals(Constant.THIRD_LEVEL_MENU_FLAG)){
            //合作单位
            map.put("contractUnit", StringToolUtils.isNull(tempVO.getContractUnit())?"-":tempVO.getContractUnit());
            //合同金额
            map.put("withTaxMoney", StringToolUtils.isNull(tempVO.getWithTaxMoney())?"0.0":tempVO.getWithTaxMoney());
            //预计结算金额
            map.put("predictCloseValue", StringToolUtils.isNull(tempVO.getPredictCloseValue())?"0.0":tempVO.getPredictCloseValue());
            List<HTWholeProcessPayVO> periodList = tempVO.getChildList();
            //产值累计
            Double sumCurrentValue = 0.0;
            //应付累计
            Double sumCurrentPayment = 0.0;
            for (int i = 0; i < periodList.size(); i++) {
                if (!"0".equals(periodList.get(i).getPeriodNum())) {
                    //本期产值
                    map.put(periodList.get(i).getPeriodNum() + "_currentValue", StringToolUtils.isNull(periodList.get(i).getCurrentValue()) ? "0.0" : periodList.get(i).getCurrentValue());
                    //产值累计
                    sumCurrentValue += Double.parseDouble(StringToolUtils.isNull(periodList.get(i).getCurrentValue()) ? "0.0" : this.getNumberStr(periodList.get(i).getCurrentValue() + ""));
                }
                //本期应付
                map.put(periodList.get(i).getPeriodNum()+"_currentPayment", StringToolUtils.isNull(periodList.get(i).getCurrentPayment())?"0.0":periodList.get(i).getCurrentPayment());
                //应付累计
                sumCurrentPayment+=Double.parseDouble(StringToolUtils.isNull(periodList.get(i).getCurrentPayment())?"0.0":this.getNumberStr(periodList.get(i).getCurrentPayment()+""));
                map.put(periodList.get(i).getPeriodNum()+"_editDate", StringToolUtils.isNull(periodList.get(i).getEditDate())?"-":periodList.get(i).getEditDate());
            }
            //产值累计
            map.put("sumCurrentValue", this.getNumberStr(sumCurrentValue+""));
            //应付累计
            map.put("sumCurrentPayment", this.getNumberStr(sumCurrentPayment+""));
            if (StringToolUtils.isNull(tempVO.getWithTaxMoney())||Double.parseDouble(tempVO.getWithTaxMoney())==0){
                //累计产值/合同金额
                map.put("sumCurrentValue/withTaxMoney","-");
                //累计应付/合同金额
                map.put("sumCurrentPayment/withTaxMoney","-");
            }else {
                //累计产值/合同金额
                map.put("sumCurrentValue/withTaxMoney",decimalFormat.format(Double.parseDouble(this.getNumberStr(sumCurrentValue/Double.parseDouble(tempVO.getWithTaxMoney())+""))*100));
                //累计应付/合同金额
                map.put("sumCurrentPayment/withTaxMoney",decimalFormat.format(Double.parseDouble(this.getNumberStr(sumCurrentPayment/Double.parseDouble(tempVO.getWithTaxMoney())+""))*100));
            }
         }

        /*-------------------------------------end--------------------------------------*/
        //返回Map
        return map;
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
