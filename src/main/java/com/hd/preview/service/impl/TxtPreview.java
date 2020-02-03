package com.hd.preview.service.impl;

import com.hd.preview.service.AbsPreview;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class TxtPreview extends AbsPreview {

    @Value("${system-param.preview.txt-charset}")
    private String outputCharset;

    @Override
    public void preview(String path, OutputStream outputStream) throws IOException {
        writeFile(path, outputStream, "UTF-8", outputCharset);
    }

}
