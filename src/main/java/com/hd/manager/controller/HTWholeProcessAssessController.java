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
import com.hd.manager.dao.bean.HTAchievementReportFormBean;
import com.hd.manager.service.HTWholeProcessAssessService;
import com.hd.manager.vo.HTWholeProcessAssessVO;
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
 * 全过程(估算)Controller
 *
 * @author LH
 * Created in 2019/9/3 14:29
 */
@Controller
@RequestMapping("/wholeProcess/assess")
@CrossOrigin(allowCredentials = "true")
public class HTWholeProcessAssessController extends BaseController {


    /**
     * 项目估算阶段service
     */
    @Resource
    HTWholeProcessAssessService htWholeProcessAssessService;

    @Resource
    private ExcelExport excelExport;

    /**
     * 查询估算列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectAssessPeriodList")
    public Map<String,Object> searchProjectAssessPeriodList(@RequestBody HTWholeProcessAssessVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htWholeProcessAssessService.excSearchProjectAssessPeriodList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询估算详情
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectAssessPeriodInfo")
    public Map<String,Object> searchProjectAssessPeriodInfo(@RequestBody HTWholeProcessAssessVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_VO_KEY,htWholeProcessAssessService.excSearchProjectAssessPeriodInfo(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 新增项目估算信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/insert/insertWholeProcessAssess")
    public Map<String,Object> excInsertWholeProcessAssess(HTWholeProcessAssessVO paramVO, HttpSession session, @RequestParam(value = "file" ,required = false)MultipartFile file){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessAssessService.excInsertWholeProcessAssess(paramVO,file);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 修改项目估算信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/modify/updateWholeProcessAssess")
    public Map<String,Object> excUpdateWholeProcessAssess(HTWholeProcessAssessVO paramVO, HttpSession session, @RequestParam(value = "file",required = false)MultipartFile file){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessAssessService.excUpdateWholeProcessAssess(paramVO,file);
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
    @RequestMapping(value = "/exprot/exportAssessData", method = RequestMethod.GET)
    public String exportPerformanceReport(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String realFileName = request.getParameter("realFileName");
        response.reset();
        // 解决中文显示不出来的问题
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(realFileName, "UTF-8") + ";filename=" + URLEncoder.encode(realFileName, "UTF-8"));
        //获取参数projectId值
        String projectId = request.getParameter("projectId");
        HTWholeProcessAssessVO paramVO = new HTWholeProcessAssessVO();
        paramVO.setProjectId(projectId);
        //查询估算数据
        List<HTWholeProcessAssessVO> wholeProcessAssessVOList = htWholeProcessAssessService.excSearchProjectAssessPeriodListForExport(paramVO);
        //格式转换
        List<Map<String, Object>> dataList = IntStream.range(0, wholeProcessAssessVOList.size())
                .mapToObj(i -> toAssessMap(wholeProcessAssessVOList.get(i), i))
                .collect(Collectors.toList());
        //固定表头
        String[] columnTxt = {"序号","编制日期", "估算名称/版本", "建筑面积（m²）", "估算费用（元）", "估算单方（元/m²）","费用范围","备注"};
        //对应属性
        String[] columnNames = {"sortNum","compileDate", "assessName", "floorSpace", "estimatedCost", "estimationExParte", "costRange", "remark"};
        //单元格宽高
        int[] columnWidth = {30 * 100,80 * 100, 100 * 100, 60 * 100, 60 * 100, 60 * 100, 80 * 100, 60 * 100};
        //创建表格实体
        ExcelData excelData = new ExcelData();
        //表格sheet页名称
        excelData.setSheetName("估算");
        //表格单页标题
        excelData.setTitle("估算台账");
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
    private Map<String, Object> toAssessMap(HTWholeProcessAssessVO tempVO,Integer index) {
        Map<String, Object> map = new HashMap<>();
        /*----------------------将Object对象转换成Map<key,value>------------------------*/
        map.put("sortNum", index+1);
        map.put("compileDate", StringToolUtils.isNull(tempVO.getCompileDate())?"-":tempVO.getCompileDate());
        map.put("assessName", StringToolUtils.isNull(tempVO.getAssessName())?"-":tempVO.getAssessName());
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
