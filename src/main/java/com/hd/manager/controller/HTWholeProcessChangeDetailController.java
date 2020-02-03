package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.*;
import com.hd.export.bean.ExcelExportParam;
import com.hd.export.style.ExcelCellStyle;
import com.hd.manager.service.HTProjectService;
import com.hd.manager.service.HTWholeProcessChangeDetailService;
import com.hd.manager.vo.HTProAlterDetailVO;
import com.hd.manager.vo.HTProjectVO;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wholeProcess/changeDetail")
@CrossOrigin(allowCredentials = "true")
public class HTWholeProcessChangeDetailController extends BaseController {

    @Resource
    private HTWholeProcessChangeDetailService changeDetailService;

    @Resource
    HTProjectService htProjectService;


    /**
     * 查询列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchChangeDetail")
    @ResponseBody
    public Map<String, Object> searchChangeDetail(@RequestBody HTProAlterDetailVO paramVO) {
        Map<String, Object> resultMap;
        try {
            Map<String, Object> retMap = changeDetailService.excSearchChangeDetailList(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 新增列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/save/saveChangeDetail")
    @ResponseBody
    public Map<String, Object> saveChangeDetail(@RequestBody HTProAlterDetailVO paramVO) {
        Map<String, Object> resultMap;
        try {
            changeDetailService.saveChangeDetail(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 修改列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateChangeDetail")
    @ResponseBody
    public Map<String, Object> updateChangeDetail(@RequestBody HTProAlterDetailVO paramVO) {
        Map<String, Object> resultMap;
        try {
            changeDetailService.updateChangeDetail(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询合同数据
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchContractName")
    @ResponseBody
    public Map<String, Object> searchContractName(@RequestBody HTProAlterDetailVO paramVO) {
        Map<String, Object> resultMap;
        try {
            resultMap = changeDetailService.excSearchContractInfo(paramVO);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 导出
     */
    @RequestMapping(value = "/export/exportChangeDetailData", method = RequestMethod.GET)
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
        List<Map<String, Object>> dataList = changeDetailService.getExportData(projectId);
        //对应属性
        Map<String, String> columnMap = new LinkedHashMap<>();
        columnMap.put("professional_name", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("alter_name", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("content_summary", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("talk_date", ExcelUtils.CELL_TYPE_DATE);
        columnMap.put("receive_date", ExcelUtils.CELL_TYPE_DATE);
        columnMap.put("sts", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("declare_amount", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("audit_amount", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("hantten_amount", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("owner_amount", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("add_sub_amount", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("symbol", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("remark", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("alter_analyse", ExcelUtils.CELL_TYPE_TEXT);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/ChangeDetail.xlsx");
        Workbook workbook = ExcelUtils.readExcel(inputStream);

        ExcelExportParam param = new ExcelExportParam();
        param.setSheetNum(0);
        param.setTitleRowNum(0);
        param.setTitleValue(projectVO.getProjectNum() + "项目合同变更洽商签证台帐");
        param.setTimeRowNum(1);
        param.setDataRowNum(4);
        param.setDataLineHeight((short) 400);
        param.setStartColumn(1);
        param.setSubTitleStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
        param.setContentStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));


        Workbook dataBook = ExcelUtils.writeContent(workbook, columnMap, dataList, param);
        ExcelUtils.createChangeDetailExcel(dataBook);
        WorkBookUtils.write(workbook, response);
        return null;
    }

}
