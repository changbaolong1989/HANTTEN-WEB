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
import com.hd.manager.service.HTWholeProcessAssessService;
import com.hd.manager.service.HTWholeProcessEstimateService;
import com.hd.manager.vo.HTWholeProcessAssessVO;
import com.hd.manager.vo.HTWholeProcessEstimateVO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 全过程(概算)Controller
 *
 * @author LH
 * Created in 2019/9/3 14:29
 */
@Controller
@RequestMapping("/wholeProcess/estimate")
@CrossOrigin(allowCredentials = "true")
public class HTWholeProcessEstimateController extends BaseController {


    /**
     * 项目概算阶段service
     */
    @Resource
    HTWholeProcessEstimateService htWholeProcessEstimateService;

    @Resource
    private ExcelExport excelExport;

    /**
     * 查询概算列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectEstimatePeriodList")
    public Map<String,Object> searchProjectEstimatePeriodList(@RequestBody HTWholeProcessEstimateVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htWholeProcessEstimateService.excSearchProjectEstimatePeriodList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询概算详情
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectEstimatePeriodInfo")
    public Map<String,Object> searchProjectEstimatePeriodInfo(@RequestBody HTWholeProcessEstimateVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_VO_KEY,htWholeProcessEstimateService.excSearchProjectEstimatePeriodInfo(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 新增项目概算信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/insert/insertWholeProcessEstimate")
    public Map<String,Object> insertWholeProcessEstimate(HTWholeProcessEstimateVO paramVO, HttpSession session, @RequestParam(value = "file",required = false)MultipartFile file){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessEstimateService.excInsertWholeProcessEstimate(paramVO,file);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 修改项目概算信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/modify/updateWholeProcessEstimate")
    public Map<String,Object> updateWholeProcessEstimate(HTWholeProcessEstimateVO paramVO, HttpSession session, @RequestParam(value = "file",required = false)MultipartFile file){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessEstimateService.excUpdateWholeProcessEstimate(paramVO,file);
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
    @RequestMapping(value = "/export/exportEstimateData", method = RequestMethod.GET)
    public String exportPerformanceReport(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String realFileName = request.getParameter("realFileName");
        response.reset();
        // 解决中文显示不出来的问题
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(realFileName, "UTF-8") + ";filename=" + URLEncoder.encode(realFileName, "UTF-8"));
        //获取参数projectId值
        String projectId = request.getParameter("projectId");
        HTWholeProcessEstimateVO paramVO = new HTWholeProcessEstimateVO();
        paramVO.setProjectId(projectId);
        //查询估算数据
        List<HTWholeProcessEstimateVO> wholeProcessEstimateVOList = htWholeProcessEstimateService.excSearchProjectEstimatePeriodListForExport(paramVO);
        //格式转换
        List<Map<String, Object>> dataList = IntStream.range(0, wholeProcessEstimateVOList.size())
                .mapToObj(i -> toEstimateMap(wholeProcessEstimateVOList.get(i), i))
                .collect(Collectors.toList());
        //固定表头
        String[] columnTxt = {"序号","编制日期", "概算名称/版本", "建筑面积（m²）", "概算费用（元）", "概算单方（元/m²）","费用范围","备注"};
        //对应属性
        String[] columnNames = {"sortNum","compileDate", "estimateName", "floorSpace", "estimatedCost", "estimationExParte", "costRange","remark"};
        //单元格宽高
        int[] columnWidth = {30 * 100,80 * 100, 100 * 100, 60 * 100, 60 * 100, 60 * 100, 80 * 100, 60 * 100};
        //创建表格实体
        ExcelData excelData = new ExcelData();
        //表格sheet页名称
        excelData.setSheetName("概算");
        //表格单页标题
        excelData.setTitle("概算台账");
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
        Workbook workbook = (Workbook) excelExport.export(exportDatas);
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
    private Map<String, Object> toEstimateMap(HTWholeProcessEstimateVO tempVO,Integer index) {
        Map<String, Object> map = new HashMap<>();
        /*----------------------将Object对象转换成Map<key,value>------------------------*/
        map.put("sortNum", index+1);
        map.put("compileDate", StringToolUtils.isNull(tempVO.getCompileDate())?"-":tempVO.getCompileDate());
        map.put("estimateName", StringToolUtils.isNull(tempVO.getEstimateName())?"-":tempVO.getEstimateName());
        map.put("floorSpace", StringToolUtils.isNull(tempVO.getFloorSpace())?"-":tempVO.getFloorSpace());
        map.put("estimatedCost", StringToolUtils.isNull(tempVO.getEstimatedCost())?"-":tempVO.getEstimatedCost());
        map.put("estimationExParte", StringToolUtils.isNull(tempVO.getEstimationExParte())?"-":tempVO.getEstimationExParte());
        map.put("costRange", StringToolUtils.isNull(tempVO.getCostRange())?"-":tempVO.getCostRange());
        map.put("remark", StringToolUtils.isNull(tempVO.getRemark())?"-":tempVO.getRemark());
        /*-------------------------------------end--------------------------------------*/
        //返回Map
        return map;
    }
}
