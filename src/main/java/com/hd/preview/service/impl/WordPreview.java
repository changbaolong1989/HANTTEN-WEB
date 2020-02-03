package com.hd.preview.service.impl;

import com.hd.preview.service.AbsPreview;
import org.jodconverter.DocumentConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.jodconverter.office.OfficeException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class WordPreview extends AbsPreview {

    //@Resource
    private DocumentConverter documentConverter;

    //word将转换成pdf实现预览
    private static final String SUFFIX = "pdf";

    @Override
    public void preview(String wordPath, OutputStream outputStream) throws IOException {
        String pdfFilePath = createFile(SUFFIX);
        File wordFile = new File(wordPath);
        try {
            documentConverter.convert(wordFile).to(new File(pdfFilePath)).execute();
            writeFile(pdfFilePath, outputStream);
        } catch (OfficeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void preview(InputStream inputStream, OutputStream outputStream) throws IOException {
        String pdfFilePath = createFile(SUFFIX);
        try {
            documentConverter.convert(inputStream).as(DefaultDocumentFormatRegistry.DOCX).to(new File(pdfFilePath)).execute();
            writeFile(pdfFilePath, outputStream);
        } catch (OfficeException e) {
            e.printStackTrace();
        }
    }
}
