package com.hd.processes.bench;

import com.hd.base.enums.CashApplyEnum;
import com.hd.common.dao.TbRequestMenuEntityMapper;
import com.hd.common.entity.TbRequestMenuEntity;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Service
public class WbCashApply extends AbsWorkBench {

    @Resource
    private TbRequestMenuEntityMapper requestMenuDao;

    @Override
    public void processDone(HistoricTaskInstance taskInstance, HistoricProcessInstance processInstance, Map<String, Set<String>> result) {
        String processKey = processInstance.getProcessDefinitionKey();
        if (!result.containsKey(processKey)) {
            result.put(processKey, new TreeSet<>());
        }
        Set<String> set = result.get(processKey);
        String cashApplyId = processInstance.getBusinessKey();
        TbRequestMenuEntity requestMenu = requestMenuDao.selectByPrimaryKey(cashApplyId);
        if (requestMenu != null && !CashApplyEnum.SubmitVerify.getNodeKey().equals(taskInstance.getTaskDefinitionKey())) {//请款单提审后不需要有已办
            set.add(requestMenu.getContractId());
        }
    }
}
