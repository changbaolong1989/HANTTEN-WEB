package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.service.CommonService;
import com.hd.common.service.CommonTenderTaskService;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTTenderTaskHisTaskInstService;
import com.hd.manager.service.HTTenderTaskService;
import com.hd.manager.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 投标任务Controller
 *
 * @author LH
 * Created in 2019/7/13 15:21
 */
@Controller
@RequestMapping("/tenderTask")
@CrossOrigin(allowCredentials ="true")
public class HTTenderTaskController extends BaseController {

    /**
     * 通用投标任务Service对象
     */
    @Resource
    private CommonTenderTaskService commonTenderTaskService;

    /**
     * 投标任务service对象
     */
    @Resource
    private HTTenderTaskService htTenderTaskService;
    /**
     * 通用service
     */
    @Resource
    private CommonService commonService;
    /**
     * 工作内容说明Service对象
     */
    @Resource
    private HTTenderTaskHisTaskInstService htTenderTaskHisTaskInstService;
    /**
     * 添加投标计划
     *
     * @return
     */
    @RequestMapping("/insert/insertTenderTaskInfo")
    @ResponseBody
    public Map<String,Object> insertTenderTaskInfo(@RequestBody HTTenderTaskVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //添加投标计划
            htTenderTaskService.excInsertTenderTaskInfo(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询投标计划列表信息
     *
     * @return 投标计划列表
     */
    @RequestMapping("/query/searchTenderTaskPeriodList")
    @ResponseBody
    public Map<String,Object> searchTenderTaskPeriodList(@RequestBody HTTenderTaskVO paramVO){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //投标计划列表信息
            resultMap.putAll(htTenderTaskService.searchTenderTaskList(paramVO));
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 修改投标计划
     *
     * @return
     */
    @RequestMapping("/modify/updateTenderTask")
    @ResponseBody
    public Map<String,Object> updateTenderTask(@RequestBody HTTenderTaskVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //修改投标计划
            htTenderTaskService.updateTenderTask(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 修改投标状态
     *
     * @return
     */
    @RequestMapping("/modify/updateTenderTaskSts")
    @ResponseBody
    public Map<String,Object> updateTenderTaskSts(@RequestBody HTTenderTaskVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //修改投标计划
            htTenderTaskService.updateTenderTaskSts(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 根据部门ID查询部门下成员
     *
     * @return
     */
    @RequestMapping("/query/searchUsersByDepartmentId")
    @ResponseBody
    public Map<String,Object> searchUsersByDepartmentId(@RequestBody HTUserVO paramVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<HTUserVO> htUserVOList = commonService.excSearchUserListByDepartmentId(paramVO);
            resultMap.put(Constant.DATA_LIST_KEY,htUserVOList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 根据投标任务ID查询阶段
     *
     * @return
     */
    @RequestMapping("/query/searchTaskPeriodByTenderTaskId")
    @ResponseBody
    public Map<String,Object> searchTenderPeriodByTenderTaskId(@RequestBody HTTenderTaskVO paramVO, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //阶段列表
            resultMap.put(Constant.DATA_LIST_KEY,commonTenderTaskService.searchTaskPeriodByTenderTaskId(paramVO));
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 删除投标计划
     *
     * @return
     */
    @RequestMapping("/delete/deleteTenderTask")
    @ResponseBody
    public Map<String,Object> deleteTenderTask(@RequestBody HTTenderTaskVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //删除投标计划
            htTenderTaskService.deleteTenderTask(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 查询投标任务信息
     *
     * @return
     */
    @RequestMapping("/query/searchTenderTaskInfoById")
    @ResponseBody
    public Map<String,Object> searchTenderTaskInfoById(@RequestBody HTTenderTaskVO paramVO, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            HTTenderTaskVO htTenderTaskVO = htTenderTaskService.excSearchTenderTaskInfoById(paramVO);
            resultMap.put(Constant.DATA_VO_KEY,htTenderTaskVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 根据阶段ID查询阶段关联文件
     *
     * @return
     */
    @RequestMapping("/query/searchTenderTaskPeriodMaterialList")
    @ResponseBody
    public Map<String,Object> searchTenderTaskPeriodMaterialList(@RequestBody HTTenderTaskPeriodVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //根据阶段ID查询阶段关联
            List<HTTenderTaskPeriodMaterialVO> htTenderTaskPeriodMaterialVOList = htTenderTaskService.
                    searchTenderTaskPeriodMaterialList(paramVO);
            resultMap.put(Constant.DATA_LIST_KEY,htTenderTaskPeriodMaterialVOList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 查询文件类别列表
     *
     * @return
     */
    @RequestMapping("/query/searchMaterialTypeList")
    @ResponseBody
    public Map<String,Object> searchMaterialTypeList(@RequestBody HTDictFileTypeVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //查询文件类别列表
            List<HTDictFileTypeVO> materialTypeList = htTenderTaskService.
                    searchMaterialTypeList(paramVO);
            resultMap.put(Constant.DATA_LIST_KEY,materialTypeList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 添加阶段文件信息
     *
     * @return
     */
    @RequestMapping("/insert/saveTenderTaskPeriodMaterialInfo")
    @ResponseBody
    public Map<String,Object> saveTenderTaskPeriodMaterialInfo( HTTenderTaskPeriodMaterialVO paramVO,
                                                               HttpSession session,
                                                               @RequestParam(value = "file",required = false) MultipartFile[] fileList){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //添加文件信息
            htTenderTaskService.saveTenderTaskPeriodMaterialInfo(paramVO,fileList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 删除阶段文件信息
     *
     * @return
     */
    @RequestMapping("/delete/deleteTenderTaskPeriodMaterialInfo")
    @ResponseBody
    public Map<String,Object> deleteTenderTaskPeriodMaterialInfo(@RequestBody HTTenderTaskPeriodMaterialVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //删除文件信息
            htTenderTaskService.deleteTenderTaskPeriodMaterialInfo(paramVO);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 查询投标任务相关所有文件类型以级相关文件类型的文件
     *
     * @return
     */
    @RequestMapping("/query/searchAllMaterialTypeAndMaterialList")
    @ResponseBody
    public Map<String,Object> searchAllMaterialTypeAndMaterialList(@RequestBody HTTenderTaskVO paramVO,
                                                                      HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //投标任务相关所有文件类型以级相关文件类型的文件列表信息
            List<ElementTreeVO> resultList = htTenderTaskService.excSearchAllMaterialTypeAndMaterialList(paramVO);
            resultMap.put(Constant.DATA_LIST_KEY,resultList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     *提交审核(批量修改阶段文件信息提审状态)
     *
     * @return
     */
    @RequestMapping("/modify/updateTenderTaskPeriodMaterialInfoIsReviewType")
    @ResponseBody
    public Map<String,Object> updateTenderTaskPeriodMaterialInfoIsReviewType( @RequestBody HTTenderTaskPeriodVO paramVO,
                                                                HttpSession session ){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //批量修改阶段文件信息提审状态
            htTenderTaskService.excUpdateTenderTaskPeriodMaterialInfoIsReviewType(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 查询投标任务以提审文件
     *
     * @return
     */
    @RequestMapping("/query/searchIsReviewTenderTaskPeriodMaterialList")
    @ResponseBody
    public Map<String,Object> searchIsReviewTenderTaskPeriodMaterialList(@RequestBody HTTenderTaskPeriodMaterialVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //根据阶段ID查询阶段关联
            List<HTTenderTaskPeriodMaterialVO> htTenderTaskPeriodMaterialVOList = htTenderTaskService.
                    excSearchIsReviewTenderTaskPeriodMaterialList(paramVO);
            resultMap.put(Constant.DATA_LIST_KEY,htTenderTaskPeriodMaterialVOList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 投标任务撤回
     *
     * @return
     */
    @RequestMapping("/modify/recallTask")
    @ResponseBody
    public Map<String,Object> recallTask(@RequestBody HTTenderTaskVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //执行撤回
            htTenderTaskService.excRecallTask(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询问题类型列表信息
     *
     * @return
     */
    @RequestMapping("/query/searchQuestionList")
    @ResponseBody
    public Map<String,Object> searchQuestionList(@RequestBody HTTenderTaskVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            HTDictQuestionVO htDictQuestionVO = new HTDictQuestionVO();
            htDictQuestionVO.setDepartmentId(paramVO.getDepartmentId());
            List<HTDictQuestionVO> questionVOList = commonService.excSearchQuestionTypeByDeptId(htDictQuestionVO);
            resultMap.put(Constant.DATA_LIST_KEY,questionVOList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }



    /**
     * 审核
     *
     * @return
     */
    @RequestMapping("/insert/auditingHTTenderTask")
    @ResponseBody
    public Map<String,Object> auditingHTTenderTask(@RequestBody HTTenderTaskVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htTenderTaskService.excAuditingHTTenderTask(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 功能展示(查询当前用户在页面身份所展示功能)
     *
     * @return
     */
    @RequestMapping("/query/showBtnType")
    @ResponseBody
    public Map<String,Object> showBtnType(@RequestBody HTTenderTaskVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            String typeFlag =htTenderTaskService.excQueryShowBtnType(paramVO);
            resultMap.put(Constant.BUTTON_TYPE_FLAG,typeFlag);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 查询审核意见
     */
    @ResponseBody
    @RequestMapping("/query/searchHTTenderTaskHisTaskInst")
    public Map<String,Object> searchHTTenderTaskHisTaskInst(@RequestBody HTTenderTaskHisTaskInstVO paramVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //查询审核意见
            List<HTTenderTaskHisTaskInstVO> resultList = htTenderTaskHisTaskInstService.excSearchHTTenderTaskHisTaskInst(paramVO);
            resultMap.put(Constant.DATA_LIST_KEY,resultList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 查询投标任务投标阶段文件信息
     *
     * @return
     */
    @RequestMapping("/query/searchTenderStageMaterialByTenderTaskId")
    @ResponseBody
    public Map<String,Object> searchTenderStageMaterialByTenderTaskId(@RequestBody HTTenderTaskPeriodMaterialVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //删除投标计划
            HTTenderTaskPeriodMaterialVO stageMaterial = htTenderTaskService.excSearchTenderStageMaterialByTenderTaskId(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
            resultMap.put(Constant.DATA_VO_KEY,stageMaterial);
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

}
