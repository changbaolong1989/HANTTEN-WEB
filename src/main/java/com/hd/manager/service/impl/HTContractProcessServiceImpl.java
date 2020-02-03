package com.hd.manager.service.impl;

import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.base.vo.BaseVO;
import com.hd.common.dao.TbContractEntityMapper;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.entity.TbUserJobRelation;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.service.CommonCacheService;
import com.hd.common.util.Constant;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.service.HTContractProcessService;
import com.hd.manager.service.HTProjectProcessService;
import com.hd.manager.vo.HTContractVO;
import com.hd.manager.vo.HTProjectVO;
import com.hd.manager.vo.HTUserVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HTContractProcessServiceImpl extends BaseServiceImpl implements HTContractProcessService {

    //编撰合同枚举
    private static final ProcessDictEnum WRITE_CONTRACT_ENUM = ProcessDictEnum.WriteContract;

    //编撰合同流程Key
    private static final String WRITE_CONTRACT_PROCESS_KEY = WRITE_CONTRACT_ENUM.getProcessKey();

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private CommonCacheService commonCacheService;

    @Resource
    private TbContractEntityMapper contractDao;

    @Resource
    private HTProjectProcessService projectProcessService;

    @Resource
    private HistoryService historyService;

    /**
     * 启动工作流
     *
     * @param variables 流程变量
     * @return 流程实例对象
     * @throws SystemException 系统异常
     */
    @Override
    public ProcessInstance excStart(Map<String, Object> variables) throws SystemException {
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        String bizKey = contractVO.getContractId();//业务主键
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(WRITE_CONTRACT_PROCESS_KEY, bizKey, variables);//启动合同编撰流程
        //转换流程ID
        String processInstanceId = processInstance.getId();
        contractVO.setProcessInstId(processInstanceId);
        TbContractEntity contractEntity = (TbContractEntity) ObjectReflectUtils.convertObj(contractVO, TbContractEntity.class);
        contractDao.updateByPrimaryKey(contractEntity);
        logger.info("编撰合同流程已经启动。流程实例ID为：" + processInstance.getId());
        return processInstance;
    }

    /**
     * 办理任务
     *
     * @param taskId    任务ID
     * @param variables 流程变量
     * @throws SystemException 系统异常
     */
    public void excHandleTask(String userId, String taskId, Map<String, Object> variables) throws LogicException, SystemException {
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, WRITE_CONTRACT_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, taskId, variables);
        logger.info("合同编撰流程任务办理完成。任务ID为：" + taskId);
    }

    /**
     * 撤回任务
     *
     * @param vo VO
     * @throws SystemException 系统异常
     */
    @Override
    public void excRecallTask(Object vo) throws SystemException {
        if (vo instanceof HTContractVO) {
            HTContractVO contractVO = (HTContractVO) vo;
            String taskId = contractVO.getDelegateTaskId();
            Map<String, Object> variables = new HashMap<>();
            variables.put(WorkFlowConstant.KEY_CONTRACT_VO, contractVO);
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, Constant.NOT_APPROVED);
            variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, contractVO.getDelegateTaskKey());
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            commonActivitiListenVO.setDepartmentId(contractVO.getDepartmentId());
            commonActivitiListenVO.setIsRecall(Constant.IS_CAN_RECALL_ENABLE);
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, contractVO.getLoginUserInfo());
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            this.excHandleTask(((HTContractVO) vo).getLoginUserInfo().getLoginUserId(), variables);
            //删除历史
            historyService.deleteHistoricTaskInstance(taskId);
        } else {
            logger.error("类型异常");
            throw new SystemException();
        }
    }

    /**
     * 改派
     *
     * @param contractVO 合同VO
     * @throws SystemException 系统异常
     */
    @Override
    public void excReassignment(HTContractVO contractVO) throws SystemException {
        String contractId = contractVO.getContractId();
        TbContractEntity contract = contractDao.selectByPrimaryKey(contractId);
        String oldJob = contract.getJobId();
        String newJob = contractVO.getJobId();
        StringBuilder oldUserBuilder = new StringBuilder();
        StringBuilder newUserBuilder = new StringBuilder();
        if (oldJob.equals(newJob)) return;
        List<TbUserJobRelation> oldUsers = commonCacheService.getUserByJob(oldJob);
        List<TbUserJobRelation> newUsers = commonCacheService.getUserByJob(newJob);
        if (newUsers.isEmpty()) throw new SystemException();
        for (TbUserJobRelation userJobRelation : oldUsers) {
            taskService.deleteCandidateUser(contractVO.getDelegateTaskId(), userJobRelation.getUserId());
            oldUserBuilder.append(userJobRelation.getUserId()).append(",");
        }
        for (TbUserJobRelation userJobRelation : newUsers) {
            taskService.addCandidateUser(contractVO.getDelegateTaskId(), userJobRelation.getUserId());
            newUserBuilder.append(userJobRelation.getUserId()).append(",");
        }
        Map<String, Object> variables = taskService.getVariables(contractVO.getDelegateTaskId());
        CommonActivitiListenVO commonActivitiListen = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        commonActivitiListen.setDepartmentId(contractVO.getDepartmentId());
        taskService.setVariable(contractVO.getDelegateTaskId(), WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListen);
        taskService.setVariable(contractVO.getDelegateTaskId(), WorkFlowConstant.KEY_JOB_ID, newJob);
        taskService.setVariable(contractVO.getDelegateTaskId(), WorkFlowConstant.KEY_PRE_HANDLER, contractVO.getLoginUserInfo());
        taskService.setVariable(contractVO.getDelegateTaskId(), WorkFlowConstant.KEY_HANDLE_TIME, new Date());
        logger.info("任务改派完成。从用户:[ " + oldUserBuilder.toString().substring(0, oldUserBuilder.length() - 1) + " ] 改派到用户：[ " + newUserBuilder.toString().substring(0, newUserBuilder.length() - 1) + " ] ");
    }

    /**
     * 修改导入的数据的合同编撰人
     */
    @Override
    public void excReassignment(String contractId, String newUserId, String newUserName) throws SystemException {
        TbContractEntity contract = contractDao.selectByPrimaryKey(contractId);
        String oldUserName = contract.getApplyUserName();
        String taskId = contract.getDelegateTaskId();
        if (StringUtils.isBlank(newUserId) || StringUtils.isBlank(newUserName)) return;
        taskService.deleteCandidateUser(taskId, contract.getApplyUserId());
        taskService.addCandidateUser(taskId, newUserId);
        Map<String, Object> variables = taskService.getVariables(taskId);
        CommonActivitiListenVO commonActivitiListen = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        commonActivitiListen.setDepartmentId(contract.getDepartmentId());
        contract.setApplyUserId(newUserId);
        contract.setApplyUserName(newUserName);
        contractDao.updateByPrimaryKeySelective(contract);
        taskService.setVariable(taskId, WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListen);
        taskService.setVariable(taskId, WorkFlowConstant.KEY_JOB_ID, contract.getJobId());
        //taskService.setVariable(taskId, WorkFlowConstant.KEY_PRE_HANDLER, contractVO.getLoginUserInfo());
        taskService.setVariable(taskId, WorkFlowConstant.KEY_HANDLE_TIME, new Date());
        taskService.setVariable(taskId, WorkFlowConstant.KEY_DELEGATE_USER, newUserId);
        logger.info("任务改派完成。从用户:[ " + oldUserName + " ] 改派到用户：[ " + newUserName + " ] ");
    }

    /**
     * 添加合同
     *
     * @param projectVO 项目VO
     */
    @Override
    public void addContract(HTProjectVO projectVO, HTContractVO contractVO) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(WorkFlowConstant.KEY_PROJECT_VO, projectVO);
        variables.put(WorkFlowConstant.KEY_CONTRACT_VO, contractVO);
        CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
        commonActivitiListenVO.setDepartmentId(contractVO.getDepartmentId());
        variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
        variables.put(WorkFlowConstant.KEY_PRE_HANDLER, contractVO.getLoginUserInfo());
        variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
        projectProcessService.excStart(variables);
    }

    /**
     * 删除合同
     *
     * @param contractId 合同ID
     */
    public void delContract(String contractId) {
        TbContractEntity contract = contractDao.selectByPrimaryKey(contractId);
        String processInstId = contract.getProcessInstId();
        commonActivitiService.excDeleteProcess(processInstId);
    }

    /**
     * 办理任务
     *
     * @param variables 变量
     * @return 历史ID
     * @throws SystemException 系统异常
     */
    @Override
    public String excHandleTask(String userId, Map<String, Object> variables) throws LogicException, SystemException {
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, WRITE_CONTRACT_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, contractVO.getDelegateTaskId(), variables);
        logger.info("合同编撰流程任务办理完成。任务ID为：" + contractVO.getDelegateTaskId());
        return ((CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO)).getHistoryRecordId();
    }

    /**
     * 查询页面类型
     */
    @Override
    public String excQueryPageType(HTUserVO userVO, String bizDeptId, String processDictId, BaseVO vo, List<String> executorUsers) {
        return commonActivitiService.excQueryPageType(userVO, bizDeptId, processDictId, vo, executorUsers);
    }

}
