package com.hd.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.vo.BaseVO;
import com.hd.common.dao.*;
import com.hd.common.entity.*;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.FileUtils;
import com.hd.common.util.ListToolUtils;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.OnlyOfficeUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.manager.dao.HTDictQuestionDao;
import com.hd.manager.dao.HTReviewFileRelationDao;
import com.hd.manager.dao.HTTaskDao;
import com.hd.manager.dao.HTUserDao;
import com.hd.manager.dao.bean.HTDictQuestionBean;
import com.hd.manager.dao.bean.HTReviewFileRelationBean;
import com.hd.manager.dao.bean.HTTaskBean;
import com.hd.manager.dao.bean.HTUserBean;
import com.hd.manager.vo.HTDictQuestionVO;
import com.hd.manager.vo.HTReviewFileRelationVO;
import com.hd.manager.vo.HTUserVO;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.service.CommonService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 通用 Service 实现类
 * @author JLF
 */
@Service
public class CommonServiceImpl extends BaseServiceImpl implements CommonService {


    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 持久层资源 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */


    /**
     * 取得log对象
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 用户dao
     */
    @Resource
    private HTUserDao userDao;

    /**
     * 错误类型dao
     */
    @Resource
    private HTDictQuestionDao htDictQuestionDao;

    /**
     * 审核意见文件Dao
     */
    @Resource
    private HTReviewFileRelationDao htReviewFileRelationDao;

    /**
     * 通用 审核意见文件entityMapper
     */
    @Resource
    private TbReviewFileRelationEntityMapper tbReviewFileRelationEntityMapper;

    /**
     * 消息mapper
     */
    @Resource
    private TbMessageEntityMapper tbMessageEntityMapper;

    /**
     * 项目mapper
     */
    @Resource
    private TbProjectEntityMapper tbProjectEntityMapper;

    /**
     * 合同mapper
     */
    @Resource
    private TbContractEntityMapper tbContractEntityMapper;

    /**
     * 任务dao
     */
    @Resource
    private HTTaskDao htTaskDao;

    /**
     * 用户mapper
     */
    @Resource
    private TbUserEntityMapper tbUserEntityMapper;

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 持久层资源 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /**
     * 微信公众号推送模板消息
     * @param loginUserId
     * @param openId 用户唯一标识
     * @param acceptUserName 接收人名称
     * @param messageContent 消息内容
     */
    @Transactional
    public void sendTemplateMessageToWeChat(String loginUserId,String openId,String acceptUserName,String messageContent) throws SystemException{

        try{

//            // 如果微信和登录项目的用户绑定了，则向公众号推送消息
//            if(!StringToolUtils.STRING_EMPTY.equals(StringToolUtils.convertNullObjectToString(openId))){
//
//                // 设置token为""
//                String token = StringToolUtils.STRING_EMPTY;
//
//                // 查询用户信息，目的是获取token
//                HTUserBean htUserBean = userDao.excSearchAccessToken();
//
//                // 如果查询到的用户不为空
//                if(null != htUserBean){
//                    // 设置token
//                    token = StringToolUtils.convertNullObjectToString(htUserBean.getAccessToken());
//                }
//
//                // 创建对象
//                TbUserEntity userEntity = new TbUserEntity();
//
//                // 用户id
//                userEntity.setUserId(loginUserId);
//
//                // 设置token时间
//                userEntity.setSaveAccessTokenDate(getCurrentDate());
//
//                // 如果没有token，则获取一个token，并存入数据库
//                if(StringToolUtils.STRING_EMPTY.equals(token)){
//
//                    // 获取token
//                    token = this.createToken();
//
//                    // 设置token
//                    userEntity.setAccessToken(token);
//
//                    // 保存token信息
//                    tbUserEntityMapper.updateByPrimaryKeySelective(userEntity);
//
//                } else {
//
//                    // 是否有效
//                    boolean isValid = (getCurrentDate().getTime() - htUserBean.getSaveAccessTokenDate().getTime()) < 1000*60*60*2;
//
//                    // 如果失效，则重新创建token
//                    if(!isValid){
//
//                        // 获取token
//                        token = this.createToken();
//
//                        // 设置token
//                        userEntity.setAccessToken(token);
//
//                        // 保存token信息
//                        tbUserEntityMapper.updateByPrimaryKeySelective(userEntity);
//
//                    }
//
//                }
//
//                this.sendTemplateMessageToWeChatV2(token,openId,acceptUserName,messageContent);
//
//            }

        } catch (Exception e) {
            throw new SystemException(e, e.getMessage());
        }

    }

