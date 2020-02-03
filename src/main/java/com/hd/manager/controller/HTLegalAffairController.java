package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTContractService;
import com.hd.manager.vo.HTContractVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 法务Controller
 *
 * @author lihai
 * Created in 2019/7/3 11:45
 **/


@Controller
@RequestMapping("/legalAffair")
@CrossOrigin(allowCredentials ="true")
public class HTLegalAffairController extends BaseController {



    @Resource
    HTContractService htContractService;



    /**
     * 查询合同列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchContractList")
    public Map<String,Object> searchContractList(@RequestBody HTContractVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htContractService.excSearchContractList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }




    /**
     * 添加法务信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/modify/insertLegalOpinion")
    public Map<String,Object> insertLegalOpinion(@RequestBody HTContractVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htContractService.excInsertLegalOpinion(paramVO);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询合同对应的法务信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/excSearchLegalOpinionInfoById")
    public Map<String,Object> excSearchLegalOpinionInfoById(@RequestBody HTContractVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_VO_KEY,htContractService.excSearchLegalOpinionInfoById(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }
}
