package com.hd.processes.listener.writeContract;

import com.hd.base.enums.WriteContractEnum;
import com.hd.common.dao.TbContractEntityMapper;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.entity.TbContractEntityExample;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.manager.vo.HTContractVO;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 编撰合同任务监听
 * 结束节点监听
 * End
 */
@Service
public class ListenerContractEnd implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -8958683756426080527L;

    private static final WriteContractEnum EVENT_ENUM = WriteContractEnum.EndEvent;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private TbContractEntityMapper contractDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String processInstanceId = delegateExecution.getProcessInstanceId();
        String bizKey = commonActivitiService.getBizKeyByProInstId(processInstanceId);
        //更新业务数据
        HTContractVO contractVO = (HTContractVO) delegateExecution.getVariable(WorkFlowConstant.KEY_CONTRACT_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(delegateExecution.getVariable(WorkFlowConstant.KEY_APPROVAL_STATUS));
        contractVO.setCompleteTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
        contractVO.setVerifyState(approvalStatus);
        TbContractEntity contract = (TbContractEntity) ObjectReflectUtils.convertObj(contractVO, TbContractEntity.class);
        contract.setDelegateTaskKey(EVENT_ENUM.getNodeKey());
        contract.setSubmitNode(null);
        contract.setCurrentState(StringToolUtils.convertNullObjectToString(EVENT_ENUM.getNodeSort()));
        TbContractEntityExample example = new TbContractEntityExample();
        example.createCriteria().andContractIdEqualTo(bizKey);
        contractDao.updateByExampleSelective(contract, example);
    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }

}
