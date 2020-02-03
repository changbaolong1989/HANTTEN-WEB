package com.hd.manager.service.impl;

import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.enums.TenderEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbTenderTaskEntityMapper;
import com.hd.common.dao.TbTenderTaskPeriodMaterialEntityMapper;
import com.hd.common.entity.TbMessageEntity;
import com.hd.common.entity.TbTenderTaskEntity;
import com.hd.common.entity.TbTenderTaskPeriodMaterialEntity;
import com.hd.common.entity.TbTenderTaskUser;
import com.hd.common.service.CommonCacheService;
import com.hd.common.service.CommonService;
import com.hd.common.service.CommonTenderTaskService;
import com.hd.common.util.*;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.dao.*;
import com.hd.manager.dao.bean.*;
import com.hd.manager.service.HTTenderProcessService;
import com.hd.manager.service.HTTenderTaskService;
import com.hd.manager.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HTTenderTaskServiceImpl extends BaseServiceImpl implements HTTenderTaskService {

    /**
     * 投标计划持久层(自生成)
     */
    @Resource
    private TbTenderTaskEntityMapper tbTenderTaskEntityMapper;

    /**
     * 投标计划Service层(通用)
     */
    @Resource
    private CommonTenderTaskService commonTenderTaskService;

    /**
     * 投标成员Dao层(通用)
     */
    @Resource
    HTTenderTaskUserDao htTenderTaskUserDao;

    /**
     * 投标计划DAO
     */
    @Resource
    private HTTenderTaskDao htTenderTaskDao;


    /**
     * 通用Service
     */
    @Resource
    CommonService commonService;

    /**
     * 投标流程Service
     */
    @Resource
    private HTTenderProcessService tenderProcessService;

    /**
     *
     */
    @Resource
    private CommonCacheService commonCacheService;

    /**
     * 投标关联文件Mapper
     */
    @Resource
    private TbTenderTaskPeriodMaterialEntityMapper tbTenderTaskPeriodMaterialEntityMapper;

    /**
     * 投标关联文件DAO
     */
    @Resource
    private HTTenderTaskPeriodMaterialDao htTenderTaskPeriodMaterialDao;

    /**
     * 审核记录和人员和问题类型关联表 DAO
     */
    @Resource
    private HTUserQuestionRecordRelationDao htUserQuestionRecordRelationDao;

    /**
     * 合同dao
     */
    @Resource
    private HTContractDao htContractDao;

    /**
     * 添加投标任务
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excInsertTenderTaskInfo(HTTenderTaskVO paramVO) throws LogicException, SystemException {
        try {
            //当前时间
            Date currentDate = new Date();
            //离职拦截
            List<HTTenderTaskUserVO> htTenderTaskUserVOList = paramVO.getUserList();
            List<String> userIds = new ArrayList<>();
            for (HTTenderTaskUserVO htTenderTaskUserVO:htTenderTaskUserVOList){
                userIds.add(htTenderTaskUserVO.getUserId());
            }
            //查询包含离职人员个数
            Integer quitUserCount = commonService.excSearchQuitUserByIds(userIds);
            //包含离职人员时
            if (quitUserCount > 0){
                throw new LogicException(MessageConstant.MSG_ERROR_USER_0003);
            }
            HTTenderTaskBean paramBean = new HTTenderTaskBean();
            //投标任务名称
            paramBean.setTenderTaskName(paramVO.getTenderTaskName());
            //查询投标任务是否重复
            Integer count = htTenderTaskDao.excSearchTenderTaskCountByName(paramBean);
            //校验是否有同名数据
            if (count>0){
                throw new LogicException(MessageConstant.MSG_ERROR_TENDER_TASK_0003);
            }

            TbTenderTaskEntity paramEntity = new TbTenderTaskEntity();
            //投标任务名称
            paramEntity.setTenderTaskName(paramVO.getTenderTaskName());
            //部门ID
            paramEntity.setDepartmentId(paramVO.getDepartmentId());
            //投标任务概况
            paramEntity.setTenderTaskContent(paramVO.getTenderTaskContent());
            //投标开始时间
            paramEntity.setStartDate(DateToolUtils.convertTimeMillisStrToDate(paramVO.getStartDate()));
            //投标任务状态(0：未开标 1；未中标 2：已中标)
            paramEntity.setTenderTaskSts(StringToolUtils.STRING_ZERO);
            //创建人
            paramEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //创建时间
            paramEntity.setCreateDate(currentDate);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //创建人
            paramEntity.setCreateTaskUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //创建时间
            paramEntity.setCreateTaskDate(currentDate);
            //表内数据最大条数
            Integer maxSortNum = htTenderTaskDao.excGetMaxSortNum();
            maxSortNum++;
            //序号
            paramEntity.setSortNum(maxSortNum);
            //是否删除
            paramEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
            //新增投标任务
            tbTenderTaskEntityMapper.insertSelective(paramEntity);

            HTTenderTaskPeriodVO htTenderTaskPeriodVO = new HTTenderTaskPeriodVO();
            //新增投标任务ID
            htTenderTaskPeriodVO.setTenderTaskId(paramEntity.getTenderTaskId());
            //新增投标任务相关部门ID
            htTenderTaskPeriodVO.setDepartmentId(paramVO.getDepartmentId());
            //创建人
            htTenderTaskPeriodVO.setCreateUserId(paramEntity.getCreateUserId());
            //批量添加投标任务和阶段关联信息
            commonTenderTaskService.saveTenderTaskPeriodInfo(htTenderTaskPeriodVO);
            List<HTTenderTaskUserVO> userVOList = paramVO.getUserList();
            List<HTTenderTaskUserBean> userBeanList = new ArrayList<>();
            for (HTTenderTaskUserVO htTenderTaskUserVO : userVOList) {
                HTTenderTaskUserBean htTenderTaskUserBean = new HTTenderTaskUserBean();
                //关联ID
                htTenderTaskUserBean.setRelationId(UUID.randomUUID().toString());
                //投标任务ID
                htTenderTaskUserBean.setTenderTaskId(paramEntity.getTenderTaskId());
                //投标任务相关人员ID
                htTenderTaskUserBean.setUserId(htTenderTaskUserVO.getUserId());
                //是否是主负责人
                htTenderTaskUserBean.setIsMainDuty(htTenderTaskUserVO.getIsMainDuty());
                //创建人
                htTenderTaskUserBean.setCreateUserId(paramEntity.getCreateUserId());
                //创建时间
                htTenderTaskUserBean.setCreateDate(currentDate);
                //修改人
                htTenderTaskUserBean.setUpdateUserId(paramEntity.getCreateUserId());
                //修改时间
                htTenderTaskUserBean.setUpdateDate(currentDate);
                //是否删除
                htTenderTaskUserBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
                userBeanList.add(htTenderTaskUserBean);
                if ("1".equals(htTenderTaskUserVO.getIsMainDuty())) {
                    paramVO.setMainLeaderId(htTenderTaskUserVO.getUserId());
                }
            }
            //批量新增投标任务相关成员信息
            htTenderTaskDao.excInsertTenderTaskUsers(userBeanList);
            //启动工作流
            Map<String, Object> variable = new HashMap<>();
            paramVO.setTenderTaskId(paramEntity.getTenderTaskId());
            variable.put(WorkFlowConstant.KEY_TENDER_VO, paramVO);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            commonActivitiListenVO.setDepartmentId(paramVO.getDepartmentId());
            variable.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
            variable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            tenderProcessService.excStart(variable);
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询投标计划列表信息
     *
     * @return 投标计划列表
     */
    @Override
    public Map<String, Object> searchTenderTaskList(HTTenderTaskVO paramVO) throws LogicException, SystemException {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            HTTenderTaskBean paramBean = new HTTenderTaskBean();
            paramBean.setUserName(paramVO.getUserName());
            paramBean.setTenderTaskName(paramVO.getTenderTaskName());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //分页数据
            List<HTTenderTaskBean> tenderTaskBeanList = htTenderTaskDao.excSearchTenderTaskList(paramBean);
            List<HTTenderTaskVO> tenderTaskVOList = new ArrayList<>();
            for (HTTenderTaskBean htTenderTaskBean : tenderTaskBeanList) {
                HTTenderTaskVO htTenderTaskVO = new HTTenderTaskVO();
                //投标任务ID
                htTenderTaskVO.setTenderTaskId(htTenderTaskBean.getTenderTaskId());
                //序号
                htTenderTaskVO.setSortNum(htTenderTaskBean.getSortNum());
                //投标任务名称
                htTenderTaskVO.setTenderTaskName(htTenderTaskBean.getTenderTaskName());
                //投标任务说明
                htTenderTaskVO.setTenderTaskContent(htTenderTaskBean.getTenderTaskContent());
                //开始时间
                htTenderTaskVO.setStartDate(DateToolUtils.convertDateFormat(htTenderTaskBean.
                        getStartDate(), DateToolUtils.yyyy_MM_dd));
                //负责人名称
                htTenderTaskVO.setUserName(htTenderTaskBean.getUserName());
                //状态
                htTenderTaskVO.setTenderTaskSts(htTenderTaskBean.getTenderTaskSts());
                //部门ID
                htTenderTaskVO.setDepartmentId(htTenderTaskBean.getDepartmentId());
                // 业务ID
                htTenderTaskVO.setBusinessId(htTenderTaskBean.getBusinessId());
                tenderTaskVOList.add(htTenderTaskVO);
            }
            //查询列表总数
            Integer count = htTenderTaskDao.excSearchTenderTaskListCount(paramBean);
            resultMap.put(Constant.DATA_LIST_KEY, tenderTaskVOList);
            resultMap.put(Constant.DATA_COUNT_KEY, count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 修改投标计划
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenderTask(HTTenderTaskVO paramVO) throws LogicException, SystemException {
        try {
            HTTenderTaskBean checkBean = htTenderTaskDao.excSearchTenderTaskInfoById(paramVO.getTenderTaskId());
            if (!checkBean.getDelegateTaskId().equals(paramVO.getDelegateTaskId())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            //离职拦截
            List<HTTenderTaskUserVO> htTenderTaskUserVOList = paramVO.getUserList();
            List<String> userIds = new ArrayList<>();
            for (HTTenderTaskUserVO htTenderTaskUserVO:htTenderTaskUserVOList){
                userIds.add(htTenderTaskUserVO.getUserId());
            }
            //查询包含离职人员个数
            Integer quitUserCount = commonService.excSearchQuitUserByIds(userIds);
            //包含离职人员时
            if (quitUserCount > 0){
                throw new LogicException(MessageConstant.MSG_ERROR_USER_0003);
            }
            HTTenderTaskBean paramBean = new HTTenderTaskBean();
            //投标任务名称
            paramBean.setTenderTaskName(paramVO.getTenderTaskName());
            //投标任务ID
            paramBean.setTenderTaskId(paramVO.getTenderTaskId());
            //查询投标任务是否重复
            Integer count = htTenderTaskDao.excSearchTenderTaskCountByName(paramBean);
            //校验是否有同名数据
            if (count>0){
                throw new LogicException(MessageConstant.MSG_ERROR_TENDER_TASK_0003);
            }
            //当前时间
            Date currentDate = new Date();
            TbTenderTaskEntity paramEntity = new TbTenderTaskEntity();
            //投标任务ID
            paramEntity.setTenderTaskId(paramVO.getTenderTaskId());
            //投标任务名称
            paramEntity.setTenderTaskName(paramVO.getTenderTaskName());
            //投标任务概况
            paramEntity.setTenderTaskContent(paramVO.getTenderTaskContent());
            //投标开始时间
            paramEntity.setStartDate(DateToolUtils.convertTimeMillisStrToDate(paramVO.getStartDate()));
            //投标任务状态(0：未开标 1；未中标 2：已中标)
            paramEntity.setTenderTaskSts(StringToolUtils.STRING_ZERO);
            //修改人
            paramEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //成员列表
            List<HTTenderTaskUserVO> userVOList = paramVO.getUserList();
            List<HTTenderTaskUserBean> userBeanList = new ArrayList<>();
            for (HTTenderTaskUserVO htTenderTaskUserVO : userVOList) {
                HTTenderTaskUserBean htTenderTaskUserBean = new HTTenderTaskUserBean();
                //关联ID
                htTenderTaskUserBean.setRelationId(UUID.randomUUID().toString());
                //投标任务ID
                htTenderTaskUserBean.setTenderTaskId(paramEntity.getTenderTaskId());
                //投标任务相关人员ID
                htTenderTaskUserBean.setUserId(htTenderTaskUserVO.getUserId());
                //是否是主负责人
                htTenderTaskUserBean.setIsMainDuty(htTenderTaskUserVO.getIsMainDuty());
                //创建人
                htTenderTaskUserBean.setCreateUserId(paramEntity.getCreateUserId());
                //创建时间
                htTenderTaskUserBean.setCreateDate(currentDate);
                //修改人
                htTenderTaskUserBean.setUpdateUserId(paramEntity.getCreateUserId());
                //修改时间
                htTenderTaskUserBean.setUpdateDate(currentDate);
                //是否删除
                htTenderTaskUserBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
                userBeanList.add(htTenderTaskUserBean);
                if("1".equals(htTenderTaskUserVO.getIsMainDuty())){
                    paramVO.setApplyUserId(htTenderTaskUserVO.getUserId());
                }
            }
            //启动工作流
            tenderProcessService.excReassignment(paramVO);
            //修改投标任务
            tbTenderTaskEntityMapper.updateByPrimaryKeySelective(paramEntity);
            //删除投标任务相关成员信息
            htTenderTaskDao.excDeleteHTTenderTaskUsersByTenderTaskId(paramVO.getTenderTaskId());
            //批量新增投标任务相关成员信息
            htTenderTaskDao.excInsertTenderTaskUsers(userBeanList);
            // 清除投标任务执行人的缓存
            commonCacheService.clearTenderUserCache(paramVO.getTenderTaskId());
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 修改投标计划状态
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenderTaskSts(HTTenderTaskVO paramVO) throws LogicException, SystemException {
        try {
            //当前时间
            Date currentDate = new Date();
            String currentUserId = paramVO.getLoginUserInfo().getLoginUserId();
            TbTenderTaskEntity paramEntity = new TbTenderTaskEntity();
            //投标任务ID
            paramEntity.setTenderTaskId(paramVO.getTenderTaskId());
            //投标任务状态(0：未开标 1；未中标 2：已中标)
            paramEntity.setTenderTaskSts(paramVO.getTenderTaskSts());
            //修改人
            paramEntity.setUpdateUserId(currentUserId);
            //修改时间
            paramEntity.setUpdateDate(currentDate);
            //修改投标任务
            tbTenderTaskEntityMapper.updateByPrimaryKeySelective(paramEntity);

            // ================================================ 插入消息信息 start =====================================================

            // 如果已中标，则添加消息
            if("2".equals(paramVO.getTenderTaskSts())){

                // 查询投标任务名称
                TbTenderTaskEntity returnEntity = tbTenderTaskEntityMapper.selectByPrimaryKey(paramVO.getTenderTaskId());

                // 消息对象
                TbMessageEntity tbMessageEntity = new TbMessageEntity();

                // 消息内容
                String tempMessageContentStr = "有"+returnEntity.getTenderTaskName()+"任务已中标，可以创建项目，请行政人员与市场部联系";

                HTUserVO htUserVO = new HTUserVO();
                // 设置行政部ID
                htUserVO.setDepartmentId(Constant.DEPT_ID_XZ);
                // 查询行政部人员
                List<HTUserVO> htUserVOList = commonService.excSearchUserListByDepartmentId(htUserVO);
                // 遍历行政部人员，给每个人发送消息
                for(HTUserVO htUserVO1:htUserVOList){
                    // 设置消息内容
                    tbMessageEntity.setMessageContent(tempMessageContentStr);
                    // 设置接收消息人
                    tbMessageEntity.setAcceptUserId(htUserVO1.getUserId());
                    // 设置是否阅读 0：否    1：是
                    tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
                    // 设置创建时间
                    tbMessageEntity.setCreateDate(currentDate);
                    // 设置创建人id
                    tbMessageEntity.setCreateUserId(currentUserId);
                    // 设置修改时间
                    tbMessageEntity.setUpdateDate(currentDate);
                    // 设置修改人id
                    tbMessageEntity.setUpdateUserId(currentUserId);
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
                            ,htUserVO1.getOpenId(),htUserVO1.getUserName(),tempMessageContentStr);
                }

            }

            // ================================================ 插入消息信息 end =====================================================

        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 删除投标计划
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTenderTask(HTTenderTaskVO paramVO) throws LogicException, SystemException {
        try {
            HTTenderTaskBean htTenderTaskBean = htTenderTaskDao.excSearchTenderTaskInfoById(paramVO.getTenderTaskId());
            if (!StringToolUtils.isNull(htTenderTaskBean.getVerifyState()) && !htTenderTaskBean.getVerifyState().isEmpty()) {
                throw new LogicException(MessageConstant.MSG_ERROR_TENDER_TASK_0001);
            }
            //删除投标任务与阶段关联
            htTenderTaskDao.excDeleteHTTenderTaskPeriodsByTenderTaskId(paramVO.getTenderTaskId());
            //删除投标任务相关成员信息
            htTenderTaskDao.excDeleteHTTenderTaskUsersByTenderTaskId(paramVO.getTenderTaskId());
            //删除投标任务
            tbTenderTaskEntityMapper.deleteByPrimaryKey(paramVO.getTenderTaskId());
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 查询投标计划详情
     *
     * @return
     */
    @Override
    public HTTenderTaskVO excSearchTenderTaskInfoById(HTTenderTaskVO paramVO) throws LogicException, SystemException {
        try {
            //查询投标任务详情
            HTTenderTaskBean htTenderTaskBean = htTenderTaskDao.excSearchTenderTaskInfoById(paramVO.getTenderTaskId());
            /*格式转换*/
            HTTenderTaskVO htTenderTaskVO = new HTTenderTaskVO();
            //投标任务ID
            htTenderTaskVO.setTenderTaskId(htTenderTaskBean.getTenderTaskId());
            //投标任务名称
            htTenderTaskVO.setTenderTaskName(htTenderTaskBean.getTenderTaskName());
            //投标任务说明
            htTenderTaskVO.setTenderTaskContent(htTenderTaskBean.getTenderTaskContent());
            //任务Key
            htTenderTaskVO.setDelegateTaskKey(htTenderTaskBean.getDelegateTaskKey());
            //审批时间
            htTenderTaskVO.setApproveTime(htTenderTaskBean.getApproveTime());
            //开标时间
            htTenderTaskVO.setStartDate(DateToolUtils.
                    convertDateFormat(htTenderTaskBean.getStartDate(), DateToolUtils.yyyy_MM_dd));
            //部门ID
            htTenderTaskVO.setDepartmentId(htTenderTaskBean.getDepartmentId());
            //委托任务ID
            htTenderTaskVO.setDelegateTaskId(htTenderTaskBean.getDelegateTaskId());
            //提交节点
            htTenderTaskVO.setSubmitNode(htTenderTaskBean.getSubmitNode());
            //投标任务状态
            htTenderTaskVO.setTenderTaskSts(htTenderTaskBean.getTenderTaskSts());
            //审核状态
            htTenderTaskVO.setVerifyState(htTenderTaskBean.getVerifyState());
            //是否可以撤回
            htTenderTaskVO.setIsCanRecall(htTenderTaskBean.getIsCanRecall());
            //查询投标任务成员列表
            List<HTTenderTaskUserBean> userBeanList = htTenderTaskDao.
                    excSearchTenderTaskUserListByTenderTaskId(paramVO.getTenderTaskId());
            List<HTTenderTaskUserVO> userVOList = new ArrayList<>();
            for (HTTenderTaskUserBean htTenderTaskUserBean : userBeanList) {
                HTTenderTaskUserVO htTenderTaskUserVO = new HTTenderTaskUserVO();
                //成员ID
                htTenderTaskUserVO.setUserId(htTenderTaskUserBean.getUserId());
                //成员名称
                htTenderTaskUserVO.setUserName(htTenderTaskUserBean.getUserName());
                //是否是主负责人
                htTenderTaskUserVO.setIsMainDuty(htTenderTaskUserBean.getIsMainDuty());

                userVOList.add(htTenderTaskUserVO);
            }
            htTenderTaskVO.setUserList(userVOList);
            return htTenderTaskVO;
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 查询投标阶段文件列表
     *
     * @return
     */
    @Override
    public List<HTTenderTaskPeriodMaterialVO> searchTenderTaskPeriodMaterialList(
            HTTenderTaskPeriodVO paramVO) throws LogicException, SystemException {
        try {
            //查询投标阶段文件信息列表
            HTTenderTaskPeriodMaterialBean paramBean = new HTTenderTaskPeriodMaterialBean();
            //阶段主键ID
            paramBean.setTenderTaskPeriodId(paramVO.getRelationId());
            //查询阶段关联文件信息
            List<HTTenderTaskPeriodMaterialBean> htTenderTaskPeriodMaterialBeanList = htTenderTaskDao.
                    excSearchTenderTaskPeriodMaterialListByProperty(paramBean);
            List<HTTenderTaskPeriodMaterialVO> htTenderTaskPeriodMaterialVOList = new ArrayList<>();
            for (HTTenderTaskPeriodMaterialBean htTenderTaskPeriodMaterialBean : htTenderTaskPeriodMaterialBeanList) {
                HTTenderTaskPeriodMaterialVO htTenderTaskPeriodMaterialVO = new HTTenderTaskPeriodMaterialVO();
                //唯一标识
                htTenderTaskPeriodMaterialVO.setRelationId(htTenderTaskPeriodMaterialBean.getRelationId());
                //关联阶段ID
                htTenderTaskPeriodMaterialVO.
                        setTenderTaskPeriodId(htTenderTaskPeriodMaterialBean.getTenderTaskPeriodId());
                //文件类型ID
                htTenderTaskPeriodMaterialVO.setFileTypeId(htTenderTaskPeriodMaterialBean.getFileTypeId());
                //文件名称
                htTenderTaskPeriodMaterialVO.setMaterialName(htTenderTaskPeriodMaterialBean.getMaterialName());
                //文件地址
                htTenderTaskPeriodMaterialVO.setMaterialPath(htTenderTaskPeriodMaterialBean.getMaterialPath());
                //是否提审（0：否 1：是）
                htTenderTaskPeriodMaterialVO.setIsReview(htTenderTaskPeriodMaterialBean.getIsReview());
                //判断文件是否是当前人上传(标记可删除标识)
                if (paramVO.getLoginUserInfo().getLoginUserId().equals(htTenderTaskPeriodMaterialBean.getCreateUserId())){
                    //可删
                    htTenderTaskPeriodMaterialVO.setIsCanDel(Constant.IS_CAN_DEL_FLG_ABLE);
                }else {
                    //不可删
                    htTenderTaskPeriodMaterialVO.setIsCanDel(Constant.IS_CAN_DEL_FLG_ENABLE);
                }
                //创建人名称
                htTenderTaskPeriodMaterialVO.setUserName(htTenderTaskPeriodMaterialBean.getUserName());
                htTenderTaskPeriodMaterialVOList.add(htTenderTaskPeriodMaterialVO);
            }
            return htTenderTaskPeriodMaterialVOList;
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 查询文件类别列表
     *
     * @return
     */
    @Override
    public List<HTDictFileTypeVO> searchMaterialTypeList(HTDictFileTypeVO paramVO) {
        try {
            HTDictFileTypeBean paramBean = new HTDictFileTypeBean();
            if(!StringToolUtils.isNull(paramVO.getDepartmentId())){
                // 部门ID
                paramBean.setDepartmentId(paramVO.getDepartmentId());
            }
            if(!StringToolUtils.isNull(paramVO.getDepartmentId())){
                if(paramVO.getDepartmentId().equals(Constant.DEPT_ID_ZJ)){
                    // 咨询类别ID
                    paramBean.setCounselTypeId(paramVO.getCounselTypeId());
                }
            }

            //查询文件类别列表
            List<HTDictFileTypeBean> htDictFileTypeBeanList = htTenderTaskDao.
                    excSearchMaterialTypeList(paramBean);
            List<HTDictFileTypeVO> htDictFileTypeVOList = new ArrayList<>();
            for (HTDictFileTypeBean htDictFileTypeBean : htDictFileTypeBeanList) {
                HTDictFileTypeVO htDictFileTypeVO = new HTDictFileTypeVO();
                //文件类型ID
                htDictFileTypeVO.setFileTypeId(htDictFileTypeBean.getFileTypeId());
                //文件类型名称
                htDictFileTypeVO.setFileTypeName(htDictFileTypeBean.getFileTypeName());
                htDictFileTypeVOList.add(htDictFileTypeVO);
            }
            return htDictFileTypeVOList;
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 新增投标阶段文件
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTenderTaskPeriodMaterialInfo(HTTenderTaskPeriodMaterialVO paramVO, MultipartFile[] fileList) throws LogicException, SystemException {

        try {
            Date currentDate = new Date();
            TbTenderTaskPeriodMaterialEntity tbTenderTaskPeriodMaterialEntity = new TbTenderTaskPeriodMaterialEntity();
            String userId = paramVO.getLoginUserInfo().getLoginUserId();

            for (MultipartFile file:fileList){
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
                filePath = Constant.FILE_SEPARATE + "tenderTask" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;
                // 文件名称
                tbTenderTaskPeriodMaterialEntity.setMaterialName(realFileName);
                // 文件地址
                tbTenderTaskPeriodMaterialEntity.setMaterialPath(filePath);
                //阶段关联ID
                tbTenderTaskPeriodMaterialEntity.setTenderTaskPeriodId(paramVO.getTenderTaskPeriodId());
                //文件类型ID
                if (StringToolUtils.isNull(paramVO.getFileTypeId())){
                    throw new LogicException(MessageConstant.MSG_ERROR_TENDER_TASK_0002);
                }
                tbTenderTaskPeriodMaterialEntity.setFileTypeId(paramVO.getFileTypeId());
                //是否提审（0：否 1：是）
                tbTenderTaskPeriodMaterialEntity.setIsReview(Constant.IS_REVIEW_DEFAULT_VALUE);
                //是否删除
                tbTenderTaskPeriodMaterialEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
                //创建人
                tbTenderTaskPeriodMaterialEntity.setCreateUserId(userId);
                //创建时间
                tbTenderTaskPeriodMaterialEntity.setCreateDate(currentDate);
                //修改人
                tbTenderTaskPeriodMaterialEntity.setUpdateUserId(userId);
                //修改时间
                tbTenderTaskPeriodMaterialEntity.setUpdateDate(currentDate);
                if(paramVO.getPeriodType().equals(Constant.TASK_PERIOD_TYPE_LEVEL_TWO)){
                    HTTenderTaskPeriodMaterialBean paramBean = new HTTenderTaskPeriodMaterialBean();
                    //投标任务和阶段的关联ID
                    paramBean.setTenderTaskPeriodId(paramVO.getTenderTaskPeriodId());
                    //查询阶段关联文件信息
                    List<HTTenderTaskPeriodMaterialBean> materialList = htTenderTaskDao.
                            excSearchTenderTaskPeriodMaterialListByProperty(paramBean);
                    for (HTTenderTaskPeriodMaterialBean htTenderTaskPeriodMaterialBean:materialList){
                        //文件路径
                        String materialPath = htTenderTaskPeriodMaterialBean.getMaterialPath();
                        //删除真实文件
                        FileUtils.deleteFile(materialPath);
                    }
                    //删除投标文件通过投标任务和阶段的关联ID
                    htTenderTaskPeriodMaterialDao.
                            excDeleteTenderTaskPeriodMaterialsByTenderTaskPeriodId(paramVO.getTenderTaskPeriodId());
                }
                //添加文件信息
                tbTenderTaskPeriodMaterialEntityMapper.insertSelective(tbTenderTaskPeriodMaterialEntity);
                // 上传文件
                boolean isUploadSuccess = FileUtils.uploadFile(filePath, file);
            }


        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 删除投标阶段文件信息
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTenderTaskPeriodMaterialInfo(HTTenderTaskPeriodMaterialVO paramVO) throws LogicException,
            SystemException {

        try {
            String materialPath = paramVO.getMaterialPath();
            //删除文件信息
            tbTenderTaskPeriodMaterialEntityMapper.deleteByPrimaryKey(paramVO.getRelationId());
            //删除真实文件
            FileUtils.deleteFile(materialPath);
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 投标任务相关所有文件类型以级相关文件类型的文件列表信息
     *
     * @return
     */
    @Override
    public List<ElementTreeVO> excSearchAllMaterialTypeAndMaterialList(HTTenderTaskVO paramVO) throws LogicException,
            SystemException {
        try {
            HTDictFileTypeBean dictFileTypeBean = new HTDictFileTypeBean();
            dictFileTypeBean.setDepartmentId(paramVO.getDepartmentId());
            //查询文件类别列表
            List<HTDictFileTypeBean> fileTypeBeanList = htTenderTaskDao.excSearchMaterialTypeList(dictFileTypeBean);
            HTTenderTaskPeriodMaterialBean paramBean = new HTTenderTaskPeriodMaterialBean();
            //投标任务ID
            paramBean.setTenderTaskId(paramVO.getTenderTaskId());
            //查询阶段关联文件信息
            List<HTTenderTaskPeriodMaterialBean> htTenderTaskPeriodMaterialBeanList = htTenderTaskDao.
                    excSearchTenderTaskPeriodMaterialListByProperty(paramBean);
            List<ElementTreeVO> treeVOList = new ArrayList<>();
            //格式转换(转换成前台elementTree组件适应格式)
            for (HTDictFileTypeBean htDictFileTypeBean : fileTypeBeanList) {
                ElementTreeVO treeVO = new ElementTreeVO();
                //级别
                treeVO.setTreeNodeLevel(Constant.FIRST_LEVEL_MENU_FLAG);
                //唯一标识
                treeVO.setId(htDictFileTypeBean.getFileTypeId());
                //名称
                treeVO.setLabel(htDictFileTypeBean.getFileTypeName());
                //子集
                List<ElementTreeVO> childrenList = new ArrayList<>();
                for (HTTenderTaskPeriodMaterialBean htTenderTaskPeriodMaterialBean : htTenderTaskPeriodMaterialBeanList) {
                    //找到对应父级
                    if (htTenderTaskPeriodMaterialBean.getFileTypeId().equals(treeVO.getId())) {
                        ElementTreeVO childTreeVO = new ElementTreeVO();
                        //唯一标识
                        childTreeVO.setId(htTenderTaskPeriodMaterialBean.getRelationId());
                        //名称
                        childTreeVO.setLabel(htTenderTaskPeriodMaterialBean.getMaterialName());
                        //级别
                        childTreeVO.setTreeNodeLevel(Constant.SECOND_LEVEL_MENU_FLAG);
                        childrenList.add(childTreeVO);
                    }
                }
                treeVO.setChildren(childrenList);
                treeVOList.add(treeVO);
            }
            return treeVOList;
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 提交审核(修改阶段文件信息提审状态)
     *
     * @return
     */
    @Override
    @Transactional
    public void excUpdateTenderTaskPeriodMaterialInfoIsReviewType(HTTenderTaskPeriodVO paramVO) {
        try {
            //投标任务详情
            HTTenderTaskBean htTenderTaskBean = htTenderTaskDao.excSearchTenderTaskInfoById(paramVO.getTenderTaskId());
            htTenderTaskBean.setSubmitNode(paramVO.getSubmitNode());
            //格式转换
            HTTenderTaskVO htTenderTaskVO = (HTTenderTaskVO) ObjectReflectUtils.convertObj(htTenderTaskBean, HTTenderTaskVO.class);
            htTenderTaskVO.setLoginUserInfo(paramVO.getLoginUserInfo());
            Map<String, Object> variable = new HashMap<>();
            variable.put(WorkFlowConstant.KEY_TENDER_VO, htTenderTaskVO);
            variable.put(WorkFlowConstant.KEY_SUBMIT_NODE, htTenderTaskVO.getSubmitNode());
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            commonActivitiListenVO.setDepartmentId(TenderEnum.getDeptId(htTenderTaskVO.getSubmitNode()));
            variable.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            variable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
            //启动工作流
            tenderProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(),htTenderTaskVO.getDelegateTaskId(), variable);
            //重置提审文件状态
            htTenderTaskDao.excCleanTenderTaskPeriodMaterialInfoIsReviewType(htTenderTaskVO.getTenderTaskId());
            //校验非空
            if (null != paramVO.getMaterialIds() && !paramVO.getMaterialIds().isEmpty()) {
                //修改阶段文件信息提审状态
                htTenderTaskDao.excUpdateTenderTaskPeriodMaterialInfoIsReviewType(paramVO.getMaterialIds());
            }
            HTReviewFileRelationBean htReviewFileRelationBean = new HTReviewFileRelationBean();
            //事务唯一标识
            htReviewFileRelationBean.setBusinessId(paramVO.getTenderTaskId());

            // ================================================ 插入消息信息 start =====================================================

            Date currentDate = getCurrentDate();

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(currentDate,DateToolUtils.yyyy_MM_dd);

            HTContractBean htContractBean = new HTContractBean();
            // 设置当前登录用户id
            htContractBean.setCurrentUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置部门id
            htContractBean.setDepartmentId(htTenderTaskBean.getDepartmentId());
            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            // 职位和查询得到的人员id
            HTTaskBean returnBean = commonService.excSearchUserIdForMessage(paramVO.getSubmitNode(),htTenderTaskBean.getDepartmentId());

            // 消息内容
            String tempMessageContentStr = "由"+htContractBeanList.get(0).getJobName()+"("+paramVO.getLoginUserInfo().getUserName()+")"+applyTime+"发起的"
                    +htTenderTaskBean.getTenderTaskName()+"投标任务该由您审核了";

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

        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询投标任务以提审文件列表
     *
     * @return
     */
    @Override
    public List<HTTenderTaskPeriodMaterialVO> excSearchIsReviewTenderTaskPeriodMaterialList(
            HTTenderTaskPeriodMaterialVO paramVO) throws LogicException, SystemException {
        try {
            //查询投标阶段文件信息列表
            HTTenderTaskPeriodMaterialBean paramBean = new HTTenderTaskPeriodMaterialBean();
            //主键ID
            paramBean.setTenderTaskId(paramVO.getTenderTaskId());
            //是否提审(1：是）
            paramBean.setIsReview(Constant.IS_REVIEW_FLAG_TRUE);
            //
            List<HTTenderTaskPeriodMaterialBean> htTenderTaskPeriodMaterialBeanList = htTenderTaskDao.
                    excSearchTenderTaskPeriodMaterialListByProperty(paramBean);
            List<HTTenderTaskPeriodMaterialVO> htTenderTaskPeriodMaterialVOList = new ArrayList<>();
            for (HTTenderTaskPeriodMaterialBean htTenderTaskPeriodMaterialBean : htTenderTaskPeriodMaterialBeanList) {
                HTTenderTaskPeriodMaterialVO htTenderTaskPeriodMaterialVO = new HTTenderTaskPeriodMaterialVO();
                //唯一标识
                htTenderTaskPeriodMaterialVO.setRelationId(htTenderTaskPeriodMaterialBean.getRelationId());
                //关联阶段ID
                htTenderTaskPeriodMaterialVO.
                        setTenderTaskPeriodId(htTenderTaskPeriodMaterialBean.getTenderTaskPeriodId());
                //文件类型ID
                htTenderTaskPeriodMaterialVO.setFileTypeId(htTenderTaskPeriodMaterialBean.getFileTypeId());
                //文件名称
                htTenderTaskPeriodMaterialVO.setMaterialName(htTenderTaskPeriodMaterialBean.getMaterialName());
                //文件地址
                htTenderTaskPeriodMaterialVO.setMaterialPath(htTenderTaskPeriodMaterialBean.getMaterialPath());
                //是否提审（0：否 1：是）
                htTenderTaskPeriodMaterialVO.setIsReview(htTenderTaskPeriodMaterialBean.getIsReview());
                //上传人名称
                htTenderTaskPeriodMaterialVO.setUserName(htTenderTaskPeriodMaterialBean.getUserName());
                htTenderTaskPeriodMaterialVOList.add(htTenderTaskPeriodMaterialVO);
            }
            return htTenderTaskPeriodMaterialVOList;
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询投标任务投标阶段文件信息
     *
     * @return
     */
    @Override
    public HTTenderTaskPeriodMaterialVO excSearchTenderStageMaterialByTenderTaskId(
            HTTenderTaskPeriodMaterialVO paramVO) throws LogicException, SystemException {
        try {
            HTTenderTaskPeriodMaterialVO htTenderTaskPeriodMaterialVO= new HTTenderTaskPeriodMaterialVO();
            //查询投标阶段文件信息列表
            HTTenderTaskPeriodMaterialBean paramBean = new HTTenderTaskPeriodMaterialBean();
            //主键ID
            paramBean.setTenderTaskId(paramVO.getTenderTaskId());
            //投标文件类型:A3审核阶段
            paramBean.setPeriodType(Constant.TASK_PERIOD_TYPE_LEVEL_TWO);
            HTTenderTaskPeriodMaterialBean htTenderTaskPeriodMaterialBean = htTenderTaskDao.
                    excSearchTenderStageMaterialByTenderTaskId(paramBean);
            if (null != htTenderTaskPeriodMaterialBean){
                //唯一标识
                htTenderTaskPeriodMaterialVO.setRelationId(htTenderTaskPeriodMaterialBean.getRelationId());
                //关联阶段ID
                htTenderTaskPeriodMaterialVO.
                        setTenderTaskPeriodId(htTenderTaskPeriodMaterialBean.getTenderTaskPeriodId());
                //文件类型ID
                htTenderTaskPeriodMaterialVO.setFileTypeId(htTenderTaskPeriodMaterialBean.getFileTypeId());
                //文件名称
                String materialName = htTenderTaskPeriodMaterialBean.getMaterialName();
                htTenderTaskPeriodMaterialVO.setMaterialName(materialName);
                //文件地址
                String materialPath = htTenderTaskPeriodMaterialBean.getMaterialPath();
                htTenderTaskPeriodMaterialVO.setMaterialPath(materialPath);
                //是否提审（0：否 1：是）
                htTenderTaskPeriodMaterialVO.setIsReview(htTenderTaskPeriodMaterialBean.getIsReview());
                //给onlyOffice使用的绝对路径
                htTenderTaskPeriodMaterialVO.setReviewFilePath(
                        OnlyOfficeUtils.getOnlyOfficeSaveCallbackUrl());
                //给onlyOffice使用的真实文件名称
                htTenderTaskPeriodMaterialVO.setReviewFileName(htTenderTaskPeriodMaterialBean.getMaterialName());
                //给onlyOffice使用的唯一标识
                htTenderTaskPeriodMaterialVO.setOnlyOfficeEditKey(OnlyOfficeUtils.generateOnlyOfficeEditKey());
                //给onlyOffice使用的下载地址
                htTenderTaskPeriodMaterialVO.setOnlyOfficeAbsDownLoadPath(
                        OnlyOfficeUtils.getFileDownloadAbsoluteUrl(Constant.COMMON_MODULE_URL+"/downloadFile?filePathName="+
                                materialPath+"&realFileName="+materialName));
            }
            return htTenderTaskPeriodMaterialVO;
        } catch (LogicException lex) {
            throw lex;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }



    /**
     * 撤回投标任务
     */
    @Override
    public void excRecallTask(HTTenderTaskVO paramVO) throws LogicException, SystemException {
        try {
            HTTenderTaskBean checkBean = htTenderTaskDao.excSearchTenderTaskInfoById(paramVO.getTenderTaskId());
            if (!checkBean.getDelegateTaskId().equals(paramVO.getDelegateTaskId())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            HTTenderTaskBean tenderTaskBean = htTenderTaskDao.excSearchTenderTaskInfoById(paramVO.getTenderTaskId());
            //是否可以撤回（0：否 1：是）
            if (tenderTaskBean.getIsCanRecall().equals(Constant.IS_CAN_RECALL_DISABLE)) {
                throw new LogicException("该投标任务不可撤回!");
            }
            //启动工作流
            HTTenderTaskVO htTenderTaskVO = (HTTenderTaskVO) ObjectReflectUtils.convertObj(tenderTaskBean, HTTenderTaskVO.class);
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            //添加登录人信息
            htTenderTaskVO.setLoginUserInfo(userInfo);
            //审批人
            htTenderTaskVO.setApproveUserId(userInfo.getLoginUserId());
            //审批人名称
            htTenderTaskVO.setApproveUserName(userInfo.getUserName());
            //未通过
            htTenderTaskVO.setIsPass(Constant.NOT_APPROVED);
            tenderProcessService.excRecallTask(htTenderTaskVO);
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 审核
     */
    @Override
    @Transactional
    public void excAuditingHTTenderTask(HTTenderTaskVO paramVO) throws LogicException, SystemException {
        try {
            HTTenderTaskBean checkBean = htTenderTaskDao.excSearchTenderTaskInfoById(paramVO.getTenderTaskId());
            if (!checkBean.getDelegateTaskId().equals(paramVO.getDelegateTaskId())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            //校验驳回意见是否为空
//            if (Constant.NOT_APPROVED.equals(paramVO.getIsPass())&&StringToolUtils.isNull(paramVO.getRejectReason())){
//                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0013);
//            }
            if (Constant.NOT_APPROVED.equals(paramVO.getIsPass())) {
                if (null == paramVO.getUserQuestionRecordRelationList() || paramVO.getUserQuestionRecordRelationList().size() == 0) {
                    throw new LogicException(MessageConstant.MSG_ERROR_TASK_0013);
                }
            }
            HTTenderTaskBean htTenderTaskBean = htTenderTaskDao.excSearchTenderTaskInfoById(paramVO.getTenderTaskId());
            //是否通过
            htTenderTaskBean.setIsPass(paramVO.getIsPass());
            //意见
            htTenderTaskBean.setRejectReason(paramVO.getRejectReason());
            //下一节点
            htTenderTaskBean.setSubmitNode(paramVO.getSubmitNode());
            Map<String, Object> variables = new HashMap<>();
            HTTenderTaskVO htTenderTaskVO = (HTTenderTaskVO) ObjectReflectUtils.convertObj(htTenderTaskBean, HTTenderTaskVO.class);
            //登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            //登录人信息
            htTenderTaskVO.setLoginUserInfo(userInfo);
            //审批人ID
            htTenderTaskVO.setApproveUserId(userInfo.getLoginUserId());
            //审批人名称
            htTenderTaskVO.setApproveUserName(userInfo.getUserName());

            //部门Id
            htTenderTaskVO.setDepartmentId(htTenderTaskBean.getDepartmentId());

            variables.put(WorkFlowConstant.KEY_TENDER_VO, htTenderTaskVO);
            String isPass = htTenderTaskVO.getIsPass();
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, isPass);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            if (Constant.NOT_APPROVED.equals(isPass)) {
                variables.put(WorkFlowConstant.KEY_REJECT_REASON, htTenderTaskVO.getRejectReason());
                variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, htTenderTaskVO.getDelegateTaskKey());
                commonActivitiListenVO.setDepartmentId(htTenderTaskBean.getDepartmentId());
            } else {
                commonActivitiListenVO.setDepartmentId(TenderEnum.getDeptId(htTenderTaskVO.getSubmitNode()));
                variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, htTenderTaskVO.getSubmitNode());
            }
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            //启动工作流
            String historyRecordId = tenderProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(),variables);
            Date currentDate = new Date();
            if (null!= paramVO.getUserQuestionRecordRelationList()&&paramVO.getUserQuestionRecordRelationList().size()>0 ){
                //批量插入审核记录和人员和问题类型关联
                List<HTUserQuestionRecordRelationVO> relationVOList = paramVO.getUserQuestionRecordRelationList();
                List<HTUserQuestionRecordRelationBean>  relationBeanList = new ArrayList<>();
                for (HTUserQuestionRecordRelationVO htUserQuestionRecordRelationVO:relationVOList){
                    HTUserQuestionRecordRelationBean htUserQuestionRecordRelationBean = new HTUserQuestionRecordRelationBean();
                    //唯一标识
                    htUserQuestionRecordRelationBean.setRelationId(UUID.randomUUID().toString());
                    //业务ID
                    htUserQuestionRecordRelationBean.setBusinessId(paramVO.getTenderTaskId());
                    //历史id
                    htUserQuestionRecordRelationBean.setRecordId(historyRecordId);
                    //人员ID
                    htUserQuestionRecordRelationBean.setUserId(htUserQuestionRecordRelationVO.getUserId());
                    //问题ID
                    htUserQuestionRecordRelationBean.setQuestionId(htUserQuestionRecordRelationVO.getQuestionId());
                    //问题个数
                    htUserQuestionRecordRelationBean.setQuestionNum(htUserQuestionRecordRelationVO.getQuestionNum());
                    //来源
                    htUserQuestionRecordRelationBean.setSourceType(Constant.TENDER_TASK_SOURCE_TYPE);
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
            HTTenderTaskPeriodMaterialVO htTenderTaskPeriodMaterialVO = new HTTenderTaskPeriodMaterialVO();
            htTenderTaskPeriodMaterialVO.setTenderTaskId(paramVO.getTenderTaskId());
            HTTenderTaskPeriodMaterialVO tenderTaskPeriodMaterialVO = this.excSearchTenderStageMaterialByTenderTaskId(htTenderTaskPeriodMaterialVO);
            //业务ID
            paramVO.setBusinessId(paramVO.getTenderTaskId());
            //创建时间
            paramVO.setCreateDate(DateToolUtils.convertDateFormat(currentDate,DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            //创建人
            paramVO.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改时间
            paramVO.setUpdateDate(DateToolUtils.convertDateFormat(currentDate,DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            //修改人
            paramVO.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //如果是驳回
            if (Constant.NOT_APPROVED.equals(isPass)&&!StringToolUtils.isNull(paramVO.getOnlyOfficeEditKey())) {
                String uuid = UUID.randomUUID().toString();
                // 文件上传后的路径
                String filePath = "";
                // 文件名
                String realFileName = tenderTaskPeriodMaterialVO.getMaterialName();
                // 获取文件的后缀名
                String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
                // 获取文件路径
                String fileName = uuid + suffixName;
                // 文件上传后的路径
                filePath = Constant.FILE_SEPARATE + "review" + Constant.FILE_SEPARATE + "tenderTask" + Constant.FILE_SEPARATE  + uuid + Constant.FILE_SEPARATE + fileName;
                //文件地址
                paramVO.setReviewFilePath(filePath);
                // 获取文件的真实名称
                paramVO.setReviewFileName(tenderTaskPeriodMaterialVO.getMaterialName());
                //通知OnlyOffice documentServer 保存文件
                OnlyOfficeUtils.notifyOnlyOfficeToSave(paramVO);
            }

            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(htTenderTaskBean.getApplyTime(),DateToolUtils.yyyy_MM_dd);

            // 当前审核节点
            String delegateTaskKey = htTenderTaskBean.getDelegateTaskKey();

            // 职位名称
            String jobName = "";

            switch (delegateTaskKey){
                // 部门经理
                case "ManagerVerify":
                    jobName = TenderEnum.ManagerVerify.getJobName();
                    break;

                // 质控部组员
                case "QualityVerify":
                    jobName = TenderEnum.QualityVerify.getJobName();
                    break;

                // 质控部副总经理
                case "GenerManagerVerify":
                    jobName = TenderEnum.GenerManagerVerify.getJobName();
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
                    +htTenderTaskBean.getTenderTaskName()+"投标任务 申请 ，被 "
                    +jobName+"("+userInfo.getUserName()+")审核并"+tempAuditResultStr;

            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(htTenderTaskBean.getApplyUserId());
            // 设置是否阅读 0：否    1：是
            tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
            // 设置创建时间
            tbMessageEntity.setCreateDate(currentDate);
            // 设置创建人id
            tbMessageEntity.setCreateUserId(userInfo.getLoginUserId());
            // 设置修改时间
            tbMessageEntity.setUpdateDate(currentDate);
            // 设置修改人id
            tbMessageEntity.setUpdateUserId(userInfo.getLoginUserId());
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
                    ,htTenderTaskBean.getOpenId(),htTenderTaskBean.getApplyUserName(),tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }
    /**
     * 功能展示(查询当前用户在页面身份所展示功能)
     *
     */
    @Override
    public String excQueryShowBtnType(HTTenderTaskVO paramVO) {
        HTTenderTaskBean htTenderTaskBean = htTenderTaskDao.excSearchTenderTaskInfoById(paramVO.getTenderTaskId());
        HTTenderTaskVO htTenderTaskVO = new HTTenderTaskVO();
        //投标任务ID
        htTenderTaskVO.setTenderTaskId(htTenderTaskBean.getTenderTaskId());
        //投标任务名称
        htTenderTaskVO.setTenderTaskName(htTenderTaskBean.getTenderTaskName());
        //投标任务说明
        htTenderTaskVO.setTenderTaskContent(htTenderTaskBean.getTenderTaskContent());
        //流程当前状态
        htTenderTaskVO.setCurrentState(htTenderTaskBean.getCurrentState());
        //任务Key
        htTenderTaskVO.setDelegateTaskKey(htTenderTaskBean.getDelegateTaskKey());
        //开标时间
        htTenderTaskVO.setStartDate(DateToolUtils.
                convertDateFormat(htTenderTaskBean.getStartDate(), DateToolUtils.yyyy_MM_dd));
        //委托任务ID
        htTenderTaskVO.setDelegateTaskId(htTenderTaskBean.getDelegateTaskId());
        //提交节点
        htTenderTaskVO.setSubmitNode(htTenderTaskBean.getSubmitNode());
        //投标任务状态(0：未开标 1；未中标 2：已中标)
        htTenderTaskVO.setTenderTaskSts(htTenderTaskBean.getTenderTaskSts());
        //流程实例ID
        htTenderTaskVO.setProcessInstId(htTenderTaskBean.getProcessInstId());
        //主提审人ID
        htTenderTaskVO.setApplyUserId(htTenderTaskBean.getApplyUserId());
        //主提审人名称
        htTenderTaskVO.setApplyUserName(htTenderTaskBean.getApplyUserName());
        //能否撤回
        htTenderTaskVO.setIsCanRecall(htTenderTaskBean.getIsCanRecall());
        //审核状态
        htTenderTaskVO.setVerifyState(htTenderTaskBean.getVerifyState());

        htTenderTaskVO.setApproveTime(htTenderTaskBean.getApproveTime());

        List<TbTenderTaskUser> list = commonCacheService.getTenderUsers(htTenderTaskVO.getTenderTaskId());
        List<String> tenderUsers = null;
        if (list != null && !list.isEmpty()) {
            tenderUsers = list.stream().map(TbTenderTaskUser::getUserId).collect(Collectors.toList());
        }
        String showBtnType = null;
        try {
            showBtnType = tenderProcessService.excQueryPageType(paramVO.getLoginUserInfo(),htTenderTaskBean.getDepartmentId(), ProcessDictEnum.Tender.getProcessDictId(), htTenderTaskVO, tenderUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showBtnType;

    }

}
