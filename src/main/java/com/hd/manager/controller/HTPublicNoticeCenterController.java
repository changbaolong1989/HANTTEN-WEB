package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
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
import javax.annotation.Resource;
import java.util.Map;

/**
 * 公告中心管理Controller
 *
 * @author jwl
 * Created in 2019/7/5 10:26
 */
@Controller
@RequestMapping("/publicNoticeCenter")
@CrossOrigin(allowCredentials = "true")
public class HTPublicNoticeCenterController extends BaseController {

    /**
     * 公告service 业务层资源
     */
    @Resource
    private HTPublicNoticeService htPublicNoticeService;

    /**
     * 查询公告中心列表数据
     *
     * @return 请求参数
     */
    @RequestMapping("/query/searchPublicNoticeCenterList")
    @ResponseBody
    public Map<String, Object> searchPublicNoticeCenterList(@RequestBody HTPublicNoticeVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询公告中心列表数据和总条数
            Map<String, Object> retMap = htPublicNoticeService.excSearchPublicNoticeList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回公告中心列表数据
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

}
