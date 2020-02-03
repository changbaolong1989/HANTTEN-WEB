package com.hd.manager.service.impl;

import com.hd.base.bean.BaseBean;
import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.base.vo.BaseVO;
import com.hd.common.dao.TbApplySealRecordEntityMapper;
import com.hd.common.entity.TbApplySealRecordEntity;
import com.hd.common.entity.TbApplySealRecordEntityExample;
import com.hd.common.entity.TbDictSealEntity;
import com.hd.common.entity.TbJobProcessNodeRelationEntity;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.service.CommonCacheService;
import com.hd.common.util.*;
import com.hd.manager.dao.HTSealApplyDao;
import com.hd.manager.service.HTSealApplyService;
import com.hd.manager.vo.HTSealApplyVO;
import com.hd.manager.vo.HTUserVO;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HTSealApplyServiceImpl extends BaseServiceImpl implements HTSealApplyService {

    /**
     * log对象
     */
    private static final Logger logger = LoggerFactory.getLogger(HTSealApplyServiceImpl.class);

    @Resource
    private TbApplySealRecordEntityMapper tbApplySealRecordMapper;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private IdentityService identityService;

    @Resource
    private HTSealApplyDao sealApplyDao;

    @Resource
    private CommonCacheService commonCacheService;

    //标记盖章
    private static final String IS_SIGNATURED = "1";

    //请章枚举
    private static final ProcessDictEnum sealApplyEnum = ProcessDictEnum.SealApply;

    //请章流程Key
    private static final String SEAL_APPLY_PROCESS_KEY = sealApplyEnum.getProcessKey();

    /**
     * 启动工作流
     *
     * @param variables 流程变量
     * @return 流程实例对象
     * @throws SystemException 系统异常
     */
    @Override
    @Transactional
    public ProcessInstance excStart(Map<String, Object> variables) throws LogicException, SystemException {
        ProcessInstance processInstance;
        try {
            //1.启动流程
            HTSealApplyVO sealApplyVO = (HTSealApplyVO) variables.get(WorkFlowConstant.KEY_SEAL_APPLY_VO);
            MultipartFile resourceFiles = (MultipartFile) variables.get(WorkFlowConstant.KEY_RESOURCE_FILES);//文件
            variables.remove(WorkFlowConstant.KEY_RESOURCE_FILES);
            String applyUserId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_APPLY_USER_ID));
            String bizKey = sealApplyVO.getRecordId();//业务主键
            identityService.setAuthenticatedUserId(applyUserId);//设置提交人
            processInstance = runtimeService.startProcessInstanceByKey(SEAL_APPLY_PROCESS_KEY, bizKey);//启动请章流程
            //2.提交请章申请
            String processInstanceId = processInstance.getId();//流程实例ID
            Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();//提交请章
            String taskId = task.getId();
            //3.保存业务数据
            sealApplyVO.setProcessInstId(processInstanceId);
            sealApplyVO.setApplyUserId(applyUserId);
            //4. 办理任务
            commonActivitiService.excCompleteTask(taskId, variables);
            // 如果附件不为空，则上传附件，附件名称和附件路径
            if (null != sealApplyVO.getAttachmentPath()) {
                // 上传文件
                boolean isUploadSignSuccess = FileUtils.uploadFile(sealApplyVO.getAttachmentPath(), resourceFiles);
                if (!isUploadSignSuccess) {
                    // MSG_ERROR_PUBLIC_NOTICE_0001 上传文件失败！
                    throw new LogicException(MessageConstant.MSG_ERROR_PUBLIC_NOTICE_0001);
                }
            }
            logger.info("请章申请提交成功。任务ID为：" + taskId);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
        return processInstance;
    }

    /**
     * 办理任务
     *
     * @param taskId    任务ID
     * @param variables 流程变量
     * @throws SystemException 系统异常
     */
    @Override
    @Transactional
    public void excHandleTask(String userId, String taskId, Map<String, Object> variables) throws LogicException, SystemException {
        // 办理任务
        try {
            commonActivitiService.excCompleteTask(userId, taskId, variables);
            logger.info("请章审批完成。任务ID为：" + taskId);
        } catch (LogicException lex) {
            throw lex;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 办理任务
     *
     * @param variables 变量
     * @return 历史ID
     * @throws SystemException 系统异常
     */
    @Override
    public String excHandleTask(String userId, Map<String, Object> variables) throws SystemException {
        return null;
    }

    /**
     * 查询页面类型
     *
     * @param userVO        用户VO
     * @param bizDeptId     业务线部门ID
     * @param processDictId 流程字典ID
     * @param vo            业务VO
     * @param executorUsers 执行人
     * @return 类型
     */
    @Override
    public String excQueryPageType(HTUserVO userVO, String bizDeptId, String processDictId, BaseVO vo, List<String> executorUsers) {
        return null;
    }

    /**
     * 查询已完成的历史任务
     *
     * @param userId        用户ID
     * @param processDefKey 流程定义Key
     * @return 历史任务实例
     * @throws SystemException 系统异常
     */
    @Override
    public List<HistoricTaskInstance> excQueryHisTask(String userId, String processDefKey) throws SystemException {
        return commonActivitiService.excQueryHisTask(userId, processDefKey);
    }

    @Override
    public boolean isOperated(HTSealApplyVO sealApplyVO) {
        TbApplySealRecordEntity applySealRecordEntity = tbApplySealRecordMapper.selectByPrimaryKey(sealApplyVO.getRecordId());
        if (applySealRecordEntity.getCompleteTime() != null) return true;
        return !applySealRecordEntity.getDelegateTaskId().equals(sealApplyVO.getDelegateTaskId());
    }

    /**
     * 查询我的请章记录
     *
     * @param sealApplyVO 请章
     * @param userId      用户
     * @param example     条件
     * @return 查询结果
     * @throws SystemException 系统异常
     */
    @Transactional(readOnly = true)
    public Map<String, Object> querySealApplyRecords(HTSealApplyVO sealApplyVO, String userId, TbApplySealRecordEntityExample example) throws SystemException {
        // 结果Map
        Map<String, Object> resultPageMap = new HashMap<>();
        try {
            // 分页
            BaseBean paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(sealApplyVO, new BaseBean());
            example.setLimitStart(paramBean.getPageNumber());
            example.setLimitEnd(paramBean.getPageSize());
            TbApplySealRecordEntityExample.Criteria criteria = example.createCriteria();
            criteria.andApplyUserIdEqualTo(userId);
            //查询我的请章记录
            List<TbApplySealRecordEntity> list = tbApplySealRecordMapper.selectByExample(example);
            // entity 转化 VO
            List<HTSealApplyVO> retUserList = new ArrayList<>();
            for (TbApplySealRecordEntity tmpBean : list) {
                HTSealApplyVO tmpVO = (HTSealApplyVO) ObjectReflectUtils.convertObj(tmpBean, HTSealApplyVO.class);
                tmpVO.setSealTypeText(getSealTypeText(tmpBean.getSealTypeId()));
                retUserList.add(tmpVO);
            }
            // 查询个数
            long count = tbApplySealRecordMapper.countByExample(example);
            resultPageMap.put(Constant.DATA_LIST_KEY, retUserList);
            resultPageMap.put(Constant.DATA_COUNT_KEY, count);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(e, "查询我的请章记录出现系统异常");
        }
        return resultPageMap;
    }


    /**
     * 查询所有已通过的请章
     *
     * @param example 查询条件
     */
    @Override
    public Map<String, Object> queryAlreadyPassRecords(HTSealApplyVO sealApplyVO, TbApplySealRecordEntityExample example) throws SystemException {
        // 结果Map
        Map<String, Object> resultPageMap = new HashMap<>();
        try {
            // 分页
            BaseBean paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(sealApplyVO, new BaseBean());
            example.setLimitStart(paramBean.getPageNumber());
            example.setLimitEnd(paramBean.getPageSize());
            //查询所有已通过的请章
            TbApplySealRecordEntityExample.Criteria criteria = example.createCriteria();
            criteria.andVerifyStateEqualTo(Constant.APPROVED);
            List<TbApplySealRecordEntity> list = tbApplySealRecordMapper.selectByExample(example);
            // entity 转化 VO
            List<HTSealApplyVO> retUserList = new ArrayList<>();
            for (TbApplySealRecordEntity tmpBean : list) {
                HTSealApplyVO tmpVO = (HTSealApplyVO) ObjectReflectUtils.convertObj(tmpBean, HTSealApplyVO.class);
                tmpVO.setSealTypeText(getSealTypeText(tmpBean.getSealTypeId()));
                retUserList.add(tmpVO);
            }
            // 查询个数
            long count = tbApplySealRecordMapper.countByExample(example);
            resultPageMap.put(Constant.DATA_LIST_KEY, retUserList);
            resultPageMap.put(Constant.DATA_COUNT_KEY, count);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(e, "查询查询所有已通过的请章系统异常");
        }
        return resultPageMap;

    }

    /**
     * 根据业务键查询业务数据
     *
     * @param bussinessKeys 业务键
     * @return 请章记录数据
     */
    @Override
    public List<TbApplySealRecordEntity> queryBizData(List<String> bussinessKeys) {
        List<TbApplySealRecordEntity> list = new LinkedList<>();
        if (!bussinessKeys.isEmpty()) {
            TbApplySealRecordEntityExample example = new TbApplySealRecordEntityExample();
            example.setOrderByClause(" apply_time desc ");
            try {
                TbApplySealRecordEntityExample.Criteria criteria = example.createCriteria();
                criteria.andRecordIdIn(bussinessKeys);
                list = tbApplySealRecordMapper.selectByExample(example);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 标记盖章
     *
     * @param sealApplyVO 请章VO
     * @throws SystemException 系统异常
     */
    @Override
    public void signSeal(HTSealApplyVO sealApplyVO) throws SystemException {
        TbApplySealRecordEntity applySealRecordEntity = (TbApplySealRecordEntity) ObjectReflectUtils.convertObj(sealApplyVO, TbApplySealRecordEntity.class);
        applySealRecordEntity.setIsSignatured(IS_SIGNATURED);
        tbApplySealRecordMapper.updateByPrimaryKeySelective(applySealRecordEntity);
    }

    /**
     * 查询指定办理人
     *
     * @param nodeId 节点ID
     * @return 结果集
     */
    @Override
    public List<Map<String, Object>> queryAssignUsers(String nodeId, String processDictId, String departmentId) {
        return sealApplyDao.queryAssignUsers(nodeId, processDictId, departmentId);
    }

    /**
     * 请章模块查询权限
     *
     * @param userVO 当前用户
     * @return 权限
     */
    @Override
    public Map<String, Object> queryAuthority(HTUserVO userVO) {
        Map<String, Object> authorithMap = new HashMap<>();
        List<TbJobProcessNodeRelationEntity> list = commonCacheService.getProcessNodeCache().get(sealApplyEnum.getProcessDictId());
        Set<String> userJobs = userVO.getJobIds();
        List<String> nodeJobIds = list.stream().map(TbJobProcessNodeRelationEntity::getJobId).collect(Collectors.toList());
        //判断登陆用户职位中是否包含已配置的节点职位
        userJobs.retainAll(nodeJobIds);
        //设置是否有审批权限
        String isApproveRole = userJobs.isEmpty() ? "0" : "1";
        authorithMap.put("isApproveRole", isApproveRole);
        //设置是否有标记盖章权限
        List<String> userDeptIds = userVO.getDepartmentIds();
        String isSignSealRole = userDeptIds.contains(Constant.DEPT_ID_XZ) ? "1" : "0";
        authorithMap.put("isSignSealRole", isSignSealRole);
        return authorithMap;
    }

    /***
     * Bean转VO
     */
    public List<HTSealApplyVO> beanToVo(List<TbApplySealRecordEntity> list, String isHistory) throws SystemException {
        List<HTSealApplyVO> retUserList = new ArrayList<>();
        HTSealApplyVO tmpVO;
        // Bean 转化 VO
        for (TbApplySealRecordEntity tmpBean : list) {
            tmpVO = (HTSealApplyVO) ObjectReflectUtils.convertObj(tmpBean, HTSealApplyVO.class);
            tmpVO.setSealTypeText(getSealTypeText(tmpBean.getSealTypeId()));
            tmpVO.setIsHistory(isHistory);// 设置历史状态
            if (StringUtils.isNotBlank(tmpBean.getRejectReason())) {
                tmpVO.setIsViewReason("1");// 设置显示拒绝原因
            }
            retUserList.add(tmpVO);
        }
        return retUserList;
    }

    /**
     * 获取章类型Txt
     */
    private String getSealTypeText(String sealTypeIds) {
        String sealTypeText;
        StringBuilder sealTypeBuilder = new StringBuilder();
        Map<String, TbDictSealEntity> sealTypeCache = commonCacheService.getSealTypeCacheMap();
        String[] sealTypeArray = sealTypeIds.split(",");
        for (String sealTypeId : sealTypeArray) {
            String text = sealTypeCache.get(sealTypeId).getSealTypeText();
            sealTypeBuilder.append(text).append(",");
        }
        if (sealTypeBuilder.length() > 0) {
            sealTypeText = sealTypeBuilder.substring(0, sealTypeBuilder.length() - 1);
        } else {
            sealTypeText = sealTypeBuilder.toString();
        }
        return sealTypeText;
    }

}
