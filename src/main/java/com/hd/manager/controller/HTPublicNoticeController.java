package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.service.HTPublicNoticeService;
import com.hd.manager.vo.HTPublicNoticeVO;
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
 * 设置-公告管理Controller
 *
 * @author jwl
 * Created in 2019/7/3 15:21
 */
@Controller
@RequestMapping("/config/publicNotice")
@CrossOrigin(allowCredentials ="true")
public class HTPublicNoticeController extends BaseController {

    /**
     * 公告service 业务层资源
     */
    @Resource
    private HTPublicNoticeService htPublicNoticeService;

    /**
     * 查询公告列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchPublicNoticeList")
    @ResponseBody
    public Map<String, Object> searchPublicNoticeList(@RequestBody HTPublicNoticeVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询公告列表数据和总条数
            Map<String, Object> retMap = htPublicNoticeService.excSearchPublicNoticeList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回公告列表数据
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 添加公告信息
     *
     * @return 请求参数
     */
    @RequestMapping("/add/savePublicNoticeInfo")
    @ResponseBody
    public Map<String, Object> savePublicNoticeInfo(HttpSession session, HTPublicNoticeVO paramVO, MultipartFile signPathFile) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 添加公告信息
            htPublicNoticeService.excSavePublicNoticeInfo(paramVO,signPathFile);

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
     * 查询公告详情信息
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchPublicNoticeInfo")
    @ResponseBody
    public Map<String, Object> searchPublicNoticeInfo(@RequestBody HTPublicNoticeVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询公告详情信息
            HTPublicNoticeVO htPublicNoticeVO = htPublicNoticeService.excSearchPublicNoticeInfo(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回公告详情信息
            resultMap.put(Constant.DATA_VO_KEY, htPublicNoticeVO);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

    /**
     * 更改公告信息
     *
     * @return 请求参数
     */
    @RequestMapping("/modify/updatePublicNoticeInfo")
    @ResponseBody
    public Map<String, Object> updatePublicNoticeInfo(HttpSession session, HTPublicNoticeVO paramVO, MultipartFile signPathFile) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 更改公告信息
            htPublicNoticeService.excUpdatePublicNoticeInfo(paramVO,signPathFile);

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
     * 删除公告信息
     *
     * @return 请求参数
     */
    @RequestMapping("/delete/deletePublicNoticeInfo")
    @ResponseBody
    public Map<String, Object> deletePublicNoticeInfo(HttpSession session, @RequestBody HTPublicNoticeVO paramVO) {

        Map<String, Object> resultMap = null;

        try {
            // 获取session 登陆用户信息
            paramVO = putLoginUserToVOFromSession(paramVO, session);

            // 删除公告信息
            htPublicNoticeService.excDeletePublicNoticeInfo(paramVO);

            // MSG_ERROR_COMMON_0008 删除成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_ERROR_COMMON_0008));

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

}
