package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTTechnicalStandardService;
import com.hd.manager.vo.HTTechnicalStandardVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 设置-技术标准管理Controller
 *
 * @author jwl
 * Created in 2019/7/5 15:21
 */
@Controller
@RequestMapping("/config/technicalStandard")
@CrossOrigin(allowCredentials ="true")
public class HTTechnicalStandardController extends BaseController {

    /**
     * 技术标准service 业务层资源
     */
    @Resource
    private HTTechnicalStandardService htTechnicalStandardService;

    /**
     * 查询技术标准列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchTechnicalStandardList")
    @ResponseBody
    public Map<String, Object> searchTechnicalStandardList(@RequestBody HTTechnicalStandardVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询技术标准列表数据和总条数
            Map<String, Object> retMap = htTechnicalStandardService.excSearchTechnicalStandardList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回技术标准列表数据
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加技术标准信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/saveTechnicalStandardInfo")
    @ResponseBody
    public Map<String, Object> saveTechnicalStandardInfo(HttpSession session, HTTechnicalStandardVO paramVO, MultipartFile signPathFile) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加技术标准信息
            htTechnicalStandardService.excSaveTechnicalStandardInfo(paramVO,signPathFile);

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
     * 查询技术标准详情信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchTechnicalStandardInfo")
    @ResponseBody
    public Map<String, Object> searchTechnicalStandardInfo(@RequestBody HTTechnicalStandardVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询技术标准详情信息
            HTTechnicalStandardVO htTechnicalStandardVO = htTechnicalStandardService.excSearchTechnicalStandardInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回技术标准详情信息
            resultMap.put(Constant.DATA_VO_KEY, htTechnicalStandardVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 更改技术标准信息
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updateTechnicalStandardInfo")
    @ResponseBody
    public Map<String, Object> updateTechnicalStandardInfo(HttpSession session, HTTechnicalStandardVO paramVO, MultipartFile signPathFile) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 更改技术标准信息
            htTechnicalStandardService.excUpdateTechnicalStandardInfo(paramVO,signPathFile);

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
     * 删除技术标准信息
     *
     * @return 请求参数
     */
    @RequestMapping("/delete/deleteTechnicalStandardInfo")
    @ResponseBody
    public Map<String, Object> deleteTechnicalStandardInfo(HttpSession session, @RequestBody HTTechnicalStandardVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 删除技术标准信息
            htTechnicalStandardService.excDeleteTechnicalStandardInfo(paramVO);

            // MSG_ERROR_COMMON_0008 删除成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_ERROR_COMMON_0008));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

}
