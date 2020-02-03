package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTRoleService;
import com.hd.manager.vo.HTRoleVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 角色Controller
 *
 * @author lihai
 * Created in 2019/7/3 11:45
 **/


@Controller
@RequestMapping("/config/role")
@CrossOrigin(allowCredentials ="true")
public class HTRoleController extends BaseController {

    /**
     * 角色Service对象
     */
    @Resource
    private HTRoleService htRoleService;

    /**
     * 查询角色详情
     */
    @ResponseBody
    @RequestMapping("/query/findRoleInfo")
    public Map<String,Object> findRoleInfo(@RequestBody HTRoleVO htRoleVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {

            //查询角色详情
            HTRoleVO roleInfo = htRoleService.findRoleInfo(htRoleVO);
            resultMap.put(Constant.DATA_VO_KEY,roleInfo);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 添加角色信息
     */
    @ResponseBody
    @RequestMapping("/add/insertRoleInfo")
    public Map<String,Object> insertRoleInfo(@RequestBody HTRoleVO paramVO , HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //添加角色信息
            htRoleService.insertRoleInfo(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 修改角色信息
     */
    @ResponseBody
    @RequestMapping("/modify/updateRoleInfo")
    public Map<String,Object> updateRoleInfo(@RequestBody HTRoleVO paramVO , HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //修改角色信息
            htRoleService.updateRoleInfo(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 删除角色信息
     */
    @ResponseBody
    @RequestMapping("/delete/deleteRoleInfo")
    public Map<String,Object> deleteRoleInfo(@RequestBody HTRoleVO paramVO, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //删除角色信息(逻辑)
            htRoleService.deleteRoleInfo(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 查询角色列表(分页)
     */
    @ResponseBody
    @RequestMapping("/query/findRoleList")
    public Map<String,Object> findRoleList(@RequestBody HTRoleVO htRoleVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //查询角色列表(分页)
            Map<String, Object> roleList = htRoleService.findRoleList(htRoleVO);
            resultMap.put(Constant.PAGEED_DATA_KEY,roleList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 查询所有功能菜单列表
     */
    @ResponseBody
    @RequestMapping("/query/findAllMenuList")
    public Map<String,Object> findAllMenuList(){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Map<String, Object> allMenuList = htRoleService.findAllMenuList();
            resultMap.put(Constant.PAGEED_DATA_KEY,allMenuList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }
}
