package com.hd.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.hd.base.enums.*;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.*;
import com.hd.common.entity.*;
import com.hd.common.service.CommonCacheService;
import com.hd.common.service.CommonDictContractTypeService;
import com.hd.common.service.CommonDictCounselTypeService;
import com.hd.common.service.CommonService;
import com.hd.common.util.*;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.dao.HTContractDao;
import com.hd.manager.dao.HTTaskDao;
import com.hd.manager.dao.HTUserQuestionRecordRelationDao;
import com.hd.manager.dao.bean.*;
import com.hd.manager.service.HTTaskArchiveProcessService;
import com.hd.manager.service.HTTaskProcessService;
import com.hd.manager.service.HTTaskService;
import com.hd.manager.vo.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 任务 业务层 实现类
 *
 * @author jwl
 * Created in 2019/7/22 15:31
 */
@Service
public class HTTaskServiceImpl extends BaseServiceImpl implements HTTaskService {

    /**
     * 任务dao 持久层资源
     */
    @Resource
    private HTTaskDao htTaskDao;

    /**
     * 任务Mapper 持久层资源
     */
    @Resource
    private TbTaskEntityMapper tbTaskEntityMapper;

    /**
     * 通用service
     */
    @Resource
    private CommonDictCounselTypeService commonDictCounselTypeService;

    /**
     * 通用service
     */
    @Resource
    private CommonDictContractTypeService commonDictContractTypeService;

    /**
     * 通用service
     */
    @Resource
    private CommonService commonService;

    /**
     * 通用mapper
     */
    @Resource
    private TbUserScheduleRecordEntityMapper tbUserScheduleRecordEntityMapper;

    /**
     * 通用mapper
     */
    @Resource
    private TbTaskUserRelationEntityMapper tbTaskUserRelationEntityMapper;

    /**
     * 问题dao
     */
    @Resource
    private HTUserQuestionRecordRelationDao htUserQuestionRecordRelationDao;

    /**
     * 任务阶段素材mapper
     */
    @Resource
    private TbTaskPeriodMaterialRelationEntityMapper tbTaskPeriodMaterialRelationEntityMapper;

    /**
     * 任务归档文件mapper
     */
    @Resource
    private TbTaskSaveMaterialRelationEntityMapper tbTaskSaveMaterialRelationEntityMapper;

    /**
     * 任务阶段关联mapper
     */
    @Resource
    private TbTaskPeriodRelationEntityMapper tbTaskPeriodRelationEntityMapper;

    /**
     * 任务结算和57的关联mapper
     */
    @Resource
    private TbTableFiveRelationEntityMapper tbTableFiveRelationEntityMapper;

    /**
     * 造价部和2的关联mapper
     */
    @Resource
    private TbTableTwoRelationEntityMapper tbTableTwoRelationEntityMapper;

    /**
     * 开标、评标mapper
     */
    @Resource
    private TbOpenBidRelationEntityMapper tbOpenBidRelationEntityMapper;

    /**
     * A3阶段流程service
     */
    @Resource(type = HTTaskA3ProcessServiceImpl.class)
    private HTTaskProcessService taskA3ProcessService;

    /**
     * 成果文件阶段流程service
     */
    @Resource(type = HTTaskProcessServiceImpl.class)
    private HTTaskProcessService taskProcessService;

    /**
     * 重新编辑流程service
     */
    @Resource(type = HTTaskReeditProcessServiceImpl.class)
    private HTTaskProcessService taskReeditProcessService;

    /**
     * 重新编辑流程service
     */
    @Resource
    private HTTaskArchiveProcessService taskArchiveProcessService;

    @Resource
    private HTTaskArchiveProcessService archiveProcessService;

    /**
     * 缓存service
     */
    @Resource
    private CommonCacheService commonCacheService;

    /**
     * 人员mapper
     */
    @Resource
    private TbUserEntityMapper tbUserEntityMapper;

    /**
     * 重新编辑mapper
     */
    @Resource
    private TbTaskReeditEntityMapper tbTaskReeditEntityMapper;

    @Resource
    private TbTaskPeriodHisTaskInstMapper taskPeriodHisDao;

    @Resource
    private TbTaskReeditHisTaskInstMapper taskReeditHisDao;

    /**
     * 归档任务mapper
     */
    @Resource
    private TbTaskArchiveEntityMapper archiveDao;

    /**
     * 合同dao
     */
    @Resource
    private HTContractDao htContractDao;

    /**
     * 合同mapper
     */
    @Resource
    private TbContractEntityMapper tbContractEntityMapper;

    /**
     * 招标文件专家论证意见表和任务ID的关联表mapper
     */
    @Resource
    private TbExpertOpinionRelationEntityMapper tbExpertOpinionRelationEntityMapper;

    /**
     * 落标通知书和任务关联表mapper
     */
    @Resource
    private TbFailTenderNoticeRelationEntityMapper tbFailTenderNoticeRelationEntityMapper;

    /**
     * 落标通知书和投标人的关联表mapper
     */
    @Resource
    private TbFailTenderUserRelationEntityMapper tbFailTenderUserRelationEntityMapper;

    /**
     * 招标文件确认意见表和任务的关联表mapper
     */
    @Resource
    private TbFileConfirmOpinionRelationEntityMapper tbFileConfirmOpinionRelationEntityMapper;

    /**
     * 项目mapper
     */
    @Resource
    private TbProjectEntityMapper tbProjectEntityMapper;

    /**
     * 自定义工时DAO
     */
    @Resource
    private TbUserCustomTimeMapper userCustomTimeMapper;

    /**
     * 功能展示(查询当前用户在页面身份所展示功能)
     */
    @Override
    @Transactional
    public String excQueryShowBtnType(HTTaskPeriodVO paramVO) throws SystemException {

        String showBtnType = null;

        try {

            // 如果是普通阶段、收集资料阶段、现场人员调整、专家论证意见、招标文件确认意见、落标通知书阶段、开标和投标
            if(Constant.GENERAL_PERIOD_TYPE.equals(paramVO.getPeriodType()) ||
                Constant.GATHER_DATA_PERIOD_TYPE.equals(paramVO.getPeriodType()) ||
                Constant.PERSONNEL_ADJUSTMEN_PERIOD_TYPE.equals(paramVO.getPeriodType()) ||
                Constant.EXPERT_OPINION_PERIOD_TYPE.equals(paramVO.getPeriodType()) ||
                Constant.FILE_CONFIRM_OPINION_PERIOD_TYPE.equals(paramVO.getPeriodType()) ||
                Constant.FAIL_TENDER_NOTICE_PERIOD_TYPE.equals(paramVO.getPeriodType()) ||
                Constant.OPEN_SEALED_TENDERS_PERIOD_TYPE.equals(paramVO.getPeriodType())){

                // 通过任务阶段id查询任务详情
                HTTaskBean htTaskBean = htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());

                // 项目监理部,传入归档流程id
                if(Constant.DEPT_ID_XMJL.equals(htTaskBean.getDepartmentId())){

                    // 归档信息详情
                    HTTaskArchiveBean htTaskArchiveBean = htTaskDao.excSearchTaskArchiveDetailInfoByTaskId(paramVO.getTaskId());

                    // 格式转换 Bean->VO
                    HTTaskArchiveVO htTaskArchiveVO = (HTTaskArchiveVO) ObjectReflectUtils.convertObj(htTaskArchiveBean, HTTaskArchiveVO.class);
                    List<TbTaskUserRelationEntity> list = commonCacheService.getTaskUsers(htTaskArchiveBean.getTaskId());
                    List<String> taskUsers = null;
                    if (list != null && !list.isEmpty()) {
                        taskUsers = list.stream().map(TbTaskUserRelationEntity::getUserId).collect(Collectors.toList());
                    }

                    TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(htTaskBean.getContractId());
                    htTaskArchiveVO.setIsInvalidFlag(tbContractEntity.getIsInvalidFlag());
                    // 查询页面类型
                    showBtnType = taskProcessService.excQueryPageType(
                            paramVO.getLoginUserInfo(), htTaskBean.getDepartmentId(),ProcessDictEnum.Archive.getProcessDictId(), htTaskArchiveVO, taskUsers);

                    // 其他部门，传入任务流程id
                } else {

                    // 格式转换 Bean->VO
                    HTTaskVO htTaskVO = (HTTaskVO) ObjectReflectUtils.convertObj(htTaskBean, HTTaskVO.class);

                    List<TbTaskUserRelationEntity> list = commonCacheService.getTaskUsers(htTaskBean.getTaskId());
                    List<String> taskUsers = null;
                    if (list != null && !list.isEmpty()) {
                        taskUsers = list.stream().map(TbTaskUserRelationEntity::getUserId).collect(Collectors.toList());
                    }

                    TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(htTaskVO.getContractId());
                    htTaskVO.setIsInvalidFlag(tbContractEntity.getIsInvalidFlag());
                    // 查询页面类型
                    showBtnType = taskProcessService.excQueryPageType(
                            paramVO.getLoginUserInfo(), htTaskBean.getDepartmentId(),ProcessDictEnum.Task.getProcessDictId(), htTaskVO, taskUsers);

                }

                // 如果是A3阶段,则设置A3阶段流程id
            } else if (Constant.A3_PERIOD_TYPE.equals(paramVO.getPeriodType())) {

                // 通过任务阶段id查询任务阶段详情
                HTTaskPeriodBean htTaskPeriodBean = htTaskDao.excSearchTaskPeriodDetailInfoByRelationId(paramVO.getRelationId());

                // 格式转换 Bean->VO
                HTTaskPeriodVO htTaskPeriodVO = (HTTaskPeriodVO) ObjectReflectUtils.convertObj(htTaskPeriodBean, HTTaskPeriodVO.class);

                List<TbTaskUserRelationEntity> list = commonCacheService.getTaskUsers(htTaskPeriodBean.getTaskId());
                List<String> taskUsers = null;
                if (list != null && !list.isEmpty()) {
                    taskUsers = list.stream().map(TbTaskUserRelationEntity::getUserId).collect(Collectors.toList());
                }

                // 通过任务阶段id查询任务详情
                HTTaskBean htTaskBean = htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());
                TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(htTaskBean.getContractId());
                htTaskPeriodVO.setIsInvalidFlag(tbContractEntity.getIsInvalidFlag());
                // 查询页面类型
                showBtnType = taskA3ProcessService.excQueryPageType(
                        paramVO.getLoginUserInfo(), htTaskPeriodBean.getDepartmentId(),ProcessDictEnum.Task_A3.getProcessDictId(), htTaskPeriodVO, taskUsers);

                // 如果是成果文件阶段，需要判断是否是重新编辑申请
                // 如果提交了重新编辑申请,则传入重新编辑申请流程id,否则传入成果文件流程id
            } else if (Constant.RESULT_FILE_PERIOD_TYPE.equals(paramVO.getPeriodType())) {

                // 通过任务阶段id查询任务详情
                HTTaskBean htTaskBean = htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());

                // 如果是提交了重新编辑申请
                if (Constant.IS_SUBMIT_REEDIT.equals(htTaskBean.getIsSubmitReedit())) {

                    TbTaskEntity tbTaskEntity = new TbTaskEntity();

                    // 设置任务id
                    tbTaskEntity.setTaskId(StringToolUtils.convertNullObjectToString(htTaskBean.getTaskId()));

                    // 设置审核状态
                    tbTaskEntity.setVerifyState("2");

                    // 通过任务id查询重新编辑申请详情
                    HTTaskReeditBean htTaskReeditBean = htTaskDao.excSearchTaskReeditDetailInfoByRelationId(tbTaskEntity);

                    // 格式转换 Bean->VO
                    HTTaskReeditVO htTaskReeditVO = (HTTaskReeditVO) ObjectReflectUtils.convertObj(htTaskReeditBean, HTTaskReeditVO.class);

                    List<TbTaskUserRelationEntity> list = commonCacheService.getTaskUsers(htTaskBean.getTaskId());
                    List<String> taskUsers = null;
                    if (list != null && !list.isEmpty()) {
                        taskUsers = list.stream().map(TbTaskUserRelationEntity::getUserId).collect(Collectors.toList());
                    }

                    TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(htTaskBean.getContractId());
                    htTaskReeditVO.setIsInvalidFlag(tbContractEntity.getIsInvalidFlag());
                    // 查询页面类型
                    showBtnType = taskReeditProcessService.excQueryPageType(
                            paramVO.getLoginUserInfo(), htTaskReeditBean.getDepartmentId(),ProcessDictEnum.Task_Reedit.getProcessDictId(), htTaskReeditVO, taskUsers);

                    // 如果是成果文件阶段
                } else {

                    // 格式转换 Bean->VO
                    HTTaskVO htTaskVO = (HTTaskVO) ObjectReflectUtils.convertObj(htTaskBean, HTTaskVO.class);

                    List<TbTaskUserRelationEntity> list = commonCacheService.getTaskUsers(htTaskBean.getTaskId());
                    List<String> taskUsers = null;
                    if (list != null && !list.isEmpty()) {
                        taskUsers = list.stream().map(TbTaskUserRelationEntity::getUserId).collect(Collectors.toList());
                    }

                    TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(htTaskBean.getContractId());
                    htTaskVO.setIsInvalidFlag(tbContractEntity.getIsInvalidFlag());
                    // 查询页面类型
                    showBtnType = taskProcessService.excQueryPageType(
                            paramVO.getLoginUserInfo(), htTaskBean.getDepartmentId(),ProcessDictEnum.Task.getProcessDictId(), htTaskVO, taskUsers);

                }

