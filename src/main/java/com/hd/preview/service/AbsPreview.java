package com.hd.preview.service;

import com.hd.preview.common.PreviewCommon;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;

@Service
public abstract class AbsPreview implements Preview {

    @Resource
    private PreviewCommon previewCommon;

    public void writeFile(String path, byte[] data) {
        try (OutputStream os = new FileOutputStream(new File(path))) {
            os.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void writeFile(String path, OutputStream outputStream) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(new File(path))) {
            byte[] bytes = new byte[1024];
            int tmp;
            while ((tmp = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, tmp);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    protected void writeFile(String path, OutputStream outputStream, String inputCharset, String outputCharset) throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(new File(path)), inputCharset);
        try (Writer writer = new OutputStreamWriter(outputStream, outputCharset)) {
            char[] buffer = new char[1024];
            int len;
            while ((len = reader.read(buffer)) != -1) {
                writer.write(new String(buffer, 0, len));
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public String createFile(String suffix) throws IOException {
        String fileName = (int) ((Math.random() * 9 + 1) * 1000) + "_" + Long.toString(new Date().getTime());//4位随机数_毫秒数
        String path = previewCommon.getPath() + fileName + "." + suffix;
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return path;
    }

    @Override
    public Object preview(String path) throws IOException {
        return null;
    }

    @Override
    public void preview(String path, OutputStream outputStream) throws IOException {

    }

    @Override
    public void preview(InputStream inputStream, OutputStream outputStream) throws IOException {

    }
}
