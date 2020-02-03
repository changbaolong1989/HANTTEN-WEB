package com.hd.preview.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Preview {

    void preview(String path, OutputStream outputStream) throws IOException;

    Object preview(String path) throws IOException;

    void preview(InputStream inputStream, OutputStream outputStream) throws IOException;

}
