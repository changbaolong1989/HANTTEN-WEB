package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTDepartmentService;
import com.hd.manager.vo.HTDepartmentVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门Controller
 *
 * @author lihai
 * Created in 2019/7/4 17:40
 **/
@Controller
@RequestMapping("/config/department")
public class HTDepartmentController extends BaseController {

    /**
     * 部门Service对象
     */
    @Resource
    private HTDepartmentService htDepartmentService;

    /**
     * 查询部门列表
     */
    @ResponseBody
    @RequestMapping("/query/findDepartmentList")
    public Map<String,Object> findRoleList(@RequestBody HTDepartmentVO paramVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //查询部门列表
            Map<String,Object> departmentVOList = htDepartmentService.searchDepartMentList(paramVO);
            resultMap.put(Constant.PAGEED_DATA_KEY,departmentVOList);
            resultMap.put(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
        } catch (LogicException lex) {
            resultMap.put(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap.put(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     *修改部门信息(仅名称)
     */
    @ResponseBody
    @RequestMapping("/modify/updateDepartmentInfo")
    public Map<String,Object> updateDepartmentInfo(@RequestBody HTDepartmentVO paramVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //修改部门信息
            htDepartmentService.updateDepartmentInfo(paramVO);
            resultMap.put(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap.put(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap.put(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }
}
