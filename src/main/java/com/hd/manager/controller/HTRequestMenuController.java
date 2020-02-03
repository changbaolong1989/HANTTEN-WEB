package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.enums.CashApplyEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.dao.TbRequestMenuEntityMapper;
import com.hd.common.entity.TbRequestMenuEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.ExcelUtils;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.export.bean.ExcelData;
import com.hd.manager.service.HTRequestMenuService;
import com.hd.manager.vo.HTRequestMenuHisTaskInstVO;
import com.hd.manager.vo.HTRequestMenuVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请款单Service
 *
 * @author lihai
 * Created in 2019/8/1 14:45
 **/
@Controller
@RequestMapping("/requestMenu")
@CrossOrigin(allowCredentials ="true")
public class HTRequestMenuController extends BaseController {


    /**
     * 请款单Service对象
     */
    @Resource
    private HTRequestMenuService htRequestMenuService;

    @Resource
    private TbRequestMenuEntityMapper requestMenuEntityMapper;


    /**
     * 添加请款单信息
     */
    @ResponseBody
    @RequestMapping("/add/insertRequestMenuInfo")
    public Map<String,Object> insertRequestMenuInfo(@RequestBody HTRequestMenuVO paramVO, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            String requestMenuId = htRequestMenuService.excInsertRequestMenuInfo(paramVO);
            resultMap.put(Constant.DATA_PRIMARY_KEY,requestMenuId);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }



