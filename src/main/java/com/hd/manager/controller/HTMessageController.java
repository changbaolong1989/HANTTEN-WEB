package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTMessageService;
import com.hd.manager.vo.HTMessageVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息Controller
 *
 * @author lihai
 * Created in 2019/7/4 17:40
 **/
@Controller
@RequestMapping("/message")
public class HTMessageController extends BaseController {

    /**
     * 信息Service对象
     */
    @Resource
    private HTMessageService htMessageService;

    /**
     * 查询信息列表
     */
    @ResponseBody
    @RequestMapping("/query/searchMessageList")
    public Map<String, Object> findMessageList(@RequestBody HTMessageVO paramVO, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //查询信息列表
            Map<String, Object> messageVOList = htMessageService.searchMessageList(paramVO);
            resultMap.put(Constant.PAGEED_DATA_KEY, messageVOList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 修改信息已读状态
     */
    @ResponseBody
    @RequestMapping("/modify/updateMessageIsReadType")
    public Map<String, Object> updateMessageIsReadType(@RequestBody HTMessageVO paramVO, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //修改信息已读状态
            htMessageService.updateMessageIsReadType(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 删除信息（同样适用于批量删除）
     */
    @ResponseBody
    @RequestMapping("/delete/deleteMessageInfo")
    public Map<String, Object> deleteMessageInfo(@RequestBody HTMessageVO paramVO, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //删除信息
            htMessageService.deleteMessageInfo(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询消息集合中的最大创建时间的数据信息
     */
    @ResponseBody
    @RequestMapping("/query/searchMessageDetailInfo")
    public Map<String, Object> searchMessageDetailInfo(@RequestBody HTMessageVO paramVO, HttpSession session) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询消息集合中的最大创建时间的数据信息
            HTMessageVO htMessageVO = htMessageService.excSearchMessageDetailInfo(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回消息集合中的最大创建时间的数据信息
            resultMap.put(Constant.DATA_VO_KEY, htMessageVO);

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }



}
