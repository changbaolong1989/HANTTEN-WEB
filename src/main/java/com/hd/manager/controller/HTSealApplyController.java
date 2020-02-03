package com.hd.manager.controller;

import com.hd.base.controller.BaseController;
import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.dao.TbUserEntityMapper;
import com.hd.common.entity.*;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.service.CommonCacheService;
import com.hd.common.service.CommonService;
import com.hd.common.util.*;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.export.bean.ExcelData;
import com.hd.export.bean.ExportData;
import com.hd.export.service.impl.ExcelExport;
import com.hd.manager.dao.HTContractDao;
import com.hd.manager.dao.HTSealApplyDao;
import com.hd.manager.dao.bean.HTContractBean;
import com.hd.manager.dao.bean.HTTaskBean;
import com.hd.manager.service.HTSealApplyService;
import com.hd.manager.vo.HTSealApplyVO;
import com.hd.manager.vo.HTUserVO;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * 请章相关Controller
 */
@Controller
@CrossOrigin(allowCredentials = "true")
public class HTSealApplyController extends BaseController {

    @Resource
    private HTSealApplyService htSealApplyService;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private CommonCacheService commonCacheService;

    @Resource
    private ExcelExport excelExport;

    @Resource
    private HTSealApplyDao sealApplyDao;

    //指派审核人节点ID
    private static final String ASSIGN_NODE_ID = "AssignHandleVerify";

    //请章枚举
    private static final ProcessDictEnum sealApplyEnum = ProcessDictEnum.SealApply;

    //历史任务
    private static final String IS_HISTORY_TASK = "1";
    //当前任务
    private static final String CURRENT_TASK = "0";

    /**
     * 通用service
     */
    @Resource
    private CommonService commonService;

    /**
     * 合同dao
     */
    @Resource
    private HTContractDao htContractDao;

    /**
     * 用户mapper
     */
    @Resource
    private TbUserEntityMapper tbUserEntityMapper;

