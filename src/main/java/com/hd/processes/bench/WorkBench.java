package com.hd.processes.bench;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

import java.util.Map;
import java.util.Set;

public interface WorkBench {

    /**
     * 流程待办
     *
     * @param processInstance 流程实例
     * @param result          结果集
     */
    void processTodo(HistoricProcessInstance processInstance, Task task, Map<String, Set<String>> result);

    /**
     * 流程待办V2.0
     */
    void processTodo(String taskId, String taskDefKey, String bizKey, WorkFlow workFlow);

    void processDone(HistoricProcessInstance processInstance, Map<String, Set<String>> result);

    void processDone(HistoricTaskInstance taskInstance, HistoricProcessInstance processInstance, Map<String, Set<String>> result);

}
