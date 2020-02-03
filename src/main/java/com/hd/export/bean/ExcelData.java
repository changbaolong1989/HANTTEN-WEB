package com.hd.export.bean;

import com.hd.manager.vo.ElementTreeVO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ExcelData extends ExportData implements Serializable {

    private static final long serialVersionUID = 3275816922595630201L;

    private String sheetName;

    private String title;

    private String[] columnTxt;

    private String[] columnNames;

    private int[] columnWidth;

    private List<Map<String, Object>> dataList;

    private List<ElementTreeVO> specialDataList;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }

    public int[] getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(int[] columnWidth) {
        this.columnWidth = columnWidth;
    }

    public String[] getColumnTxt() {
        return columnTxt;
    }

    public void setColumnTxt(String[] columnTxt) {
        this.columnTxt = columnTxt;
    }

    public List<ElementTreeVO> getSpecialDataList() {
        return specialDataList;
    }

    public void setSpecialDataList(List<ElementTreeVO> specialDataList) {
        this.specialDataList = specialDataList;
    }


    public static class SerialNum {

        private int undertakeTypeNum = 1;//承包方式序号
        private int undertakeTypeSubNum = 1;//承包方式子序号


        public int getUndertakeTypeNum() {
            return undertakeTypeNum;
        }

        public void setUndertakeTypeNum(int undertakeTypeNum) {
            this.undertakeTypeNum = undertakeTypeNum;
        }

        public int getUndertakeTypeSubNum() {
            return undertakeTypeSubNum;
        }

        public void setUndertakeTypeSubNum(int undertakeTypeSubNum) {
            this.undertakeTypeSubNum = undertakeTypeSubNum;
        }

    }

}
