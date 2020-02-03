package com.hd.common.util;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.export.bean.ExcelData;
import com.hd.export.bean.ExcelExportParam;
import com.hd.export.style.ExcelCellStyle;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


public class ExcelUtils {



    public static final String OFFICE_EXCEL_XLS = "xls";
    public static final String OFFICE_EXCEL_XLSX = "xlsx";

    public static final String SUB_LEVEL_CELL_TYPE = "cellType";
    public static final String SUB_LEVEL_CELL_TYPE_TEXT = "subLevelTitle";
    public static final String SUB_LEVEL_CELL_TEXT = "subLevelTitleText";

    public static final String CELL_TYPE_TEXT = "TEXT";
    public static final String CELL_TYPE_NUMBIC = "NUMBIC";
    public static final String CELL_TYPE_DATE = "DATE";

    private static final Map<String,String> StsMap = new HashMap<>();
    private static final String[] ExplainColumnsTile = {"代号","申请金额","","审核金额","核增/核减金额","变更份数"};


    /**
     * 读取指定Sheet页的内容
     *
     */
    public static Workbook readExcel(InputStream inputStream)
            throws LogicException, SystemException {
        Workbook workbook;
        try {
            //根据文件路径生成工作簿实体
            workbook = getWorkbook(inputStream);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_EXCEL_UTILS_0001);
        }
        return workbook;
    }

    /**
     * 根据文件路径获取Workbook对象
     */
    private static Workbook getWorkbook(InputStream inputStream)
            throws LogicException, SystemException {
        //工作簿
        Workbook wb;
        try {
            //文件路径非空校验
            if (inputStream==null) {
                throw new LogicException("文件路径不能为空");
            } else {
                //生成工作簿
                wb = WorkbookFactory.create(inputStream);
            }
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception sex) {
            sex.printStackTrace();
            throw new SystemException(sex, MessageConstant.MSG_ERROR_EXCEL_UTILS_0001);
        }
        return wb;
    }

    /**
     * 获取后缀
     * @param filepath filepath 文件全路径
     */
    public static String getSuffiex(String filepath) {
        if (StringUtils.isBlank(filepath)) {
            return "";
        }
        int index = filepath.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return filepath.substring(index + 1, filepath.length());
    }

    /**
     * 内容单元格样式
     */
    public static CellStyle getContentStyle(Workbook workbook, boolean isNum) {
        Font font = workbook.createFont();
        font.setFontName("Courier New");
        CellStyle style = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        //底边框
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //左边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //右边框
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //上边框
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //字体
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        if(isNum){
            style.setDataFormat(dataFormat.getFormat("#,#0.0"));
        }else{
            style.setDataFormat(dataFormat.getFormat("@"));
        }
        return style;
    }

    /**
     * 一级单元格样式
     */
    public static CellStyle getLevelOneContentStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("Courier New");
        CellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //背景色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        //字体
        style.setFont(font);
        style.setWrapText(true);
        //字体左对齐
        style.setAlignment(HorizontalAlignment.LEFT);
        //中间对齐
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 2级单元格样式
     */
    public static CellStyle getLevelSecondContentStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("Courier New");
        font.getBold();
        CellStyle style = workbook.createCellStyle();
        //底边框
        style.setBorderBottom(BorderStyle.THIN);
        //底边框颜色
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //左边框
        style.setBorderLeft(BorderStyle.THIN);
        //左边框颜色
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //右边框
        style.setBorderRight(BorderStyle.THIN);
        //右边框颜色
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //上边框
        style.setBorderTop(BorderStyle.THIN);
        //上边框颜色
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //字体
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 时间单元格样式
     */
    public static CellStyle getDateTitleStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        font.getBold();
        CellStyle style = workbook.createCellStyle();
        //字体
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
    /**
     * 2级单元格样式
     */
    public static CellStyle getTitleStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeight((short) 220);
        font.getBold();
        CellStyle style = workbook.createCellStyle();
        //底边框
        style.setBorderBottom(BorderStyle.THIN);
        //底边框颜色
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //左边框
        style.setBorderLeft(BorderStyle.THIN);
        //左边框颜色
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //右边框
        style.setBorderRight(BorderStyle.THIN);
        //右边框颜色
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //上边框
        style.setBorderTop(BorderStyle.THIN);
        //上边框颜色
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //字体
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }


    /**
     * 生成excel对象
     */
    public static Workbook creatExcel(Workbook workbook, int sheetNum, int titleRowNum, String titleValue, int timeRowNum
            , String[] columnNames, List<Map<String, Object>> dataList, int dataRowNum){
        Date currentDate = new Date();
        Sheet sheet = workbook.getSheetAt(sheetNum);
        Row titleCellRow = sheet.getRow(titleRowNum);
        titleCellRow.getCell(titleCellRow.getFirstCellNum()).setCellValue(titleValue);
        Row timeCellRow = sheet.getRow(timeRowNum);
        timeCellRow.getCell(columnNames.length-1).setCellValue("日期:"+DateToolUtils.convertDateFormat(currentDate,DateToolUtils.YYYY_MM_DD_CHINESE));
        timeCellRow.getCell(columnNames.length-1).setCellStyle(ExcelUtils.getDateTitleStyle(workbook));
        for (Map<String, Object> obj:dataList){
            Row DataRow = sheet.createRow(dataRowNum);
            DataRow.setHeight((short)500);
            for (int i=0; i<columnNames.length;i++){
                if (obj.containsKey(SUB_LEVEL_CELL_TYPE) && obj.get(SUB_LEVEL_CELL_TYPE).toString().equals(SUB_LEVEL_CELL_TYPE_TEXT)){
                    Cell treatyTypeCell = DataRow.createCell(0);
                    sheet.addMergedRegion(new CellRangeAddress(dataRowNum, dataRowNum, 0, columnNames.length-1));//合并
                    treatyTypeCell.setCellValue(obj.get(SUB_LEVEL_CELL_TEXT).toString());
                    treatyTypeCell.setCellStyle(ExcelUtils.getLevelOneContentStyle(workbook));
                    break;
                }else {
                    if(obj.containsKey(columnNames[i])){
                        Cell cell = DataRow.createCell(i);
                        cell.setCellValue(obj.get(columnNames[i])+"");
                        cell.setCellStyle(ExcelUtils.getContentStyle(workbook,false));
                    }else{
                        Cell cell = DataRow.createCell(i);
                        cell.setCellValue("");
                        cell.setCellStyle(ExcelUtils.getContentStyle(workbook, false));
                    }
                }
            }
            dataRowNum++;
        }
        return workbook;
    }


    /**
     * 生成excel对象
     */
    public static Workbook writeContent(Workbook workbook, Map<String, String> columnMap, List<Map<String, Object>> dataList, ExcelExportParam param) {
        int sheetNum = param.getSheetNum();
        int titleRowNum = param.getTitleRowNum();
        String titleValue = param.getTitleValue();
        int timeRowNum = param.getTimeRowNum();
        int dataRowNum = param.getDataRowNum();
        short dataLineHeight = param.getDataLineHeight();
        int startColumn = param.getStartColumn();
        CellStyle contentStyle = param.getContentStyle();
        CellStyle subTitleStyle = param.getSubTitleStyle();
        Date currentDate = new Date();
        Sheet sheet = workbook.getSheetAt(sheetNum);
        Row titleCellRow = sheet.getRow(titleRowNum);
        titleCellRow.getCell(titleCellRow.getFirstCellNum()).setCellValue(titleValue);
        Row timeCellRow = sheet.getRow(timeRowNum);
        timeCellRow.getCell(timeCellRow.getLastCellNum() - 1).setCellValue("日期:" + DateToolUtils.convertDateFormat(currentDate, DateToolUtils.YYYY_MM_DD_CHINESE));
        for (Map<String, Object> obj : dataList) {
            Row DataRow = sheet.createRow(dataRowNum);
            DataRow.setHeight(dataLineHeight);
            if (obj.containsKey(SUB_LEVEL_CELL_TYPE) && obj.get(SUB_LEVEL_CELL_TYPE).toString().equals(SUB_LEVEL_CELL_TYPE_TEXT)) {
                Cell treatyTypeCell = DataRow.createCell(0);
                sheet.addMergedRegion(new CellRangeAddress(dataRowNum, dataRowNum, 0, columnMap.size() - 1));//合并
                treatyTypeCell.setCellValue(obj.get(SUB_LEVEL_CELL_TEXT).toString());
                treatyTypeCell.setCellStyle(subTitleStyle);
                dataRowNum++;
                continue;
            } else {
                int i = startColumn;
                for (String columnName : columnMap.keySet()) {
                    if (obj.containsKey(columnName)) {
                        Cell cell = DataRow.createCell(i);
                        cell.setCellStyle(contentStyle);
                        String columnType = columnMap.get(columnName);
                        if (CELL_TYPE_NUMBIC.equals(columnType)) {
                            cell.setCellValue(Double.parseDouble(obj.get(columnName) + ""));
                        } else if (CELL_TYPE_TEXT.equals(columnType)) {
                            cell.setCellValue(obj.get(columnName) + "");
                        } else if (CELL_TYPE_DATE.equals(columnType)) {
                            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
                            cell.setCellValue(simpleFormat.format(obj.get(columnName)));
                        }
                    } else {
                        Cell cell = DataRow.createCell(i);
                        cell.setCellValue("");
                        cell.setCellStyle(contentStyle);
                    }
                    i++;
                }
            }
            dataRowNum++;
        }
        return workbook;
    }


    /**
     * 创建变更台账Excel
     */
    public static void createChangeDetailExcel(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        //---------------------------------合计----------------------------------------------------------
        int totalNum = sheet.getLastRowNum() + 1;
        Row totalRow = sheet.createRow(totalNum);
        totalRow.setHeight((short) 400);
        Cell totalCell = totalRow.createCell(1);
        totalCell.setCellValue("合计");
        totalCell.setCellStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
        CellRangeAddress cellAddresses = new CellRangeAddress(totalNum, totalNum, 1, 6);
        sheet.addMergedRegion(cellAddresses);//合并
        ExcelCellStyle.setBorderStyle(sheet, cellAddresses);
        for (int i = 7, col = 'H'; i <= 14; i++, col++) {
            if (i == 7 || i == 9 || i == 10 || i == 11) {
                Cell totalInfoCell = totalRow.createCell(i);
                totalInfoCell.setCellFormula("SUM(" + (char) col + "5:" + (char) col + "" + totalNum + ")");
                totalInfoCell.setCellStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            } else {
                Cell totalInfoCell = totalRow.createCell(i);
                totalInfoCell.setCellValue("");
                totalInfoCell.setCellStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            }
        }
        //---------------------------------合计----------------------------------------------------------

        Row emptyRow = sheet.createRow(totalNum+1);
        emptyRow.setHeight((short) 400);

        //---------------------------------说明----------------------------------------------------------
        int explainNum = totalNum + 2;
        Row explainRow = sheet.createRow(explainNum);
        explainRow.setHeight((short) 400);
        Cell explainCell = explainRow.createCell(1);
        explainCell.setCellValue("说明:");
        explainCell.setCellStyle(ExcelCellStyle.getDefaultStyleBlod(workbook, "宋体", (short) 10, null, VerticalAlignment.CENTER));
        for (int i = 6, index = 0; i <= 11; i++, index++) {
            Cell cell = explainRow.createCell(i);
            cell.setCellValue(ExplainColumnsTile[index]);
            cell.setCellStyle(ExcelCellStyle.getStyleFontBlod(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
        }
        int explainInfoNum = explainNum + 1;
        int startTotalNum = explainInfoNum + 1;
        for (String key : StsMap.keySet()) {
            Row explainInfoRow = sheet.createRow(explainInfoNum);
            explainInfoRow.setHeight((short) 400);
            Cell cellInfo1 = explainInfoRow.createCell(1);
            cellInfo1.setCellValue(key + "：" + StsMap.get(key));
            cellInfo1.setCellStyle(ExcelCellStyle.getDefaultStyleBlod(workbook, "宋体", (short) 10, null, VerticalAlignment.CENTER));
            Cell cellInfo6 = explainInfoRow.createCell(6);
            cellInfo6.setCellValue(key);
            cellInfo6.setCellStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            Cell cellInfo7 = explainInfoRow.createCell(7);
            cellInfo7.setCellFormula("SUMIF(G$5:G$" + totalNum + ",$G" + (explainInfoNum + 1) + ",H$5:H$" + totalNum + ")");
            cellInfo7.setCellStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            Cell cellInfo8 = explainInfoRow.createCell(8);
            cellInfo8.setCellValue("");
            cellInfo8.setCellStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            Cell cellInfo9 = explainInfoRow.createCell(9);
            cellInfo9.setCellFormula("SUMIF(G$5:G$" + totalNum + ",$G" + (explainInfoNum + 1) + ",J$5:J$" + totalNum + ")");
            cellInfo9.setCellStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            Cell cellInfo10 = explainInfoRow.createCell(10);
            cellInfo10.setCellFormula("SUMIF(G$5:G$" + totalNum + ",$G" + (explainInfoNum + 1) + ",L$5:L$" + totalNum + ")");
            cellInfo10.setCellStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            Cell cellInfo11 = explainInfoRow.createCell(11);
            cellInfo11.setCellFormula("COUNTIF(G$5:G$" + totalNum + ",\"=" + key + "\")");
            cellInfo11.setCellStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            explainInfoNum++;
        }

        //-------------------------------说明合计----------------------------------------------
        Row explainTotalRow = sheet.createRow(explainInfoNum);
        explainTotalRow.setHeight((short) 400);
        Cell cellTotal6 = explainTotalRow.createCell(6);
        cellTotal6.setCellValue("合计");
        cellTotal6.setCellStyle(ExcelCellStyle.getStyleFontBlod(workbook, "宋体", (short) 10, null, VerticalAlignment.CENTER));
        for (int i = 7, col = 'H'; i <= 11; i++, col++) {
            if (i == 8) {
                Cell cell = explainTotalRow.createCell(8);
                cell.setCellValue("");
                cell.setCellStyle(ExcelCellStyle.getStyleFontBlod(workbook, "宋体", (short) 10, null, VerticalAlignment.CENTER));
            } else {
                Cell cell = explainTotalRow.createCell(i);
                cell.setCellFormula("SUM(" + (char) col + "" + startTotalNum + ":" + (char) col + explainInfoNum + ")");
                cell.setCellStyle(ExcelCellStyle.getStyleFontBlod(workbook, "宋体", (short) 10, null, VerticalAlignment.CENTER));
            }
        }
        //-------------------------------说明合计----------------------------------------------

        //---------------------------------说明----------------------------------------------------------
    }

    /**
     * 组装变更明细导出数据
     * @return
     */
    public static List<Map<String,Object>> assesChangeDetail(List<Map<String, Object>> dataList){
        List<Map<String, Object>> result = new ArrayList<>();
        Object processionName = "";
        for (Map<String, Object> map : dataList) {
            if(processionName.equals(map.get("professional_name"))){
                result.add(map);
            }else{
                Map<String, Object> processionMap = new HashMap<>();
                processionMap.put("professional_name", map.get("professional_name").toString());
                result.add(processionMap);
                result.add(map);
            }
            processionName = map.get("professional_name");
        }
        return result;
    }

    /**
     * 格式组装方法
     */
    public static List<Map<String, Object>> changeStructure(List<Map<String, Object>> dataList){
        List<Map<String, Object>> result = new ArrayList<>();
        Object treatyType = "";//合同分类
        int treatyTypeNum = 1; //合同分类序号
        Object undertakeType = ""; //承包方式
        ExcelData.SerialNum serialNum = new ExcelData.SerialNum();
        for (Map<String, Object> map : dataList) {
            if (treatyType.equals(map.get("treaty_type"))) {
                assesSerial(undertakeType, map, result, serialNum);
                undertakeType = map.get("undertake_type");
            } else {
                Map<String, Object> treatyTypeMap = new HashMap<>();
                treatyTypeMap.put(ExcelUtils.SUB_LEVEL_CELL_TYPE, ExcelUtils.SUB_LEVEL_CELL_TYPE_TEXT);
                treatyTypeMap.put(ExcelUtils.SUB_LEVEL_CELL_TEXT, NumberToCN.getCNNum(treatyTypeNum) + "、" + map.get("treaty_type").toString());
                result.add(treatyTypeMap);
                treatyTypeNum++;
                undertakeType = "";
                assesSerial(undertakeType, map, result, serialNum);
                undertakeType = map.get("undertake_type");
            }
            treatyType = map.get("treaty_type");
        }
        return result;
    }


    /**
     * 组装序号方法
     */
    private static void assesSerial(Object undertakeType, Map<String, Object> map, List<Map<String, Object>> result, ExcelData.SerialNum serialNum) {
        if (undertakeType.equals(map.get("undertake_type"))) {
            int undertakeTypeNum = serialNum.getUndertakeTypeNum() - 1;
            int undertakeTypeSubNum = serialNum.getUndertakeTypeSubNum() + 1;
            map.put("serial_num", undertakeTypeNum + "." + undertakeTypeSubNum);
            result.add(map);
            serialNum.setUndertakeTypeSubNum(undertakeTypeSubNum);
        } else {
            int undertakeTypeNum = serialNum.getUndertakeTypeNum();
            serialNum.setUndertakeTypeSubNum(1);
            Map<String, Object> undertakeTypeMap = new HashMap<>();
            undertakeTypeMap.put("serial_num", undertakeTypeNum);
            undertakeTypeMap.put("contract_name", map.get("undertake_type").toString());
            result.add(undertakeTypeMap);
            map.put("serial_num", undertakeTypeNum + "." + serialNum.getUndertakeTypeSubNum());
            result.add(map);
            serialNum.setUndertakeTypeNum(undertakeTypeNum + 1);
        }

    }

    static {
        StsMap.put("A","业主审批金额");
        StsMap.put("B","汉腾与承包商已核对且无争议的变更洽商金额");
        StsMap.put("C","汉腾与承包商已核对且有争议的变更洽商金额");
        StsMap.put("D","汉腾已审核完毕尚未与申报单位核对金额");
        StsMap.put("E","汉腾正在审核的变更金额");
        StsMap.put("F","承包商申报资料不全或尚未申报费用");
        StsMap.put("G","预计会发生，但无变更资料");
    }

}


