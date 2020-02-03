package com.hd.mybatisGen.app;

import org.mybatis.generator.api.ShellRunner;

public class MainApp {

    public static void main(String[] args) {
        args = new String[] { "-configfile", "src\\main\\resources\\mybatis-generator-config.xml", "-overwrite" };
        ShellRunner.main(args);
    }
}
