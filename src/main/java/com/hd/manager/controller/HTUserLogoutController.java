package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userLogout")
@CrossOrigin
public class HTUserLogoutController extends BaseController {

    @ResponseBody
    @RequestMapping("/logout")
    public Map<String, Object> userLogout(HttpSession session) {
        Map<String, Object> resultMap;
        try {
            session.removeAttribute("loginUserInfo");
            session.invalidate();
            resultMap = new HashMap<>(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_LOGIN_0001)));
        } catch (Exception lex) {
            resultMap = setReturnMessageMap(FAILURE, "session销毁异常");
        }
        return resultMap;
    }


}
