package com.hd.preview.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.util.InterfaceUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.word.WordTemplate;
import com.hd.manager.service.HTContractService;
import com.hd.manager.service.HTRequestMenuService;
import com.hd.manager.service.HTTaskExecuteService;
import com.hd.manager.service.HTTaskService;
import com.hd.manager.vo.HTContractVO;
import com.hd.manager.vo.HTRequestMenuVO;
import com.hd.manager.vo.HTTaskExecuteVO;
import com.hd.manager.vo.HTTaskVO;
import com.hd.preview.entity.FilePreview;
import com.hd.preview.enums.PreviewFileTypeEnum;
import com.hd.preview.service.Preview;
import com.hd.preview.service.impl.WordPreview;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin(allowCredentials = "true")
public class PreviewController extends BaseController {

    /**
     * 返回消息 key值
     */
    private static final String RETURN_STATUS_KEY = "status";
    private static final String RETURN_MESSAGE_KEY = "message";
    private static final String PREFIX_VIEWER = "prefixViewer";
    private static final String SERVER_IP = "openOfficeServerIp";
    private static final String PREFIX_PATH = "prefixPath";
    private static final String SERVER_DOMAIN = "serverDomain";
    private static final String OFFICE_URL = "officeappsUrl";
    private static final String PIC_PREVIEW_URL = "picPreviewUrl";

    /**
     * 响应状态：失败
     */
    public static final String FAILURE = "failure";
    private static final String FAILURE_TXT_ILLEGAL_TYPE = "只能支持.pdf、.docx、.doc、.png、.jpg、.jpeg格式";
    private static final String FAILURE_TXT_FILE_NOT_EXIST = "文件不存在";
    private static final String FAILURE_TXT_FILE_TOO_BIG = "文件过大，不支持预览";
    private static final String FAILURE_TXT_EXCEPTION = "文件预览失败";

    /**
     * 响应状态：成功
     */
    public static final String SUCCESS = "success";

    @Value("${system-param.preview.max-size}")
    private long previewMaxSize;

    @Value("${system-param.preview.prefix-viewer-pc}")
    private String prefixViewerPc;

    @Value("${system-param.preview.prefix-viewer-app}")
    private String prefixViewerApp;

    @Value("${system-param.preview.openOffice-server-ip}")
    private String openOfficeServerIp;

    @Value("${file.request.rootpath}")
    private String prefixPath;

    @Value("${system-param.preview.openOffice-server-ip-stream}")
    private String openOfficeServerIpStream;

    @Value("${system-param.preview.openOffice-server-ip-stream-no-submit}")
    private String openOfficeServerIpStreamNoSubmit;

    @Value("${system-param.preview.server-domain}")
    private String serverDomain;

    @Value("${system-param.preview.officeapps-url}")
    private String officeappsUrl;

    @Value("${system-param.preview.picture-preview-url}")
    private String picPreviewUrl;


    @Resource
    private WordPreview wordPreview;

    /**
     * 任务
     */
    @Resource
    private HTTaskExecuteService htTaskExecuteService;

    /**
     * 合同
     */
    @Resource
    private HTContractService htContractService;

    /**
     * 任务service 业务层资源
     */
    @Resource
    private HTTaskService htTaskService;

    /**
     * 请款单service 业务层资源
     */
    @Resource
    private HTRequestMenuService htRequestMenuService;

