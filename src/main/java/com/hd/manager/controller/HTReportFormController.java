package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.export.bean.ExcelData;
import com.hd.export.bean.ExportData;
import com.hd.export.service.impl.ExcelExport;
import com.hd.manager.dao.HTReportFormDao;
import com.hd.manager.dao.bean.HTAchievementReportFormBean;
import com.hd.manager.dao.bean.HTUserQuestionRecordRelationBean;
import com.hd.manager.service.HTReportFormService;
import com.hd.manager.vo.HTAchievementReportFormVO;
import com.hd.manager.vo.HTSyntheticalReportFormVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


/**
 * 报表Controller
 *
 * @author LH
 * Created in 2019/8/8 16:15
 */
@Controller
@RequestMapping("/reportForm")
@CrossOrigin(allowCredentials = "true")
public class HTReportFormController extends BaseController {


    /**
     * 角色Service对象
     */
    @Resource
    private HTReportFormService htReportFormService;

    @Resource
    private HTReportFormDao htReportFormDao;

    @Resource
    private ExcelExport excelExport;

    /**
     * 查询绩效报表
     */
    @ResponseBody
    @RequestMapping("/query/searchUserAchievementsReportForm")
    public Map<String, Object> searchUserAchievementsReportForm(@RequestBody HTAchievementReportFormVO paramVO, HttpSession session) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            Map<String, Object> result = htReportFormService.excSearchUserAchievementsReportForm(paramVO);
            resultMap.putAll(result);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 成员扣分记录列表信息查询
     */
    @ResponseBody
    @RequestMapping("/query/searchHTUserQuestionRecordRelationList")
    public Map<String, Object> searchHTUserQuestionRecordRelationList(@RequestBody HTAchievementReportFormVO paramVO, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            Map<String, Object> result = htReportFormService.excSearchHTUserQuestionRecordRelationList(paramVO);
            resultMap.putAll(result);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 综合统计记录列表信息
     */
    @ResponseBody
    @RequestMapping("/query/searchSyntheticalReportFormList")
    public Map<String, Object> searchSyntheticalReportFormList(@RequestBody HTSyntheticalReportFormVO paramVO, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            Map<String, Object> result = htReportFormService.excSearchSyntheticalReportFormList(paramVO);
            resultMap.putAll(result);
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001)));
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
    @RequestMapping(value = "/exprot/exportPerformanceReport", method = RequestMethod.GET)
    public String exportPerformanceReport(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String realFileName = request.getParameter("realFileName");
        response.reset();
        // 解决中文显示不出来的问题
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(realFileName, "UTF-8") + ";filename=" + URLEncoder.encode(realFileName, "UTF-8"));
        String month = request.getParameter("month");
        HTAchievementReportFormBean paramBean = new HTAchievementReportFormBean();
        paramBean.setSearchDate(month);
        List<HTAchievementReportFormBean> htAchievementReportFormBeanList = htReportFormDao.excSearchUserAchievementsReportForm(paramBean);
        List<Map<String, Object>> dataList = htAchievementReportFormBeanList.stream().map(this::toReportMap).collect(Collectors.toList());
        String[] columnTxt = {"姓名", "扣分合计", "扣分合同编撰数", "扣分任务数", "扣分请款单数", "个人签名"};
        String[] columnNames = {"username", "sumNum", "contractCountNum", "taskCountNum", "requestMenuCountNum", "signPath"};
        int[] columnWidth = {80 * 100, 100 * 100, 60 * 100, 60 * 100, 60 * 100, 80 * 100, 60 * 100, 30 * 100};
        ExcelData excelData = new ExcelData();
        excelData.setSheetName("绩效报表");
        excelData.setTitle("绩效报表");
        excelData.setColumnTxt(columnTxt);
        excelData.setColumnNames(columnNames);
        excelData.setColumnWidth(columnWidth);
        excelData.setDataList(dataList);
        List<ExportData> exportDatas = new ArrayList<>();
        exportDatas.add(excelData);
        for(HTAchievementReportFormBean bean : htAchievementReportFormBeanList){
            String userId = bean.getUserId();
            String userName = bean.getUserName();
            HTAchievementReportFormBean param = new HTAchievementReportFormBean();
            param.setSearchDate(month);
            param.setUserId(userId);
            List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList = htReportFormDao.excSearchHTUserQuestionRecordRelationList(param);
            ExcelData ed = new ExcelData();
            ed.setSheetName(userName);
            ed.setTitle(userName+"绩效报表");
            String[] colTxt = {"合同编号", "扣分数据", "审核时间", "审核人", "错误描述", "错误数", "扣分值", "个人签名"};
            String[] names = {"businessNum", "businessName", "approveTime", "approveUserName", "questionName", "questionNum", "grade", "signPath"};
            ed.setColumnTxt(colTxt);
            ed.setColumnNames(names);
            ed.setColumnWidth(columnWidth);
            List<Map<String, Object>> data = htUserQuestionRecordRelationBeanList.stream().map(this::toQuestionMap).collect(Collectors.toList());
            ed.setDataList(data);
            exportDatas.add(ed);
        }
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

    private Map<String, Object> toReportMap(HTAchievementReportFormBean bean) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", bean.getUserName());
        String sumNum = bean.getSumNum();
        if (sumNum == null) {
            sumNum = "-";
        } else {
            sumNum = Integer.parseInt(bean.getSumNum()) >= 100 ? "100%" : bean.getSumNum() + "%";
        }
        map.put("sumNum", sumNum);
        map.put("contractCountNum", bean.getContractCountNum());
        map.put("taskCountNum", bean.getTaskCountNum());
        map.put("requestMenuCountNum", bean.getRequestMenuCountNum());
        map.put("signPath", bean.getSignPath());
        return map;
    }

    private Map<String, Object> toQuestionMap(HTUserQuestionRecordRelationBean bean) {
        Map<String, Object> map = new HashMap<>();
        map.put("businessNum", bean.getBusinessNum());
        map.put("businessName", bean.getBusinessName());
        map.put("approveTime", DateToolUtils.convertDateFormat(bean.getApproveTime(),DateToolUtils.yyyy_MM_dd));
        map.put("approveUserName", bean.getApproveUserName());
        map.put("questionName", bean.getQuestionName());
        map.put("questionNum", bean.getQuestionNum());
        map.put("grade", bean.getGrade() == null ? "-" : Integer.parseInt(bean.getQuestionNum()) * Integer.parseInt(bean.getGrade()) + "%");
        map.put("signPath", bean.getSignPath());
        return map;
    }

}
