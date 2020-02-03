package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTPowerConfigService;
import com.hd.manager.vo.HTJobVO;
import com.hd.manager.vo.HTPowerVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职位权限Controller
 *
 * @author lihai
 * Created in 2019/10/9 10:59
 **/


@Controller
@RequestMapping("/config/powerConfig")
@CrossOrigin(allowCredentials ="true")
public class HTPowerConfigController extends BaseController {

    /**
     * 职位权限Service对象
     */
    @Resource
    private HTPowerConfigService htPowerConfigService;


    /**
     * 查询职位列表
     */
    @ResponseBody
    @RequestMapping("/query/searchJobList")
    public Map<String,Object> searchJobList(@RequestBody HTJobVO paramVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //查询职位列表
            resultMap.putAll(htPowerConfigService.excSearchJobList(paramVO));
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询职位权限信息
     */
    @ResponseBody
    @RequestMapping("/query/searchJobInfo")
    public Map<String,Object> searchJobInfo(@RequestBody HTJobVO paramVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //查询职位列表
            HTJobVO htJobVO = htPowerConfigService.excSearchJobInfo(paramVO);
            resultMap.put(Constant.DATA_VO_KEY,htJobVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询所有权限列表
     */
    @ResponseBody
    @RequestMapping("/query/searchAllPowerList")
    public Map<String,Object> searchAllPowerList(){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //查询职位列表
            List<HTPowerVO> htPowerVOS = htPowerConfigService.excSearchAllPowerList();
            resultMap.put(Constant.DATA_LIST_KEY,htPowerVOS);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 批量修改职位权限
     */
    @ResponseBody
    @RequestMapping("/modify/updatePowers")
    public Map<String,Object> updatePowers(@RequestBody HTJobVO paramVO, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //批量修改职位权限
            htPowerConfigService.excUpdatePowers(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询所有部门
     */
    @ResponseBody
    @RequestMapping("/query/searchAllDepartments")
    public Map<String,Object> searchAllDepartments(@RequestBody HTJobVO paramVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //批量修改职位权限
            resultMap.put(Constant.DATA_LIST_KEY,htPowerConfigService.excSearchAllDepartments());
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }
}