    /**
     * 删除请款单信息
     */
    @ResponseBody
    @RequestMapping("/delete/deleteRequestMenuInfo")
    public Map<String,Object> deleteRequestMenuInfo(@RequestBody HTRequestMenuVO paramVO, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htRequestMenuService.excDeleteRequestMenuInfo(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 提交审核(修改请款单信息)
     */
    @ResponseBody
    @RequestMapping("/modify/updateRequestMenuInfo")
    public Map<String,Object> updateRequestMenuInfo(HTRequestMenuVO paramVO,
                                                    HttpSession session,
                                                    @RequestParam(value = "file",required = false) MultipartFile file,
                                                    @RequestParam(value = "demoFile",required = false) MultipartFile demoFile){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htRequestMenuService.excUpdateRequestMenuInfo(paramVO,file,demoFile);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询请款单信息
     */
    @ResponseBody
    @RequestMapping("/query/searchRequestMenuInfo")
    public Map<String,Object> searchRequestMenuInfo(@RequestBody HTRequestMenuVO paramVO, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            HTRequestMenuVO htRequestMenuVO = htRequestMenuService.excSearchRequestMenuInfo(paramVO);
            resultMap.put(Constant.DATA_VO_KEY,htRequestMenuVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 请款单撤回
     *
     * @return
     */
    @RequestMapping("/modify/recallQuestionMenu")
    @ResponseBody
    public Map<String,Object> recallQuestionMenu(@RequestBody HTRequestMenuVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //执行撤回
            htRequestMenuService.excRecallQuestionMenu(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }



    /**
     * 请款单审核
     *
     * @return
     */
    @RequestMapping("/modify/auditingQuestionMenu")
    @ResponseBody
    public Map<String,Object> auditingQuestionMenu(@RequestBody HTRequestMenuVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //执行提交审核
            htRequestMenuService.excAuditingQuestionMenu(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 财务开具发票
     */
    @RequestMapping("/modify/uploadInvoiceFile")
    @ResponseBody
    public Map<String,Object> uploadInvoiceFile(HTRequestMenuVO paramVO, HttpSession session, @RequestParam(value = "file",required = false) MultipartFile file){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //上传发票
            htRequestMenuService.excUploadInvoiceFile(paramVO,file);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 上传发票
     */
    @RequestMapping("/modify/uploadBill")
    @ResponseBody
    public Map<String,Object> uploadBill(HTRequestMenuVO paramVO, HttpSession session,
                                         @RequestParam(value = "file",required = false) MultipartFile file){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //上传发票
            htRequestMenuService.excUploadBill(paramVO,file);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 上传发票申请单附件
     */
    @RequestMapping("/modify/uploadInvoiceApplyFile")
    @ResponseBody
    public Map<String,Object> uploadInvoiceApplyFile(HTRequestMenuVO paramVO, HttpSession session,
                                                     @RequestParam(value = "file", required = false) MultipartFile[] file){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //上传发票申请单附件
            HTRequestMenuVO requestMenuVO = htRequestMenuService.excUploadInvoiceApplyFile(paramVO,file);
            resultMap.put(Constant.DATA_VO_KEY,requestMenuVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 删除发票申请单附件
     */
    @RequestMapping("/modify/deleteInvoiceApplyFile")
    @ResponseBody
    public Map<String,Object> deleteInvoiceApplyFile(@RequestBody HTRequestMenuVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //上传发票申请单附件
            htRequestMenuService.excDeleteInvoiceApplyFile(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());

        }
        return resultMap;
    }


    /**
     * 上传打款凭证
     *
     * @return
     */
    @RequestMapping("/modify/uploadPaymentVoucher")
    @ResponseBody
    public Map<String,Object> uploadPaymentVoucher(HTRequestMenuVO paramVO, HttpSession session,
                                                   @RequestParam(value = "file",required = false) MultipartFile file){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //上传打款凭证
            htRequestMenuService.excUploadPaymentVoucher(paramVO,file);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 查询登录角色功能
     *
     * @return
     */
    @RequestMapping("/query/showUserType")
    @ResponseBody
    public Map<String,Object> showUserType(@RequestBody HTRequestMenuVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //查询当前登录人身份
            resultMap.put(Constant.BUTTON_TYPE_FLAG,htRequestMenuService.excShowUserType(paramVO));
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }



    /**
     * 查询审核意见信息
     *
     * @return
     */
    @RequestMapping("/query/searchHTRequestMenuHisTaskInst")
    @ResponseBody
    public Map<String,Object> searchHTRequestMenuHisTaskInst(@RequestBody HTRequestMenuHisTaskInstVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //上传打款凭证
            List<HTRequestMenuHisTaskInstVO> hisTaskInstVOList = htRequestMenuService.excSearchHTRequestMenuHisTaskInst(paramVO);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
            resultMap.put(Constant.DATA_LIST_KEY,hisTaskInstVOList);
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 提交发票
     *
     * @return
     */
    @RequestMapping("/modify/commitBillStep")
    @ResponseBody
    public Map<String,Object> commitBillStep(@RequestBody HTRequestMenuVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //提交发票
            htRequestMenuService.excCommitBillStep(paramVO);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 确认到款
     *
     * @return
     */
    @RequestMapping("/modify/confirmVoucherStep")
    @ResponseBody
    public Map<String,Object> confirmVoucherStep(@RequestBody HTRequestMenuVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //确认到款
            htRequestMenuService.excConfirmVoucherStep(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 获取是否是主提审人
     */
    @RequestMapping("/query/getIsApplyUser")
    @ResponseBody
    public Boolean getIsApplyUser(@RequestBody HTRequestMenuVO paramVO, HttpSession session){
        // 获取session 登陆用户信息
        paramVO = putLoginUserToVOFromSession(paramVO, session);
        TbRequestMenuEntity requestMenu = requestMenuEntityMapper.selectByPrimaryKey(paramVO.getRequestMenuId());
        boolean result = false;
        if(paramVO.getLoginUserInfo().getLoginUserId().equals(requestMenu.getApplyUserId())){
            if(StringUtils.isBlank(requestMenu.getCurrentState()) || Integer.parseInt(requestMenu.getCurrentState()) <= CashApplyEnum.FinanceInvoice.getNodeSort()){
                result = true;
            }
        }
        return result;
    }

    /**
     *下载发票申请单模板
     */
    @RequestMapping(value = "/download/downloadDemoFile", method = RequestMethod.GET)
    public String exportPerformanceReport(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String realFileName = request.getParameter("realFileName");
        response.reset();
        // 解决中文显示不出来的问题
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(realFileName, "UTF-8") + ";filename=" + URLEncoder.encode(realFileName, "UTF-8"));
        //创建表格实体
        ExcelData excelData = new ExcelData();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("requestMenu/demo.xlsx");
        Workbook workbook = ExcelUtils.readExcel(inputStream);
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return "导出文件失败";
        }
        return "导出文件成功";
    }



}
