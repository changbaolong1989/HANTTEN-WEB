package com.hd.export.style;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

public class ExcelCellStyle {

    /**
     * 获取单元格样式
     *
     * @param workbook 工作簿
     * @param fontName 字体
     * @param fontSize 字体大小
     * @param ha       水平对齐方式
     * @param va       垂直对齐方式
     * @return CellStyle
     */
    public static CellStyle getStyle(Workbook workbook, String fontName, short fontSize, HorizontalAlignment ha, VerticalAlignment va) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontSize);
        style.setFont(font);
        if (ha != null) style.setAlignment(ha);
        if (va != null) style.setVerticalAlignment(va);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public static CellStyle getStyleFontBlod(Workbook workbook, String fontName, short fontSize, HorizontalAlignment ha, VerticalAlignment va) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontSize);
        font.setBold(true);
        style.setFont(font);
        if (ha != null) style.setAlignment(ha);
        if (va != null) style.setVerticalAlignment(va);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public static CellStyle getDefaultStyleBlod(Workbook workbook, String fontName, short fontSize, HorizontalAlignment ha, VerticalAlignment va) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontSize);
        font.setBold(true);
        style.setFont(font);
        if (ha != null) style.setAlignment(ha);
        if (va != null) style.setVerticalAlignment(va);
        return style;
    }

    public static CellStyle getDefaultStyle(Workbook workbook, String fontName, short fontSize, HorizontalAlignment ha, VerticalAlignment va) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontSize);
        style.setFont(font);
        if (ha != null) style.setAlignment(ha);
        if (va != null) style.setVerticalAlignment(va);
        return style;
    }

    public static void setBorderStyle(Sheet sheet, CellRangeAddress region) {
        RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
    }

    public static CellStyle getSubTitileStyle(Workbook workbook, IndexedColors backgroundcolor, String fontName, short fontSize, HorizontalAlignment ha, VerticalAlignment va) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontSize);
        if (backgroundcolor != null) {
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //背景色
            style.setFillForegroundColor(backgroundcolor.index);
        }
        //字体
        style.setFont(font);
        style.setAlignment(ha);
        style.setVerticalAlignment(va);
        return style;
    }

    public static CellStyle getSubTitileBlodStyle(Workbook workbook, IndexedColors backgroundcolor, String fontName, short fontSize, HorizontalAlignment ha, VerticalAlignment va) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontSize);
        font.setBold(true);
        if (backgroundcolor != null) {
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //背景色
            style.setFillForegroundColor(backgroundcolor.index);
        }
        //字体
        style.setFont(font);
        style.setAlignment(ha);
        style.setVerticalAlignment(va);
        return style;
    }

}
