package com.hd.preview.enums;

import com.hd.common.util.SpringUtils;
import com.hd.preview.service.Preview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件预览类型枚举
 */
public enum PreviewFileTypeEnum {

    PDF("com.hd.preview.service.impl.PdfPreview"),
    DOC("com.hd.preview.service.impl.WordPreview"),
    DOCX("com.hd.preview.service.impl.WordPreview"),
    TXT("com.hd.preview.service.impl.TxtPreview"),
    ZIP("com.hd.preview.service.impl.ZipPreview"),
    JPG("com.hd.preview.service.impl.PicPreview"),
    PNG("com.hd.preview.service.impl.PicPreview"),
    JPEG("com.hd.preview.service.impl.PicPreview");


    private static Logger logger = LoggerFactory.getLogger(PreviewFileTypeEnum.class);
    private String className;

    PreviewFileTypeEnum(String className) {
        this.className = className;
    }

    public static Preview getInstance(String fileType) {
        Preview preview = null;
        try {
            PreviewFileTypeEnum fileTypeEnum = PreviewFileTypeEnum.valueOf(fileType);
            String clazz = fileTypeEnum.getClassName();
            preview = (Preview) SpringUtils.getBean(Class.forName(clazz));
        } catch (IllegalArgumentException e) {
            logger.error("不支持的文件预览类型！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return preview;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
