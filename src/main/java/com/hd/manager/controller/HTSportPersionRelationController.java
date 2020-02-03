package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTSportPersionRelationService;
import com.hd.manager.vo.TbTaskSpotPersonRelationVO;
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
 * 任务和现场人员调整接口类
 *
 * @author ZHC
 * Created in 2019/8/7 14:30
 */
@Controller
@RequestMapping("/personnelAdjustment")
@CrossOrigin(allowCredentials = "true")
public class HTSportPersionRelationController extends BaseController {

    @Resource
    private HTSportPersionRelationService htSportPersionRelationService;

    @RequestMapping("/queryAll")
    @ResponseBody
    public Map<String, Object> queryAll(@RequestBody TbTaskSpotPersonRelationVO paramVO){

        Map<String, Object> resultMap;
        try {
            // 查询总条数
            Map<String, Object> retMap = htSportPersionRelationService.queryAll(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回列表数据
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map<String, Object> add(@RequestBody TbTaskSpotPersonRelationVO paramVO, HttpSession session){
        Map<String, Object> resultMap;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            htSportPersionRelationService.add(paramVO);

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

     @RequestMapping("/modifyLeaveTime")
     @ResponseBody
     public Map<String,Object> updateLeaveTime(@RequestBody TbTaskSpotPersonRelationVO paramVO, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htSportPersionRelationService.updateLeaveTime(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
     }
}
