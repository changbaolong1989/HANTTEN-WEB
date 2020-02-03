package com.hd.manager.controller;

import com.alibaba.fastjson.JSON;
import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.*;
import com.hd.manager.service.HTUserLoginService;
import com.hd.manager.service.HTUserPowerService;
import com.hd.manager.service.HTUserService;
import com.hd.manager.vo.HTMenuVO;
import com.hd.manager.vo.HTUserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

@Controller
@RequestMapping("/userLogin")
public class HTUserLoginController extends BaseController {

    @Resource
    private HTUserLoginService htUserLoginService;
    
    @Resource
    private HTUserPowerService htUserPowerService;

    @Resource
    private HTUserService htUserService;

    /**
     * 从公众号链接中跳转到此，重定向给微信
     *
     * @return 路径
     */
    @RequestMapping("/toWx")
    public String helloWx() {

        // 重定向
        return REDIRECT + "https://open.weixin.qq.com/connect/oauth2/authorize" +
                "?appid="+Constant.APP_ID
                +"&redirect_uri="+Constant.REDIRECT_URI
                +"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

    }

    /**
     * 微信通过重定向跳转到此，并传递openId
     *
     * @return 登录页
     */
    @RequestMapping("/toLogin")
    public HttpServletResponse toLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        try {

            // 获取code码
            String code = request.getParameter("code");

            // 获取openid的url
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+Constant.APP_ID+"&secret="+Constant.SECRET+"&code="+code+"&grant_type=authorization_code";

            URL u = new URL(url);
            HttpsURLConnection huconn = (HttpsURLConnection) u.openConnection();
            BufferedReader in = null;
            StringBuilder resultMap = new StringBuilder();
            huconn.connect();
            in = new BufferedReader(new InputStreamReader(huconn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                resultMap.append(line);
            }
            if (in != null) {
                in.close();
            }

            // openid
            String openId = JSON.parseObject(resultMap.toString()).getString("openid");

            // 用户对象
            HTUserVO userVO = new HTUserVO();

            // 设置openid
            userVO.setOpenId(openId);

            // 查询用户详情信息
            HTUserVO htUserVO = htUserService.excSearchUserDetailInfo(userVO);

            // openid
            Cookie openid = new Cookie("openid", openId);
            openid.setPath("/");
            response.addCookie(openid);

            // 如果有用户绑定信息
            if(null != htUserVO.getLoginUserId()){

                // 账户
                Cookie userAccount = new Cookie("userAccount", htUserVO.getUserAccount());
                userAccount.setPath("/");
                response.addCookie(userAccount);

                //查询用户所属部门ID集合
                List<String> departmentIds = htUserLoginService.searchDepartmentIds(htUserVO);

                //查询用户所属职位集合
                List<String> jobIds = htUserLoginService.searchJobIds(htUserVO);

                Map<String, Object> map = htUserPowerService.excSearchLoginUserPowerMap(jobIds);

                //查询用户菜单权限
                List<HTMenuVO> menuList = htUserLoginService.searchMenuList(htUserVO);

                //将信息放置到session中
                htUserVO.setDepartmentIds(departmentIds);

                Set result = new HashSet(jobIds);

                htUserVO.setJobIds(result);
                htUserVO.setMenuList(menuList);

                session.setAttribute("loginUserInfo", htUserVO);

            } else {
                // 账户
                Cookie userAccount = new Cookie("userAccount", "");
                userAccount.setPath("/");
                response.addCookie(userAccount);
            }

            // 重定向到app登录页
            response.sendRedirect(Constant.APP_LOGIN_URL);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @ResponseBody
    @RequestMapping("/getLoginInfo")
    @CrossOrigin(allowCredentials ="true")
    public Map<String,Object> getLoginInfo (HttpSession session,@RequestBody HTUserVO paramVO){
        Map<String,Object> resultMap = new HashMap<>();

        try {

            // 查询用户详情信息
            HTUserVO htUserVO = htUserService.excSearchUserDetailInfo(paramVO);

            //查询用户所属部门ID集合
            List<String> departmentIds = htUserLoginService.searchDepartmentIds(htUserVO);

            //查询用户所属职位集合
            List<String> jobIds = htUserLoginService.searchJobIds(htUserVO);

            Map<String, Object> map = htUserPowerService.excSearchLoginUserPowerMap(jobIds);

            //查询用户菜单权限
            List<HTMenuVO> menuList = htUserLoginService.searchMenuList(htUserVO);

            //将信息放置到session中
            htUserVO.setDepartmentIds(departmentIds);

            Set result = new HashSet(jobIds);
            htUserVO.setJobIds(result);
            htUserVO.setMenuList(menuList);
            session.setAttribute("loginUserInfo", htUserVO);

            //向前台传输所需信息
            resultMap.put("loginUserInfo", htUserVO);
            resultMap.put("loginPowerInfo", map);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_LOGIN_0001)));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;

    }

    @ResponseBody
    @RequestMapping("/checkLogin")
    @CrossOrigin(allowCredentials ="true")
    public Map<String,Object> userLogin (HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestBody HTUserVO paramVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //查询用户信息
            HTUserVO userVO = htUserLoginService.userLogin(paramVO);

            if(null != request.getCookies()){

                if(request.getCookies().length > 0){

                    for(int i = 0;i<request.getCookies().length;i++){

                        if("openid".equals(request.getCookies()[i].getName())){

                            // 设置openid
                            userVO.setOpenId(request.getCookies()[i].getValue());

                            // 添加openid
                            htUserService.excUpdateOpenIdByUserId(userVO);

                            // 账户
                            Cookie userAccount = new Cookie("userAccount", userVO.getUserAccount());
                            userAccount.setPath("/");
                            response.addCookie(userAccount);

                            break;

                        }
                    }

                }

            }

            //查询用户所属部门ID集合
            List<String> departmentIds = htUserLoginService.searchDepartmentIds(userVO);
            //查询用户所属职位集合
            List<String> jobIds = htUserLoginService.searchJobIds(userVO);
            Map<String, Object> map = htUserPowerService.excSearchLoginUserPowerMap(jobIds);
            //查询用户菜单权限
            List<HTMenuVO> menuList = htUserLoginService.searchMenuList(userVO);
            //将信息放置到session中
            userVO.setDepartmentIds(departmentIds);
            Set result = new HashSet(jobIds);
            userVO.setJobIds(result);
            userVO.setMenuList(menuList);
            session.setAttribute("loginUserInfo", userVO);
            //向前台传输所需信息
            resultMap.put("loginUserShowName",userVO.getRoleName()+"-"+userVO.getUserName());
            resultMap.put("loginUserInfo", userVO);
            resultMap.put("loginPowerInfo", map);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_LOGIN_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }
}
