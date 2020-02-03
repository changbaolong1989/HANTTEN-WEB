package com.hd.base.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.vo.BaseVO;
import com.hd.common.service.CommonService;
import com.hd.common.util.StringEditor;
import com.hd.common.util.Constant;
import com.hd.manager.vo.HTUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


/**
 * 基础Controller
 *
 * @author JLF
 */
public class BaseController {

    /**
     * 重定向关键字
     */
    protected static final String REDIRECT = "redirect:";
    /**
     * 重定向关键字
     */
    protected static final String FORWARD = "forward:";
    /**
     * 响应失败关键字
     */
    protected static final String FAILURE = Constant.RESPONSE_STATUS_FAILURE;
    /**
     * 响应成功关键字
     */
    protected static final String SUCCESS = Constant.RESPONSE_STATUS_SUCCESS;

    /**
     * 取得log对象
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 标签对表单数据绑定
     *
     * @param binder 表单数据绑定对象
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        // 设置controller 方法接参时，对象内list 最大size 数（默认256，此处改为integer 的max value）
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);

        binder.registerCustomEditor(String.class, new StringEditor(true));
    }

    /**
     * 通用Service
     */
    @Resource
    private CommonService commonService;

    /**
     * 取得 当前 时间
     *
     * @return date 当前 时间
     */
    protected Date getCurrentDate() {

        return commonService.getCurrentDate();
    }

    /**
     * 将map对象转换为json字符串
     *
     * @param returnStatusValue     String 返回消息状态值
     * @param returnMsgContentValue String 返回消息编码
     * @return retMessageMap Map<String, Object> 返回画面消息对象
     */
    protected Map<String, Object> setReturnMessageMap(String returnStatusValue, String returnMsgContentValue) {
        // 返回消息对象
        Map<String, Object> retMessageMap = new HashMap<String, Object>();
        // 返回消息状态
        retMessageMap.put(Constant.RETURN_STATUS_KEY, returnStatusValue);
        // 返回的消息
        retMessageMap.put(Constant.RETURN_MESSAGE_KEY, returnMsgContentValue);

        return retMessageMap;
    }

    /**
     * 从请求中 取得 登录用户信息 并 存放至 参数VO 对象中
     *
     * @param paramVO 参数VO (含各种业务参数)
     * @param session 请求，含登录用户信息
     * @return Set 了登录用户信息 的参数VO 对象
     */
    protected <VO_TYPE extends BaseVO> VO_TYPE putLoginUserToVOFromSession(
            VO_TYPE paramVO, HttpSession session) {

        // 设置当前用户信息
        paramVO.setLoginUserInfo(getLoginUserToVOFromSession(session));

        return paramVO;
    }

    /**
     * 从请求中 取得 登录用户信息
     *
     * @param session 请求，含登录用户信息
     * @return Set 了登录用户信息 的参数VO 对象
     */
    protected HTUserVO getLoginUserToVOFromSession(HttpSession session) {

        return (HTUserVO) session.getAttribute("loginUserInfo");
    }

}
