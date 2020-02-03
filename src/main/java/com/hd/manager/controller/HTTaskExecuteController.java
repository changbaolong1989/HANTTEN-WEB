package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.dao.TbTaskEntityMapper;
import com.hd.common.dao.TbTenderTaskEntityMapper;
import com.hd.common.entity.TbTaskEntity;
import com.hd.common.entity.TbTenderTaskEntity;
import com.hd.common.service.CommonService;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.word.WordTemplate;
import com.hd.manager.service.HTTaskExecuteService;
import com.hd.manager.vo.HTTaskExecuteVO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * 任务执行Controller
 *
 * @author Dongzx
 * Created in 2019/8/13 10:48
 */
@Controller
@RequestMapping("/taskExecute")
@CrossOrigin(allowCredentials = "true")
public class HTTaskExecuteController extends BaseController {

    @Value("${file.request.rootpath}")
    private String prefixPath;

    /**
     * 任务执行service 业务层资源
     */
    @Resource
    private HTTaskExecuteService htTaskExecuteService;

    /**
     * 任务执行service 业务层资源
     */
    @Resource
    private TbTaskEntityMapper tbTaskEntityMapper;

    @Resource
    private TbTenderTaskEntityMapper tbTenderTaskEntityMapper;

    /**
     * 通用service
     */
    @Resource
    private CommonService commonService;



