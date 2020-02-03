package com.hd.preview.service.impl;

import com.hd.preview.service.AbsPreview;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfPreview extends AbsPreview {

    @Override
    public void preview(String path, OutputStream outputStream) throws IOException {
        writeFile(path, outputStream);
    }

}