    /**
     * 不重新获取token，直接用转入的参数token，去推送消息
     * @param token
     * @param openId 用户唯一标识
     * @param acceptUserName 接收人名称
     * @param messageContent 消息内容
     */
    public void sendTemplateMessageToWeChatV2(String token,String openId,String acceptUserName,String messageContent) throws SystemException{

        try{

            // 如果微信和登录项目的用户绑定了，则向公众号推送消息
            if(!StringToolUtils.STRING_EMPTY.equals(StringToolUtils.convertNullObjectToString(openId))){

                // 地址
                String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("value", "汉腾业务系统工作消息");
                jsonObject1.put("color", "#173177");

                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("value", StringToolUtils.convertNullObjectToString(acceptUserName));
                jsonObject2.put("color", "#173177");

                JSONObject jsonObject3 = new JSONObject();
                jsonObject3.put("value", DateToolUtils.convertDateFormat(getCurrentDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                jsonObject3.put("color", "#173177");

                JSONObject jsonObject4 = new JSONObject();
                jsonObject4.put("value", messageContent);
                jsonObject4.put("color", "#173177");

                JSONObject jsonObject5 = new JSONObject();
                jsonObject5.put("value", "");
                jsonObject5.put("color", "#173177");

                JSONObject jsonObject6 = new JSONObject();
                jsonObject6.put("first", jsonObject1);
                jsonObject6.put("keyword1", jsonObject2);
                jsonObject6.put("keyword2", jsonObject3);
                jsonObject6.put("keyword3", jsonObject4);
                jsonObject6.put("remark", jsonObject5);

                JSONObject jsonObject7 = new JSONObject();
                jsonObject7.put("touser", openId);
                jsonObject7.put("template_id", Constant.TEMPLATE_ID);
                jsonObject7.put("data", jsonObject6);

                // 发送post请求
                String result = post(jsonObject7, url);

                System.out.println(result);

            }

        } catch (Exception e) {
            throw new SystemException(e, "推送模板消息失败！");
        }

    }

    /**
     * 发送post请求
     * @param json
     * @param url
     * @return
     */
    public static String post(JSONObject json, String url){
        String result = "";
        HttpPost post = new HttpPost(url);
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();

            post.setHeader("Content-Type","application/json;charset=utf-8");
            post.addHeader("Authorization", "Basic YWRtaW46");
            StringEntity postingString = new StringEntity(json.toString(),"utf-8");
            post.setEntity(postingString);
            HttpResponse response = httpClient.execute(post);

            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuilder strber= new StringBuilder();
            String line = null;
            while((line = br.readLine())!=null){
                strber.append(line+'\n');
            }
            br.close();
            in.close();
            result = strber.toString();
            if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
                result = "服务器异常";
            }
        } catch (Exception e){
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally{
            post.abort();
        }
        return result;
    }

    /**
     * 创建token
     *
     * @return 路径
     */
    public String createToken() throws SystemException,LogicException{

        String token = StringToolUtils.STRING_EMPTY;

        try {

//            // 获取token
//            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+Constant.APP_ID+"&secret="+Constant.SECRET;
//
//            URL u = new URL(url);
//            HttpsURLConnection huconn = (HttpsURLConnection) u.openConnection();
//            BufferedReader in = null;
//            StringBuilder resultMap = new StringBuilder();
//            huconn.connect();
//            in = new BufferedReader(new InputStreamReader(huconn.getInputStream(), "UTF-8"));
//            String line;
//            while ((line = in.readLine()) != null) {
//                resultMap.append(line);
//            }
//            if (in != null) {
//                in.close();
//            }
//
//            if("40001".equals(JSON.parseObject(resultMap.toString()).getString("errcode"))){
//                logger.info("================错误编码：40001");
//                logger.info("================错误原因：AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性。");
//                throw new NoSuchMessageException("AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性");
//            } else if ("40002".equals(JSON.parseObject(resultMap.toString()).getString("errcode"))){
//                logger.info("================错误编码：40002");
//                logger.info("================错误原因：请确保grant_type字段值为client_credential。");
//                throw new NoSuchMessageException("请确保grant_type字段值为client_credential");
//            } else if ("40164".equals(JSON.parseObject(resultMap.toString()).getString("errcode"))){
//                logger.info("================错误编码：40164");
//                logger.info("================错误原因：调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置。");
//                throw new NoSuchMessageException("调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置");
//            }
//
//            token = JSON.parseObject(resultMap.toString()).getString("access_token");
//
//            logger.info(token);

        } catch (NoSuchMessageException nsme) {
            logger.info(nsme.getMessage());
            throw nsme;
        } catch (Exception e) {
            throw new SystemException(e, "获取token失败！");
        }

        return token;

    }

    /**
     * 为消息中心查询用户id
     * @param submitNode
     * @param departmentId
     * @return
     * @throws SystemException
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskBean excSearchUserIdForMessage(String submitNode,String departmentId) throws SystemException {

        HTTaskBean htTaskBean = new HTTaskBean();

        try {

            List<String> list = new ArrayList<>();

            switch (submitNode){
                // 部门经理
                case "ManagerVerify":
                    list.add("经理");
                    list.add("副经理");
                    // 质检部id
                    htTaskBean.setDepartmentId(departmentId);
                    break;

                // 质控部组员
                case "QualityVerify":
                    list.add("组员");
                    // 质检部id
                    htTaskBean.setDepartmentId(Constant.DEPT_ID_QUALITY);
                    break;

                // 质控部副总经理
                case "GenerManagerVerify":
                    list.add("副总");
                    // 质检部id
                    htTaskBean.setDepartmentId(Constant.DEPT_ID_QUALITY);
                    break;

                // 签发人
                case "Signer":
                    list.add("签发人");
                    // 质检部id
                    htTaskBean.setDepartmentId(departmentId);
                    break;

                default:
                    break;
            }

            // 设置职位名称集合
            htTaskBean.setList(list);

            // 存放查询得到的人员id
            List<HTTaskBean> htTaskBeanList = htTaskDao.excSearchUserIdForMessage(htTaskBean);

            htTaskBean.setHtTaskBeanList(htTaskBeanList);

        } catch (SystemException sex) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htTaskBean;
    }

    /**
     * 根据部门查人员列表
     *
     * @param paramVO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTUserVO> excSearchUserListByDepartmentId(HTUserVO paramVO) throws SystemException {

        // 集合
        List<HTUserVO> htUserVOList = new ArrayList<>();

        try {

            HTUserBean paramBean = new HTUserBean();

            // 部门ID
            paramBean.setDepartmentId(StringToolUtils.convertNullObjectToString(paramVO.getDepartmentId()));

            // 根据部门查人员列表
            List<HTUserBean> htUserBeanList = userDao.excSearchUserListByDepartmentId(paramBean);

            // Bean 转化成 VO
            for (HTUserBean htUserBean : htUserBeanList) {

                HTUserVO htUserVO = new HTUserVO();

                // 设置用户ID
                htUserVO.setUserId(StringToolUtils.convertNullObjectToString(
                        htUserBean.getUserId()));

                // 设置用户名称
                htUserVO.setUserName(StringToolUtils.convertNullObjectToString(
                        htUserBean.getUserName()));

                // 设置openid
                htUserVO.setOpenId(StringToolUtils.convertNullObjectToString(
                        htUserBean.getOpenId()));

                htUserVOList.add(htUserVO);

            }

            return htUserVOList;

        } catch (SystemException sex) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 通过部门id查询问题类型
     * @return 问题类型
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTDictQuestionVO> excSearchQuestionTypeByDeptId(HTDictQuestionVO paramVO) throws SystemException {

        // 集合
        List<HTDictQuestionVO> htDictQuestionVOList = new ArrayList<>();

        try {

            HTDictQuestionBean paramBean = new HTDictQuestionBean();

            // 部门ID
            paramBean.setDepartmentId(StringToolUtils.convertNullObjectToString(paramVO.getDepartmentId()));

            // 根据部门查错误类型列表
            List<HTDictQuestionBean> htDictQuestionBeanList = htDictQuestionDao.excSearchQuestionTypeByDeptId(paramBean);

            // Bean 转化成 VO
            for (HTDictQuestionBean htDictQuestionBean : htDictQuestionBeanList) {

                HTDictQuestionVO htDictQuestionVO = new HTDictQuestionVO();

                // 设置错误ID
                htDictQuestionVO.setQuestionId(StringToolUtils.convertNullObjectToString(
                        htDictQuestionBean.getQuestionId()));

                // 设置错误名称
                htDictQuestionVO.setQuestionName(StringToolUtils.convertNullObjectToString(
                        htDictQuestionBean.getQuestionName()));

                // 设置类型
                htDictQuestionVO.setExamineTypeId(StringToolUtils.convertNullObjectToString(
                        htDictQuestionBean.getExamineTypeId()
                ));

                // 设置名称
                htDictQuestionVO.setExamineTypeName(StringToolUtils.convertNullObjectToString(
                        htDictQuestionBean.getExamineTypeName()
                ));

                htDictQuestionVOList.add(htDictQuestionVO);

            }

            return htDictQuestionVOList;

        } catch (SystemException sex) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 审核记录和人员和问题类型关联表
     */
    @Override
    @Transactional
    public void excInsertUserQuestionRecordRelations(HTDictQuestionVO paramVO) throws SystemException {

        try {

            Date tempCurrentDate = getCurrentDate();

            // 获取错误类型列表
            List<HTDictQuestionVO> htDictQuestionVOList = paramVO.getHtDictQuestionList();

            // 集合
            List<HTDictQuestionBean> htDictQuestionBeanList = new ArrayList<>();

            // Bean 转化成 VO
            if(!ListToolUtils.isEmpty(htDictQuestionVOList))
            for (HTDictQuestionVO htDictQuestionVO : htDictQuestionVOList) {

                // 获取问题列表
                List<HTDictQuestionVO> questionList = htDictQuestionVO.getQuestionList();

                if(!ListToolUtils.isEmpty(questionList))
                for (HTDictQuestionVO questionVO : questionList) {

                    // 生成uuid
                    String tempRandomUUIDStr = UUID.randomUUID().toString();

                    HTDictQuestionBean htDictQuestionBean = new HTDictQuestionBean();

                    // 设置关联id
                    htDictQuestionBean.setRelationId(tempRandomUUIDStr);

                    // 设置记录id
                    htDictQuestionBean.setRecordId(StringToolUtils
                            .convertNullObjectToString(paramVO.getRecordId()));

                    //设置来源类型
                    htDictQuestionBean.setSourceType(StringToolUtils
                            .convertNullObjectToString(paramVO.getSourceType()));

                    // 设置用户id
                    htDictQuestionBean.setUserId(StringToolUtils
                            .convertNullObjectToString(htDictQuestionVO.getUserId()));

                    // 问题类型id
                    htDictQuestionBean.setQuestionId(StringToolUtils
                            .convertNullObjectToString(questionVO.getQuestionId()));

                    // 错误次数
                    htDictQuestionBean.setQuestionNum(StringToolUtils
                            .convertNullObjectToString(questionVO.getQuestionNum()));

                    // 默认启用 0
                    htDictQuestionBean.setIsDelFlg(StringToolUtils.STRING_ZERO);

                    // 创建时间
                    htDictQuestionBean.setCreateDate(tempCurrentDate);

                    // 修改时间
                    htDictQuestionBean.setUpdateDate(tempCurrentDate);

                    // 创建人id
                    htDictQuestionBean.setCreateUserId(paramVO.getLoginUserInfo().getUserId());

                    // 修改人id
                    htDictQuestionBean.setCreateUserId(paramVO.getLoginUserInfo().getUserId());

                    htDictQuestionBeanList.add(htDictQuestionBean);
                }

            }

            htDictQuestionDao.excInsertUserQuestionRecordRelations(htDictQuestionBeanList);

        } catch (SystemException sex) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 批量查询离职人员
     */
    @Override
    public Integer excSearchQuitUserByIds(List<String> userIds) {

        try {
            //离职人员个数
            Integer quitUserCount = userDao.excSearchQuitUserByIds(userIds);
            return quitUserCount;
        } catch (SystemException sex) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 插入并保存批注文件数据，并保存批注文件
     *
     * @param paramJsonStr 回调参数
     * @return 回调响应
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> excInsertReviewFile(String paramJsonStr) {

        // 结果响应对象(格式为：{"error":0})
        Map<String,Object> resultMap = new HashMap<>();

        // 结果上传状态（默认0，成功，1为失败）
        Integer resultErrorStatusInt = OnlyOfficeUtils.ONLY_OFFICE_SAVE_STATUS_SUC;

        try {
            ObjectMapper tmpOm = new ObjectMapper();

            // 保存参数对象
            JsonNode tmpSaveParamJn = tmpOm.readTree(paramJsonStr);

            // 保存类型：
            // 0 - 找不到带有密钥标识符的文档，
            // 1 - 正在编辑文档，
            // 2 - 文件已准备好保存，
            // 3 - 发生了文档保存错误，
            // 4 - 文件关闭，没有变化，
            // 6 - 正在编辑文档，但保存当前文档状态，
            // 7 - 强制保存文档时发生错误。
            int tmpSaveTypeInt = tmpSaveParamJn.get("status").intValue();

            // 如果保存类型 为 6 （编辑时，我们主动触发的 强制保存），则保存文件，其他情况不进行保存。
            if (OnlyOfficeUtils.ONLY_OFFICE_SAVE_TYPE_FORCE_SAVE == tmpSaveTypeInt){

                // 自定义参数Json节点
                JsonNode userdataNode = tmpSaveParamJn.get("userdata");

                // 临时变量:参数VO
                BaseVO tmpParamBaseVO = tmpOm.readValue(userdataNode.toString(), BaseVO.class);

                // 维护数据库
                TbReviewFileRelationEntity tmpParamEntity = new TbReviewFileRelationEntity();

                // 临时变量：文件保存路径
                String tmpReviewFilePathStr = tmpParamBaseVO.getReviewFilePath();

                // 文件保存路径
                tmpParamEntity.setReviewFilePath(tmpReviewFilePathStr);
                // 文件名
                tmpParamEntity.setReviewFileName(tmpParamBaseVO.getReviewFileName());
                // 关联id（请款单/任务/阶段/合同编撰/投标任务 id）
                tmpParamEntity.setBusinessId(tmpParamBaseVO.getBusinessId());
                // 创建时间（yyyy-MM-dd hh:mm:ss）
                tmpParamEntity.setCreateDate(
                        DateToolUtils.convertDateFormat(
                                tmpParamBaseVO.getCreateDate(),
                                DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO
                        )
                );
                // 创建人
                tmpParamEntity.setCreateUserId(tmpParamBaseVO.getCreateUserId());
                // 修改时间（yyyy-MM-dd hh:mm:ss）
                tmpParamEntity.setUpdateDate(
                        DateToolUtils.convertDateFormat(
                                tmpParamBaseVO.getUpdateDate(),
                                DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO
                        )
                );
                // 修改人
                tmpParamEntity.setUpdateUserId(tmpParamBaseVO.getUpdateUserId());
                // 逻辑删除标识位
                tmpParamEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

                // 插入审核意见文件表
                tbReviewFileRelationEntityMapper.insert(tmpParamEntity);

                // 保存
                URL downLoadUrl = new URL(StringToolUtils.convertNullObjectToString(tmpSaveParamJn.get("url").textValue()));

                // 保存请求连接
                HttpURLConnection fileConnection = (HttpURLConnection) downLoadUrl.openConnection();
                // 写入文件。
                InputStream docInputStream = fileConnection.getInputStream();
                // 批注文件
                File tmpCommentFile = new File(FileUtils.initPath + tmpReviewFilePathStr);
                // 批注文件父级目录
                File tmpParentFile = tmpCommentFile.getParentFile();
                // 如果父级目录不存在
                if (!tmpParentFile.exists()){
                    // 创建父级目录
                    tmpParentFile.mkdirs();
                }
                FileOutputStream docOutputStream = new FileOutputStream(tmpCommentFile);
                IOUtils.copy(docInputStream,docOutputStream);
                docOutputStream.flush();
                docOutputStream.close();
                fileConnection.disconnect();

            }

        // 异常时 的异常响应，只是通知OnlyOffice 服务器 这是失败了。
        }catch (Exception e){

            resultErrorStatusInt = OnlyOfficeUtils.ONLY_OFFICE_SAVE_STATUS_FAIL;
        }

        resultMap.put("error",resultErrorStatusInt);

        return resultMap;
    }



    /**
     * 查询审核意见文件信息
     *
     * @return
     */
    @Override
    public List<HTReviewFileRelationVO> excSearchReviewFileRelationList(HTReviewFileRelationVO paramVO) throws
            LogicException, SystemException {

        try {
            HTReviewFileRelationBean paramBean = new HTReviewFileRelationBean();
            //业务唯一标识
            paramBean.setBusinessId(paramVO.getBusinessId());
            //查询审核意见文件信息
            List<HTReviewFileRelationBean> reviewFileRelationBeanList = htReviewFileRelationDao
                    .excSearchReviewFileRelationInfoByBusinessId(paramBean);
            List<HTReviewFileRelationVO> htReviewFileRelationVOList = new ArrayList<>();
            for (HTReviewFileRelationBean htReviewFileRelationBean:reviewFileRelationBeanList){
                HTReviewFileRelationVO htReviewFileRelationVO = new HTReviewFileRelationVO();
                //文件唯一标识
                htReviewFileRelationVO.setFileId(htReviewFileRelationBean.getFileId());
                //业务唯一标识
                htReviewFileRelationVO.setBusinessId(htReviewFileRelationBean.getBusinessId());
                //文件名(真实名称)
                htReviewFileRelationVO.setReviewFileName(htReviewFileRelationBean.getReviewFileName());
                //文件路径(相对路径)
                htReviewFileRelationVO.setReviewFilePath(htReviewFileRelationBean.getReviewFilePath());
                htReviewFileRelationVOList.add(htReviewFileRelationVO);
            }
            return htReviewFileRelationVOList;
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 插入消息信息
     */
    @Override
    @Transactional
    public void excInsertMessageInfo(TbMessageEntity tbMessageEntity) throws SystemException{

        try {

            // 插入消息信息
            tbMessageEntityMapper.insertSelective(tbMessageEntity);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 判断项目是否结束
     */
    @Override
    @Transactional
    public void excIsOverProject(String projectId) throws LogicException,SystemException {

        try {

            // 通过项目id查询项目状态
            TbProjectEntity returnEntity = tbProjectEntityMapper.selectByPrimaryKey(projectId);

            // 如果项目已结束，则抛出异常
            if(StringToolUtils.STRING_ONE.equals(returnEntity.getProjectSts())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0012);
            }

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }



    /**
     * 校验查询合同是否已作废
     */
    @Transactional(rollbackFor = Exception.class)
    public void excIsInvalidContract(String contractId) throws LogicException, SystemException  {
        try {

            // 通过项目id查询项目状态
            TbContractEntity returnEntity = tbContractEntityMapper.selectByPrimaryKey(contractId);

            // 如果项目已结束，则抛出异常
            if(StringToolUtils.STRING_ONE.equals(returnEntity.getIsInvalidFlag())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0014);
            }
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 校验查询项目是否已作废
     */
    @Transactional(rollbackFor = Exception.class)
    public void excIsInvalidProject(String projectId) throws LogicException, SystemException  {
        try {

            // 通过项目id查询项目状态
            TbProjectEntity returnEntity = tbProjectEntityMapper.selectByPrimaryKey(projectId);

            // 如果项目已结束，则抛出异常
            if(StringToolUtils.STRING_ONE.equals(returnEntity.getIsInvalidFlag())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0015);
            }
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }
}
