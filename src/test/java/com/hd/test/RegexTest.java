package com.hd.test;

import application.Application;
import org.apache.commons.lang3.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RegexTest {


    private static String regexHandle(String remark, String index) {
        if (StringUtils.isBlank(remark)) return null;
        String HTRegex = ".*?HT(\\d+).*?";
        String HTFullRegex = ".*?HT(.*?)-([a-zA-Z\\d]+).*?";

        Pattern HTPattern = Pattern.compile(HTRegex);
        Matcher HTMatcher = HTPattern.matcher(remark);
        Pattern HTFullPattern = Pattern.compile(HTFullRegex);
        Matcher HTFullMatcher = HTFullPattern.matcher(remark);

        if (HTFullMatcher.matches()) {
            if (HTFullMatcher.group(1).contains("BJ")) {
                return "HT" + HTFullMatcher.group(1) + "-" + HTFullMatcher.group(2);
            } else {
                return "HT" + HTFullMatcher.group(1) + "BJ-" + HTFullMatcher.group(2);
            }
        } else if (HTMatcher.matches()) {
            return "HT" + HTMatcher.group(1) + "BJ-" + index;
        } else {
            return null;
        }
    }



}
