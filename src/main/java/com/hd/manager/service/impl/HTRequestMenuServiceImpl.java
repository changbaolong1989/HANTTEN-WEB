package com.hd.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.enums.CashApplyEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbContractEntityMapper;
import com.hd.common.dao.TbRequestMenuEntityMapper;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.entity.TbMessageEntity;
import com.hd.common.entity.TbRequestMenuEntity;
import com.hd.common.service.CommonService;
import com.hd.common.util.*;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.dao.*;
import com.hd.manager.dao.bean.*;
import com.hd.manager.service.HTCashProcessService;
import com.hd.manager.service.HTRequestMenuService;
import com.hd.manager.vo.HTRequestMenuHisTaskInstVO;
import com.hd.manager.vo.HTRequestMenuVO;
import com.hd.manager.vo.HTUserQuestionRecordRelationVO;
import com.hd.manager.vo.HTUserVO;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 请款单ServiceImpl
 *
 * @author lihai
 * Created in 2019/8/1 14:45
 **/
@Service
public class HTRequestMenuServiceImpl extends BaseServiceImpl implements HTRequestMenuService {

    @Resource
    private HTCashProcessService cashProcessService;


    /**
     * 请款单Mapper(自动生成)对象
     */
    @Resource
    private TbRequestMenuEntityMapper tbRequestMenuEntityMapper;

    /**
     * 请款单Dao对象
     */
    @Resource
    private HTRequestMenuDao htRequestMenuDao;


    /**
     * 合同Mapper(自动生成)对象
     */
    @Resource
    private TbContractEntityMapper tbContractEntityMapper;

    /**
     * 请款单审核记录表Dao对象
     */
    @Resource
    private HTRequestMenuHisTaskInstDao htRequestMenuHisTaskInstDao;


    /**
     * 合同Dao对象
     */
    @Resource
    private HTContractDao htContractDao;


    /**
     * 审核记录和人员和问题类型关联表 DAO
     */
    @Resource
    private HTUserQuestionRecordRelationDao htUserQuestionRecordRelationDao;


    /**
     * 请款流程Service
     */
    @Resource
    HTCashProcessService hTCashProcessService;

    /**
     * 通用service
     */
    @Resource
    private CommonService commonService;

