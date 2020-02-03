package com.hd.export.enums;

import com.hd.common.util.SpringUtils;
import com.hd.export.service.Export;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ExportTypeEnum {

    EXCEL("com.hd.export.service.impl.ExcelExport");

    private static Logger logger = LoggerFactory.getLogger(ExportTypeEnum.class);
    private String className;

    ExportTypeEnum(String className) {
        this.className = className;
    }

    public static Export getInstance(String fileType) {
        Export export = null;
        try {
            ExportTypeEnum exportTypeEnum = ExportTypeEnum.valueOf(fileType);
            String clazz = exportTypeEnum.getClassName();
            export = (Export) SpringUtils.getBean(Class.forName(clazz));
        } catch (IllegalArgumentException e) {
            logger.error("不支持的文件导出类型！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return export;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
