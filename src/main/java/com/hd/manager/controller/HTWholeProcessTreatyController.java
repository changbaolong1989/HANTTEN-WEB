package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.*;
import com.hd.export.bean.ExcelData;
import com.hd.export.bean.ExportData;
import com.hd.export.service.impl.ExcelExport;
import com.hd.manager.service.HTProjectService;
import com.hd.manager.service.HTWholeProcessTreatyService;
import com.hd.manager.vo.HTProjectVO;
import com.hd.manager.vo.HTWholeProcessTreatyVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全过程(合约规划)Controller
 *
 * @author LH
 * Created in 2019/9/3 14:29
 */
@Controller
@RequestMapping("/wholeProcess/treaty")
@CrossOrigin(allowCredentials = "true")
public class HTWholeProcessTreatyController extends BaseController {


    /**
     * 项目合约规划阶段service
     */
    @Resource
    HTWholeProcessTreatyService htWholeProcessTreatyService;

    /**
     * 项目service
     */
    @Resource
    HTProjectService htProjectService;

    @Resource
    private ExcelExport excelExport;

    /**
     * 查询合约规划列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectTreatyPeriodList")
    public Map<String,Object> searchProjectTreatyPeriodList(@RequestBody HTWholeProcessTreatyVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htWholeProcessTreatyService.excSearchProjectTreatyPeriodList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询合约规划详情
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectTreatyPeriodInfo")
    public Map<String,Object> searchProjectTreatyPeriodInfo(@RequestBody HTWholeProcessTreatyVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_VO_KEY,htWholeProcessTreatyService.excSearchProjectTreatyPeriodInfo(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 新增项目合约规划信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/insert/insertWholeProcessTreaty")
    public Map<String,Object> insertWholeProcessTreaty(@RequestBody HTWholeProcessTreatyVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessTreatyService.excInsertWholeProcessTreaty(paramVO);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 修改项目合约规划信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/modify/updateWholeProcessTreaty")
    public Map<String,Object> updateWholeProcessTreaty(@RequestBody HTWholeProcessTreatyVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessTreatyService.excUpdateWholeProcessTreaty(paramVO);
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
    @RequestMapping(value = "/export/exportTreatyData", method = RequestMethod.GET)
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
        HTWholeProcessTreatyVO paramVO = new HTWholeProcessTreatyVO();
        paramVO.setProjectId(projectId);
        //查询估算数据
        List<HTWholeProcessTreatyVO> wholeProcessTreatyVOList = htWholeProcessTreatyService.excSearchProjectTreatyPeriodListForExport(paramVO);
        //序号
        String index = "";
        //格式转换
        List<Map<String, Object>> dataList = new ArrayList<>();
        this.ListChangeUtil(wholeProcessTreatyVOList, dataList,index);
        //对应属性
        String[] columnNames = {"sortNum","treatyNum","treatyName","treatyContent","targetCost","contractType","contractWay", "purchaserWay","remark"};
        //单元格宽高
        int[] columnWidth = {50 * 100,80 * 100, 100 * 100, 60 * 100, 60 * 100, 80 * 100, 60 * 100, 60 * 100, 60 * 100, 60 * 100};
        //创建表格实体
        ExcelData excelData = new ExcelData();
        List<ExportData> exportDatas = new ArrayList<>();
        exportDatas.add(excelData);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/treaty.xls");
        Workbook workbook = ExcelUtils.readExcel(inputStream);
        ExcelUtils.creatExcel(workbook,0,0,projectVO.getProjectNum()+"项目合约规划",1,columnNames,dataList,3);

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
    private List<Map<String, Object>> ListChangeUtil(List<HTWholeProcessTreatyVO>  wholeProcessTreatyVOList,List<Map<String, Object>> dataList,String index) {
        //格式转换
        for (int i=0;i<wholeProcessTreatyVOList.size();i++){
            boolean saveFlag = true;
            StringBuffer sortNum = new StringBuffer(index);
            if (wholeProcessTreatyVOList.get(i).getCurrentLevel().equals(Constant.SECOND_LEVEL_MENU_FLAG)
                    &&!wholeProcessTreatyVOList.get(i).getTreatyType().equals("1")
                    ||wholeProcessTreatyVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                saveFlag = false;
            }
            if(saveFlag||wholeProcessTreatyVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                Map<String, Object> resultMap = this.toTreatyMap(wholeProcessTreatyVOList.get(i),sortNum.append(i+1).toString());
                dataList.add(resultMap);
            }
            if (wholeProcessTreatyVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                sortNum = new StringBuffer("");
            }
            List<HTWholeProcessTreatyVO> childList = wholeProcessTreatyVOList.get(i).getChildList();
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
    private Map<String, Object> toTreatyMap(HTWholeProcessTreatyVO tempVO,String index) {
        Map<String, Object> map = new HashMap<>();
        /*----------------------将Object对象转换成Map<key,value>------------------------*/
        map.put("sortNum", index);
        if (tempVO.getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
            map.put(ExcelUtils.SUB_LEVEL_CELL_TYPE, ExcelUtils.SUB_LEVEL_CELL_TYPE_TEXT);
            map.put(ExcelUtils.SUB_LEVEL_CELL_TEXT,tempVO.getTreatyTypeName());
        }
        map.put("treatyNum", StringToolUtils.isNull(tempVO.getTreatyNum())?"":tempVO.getTreatyNum());
        map.put("treatyName", StringToolUtils.isNull(tempVO.getTreatyName())?"":tempVO.getTreatyName());
        if (!StringToolUtils.isNull(tempVO.getUndertakeTypeName())){
            map.put("treatyName", tempVO.getUndertakeTypeName());
        }
        map.put("treatyContent", StringToolUtils.isNull(tempVO.getTreatyContent())?"":tempVO.getTreatyContent());
        map.put("targetCost", StringToolUtils.isNull(tempVO.getTargetCost())?"":tempVO.getTargetCost());
        map.put("contractType", StringToolUtils.isNull(tempVO.getContractType())?"":tempVO.getContractType());
        map.put("contractWay", StringToolUtils.isNull(tempVO.getContractWay())?"":tempVO.getContractWay());
        map.put("purchaserWay", StringToolUtils.isNull(tempVO.getPurchaserWay())?"":tempVO.getPurchaserWay());
        map.put("remark", StringToolUtils.isNull(tempVO.getRemark())?"":tempVO.getRemark());
        /*-------------------------------------end--------------------------------------*/
        //返回Map
        return map;
    }
}
