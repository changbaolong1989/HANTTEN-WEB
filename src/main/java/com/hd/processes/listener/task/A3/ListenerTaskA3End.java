package com.hd.processes.listener.task.A3;

import com.hd.base.enums.TaskA3Enum;
import com.hd.common.dao.TbTaskPeriodRelationEntityMapper;
import com.hd.common.entity.TbTaskPeriodRelationEntity;
import com.hd.common.entity.TbTaskPeriodRelationEntityExample;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.manager.vo.HTTaskPeriodVO;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 任务_A3审批监听
 * 结束节点监听
 * End
 */
@Service
public class ListenerTaskA3End implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -5142866563305345472L;

    private static final TaskA3Enum EVENT_ENUM = TaskA3Enum.EndEvent;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private TbTaskPeriodRelationEntityMapper taskPeriodRelationDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String processInstanceId = delegateExecution.getProcessInstanceId();
        String bizKey = commonActivitiService.getBizKeyByProInstId(processInstanceId);
        //更新业务数据
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) delegateExecution.getVariable(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(delegateExecution.getVariable(WorkFlowConstant.KEY_APPROVAL_STATUS));
        taskPeriodVO.setCompleteTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
        taskPeriodVO.setVerifyState(approvalStatus);
        TbTaskPeriodRelationEntity taskPeriodRelation = (TbTaskPeriodRelationEntity) ObjectReflectUtils.convertObj(taskPeriodVO, TbTaskPeriodRelationEntity.class);
        taskPeriodRelation.setDelegateTaskKey(EVENT_ENUM.getNodeKey());
        taskPeriodRelation.setSubmitNode(null);
        taskPeriodRelation.setCurrentState(StringToolUtils.convertNullObjectToString(EVENT_ENUM.getNodeSort()));
        TbTaskPeriodRelationEntityExample example = new TbTaskPeriodRelationEntityExample();
        example.createCriteria().andRelationIdEqualTo(bizKey);
        taskPeriodRelationDao.updateByExampleSelective(taskPeriodRelation, example);
    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
