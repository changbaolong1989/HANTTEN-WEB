package com.hd.processes.listener.cashApply;

import com.hd.base.enums.CashApplyEnum;
import com.hd.base.enums.TenderEnum;
import com.hd.common.dao.TbRequestMenuEntityMapper;
import com.hd.common.entity.TbRequestMenuEntity;
import com.hd.common.entity.TbRequestMenuEntityExample;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.manager.vo.HTRequestMenuVO;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 请款任务监听
 * 结束节点监听
 * End
 */
@Service
public class ListenerCashApplyEnd implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -1723466443849396575L;

    private static final CashApplyEnum EVENT_ENUM = CashApplyEnum.EndEvent;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private TbRequestMenuEntityMapper requestMenuDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String processInstanceId = delegateExecution.getProcessInstanceId();
        String bizKey = commonActivitiService.getBizKeyByProInstId(processInstanceId);
        //更新业务数据
        HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) delegateExecution.getVariable(WorkFlowConstant.KEY_CASH_APPLY_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(delegateExecution.getVariable(WorkFlowConstant.KEY_APPROVAL_STATUS));
        requestMenuVO.setCompleteTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
        requestMenuVO.setVerifyState(approvalStatus);
        TbRequestMenuEntity requestMenuEntity = (TbRequestMenuEntity) ObjectReflectUtils.convertObj(requestMenuVO, TbRequestMenuEntity.class);
        requestMenuEntity.setDelegateTaskKey(EVENT_ENUM.getNodeKey());
        requestMenuEntity.setSubmitNode(null);
        requestMenuEntity.setCurrentState(StringToolUtils.convertNullObjectToString(EVENT_ENUM.getNodeSort()));
        TbRequestMenuEntityExample example = new TbRequestMenuEntityExample();
        example.createCriteria().andRequestMenuIdEqualTo(bizKey);
        requestMenuDao.updateByExampleSelective(requestMenuEntity, example);
    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
