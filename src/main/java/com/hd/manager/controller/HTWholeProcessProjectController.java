package com.hd.manager.controller;


import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTContractService;
import com.hd.manager.service.HTWholeProcessProjectService;
import com.hd.manager.vo.HTContractVO;
import com.hd.manager.vo.HTProjectVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 全过程(项目列表)Controller
 *
 * @author LH
 * Created in 2019/9/3 14:29
 */
@Controller
@RequestMapping("/wholeProcess/project")
@CrossOrigin(allowCredentials = "true")
public class HTWholeProcessProjectController extends BaseController {


    /**
     * 项目阶段service
     */
    @Resource
    HTWholeProcessProjectService htWholeProcessProjectService;

    /**
     * 查询项目列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchContractList")
    public Map<String,Object> searchContractList(@RequestBody HTProjectVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htWholeProcessProjectService.excSearchContractList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 查询项目阶段列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectPeriodList")
    public Map<String,Object> searchProjectPeriodList(@RequestBody HTProjectVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_LIST_KEY,htWholeProcessProjectService.excSearchProjectPeriodList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }
}
