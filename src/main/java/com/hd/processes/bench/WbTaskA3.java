package com.hd.processes.bench;

import com.hd.common.entity.TbTaskEntity;
import com.hd.common.service.CommonCacheService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Service
public class WbTaskA3 extends AbsWorkBench {

    @Resource
    private CommonCacheService commonCacheService;

    @Override
    public void processDone(HistoricProcessInstance processInstance, Map<String, Set<String>> result) {
        String processKey = processInstance.getProcessDefinitionKey();
        if (!result.containsKey(processKey)) {
            result.put(processKey, new TreeSet<>());
        }
        Set<String> set = result.get(processKey);
        String taskPeriodId = processInstance.getBusinessKey();
        TbTaskEntity taskEntity = commonCacheService.getTaskPeriod(taskPeriodId);
        if (taskEntity != null) {
            set.add(taskEntity.getContractId());
        }
    }

}
