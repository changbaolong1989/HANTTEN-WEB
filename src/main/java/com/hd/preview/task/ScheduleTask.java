package com.hd.preview.task;

import com.hd.preview.common.PreviewCommon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Configuration
@EnableScheduling
public class ScheduleTask {

    @Resource
    private PreviewCommon previewCommon;

    @Scheduled(fixedRate = 15 * 60 * 1000)
    private void DeleteTempFile() {
        String exp = "\\d{4}_(\\d+).[a-zA-Z]+|\\d{4}_(\\d+)";
        long time = new Date().getTime() - 30 * 60 * 1000;
        File file = new File(previewCommon.getPath());
        File[] files = file.listFiles();
        for (File f : files != null ? files : new File[0]) {
            String fullName = f.getName();
            Pattern pattern = Pattern.compile(exp);
            Matcher matcher = pattern.matcher(fullName);
            if (matcher.matches()) {
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    if (StringUtils.isNotBlank(matcher.group(i))) {
                        long mills = Long.parseLong(matcher.group(i));
                        if (mills < time) {
                            delFile(f);
                            //f.delete();
                        }
                    }
                }
            }
        }
    }

    static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

}
