package com.hd.preview.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class PreviewCommon {

    @Value("${system-param.preview.is-deploy}")
    private boolean isDeploy;

    @Value("${file.openOffice.convertPath}")
    private String convertPath;

    public String getPath() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert path != null;
        String pathStr = path.getAbsolutePath() + "\\temporary\\";
        if (!isDeploy) {
            pathStr = pathStr.replace("\\target\\classes", "\\src\\main\\resources");
        }else{
            pathStr = convertPath;
        }
        return pathStr;
    }

    /**
     * zip解压
     */
    public void unZip(File sourceFile, String targetPath) throws IOException {
        ZipFile zipFile = new ZipFile(sourceFile);
        Enumeration<?> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            if (entry.isDirectory()) {
                String dirPath = targetPath + "/" + entry.getName();
                File dir = new File(dirPath);
                dir.mkdirs();
            } else {
                File targetFile = new File(targetPath + "/" + entry.getName());
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                targetFile.createNewFile();
                InputStream is = zipFile.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(targetFile);
                int len;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.close();
                is.close();
            }
        }
    }

    public void getAllFile(String path, JSONArray jsonArray) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    JSONObject jsonObject = new JSONObject(true);
                    jsonObject.put("label", f.getName());
                    JSONArray children = new JSONArray();
                    jsonObject.put("children", children);
                    jsonArray.add(jsonObject);
                    getAllFile(f.getAbsolutePath(), children);
                } else {
                    JSONObject jsonObject = new JSONObject(true);
                    jsonObject.put("label", f.getName());
                    jsonArray.add(jsonObject);
                }
            }
        }
    }

}
