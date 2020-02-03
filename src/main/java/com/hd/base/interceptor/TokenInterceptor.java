package com.hd.base.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.common.util.Constant;
import com.hd.common.util.SpringUtils;
import com.hd.common.util.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * 登录拦截
 *
 * @author JLF
 */
@Component
@Lazy(false)
@CrossOrigin(origins = "*")
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static final int NO_AUTH_STATE = 401;

    //未登录请求头特点
    private static final List<String> unLoginRequestHeaders0 = new ArrayList<>();
    private static final List<String> unLoginRequestHeaders1 = new ArrayList<>();
    private static final List<String> unLoginRequestHeaders2 = new ArrayList<>();

    /**
     * 请求拦截
     *
     * @param request  拦截的请求
     * @param response 本次处理的响应
     * @param handler  本次处理的对应controller方法
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        /*
         * 2019.10.29修改
         *
         * 问题现象：当用户未登录时，在地址栏输入地址时，页面显示401非法访问。
         *
         * 问题原因：
         * vue路由在dev/跨域/前后端分离时，路由拦截有效。
         *        在build/单体应用时，路由拦截失效。
         *
         * 目前并没有找到优雅的解决方式，所以在后台暴力解决
         *
         */
        if (!"OPTIONS".equals(request.getMethod())) {
            if (sessionIsNULL(request)) {
                Enumeration enumeration = request.getHeaderNames();
                if (isUnLoginRequest(enumeration)) {//未登录
                    response.sendRedirect("/");
                    return false;
                } else {//已登录
                    response.setStatus(NO_AUTH_STATE);
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isUnLoginRequest(Enumeration enumeration) {
        List<String> list = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement().toString());
        }
        list.sort(Comparator.comparing(String::hashCode));
        return unLoginRequestHeaders0.equals(list) || unLoginRequestHeaders1.equals(list) || unLoginRequestHeaders2.equals(list);
    }

    private static boolean tokenIsNULL(String authorization) {
        return authorization == null || !TokenUtils.tokenSet.contains(authorization);
    }

    private static boolean sessionIsNULL(HttpServletRequest request) {
        return request.getSession().getAttribute("loginUserInfo") == null;
    }

    private static boolean isSafe(String authorization) {
        return StringUtils.isNotBlank(TokenUtils.decode(authorization));
    }


    /**
     * 将map对象转换为json字符串
     *
     * @param returnStatusValue     String 返回消息状态值
     * @param returnMsgContentValue String 返回消息编码
     * @return retMessageMap Map<String, Object> 返回画面消息对象
     */
    private Map<String, Object> getReturnMessageMap(String returnStatusValue, String returnMsgContentValue) {
        // 返回消息对象
        Map<String, Object> retMessageMap = new HashMap<String, Object>();
        // 返回消息状态
        retMessageMap.put(Constant.RETURN_STATUS_KEY, returnStatusValue);
        // 返回的消息
        retMessageMap.put(Constant.RETURN_MESSAGE_KEY, returnMsgContentValue);
        return retMessageMap;
    }

    /**
     * 发送认证失败错误响应
     *
     * @param paramResponse          响应
     * @param paramIsDispatchCtlBool 是否是跳页请求
     */
    private void sendErrorResponse(HttpServletResponse paramResponse, String paramErrorMsgCode, boolean paramIsDispatchCtlBool) throws Exception {

        // 如果是跳页请求，就发送重定向，跳转至 登录页
        if (paramIsDispatchCtlBool) {
            // 跳转至 登录页
            paramResponse.sendRedirect(Constant.ROOT_PATH);
        } else {
            // 取得响应Map
            Map<String, Object> respMap = getReturnMessageMap(
                    Constant.RESPONSE_STATUS_AUTH_ERROR,
                    SpringUtils.getMessage(paramErrorMsgCode)
            );

            writeAjaxResponse(paramResponse, respMap);
        }
    }

    /**
     * 写Ajax 响应
     *
     * @param paramResponse 响应
     * @param paramRespMap  响应值Map
     */
    private void writeAjaxResponse(HttpServletResponse paramResponse, Map<String, Object> paramRespMap) throws Exception {

        ObjectMapper tmpObjectMapper = new ObjectMapper();
        // Map -> Json 字符串
        String respJsonStr = tmpObjectMapper.writeValueAsString(paramRespMap);

        // 写响应
        paramResponse.setHeader("Content-Type", "application/json");
        PrintWriter tmpRespWriter = paramResponse.getWriter();
        tmpRespWriter.print(respJsonStr);
    }

    static {
        unLoginRequestHeaders0.add("accept");
        unLoginRequestHeaders0.add("connection");
        unLoginRequestHeaders0.add("accept-encoding");
        unLoginRequestHeaders0.add("host");
        unLoginRequestHeaders0.add("user-agent");
        unLoginRequestHeaders0.add("accept-language");
        unLoginRequestHeaders0.add("upgrade-insecure-requests");

        unLoginRequestHeaders1.add("accept");
        unLoginRequestHeaders1.add("accept-encoding");
        unLoginRequestHeaders1.add("accept-language");
        unLoginRequestHeaders1.add("user-agent");
        unLoginRequestHeaders1.add("upgrade-insecure-requests");
        unLoginRequestHeaders1.add("connection");
        unLoginRequestHeaders1.add("host");
        unLoginRequestHeaders1.add("cookie");

        unLoginRequestHeaders2.add("accept");
        unLoginRequestHeaders2.add("accept-encoding");
        unLoginRequestHeaders2.add("accept-language");
        unLoginRequestHeaders2.add("user-agent");
        unLoginRequestHeaders2.add("upgrade-insecure-requests");
        unLoginRequestHeaders2.add("connection");
        unLoginRequestHeaders2.add("host");
        unLoginRequestHeaders2.add("cookie");
        unLoginRequestHeaders2.add("cache-control");

        unLoginRequestHeaders0.sort(Comparator.comparing(String::hashCode));
        unLoginRequestHeaders1.sort(Comparator.comparing(String::hashCode));
        unLoginRequestHeaders2.sort(Comparator.comparing(String::hashCode));
    }

}
