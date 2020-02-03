package com.hd.processes.bench;

import com.hd.common.dao.TbTaskArchiveEntityMapper;
import com.hd.common.entity.TbTaskArchiveEntity;
import com.hd.common.entity.TbTaskEntity;
import com.hd.common.service.CommonCacheService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Service
public class WbArchive extends AbsWorkBench {

    @Resource
    private CommonCacheService commonCacheService;

    @Resource
    private TbTaskArchiveEntityMapper archiveDao;

    @Override
    public void processDone(HistoricProcessInstance processInstance, Map<String, Set<String>> result) {
        String processKey = processInstance.getProcessDefinitionKey();
        if (!result.containsKey(processKey)) {
            result.put(processKey, new TreeSet<>());
        }
        Set<String> set = result.get(processKey);
        String archiveId = processInstance.getBusinessKey();
        TbTaskArchiveEntity archiveEntity = archiveDao.selectByPrimaryKey(archiveId);
        if (archiveEntity != null) {
            String taskId = archiveEntity.getTaskId();
            TbTaskEntity task = commonCacheService.getTaskCache(taskId);
            set.add(task.getContractId());
        }
    }

}