    /**
     * 生成任务执行execl文件
     * @param paramVO 任务id
     */
    @RequestMapping("/downLoad/downLoadTaskExecuteExecl")
    public void downLoadTaskExecuteExecl(HTTaskExecuteVO paramVO, HttpServletResponse response){

        try{
            Workbook tmpWorkbook = htTaskExecuteService.excGenerateTaskExecuteExeclInfo(paramVO);

            String fileNameStr = "";
            if(!StringToolUtils.isNull(paramVO.getTaskId())){
                TbTaskEntity tbTaskEntity = tbTaskEntityMapper.selectByPrimaryKey(paramVO.getTaskId());
                fileNameStr = tbTaskEntity.getTaskName()+"_流程控制表.xls";
            }else{
                TbTenderTaskEntity tbTenderTaskEntity = tbTenderTaskEntityMapper.selectByPrimaryKey(paramVO.getTenderTaskId());
                fileNameStr = tbTenderTaskEntity.getTenderTaskName()+"_流程控制表.xls";
            }

            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition",
                    "attachment; filename*=UTF-8''"
                            + URLEncoder.encode(fileNameStr,
                            "UTF-8")
                            + ";filename="
                            + URLEncoder.encode(fileNameStr,
                            "UTF-8")
            );

            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            ServletOutputStream servletOS = response.getOutputStream(); //输出流
            tmpWorkbook.write(ostream);
            servletOS.write(ostream.toByteArray());
            servletOS.flush();
            servletOS.close();
            ostream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询A4
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchA4Info")
    @ResponseBody
    public Map<String, Object> searchA4Info(@RequestBody HTTaskExecuteVO paramVO) {

        Map<String, Object> resultMap = null;
        try {
            // 查询A4信息
            HTTaskExecuteVO htTaskExecuteVO = htTaskExecuteService.excSearchA4Info(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回A4信息
            resultMap.put(Constant.DATA_VO_KEY, htTaskExecuteVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 添加A7招标信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertA7ZbdlInfo")
    @ResponseBody
    public Map<String,Object> insertA7ZbdlInfo(@RequestBody HTTaskExecuteVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加招标工作进度计划A7信息
            htTaskExecuteService.excInsertA7ZbdlInfo(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 添加【招标部-执行任务(咨询记录A8)】信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertA8ZbdlInfo")
    @ResponseBody
    public Map<String,Object> insertA8ZbdlInfo(@RequestBody HTTaskExecuteVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加【招标部-执行任务(咨询记录A8)】信息
            htTaskExecuteService.excInsertA8ZbdlInfo(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 添加A5信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertA5Info")
    @ResponseBody
    public Map<String, Object> insertA5Info(HttpSession session, @RequestBody HTTaskExecuteVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加拟定工作计划信息
            htTaskExecuteService.excInsertA5Info(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }


    /**
     * 添加【造价部-执行任务(修改完善A6)】信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertA6Info")
    @ResponseBody
    public Map<String,Object> insertA6Info(@RequestBody HTTaskExecuteVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加【造价部-执行任务(修改完善A6)】信息
            htTaskExecuteService.excInsertA6Info(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 添加【招标部-执行任务(拟定招标规划A6)】信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertA6ZbdlInfo")
    @ResponseBody
    public Map<String,Object> insertA6ZbdlInfo(@RequestBody HTTaskExecuteVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加【招标部-执行任务(拟定招标规划A6)】信息
            htTaskExecuteService.excInsertA6ZbdlInfo(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 添加【造价部-执行任务(踏勘现场A8)】信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertA8Info")
    @ResponseBody
    public Map<String,Object> insertA8Info(@RequestBody HTTaskExecuteVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加【招标部-执行任务(咨询记录A8)】信息
            htTaskExecuteService.excInsertA8Info(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询A7招标信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchA7ZbdlInfo")
    @ResponseBody
    public Map<String, Object> searchA7ZbdlInfo(@RequestBody HTTaskExecuteVO paramVO) {

        Map<String, Object> resultMap = null;
        try {
            // 查询招标工作进度计划A7信息
            HTTaskExecuteVO htTaskExecuteVO = htTaskExecuteService.excSearchA7ZbdlInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回A4信息
            resultMap.put(Constant.DATA_VO_KEY, htTaskExecuteVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 检索【A8】信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchA8Info")
    @ResponseBody
    public Map<String, Object> searchA8Info(@RequestBody HTTaskExecuteVO paramVO) {

        Map<String, Object> resultMap = null;
        try {
            // 查询A8信息
            HTTaskExecuteVO htTaskExecuteVO = htTaskExecuteService.excSearchA8Info(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回A8信息
            resultMap.put(Constant.DATA_VO_KEY, htTaskExecuteVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询A5信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchA5Info")
    @ResponseBody
    public Map<String, Object> searchTaskWorkPlan(@RequestBody HTTaskExecuteVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询拟定工作计划信息
            HTTaskExecuteVO htTaskVO = htTaskExecuteService.excSearchA5Info(paramVO);

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
     * 检索【A6】信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchA6Info")
    @ResponseBody
    public Map<String, Object> searchA6Info(@RequestBody HTTaskExecuteVO paramVO) {

        Map<String, Object> resultMap = null;
        try {
            // 查询A6信息
            HTTaskExecuteVO htTaskExecuteVO = htTaskExecuteService.excSearchA6Info(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回A6信息
            resultMap.put(Constant.DATA_VO_KEY, htTaskExecuteVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 添加A7造价信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/insertA7ZJInfo")
    @ResponseBody
    public Map<String,Object> insertA7ZJInfo(@RequestBody HTTaskExecuteVO paramVO, HttpSession session){

        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加编制成果文件A7信息
            htTaskExecuteService.excInsertA7ZJInfo(paramVO);

            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询A7造价信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchA7ZJInfo")
    @ResponseBody
    public Map<String, Object> searchA7ZJInfo(@RequestBody HTTaskExecuteVO paramVO) {

        Map<String, Object> resultMap = null;
        try {
            // 查询招标工作进度计划A7信息
            HTTaskExecuteVO htTaskExecuteVO = htTaskExecuteService.excSearchA7ZJInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回成果文件A7信息
            resultMap.put(Constant.DATA_VO_KEY, htTaskExecuteVO);

        } catch (SystemException sex) {
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
    public Map<String, Object> updateContractInfo(HttpSession session, @RequestBody HTTaskExecuteVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

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
     *  生成word 成果文件信息
     * @param paramVO 任务id
     * @param response 画面响应
     */
    @RequestMapping("/downLoad/downLoadTaskExecuteWord")
    public void downLoadTaskExecuteWord(HTTaskExecuteVO paramVO, HttpServletResponse response) {

        Map<String, Object> resultMap = null;

        try {

            // 查询任务基本信息、模板是哪个、并把信息放到map中
            resultMap = htTaskExecuteService.getTaskExecuteTemplateInfo(paramVO);

            // 取得模板名称
            String tempTaskTemplateNameStr = (String) resultMap.get("taskTemplateName");

            InputStream tempInputStream = new ClassPathResource("/task/" + tempTaskTemplateNameStr).getInputStream();


            WordTemplate wordTemplate = new WordTemplate(tempInputStream);

            // 替换模板中的变量
            wordTemplate.replaceTag(resultMap);

            response.reset();

            // String downloadFileName = new String(tempContractTemplateNameStr.getBytes("UTF-8"),"ISO-8859-1");
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition",
                    "attachment; filename*=UTF-8''"
                            + URLEncoder.encode(tempTaskTemplateNameStr,
                            "UTF-8")
                            + ";filename="
                            + URLEncoder.encode(tempTaskTemplateNameStr,
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


    @RequestMapping("/downLoad/previewByPath")
    public void previewByPath(HttpServletRequest request, HttpServletResponse response) {

        try {

            String filePath = prefixPath + request.getParameter("filePath");
            System.out.println("--------------------------------------------------");
            System.out.println(filePath);
            System.out.println("--------------------------------------------------");
            String suffix = filePath.substring(filePath.lastIndexOf("."));
            String realFileName = (int) ((Math.random() * 9 + 1) * 1000) + "_" + Long.toString(new Date().getTime()) + suffix;//4位随机数_毫秒数

            InputStream tempInputStream = new FileInputStream(filePath);

            response.reset();

            // String downloadFileName = new String(tempContractTemplateNameStr.getBytes("UTF-8"),"ISO-8859-1");
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition",
                    "attachment; filename*=UTF-8''"
                            + URLEncoder.encode(realFileName,
                            "UTF-8")
                            + ";filename="
                            + URLEncoder.encode(realFileName,
                            "UTF-8")
            );

            //FileOutputStream ostream = new FileOutputStream(new File(filePath));
            ServletOutputStream servletOS = response.getOutputStream(); //输出流
            servletOS.write(getBytes(filePath));

            servletOS.flush();
            servletOS.close();
            //ostream.close();
            tempInputStream.close();


        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] getBytes(String filePath){
        File file = new File(filePath);
        ByteArrayOutputStream out = null;
        try {
            FileInputStream in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) != -1) {

                out.write(b, 0, b.length);
            }
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return out.toByteArray();

    }

    /**
     *  生成word 请款单信息
     * @param paramVO 任务id
     * @param response 画面响应
     */
    @RequestMapping("/downLoad/downLoadRequestMenuWord")
    public void downLoadRequestMenuWord(HTTaskExecuteVO paramVO, HttpServletResponse response) {

        Map<String, Object> resultMap = null;

        try {

            // 查询请款单信息并把信息放到map中
            resultMap = htTaskExecuteService.getRequestMenuTemplateInfo(paramVO);

            // 取得模板
            InputStream tempInputStream = new ClassPathResource("/requestMenu/2019HT请款申请模板.docx").getInputStream();


            WordTemplate wordTemplate = new WordTemplate(tempInputStream);

            // 替换模板中的变量
            wordTemplate.replaceTag(resultMap);

            response.reset();

            // String downloadFileName = new String(tempContractTemplateNameStr.getBytes("UTF-8"),"ISO-8859-1");
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition",
                    "attachment; filename*=UTF-8''"
                            + URLEncoder.encode("2019HT请款申请单.docx",
                            "UTF-8")
                            + ";filename="
                            + URLEncoder.encode("2019HT请款申请单.docx",
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

}