    /**
     * 提交请章申请 启动流程
     *
     * @return 请章记录页面
     */
    @RequestMapping("/workbench/sealApply/insert/submitSealApply")
    @ResponseBody
    public Map<String, Object> submitSealApply(HttpServletRequest request, HTSealApplyVO sealApplyVO, MultipartFile resourceFiles) {
        Map<String, Object> resultMap;
        try {
            HTUserVO user = (HTUserVO) request.getSession().getAttribute("loginUserInfo");
            String departmentId = sealApplyVO.getDepartmentId();
            String userId = user.getLoginUserId();
            String bizKey = UUID.randomUUID().toString();//业务主键
            sealApplyVO.setRecordId(bizKey);
            sealApplyVO.setApplyUserName(user.getUserName());
            // 设置附件信息
            if (null != resourceFiles) {
                // 源文件名
                String signPathFileName = resourceFiles.getOriginalFilename();
                // 文件上传后的路径
                String signPathFilePath = Constant.FILE_SEPARATE + "sealApply" + Constant.FILE_SEPARATE + bizKey + Constant.FILE_SEPARATE;
                // 自定义上传文件的文件名称（公告id+.后缀名）
                String tempFileNameStr = bizKey + signPathFileName.substring(signPathFileName.lastIndexOf("."));
                // 设置附件路径
                sealApplyVO.setAttachmentPath(signPathFilePath + tempFileNameStr);
                // 设置附件名称
                sealApplyVO.setAttachmentName(StringToolUtils.convertNullObjectToString(signPathFileName));
            }
            //设置流程变量
            Map<String, Object> variables = new HashMap<>();
            variables.put(WorkFlowConstant.KEY_SEAL_APPLY_VO, sealApplyVO);//请章对象
            variables.put(WorkFlowConstant.KEY_DEPARTMENT_ID, departmentId);//部门ID
            variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, sealApplyEnum.getProcessDictId());//流程类型
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, new CommonActivitiListenVO());//流程监听对象
            variables.put(WorkFlowConstant.KEY_APPLY_USER_ID, userId);//申请人ID
            variables.put(WorkFlowConstant.KEY_RESOURCE_FILES, resourceFiles);//文件
            variables.put(WorkFlowConstant.KEY_DELEGATE_USER, sealApplyVO.getDelegateUser());//指定审批人
            // 设置用户信息
            sealApplyVO.setLoginUserInfo(user);
            //提交请章申请
            ProcessInstance processInstance = htSealApplyService.excStart(variables);
            logger.info("请章流程已经启动。流程实例ID为：" + processInstance.getId());
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));

            // ================================================ 插入消息信息 start =====================================================

            Date currentDate = getCurrentDate();

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(getCurrentDate(), DateToolUtils.yyyy_MM_dd);

            HTContractBean htContractBean = new HTContractBean();
            // 设置当前登录用户id
            htContractBean.setCurrentUserId(userId);
            // 设置部门id
            htContractBean.setDepartmentId(departmentId);
            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            // 经理 副经理
            String job = "ManagerVerify";

            if (!StringToolUtils.isNull(sealApplyVO.getDelegateUser())) {
                // 签发人
                job = "Signer";
            }

            // 职位和查询得到的人员id
            HTTaskBean returnBean = commonService.excSearchUserIdForMessage(job, departmentId);

            // 消息内容
            String tempMessageContentStr = "由" + htContractBeanList.get(0).getJobName() + "(" + user.getUserName() + ")" + applyTime + "发起的"
                    + "请章该由您审核了";

            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置是否阅读 0：否    1：是
            tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
            // 设置创建时间
            tbMessageEntity.setCreateDate(currentDate);
            // 设置创建人id
            tbMessageEntity.setCreateUserId(userId);
            // 设置修改时间
            tbMessageEntity.setUpdateDate(currentDate);
            // 设置修改人id
            tbMessageEntity.setUpdateUserId(userId);
            // 设置是否删除  0：否    1：是
            tbMessageEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);

            // 如果该职位有人员，则遍历人员信息，添加消息
            if (!ListToolUtils.isEmpty(returnBean.getHtTaskBeanList())) {
                // 遍历人员信息
                for (HTTaskBean taskBean : returnBean.getHtTaskBeanList()) {
                    // 设置接收消息人
                    tbMessageEntity.setAcceptUserId(taskBean.getUserId());
                    // 插入消息信息
                    commonService.excInsertMessageInfo(tbMessageEntity);

                    // 微信公众号推送模板消息
                    // loginUserId 当前登录用户id
                    // openId 用户唯一标识
                    // acceptUserName 接收人名称
                    // messageContent 消息内容
                    commonService.sendTemplateMessageToWeChat(userId
                            , taskBean.getOpenId(), taskBean.getUserName(), tempMessageContentStr);

                }
            }

            // ================================================ 插入消息信息 end =====================================================

        } catch (LogicException e) {
            resultMap = setReturnMessageMap(FAILURE, e.getMsgDes());
            e.printStackTrace();
        } catch (SystemException e) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, e.getMsgDes());
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 请章审批
     *
     * @return 审批页面
     */
    @RequestMapping("/workbench/sealApply/modify/verify")
    @ResponseBody
    public Map<String, Object> verify(HttpServletRequest request, @RequestBody HTSealApplyVO sealApplyVO) {
        Map<String, Object> resultMap;
        Date verifyTime = new Date();//审批时间
        if (htSealApplyService.isOperated(sealApplyVO)) {
            resultMap = setReturnMessageMap(FAILURE, SpringUtils.getMessage(MessageConstant.MSG_INFO_PROCESS_COMPLETE_0003));
        } else {
            try {
                HTUserVO user = (HTUserVO) request.getSession().getAttribute("loginUserInfo");
                String departmentId = sealApplyVO.getDepartmentId();
                Map<String, Object> variables = new HashMap<>();
                //审批状态
                String approvalStatus = (sealApplyVO.getRejectReason() != null && StringUtils.isNotBlank(sealApplyVO.getRejectReason())) ? Constant.NOT_APPROVED : Constant.APPROVED;
                String taskId = sealApplyVO.getDelegateTaskId();
                //封装属性
                sealApplyVO.setApproveUserName(user.getUserName());
                sealApplyVO.setApproveUserId(user.getLoginUserId());
                sealApplyVO.setApproveTime(DateToolUtils.convertDateFormat(verifyTime, DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                //设置变量
                variables.put(WorkFlowConstant.KEY_DEPARTMENT_ID, departmentId);
                variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, approvalStatus);
                variables.put(WorkFlowConstant.KEY_SEAL_APPLY_VO, sealApplyVO);
                variables.put(WorkFlowConstant.KEY_LISTENER_VO, new CommonActivitiListenVO());
                variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, sealApplyEnum.getProcessDictId());
                //办理任务
                htSealApplyService.excHandleTask(user.getLoginUserId(), taskId, variables);

                // ================================================ 插入消息信息 start =====================================================

                TbMessageEntity tbMessageEntity = new TbMessageEntity();

                // 提审时间
                String applyTime = DateToolUtils.convertDateFormat(
                        DateToolUtils.convertDateFormat(
                                sealApplyVO.getApplyTime(), DateToolUtils.yyyy_MM_dd), DateToolUtils.yyyy_MM_dd);

                // 当前审核节点
                String delegateTaskKey = sealApplyVO.getDelegateTaskKey();

                // 职位名称
                String jobName = "";

                switch (delegateTaskKey) {
                    // 部门经理
                    case "DepartmentManagerVerify":
                        jobName = "部门经理";
                        break;

                    // 签发人
                    case "GeneralManagerVerify":
                        // 签发人
                    case "AssignHandleVerify":
                        jobName = "签发人";
                        break;

                    default:
                        break;
                }

                // 审核结果字符串
                String tempAuditResultStr = "通过";

                // 审核结果:驳回
                if (Constant.NOT_APPROVED.equals(approvalStatus)) {
                    tempAuditResultStr = "驳回";
                }

                String tempMessageContentStr = "您在 " + applyTime + " 发起的 请章 申请 ，被 "
                        + jobName + "(" + user.getUserName() + ")审核并" + tempAuditResultStr;

                // 设置消息内容
                tbMessageEntity.setMessageContent(tempMessageContentStr);
                // 设置接收消息人
                tbMessageEntity.setAcceptUserId(sealApplyVO.getApplyUserId());
                // 设置是否阅读 0：否    1：是
                tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
                // 设置创建时间
                tbMessageEntity.setCreateDate(verifyTime);
                // 设置创建人id
                tbMessageEntity.setCreateUserId(user.getLoginUserId());
                // 设置修改时间
                tbMessageEntity.setUpdateDate(verifyTime);
                // 设置修改人id
                tbMessageEntity.setUpdateUserId(user.getLoginUserId());
                // 设置是否删除  0：否    1：是
                tbMessageEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);
                // 插入消息信息
                commonService.excInsertMessageInfo(tbMessageEntity);

                // 查询用户信息
                TbUserEntity tbUserEntity = tbUserEntityMapper.selectByPrimaryKey(sealApplyVO.getApplyUserId());

                // 微信公众号推送模板消息
                // loginUserId 当前登录用户id
                // openId 用户唯一标识
                // acceptUserName 接收人名称
                // messageContent 消息内容
                commonService.sendTemplateMessageToWeChat(user.getLoginUserId()
                        , tbUserEntity.getOpenId(), tbUserEntity.getUserName(), tempMessageContentStr);

                // 如果是高管（包括指定流程和不指定流程的），给所有行政人员发送消息
                if (("GeneralManagerVerify".equals(sealApplyVO.getDelegateTaskKey()) ||
                        "AssignHandleVerify".equals(sealApplyVO.getDelegateTaskKey())) && Constant.APPROVED.equals(approvalStatus)) {

                    HTUserVO htUserVO = new HTUserVO();
                    // 设置部门为行政部
                    htUserVO.setDepartmentId(Constant.DEPT_ID_XZ);
                    // 查询行政部的所有人
                    List<HTUserVO> htUserVOList = commonService.excSearchUserListByDepartmentId(htUserVO);

                    // 循环行政部的所有人，并向他们发送消息
                    for (HTUserVO userVO : htUserVOList) {

                        // 消息内容
                        tempMessageContentStr = "由(" + sealApplyVO.getApplyUserName() + ")" + applyTime + " 发起的请章申请,该由您标记盖章了";

                        // 设置消息内容
                        tbMessageEntity.setMessageContent(tempMessageContentStr);
                        // 设置接收消息人
                        tbMessageEntity.setAcceptUserId(userVO.getUserId());
                        // 插入消息信息
                        commonService.excInsertMessageInfo(tbMessageEntity);

                        // 微信公众号推送模板消息
                        // loginUserId 当前登录用户id
                        // openId 用户唯一标识
                        // acceptUserName 接收人名称
                        // messageContent 消息内容
                        commonService.sendTemplateMessageToWeChat(user.getLoginUserId()
                                , userVO.getOpenId(), userVO.getUserName(), tempMessageContentStr);

                    }

                }

                // ================================================ 插入消息信息 end =====================================================

                resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_PROCESS_COMPLETE_0001));
            } catch (LogicException e) {
                resultMap = setReturnMessageMap(FAILURE, e.getMsgDes());
                e.printStackTrace();
            } catch (SystemException e) {
                // 失败响应
                resultMap = setReturnMessageMap(FAILURE, e.getMsgDes());
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    /**
     * 请章记录查询
     *
     * @return 查询结果
     */
    @RequestMapping("/workbench/sealApply/query/querySealApplyRecord")
    @ResponseBody
    public Map<String, Object> querySealApplyRecord(HttpServletRequest request, @RequestBody HTSealApplyVO sealApplyVO) {
        Map<String, Object> resultMap;
        try {
            HTUserVO user = (HTUserVO) request.getSession().getAttribute("loginUserInfo");
            String userId = user.getLoginUserId();
            TbApplySealRecordEntityExample example = new TbApplySealRecordEntityExample();
            example.setOrderByClause(" apply_time desc ");
            example.createCriteria().andApplyUserIdEqualTo(userId);
            //请章记录查询
            Map<String, Object> resultPageMap = htSealApplyService.querySealApplyRecords(sealApplyVO, userId, example);
            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            // 返回请章记录信息
            resultMap.put(Constant.PAGEED_DATA_KEY, resultPageMap);
        } catch (SystemException e) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, e.getMsgDes());
            e.printStackTrace();
        }
        return resultMap;

    }

    /**
     * 是否有请章待办
     */
    @RequestMapping("/workbench/sealApply/query/hasSealApplyTodo")
    @ResponseBody
    public Boolean hasSealApplyTodo(HttpServletRequest request) {
        HTUserVO user = (HTUserVO) request.getSession().getAttribute("loginUserInfo");
        String userId = user.getLoginUserId();
        List<TbJobProcessNodeRelationEntity> processNodeList = commonCacheService.getProcessNodeCache().get(sealApplyEnum.getProcessDictId());
        List<Task> taskList = commonActivitiService.excQueryTask(userId, sealApplyEnum.getProcessKey(), processNodeList);
        return taskList != null && !taskList.isEmpty();
    }

    /**
     * 审批列表查询
     *
     * @return 查询结果
     */
    @RequestMapping("/workbench/sealApply/query/queryApproveList")
    @ResponseBody
    public Map<String, Object> queryApproveList(HttpServletRequest request, @RequestBody HTSealApplyVO sealApplyVO) {
        Map<String, Object> resultMap;
        HTUserVO user = (HTUserVO) request.getSession().getAttribute("loginUserInfo");
        String userId = user.getLoginUserId();
        try {
            //查询我的任务(部门经理，总经理，指定办理人)
            List<String> toBizKeys = new LinkedList<>();
            List<TbJobProcessNodeRelationEntity> processNodeList = commonCacheService.getProcessNodeCache().get(sealApplyEnum.getProcessDictId());
            List<Task> taskList = commonActivitiService.excQueryTask(userId, sealApplyEnum.getProcessKey(), processNodeList);
            if (taskList != null && !taskList.isEmpty()) {
                toBizKeys.addAll(commonActivitiService.getBizKeyByTask(taskList));
            }
            //查询业务数据
            List<TbApplySealRecordEntity> todoList = htSealApplyService.queryBizData(toBizKeys);
            //查询我的历史
            List<String> hisBizKeys = new LinkedList<>();
            List<HistoricTaskInstance> hisTaskList = htSealApplyService.excQueryHisTask(userId, sealApplyEnum.getProcessKey());
            if (hisTaskList != null && !hisTaskList.isEmpty()) {
                hisBizKeys.addAll(commonActivitiService.getBizKeyByHisTasks(hisTaskList));
            }
            //查询业务数据
            List<TbApplySealRecordEntity> historyList = htSealApplyService.queryBizData(hisBizKeys);
            //页面结果集
            LinkedList<HTSealApplyVO> allTodoList = new LinkedList<>();
            allTodoList.addAll(htSealApplyService.beanToVo(todoList, CURRENT_TASK));
            allTodoList.addAll(htSealApplyService.beanToVo(historyList, IS_HISTORY_TASK));
            // 结果Map
            Map<String, Object> resultPageMap = new HashMap<>();
            // 查询个数
            long count = allTodoList.size();
            int pageNumber = Integer.parseInt(sealApplyVO.getPageNumber());
            int pageSize = Integer.parseInt(sealApplyVO.getPageSize());
            int start = (pageNumber - 1) * pageSize;
            int end = start + pageSize;
            end = allTodoList.size() > end ? end : allTodoList.size();
            start = start > allTodoList.size() ? 0 : start;
            resultPageMap.put(Constant.DATA_LIST_KEY, allTodoList.subList(start, end));
            resultPageMap.put(Constant.DATA_COUNT_KEY, count);
            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            // 返回请章记录信息
            resultMap.put(Constant.PAGEED_DATA_KEY, resultPageMap);
        } catch (Exception e) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, "请章审批列表查询异常");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 行政部用章记录查询
     *
     * @return 查询结果
     */
    @RequestMapping("/workbench/sealApply/query/queryApprovedRecord")
    @ResponseBody
    public Map<String, Object> queryApprovedRecord(@RequestBody HTSealApplyVO sealApplyVO) {
        Map<String, Object> resultMap;
        try {
            TbApplySealRecordEntityExample example = new TbApplySealRecordEntityExample();
            example.setOrderByClause(" is_signatured, apply_time desc ");
            //用章记录查询
            Map<String, Object> resultPageMap = htSealApplyService.queryAlreadyPassRecords(sealApplyVO, example);
            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            // 返回请章记录信息
            resultMap.put(Constant.PAGEED_DATA_KEY, resultPageMap);
        } catch (Exception e) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, "请章审批列表查询异常");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 标记盖章
     *
     * @return 查询结果
     */
    @RequestMapping("/workbench/sealApply/modify/signSeal")
    @ResponseBody
    public Map<String, Object> signSeal(HttpServletRequest request, @RequestBody HTSealApplyVO sealApplyVO) {
        Map<String, Object> resultMap;
        try {

            //标记盖章
            htSealApplyService.signSeal(sealApplyVO);

            // ================================================ 插入消息信息 start =====================================================

            // 当前时间
            Date currentDate = getCurrentDate();

            // 用户信息
            HTUserVO user = (HTUserVO) request.getSession().getAttribute("loginUserInfo");

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(
                    DateToolUtils.convertDateFormat(
                            sealApplyVO.getApplyTime(), DateToolUtils.yyyy_MM_dd), DateToolUtils.yyyy_MM_dd);

            String tempMessageContentStr = "您在 " + applyTime + " 发起的 请章 申请 ，被行政人员(" + user.getUserName() + ")标记盖章了";

            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(sealApplyVO.getApplyUserId());
            // 设置是否阅读 0：否    1：是
            tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
            // 设置创建时间
            tbMessageEntity.setCreateDate(currentDate);
            // 设置创建人id
            tbMessageEntity.setCreateUserId(user.getLoginUserId());
            // 设置修改时间
            tbMessageEntity.setUpdateDate(currentDate);
            // 设置修改人id
            tbMessageEntity.setUpdateUserId(user.getLoginUserId());
            // 设置是否删除  0：否    1：是
            tbMessageEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);
            // 插入消息信息
            commonService.excInsertMessageInfo(tbMessageEntity);

            // 查询用户信息
            TbUserEntity tbUserEntity = tbUserEntityMapper.selectByPrimaryKey(sealApplyVO.getApplyUserId());

            // 微信公众号推送模板消息
            // loginUserId 当前登录用户id
            // openId 用户唯一标识
            // acceptUserName 接收人名称
            // messageContent 消息内容
            commonService.sendTemplateMessageToWeChat(user.getLoginUserId()
                    , tbUserEntity.getOpenId(), tbUserEntity.getUserName(), tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0001));
        } catch (SystemException e) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, e.getMsgDes());
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 章类型查询
     *
     * @return 查询结果
     */
    @RequestMapping("/workbench/sealApply/query/querySealType")
    @ResponseBody
    public Map<String, Object> querySealType() {
        Map<String, Object> resultMap;
        try {
            List<TbDictSealEntity> sealEntityList = commonCacheService.getSealTypeCache();
            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            // 返回类型信息
            resultMap.put(Constant.DATA_LIST_KEY, sealEntityList);
        } catch (SystemException e) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, e.getMsgDes());
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 指派审核人员查询
     *
     * @return 查询结果
     */
    @RequestMapping("/workbench/sealApply/query/queryAssignUsers")
    @ResponseBody
    public Map<String, Object> queryAssignUsers(@RequestBody HTSealApplyVO sealApplyVO) {
        Map<String, Object> resultMap;
        try {
            List<Map<String, Object>> sealEntityList = htSealApplyService.queryAssignUsers(ASSIGN_NODE_ID, sealApplyEnum.getProcessDictId(), sealApplyVO.getDepartmentId());
            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            // 返回类型信息
            resultMap.put(Constant.DATA_LIST_KEY, sealEntityList);
        } catch (SystemException e) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, e.getMsgDes());
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 请章初始化
     *
     * @return 结果
     */
    @RequestMapping("/workbench/sealApply/query/sealApplyInit")
    @ResponseBody
    public Map<String, Object> sealApplyInit(HttpServletRequest request) {
        Map<String, Object> resultMap;
        try {
            HTUserVO user = (HTUserVO) request.getSession().getAttribute("loginUserInfo");
            Map<String, Object> map = htSealApplyService.queryAuthority(user);
            // MSG_INFO_COMMON_0002 检索成功！
            resultMap = setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_COMMON_0002));
            // 返回类型信息
            resultMap.put(Constant.DATA_LIST_KEY, map);
        } catch (SystemException e) {
            // 失败响应
            resultMap = setReturnMessageMap(FAILURE, "请章模块初始化异常");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 导出
     */
    @RequestMapping(value = "/workbench/sealApply/exprot/exportSealApplySecord", method = RequestMethod.GET)
    public String exportSealApplySecord(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String realFileName = request.getParameter("realFileName");
        response.reset();
        // 解决中文显示不出来的问题
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(realFileName, "UTF-8") + ";filename=" + URLEncoder.encode(realFileName, "UTF-8"));
        List<Map<String, Object>> dataList = sealApplyDao.querySealApplyRecord();
        String[] columnTxt = {"创建时间", "用章原因", "用章类型", "是否骑缝", "申请人", "审批时间", "审批人"};
        String[] columnNames = {"apply_time", "apply_reason", "seal_type_text", "is_paging_seal", "apply_user_name", "approve_time", "approve_user_name"};
        int[] columnWidth = {80 * 100, 100 * 100, 60 * 100, 60 * 100, 60 * 100, 80 * 100, 60 * 100};
        ExcelData excelData = new ExcelData();
        excelData.setSheetName("用章记录");
        excelData.setTitle("用章记录");
        excelData.setColumnTxt(columnTxt);
        excelData.setColumnNames(columnNames);
        excelData.setColumnWidth(columnWidth);
        excelData.setDataList(dataList);
        List<ExportData> exportDatas = new ArrayList<>();
        exportDatas.add(excelData);
        Workbook workbook = (Workbook) excelExport.export(exportDatas);
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return "导出文件失败";
        }
        return "导出文件成功";
    }


}
