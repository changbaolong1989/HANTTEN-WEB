package com.hd.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

import static org.apache.catalina.startup.ExpandWar.delete;

@Component
@Lazy(false)
public class FileUtils {

    public FileUtils(){};

    public static String initPath;

    /**
     * initPath
     *
     * @param initPath 项目描述（略）
     */
    @Value("${file.request.rootpath}")
    public void setInitPath(String initPath) {
        FileUtils.initPath = initPath;
    }

    /**
     * Description: 向本地上传单个文件，并存储在本地
     *
     * @param filePath 上传文件路径
     * @return 成功返回true，否则返回false
     * @Version1.0
     */
    public static boolean uploadFile(String filePath, MultipartFile multipartFile) {

        File fileDest = new File(initPath + filePath);

        // 判断上传的文件的长度是否为0，排除文件大小不为0的文件
        if(multipartFile.getSize() == 0) {
            return false;
        }

        // 检测是否存在目录
        if (!fileDest.getParentFile().exists()) {
            fileDest.getParentFile().mkdirs();
        }

        try {

            multipartFile.transferTo(fileDest);
//            FileOutputStream out = new FileOutputStream(fileDest);
//            out.write(multipartFile.getBytes());
//            out.flush();
//            out.close();
            return true;

        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Description: 删除本地文件
     *
     * @param filePath 路径
     */
    public static void deleteFile(String filePath) {

        // 找到文件
        File file = new File(initPath + filePath);

        // 判断路径是否存在
        if (file.exists()) {
            //boolean isFile():测试此抽象路径名表示的文件是否是一个标准文件,如果是,则删除
            if (file.isFile()) {
                file.delete();

                //不是文件，对于文件夹的操作
            } else {
                //listFiles方法：返回file路径下所有文件和文件夹的绝对路径
                File[] listFiles = file.listFiles();

                if (null != listFiles){
                    for (File file2 : listFiles) {
                        /*
                         * 递归作用：由外到内先一层一层删除里面的文件 再从最内层 反过来删除文件夹
                         *    注意：此时的文件夹在上一步的操作之后，里面的文件内容已全部删除
                         *         所以每一层的文件夹都是空的  -> 最后就可以直接删除了
                         */
                        delete(file2);
                    }
                }
            }
            file.delete();
        } else {
            System.out.println("该文件路径不存在！");
        }

    }


}
