package com.hd.manager.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hd.base.controller.BaseController;
import com.hd.base.dao.BaseDao;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.vo.HTUserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 报表Controller
 *
 * @author LH
 * Created in 2019/10/15 13:15
 */
@Controller
@RequestMapping("/qqEmail")
@CrossOrigin(allowCredentials = "true")
public class HTQQEmailConfigController extends BaseController {


    /**
     * 得到邮箱操作所需Token
     *
     * @return
     */
    @RequestMapping("/query/getQQEmailToken")
    @ResponseBody
    public Map<String,Object> getQQEmailToken(@RequestBody HTUserVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //访问外网路径
            String url = "https://api.exmail.qq.com/cgi-bin/gettoken?corpid=wm39e86766ed57cf04&corpsecret=bGTemYH-cB6ykjaROknAOy_BjVRzzdZsePXdRpXJLVLTUjuXQbRWlm2zXkv9Z3s0";
            URL u = new URL(url);
            //生成链接
            HttpsURLConnection huconn = (HttpsURLConnection) u.openConnection();
            BufferedReader in = null;
            StringBuilder result = new StringBuilder();
            //链接外网地址
            huconn.connect();
            //接收数据
            in = new BufferedReader(new InputStreamReader(huconn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            if (in != null) {
                in.close();
            }
            // 返回结果为JSON格式,转换格式后返回到前台
            resultMap.put(Constant.DATA_VO_KEY,JSON.parseObject(result.toString()));
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (Exception sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMessage());
        }
        return resultMap;
    }


    /**
     * 根据Token和UserId(邮箱地址)获取邮箱单点登录页面
     *
     * @return
     */
    @RequestMapping("/query/goToEmailPage")
    @ResponseBody
    public Map<String,Object> goToEmailPage(@RequestBody HTUserVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //访问外网路径
            String url = "https://api.exmail.qq.com/cgi-bin/service/get_login_url?access_token="+paramVO.getToken()+"&userid="+paramVO.getLoginUserInfo().getEmail();
            URL u = new URL(url);
            //生成链接
            HttpsURLConnection huconn = (HttpsURLConnection) u.openConnection();
            BufferedReader in = null;
            StringBuilder result = new StringBuilder();
            //链接外网地址
            huconn.connect();
            //接收数据
            in = new BufferedReader(new InputStreamReader(huconn.getInputStream(), "UTF-8"));
            String line;
            //格式转换
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            if (in != null) {
                in.close();
            }
            // 返回结果为JSON格式,转换格式后返回到前台
            resultMap.put(Constant.DATA_VO_KEY,JSON.parseObject(result.toString()));
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (Exception sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMessage());
        }
        return resultMap;
    }
}
