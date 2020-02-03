package com.hd.export.service;

import com.hd.export.bean.ExportData;
import com.hd.manager.vo.ElementTreeVO;
import org.apache.poi.POIDocument;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Date;
import java.util.List;

public interface Export {

    POIDocument export(List<ExportData> exportDatas);

    /**
     * 带有模板表头excel表格生成方法
     */
    Workbook export(Workbook workbook, List<ExportData> exportDatas);
    /**
     * 带有导出时间excel表格生成方法
     */
    POIDocument export(List<ExportData> exportDatas,Date date);

    /**
     * 特殊标头样式excel表格生成方法
     */
    POIDocument export(List<ExportData> exportDatas,List<ElementTreeVO> titleData);

    POIDocument exportWeek(List<ExportData> exportDatas);





}
