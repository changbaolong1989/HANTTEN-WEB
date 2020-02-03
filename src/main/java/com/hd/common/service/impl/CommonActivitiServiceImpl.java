package com.hd.common.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hd.base.enums.*;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.base.vo.BaseVO;
import com.hd.common.dao.TbProjectProcessRelationEntityMapper;
import com.hd.common.entity.TbJobProcessNodeRelationEntity;
import com.hd.common.entity.TbProjectProcessRelationEntity;
import com.hd.common.entity.TbUserJobRelation;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.service.CommonCacheService;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.dao.HTWorkFlowCustomSQLDao;
import com.hd.manager.dao.bean.HTWorkBean;
import com.hd.manager.vo.HTUserVO;
import com.hd.processes.bench.WorkBench;
import com.hd.processes.bench.WorkFlow;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

@Service
public class CommonActivitiServiceImpl extends BaseServiceImpl implements CommonActivitiService {

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private HTWorkFlowCustomSQLDao workFlowCustomSQLDao;

    @Resource
    private HistoryService historyService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private CommonCacheService commonCacheService;

    @Resource
    private ProcessEngine processEngine;

    @Resource
    private TbProjectProcessRelationEntityMapper projectProcessDao;

    //是否是撤回状态
    private static final String IS_RECALL = "1";

    //是否是关闭状态
    private static final String IS_CLOSED = "1";

    //是否是作废状态
    private static final String IS_CANCEL = "1";

    //撤回状态码
    private static final String STATE_RECALL = "20";

    //执行人提审状态码
    private static final String STATE_EXEUSER_SUBMIT = "15";

    //可以提交重新编辑申请Flag
    private static final String CAN_REEDIT_FLAG = "Reedit_Flag";

    //提审节点
    private static final String SUBMIT_NODES = "SubmitVerify,SubmitArchive";
    //审批节点
    private static final String VERIFY_NODES = "ManagerVerify,QualityVerify,GenerManagerVerify,Signer,Seal,ArchiveVerify,Archive,AffirmArchive,FinanceInvoice,IssueInvoice,CheckCash";

    private static final String EXCLUDE_DEPTIDS = Constant.DEPT_ID_XZ + "," + Constant.DEPT_ID_CW + "," + Constant.DEPT_ID_QUALITY;

    private JSONObject processNodeInfo = new JSONObject();

    @Override
    public void getFlowDiagram(HttpServletResponse response, String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefId;
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefId = historicProcessInstance.getProcessDefinitionId();
        } else {
            processDefId = processInstance.getProcessDefinitionId();
        }
        BpmnModel model = repositoryService.getBpmnModel(processDefId);
        List<String> currentNode = new ArrayList<>();
        if (processInstance != null) {
            currentNode = runtimeService.getActiveActivityIds(processInstance.getId());
        }
        String fontName = "黑体";
        InputStream inputStream = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator().generateDiagram(model, "jpg", currentNode, new ArrayList<>(),
                fontName, fontName, fontName, null, 2.0);
        try (OutputStream outputStream = response.getOutputStream()) {
            byte[] bytes = new byte[1024];
            int tmp;
            while ((tmp = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, tmp);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过TaskId获取bussinessKey
     *
     * @param taskId 任务ID
     * @return bussinessKey
     */
    @Override
    public String getBizKeyByTaskId(String taskId) {
        //获取任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return this.getBizKeyByProInstId(task.getProcessInstanceId());
    }

    /**
     * 通过Task获取bussinessKey
     *
     * @param tasks 任务
     * @return bussinessKey
     */
    @Override
    public List<String> getBizKeyByTask(List<? extends Task> tasks) {
        List<String> result = new ArrayList<>();
        for (Task task : tasks) {
            result.add(this.getBizKeyByProInstId(task.getProcessInstanceId()));
        }
        return result;
    }

    /**
     * 通过hisTasks获取bussinessKey
     *
     * @param hisTasks 历史任务
     * @return bussinessKey
     */
    @Override
    public List<String> getBizKeyByHisTasks(List<HistoricTaskInstance> hisTasks) {
        List<String> result = new ArrayList<>();
        for (HistoricTaskInstance task : hisTasks) {
            result.add(this.getBizKeyByHisProInstId(task.getProcessInstanceId()));
        }
        return result;
    }


    /**
     * 通过流程实例ID获取bussinessKey
     *
     * @param proInstId 流程实例ID
     * @return bussinessKey
     */
    @Override
    public String getBizKeyByProInstId(String proInstId) {
        //通过任务对象获取流程实例
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(proInstId).singleResult();
        //获取bussinessKey
        return pi.getBusinessKey();
    }

    /**
     * 通过流程实例ID获取bussinessKey
     *
     * @param proInstId 流程实例ID
     * @return bussinessKey
     */
    @Override
    public String getBizKeyByHisProInstId(String proInstId) {
        HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processInstanceId(proInstId).singleResult();
        //获取bussinessKey
        return pi.getBusinessKey();
    }

    /**
     * 部署
     *
     * @param fileName   文件名
     * @param deployName 部署名
     */
    @Override
    public void excDeploy(String fileName, String deployName) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        Deployment deployment = repositoryService.createDeployment().name(deployName).addZipInputStream(zipInputStream).deploy();
        String deploymentId = deployment.getId();
        //查询当前部署的最高版本
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).orderByProcessDefinitionVersion().desc().list().get(0);
        logger.info(deployment.getName() + "部署成功！当前部署ID为：" + deploymentId + "。当前版本号为：" + processDefinition.getVersion() + "。");
    }

