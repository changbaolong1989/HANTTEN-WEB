package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTUserService;
import com.hd.manager.vo.HTUserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 设置-员工管理Controller
 *
 * @author zjy
 * Created in 2019/7/1 16:56
 */
@Controller
@RequestMapping("/config/user")
@CrossOrigin(allowCredentials ="true")
public class HTUserController extends BaseController {


    /**
     * 用户service 业务层资源
     */
    @Resource
    private HTUserService htUserService;

    /**
     * 查询公司和合作公司的信息下拉列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchCompanyList")
    @ResponseBody
    public Map<String, Object> searchCompanyList() {

        Map<String, Object> resultMap = null;

        try {

            // 查询公司和合作公司的信息下拉列表
            List<HTUserVO> companyList = htUserService.excSearchCompanyList();

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap =(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));

            // 返回公司和合作公司信息
            resultMap.put(Constant.DATA_LIST_KEY, companyList);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询角色权限信息下拉列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchRoleList")
    @ResponseBody
    public Map<String, Object> searchRoleList() {

        Map<String, Object> resultMap = null;

        try {

            // 查询角色权限信息下拉列表
            List<HTUserVO> roleList = htUserService.excSearchRoleList();

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap=(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));

            // 返回角色权限信息
            resultMap.put(Constant.DATA_LIST_KEY, roleList);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询专业信息下拉列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchProfessionalList")
    @ResponseBody
    public Map<String, Object> searchProfessionalList() {

        Map<String, Object> resultMap = null;

        try {

            // 查询专业信息下拉列表
            List<HTUserVO> professionalList = htUserService.excSearchProfessionalList();

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap=(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));

            // 返回专业信息
            resultMap.put(Constant.DATA_LIST_KEY, professionalList);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询文化程度信息下拉列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchEducationList")
    @ResponseBody
    public Map<String, Object> searchEducationList() {

        Map<String, Object> resultMap = null;

        try {

            // 查询文化程度信息下拉列表
            List<HTUserVO> educationList = htUserService.excSearchEducationList();

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap=(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));

            // 返回文化程度信息
            resultMap.put(Constant.DATA_LIST_KEY, educationList);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询部门下拉列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchDeptTree")
    @ResponseBody
    public Map<String, Object> searchDeptTree() {

        Map<String, Object> resultMap = null;

        try {

            // 查询部门信息下拉列表
            List<HTUserVO> deptTreeList = htUserService.excSearchDeptTree();

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = (setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));

            // 返回部门信息
            resultMap.put(Constant.DATA_LIST_KEY, deptTreeList);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 根据部门联动职位信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchJobListByDeptId")
    @ResponseBody
    public Map<String, Object> searchJobListByDeptId(@RequestBody HTUserVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 根据部门ID联动职位信息
            List<HTUserVO> jobList = htUserService.excSearchJobListByDeptId(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap=(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));

            // 返回职务信息
            resultMap.put(Constant.DATA_LIST_KEY, jobList);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 查询用户列表
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchUserList")
    @ResponseBody
    public Map<String, Object> searchUserList(@RequestBody HTUserVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询用户列表
            Map<String, Object> resultPageMap = htUserService.excSearchUserList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap=(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002)));

            // 返回部门信息
            resultMap.put(Constant.PAGEED_DATA_KEY, resultPageMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加用户信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/saveUserInfo")
    @ResponseBody
    public Map<String, Object> saveUserInfo(HttpSession session, HTUserVO paramVO, @RequestParam("resourceFiles") MultipartFile[] resourceFiles, @RequestParam(value = "signPathFile",required = false) MultipartFile signPathFile) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加用户信息
            htUserService.excSaveUserInfo(paramVO, resourceFiles, signPathFile);

            // MSG_INFO_COMMON_0001 提交成功！
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

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
     * 查询用户详情
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchUserInfo")
    @ResponseBody
    public Map<String, Object> searchUserInfo(@RequestBody HTUserVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 更改用户信息
            HTUserVO retUser = htUserService.excSearchUserInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap=setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回用户信息
            resultMap.put(Constant.DATA_VO_KEY, retUser);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 更改用户信息
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateUserInfo")
    @ResponseBody
    public Map<String, Object> updateUserInfo(HttpSession session, HTUserVO paramVO, @RequestParam("resourceFiles") MultipartFile[] resourceFiles, @RequestParam(value = "signPathFile",required = false) MultipartFile signPathFile) {

        Map<String, Object> resultMap = null;

        try {
            // 从请求中 取得 用户信息，并存放至参数VO中
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 更改用户信息
            htUserService.excUpdateUserInfo(session, paramVO, resourceFiles, signPathFile);

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


}
