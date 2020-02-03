package com.hd.processes.listener.createProject;

import com.hd.base.exception.NoDelegationException;
import com.hd.common.dao.TbContractEntityMapper;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.entity.TbUserJobRelation;
import com.hd.common.service.CommonCacheService;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.service.HTContractProcessService;
import com.hd.manager.vo.HTContractVO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 创建项目监听
 * 分配副总节点监听
 * AllotManager
 */
@Service
public class ListenerCreateProjectAm extends ListenerCreateProjectSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -8072333457097737544L;

    private CommonActivitiListenVO activitiListenVO;

    private static final String WAITTING_ALLOT = "9";

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private CommonCacheService cacheService;

    @Resource
    private HTContractProcessService contractProcessService;

    @Resource
    private TbContractEntityMapper contractDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);//流程变量，要有部门ID
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            //分配合同编撰人的同时，启动合同流程
            HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
            ProcessInstance processInstance = contractProcessService.excStart(variables);
            contractVO.setProcessInstId(processInstance.getId());
            TbContractEntity contractEntity = (TbContractEntity) ObjectReflectUtils.convertObj(contractVO, TbContractEntity.class);
            contractEntity.setVerifyState(STATE_ASSIGNED);
            contractDao.updateByPrimaryKeySelective(contractEntity);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        //委托任务的节点ID
        String delegateTaskId = delegateTask.getId();
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            //流程节点Key
            String nodeKey = delegateTask.getTaskDefinitionKey();
            List<String> jobIds = new ArrayList<>();
            String jobId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_JOB_ID));//职位ID
            jobIds.add(jobId);
            List<TbUserJobRelation> userJobList = cacheService.getUserByJob(jobIds);
            if (userJobList == null || userJobList.isEmpty())
                throw new NoDelegationException(MessageConstant.MSG_INFO_PROCESS_NO_USER);
            //分配任务办理人
            List<String> delegateUsers = userJobList.stream().map(TbUserJobRelation::getUserId).collect(Collectors.toList());
            delegateTask.addCandidateUsers(delegateUsers);
            //更新合同业务数据
            HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
            TbContractEntity contractEntity = (TbContractEntity) ObjectReflectUtils.convertObj(contractVO, TbContractEntity.class);
            contractEntity.setDelegateTaskKey(nodeKey);
            contractEntity.setDelegateTaskId(delegateTaskId);
            //设置合同待分配状态
            contractEntity.setCurrentState(WAITTING_ALLOT);
            contractDao.updateByPrimaryKeySelective(contractEntity);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }

    }
}
