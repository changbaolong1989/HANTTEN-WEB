package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.*;
import com.hd.export.bean.ExcelData;
import com.hd.export.bean.ExportData;
import com.hd.export.service.impl.ExcelExport;
import com.hd.manager.service.HTProjectService;
import com.hd.manager.service.HTWholeProcessTenderService;
import com.hd.manager.vo.HTProjectVO;
import com.hd.manager.vo.HTWholeProcessTenderVO;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 全过程(概算)Controller
 *
 * @author LH
 * Created in 2019/9/3 14:29
 */
@Controller
@RequestMapping("/wholeProcess/tender")
@CrossOrigin(allowCredentials = "true")
public class HTWholeProcessTenderController extends BaseController {


    /**
     * 项目招标/清标阶段service
     */
    @Resource
    HTWholeProcessTenderService htWholeProcessTenderService;

    /**
     * 项目service
     */
    @Resource
    HTProjectService htProjectService;

    @Resource
    private ExcelExport excelExport;


    /**
     * 查询招标列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectTenderPeriodList")
    public Map<String,Object> searchProjectTenderPeriodList(@RequestBody HTWholeProcessTenderVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htWholeProcessTenderService.excSearchProjectTenderPeriodList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询清标列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectCleanTenderPeriodList")
    public Map<String,Object> searchProjectCleanTenderPeriodList(@RequestBody HTWholeProcessTenderVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htWholeProcessTenderService.excSearchProjectCleanTenderPeriodList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询招标/清标详情
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectTenderPeriodInfo")
    public Map<String,Object> searchProjectTenderPeriodInfo(@RequestBody HTWholeProcessTenderVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_VO_KEY,htWholeProcessTenderService.excSearchProjectTenderPeriodInfo(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 新增项目招标信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/insert/insertWholeProcessTender")
    public Map<String,Object> insertWholeProcessTender(HTWholeProcessTenderVO paramVO, HttpSession session, @RequestParam(value = "file")MultipartFile file){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessTenderService.excInsertWholeProcessTender(paramVO,file);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 修改项目招标信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/modify/updateWholeProcessTender")
    public Map<String,Object> updateWholeProcessTender(HTWholeProcessTenderVO paramVO, HttpSession session, @RequestParam(value = "file",required = false)MultipartFile file){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessTenderService.excUpdateWholeProcessTender(paramVO,file);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 修改项目清标信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/modify/updateWholeProcessCleanTender")
    public Map<String,Object> updateWholeProcessCleanTender(HTWholeProcessTenderVO paramVO, HttpSession session, @RequestParam(value = "file",required = false)MultipartFile file){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessTenderService.excUpdateWholeProcessCleanTender(paramVO,file);
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
    @RequestMapping(value = "/export/exportTenderData", method = RequestMethod.GET)
    public String exportPerformanceReport(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String realFileName = request.getParameter("realFileName");
        response.reset();
        // 解决中文显示不出来的问题
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(realFileName, "UTF-8") + ";filename=" + URLEncoder.encode(realFileName, "UTF-8"));
        //获取参数projectId值
        String projectId = request.getParameter("projectId");
        HTProjectVO htProjectVO = new HTProjectVO();
        htProjectVO.setProjectId(projectId);
        HTProjectVO projectVO = htProjectService.excSearchProjectInfo(htProjectVO);
        HTWholeProcessTenderVO paramVO = new HTWholeProcessTenderVO();
        paramVO.setProjectId(projectId);
        //查询估算数据
        List<HTWholeProcessTenderVO> wholeProcessTenderVOList = htWholeProcessTenderService.excSearchProjectTenderPeriodListForExport(paramVO);
        //序号
        String index = "";
        //格式转换
        List<Map<String, Object>> dataList = new ArrayList<>();
        this.ListChangeUtil(wholeProcessTenderVOList, dataList,index);
        //对应属性
        String[] columnNames = {"sortNum","tenderNum","tenderName","planTime","contractType","purchaserWay","targetCost", "fileType","exciseMoney","taxRatio","taxMoney","withTaxMoney","remark"};
        //创建表格实体
        ExcelData excelData = new ExcelData();
        List<ExportData> exportDatas = new ArrayList<>();
        exportDatas.add(excelData);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/tender.xls");
        Workbook workbook = ExcelUtils.readExcel(inputStream);
        ExcelUtils.creatExcel(workbook,0,0,projectVO.getProjectNum()+"项目招标台账",1,columnNames,dataList,4);

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
    private List<Map<String, Object>> ListChangeUtil(List<HTWholeProcessTenderVO>  wholeProcessTenderVOList,List<Map<String, Object>> dataList,String index) {
        //格式转换
        for (int i=0;i<wholeProcessTenderVOList.size();i++){
            boolean saveFlag = true;
            StringBuffer sortNum = new StringBuffer(index);
            if (wholeProcessTenderVOList.get(i).getCurrentLevel().equals(Constant.SECOND_LEVEL_MENU_FLAG)
                    &&!wholeProcessTenderVOList.get(i).getTreatyType().equals("1")
                    ||wholeProcessTenderVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                saveFlag = false;
            }
            if(saveFlag||wholeProcessTenderVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                Map<String, Object> resultMap = this.toTenderMap(wholeProcessTenderVOList.get(i),sortNum.append(i+1).toString());
                dataList.add(resultMap);
            }
            if (wholeProcessTenderVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                sortNum = new StringBuffer("");
            }
            List<HTWholeProcessTenderVO> childList = wholeProcessTenderVOList.get(i).getChildList();
            if (!ListToolUtils.isEmpty(childList)){
                if (saveFlag){
                    sortNum.append(".");
                }
                dataList = this.ListChangeUtil(childList,dataList,sortNum.toString());
            }
        }
        return dataList;
    }

    /**
     * 格式转换
     */
    private Map<String, Object> toTenderMap(HTWholeProcessTenderVO tempVO,String index) {
        Map<String, Object> map = new HashMap<>();
        /*----------------------将Object对象转换成Map<key,value>------------------------*/
        //序号
        map.put("sortNum", index);
        //校验是否是一级(需要展示合并单元格的合约分类)
        if (tempVO.getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
            map.put(ExcelUtils.SUB_LEVEL_CELL_TYPE, ExcelUtils.SUB_LEVEL_CELL_TYPE_TEXT);
            map.put(ExcelUtils.SUB_LEVEL_CELL_TEXT,tempVO.getTreatyTypeName());
        }
        //招标编号
        map.put("tenderNum", StringToolUtils.isNull(tempVO.getTenderNum())?"":tempVO.getTenderNum());
        //计划起止时间
        map.put("planTime", (StringToolUtils.isNull(tempVO.getPlanStartTime())?"":tempVO.getPlanStartTime())+"-"+(StringToolUtils.isNull(tempVO.getPlanEndTime())?"":tempVO.getPlanEndTime()));
        //招标名称
        map.put("tenderName", StringToolUtils.isNull(tempVO.getTenderName())?"":tempVO.getTenderName());
        if (!StringToolUtils.isNull(tempVO.getUndertakeTypeName())){
            map.put("tenderName", tempVO.getUndertakeTypeName());
            map.put("planTime", "");
        }
        //合同类型
        map.put("contractType", StringToolUtils.isNull(tempVO.getContractType())?"":tempVO.getContractType());
        //采购方式
        map.put("purchaserWay", StringToolUtils.isNull(tempVO.getPurchaserWay())?"":tempVO.getPurchaserWay());
        //目标成本
        map.put("targetCost", StringToolUtils.isNull(tempVO.getTargetCost())?"":tempVO.getTargetCost());
        //文件类型
        map.put("fileType", StringToolUtils.isNull(tempVO.getFileType())?"":tempVO.getFileType());
        //除税金额
        map.put("exciseMoney", StringToolUtils.isNull(tempVO.getExciseMoney())?"":tempVO.getExciseMoney());
        //税率
        map.put("taxRatio", StringToolUtils.isNull(tempVO.getTaxRatio())?"":tempVO.getTaxRatio());
        //税金
        map.put("taxMoney", StringToolUtils.isNull(tempVO.getTaxMoney())?"":tempVO.getTaxMoney());
        //含税金额
        map.put("withTaxMoney", StringToolUtils.isNull(tempVO.getWithTaxMoney())?"":tempVO.getWithTaxMoney());
        //备注
        map.put("remark", StringToolUtils.isNull(tempVO.getRemark())?"":tempVO.getRemark());
        /*-------------------------------------end--------------------------------------*/
        //返回Map
        return map;
    }


