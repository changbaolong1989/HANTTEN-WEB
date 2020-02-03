package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTCompanyService;
import com.hd.manager.vo.HTCompanyVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 设置-公司管理Controller
 *
 * @author jwl
 * Created in 2019/7/3 15:21
 */
@Controller
@RequestMapping("/config/company")
@CrossOrigin(allowCredentials = "true")
public class HTCompanyController extends BaseController {

    /**
     * 公司service 业务层资源
     */
    @Resource
    private HTCompanyService htCompanyService;

    /**
     * 查询公司列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchCompanyList")
    @ResponseBody
    public Map<String, Object> searchCompanyList(@RequestBody HTCompanyVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询公司列表数据和总条数
            Map<String, Object> retMap = htCompanyService.excSearchCompanyList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回公司列表数据
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加公司信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/saveCompanyInfo")
    @ResponseBody
    public Map<String, Object> saveCompanyInfo(HttpSession session, @RequestBody HTCompanyVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加公司信息
            htCompanyService.excSaveCompanyInfo(paramVO);

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
     * 查询公司详情信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchCompanyInfo")
    @ResponseBody
    public Map<String, Object> searchCompanyInfo(@RequestBody HTCompanyVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询公司详情信息
            HTCompanyVO htCompanyVO = htCompanyService.excSearchCompanyInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回公司详情信息
            resultMap.put(Constant.DATA_VO_KEY, htCompanyVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 更改公司信息
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateCompanyInfo")
    @ResponseBody
    public Map<String, Object> updateCompanyInfo(HttpSession session, @RequestBody HTCompanyVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 更改公司信息
            htCompanyService.excUpdateCompanyInfo(paramVO);

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
