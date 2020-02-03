package com.hd.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.vo.BaseVO;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.UUID;

@Component
@Lazy(false)
public class OnlyOfficeUtils {

    public OnlyOfficeUtils(){};

    /**
     * 本地 内网IP
     */
    private static String serverLocalIp;

    /**
     * 本程序 使用的端口号
     */
    private static String serverPort;

    /**
     * docServer 外网IP
     */
    private static String docServerIp;

    /**
     * docServer 外网端口
     */
    private static String docServerPort;

    /**
     * OnlyOffice 回调保存状态：成功
     */
    public static final int ONLY_OFFICE_SAVE_STATUS_SUC = 0;

    /**
     * OnlyOffice 回调保存状态：失败
     */
    public static final int ONLY_OFFICE_SAVE_STATUS_FAIL = 1;

    /**
     * OnlyOffice 回调保存类型：强制保存
     */
    public static final int ONLY_OFFICE_SAVE_TYPE_FORCE_SAVE = 6;

    /**
     * docServerIp
     *
     * @param docServerIp 项目描述（略）
     */
    @Value("${only-office.doc-server-ip}")
    public void setDocServerIp(String docServerIp) {
        OnlyOfficeUtils.docServerIp = docServerIp;
    }

    /**
     * docServerPort
     *
     * @param docServerPort 项目描述（略）
     */
    @Value("${only-office.doc-server-port}")
    public void setDocServerPort(String docServerPort) {
        OnlyOfficeUtils.docServerPort = docServerPort;
    }

    /**
     * serverLocalIp
     *
     * @param serverLocalIp 项目描述（略）
     */
    @Value("${only-office.web-ip}")
    public void setServerLocalIp(String serverLocalIp) {
        OnlyOfficeUtils.serverLocalIp = serverLocalIp;
    }

    /**
     * serverPort
     *
     * @param serverPort 项目描述（略）
     */
    @Value("${server.port}")
    public void setServerPort(String serverPort) {
        OnlyOfficeUtils.serverPort = serverPort;
    }

    /**
     * 通过 相对路径，取得给OnlyOffice 下载文件使用的绝对路径
     *
     * @param paramRelativePath 参数：文件相对路径
     * @return 给OnlyOffice 用的 文件下载绝对路径。
     */
    public static String getFileDownloadAbsoluteUrl(String paramRelativePath) throws LogicException,Exception {

        // 判断如果传来的相对路径为空，则提示文件不存在！
        if (StringToolUtils.isNull(paramRelativePath)){
            // MSG_ERROR_COMMON_0010 文件不存在！
            throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0010);
        }

        return "http://" + serverLocalIp + ":" + serverPort + paramRelativePath;
    }

    /**
     * 取得OnlyOffice 编辑需要的标识Key
     *
     * @return 给OnlyOffice 编辑的标识Key
     */
    public static String generateOnlyOfficeEditKey(){

        return UUID.randomUUID().toString();
    }

    /**
     * 取得OnlyOffice 编辑需要的标识Key
     *
     * @return 给OnlyOffice 用的 文件上传绝对路径。
     */
    public static String getOnlyOfficeSaveCallbackUrl(){

        return "http://" + serverLocalIp + ":" + serverPort + Constant.COMMON_MODULE_URL + "/onlyOfficeUpload";
    }

    /**
     * 通知OnlyOffice documentServer 保存文件
     *
     * @param paramVO VO 类型
     *                必传：
     *                reviewFilePath 文件保存路径
     *                reviewFileName 文件名
     *                businessId 关联id（请款单/任务/阶段/合同编撰/投标任务 id）
     *                createDate 创建时间（yyyy-MM-dd hh:mm:ss）
     *                createUserId 创建人
     *                updateDate 修改时间（yyyy-MM-dd hh:mm:ss）
     *                updateUserId 修改人
     *                onlyOfficeEditKey 编辑标识
     *
     *                否则报错。
     */
    public static <VO_TYPE extends BaseVO> void notifyOnlyOfficeToSave(VO_TYPE paramVO)
            throws LogicException, SystemException {
        Logger logger = LoggerFactory.getLogger(OnlyOfficeUtils.class);
        try {
            // 如果我们需要的必填字段存在未传的情况
            if (null == paramVO.getReviewFilePath()
                || null == paramVO.getReviewFileName()
                || null == paramVO.getBusinessId()
                || null == paramVO.getCreateDate()
                || null == paramVO.getCreateUserId()
                || null == paramVO.getUpdateDate()
                || null == paramVO.getUpdateUserId()
                || null == paramVO.getOnlyOfficeEditKey()
            ){
                throw new Exception("文件上传必填字段不全，请修改！！");
            }

            // 睡眠 5秒，等待页面编辑Iframe 与 documentServer
            // 最后的同步数据，提高保存数据的实时性
            Thread.sleep(5000);

            // 参数VOJson
            String paramVOJsonStr = new ObjectMapper().writeValueAsString(paramVO);

            // 通知onlyOffice 强制保存的指令参数
            String tmpParamJsonStr = "{" +
                    "\"key\":\"" + paramVO.getOnlyOfficeEditKey() + "\"," +
                    "\"c\": \"forcesave\"," +
                    "\"userdata\":" + paramVOJsonStr +
                "}";

            // 通知onlyOffice 强制保存。
            CloseableHttpResponse resp = RequestUtils.doPost(
                    "http://" + docServerIp + ":" + docServerPort + "/coauthoring/CommandService.ashx",
                    tmpParamJsonStr
            );

            /*
             * 0	没有错误。
             * 1	缺少文档密钥或找不到具有该密钥的文档。
             * 2	回调网址不正确。
             * 3	内部服务器错误。
             * 4	在收到forcesave命令之前，没有对文档进行任何更改。
             * 5	命令不正确。
             * 6	令牌无效。
             */
            
            logger.debug("----------------------------通知OnlyOffice 保存调用Callback START---------------------------");
            logger.debug(paramVOJsonStr);
            String respStr = IOUtils.toString(resp.getEntity().getContent(), "UTF-8");
            logger.debug(respStr);
            logger.debug("----------------------------通知OnlyOffice 保存调用Callback END-----------------------------");

            // 再睡眠 5秒，等待页面编辑Iframe 与 documentServer
            // 最后的同步数据，提高保存数据的实时性
            // TODO
            Thread.sleep(5000);

        }catch (LogicException lex){
            throw lex;
        }catch (Exception ex){
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0011);
        }
    }


}
