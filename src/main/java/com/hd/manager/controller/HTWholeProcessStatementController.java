package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.*;
import com.hd.export.bean.ExcelExportParam;
import com.hd.export.style.ExcelCellStyle;
import com.hd.manager.service.HTProjectService;
import com.hd.manager.service.HTWholeProcessStatementService;
import com.hd.manager.vo.HTProStatementVO;
import com.hd.manager.vo.HTProjectVO;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/wholeProcess/statement")
public class HTWholeProcessStatementController extends BaseController {

    @Resource
    private HTWholeProcessStatementService statementService;

    @Resource
    HTProjectService htProjectService;

    /**
     * 查询列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchWholeProcessStatement")
    @ResponseBody
    public Map<String, Object> searchWholeProcessStatement(@RequestBody HTProStatementVO paramVO) {
        Map<String, Object> resultMap;
        try {
            Map<String, Object> retMap = statementService.excSearchWholeProcessStatement(paramVO);
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
    @RequestMapping("/save/saveStatement")
    @ResponseBody
    public Map<String, Object> saveStatement(@RequestBody HTProStatementVO paramVO) {
        Map<String, Object> resultMap;
        try {
            statementService.saveStatement(paramVO);
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
    @RequestMapping("/modify/updateStatement")
    @ResponseBody
    public Map<String, Object> updateStatement(@RequestBody HTProStatementVO paramVO) {
        Map<String, Object> resultMap;
        try {
            statementService.updateStatement(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 导出
     */
    @RequestMapping(value = "/export/exportStatementData", method = RequestMethod.GET)
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
        List<Map<String, Object>> dataList = statementService.getExportData(projectId);
        //对应属性
        Map<String, String> columnMap = new LinkedHashMap<>();
        columnMap.put("serial_num", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("contract_num", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("contract_name", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("contract_unit", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("plan_date", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("contract_class", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("target_cost", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("excise_money", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("tax_ratio", ExcelUtils.CELL_TYPE_TEXT);
        columnMap.put("tax_money", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("with_tax_money", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("statement_amount", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("drawing_scheme", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("alter_discuss", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("visa", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("adjust_price", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("surge_price", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("service_fee", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("tax_price", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("other_thing", ExcelUtils.CELL_TYPE_NUMBIC);
        columnMap.put("remark", ExcelUtils.CELL_TYPE_TEXT);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/ClostAccountStandingBook.xlsx");
        Workbook workbook = ExcelUtils.readExcel(inputStream);
        ExcelExportParam param = new ExcelExportParam();
        param.setSheetNum(0);
        param.setTitleRowNum(0);
        param.setTitleValue(projectVO.getProjectNum() + "项目结算台账");
        param.setTimeRowNum(1);
        param.setDataRowNum(4);
        param.setDataLineHeight((short) 400);
        param.setStartColumn(0);
        param.setSubTitleStyle(ExcelCellStyle.getSubTitileBlodStyle(workbook, IndexedColors.GREY_25_PERCENT, "宋体", (short) 10, HorizontalAlignment.LEFT, VerticalAlignment.CENTER));
        param.setContentStyle(ExcelCellStyle.getStyle(workbook, "宋体", (short) 10, HorizontalAlignment.LEFT, VerticalAlignment.CENTER));
        ExcelUtils.writeContent(workbook,columnMap,dataList,param);
        WorkBookUtils.write(workbook, response);

        return null;
    }

}
