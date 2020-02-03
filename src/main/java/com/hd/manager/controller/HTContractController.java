package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.common.util.word.WordTemplate;
import com.hd.manager.service.HTContractService;
import com.hd.manager.vo.HTContractTaskHisTaskInstVO;
import com.hd.manager.vo.HTContractVO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设置-合同Controller
 *
 * @author jwl
 * Created in 2019/7/17 15:21
 */
@Controller
@RequestMapping("/contract")
@CrossOrigin(allowCredentials = "true")
public class HTContractController extends BaseController {

    @Resource
    private HTContractService htContractService;

    @Value("${file.request.rootpath}")
    private String fileRootPath;

    /**
     * 查询合同详细信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/excSearchContractDetailInfo")
    @ResponseBody
    public Map<String, Object> excSearchContractDetailInfo(@RequestBody HTContractVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询合同详细信息
            HTContractVO htContractVO = htContractService.excSearchContractDetailInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回合同详细信息
            resultMap.put(Constant.DATA_VO_KEY, htContractVO);

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        }catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 修改合同信息
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateContractInfo")
    @ResponseBody
    public Map<String, Object> updateContractInfo(HttpSession session, HTContractVO paramVO, MultipartFile signPathFile) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 修改合同信息
            htContractService.updateContractInfo(paramVO,signPathFile);

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
     * 添加和修改项目基本情况信息
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateBaseContractInfo")
    @ResponseBody
    public Map<String, Object> updateBaseContractInfo(HttpSession session, HTContractVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加和修改项目基本情况信息
            String baseId = htContractService.updateBaseContractInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

            resultMap.put("baseId",baseId);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询项目基本情况信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/excSearchBaseContractDetailInfo")
    @ResponseBody
    public Map<String, Object> excSearchBaseContractDetailInfo(@RequestBody HTContractVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询项目基本情况信息
            HTContractVO htContractVO = htContractService.excSearchBaseContractDetailInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回合同详细信息
            resultMap.put(Constant.DATA_VO_KEY, htContractVO);

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        }catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     *  生成word合同信息
     * @param paramVO 合同id
     * @param response 画面响应
     */
    @RequestMapping("/downLoad/downLoadContractWord")
    public void downLoadContractWord(HTContractVO paramVO, HttpServletResponse response) {

        Map<String, Object> resultMap = null;

        try {

            // 查询合同基本信息、合同模板是哪个、并把信息放到map中
            resultMap = htContractService.getContractDetailInfoByContractId(paramVO);

            // 取得模板名称
            String tempContractTemplateNameStr = (String) resultMap.get("contractTemplateName");

            InputStream tempInputStream = new ClassPathResource("/contract/" + tempContractTemplateNameStr).getInputStream();


            WordTemplate wordTemplate = new WordTemplate(tempInputStream);

            // 替换合同中的变量
            wordTemplate.replaceTag(resultMap);

            response.reset();

           // String downloadFileName = new String(tempContractTemplateNameStr.getBytes("UTF-8"),"ISO-8859-1");
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition",
                    "attachment; filename*=UTF-8''"
                            + URLEncoder.encode(tempContractTemplateNameStr,
                            "UTF-8")
                            + ";filename="
                            + URLEncoder.encode(tempContractTemplateNameStr,
                            "UTF-8")
            );

            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            ServletOutputStream servletOS = response.getOutputStream(); //输出流
            wordTemplate.write(ostream);//数据写入到输出流
            servletOS.write(ostream.toByteArray());

            servletOS.flush();
            servletOS.close();
            ostream.close();
            tempInputStream.close();


        }  catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * 审核合同
     * @param paramVO 合同id
     * @return 请求参数
     */
    @RequestMapping("/audit/auditingHTContract")
    @ResponseBody
    public Map<String,Object> auditingHTContract(@RequestBody HTContractVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            // 审核合同
            htContractService.excAuditingHTContract(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 确认盖章
     * @param paramVO 合同信息
     * @return 请求参数
     */
    @RequestMapping("/modify/confirmSealHTContract")
    @ResponseBody
    public Map<String,Object> confirmSealHTContract(@RequestBody HTContractVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            // 盖章
            htContractService.excContractConfirmSeal(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 提交归档
     * @param paramVO 合同信息
     * @return 请求参数
     */
    @RequestMapping("/modify/submitArchiveHTContract")
    @ResponseBody
    public Map<String,Object> submitArchiveHTContract(@RequestBody HTContractVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            // 归档
            htContractService.excContractSubmitArchive(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询合同审核意见
     */
    @ResponseBody
    @RequestMapping("/query/searchHTContractTaskHisTaskInst")
    public Map<String, Object> searchHTTenderTaskHisTaskInst(@RequestBody HTContractTaskHisTaskInstVO paramVO) {

        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 查询合同审核意见
            List<HTContractTaskHisTaskInstVO> resultList = htContractService.excSearchHTContractTaskHisTaskInst(paramVO);
            resultMap.put(Constant.DATA_LIST_KEY, resultList);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询合同信息
     *
     */
    @RequestMapping("/query/searchContractInfoById")
    @ResponseBody
    public Map<String,Object> searchContractInfoById(@RequestBody HTContractVO paramVO){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            HTContractVO htContractTaskVO = htContractService.excSearchContractDetailInfoById(paramVO);
            resultMap.put(Constant.DATA_VO_KEY,htContractTaskVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        }  catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 添加合同归档文件
     */
    @RequestMapping("/add/insertContractArchiveFileInfo")
    @ResponseBody
    public Map<String,Object> insertContractArchiveFileInfo(
            HTContractVO paramVO,
            HttpSession session,
            @RequestParam(value = "file",required = false) MultipartFile file[]){

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            //添加归档文件信息
            htContractService.excInsertContractArchiveFileInfo(paramVO,file);

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
     *  删除合同归档文件信息
     * @param paramVO 合同归档路径
     * @param session 会话
     */
    @RequestMapping("/delete/deleteContractArchiveFileInfo")
    @ResponseBody
    public Map<String,Object> deleteContractArchiveFileInfo(@RequestBody HTContractVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 删除文件信息
            htContractService.excDeleteContractArchiveFileInfo(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }


    /**
     * 根据合同ID查询合同归档文件
     *
     * @return
     */
    @RequestMapping("/query/searchContractArchiveFileList")
    @ResponseBody
    public Map<String,Object> searchContractArchiveFileList(@RequestBody HTContractVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            //根据合同ID查询归档文件
            List<HTContractVO> htContractArchiveFileList = htContractService.excSearchContractArchiveFileListList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            resultMap.put(Constant.DATA_LIST_KEY,htContractArchiveFileList);

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 根据合同ID查询合同归档文件
     *
     * @return
     */
    @RequestMapping("/query/searchContractArchiveSts")
    @ResponseBody
    public Map<String,Object> searchContractArchiveSts(@RequestBody HTContractVO paramVO, HttpSession session){
        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            //根据合同id  查询合同状态
           String tempTypeFlgStr = htContractService.excGetHTContractSts(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            resultMap.put(Constant.BUTTON_TYPE_FLAG,tempTypeFlgStr);

        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 撤回
     * @param paramVO 合同信息
     * @return 请求参数
     */
    @RequestMapping("/revoke/revokeHTContract")
    @ResponseBody
    public Map<String,Object> revokeHTContract(@RequestBody HTContractVO paramVO, HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            // 撤回
            htContractService.excRevokeContractInfo(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 作废合同
     *
     * @return 请求参数
     */
    @RequestMapping("/update/invalidContract")
    @ResponseBody
    public Map<String, Object> invalidContract(HttpSession session ,@RequestBody HTContractVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            //放置用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            //作废项目
            htContractService.excInvalidContract(paramVO);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }
}