    /**
     * 办理任务，带监听器对象，根据状态回滚
     *
     * @param taskId    任务ID
     * @param variables 变量，监听器对象
     */
    @Override
    public void excCompleteTask(String taskId, Map<String, Object> variables) throws LogicException, SystemException {
        CommonActivitiListenVO lisVo = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        //先认领任务，再办理任务，可以解决已办数据异常问题
        //taskService.claim(taskId, userId);
        taskService.complete(taskId, variables);
        if (lisVo.isRollBack() && MessageConstant.MSG_INFO_PROCESS_NO_USER.equals(lisVo.getExceptionMsg())) {
            throw new LogicException(MessageConstant.MSG_INFO_PROCESS_NO_USER);
        }
        if (lisVo.isRollBack()) {
            throw new SystemException();
        }
    }

    /**
     * 办理任务，带监听器对象，根据状态回滚
     *
     * @param taskId    任务ID
     * @param variables 变量，监听器对象
     */
    @Override
    public void excCompleteTask(String userId, String taskId, Map<String, Object> variables) throws LogicException, SystemException {
        try {
            synchronized (this) {
                CommonActivitiListenVO lisVo = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
                //先认领任务，再办理任务，可以解决已办数据异常问题
                if (StringUtils.isNotBlank(userId)) {
                    taskService.claim(taskId, userId);
                }
                taskService.complete(taskId, variables);
                if (lisVo.isRollBack() && MessageConstant.MSG_INFO_PROCESS_NO_USER.equals(lisVo.getExceptionMsg())) {
                    throw new LogicException(MessageConstant.MSG_INFO_PROCESS_NO_USER);
                }
                if (lisVo.isRollBack()) {
                    throw new SystemException();
                }
            }
        }catch (ActivitiOptimisticLockingException e){
            e.printStackTrace();
            throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
        }
    }

    /**
     * 查询待办
     *
     * @param userId 用户ID
     * @return 待办任务
     * @throws SystemException 系统异常
     */
    @Override
    public List<Task> excQueryTask(String userId) throws SystemException {
        return taskService.createTaskQuery().taskCandidateOrAssigned(userId).active().list();
    }

