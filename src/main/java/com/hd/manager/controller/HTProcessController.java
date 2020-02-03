package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.dao.bean.HTJobProcessNodeRelationBean;
import com.hd.manager.service.HTProcessService;
import com.hd.manager.vo.HTJobProcessNodeRelationVo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 流程相关Controller
 */
@Controller
@CrossOrigin(allowCredentials = "true")
public class HTProcessController extends BaseController {

    @Resource
    private HTProcessService processService;

    /**
     * 查询流程节点配置列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/process/config/searchProcessConfigInfo")
    @ResponseBody
    public Map<String, Object> searchProcessConfigInfo(@RequestBody HTJobProcessNodeRelationVo paramVO) {
        Map<String, Object> resultMap;
        try {
            Map<String, Object> retMap = processService.getProcessNodeRelation(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 修改流程节点配置列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/process/config/updateProcessNodeRelation")
    @ResponseBody
    public Map<String, Object> updateProcessNodeRelation(@RequestBody HTJobProcessNodeRelationVo paramVO) {
        Map<String, Object> resultMap;
        try {
            processService.updateProcessNodeRelation(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
        } catch (DuplicateKeyException e) {
            resultMap = setReturnMessageMap(FAILURE, "违反唯一约束");
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 修改流程节点配置列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/process/config/delProcessNodeRelation")
    @ResponseBody
    public Map<String, Object> delProcessNodeRelation(@RequestBody HTJobProcessNodeRelationVo paramVO) {
        Map<String, Object> resultMap;
        try {
            processService.delProcessNodeRelation(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询下拉列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/process/config/searchSelectByExample")
    @ResponseBody
    public Map<String, Object> searchSelectByExample(@RequestBody HTJobProcessNodeRelationVo paramVO) {
        Map<String, Object> resultMap;
        try {
            List<HTJobProcessNodeRelationBean> list = processService.searchSelectByExample(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_LIST_KEY, list);
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询流程节点数据
     *
     * @return 请求参数
     */
    @RequestMapping("/process/config/searchProcessConfigSelect")
    @ResponseBody
    public Map<String, Object> searchProcessConfigInfo() {
        Map<String, Object> resultMap;
        try {
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_LIST_KEY, processService.searchProcessSelect());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询部门数据
     *
     * @return 请求参数
     */
    @RequestMapping("/process/config/searchDepartmentSelect")
    @ResponseBody
    public Map<String, Object> searchDepartmentSelect() {
        Map<String, Object> resultMap;
        try {
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_LIST_KEY, processService.getDepartmentSelect());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询职位数据
     *
     * @return 请求参数
     */
    @RequestMapping("/process/config/searchJobSelect")
    @ResponseBody
    public Map<String, Object> searchJobSelect(@RequestBody HTJobProcessNodeRelationVo paramVO) {
        Map<String, Object> resultMap;
        try {
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_LIST_KEY, processService.getJobSelect(paramVO));
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询节点数据
     *
     * @return 请求参数
     */
    @RequestMapping("/process/config/searchNodeSelect")
    @ResponseBody
    public Map<String, Object> searchNodeSelect(@RequestBody HTJobProcessNodeRelationVo paramVO) {
        Map<String, Object> resultMap;
        try {
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_LIST_KEY, processService.getNodeSelect(paramVO));
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 保存节点配置
     *
     * @return 请求参数
     */
    @RequestMapping("/process/config/saveProcessNodeRelation")
    @ResponseBody
    public Map<String, Object> saveProcessNodeRelation(@RequestBody HTJobProcessNodeRelationVo paramVO) {
        Map<String, Object> resultMap;
        try {
            processService.saveProcessNodeRelation(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        } catch (DuplicateKeyException e) {
            resultMap = setReturnMessageMap(FAILURE, "违反唯一约束");
        }
        return resultMap;
    }


}
