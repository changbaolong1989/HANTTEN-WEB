package com.hd.processes.bench;

import com.hd.common.service.CommonActivitiService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Service
public abstract class AbsWorkBench implements WorkBench {

    @Resource
    private CommonActivitiService commonActivitiService;

    /**
     * 流程待办
     *
     * @param processInstance 流程实例
     * @param result          结果集
     */
    @Override
    public void processTodo(HistoricProcessInstance processInstance, Task task, Map<String, Set<String>> result) {
        String taskId = task.getId();
        String processKey = processInstance.getProcessDefinitionKey();
        if (!result.containsKey(processKey)) {
            result.put(processKey, new TreeSet<>());
        }
        Set<String> set = result.get(processKey);
        set.add(commonActivitiService.getBizKeyByTaskId(taskId));
    }

    /**
     * 流程待办V2.0
     */
    @Override
    public void processTodo(String taskId, String taskDefKey, String bizKey, WorkFlow workFlow) {
        Set<String> bizKeys = new HashSet<>();
        bizKeys.add(bizKey);
        workFlow.setBizKey(bizKeys);
    }

    @Override
    public void processDone(HistoricProcessInstance processInstance, Map<String, Set<String>> result) {
        String processKey = processInstance.getProcessDefinitionKey();
        if (!result.containsKey(processKey)) {
            result.put(processKey, new TreeSet<>());
        }
        Set<String> set = result.get(processKey);
        set.add(processInstance.getBusinessKey());
    }

    @Override
    public void processDone(HistoricTaskInstance taskInstance, HistoricProcessInstance processInstance, Map<String, Set<String>> result) {

    }

}
