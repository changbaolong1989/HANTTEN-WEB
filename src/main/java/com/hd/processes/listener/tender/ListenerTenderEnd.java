package com.hd.processes.listener.tender;

import com.hd.base.enums.TenderEnum;
import com.hd.common.dao.TbTenderTaskEntityMapper;
import com.hd.common.entity.TbTenderTaskEntity;
import com.hd.common.entity.TbTenderTaskEntityExample;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.manager.vo.HTTenderTaskVO;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 投标任务监听
 * 结束节点监听
 * End
 */
@Service
public class ListenerTenderEnd implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -8386551210741200592L;

    private static final TenderEnum EVENT_ENUM = TenderEnum.EndEvent;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private TbTenderTaskEntityMapper tenderTaskDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String processInstanceId = delegateExecution.getProcessInstanceId();
        String bizKey = commonActivitiService.getBizKeyByProInstId(processInstanceId);
        //更新业务数据
        HTTenderTaskVO tenderTaskVO = (HTTenderTaskVO) delegateExecution.getVariable(WorkFlowConstant.KEY_TENDER_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(delegateExecution.getVariable(WorkFlowConstant.KEY_APPROVAL_STATUS));
        tenderTaskVO.setCompleteTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
        tenderTaskVO.setVerifyState(approvalStatus);
        TbTenderTaskEntity tenderTask = (TbTenderTaskEntity) ObjectReflectUtils.convertObj(tenderTaskVO, TbTenderTaskEntity.class);
        tenderTask.setDelegateTaskKey(EVENT_ENUM.getNodeKey());
        tenderTask.setSubmitNode(null);
        tenderTask.setCurrentState(StringToolUtils.convertNullObjectToString(EVENT_ENUM.getNodeSort()));
        TbTenderTaskEntityExample example = new TbTenderTaskEntityExample();
        example.createCriteria().andTenderTaskIdEqualTo(bizKey);
        tenderTaskDao.updateByExampleSelective(tenderTask, example);
    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
