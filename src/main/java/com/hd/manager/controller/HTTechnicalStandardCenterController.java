package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
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
import javax.annotation.Resource;
import java.util.Map;

/**
 * 技术标准中心管理Controller
 *
 * @author jwl
 * Created in 2019/7/5 15:21
 */
@Controller
@RequestMapping("/technicalStandardCenter")
@CrossOrigin(allowCredentials = "true")
public class HTTechnicalStandardCenterController extends BaseController {

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
    @RequestMapping("/query/searchTechnicalStandardCenterList")
    @ResponseBody
    public Map<String, Object> searchTechnicalStandardList(@RequestBody HTTechnicalStandardVO paramVO) {

        Map<String, Object> resultMap = null;

        try {

            // 查询技术标准中心列表数据和总条数
            Map<String, Object> retMap = htTechnicalStandardService.excSearchTechnicalStandardList(paramVO);

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));

            // 返回技术标准中心列表数据
            resultMap.put(Constant.PAGEED_DATA_KEY, retMap);

        } catch (SystemException sex) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }

        return resultMap;
    }

}