                // 如果是 "5",则传入归档id
            } else if (Constant.ARCHIVE_PERIOD_TYPE.equals(paramVO.getPeriodType())) {

                // 归档信息详情
                HTTaskArchiveBean htTaskArchiveBean = htTaskDao.excSearchTaskArchiveDetailInfoByTaskId(paramVO.getTaskId());

                // 格式转换 Bean->VO
                HTTaskArchiveVO htTaskArchiveVO = (HTTaskArchiveVO) ObjectReflectUtils.convertObj(htTaskArchiveBean, HTTaskArchiveVO.class);

                List<TbTaskUserRelationEntity> list = commonCacheService.getTaskUsers(htTaskArchiveBean.getTaskId());
                List<String> taskUsers = null;
                if (list != null && !list.isEmpty()) {
                    taskUsers = list.stream().map(TbTaskUserRelationEntity::getUserId).collect(Collectors.toList());
                }

                // 通过任务阶段id查询任务详情
                HTTaskBean htTaskBean = htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());
                TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(htTaskBean.getContractId());
                htTaskArchiveVO.setIsInvalidFlag(tbContractEntity.getIsInvalidFlag());
                // 查询页面类型
                showBtnType = archiveProcessService.excQueryPageType(
                        paramVO.getLoginUserInfo(), htTaskArchiveBean.getDepartmentId(),ProcessDictEnum.Archive.getProcessDictId(), htTaskArchiveVO, taskUsers);
            }

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }

        return showBtnType;

    }

    /**
     * 添加落标通知书和任务关联表信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excInsertFailTenderNoticeRelationInfo(HTFailTenderNoticeRelationVO paramVO) throws SystemException {

        try {

            Date currentDate = getCurrentDate();
            String currentUserId = paramVO.getLoginUserInfo().getLoginUserId();

            TbFailTenderNoticeRelationEntityExample tbFailTenderNoticeRelationEntityExample = new TbFailTenderNoticeRelationEntityExample();
            TbFailTenderNoticeRelationEntityExample.Criteria criteria = tbFailTenderNoticeRelationEntityExample.createCriteria();
            criteria.andTaskIdEqualTo(paramVO.getTaskId());
            // 查询信息
            List<TbFailTenderNoticeRelationEntity> tbFailTenderNoticeRelationEntityList =
                    tbFailTenderNoticeRelationEntityMapper.selectByExample(tbFailTenderNoticeRelationEntityExample);

            // 查询的信息不能为空，如果为空的话取第一个元素取不到会有问题
            if(!ListToolUtils.isEmpty(tbFailTenderNoticeRelationEntityList)){
                // 通知id
                String noticeId = tbFailTenderNoticeRelationEntityList.get(0).getNoticeId();
                // 如果通知id不为空，则表明有通知数据，则可以投标人信息
                if(!StringToolUtils.isNull(noticeId)){
                    TbFailTenderUserRelationEntityExample tbFailTenderUserRelationEntityExample = new TbFailTenderUserRelationEntityExample();
                    TbFailTenderUserRelationEntityExample.Criteria criteria2 = tbFailTenderUserRelationEntityExample.createCriteria();
                    criteria2.andNoticeIdEqualTo(noticeId);
                    // 删除投标人信息
                    tbFailTenderUserRelationEntityMapper.deleteByExample(tbFailTenderUserRelationEntityExample);
                }
            }
            // 删除通知书信息
            tbFailTenderNoticeRelationEntityMapper.deleteByExample(tbFailTenderNoticeRelationEntityExample);

            TbFailTenderNoticeRelationEntity tbFailTenderNoticeRelationEntity = new TbFailTenderNoticeRelationEntity();
            // 主键
            tbFailTenderNoticeRelationEntity.setNoticeId(UUID.randomUUID().toString());
            // 任务id
            tbFailTenderNoticeRelationEntity.setTaskId(paramVO.getTaskId());
            // 投标单位名称
            tbFailTenderNoticeRelationEntity.setTenderUnitName(paramVO.getTenderUnitName());
            // 采购代理机构
            tbFailTenderNoticeRelationEntity.setAgentOrg(paramVO.getAgentOrg());
            // 论证时间
            tbFailTenderNoticeRelationEntity.setEditDate(DateToolUtils
                    .convertDateFormat(paramVO.getEditDate(),DateToolUtils.yyyy_MM_dd));
            // 当前登录时间
            tbFailTenderNoticeRelationEntity.setCreateDate(currentDate);
            // 当前登录人id
            tbFailTenderNoticeRelationEntity.setCreateUserId(currentUserId);
            // 当前登录时间
            tbFailTenderNoticeRelationEntity.setUpdateDate(currentDate);
            // 当前登录人id
            tbFailTenderNoticeRelationEntity.setUpdateUserId(currentUserId);
            // 是否删除
            tbFailTenderNoticeRelationEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);
            // 添加信息
            tbFailTenderNoticeRelationEntityMapper.insertSelective(tbFailTenderNoticeRelationEntity);

            // 如果投标人不为空
            if(!ListToolUtils.isEmpty(paramVO.getBidderList())){

                for(HTFailTenderUserRelationVO htFailTenderUserRelationVO:paramVO.getBidderList()){
                    TbFailTenderUserRelationEntity tbFailTenderUserRelationEntity = new TbFailTenderUserRelationEntity();

                    // 关联id
                    tbFailTenderUserRelationEntity.setNoticeId(tbFailTenderNoticeRelationEntity.getNoticeId());
                    // 投标人名称
                    tbFailTenderUserRelationEntity.setTenderUserName(htFailTenderUserRelationVO.getTenderUserName());
                    // 排名次序
                    tbFailTenderUserRelationEntity.setSort(htFailTenderUserRelationVO.getSort());
                    // 评标得分
                    tbFailTenderUserRelationEntity.setBidScore(htFailTenderUserRelationVO.getBidScore());
                    // 当前登录时间
                    tbFailTenderUserRelationEntity.setCreateDate(currentDate);
                    // 当前登录人id
                    tbFailTenderUserRelationEntity.setCreateUserId(currentUserId);
                    // 当前登录时间
                    tbFailTenderUserRelationEntity.setUpdateDate(currentDate);
                    // 当前登录人id
                    tbFailTenderUserRelationEntity.setUpdateUserId(currentUserId);
                    // 是否删除
                    tbFailTenderUserRelationEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);
                    // 添加信息
                    tbFailTenderUserRelationEntityMapper.insertSelective(tbFailTenderUserRelationEntity);
                }

            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 查询落标通知书和任务关联表详情
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTFailTenderNoticeRelationVO excSearchFailTenderNoticeRelationInfoDetail(HTFailTenderNoticeRelationVO paramVO) throws SystemException {

        HTFailTenderNoticeRelationVO htFailTenderNoticeRelationVO = new HTFailTenderNoticeRelationVO();

        try {

            TbFailTenderNoticeRelationEntity tbFailTenderNoticeRelationEntity = new TbFailTenderNoticeRelationEntity();

            tbFailTenderNoticeRelationEntity.setTaskId(paramVO.getTaskId());

            // 查询落标通知书和任务关联表详情
            HTFailTenderNoticeRelationBean returnBean =
                    htTaskDao.excSearchFailTenderNoticeRelationDetail(tbFailTenderNoticeRelationEntity);

            // 查询落标通知书和任务关联表详情，则bean->vo
            if (null != returnBean) {

                // 任务id
                htFailTenderNoticeRelationVO.setTaskId(StringToolUtils
                        .convertNullObjectToString(returnBean.getTaskId()));
                // 投标单位名称
                htFailTenderNoticeRelationVO.setTenderUnitName(StringToolUtils
                        .convertNullObjectToString(returnBean.getTenderUnitName()));
                // 采购代理机构
                htFailTenderNoticeRelationVO.setAgentOrg(StringToolUtils
                        .convertNullObjectToString(returnBean.getAgentOrg()));
                // 日期
                htFailTenderNoticeRelationVO.setEditDate(DateToolUtils
                        .convertDateFormat(returnBean.getEditDate(),DateToolUtils.yyyy_MM_dd));

                String projectName = returnBean.getTaskName();// 项目名称
                if(!StringToolUtils.isNull(returnBean.getProjectName())){// 如果项目名称不为空，则拼接项目名称-任务名称
                    projectName = returnBean.getProjectName()+"-"+returnBean.getTaskName();
                }
                // 项目名称
                htFailTenderNoticeRelationVO.setProjectName(StringToolUtils.convertNullObjectToString(projectName));
                // 项目编号
                htFailTenderNoticeRelationVO.setProjectNum(StringToolUtils
                        .convertNullObjectToString(returnBean.getProjectNum()));

                List<HTFailTenderUserRelationVO> htFailTenderNoticeRelationVOList = new ArrayList<>();

                // 初次查询，noticeId为空
                if(!StringToolUtils.isNull(returnBean.getNoticeId())){

                    TbFailTenderUserRelationEntityExample tbFailTenderUserRelationEntityExample = new TbFailTenderUserRelationEntityExample();
                    TbFailTenderUserRelationEntityExample.Criteria criteria = tbFailTenderUserRelationEntityExample.createCriteria();
                    criteria.andNoticeIdEqualTo(returnBean.getNoticeId());
                    List<TbFailTenderUserRelationEntity> tbFailTenderUserRelationEntityList = tbFailTenderUserRelationEntityMapper.selectByExample(tbFailTenderUserRelationEntityExample);

                    // 如果投标人列表不为空
                    if(!ListToolUtils.isEmpty(tbFailTenderUserRelationEntityList)){

                        for(TbFailTenderUserRelationEntity tbFailTenderUserRelationEntity:tbFailTenderUserRelationEntityList){

                            HTFailTenderUserRelationVO htFailTenderUserRelationVO = new HTFailTenderUserRelationVO();

                            // 投标人
                            htFailTenderUserRelationVO.setTenderUserName(tbFailTenderUserRelationEntity.getTenderUserName());
                            // 次序
                            htFailTenderUserRelationVO.setSort(tbFailTenderUserRelationEntity.getSort());
                            // 评标得分
                            htFailTenderUserRelationVO.setBidScore(tbFailTenderUserRelationEntity.getBidScore());

                            htFailTenderNoticeRelationVOList.add(htFailTenderUserRelationVO);

                        }

                    }
                }

                // 投标人信息
                htFailTenderNoticeRelationVO.setBidderList(htFailTenderNoticeRelationVOList);

            }

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htFailTenderNoticeRelationVO;
    }

    /**
     * 添加招标文件确认意见表和任务的关联表信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excInsertFileConfirmOpinionRelationInfo(HTFileConfirmOpinionRelationVO paramVO) throws SystemException {

        try {

            Date currentDate = getCurrentDate();
            String currentUserId = paramVO.getLoginUserInfo().getLoginUserId();

            TbFileConfirmOpinionRelationEntityExample tbFileConfirmOpinionRelationEntityExample = new TbFileConfirmOpinionRelationEntityExample();
            TbFileConfirmOpinionRelationEntityExample.Criteria criteria = tbFileConfirmOpinionRelationEntityExample.createCriteria();
            criteria.andTaskIdEqualTo(paramVO.getTaskId());

            // 删除信息
            tbFileConfirmOpinionRelationEntityMapper.deleteByExample(tbFileConfirmOpinionRelationEntityExample);

            HTFileConfirmOpinionRelationBean htFileConfirmOpinionRelationBean = new HTFileConfirmOpinionRelationBean();

            // 任务id
            htFileConfirmOpinionRelationBean.setTaskId(paramVO.getTaskId());

            // 投标控制价
            htFileConfirmOpinionRelationBean.setTenderSumLimit(NumberToolUtils
                    .convertNullToInteger(paramVO.getTenderSumLimit()));

            // 采购人
            htFileConfirmOpinionRelationBean.setPurchaser(paramVO.getPurchaser());

            // 采购代理机构
            htFileConfirmOpinionRelationBean.setAgentOrg(paramVO.getAgentOrg());

            // 是否同意
            htFileConfirmOpinionRelationBean.setIsAgree(paramVO.getIsAgree());

            // 审核意见
            htFileConfirmOpinionRelationBean.setApproveOpinion(paramVO.getApproveOpinion());

            // 论证时间
            htFileConfirmOpinionRelationBean.setEditDate(DateToolUtils
                    .convertDateFormat(paramVO.getEditDate(),DateToolUtils.yyyy_MM_dd));

            // 当前登录时间
            htFileConfirmOpinionRelationBean.setCreateDate(currentDate);

            // 当前登录人id
            htFileConfirmOpinionRelationBean.setCreateUserId(currentUserId);

            // 当前登录时间
            htFileConfirmOpinionRelationBean.setUpdateDate(currentDate);

            // 当前登录人id
            htFileConfirmOpinionRelationBean.setUpdateUserId(currentUserId);

            // 是否删除
            htFileConfirmOpinionRelationBean.setIsDelFlg(StringToolUtils.STRING_ZERO);

            // 添加信息
            htTaskDao.insertSelective(htFileConfirmOpinionRelationBean);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 查询招标文件确认意见表和任务的关联表详情
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTFileConfirmOpinionRelationVO excSearchFileConfirmOpinionRelationDetail(HTFileConfirmOpinionRelationVO paramVO) throws SystemException {

        HTFileConfirmOpinionRelationVO htFileConfirmOpinionRelationVO = new HTFileConfirmOpinionRelationVO();

        try {

            TbFileConfirmOpinionRelationEntity tbFileConfirmOpinionRelationEntity = new TbFileConfirmOpinionRelationEntity();

            tbFileConfirmOpinionRelationEntity.setTaskId(paramVO.getTaskId());

            // 查询招标文件确认意见表和任务的关联表详情
            HTFileConfirmOpinionRelationBean returnBean =
                    htTaskDao.excSearchFileConfirmOpinionRelationDetail(tbFileConfirmOpinionRelationEntity);

            // 查询招标文件确认意见表和任务的关联表详情，则bean->vo
            if (null != returnBean) {

                // 任务id
                htFileConfirmOpinionRelationVO.setTaskId(StringToolUtils
                        .convertNullObjectToString(returnBean.getTaskId()));

                String projectName = returnBean.getTaskName();// 项目名称
                if(!StringToolUtils.isNull(returnBean.getProjectName())){// 如果项目名称不为空，则拼接项目名称-任务名称
                    projectName = returnBean.getProjectName()+"-"+returnBean.getTaskName();
                }

                // 项目名称
                htFileConfirmOpinionRelationVO.setProjectName(StringToolUtils.convertNullObjectToString(projectName));

                // 项目编号
                htFileConfirmOpinionRelationVO.setProjectNum(StringToolUtils
                        .convertNullObjectToString(returnBean.getProjectNum()));

                // 如果有招标文件数据
                if(!StringToolUtils.isNull(returnBean.getConfirmOpinionId())){

                    // 招标控制价
                    htFileConfirmOpinionRelationVO.setTenderSumLimit(StringToolUtils
                            .convertNullObjectToString(returnBean.getTenderSumLimit()));

                    // 采购人
                    htFileConfirmOpinionRelationVO.setPurchaser(StringToolUtils
                            .convertNullObjectToString(returnBean.getPurchaser()));

                    // 采购代理机构
                    htFileConfirmOpinionRelationVO.setAgentOrg(StringToolUtils
                            .convertNullObjectToString(returnBean.getAgentOrg()));

                    // 是否同意
                    htFileConfirmOpinionRelationVO.setIsAgree(StringToolUtils
                            .convertNullObjectToString(returnBean.getIsAgree()));

                    // 审核意见
                    htFileConfirmOpinionRelationVO.setApproveOpinion(StringToolUtils
                            .convertNullObjectToString(returnBean.getApproveOpinion()));

                    // 日期
                    htFileConfirmOpinionRelationVO.setEditDate(DateToolUtils
                            .convertDateFormat(returnBean.getEditDate(),DateToolUtils.yyyy_MM_dd));

                }

            }

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htFileConfirmOpinionRelationVO;
    }

    /**
     * 添加专家论证意见表信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excInsertExpertOpinionRelationInfo(HTExpertOpinionRelationVO paramVO) throws SystemException {

        try {

            Date currentDate = getCurrentDate();
            String currentUserId = paramVO.getLoginUserInfo().getLoginUserId();

            TbExpertOpinionRelationEntityExample tbExpertOpinionRelationEntityExample = new TbExpertOpinionRelationEntityExample();
            TbExpertOpinionRelationEntityExample.Criteria criteria = tbExpertOpinionRelationEntityExample.createCriteria();
            criteria.andTaskIdEqualTo(paramVO.getTaskId());

            // 删除信息
            tbExpertOpinionRelationEntityMapper.deleteByExample(tbExpertOpinionRelationEntityExample);

            TbExpertOpinionRelationEntity tbExpertOpinionRelationEntity = new TbExpertOpinionRelationEntity();

            // 任务id
            tbExpertOpinionRelationEntity.setTaskId(paramVO.getTaskId());

            // 采购人
            tbExpertOpinionRelationEntity.setPurchaser(paramVO.getPurchaser());

            // 采购代理机构
            tbExpertOpinionRelationEntity.setAgentOrg(paramVO.getAgentOrg());

            // 采购类别
            tbExpertOpinionRelationEntity.setPurchaserType(paramVO.getPurchaserType());

            // 专家论证意见
            tbExpertOpinionRelationEntity.setExpertOpinion(paramVO.getExpertOpinion());

            // 论证专家签字
            tbExpertOpinionRelationEntity.setExpertSign(paramVO.getExpertSign());

            // 备注
            tbExpertOpinionRelationEntity.setRemark(paramVO.getRemark());

            // 论证时间
            tbExpertOpinionRelationEntity.setProveTime(DateToolUtils
                    .convertDateFormat(paramVO.getProveTime(),DateToolUtils.yyyy_MM_dd));

            // 当前登录时间
            tbExpertOpinionRelationEntity.setCreateDate(currentDate);

            // 当前登录人id
            tbExpertOpinionRelationEntity.setCreateUserId(currentUserId);

            // 当前登录时间
            tbExpertOpinionRelationEntity.setUpdateDate(currentDate);

            // 当前登录人id
            tbExpertOpinionRelationEntity.setUpdateUserId(currentUserId);

            // 是否删除
            tbExpertOpinionRelationEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);

            // 添加信息
            tbExpertOpinionRelationEntityMapper.insertSelective(tbExpertOpinionRelationEntity);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 查询专家论证意见表详情
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTExpertOpinionRelationVO excSearchExpertOpinionRelationDetail(HTExpertOpinionRelationVO paramVO) throws SystemException {

        HTExpertOpinionRelationVO htExpertOpinionRelationVO = new HTExpertOpinionRelationVO();

        try {

            TbExpertOpinionRelationEntity tbExpertOpinionRelationEntity = new TbExpertOpinionRelationEntity();

            tbExpertOpinionRelationEntity.setTaskId(paramVO.getTaskId());

            // 查询专家论证意见表详情
            HTExpertOpinionRelationBean returnBean =
                    htTaskDao.excSearchExpertOpinionRelationDetail(tbExpertOpinionRelationEntity);

            // 查询专家论证意见表详情，则bean->vo
            if (null != returnBean) {

                // 任务id
                htExpertOpinionRelationVO.setTaskId(StringToolUtils
                        .convertNullObjectToString(returnBean.getTaskId()));

                String projectName = returnBean.getTaskName();// 项目名称
                if(!StringToolUtils.isNull(returnBean.getProjectName())){// 如果项目名称不为空，则拼接项目名称-任务名称
                    projectName = returnBean.getProjectName()+"-"+returnBean.getTaskName();
                }

                // 项目名称
                htExpertOpinionRelationVO.setProjectName(StringToolUtils.convertNullObjectToString(projectName));

                // 项目编号
                htExpertOpinionRelationVO.setProjectNum(StringToolUtils
                        .convertNullObjectToString(returnBean.getProjectNum()));

                // 如果有专家论证意见数据
                if(!StringToolUtils.isNull(returnBean.getExpertOpinionId())){

                    // 采购人
                    htExpertOpinionRelationVO.setPurchaser(StringToolUtils
                            .convertNullObjectToString(returnBean.getPurchaser()));

                    // 采购代理机构
                    htExpertOpinionRelationVO.setAgentOrg(StringToolUtils
                            .convertNullObjectToString(returnBean.getAgentOrg()));

                    // 采购类别
                    htExpertOpinionRelationVO.setPurchaserType(StringToolUtils
                            .convertNullObjectToString(returnBean.getPurchaserType()));

                    // 专家论证意见
                    htExpertOpinionRelationVO.setExpertOpinion(StringToolUtils
                            .convertNullObjectToString(returnBean.getExpertOpinion()));

                    // 论证专家签字
                    htExpertOpinionRelationVO.setExpertSign(StringToolUtils
                            .convertNullObjectToString(returnBean.getExpertSign()));

                    // 备注
                    htExpertOpinionRelationVO.setRemark(StringToolUtils
                            .convertNullObjectToString(returnBean.getRemark()));

                    // 论证时间
                    htExpertOpinionRelationVO.setProveTime(DateToolUtils
                            .convertDateFormat(returnBean.getProveTime(),DateToolUtils.yyyy_MM_dd));

                }

            }

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htExpertOpinionRelationVO;
    }

    /**
     * 任务阶段是否完成
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    public void updateIsFinish(HTTaskPeriodVO paramVO) throws SystemException {

        try {

            TbTaskPeriodRelationEntity tbTaskPeriodRelationEntity = new TbTaskPeriodRelationEntity();

            // 设置任务阶段id
            tbTaskPeriodRelationEntity.setRelationId(paramVO.getRelationId());
            // 是否忽略
            tbTaskPeriodRelationEntity.setIsFinish(paramVO.getIsFinish());

            // 任务阶段是否完成
            tbTaskPeriodRelationEntityMapper.updateByPrimaryKeySelective(tbTaskPeriodRelationEntity);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 任务阶段是否忽略
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    public void updateIsIgnore(HTTaskPeriodVO paramVO) throws SystemException {

        try {

            TbTaskPeriodRelationEntity tbTaskPeriodRelationEntity = new TbTaskPeriodRelationEntity();

            // 设置任务阶段id
            tbTaskPeriodRelationEntity.setRelationId(paramVO.getRelationId());
            // 是否忽略
            tbTaskPeriodRelationEntity.setIsIgnore(paramVO.getIsIgnore());

            // 任务阶段是否忽略
            tbTaskPeriodRelationEntityMapper.updateByPrimaryKeySelective(tbTaskPeriodRelationEntity);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 项目监理归档信息列表
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    public Map<String, Object> excSearchArchiveListInfo(HTTaskArchiveVO paramVO) throws SystemException {

        Map<String, Object> resultMap = new HashMap<>();

        try {

            // vo->bean
            HTTaskArchiveBean paramBean = (HTTaskArchiveBean) ObjectReflectUtils.convertObj(paramVO, HTTaskArchiveBean.class);
            // 设置分页参数
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            TbTaskArchiveEntityExample tbTaskArchiveEntityExample = new TbTaskArchiveEntityExample();
            TbTaskArchiveEntityExample.Criteria criteria = tbTaskArchiveEntityExample.createCriteria();
            // 设置任务id
            criteria.andTaskIdEqualTo(paramVO.getTaskId());
            // 审核时间不能为空
            criteria.andApproveTimeIsNotNull();
            // 按照创建时间倒序排列
            tbTaskArchiveEntityExample.setOrderByClause(" approve_time desc ");
            // 页数
            tbTaskArchiveEntityExample.setLimitStart(paramBean.getPageNumber());
            // 每页显示的条数
            tbTaskArchiveEntityExample.setLimitEnd(paramBean.getPageSize());
            // 查询列表数据
            List<TbTaskArchiveEntity> tbTaskArchiveEntityList = archiveDao.selectByExample(tbTaskArchiveEntityExample);
            // 查询总条数
            long dataCount = archiveDao.countByExample(tbTaskArchiveEntityExample);

            List<HTTaskArchiveVO> htTaskArchiveVOList = new ArrayList<>();

            // 定义变量，当做序号
            int i = 0;

            // entity->vo
            for (TbTaskArchiveEntity tbTaskArchiveEntity:tbTaskArchiveEntityList){

                i++;

                HTTaskArchiveVO htTaskArchiveVO = new HTTaskArchiveVO();

                // 序号
                htTaskArchiveVO.setOrderNumber(StringToolUtils.convertNullObjectToString(i));

                // 归档时间
                htTaskArchiveVO.setApproveTime(DateToolUtils
                        .convertDateFormat(tbTaskArchiveEntity.getApproveTime(),DateToolUtils.yyyy_MM_dd));

                // 归档人
                htTaskArchiveVO.setApproveUserName(StringToolUtils
                        .convertNullObjectToString(tbTaskArchiveEntity.getApproveUserName()));

                // 档案编号
                htTaskArchiveVO.setFileNumber(StringToolUtils
                        .convertNullObjectToString(tbTaskArchiveEntity.getFileNumber()));

                htTaskArchiveVOList.add(htTaskArchiveVO);

            }

            resultMap.put(Constant.DATA_LIST_KEY,htTaskArchiveVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,dataCount);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    /**
     * 通过任务阶段id查询审核阶段文件
     *
     * @return
     */
    @Override
    public HTTaskPeriodMaterialVO excSearchMaterialByTaskPeriodId(HTTaskPeriodMaterialVO paramVO) throws SystemException {

        try {

            HTTaskPeriodMaterialVO htTaskPeriodMaterialVO= new HTTaskPeriodMaterialVO();

            HTTaskPeriodMaterialBean paramBean = new HTTaskPeriodMaterialBean();

            // 设置任务阶段id
            paramBean.setTaskPeriodId(paramVO.getTaskPeriodId());

            // 通过任务id查询审核阶段文件
            HTTaskPeriodMaterialBean retBean = htTaskDao.excSearchMaterialByTaskPeriodId(paramBean);

            // 如果文件不为空
            if (null != retBean){

                // 唯一标识
                htTaskPeriodMaterialVO.setRelationId(retBean.getRelationId());

                // 关联阶段ID
                htTaskPeriodMaterialVO.setTaskPeriodId(retBean.getTaskPeriodId());

                // 获取文件名称
                String materialName = retBean.getMaterialName();

                // 设置文件名称
                htTaskPeriodMaterialVO.setMaterialName(materialName);

                // 获取文件地址
                String materialPath = retBean.getMaterialPath();

                // 设置文件地址
                htTaskPeriodMaterialVO.setMaterialPath(materialPath);

                // 是否提审（0：否 1：是）
                htTaskPeriodMaterialVO.setIsReview(retBean.getIsReview());

                // 给onlyOffice使用的绝对路径
                htTaskPeriodMaterialVO.setReviewFilePath(OnlyOfficeUtils.getOnlyOfficeSaveCallbackUrl());

                // 给onlyOffice使用的真实文件名称
                htTaskPeriodMaterialVO.setReviewFileName(materialName);

                // 给onlyOffice使用的唯一标识
                htTaskPeriodMaterialVO.setOnlyOfficeEditKey(OnlyOfficeUtils.generateOnlyOfficeEditKey());

                // 给onlyOffice使用的下载地址
                htTaskPeriodMaterialVO.setOnlyOfficeAbsDownLoadPath(
                        OnlyOfficeUtils.getFileDownloadAbsoluteUrl(Constant.COMMON_MODULE_URL+"/downloadFile?filePathName="+
                                materialPath+"&realFileName="+materialName));

            }

            return htTaskPeriodMaterialVO;

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 发起归档(归档阶段 项目监理部)
     */
    @Override
    @Transactional
    public void excInitiateArchive(HTTaskVO paramVO) throws SystemException {

        try {

            // =====================================判断项目是否结束 start=========================================

            // 查询该任务所属的项目的状态
            HTTaskBean htTaskBean = htTaskDao.excSearchTaskDetailInfoByTaskId(paramVO.getTaskId());

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            //==================================== 任务详情 start ============================================
            //启动工作流
            TbTaskArchiveEntityExample example = new TbTaskArchiveEntityExample();
            TbTaskArchiveEntityExample.Criteria criteria = example.createCriteria();
            criteria.andTaskIdEqualTo(paramVO.getTaskId());
            criteria.andVerifyStateIsNull();
            List<TbTaskArchiveEntity> archiveList = archiveDao.selectByExample(example);
            if(archiveList!=null && !archiveList.isEmpty()){
                TbTaskArchiveEntity taskArchiveEntity = archiveList.get(0);
                // 格式转换 Bean->VO
                HTTaskArchiveVO taskArchiveVO = (HTTaskArchiveVO) ObjectReflectUtils.convertObj(taskArchiveEntity, HTTaskArchiveVO.class);
                // 设置当前登录人信息
                taskArchiveVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                //启动工作流
                Map<String, Object> variable = new HashMap<>();
                variable.put(WorkFlowConstant.KEY_ARCHIVE_VO, taskArchiveVO);
                CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
                String departmentId = ArchiveEnum.getDeptId(ArchiveEnum.AffirmArchive.getNodeKey());
                commonActivitiListenVO.setDepartmentId(departmentId);
                variable.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
                variable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
                variable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
                archiveProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(),taskArchiveVO.getDelegateTaskId(), variable);
            }else{
                logger.error("任务数据异常");
                throw new SystemException();
            }
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 确认归档(归档阶段 项目监理部)
     */
    @Override
    @Transactional
    public void excConfirmArchiveXM(HTTaskVO paramVO) throws LogicException,SystemException {

        try {

            // 当前时间
            Date currentDate = getCurrentDate();

            HTTaskArchiveBean htTaskArchiveBean = htTaskDao.excSearchTaskArchiveDetailInfoByTaskId(paramVO.getTaskId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskArchiveBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            //==================================== 委托任务id比较 start ============================================

            // 如果有数据
            if (htTaskArchiveBean != null) {
                // 对比委托任务id,如果不同，则提示该任务已被确认归档，请重新获取数据！
                if (!paramVO.getDelegateTaskId().equals(htTaskArchiveBean.getDelegateTaskId())) {
                    throw new LogicException(MessageConstant.MSG_ERROR_TASK_0010);
                }
            }

            //==================================== 委托任务id比较 start ============================================

            //==================================== 修改其他阶段文件is_review为1 start ============================================

            HTTaskPeriodMaterialBean htTaskPeriodMaterialBean = new HTTaskPeriodMaterialBean();
            // 设置任务id
            htTaskPeriodMaterialBean.setTaskId(StringToolUtils.convertNullObjectToString(paramVO.getTaskId()));
            // 设置修改时间
            htTaskPeriodMaterialBean.setUpdateDate(currentDate);
            // 设置修改人id
            htTaskPeriodMaterialBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 通过任务id修改该任务下的阶段文件提审状态
            htTaskDao.excUpdateIsReviewByTaskId(htTaskPeriodMaterialBean);

            //==================================== 修改其他阶段文件is_review为1 start ============================================

            //==================================== 修改档案编号 start ============================================

            HTTaskArchiveBean paramBean = new HTTaskArchiveBean();
            // 设置任务id
            paramBean.setTaskId(paramVO.getTaskId());
            // 设置档案编号
            paramBean.setFileNumber(paramVO.getFileNumber());
            // 修改时间
            paramBean.setUpdateDate(currentDate);
            // 修改人id
            paramBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 通过任务id修改该任务下归档文件编号
            htTaskDao.excUpdateFileNumberTaskId(paramBean);

            //==================================== 修改档案编号 start ============================================

            //==================================== 任务归档详情 start ============================================

            // 格式转换 Bean->VO
            HTTaskArchiveVO htTaskArchiveVO = (HTTaskArchiveVO) ObjectReflectUtils.convertObj(htTaskArchiveBean, HTTaskArchiveVO.class);

            //启动工作流
            Map<String, Object> variables = new HashMap<>();
            // 登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            // 登录人信息
            htTaskArchiveVO.setLoginUserInfo(userInfo);
            // 审批人ID
            htTaskArchiveVO.setApproveUserId(userInfo.getLoginUserId());
            // 审批人名称
            htTaskArchiveVO.setApproveUserName(userInfo.getUserName());
            variables.put(WorkFlowConstant.KEY_ARCHIVE_VO, htTaskArchiveVO);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            archiveProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);

            //重新预启动归档流程
            //启动工作流
            TbTaskArchiveEntity tbTaskArchiveEntity = new TbTaskArchiveEntity();
            tbTaskArchiveEntity.setTaskArchiveId(UUID.randomUUID().toString());
            tbTaskArchiveEntity.setTaskId(paramVO.getTaskId());
            tbTaskArchiveEntity.setDepartmentId(htTaskArchiveVO.getDepartmentId());
            archiveDao.insertSelective(tbTaskArchiveEntity);
            HTTaskArchiveVO archiveVO = (HTTaskArchiveVO) ObjectReflectUtils.convertObj(tbTaskArchiveEntity, HTTaskArchiveVO.class);
            archiveVO.setLoginUserInfo(paramVO.getLoginUserInfo());
            Map<String, Object> var = new HashMap<>();
            var.put(WorkFlowConstant.KEY_ARCHIVE_VO, archiveVO);
            CommonActivitiListenVO listenVO = new CommonActivitiListenVO();
            listenVO.setDepartmentId(archiveVO.getDepartmentId());
            var.put(WorkFlowConstant.KEY_LISTENER_VO, listenVO);
            var.put(WorkFlowConstant.KEY_DELEGATE_USER, htTaskArchiveVO.getApplyUserId());
            var.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
            var.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            archiveProcessService.excStart(var);

            //==================================== 工作流 end ============================================

            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(htTaskArchiveBean.getApplyTime(),DateToolUtils.yyyy_MM_dd);

            HTContractBean htContractBean = new HTContractBean();
            // 设置当前登录用户id
            htContractBean.setCurrentUserId(userInfo.getLoginUserId());
            // 设置部门id
            htContractBean.setDepartmentId(Constant.DEPT_ID_XZ);

            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            String tempMessageContentStr = "您在 "+applyTime+" 发起的 "
                    +htTaskArchiveBean.getProjectNum()+"项目"
                    +htTaskArchiveBean.getProjectNum()+"-"+htTaskArchiveBean.getContractNum()+"合同 "
                    +htTaskArchiveBean.getTaskName()+"任务的 成果文件 归档 申请 ，被 "
                    +htContractBeanList.get(0).getJobName()+"("+userInfo.getUserName()+")确认归档了";
            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(htTaskArchiveBean.getApplyUserId());
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
                    ,htTaskArchiveBean.getOpenId(),htTaskArchiveBean.getApplyUserName(),tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 根据阶段ID查询阶段关联文件(成果文件阶段)
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskVO searchTaskMaterialInfo(HTTaskVO paramVO) throws SystemException {

        HTTaskVO htTaskVO = new HTTaskVO();

        try {

            // 根据阶段ID查询阶段关联文件
            TbTaskEntity tbTaskEntity = tbTaskEntityMapper.selectByPrimaryKey(paramVO.getTaskId());

            if (Constant.IS_DRAFT.equals(tbTaskEntity.getIsDraftFlg())
                    &&!paramVO.getLoginUserInfo().getLoginUserId().equals(tbTaskEntity.getAddDraftUserId())){
                return htTaskVO;
            }
            // 任务文件名称
            String tempTaskFileNameStr = StringToolUtils.STRING_EMPTY;

            // 任务文件路径
            String tempTaskFilePathStr = StringToolUtils.STRING_EMPTY;

            // onlyOffice使用的下载地址
            String tempOfficePath = null;

            // 如果任务文件类型为自定义 则取得自定义任务文件名称和路径
            if (StringToolUtils.STRING_ZERO.equals(tbTaskEntity.getTaskType())){

                // 自定义任务文件真实名字
                tempTaskFileNameStr = StringToolUtils.convertNullObjectToString(tbTaskEntity.getTaskMaterialName());

                // 自定义任务文件路径
                tempTaskFilePathStr = StringToolUtils.convertNullObjectToString(tbTaskEntity.getTaskMaterialPath());

                tempOfficePath = Constant.COMMON_MODULE_URL+"/downloadFile?filePathName="+
                        StringToolUtils.convertNullObjectToString(tbTaskEntity.getTaskMaterialPath())
                        +"&realFileName="+tempTaskFileNameStr;

                htTaskVO.setReportNum(StringToolUtils.convertNullObjectToString(tbTaskEntity.getReportNum()));

                // 如果任务文件类型为模板 则取得模板名称和生成路径
            } else if(StringToolUtils.STRING_ONE.equals(tbTaskEntity.getTaskType())){

                if(!StringToolUtils.STRING_EMPTY.equals(StringToolUtils.convertNullObjectToString(tbTaskEntity.getFormworkType()))){
                    // 任务文件模板名称
                    tempTaskFileNameStr = BusinessUtils.convertTaskTemplateTypeToStateStr(
                            NumberToolUtils.convertNullToInteger(tbTaskEntity.getFormworkType()));

                    // 任务文件模板生成路径
                    tempTaskFilePathStr = "/taskExecute/downLoad/downLoadTaskExecuteWord?taskId="+tbTaskEntity.getTaskId();

                    tempOfficePath = "/taskExecute/downLoad/downLoadTaskExecuteWord?taskId="+tbTaskEntity.getTaskId();
                }

            }

            // 成果文件份数
            htTaskVO.setReportCopies(StringToolUtils.convertNullObjectToString(tbTaskEntity.getReportCopies()));

            // 文件名称
            htTaskVO.setTaskMaterialName(tempTaskFileNameStr);

            // 文件路径
            htTaskVO.setTaskMaterialPath(tempTaskFilePathStr);

            // 任务文件类型
            htTaskVO.setTaskType(StringToolUtils.convertNullObjectToString(tbTaskEntity.getTaskType()));

            // 设置是否提交了重新编辑申请
            htTaskVO.setIsSubmitReedit(tbTaskEntity.getIsSubmitReedit());

            // 设置提审人名称
            htTaskVO.setUserName(tbTaskEntity.getApplyUserName());

            // 如果任务文件类型为自定义 则取得自定义任务文件名称和路径
            if (!StringToolUtils.isNull(tempTaskFilePathStr) && (
                    StringToolUtils.STRING_ZERO.equals(tbTaskEntity.getTaskType()) ||
                    StringToolUtils.STRING_ONE.equals(tbTaskEntity.getTaskType()))){

                // 给onlyOffice使用的绝对路径
                htTaskVO.setReviewFilePath(OnlyOfficeUtils.getOnlyOfficeSaveCallbackUrl());

                // 给onlyOffice使用的真实文件名称
                htTaskVO.setReviewFileName(tempTaskFileNameStr);

                // 给onlyOffice使用的唯一标识
                htTaskVO.setOnlyOfficeEditKey(OnlyOfficeUtils.generateOnlyOfficeEditKey());

                //给onlyOffice使用的下载地址
                htTaskVO.setOnlyOfficeAbsDownLoadPath(
                        OnlyOfficeUtils.getFileDownloadAbsoluteUrl(tempOfficePath));

            }

        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htTaskVO;
    }

    /**
     * 提交重新编辑申请
     */
    @Override
    @Transactional
    public void excSubmitReedit(HTTaskReeditVO paramVO) throws SystemException {

        try {

            // 通过任务阶段id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            // 查询任务id
            TbTaskPeriodRelationEntity tbTaskPeriodRelationEntity =
                    tbTaskPeriodRelationEntityMapper.selectByPrimaryKey(paramVO.getRelationId());

            TbTaskEntity tbTaskEntity = new TbTaskEntity();

            // 设置任务id
            tbTaskEntity.setTaskId(StringToolUtils.convertNullObjectToString(tbTaskPeriodRelationEntity.getTaskId()));

            // 通过任务id查询重新编辑申请列表
            List<HTTaskReeditBean> htTaskReeditBeanList =
                    htTaskDao.excSearchTaskReeditListByRelationId(tbTaskEntity);

            // 定义审核状态是否为null
            boolean tempVerifyStateNotNullFlg = true;

            // 如果返回集合不为空，则判断数据是否是审核中
            if (!ListToolUtils.isEmpty(htTaskReeditBeanList)) {

                // 遍历数据
                for (HTTaskReeditBean htTaskReeditBean : htTaskReeditBeanList) {

                    // 如果审核状态为null,则走工作流
                    if (htTaskReeditBean.getVerifyState() == null) {

                        // 跳转节点
                        htTaskReeditBean.setSubmitNode(paramVO.getSubmitNode());

                        // 格式转换 Bean->VO
                        HTTaskReeditVO htTaskReeditVO = (HTTaskReeditVO) ObjectReflectUtils.convertObj(htTaskReeditBean, HTTaskReeditVO.class);

                        // 设置当前登录人信息
                        htTaskReeditVO.setLoginUserInfo(paramVO.getLoginUserInfo());

                        //启动工作流
                        Map<String, Object> variable = new HashMap<>();
                        variable.put(WorkFlowConstant.KEY_TASK_REEDIT_VO, htTaskReeditVO);
                        CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
                        String departmentId = StringUtils.isBlank(TaskReeditEnum.getDeptId(htTaskReeditVO.getSubmitNode())) ? htTaskReeditVO.getDepartmentId() : TaskReeditEnum.getDeptId(htTaskReeditVO.getSubmitNode());
                        commonActivitiListenVO.setDepartmentId(departmentId);
                        variable.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
                        variable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
                        variable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
                        taskReeditProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(),htTaskReeditVO.getDelegateTaskId(), variable);

                        tempVerifyStateNotNullFlg = false;

                        break;
                    }

                }

                // 如果审核状态没有为Null的
                if (tempVerifyStateNotNullFlg) {

                    // ======================================添加重新编辑记录 start=========================================

                    TbTaskReeditEntity tbTaskReeditEntity = new TbTaskReeditEntity();

                    // 设置任务id
                    tbTaskReeditEntity.setTaskId(tbTaskEntity.getTaskId());

                    // 设置任务流程id
                    tbTaskReeditEntity.setTaskProcessInstId(tbTaskPeriodRelationEntity.getTaskProcessInstId());

                    // 插入重新编辑记录
                    tbTaskReeditEntityMapper.insertSelective(tbTaskReeditEntity);

                    //启动工作流
                    HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) ObjectReflectUtils.convertObj(tbTaskReeditEntity, HTTaskReeditVO.class);

                    // 设置部门id
                    taskReeditVO.setDepartmentId(htTaskBean.getDepartmentId());

                    taskReeditVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                    Map<String, Object> reeditVariable = new HashMap<>();
                    reeditVariable.put(WorkFlowConstant.KEY_TASK_REEDIT_VO, taskReeditVO);
                    CommonActivitiListenVO reeditListenerVO = new CommonActivitiListenVO();
                    reeditListenerVO.setDepartmentId(taskReeditVO.getDepartmentId());
                    reeditVariable.put(WorkFlowConstant.KEY_LISTENER_VO, reeditListenerVO);
                    reeditVariable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
                    reeditVariable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
                    taskReeditProcessService.excStart(reeditVariable);

                    // ======================================添加重新编辑记录 end=========================================

                    TbTaskReeditEntity taskReeditEntity = tbTaskReeditEntityMapper.selectByPrimaryKey(tbTaskReeditEntity.getReeditId());


                    // 跳转节点
                    taskReeditEntity.setSubmitNode(paramVO.getSubmitNode());

                    // 格式转换 Bean->VO
                    HTTaskReeditVO htTaskReeditVO = (HTTaskReeditVO) ObjectReflectUtils.convertObj(taskReeditEntity, HTTaskReeditVO.class);

                    // 设置部门id
                    htTaskReeditVO.setDepartmentId(htTaskBean.getDepartmentId());

                    // 设置当前登录人信息
                    htTaskReeditVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                    //启动工作流
                    Map<String, Object> variable = new HashMap<>();
                    variable.put(WorkFlowConstant.KEY_TASK_REEDIT_VO, htTaskReeditVO);
                    CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
                    String departmentId = StringUtils.isBlank(TaskReeditEnum.getDeptId(htTaskReeditVO.getSubmitNode())) ? htTaskReeditVO.getDepartmentId() : TaskReeditEnum.getDeptId(htTaskReeditVO.getSubmitNode());
                    commonActivitiListenVO.setDepartmentId(departmentId);
                    variable.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
                    variable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
                    variable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
                    taskReeditProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(),htTaskReeditVO.getDelegateTaskId(), variable);
                }

            }

            // ================================================ 插入消息信息 start =====================================================

            Date currentDate = getCurrentDate();

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(htTaskBean.getApplyTime(),DateToolUtils.yyyy_MM_dd);

            HTContractBean htContractBean = new HTContractBean();
            // 设置当前登录用户id
            htContractBean.setCurrentUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置部门id
            htContractBean.setDepartmentId(htTaskBean.getDepartmentId());
            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            // 职位和查询得到的人员id
            HTTaskBean returnBean = commonService.excSearchUserIdForMessage("ManagerVerify",htTaskBean.getDepartmentId());

            // 消息内容
            String tempMessageContentStr = "由"+htContractBeanList.get(0).getJobName()+"("+paramVO.getLoginUserInfo().getUserName()+")"+applyTime+"发起的"
                    +htTaskBean.getProjectNum()+"项目"
                    +htTaskBean.getProjectNum()+"-"+htTaskBean.getContractNum()+"合同"
                    +htTaskBean.getTaskName()+"任务的重新编辑申请该由您审核了";

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

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 查询重新编辑审核列表
     *
     * @return 重新编辑审核列表
     */
    @Override
    @Transactional
    public Map<String, Object> excSearchTaskReeditHisTaskInst(HTTaskPeriodVO paramVO) throws SystemException {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            HTTaskPeriodBean paramBean = new HTTaskPeriodBean();

            // 设置任务id
            paramBean.setTaskId(StringToolUtils
                    .convertNullObjectToString(paramVO.getTaskId()));

            // 查询重新编辑审核列表
            List<HTTaskPeriodBean> htTaskPeriodBeanList =
                    htTaskDao.excSearchTaskReeditHisTaskInst(paramBean);

            HTTaskBean htTaskBean = htTaskDao.excSearchTaskDetailInfoByTaskId(paramVO.getTaskId());

            List<HTTaskPeriodVO> resultList = new ArrayList<>();

            // 格式转换
            for (HTTaskPeriodBean retBean : htTaskPeriodBeanList) {

                HTTaskPeriodVO retVO = new HTTaskPeriodVO();

                // 重新编辑申请id
                retVO.setTaskReeditId(StringToolUtils
                        .convertNullObjectToString(retBean.getTaskReeditId()));

                // 重新编辑申请记录的审核状态
                retVO.setVerifyState(StringToolUtils
                        .convertNullObjectToString(retBean.getVerifyState()));

                // 每一套重新编辑申请集合
                List<HTTaskPeriodBean> retFirstReeditList = retBean.getHTTaskPeriodBeanList();

                List<HTTaskPeriodVO> firstReeditList = new ArrayList<>();

                // 格式转换
                for (HTTaskPeriodBean htTaskPeriodBean : retFirstReeditList) {

                    HTTaskPeriodVO htTaskPeriodVO = new HTTaskPeriodVO();

                    // 任务Key
                    htTaskPeriodVO.setTaskKey(htTaskPeriodBean.getTaskKey());

                    // 唯一标识
                    htTaskPeriodVO.setHistoryId(htTaskPeriodBean.getHistoryId());

                    // 重新编辑申请ID
                    htTaskPeriodVO.setTaskReeditId(htTaskPeriodBean.getTaskReeditId());

                    // 审批人id
                    htTaskPeriodVO.setApproveUserId(htTaskPeriodBean.getApproveUserId());

                    // 审批人名称
                    htTaskPeriodVO.setApproveUserName(htTaskPeriodBean.getApproveUserName());

                    // 获取任务阶段list
                    List<HTTaskPeriodBean> childHisTaskInstBeanList = htTaskPeriodBean.getHTTaskPeriodBeanList();

                    List<HTTaskPeriodVO> childHisTaskInstVOList = new ArrayList<>();

                    // 循环遍历
                    for (HTTaskPeriodBean childHisTaskInstBean : childHisTaskInstBeanList) {

                        HTTaskPeriodVO childHisTaskInstVO = new HTTaskPeriodVO();

                        // 任务Key
                        childHisTaskInstVO.setTaskKey(childHisTaskInstBean.getTaskKey());

                        // 唯一标识
                        childHisTaskInstVO.setHistoryId(childHisTaskInstBean.getHistoryId());

                        // 重新编辑申请ID
                        childHisTaskInstVO.setTaskReeditId(childHisTaskInstBean.getTaskReeditId());

                        // 拒绝原因
                        childHisTaskInstVO.setRejectReason(childHisTaskInstBean.getRejectReason());

                        // 审批人id
                        childHisTaskInstVO.setApproveUserId(childHisTaskInstBean.getApproveUserId());

                        // 审批人名称
                        childHisTaskInstVO.setApproveUserName(childHisTaskInstBean.getApproveUserName());

                        // 审批时间
                        childHisTaskInstVO.setApproveTime(DateToolUtils.convertDateFormat(
                                childHisTaskInstBean.getApproveTime(), DateToolUtils.yyyy_MM_dd));

                        // 是否通过
                        childHisTaskInstVO.setIsPass(childHisTaskInstBean.getIsPass());

                        // 把对象添加到集合中
                        childHisTaskInstVOList.add(childHisTaskInstVO);
                    }

                    // 设置问题原因集合
                    htTaskPeriodVO.setHtTaskPeriodVOList(childHisTaskInstVOList);

                    // 把对象添加到集合中
                    firstReeditList.add(htTaskPeriodVO);
                }

                // 设置重新编辑申请集合
                retVO.setHtTaskPeriodVOList(firstReeditList);

                // 设置重新编辑申请对象
                resultList.add(retVO);

            }

            resultMap.put("isSubmitReedit",htTaskBean.getIsSubmitReedit());

            resultMap.put("dataList",resultList);

            return resultMap;

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }

    }

    /**
     * 签发(成果文件阶段 重新编辑申请)
     */
    @Override
    @Transactional
    public void excReeditApplicationIssue(HTTaskReeditVO paramVO) throws LogicException,SystemException {

        try {

            Date currentDate = getCurrentDate();

            // 查询任务id
            TbTaskPeriodRelationEntity tbTaskPeriodRelationEntity =
                    tbTaskPeriodRelationEntityMapper.selectByPrimaryKey(paramVO.getRelationId());

            TbTaskEntity tbTaskEntity = new TbTaskEntity();

            // 设置任务id
            tbTaskEntity.setTaskId(tbTaskPeriodRelationEntity.getTaskId());

            // 设置审核状态
            tbTaskEntity.setVerifyState("2");

            // 通过任务id查询重新编辑申请详情
            HTTaskReeditBean htTaskReeditBean =
                    htTaskDao.excSearchTaskReeditDetailInfoByRelationId(tbTaskEntity);

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskReeditBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            //对比更新时间,如果时间不同，说明此条数据已经被审核，则提示该申请已经被签发，请重新获取数据！
            if (!paramVO.getUpdateDate().equals(DateToolUtils.convertDateFormat(
                    htTaskReeditBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO))) {
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0008);
            }

            // 拒绝原因
            htTaskReeditBean.setRejectReason(paramVO.getRejectReason());

            // 格式转换 Bean->VO
            HTTaskReeditVO htTaskReeditVO = (HTTaskReeditVO) ObjectReflectUtils.convertObj(htTaskReeditBean, HTTaskReeditVO.class);

            Map<String, Object> variables = new HashMap<>();
            //登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            //登录人信息
            htTaskReeditVO.setLoginUserInfo(userInfo);
            //审批人ID
            htTaskReeditVO.setApproveUserId(userInfo.getLoginUserId());
            //审批人名称
            htTaskReeditVO.setApproveUserName(userInfo.getUserName());
            //启动工作流
            variables.put(WorkFlowConstant.KEY_TASK_REEDIT_VO, htTaskReeditVO);
            // 是否通过
            String isPass = paramVO.getIsPass();
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, isPass);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            if (Constant.NOT_APPROVED.equals(isPass)) {
                variables.put(WorkFlowConstant.KEY_REJECT_REASON, htTaskReeditVO.getRejectReason());
                commonActivitiListenVO.setDepartmentId(htTaskReeditVO.getDepartmentId());
            }
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            taskReeditProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);

            if (Constant.APPROVED.equals(isPass)) {
                //重新预启动成果文件流程
                String taskId = tbTaskPeriodRelationEntity.getTaskId();
                TbTaskEntity taskEntity = tbTaskEntityMapper.selectByPrimaryKey(taskId);
                // 查询部门id
                String tempDepartmentIdStr = htTaskDao.excSearchDepartmentIdByContractId(taskEntity);
                //启动工作流
                HTTaskVO taskVO = (HTTaskVO) ObjectReflectUtils.convertObj(taskEntity, HTTaskVO.class);
                taskVO.setDepartmentId(tempDepartmentIdStr);
                Map<String, Object> var = new HashMap<>();
                var.put(WorkFlowConstant.KEY_TASK_VO, taskVO);
                CommonActivitiListenVO listenVO = new CommonActivitiListenVO();
                listenVO.setDepartmentId(taskVO.getDepartmentId());
                var.put(WorkFlowConstant.KEY_LISTENER_VO, listenVO);
                var.put(WorkFlowConstant.KEY_DELEGATE_USER, taskEntity.getApplyUserId());
                var.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
                var.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
                ProcessInstance processInstance = taskProcessService.excStart(var);
                String taskProcessInstId = processInstance.getProcessInstanceId();
                //更新A3流程数据
                TbTaskPeriodRelationEntity taskPeriodRelationEntity = new TbTaskPeriodRelationEntity();
                taskPeriodRelationEntity.setTaskProcessInstId(taskProcessInstId);
                TbTaskPeriodRelationEntityExample example = new TbTaskPeriodRelationEntityExample();
                example.createCriteria().andTaskIdEqualTo(taskId);
                tbTaskPeriodRelationEntityMapper.updateByExampleSelective(taskPeriodRelationEntity, example);
                //更新历史审批记录
                TbTaskPeriodHisTaskInst taskPeriodHisTaskInst = new TbTaskPeriodHisTaskInst();
                taskPeriodHisTaskInst.setIsDelFlg(Constant.IS_DEL_FLG_DISABLE);
                TbTaskPeriodHisTaskInstExample taskPeriodHisExample = new TbTaskPeriodHisTaskInstExample();
                TbTaskPeriodHisTaskInstExample.Criteria criteria = taskPeriodHisExample.createCriteria();
                criteria.andTaskIdEqualTo(taskId);
                criteria.andIsResultFileEqualTo("1");
                taskPeriodHisDao.updateByExampleSelective(taskPeriodHisTaskInst, taskPeriodHisExample);
                TbTaskReeditHisTaskInst taskReeditHisTaskInst = new TbTaskReeditHisTaskInst();
                taskReeditHisTaskInst.setIsDelFlg(Constant.IS_DEL_FLG_DISABLE);
                TbTaskReeditHisTaskInstExample taskReeditHisExample = new TbTaskReeditHisTaskInstExample();
                taskReeditHisExample.createCriteria().andTaskIdEqualTo(taskId);
                taskReeditHisDao.updateByExampleSelective(taskReeditHisTaskInst, taskReeditHisExample);

                // 删除归档文件
                htTaskDao.excDeleteSaveMaterialRelations(tbTaskEntity);
            }

            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(htTaskReeditBean.getApplyTime(),DateToolUtils.yyyy_MM_dd);

            // 当前审核节点
            String delegateTaskKey = htTaskReeditBean.getDelegateTaskKey();

            // 职位名称
            String jobName = "";

            switch (delegateTaskKey) {
                // 部门经理
                case "ManagerVerify":
                    jobName = TaskReeditEnum.ManagerVerify.getJobName();
                    break;

                // 质控部组员
                case "QualityVerify":
                    jobName = TaskReeditEnum.QualityVerify.getJobName();
                    break;

                // 质控部副总经理
                case "GenerManagerVerify":
                    jobName = TaskReeditEnum.GenerManagerVerify.getJobName();
                    break;

                // 签发人
                case "Signer":
                    jobName = TaskReeditEnum.Signer.getJobName();
                    break;

                default:
                    break;
            }

            String tempMessageContentStr = "您在 " + applyTime + " 发起的 "
                    + htTaskReeditBean.getProjectNum() + "项目"
                    + htTaskReeditBean.getProjectNum() + "-" + htTaskReeditBean.getContractNum() + "合同"
                    + htTaskReeditBean.getTaskName() + "任务的成果文件 重新编辑 申请 ，被 "
                    + jobName + "(" + userInfo.getUserName() + ")审核并通过";

            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(htTaskReeditBean.getApplyUserId());
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
                    ,htTaskReeditBean.getOpenId(),htTaskReeditBean.getApplyUserName(),tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

            TbTaskEntity tbTaskEntity2 = new TbTaskEntity();
            // 设置任务id
            tbTaskEntity2.setTaskId(htTaskReeditBean.getTaskId());
            // 设置修改时间
            tbTaskEntity2.setUpdateDate(currentDate);
            // 设置修改人id
            tbTaskEntity2.setUpdateUserId(userInfo.getLoginUserId());
            // 修改数据
            tbTaskEntityMapper.updateByPrimaryKeySelective(tbTaskEntity2);

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 审核任务(成果文件阶段 重新编辑申请)
     */
    @Override
    @Transactional
    public void excAuditingOutcomeDocumentPeriodReeditApplication(HTTaskReeditVO paramVO) throws LogicException,SystemException {

        try {

//            // =====================================判断驳回原因和选择错误是否为空 start=========================================
//
//            // 如果是驳回，并且驳回原因或者选择错误不能为空
//            if (Constant.NOT_APPROVED.equals(paramVO.getIsPass()) && StringToolUtils.isNull(paramVO.getRejectReason())) {
//                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0014);
//            }
//
//            // =====================================判断驳回原因和选择错误是否为空 start=========================================

            Date currentDate = getCurrentDate();

            // 查询任务id
            TbTaskPeriodRelationEntity tbTaskPeriodRelationEntity =
                    tbTaskPeriodRelationEntityMapper.selectByPrimaryKey(paramVO.getRelationId());

            TbTaskEntity tbTaskEntity = new TbTaskEntity();

            // 设置审核状态
            tbTaskEntity.setVerifyState("2");

            // 设置任务id
            tbTaskEntity.setTaskId(StringToolUtils
                    .convertNullObjectToString(tbTaskPeriodRelationEntity.getTaskId()));

            // 通过任务id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByTaskId(tbTaskPeriodRelationEntity.getTaskId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            //对比更新时间,如果时间不同，则提示该申请已经被审核，请重新获取数据！
            if (!paramVO.getUpdateDate().equals(DateToolUtils.convertDateFormat(
                    htTaskBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO))) {
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0007);
            }

            // 通过任务id查询重新编辑申请详情
            HTTaskReeditBean htTaskReeditBean =
                    htTaskDao.excSearchTaskReeditDetailInfoByRelationId(tbTaskEntity);

            // 拒绝原因
            htTaskReeditBean.setRejectReason(paramVO.getRejectReason());

            // 跳转节点
            htTaskReeditBean.setSubmitNode(paramVO.getSubmitNode());

            // 格式转换 Bean->VO
            HTTaskReeditVO htTaskReeditVO = (HTTaskReeditVO) ObjectReflectUtils.convertObj(htTaskReeditBean, HTTaskReeditVO.class);

            Map<String, Object> variables = new HashMap<>();
            //登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            //登录人信息
            htTaskReeditVO.setLoginUserInfo(userInfo);
            //审批人ID
            htTaskReeditVO.setApproveUserId(userInfo.getLoginUserId());
            //审批人名称
            htTaskReeditVO.setApproveUserName(userInfo.getUserName());

            //启动工作流
            variables.put(WorkFlowConstant.KEY_TASK_REEDIT_VO, htTaskReeditVO);
            // 是否通过
            String isPass = paramVO.getIsPass();
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, isPass);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            if (Constant.NOT_APPROVED.equals(isPass)) {
                variables.put(WorkFlowConstant.KEY_REJECT_REASON, htTaskReeditVO.getRejectReason());
                commonActivitiListenVO.setDepartmentId(htTaskReeditVO.getDepartmentId());
            } else {
                String departmentId = StringUtils.isBlank(TaskReeditEnum.getDeptId(htTaskReeditVO.getSubmitNode())) ? htTaskReeditVO.getDepartmentId() : TaskReeditEnum.getDeptId(htTaskReeditVO.getSubmitNode());
                commonActivitiListenVO.setDepartmentId(departmentId);
            }
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            taskReeditProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);

            TbTaskEntity tbTaskEntity2 = new TbTaskEntity();
            // 设置任务id
            tbTaskEntity2.setTaskId(htTaskReeditBean.getTaskId());
            // 设置修改时间
            tbTaskEntity2.setUpdateDate(currentDate);
            // 设置修改人id
            tbTaskEntity2.setUpdateUserId(userInfo.getLoginUserId());
            // 修改数据
            tbTaskEntityMapper.updateByPrimaryKeySelective(tbTaskEntity2);

            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(htTaskReeditBean.getApplyTime(),DateToolUtils.yyyy_MM_dd);

            // 当前审核节点
            String delegateTaskKey = htTaskReeditBean.getDelegateTaskKey();

            // 职位名称
            String jobName = "";

            switch (delegateTaskKey) {
                // 部门经理
                case "ManagerVerify":
                    jobName = TaskReeditEnum.ManagerVerify.getJobName();
                    break;

                // 质控部组员
                case "QualityVerify":
                    jobName = TaskReeditEnum.QualityVerify.getJobName();
                    break;

                // 质控部副总经理
                case "GenerManagerVerify":
                    jobName = TaskReeditEnum.GenerManagerVerify.getJobName();
                    break;

                // 签发人
                case "Signer":
                    jobName = TaskReeditEnum.Signer.getJobName();
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

            String tempMessageContentStr = "您在 " + applyTime + " 发起的 "
                    + htTaskReeditBean.getProjectNum() + "项目"
                    + htTaskReeditBean.getProjectNum() + "-" + htTaskReeditBean.getContractNum() + "合同"
                    + htTaskReeditBean.getTaskName() + "任务的成果文件 重新编辑 申请 ，被 "
                    + jobName + "(" + userInfo.getUserName() + ")审核并"+tempAuditResultStr;

            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(htTaskReeditBean.getApplyUserId());
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
                    ,htTaskReeditBean.getOpenId(),htTaskReeditBean.getApplyUserName(),tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 审核任务(成果文件阶段 归档文件)
     */
    @Override
    @Transactional
    public void excAuditingTaskOutcomeDocumentPeriod(HTTaskVO paramVO) throws LogicException,SystemException {

        try {

            // =====================================判断选择错误是否为空 start=========================================

            // 如果是驳回，并且选择错误不能为空
            if (!"7".equals(paramVO.getCurrentState()) &&
                Constant.NOT_APPROVED.equals(paramVO.getIsPass()) && ListToolUtils.isEmpty(paramVO.getUserQuestionRecordRelationList())) {
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0013);
            }

            // =====================================判断选择错误是否为空 start=========================================

            // 通过任务阶段id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            //对比更新时间,如果时间不同，则提示该申请已经被审核，请重新获取数据！
            if (!paramVO.getUpdateDate().equals(DateToolUtils.convertDateFormat(
                    htTaskBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO))) {
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0007);
            }

            htTaskBean.setRelationId(paramVO.getRelationId());

            // 是否通过
            htTaskBean.setIsPass(StringToolUtils
                    .convertNullObjectToString(paramVO.getIsPass()));
            // 意见
            htTaskBean.setRejectReason(StringToolUtils
                    .convertNullObjectToString(paramVO.getRejectReason()));

            // 下一节点
            htTaskBean.setSubmitNode(StringToolUtils
                    .convertNullObjectToString(paramVO.getSubmitNode()));

            HTTaskVO htTaskVO = (HTTaskVO) ObjectReflectUtils.convertObj(htTaskBean, HTTaskVO.class);
            Map<String, Object> variables = new HashMap<>();
            // 登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            // 登录人信息
            htTaskVO.setLoginUserInfo(userInfo);

            // 审批人ID
            htTaskVO.setApproveUserId(userInfo.getLoginUserId());

            // 审批人名称
            htTaskVO.setApproveUserName(userInfo.getUserName());
            //启动工作流
            variables.put(WorkFlowConstant.KEY_TASK_VO, htTaskVO);
            // 是否通过
            String isPass = htTaskVO.getIsPass();
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, isPass);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            if (Constant.NOT_APPROVED.equals(isPass)) {
                variables.put(WorkFlowConstant.KEY_REJECT_REASON, htTaskVO.getRejectReason());
                variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, htTaskVO.getDelegateTaskKey());
                commonActivitiListenVO.setDepartmentId(htTaskVO.getDepartmentId());
            } else {
                String departmentId = StringUtils.isBlank(TaskEnum.getDeptId(htTaskVO.getSubmitNode())) ? htTaskVO.getDepartmentId() : TaskEnum.getDeptId(htTaskVO.getSubmitNode());
                if(TaskEnum.ArchiveVerify.getNodeKey().equals(htTaskVO.getDelegateTaskKey())){
                    departmentId = TaskEnum.Archive.getDepartmentId();
                }
                commonActivitiListenVO.setDepartmentId(departmentId);
                variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, htTaskVO.getSubmitNode());
            }
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            String historyRecordId = taskProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);

            // ======================================= 工作流相关 end =========================================

            // 获取当前时间，为了保证创建时间和修改时间都一样
            Date currentDate = getCurrentDate();

            // 如果有错误问题，则记录
            if (!ListToolUtils.isEmpty(paramVO.getUserQuestionRecordRelationList())) {

                //批量插入审核记录和人员和问题类型关联
                List<HTUserQuestionRecordRelationVO> relationVOList = paramVO.getUserQuestionRecordRelationList();

                List<HTUserQuestionRecordRelationBean> relationBeanList = new ArrayList<>();

                // 循环遍历问题list
                for (HTUserQuestionRecordRelationVO htUserQuestionRecordRelationVO : relationVOList) {

                    HTUserQuestionRecordRelationBean htUserQuestionRecordRelationBean = new HTUserQuestionRecordRelationBean();
                    //唯一标识
                    htUserQuestionRecordRelationBean.setRelationId(UUID.randomUUID().toString());
                    //历史id
                    htUserQuestionRecordRelationBean.setRecordId(historyRecordId);
                    //人员ID
                    htUserQuestionRecordRelationBean.setUserId(htUserQuestionRecordRelationVO.getUserId());
                    //问题ID
                    htUserQuestionRecordRelationBean.setQuestionId(htUserQuestionRecordRelationVO.getQuestionId());
                    //问题个数
                    htUserQuestionRecordRelationBean.setQuestionNum(htUserQuestionRecordRelationVO.getQuestionNum());
                    //来源
                    htUserQuestionRecordRelationBean.setSourceType(Constant.TASK_SOURCE_TYPE);
                    //业务id
                    htUserQuestionRecordRelationBean.setBusinessId(htTaskBean.getTaskId());
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

            // 业务ID(任务阶段ID)
            paramVO.setBusinessId(htTaskBean.getTaskId());

            // 创建时间
            paramVO.setCreateDate(DateToolUtils.convertDateFormat(currentDate,DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            // 创建人
            paramVO.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 修改时间
            paramVO.setUpdateDate(DateToolUtils.convertDateFormat(currentDate,DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            // 修改人
            paramVO.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 如果是驳回
            if (Constant.NOT_APPROVED.equals(isPass) && !StringToolUtils.isNull(paramVO.getOnlyOfficeEditKey())) {

                String uuid = UUID.randomUUID().toString();

                // 文件上传后的路径
                String filePath = "";

                // 文件名
                String realFileName = paramVO.getReviewFileName();

                // 获取文件的后缀名
                String suffixName = realFileName.substring(realFileName.lastIndexOf("."));

                // 获取文件路径
                String fileName = uuid + suffixName;

                // 文件上传后的路径
                filePath = Constant.FILE_SEPARATE + "review" + Constant.FILE_SEPARATE + "outcomeDocument" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;

                //文件地址
                paramVO.setReviewFilePath(filePath);

                // 获取文件的真实名称
                paramVO.setReviewFileName(paramVO.getReviewFileName());

                //通知OnlyOffice documentServer 保存文件
                OnlyOfficeUtils.notifyOnlyOfficeToSave(paramVO);

            }

            TbTaskEntity tbTaskEntity = new TbTaskEntity();
            // 设置任务id
            tbTaskEntity.setTaskId(htTaskBean.getTaskId());
            // 设置修改时间
            tbTaskEntity.setUpdateDate(currentDate);
            // 设置修改人id
            tbTaskEntity.setUpdateUserId(userInfo.getLoginUserId());
            // 修改数据
            tbTaskEntityMapper.updateByPrimaryKeySelective(tbTaskEntity);

            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(htTaskBean.getApplyTime(),DateToolUtils.yyyy_MM_dd);

            // 当前审核节点
            String delegateTaskKey = htTaskBean.getDelegateTaskKey();

            // 职位名称
            String jobName = "";

            // 归档
            String archive = "";

            switch (delegateTaskKey) {
                // 部门经理
                case "ManagerVerify":
                    jobName = TaskEnum.ManagerVerify.getJobName();
                    break;

                // 质控部组员
                case "QualityVerify":
                    jobName = TaskEnum.QualityVerify.getJobName();
                    break;

                // 质控部副总经理
                case "GenerManagerVerify":
                    jobName = TaskEnum.GenerManagerVerify.getJobName();
                    break;

                // 签发人
                case "Signer":
                    jobName = TaskEnum.Signer.getJobName();
                    break;

                // 归档审批
                case "ArchiveVerify":
                    jobName = TaskEnum.ArchiveVerify.getJobName();

                    archive = "归档";
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

            String tempMessageContentStr = "您在 " + applyTime + " 发起的 "
                    + htTaskBean.getProjectNum() + "项目"
                    + htTaskBean.getProjectNum() + "-" + htTaskBean.getContractNum() + "合同"
                    + htTaskBean.getTaskName() + "任务的成果文件 " + archive + " 申请 ，被 "
                    + jobName + "(" + userInfo.getUserName() + ")审核并"+tempAuditResultStr;

            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(htTaskBean.getApplyUserId());
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
                    ,htTaskBean.getOpenId(),htTaskBean.getApplyUserName(),tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 撤回任务(成果文件阶段)
     */
    @Override
    @Transactional
    public void excRecallTaskOutcomeDocumentPeriod(HTTaskVO paramVO) throws LogicException, SystemException {

        try {

            // 通过任务阶段id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            //是否可以撤回（0：否 1：是）,如果不能撤回，则提示信息
            if (htTaskBean.getIsCanRecall().equals(Constant.IS_CAN_RECALL_DISABLE)) {
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0001);
            }

            // 格式转换 Bean->VO
            HTTaskVO htTaskVO = (HTTaskVO) ObjectReflectUtils.convertObj(htTaskBean, HTTaskVO.class);

            // 设置审批状态为未通过
            htTaskVO.setIsPass(Constant.NOT_APPROVED);

            // 获取当前登录用户信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();

            // 设置当前登录用户信息
            htTaskVO.setLoginUserInfo(userInfo);

            // 设置审批人id
            htTaskVO.setApproveUserId(userInfo.getLoginUserId());

            // 设置审批人
            htTaskVO.setApproveUserName(userInfo.getUserName());

            //启动工作流
            taskProcessService.excRecallTask(htTaskVO);

            // 添加修改日期和修改人
            TbTaskEntity tbTaskEntity = new TbTaskEntity();
            tbTaskEntity.setTaskId(htTaskBean.getTaskId());
            tbTaskEntity.setUpdateDate(getCurrentDate());
            tbTaskEntity.setUpdateUserId(userInfo.getLoginUserId());
            tbTaskEntityMapper.updateByPrimaryKeySelective(tbTaskEntity);

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_TASK_0004);
        }
    }

    /**
     * 提交审核(成果文件阶段)
     */
    @Override
    @Transactional
    public void excSubmitAuditOutcomeDocumentPeriod(HTTaskVO paramVO, MultipartFile signPathFile,MultipartFile[] fileList) throws LogicException, SystemException {

        try {

            // 通过任务阶段id查询任务详情
            HTTaskBean htTaskBean = htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());

            // =====================================判断项目是否结束 start=========================================
            commonService.excIsOverProject(htTaskBean.getProjectId());// 判断项目是否结束
            // =====================================判断项目是否结束 end=========================================

            Date tempCurrentDate = getCurrentDate();// 临时变量：当前时间
            String tempLoginUserIdStr = paramVO.getLoginUserInfo().getLoginUserId();// 临时变量：当前登录用户id

            // ============================ 该任务下的A3阶段的审核流程是否都已完成 start ==================================

            // 查询该阶段所属的任务id
            TbTaskPeriodRelationEntity tbTaskPeriodRelationEntity =
                    tbTaskPeriodRelationEntityMapper.selectByPrimaryKey(paramVO.getRelationId());

            // 查询该任务下的所有A3阶段信息
            List<TbTaskPeriodRelationEntity> tbTaskPeriodRelationEntityList =
                    htTaskDao.excSearchAllA3PeriodInfoByTaskId(tbTaskPeriodRelationEntity);

            // 如果该任务下的所有A3阶段信息不为空，则遍历信息，
            // 判断verify_state字段是否都为1，如果有不为1的信息，则抛出异常
            if (!ListToolUtils.isEmpty(tbTaskPeriodRelationEntityList)) {
                for (TbTaskPeriodRelationEntity retEntity : tbTaskPeriodRelationEntityList) {// 遍历信息
                    if (!StringToolUtils.STRING_ONE.equals(retEntity.getVerifyState())) {// 判断verify_state字段是否都为1，如果有不为1的信息，则抛出异常
                        throw new LogicException(MessageConstant.MSG_ERROR_TASK_0002);
                    }
                }
            }

            // ============================ 该任务下的A3阶段的审核流程是否都已完成 end ====================================

            // ============================ 成果文件信息 start ====================================

            String tempTaskIdStr = paramVO.getTaskId();// 任务id

            TbTaskEntity updateTbTaskEntity = new TbTaskEntity();
            updateTbTaskEntity.setTaskId(tempTaskIdStr);// 设置任务id

            String signPathFilePath = null;// 文件上传后的路径
            String tempFileNameStr = null;// 自定义上传文件的文件名称（id+.后缀名）

            String tempTaskTypeStr = paramVO.getTaskType();// 临时变量：成果文件类型

            // 当成果文件类型为自定义成果文件时，并且附件不为空，则上传附件，附件名称和附件路径
            if(StringToolUtils.STRING_ZERO.equals(tempTaskTypeStr) && null != signPathFile){

                String fileNameStr = signPathFile.getOriginalFilename();// 源文件名

                // 文件上传后的路径
                signPathFilePath = Constant.FILE_SEPARATE + "task" + Constant.FILE_SEPARATE+ tempTaskIdStr + Constant.FILE_SEPARATE;

                // 自定义上传文件的文件名称（uuid+.后缀名）
                tempFileNameStr = UUID.randomUUID().toString() + fileNameStr.substring(fileNameStr.lastIndexOf("."));

                // 设置附件路径
                updateTbTaskEntity.setTaskMaterialPath(signPathFilePath + tempFileNameStr);

                // 设置附件名称
                updateTbTaskEntity.setTaskMaterialName(StringToolUtils.convertNullObjectToString(fileNameStr));

                // 如果为模板时，设置模板类型 和json 串
            }else if(StringToolUtils.STRING_ONE.equals(tempTaskTypeStr)){
                updateTbTaskEntity.setFormworkType(StringToolUtils.convertNullObjectToString(paramVO.getFormworkType()));// 设置模板合同类型
                updateTbTaskEntity.setFormworkContent(paramVO.getFormworkContent());// 表单中的内容（json串）
            }

            updateTbTaskEntity.setReportNum(paramVO.getReportNum());// 咨询报告编号
            updateTbTaskEntity.setReportCopies(paramVO.getReportCopies());// 咨询报告份数
            updateTbTaskEntity.setUpdateDate(tempCurrentDate);// 修改时间
            updateTbTaskEntity.setUpdateUserId(tempLoginUserIdStr);// 修改人id
            updateTbTaskEntity.setTaskType(tempTaskTypeStr);// 任务类型

            // ============================ 成果文件信息 end ====================================

            // ======================================= 工作流 start =========================================

            htTaskBean.setSubmitNode(paramVO.getSubmitNode());// 跳转节点
            HTTaskVO htTaskVO = (HTTaskVO) ObjectReflectUtils.convertObj(htTaskBean, HTTaskVO.class);// 格式转换 Bean->VO
            htTaskVO.setLoginUserInfo(paramVO.getLoginUserInfo());// 设置当前登录人信息

            //启动工作流
            Map<String, Object> variable = new HashMap<>();
            variable.put(WorkFlowConstant.KEY_TASK_VO, htTaskVO);
            variable.put(WorkFlowConstant.KEY_SUBMIT_NODE, htTaskVO.getSubmitNode());
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            String departmentId = StringUtils.isBlank(TaskEnum.getDeptId(htTaskVO.getSubmitNode())) ? htTaskVO.getDepartmentId() : TaskEnum.getDeptId(htTaskVO.getSubmitNode());
            commonActivitiListenVO.setDepartmentId(departmentId);
            variable.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
            variable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            taskProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variable);

            // ======================================= 工作流 end =========================================

            //保存标识
            updateTbTaskEntity.setIsDraftFlg(Constant.IS_NOT_DRAFT);
            //保存人Id
            updateTbTaskEntity.setAddDraftUserId(tempLoginUserIdStr);
            tbTaskEntityMapper.updateByPrimaryKeySelective(updateTbTaskEntity);

            // ==========================================修改任务文件审核状态 start======================================

            HTTaskPeriodBean htTaskPeriodBean = new HTTaskPeriodBean();

            // 设置任务阶段id
            htTaskPeriodBean.setRelationId(StringToolUtils.convertNullObjectToString(paramVO.getRelationId()));

            // 根据任务阶段id修改非自己任务阶段文件的审核状态
            htTaskDao.excUpdateIsReviewByRelationId(htTaskPeriodBean);

            if (!ListToolUtils.isEmpty(paramVO.getMaterialIds())) {// 如果关联文件id不为空，则修改其审核状态
                htTaskDao.excUpdateIsReviewType(paramVO.getMaterialIds());// 修改其审核状态
            }

            if(StringToolUtils.STRING_ZERO.equals(tempTaskTypeStr) && null != signPathFile){// 如果附件不为空，则上传附件，附件名称和附件路径
                boolean isUploadSignSuccess = FileUtils.uploadFile(signPathFilePath + tempFileNameStr, signPathFile);// 上传文件（true：成功 false：失败）
                if (!isUploadSignSuccess) {
                    throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0001);// MSG_ERROR_PUBLIC_NOTICE_0001 上传文件失败！
                }
            }

            // ==========================================修改任务文件审核状态 end======================================

            if(fileList.length>NumberToolUtils.DEFAULT_INT_ZERO){// 当上传了相关资料（非弹窗中显示的），则设置信息
                for(int i=0;i<fileList.length;i++){// 遍历相关资料（非弹窗中显示的），上传文件
                    String originalFilename = fileList[i].getOriginalFilename();// 源文件名
                    String filePath = Constant.FILE_SEPARATE + "task"+ Constant.FILE_SEPARATE + tempTaskIdStr + Constant.FILE_SEPARATE;// 文件上传后的路径
                    String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));// 自定义上传文件的文件名称（uuid+.后缀名）
                    filePath = filePath + fileName;// 文件路径 + 文件名

                    TbTaskPeriodMaterialRelationEntity tbTaskPeriodMaterialRelationEntity = new TbTaskPeriodMaterialRelationEntity();
                    tbTaskPeriodMaterialRelationEntity.setTaskPeriodId(paramVO.getRelationId());// 任务阶段id
                    tbTaskPeriodMaterialRelationEntity.setMaterialPath(filePath);// 文件路径
                    tbTaskPeriodMaterialRelationEntity.setMaterialName(originalFilename);// 设置附件名称
                    tbTaskPeriodMaterialRelationEntity.setIsReview(StringToolUtils.STRING_ONE);// 是否提审（0：否 1：是）
                    tbTaskPeriodMaterialRelationEntity.setIsResultFile(StringToolUtils.STRING_ONE);// 是否是成果文件（0：否，1：是）
                    tbTaskPeriodMaterialRelationEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);// 是否删除（0：否 1：是）
                    tbTaskPeriodMaterialRelationEntity.setCreateDate(tempCurrentDate);// 创建时间
                    tbTaskPeriodMaterialRelationEntity.setCreateUserId(tempLoginUserIdStr);// 创建人id
                    tbTaskPeriodMaterialRelationEntity.setUpdateDate(tempCurrentDate);// 修改时间（和添加时间相同）
                    tbTaskPeriodMaterialRelationEntity.setUpdateUserId(tempLoginUserIdStr);//修改人id(和添加人id相同)
                    tbTaskPeriodMaterialRelationEntityMapper.insertSelective(tbTaskPeriodMaterialRelationEntity);// 添加信息

                    boolean isUploadSignSuccess = FileUtils.uploadFile(filePath, fileList[i]); // 上传文件（true：成功 false：失败）
                    if (!isUploadSignSuccess) {// 如果失败，则抛出异常
                        throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0001);// MSG_ERROR_PUBLIC_NOTICE_0001 上传文件失败！
                    }
                }
            }

            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(tempCurrentDate,DateToolUtils.yyyy_MM_dd);

            HTContractBean htContractBean = new HTContractBean();
            // 设置当前登录用户id
            htContractBean.setCurrentUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置部门id
            htContractBean.setDepartmentId(htTaskBean.getDepartmentId());
            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            // 职位和查询得到的人员id
            HTTaskBean returnBean = commonService.excSearchUserIdForMessage(paramVO.getSubmitNode(),htTaskBean.getDepartmentId());

            // 消息内容
            String tempMessageContentStr = "由"+htContractBeanList.get(0).getJobName()+"("+paramVO.getLoginUserInfo().getUserName()+")"+applyTime+"发起的"
                    +htTaskBean.getProjectNum()+"项目"
                    +htTaskBean.getProjectNum()+"-"+htTaskBean.getContractNum()+"合同"
                    +htTaskBean.getTaskName()+"任务"+paramVO.getPeriodName()+"阶段该由您审核了";

            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置是否阅读 0：否    1：是
            tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);
            // 设置创建时间
            tbMessageEntity.setCreateDate(tempCurrentDate);
            // 设置创建人id
            tbMessageEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置修改时间
            tbMessageEntity.setUpdateDate(tempCurrentDate);
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

