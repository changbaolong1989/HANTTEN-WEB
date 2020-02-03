package com.hd.processes.listener.sealApply;

import com.hd.common.dao.TbApplySealRecordEntityMapper;
import com.hd.common.entity.TbApplySealRecordEntity;
import com.hd.common.entity.TbApplySealRecordEntityExample;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.manager.vo.HTSealApplyVO;
import com.hd.processes.listener.abs.AbsActTaskListener;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
public abstract class ListenerSealApplySuper extends AbsActTaskListener {

    @Resource
    private TbApplySealRecordEntityMapper applySealRecordEntityMapper;

    @Resource
    private CommonActivitiService commonActivitiService;

    /**
     * 更新任务状态
     *
     * @param delegateExecution 执行对象
     */
    @Transactional
    protected void updateTaskStatus(DelegateExecution delegateExecution) {
        String processInstanceId = delegateExecution.getProcessInstanceId();
        String bizKey = commonActivitiService.getBizKeyByProInstId(processInstanceId);
        //更新业务数据
        HTSealApplyVO sealApplyVO = (HTSealApplyVO) delegateExecution.getVariable(WorkFlowConstant.KEY_SEAL_APPLY_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(delegateExecution.getVariable(WorkFlowConstant.KEY_APPROVAL_STATUS));
        sealApplyVO.setCompleteTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
        sealApplyVO.setVerifyState(approvalStatus);
        TbApplySealRecordEntity applySealRecordEntity = (TbApplySealRecordEntity) ObjectReflectUtils.convertObj(sealApplyVO, TbApplySealRecordEntity.class);
        TbApplySealRecordEntityExample example = new TbApplySealRecordEntityExample();
        example.createCriteria().andRecordIdEqualTo(bizKey);
        applySealRecordEntityMapper.updateByExampleSelective(applySealRecordEntity, example);
    }

    /**
     * 保存审批历史记录
     */
    @Override
    public void saveApproveRecord(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables) {

    }

}
