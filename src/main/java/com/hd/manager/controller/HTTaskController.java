package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.service.CommonService;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.manager.service.HTTaskService;
import com.hd.manager.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 任务Controller
 *
 * @author jwl
 * Created in 2019/7/22 15:21
 */
@Controller
@RequestMapping("/task")
@CrossOrigin(allowCredentials = "true")
public class HTTaskController extends BaseController {

    /**
     * 任务service 业务层资源
     */
    @Resource
    private HTTaskService htTaskService;

    /**
     * 通用service
     */
    @Resource
    private CommonService commonService;

    /**
     * 功能展示(查询当前用户在页面身份所展示功能)
     *
     * @return
     */
    @RequestMapping("/query/showBtnType")
    @ResponseBody
    public Map<String,Object> showBtnType(@RequestBody HTTaskPeriodVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询页面类型
            String typeFlag = htTaskService.excQueryShowBtnType(paramVO);

            // 查询任务vo
            HTTaskVO htTaskVO = htTaskService.excSearchTaskDetailInfoByTaskId(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            resultMap.put(Constant.BUTTON_TYPE_FLAG,typeFlag);

            // 返回任务vo
            resultMap.put(Constant.DATA_VO_KEY,htTaskVO);

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 添加落标通知书和任务关联表信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertFailTenderNoticeRelationInfo")
    @ResponseBody
    public Map<String, Object> insertFailTenderNoticeRelationInfo(HttpSession session, @RequestBody HTFailTenderNoticeRelationVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加落标通知书和任务关联表信息
            htTaskService.excInsertFailTenderNoticeRelationInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询落标通知书和任务关联表详情
     *
     * @return 落标通知书和任务关联表详情
     */
    @RequestMapping("/query/searchFailTenderNoticeRelationInfoDetail")
    @ResponseBody
    public Map<String, Object> searchFailTenderNoticeRelationInfoDetail(@RequestBody HTFailTenderNoticeRelationVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询落标通知书和任务关联表详情
            HTFailTenderNoticeRelationVO htFailTenderNoticeRelationVO = htTaskService.excSearchFailTenderNoticeRelationInfoDetail(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 查询落标通知书和任务关联表详情
            resultMap.put(Constant.DATA_VO_KEY, htFailTenderNoticeRelationVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加招标文件确认意见表和任务的关联表信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertFileConfirmOpinionRelationInfo")
    @ResponseBody
    public Map<String, Object> insertFileConfirmOpinionRelationInfo(HttpSession session, @RequestBody HTFileConfirmOpinionRelationVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加招标文件确认意见表和任务的关联表信息
            htTaskService.excInsertFileConfirmOpinionRelationInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询招标文件确认意见表和任务的关联表详情
     *
     * @return 招标文件确认意见表和任务的关联表详情
     */
    @RequestMapping("/query/searchFileConfirmOpinionRelationDetail")
    @ResponseBody
    public Map<String, Object> searchFileConfirmOpinionRelationDetail(@RequestBody HTFileConfirmOpinionRelationVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询招标文件确认意见表和任务的关联表详情
            HTFileConfirmOpinionRelationVO htFileConfirmOpinionRelationVO = htTaskService.excSearchFileConfirmOpinionRelationDetail(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 查询招标文件确认意见表和任务的关联表详情
            resultMap.put(Constant.DATA_VO_KEY, htFileConfirmOpinionRelationVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加专家论证意见表信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertExpertOpinionRelationInfo")
    @ResponseBody
    public Map<String, Object> insertExpertOpinionRelationInfo(HttpSession session, @RequestBody HTExpertOpinionRelationVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加专家论证意见表信息
            htTaskService.excInsertExpertOpinionRelationInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询专家论证意见表详情
     *
     * @return 专家论证意见表详情
     */
    @RequestMapping("/query/searchExpertOpinionRelationDetail")
    @ResponseBody
    public Map<String, Object> searchExpertOpinionRelationDetail(@RequestBody HTExpertOpinionRelationVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询专家论证意见表详情
            HTExpertOpinionRelationVO htExpertOpinionRelationVO = htTaskService.excSearchExpertOpinionRelationDetail(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回专家论证意见表详情
            resultMap.put(Constant.DATA_VO_KEY, htExpertOpinionRelationVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加开标、评标信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertOpenBidInfo")
    @ResponseBody
    public Map<String, Object> insertOpenBidInfo(HttpSession session, @RequestBody HTOpenBidVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加开标、评标信息
            htTaskService.excInsertOpenBidInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询开标、评标详情
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchOpenBidInfoDetail")
    @ResponseBody
    public Map<String, Object> searchOpenBidInfoDetail(@RequestBody HTOpenBidVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询开标、评标信息详情
            HTOpenBidVO htOpenBidVO = htTaskService.excSearchOpenBidInfoDetail(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 查询开标、评标信息详情
            resultMap.put(Constant.DATA_VO_KEY, htOpenBidVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 任务阶段是否完成
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateIsFinish")
    @ResponseBody
    public Map<String, Object> updateIsFinish(@RequestBody HTTaskPeriodVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 任务阶段是否完成
            htTaskService.updateIsFinish(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }


    /**
     * 任务阶段是否忽略
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateIsIgnore")
    @ResponseBody
    public Map<String, Object> updateIsIgnore(@RequestBody HTTaskPeriodVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 任务阶段是否忽略
            htTaskService.updateIsIgnore(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 项目监理归档信息列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchArchiveListInfo")
    @ResponseBody
    public Map<String, Object> searchArchiveListInfo(@RequestBody HTTaskArchiveVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询查询项目列表数据
            Map<String, Object> retMap = htTaskService.excSearchArchiveListInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 项目监理归档信息列表
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 通过任务id查询审核阶段文件
     *
     * @return
     */
    @RequestMapping("/query/searchMaterialByTaskPeriodId")
    @ResponseBody
    public Map<String,Object> searchMaterialByTaskPeriodId(@RequestBody HTTaskPeriodMaterialVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 通过任务id查询审核阶段文件
            HTTaskPeriodMaterialVO stageMaterial = htTaskService.excSearchMaterialByTaskPeriodId(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            resultMap.put(Constant.DATA_VO_KEY,stageMaterial);

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 发起归档(归档阶段 项目监理部)
     *
     * @return
     */
    @RequestMapping("/modify/initiateArchive")
    @ResponseBody
    public Map<String,Object> initiateArchive(@RequestBody HTTaskVO paramVO, HttpSession session ){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 发起归档
            htTaskService.excInitiateArchive(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 确认归档(归档阶段 项目监理部)
     *
     * @return
     */
    @RequestMapping("/modify/confirmArchiveXM")
    @ResponseBody
    public Map<String,Object> confirmArchiveXM(@RequestBody HTTaskVO paramVO, HttpSession session ){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 确认归档
            htTaskService.excConfirmArchiveXM(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 根据阶段ID查询阶段关联文件(成果文件阶段)
     *
     * @return
     */
    @RequestMapping("/query/searchOutcomeDocumentMaterial")
    @ResponseBody
    public Map<String,Object> searchOutcomeDocumentMaterial(@RequestBody HTTaskVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            //根据任务id查询任务文件信息
            HTTaskVO htTaskVO = htTaskService.searchTaskMaterialInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            //回显任务信息
            resultMap.put(Constant.DATA_VO_KEY,htTaskVO);

            // 当多文件回显的时候传入任务阶段id,不是多文件回显的时候不需要查询
            if(!StringToolUtils.isNull(paramVO.getRelationId())){
                HTTaskPeriodMaterialVO htTaskPeriodMaterialVO = new HTTaskPeriodMaterialVO();
                htTaskPeriodMaterialVO = putLoginUserToVOFromSession(htTaskPeriodMaterialVO, session);// 获取session 登陆用户信息
                htTaskPeriodMaterialVO.setTaskId(paramVO.getTaskId());// 设置任务id
                htTaskPeriodMaterialVO.setRelationId(paramVO.getRelationId());// 设置任务阶段id
                // 查询相关资料文件列表(详情)
                List<HTTaskPeriodMaterialVO> htTaskPeriodMaterialVOList = htTaskService.excSearchRelationMaterial(htTaskPeriodMaterialVO);
                resultMap.put("fileList",htTaskPeriodMaterialVOList);// 回显相关资料
            }

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 提交重新编辑申请
     *
     * @return
     */
    @RequestMapping("/modify/submitReedit")
    @ResponseBody
    public Map<String,Object> submitReedit(@RequestBody HTTaskReeditVO paramVO, HttpSession session ){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 提交重新编辑申请
            htTaskService.excSubmitReedit(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询重新编辑审核列表
     */
    @ResponseBody
    @RequestMapping("/query/searchTaskReeditHisTaskInst")
    public Map<String,Object> searchTaskReeditHisTaskInst(@RequestBody HTTaskPeriodVO paramVO){

        Map<String,Object> resultMap = null;

        try {

            // 查询审核意见
            Map<String, Object> resultList = htTaskService.excSearchTaskReeditHisTaskInst(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            resultMap.put("isSubmitReedit",resultList.get("isSubmitReedit"));

            resultMap.put("dataList",resultList.get("dataList"));

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 签发(成果文件阶段 重新编辑申请)
     *
     * @return
     */
    @RequestMapping("/add/reeditApplicationIssue")
    @ResponseBody
    public Map<String,Object> reeditApplicationIssue(@RequestBody HTTaskReeditVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 签发
            htTaskService.excReeditApplicationIssue(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 审核任务(成果文件阶段 重新编辑申请)
     *
     * @return
     */
    @RequestMapping("/add/auditingOutcomeDocumentPeriodReeditApplication")
    @ResponseBody
    public Map<String,Object> auditingOutcomeDocumentPeriodReeditApplication(@RequestBody HTTaskReeditVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 审核任务
            htTaskService.excAuditingOutcomeDocumentPeriodReeditApplication(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 审核任务(成果文件阶段 归档文件)
     *
     * @return
     */
    @RequestMapping("/add/auditingTaskOutcomeDocumentPeriod")
    @ResponseBody
    public Map<String,Object> auditingTaskOutcomeDocumentPeriod(@RequestBody HTTaskVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 审核任务
            htTaskService.excAuditingTaskOutcomeDocumentPeriod(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 撤回任务(成果文件阶段)
     *
     * @return
     */
    @RequestMapping("/modify/recallTaskOutcomeDocumentPeriod")
    @ResponseBody
    public Map<String,Object> recallTaskOutcomeDocumentPeriod(@RequestBody HTTaskVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 执行撤回
            htTaskService.excRecallTaskOutcomeDocumentPeriod(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_ERROR_TASK_0003));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 提交审核(成果文件阶段)
     *
     * @return
     */
    @RequestMapping("/modify/submitAuditOutcomeDocumentPeriod")
    @ResponseBody
    public Map<String,Object> submitAuditOutcomeDocumentPeriod(HTTaskVO paramVO, HttpSession session, MultipartFile signPathFile,MultipartFile[] fileList){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 提交审核(成果文件阶段)
            htTaskService.excSubmitAuditOutcomeDocumentPeriod(paramVO,signPathFile,fileList);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 保存成果文件阶段信息
     *
     * @return
     */
    @RequestMapping("/modify/saveDraftAuditOutcomeDocumentPeriod")
    @ResponseBody
    public Map<String,Object> saveDraftAuditOutcomeDocumentPeriod(HTTaskVO paramVO, HttpSession session, MultipartFile signPathFile,MultipartFile[] fileList){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 提交审核(成果文件阶段)
            htTaskService.excSaveDraftAuditOutcomeDocumentPeriod(paramVO,signPathFile,fileList);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 获取成果文件信息 测试用
     *
     * @return
     */
    @RequestMapping("/query/getPreparationOfOutcomeDocumentsDetailInfo")
    @ResponseBody
    public Map<String,Object> getPreparationOfOutcomeDocumentsDetailInfo(@RequestBody HTTaskVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            HTTaskVO retVO = new HTTaskVO();

            retVO.setTaskType("1");

            retVO.setFormworkType("15");

            retVO.setFormworkContent("{\"input1\":\"1\",\"input2\":\"3\",\"input3\":\"4\",\"input4\":\"5\",\"input5\":\"6\",\"input6\":\"2019年08月01日\",\"input7\":\"8\",\"input8\":\"9\",\"input9\":\"10\",\"input10\":\"11\",\"input11\":\"12\",\"input12\":\"13\",\"input13\":\"14\",\"input14\":\"0\",\"input15\":\"\",\"input16\":\"1\",\"input17\":\"18\",\"input18\":\"19\",\"input100\":\"2019年08月23日\",\"input105\":\"2\"}");

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            resultMap.put(Constant.DATA_VO_KEY, retVO);

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询当前任务下的所有的相关资料文件列表
     *
     * @return
     */
    @RequestMapping("/query/searchIsReviewTaskPeriodMaterialList")
    @ResponseBody
    public Map<String,Object> searchIsReviewTaskPeriodMaterialList(@RequestBody HTTaskPeriodMaterialVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询当前任务下的所有的相关资料文件列表
            List<HTTaskPeriodMaterialVO> htTaskPeriodMaterialVOList = htTaskService.excSearchIsReviewTaskPeriodMaterialList(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 当前任务下的所有的相关资料文件列表
            resultMap.put(Constant.DATA_LIST_KEY,htTaskPeriodMaterialVOList);

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 根据阶段ID查询归档文件列表
     *
     * @return
     */
    @RequestMapping("/query/searchTaskSaveMaterialList")
    @ResponseBody
    public Map<String,Object> searchTaskSaveMaterialList(@RequestBody HTTaskSaveMaterialVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 根据阶段ID查询归档文件列表
            List<HTTaskSaveMaterialVO> htTaskSaveMaterialVOList = htTaskService.searchTaskSaveMaterialList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            resultMap.put(Constant.DATA_LIST_KEY,htTaskSaveMaterialVOList);

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 删除归档文件信息
     *
     * @return
     */
    @RequestMapping("/delete/deleteTaskSaveMaterialInfo")
    @ResponseBody
    public Map<String,Object> deleteTaskSaveMaterialInfo(@RequestBody HTTaskSaveMaterialVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 删除文件信息
            htTaskService.excDeleteTaskSaveMaterialInfo(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_ERROR_COMMON_0008));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 确认归档
     *
     * @return
     */
    @RequestMapping("/modify/confirmArchive")
    @ResponseBody
    public Map<String,Object> confirmArchive(@RequestBody HTTaskVO paramVO,HttpSession session){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 确认归档
            htTaskService.excConfirmArchive(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 提交归档
     *
     * @return
     */
    @RequestMapping("/modify/submitArchive")
    @ResponseBody
    public Map<String,Object> submitArchive(
            HTTaskVO paramVO,
            HttpSession session,
            @RequestParam("resourceFiles") MultipartFile[] resourceFiles){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 提交归档
            htTaskService.excSubmitArchive(paramVO,resourceFiles);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询任务阶段相关所有文件类型以级相关文件类型的文件
     *
     * @return
     */
    @RequestMapping("/query/searchAllMaterialTypeAndMaterialList")
    @ResponseBody
    public Map<String,Object> searchAllMaterialTypeAndMaterialList(@RequestBody HTTaskPeriodVO paramVO){

        Map<String, Object> resultMap = null;

        try {

            // 查询任务阶段相关所有文件类型以级相关文件类型的文件
            List<ElementTreeVO> resultList = htTaskService.excSearchAllMaterialTypeAndMaterialList(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回任务阶段相关所有文件类型以级相关文件类型的文件
            resultMap.put(Constant.DATA_LIST_KEY,resultList);

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 通过任务id查询归档任务详情
     *
     * @return
     */
    @RequestMapping("/query/searchArchiveTaskDetailInfoByTaskId")
    @ResponseBody
    public Map<String,Object> searchArchiveTaskDetailInfoByTaskId(@RequestBody HTTaskArchiveVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询归档任务vo
            HTTaskArchiveVO htTaskArchiveVO = htTaskService.excSearchArchiveTaskDetailInfoByTaskId(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回归档任务vo
            resultMap.put(Constant.DATA_VO_KEY,htTaskArchiveVO);

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 通过任务阶段id查询任务详情
     *
     * @return
     */
    @RequestMapping("/query/searchTaskDetailInfoByRelationId")
    @ResponseBody
    public Map<String,Object> searchTaskDetailInfoByRelationId(@RequestBody HTTaskVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询任务vo
            HTTaskVO htTaskVO = htTaskService.excSearchTaskDetailInfoByRelationId(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回任务vo
            resultMap.put(Constant.DATA_VO_KEY,htTaskVO);

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 通过任务阶段id查询任务阶段详情
     *
     * @return
     */
    @RequestMapping("/query/searchTaskPeriodDetailInfoByRelationId")
    @ResponseBody
    public Map<String,Object> searchTaskPeriodDetailInfoByRelationId(@RequestBody HTTaskPeriodVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询任务阶段vo
            HTTaskPeriodVO htTaskPeriodVO = htTaskService.excSearchTaskPeriodDetailInfoByRelationId(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回任务阶段vo
            resultMap.put(Constant.DATA_VO_KEY,htTaskPeriodVO);

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
    public Map<String,Object> searchQuestionList(@RequestBody HTTaskVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            HTDictQuestionVO htDictQuestionVO = new HTDictQuestionVO();

            // 设置部门id
            htDictQuestionVO.setDepartmentId(StringToolUtils.convertNullObjectToString(paramVO.getDepartmentId()));

            // 查询问题列表
            List<HTDictQuestionVO> questionVOList = commonService.excSearchQuestionTypeByDeptId(htDictQuestionVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回问题列表
            resultMap.put(Constant.DATA_LIST_KEY,questionVOList);

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询审核意见列表
     */
    @ResponseBody
    @RequestMapping("/query/searchHTTaskHisTaskInst")
    public Map<String,Object> searchHTTaskHisTaskInst(@RequestBody HTTaskPeriodVO paramVO){

        Map<String,Object> resultMap = null;

        try {

            // 查询审核意见
            List<HTTaskPeriodVO> resultList = htTaskService.excSearchHTTaskHisTaskInst(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            resultMap.put(Constant.DATA_LIST_KEY,resultList);

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 审核任务(A3阶段)
     *
     * @return
     */
    @RequestMapping("/add/auditingTaskA3Period")
    @ResponseBody
    public Map<String,Object> auditingTaskA3Period(@RequestBody HTTaskPeriodVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 审核任务
            htTaskService.auditingTaskA3Period(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 撤回任务(A3阶段)
     *
     * @return
     */
    @RequestMapping("/modify/recallTaskA3Period")
    @ResponseBody
    public Map<String,Object> recallTaskA3Period(@RequestBody HTTaskPeriodVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 执行撤回
            htTaskService.excRecallTaskA3Period(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_ERROR_TASK_0003));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 提交审核(A3阶段)
     *
     * @return
     */
    @RequestMapping("/modify/submitAuditA3Period")
    @ResponseBody
    public Map<String,Object> submitAuditA3Period(@RequestBody HTTaskPeriodVO paramVO, HttpSession session ){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 提交审核
            htTaskService.excSubmitAuditA3Period(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

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
    @RequestMapping("/delete/deleteTaskPeriodMaterialInfo")
    @ResponseBody
    public Map<String,Object> deleteTaskPeriodMaterialInfo(@RequestBody HTTaskPeriodMaterialVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 删除文件信息
            htTaskService.excDeleteTaskPeriodMaterialInfo(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_ERROR_COMMON_0008));

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 删除成果文件信息
     *
     * @return
     */
    @RequestMapping("/delete/excDeleteResultFileMaterialInfo")
    @ResponseBody
    public Map<String,Object> excDeleteResultFileMaterialInfo(@RequestBody HTTaskPeriodMaterialVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 删除文件信息
            htTaskService.excDeleteResultFileMaterialInfo(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_ERROR_COMMON_0008));

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
    @RequestMapping("/query/searchTaskPeriodMaterialList")
    @ResponseBody
    public Map<String,Object> searchTenderTaskPeriodMaterialList(@RequestBody HTTaskPeriodMaterialVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            //根据阶段ID查询阶段关联
            List<HTTaskPeriodMaterialVO> htTaskPeriodMaterialVOList = htTaskService.searchTaskPeriodMaterialList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            resultMap.put(Constant.DATA_LIST_KEY,htTaskPeriodMaterialVOList);

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 根据任务阶段id查询项目编号、合同编号和成果文件编号
     *
     * @return 项目编号、合同编号和成果文件编号
     */
    @RequestMapping("/query/searchProjectNumContractNumFileNumberByTaskPeriodId")
    @ResponseBody
    public Map<String, Object> searchProjectNumContractNumFileNumberByTaskPeriodId(HttpSession session,@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询备注
            HTTaskVO htTaskVO = htTaskService.excSearchProjectNumContractNumFileNumberByTaskPeriodId(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回项目编号、合同编号和成果文件编号
            resultMap.put(Constant.DATA_VO_KEY, htTaskVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加阶段文件信息
     *
     * @return
     */
    @RequestMapping("/add/insertTaskPeriodMaterialInfo")
    @ResponseBody
    public Map<String,Object> insertTaskPeriodMaterialInfo(
            HTTaskPeriodMaterialVO paramVO,
            HttpSession session,
            @RequestParam(value = "file",required = false) MultipartFile[] file){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            //添加文件信息
            htTaskService.excInsertTaskPeriodMaterialInfo(paramVO,file);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 修改工时列表中的备注
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateRemarksInfo")
    @ResponseBody
    public Map<String, Object> updateRemarksInfo(HttpSession session,@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 修改工时列表中的备注
            htTaskService.excUpdateRemarksInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询备注
     *
     * @return 查询备注
     */
    @RequestMapping("/query/searchRemarksByRelationId")
    @ResponseBody
    public Map<String, Object> searchRemarksByRelationId(HttpSession session,@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询备注
            HTTaskVO htTaskVO = htTaskService.excSearchRemarksByRelationId(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回备注
            resultMap.put(Constant.DATA_VO_KEY, htTaskVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 工时列表和总条数
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchHourListInfo")
    @ResponseBody
    public Map<String, Object> searchHourListInfo(HttpSession session,@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询工时列表和总条数
            Map<String, Object> retMap = htTaskService.excSearchHourListInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回工时列表和总条数
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 修改我的工时
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateMyHourListInfo")
    @ResponseBody
    public Map<String, Object> updateMyHourListInfo(HttpSession session,@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 修改我的工时
            htTaskService.excUpdateMyHourListInfo(paramVO);

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

    /**
     * 查询我的工时
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchMyHourListInfo")
    @ResponseBody
    public Map<String, Object> searchMyHourListInfo(HttpSession session,@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询我的工时
            Map<String,Object> retMap = htTaskService.excSearchMyHourListInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回我的工时和当前时间
            resultMap.putAll(retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询自定义工时列表
     */
    @RequestMapping("/query/queryCustomTimeList")
    @ResponseBody
    public Map<String, Object> queryCustomTimeList(HttpSession session, @RequestBody HTTaskVO paramVO) {
        Map<String, Object> resultMap;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            // 查询自定义工时列表
            Map<String, Object> retMap = htTaskService.excQueryCustomTimeList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回自定义工时列表
            resultMap.putAll(retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 保存自定义工时
     */
    @RequestMapping("/save/saveCustomTime")
    @ResponseBody
    public Map<String, Object> saveCustomTime(HttpSession session, @RequestBody HTUserCustomTimeVO paramVO) {
        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            // 保存自定义工时
            htTaskService.excSaveCustomTime(paramVO);
            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询任务内容
     *
     * @return 任务内容
     */
    @RequestMapping("/query/searchTaskContent")
    @ResponseBody
    public Map<String, Object> searchTaskContent(HttpSession session,@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询任务内容
            HTTaskVO htTaskVO = htTaskService.excSearchTaskContent(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回任务内容
            resultMap.put(Constant.DATA_VO_KEY, htTaskVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 修改任务信息
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateTaskInfo")
    @ResponseBody
    public Map<String, Object> updateTaskInfo(HttpSession session, @RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 修改任务信息信息
            htTaskService.excUpdateTaskInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex){
            // 失败相应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询任务详情
     *
     * @return 任务详情
     */
    @RequestMapping("/query/searchTaskInfo")
    @ResponseBody
    public Map<String, Object> excSearchTaskInfo(HttpSession session,@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询任务详情
            Map<String,Object> retMap = htTaskService.excSearchTaskInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回任务详情
            resultMap.put(Constant.DATA_VO_KEY, retMap.get(Constant.DATA_VO_KEY));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 删除任务信息
     *
     * @return 请求参数
     */
    @RequestMapping("/delete/deleteTaskInfo")
    @ResponseBody
    public Map<String, Object> deleteContractInfo(@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 删除任务信息
            htTaskService.excDeleteTaskInfo(paramVO);

            // MSG_ERROR_COMMON_0008 删除成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_ERROR_COMMON_0008));

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 根据部门查人员列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchUserListByDepartmentId")
    @ResponseBody
    public Map<String, Object> searchUserListByDepartmentId(@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 根据部门查人员列表
            List<HTUserVO> htUserVOList = htTaskService.excSearchUserListByDepartmentId(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回人员列表
            resultMap.put(Constant.DATA_LIST_KEY, htUserVOList);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 根据合同类型ID查询咨询类别状态列表
     *
     * @return 咨询类别状态列表
     */
    @RequestMapping("/query/searchDictCounselTypeListByContractTypeId")
    @ResponseBody
    public Map<String, Object> searchDictCounselTypeListByContractTypeId(@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 咨询类别状态列表
            List<HTDictCounselTypeVO> htDictCounselTypeVOList = htTaskService.excSearchDictCounselTypeListByContractTypeId(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回咨询类别状态列表
            resultMap.put(Constant.DATA_LIST_KEY, htDictCounselTypeVOList);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 根据部门ID查询合同类型列表
     *
     * @return 请求合同类型列表参数
     */
    @RequestMapping("/query/searchDictContractTypeListByDepartmentId")
    @ResponseBody
    public Map<String, Object> searchDictContractTypeListByDepartmentId(@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 请求合同类型列表参数
            List<HTDictContractTypeVO> htDictContractTypeVOList = htTaskService.excSearchDictContractTypeListByDepartmentId(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回合同类型列表参数
            resultMap.put(Constant.DATA_LIST_KEY, htDictContractTypeVOList);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加任务信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertTaskInfo")
    @ResponseBody
    public Map<String, Object> insertTaskInfo(HttpSession session, @RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加任务信息,返回任务id
            String taskId = htTaskService.excInsertTaskInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

            // 任务id
            resultMap.put(Constant.DATA_PRIMARY_KEY,taskId);

        } catch (LogicException lex){
            // 失败相应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询人员信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchUserInfo")
    @ResponseBody
    public Map<String, Object> searchUserInfo(@RequestBody HTTaskVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询人员信息
            List<HTTaskVO> htTaskVO = htTaskService.excSearchUserInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回人员信息
            resultMap.put(Constant.DATA_LIST_KEY, htTaskVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询拟定工作计划信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchTaskWorkPlan")
    @ResponseBody
    public Map<String, Object> searchTaskWorkPlan(@RequestBody HTTableFiveRelationVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询拟定工作计划信息
            HTTableFiveRelationVO htTaskVO = htTaskService.excSearchTaskWorkPlanInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回人员信息
            resultMap.put(Constant.DATA_VO_KEY, htTaskVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加拟定工作计划信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/addTaskWorkPlan")
    @ResponseBody
    public Map<String, Object> addTaskWorkPlan(HttpSession session, @RequestBody HTTableFiveRelationVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加拟定工作计划信息
            htTaskService.excInsertTaskWorkPlan(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

     /**
     * 查询编制成果文件-A2信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchTaskCompileResultsFileA2")
    @ResponseBody
    public Map<String, Object> searchTaskCompileResultsFileA2(@RequestBody HTTableTwoRelationVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询编制成果文件-A2信息
            HTTableTwoRelationVO htTaskVO = htTaskService.excSearchTaskCompileResultsFileA2(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回人员信息
            resultMap.put(Constant.DATA_VO_KEY, htTaskVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

     /**
     * 添加编制成果文件-A2信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/addTaskCompileResultsFileA2")
    @ResponseBody
    public Map<String, Object> addTaskCompileResultsFileA2(HttpSession session, @RequestBody HTTableTwoRelationVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加编制成果文件-A2信息
            htTaskService.excInsertTaskCompileResultsFileA2(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }
}
