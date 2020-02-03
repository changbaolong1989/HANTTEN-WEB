package com.hd.common.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.service.CommonService;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.manager.vo.HTReviewFileRelationVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 通用Controller
 *
 * @author JLF
 * Created in 2019/1/25 15:55
 */
@Controller
@RequestMapping(Constant.COMMON_MODULE_URL)
@CrossOrigin(allowCredentials = "true")
public class CommonController extends BaseController {

    /**
     * 通用业务层处理
     */
    @Resource
    private CommonService commonService;

    /**
     * 文件物理路径根目录
     */
    @Value("${file.request.rootpath}")
    private String fileRootPath;

    /**
     * 下载文件
     *
     * @return 请求参数
     */
    @RequestMapping(value = "/downloadFile",method = RequestMethod.GET)
    public String downloadFile(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {

        // 实际文件路径
        String actFilePath = fileRootPath + request.getParameter("filePathName");
        // 文件名称
        String realFileName = request.getParameter("realFileName");

        File file = new File(actFilePath);
        if (!file.exists()) {
            return "文件不存在！";
        }

        response.reset();
        // 解决中文显示不出来的问题
        response.setHeader("Content-Disposition",
                "attachment; filename*=UTF-8''"
                        + URLEncoder.encode(realFileName,
                        "UTF-8")
                        + ";filename="
                        + URLEncoder.encode(realFileName,
                        "UTF-8")
        );

        try {
            InputStream inStream = new FileInputStream(actFilePath);
            OutputStream os = response.getOutputStream();

            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buff)) > 0) {
                os.write(buff, 0, len);
            }
            os.flush();
            os.close();

            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "下载文件失败";
        }

        return "下载文件成功";
    }

    /**
     * OnlyOffice 上传回调
     *
     * @return
     */
    @RequestMapping(value = "/onlyOfficeUpload")
    @ResponseBody
    public Map<String,Object> doOnlyOfficeUpload(@RequestBody String paramJsonStr) {
        return commonService.excInsertReviewFile(paramJsonStr);
    }



    /**
     * 查询投标阶段文件信息
     *
     * @return
     */
    @RequestMapping("/query/searchReviewFileRelationList")
    @ResponseBody
    public Map<String,Object> searchReviewFileRelationList(@RequestBody HTReviewFileRelationVO paramVO){
        Map<String, Object> resultMap = null;
        try {
            //查询审核意见文件信息
            List<HTReviewFileRelationVO> htReviewFileRelationVOList = commonService.excSearchReviewFileRelationList(paramVO);
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            resultMap.put(Constant.DATA_LIST_KEY,htReviewFileRelationVOList);
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }
}