    /**
     * 添加请款单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String excInsertRequestMenuInfo(HTRequestMenuVO paramVO)  throws LogicException,SystemException {
        try {
            // =====================================判断项目是否结束 start=========================================

            TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(paramVO.getContractId());

            // 判断项目是否结束
            commonService.excIsOverProject(tbContractEntity.getProjectId());

            // =====================================判断项目是否结束 end=========================================
            Date currentDate = new Date();
            HTRequestMenuBean paramBean = new HTRequestMenuBean();
            //合同ID
            paramBean.setContractId(paramVO.getContractId());
            HTContractBean contractBean = htContractDao.getContractInfoByContractId(paramVO.getContractId());
            String requestMenuNum = null;
            Integer count = htRequestMenuDao.excSearchRequestMenuListCountByProperty(paramBean);
            //请款单名称
            paramBean.setRequestMenuName(paramVO.getRequestMenuName());
            //校验请款单名称是否重复
            Integer checkCount = htRequestMenuDao.excCheckRequestMenuListCountByProperty(paramBean);
            //重复时抛出异常
            if (null != checkCount&&checkCount!=0){
                throw new LogicException(MessageConstant.MSG_ERROR_REQUEST_MENU_0001);
            }
            //没有数据则初始化count值
            if (null == count){
                count = 0;
            }
            count++;
            //判断大小(用以拼接字符串)
            if (count<10){
                requestMenuNum = paramVO.getContractName()+"-00"+count;
            }else if (count<100){
                requestMenuNum = paramVO.getContractName()+"-0"+count;
            }else{
                requestMenuNum = paramVO.getContractName()+"-"+count;
            }
            TbRequestMenuEntity paramEntity = new TbRequestMenuEntity();
            //合同ID
            paramEntity.setContractId(paramVO.getContractId());
            //合同所属部门ID
            paramEntity.setDepartmentId(contractBean.getDepartmentId());
            //请款单名
            paramEntity.setRequestMenuName(paramVO.getRequestMenuName());
            //请款单编号
            paramEntity.setRequestMenuNum(requestMenuNum);
            //请款单编号
            paramEntity.setRequestType(Constant.REQUEST_MENU_FILE_TYPE);
            //创建人
            paramEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //创建时间
            paramEntity.setCreateDate(currentDate);
            //开始时间
            paramEntity.setStartDate(currentDate);
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //是否删除
            paramEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
            //添加请款单信息
            tbRequestMenuEntityMapper.insertSelective(paramEntity);
            //启动工作流
            HTUserVO user = paramVO.getLoginUserInfo();
            HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) ObjectReflectUtils.convertObj(paramEntity, HTRequestMenuVO.class);
            requestMenuVO.setLoginUserInfo(user);
            Map<String, Object> var = new HashMap<>();
            var.put(WorkFlowConstant.KEY_CASH_APPLY_VO, requestMenuVO);
            CommonActivitiListenVO listenVO = new CommonActivitiListenVO();
            listenVO.setDepartmentId(requestMenuVO.getDepartmentId());
            var.put(WorkFlowConstant.KEY_LISTENER_VO, listenVO);
            var.put(WorkFlowConstant.KEY_DELEGATE_USER, user.getLoginUserId());
            var.put(WorkFlowConstant.KEY_PRE_HANDLER, user);
            var.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            cashProcessService.excStart(var);
            return paramEntity.getRequestMenuId();
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }


    /**
     * 修改请款单名称
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUpdateRequestMenuName(HTRequestMenuVO paramVO)  throws LogicException,SystemException {
        try {
            //当前时间
            Date currentDate = new Date();
            HTRequestMenuBean paramBean = new HTRequestMenuBean();
            //请款单ID
            paramBean.setRequestMenuId(paramVO.getRequestMenuId());
            //请款单名称
            paramBean.setRequestMenuName(paramVO.getRequestMenuName());
            //原请款单信息
            HTRequestMenuBean requestMenuBean = htRequestMenuDao.excSearchRequestMenuInfoById(paramBean);
            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(requestMenuBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================
            TbRequestMenuEntity paramEntity = new TbRequestMenuEntity();
            //合同ID
            paramEntity.setContractId(paramVO.getContractId());
            //请款单ID
            paramEntity.setRequestMenuId(paramVO.getRequestMenuId());
            //请款单名
            paramEntity.setRequestMenuName(paramVO.getRequestMenuName());
            //校验请款单名称是否重复
            Integer checkCount = htRequestMenuDao.excCheckRequestMenuListCountByProperty(paramBean);
            //重复时抛出异常
            if (null != checkCount&&checkCount!=0){
                throw new LogicException(MessageConstant.MSG_ERROR_REQUEST_MENU_0001);
            }
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //删除标识
            paramEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
            //修改请款单名称
            tbRequestMenuEntityMapper.updateByPrimaryKeySelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }



    /**
     * 提交审核(修改请款单信息)
     */
    @Override
    @Transactional
    public void excUpdateRequestMenuInfo(HTRequestMenuVO paramVO, MultipartFile file, MultipartFile demoFile)  throws LogicException,SystemException {
        try {
            Date currentDate = getCurrentDate();
            //新请款单信息载体
            TbRequestMenuEntity paramEntity = new TbRequestMenuEntity();
            //查询参数载体
            HTRequestMenuBean paramBean = new HTRequestMenuBean();
            //请款单编号
            paramBean.setRequestMenuNum(paramVO.getRequestMenuNum());
            //请款单ID
            paramBean.setRequestMenuId(paramVO.getRequestMenuId());
            //查询是否有重复编号的请款单
            Integer count = htRequestMenuDao.excCheckRequestMenuListCountByProperty(paramBean);
            //是否有重复编号的请款单
            if (count>0){
                throw new LogicException(MessageConstant.MSG_ERROR_REQUEST_MENU_0002);
            }
            //原请款单信息
            HTRequestMenuBean requestMenuBean = htRequestMenuDao.excSearchRequestMenuInfoById(paramBean);
            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(requestMenuBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================
            if (null != demoFile){
                /*----------------文件上传信息-----------------*/
                String uuid = UUID.randomUUID().toString();
                // 文件上传后的路径
                String filePath = "";
                // 文件名
                String realFileName = demoFile.getOriginalFilename();
                // 获取文件的后缀名
                String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
                // 获取文件路径
                String fileName = uuid + suffixName;
                // 文件上传后的路径
                filePath = Constant.FILE_SEPARATE + "requestMenu" +Constant.FILE_SEPARATE + "requestNoteFile" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                //自定义模板真实名称
                paramEntity.setRequestNoteFileName(realFileName);
                //自定义模板文件路径
                paramEntity.setRequestNoteFilePath(filePath);
                // 上传文件
                    boolean isUploadSuccess = FileUtils.uploadFile(filePath, demoFile);
            }
            if ( StringToolUtils.STRING_ZERO.equals(paramVO.getRequestType())){
                if (null != file){
                    /*----------------文件上传信息-----------------*/
                    String uuid = UUID.randomUUID().toString();
                    // 文件上传后的路径
                    String filePath = "";
                    // 文件名
                    String realFileName = file.getOriginalFilename();
                    // 获取文件的后缀名
                    String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
                    // 获取文件路径
                    String fileName = uuid + suffixName;
                    // 文件上传后的路径
                    filePath = Constant.FILE_SEPARATE + "requestMenu" +Constant.FILE_SEPARATE + "requestFile" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                    //自定义模板真实名称
                    paramEntity.setRequestFileName(realFileName);
                    //自定义模板文件路径
                    paramEntity.setRequestFilePath(filePath);
                    // 上传文件
                    boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
                }
                /*-----------------------------------------*/
            }else{

                // 模板信息json
                paramEntity.setFrameworkContent(paramVO.getFrameworkContent());

                // 模板类型  默认0 目前只有一种
                paramEntity.setFrameworkType(StringToolUtils.STRING_ZERO);

            }

            //请款单ID
            paramEntity.setRequestMenuId(paramVO.getRequestMenuId());
            //合同ID
            paramEntity.setContractId(paramVO.getContractId());
            //请款单名
            paramEntity.setRequestMenuName(paramVO.getRequestMenuName());
            //档案编号
            paramEntity.setRequestMenuNum(paramVO.getRequestMenuNum());
            //工作截止时间
            paramEntity.setEndDate(DateToolUtils.convertDateFormat(paramVO.getEndDate(),DateToolUtils.yyyy_MM_dd));
            //请款金额
            paramEntity.setRequestAmount(NumberToolUtils.convertNullToDouble(paramVO.getRequestAmount()));
            //模板请款单类型
            paramEntity.setRequestType(paramVO.getRequestType());
            //所属高管
            paramEntity.setSeniorManagerId(paramVO.getSeniorManagerId());
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //是否删除
            paramEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);


            // 跳转节点
            requestMenuBean.setSubmitNode(paramVO.getSubmitNode());
            // 格式转换 Bean->VO
            HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) ObjectReflectUtils.convertObj(requestMenuBean, HTRequestMenuVO.class);

            // 设置当前登录人信息
            requestMenuVO.setLoginUserInfo(paramVO.getLoginUserInfo());

            //启动工作流
            Map<String, Object> variable = new HashMap<>();
            variable.put(WorkFlowConstant.KEY_CASH_APPLY_VO, requestMenuVO);
            variable.put(WorkFlowConstant.KEY_SUBMIT_NODE, requestMenuVO.getSubmitNode());
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            String departmentId = StringUtils.isBlank(CashApplyEnum.getDeptId(requestMenuVO.getSubmitNode())) ? requestMenuVO.getDepartmentId() : CashApplyEnum.getDeptId(requestMenuVO.getSubmitNode());
            commonActivitiListenVO.setDepartmentId(departmentId);
            variable.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
            variable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            cashProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(),requestMenuVO.getDelegateTaskId(), variable);

