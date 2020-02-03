package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.*;
import com.hd.export.service.impl.ExcelExport;
import com.hd.manager.service.HTProjectService;
import com.hd.manager.service.HTWholeProcessClaimService;
import com.hd.manager.vo.HTProjectVO;
import com.hd.manager.vo.HTWholeProcessClaimVO;
import com.hd.manager.vo.HTWholeProcessContractVO;
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
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全过程(索赔台账)Controller
 *
 * @author LH
 * Created in 2019/9/11 14:29
 */
@Controller
@RequestMapping("/wholeProcess/claim")
@CrossOrigin(allowCredentials = "true")
public class HTWholeProcessClaimController extends BaseController {


    /**
     * 项目索赔台账阶段service
     */
    @Resource
    HTWholeProcessClaimService htWholeProcessClaimService;

    /**
     * 项目service
     */
    @Resource
    HTProjectService htProjectService;

    @Resource
    private ExcelExport excelExport;

    /**
     * 查询索赔台账列表
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectClaimPeriodList")
    public Map<String,Object> searchProjectClaimPeriodList(@RequestBody HTWholeProcessClaimVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.putAll(htWholeProcessClaimService.excSearchProjectClaimPeriodList(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 查询索赔台账详情
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchProjectClaimPeriodInfo")
    public Map<String,Object> searchProjectClaimPeriodInfo(@RequestBody HTWholeProcessClaimVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_VO_KEY,htWholeProcessClaimService.excSearchProjectClaimPeriodInfo(paramVO));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 新增项目索赔台账信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/insert/insertWholeProcessClaim")
    public Map<String,Object> insertWholeProcessClaim(@RequestBody HTWholeProcessClaimVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessClaimService.excInsertWholeProcessClaim(paramVO);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }


    /**
     * 修改项目索赔台账信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/modify/updateWholeProcessClaim")
    public Map<String,Object> updateWholeProcessClaim(@RequestBody HTWholeProcessClaimVO paramVO, HttpSession session){

        Map<String,Object> resultMap = null;
        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);
            htWholeProcessClaimService.excUpdateWholeProcessClaim(paramVO);
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 修改项目索赔台账信息
     *
     * @return 请求参数
     */
    @ResponseBody
    @RequestMapping("/query/searchContractList")
    public Map<String,Object> searchContractList( HttpSession session,@RequestBody HTWholeProcessContractVO paramVO){

        Map<String,Object> resultMap = null;
        try {
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
            resultMap.put(Constant.DATA_LIST_KEY,htWholeProcessClaimService.excSearchContractList(paramVO));
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
    @RequestMapping(value = "/export/exportClaimData", method = RequestMethod.GET)
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
        HTWholeProcessClaimVO paramVO = new HTWholeProcessClaimVO();
        paramVO.setProjectId(projectId);
        //查询估算数据
        List<HTWholeProcessClaimVO> wholeProcessClaimVOList = htWholeProcessClaimService.excSearchProjectClaimPeriodListForExport(paramVO);
        //序号
        String index = "";
        //格式转换
        List<Map<String, Object>> dataList = new ArrayList<>();
        this.ListChangeUtil(wholeProcessClaimVOList, dataList,index);
        //对应属性
        String[] columnNames = {"sortNum","contractNum","contractName","contractClass","withTaxMoney","declareAmount","hanttenAmount", "exciseMoney","confirmAmount","estimatedAmount","","remark"};
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/claim.xls");
        Workbook workbook = ExcelUtils.readExcel(inputStream);
        ExcelUtils.creatExcel(workbook,0,0,projectVO.getProjectNum()+"项目索赔台账",1,columnNames,dataList,4);

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
    private List<Map<String, Object>> ListChangeUtil(List<HTWholeProcessClaimVO>  wholeProcessClaimVOList,List<Map<String, Object>> dataList,String index) {
        //格式转换
        for (int i=0;i<wholeProcessClaimVOList.size();i++){
            boolean saveFlag = true;
            StringBuffer sortNum = new StringBuffer(index);
            if (wholeProcessClaimVOList.get(i).getCurrentLevel().equals(Constant.SECOND_LEVEL_MENU_FLAG)
                    &&!wholeProcessClaimVOList.get(i).getTreatyType().equals("1")
                    ||wholeProcessClaimVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                saveFlag = false;
            }
            if(saveFlag||wholeProcessClaimVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                Map<String, Object> resultMap = this.toClaimMap(wholeProcessClaimVOList.get(i),sortNum.append(i+1).toString());
                dataList.add(resultMap);
            }
            if (wholeProcessClaimVOList.get(i).getCurrentLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                sortNum = new StringBuffer("");
            }
            List<HTWholeProcessClaimVO> childList = wholeProcessClaimVOList.get(i).getChildList();
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
    private Map<String, Object> toClaimMap(HTWholeProcessClaimVO tempVO,String index) {
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
        map.put("contractNum", StringToolUtils.isNull(tempVO.getContractNum())?"":tempVO.getContractNum());
        //招标名称
        map.put("contractName", StringToolUtils.isNull(tempVO.getContractName())?"":tempVO.getContractName());
        if (!StringToolUtils.isNull(tempVO.getUndertakeTypeName())){
            map.put("contractName", tempVO.getUndertakeTypeName());
        }
        //合同类型
        map.put("contractClass", StringToolUtils.isNull(tempVO.getContractClass())?"":tempVO.getContractClass());
        //合同签订金额
        map.put("withTaxMoney", StringToolUtils.isNull(tempVO.getWithTaxMoney())?"":tempVO.getWithTaxMoney());
        //申报金额
        map.put("declareAmount", StringToolUtils.isNull(tempVO.getDeclareAmount())?"0":tempVO.getDeclareAmount());
        //汉腾审核金额
        map.put("hanttenAmount", StringToolUtils.isNull(tempVO.getHanttenAmount())?"0":tempVO.getHanttenAmount());
        Double hanttenAmount = StringToolUtils.isNull(tempVO.getHanttenAmount())?0:Double.parseDouble(tempVO.getHanttenAmount());
        Double withTaxMoney = StringToolUtils.isNull(tempVO.getWithTaxMoney())?1:tempVO.getWithTaxMoney().equals("0")?1:tempVO.getWithTaxMoney().equals("0.0")?1:tempVO.getWithTaxMoney().equals("")?1:Double.parseDouble(tempVO.getWithTaxMoney());
        //汉腾审核金额（%)
        map.put("exciseMoney", this.getNumberStr(String.valueOf((hanttenAmount/withTaxMoney)*100))+"%");
        //已确认(元)
        map.put("confirmAmount", StringToolUtils.isNull(tempVO.getConfirmAmount())?"0":tempVO.getConfirmAmount());
        //预估金额(元)
        map.put("estimatedAmount", StringToolUtils.isNull(tempVO.getEstimatedAmount())?"0":tempVO.getEstimatedAmount());
        //空白
        map.put("", "");
        //备注
        map.put("remark", StringToolUtils.isNull(tempVO.getRemark())?"":tempVO.getRemark());
        /*-------------------------------------end--------------------------------------*/
        //返回Map
        return map;
    }



    /**
     * 将科学计数法的字符串传入
     * @param str
     * @return 返回String类型
     */
    private String getNumberStr(String str){
        String result = "";
        if (!StringToolUtils.isNull(str)){
            BigDecimal bd = new BigDecimal(str);
            result = bd.toPlainString();
        }
        return result;
    }


}
