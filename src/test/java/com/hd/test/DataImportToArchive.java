package com.hd.test;

import application.Application;
import com.hd.data.dao.OldProjectInfoToArchiveMapper;
import com.hd.data.entity.OldProjectInfoToArchive;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DataImportToArchive extends DataImportTest{

    private static final Logger logger = LoggerFactory.getLogger(DataImportTest.class);

    @Resource
    private OldProjectInfoToArchiveMapper oldProjectInfoToArchiveMapper;

    @Test
    public void importData() {
        readExcel("D:/需要修改的数据，改到待归档.xlsx");
    }

    private void readExcel(String url) {
/*        File excelFile = new File(url);
        InputStream is;
        Workbook workbook;
        int sheetIndex = 0;
        int rowNum = 0;
        String contractNumStr = null;
        try {
            is = new FileInputStream(excelFile);
            workbook = getWorkBook(is, excelFile);
            int sheetNum = workbook.getNumberOfSheets();
            for (int index = 0; index < sheetNum; index++) {
                sheetIndex = index;
                Sheet sheet = workbook.getSheetAt(index);
                if (sheet == null) {
                    continue;
                }
                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    rowNum = rowIndex;
                    Row row = sheet.getRow(rowIndex);
                    if (row == null) {
                        continue;
                    }
                    OldProjectInfoToArchive oldProjectInfoToArchive = new OldProjectInfoToArchive();
                    String contractNum = getCellValue(row.getCell(0), CellType.STRING);
                    if(!contractNum.toUpperCase().contains("BJ")){
                        contractNum += "BJ";
                    }
                    contractNumStr = contractNum;
                    String projectName = getCellValue(row.getCell(1), CellType.STRING);
                    setValue(oldProjectInfoToArchive, contractNum, projectName);
                    oldProjectInfoToArchiveMapper.insert(oldProjectInfoToArchive);
                }
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("第：" + (sheetIndex + 1) + "个Sheet页中，第：" + rowNum + "行，合同编号为：" + contractNumStr + "的数据，合同类别项有误，无法导入。");
        }*/
    }

}
