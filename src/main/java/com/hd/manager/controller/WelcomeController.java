package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 首页Controller
 *
 * @author JLF
 * Created in 2019/3/12 14:44
 */
@Controller
public class WelcomeController extends BaseController {

    /**
     * 首页登录页 跳转
     *
     * @return 转发登录页
     */
    @RequestMapping(value = {
            "/",
            "/page/**"
    })
    public String toPcWelcome(){
        return "/PC/index";
    }

    /**
     * 首页登录页 跳转
     *
     * @return 转发登录页
     */
    @RequestMapping(value = {
            "/app/**"
    })
    public String toAppWelcome(){
        return "/APP/index";
    }

    /**
     * 测试返回Json
     *
     * @return Json
     */
    @RequestMapping("/hello")
    @ResponseBody
    public Map<String,Object> testJson(){
        Map<String,Object> resultHashMap = new HashMap<>();

        resultHashMap.put("hello", UUID.randomUUID().toString());

        return resultHashMap;
    }
}