//            // ==========================================删除文件 start======================================
//
//            HTReviewFileRelationBean htReviewFileRelationBean = new HTReviewFileRelationBean();
//
//            // 事务唯一标识
//            htReviewFileRelationBean.setBusinessId(tempTaskIdStr);
//
//            // 审核意见文件列表
//            List<HTReviewFileRelationBean> htReviewFileRelationBeanList = htReviewFileRelationDao
//                    .excSearchReviewFileRelationInfoByBusinessId(htReviewFileRelationBean);
//
//            // 遍历删除审核意见文件(物理删除)
//            for (HTReviewFileRelationBean resultBean:htReviewFileRelationBeanList){
//                //删除审核意见文件(物理删除)
//                FileUtils.deleteFile(resultBean.getReviewFilePath());
//            }
//
//            //删除审核意见文件(删除数据)
//            htReviewFileRelationDao.excDeleteReviewFileRelationInfoByBusinessId(htReviewFileRelationBean);
//
//            // ==========================================删除文件 end======================================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }


    /**
     * 保存成果文件阶段信息
     */
    @Override
    @Transactional
    public void excSaveDraftAuditOutcomeDocumentPeriod(HTTaskVO paramVO, MultipartFile signPathFile,MultipartFile[] fileList) throws LogicException, SystemException {

        try {

            // 通过任务阶段id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();
            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = paramVO.getLoginUserInfo().getLoginUserId();

            // ============================ 该任务下的A3阶段的审核流程是否都已完成 start ==================================

            // 查询该阶段所属的任务id
            TbTaskPeriodRelationEntity tbTaskPeriodRelationEntity =
                    tbTaskPeriodRelationEntityMapper.selectByPrimaryKey(paramVO.getRelationId());

            // 查询该任务下的所有A3阶段信息
            List<TbTaskPeriodRelationEntity> tbTaskPeriodRelationEntityList =
                    htTaskDao.excSearchAllA3PeriodInfoByTaskId(tbTaskPeriodRelationEntity);


            // ============================ 该任务下的A3阶段的审核流程是否都已完成 end ====================================

            // ============================ 成果文件信息 start ====================================
            // 任务id
            String tempTaskIdStr = paramVO.getTaskId();

            TbTaskEntity updateTbTaskEntity = new TbTaskEntity();

            // 设置任务id
            updateTbTaskEntity.setTaskId(tempTaskIdStr);

            // 文件上传后的路径
            String signPathFilePath = null;

            // 自定义上传文件的文件名称（id+.后缀名）
            String tempFileNameStr = null;

            // 临时变量：成果文件类型
            String tempTaskTypeStr = paramVO.getTaskType();

            // 当成果文件类型为自定义成果文件时，并且附件不为空，则上传附件，附件名称和附件路径
            if(StringToolUtils.STRING_ZERO.equals(tempTaskTypeStr) && null != signPathFile){

                // 源文件名
                String fileNameStr = signPathFile.getOriginalFilename();

                // 文件上传后的路径
                signPathFilePath = Constant.FILE_SEPARATE + "task"
                        + Constant.FILE_SEPARATE+ tempTaskIdStr + Constant.FILE_SEPARATE;

                // 自定义上传文件的文件名称（任务id+.后缀名）
                tempFileNameStr = tempTaskIdStr + fileNameStr
                        .substring(fileNameStr.lastIndexOf("."));

                // 设置附件路径
                updateTbTaskEntity.setTaskMaterialPath(signPathFilePath + tempFileNameStr);

                // 设置附件名称
                updateTbTaskEntity.setTaskMaterialName(StringToolUtils
                        .convertNullObjectToString(fileNameStr));
                boolean isUploadSignSuccess = FileUtils.uploadFile(signPathFilePath + tempFileNameStr, signPathFile);
                // 如果为模板时，设置模板类型 和json 串
            }else if(StringToolUtils.STRING_ONE.equals(tempTaskTypeStr)){
                // 设置模板合同类型
                updateTbTaskEntity.setFormworkType(StringToolUtils
                        .convertNullObjectToString(paramVO.getFormworkType()));

                updateTbTaskEntity.setFormworkContent(StringToolUtils.convertNullObjectToString(paramVO.getFormworkContent()));

            }
            // 咨询报告编号
            updateTbTaskEntity.setReportNum(StringToolUtils.convertNullObjectToString(paramVO.getReportNum()));
            // 修改时间
            updateTbTaskEntity.setUpdateDate(tempCurrentDate);
            // 修改人id
            updateTbTaskEntity.setUpdateUserId(tempLoginUserIdStr);
            //保存标识
            updateTbTaskEntity.setIsDraftFlg(Constant.IS_DRAFT);
            //保存人Id
            updateTbTaskEntity.setAddDraftUserId(tempLoginUserIdStr);
            // 任务类型
            updateTbTaskEntity.setTaskType(StringToolUtils.convertNullObjectToString(tempTaskTypeStr));

            // ============================ 成果文件信息 end ====================================
            if(fileList.length>NumberToolUtils.DEFAULT_INT_ZERO){// 当上传了相关资料（非弹窗中显示的），则设置信息
                for(int i=0;i<fileList.length;i++){// 遍历相关资料（非弹窗中显示的），上传文件
                    String originalFilename = fileList[i].getOriginalFilename();// 源文件名
                    String filePath = Constant.FILE_SEPARATE + "task"+ Constant.FILE_SEPARATE + tempTaskIdStr + Constant.FILE_SEPARATE;// 文件上传后的路径
                    String fileName = tempTaskIdStr + "_" + i + originalFilename.substring(originalFilename.lastIndexOf("."));// 自定义上传文件的文件名称（任务id_1+.后缀名）
                    filePath = filePath + fileName;// 文件路径 + 文件名

                    TbTaskPeriodMaterialRelationEntity tbTaskPeriodMaterialRelationEntity = new TbTaskPeriodMaterialRelationEntity();
                    tbTaskPeriodMaterialRelationEntity.setTaskPeriodId(paramVO.getRelationId());// 任务阶段id
                    tbTaskPeriodMaterialRelationEntity.setMaterialPath(filePath);// 文件路径
                    tbTaskPeriodMaterialRelationEntity.setMaterialName(originalFilename);// 设置附件名称
                    tbTaskPeriodMaterialRelationEntity.setIsReview(StringToolUtils.STRING_ONE);
                    tbTaskPeriodMaterialRelationEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);// 是否删除（0：否 1：是）
                    tbTaskPeriodMaterialRelationEntity.setCreateDate(tempCurrentDate);// 创建时间
                    tbTaskPeriodMaterialRelationEntity.setCreateUserId(tempLoginUserIdStr);// 创建人id
                    tbTaskPeriodMaterialRelationEntity.setUpdateDate(tempCurrentDate);// 修改时间（和添加时间相同）
                    tbTaskPeriodMaterialRelationEntity.setUpdateUserId(tempLoginUserIdStr);//修改人id(和添加人id相同)
                    tbTaskPeriodMaterialRelationEntityMapper.insertSelective(tbTaskPeriodMaterialRelationEntity);// 添加信息

                    boolean isUploadSignSuccess = FileUtils.uploadFile(filePath, fileList[i]); // 上传文件（true：成功 false：失败）
                    if (!isUploadSignSuccess) {// 如果失败，则抛出异常
                        throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0001);// MSG_ERROR_PUBLIC_NOTICE_0001 上传文件失败！
                    }
                }
            }
            tbTaskEntityMapper.updateByPrimaryKeySelective(updateTbTaskEntity);


        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }




    /**
     * 转换数据格式为json
     *
     * @param paramVO 任务id
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public String mapConvertToJson(HTTaskVO paramVO) throws LogicException, SystemException{

        Map<String, Object> retMap = new HashedMap();

        try {

            // ============================ 成果文件信息 start ====================================
            // 任务id
            String tempTaskIdStr = paramVO.getTaskId();

            // 临时变量：成果文件类型
            String tempTaskTypeStr = paramVO.getTaskType();

            // ============================ 成果文件信息 end ====================================

            // 如果是自定义的时候，则抛出异常
            if(!StringToolUtils.STRING_ONE.equals(tempTaskTypeStr)){
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0006);
            }

            // 根据任务id查询任务信息
            TbTaskEntity retTaskEntity = tbTaskEntityMapper.selectByPrimaryKey(tempTaskIdStr);

            // 如果查到任务模板信息则将信息传到map里
            if (retTaskEntity != null){

                // 任务成果文件模板json串
                String tempFormworkJsonStr = paramVO.getFormworkContent();

                // 如果json 串不为空则转成map格式
                if (!StringToolUtils.isNull(tempFormworkJsonStr)){

                    // json转成map
                    retMap = InterfaceUtils.jsonToObject(tempFormworkJsonStr,Map.class);
                }

                // 根据合同id  查询合同信息
                TbContractEntity retContractEntity = tbContractEntityMapper.selectByPrimaryKey(retTaskEntity.getContractId());

                // 项目编号
                String tempProjectNumStr = StringToolUtils.STRING_EMPTY;

                // 合同编号
                String tempContractNumStr = StringToolUtils.STRING_EMPTY;

                // 如果查到合同信息 则 设置合同编号
                if (retContractEntity != null){
                    // 设置合同编号
                    tempContractNumStr = StringToolUtils.convertNullObjectToString(retContractEntity.getContractNum());
                    // 根据项目id  查询项目信息
                    TbProjectEntity retProjectEntity = tbProjectEntityMapper.selectByPrimaryKey(retContractEntity.getProjectId());

                    // 如果查到项目信息 则 设置项目编号
                    if (retProjectEntity != null){
                        tempProjectNumStr = retProjectEntity.getProjectNum();
                    }

                }

                retMap.put("contractNum",tempProjectNumStr + "-" + tempContractNumStr);

                // 模板类型
                String tempFormworkTypeStr = paramVO.getFormworkType();

                // 如果不选择模板类型，则抛出异常
                if(StringToolUtils.STRING_EMPTY.equals(tempFormworkTypeStr)){
                    throw new LogicException(MessageConstant.MSG_ERROR_TASK_0017);
                }

                retMap.put("taskFormworkType",tempFormworkTypeStr);

                Integer tempFormworkTypeInt  = NumberToolUtils.convertNullToInteger(tempFormworkTypeStr);

                // 模板名称
                String tempTaskTemplateNameStr = BusinessUtils.convertTaskTemplateTypeToStateStr(tempFormworkTypeInt);
                //0：编制概算 1：编制估算 2：编制结算 3：编制决算
                //4：编制清标报告 5：招标控制价模版 6：招标清单模版（分包、承包） 7：招标控制价模版（总包）
                //8：预算编制模板 9：概算审核 10：估算审核 11：结算审核
                //12：预算审核 13：合同审核意见（未招标） 14：合同审核意见（招标） 15：进度款审核意见
                //16：变更审核意见 17：预算审核意见
                //18：招标代理 19：评标书面报告

                // 如果成果文件模板为 编制概算
                if ("0".equals(tempFormworkTypeStr)){

                    // 编制依据
                    String tempInput9Str = "1." + retMap.get("input9");

                    retMap.put("input9",tempInput9Str);

                    // 建筑工程人工费
                    String tempInput14Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input14"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input14",tempInput14Str);

                    // 装饰工程人工费
                    String tempInput15Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input15"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input15",tempInput15Str);


                    // 安装工程人工费
                    String tempInput16Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input16"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input16",tempInput16Str);


                    // 编制估算
                }else if ("1".equals(tempFormworkTypeStr)){



                    // 编制结算
                }else if ("2".equals(tempFormworkTypeStr)){

                    // 结算类型数字
                    Integer tempStatementTypeInt = NumberToolUtils
                            .convertNullToInteger(retMap.get("input11"));

                    // 转成对应文字
                    String tempStatementType = BusinessUtils
                            .convertSatementTypeToStateStr(tempStatementTypeInt);

                    retMap.put("input11",tempStatementType);

                    // 编制决算
                }else if ("3".equals(tempFormworkTypeStr)){

                    // 编制清标报告
                }else if ("4".equals(tempFormworkTypeStr)){


                    // 控制价
                    String tempInput13Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input13"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input13",tempInput13Str);


                    // 中标价低于控制价
                    String tempInput14Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input14"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input14",tempInput14Str);

                    // 招标控制价模版
                }else if ("5".equals(tempFormworkTypeStr)){
                    // 编制依据
                    String tempInput9Str = "1." + retMap.get("input9");

                    retMap.put("input9",tempInput9Str);

                    // 建筑工程人工费
                    String tempInput12Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input12",tempInput12Str);

                    // 装饰工程人工费
                    String tempInput13Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input13"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input13",tempInput13Str);


                    // 安装工程人工费
                    String tempInput14Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input14"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input14",tempInput14Str);


                    // 清单项目计价说明
                    String tempInput17Str = "4." + retMap.get("input17");

                    retMap.put("input17",tempInput17Str);


                    // 招标清单模版（分包、承包）
                }else if ("6".equals(tempFormworkTypeStr)){
                    // 编制依据
                    String tempInput9Str = "1." + retMap.get("input9");

                    retMap.put("input9",tempInput9Str);


                    // 清单项目计价说明
                    String tempInput13Str = "4." + retMap.get("input13");

                    retMap.put("input13",tempInput13Str);
                    // 招标控制价模版（总包）
                }else if ("7".equals(tempFormworkTypeStr)){
                    // 编制依据
                    String tempInput9Str = "1." + retMap.get("input9");

                    retMap.put("input9",tempInput9Str);

                    // 清单项目计价说明
                    String tempInput12Str = "4." + retMap.get("input12");

                    retMap.put("input12",tempInput12Str);
                    // 预算编制模板
                }else if ("8".equals(tempFormworkTypeStr)){
                    // 编制依据
                    String tempInput9Str = "1." + retMap.get("input9");

                    retMap.put("input9",tempInput9Str);

                    // 清单项目计价说明
                    String tempInput19Str = "5." + retMap.get("input19");

                    retMap.put("input19",tempInput19Str);

                    // 建筑工程人工费
                    String tempInput13Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input13"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input13",tempInput13Str);


                    // 装饰工程人工费
                    String tempInput14Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input14"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input14",tempInput14Str);

                    // 安装工程人工费
                    String tempInput15Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input15"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input15",tempInput15Str);


                    // 概算审核
                }else if ("9".equals(tempFormworkTypeStr)){

                    // 审核依据 第一条
                    String tempInput17Str = "1." + retMap.get("input17")+ ";";

                    retMap.put("input17",tempInput17Str);

                    // 审核依据 第四条
                    String tempInput19Str = "4." + retMap.get("input19")+ ";";

                    retMap.put("input19",tempInput19Str);

                    // 送审价格
                    String tempInput10Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input10"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input10",tempInput10Str);


                    // 审定价格
                    String tempInput11Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input11"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input11",tempInput11Str);

                    // 审减价格
                    String tempInput12Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input12",tempInput12Str);


                    // ------工程费用
                    // 送审价格
                    String tempInput20Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input20"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input20",tempInput20Str);


                    // 审定价格
                    String tempInput21Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input21"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input21",tempInput21Str);

                    // 审减价格
                    String tempInput22Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input22"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input22",tempInput22Str);

                    // ------其他费用

                    // 送审价格
                    String tempInput24Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input24"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input24",tempInput24Str);


                    // 审定价格
                    String tempInput25Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input25"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input25",tempInput25Str);

                    // 审减价格
                    String tempInput26Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input26"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input26",tempInput26Str);

                    // ------工程预备费

                    // 送审价格
                    String tempInput28Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input28"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input28",tempInput28Str);


                    // 审定价格
                    String tempInput29Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input29"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input29",tempInput29Str);

                    // 审减价格
                    String tempInput30Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input30"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input30",tempInput30Str);

                    // 估算审核
                }else if ("10".equals(tempFormworkTypeStr)){

                    // 审核依据 第四条
                    String tempInput18Str = "4." + retMap.get("input18")+ ";";

                    retMap.put("input18",tempInput18Str);


                    // 投资估算工程造价
                    String tempInput10Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input10")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input10",tempInput10Str);


                    // 审定工程造价
                    String tempInput11Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input11")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input11",tempInput11Str);

                    // 审减金额
                    String tempInput12Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input12")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input12",tempInput12Str);


                    // 工程费用：送审
                    String tempInput21Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input21")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input21",tempInput21Str);


                    // 工程费用：审定
                    String tempInput22Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input22")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input22",tempInput22Str);

                    // 工程费用：审减
                    String tempInput23Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input23")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input23",tempInput23Str);


                    // 其他费用：送审
                    String tempInput25Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input25"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input25",tempInput25Str);


                    // 其他费用：审定
                    String tempInput26Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input26"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input26",tempInput26Str);

                    // 其他费用：审减
                    String tempInput27Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input27"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input27",tempInput27Str);


                    // 工程预备费：送审
                    String tempInput29Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input29")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input29",tempInput29Str);


                    // 工程预备费：审定
                    String tempInput30Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input30")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input30",tempInput30Str);

                    // 工程预备费：审减
                    String tempInput31Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input31")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input31",tempInput31Str);




                    // 结算审核
                }else if ("11".equals(tempFormworkTypeStr)){

                    // 结算类型数字
                    Integer tempStatementTypeInt = NumberToolUtils
                            .convertNullToInteger(retMap.get("input19"));

                    // 转成对应文字
                    String tempStatementType = BusinessUtils
                            .convertSatementTypeToStateStr(tempStatementTypeInt);

                    retMap.put("input19",tempStatementType);

                    // 审核依据 第五条
                    String tempInput25Str = "5." + retMap.get("input25")+ ";";

                    retMap.put("input25",tempInput25Str);


                    // 审核依据 第八条
                    String tempInput26Str = "8." + retMap.get("input26")+ ";";

                    retMap.put("input26",tempInput26Str);

                    // 审核依据 第十二条
                    String tempInput27Str = "12." + retMap.get("input27")+ ";";

                    retMap.put("input27",tempInput27Str);
                    // 投资估算工程造价
                    String tempInput10Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input10")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input10",tempInput10Str);


                    // 审定工程造价
                    String tempInput11Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input11")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input11",tempInput11Str);

                    // 审减金额
                    String tempInput12Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(NumberToolUtils.convertNullToBigDecimal(retMap.get("input12")).multiply(BigDecimal.valueOf(10000)),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input12",tempInput12Str);


                    // ------工程预备费

                    // 送审价格
                    String tempInput28Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input28"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input28",tempInput28Str);


                    // 审定价格
                    String tempInput29Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input29"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input29",tempInput29Str);

                    // 审减价格
                    String tempInput30Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input30"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input30",tempInput30Str);



                    // 申报结算
                    String tempInput44Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input44"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input44",tempInput44Str);


                    // 审定价格
                    String tempInput45Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input45"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input45",tempInput45Str);

                    // 调减金额
                    String tempInput46Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input46"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input46",tempInput46Str);

                    // 审定金额 （大写）
                    String tempInput47Str = NumberToCN
                            .number2CNMontrayUnit(NumberToolUtils.convertNullToBigDecimal(retMap.get("input47"))) ;

                    retMap.put("input47",tempInput47Str);


                    // 预算审核
                }else if ("12".equals(tempFormworkTypeStr)){

                    // 送审价格
                    String tempInput10Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input10"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input10",tempInput10Str);


                    // 审定价格
                    String tempInput11Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input11"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input11",tempInput11Str);

                    // 审减价格
                    String tempInput12Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input12",tempInput12Str);

                    // ------工程费用

                    //  送审价格
                    String tempInput19Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input19"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input19",tempInput19Str);

                    // 审定价格
                    String tempInput20Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input20"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input20",tempInput20Str);


                    //  审减价格
                    String tempInput21Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input21"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input21",tempInput21Str);

                    // 合同审核意见（未招标）
                }else if ("13".equals(tempFormworkTypeStr)){

                    //  合同金额
                    String tempInput8Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input8"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input8",tempInput8Str);



                    // 合同审核意见（招标）
                }else if ("14".equals(tempFormworkTypeStr)){

                    //  合同金额
                    String tempInput7Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input7"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input7",tempInput7Str);

                    // 进度款审核意见
                }else if ("15".equals(tempFormworkTypeStr)){

                    // 送审价格
                    String tempInput10Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input10"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input10",tempInput10Str);


                    // 审定价格
                    String tempInput11Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input11"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input11",tempInput11Str);

                    // 审减价格
                    String tempInput12Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input12",tempInput12Str);

                    // 累计已支付金额
                    String tempInput13Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input13"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input13",tempInput13Str);

                    // 是否有附件 0：没有，1：有
                    String tempInput14Str = StringToolUtils.convertNullObjectToString(retMap.get("input14"));

                    String tempInput15Str = StringToolUtils.STRING_EMPTY;

                    // 如果有附件则 拼接上附件信息
                    if ("1".equals(tempInput14Str)){

                        tempInput15Str = "附件：" + StringToolUtils.convertNullObjectToString(retMap.get("input15"));

                    }
                    retMap.put("input15",tempInput15Str);

                    // 是否抄送 0：没有，1：有
                    String tempInput16Str = StringToolUtils.convertNullObjectToString(retMap.get("input16"));

                    // 抄送公司
                    String tempInput17Str = StringToolUtils.STRING_EMPTY;
                    // 抄送人
                    String tempInput18Str = StringToolUtils.STRING_EMPTY;

                    // 如果有附件则 拼接上附件信息
                    if ("1".equals(tempInput16Str)){

                        tempInput17Str = "抄送：" + StringToolUtils.convertNullObjectToString(retMap.get("input17"));

                        tempInput18Str = StringToolUtils.convertNullObjectToString(retMap.get("input18"));

                    }
                    retMap.put("input17",tempInput17Str);

                    retMap.put("input18",tempInput18Str);

                    // 变更审核意见
                }else if ("16".equals(tempFormworkTypeStr)){

                    // 送审价格
                    String tempInput10Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input10"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input10",tempInput10Str);


                    // 审定价格
                    String tempInput11Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input11"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input11",tempInput11Str);

                    // 审减价格
                    String tempInput12Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input12",tempInput12Str);


                    // 是否有附件 0：没有，1：有
                    String tempInput14Str = StringToolUtils.convertNullObjectToString(retMap.get("input14"));

                    String tempInput15Str = StringToolUtils.STRING_EMPTY;

                    // 如果有附件则 拼接上附件信息
                    if ("1".equals(tempInput14Str)){

                        tempInput15Str = "附件：" + StringToolUtils.convertNullObjectToString(retMap.get("input15"));

                    }
                    retMap.put("input15",tempInput15Str);

                    // 是否抄送 0：没有，1：有
                    String tempInput16Str = StringToolUtils.convertNullObjectToString(retMap.get("input16"));

                    // 抄送公司
                    String tempInput17Str = StringToolUtils.STRING_EMPTY;
                    // 抄送人
                    String tempInput18Str = StringToolUtils.STRING_EMPTY;

                    // 如果有附件则 拼接上附件信息
                    if ("1".equals(tempInput16Str)){

                        tempInput17Str = "抄送：" + StringToolUtils.convertNullObjectToString(retMap.get("input17"));

                        tempInput18Str = StringToolUtils.convertNullObjectToString(retMap.get("input18"));

                    }
                    retMap.put("input17",tempInput17Str);

                    retMap.put("input18",tempInput18Str);

                    // 招标代理、评标书面报告
                }else if ("18".equals(tempFormworkTypeStr) || "19".equals(tempFormworkTypeStr)){

                    // 日期
                    String tempDateStr = DateToolUtils.convertDateFormat(DateToolUtils.convertDateFormat(
                            retMap.get("date").toString(),DateToolUtils.yyyy_MM_dd),DateToolUtils.YYYY_MM_DD_CHINESE);

                    retMap.put("date",tempDateStr);

                }else {

                    // 送审价格
                    String tempInput10Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input10"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input10",tempInput10Str);


                    // 审定价格
                    String tempInput11Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input11"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input11",tempInput11Str);

                    // 审减价格
                    String tempInput12Str = NumberToolUtils
                            .convertNumberToFormatStringWithHalfUp(retMap.get("input12"),NumberToolUtils.INT_SCALE_FLOAT) ;

                    retMap.put("input12",tempInput12Str);

                    // 是否有附件 0：没有，1：有
                    String tempInput14Str = StringToolUtils.convertNullObjectToString(retMap.get("input14"));

                    String tempInput15Str = StringToolUtils.STRING_EMPTY;

                    // 如果有附件则 拼接上附件信息
                    if ("1".equals(tempInput14Str)){

                        tempInput15Str = "附件：" + StringToolUtils.convertNullObjectToString(retMap.get("input15"));

                    }
                    retMap.put("input15",tempInput15Str);

                    // 是否抄送 0：没有，1：有
                    String tempInput16Str = StringToolUtils.convertNullObjectToString(retMap.get("input16"));

                    // 抄送公司
                    String tempInput17Str = StringToolUtils.STRING_EMPTY;
                    // 抄送人
                    String tempInput18Str = StringToolUtils.STRING_EMPTY;

                    // 如果有附件则 拼接上附件信息
                    if ("1".equals(tempInput16Str)){

                        tempInput17Str = "抄送：" + StringToolUtils.convertNullObjectToString(retMap.get("input17"));

                        tempInput18Str = StringToolUtils.convertNullObjectToString(retMap.get("input18"));

                    }
                    retMap.put("input17",tempInput17Str);

                    retMap.put("input18",tempInput18Str);

                }

                retMap.put("taskTemplateName",tempTaskTemplateNameStr);

            }

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }

        return JSON.toJSONString(retMap);
    }

    /**
     * 查询当前任务下的所有的相关资料文件列表
     *
     * @return
     */
    @Override
    @Transactional
    public List<HTTaskPeriodMaterialVO> excSearchIsReviewTaskPeriodMaterialList(HTTaskPeriodMaterialVO paramVO) throws SystemException {
        try {

            HTTaskPeriodMaterialBean paramBean = new HTTaskPeriodMaterialBean();

            // 任务id
            paramBean.setTaskId(StringToolUtils.convertNullObjectToString(paramVO.getTaskId()));

            // 查询相关资料文件列表，当传入任务id的时候查询的是当前任务下的所有的相关资料，当传入任务阶段id的时候查询的是当前阶段的相关资料
            List<HTTaskPeriodMaterialBean> htTaskPeriodMaterialBeanList = htTaskDao.excSearchIsReviewTaskPeriodMaterialList(paramBean);

            List<HTTaskPeriodMaterialVO> htTaskPeriodMaterialVOList = new ArrayList<>();

            TbTaskEntity tbTaskEntity = tbTaskEntityMapper.selectByPrimaryKey(paramVO.getTaskId());

            if (Constant.IS_DRAFT.equals(tbTaskEntity.getIsDraftFlg())
                    &&!paramVO.getLoginUserInfo().getLoginUserId().equals(tbTaskEntity.getAddDraftUserId())){
                return htTaskPeriodMaterialVOList;
            }

            for (HTTaskPeriodMaterialBean htTaskPeriodMaterialBean : htTaskPeriodMaterialBeanList) {

                HTTaskPeriodMaterialVO HTTaskPeriodMaterialVO = new HTTaskPeriodMaterialVO();

                // 唯一标识
                HTTaskPeriodMaterialVO.setRelationId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getRelationId()));

                // 任务阶段关联ID
                HTTaskPeriodMaterialVO.setTaskPeriodId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getTaskPeriodId()));

                // 文件类型ID
                HTTaskPeriodMaterialVO.setFileType(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getFileTypeId()));

                // 文件名称
                HTTaskPeriodMaterialVO.setMaterialName(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getMaterialName()));

                // 文件地址
                HTTaskPeriodMaterialVO.setMaterialPath(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getMaterialPath()));

                // 是否提审（0：否 1：是）
                HTTaskPeriodMaterialVO.setIsReview(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getIsReview()));

                // 上传人名称
                HTTaskPeriodMaterialVO.setUserName(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getUserName()));

                htTaskPeriodMaterialVOList.add(HTTaskPeriodMaterialVO);
            }

            return htTaskPeriodMaterialVOList;

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 查询相关资料文件列表(详情)
     *
     * @return
     */
    @Override
    @Transactional
    public List<HTTaskPeriodMaterialVO> excSearchRelationMaterial(HTTaskPeriodMaterialVO paramVO) throws SystemException {
        try {

            HTTaskPeriodMaterialBean paramBean = new HTTaskPeriodMaterialBean();

            paramBean.setTaskPeriodId(StringToolUtils.convertNullObjectToString(paramVO.getRelationId())); // 任务阶段id

            // 查询相关资料文件列表，当传入任务id的时候查询的是当前任务下的所有的相关资料，当传入任务阶段id的时候查询的是当前阶段的相关资料
            List<HTTaskPeriodMaterialBean> htTaskPeriodMaterialBeanList = htTaskDao.excSearchIsReviewTaskPeriodMaterialList(paramBean);

            List<HTTaskPeriodMaterialVO> htTaskPeriodMaterialVOList = new ArrayList<>();

            TbTaskEntity tbTaskEntity = tbTaskEntityMapper.selectByPrimaryKey(paramVO.getTaskId());

            if (Constant.IS_DRAFT.equals(tbTaskEntity.getIsDraftFlg())
                    &&!paramVO.getLoginUserInfo().getLoginUserId().equals(tbTaskEntity.getAddDraftUserId())){
                return htTaskPeriodMaterialVOList;
            }

            for (HTTaskPeriodMaterialBean htTaskPeriodMaterialBean : htTaskPeriodMaterialBeanList) {

                HTTaskPeriodMaterialVO HTTaskPeriodMaterialVO = new HTTaskPeriodMaterialVO();

                // 唯一标识
                HTTaskPeriodMaterialVO.setRelationId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getRelationId()));

                // 任务阶段关联ID
                HTTaskPeriodMaterialVO.setTaskPeriodId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getTaskPeriodId()));

                // 文件类型ID
                HTTaskPeriodMaterialVO.setFileType(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getFileTypeId()));

                // 文件名称
                HTTaskPeriodMaterialVO.setMaterialName(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getMaterialName()));

                // 文件地址
                HTTaskPeriodMaterialVO.setMaterialPath(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getMaterialPath()));

                // 是否提审（0：否 1：是）
                HTTaskPeriodMaterialVO.setIsReview(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getIsReview()));

                // 上传人名称
                HTTaskPeriodMaterialVO.setUserName(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getUserName()));

                htTaskPeriodMaterialVOList.add(HTTaskPeriodMaterialVO);
            }

            return htTaskPeriodMaterialVOList;

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 根据阶段ID查询归档文件列表
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTTaskSaveMaterialVO> searchTaskSaveMaterialList(
            HTTaskSaveMaterialVO paramVO) throws SystemException {
        try {

            TbTaskSaveMaterialRelationEntity tbTaskSaveMaterialEntity = new TbTaskSaveMaterialRelationEntity();

            // 根据任务阶段id查询任务id
            TbTaskPeriodRelationEntity retEntity =
                    tbTaskPeriodRelationEntityMapper.selectByPrimaryKey(paramVO.getRelationId());

            // 如果查询到entity，则设置任务id
            if (null != retEntity)
                // 任务ID
                tbTaskSaveMaterialEntity.setTaskId(StringToolUtils
                        .convertNullObjectToString(retEntity.getTaskId()));

            // 根据阶段ID查询归档文件列表
            List<HTTaskSaveMaterialBean> htTaskSaveMaterialBeanList =
                    htTaskDao.excSearchTaskSaveMaterialListByProperty(tbTaskSaveMaterialEntity);

            List<HTTaskSaveMaterialVO> htTaskSaveMaterialVOList = new ArrayList<>();

            for (HTTaskSaveMaterialBean htTaskSaveMaterialBean : htTaskSaveMaterialBeanList) {

                HTTaskSaveMaterialVO htTaskSaveMaterialVO = new HTTaskSaveMaterialVO();

                // 唯一标识
                htTaskSaveMaterialVO.setRelationId(StringToolUtils
                        .convertNullObjectToString(htTaskSaveMaterialBean.getRelationId()));

                // 关联阶段ID
                htTaskSaveMaterialVO.setTaskId(StringToolUtils
                        .convertNullObjectToString(htTaskSaveMaterialBean.getTaskId()));

                // 文件类型ID
                htTaskSaveMaterialVO.setFileType(StringToolUtils
                        .convertNullObjectToString(htTaskSaveMaterialBean.getFileType()));

                // 文件名称
                htTaskSaveMaterialVO.setMaterialName(StringToolUtils
                        .convertNullObjectToString(htTaskSaveMaterialBean.getMaterialName()));

                // 文件地址
                htTaskSaveMaterialVO.setMaterialPath(StringToolUtils
                        .convertNullObjectToString(htTaskSaveMaterialBean.getMaterialPath()));

                // 用户名
                htTaskSaveMaterialVO.setUserName(StringToolUtils
                        .convertNullObjectToString(htTaskSaveMaterialBean.getUserName()));

                // 如果该文件是当前登录用户上传的，则返回1：可以删除，否则返回0：不可以删除
                if (paramVO.getLoginUserInfo().getLoginUserId().equals(htTaskSaveMaterialBean.getCreateUserId())) {
                    // 1：可以删除
                    htTaskSaveMaterialVO.setIsCanDel(Constant.IS_CAN_DEL_FLG_ABLE);
                } else {
                    // 0：不可以删除
                    htTaskSaveMaterialVO.setIsCanDel(Constant.IS_CAN_DEL_FLG_ENABLE);
                }

                htTaskSaveMaterialVOList.add(htTaskSaveMaterialVO);

            }

            return htTaskSaveMaterialVOList;

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 删除归档文件信息
     *
     * @return
     */
    @Override
    @Transactional
    public void excDeleteTaskSaveMaterialInfo(HTTaskSaveMaterialVO paramVO) throws SystemException {

        try {

            // 通过任务阶段id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByTaskId(paramVO.getTaskId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            String materialPath = paramVO.getMaterialPath();

            //删除文件信息
            tbTaskSaveMaterialRelationEntityMapper.deleteByPrimaryKey(paramVO.getRelationId());

            //删除真实文件
            FileUtils.deleteFile(materialPath);

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0009);
        }
    }

    /**
     * 确认归档
     */
    @Override
    @Transactional
    public void excConfirmArchive(HTTaskVO paramVO) throws LogicException,SystemException {

        try {

            Date currentDate = getCurrentDate();

            // 通过任务阶段id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            //对比更新时间,如果时间不同，则提示该申请已经被确认归档，请重新获取数据！
            if (!paramVO.getUpdateDate().equals(DateToolUtils.convertDateFormat(
                    htTaskBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO))) {
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0009);
            }

            HTTaskVO htTaskVO = (HTTaskVO) ObjectReflectUtils.convertObj(htTaskBean, HTTaskVO.class);

            //启动工作流
            Map<String, Object> variables = new HashMap<>();
            // 登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            // 登录人信息
            htTaskVO.setLoginUserInfo(userInfo);
            // 审批人ID
            htTaskVO.setApproveUserId(userInfo.getLoginUserId());
            // 审批人名称
            htTaskVO.setApproveUserName(userInfo.getUserName());
            variables.put(WorkFlowConstant.KEY_TASK_VO, htTaskVO);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            taskProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);

            // 登录人信息
            htTaskVO.setLoginUserInfo(paramVO.getLoginUserInfo());

            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(htTaskBean.getApplyTime(),DateToolUtils.yyyy_MM_dd);

            HTContractBean htContractBean = new HTContractBean();
            // 设置当前登录用户id
            htContractBean.setCurrentUserId(userInfo.getLoginUserId());
            // 设置部门id
            htContractBean.setDepartmentId(Constant.DEPT_ID_XZ);

            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            String tempMessageContentStr = "您在 "+applyTime+" 发起的 "
                    +htTaskBean.getProjectNum()+"项目"
                    +htTaskBean.getProjectNum()+"-"+htTaskBean.getContractNum()+"合同 "
                    +htTaskBean.getTaskName()+"任务的 成果文件 归档 申请 ，被 "
                    +htContractBeanList.get(0).getJobName()+"("+userInfo.getUserName()+")确认归档了";
            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(htTaskBean.getApplyUserId());
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
                    ,htTaskBean.getOpenId(),htTaskBean.getApplyUserName(),tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

            // ======================================= 修改档案编号 start =========================================

            TbTaskEntity tbTaskEntity = new TbTaskEntity();
            // 任务id
            tbTaskEntity.setTaskId(StringToolUtils
                    .convertNullObjectToString(htTaskBean.getTaskId()));
            // 档案编号
            tbTaskEntity.setFileNumber(StringToolUtils
                    .convertNullObjectToString(paramVO.getFileNumber()));
            // 设置修改时间
            tbTaskEntity.setUpdateDate(currentDate);
            // 设置修改人id
            tbTaskEntity.setUpdateUserId(userInfo.getLoginUserId());
            // 修改任务的档案编号和时间
            tbTaskEntityMapper.updateByPrimaryKeySelective(tbTaskEntity);

            // ======================================= 修改档案编号 end =========================================

        }  catch (LogicException lex) {
            throw lex;
        }  catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 提交归档
     */
    @Override
    @Transactional
    public void excSubmitArchive(HTTaskVO paramVO,MultipartFile[] resourceFiles) throws LogicException,SystemException {

        try {

            // 通过任务阶段id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            // =====================================判断是否上传归档文件 start=========================================

            TbTaskSaveMaterialRelationEntity tbTaskSaveMaterialEntity = new TbTaskSaveMaterialRelationEntity();

            // 任务ID
            tbTaskSaveMaterialEntity.setTaskId(StringToolUtils.convertNullObjectToString(paramVO.getTaskId()));

            // 根据阶段ID查询归档文件列表
            List<HTTaskSaveMaterialBean> htTaskSaveMaterialBeanList =
                    htTaskDao.excSearchTaskSaveMaterialListByProperty(tbTaskSaveMaterialEntity);

            // 如果没有上传过归档文件，则添加非空校验
            if(ListToolUtils.isEmpty(htTaskSaveMaterialBeanList)){
                // 如果没有上传归档文件，则提示请上传归档文件
                if (!(null != resourceFiles && resourceFiles.length > 0)) {
                    throw new LogicException(MessageConstant.MSG_ERROR_TASK_0015);
                }
            }

            // =====================================判断是否上传归档文件 end=========================================

            HTTaskVO htTaskVO = (HTTaskVO) ObjectReflectUtils.convertObj(htTaskBean, HTTaskVO.class);

            //启动工作流
            Map<String, Object> variables = new HashMap<>();
            // 登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            // 登录人信息
            htTaskVO.setLoginUserInfo(userInfo);
            // 审批人ID
            htTaskVO.setApproveUserId(userInfo.getLoginUserId());
            // 审批人名称
            htTaskVO.setApproveUserName(userInfo.getUserName());
            variables.put(WorkFlowConstant.KEY_TASK_VO, htTaskVO);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            String departmentId = StringUtils.isBlank(TaskEnum.getDeptId(htTaskVO.getSubmitNode())) ? htTaskVO.getDepartmentId() : TaskEnum.getDeptId(htTaskVO.getSubmitNode());
            commonActivitiListenVO.setDepartmentId(departmentId);
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            taskProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);

            // =========================================== 添加归档文件 start ========================================

            // 如果文件不为空,则添加归档文件
            if (null != resourceFiles && resourceFiles.length > 0) {

                for (MultipartFile file : resourceFiles) {

                    Date currentDate = getCurrentDate();

                    TbTaskSaveMaterialRelationEntity tbTaskSaveMaterialRelationEntity = new TbTaskSaveMaterialRelationEntity();

                    // 登录用户id
                    String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(
                            paramVO.getLoginUserInfo().getLoginUserId());

                    // uuid 用于拼接文件路径
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
                    filePath = Constant.FILE_SEPARATE + "archive" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;

                    // 文件名称
                    tbTaskSaveMaterialRelationEntity.setMaterialName(realFileName);

                    // 文件地址
                    tbTaskSaveMaterialRelationEntity.setMaterialPath(filePath);

                    // 设置主键id
                    tbTaskSaveMaterialRelationEntity.setRelationId(
                            StringToolUtils.convertNullObjectToString(uuid));

                    //任务ID
                    tbTaskSaveMaterialRelationEntity.setTaskId(
                            StringToolUtils.convertNullObjectToString(paramVO.getTaskId()));

                    //是否删除
                    tbTaskSaveMaterialRelationEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

                    //创建人
                    tbTaskSaveMaterialRelationEntity.setCreateUserId(tempLoginUserIdStr);

                    //创建时间
                    tbTaskSaveMaterialRelationEntity.setCreateDate(currentDate);

                    //修改人
                    tbTaskSaveMaterialRelationEntity.setUpdateUserId(tempLoginUserIdStr);

                    //修改时间
                    tbTaskSaveMaterialRelationEntity.setUpdateDate(currentDate);

                    //添加归档文件信息
                    htTaskDao.excInsertTaskSaveMaterialRelationInfo(tbTaskSaveMaterialRelationEntity);

                    // 上传文件
                    boolean isUploadSignSuccess = FileUtils.uploadFile(filePath, file);

                    if (!isUploadSignSuccess) {
                        // MSG_ERROR_PUBLIC_NOTICE_0001 上传文件失败！
                        throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0001);
                    }

                }

            }

            // =========================================== 添加归档文件 end ========================================

            // ================================================ 插入消息信息 start =====================================================

            Date currentDate = getCurrentDate();

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(htTaskBean.getApplyTime(),DateToolUtils.yyyy_MM_dd);

            HTContractBean htContractBean = new HTContractBean();
            // 设置当前登录用户id
            htContractBean.setCurrentUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置部门id
            htContractBean.setDepartmentId(htTaskBean.getDepartmentId());
            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            // 职位和查询得到的人员id
            HTTaskBean returnBean = commonService.excSearchUserIdForMessage("QualityVerify",htTaskBean.getDepartmentId());

            // 消息内容
            String tempMessageContentStr = "由"+htContractBeanList.get(0).getJobName()+"("+paramVO.getLoginUserInfo().getUserName()+")"+applyTime+"发起的"
                    +htTaskBean.getProjectNum()+"项目"
                    +htTaskBean.getProjectNum()+"-"+htTaskBean.getContractNum()+"合同"
                    +htTaskBean.getTaskName()+"任务提交归档该由您审核了";

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

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询任务阶段相关所有文件类型以级相关文件类型的文件
     *
     * @return 任务阶段相关所有文件类型以级相关文件类型的文件
     */
    @Override
    @Transactional(readOnly = true)
    public List<ElementTreeVO> excSearchAllMaterialTypeAndMaterialList(HTTaskPeriodVO paramVO) throws SystemException {

        List<ElementTreeVO> treeVOList = new ArrayList<>();

        try {

            // 查询所有的文件类型
            List<HTDictFileTypeBean> fileTypeBeanList = htTaskDao.searchMaterialTypeList();

            HTTaskPeriodMaterialBean paramBean = new HTTaskPeriodMaterialBean();

            // 任务阶段id
            paramBean.setTaskPeriodId(StringToolUtils
                    .convertNullObjectToString(paramVO.getRelationId()));

            // 根据任务阶段id查询非自己任务阶段的文件列表
            List<HTTaskPeriodMaterialBean> htTaskPeriodMaterialBeanList =
                    htTaskDao.excSearchTaskPeriodMaterialListByTaskPeriodId(paramBean);

            // 循环遍历文件列表
            for (HTDictFileTypeBean htDictFileTypeBean : fileTypeBeanList) {

                ElementTreeVO treeVO = new ElementTreeVO();

                // 一级菜单标识
                treeVO.setTreeNodeLevel(Constant.FIRST_LEVEL_MENU_FLAG);

                // 设置文件类型id
                treeVO.setId(StringToolUtils.convertNullObjectToString(
                        htDictFileTypeBean.getFileTypeId()));

                // 设置文件菜单名称
                treeVO.setLabel(StringToolUtils.convertNullObjectToString(
                        htDictFileTypeBean.getFileTypeName()));

                List<ElementTreeVO> childrenList = new ArrayList<>();

                // 循环遍历任务阶段文件信息列表
                for (HTTaskPeriodMaterialBean htTaskPeriodMaterialBean : htTaskPeriodMaterialBeanList) {

                    // 如果文件类型id和任务阶段类型所属的文件id相同
                    if (htTaskPeriodMaterialBean.getFileType().equals(treeVO.getId())) {

                        ElementTreeVO childTreeVO = new ElementTreeVO();

                        // 设置任务阶段文件id
                        childTreeVO.setId(StringToolUtils.convertNullObjectToString(
                                htTaskPeriodMaterialBean.getRelationId()));

                        // 设置任务阶段文件名称
                        childTreeVO.setLabel(StringToolUtils.convertNullObjectToString(
                                htTaskPeriodMaterialBean.getMaterialName()));

                        // 设置二级菜单标识
                        childTreeVO.setTreeNodeLevel(Constant.SECOND_LEVEL_MENU_FLAG);

                        childrenList.add(childTreeVO);
                    }

                }

                treeVO.setChildren(childrenList);

                treeVOList.add(treeVO);
            }

            ElementTreeVO elementTreeVO = new ElementTreeVO();
            // 一级菜单标识
            elementTreeVO.setTreeNodeLevel(Constant.FIRST_LEVEL_MENU_FLAG);
            // 设置文件类型id
//            elementTreeVO.setId(UUID.randomUUID().toString());
            // 设置文件菜单名称
            elementTreeVO.setLabel("其他");
            List<ElementTreeVO> elementTreeVOList = new ArrayList<>();

            // 循环遍历任务阶段文件信息列表
            for (HTTaskPeriodMaterialBean taskPeriodMaterialBean : htTaskPeriodMaterialBeanList) {

                // 如果文件类型id和任务阶段类型所属的文件id相同
                if (StringToolUtils.isNull(taskPeriodMaterialBean.getFileType())) {
                    ElementTreeVO treeVO = new ElementTreeVO();
                    treeVO.setId(StringToolUtils.convertNullObjectToString(taskPeriodMaterialBean.getRelationId()));// 设置任务阶段文件id
                    treeVO.setLabel(StringToolUtils.convertNullObjectToString(taskPeriodMaterialBean.getMaterialName()));// 设置任务阶段文件名称
                    treeVO.setTreeNodeLevel(Constant.SECOND_LEVEL_MENU_FLAG); // 设置二级菜单标识
                    elementTreeVOList.add(treeVO);
                }

            }

            elementTreeVO.setChildren(elementTreeVOList);

            treeVOList.add(elementTreeVO);

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }

        return treeVOList;
    }

    /**
     * 通过任务id查询归档任务详情
     *
     * @return 任务详情
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskArchiveVO excSearchArchiveTaskDetailInfoByTaskId(HTTaskArchiveVO paramVO) throws SystemException {

        HTTaskArchiveVO htTaskArchiveVO = new HTTaskArchiveVO();

        try {

            HTTaskArchiveBean htTaskArchiveBean = htTaskDao.excSearchTaskArchiveDetailInfoByTaskId(paramVO.getTaskId());

            // 如果有数据
            if (htTaskArchiveBean != null) {

                // 委托任务id
                htTaskArchiveVO.setDelegateTaskId(StringToolUtils
                        .convertNullObjectToString(htTaskArchiveBean.getDelegateTaskId()));

            }

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }

        return htTaskArchiveVO;
    }

    /**
     * 通过任务id查询任务详情
     *
     * @return 任务详情
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskVO excSearchTaskDetailInfoByTaskId(HTTaskPeriodVO paramVO) throws SystemException {

        HTTaskVO htTaskVO = new HTTaskVO();

        try {

            // 通过任务id查询任务详情
            HTTaskBean htTaskBean = htTaskDao
                    .excSearchTaskDetailInfoByTaskId(paramVO.getTaskId());
            if (htTaskBean != null) {
                // 格式转换 Bean->VO
                htTaskVO = (HTTaskVO) ObjectReflectUtils.convertObj(htTaskBean, HTTaskVO.class);
                // 审核状态（预防此字段为空时，前端收不到此字段报错）
                htTaskVO.setVerifyState(StringToolUtils.convertNullObjectToString(htTaskBean.getVerifyState()));
            }

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }

        return htTaskVO;
    }

    /**
     * 通过任务阶段id查询任务详情
     *
     * @return 任务详情
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskVO excSearchTaskDetailInfoByRelationId(HTTaskVO paramVO) throws SystemException {
        //TODO
        HTTaskVO htTaskVO = null;

        try {

            // 通过任务阶段id查询任务详情
            HTTaskBean htTaskBean = htTaskDao
                    .excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());

            // 如果是提交了重新编辑申请
            if (Constant.IS_SUBMIT_REEDIT.equals(htTaskBean.getIsSubmitReedit())) {

                TbTaskEntity tbTaskEntity = new TbTaskEntity();

                // 设置任务id
                tbTaskEntity.setTaskId(StringToolUtils.convertNullObjectToString(htTaskBean.getTaskId()));

                // 设置审核状态
                tbTaskEntity.setVerifyState("2");

                // 通过任务id查询重新编辑申请详情
                HTTaskReeditBean htTaskReeditBean =
                        htTaskDao.excSearchTaskReeditDetailInfoByRelationId(tbTaskEntity);

                htTaskReeditBean.setIsSubmitReedit(htTaskBean.getIsSubmitReedit());

                // 格式转换 Bean->VO
                htTaskVO = (HTTaskVO) ObjectReflectUtils.convertObj(htTaskReeditBean, HTTaskVO.class);

                // 如果是成果文件阶段
            } else {
                //判断是否是草稿信息
                //如果是草稿信息的话且当前登录人不是主提审人(草稿保存人)则不展示保存的信息
                if (Constant.IS_DRAFT.equals(htTaskBean.getIsDraftFlg())
                        &&!paramVO.getLoginUserInfo().getLoginUserId().equals(htTaskBean.getAddDraftUserId())){
                    //任务ID
                    htTaskVO.setTaskId(htTaskBean.getTaskId());
                    //任务名称
                    htTaskVO.setTaskName(htTaskBean.getTaskName());
                    //合同ID
                    htTaskVO.setContractId(htTaskBean.getContractId());
                    //合同编号
                    htTaskVO.setContractNum(htTaskBean.getContractNum());
                    //咨询类型ID
                    htTaskVO.setCounselTypeId(htTaskBean.getCounselTypeId());
                    //部门ID
                    htTaskVO.setDepartmentId(htTaskBean.getDepartmentId());
                    //项目编号
                    htTaskVO.setProjectNum(htTaskBean.getProjectNum());
                    //提审人ID
                    htTaskVO.setApplyUserId(htTaskBean.getApplyUserId());
                    //提审人名称
                    htTaskVO.setApplyUserName(htTaskBean.getApplyUserName());
                    //提交节点
                    htTaskVO.setSubmitNode(htTaskBean.getSubmitNode());
                    //流程实例ID
                    htTaskVO.setProcessInstId(htTaskBean.getProcessInstId());
                    //委托任务ID
                    htTaskVO.setDelegateTaskId(htTaskBean.getDelegateTaskId());
                    //委托任务Key
                    htTaskVO.setDelegateTaskKey(htTaskBean.getDelegateTaskKey());
                    //当前流程状态
                    htTaskVO.setCurrentState(htTaskBean.getCurrentState());
                    //是否可以撤回（0：否 1：是）
                    htTaskVO.setIsCanRecall(htTaskBean.getIsCanRecall());
                    //是否已经提交重新编辑申请（0：否 1：是）
                    htTaskVO.setIsSubmitReedit(htTaskBean.getIsSubmitReedit());
                    //是否已经通过重新编辑申请（0：否 1：是）
                    htTaskVO.setIsPassReedit(htTaskBean.getIsPassReedit());
                    //是否删除
                    htTaskVO.setIsDelFlg(htTaskBean.getIsDelFlg());
                    //修改时间
                    htTaskVO.setUpdateDate(DateToolUtils.convertDateFormat(htTaskBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                }else {
                    // 格式转换 Bean->VO
                    htTaskVO = (HTTaskVO) ObjectReflectUtils.convertObj(htTaskBean, HTTaskVO.class);
                }

            }

            HTTaskPeriodBean htTaskPeriodBean = new HTTaskPeriodBean();

            // 设置任务id
            htTaskPeriodBean.setTaskId(StringToolUtils.convertNullObjectToString(htTaskBean.getTaskId()));

            // 查询任务成员列表
            List<HTTaskBean> htTaskBeanList = htTaskDao.excSearchTaskUserListByTaskId(htTaskPeriodBean);

            List<HTTaskVO> userList = new ArrayList<>();

            for (HTTaskBean retBean : htTaskBeanList) {

                HTTaskVO retVO = new HTTaskVO();

                // 成员ID
                retVO.setUserId(StringToolUtils.convertNullObjectToString(retBean.getUserId()));

                // 成员名称
                retVO.setUserName(StringToolUtils.convertNullObjectToString(retBean.getUserName()));

                // 是否是主负责人
                retVO.setIsMainDuty(StringToolUtils.convertNullObjectToString(retBean.getIsMainDuty()));

                userList.add(retVO);
            }

            // 设置人员信息list
            htTaskVO.setUserList(userList);

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }

        return htTaskVO;
    }

    /**
     * 通过任务阶段id查询任务阶段详情
     *
     * @return 任务阶段详情
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskPeriodVO excSearchTaskPeriodDetailInfoByRelationId(HTTaskPeriodVO paramVO) throws SystemException {

        HTTaskPeriodVO htTaskPeriodVO = new HTTaskPeriodVO();

        try {
            // 通过任务阶段id查询任务阶段详情
            HTTaskPeriodBean htTaskPeriodBean = htTaskDao
                    .excSearchTaskPeriodDetailInfoByRelationId(paramVO.getRelationId());

            // 如果任务阶段详情不为空，则转换格式bean->vo
            if (null != htTaskPeriodBean) {

                // 任务阶段关联id
                htTaskPeriodVO.setRelationId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getRelationId()));

                // 任务ID
                htTaskPeriodVO.setTaskId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getTaskId()));

                // 阶段id
                htTaskPeriodVO.setPeriodId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getPeriodId()));

                // 流程实例ID
                htTaskPeriodVO.setProcessInstId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getProcessInstId()));

                // 委托任务Key
                htTaskPeriodVO.setDelegateTaskKey(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getDelegateTaskKey()));

                // 委托任务Id
                htTaskPeriodVO.setDelegateTaskId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getDelegateTaskId()));

                // 提审人id
                htTaskPeriodVO.setApplyUserId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getApplyUserId()));

                // 提审人
                htTaskPeriodVO.setApplyUserName(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getApplyUserName()));

                // 审批人id
                htTaskPeriodVO.setApproveUserId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getApproveUserId()));

                // 审批人
                htTaskPeriodVO.setApproveUserName(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getApproveUserName()));

                // 审批时间
                htTaskPeriodVO.setApproveTime(DateToolUtils.convertDateFormat(
                        htTaskPeriodBean.getApproveTime(), DateToolUtils.yyyy_MM_dd));

                // 流程完成时间
                htTaskPeriodVO.setCompleteTime(DateToolUtils.convertDateFormat(
                        htTaskPeriodBean.getCompleteTime(), DateToolUtils.yyyy_MM_dd));

                // 审核状态
                htTaskPeriodVO.setVerifyState(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getVerifyState()));

                // 是否可以撤回（0：否 1：是）
                htTaskPeriodVO.setIsCanRecall(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getIsCanRecall()));

                // 部门id
                htTaskPeriodVO.setDepartmentId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getDepartmentId()));

                // 提交节点
                htTaskPeriodVO.setSubmitNode(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getSubmitNode()));

                // 是否提了重新编辑申请
                htTaskPeriodVO.setIsSubmitReedit(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getIsSubmitReedit()));

                // 修改时间
                htTaskPeriodVO.setUpdateDate(DateToolUtils
                        .convertDateFormat(htTaskPeriodBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

                // 查询任务成员列表
                List<HTTaskBean> htTaskBeanList = htTaskDao.excSearchTaskUserListByTaskId(htTaskPeriodBean);

                List<HTTaskPeriodVO> userList = new ArrayList<>();

                for (HTTaskBean retBean : htTaskBeanList) {

                    HTTaskPeriodVO retVO = new HTTaskPeriodVO();

                    // 成员ID
                    retVO.setUserId(StringToolUtils.convertNullObjectToString(retBean.getUserId()));

                    // 成员名称
                    retVO.setUserName(StringToolUtils.convertNullObjectToString(retBean.getUserName()));

                    // 是否是主负责人
                    retVO.setIsMainDuty(StringToolUtils.convertNullObjectToString(retBean.getIsMainDuty()));

                    userList.add(retVO);
                }

                // 设置人员信息list
                htTaskPeriodVO.setUserList(userList);

            }

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }

        return htTaskPeriodVO;
    }

    /**
     * 查询审核意见列表
     *
     * @return 审核意见信息
     */
    @Override
    @Transactional
    public List<HTTaskPeriodVO> excSearchHTTaskHisTaskInst(HTTaskPeriodVO paramVO) throws SystemException {

        try {

            HTTaskPeriodBean paramBean = new HTTaskPeriodBean();

            // 设置任务用户关联id
            paramBean.setTaskPeriodId(paramVO.getRelationId());

            // 查询审核意见信息
            List<HTTaskPeriodBean> htTaskPeriodBeanList = htTaskDao.excSearchHTTaskHisTaskInstInfoList(paramBean);

            List<HTTaskPeriodVO> resultList = new ArrayList<>();

            // 格式转换
            for (HTTaskPeriodBean htTaskPeriodBean : htTaskPeriodBeanList) {

                HTTaskPeriodVO htTaskPeriodVO = new HTTaskPeriodVO();

                //任务Key
                htTaskPeriodVO.setTaskKey(htTaskPeriodBean.getTaskKey());
                //唯一标识
                htTaskPeriodVO.setHistoryId(htTaskPeriodBean.getHistoryId());
                //任务阶段ID
                htTaskPeriodVO.setTaskPeriodId(htTaskPeriodBean.getTaskPeriodId());
                //审批人
                htTaskPeriodVO.setApproveUserId(htTaskPeriodBean.getApproveUserId());
                //审批人名称
                htTaskPeriodVO.setApproveUserName(htTaskPeriodBean.getApproveUserName());

                // 获取任务阶段list
                List<HTTaskPeriodBean> childHisTaskInstBeanList = htTaskPeriodBean.getHTTaskPeriodBeanList();

                List<HTTaskPeriodVO> childHisTaskInstVOList = new ArrayList<>();

                // 循环遍历
                for (HTTaskPeriodBean childHisTaskInstBean : childHisTaskInstBeanList) {

                    HTTaskPeriodVO childHisTaskInstVO = new HTTaskPeriodVO();

                    //任务Key
                    childHisTaskInstVO.setTaskKey(childHisTaskInstBean.getTaskKey());
                    //唯一标识
                    childHisTaskInstVO.setHistoryId(childHisTaskInstBean.getHistoryId());
                    //任务阶段ID
                    childHisTaskInstVO.setTaskPeriodId(childHisTaskInstBean.getTaskPeriodId());
                    //拒绝原因
                    childHisTaskInstVO.setRejectReason(childHisTaskInstBean.getRejectReason());
                    //审批人
                    childHisTaskInstVO.setApproveUserId(childHisTaskInstBean.getApproveUserId());
                    //审批人名称
                    childHisTaskInstVO.setApproveUserName(childHisTaskInstBean.getApproveUserName());
                    //签名路径
                    childHisTaskInstVO.setSignPath(childHisTaskInstBean.getSignPath());
                    //审批时间
                    childHisTaskInstVO.setApproveTime(DateToolUtils.convertDateFormat(
                            childHisTaskInstBean.getApproveTime(), DateToolUtils.yyyy_MM_dd));
                    //审批人名称
                    childHisTaskInstVO.setIsPass(childHisTaskInstBean.getIsPass());

                    //成员问题关联数据
                    List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList = childHisTaskInstBean.getHtUserQuestionRecordRelationBeanList();

                    List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList = new ArrayList<>();

                    // 循环遍历
                    for (HTUserQuestionRecordRelationBean htUserQuestionRecordRelationBean : htUserQuestionRecordRelationBeanList) {
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

                htTaskPeriodVO.setHtTaskPeriodVOList(childHisTaskInstVOList);

                resultList.add(htTaskPeriodVO);
            }
            return resultList;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }

    }

    /**
     * 审核任务(A3阶段)
     */
    @Override
    @Transactional
    public void auditingTaskA3Period(HTTaskPeriodVO paramVO) throws LogicException,SystemException {

        try {

            // =====================================判断选择错误是否为空 start=========================================

            // 如果是驳回，并且选择错误不能为空
            if (Constant.NOT_APPROVED.equals(paramVO.getIsPass()) && ListToolUtils.isEmpty(paramVO.getUserQuestionRecordRelationList())) {
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0013);
            }

            // =====================================判断选择错误是否为空 start=========================================

            // 通过任务阶段id查询任务阶段详情
            HTTaskPeriodBean htTaskPeriodBean =
                    htTaskDao.excSearchTaskPeriodDetailInfoByRelationId(paramVO.getRelationId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskPeriodBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            // 获取当前时间，为了保证创建时间和修改时间都一样
            Date currentDate = getCurrentDate();

            //对比更新时间,如果时间不同，则提示该申请已经被审核，请重新获取数据！
            if (!paramVO.getUpdateDate().equals(DateToolUtils.convertDateFormat(
                    htTaskPeriodBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO))) {
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0007);
            }

            // 是否通过
            htTaskPeriodBean.setIsPass(paramVO.getIsPass());

            // 意见
            htTaskPeriodBean.setRejectReason(paramVO.getRejectReason());

            // 下一节点
            htTaskPeriodBean.setSubmitNode(paramVO.getSubmitNode());

            HTTaskPeriodVO htTaskPeriodVO = (HTTaskPeriodVO) ObjectReflectUtils.convertObj(htTaskPeriodBean, HTTaskPeriodVO.class);

            Map<String, Object> variables = new HashMap<>();

            //登录人信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();
            //登录人信息
            htTaskPeriodVO.setLoginUserInfo(userInfo);
            //审批人ID
            htTaskPeriodVO.setApproveUserId(userInfo.getLoginUserId());
            //审批人名称
            htTaskPeriodVO.setApproveUserName(userInfo.getUserName());
            //启动工作流
            variables.put(WorkFlowConstant.KEY_TASK_PERIOD_VO, htTaskPeriodVO);
            // 是否通过
            String isPass = htTaskPeriodVO.getIsPass();
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, isPass);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            if (Constant.NOT_APPROVED.equals(isPass)) {
                variables.put(WorkFlowConstant.KEY_REJECT_REASON, htTaskPeriodVO.getRejectReason());
                variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, htTaskPeriodVO.getDelegateTaskKey());
                commonActivitiListenVO.setDepartmentId(htTaskPeriodVO.getDepartmentId());
            } else {
                String departmentId = StringUtils.isBlank(TaskA3Enum.getDeptId(htTaskPeriodVO.getSubmitNode())) ? htTaskPeriodVO.getDepartmentId() : TaskA3Enum.getDeptId(htTaskPeriodVO.getSubmitNode());
                commonActivitiListenVO.setDepartmentId(departmentId);
                variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, htTaskPeriodVO.getSubmitNode());
            }
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, userInfo);
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            String historyRecordId = taskA3ProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(), variables);

            // 如果有错误问题，则记录
            if (!ListToolUtils.isEmpty(paramVO.getUserQuestionRecordRelationList())) {

                //批量插入审核记录和人员和问题类型关联
                List<HTUserQuestionRecordRelationVO> relationVOList = paramVO.getUserQuestionRecordRelationList();

                List<HTUserQuestionRecordRelationBean> relationBeanList = new ArrayList<>();

                // 循环遍历问题list
                for (HTUserQuestionRecordRelationVO htUserQuestionRecordRelationVO : relationVOList) {

                    HTUserQuestionRecordRelationBean htUserQuestionRecordRelationBean = new HTUserQuestionRecordRelationBean();
                    //唯一标识
                    htUserQuestionRecordRelationBean.setRelationId(UUID.randomUUID().toString());
                    //历史id
                    htUserQuestionRecordRelationBean.setRecordId(historyRecordId);
                    //人员ID
                    htUserQuestionRecordRelationBean.setUserId(htUserQuestionRecordRelationVO.getUserId());
                    //问题ID
                    htUserQuestionRecordRelationBean.setQuestionId(htUserQuestionRecordRelationVO.getQuestionId());
                    //问题个数
                    htUserQuestionRecordRelationBean.setQuestionNum(htUserQuestionRecordRelationVO.getQuestionNum());
                    //来源
                    htUserQuestionRecordRelationBean.setSourceType(Constant.TASK_SOURCE_TYPE);
                    //业务id
                    htUserQuestionRecordRelationBean.setBusinessId(htTaskPeriodBean.getTaskId());
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

            HTTaskPeriodMaterialVO htTaskPeriodMaterialVO = new HTTaskPeriodMaterialVO();

            // 设置任务阶段id
            htTaskPeriodMaterialVO.setTaskPeriodId(paramVO.getRelationId());

            // 查询onlyOffice需要的信息
            HTTaskPeriodMaterialVO taskPeriodMaterialVO = this.excSearchMaterialByTaskPeriodId(htTaskPeriodMaterialVO);

            // 业务ID(任务阶段ID)
            paramVO.setBusinessId(paramVO.getRelationId());

            // 创建时间
            paramVO.setCreateDate(DateToolUtils.convertDateFormat(currentDate,DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            // 创建人
            paramVO.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 修改时间
            paramVO.setUpdateDate(DateToolUtils.convertDateFormat(currentDate,DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            // 修改人
            paramVO.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 如果是驳回
            if (Constant.NOT_APPROVED.equals(isPass) && !StringToolUtils.isNull(paramVO.getOnlyOfficeEditKey())) {

                String uuid = UUID.randomUUID().toString();
                // 文件名
                String realFileName = taskPeriodMaterialVO.getMaterialName();
                // 获取文件的后缀名
                String suffixName = realFileName.substring(realFileName.lastIndexOf("."));
                // 获取文件路径
                String fileName = uuid + suffixName;
                // 文件上传后的路径
                String filePath = Constant.FILE_SEPARATE + "review" + Constant.FILE_SEPARATE + "a3" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;

                //文件地址
                paramVO.setReviewFilePath(filePath);
                // 获取文件的真实名称
                paramVO.setReviewFileName(taskPeriodMaterialVO.getMaterialName());
                //通知OnlyOffice documentServer 保存文件
                OnlyOfficeUtils.notifyOnlyOfficeToSave(paramVO);

            }

            TbTaskPeriodRelationEntity tbTaskPeriodRelationEntity = new TbTaskPeriodRelationEntity();
            // 设置任务阶段id
            tbTaskPeriodRelationEntity.setRelationId(paramVO.getRelationId());
            // 设置修改时间
            tbTaskPeriodRelationEntity.setUpdateDate(currentDate);
            // 设置修改人id
            tbTaskPeriodRelationEntity.setUpdateUserId(userInfo.getLoginUserId());
            // 修改数据
            tbTaskPeriodRelationEntityMapper.updateByPrimaryKeySelective(tbTaskPeriodRelationEntity);

            // ================================================ 插入消息信息 start =====================================================

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(
                    htTaskPeriodBean.getApplyTime(), DateToolUtils.yyyy_MM_dd);

            // 当前审核节点
            String delegateTaskKey = htTaskPeriodBean.getDelegateTaskKey();

            // 职位名称
            String jobName = "";

            switch (delegateTaskKey){
                // 部门经理
                case "ManagerVerify":
                    jobName = TaskA3Enum.ManagerVerify.getJobName();
                    break;

                // 质控部组员
                case "QualityVerify":
                    jobName = TaskA3Enum.QualityVerify.getJobName();
                    break;

                // 质控部副总经理
                case "GenerManagerVerify":
                    jobName = TaskA3Enum.GenerManagerVerify.getJobName();
                    break;

                // 签发人
                case "Signer":
                    jobName = TaskA3Enum.Signer.getJobName();
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
                    +htTaskPeriodBean.getProjectNum()+"项目"
                    +htTaskPeriodBean.getProjectNum()+"-"+htTaskPeriodBean.getContractNum()+"合同"
                    +htTaskPeriodBean.getTaskName()+"任务的"
                    +htTaskPeriodBean.getPeriodName()+"阶段 申请 ，被 "
                    +jobName+"("+userInfo.getUserName()+")审核并"+tempAuditResultStr;

            // 设置消息内容
            tbMessageEntity.setMessageContent(tempMessageContentStr);
            // 设置接收消息人
            tbMessageEntity.setAcceptUserId(htTaskPeriodBean.getApplyUserId());
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
                    ,htTaskPeriodBean.getOpenId(),htTaskPeriodBean.getApplyUserName(),tempMessageContentStr);

            // ================================================ 插入消息信息 end =====================================================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 撤回任务(A3阶段)
     */
    @Override
    @Transactional
    public void excRecallTaskA3Period(HTTaskPeriodVO paramVO) throws LogicException, SystemException {
        try {

            // 通过任务阶段id查询任务阶段详情
            HTTaskPeriodBean htTaskPeriodBean =
                    htTaskDao.excSearchTaskPeriodDetailInfoByRelationId(paramVO.getRelationId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskPeriodBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            //是否可以撤回（0：否 1：是）,如果不能撤回，则提示信息
            if (htTaskPeriodBean.getIsCanRecall().equals(Constant.IS_CAN_RECALL_DISABLE)) {
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0001);
            }

            // 格式转换 Bean->VO
            HTTaskPeriodVO htTaskPeriodVO = (HTTaskPeriodVO) ObjectReflectUtils.convertObj(htTaskPeriodBean, HTTaskPeriodVO.class);

            // 设置审批状态为未通过
            htTaskPeriodVO.setIsPass(Constant.NOT_APPROVED);

            // 获取当前登录用户信息
            HTUserVO userInfo = paramVO.getLoginUserInfo();

            // 设置当前登录用户信息
            htTaskPeriodVO.setLoginUserInfo(userInfo);

            // 设置审批人id
            htTaskPeriodVO.setApproveUserId(userInfo.getLoginUserId());

            // 设置审批人
            htTaskPeriodVO.setApproveUserName(userInfo.getUserName());

            htTaskPeriodVO.setIsPass(Constant.NOT_APPROVED);

            //启动工作流
            taskA3ProcessService.excRecallTask(htTaskPeriodVO);

            // 添加修改日期和修改人
            TbTaskPeriodRelationEntity tbTaskPeriodRelationEntity = new TbTaskPeriodRelationEntity();
            tbTaskPeriodRelationEntity.setRelationId(paramVO.getRelationId());
            tbTaskPeriodRelationEntity.setUpdateDate(getCurrentDate());
            tbTaskPeriodRelationEntity.setUpdateUserId(userInfo.getLoginUserId());
            tbTaskPeriodRelationEntityMapper.updateByPrimaryKeySelective(tbTaskPeriodRelationEntity);

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_TASK_0004);
        }
    }

    /**
     * 提交审核(A3阶段)
     */
    @Override
    @Transactional
    public void excSubmitAuditA3Period(HTTaskPeriodVO paramVO) throws LogicException,SystemException {

        try {

            // 通过任务阶段id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getRelationId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            // ======================================= 判断该阶段下的任务的审核状态 start =========================================

            // 如果审核状态不为空
            if (null != htTaskBean.getVerifyState()) {
                // 抛出异常
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0005);
            }

            // ======================================= 判断该阶段下的任务的审核状态 end =========================================

            // ======================================= 任务阶段详情 start =========================================

            // 通过任务阶段id查询任务阶段详情
            HTTaskPeriodBean htTaskPeriodBean =
                    htTaskDao.excSearchTaskPeriodDetailInfoByRelationId(paramVO.getRelationId());

            // 跳转节点
            htTaskPeriodBean.setSubmitNode(paramVO.getSubmitNode());

            // 格式转换 Bean->VO
            HTTaskPeriodVO htTaskPeriodVO =
                    (HTTaskPeriodVO) ObjectReflectUtils.convertObj(htTaskPeriodBean, HTTaskPeriodVO.class);

            // 设置当前登录人信息
            htTaskPeriodVO.setLoginUserInfo(paramVO.getLoginUserInfo());

            // ======================================= 任务阶段详情 end =========================================

            //启动工作流
            Map<String, Object> variable = new HashMap<>();
            variable.put(WorkFlowConstant.KEY_TASK_PERIOD_VO, htTaskPeriodVO);
            variable.put(WorkFlowConstant.KEY_SUBMIT_NODE, htTaskPeriodVO.getSubmitNode());
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            String departmentId = StringUtils.isBlank(TaskA3Enum.getDeptId(htTaskPeriodVO.getSubmitNode())) ? htTaskPeriodVO.getDepartmentId() : TaskA3Enum.getDeptId(htTaskPeriodVO.getSubmitNode());
            commonActivitiListenVO.setDepartmentId(departmentId);
            variable.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
            variable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            taskA3ProcessService.excHandleTask(paramVO.getLoginUserInfo().getLoginUserId(),htTaskPeriodVO.getDelegateTaskId(), variable);

            // ================================================ 插入消息信息 start =====================================================

            Date currentDate = getCurrentDate();

            TbMessageEntity tbMessageEntity = new TbMessageEntity();

            // 提审时间
            String applyTime = DateToolUtils.convertDateFormat(currentDate,DateToolUtils.yyyy_MM_dd);

            HTContractBean htContractBean = new HTContractBean();
            // 设置当前登录用户id
            htContractBean.setCurrentUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 设置部门id
            htContractBean.setDepartmentId(htTaskBean.getDepartmentId());
            // 通过部门id和当前登录人id查询职位名称
            List<HTContractBean> htContractBeanList = htContractDao.excSearchJobNameByLoginUserIdAndDepartmentId(htContractBean);

            // 职位和查询得到的人员id
            HTTaskBean returnBean = commonService.excSearchUserIdForMessage(paramVO.getSubmitNode(),htTaskBean.getDepartmentId());

            // 消息内容
            String tempMessageContentStr = "由"+htContractBeanList.get(0).getJobName()+"("+paramVO.getLoginUserInfo().getUserName()+")"+applyTime+"发起的"
                    +htTaskBean.getProjectNum()+"项目"
                    +htTaskBean.getProjectNum()+"-"+htTaskBean.getContractNum()+"合同"
                    +htTaskBean.getTaskName()+"任务"+paramVO.getPeriodName()+"阶段该由您审核了";

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

//            // ==========================================删除文件 start======================================
//
//            HTReviewFileRelationBean htReviewFileRelationBean = new HTReviewFileRelationBean();
//
//            // 事务唯一标识
//            htReviewFileRelationBean.setBusinessId(paramVO.getRelationId());
//
//            // 审核意见文件列表
//            List<HTReviewFileRelationBean> htReviewFileRelationBeanList = htReviewFileRelationDao
//                    .excSearchReviewFileRelationInfoByBusinessId(htReviewFileRelationBean);
//
//            // 遍历删除审核意见文件(物理删除)
//            for (HTReviewFileRelationBean resultBean:htReviewFileRelationBeanList){
//                //删除审核意见文件(物理删除)
//                FileUtils.deleteFile(resultBean.getReviewFilePath());
//            }
//
//            //删除审核意见文件(删除数据)
//            htReviewFileRelationDao.excDeleteReviewFileRelationInfoByBusinessId(htReviewFileRelationBean);
//
//            // ==========================================删除文件 end======================================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 删除阶段文件信息
     *
     * @return
     */
    @Override
    @Transactional
    public void excDeleteTaskPeriodMaterialInfo(HTTaskPeriodMaterialVO paramVO) throws SystemException {

        try {

            // 通过任务id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByTaskId(paramVO.getTaskId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            String materialPath = paramVO.getMaterialPath();

            //删除文件信息
            tbTaskPeriodMaterialRelationEntityMapper.deleteByPrimaryKey(paramVO.getRelationId());

            //删除真实文件
            FileUtils.deleteFile(materialPath);

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0009);
        }
    }

    /**
     * 删除成果文件信息
     *
     * @return
     */
    @Override
    @Transactional
    public void excDeleteResultFileMaterialInfo(HTTaskPeriodMaterialVO paramVO) throws SystemException {

        try {

            // 通过任务id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByTaskId(paramVO.getTaskId());

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            TbTaskEntityExample tbTaskEntityExample = new TbTaskEntityExample();
            TbTaskEntityExample.Criteria criteria = tbTaskEntityExample.createCriteria();
            criteria.andTaskMaterialPathEqualTo(paramVO.getMaterialPath());
            List<TbTaskEntity> tbTaskEntityList = tbTaskEntityMapper.selectByExample(tbTaskEntityExample);

            // 根据路径，判断该成果文件是否存在，如果存在的话，则清空；如果不存在，则删除多文件
            if(!ListToolUtils.isEmpty(tbTaskEntityList)){
                TbTaskEntity tbTaskEntity = new TbTaskEntity();
                tbTaskEntity.setTaskId(paramVO.getTaskId());// 设置任务id
                tbTaskEntity.setTaskMaterialName("");// 清空文件名称
                tbTaskEntity.setTaskMaterialPath("");// 清空文件路径
                tbTaskEntity.setFormworkType("");// 清空模板类型
                tbTaskEntity.setFormworkContent("");// 清空模板内容

                //修改成果文件路径和名称信息为空
                tbTaskEntityMapper.updateByPrimaryKeySelective(tbTaskEntity);
            } else {
                //删除文件信息
                tbTaskPeriodMaterialRelationEntityMapper.deleteByPrimaryKey(paramVO.getRelationId());
            }

            String materialPath = paramVO.getMaterialPath(); // 临时变量：文件路径
            FileUtils.deleteFile(materialPath);// 删除真实文件

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0009);
        }
    }

    /**
     * 查询任务阶段文件列表
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTTaskPeriodMaterialVO> searchTaskPeriodMaterialList(HTTaskPeriodMaterialVO paramVO) throws SystemException {

        List<HTTaskPeriodMaterialVO> htTaskPeriodMaterialVOList = new ArrayList<>();

        try {

            HTTaskPeriodMaterialBean paramBean = new HTTaskPeriodMaterialBean();

            // 任务阶段ID
            paramBean.setTaskPeriodId(StringToolUtils
                    .convertNullObjectToString(paramVO.getRelationId()));

            // 查询任务阶段文件信息列表
            List<HTTaskPeriodMaterialBean> htTaskPeriodMaterialBeanList =
                    htTaskDao.excSearchTaskPeriodMaterialListByProperty(paramBean);

            for (HTTaskPeriodMaterialBean htTaskPeriodMaterialBean : htTaskPeriodMaterialBeanList) {

                HTTaskPeriodMaterialVO htTaskPeriodMaterialVO = new HTTaskPeriodMaterialVO();

                // 唯一标识
                htTaskPeriodMaterialVO.setRelationId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getRelationId()));

                // 关联阶段ID
                htTaskPeriodMaterialVO.setTaskPeriodId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getTaskPeriodId()));

                // 文件类型ID
                htTaskPeriodMaterialVO.setFileType(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getFileType()));

                // 文件名称
                htTaskPeriodMaterialVO.setMaterialName(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getMaterialName()));

                // 文件地址
                htTaskPeriodMaterialVO.setMaterialPath(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getMaterialPath()));

                // 是否提审（0：否 1：是）
                htTaskPeriodMaterialVO.setIsReview(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getIsReview()));

                // 用户名
                htTaskPeriodMaterialVO.setUserName(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodMaterialBean.getUserName()));

                // 如果该文件是当前登录用户上传的，则返回1：可以删除，否则返回0：不可以删除
                if (paramVO.getLoginUserInfo().getLoginUserId().equals(htTaskPeriodMaterialBean.getCreateUserId())) {
                    // 1：可以删除
                    htTaskPeriodMaterialVO.setIsCanDel(Constant.IS_CAN_DEL_FLG_ABLE);
                } else {
                    // 0：不可以删除
                    htTaskPeriodMaterialVO.setIsCanDel(Constant.IS_CAN_DEL_FLG_ENABLE);
                }

                htTaskPeriodMaterialVOList.add(htTaskPeriodMaterialVO);

            }

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }

        return htTaskPeriodMaterialVOList;
    }

    /**
     * 根据任务阶段id查询项目编号、合同编号和成果文件编号
     *
     * @return 项目编号、合同编号和成果文件编号
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskVO excSearchProjectNumContractNumFileNumberByTaskPeriodId(HTTaskVO paramVO) {

        HTTaskVO htTaskVO = new HTTaskVO();

        try {

            HTTaskBean paramBean = new HTTaskBean();

            // 任务阶段id
            paramBean.setRelationId(StringToolUtils
                    .convertNullObjectToString(paramVO.getRelationId()));

            // 根据任务阶段id查询项目编号、合同编号和成果文件编号
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchProjectNumContractNumFileNumberByTaskPeriodId(paramBean);

            // 如果有数据
            if (null != htTaskBean) {
                String projectName = htTaskBean.getTaskName();// 项目名称
                if(!StringToolUtils.isNull(htTaskBean.getProjectName())){// 如果项目名称不为空，则拼接项目名称-任务名称
                    projectName = htTaskBean.getProjectName()+"-"+htTaskBean.getTaskName();
                }
                // 项目名称
                htTaskVO.setProjectName(StringToolUtils.convertNullObjectToString(projectName));

                // 合同编号
                htTaskVO.setContractNum(StringToolUtils.convertNullObjectToString(
                        htTaskBean.getProjectNum()+"-"+htTaskBean.getContractNum()));

                // 成果文件编号
                htTaskVO.setReportNum(StringToolUtils
                        .convertNullObjectToString(htTaskBean.getReportNum()));
            }

        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htTaskVO;
    }

    /**
     * 新增任务阶段文件
     *
     * @return
     */
    @Override
    @Transactional
    public void excInsertTaskPeriodMaterialInfo(HTTaskPeriodMaterialVO paramVO, MultipartFile[] file) throws LogicException, SystemException {

        try {

            // 通过任务阶段id查询任务详情
            HTTaskBean htTaskBean =
                    htTaskDao.excSearchTaskDetailInfoByRelationId(paramVO.getTaskPeriodId());

            // 如果任务详情为空，则提示该阶段所属的任务已被删除
            if(null == htTaskBean){
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0012);
            }

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            // =====================================删除原有文件 start=====================================

            // 如果是成果文件阶段或者是A3阶段
            if(Constant.A3_PERIOD_TYPE.equals(paramVO.getPeriodType())){

                HTTaskPeriodMaterialBean htTaskPeriodMaterialBean = new HTTaskPeriodMaterialBean();

                // 设置任务阶段id
                htTaskPeriodMaterialBean.setTaskPeriodId(StringToolUtils
                        .convertNullObjectToString(paramVO.getTaskPeriodId()));

                // 通过任务阶段id查询素材路径和关联id
                TbTaskPeriodMaterialRelationEntity retEntity =
                        htTaskDao.excSearchMaterialPathByTaskPeriodId(htTaskPeriodMaterialBean);

                // 如果数据不为空，则删除文件信息
                if(null != retEntity){

                    // 删除文件信息
                    tbTaskPeriodMaterialRelationEntityMapper.deleteByPrimaryKey(retEntity.getRelationId());

                    // 删除真实文件
                    FileUtils.deleteFile(retEntity.getMaterialPath());

                }

            }

            // =====================================删除原有文件 end=====================================

            // 登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(paramVO.getLoginUserInfo().getLoginUserId());

            // 循环添加文件
            for (int i = 0;i < file.length;i++) {

                Date currentDate = getCurrentDate();

                TbTaskPeriodMaterialRelationEntity tbTaskPeriodMaterialRelationEntity = new TbTaskPeriodMaterialRelationEntity();

                // uuid 用于拼接文件路径
                String uuid = UUID.randomUUID().toString();

                // 文件上传后的路径
                String filePath = "";

                // 文件名
                String realFileName = file[i].getOriginalFilename();

                // 拼接文件名称
                String fileName = uuid + realFileName.substring(realFileName.lastIndexOf("."));

                // 文件上传后的路径
                filePath = Constant.FILE_SEPARATE + "task" + Constant.FILE_SEPARATE + uuid + Constant.FILE_SEPARATE + fileName;

                // 文件名称
                tbTaskPeriodMaterialRelationEntity.setMaterialName(realFileName);
                // 文件地址
                tbTaskPeriodMaterialRelationEntity.setMaterialPath(filePath);
                // 设置主键id
                tbTaskPeriodMaterialRelationEntity.setRelationId(uuid);
                // 任务阶段关联ID
                tbTaskPeriodMaterialRelationEntity.setTaskPeriodId(StringToolUtils.convertNullObjectToString(paramVO.getTaskPeriodId()));
                // 文件类型ID
                tbTaskPeriodMaterialRelationEntity.setFileType(StringToolUtils.convertNullObjectToString(paramVO.getFileType()));
                // 备注
                tbTaskPeriodMaterialRelationEntity.setRemark(StringToolUtils.convertNullObjectToString(paramVO.getRemark()));
                // 是否提审（0：否 1：是）
                tbTaskPeriodMaterialRelationEntity.setIsReview(Constant.IS_REVIEW_DEFAULT_VALUE);
                // 原件/复印件
                tbTaskPeriodMaterialRelationEntity.setCopyFile(StringToolUtils.convertNullObjectToString(paramVO.getCopyFile()));
                // 份数
                tbTaskPeriodMaterialRelationEntity.setNum(StringToolUtils.convertNullObjectToString(paramVO.getNum()));
                // 接收人
                tbTaskPeriodMaterialRelationEntity.setRecipient(StringToolUtils.convertNullObjectToString(paramVO.getRecipient()));
                // 接收时间
                tbTaskPeriodMaterialRelationEntity.setRecipientDate(DateToolUtils.convertDateFormat(paramVO.getRecipientDate(), DateToolUtils.yyyy_MM_dd));
                // 是否删除
                tbTaskPeriodMaterialRelationEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
                // 创建人
                tbTaskPeriodMaterialRelationEntity.setCreateUserId(tempLoginUserIdStr);
                // 创建时间
                tbTaskPeriodMaterialRelationEntity.setCreateDate(currentDate);
                // 修改人
                tbTaskPeriodMaterialRelationEntity.setUpdateUserId(tempLoginUserIdStr);
                // 修改时间
                tbTaskPeriodMaterialRelationEntity.setUpdateDate(currentDate);

                // 添加文件信息
                htTaskDao.excInsertTaskPeriodMaterialRelationInfo(tbTaskPeriodMaterialRelationEntity);

                // 上传文件
                boolean isUploadSignSuccess = FileUtils.uploadFile(filePath, file[i]);

                if (!isUploadSignSuccess) {
                    // MSG_ERROR_PUBLIC_NOTICE_0001 上传文件失败！
                    throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0001);
                }

            }

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 修改工时列表中的备注
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdateRemarksInfo(HTTaskVO paramVO) throws SystemException {

        try {

            TbTaskUserRelationEntity tbTaskUserRelationEntity = new TbTaskUserRelationEntity();

            // 任务用户关联id
            tbTaskUserRelationEntity.setRelationId(StringToolUtils
                    .convertNullObjectToString(paramVO.getRelationId()));

            // 备注
            tbTaskUserRelationEntity.setRemarks(StringToolUtils
                    .convertNullObjectToString(paramVO.getRemarks()));

            // 修改时间
            tbTaskUserRelationEntity.setUpdateDate(getCurrentDate());

            // 修改人id
            tbTaskUserRelationEntity.setUpdateUserId(
                    StringToolUtils.convertNullObjectToString(
                            paramVO.getLoginUserInfo().getLoginUserId()));

            tbTaskUserRelationEntityMapper.updateByPrimaryKeySelective(tbTaskUserRelationEntity);

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 查询备注
     *
     * @return 查询备注
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskVO excSearchRemarksByRelationId(HTTaskVO paramVO) throws SystemException {

        HTTaskVO htTaskVO = new HTTaskVO();

        try {

            // 查询任务内容
            TbTaskUserRelationEntity retEntity =
                    tbTaskUserRelationEntityMapper.selectByPrimaryKey(paramVO.getRelationId());

            // 如果有数据
            if (null != retEntity) {
                // 关联id
                htTaskVO.setRelationId(StringToolUtils
                        .convertNullObjectToString(retEntity.getRelationId()));

                // 备注
                htTaskVO.setRemarks(StringToolUtils
                        .convertNullObjectToString(retEntity.getRemarks()));
            }

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htTaskVO;
    }

    /**
     * 工时列表和总条数
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchHourListInfo(HTTaskVO paramVO) throws SystemException {

        Map<String, Object> resultMap = new HashMap<>();

        try {

            List<HTTaskVO> hourVOListInfo = new ArrayList<>();

            HTTaskBean paramBean = new HTTaskBean();

            // 设置用户ID
            paramBean.setUserId(StringToolUtils.convertNullObjectToString(
                    paramVO.getLoginUserInfo().getLoginUserId()));

            // 任务名称
            paramBean.setTaskName(StringToolUtils
                    .convertNullObjectToString(paramVO.getTaskName()));

            // 姓名
            paramBean.setUserName(StringToolUtils
                    .convertNullObjectToString(paramVO.getUserName()));

            // 如果检索条件任务分配年月不为空
            if (!StringToolUtils.isNull(paramVO.getCreateDate())) {
                //将任务分配年月赋值给bean
                paramBean.setCreateDate(DateToolUtils.convertDateFormat(
                        paramVO.getCreateDate(), DateToolUtils.yyyy_MM));
            }

            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            // 查询工时列表数据的总条数
            int retCount = htTaskDao.excSearchHourListCount(paramBean);

            // 工时列表
            List<HTTaskBean> hourBeanListInfo = htTaskDao.excSearchHourListInfo(paramBean);

            // 如果有数据，hourBeanListInfo->hourVOListInfo
            if (!ListToolUtils.isEmpty(hourBeanListInfo)) {

                // hourBeanListInfo->hourVOListInfo
                for (HTTaskBean htTaskBean : hourBeanListInfo) {

                    HTTaskVO htTaskVO = new HTTaskVO();

                    // 任务名称
                    htTaskVO.setTaskName(StringToolUtils
                            .convertNullObjectToString(htTaskBean.getTaskName()));

                    // 用户id
                    htTaskVO.setUserId(StringToolUtils
                            .convertNullObjectToString(htTaskBean.getUserId()));

                    // 用户名称
                    htTaskVO.setUserName(StringToolUtils
                            .convertNullObjectToString(htTaskBean.getUserName()));

                    // 完成时间
                    htTaskVO.setEndDate(DateToolUtils
                            .convertDateFormat(htTaskBean.getEndDate(), DateToolUtils.yyyy_MM_dd));

                    // 预计占用工时
                    String needHour = StringToolUtils.convertNullObjectToString(
                            htTaskBean.getNeedHour() == null ? "0" : htTaskBean.getNeedHour());

                    // 设置预计占用工时
                    htTaskVO.setNeedHour(needHour);

                    // 已占用工时
                    String actualHourSum = StringToolUtils.convertNullObjectToString(
                            htTaskBean.getActualHourSum() == null ? "0" : htTaskBean.getActualHourSum());

                    // 设置已占用工时
                    htTaskVO.setActualHourSum(actualHourSum);

                    // 如果实际工时大于预计工时，则高亮显示 0：高亮   1：不高亮
                    if(NumberToolUtils.convertNullToInteger(needHour) - NumberToolUtils.convertNullToInteger(actualHourSum) >= 0){
                        htTaskVO.setHighlight(StringToolUtils.STRING_ONE);
                    } else {
                        htTaskVO.setHighlight(StringToolUtils.STRING_ZERO);
                    }

                    // 任务和人员关联id
                    htTaskVO.setRelationId(StringToolUtils
                            .convertNullObjectToString(htTaskBean.getRelationId()));

                    hourVOListInfo.add(htTaskVO);

                }

            }

            resultMap.put(Constant.DATA_LIST_KEY, hourVOListInfo);

            resultMap.put(Constant.DATA_COUNT_KEY, retCount);

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    /**
     * 修改我的工时
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdateMyHourListInfo(HTTaskVO paramVO) throws SystemException,LogicException {

        try {

            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();

            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(
                    paramVO.getLoginUserInfo().getLoginUserId());

            // 时间对比
            // dateCompare=0,则说明时间相同；
            // dateCompare>0,则说明前端传入时间大于系统后台生成时间；
            // dateCompare<0,则说明前端传入时间小于系统后台生成时间；
            int dateCompare = paramVO.getNowDate().compareTo(DateToolUtils
                    .convertDateFormat(getCurrentDate(),"yyyy-MM-dd"));

            // 如果两次参数不一致，则说明此次请求为获取数据之后的第二天，则提示消息让用户从新获取数据
            if(dateCompare != 0){
                throw new LogicException(MessageConstant.MSG_ERROR_TASK_0016);
            }

            HTTaskBean paramBean = new HTTaskBean();

            List<HTTaskVO> htTaskVOList = paramVO.getTaskList();

            // 如果有数据
            if (!ListToolUtils.isEmpty(htTaskVOList)) {
                for (HTTaskVO htTaskVO : htTaskVOList) {

                    // 设置任务和用户关联ID
                    paramBean.setTaskUserId(StringToolUtils
                            .convertNullObjectToString(htTaskVO.getTaskUserId()));

                    // 设置当前时间
                    paramBean.setNowDate(getCurrentDate());

                    // 查询是否存在今天的工时记录
                    int count = htTaskDao.excSearchTodayHourInfoCount(paramBean);

                    TbUserScheduleRecordEntity tbUserScheduleRecordEntity = new TbUserScheduleRecordEntity();

                    // 设置今日工时分配
                    tbUserScheduleRecordEntity.setActualHour(NumberToolUtils
                            .convertNullToInteger(htTaskVO.getActualHour()));

                    // 设置修改时间
                    tbUserScheduleRecordEntity.setUpdateDate(tempCurrentDate);

                    // 设置修改人id
                    tbUserScheduleRecordEntity.setUpdateUserId(tempLoginUserIdStr);

                    // 如果有，则修改数据
                    if (count > NumberToolUtils.INT_ZERO) {

                        // 设置关联id
                        tbUserScheduleRecordEntity.setRelationId(StringToolUtils
                                .convertNullObjectToString(htTaskVO.getRelationId()));

                        tbUserScheduleRecordEntityMapper.updateByPrimaryKeySelective(tbUserScheduleRecordEntity);
                        //任务用户关联实体
                        TbTaskUserRelationEntity tbTaskUserRelationEntity = new TbTaskUserRelationEntity();
                        //唯一标识
                        tbTaskUserRelationEntity.setRelationId(htTaskVO.getTaskUserId());
                        //工时原因
                        tbTaskUserRelationEntity.setHourReason(StringToolUtils.convertNullObjectToString(htTaskVO.getHourReason()));
                        String customizeHour = htTaskVO.getCustomizeHour();
                        if (StringToolUtils.isNull(customizeHour)){
                            customizeHour = "0";
                        }
                        //自定义工时
                        tbTaskUserRelationEntity.setCustomizeHour(Integer.parseInt(customizeHour));
                        tbTaskUserRelationEntityMapper.updateByPrimaryKeySelective(tbTaskUserRelationEntity);
                        // 如果没有，则创建数据
                    } else {

                        // 设置任务和用户关联ID
                        tbUserScheduleRecordEntity.setTaskUserId(StringToolUtils
                                .convertNullObjectToString(htTaskVO.getTaskUserId()));

                        // 设置当前时间
                        tbUserScheduleRecordEntity.setNowDate(tempCurrentDate);

                        // 设置创建时间
                        tbUserScheduleRecordEntity.setCreateDate(tempCurrentDate);

                        // 设置创建人id
                        tbUserScheduleRecordEntity.setCreateUserId(tempLoginUserIdStr);

                        // 设置是否删除
                        tbUserScheduleRecordEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);

                        tbUserScheduleRecordEntityMapper.insertSelective(tbUserScheduleRecordEntity);
                    }

                }
            }

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 查询我的工时
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchMyHourListInfo(HTTaskVO paramVO) throws SystemException {

        Map<String, Object> retMap = new HashMap<>();

        try {

            List<HTTaskVO> htTaskVOList = new ArrayList<>();

            HTTaskBean paramBean = new HTTaskBean();

            // 设置用户ID
            paramBean.setUserId(StringToolUtils.convertNullObjectToString(
                    paramVO.getLoginUserInfo().getLoginUserId()));

            // 设置当前时间
            paramBean.setNowDate(getCurrentDate());

            // 查询我的工时
            List<HTTaskBean> htTaskBeanList = htTaskDao.excSearchMyHourListInfo(paramBean);

            // 如果有数据，则把beanList->voList
            if (!ListToolUtils.isEmpty(htTaskBeanList)) {
                // beanList->voList
                for (HTTaskBean htTaskBean : htTaskBeanList) {

                    HTTaskVO htTaskVO = new HTTaskVO();

                    // 任务和用户关联ID
                    htTaskVO.setTaskUserId(StringToolUtils.convertNullObjectToString(
                            htTaskBean.getTaskUserId()));

                    // 设置任务名称
                    htTaskVO.setTaskName(StringToolUtils.convertNullObjectToString(
                            htTaskBean.getTaskName()));

                    // 设置完成时间
                    htTaskVO.setEndDate(DateToolUtils.convertDateFormat(
                            htTaskBean.getEndDate(), DateToolUtils.yyyy_MM_dd));

                    // 设置关联id
                    htTaskVO.setRelationId(StringToolUtils
                            .convertNullObjectToString(htTaskBean.getRelationId()));

                    // 设置所需工时
                    htTaskVO.setNeedHour(StringToolUtils.convertNullObjectToString(
                            htTaskBean.getNeedHour() == null ? "0" : htTaskBean.getNeedHour()));

                    // 实际工时
                    htTaskVO.setActualHour(StringToolUtils
                            .convertNullObjectToString(htTaskBean.getActualHour()));

                    // 已分配工时合计
                    htTaskVO.setActualHourSum(StringToolUtils.convertNullObjectToString(
                            htTaskBean.getActualHourSum() == null ? "0" : htTaskBean.getActualHourSum()));

                    // 自定义工时
                    htTaskVO.setCustomizeHour(StringToolUtils.convertNullObjectToString(
                            htTaskBean.getCustomizeHour() == null ? "0" : htTaskBean.getCustomizeHour()));

                    // 工时原因
                    htTaskVO.setHourReason(StringToolUtils.convertNullObjectToString(htTaskBean.getHourReason()));


                    htTaskVOList.add(htTaskVO);

                }
            }

            // 集合
            retMap.put(Constant.DATA_LIST_KEY, htTaskVOList);
            // 当前时间
            retMap.put("currentDate", DateToolUtils.convertDateFormat(
                    getCurrentDate(), DateToolUtils.yyyy_MM_dd));

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retMap;
    }

    /**
     * 查询我的自定义工时
     *
     * @param paramVO
     * @throws SystemException 不可预估的异常
     */
    @Override
    public Map<String, Object> excQueryCustomTimeList(HTTaskVO paramVO) throws SystemException {
        Map<String, Object> retMap = new HashMap<>();
        Date createDate = new Date();
        try {
            TbUserCustomTimeExample example = new TbUserCustomTimeExample();
            TbUserCustomTimeExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(paramVO.getLoginUserInfo().getLoginUserId());
            criteria.andCreateTimeEqualTo(createDate);
            List<TbUserCustomTime> customTimes = userCustomTimeMapper.selectByExample(example);
            retMap.put(Constant.DATA_LIST_KEY, customTimes);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retMap;
    }

    /**
     * 保存自定义工时
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excSaveCustomTime(HTUserCustomTimeVO userCustomTime) throws SystemException {
        List<HTUserCustomTimeVO> userCustomTimeList = userCustomTime.getUserCustomTimes();
        Date createDate = new Date();
        try {
            TbUserCustomTimeExample example = new TbUserCustomTimeExample();
            TbUserCustomTimeExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(userCustomTime.getLoginUserInfo().getLoginUserId());
            criteria.andCreateTimeEqualTo(createDate);
            userCustomTimeMapper.deleteByExample(example);
            if (userCustomTimeList != null && !userCustomTimeList.isEmpty()) {
                for (HTUserCustomTimeVO userCustomTimeVO : userCustomTimeList) {
                    TbUserCustomTime tbUserCustomTime = (TbUserCustomTime) ObjectReflectUtils.convertObj(userCustomTimeVO, TbUserCustomTime.class);
                    tbUserCustomTime.setCustomTaskId(UUID.randomUUID().toString());
                    tbUserCustomTime.setUserId(userCustomTime.getLoginUserInfo().getLoginUserId());
                    tbUserCustomTime.setCreateTime(createDate);
                    userCustomTimeMapper.insert(tbUserCustomTime);
                }
            }
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询任务内容
     *
     * @return 任务内容
     */
    @Override
    @Transactional(readOnly = true)
    public HTTaskVO excSearchTaskContent(HTTaskVO paramVO) throws SystemException{

        HTTaskVO htTaskVO = new HTTaskVO();

        try {

            HTTaskBean paramBean = new HTTaskBean();

            // 设置用户ID
            paramBean.setUserId(StringToolUtils.convertNullObjectToString(
                    paramVO.getLoginUserInfo().getLoginUserId()));

            // 设置任务id
            paramBean.setTaskId(StringToolUtils
                    .convertNullObjectToString(paramVO.getTaskId()));

            // 查询任务内容
            HTTaskBean retBean = htTaskDao.excSearchTaskContent(paramBean);

            // 如果有数据
            if (null != retBean) {
                // 任务内容
                htTaskVO.setTaskContent(StringToolUtils.convertNullObjectToString(retBean.getTaskContent()));

                // 合同编号
                htTaskVO.setContractNum(StringToolUtils.convertNullObjectToString(
                        retBean.getProjectNum() + "-" + retBean.getContractNum()));

                // 任务名称
                htTaskVO.setTaskName(StringToolUtils.convertNullObjectToString(retBean.getTaskName()));

                // 创建时间
                htTaskVO.setCreateDate(DateToolUtils.convertDateFormat(
                        retBean.getCreateDate(), DateToolUtils.yyyy_MM_dd));


                String tmpCounselTypeNameStr = StringToolUtils.convertNullObjectToString(retBean.getCounselTypeName());

                // 当咨询类别大类可以选择时，将咨询大类和咨询小类拼接在一起显示
                if(!StringToolUtils.isNull(retBean.getContractTypeName())){
                    tmpCounselTypeNameStr = retBean.getContractTypeName() + "-" + retBean.getCounselTypeName();
                }

                // 咨询类别名称
                htTaskVO.setCounselTypeName(tmpCounselTypeNameStr);

                // 合同类型
                //htTaskVO.setContractTypeName(StringToolUtils.convertNullObjectToString());
            }

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htTaskVO;
    }

    /**
     * 修改任务信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdateTaskInfo(HTTaskVO paramVO) throws LogicException,SystemException {

        try {

            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();

            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(
                    paramVO.getLoginUserInfo().getLoginUserId());

            // 如果任务不为空，则循环遍历任务list，批量修改query/showBtnType
            if (!ListToolUtils.isEmpty(paramVO.getTaskList())) {

                for (HTTaskVO htTaskVO : paramVO.getTaskList()) {

                    // =====================================判断项目是否结束 start=========================================

                    TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(htTaskVO.getContractId());

                    // 判断项目是否结束
                    commonService.excIsOverProject(tbContractEntity.getProjectId());

                    // =====================================判断项目是否结束 end=========================================

                    // 获取任务id
                    String tempTaskIdStr = StringToolUtils
                            .convertNullObjectToString(htTaskVO.getTaskId());

                    TbTaskEntityExample tbTaskEntityExample = new TbTaskEntityExample();
                    TbTaskEntityExample.Criteria criteria = tbTaskEntityExample.createCriteria();
                    criteria.andTaskIdNotEqualTo(tempTaskIdStr);
                    criteria.andTaskNameEqualTo(htTaskVO.getTaskName());
                    List<TbTaskEntity> tbTaskEntityList = tbTaskEntityMapper.selectByExample(tbTaskEntityExample);
                    // 如果有数据，则抛出异常
                    if(!ListToolUtils.isEmpty(tbTaskEntityList)){
                        throw new LogicException(MessageConstant.MSG_ERROR_TASK_0011);
                    }

                    TbTaskEntity tbTaskEntity = new TbTaskEntity();

                    // 设置任务id
                    tbTaskEntity.setTaskId(tempTaskIdStr);
                    // 设置任务名称
                    tbTaskEntity.setTaskName(StringToolUtils.convertNullObjectToString(htTaskVO.getTaskName()));
                    // 设置所属高管
                    tbTaskEntity.setSeniorExecutive(StringToolUtils.convertNullObjectToString(htTaskVO.getSeniorExecutive()));
                    // 设置合同id
                    tbTaskEntity.setContractId(StringToolUtils.convertNullObjectToString(htTaskVO.getContractId()));
                    // 设置修改时间
                    tbTaskEntity.setUpdateDate(tempCurrentDate);
                    // 设置修改人id
                    tbTaskEntity.setUpdateUserId(tempLoginUserIdStr);

                    // 通过任务id查询任务详情
                    HTTaskBean htTaskBean = htTaskDao.excSearchTaskDetailInfoByTaskId(tempTaskIdStr);

                    // 主提审人id
                    String mainApplyUserId = "";

                    // 主提审人名称
                    String mainApplyUserName = "";

                    // 循环人员列表，查询主提审人id和名称
                    for (HTTaskVO userInfo : htTaskVO.getUserList()) {

                        // 如果是主提审人
                        if (StringToolUtils.STRING_ONE.equals(userInfo.getIsMainDuty())) {

                            // 主提审人id
                            mainApplyUserId = StringToolUtils
                                    .convertNullObjectToString(userInfo.getUserId());

                            // 查询主提审人名称
                            TbUserEntity tbUserEntity = tbUserEntityMapper.selectByPrimaryKey(userInfo.getUserId());

                            // 主提审人名称
                            mainApplyUserName = StringToolUtils
                                    .convertNullObjectToString(tbUserEntity.getUserName());
                            break;

                        }

                    }

                    // 设置主提审人id
                    htTaskBean.setApplyUserId(mainApplyUserId);

                    // 设置主提审人名称
                    htTaskBean.setApplyUserName(mainApplyUserName);

                    // 格式转换 Bean->VO
                    HTTaskVO taskVO = (HTTaskVO) ObjectReflectUtils.convertObj(htTaskBean, HTTaskVO.class);

                    // 招标代理部、造价部
                    if(Constant.DEPT_ID_ZJ.equals(htTaskBean.getDepartmentId()) ||
                            Constant.DEPT_ID_ZBDL.equals(htTaskBean.getDepartmentId())){

                        // 启动工作流
                        taskProcessService.excReassignment(taskVO);

                        // 项目监理部  todo 调用工作流
                    } else if(Constant.DEPT_ID_XMJL.equals(htTaskBean.getDepartmentId())){

                        // 通过任务id查询任务详情
                        HTTaskArchiveBean htTaskArchiveBean =
                                htTaskDao.excSearchTaskArchiveDetailInfoByTaskId(tempTaskIdStr);

                        // 设置主提审人id
                        htTaskArchiveBean.setApplyUserId(mainApplyUserId);

                        // 设置主提审人名称
                        htTaskArchiveBean.setApplyUserName(mainApplyUserName);

                        HTTaskArchiveVO taskArchiveVO = (HTTaskArchiveVO) ObjectReflectUtils.convertObj(htTaskArchiveBean, HTTaskArchiveVO.class);

                        taskArchiveProcessService.excReassignment(taskArchiveVO);

                    }

                    // 修改任务信息
                    tbTaskEntityMapper.updateByPrimaryKeySelective(tbTaskEntity);

                    // 删除用户每天实际完成工时记录信息
                    htTaskDao.excDeleteUserScheduleRecordInfo(tbTaskEntity);

                    // 删除任务和人员关联信息
                    htTaskDao.excDeleteTaskUserRelations(tbTaskEntity);

                    //========================= 添加任务和人员关联信息  start ========================

                    // 如果指定人员不为空，则批量插入任务和人员id关联信息，并修改A5数据
                    if (!ListToolUtils.isEmpty(htTaskVO.getUserList())) {
                        this.excInsertTaskUserRelations(htTaskVO.getUserList(), tempTaskIdStr, tempLoginUserIdStr);
                    }

                    //========================= 添加任务和人员关联信息  end ========================

                    // 清除任务执行人
                    commonCacheService.clearTaskUserCache(tempTaskIdStr);
                }

            }

        } catch (LogicException lex){
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 添加任务和人员关联信息
     * 如果是新建任务，则创建A5数据，如果是修改任务，则修改A5数据
     * @param htTaskVOList 任务集合信息
     * @param taskId 任务id
     * @param tempLoginUserIdStr 登录人id
     * @throws SystemException 系统异常
     */
    private void excInsertTaskUserRelations(List<HTTaskVO> htTaskVOList, String taskId, String tempLoginUserIdStr) throws SystemException {

        try {

            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();

            // 临时变量：普通组员名字的字符串
            StringBuilder tempUserNameBuilder = new StringBuilder();

            // 集合
            List<HTTaskBean> userBeanList = new ArrayList<>();

            // 循环添加用户-任务关联信息
            for (HTTaskVO userInfo : htTaskVOList) {

                HTTaskBean htTaskBean = new HTTaskBean();

                // 用户id
                String tempUserIdStr = StringToolUtils.convertNullObjectToString(userInfo.getUserId());

                // 设置关联id
                htTaskBean.setRelationId(StringToolUtils.convertNullObjectToString(UUID.randomUUID().toString()));
                // 设置任务id
                htTaskBean.setTaskId(taskId);
                // 设置用户id
                htTaskBean.setUserId(tempUserIdStr);
                // 设置是否是主负责人
                htTaskBean.setIsMainDuty(StringToolUtils.convertNullObjectToString(userInfo.getIsMainDuty()));
                // 设置任务概况
                htTaskBean.setTaskContent(StringToolUtils.convertNullObjectToString(userInfo.getTaskContent()));
                // 设置开始时间
                htTaskBean.setStartDate(tempCurrentDate);
                // 设置结束时间
                htTaskBean.setEndDate(DateToolUtils.convertDateFormat(userInfo.getEndDate(), DateToolUtils.yyyy_MM_dd));
                // 设置所需工时
                htTaskBean.setNeedHour(NumberToolUtils.convertNullToInteger(userInfo.getNeedHour()));
                // 是否删除
                htTaskBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
                // 设置创建时间
                htTaskBean.setCreateDate(tempCurrentDate);
                // 设置修改时间
                htTaskBean.setUpdateDate(tempCurrentDate);
                // 设置创建人id
                htTaskBean.setCreateUserId(tempLoginUserIdStr);
                // 设置修改人id
                htTaskBean.setUpdateUserId(tempLoginUserIdStr);

                userBeanList.add(htTaskBean);

                // 根据用户id查询用户信息
                TbUserEntity tbUserEntity = tbUserEntityMapper.selectByPrimaryKey(tempUserIdStr);
                // 如果用户信息不为空，则获取用户名称并拼接到一起
                if(null != tbUserEntity){
                    // 拼接用户名
                    tempUserNameBuilder.append(tbUserEntity.getUserName()).append(",");
                }

            }

            // 批量插入任务和人员id关联信息
            htTaskDao.excInsertTaskUserRelations(userBeanList);

            // 去掉拼接之后的用户名的最后一个，
            String tempUserNameStr = tempUserNameBuilder.substring(0, tempUserNameBuilder.length()-1);

            // 根据任务id,查询A5表中的数据集合
            TbTableFiveRelationEntityExample tbTableFiveRelationEntityExample = new TbTableFiveRelationEntityExample();
            TbTableFiveRelationEntityExample.Criteria criteria = tbTableFiveRelationEntityExample.createCriteria();
            criteria.andTaskIdEqualTo(taskId);
            List<TbTableFiveRelationEntity> tbTableFiveRelationEntityList = tbTableFiveRelationEntityMapper.selectByExample(tbTableFiveRelationEntityExample);

            TbTableFiveRelationEntity tbTableFiveRelationEntity = new TbTableFiveRelationEntity();

            // 任务id
            tbTableFiveRelationEntity.setTaskId(taskId);
            // 咨询业务小组分工（任务提审人+普通人的名称拼接）
            tbTableFiveRelationEntity.setDivideWork(tempUserNameStr);
            // 创建人
            tbTableFiveRelationEntity.setCreateUserId(tempLoginUserIdStr);
            // 创建时间
            tbTableFiveRelationEntity.setCreateDate(tempCurrentDate);
            // 修改人
            tbTableFiveRelationEntity.setUpdateUserId(tempLoginUserIdStr);
            // 修改时间
            tbTableFiveRelationEntity.setUpdateDate(tempCurrentDate);
            // 删除标识（0：未删除）
            tbTableFiveRelationEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);

            // 如果A5表中不存在该任务关联的数据，则创建一条，否则修改它的分工信息
            if(ListToolUtils.isEmpty(tbTableFiveRelationEntityList)){
                // 插入A5关联表数据
                tbTableFiveRelationEntityMapper.insertSelective(tbTableFiveRelationEntity);
            } else {
                // 关联id
                tbTableFiveRelationEntity.setRelationId(tbTableFiveRelationEntityList.get(0).getRelationId());
                // 修改A5关联表数据
                tbTableFiveRelationEntityMapper.updateByPrimaryKeySelective(tbTableFiveRelationEntity);
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 查询任务详情
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchTaskInfo(HTTaskVO paramVO) throws SystemException {

        Map<String, Object> retMap = new HashMap<>();
        try {

            HTTaskBean paramBean = new HTTaskBean();

            // 设置任务ID
            paramBean.setTaskId(StringToolUtils
                    .convertNullObjectToString(paramVO.getTaskId()));

            // =========================================== 任务详情 start================================================

            // 查询任务详情（不包括执行人信息）
            HTTaskBean retTaskInfo = htTaskDao.excSearchTaskInfo(paramBean);

            HTTaskVO retTaskVO = new HTTaskVO();

            // 如果查询到任务详情，则bean->vo
            if (null != retTaskInfo) {

                // 任务id
                retTaskVO.setTaskId(StringToolUtils.convertNullObjectToString(retTaskInfo.getTaskId()));
                // 任务名称
                retTaskVO.setTaskName(StringToolUtils.convertNullObjectToString(retTaskInfo.getTaskName()));
                // 合同类型id
                retTaskVO.setContractTypeId(StringToolUtils.convertNullObjectToString(retTaskInfo.getContractTypeId()));
                // 咨询类型id
                retTaskVO.setCounselTypeId(StringToolUtils.convertNullObjectToString(retTaskInfo.getCounselTypeId()));
                // 合同类型名称
                retTaskVO.setContractTypeName(StringToolUtils.convertNullObjectToString(retTaskInfo.getContractTypeName()));
                // 咨询类型名称
                retTaskVO.setCounselTypeName(StringToolUtils.convertNullObjectToString(retTaskInfo.getCounselTypeName()));
                // 用户id
                retTaskVO.setUserId(StringToolUtils.convertNullObjectToString(retTaskInfo.getUserId()));
                // 用户名称
                retTaskVO.setUserName(StringToolUtils.convertNullObjectToString(retTaskInfo.getUserName()));
                // 任务内容
                retTaskVO.setTaskContent(StringToolUtils.convertNullObjectToString(retTaskInfo.getTaskContent()));
                // 完成时间
                retTaskVO.setEndDate(DateToolUtils.convertDateFormat(retTaskInfo.getEndDate(), DateToolUtils.yyyy_MM_dd));
                // 所需工时
                retTaskVO.setNeedHour(StringToolUtils.convertNullObjectToString(retTaskInfo.getNeedHour()));
                // 所属高管
                retTaskVO.setSeniorExecutive(StringToolUtils.convertNullObjectToString(retTaskInfo.getSeniorExecutive()));
                // 合同id
                retTaskVO.setContractId(StringToolUtils.convertNullObjectToString(retTaskInfo.getContractId()));
                // 合同编号
                retTaskVO.setContractNum(StringToolUtils.convertNullObjectToString(
                                retTaskInfo.getProjectNum() + "-" + retTaskInfo.getContractNum()));

                if (!(Constant.IS_DRAFT.equals(retTaskInfo.getIsDraftFlg())
                        &&!paramVO.getLoginUserInfo().getLoginUserId().equals(retTaskInfo.getAddDraftUserId()))){
                    // 模板内容
                    retTaskVO.setFormworkContent(StringToolUtils
                            .convertNullObjectToString(retTaskInfo.getFormworkContent()));
                    // 任务文件类型
                    retTaskVO.setTaskType(StringToolUtils.
                            convertNullObjectToString(retTaskInfo.getTaskType()));
                    // 模板类型
                    retTaskVO.setFormworkType(StringToolUtils
                            .convertNullObjectToString(retTaskInfo.getFormworkType()));
                }else{
                    // 任务文件类型
                    retTaskVO.setTaskType(Constant.TASK_TYPE_ZERO);
                }

                // 项目名称
                retTaskVO.setProjectName(StringToolUtils.convertNullObjectToString(retTaskInfo.getProjectName()));

                HTTaskBean retBean = new HTTaskBean();

                // 设置用户ID
                retBean.setUserId(StringToolUtils.convertNullObjectToString(retTaskInfo.getUserId()));
                // 设置结束日期
                retBean.setEndDate(retTaskInfo.getEndDate());
                // 设置当前时间
                retBean.setCurrentDate(getCurrentDate());
                // 设置部门id
                retBean.setDepartmentId(retTaskInfo.getDepartmentId());

                // 查询用户信息
                List<HTTaskBean> htTaskBeanList = htTaskDao.excSearchUserInfo(retBean);

                List<HTTaskVO> htTaskVOList = new ArrayList<>();

                // 如果有数据，则把beanList->voList
                if (!ListToolUtils.isEmpty(htTaskBeanList)) {
                    // beanList->voList
                    htTaskVOList = getHtTaskVOList(htTaskBeanList);
                }

                // 执行人数据
                retTaskVO.setTableData(htTaskVOList);

                // =========================================== 执行人列表 start==============================================

                // 查询执行人信息
                List<HTTaskBean> retExecutorList = htTaskDao.excSearchExecutorListInfo(paramBean);

                List<HTTaskVO> executorList = new ArrayList<>();

                // 如果有数据，则遍历
                if (!ListToolUtils.isEmpty(retExecutorList)) {
                    for (HTTaskBean htTaskBean : retExecutorList) {

                        // 避免为空报错
                        if (null != htTaskBean) {
                            HTTaskVO htTaskVO = new HTTaskVO();

                            // 用户id
                            htTaskVO.setUserId(StringToolUtils
                                    .convertNullObjectToString(htTaskBean.getUserId()));

                            // 用户名称
                            htTaskVO.setUserName(StringToolUtils
                                    .convertNullObjectToString(htTaskBean.getUserName()));

                            // 任务内容
                            htTaskVO.setTaskContent(StringToolUtils
                                    .convertNullObjectToString(htTaskBean.getTaskContent()));

                            // 完成时间
                            htTaskVO.setEndDate(DateToolUtils.convertDateFormat(
                                    htTaskBean.getEndDate(), DateToolUtils.yyyy_MM_dd));

                            // 所需工时
                            htTaskVO.setNeedHour(StringToolUtils
                                    .convertNullObjectToString(htTaskBean.getNeedHour()));

                            // 不是成果文件人标识
                            htTaskVO.setIsMainDuty(StringToolUtils.STRING_ZERO);

                            HTTaskBean bean = new HTTaskBean();

                            // 设置用户ID
                            bean.setUserId(StringToolUtils.convertNullObjectToString(htTaskBean.getUserId()));

                            // 设置结束日期
                            bean.setEndDate(htTaskBean.getEndDate());

                            // 设置当前时间
                            bean.setCurrentDate(getCurrentDate());

                            // 设置部门id
                            retBean.setDepartmentId(retTaskInfo.getDepartmentId());

                            // 查询用户信息
                            List<HTTaskBean> beanList = htTaskDao.excSearchUserInfo(bean);

                            List<HTTaskVO> htVOList = new ArrayList<>();

                            // 如果有数据，则把beanList->voList
                            if (!ListToolUtils.isEmpty(beanList)) {
                                // beanList->voList
                                htVOList = getHtTaskVOList(beanList);
                            }

                            // 执行人数据
                            htTaskVO.setTableData(htVOList);

                            executorList.add(htTaskVO);
                        }

                    }
                }

                // 执行人列表
                retTaskVO.setSmallForm(executorList);

                // =========================================== 执行人列表 end ==============================================

            }

            // =========================================== 任务详情 end ================================================

            // 任务详情
            retMap.put(Constant.DATA_VO_KEY, retTaskVO);

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retMap;
    }

    /**
     * 删除任务信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excDeleteTaskInfo(HTTaskVO paramVO) throws LogicException,SystemException {

        try {

            // =====================================判断项目是否结束 start=========================================

            HTTaskBean htTaskBean = htTaskDao.excSearchTaskDetailInfoByTaskId(paramVO.getTaskId());

            // 判断项目是否结束
            commonService.excIsOverProject(htTaskBean.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            TbTaskEntity tbTaskEntity = new TbTaskEntity();

            // 设置任务id
            tbTaskEntity.setTaskId(StringToolUtils
                    .convertNullObjectToString(paramVO.getTaskId()));

            // 删除用户每天实际完成工时记录信息
            htTaskDao.excDeleteUserScheduleRecordInfo(tbTaskEntity);

            // 删除任务信息
            htTaskDao.excDeleteTaskInfo(tbTaskEntity);

            // 删除任务阶段关联信息
            htTaskDao.excDeleteTaskPeriodRelations(tbTaskEntity);

            // 删除任务和人员关联信息
            htTaskDao.excDeleteTaskUserRelations(tbTaskEntity);

            // 删除任务归档表数据信息
            htTaskDao.excDeleteTaskArchiveInfo(tbTaskEntity);

            //删除归档流程
            archiveProcessService.delArchive(tbTaskEntity.getTaskId());

            // 清除A3缓存
            commonCacheService.clearTaskPeriodCache(paramVO.getTaskId());

            // 清除成果文件缓存
            commonCacheService.clearTaskCache(paramVO.getTaskId());

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0009 删除失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0009);
        }
    }

    /**
     * 根据部门查人员列表
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTUserVO> excSearchUserListByDepartmentId(HTTaskVO paramVO) throws SystemException {

        List<HTUserVO> htUserVOList = new ArrayList<>();

        try {

            HTUserVO htUserVO = new HTUserVO();

            // 查询合同id
            TbTaskEntity tbTaskEntity = new TbTaskEntity();

            // 如果有合同id,则直接设置合同id,如果没有，则用任务id去任务表中查询合同id
            if (!StringToolUtils.isNull(paramVO.getContractId())) {
                // 设置合同id
                tbTaskEntity.setContractId(StringToolUtils.convertNullObjectToString(paramVO.getContractId()));
            } else {
                // 查询合同id
                tbTaskEntity = tbTaskEntityMapper.selectByPrimaryKey(paramVO.getTaskId());
            }

            // 查询部门id
            String tempDepartmentId = htTaskDao.excSearchDepartmentIdByContractId(tbTaskEntity);

            // 设置用户ID
            htUserVO.setDepartmentId(StringToolUtils.convertNullObjectToString(tempDepartmentId));

            htUserVOList = commonService.excSearchUserListByDepartmentId(htUserVO);

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htUserVOList;
    }

    /**
     * 根据合同类型ID查询咨询类别状态列表
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTDictCounselTypeVO> excSearchDictCounselTypeListByContractTypeId(HTTaskVO paramVO) throws SystemException {

        List<HTDictCounselTypeVO> htDictCounselTypeVOList = new ArrayList<>();
        try {

            htDictCounselTypeVOList = commonDictCounselTypeService
                    .findDictCounselTypeListByContractTypeId(paramVO.getContractTypeId());

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htDictCounselTypeVOList;
    }

    /**
     * 根据部门ID查询合同类型列表
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTDictContractTypeVO> excSearchDictContractTypeListByDepartmentId(HTTaskVO paramVO) throws SystemException {

        List<HTDictContractTypeVO> contractTypeVOList = new ArrayList<>();
        try {

            TbTaskEntity tbTaskEntity = new TbTaskEntity();

            // 设置合同ID
            tbTaskEntity.setContractId(StringToolUtils
                    .convertNullObjectToString(paramVO.getContractId()));

            // 查询部门id
            String tempDepartmentId = htTaskDao.excSearchDepartmentIdByContractId(tbTaskEntity);

            contractTypeVOList = commonDictContractTypeService
                    .findDictContractTypeListByDepartmienId(tempDepartmentId);

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return contractTypeVOList;
    }

    /**
     * 添加任务信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public String excInsertTaskInfo(HTTaskVO paramVO) throws LogicException,SystemException {

        String taskId = StringToolUtils.STRING_EMPTY;

        try {

            // =====================================判断项目是否结束 start=========================================

            TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(paramVO.getContractId());

            // 判断项目是否结束
            commonService.excIsOverProject(tbContractEntity.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();

            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(
                    paramVO.getLoginUserInfo().getLoginUserId());

            // 获取合同id
            String tempContractIdStr = StringToolUtils
                    .convertNullObjectToString(paramVO.getContractId());

            // 如果任务不为空，则循环遍历任务list，批量插入任务列表
            for (HTTaskVO htTaskVO : paramVO.getTaskList()) {

                // 所属高管
                String tempSeniorExecutive = StringToolUtils.convertNullObjectToString(htTaskVO.getSeniorExecutive());

                TbTaskEntityExample tbTaskEntityExample = new TbTaskEntityExample();
                TbTaskEntityExample.Criteria criteria = tbTaskEntityExample.createCriteria();
                criteria.andTaskNameEqualTo(htTaskVO.getTaskName());
                List<TbTaskEntity> tbTaskEntityList = tbTaskEntityMapper.selectByExample(tbTaskEntityExample);
                // 如果有数据，则抛出异常
                if(!ListToolUtils.isEmpty(tbTaskEntityList)){
                    throw new LogicException(MessageConstant.MSG_ERROR_TASK_0011);
                }

                TbTaskEntity tbTaskEntity = new TbTaskEntity();

                // 设置合同id
                tbTaskEntity.setContractId(tempContractIdStr);

                // 设置任务名称
                tbTaskEntity.setTaskName(StringToolUtils
                        .convertNullObjectToString(htTaskVO.getTaskName()));

                // 设置合同类型id
                tbTaskEntity.setContractTypeId(StringToolUtils
                        .convertNullObjectToString(htTaskVO.getContractTypeId()));

                // 设置咨询类型
                tbTaskEntity.setCounselTypeId(StringToolUtils
                        .convertNullObjectToString(htTaskVO.getCounselTypeId()));

                // 是否删除
                tbTaskEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

                // 设置创建时间
                tbTaskEntity.setCreateDate(tempCurrentDate);

                // 设置修改时间
                tbTaskEntity.setUpdateDate(tempCurrentDate);

                // 设置创建人id
                tbTaskEntity.setCreateUserId(tempLoginUserIdStr);

                // 设置修改人id
                tbTaskEntity.setUpdateUserId(tempLoginUserIdStr);

                // 主提审人id
                String mainApplyUserId = "";

                // 主提审人名称
                String mainApplyUserName = "";

                // 循环人员列表，查询主提审人id和名称
                for (HTTaskVO userInfo : htTaskVO.getUserList()) {

                    // 如果是主提审人
                    if (StringToolUtils.STRING_ONE.equals(userInfo.getIsMainDuty())) {

                        // 主提审人id
                        mainApplyUserId = StringToolUtils
                                .convertNullObjectToString(userInfo.getUserId());

                        // 查询主提审人名称
                        TbUserEntity tbUserEntity = tbUserEntityMapper.selectByPrimaryKey(userInfo.getUserId());

                        // 主提审人名称
                        mainApplyUserName = StringToolUtils
                                .convertNullObjectToString(tbUserEntity.getUserName());
                        break;

                    }

                }

                // 设置主提审人id
                tbTaskEntity.setApplyUserId(mainApplyUserId);

                // 设置主提审人名称
                tbTaskEntity.setApplyUserName(mainApplyUserName);

                // 设置所属高管
                tbTaskEntity.setSeniorExecutive(tempSeniorExecutive);

                // 添加任务信息
                tbTaskEntityMapper.insertSelective(tbTaskEntity);

                // 任务id
                taskId = tbTaskEntity.getTaskId();

                // 查询部门id
                String tempDepartmentIdStr = htTaskDao.excSearchDepartmentIdByContractId(tbTaskEntity);

                // 工作流中的值，由于if外要用到，所以提出来了
                String taskProcessInstId = "";

                // 招标代理部、造价部
                if(Constant.DEPT_ID_ZJ.equals(tempDepartmentIdStr) ||
                        Constant.DEPT_ID_ZBDL.equals(tempDepartmentIdStr)){

                    //启动工作流
                    HTTaskVO taskVO = (HTTaskVO) ObjectReflectUtils.convertObj(tbTaskEntity, HTTaskVO.class);
                    taskVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                    taskVO.setDepartmentId(tempDepartmentIdStr);
                    Map<String, Object> var = new HashMap<>();
                    var.put(WorkFlowConstant.KEY_TASK_VO, taskVO);
                    CommonActivitiListenVO listenVO = new CommonActivitiListenVO();
                    listenVO.setDepartmentId(taskVO.getDepartmentId());
                    var.put(WorkFlowConstant.KEY_LISTENER_VO, listenVO);
                    var.put(WorkFlowConstant.KEY_DELEGATE_USER, mainApplyUserId);
                    var.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
                    var.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
                    ProcessInstance processInstance = taskProcessService.excStart(var);
                    taskProcessInstId = processInstance.getProcessInstanceId();

                    // ======================================添加重新编辑记录 start=========================================

                    TbTaskReeditEntity tbTaskReeditEntity = new TbTaskReeditEntity();

                    // 设置任务id
                    tbTaskReeditEntity.setTaskId(tbTaskEntity.getTaskId());

                    // 设置任务流程id
                    tbTaskReeditEntity.setTaskProcessInstId(taskProcessInstId);

                    // 插入重新编辑记录
                    tbTaskReeditEntityMapper.insertSelective(tbTaskReeditEntity);

                    //启动工作流
                    HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) ObjectReflectUtils.convertObj(tbTaskReeditEntity, HTTaskReeditVO.class);
                    taskReeditVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                    taskReeditVO.setDepartmentId(tempDepartmentIdStr);
                    Map<String, Object> reeditVariable = new HashMap<>();
                    reeditVariable.put(WorkFlowConstant.KEY_TASK_REEDIT_VO, taskReeditVO);
                    CommonActivitiListenVO reeditListenerVO = new CommonActivitiListenVO();
                    reeditListenerVO.setDepartmentId(taskReeditVO.getDepartmentId());
                    reeditVariable.put(WorkFlowConstant.KEY_LISTENER_VO, reeditListenerVO);
                    reeditVariable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
                    reeditVariable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
                    taskReeditProcessService.excStart(reeditVariable);

                    // ======================================添加重新编辑记录 end========================================

                }else{
                    //启动工作流
                    TbTaskArchiveEntity tbTaskArchiveEntity = new TbTaskArchiveEntity();
                    tbTaskArchiveEntity.setTaskArchiveId(UUID.randomUUID().toString());
                    tbTaskArchiveEntity.setTaskId(tbTaskEntity.getTaskId());
                    tbTaskArchiveEntity.setDepartmentId(tempDepartmentIdStr);
                    tbTaskArchiveEntity.setCreateDate(tempCurrentDate);
                    tbTaskArchiveEntity.setUpdateDate(tempCurrentDate);
                    tbTaskArchiveEntity.setCreateUserId(tempLoginUserIdStr);
                    tbTaskArchiveEntity.setUpdateUserId(tempLoginUserIdStr);
                    archiveDao.insertSelective(tbTaskArchiveEntity);
                    HTTaskArchiveVO archiveVO = (HTTaskArchiveVO) ObjectReflectUtils.convertObj(tbTaskArchiveEntity, HTTaskArchiveVO.class);
                    archiveVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                    Map<String, Object> var = new HashMap<>();
                    var.put(WorkFlowConstant.KEY_ARCHIVE_VO, archiveVO);
                    CommonActivitiListenVO listenVO = new CommonActivitiListenVO();
                    listenVO.setDepartmentId(archiveVO.getDepartmentId());
                    var.put(WorkFlowConstant.KEY_LISTENER_VO, listenVO);
                    var.put(WorkFlowConstant.KEY_DELEGATE_USER, mainApplyUserId);
                    var.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
                    var.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
                    archiveProcessService.excStart(var);
                }

                // ======================================添加任务阶段信息 end=========================================

                HTDictCounselTypeBean htDictCounselTypeBean = new HTDictCounselTypeBean();// 咨询类别状态字典实体
                htDictCounselTypeBean.setDictDepartmentId(tempDepartmentIdStr);// 部门id
                htDictCounselTypeBean.setSeniorExecutive(tempSeniorExecutive);// 所属高管

                // 根据部门ID查询任务阶段信息
                List<HTDictCounselTypeVO> retTaskPeriodList = commonDictCounselTypeService.excSearchTaskPeriodListByDepartmentId(htDictCounselTypeBean);

                // 如果有阶段，则批量插入数据信息
                if (!ListToolUtils.isEmpty(retTaskPeriodList)) {
                    // 集合
                    for (HTDictCounselTypeVO htDictCounselTypeVO : retTaskPeriodList) {

                        HTTaskBean bean = new HTTaskBean();

                        String relationId = UUID.randomUUID().toString();

                        bean.setRelationId(relationId);

                        // 设置任务id
                        bean.setTaskId(tbTaskEntity.getTaskId());

                        // 设置阶段id
                        bean.setPeriodId(StringToolUtils.convertNullObjectToString(
                                htDictCounselTypeVO.getPeriodId()));

                        // 设置主提审人id
                        bean.setApplyUserId(mainApplyUserId);

                        // 设置主提审人名称
                        bean.setApplyUserName(mainApplyUserName);

                        // 设置是否为成果文件阶段
                        bean.setIsResultFile("0");
                        // 当该阶段为成果文件时，需要维护是否为成果文件阶段
                        if ("3".equals(htDictCounselTypeVO.getPeriodType())) {
                            bean.setIsResultFile("1");
                        }

                        // 是否删除
                        bean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

                        // 设置创建时间
                        bean.setCreateDate(tempCurrentDate);

                        // 设置修改时间
                        bean.setUpdateDate(tempCurrentDate);

                        // 设置创建人id
                        bean.setCreateUserId(tempLoginUserIdStr);

                        // 设置修改人id
                        bean.setUpdateUserId(tempLoginUserIdStr);

                        // 设置部门id
                        bean.setDepartmentId(htDictCounselTypeVO.getDepartmentId());

                        // bean->VO
                        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) ObjectReflectUtils.convertObj(bean, HTTaskPeriodVO.class);

                        taskPeriodVO.setTaskProcessInstId(taskProcessInstId);

                        // 添加任务阶段关联信息
                        htTaskDao.excInsertTaskPeriodRelations(bean);

                        // 招标代理部、造价部
                        if(Constant.DEPT_ID_ZJ.equals(tempDepartmentIdStr) ||
                                Constant.DEPT_ID_ZBDL.equals(tempDepartmentIdStr)){

                            //启动工作流
                            taskPeriodVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                            Map<String, Object> variables = new HashMap<>();
                            variables.put(WorkFlowConstant.KEY_TASK_PERIOD_VO, taskPeriodVO);
                            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
                            commonActivitiListenVO.setDepartmentId(taskPeriodVO.getDepartmentId());
                            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
                            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
                            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
                            taskA3ProcessService.excStart(variables);

                        }

                    }
                }

                // ======================================添加任务阶段信息 end=========================================

                //========================= 添加任务和人员关联信息  start ========================

                // 如果指定人员不为空，则批量插入任务和人员id关联信息，并创建A5数据
                if (!ListToolUtils.isEmpty(htTaskVO.getUserList())) {
                    this.excInsertTaskUserRelations(htTaskVO.getUserList(), tbTaskEntity.getTaskId(), tempLoginUserIdStr);
                }

                //========================= 添加任务和人员关联信息  end ========================

            }

        } catch (LogicException lex){
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }

        return taskId;

    }

    /**
     * 查询人员信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTTaskVO> excSearchUserInfo(HTTaskVO paramVO) throws SystemException {

        List<HTTaskVO> htTaskVOList = new ArrayList<>();

        try {

            HTTaskBean paramBean = new HTTaskBean();

            // 设置用户ID
            paramBean.setUserId(StringToolUtils.convertNullObjectToString(paramVO.getUserId()));

            // 如果结束时间不为空，则设置结束日期参数
            if(!StringToolUtils.isNull(paramVO.getEndDate())){

                // 设置结束日期
                paramBean.setEndDate(DateToolUtils
                        .convertDateFormat(paramVO.getEndDate(),DateToolUtils.yyyy_MM_dd));

            }

            // 设置部门id
            paramBean.setDepartmentId(StringToolUtils
                    .convertNullObjectToString(paramVO.getDepartmentId()));

            // 设置当前时间
            paramBean.setCurrentDate(getCurrentDate());

            // 查询用户信息
            List<HTTaskBean> htTaskBeanList = htTaskDao.excSearchUserInfo(paramBean);

            // 如果有数据，则把beanList->voList
            if (!ListToolUtils.isEmpty(htTaskBeanList)) {
                // beanList->voList
                htTaskVOList = getHtTaskVOList(htTaskBeanList);
            }

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htTaskVOList;
    }

    /**
     * 查询拟定工作计划信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTTableFiveRelationVO excSearchTaskWorkPlanInfo(HTTableFiveRelationVO paramVO) throws SystemException {

        HTTableFiveRelationVO workPlan = new HTTableFiveRelationVO();
        try {

            // 查询拟定工作计划信息
            HTTableFiveRelationBean htTableFiveRelationBean = htTaskDao.excSearchTaskWorkPlanInfo(paramVO);

            if(null != htTableFiveRelationBean){
                workPlan = (HTTableFiveRelationVO) ObjectReflectUtils.convertObj(htTableFiveRelationBean, HTTableFiveRelationVO.class);
                //项目负责人签字时间
                workPlan.setCompileDate(DateToolUtils.convertDateFormat(htTableFiveRelationBean.getCompileDate(), DateToolUtils.yyyy_MM_dd));
            }

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return workPlan;
    }

    /**
     * 添加拟定工作计划信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excInsertTaskWorkPlan(HTTableFiveRelationVO paramVO) throws SystemException {
        try {

            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();

            //主键ID
            String tempRelationIdStr = paramVO.getRelationId();

            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(
                    paramVO.getLoginUserInfo().getLoginUserId());

            TbTableFiveRelationEntity tbTableFiveRelationEntity = new TbTableFiveRelationEntity();

            //任务ID
            tbTableFiveRelationEntity.setTaskId(paramVO.getTaskId());

            //咨询业务简述
            tbTableFiveRelationEntity.setCounselDesc(paramVO.getCounselDesc());

            //建设规模
            tbTableFiveRelationEntity.setConstructScale(paramVO.getConstructScale());

            //咨询内容与重点
            tbTableFiveRelationEntity.setCounselContent(paramVO.getCounselContent());

            //组长
            tbTableFiveRelationEntity.setCounselGroup(paramVO.getCounselGroup());

            //专业负责人
            tbTableFiveRelationEntity.setCounselDuty(paramVO.getCounselDuty());

            //咨询业务小组分工
            tbTableFiveRelationEntity.setDivideWork(paramVO.getDivideWork());

            // 是否删除
            tbTableFiveRelationEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

            // 设置修改时间
            tbTableFiveRelationEntity.setUpdateDate(tempCurrentDate);

            // 设置修改人id
            tbTableFiveRelationEntity.setUpdateUserId(tempLoginUserIdStr);

            if (!StringToolUtils.isNull(tempRelationIdStr)) {

                //主键ID
                tbTableFiveRelationEntity.setRelationId(tempRelationIdStr);

                // 更新拟定工作计划信息
                tbTableFiveRelationEntityMapper.updateByPrimaryKeySelective(tbTableFiveRelationEntity);

            } else {

                // 设置创建时间
                tbTableFiveRelationEntity.setCreateDate(tempCurrentDate);

                // 设置创建人id
                tbTableFiveRelationEntity.setCreateUserId(tempLoginUserIdStr);

                // 添加拟定工作计划信息
                tbTableFiveRelationEntityMapper.insertSelective(tbTableFiveRelationEntity);
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询编制成果文件-A2信息
     *
     * @param paramVO 检索条件
     * @return SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTTableTwoRelationVO excSearchTaskCompileResultsFileA2(HTTableTwoRelationVO paramVO) throws SystemException {
        HTTableTwoRelationVO compileResultsFile;
        try {

            // 查询编制成果文件-A2信息
            HTTableTwoRelationBean htTableTwoRelationBean = htTaskDao.excSearchTaskCompileResultsFileA2(paramVO);

            compileResultsFile = (HTTableTwoRelationVO) ObjectReflectUtils.convertObj(htTableTwoRelationBean, HTTableTwoRelationVO.class);

            //合同编号
            compileResultsFile.setContractNum(StringToolUtils.convertNullObjectToString(
                    compileResultsFile.getProjectNum()+"-"+compileResultsFile.getContractNum()
            ));

            //项目类别
            compileResultsFile.setCounselBusinessType(
                    BusinessUtils.convertCounselBusinessTypeToStateStr(NumberToolUtils.convertNullToInteger(htTableTwoRelationBean.getCounselBusinessType())));

            //合同签订时间
            compileResultsFile.setContractDate(DateToolUtils.convertDateFormat(htTableTwoRelationBean.getContractDate(), DateToolUtils.yyyy_MM_dd));

            /*************** 修改计划时间 start modify by haoyd 2019.08.30 ***************/

            // 临时变量：计划开始时间
            String tempPlanStartDateStr = DateToolUtils.convertDateFormat(htTableTwoRelationBean.getPlanStartDate(), DateToolUtils.yyyy_MM_dd);
            // 临时变量：计划完成时间
            String tempPlanEndDateStr = DateToolUtils.convertDateFormat(htTableTwoRelationBean.getPlanEndDate(), DateToolUtils.yyyy_MM_dd);
            //计划开始时间
            compileResultsFile.setPlanStartDate(tempPlanStartDateStr);
            //计划完成时间
            compileResultsFile.setPlanEndDate(tempPlanEndDateStr);
            // 计划时间数组
            String[] tempPlanDateArr = {tempPlanStartDateStr, tempPlanEndDateStr};
            //计划时间数组
            compileResultsFile.setPlanDateArr(tempPlanDateArr);

            /*************** 修改计划时间 end modify by haoyd 2019.08.30 ***************/

            //实际开始时间
            compileResultsFile.setActualStartDate(DateToolUtils.convertDateFormat(htTableTwoRelationBean.getActualStartDate(), DateToolUtils.yyyy_MM_dd));

            //实际完成时间
            compileResultsFile.setActualEndDate(DateToolUtils.convertDateFormat(htTableTwoRelationBean.getActualEndDate(), DateToolUtils.yyyy_MM_dd));

            //项目负责人签字时间
            compileResultsFile.setApproveTime(DateToolUtils.convertDateFormat(htTableTwoRelationBean.getApproveTime(), DateToolUtils.yyyy_MM_dd));

            //提审时间
            compileResultsFile.setApplyTime(DateToolUtils.convertDateFormat(htTableTwoRelationBean.getApplyTime(), DateToolUtils.yyyy_MM_dd));

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return compileResultsFile;
    }

    /**
     * 添加拟定工作计划信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excInsertTaskCompileResultsFileA2(HTTableTwoRelationVO paramVO) throws SystemException {
        try {

            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();

            //主键ID
            String tempRelationIdStr = paramVO.getRelationId();

            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(
                    paramVO.getLoginUserInfo().getLoginUserId());

            TbTableTwoRelationEntity tbTableTwoRelationEntity = new TbTableTwoRelationEntity();

            //任务ID
            tbTableTwoRelationEntity.setTaskId(paramVO.getTaskId());

            //项目概况
            tbTableTwoRelationEntity.setProjectCondition(paramVO.getProjectCondition());

            //发包人
            tbTableTwoRelationEntity.setEmployer(paramVO.getEmployer());

            //项目中标人
            tbTableTwoRelationEntity.setBidPerson(paramVO.getBidPerson());

            /*************** 修改计划时间 start modify by haoyd 2019.08.30 ***************/

            // 临时变量：计划时间数组
            String[] tempPlanDateArr = paramVO.getPlanDateArr();
            // 临时变量：计划开始时间
            Date tempPlanStartDateDate = null;
            // 临时变量：计划完成时间
            Date tempPlanEndDateDate = null;

            // 如果计划时间不为空
            if (null != tempPlanDateArr && tempPlanDateArr.length > 1) {
                // 资料最后上传开始时间
                tempPlanStartDateDate = DateToolUtils.convertDateFormat(tempPlanDateArr[0], DateToolUtils.yyyy_MM_dd);
                // 资料最后上传结束时间
                tempPlanEndDateDate = DateToolUtils.convertDateFormat(tempPlanDateArr[1], DateToolUtils.yyyy_MM_dd);
                // 计划开始时间
                tbTableTwoRelationEntity.setPlanStartDate(tempPlanStartDateDate);
                // 计划完成时间
                tbTableTwoRelationEntity.setPlanEndDate(tempPlanEndDateDate);
            }

            /*************** 修改计划时间 end modify by haoyd 2019.08.30 ***************/

            //延迟原因
            tbTableTwoRelationEntity.setDelayReason(paramVO.getDelayReason());

            //咨询主要结论
            tbTableTwoRelationEntity.setMainResult(paramVO.getMainResult());

            // 是否删除
            tbTableTwoRelationEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

            // 设置修改时间
            tbTableTwoRelationEntity.setUpdateDate(tempCurrentDate);

            // 设置修改人id
            tbTableTwoRelationEntity.setUpdateUserId(tempLoginUserIdStr);

            if (!StringToolUtils.isNull(tempRelationIdStr)) {

                //主键ID
                tbTableTwoRelationEntity.setRelationId(tempRelationIdStr);

                // 更新拟定工作计划信息
                tbTableTwoRelationEntityMapper.updateByPrimaryKeySelective(tbTableTwoRelationEntity);

            } else {

                // 设置创建时间
                tbTableTwoRelationEntity.setCreateDate(tempCurrentDate);

                // 设置创建人id
                tbTableTwoRelationEntity.setCreateUserId(tempLoginUserIdStr);

                // 添加拟定工作计划信息
                tbTableTwoRelationEntityMapper.insertSelective(tbTableTwoRelationEntity);
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 添加开标、评分的信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excInsertOpenBidInfo(HTOpenBidVO paramVO) throws SystemException, LogicException {
        try {

            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();

            // 临时变量：当前登录用户id
            String tempLoginUserIdStr = StringToolUtils.convertNullObjectToString(
                    paramVO.getLoginUserInfo().getLoginUserId());

            TbOpenBidRelationEntity tbOpenBidRelationEntity = new TbOpenBidRelationEntity();

            TbOpenBidRelationEntityExample tbOpenBidRelationEntityExample = new TbOpenBidRelationEntityExample();
            TbOpenBidRelationEntityExample.Criteria criteria = tbOpenBidRelationEntityExample.createCriteria();
            criteria.andTaskIdEqualTo(paramVO.getTaskId());

            // 删除信息
            tbOpenBidRelationEntityMapper.deleteByExample(tbOpenBidRelationEntityExample);

            //任务ID
            tbOpenBidRelationEntity.setTaskId(paramVO.getTaskId());

            // 开标时间
            tbOpenBidRelationEntity.setBidTime(DateToolUtils.convertDateFormat(paramVO.getBidTime(),DateToolUtils.YYYY_MM_DD_HH_MM));

            // 评标时间
            tbOpenBidRelationEntity.setEvaluationTime(DateToolUtils.convertDateFormat(paramVO.getEvaluationTime(),DateToolUtils.YYYY_MM_DD_HH_MM));

            // 设置创建时间
            tbOpenBidRelationEntity.setCreateDate(tempCurrentDate);

            // 设置创建人id
            tbOpenBidRelationEntity.setCreateUserId(tempLoginUserIdStr);

            // 设置修改时间
            tbOpenBidRelationEntity.setUpdateDate(tempCurrentDate);

            // 设置修改人id
            tbOpenBidRelationEntity.setUpdateUserId(tempLoginUserIdStr);

            // 是否删除
            tbOpenBidRelationEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

            // 添加开标、评标信息
            tbOpenBidRelationEntityMapper.insertSelective(tbOpenBidRelationEntity);


        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询开标、评标信息详情
     * @param paramVO 检索条件
     * @return SystemException 不可预估的异常
     */
    @Override
    public HTOpenBidVO excSearchOpenBidInfoDetail(HTOpenBidVO paramVO) throws SystemException {

        HTOpenBidVO htOpenBidVO = new HTOpenBidVO();

        try {

            TbOpenBidRelationEntity tbOpenBidRelationEntity = new TbOpenBidRelationEntity();

            // 任务ID
            tbOpenBidRelationEntity.setTaskId(paramVO.getTaskId());

            // 查询开标、评标
            HTOpenBidBean returnBean =
                    htTaskDao.excSearchOpenBidInfoDetail(tbOpenBidRelationEntity);

            // 查询开标、评标信息详情，则bean->vo
            if (null != returnBean) {

                // 任务id
                htOpenBidVO.setTaskId(StringToolUtils
                        .convertNullObjectToString(returnBean.getTaskId()));

                // 如果有专家论证意见数据
                if(!StringToolUtils.isNull(returnBean.getBidId())){

                    // 开标时间
                    htOpenBidVO.setBidTime(DateToolUtils
                            .convertDateFormat(returnBean.getBidTime(),DateToolUtils.YYYY_MM_DD_HH_MM));

                    // 评标时间
                    htOpenBidVO.setEvaluationTime(DateToolUtils
                            .convertDateFormat(returnBean.getEvaluationTime(),DateToolUtils.YYYY_MM_DD_HH_MM));

                }

            }

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htOpenBidVO;
    }

    /**
     * 任务和人员集合转换 beanList->voList
     */
    private List<HTTaskVO> getHtTaskVOList(List<HTTaskBean> htTaskBeanList) throws SystemException {

        List<HTTaskVO> htTaskVOList = new ArrayList<>();

        for (HTTaskBean htTaskBean : htTaskBeanList) {

            HTTaskVO retTaskVO = new HTTaskVO();

            // 设置任务名称
            retTaskVO.setTaskName(StringToolUtils.convertNullObjectToString(
                    htTaskBean.getTaskName()));

            // 设置完成时间
            retTaskVO.setEndDate(DateToolUtils.convertDateFormat(
                    htTaskBean.getEndDate(), DateToolUtils.yyyy_MM_dd));

            // 设置所用工时
            retTaskVO.setNeedHour(StringToolUtils.convertNullObjectToString(
                    htTaskBean.getNeedHour()));

            htTaskVOList.add(retTaskVO);

        }

        return htTaskVOList;

    }

}
