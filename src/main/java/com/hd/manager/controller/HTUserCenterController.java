package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.manager.service.HTUserService;
import com.hd.manager.vo.HTUserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 个人中心Controller
 *
 * @author zjy
 * Created in 2019/7/1 16:56
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials ="true")
public class HTUserCenterController extends BaseController {

    /**
     * 用户service 业务层资源
     */
    @Resource
    private HTUserService htUserService;

    /**
     * 查询用户详情
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchUserInfo")
    @ResponseBody
    public Map<String, Object> searchUserInfo(HttpSession session, @RequestBody HTUserVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 用户ID
            if(StringToolUtils.isNull(paramVO.getUserId())){
                paramVO = putLoginUserToVOFromSession(paramVO, session);
                paramVO.setUserId(paramVO.getLoginUserInfo().getLoginUserId());
            }

            // 更改用户信息
            HTUserVO retUser = htUserService.excSearchUserInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回用户信息信息
            resultMap.put(Constant.DATA_VO_KEY, retUser);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 修改用户密码
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateUserPwd")
    @ResponseBody
    public Map<String, Object> updateUserPwd(HttpSession session, @RequestBody HTUserVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 更改用户密码
            htUserService.excUpdateUserPwd(paramVO);

            // 清除session
            //session.removeAttribute("loginUserInfo");

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }


}