            if (!StringToolUtils.isNull(paramVO.getPredictTotal())){
                //合同实体
                TbContractEntity tbContractEntity = new TbContractEntity();
                TbContractEntity contractEntity = tbContractEntityMapper.selectByPrimaryKey(paramVO.getContractId());
                //合同ID
                tbContractEntity.setContractId(paramVO.getContractId());
                //预计合同总金额
                tbContractEntity.setPredictTotal(NumberToolUtils.convertNullToDouble(contractEntity.getPredictTotal())+Double.parseDouble(paramVO.getPredictTotal()));
                //修改合同预计合同总金额
                tbContractEntityMapper.updateByPrimaryKeySelective(tbContractEntity);
            }
            HTReviewFileRelationBean htReviewFileRelationBean = new HTReviewFileRelationBean();
            //事务唯一标识
            htReviewFileRelationBean.setBusinessId(paramVO.getRequestMenuId());
            //修改请款单信息
            tbRequestMenuEntityMapper.updateByPrimaryKeySelective(paramEntity);
            //判断原请款单是否以上传自定义原请款单模板文件
            if (null !=requestMenuBean.getRequestFilePath()&&null!=file){
                //删除自定义原请款单模板文件(物理删除)
                FileUtils.deleteFile(requestMenuBean.getRequestFilePath());
            }

            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(currentDate,DateToolUtils.yyyy_MM_dd);

            HTContractBean htContractBean = new HTContractBean();
            // 设置当前登录用户id
            htContractBean.setCurrentUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置部门id
            htContractBean.setDepartmentId(requestMenuBean.getDepartmentId());
            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            // 职位和查询得到的人员id
            HTTaskBean returnBean = commonService.excSearchUserIdForMessage(paramVO.getSubmitNode(),requestMenuBean.getDepartmentId());

