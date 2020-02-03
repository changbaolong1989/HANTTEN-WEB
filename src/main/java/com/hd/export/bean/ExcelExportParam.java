package com.hd.export.bean;

import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelExportParam {

    private int sheetNum;

    private int titleRowNum;

    private String titleValue;

    private int timeRowNum;

    private int dataRowNum;

    private short dataLineHeight;

    private int startColumn;

    private CellStyle contentStyle;

    private CellStyle subTitleStyle;


    public int getSheetNum() {
        return sheetNum;
    }

    public void setSheetNum(int sheetNum) {
        this.sheetNum = sheetNum;
    }

    public int getTitleRowNum() {
        return titleRowNum;
    }

    public void setTitleRowNum(int titleRowNum) {
        this.titleRowNum = titleRowNum;
    }

    public String getTitleValue() {
        return titleValue;
    }

    public void setTitleValue(String titleValue) {
        this.titleValue = titleValue;
    }

    public int getTimeRowNum() {
        return timeRowNum;
    }

    public void setTimeRowNum(int timeRowNum) {
        this.timeRowNum = timeRowNum;
    }

    public int getDataRowNum() {
        return dataRowNum;
    }

    public void setDataRowNum(int dataRowNum) {
        this.dataRowNum = dataRowNum;
    }

    public short getDataLineHeight() {
        return dataLineHeight;
    }

    public void setDataLineHeight(short dataLineHeight) {
        this.dataLineHeight = dataLineHeight;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    public CellStyle getContentStyle() {
        return contentStyle;
    }

    public void setContentStyle(CellStyle contentStyle) {
        this.contentStyle = contentStyle;
    }

    public CellStyle getSubTitleStyle() {
        return subTitleStyle;
    }

    public void setSubTitleStyle(CellStyle subTitleStyle) {
        this.subTitleStyle = subTitleStyle;
    }
}