    /**
     * 校验文件
     */
    @RequestMapping(value = "/preview/checkFile")
    @ResponseBody
    public Map<String, Object> checkFile(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        String filePath = prefixPath + request.getParameter("filePath");
        File file = new File(filePath);
        String suffix = StringUtils.isBlank(filePath) ? "" : filePath.substring(filePath.lastIndexOf(".") + 1);
        if (StringUtils.isBlank(suffix) || PreviewFileTypeEnum.getInstance(suffix.toUpperCase()) == null) {
            resultMap.put(RETURN_STATUS_KEY, FAILURE);
            resultMap.put(RETURN_MESSAGE_KEY, FAILURE_TXT_ILLEGAL_TYPE);
        } else if (!file.exists()) {
            resultMap.put(RETURN_STATUS_KEY, FAILURE);
            resultMap.put(RETURN_MESSAGE_KEY, FAILURE_TXT_FILE_NOT_EXIST);
        } else if (file.length() > previewMaxSize) {
            resultMap.put(RETURN_STATUS_KEY, FAILURE);
            resultMap.put(RETURN_MESSAGE_KEY, FAILURE_TXT_FILE_TOO_BIG);
        } else {
            resultMap.put(RETURN_STATUS_KEY, SUCCESS);
            if (isApp(request)) {
                resultMap.put(PREFIX_VIEWER, prefixViewerApp);
            } else {
                resultMap.put(PREFIX_VIEWER, prefixViewerPc);
            }
            resultMap.put(SERVER_IP, openOfficeServerIp);
            resultMap.put(PREFIX_PATH, prefixPath);
        }
        return resultMap;
    }

    /**
     * 校验文件
     */
    @RequestMapping(value = "/preview/checkFile/byFileStream")
    @ResponseBody
    public Map<String, Object> checkFileByFileStream(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(RETURN_STATUS_KEY, SUCCESS);
        if (isApp(request)) {
            resultMap.put(PREFIX_VIEWER, prefixViewerApp);
        } else {
            resultMap.put(PREFIX_VIEWER, prefixViewerPc);
        }
        resultMap.put(SERVER_IP, openOfficeServerIpStream);
        resultMap.put(PREFIX_PATH, prefixPath);
        return resultMap;
    }

    /**
     * 校验文件（模板未提交审核）
     */
    @RequestMapping(value = "/preview/checkFile/byFileStreamNoSubmitPreview")
    @ResponseBody
    public Map<String, Object> byFileStreamNoSubmitPreview(HttpServletRequest request, HTTaskVO taskVO, HTRequestMenuVO requestMenuVO, HTContractVO contractVO) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            if (isApp(request)) {
                resultMap.put(PREFIX_VIEWER, prefixViewerApp);
            } else {
                resultMap.put(PREFIX_VIEWER, prefixViewerPc);
            }
            resultMap.put(RETURN_STATUS_KEY, SUCCESS);
            resultMap.put(SERVER_IP, openOfficeServerIp);
            resultMap.put(PREFIX_PATH, prefixPath);

