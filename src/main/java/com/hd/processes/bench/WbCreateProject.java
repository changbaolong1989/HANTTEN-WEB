package com.hd.processes.bench;

import com.hd.common.dao.TbProjectProcessRelationEntityMapper;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.entity.TbProjectProcessRelationEntity;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.service.CommonCacheService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class WbCreateProject extends AbsWorkBench {

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private CommonCacheService commonCacheService;

    @Resource
    private TbProjectProcessRelationEntityMapper projectProcessDao;

    @Override
    public void processTodo(HistoricProcessInstance processInstance, Task task, Map<String, Set<String>> result) {
        //创建项目,保存合同ID
        String processKey = processInstance.getProcessDefinitionKey();
        if (!result.containsKey(processKey)) {
            result.put(processKey, new TreeSet<>());
        }
        Set<String> set = result.get(processKey);
        String taskId = task.getId();
        String taskDefKey = task.getTaskDefinitionKey();
        String projectId = commonActivitiService.getBizKeyByTaskId(taskId);
        List<TbContractEntity> contractList = commonCacheService.getContracts(projectId);
        for (TbContractEntity contract : contractList) {
            if (taskId.equals(contract.getDelegateTaskId()) && taskDefKey.equals(contract.getDelegateTaskKey())) {
                set.add(contract.getContractId());
            }
        }
    }

    /**
     * 流程待办V2.0
     */
    @Override
    public void processTodo(String taskId, String taskDefKey, String bizKey, WorkFlow workFlow) {
        List<TbContractEntity> contractList = commonCacheService.getContracts(bizKey);
        Set<String> bizKeys = new HashSet<>();
        for (TbContractEntity contract : contractList) {
            if (taskId.equals(contract.getDelegateTaskId()) && contract.getDelegateTaskKey().equals(taskDefKey)) {
                bizKeys.add(contract.getContractId());
            }
        }
        workFlow.setBizKey(bizKeys);
    }

    @Override
    public void processDone(HistoricProcessInstance processInstance, Map<String, Set<String>> result) {
        String processKey = processInstance.getProcessDefinitionKey();
        if (!result.containsKey(processKey)) {
            result.put(processKey, new TreeSet<>());
        }
        Set<String> set = result.get(processKey);
        String projectId = commonActivitiService.getBizKeyByHisProInstId(processInstance.getId());
        TbProjectProcessRelationEntity projectProcess = projectProcessDao.selectByPrimaryKey(projectId, processInstance.getId());
        if (projectProcess != null && StringUtils.isNotBlank(projectProcess.getContractId())) {
            set.add(projectProcess.getContractId());
        }
    }

}
