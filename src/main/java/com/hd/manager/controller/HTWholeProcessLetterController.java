package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.export.bean.ExcelData;
import com.hd.export.bean.ExportData;
import com.hd.export.service.impl.ExcelExport;
import com.hd.manager.service.HTProjectService;
import com.hd.manager.service.HTWholeProcessLetterService;
import com.hd.manager.vo.HTProjectVO;
import com.hd.manager.vo.HTWholeProcessLetterVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 全过程(发出信函登记表)Controller
 *
 * @author zjy
 * Created in 2019/9/9 11:14
 */
@Controller
@RequestMapping("/wholeProcess/letter")
@CrossOrigin(allowCredentials = "true")
public class HTWholeProcessLetterController extends BaseController {

    /**
     * 发出信函登记表阶段service
     */
    @Resource
    private HTWholeProcessLetterService htWholeProcessLetterService;

    @Resource
    private ExcelExport excelExport;

    /**
     * 项目service
     */
    @Resource
    HTProjectService htProjectService;


    /**
     * 查询发出信函登记表列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchLetterPeriodList")
    public Map<String, Object> searchLetterPeriodList(@RequestBody HTWholeProcessLetterVO paramVO, HttpSession session) {

        Map<String, Object> resultMap = null;
        try {

            // 查询列表
            Map<String, Object> letterMap = htWholeProcessLetterService.excSearchLetterPeriodList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = (setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));

            // 返回信息
            resultMap.put(Constant.PAGEED_DATA_KEY, letterMap);

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询发出信函登记表详情
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectLetterPeriodInfo")
    public Map<String, Object> searchProjectLetterPeriodInfo(@RequestBody HTWholeProcessLetterVO paramVO, HttpSession session) {

        Map<String, Object> resultMap = null;
        try {

            // 查询详情
            HTWholeProcessLetterVO retVO = htWholeProcessLetterService.excSearchProjectLetterPeriodInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回信息
            resultMap.put(Constant.DATA_VO_KEY, retVO);

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 新增信函登记信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/add/saveWholeProcessLetter")
    public Map<String,Object> saveWholeProcessLetter(HTWholeProcessLetterVO paramVO, HttpSession session, @RequestParam(value = "file") MultipartFile file){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 新增信息
            htWholeProcessLetterService.excSaveWholeProcessLetter(paramVO,file);

            // 返回信息
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 修改信函登记信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/modify/updateWholeProcessLetter")
    public Map<String,Object> updateWholeProcessLetter(HTWholeProcessLetterVO paramVO, HttpSession session, @RequestParam(value = "file",required = false) MultipartFile file){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 修改信息
            htWholeProcessLetterService.excUpdateWholeProcessLetter(paramVO,file);

            // 返回信息
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 导出
     */
    @RequestMapping(value = "/export/exportLetterData", method = RequestMethod.GET)
    public String exportPerformanceReport(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Date currentDate = new Date();
        String realFileName = request.getParameter("realFileName");
        response.reset();
        // 解决中文显示不出来的问题
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(realFileName, "UTF-8") + ";filename=" + URLEncoder.encode(realFileName, "UTF-8"));
        //获取参数projectId值
        String projectId = request.getParameter("projectId");
        HTProjectVO htProjectVO = new HTProjectVO();
        htProjectVO.setProjectId(projectId);
        HTProjectVO projectVO = htProjectService.excSearchProjectInfo(htProjectVO);
        HTWholeProcessLetterVO paramVO = new HTWholeProcessLetterVO();
        paramVO.setProjectId(projectId);
        //查询估算数据
        List<HTWholeProcessLetterVO> wholeProcessLetterVOList = htWholeProcessLetterService.excSearchProjectLetterPeriodListForExport(paramVO);
        //格式转换
        List<Map<String, Object>> dataList = IntStream.range(0, wholeProcessLetterVOList.size())
                .mapToObj(i -> toLetterMap(wholeProcessLetterVOList.get(i), i))
                .collect(Collectors.toList());
        //固定表头
        String[] columnTxt = {"序号","日期", "文件名称", "文件编号", "发文形式", "收件单位","收件人","文件形式","份数","备注"};
        //对应属性
        String[] columnNames = {"sortNum","editDate", "fileName", "fileNumber", "sendType", "receiveUnit", "receivePeople","fileType","copyNum", "remark"};
        //单元格宽高
        int[] columnWidth = {50 * 100,80 * 100, 100 * 100, 60 * 100, 60 * 100, 60 * 100, 80 * 100, 60 * 100, 60 * 100, 60 * 100};
        //创建表格实体
        ExcelData excelData = new ExcelData();
        //表格sheet页名称
        excelData.setSheetName("发出信函登记表");
        //表格单页标题
        excelData.setTitle(projectVO.getProjectNum()+"项目发文台账");
        //填入表头
        excelData.setColumnTxt(columnTxt);
        //对应属性名称
        excelData.setColumnNames(columnNames);
        //填入单元格样式
        excelData.setColumnWidth(columnWidth);
        //填入数据
        excelData.setDataList(dataList);
        List<ExportData> exportDatas = new ArrayList<>();
        exportDatas.add(excelData);
        Workbook workbook = (Workbook) excelExport.export(exportDatas,currentDate);
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return "导出文件失败";
        }
        return "导出文件成功";
    }

    /**
     * 格式转换
     */
    private Map<String, Object> toLetterMap(HTWholeProcessLetterVO tempVO,Integer index) {
        Map<String, Object> map = new HashMap<>();
        /*----------------------将Object对象转换成Map<key,value>------------------------*/
        map.put("sortNum", index+1);
        map.put("editDate", StringToolUtils.isNull(tempVO.getEditDate())?"-":tempVO.getEditDate());
        map.put("fileName", StringToolUtils.isNull(tempVO.getFileName())?"-":tempVO.getFileName());
        map.put("fileNumber", StringToolUtils.isNull(tempVO.getFileNumber())?"-":tempVO.getFileNumber());
        map.put("fileType", StringToolUtils.isNull(tempVO.getSendType())?"-":tempVO.getSendType());
        map.put("receiveUnit", StringToolUtils.isNull(tempVO.getReceiveUnit())?"-":tempVO.getReceiveUnit());
        map.put("receivePeople", StringToolUtils.isNull(tempVO.getReceivePeople())?"-":tempVO.getReceivePeople());
        map.put("sendType", StringToolUtils.isNull(tempVO.getFileType())?"-":tempVO.getFileType());
        map.put("copyNum", StringToolUtils.isNull(tempVO.getCopyNum())?"-":tempVO.getCopyNum());
        map.put("remark", StringToolUtils.isNull(tempVO.getRemark())?"-":tempVO.getRemark());
        /*-------------------------------------end--------------------------------------*/
        //返回Map
        return map;
    }

}
