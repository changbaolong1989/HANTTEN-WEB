package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTCooperationCompanyService;
import com.hd.manager.vo.HTCooperationCompanyVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 设置-合作公司管理Controller
 *
 * @author jwl
 * Created in 2019/7/3 15:21
 */
@Controller
@RequestMapping("/config/cooperationCompany")
@CrossOrigin(allowCredentials = "true")
public class HTCooperationCompanyController extends BaseController {

    /**
     * 合作公司service 业务层资源
     */
    @Resource
    private HTCooperationCompanyService htCooperationCompanyService;

    /**
     * 查询合作公司列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchCooperationCompanyList")
    @ResponseBody
    public Map<String, Object> searchCooperationCompanyList(@RequestBody HTCooperationCompanyVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询合作公司列表数据和总条数
            Map<String, Object> retMap = htCooperationCompanyService.excSearchCooperationCompanyList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回合作公司列表数据
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加合作公司信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/saveCooperationCompanyInfo")
    @ResponseBody
    public Map<String, Object> saveCooperationCompanyInfo(HttpSession session, @RequestBody HTCooperationCompanyVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加合作公司信息
            htCooperationCompanyService.excSaveCooperationCompanyInfo(paramVO);

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
     * 查询合作公司详情信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchCooperationCompanyInfo")
    @ResponseBody
    public Map<String, Object> searchCooperationCompanyInfo(@RequestBody HTCooperationCompanyVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询合作公司详情信息
            HTCooperationCompanyVO htCooperationCompanyVO = htCooperationCompanyService.excSearchCooperationCompanyInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回合作公司详情信息
            resultMap.put(Constant.DATA_VO_KEY, htCooperationCompanyVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 更改合作公司信息
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateCooperationCompanyInfo")
    @ResponseBody
    public Map<String, Object> updateCooperationCompanyInfo(HttpSession session, @RequestBody HTCooperationCompanyVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 更改合作公司信息
            htCooperationCompanyService.excUpdateCooperationCompanyInfo(paramVO);

            // MSG_INFO_COMMON_0001 检索成功！
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
