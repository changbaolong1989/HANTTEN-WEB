package com.hd.manager.controller;


import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.export.bean.ExcelData;
import com.hd.export.bean.ExportData;
import com.hd.export.service.impl.ExcelExport;

import com.hd.manager.service.HTWorkService;
import com.hd.manager.vo.HTWorkVO;
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
import java.util.stream.IntStream;

import java.text.*;
import java.util.Calendar;
import java.util.Date;


/**
 * 代办Controller
 *
 * @author lihai
 * Created in 2019/7/3 11:45
 **/


@Controller
@RequestMapping("/work")
@CrossOrigin(allowCredentials = "true")
public class HTWorkController extends BaseController {

    @Resource
    HTWorkService htWorkService;

    @Resource
    private ExcelExport excelExport;

    /**
     * 查询待办列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchPendingList")
    public Map<String, Object> searchPendingList(@RequestBody HTWorkVO paramVO, HttpSession session) {

        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
//            resultMap.putAll(htWorkService.excSearchPendingList(paramVO));
            resultMap.putAll(htWorkService.excSearchNewPendingList2(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询已办列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchCompletedList")
    public Map<String, Object> searchAlreadyDoneList(@RequestBody HTWorkVO paramVO, HttpSession session) throws LogicException,
            SystemException {

        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htWorkService.excSearchNewCompletedList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询经手列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchHandleList")
    public Map<String, Object> searchHandleList(@RequestBody HTWorkVO paramVO, HttpSession session) throws LogicException,
            SystemException {

        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htWorkService.excSearchHandleList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 查询所有业务线（部门）列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchAllDepartmentList")
    public Map<String, Object> searchAllDepartmentList(@RequestBody HTWorkVO paramVO, HttpSession session) {

        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htWorkService.excSearchAllDepartmentList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 批量通过业务
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/update/batchPassCheckBusinessList")
    public Map<String, Object> batchPassCheckBusinessList(@RequestBody List<HTWorkVO> paramVOList, HttpSession session) {

        Map<String, Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            HTWorkVO paramVO = new HTWorkVO();
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWorkService.excBatchPassCheckBusinessList(paramVO, paramVOList);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 导出周报
     *
     * @return 请求参数
     */
    @RequestMapping(value = "/export/exportWeekInfo", method = RequestMethod.GET)
    public String exportWeek(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        String realFileName = request.getParameter("realFileName");
        String startTime = (String) request.getParameter("startTime");
        String endTime = (String) request.getParameter("endTime");

        response.reset();
        // 解决中文显示不出来的问题
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(realFileName, "UTF-8") + ";filename=" + URLEncoder.encode(realFileName, "UTF-8"));

        HTWorkVO htWorkVO = new HTWorkVO();
        htWorkVO = putLoginUserToVOFromSession(htWorkVO, session);
        String userId = htWorkVO.getLoginUserInfo().getLoginUserId();
        String userName = htWorkVO.getLoginUserInfo().getUserName();

        List<ExportData> exportDatas = new ArrayList<>();

        //创建表格实体
        ExcelData excelData = new ExcelData();
        //表格sheet页名称
        excelData.setSheetName(startTime + "~" + endTime);
        //表格单页标题
        excelData.setTitle("工作台账（" + startTime + "~" + endTime + "）");

        //固定表头
        String[] columnTxt = {"序号", "合同编号", "合同名称", "合作公司", "业务类型", "咨询类别", "任务名称", "送审金额（元）", "审定金额（元）", "审减金额（元）", "接收时间", "处理时间", "处理结果", "审核人", "审核时间"};
        //对应属性
        String[] columnNames = {"sortNum", "contractNum", "contractName", "cooperateCompany", "counselTypePeriod", "counselType", "tenderTaskName", "trialSum", "authorizedAmount", "reductionAmount", "startDate", "endDate", "isPass", "applyUserName", "duration"};
        //单元格宽高
        int[] columnWidth = {30 * 100, 60 * 100, 60 * 100, 60 * 100, 80 * 100, 100 * 100, 60 * 100, 60 * 100, 60 * 100, 60 * 100, 70 * 100, 70 * 100, 60 * 100, 60 * 100, 60 * 100};

        //填入表头
        excelData.setColumnTxt(columnTxt);
        //对应属性名称
        excelData.setColumnNames(columnNames);
        //填入单元格样式
        excelData.setColumnWidth(columnWidth);


        HTWorkVO paramVO = new HTWorkVO();
        // 设置登陆用户
        paramVO.setApplyUserId(userId);
        // 设置选择的开始时间
        paramVO.setStartDate(startTime);
        // 设置选择的结束时间
        paramVO.setEndDate(endTime);

        //根据选择的时间端，查询时间段内周报数据
        List<HTWorkVO> workVOList = htWorkService.excSearchWeekWorkList(paramVO);
        //格式转换
        List<Map<String, Object>> dataList = IntStream.range(0, workVOList.size())
                .mapToObj(i -> toWorkMap(workVOList.get(i), i, userName))
                .collect(Collectors.toList());
        //填入数据
        excelData.setDataList(dataList);

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
    private Map<String, Object> toWorkMap(HTWorkVO tempVO, Integer index, String userName) {
        Map<String, Object> map = new HashMap<>();
        /*----------------------将Object对象转换成Map<key,value>------------------------*/
        map.put("sortNum", tempVO.getSortNum());
        map.put("contractNum", tempVO.getContractNum());
        map.put("contractName", tempVO.getContractName());
        map.put("counselTypePeriod", tempVO.getCounselTypePeriod());
        map.put("counselType", tempVO.getCounselType());
        map.put("cooperateCompany", tempVO.getCooperateCompany());
        map.put("tenderTaskName", tempVO.getTenderTaskName());
        map.put("startDate", tempVO.getStartDate());
        map.put("endDate", tempVO.getEndDate());
        map.put("isPass", tempVO.getIsPass());
        map.put("applyUserName", userName);
        map.put("duration", tempVO.getDuration());
        /*-------------------------------------end--------------------------------------*/
        //返回Map
        return map;
    }

    private static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

}