            // 获取预览合同类型（task:任务  request:请款单  contract:合同）
            String type = request.getParameter("type");
            String json = "";
            // json字符串（存放要放入模板合同中的数据）
            if ("task".equals(taskVO.getType())) {// 如果是任务的成果文件阶段
                // 转换数据格式为json
                json = htTaskService.mapConvertToJson(taskVO);
                // 如果是请款单
            } else if ("request".equals(requestMenuVO.getType())) {
                // 转换数据格式为json
                json = htRequestMenuService.mapConvertToJson(requestMenuVO);
                // 如果是合同
            } else if ("contract".equals(requestMenuVO.getType())) {
                // 转换数据格式为json
                json = htContractService.mapConvertToJson(contractVO);
            }
            // 字节数组输出流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 把要放入模板合同的json数据转换成map
            Map jsonMap = InterfaceUtils.jsonToObject(json, Map.class);
            // 访问模板合同的路径
            String path = StringToolUtils.STRING_EMPTY;
            // 如果是任务文件
            if ("task".equals(type)) {
                // 模板文件路径
                path = "/task/" + jsonMap.get("taskTemplateName");
                // 如果是请款单文件
            } else if ("request".equals(type)) {
                // 模板文件路径
                path = "/requestMenu/2019HT请款申请模板.docx";
                // 如果是合同
            } else if ("contract".equals(type)) {
                // 模板文件路径
                path = "/contract/" + jsonMap.get("contractTemplateName");
            }
            try (InputStream tempInputStream = new ClassPathResource(path).getInputStream()) {
                // word模板对象
                WordTemplate wordTemplate = new WordTemplate(tempInputStream);
                // 替换模板中的变量
                wordTemplate.replaceTag(jsonMap);
                // 数据写入到输出流
                wordTemplate.write(byteArrayOutputStream);
                if (byteArrayOutputStream.size() > 0) {
                    String wordFilePath = wordPreview.createFile("docx");
                    wordPreview.writeFile(wordFilePath, byteArrayOutputStream.toByteArray());
                    resultMap.put("filePath", wordFilePath);
                } else {
                    resultMap.put("filePath", "");
                }
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        }
        return resultMap;
    }

    /**
     * 预览（模板合同）
     */
    @RequestMapping(value = "/preview/fileInputStreamViewer")
    public void previewStreamHandler(HttpServletRequest request, HttpServletResponse response) {
        try {
            OutputStream outputStream = response.getOutputStream();

            // 获取类型
            String type = request.getParameter("type");

            // 主键id
            String id = request.getParameter("id");

            ByteArrayOutputStream ostream = new ByteArrayOutputStream();

            ByteArrayInputStream byteArrayInputStream;

            HTTaskExecuteVO taskVO = new HTTaskExecuteVO();

            // 如果是任务文件
            if ("task".equals(type)) {

                // 设置任务id
                taskVO.setTaskId(id);

                // 转换流
                htTaskExecuteService.writeTaskExecuteWordStreamById(taskVO, ostream);

                // 如果是请款单文件
            } else if ("request".equals(type)) {

                // 设置请款单id
                taskVO.setRequestMenuId(id);

                // 转换流
                htTaskExecuteService.writeRequestMenuWordStreamById(taskVO, ostream);

                // 如果是合同文件
            } else if ("contract".equals(type)) {

                HTContractVO contractVO = new HTContractVO();

                // 设置合同id
                contractVO.setContractId(id);

                // 转换流
                htContractService.writeContractWordStreamById(contractVO, ostream);

            }

            byteArrayInputStream = new ByteArrayInputStream(ostream.toByteArray());

            // 预览
            wordPreview.preview(byteArrayInputStream, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 预览
     */
    @RequestMapping(value = "/preview/fileViewer")
    public void previewHandler(HttpServletRequest request, HttpServletResponse response) {
        String filePath = request.getParameter("filePath");
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
        Preview preview = PreviewFileTypeEnum.getInstance(suffix.toUpperCase());
        try {
            OutputStream outputStream = response.getOutputStream();
            preview.preview(filePath, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/preview/zipViewer")
    @ResponseBody
    public Map<String, Object> zipViewer(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        String filePath = request.getParameter("filePath");
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
        Preview preview = PreviewFileTypeEnum.getInstance(suffix.toUpperCase());
        try {
            String fileTree = preview.preview(filePath).toString();
            resultMap.put(RETURN_STATUS_KEY, SUCCESS);
            resultMap.put(RETURN_MESSAGE_KEY, fileTree);
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put(RETURN_STATUS_KEY, FAILURE);
            resultMap.put(RETURN_MESSAGE_KEY, FAILURE_TXT_EXCEPTION);
        }
        return resultMap;
    }

    private static boolean isApp(HttpServletRequest request) {
        return request.getParameter("appType") != null && "App".equals(request.getParameter("appType"));
    }


    //-----------------------------------------------------------------------预览V2.0--------------------------------------------------------------------------------------------------//


    /**
     * 文件预览By文件路径
     */
    @RequestMapping("/preview/previewByPath")
    public void previewByPath(FilePreview filePreview, HttpServletResponse response) {
        try {
            String filePath = filePreview.getFilePath();
            String fileName = filePreview.getFileName();
            InputStream tempInputStream = new FileInputStream(filePath);
            response.reset();
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8") + ";filename=" + URLEncoder.encode(fileName, "UTF-8"));
            ServletOutputStream servletOS = response.getOutputStream(); //输出流
            byte[] buff = new byte[1024];
            int len;
            while ((len = tempInputStream.read(buff)) > 0) {
                servletOS.write(buff, 0, len);
            }
            servletOS.flush();
            servletOS.close();
            tempInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * PDF预览By文件路径
     */
    @RequestMapping("/preview/pdfPreviewByPath")
    public void pdfPreviewByPath(FilePreview filePreview, HttpServletResponse response) {
        String filePath = filePreview.getFilePath();
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
        Preview preview = PreviewFileTypeEnum.getInstance(suffix.toUpperCase());
        try {
            OutputStream outputStream = response.getOutputStream();
            preview.preview(filePath, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件预览ByData
     */
    @RequestMapping("/preview/previewByData")
    public void previewByData(FilePreview filePreview, HttpServletResponse response) {
        try {
            // 取得模板名称
            String tempName = "文件预览.docx";
            // 获取类型
            String type = filePreview.getType();
            // 主键id
            String id = filePreview.getId();
            HTTaskExecuteVO taskVO = new HTTaskExecuteVO();
            response.reset();
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(tempName, "UTF-8") + ";filename=" + URLEncoder.encode(tempName, "UTF-8"));
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            ServletOutputStream servletOS = response.getOutputStream(); //输出流
            // 如果是任务文件
            if ("task".equals(type)) {
                // 设置任务id
                taskVO.setTaskId(id);
                // 转换流
                htTaskExecuteService.writeTaskExecuteWordStreamById(taskVO, ostream);
                // 如果是请款单文件
            } else if ("request".equals(type)) {
                // 设置请款单id
                taskVO.setRequestMenuId(id);
                // 转换流
                htTaskExecuteService.writeRequestMenuWordStreamById(taskVO, ostream);
                // 如果是合同文件
            } else if ("contract".equals(type)) {
                HTContractVO contractVO = new HTContractVO();
                // 设置合同id
                contractVO.setContractId(id);
                // 转换流
                htContractService.writeContractWordStreamById(contractVO, ostream);
            }
            servletOS.write(ostream.toByteArray());
            servletOS.flush();
            servletOS.close();
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件预览By内容
     */
    @RequestMapping(value = "/preview/previewByContent")
    @ResponseBody
    public Map<String, Object> previewByContent(HttpServletRequest request, HTTaskVO taskVO, HTRequestMenuVO requestMenuVO, HTContractVO contractVO, HttpServletResponse response) {
        Map<String, Object> resultMap = null;
        try {
            // 获取预览合同类型（task:任务  request:请款单  contract:合同）
            String type = request.getParameter("type");
            String json = "";
            // json字符串（存放要放入模板合同中的数据）
            if ("task".equals(taskVO.getType())) {// 如果是任务的成果文件阶段
                // 转换数据格式为json
                json = htTaskService.mapConvertToJson(taskVO);
                // 如果是请款单
            } else if ("request".equals(requestMenuVO.getType())) {
                // 转换数据格式为json
                json = htRequestMenuService.mapConvertToJson(requestMenuVO);
                // 如果是合同
            } else if ("contract".equals(requestMenuVO.getType())) {
                // 转换数据格式为json
                json = htContractService.mapConvertToJson(contractVO);
            }
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            // 把要放入模板合同的json数据转换成map
            Map jsonMap = InterfaceUtils.jsonToObject(json, Map.class);
            // 访问模板合同的路径
            String path = StringToolUtils.STRING_EMPTY;
            // 如果是任务文件
            if ("task".equals(type)) {
                // 模板文件路径
                path = "/task/" + jsonMap.get("taskTemplateName");
                // 如果是请款单文件
            } else if ("request".equals(type)) {
                // 模板文件路径
                path = "/requestMenu/2019HT请款申请模板.docx";
                // 如果是合同
            } else if ("contract".equals(type)) {
                // 模板文件路径
                path = "/contract/" + jsonMap.get("contractTemplateName");
            }
            try (InputStream tempInputStream = new ClassPathResource(path).getInputStream()) {
                // word模板对象
                WordTemplate wordTemplate = new WordTemplate(tempInputStream);
                // 替换模板中的变量
                wordTemplate.replaceTag(jsonMap);
                // 数据写入到输出流
                wordTemplate.write(ostream);
                if (ostream.size() > 0) {
                    String wordFilePath = wordPreview.createFile("docx");
                    wordPreview.writeFile(wordFilePath, ostream.toByteArray());
                    resultMap = setReturnMessageMap(SUCCESS, "预览成功！");
                    resultMap.put("filePath", wordFilePath);
                } else {
                    resultMap = setReturnMessageMap(FAILURE, "预览失败！");
                    resultMap.put("filePath", "");
                }
            } catch (IOException | InvalidFormatException e) {
                resultMap = setReturnMessageMap(FAILURE, "预览失败！");
            }
        } catch (LogicException lex) {
            resultMap = setReturnMessageMap(FAILURE, lex.getMsgDes());
        } catch (SystemException sex) {
            resultMap = setReturnMessageMap(FAILURE, sex.getMsgDes());
        } catch (Exception e) {
            resultMap = setReturnMessageMap(FAILURE, "预览失败！");
        }
        return resultMap;
    }

    /**
     * 获取预览Url
     */
    @PostMapping("/preview/getConfiguration")
    @ResponseBody
    public Map<String, Object> getConfiguration(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        String path = request.getParameter("filePath");
        if (StringUtils.isNotBlank(path)) {
            String fullFilePath = prefixPath + request.getParameter("filePath");
            File file = new File(fullFilePath);
            String suffix = StringUtils.isBlank(fullFilePath) ? "" : fullFilePath.substring(fullFilePath.lastIndexOf(".") + 1);
            if (StringUtils.isBlank(suffix)) {
                resultMap.put(RETURN_STATUS_KEY, FAILURE);
                resultMap.put(RETURN_MESSAGE_KEY, FAILURE_TXT_ILLEGAL_TYPE);
            } else if (!file.exists()) {
                resultMap.put(RETURN_STATUS_KEY, FAILURE);
                resultMap.put(RETURN_MESSAGE_KEY, FAILURE_TXT_FILE_NOT_EXIST);
            } else if (file.length() > previewMaxSize) {
                resultMap.put(RETURN_STATUS_KEY, FAILURE);
                resultMap.put(RETURN_MESSAGE_KEY, FAILURE_TXT_FILE_TOO_BIG);
            } else {
                resultMap.put(RETURN_STATUS_KEY, SUCCESS);
                resultMap.put(SERVER_DOMAIN, serverDomain);
                resultMap.put(OFFICE_URL, officeappsUrl);
                resultMap.put(PREFIX_PATH, prefixPath);
                resultMap.put(PIC_PREVIEW_URL, picPreviewUrl);
            }
        } else {
            resultMap.put(RETURN_STATUS_KEY, SUCCESS);
            resultMap.put(SERVER_DOMAIN, serverDomain);
            resultMap.put(OFFICE_URL, officeappsUrl);
            resultMap.put(PREFIX_PATH, prefixPath);
            resultMap.put(PIC_PREVIEW_URL, picPreviewUrl);
        }
        return resultMap;
    }

    /**
     * 获取预览Url
     */
    @PostMapping("/preview/getContentPreviewConfig")
    @ResponseBody
    public Map<String, Object> getContentPreviewConfig(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(RETURN_STATUS_KEY, SUCCESS);
        resultMap.put(SERVER_DOMAIN, serverDomain);
        resultMap.put(OFFICE_URL, officeappsUrl);
        resultMap.put(PREFIX_PATH, prefixPath);
        resultMap.put(PIC_PREVIEW_URL, picPreviewUrl);
        return resultMap;
    }


}