    /**
     * 查询待办
     *
     * @param userId   用户ID
     * @param nodeList 节点集合
     * @return 待办任务
     * @throws SystemException 系统异常
     */
    @Override
    public List<Task> excQueryTask(String userId, List<TbJobProcessNodeRelationEntity> nodeList) throws SystemException {
        if (nodeList == null || nodeList.isEmpty()) return null;
        List<Task> tasks = this.excQueryTask(userId);
        List<Task> taskList = new LinkedList<>();
        List<String> taskKeys = nodeList.stream().map(TbJobProcessNodeRelationEntity::getNodeId).collect(Collectors.toList());
        for (Task task : tasks) {
            if (taskKeys.contains(task.getTaskDefinitionKey())) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    /**
     * 查询待办
     *
     * @param userId        用户ID
     * @param processDefKey 流程定义Key
     * @param nodeList      节点集合
     * @return 待办任务
     * @throws SystemException 系统异常
     */
    @Override
    public List<Task> excQueryTask(String userId, String processDefKey, List<TbJobProcessNodeRelationEntity> nodeList) throws SystemException {
        if (nodeList == null || nodeList.isEmpty()) return null;
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefKey).list();
        if (processInstances == null || processInstances.isEmpty()) return null;
        List<Task> tasks = this.excQueryTask(userId);
        List<Task> taskList = new LinkedList<>();
        List<String> taskKeys = nodeList.stream().map(TbJobProcessNodeRelationEntity::getNodeId).collect(Collectors.toList());
        List<String> processInstanceIds = processInstances.stream().map(ProcessInstance::getId).collect(Collectors.toList());
        for (Task task : tasks) {
            if (processInstanceIds.contains(task.getProcessInstanceId()) && taskKeys.contains(task.getTaskDefinitionKey())) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    /**
     * 查询待办
     *
     * @param userId       用户ID
     * @param departmentId 部门ID
     * @return 当前流程/部门下的待办
     * @throws SystemException 系统异常
     */
    @Override
    public Map<String, Set<String>> excQueryTask(String userId, String departmentId) throws SystemException {
        Map<String, Set<String>> result = new HashMap<>();
        List<Task> tasks = this.excQueryTask(userId);
        if (tasks == null || tasks.isEmpty()) return result;
        List<HistoricProcessInstance> processInstanceList = historyService.createHistoricProcessInstanceQuery().list();
        if (processInstanceList == null || processInstanceList.isEmpty()) return result;
        Map<String, HistoricProcessInstance> processInstanceMap = processInstanceList.stream().collect(Collectors.toMap(HistoricProcessInstance::getId, a -> a));
        for (Task task : tasks) {
            String taskId = task.getId();
            Map<String, Object> variables = taskService.getVariables(taskId);
            CommonActivitiListenVO commonActivitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
            if (departmentId.equals(commonActivitiListenVO.getDepartmentId())) {
                try {
                    HistoricProcessInstance processInstance = processInstanceMap.get(task.getProcessInstanceId());
                    String processDefKey = processInstance.getProcessDefinitionKey();
                    WorkBench workBench = ProcessDictEnum.getInstance(processDefKey);
                    if (workBench != null) {
                        workBench.processTodo(processInstance, task, result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public List<WorkFlow> hasTodoTask(String userId) throws SystemException {
        List<WorkFlow> result = new ArrayList<>();
        List<Map<String, Object>> tasks = workFlowCustomSQLDao.getTask(userId);
        if (tasks == null || tasks.isEmpty()) return result;
        for (Map<String, Object> task : tasks) {
            String taskId = task.get("TASK_ID_").toString();
            Map<String, Object> variables = taskService.getVariables(taskId);
            CommonActivitiListenVO commonActivitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
            try {
                String departmentId = commonActivitiListenVO.getDepartmentId();
                String processDefId = task.get("PROC_DEF_ID_").toString();
                String processDefKey = processDefId.substring(0, processDefId.indexOf(":"));
                String bizKey = task.get("BUSINESS_KEY_").toString();
                WorkFlow workFlow = new WorkFlow();
                WorkBench workBench = ProcessDictEnum.getInstance(processDefKey);
                String taskDefKey = task.get("TASK_DEF_KEY_").toString();
                if (workBench != null) {
                    workBench.processTodo(taskId, taskDefKey, bizKey, workFlow);
                }
                workFlow.setProcessDefinitionKey(processDefKey);
                workFlow.setDepartmentId(departmentId);
                result.add(workFlow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询待办V2.0版
     */
    @Override
    public List<WorkFlow> excQueryTodo(String userId, String departmentId, List<HTWorkBean> exeWorks) throws SystemException {
        List<WorkFlow> result = new ArrayList<>();
        //构造执行人数据
        if (exeWorks != null && !exeWorks.isEmpty()) {
            for (HTWorkBean work : exeWorks) {
                String taskId = work.getDelegateTaskId();
                Map<String, Object> variables = taskService.getVariables(taskId);
                CommonActivitiListenVO commonActivitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
                if (departmentId.equals(commonActivitiListenVO.getDepartmentId())) {
                    try {
                        String processDefKey = work.getBusinessType();
                        String bizKey = work.getBusinessId();
                        WorkFlow workFlow = new WorkFlow();
                        WorkBench workBench = ProcessDictEnum.getInstance(processDefKey);
                        String taskDefKey = work.getDelegateTaskKey();
                        if (workBench != null) {
                            workBench.processTodo(taskId, taskDefKey, bizKey, workFlow);
                        }
                        workFlow.setProcessDefinitionKey(processDefKey);
                        workFlow.setPreNodeHandlerId(variables.get(WorkFlowConstant.KEY_PRE_HANDLER) == null ? "" : ((HTUserVO) variables.get(WorkFlowConstant.KEY_PRE_HANDLER)).getLoginUserId());
                        workFlow.setPreNodeHandlerName(variables.get(WorkFlowConstant.KEY_PRE_HANDLER) == null ? "" : ((HTUserVO) variables.get(WorkFlowConstant.KEY_PRE_HANDLER)).getUserName());
                        workFlow.setHandleDate(variables.get(WorkFlowConstant.KEY_HANDLE_TIME) == null ? null : (Date) variables.get(WorkFlowConstant.KEY_HANDLE_TIME));
                        result.add(workFlow);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //性能优化
        List<Map<String, Object>> tasks = workFlowCustomSQLDao.getTask(userId);
        if (tasks == null || tasks.isEmpty()) return result;
        for (Map<String, Object> task : tasks) {
            String taskId = task.get("TASK_ID_").toString();
            Map<String, Object> variables = taskService.getVariables(taskId);
            CommonActivitiListenVO commonActivitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
            if (departmentId.equals(commonActivitiListenVO.getDepartmentId())) {
                try {
                    String processDefId = task.get("PROC_DEF_ID_").toString();
                    String processDefKey = processDefId.substring(0, processDefId.indexOf(":"));
                    String bizKey = task.get("BUSINESS_KEY_").toString();
                    WorkFlow workFlow = new WorkFlow();
                    WorkBench workBench = ProcessDictEnum.getInstance(processDefKey);
                    String taskDefKey = task.get("TASK_DEF_KEY_").toString();
                    if (workBench != null) {
                        workBench.processTodo(taskId, taskDefKey, bizKey, workFlow);
                    }
                    workFlow.setProcessDefinitionKey(processDefKey);
                    workFlow.setPreNodeHandlerId(variables.get(WorkFlowConstant.KEY_PRE_HANDLER) == null ? "" : ((HTUserVO) variables.get(WorkFlowConstant.KEY_PRE_HANDLER)).getLoginUserId());
                    workFlow.setPreNodeHandlerName(variables.get(WorkFlowConstant.KEY_PRE_HANDLER) == null ? "" : ((HTUserVO) variables.get(WorkFlowConstant.KEY_PRE_HANDLER)).getUserName());
                    workFlow.setHandleDate(variables.get(WorkFlowConstant.KEY_HANDLE_TIME) == null ? null : (Date) variables.get(WorkFlowConstant.KEY_HANDLE_TIME));
                    result.add(workFlow);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //result.sort((o1, o2) -> (o1.getHandleDate() != null && o2.getHandleDate() != null) ? Long.compare(o2.getHandleDate().getTime(), o1.getHandleDate().getTime()) : (o1.getHandleDate() == null ? 1 : -1));
        sort(result);
        return result;
    }

    /**
     * 查询已办经手
     *
     * @param userId 用户ID
     * @return 当前流程/部门下的待办
     * @throws SystemException 系统异常
     */
    @Override
    public Map<String, Set<String>> excQueryTaskCompleted(String userId) throws SystemException {
        Map<String, Set<String>> result = new HashMap<>();
        List<HistoricTaskInstance> hisTaskList = this.excQueryHisTask(userId);
        if (hisTaskList == null || hisTaskList.isEmpty()) return result;
        List<HistoricProcessInstance> processInstanceList = historyService.createHistoricProcessInstanceQuery().list();
        if (processInstanceList == null || processInstanceList.isEmpty()) return result;
        Map<String, HistoricProcessInstance> processInstanceMap = processInstanceList.stream().collect(Collectors.toMap(HistoricProcessInstance::getId, a -> a));
        for (HistoricTaskInstance task : hisTaskList) {
            HistoricProcessInstance processInstance = processInstanceMap.get(task.getProcessInstanceId());
            String processDefKey = processInstance.getProcessDefinitionKey();
            WorkBench workBench = ProcessDictEnum.getInstance(processDefKey);
            if (null != workBench) {
                if (ProcessDictEnum.CashApply.getProcessKey().equals(processDefKey)) {
                    workBench.processDone(task, processInstance, result);
                } else {
                    workBench.processDone(processInstance, result);
                }
            }

        }
        return result;
    }

    /**
     * 查询已办经手V2.0
     */
    @Override
    public List<WorkFlow> excQueryTaskCompleted(String userId, List<HTWorkBean> exeWorks) throws SystemException {
        List<WorkFlow> result = new ArrayList<>();
        //构造执行人数据
        if (exeWorks != null && !exeWorks.isEmpty()) {
            try {
                for (HTWorkBean work : exeWorks) {
                    String taskId = work.getDelegateTaskId();
                    Map<String, Object> variables = taskService.getVariables(taskId);
                    String processDefKey = work.getBusinessType();
                    String bizKey = work.getBusinessId();
                    WorkFlow workFlow = new WorkFlow();
                    WorkBench workBench = ProcessDictEnum.getInstance(processDefKey);
                    if (workBench != null) {
                        Set<String> bizKeys = new HashSet<>();
                        bizKeys.add(bizKey);
                        workFlow.setBizKey(bizKeys);
                    }
                    workFlow.setProcessDefinitionKey(processDefKey);
                    workFlow.setPreNodeHandlerId(variables.get(WorkFlowConstant.KEY_PRE_HANDLER) == null ? "" : ((HTUserVO) variables.get(WorkFlowConstant.KEY_PRE_HANDLER)).getLoginUserId());
                    workFlow.setPreNodeHandlerName(variables.get(WorkFlowConstant.KEY_PRE_HANDLER) == null ? "" : ((HTUserVO) variables.get(WorkFlowConstant.KEY_PRE_HANDLER)).getUserName());
                    workFlow.setHandleDate(variables.get(WorkFlowConstant.KEY_HANDLE_TIME) == null ? null : (Date) variables.get(WorkFlowConstant.KEY_HANDLE_TIME));
                    result.add(workFlow);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<HistoricTaskInstance> hisTaskList = this.excQueryHisTask(userId);
        if (hisTaskList == null || hisTaskList.isEmpty()) return result;
        List<HistoricProcessInstance> processInstanceList = historyService.createHistoricProcessInstanceQuery().list();
        if (processInstanceList == null || processInstanceList.isEmpty()) return result;
        Map<String, HistoricProcessInstance> processInstanceMap = processInstanceList.stream().collect(Collectors.toMap(HistoricProcessInstance::getId, a -> a));
        try {
            for (HistoricTaskInstance historicTaskInstance : hisTaskList) {
                String processDefId = historicTaskInstance.getProcessDefinitionId();
                String processDefKey = processDefId.substring(0, processDefId.indexOf(":"));
                List<HistoricVariableInstance> hisVariableList = historyService.createHistoricVariableInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).list();
                Map<String, Object> variables = getVariable(hisVariableList);
                HistoricProcessInstance processInstance = processInstanceMap.get(historicTaskInstance.getProcessInstanceId());
                WorkFlow workFlow = new WorkFlow();
                workFlow.setProcessDefinitionKey(processDefKey);
                if (ProcessDictEnum.CreateProjectProcess.getProcessKey().equals(processDefKey)) {
                    Set<String> bizKeys = new HashSet<>();
                    String projectId = processInstance.getBusinessKey();
                    TbProjectProcessRelationEntity projectProcess = projectProcessDao.selectByPrimaryKey(projectId, historicTaskInstance.getProcessInstanceId());
                    if (projectProcess != null && StringUtils.isNotBlank(projectProcess.getContractId())) {
                        bizKeys.add(projectProcess.getContractId());
                    }
                    workFlow.setBizKey(bizKeys);
                } else {
                    Set<String> bizKeys = new HashSet<>();
                    bizKeys.add(processInstance.getBusinessKey());
                    workFlow.setBizKey(bizKeys);
                }
                workFlow.setPreNodeHandlerId(variables.get(WorkFlowConstant.KEY_PRE_HANDLER) == null ? "" : ((HTUserVO) variables.get(WorkFlowConstant.KEY_PRE_HANDLER)).getLoginUserId());
                workFlow.setPreNodeHandlerName(variables.get(WorkFlowConstant.KEY_PRE_HANDLER) == null ? "" : ((HTUserVO) variables.get(WorkFlowConstant.KEY_PRE_HANDLER)).getUserName());
                workFlow.setHandleDate(variables.get(WorkFlowConstant.KEY_HANDLE_TIME) == null ? null : (Date) variables.get(WorkFlowConstant.KEY_HANDLE_TIME));
                result.add(workFlow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //result.sort((o1, o2) -> (o1.getHandleDate() != null && o2.getHandleDate() != null) ? Long.compare(o2.getHandleDate().getTime(), o1.getHandleDate().getTime()) : (o1.getHandleDate() == null ? 1 : -1));
        sort(result);
        return result;
    }

    private Map<String, Object> getVariable(List<HistoricVariableInstance> list) {
        Map<String, Object> result = new HashMap<>();
        result.put(WorkFlowConstant.KEY_PRE_HANDLER, null);
        result.put(WorkFlowConstant.KEY_HANDLE_TIME, null);
        for (HistoricVariableInstance variableInstance : list) {
            if (WorkFlowConstant.KEY_PRE_HANDLER.equals(variableInstance.getVariableName())) {
                result.put(WorkFlowConstant.KEY_PRE_HANDLER, variableInstance.getValue());
                continue;
            }
            if (WorkFlowConstant.KEY_HANDLE_TIME.equals(variableInstance.getVariableName())) {
                result.put(WorkFlowConstant.KEY_HANDLE_TIME, variableInstance.getValue());
            }
        }
        return result;
    }

    /**
     * 删除流程
     *
     * @param processInstanceId 流程实例ID
     * @throws SystemException 系统异常
     */
    @Override
    public void excDeleteProcess(String processInstanceId) throws SystemException {
        if (!isEnd(processInstanceId)) {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            runtimeService.deleteProcessInstance(processInstanceId, "用户删除流程实例");
        }
        historyService.deleteHistoricProcessInstance(processInstanceId);
    }

    /**
     * 流程是否结束
     */
    private boolean isEnd(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance != null) {
            return processInstance.isEnded();
        }
        return true;
    }

    /**
     * 查询已完成的历史
     *
     * @param userId        用户ID
     * @param processDefKey 流程定义KEY
     * @return 历史
     * @throws SystemException 系统异常
     */
    @Override
    public List<HistoricTaskInstance> excQueryHisTask(String userId, String processDefKey) throws SystemException {
        List<HistoricTaskInstance> hisTaskList = new LinkedList<>();
        hisTaskList.addAll(historyService.createHistoricTaskInstanceQuery().processDefinitionKey(processDefKey).taskCandidateUser(userId).finished().list());
        hisTaskList.addAll(historyService.createHistoricTaskInstanceQuery().processDefinitionKey(processDefKey).taskAssignee(userId).finished().list());
        return hisTaskList;
    }

    /**
     * 查询已完成的历史
     *
     * @param userId 用户ID
     * @return 历史
     * @throws SystemException 系统异常
     */
    @Override
    public List<HistoricTaskInstance> excQueryHisTask(String userId) throws SystemException {
        List<HistoricTaskInstance> hisTaskList = new LinkedList<>();
        hisTaskList.addAll(historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished().list());
        hisTaskList.addAll(historyService.createHistoricTaskInstanceQuery().taskCandidateUser(userId).finished().list());
        return hisTaskList;
    }

    /**
     * 查询页面显示类型
     */
    @Override
    public String excQueryPageType(HTUserVO user, String bizDeptId, String processDictId, BaseVO vo, List<String> executorUsers) {
        String result = "0";
        String userId = user.getLoginUserId();
        String applyUserId = StringToolUtils.convertNullObjectToString(vo.getApplyUserId());
        //2019.12.18 edit 重新编辑申请在提交归档及后续节点均可申请
        //为任务流程，主提审人提供按钮显示
        if (isTaskApprovedAndApply(vo, userId, processDictId)) {
            return vo.getCurrentState() + "_" + CAN_REEDIT_FLAG;
        } else if (Constant.APPROVED.equals(vo.getVerifyState())) {
            return result;
        }
        List<String> deptIds = user.getDepartmentIds();
        //部门ID匹配，为了用户所在部门只能看到自己业务线的部门数据，质检部财务部行政部除外
        if (!IS_CLOSED.equals(vo.getProjectIsClosed()) && !IS_CANCEL.equals(vo.getIsInvalidFlag()) && (isContains(deptIds, EXCLUDE_DEPTIDS) || deptIds.contains(bizDeptId))) {
            String isCanRecall = StringToolUtils.convertNullObjectToString(vo.getIsCanRecall());
            String delegateTaskKey = StringToolUtils.convertNullObjectToString(vo.getDelegateTaskKey());
            if (contains(SUBMIT_NODES.split(","), delegateTaskKey)) {
                if (userId.equals(applyUserId)) {
                    result = vo.getCurrentState();
                }
                if (executorUsers != null && !executorUsers.isEmpty()) {
                    if (executorUsers.contains(userId)) result = STATE_EXEUSER_SUBMIT;
                }
            } else if (userId.equals(applyUserId) && IS_RECALL.equals(isCanRecall) && StringUtils.isBlank(vo.getApproveTime())) {
                result = STATE_RECALL;
            }
            String verifyState = StringToolUtils.convertNullObjectToString(vo.getVerifyState());
            List<TbJobProcessNodeRelationEntity> list = commonCacheService.getProcessNodeCache(processDictId, delegateTaskKey, deptIds);
            if (contains(VERIFY_NODES.split(","), delegateTaskKey)) {
                if (this.hasApproveAuth(userId, verifyState, list)) {
                    result = vo.getCurrentState();
                } else if (ProcessDictEnum.Task.getProcessDictId().equals(processDictId) && userId.equals(applyUserId) && StringUtils.isNotBlank(vo.getApproveTime())) {
                    //2019.12.18 edit 重新编辑申请在提交归档及后续节点均可申请
                    //2020.01.16 edit 增加审批时间不为空的判断条件，为了控制提审后撤回按钮不显示
                    //为任务流程，主提审人提供按钮显示
                    result = vo.getCurrentState() + "_" + CAN_REEDIT_FLAG;
                } else if (ProcessDictEnum.CashApply.getProcessDictId().equals(processDictId) && userId.equals(applyUserId) && Integer.toString(CashApplyEnum.IssueInvoice.getNodeSort()).equals(vo.getCurrentState())) {
                    //2019.01.03 edit 请款流程上传发票由提审人自己上传
                    result = vo.getCurrentState();
                }
            }
        }
        /*//为了处理用户跨部门（如行政+招标）仍然可以审批其他部门的合同
        if (!"0".equals(result) && !deptIds.contains(bizDeptId)) {
            if ("2,5,7,8".contains(processDictId)) {
                //排除非当前部门的审批权限
                result = "0";
            }
            if (ProcessDictEnum.WriteContract.getProcessDictId().equals(processDictId) && !("6,7".contains(result))) {//合同编撰流程
                //排除非当前部门的审批权限
                result = "0";
            }
            if (ProcessDictEnum.CashApply.getProcessDictId().equals(processDictId) && !("5,6".contains(result))) {//请款流程
                //排除非当前部门的审批权限
                result = "0";
            }
            if (ProcessDictEnum.CashApply.getProcessDictId().equals(processDictId) && !("2".contains(result))) {//归档流程
                //排除非当前部门的审批权限
                result = "0";
            }
        }*/
        return result;
    }

    /**
     * 作为主提审人是否是任务流程的审批通过状态
     * 2019.12.18 edit 重新编辑申请在提交归档及后续节点均可申请
     * 为任务流程，主提审人提供按钮显示
     */
    private boolean isTaskApprovedAndApply(BaseVO vo, String userId, String processDictId) {
        String approveTime = vo.getApproveTime();
        String approveUserId = vo.getApproveUserId();
        String applyUserId = StringToolUtils.convertNullObjectToString(vo.getApplyUserId());
        boolean result = false;
        if (Constant.APPROVED.equals(vo.getVerifyState()) && userId.equals(applyUserId)) {
            if (StringUtils.isNotBlank(approveTime) && StringUtils.isNotBlank(approveUserId)) {
                if (ProcessDictEnum.Task.getProcessDictId().equals(processDictId)) {
                    result = true;
                }
            }
        }
        return result;
    }

    private static boolean contains(String[] nodes, String node) {
        boolean result = false;
        for (String n : nodes) {
            if (n.equals(node)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 查询流程信息
     */
    @Override
    public synchronized JSONObject excQueryProcessInfo() throws SystemException {
        if (processNodeInfo.isEmpty()) {
            processNodeInfo.put(ProcessDictEnum.Task.getProcessKey(), generProcessNodeObj(EnumSet.allOf(TaskEnum.class)));
            processNodeInfo.put(ProcessDictEnum.Tender.getProcessKey(), generProcessNodeObj(EnumSet.allOf(TenderEnum.class)));
            processNodeInfo.put(ProcessDictEnum.Task_Reedit.getProcessKey(), generProcessNodeObj(EnumSet.allOf(TaskReeditEnum.class)));
            processNodeInfo.put(ProcessDictEnum.Task_A3.getProcessKey(), generProcessNodeObj(EnumSet.allOf(TaskA3Enum.class)));
            processNodeInfo.put(ProcessDictEnum.WriteContract.getProcessKey(), generProcessNodeObj(EnumSet.allOf(WriteContractEnum.class)));
            processNodeInfo.put(ProcessDictEnum.CashApply.getProcessKey(), generProcessNodeObj(EnumSet.allOf(CashApplyEnum.class)));
            processNodeInfo.put(ProcessDictEnum.Archive.getProcessKey(), generProcessNodeObj(EnumSet.allOf(ArchiveEnum.class)));
        }
        return processNodeInfo;
    }

    private boolean hasApproveAuth(String userId, String verifyState, List<TbJobProcessNodeRelationEntity> list) {
        boolean result = false;
        if (list != null && !list.isEmpty()) {
            List<String> jobId = list.stream().map(TbJobProcessNodeRelationEntity::getJobId).collect(Collectors.toList());
            List<TbUserJobRelation> userJobList = commonCacheService.getUserByJob(jobId);
            if (userJobList != null && !userJobList.isEmpty()) {
                List<String> userIdList = userJobList.stream().map(TbUserJobRelation::getUserId).collect(Collectors.toList());
                if (userIdList.contains(userId) && !Constant.APPROVED.equals(verifyState))
                    result = true;
            }
        }
        return result;
    }

    private static JSONObject generProcessNodeObj(EnumSet enumSet) {
        JSONObject processObject = new JSONObject(true);
        JSONArray nodeArray = new JSONArray();
        for (Object processEnum : enumSet) {
            ProcessEnum p = (ProcessEnum) processEnum;
            JSONObject nodeObj = new JSONObject(true);
            nodeObj.put("nodeSort", Integer.toString(p.getNodeSort()));
            nodeObj.put("nodeKey", p.getNodeKey());
            nodeObj.put("nodeName", p.getNodeName());
            nodeObj.put("jobName", p.getJobName());
            nodeObj.put("departmentId", p.getDepartmentId());
            ProcessEnum ProcessNextEnum = p.next();
            JSONObject children = new JSONObject(true);
            children.put("nodeSort", Integer.toString(ProcessNextEnum.getNodeSort()));
            children.put("nodeKey", ProcessNextEnum.getNodeKey());
            children.put("nodeName", ProcessNextEnum.getNodeName());
            children.put("jobName", ProcessNextEnum.getJobName());
            children.put("departmentId", ProcessNextEnum.getDepartmentId());
            nodeObj.put("nextNodeKey", children);
            nodeArray.add(nodeObj);
            processObject.put("nodeArray", nodeArray);
        }
        return processObject;
    }

    private static boolean isContains(List<String> userDepts, String exDepts) {
        boolean result = false;
        for (String deptId : userDepts) {
            if (exDepts.contains(deptId)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void sort(List<WorkFlow> list) {
        list.sort((o1, o2) -> {
            if (o1.getHandleDate() == o2.getHandleDate()) {
                return 0;
            } else {
                if (o1.getHandleDate() != null && o2.getHandleDate() != null) {
                    return Long.compare(o2.getHandleDate().getTime(), o1.getHandleDate().getTime());
                } else {
                    return o1.getHandleDate() == null ? 1 : -1;
                }
            }
        });
    }

}