            // 消息内容
            String tempMessageContentStr = "由"+htContractBeanList.get(0).getJobName()+"("+paramVO.getLoginUserInfo().getUserName()+")"+applyTime+"发起的"
                    +requestMenuBean.getProjectNum()+"项目"
                    +requestMenuBean.getProjectNum()+"-"+requestMenuBean.getContractNum()+"合同"
                    +requestMenuBean.getRequestMenuName()+"请款单该由您审核了";

            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置是否阅读 0：否    1：是
            tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
            // 设置创建时间
            tbMessageEntity.setCreateDate(currentDate);
            // 设置创建人id
            tbMessageEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置修改时间
            tbMessageEntity.setUpdateDate(currentDate);
            // 设置修改人id
            tbMessageEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置是否删除  0：否    1：是
            tbMessageEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);

            // 如果该职位有人员，则遍历人员信息，添加消息
            if(!ListToolUtils.isEmpty(returnBean.getHtTaskBeanList())){
                // 遍历人员信息
                for(HTTaskBean taskBean:returnBean.getHtTaskBeanList()){
                    // 设置接收消息人
                    tbMessageEntity.setAcceptUserId(taskBean.getUserId());
                    // 插入消息信息
                    commonService.excInsertMessageInfo(tbMessageEntity);

                    // 微信公众号推送模板消息
                    // loginUserId 当前登录用户id
                    // openId 用户唯一标识
                    // acceptUserName 接收人名称
                    // messageContent 消息内容
                    commonService.sendTemplateMessageToWeChat(paramVO.getLoginUserInfo().getLoginUserId()
                            ,taskBean.getOpenId(),taskBean.getUserName(),tempMessageContentStr);
                }
            }

            // ================================================ 插入消息信息 end =====================================================

        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 转换数据格式为json
     */
    @Override
    @Transactional
    public String  mapConvertToJson(HTRequestMenuVO paramVO)  throws LogicException,SystemException {

        Map<String, Object> retMap = new HashedMap();

        try {

            // 如果是自定义的时候，则抛出异常
            if (!StringToolUtils.STRING_ONE.equals(paramVO.getRequestType())){
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0006);
            }

            // 任务成果文件模板json串
            String tempFormworkJsonStr = paramVO.getFrameworkContent();

            // 如果json 串不为空则转成map格式
            if (!StringToolUtils.isNull(tempFormworkJsonStr)){

                // json转成map
                retMap = InterfaceUtils.jsonToObject(tempFormworkJsonStr,Map.class);
            }

        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }

        return JSON.toJSONString(retMap);
    }



    /**
     * 查询请款单详情信息
     */
    @Override
    public HTRequestMenuVO excSearchRequestMenuInfo(HTRequestMenuVO paramVO)  throws LogicException,SystemException {
        try {
            HTRequestMenuBean paramBean = new HTRequestMenuBean();
            paramBean.setRequestMenuId(paramVO.getRequestMenuId());
            HTRequestMenuBean requestMenuBean = htRequestMenuDao.excSearchRequestMenuInfoById(paramBean);
            //HTRequestMenuVO htRequestMenuVO = new HTRequestMenuVO();
            HTRequestMenuVO  htRequestMenuVO = (HTRequestMenuVO) ObjectReflectUtils.convertObj(requestMenuBean, HTRequestMenuVO.class);
            htRequestMenuVO.setRequestAmount(this.getNumberStr(null == requestMenuBean.getRequestAmount()?"0":String.valueOf(requestMenuBean.getRequestAmount())));
            // 转换时间，供消息中心使用
            htRequestMenuVO.setApplyTime(DateToolUtils.convertDateFormat(requestMenuBean.getApplyTime(),DateToolUtils.yyyy_MM_dd));
            //文件名称
            String materialName = null;

            //文件地址
            String materialPath = null;

            // onlyOffice使用的下载地址
            String tempOfficePath = null;

            // 模板请款单
            if (StringToolUtils.STRING_ONE.equals(requestMenuBean.getRequestType())){

                materialName = "2019HT请款申请模板.docx";

                materialPath = "/taskExecute/downLoad/downLoadRequestMenuWord?requestMenuId=" + paramVO.getRequestMenuId();

                tempOfficePath = "/taskExecute/downLoad/downLoadRequestMenuWord?requestMenuId=" + paramVO.getRequestMenuId();
            }else {
                // 自定义请款单
                materialName = requestMenuBean.getRequestFileName();

                materialPath = requestMenuBean.getRequestFilePath();

                tempOfficePath = Constant.COMMON_MODULE_URL+"/downloadFile?filePathName="+
                        materialPath+"&realFileName="+materialName;

            }


            htRequestMenuVO.setRequestFileName(materialName);

            htRequestMenuVO.setRequestFilePath(materialPath);


            //给onlyOffice使用的绝对路径
            htRequestMenuVO.setReviewFilePath(
                    OnlyOfficeUtils.getOnlyOfficeSaveCallbackUrl());
            //给onlyOffice使用的真实文件名称
            htRequestMenuVO.setReviewFileName(materialName);
            //给onlyOffice使用的唯一标识
            htRequestMenuVO.setOnlyOfficeEditKey(OnlyOfficeUtils.generateOnlyOfficeEditKey());
            //给onlyOffice使用的下载地址
            htRequestMenuVO.setOnlyOfficeAbsDownLoadPath(
                    OnlyOfficeUtils.getFileDownloadAbsoluteUrl(tempOfficePath));
            return htRequestMenuVO;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 删除请款单详情信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excDeleteRequestMenuInfo(HTRequestMenuVO paramVO)  throws LogicException,SystemException {
        try {
            HTRequestMenuBean paramBean = new HTRequestMenuBean();
            //请款单编号
            paramBean.setRequestMenuNum(paramVO.getRequestMenuNum());
            //请款单ID
            paramBean.setRequestMenuId(paramVO.getRequestMenuId());
            //原请款单信息
            HTRequestMenuBean requestMenuBean = htRequestMenuDao.excSearchRequestMenuInfoById(paramBean);
            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(requestMenuBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================
            tbRequestMenuEntityMapper.deleteByPrimaryKey(paramVO.getRequestMenuId());
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 请款单撤回
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excRecallQuestionMenu(HTRequestMenuVO paramVO)  throws LogicException,SystemException {
        try {
            HTRequestMenuVO htRequestMenuVO = this.excSearchRequestMenuInfo(paramVO);
            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htRequestMenuVO.getProjectId());

            // =====================================判断项目是否结束 end=========================================
            //校验数据是否被修改过
            if (!htRequestMenuVO.getDelegateTaskId().equals(paramVO.getDelegateTaskId())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            //启动工作流
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            //添加登录人信息
            htRequestMenuVO.setLoginUserInfo(userInfo);
            //审批人
            htRequestMenuVO.setApproveUserId(userInfo.getLoginUserId());
            //审批人名称
            htRequestMenuVO.setApproveUserName(userInfo.getUserName());
            //未通过
            htRequestMenuVO.setIsPass(Constant.NOT_APPROVED);
            cashProcessService.excRecallTask(htRequestMenuVO);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 上传发票
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUploadBill(HTRequestMenuVO paramVO, MultipartFile file)  throws LogicException,SystemException {
        try {
            //得到原数据信息
            HTRequestMenuVO htRequestMenuVO = this.excSearchRequestMenuInfo(paramVO);
            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htRequestMenuVO.getProjectId());

            // =====================================判断项目是否结束 end=========================================
            if (!htRequestMenuVO.getUpdateDate().equals(paramVO.getUpdateDate())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            //删除原始文件
            FileUtils.deleteFile(htRequestMenuVO.getInvoiceFilePath());
            //当前时间
            Date currentDate = new Date();
            TbRequestMenuEntity paramEntity = new TbRequestMenuEntity();
            //唯一标识
            paramEntity.setRequestMenuId(paramVO.getRequestMenuId());
            String uuid = paramVO.getRequestMenuId();
            // 文件上传后的路径
            String filePath = "";
            // 文件名
            String realFileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
            // 获取文件路径
            String fileName = uuid + suffixName;
            // 文件上传后的路径
            filePath = Constant.FILE_SEPARATE + "requestMenu" +Constant.FILE_SEPARATE + "bill" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
            //存入发票金额
//            paramEntity.setInvoiceAmount(NumberToolUtils.convertNullToDouble(paramVO.getInvoiceAmount()));
            //存入发票文件路径
            paramEntity.setInvoiceFilePath(filePath);
            //存入发票文件名称
            paramEntity.setInvoiceFileName(realFileName);
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改请款单
            tbRequestMenuEntityMapper.updateByPrimaryKeySelective(paramEntity);
            // 上传文件
            boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 上传发票申请单附件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HTRequestMenuVO excUploadInvoiceApplyFile(HTRequestMenuVO paramVO, MultipartFile[] files) throws LogicException, SystemException {
        HTRequestMenuVO result = new HTRequestMenuVO();
        try {
            //得到原数据信息
            HTRequestMenuVO htRequestMenuVO = this.excSearchRequestMenuInfo(paramVO);
            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htRequestMenuVO.getProjectId());

            //当前时间
            Date currentDate = new Date();
            TbRequestMenuEntity paramEntity = new TbRequestMenuEntity();
            //唯一标识
            paramEntity.setRequestMenuId(paramVO.getRequestMenuId());
            String uuid = paramVO.getRequestMenuId();
            // 文件上传后的路径
            String sumFilePath = "";
            String sumFileName = "";
            for(MultipartFile file : files){
                String filePath = "";
                // 文件名
                String realFileName = file.getOriginalFilename();
                // 获取文件的后缀名
                String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
                // 获取文件路径
                String fileName = UUID.randomUUID().toString() + suffixName;
                // 文件上传后的路径
                filePath = Constant.FILE_SEPARATE + "requestMenu" +Constant.FILE_SEPARATE + "bill" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                // 上传文件
                FileUtils.uploadFile(filePath, file);
                sumFilePath += filePath + "|";
                sumFileName += realFileName + "|";
            }
            result.setRequestNoteFilePath(sumFilePath);
            result.setRequestNoteFileName(sumFileName);
            result.setInvoiceApplyRemark(paramVO.getInvoiceApplyRemark());
            if(sumFilePath.length()>0){
                sumFilePath = sumFilePath.substring(0, sumFilePath.length()-1);
                sumFileName = sumFileName.substring(0, sumFileName.length()-1);
                sumFilePath = StringUtils.isBlank(htRequestMenuVO.getRequestNoteFilePath()) ? sumFilePath : htRequestMenuVO.getRequestNoteFilePath() + "|" + sumFilePath;
                sumFileName = StringUtils.isBlank(htRequestMenuVO.getRequestNoteFileName()) ? sumFileName : htRequestMenuVO.getRequestNoteFileName() + "|" + sumFileName;
                paramEntity.setRequestNoteFilePath(sumFilePath);
                paramEntity.setRequestNoteFileName(sumFileName);
                paramEntity.setRequestNoteRemark(paramVO.getInvoiceApplyRemark());
            }
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改请款单
            tbRequestMenuEntityMapper.updateByPrimaryKeySelective(paramEntity);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
        return result;
    }

    @Override
    @Transactional
    public void excDeleteInvoiceApplyFile(HTRequestMenuVO paramVO) throws LogicException, SystemException {
        String deletePath = paramVO.getRequestNoteFilePath();
        String requestMenuId = paramVO.getRequestMenuId();
        //删除文件信息
        TbRequestMenuEntity requestMenu = tbRequestMenuEntityMapper.selectByPrimaryKey(requestMenuId);
        String[] requestNotePaths = requestMenu.getRequestNoteFilePath().split("\\|");
        String[] requestNoteNames = requestMenu.getRequestNoteFileName().split("\\|");
        int index = 0;
        for (int i = 0; i < requestNotePaths.length; i++) {
            if (deletePath.equals(requestNotePaths[i])) {
                index = i;
                break;
            }
        }
        List<String> pathList = new ArrayList<>(Arrays.asList(requestNotePaths));
        List<String> nameList = new ArrayList<>(Arrays.asList(requestNoteNames));
        pathList.remove(deletePath);
        nameList.remove(index);
        requestMenu.setRequestNoteFilePath(StringUtils.join(pathList.toArray(),"|"));
        requestMenu.setRequestNoteFileName(StringUtils.join(nameList.toArray(),"|"));
        tbRequestMenuEntityMapper.updateByPrimaryKeySelective(requestMenu);
        //删除真实文件
        FileUtils.deleteFile(deletePath);
    }

    /**
     * 提交发票
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excCommitBillStep(HTRequestMenuVO paramVO) throws LogicException, SystemException {
        try {
            HTRequestMenuVO htRequestMenuVO = this.excSearchRequestMenuInfo(paramVO);
            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htRequestMenuVO.getProjectId());

            // =====================================判断项目是否结束 end=========================================
            if (!htRequestMenuVO.getUpdateDate().equals(paramVO.getUpdateDate())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            if (!htRequestMenuVO.getDelegateTaskId().equals(paramVO.getDelegateTaskId())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            //启动工作流
            Map<String, Object> variables = new HashMap<>();
            // 登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            // 登录人信息
            htRequestMenuVO.setLoginUserInfo(userInfo);
            // 审批人ID
            htRequestMenuVO.setApproveUserId(userInfo.getLoginUserId());
            // 审批人名称
            htRequestMenuVO.setApproveUserName(userInfo.getUserName());
            variables.put(WorkFlowConstant.KEY_CASH_APPLY_VO, htRequestMenuVO);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            String departmentId = StringUtils.isBlank(CashApplyEnum.getDeptId(htRequestMenuVO.getSubmitNode())) ? htRequestMenuVO.getDepartmentId() : CashApplyEnum.getDeptId(htRequestMenuVO.getSubmitNode());
            commonActivitiListenVO.setDepartmentId(departmentId);
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            cashProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }


    /**
     * 请款单审核
     */
    @Override
    @Transactional
    public void excAuditingQuestionMenu(HTRequestMenuVO paramVO)  throws LogicException,SystemException {
        try {
            //当前时间
            Date currentDate = new Date();
            // 是否通过
            String isPass = paramVO.getIsPass();
            //请款详情实体
            HTRequestMenuVO htRequestMenuVO = this.excSearchRequestMenuInfo(paramVO);
            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htRequestMenuVO.getProjectId());

            // =====================================判断项目是否结束 end=========================================
            //校验数据是否被修改
            if (!htRequestMenuVO.getDelegateTaskId().equals(paramVO.getDelegateTaskId())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            //修改时间
            paramVO.setUpdateDate(DateToolUtils.convertDateFormat(currentDate,DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            //修改人
            paramVO.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //校验驳回意见是否为空
//            if (Constant.NOT_APPROVED.equals(isPass)&&StringToolUtils.isNull(paramVO.getRejectReason())){
//                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0013);
//            }
            if (Constant.NOT_APPROVED.equals(isPass)){
                if (null == paramVO.getUserQuestionRecordRelationList()||paramVO.getUserQuestionRecordRelationList().size()==0 ){
                    throw new LogicException(MessageConstant.MSG_ERROR_TASK_0013);
                }
            }
            //如果是驳回
            if (Constant.NOT_APPROVED.equals(isPass)&&!StringToolUtils.isNull(paramVO.getOnlyOfficeEditKey())) {
                String uuid = UUID.randomUUID().toString();
                // 文件上传后的路径
                String filePath = "";
                // 文件名
                String realFileName = htRequestMenuVO.getRequestFileName();
                // 获取文件的后缀名
                String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
                // 获取文件路径
                String fileName = uuid + suffixName;
                // 文件上传后的路径
                filePath = Constant.FILE_SEPARATE + "review" + Constant.FILE_SEPARATE +"requestMenu" +Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                //文件地址
                paramVO.setReviewFilePath(filePath);
                // 获取文件的真实名称
                paramVO.setReviewFileName(htRequestMenuVO.getRequestFileName());
                // 获取文件的创建时间
                paramVO.setCreateDate(DateToolUtils.convertDateFormat(currentDate,DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                // 获取文件的创建人
                paramVO.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                //通知OnlyOffice documentServer 保存文件
                OnlyOfficeUtils.notifyOnlyOfficeToSave(paramVO);
            }

            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = htRequestMenuVO.getApplyTime();

            // 当前审核节点
            String delegateTaskKey = htRequestMenuVO.getDelegateTaskKey();

            // 职位名称
            String jobName = "";
            boolean isLastLevelVerify = false;

            switch (delegateTaskKey){
                // 部门经理
                case "ManagerVerify":
                    jobName = CashApplyEnum.ManagerVerify.getJobName();
                    break;

                // 质控部组员
                case "QualityVerify":
                    jobName = CashApplyEnum.QualityVerify.getJobName();
                    break;

                // 质控部副总经理
                case "GenerManagerVerify":
                    jobName = CashApplyEnum.GenerManagerVerify.getJobName();
                    isLastLevelVerify = true;
                    break;

                default:
                    break;
            }

            // 审核结果字符串
            String tempAuditResultStr = "通过";

            // 审核结果:驳回
            if (Constant.NOT_APPROVED.equals(isPass)) {
                tempAuditResultStr = "驳回";
            }

            String tempMessageContentStr = "您在 "+applyTime+" 发起的 "
                    +htRequestMenuVO.getProjectNum()+"项目"
                    +htRequestMenuVO.getProjectNum()+"-"+htRequestMenuVO.getContractNum()+"合同"
                    +htRequestMenuVO.getRequestMenuName()+"请款单 申请 ，被 "
                    +jobName+"("+paramVO.getLoginUserInfo().getUserName()+")审核并"+tempAuditResultStr;

            if(isLastLevelVerify && Constant.APPROVED.equals(isPass)){
                tempMessageContentStr += "，请检查并上传发票申请单附件。";
            }


            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(htRequestMenuVO.getApplyUserId());
            // 设置是否阅读 0：否    1：是
            tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
            // 设置创建时间
            tbMessageEntity.setCreateDate(currentDate);
            // 设置创建人id
            tbMessageEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置修改时间
            tbMessageEntity.setUpdateDate(currentDate);
            // 设置修改人id
            tbMessageEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置是否删除  0：否    1：是
            tbMessageEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);
            // 插入消息信息
            commonService.excInsertMessageInfo(tbMessageEntity);

            // 微信公众号推送模板消息
            // loginUserId 当前登录用户id
            // openId 用户唯一标识
            // acceptUserName 接收人名称
            // messageContent 消息内容
            commonService.sendTemplateMessageToWeChat(paramVO.getLoginUserInfo().getLoginUserId()
                    ,htRequestMenuVO.getOpenId(),htRequestMenuVO.getApplyUserName(),tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

            htRequestMenuVO.setSubmitNode(paramVO.getSubmitNode());// 跳转节点
            Map<String, Object> variables = new HashMap<>();
            //登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            //登录人信息
            htRequestMenuVO.setLoginUserInfo(userInfo);
            //审批人ID
            htRequestMenuVO.setApproveUserId(userInfo.getLoginUserId());
            //审批人名称
            htRequestMenuVO.setApproveUserName(userInfo.getUserName());
            variables.put(WorkFlowConstant.KEY_CASH_APPLY_VO, htRequestMenuVO);
            // 是否通过
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, isPass);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            if (Constant.NOT_APPROVED.equals(isPass)) {
                variables.put(WorkFlowConstant.KEY_REJECT_REASON, paramVO.getRejectReason());
                variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, htRequestMenuVO.getDelegateTaskKey());
                commonActivitiListenVO.setDepartmentId(htRequestMenuVO.getDepartmentId());

            } else {
                String departmentId = StringUtils.isBlank(CashApplyEnum.getDeptId(htRequestMenuVO.getSubmitNode())) ? htRequestMenuVO.getDepartmentId() : CashApplyEnum.getDeptId(htRequestMenuVO.getSubmitNode());
                if(CashApplyEnum.IssueInvoice.getNodeKey().equals(htRequestMenuVO.getDelegateTaskKey())){
                    departmentId = CashApplyEnum.IssueInvoice.getDepartmentId();
                }
                commonActivitiListenVO.setDepartmentId(departmentId);
                variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, htRequestMenuVO.getSubmitNode());
            }
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            String historyId = cashProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);
            //修改时间
            paramVO.setUpdateDate(DateToolUtils.convertDateFormat(currentDate,DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            //修改人
            paramVO.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            if (Constant.NOT_APPROVED.equals(isPass)) {
                if (null!= paramVO.getUserQuestionRecordRelationList()&&paramVO.getUserQuestionRecordRelationList().size()>0 ){
                    //批量插入审核记录和人员和问题类型关联
                    List<HTUserQuestionRecordRelationVO> relationVOList = paramVO.getUserQuestionRecordRelationList();
                    List<HTUserQuestionRecordRelationBean>  relationBeanList = new ArrayList<>();
                    for (HTUserQuestionRecordRelationVO htUserQuestionRecordRelationVO:relationVOList){
                        HTUserQuestionRecordRelationBean htUserQuestionRecordRelationBean = new HTUserQuestionRecordRelationBean();
                        //唯一标识
                        htUserQuestionRecordRelationBean.setRelationId(UUID.randomUUID().toString());
                        //业务ID
                        htUserQuestionRecordRelationBean.setBusinessId(paramVO.getRequestMenuId());
                        //历史id
                        htUserQuestionRecordRelationBean.setRecordId(historyId);
                        //人员ID
                        htUserQuestionRecordRelationBean.setUserId(htUserQuestionRecordRelationVO.getUserId());
                        //问题ID
                        htUserQuestionRecordRelationBean.setQuestionId(htUserQuestionRecordRelationVO.getQuestionId());
                        //问题个数
                        htUserQuestionRecordRelationBean.setQuestionNum(htUserQuestionRecordRelationVO.getQuestionNum());
                        //来源4:请款单
                        htUserQuestionRecordRelationBean.setSourceType(Constant.REQUEST_MENU_SOURCE_TYPE);
                        //创建时间
                        htUserQuestionRecordRelationBean.setCreateDate(currentDate);
                        //创建人
                        htUserQuestionRecordRelationBean.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                        //修改时间
                        htUserQuestionRecordRelationBean.setUpdateDate(currentDate);
                        //修改人
                        htUserQuestionRecordRelationBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                        //是否删除
                        htUserQuestionRecordRelationBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
                        relationBeanList.add(htUserQuestionRecordRelationBean);
                    }
                    htUserQuestionRecordRelationDao.excInsertUserQuestionRecordRelations(relationBeanList);
                }
            }
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 财务开具发票
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUploadInvoiceFile(HTRequestMenuVO paramVO, MultipartFile file) throws LogicException, SystemException {
        try {
            //得到原数据信息
            HTRequestMenuVO htRequestMenuVO = this.excSearchRequestMenuInfo(paramVO);
            // =====================================判断项目是否结束 start=========================================
            // 判断项目是否结束
            commonService.excIsOverProject(htRequestMenuVO.getProjectId());
            // =====================================判断项目是否结束 end=========================================
            if (!htRequestMenuVO.getUpdateDate().equals(paramVO.getUpdateDate())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            //删除原始文件
            FileUtils.deleteFile(htRequestMenuVO.getRequestInvoicePath());
            //当前时间
            Date currentDate = new Date();
            TbRequestMenuEntity paramEntity = new TbRequestMenuEntity();
            //唯一标识
            paramEntity.setRequestMenuId(paramVO.getRequestMenuId());
            String uuid = paramVO.getRequestMenuId();
            // 文件上传后的路径
            String filePath = "";
            // 文件名
            String realFileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
            // 获取文件路径
            String fileName = UUID.randomUUID().toString() + suffixName;
            // 文件上传后的路径
            filePath = Constant.FILE_SEPARATE + "requestMenu" +Constant.FILE_SEPARATE + "finance" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
            //存入打款凭证金额
            paramEntity.setInvoiceAmount(NumberToolUtils.convertNullToDouble(paramVO.getInvoiceAmount()));
            //存入文件路径
            paramEntity.setRequestInvoicePath(filePath);
            //存入打款凭证文件名称
            paramEntity.setRequestInvoiceName(realFileName);
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改请款单
            tbRequestMenuEntityMapper.updateByPrimaryKeySelective(paramEntity);
            // 上传文件
            FileUtils.uploadFile(filePath, file);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 上传打款凭证
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUploadPaymentVoucher(HTRequestMenuVO paramVO, MultipartFile file)  throws LogicException,SystemException {
        try {
            //得到原数据信息
            HTRequestMenuVO htRequestMenuVO = this.excSearchRequestMenuInfo(paramVO);
            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htRequestMenuVO.getProjectId());

            // =====================================判断项目是否结束 end=========================================
            if (!htRequestMenuVO.getUpdateDate().equals(paramVO.getUpdateDate())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            //删除原始文件
            FileUtils.deleteFile(htRequestMenuVO.getPaymentFilePath());
            //当前时间
            Date currentDate = new Date();
            TbRequestMenuEntity paramEntity = new TbRequestMenuEntity();
            //唯一标识
            paramEntity.setRequestMenuId(paramVO.getRequestMenuId());
            String uuid = paramVO.getRequestMenuId();
            // 文件上传后的路径
            String filePath = "";
            // 文件名
            String realFileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
            // 获取文件路径
            String fileName = uuid + suffixName;
            // 文件上传后的路径
            filePath = Constant.FILE_SEPARATE + "requestMenu" +Constant.FILE_SEPARATE + "voucher" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
            //存入打款凭证金额
            paramEntity.setPaymentAmount(NumberToolUtils.convertNullToDouble(paramVO.getPaymentAmount()));
            //存入打款凭证文件路径
            paramEntity.setPaymentFilePath(filePath);
            //存入打款凭证文件名称
            paramEntity.setPaymentFileName(realFileName);
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改请款单
            tbRequestMenuEntityMapper.updateByPrimaryKeySelective(paramEntity);
            // 上传文件
            boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 确认到款
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excConfirmVoucherStep(HTRequestMenuVO paramVO)  throws LogicException,SystemException {
        try {
            HTRequestMenuVO htRequestMenuVO = this.excSearchRequestMenuInfo(paramVO);
            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htRequestMenuVO.getProjectId());

            // =====================================判断项目是否结束 end=========================================
            if (!htRequestMenuVO.getUpdateDate().equals(paramVO.getUpdateDate())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            if (!htRequestMenuVO.getDelegateTaskId().equals(paramVO.getDelegateTaskId())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            //启动工作流
            Map<String, Object> variables = new HashMap<>();
            // 登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            // 登录人信息
            htRequestMenuVO.setLoginUserInfo(userInfo);
            // 审批人ID
            htRequestMenuVO.setApproveUserId(userInfo.getLoginUserId());
            // 审批人名称
            htRequestMenuVO.setApproveUserName(userInfo.getUserName());
            variables.put(WorkFlowConstant.KEY_CASH_APPLY_VO, htRequestMenuVO);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            String departmentId = StringUtils.isBlank(CashApplyEnum.getDeptId(htRequestMenuVO.getSubmitNode())) ? htRequestMenuVO.getDepartmentId() : CashApplyEnum.getDeptId(htRequestMenuVO.getSubmitNode());
            commonActivitiListenVO.setDepartmentId(departmentId);
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            cashProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询审核意见信息
     * @return 审核意见信息
     */
    @Override
    public List<HTRequestMenuHisTaskInstVO> excSearchHTRequestMenuHisTaskInst(HTRequestMenuHisTaskInstVO paramVO) throws LogicException, SystemException {
        try {

            HTRequestMenuHisTaskInstBean paramBean = new HTRequestMenuHisTaskInstBean();
            paramBean.setRequestMenuId(paramVO.getRequestMenuId());
            //查询审核意见信息
            List<HTRequestMenuHisTaskInstBean> htRequestMenuHisTaskInstBeanList = htRequestMenuHisTaskInstDao.excSearchHTRequestMenuHisTaskInstInfoList(paramBean);
            List<HTRequestMenuHisTaskInstVO> resultList = new ArrayList<>();
            /*格式转换*/
            for (HTRequestMenuHisTaskInstBean htRequestMenuHisTaskInstBean:htRequestMenuHisTaskInstBeanList){
                HTRequestMenuHisTaskInstVO htTenderTaskHisTaskInstVO = new HTRequestMenuHisTaskInstVO();

                //任务Key
                htTenderTaskHisTaskInstVO.setTaskKey(htRequestMenuHisTaskInstBean.getTaskKey());
                //唯一标识
                htTenderTaskHisTaskInstVO.setHistoryId(htRequestMenuHisTaskInstBean.getHistoryId());
                //请款单ID
                htTenderTaskHisTaskInstVO.setRequestMenuId(htRequestMenuHisTaskInstBean.getRequestMenuId());
                //审批人
                htTenderTaskHisTaskInstVO.setApproveUserId(htRequestMenuHisTaskInstBean.getApproveUserId());
                //审批人名称
                htTenderTaskHisTaskInstVO.setApproveUserName(htRequestMenuHisTaskInstBean.getApproveUserName());
                List<HTRequestMenuHisTaskInstBean> childHisTaskInstBeanList = htRequestMenuHisTaskInstBean.getHtRequestMenuHisTaskInstList();
                List<HTRequestMenuHisTaskInstVO> childHisTaskInstVOList = new ArrayList<>();
                for (HTRequestMenuHisTaskInstBean childHisTaskInstBean:childHisTaskInstBeanList){
                    HTRequestMenuHisTaskInstVO childHisTaskInstVO = new HTRequestMenuHisTaskInstVO();
                    //任务Key
                    childHisTaskInstVO.setTaskKey(childHisTaskInstBean.getTaskKey());
                    //唯一标识
                    childHisTaskInstVO.setHistoryId(childHisTaskInstBean.getHistoryId());
                    //请款单ID
                    childHisTaskInstVO.setRequestMenuId(childHisTaskInstBean.getRequestMenuId());
                    //拒绝原因
                    childHisTaskInstVO.setRejectReason(childHisTaskInstBean.getRejectReason());
                    //审批人
                    childHisTaskInstVO.setApproveUserId(childHisTaskInstBean.getApproveUserId());
                    //审批人签名路径
                    childHisTaskInstVO.setSignPath(childHisTaskInstBean.getSignPath());
                    //审批人名称
                    childHisTaskInstVO.setApproveUserName(childHisTaskInstBean.getApproveUserName());
                    //审批时间
                    childHisTaskInstVO.setApproveTime(DateToolUtils.convertDateFormat(childHisTaskInstBean.getApproveTime(),DateToolUtils.yyyy_MM_dd));
                    //审批人名称
                    childHisTaskInstVO.setIsPass(childHisTaskInstBean.getIsPass());
                    //成员问题关联数据
                    List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList = childHisTaskInstBean.getHtUserQuestionRecordRelationBeanList();
                    List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList = new ArrayList<>();
                    for (HTUserQuestionRecordRelationBean htUserQuestionRecordRelationBean:htUserQuestionRecordRelationBeanList){
                        HTUserQuestionRecordRelationVO htUserQuestionRecordRelationVO = new HTUserQuestionRecordRelationVO();
                        //唯一标识
                        htUserQuestionRecordRelationVO.setRelationId(htUserQuestionRecordRelationBean.getRelationId());
                        //审核记录ID
                        htUserQuestionRecordRelationVO.setRecordId(htUserQuestionRecordRelationBean.getRecordId());
                        //问题类型ID
                        htUserQuestionRecordRelationVO.setQuestionId(htUserQuestionRecordRelationBean.getQuestionId());
                        //问题名称
                        htUserQuestionRecordRelationVO.setQuestionName(htUserQuestionRecordRelationBean.getQuestionName());
                        //错误次数
                        htUserQuestionRecordRelationVO.setQuestionNum(htUserQuestionRecordRelationBean.getQuestionNum());
                        //来源类型（0：合同 1：普通任务 2：投标任务 ）
                        htUserQuestionRecordRelationVO.setSourceType(htUserQuestionRecordRelationBean.getSourceType());
                        //人员ID
                        htUserQuestionRecordRelationVO.setUserId(htUserQuestionRecordRelationBean.getUserId());
                        //人员名称
                        htUserQuestionRecordRelationVO.setUserName(htUserQuestionRecordRelationBean.getUserName());
                        htUserQuestionRecordRelationVOList.add(htUserQuestionRecordRelationVO);
                    }
                    childHisTaskInstVO.setHtUserQuestionRecordRelationVOList(htUserQuestionRecordRelationVOList);
                    childHisTaskInstVOList.add(childHisTaskInstVO);
                }
                htTenderTaskHisTaskInstVO.setHtRequestMenuHisTaskInstList(childHisTaskInstVOList);
                resultList.add(htTenderTaskHisTaskInstVO);
            }
            return resultList;
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex,MessageConstant.MSG_ERROR_COMMON_0002);
        }

    }

    /**
     * 查询角色身份
     * @return 角色身份
     */
    @Override
    public String excShowUserType(HTRequestMenuVO paramVO)  throws LogicException,SystemException {
        HTRequestMenuVO htRequestMenuVO = this.excSearchRequestMenuInfo(paramVO);
        //角色身份
        String showUserType = null;
        try {
            TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(htRequestMenuVO.getContractId());
            htRequestMenuVO.setIsInvalidFlag(tbContractEntity.getIsInvalidFlag());
            //查询角色身份
            showUserType = hTCashProcessService.excQueryPageType(paramVO.getLoginUserInfo(),htRequestMenuVO.getDepartmentId(), ProcessDictEnum.CashApply.getProcessDictId(), htRequestMenuVO, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showUserType;
    }

    /**
     * 将科学计数法的字符串传入
     * @param str
     * @return 返回String类型
     */
    private String getNumberStr(String str){
        String result = "";
        if (!StringToolUtils.isNull(str)){
            BigDecimal bd = new BigDecimal(str);
            result = bd.toPlainString();
        }
        return result;
    }
}
