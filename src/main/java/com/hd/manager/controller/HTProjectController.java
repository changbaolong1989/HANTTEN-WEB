package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.export.bean.ExcelData;
import com.hd.export.bean.ExportData;
import com.hd.export.service.impl.ExcelExport;
import com.hd.manager.service.HTProjectService;
import com.hd.manager.vo.ElementTreeVO;
import com.hd.manager.vo.HTContractVO;
import com.hd.manager.vo.HTDictProjectCounselTypeVO;
import com.hd.manager.vo.HTProjectVO;
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

/**
 * 项目管理Controller
 *
 * @author jwl
 * Created in 2019/7/9 15:21
 */
@Controller
@RequestMapping("/project")
@CrossOrigin(allowCredentials = "true")
public class HTProjectController extends BaseController {

    /**
     * 项目service 业务层资源
     */
    @Resource
    private HTProjectService htProjectService;

    /**
     * 导出excel
     */
    @Resource
    private ExcelExport excelExport;

    /**
     * 查询已收入汇总和合同金额汇总
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchIncomeReceivedSumAndContractAmountSum")
    @ResponseBody
    public Map<String, Object> searchIncomeReceivedSumAndContractAmountSum(HttpSession session, @RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询已收入汇总和合同金额汇总
            HTProjectVO htProjectVO = htProjectService.excSearchIncomeReceivedSumAndContractAmountSum(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回公司缩写下拉列表
            resultMap.put(Constant.DATA_VO_KEY, htProjectVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询公司缩写下拉列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchCondenseList")
    @ResponseBody
    public Map<String, Object> searchCondenseList() {

        Map<String, Object> resultMap = null;

        try {

            // 查询公司缩写下拉列表
            List<HTProjectVO> htProjectVO = htProjectService.excSearchCondenseList();

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回公司缩写下拉列表
            resultMap.put(Constant.DATA_LIST_KEY, htProjectVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询项目编号
     *
     * @return 请求参数
     */
    @RequestMapping("/query/excSearchMaxProjectNum")
    @ResponseBody
    public Map<String, Object> excSearchMaxProjectNum() {

        Map<String, Object> resultMap = null;

        try {

            // 查询项目编号
            HTProjectVO htProjectVO = htProjectService.excSearchMaxProjectNum();

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回项目编号
            resultMap.put(Constant.DATA_VO_KEY, htProjectVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加项目信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/saveProjectInfo")
    @ResponseBody
    public Map<String, Object> saveProjectInfo(HttpSession session, @RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加项目信息
            htProjectService.excSaveProjectInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询项目列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchProjectList")
    @ResponseBody
    public Map<String, Object> searchProjectList(HttpSession session,@RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询查询项目列表数据
            Map<String, Object> retMap = htProjectService.excSearchProjectList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回项目列表数据
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 通过该项目的id查询项目下的合同（树形）
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchProjectTree")
    @ResponseBody
    public Map<String, Object> searchProjectTree(HttpSession session,@RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 查询查询项目树
            ElementTreeVO retVO = htProjectService.excSearchProjectTree(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回项目树
            resultMap.put(Constant.DATA_VO_KEY, retVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询项目详情
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchProjectInfo")
    @ResponseBody
    public Map<String, Object> searchProjectInfo(@RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询查询项目详情
            HTProjectVO retVO = htProjectService.excSearchProjectInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回项目详情
            resultMap.put(Constant.DATA_VO_KEY, retVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 修改项目信息
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateProjectInfo")
    @ResponseBody
    public Map<String, Object> updateProjectInfo(HttpSession session, @RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 修改项目信息
            htProjectService.excUpdateProjectInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 删除项目信息
     *
     * @return 请求参数
     */
    @RequestMapping("/delete/deleteProjectInfo")
    @ResponseBody
    public Map<String, Object> deleteProjectInfo(HttpSession session, @RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 删除项目信息
            htProjectService.excDeleteProjectInfo(paramVO);

            // MSG_ERROR_COMMON_0008 删除成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_ERROR_COMMON_0008));

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加合同信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/saveContractInfo")
    @ResponseBody
    public Map<String, Object> saveContractInfo(HttpSession session, @RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加合同信息
            String contractId = htProjectService.excSaveContractInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

            // 合同id
            resultMap.put(Constant.DATA_PRIMARY_KEY,contractId);

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 修改合同信息
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateContractInfo")
    @ResponseBody
    public Map<String, Object> updateContractInfo(HttpSession session, @RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 修改合同信息
            htProjectService.excUpdateContractInfo(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 删除合同信息
     *
     * @return 请求参数
     */
    @RequestMapping("/delete/deleteContractInfo")
    @ResponseBody
    public Map<String, Object> deleteContractInfo(HttpSession session, @RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 删除合同信息
            htProjectService.excDeleteContractInfo(paramVO);

            // MSG_ERROR_COMMON_0008 删除成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_ERROR_COMMON_0008));

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 通过项目id和部门id查询合同数
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchContractCountByProjectIdAndDepartmentId")
    @ResponseBody
    public Map<String, Object> searchContractCountByProjectIdAndDepartmentId(@RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 通过项目id和部门id查询合同数
            HTProjectVO htProjectVO = htProjectService.excSearchContractCountByProjectIdAndDepartmentId(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回项目编号
            resultMap.put(Constant.DATA_VO_KEY, htProjectVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询合同信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/excSearchContractInfo")
    @ResponseBody
    public Map<String, Object> excSearchContractInfo(@RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询合同信息
            HTProjectVO htProjectVO = htProjectService.excSearchContractInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回合同信息
            resultMap.put(Constant.DATA_VO_KEY, htProjectVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }


    /**
     * 关闭项目
     *
     * @return 请求参数
     */
    @RequestMapping("/close/closeProject")
    @ResponseBody
    public Map<String, Object> closeProject(HttpSession session, @RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 关闭项目
            htProjectService.excCloseProject(paramVO);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 取得合同关联部门下的人员下拉列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/excSearchPersonListByInvolvedDepartment")
    @ResponseBody
    public Map<String, Object> excSearchPersonListByInvolvedDepartment(@RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 取得合同关联部门下的人员下拉列表
            List<HTProjectVO> htProjectVO = htProjectService.excSearchPersonListByInvolvedDepartment(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回部门人员下拉列表
            resultMap.put(Constant.DATA_LIST_KEY, htProjectVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 分配合同编撰人
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateContractUserInfo")
    @ResponseBody
    public Map<String, Object> updateContractUserInfo(HttpSession session, @RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {


            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 分配合同编撰人
            htProjectService.excUpdateContractUserInfo(paramVO);

            // MSG_ERROR_COMMON_0008 删除成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询合同编撰人
     *
     * @return 请求参数
     */
    @RequestMapping("/query/queryContractWriter")
    @ResponseBody
    public Map<String, Object> queryContractWriter(HttpSession session, @RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询合同编撰人
            HTContractVO contractVO = htProjectService.queryContractWriter(paramVO.getContractId());


            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回部门人员下拉列表
            resultMap.put(Constant.DATA_VO_KEY, contractVO);

        } catch (LogicException lex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询参与人员信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchParticipantsInfoList")
    @ResponseBody
    public Map<String, Object> searchParticipantsInfoList(@RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = new HashMap<>();

        try {

            // 查询参与人员信息
            resultMap.putAll(htProjectService.excSearchParticipantsInfoList(paramVO));

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap.putAll(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 导出参与人员信息
     *
     * @return 请求参数
     */
    @RequestMapping(value = "/export/excExportParticipantsInfoList", method = RequestMethod.GET)
    @ResponseBody
    public String excExportParticipantsInfoList(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String realFileName = "任务台账.xls";// 文件名称
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''"
                + URLEncoder.encode(realFileName, "UTF-8") + ";filename="
                + URLEncoder.encode(realFileName, "UTF-8"));// 解决中文显示不出来的问题
        String projectId = request.getParameter("projectId");//获取参数projectId值
        HTProjectVO paramVO = new HTProjectVO();
        paramVO.setProjectId(projectId);// 设置项目id
        List<HTProjectVO> htProjectVOList = htProjectService.excExportParticipantsInfoList(paramVO);//查询任务台账
        List<Map<String, Object>> dataList = new ArrayList<>();
        for(HTProjectVO tempVO:htProjectVOList){//格式转换 object->map
            Map<String, Object> map = new HashMap<>();
            map.put("contractNum", tempVO.getContractNum());
            map.put("taskName", tempVO.getTaskName());
            map.put("jobType", tempVO.getJobType());
            map.put("userName", tempVO.getUserName());
            map.put("userType", tempVO.getUserType());
            dataList.add(map);
        }
        //固定表头
        String[] columnTxt = {"合同编号", "任务名", "工作类型","姓名","人员身份"};
        //对应属性
        String[] columnNames = {"contractNum", "taskName", "jobType", "userName", "userType"};
        //单元格宽高
        int[] columnWidth = {60 * 100,60 * 100, 60 * 100, 60 * 100, 60 * 100};

        ExcelData excelData = new ExcelData();//创建表格实体
        excelData.setSheetName("任务台账");//表格sheet页名称
        excelData.setTitle("任务台账");//表格单页标题
        excelData.setColumnTxt(columnTxt); //填入表头
        excelData.setColumnNames(columnNames);//对应属性名称
        excelData.setColumnWidth(columnWidth); //填入单元格样式
        excelData.setDataList(dataList);//填入数据
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
     * 查询咨询类别下拉列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchCounselTypePeriodList")
    @ResponseBody
    public Map<String, Object> searchCounselTypePeriodList() {

        Map<String, Object> resultMap = null;

        try {

            // 查询咨询类别下拉列表
            List<HTDictProjectCounselTypeVO> HTDictProjectCounselTypeList = htProjectService.excSearchCounselTypePeriodList();

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回部门人员下拉列表
            resultMap.put(Constant.DATA_LIST_KEY, HTDictProjectCounselTypeList);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询部门人员下拉列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/excSearchPersonList")
    @ResponseBody
    public Map<String, Object> excSearchPersonList() {

        Map<String, Object> resultMap = null;

        try {

            // 查询部门人员下拉列表
            List<HTProjectVO> htProjectVO = htProjectService.excSearchPersonList();

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回部门人员下拉列表
            resultMap.put(Constant.DATA_LIST_KEY, htProjectVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询成果文件台账列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/excSearchResultFileLedgerInfoList")
    @ResponseBody
    public Map<String, Object> excSearchResultFileLedgerInfoList(@RequestBody HTProjectVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 查询成果文件台账列表
            resultMap.putAll(htProjectService.excSearchResultFileLedgerInfoList(paramVO));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 导出成果文件台账列表
     *
     * @return 请求参数
     */
    @RequestMapping(value = "/export/excExportResultFileLedgerInfoList", method = RequestMethod.GET)
    @ResponseBody
    public String excExportResultFileLedgerInfoList(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String realFileName = "成果文件台账.xls";// 文件名称
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''"
                + URLEncoder.encode(realFileName, "UTF-8") + ";filename="
                + URLEncoder.encode(realFileName, "UTF-8"));// 解决中文显示不出来的问题
        String projectId = request.getParameter("projectId");//获取参数projectId值
        HTProjectVO paramVO = new HTProjectVO();
        paramVO.setProjectId(projectId);// 设置项目id
        List<HTProjectVO> htProjectVOList = htProjectService.excExportResultFileLedgerInfoList(paramVO);//查询成果文件台账列表
        List<Map<String, Object>> dataList = new ArrayList<>();
        for(HTProjectVO tempVO:htProjectVOList){//格式转换 object->map
            Map<String, Object> map = new HashMap<>();
            map.put("projectNum", tempVO.getProjectNum());
            map.put("projectName", tempVO.getProjectName());
            map.put("contractNum", tempVO.getContractNum());
            map.put("contractName", tempVO.getContractName());
            map.put("taskMaterialName", tempVO.getTaskMaterialName());
            map.put("reportNum", tempVO.getReportNum());
            map.put("userName", tempVO.getUserName());
            map.put("createDate", tempVO.getCreateDate());
            map.put("fileNumber", tempVO.getFileNumber());
            dataList.add(map);
        }
        //固定表头
        String[] columnTxt = {"项目编号","项目名称", "合同编号", "合同名称", "成果文件名称", "成果文件号","成果文件负责人","签发时间","归档编号"};
        //对应属性
        String[] columnNames = {"projectNum","projectName", "contractNum", "contractName", "taskMaterialName", "reportNum", "userName", "createDate", "fileNumber"};
        //单元格宽高
        int[] columnWidth = {60 * 100,60 * 100, 100 * 100, 60 * 100, 60 * 100, 60 * 100, 60 * 100, 60 * 100, 60 * 100};

        ExcelData excelData = new ExcelData();//创建表格实体
        excelData.setSheetName("成果文件台账");//表格sheet页名称
        excelData.setTitle("成果文件台账");//表格单页标题
        excelData.setColumnTxt(columnTxt); //填入表头
        excelData.setColumnNames(columnNames);//对应属性名称
        excelData.setColumnWidth(columnWidth); //填入单元格样式
        excelData.setDataList(dataList);//填入数据
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

}
