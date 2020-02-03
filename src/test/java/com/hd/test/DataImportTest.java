package com.hd.test;

import application.Application;
import com.hd.data.dao.OldProjectInfoReplenish1199Mapper;
import com.hd.data.dao.OldUserInfoMapper;
import com.hd.data.entity.OldProjectInfoReplenish1199;
import com.hd.data.entity.OldUserInfo;
import com.hd.data.entity.OldUserInfoExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DataImportTest {

    private static final Logger logger = LoggerFactory.getLogger(DataImportTest.class);

    private static final String CANCELED = "A";

    @Resource
    private OldProjectInfoReplenish1199Mapper oldProjectInfoReplenish1199Mapper;

    @Resource
    private OldUserInfoMapper oldUserInfoMapper;

    @Test
    public void importData() {
        readExcel("D:/汉腾合同台账_已修改_去重复_第二版_99.xlsx");
    }


    private void readExcel(String url) {
        File excelFile = new File(url);
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
                    OldProjectInfoReplenish1199 oldProjectInfoReplenish1199 = new OldProjectInfoReplenish1199();
                    String contractNum = getCellValue(row.getCell(0), CellType.STRING);
                    contractNumStr = contractNum;
                    String projectName = getCellValue(row.getCell(1), CellType.STRING);
                    String contractType = getCellValue(row.getCell(2), CellType.STRING) == null ? "" : getCellValue(row.getCell(2), CellType.STRING);
                    String time = getCellValue(row.getCell(3), CellType.STRING);
                    Integer contractTime = convertTime(time);
                    String targetFlag = getCellValue(row.getCell(4), CellType.STRING) == null ? "" : getCellValue(row.getCell(4), CellType.STRING);
                    String isCancel = getCellValue(row.getCell(5), CellType.STRING) == null ? "" : getCellValue(row.getCell(5), CellType.STRING);
                    String applyUserName = getCellValue(row.getCell(6), CellType.STRING) == null ? "" : getCellValue(row.getCell(6), CellType.STRING);
                    if ("一".equals(contractType.trim())) {
                        oldProjectInfoReplenish1199.setClnid(1);
                        oldProjectInfoReplenish1199.setDeid(8);
                        if(applyUserName.equals("其他")){
                            applyUserName += "_造价";
                        }
                        setValue(oldProjectInfoReplenish1199, contractNum, projectName, contractTime, targetFlag, isCancel, applyUserName);
                        oldProjectInfoReplenish1199Mapper.insert(oldProjectInfoReplenish1199);
                    } else if ("二".equals(contractType.trim())) {
                        oldProjectInfoReplenish1199.setClnid(2);
                        oldProjectInfoReplenish1199.setDeid(6);
                        if(applyUserName.equals("其他")){
                            applyUserName += "_招标";
                        }
                        setValue(oldProjectInfoReplenish1199, contractNum, projectName, contractTime, targetFlag, isCancel, applyUserName);
                        oldProjectInfoReplenish1199Mapper.insert(oldProjectInfoReplenish1199);
                    } else if (StringUtils.isBlank(contractType.trim())) {
                        oldProjectInfoReplenish1199.setClnid(1);
                        oldProjectInfoReplenish1199.setDeid(8);
                        if(applyUserName.equals("其他")){
                            applyUserName += "_造价";
                        }
                        setValue(oldProjectInfoReplenish1199, contractNum, projectName, contractTime, targetFlag, isCancel, applyUserName);
                        oldProjectInfoReplenish1199Mapper.insert(oldProjectInfoReplenish1199);
                    } else if ("一、二".equals(contractType.trim())) {
                        OldProjectInfoReplenish1199 oldProjectInfoReplenish1199_1 = new OldProjectInfoReplenish1199();
                        OldProjectInfoReplenish1199 oldProjectInfoReplenish1199_2 = new OldProjectInfoReplenish1199();
                        oldProjectInfoReplenish1199_1.setClnid(1);
                        oldProjectInfoReplenish1199_1.setDeid(8);
                        String realName1 = applyUserName.equals("其他") ? applyUserName + "_造价" : applyUserName;
                        setValue(oldProjectInfoReplenish1199_1, contractNum, projectName, contractTime, targetFlag, isCancel, realName1);
                        oldProjectInfoReplenish1199_2.setClnid(2);
                        oldProjectInfoReplenish1199_2.setDeid(6);
                        String realName2 = applyUserName.equals("其他") ? applyUserName + "_招标" : applyUserName;
                        setValue(oldProjectInfoReplenish1199_2, contractNum, projectName, contractTime, targetFlag, isCancel, realName2);
                        oldProjectInfoReplenish1199Mapper.insert(oldProjectInfoReplenish1199_1);
                        oldProjectInfoReplenish1199Mapper.insert(oldProjectInfoReplenish1199_2);
                    } else {
                        logger.error("第：" + (index + 1) + "个Sheet页中，第：" + rowNum +  "行，合同编号为：" + contractNum + "的数据，合同类别项有误，无法导入。");
                        continue;
                    }
                }
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("第：" + (sheetIndex + 1) + "个Sheet页中，合同编号为：" + contractNumStr + "的数据，合同类别项有误，无法导入。");
        }
    }

    void setValue(OldProjectInfoReplenish1199 oldProjectInfoReplenish1199, String contractNum, String projectName, Integer contractTime, String targetFlag, String isCancel, String applyUserName) {
        oldProjectInfoReplenish1199.setId(999);
        oldProjectInfoReplenish1199.setClsid(1);
        oldProjectInfoReplenish1199.setClcid(1);
        oldProjectInfoReplenish1199.setClpid(1);
        oldProjectInfoReplenish1199.setClmid(1);
        oldProjectInfoReplenish1199.setPaid(1);
        oldProjectInfoReplenish1199.setName(projectName);
        oldProjectInfoReplenish1199.setClient("/");
        oldProjectInfoReplenish1199.setConsultant("/");
        oldProjectInfoReplenish1199.setThrid("/");
        oldProjectInfoReplenish1199.setPlace("/");
        oldProjectInfoReplenish1199.setInvestSum("0");
        oldProjectInfoReplenish1199.setContractSum("0");
        oldProjectInfoReplenish1199.setContractManager("/");
        oldProjectInfoReplenish1199.setContractTime(contractTime);
        oldProjectInfoReplenish1199.setRemark(contractNum);
        oldProjectInfoReplenish1199.setManager("/");
        oldProjectInfoReplenish1199.setNumber(contractNum);
        oldProjectInfoReplenish1199.setTime(contractTime);
        oldProjectInfoReplenish1199.setUpTime(contractTime);
        oldProjectInfoReplenish1199.setStatus(true);
        oldProjectInfoReplenish1199.setFinanceStatus(true);
        oldProjectInfoReplenish1199.setType(1);
        oldProjectInfoReplenish1199.setProjectContractNum(contractNum);
        if (StringUtils.isBlank(applyUserName) && !CANCELED.equals(isCancel)) {
            logger.error("合同：" + contractNum + "的编制人有误，无法导入");
        } else {
            if (CANCELED.equals(isCancel)) {
                oldProjectInfoReplenish1199.setUid(24);
            } else {
                OldUserInfoExample example = new OldUserInfoExample();
                example.createCriteria().andNameEqualTo(applyUserName);
                List<OldUserInfo> list = oldUserInfoMapper.selectByExample(example);
                if (list == null || list.isEmpty()) {
                    if("8".equals(oldProjectInfoReplenish1199.getDeid().toString())){
                        oldProjectInfoReplenish1199.setUid(914);
                    }else if("6".equals(oldProjectInfoReplenish1199.getDeid().toString())){
                        oldProjectInfoReplenish1199.setUid(915);
                    }
                } else {
                    oldProjectInfoReplenish1199.setUid(list.get(0).getUid());
                }
            }
        }
        oldProjectInfoReplenish1199.setTargetFlag(targetFlag);
        oldProjectInfoReplenish1199.setIsCancel(isCancel);
    }

