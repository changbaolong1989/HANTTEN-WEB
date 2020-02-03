package com.hd.preview.service.impl;

import com.hd.preview.service.AbsPreview;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@Service
public class ExcelPreview extends AbsPreview {

    //@Resource
    private DocumentConverter documentConverter;

    //excel将转换成html实现预览
    private static final String SUFFIX = "html";

    @Override
    public Object preview(String excelPath) throws IOException {
        String htmlFilePath = createFile(SUFFIX);
        File excelFile = new File(excelPath);
        try {
            documentConverter.convert(excelFile).to(new File(htmlFilePath)).execute();
        } catch (OfficeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
