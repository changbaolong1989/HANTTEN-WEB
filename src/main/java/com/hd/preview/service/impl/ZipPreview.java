package com.hd.preview.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hd.preview.common.PreviewCommon;
import com.hd.preview.service.AbsPreview;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class ZipPreview extends AbsPreview {

    @Resource
    private PreviewCommon previewCommon;

    @Override
    public Object preview(String path) throws IOException {
        String folderName = (int) ((Math.random() * 9 + 1) * 1000) + "_" + Long.toString(new Date().getTime());//4位随机数_毫秒数
        String unZipPath = previewCommon.getPath() + folderName;
        File sourceFile = new File(path);
        previewCommon.unZip(sourceFile, unZipPath);
        JSONArray fileTree = new JSONArray();
        previewCommon.getAllFile(unZipPath,fileTree);
        return fileTree.toJSONString();
    }

}