/*    static void setValue(OldProjectInfoToArchive oldProjectInfoToArchive, String contractNum, String projectName) {
        oldProjectInfoToArchive.setId(999);
        oldProjectInfoToArchive.setUid(24);
        oldProjectInfoToArchive.setClsid(1);
        oldProjectInfoToArchive.setClcid(1);
        oldProjectInfoToArchive.setClpid(1);
        oldProjectInfoToArchive.setClmid(1);
        oldProjectInfoToArchive.setPaid(1);
        oldProjectInfoToArchive.setName(projectName);
        oldProjectInfoToArchive.setClient("/");
        oldProjectInfoToArchive.setConsultant("/");
        oldProjectInfoToArchive.setThrid("/");
        oldProjectInfoToArchive.setPlace("/");
        oldProjectInfoToArchive.setInvestSum("0");
        oldProjectInfoToArchive.setContractSum("0");
        oldProjectInfoToArchive.setContractManager("/");
        oldProjectInfoToArchive.setContractTime(null);
        oldProjectInfoToArchive.setRemark(contractNum);
        oldProjectInfoToArchive.setManager("/");
        oldProjectInfoToArchive.setNumber(contractNum);
        oldProjectInfoToArchive.setTime(null);
        oldProjectInfoToArchive.setUpTime(null);
        oldProjectInfoToArchive.setStatus(true);
        oldProjectInfoToArchive.setFinanceStatus(true);
        oldProjectInfoToArchive.setType(1);
        oldProjectInfoToArchive.setDeid(8);
        oldProjectInfoToArchive.setProjectContractNum(contractNum);
    }*/


    static Workbook getWorkBook(InputStream is, File file) throws Exception {
        Workbook workbook = null;
        if (file.getName().endsWith(".xls")) {
            workbook = new HSSFWorkbook(is);
        } else if (file.getName().endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(is);
        }
        return workbook;
    }

    static String getCellValue(Cell cell, CellType cellType) {
        String cellValue;
        if (cell == null || cell.toString().trim().equals("")) {
            return null;
        }
        if (cellType == CellType.STRING) {
            try {
                cellValue = cell.getStringCellValue().trim();
            } catch (IllegalStateException e) {
                //日期例如2013.1会识别为数字
                cellValue = Double.toString(cell.getNumericCellValue());
            }
            return StringUtils.isEmpty(cellValue) ? "" : cellValue;
        }
        return null;
    }

    private static Integer convertTime(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            return (int) (sdf1.parse(time).getTime() / 1000);
        } catch (ParseException e) {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.M.dd");
            try {
                return (int) (sdf2.parse(time).getTime() / 1000);
            } catch (ParseException e1) {
                SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy.M");
                try {
                    return (int) (sdf3.parse(time).getTime() / 1000);
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return null;
    }

}
