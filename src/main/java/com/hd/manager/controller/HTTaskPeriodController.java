package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTDepartmentService;
import com.hd.manager.service.HTTaskPeriodService;
import com.hd.manager.vo.HTDepartmentVO;
import com.hd.manager.vo.HTTaskPeriodVO;
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
 * 工作内容说明Controller
 *
 * @author lihai
 * Created in 2019/7/4 17:40
 **/
@Controller
@RequestMapping("/config/taskPeriod")
@CrossOrigin(allowCredentials ="true")
public class HTTaskPeriodController extends BaseController {

    /**
     * 工作内容说明Service对象
     */
    @Resource
    private HTTaskPeriodService htTaskPeriodService;

    /**
     * 查询工作内容列表
     */
    @ResponseBody
    @RequestMapping("/query/searchTaskPeriodList")
    public Map<String,Object> searchTaskPeriodList(@RequestBody HTTaskPeriodVO paramVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //查询工作内容列表
            Map<String,Object> taskPeriodVOList = htTaskPeriodService.excSearchTaskPeriodList(paramVO);
            resultMap.put(Constant.PAGEED_DATA_KEY,taskPeriodVOList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     *修改工作内容说明信息
     */
    @ResponseBody
    @RequestMapping("/modify/updateTaskPeriodInfo")
    public Map<String,Object> updateDepartmentInfo(@RequestBody HTTaskPeriodVO paramVO, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //修改工作内容说明信息
            htTaskPeriodService.excUpdateTaskPeriodInfo(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     *查询所有部门类型
     */
    @ResponseBody
    @RequestMapping("/query/searchAllDictDepartmentList")
    public Map<String,Object> searchAllDictDepartmentList(){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //查询所有部门类型
            Map<String, Object> allDictDepartmentList = htTaskPeriodService.excSearchAllDictDepartmentList();
            resultMap.put(Constant.PAGEED_DATA_KEY,allDictDepartmentList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

}
