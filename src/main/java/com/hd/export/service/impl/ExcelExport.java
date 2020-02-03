package com.hd.export.service.impl;

import com.hd.common.util.DateToolUtils;
import com.hd.common.util.ListToolUtils;
import com.hd.export.bean.ExcelData;
import com.hd.export.bean.ExportData;
import com.hd.export.service.Export;
import com.hd.manager.vo.ElementTreeVO;
import org.apache.poi.POIDocument;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ExcelExport implements Export {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String SIGN_PATH_COL_NAME = "signPath";

    private static final Logger logger = LoggerFactory.getLogger(ExcelExport.class);

    @Value("${system-param.export.watermark-path}")
    private String watermarkPath;

    @Value("${file.request.rootpath}")
    private String prefixPath;

    @Override
    public POIDocument export(List<ExportData> exportDatas) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        for (ExportData exportData : exportDatas) {
            ExcelData excelData = (ExcelData) exportData;
            String sheetName = excelData.getSheetName();
            String title = excelData.getTitle();
            String[] columnTxt = excelData.getColumnTxt();
            String[] columnName = excelData.getColumnNames();
            int[] columnWidth = excelData.getColumnWidth();
            List<Map<String, Object>> dataList = excelData.getDataList();
            //设置标题
            HSSFCellStyle topTitleStyle = this.getTopTitleStyle(workbook);
            HSSFCellStyle contentStyle = this.getContentStyle(workbook);
            HSSFSheet sheet = workbook.createSheet(sheetName);//创建sheet
            HSSFRow row = sheet.createRow(0);//标题行
            HSSFCell cellTitle = row.createCell(0);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (columnTxt.length - 1)));//合并
            cellTitle.setCellStyle(topTitleStyle);
            cellTitle.setCellValue(title);
            //设置列头
            int columnNum = columnTxt.length;
            HSSFRow titleRow = sheet.createRow(2);
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = titleRow.createCell(n);
                cellRowName.setCellType(CellType.STRING);
                HSSFRichTextString text = new HSSFRichTextString(columnTxt[n]);
                cellRowName.setCellValue(text);
                cellRowName.setCellStyle(topTitleStyle);
            }
            //设置内容
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = dataList.get(i);
                HSSFRow contentRow = sheet.createRow(i + 3);//创建行
                contentRow.setHeight((short) 500);
                for (int j = 0; j < columnName.length; j++) {
                    String key = columnName[j];
                    HSSFCell contentCell = contentRow.createCell(j, CellType.STRING);
                    Object o = dataMap.get(key);
                    if (o != null) {
                        if (SIGN_PATH_COL_NAME.equals(key)) {
                            contentCell.setCellValue("");
                            setImg(workbook, sheet, prefixPath + o.toString(), i + 3, i + 3 + 1, j, j + 1);
                        } else {
                            if (o instanceof Date) {
                                contentCell.setCellValue(sdf.format((Date) o));
                            } else {
                                contentCell.setCellValue(o.toString());
                            }
                        }
                    } else {
                        contentCell.setCellValue("");
                    }
                    contentCell.setCellStyle(contentStyle);
                }
            }
            //列宽
            for (int colNum = 0; colNum < columnNum; colNum++) {
                sheet.setColumnWidth(colNum, columnWidth[colNum]);
            }
            addWatermark(workbook, sheet, watermarkPath, 0, 0, 5, 5, 1, 1, 20, 6);
            sheet.protectSheet(UUID.randomUUID().toString());
        }
        return workbook;
    }

    /**
     * 插入图片
     */
    private static void setImg(Workbook workbook, Sheet sheet, String ImgPath, int row1, int row2, int col1, int col2) {
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            BufferedImage bufferImg = ImageIO.read(new File(ImgPath));
            ImageIO.write(bufferImg, "png", byteArrayOut);
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            Drawing drawing = sheet.createDrawingPatriarch();
            //anchor主要用于设置图片的属性
            HSSFClientAnchor anchor = new HSSFClientAnchor(10, 10, 0, 0, (short) col1, (short) row1, (short) col2, (short) row2);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            //插入图片
            drawing.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Workbook export(Workbook workbook, List<ExportData> exportDatas) {

        return workbook;
    }

    /**
     * 带有导出时间excel表格生成方法
     */
    @Override
    public POIDocument export(List<ExportData> exportDatas, Date date) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        for (ExportData exportData : exportDatas) {
            ExcelData excelData = (ExcelData) exportData;
            String sheetName = excelData.getSheetName();
            String title = excelData.getTitle();
            String[] columnTxt = excelData.getColumnTxt();
            String[] columnName = excelData.getColumnNames();
            int[] columnWidth = excelData.getColumnWidth();
            List<Map<String, Object>> dataList = excelData.getDataList();
            //设置标题
            HSSFCellStyle topTitleStyle = this.getTopTitleStyle(workbook);
            HSSFCellStyle contentStyle = this.getContentStyle(workbook);
            HSSFCellStyle dateStyle = this.getDateStyle(workbook);

            HSSFSheet sheet = workbook.createSheet(sheetName);//创建sheet
            Integer rowNum = 0;
            HSSFRow row = sheet.createRow(rowNum);//标题行
            HSSFCell cellTitle = row.createCell(0);
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 1, 0, (columnTxt.length - 1)));//合并
            rowNum += 2;
            cellTitle.setCellStyle(topTitleStyle);
            cellTitle.setCellValue(title);
            //设置导出时间
            StringBuffer time = new StringBuffer("日期：");
            time.append(DateToolUtils.convertDateFormat(date, DateToolUtils.YYYY_MM_DD_CHINESE));
            HSSFRow timeRow = sheet.createRow(rowNum);
            rowNum++;
            int columnNum = columnTxt.length;
            HSSFCell timeCell = timeRow.createCell(columnNum - 1);
            timeCell.setCellStyle(dateStyle);
            timeCell.setCellValue(time.toString());
            //设置列头
            HSSFRow titleRow = sheet.createRow(rowNum);
            rowNum++;
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = titleRow.createCell(n);
                cellRowName.setCellType(CellType.STRING);
                HSSFRichTextString text = new HSSFRichTextString(columnTxt[n]);
                cellRowName.setCellValue(text);
                cellRowName.setCellStyle(contentStyle);
            }
            //设置内容
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = dataList.get(i);
                HSSFRow contentRow = sheet.createRow(i + rowNum);//创建行
                for (int j = 0; j < columnName.length; j++) {
                    String key = columnName[j];
                    HSSFCell contentCell = contentRow.createCell(j, CellType.STRING);
                    Object o = dataMap.get(key);
                    if (o != null) {
                        if (o instanceof Date) {
                            contentCell.setCellValue(sdf.format((Date) o));
                        } else {
                            contentCell.setCellValue(o.toString());
                        }
                    }
                    contentCell.setCellStyle(contentStyle);
                }
            }
            //列宽
            for (int colNum = 0; colNum < columnNum; colNum++) {
                sheet.setColumnWidth(colNum, columnWidth[colNum]);
            }
        }
        return workbook;
    }


    /**
     * 特殊标头样式excel表格生成方法
     */
    @Override
    public POIDocument export(List<ExportData> exportDatas, List<ElementTreeVO> titleData) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        for (ExportData exportData : exportDatas) {
            ExcelData excelData = (ExcelData) exportData;
            //sheet页名称
            String sheetName = excelData.getSheetName();
            //标头
            String title = excelData.getTitle();
            //数据标识列表
            String[] columnName = excelData.getColumnNames();
            int[] columnWidth = excelData.getColumnWidth();
            List<Map<String, Object>> dataList = excelData.getDataList();
            List<ElementTreeVO> specialDataList = excelData.getSpecialDataList();
            //设置标题
            HSSFCellStyle topTitleStyle = this.getTopTitleStyle(workbook);

            HSSFCellStyle contentStyle = this.getContentStyle(workbook);
            HSSFSheet sheet = workbook.createSheet(sheetName);//创建sheet
            HSSFRow row = sheet.createRow(0);//标题行
            HSSFCell cellTitle = row.createCell(0);
            //设置列头
            sheet = this.createExcelTableHead(sheet, titleData, 0, 0);
            //设置内容
            if (!ListToolUtils.isEmpty(dataList)) {
                for (int i = 0; i < dataList.size(); i++) {
                    Map<String, Object> dataMap = dataList.get(i);
                    HSSFRow contentRow = sheet.createRow(i + 3);//创建行
                    for (int j = 0; j < columnName.length; j++) {
                        String key = columnName[j];
                        HSSFCell contentCell = contentRow.createCell(j, CellType.STRING);
                        Object o = dataMap.get(key);
                        if (o != null) {
                            if (o instanceof Date) {
                                contentCell.setCellValue(sdf.format((Date) o));
                            } else {
                                contentCell.setCellValue(o.toString());
                            }
                        }
                        contentCell.setCellStyle(contentStyle);
                    }
                }
            }
            if (!ListToolUtils.isEmpty(specialDataList)) {
                sheet = this.createExcelTableHead(sheet, titleData, 0, 0);
            }
        }
        return workbook;
    }

    /**
     * 导出周报（主要用于合并相同内容的单元格）
     */
    @Override
    public POIDocument exportWeek(List<ExportData> exportDatas) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        for (ExportData exportData : exportDatas) {
            ExcelData excelData = (ExcelData) exportData;
            String sheetName = excelData.getSheetName();
            String title = excelData.getTitle();
            String[] columnTxt = excelData.getColumnTxt();
            String[] columnName = excelData.getColumnNames();
            int[] columnWidth = excelData.getColumnWidth();
            List<Map<String, Object>> dataList = excelData.getDataList();
            //设置标题
            HSSFCellStyle topTitleStyle = this.getTopTitleStyle(workbook);
            HSSFCellStyle contentStyle = this.getContentStyle(workbook);
            HSSFSheet sheet = workbook.createSheet(sheetName);//创建sheet
            HSSFRow row = sheet.createRow(0);//标题行
            HSSFCell cellTitle = row.createCell(0);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (columnTxt.length - 1)));//合并
            cellTitle.setCellStyle(topTitleStyle);
            cellTitle.setCellValue(title);
            //设置列头
            int columnNum = columnTxt.length;
            HSSFRow titleRow = sheet.createRow(2);
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = titleRow.createCell(n);
                cellRowName.setCellType(CellType.STRING);
                HSSFRichTextString text = new HSSFRichTextString(columnTxt[n]);
                cellRowName.setCellValue(text);
                cellRowName.setCellStyle(topTitleStyle);
            }
            //设置内容
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = dataList.get(i);
                HSSFRow contentRow = sheet.createRow(i + 3);//创建行
                contentRow.setHeight((short) 500);
                // 循环列数，给当前行塞值
                for (int j = 0; j < columnName.length; j++) {
                    String key = columnName[j];
                    HSSFCell contentCell = contentRow.createCell(j, CellType.STRING);
                    Object o = dataMap.get(key);
                    if (o != null) {
                        // 对应的单元格 为时间时，将其内容转换成日期格式
                        if (o instanceof Date) {
                            contentCell.setCellValue(sdf.format((Date) o));
                        } else {
                            contentCell.setCellValue(o.toString());
                        }
                    } else {
                        contentCell.setCellValue("");
                    }
                    contentCell.setCellStyle(contentStyle);
                }
            }
            //列宽
            for (int colNum = 0; colNum < columnNum; colNum++) {
                sheet.setColumnWidth(colNum, columnWidth[colNum]);
            }
            addWatermark(workbook, sheet, watermarkPath, 0, 0, 5, 5, 1, 1, 20, 6);
            sheet.protectSheet(UUID.randomUUID().toString());
        }
        return workbook;
    }


    //个性化表头生成方法
    private HSSFSheet createExcelTableHead(HSSFSheet sheet, List<ElementTreeVO> titleData, Integer startRow, Integer startCol) {
        Integer row = startRow;
        Integer col = startCol;
        Integer maxCol = Integer.parseInt(titleData.get(0).getCellColumns());
        for (ElementTreeVO cell : titleData) {
            sheet.addMergedRegion(new CellRangeAddress(row, row + Integer.parseInt(cell.getCellRows()), col, col + Integer.parseInt(cell.getCellColumns())));//合并
            col += Integer.parseInt(cell.getCellColumns());
            if (!ListToolUtils.isEmpty(cell.getChildren())) {
                createExcelTableHead(sheet, cell.getChildren(), row, col);
            }
            col = startCol;
            row += Integer.parseInt(cell.getCellRows());
        }
        return sheet;
    }

    /*
     * Title单元格样式
     */
    private HSSFCellStyle getTopTitleStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        font.setFontName("Courier New");
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setFont(font);
        style.setWrapText(false);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /*
     * 内容单元格样式
     */
    private HSSFCellStyle getContentStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontName("Courier New");
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }


    /*
     * 导出时间单元格样式
     */
    private HSSFCellStyle getDateStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontName("Courier New");
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 加水印 不支持合并的单元格
     *
     * @param wb                Excel Workbook
     * @param sheet             sheet
     * @param watermarkPath     水印路径
     * @param startXCol         水印起始列
     * @param startYRow         水印起始行
     * @param betweenXCol       水印横向之间间隔多少列
     * @param betweenYRow       水印纵向之间间隔多少行
     * @param XCount            横向共有水印多少个
     * @param YCount            纵向共有水印多少个
     * @param waterRemarkWidth  水印图片宽度为多少列
     * @param waterRemarkHeight 水印图片高度为多少行
     */
    private static void addWatermark(Workbook wb, Sheet sheet, String watermarkPath, int startXCol, int startYRow,
                                     int betweenXCol, int betweenYRow, int XCount, int YCount,
                                     int waterRemarkWidth, int waterRemarkHeight) {
        if (!watermarkPath.endsWith("png") && !watermarkPath.endsWith("PNG")) {
            logger.error("水印功能目前只支持png格式的图片");
        }
        //加载图片
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        try {
            InputStream imageIn = new FileInputStream(watermarkPath);
            if (imageIn.available() >= 0) {
                BufferedImage bufferImg = ImageIO.read(imageIn);
                if (bufferImg != null) {
                    ImageIO.write(bufferImg, "png", byteArrayOut);
                    //打水印
                    Drawing drawing = sheet.createDrawingPatriarch();
                    //按照共需打印多少行水印进行循环
                    for (int yCount = 0; yCount < YCount; yCount++) {
                        //按照每行需要打印多少个水印进行循环
                        for (int xCount = 0; xCount < XCount; xCount++) {
                            //创建水印图片位置
                            int xIndexInteger = startXCol + (xCount * waterRemarkWidth) + (xCount * betweenXCol);
                            int yIndexInteger = startYRow + (yCount * waterRemarkHeight) + (yCount * betweenYRow);

                            /*
                             * 参数定义：
                             * 第一个参数是（x轴的开始节点）；
                             * 第二个参数是（是y轴的开始节点）；
                             * 第三个参数是（是x轴的结束节点）；
                             * 第四个参数是（是y轴的结束节点）；
                             * 第五个参数是（是从Excel的第几列开始插入图片，从0开始计数）；
                             * 第六个参数是（是从excel的第几行开始插入图片，从0开始计数）；
                             * 第七个参数是（图片宽度，共多少列）；
                             * 第8个参数是（图片高度，共多少行）；
                             */
                            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, xIndexInteger, yIndexInteger, waterRemarkWidth, waterRemarkHeight);
                            Picture pic = drawing.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), Workbook.PICTURE_TYPE_PNG));
                            pic.resize();
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            logger.error("水印图片没有找到");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