    /**
     * 清标导出
     */
    @RequestMapping(value = "/export/exportCleanTenderData", method = RequestMethod.GET)
    public String exportPerformanceCleanTenderReport(HttpServletResponse response, HttpServletRequest request) throws IOException {
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
        HTWholeProcessTenderVO paramVO = new HTWholeProcessTenderVO();
        paramVO.setProjectId(projectId);
        //查询估算数据
        List<HTWholeProcessTenderVO> wholeProcessCleanTenderVOList = htWholeProcessTenderService.excSearchProjectCleanTenderPeriodListForExport(paramVO);
        //格式转换
        List<Map<String, Object>> dataList = IntStream.range(0, wholeProcessCleanTenderVOList.size())
                .mapToObj(i -> toCleanTenderMap(wholeProcessCleanTenderVOList.get(i), i))
                .collect(Collectors.toList());
        //固定表头
        String[] columnTxt = {"序号","招标名称","合同类型", "接收资料时间", "发出报告事件"};
        //对应属性
        String[] columnNames = {"sortNum","tenderName", "contractType", "acceptFileTime", "sendReportTime"};
        //单元格宽高
        int[] columnWidth = {30 * 100,80 * 100, 100 * 100, 60 * 100, 60 * 100, 60 * 100, 80 * 100, 60 * 100};
        //创建表格实体
        ExcelData excelData = new ExcelData();
        //表格sheet页名称
        excelData.setSheetName("清标台账");
        //表格单页标题
        excelData.setTitle(projectVO.getProjectNum()+"项目清标台账");
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
    private Map<String, Object> toCleanTenderMap(HTWholeProcessTenderVO tempVO,Integer index) {
        Map<String, Object> map = new HashMap<>();
        /*----------------------将Object对象转换成Map<key,value>------------------------*/
        map.put("sortNum", index+1);
        map.put("tenderName", StringToolUtils.isNull(tempVO.getTenderName())?"-":tempVO.getTenderName());
        map.put("contractType", StringToolUtils.isNull(tempVO.getContractType())?"-":tempVO.getContractType());
        map.put("acceptFileTime", StringToolUtils.isNull(tempVO.getAcceptFileTime())?"-":tempVO.getAcceptFileTime());
        map.put("sendReportTime", StringToolUtils.isNull(tempVO.getSendReportTime())?"-":tempVO.getSendReportTime());
        /*-------------------------------------end--------------------------------------*/
        //返回Map
        return map;
    }
}
