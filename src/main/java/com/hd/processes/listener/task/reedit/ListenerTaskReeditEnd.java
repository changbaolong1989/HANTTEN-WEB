package com.hd.processes.listener.task.reedit;

import com.hd.base.enums.TaskEnum;
import com.hd.base.enums.TaskReeditEnum;
import com.hd.common.dao.TbTaskEntityMapper;
import com.hd.common.dao.TbTaskReeditEntityMapper;
import com.hd.common.entity.TbTaskEntity;
import com.hd.common.entity.TbTaskReeditEntity;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.*;
import com.hd.manager.vo.HTTaskReeditVO;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 任务_重新编辑监听
 * 结束节点监听
 * End
 */
@Service
public class ListenerTaskReeditEnd implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -1964243120641326465L;

    @Resource
    private TbTaskEntityMapper taskDao;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private TbTaskReeditEntityMapper taskReeditDao;

    private static final String CAN_NOT_RECALL = "0";

    private static final String IS_NOT_SUBMIT_REEDIT = "0";

    @Override
    public void notify(DelegateExecution delegateExecution) {
        //更新业务数据
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) delegateExecution.getVariable(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        String approvalStatus = StringToolUtils.convertNullObjectToString(delegateExecution.getVariable(WorkFlowConstant.KEY_APPROVAL_STATUS));
        if (Constant.APPROVED.equals(approvalStatus)) {
            String taskId = taskReeditVO.getTaskId();
            TbTaskEntity task = taskDao.selectByPrimaryKey(taskId);
            //删除成果文件流程数据
            commonActivitiService.excDeleteProcess(task.getProcessInstId());
            //task.setIsPassReedit(IS_NOT_PASS_REEDIT);
            task.setProcessInstId(null);
            task.setDelegateTaskId(null);
            task.setDelegateTaskKey(null);
            task.setApplyTime(null);
            task.setApproveUserId(null);
            task.setApproveUserName(null);
            task.setApproveTime(null);
            task.setCompleteTime(null);
            //task.setVerifyState(null);删除任务时会有bug
            task.setCurrentState(null);
            task.setSubmitNode(TaskEnum.ManagerVerify.getNodeKey());
            task.setIsCanRecall(CAN_NOT_RECALL);
            task.setIsSubmitReedit(IS_NOT_SUBMIT_REEDIT);
            taskReeditVO.setCompleteTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            taskReeditVO.setVerifyState(approvalStatus);
            TbTaskReeditEntity taskReeditEntity = (TbTaskReeditEntity) ObjectReflectUtils.convertObj(taskReeditVO, TbTaskReeditEntity.class);
            taskReeditEntity.setDelegateTaskKey(TaskReeditEnum.EndEvent.getNodeKey());
            taskReeditEntity.setSubmitNode(TaskReeditEnum.EndEvent.getNodeKey());
            taskReeditEntity.setCurrentState(Integer.toString(TaskReeditEnum.EndEvent.getNodeSort()));
            taskReeditDao.updateByPrimaryKeySelective(taskReeditEntity);
            taskDao.updateByPrimaryKey(task);
        } else if (Constant.NOT_APPROVED.equals(approvalStatus)) {
            taskReeditVO.setCompleteTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            taskReeditVO.setVerifyState(approvalStatus);
            TbTaskReeditEntity taskReeditEntity = (TbTaskReeditEntity) ObjectReflectUtils.convertObj(taskReeditVO, TbTaskReeditEntity.class);
            taskReeditEntity.setDelegateTaskKey(TaskReeditEnum.EndEvent.getNodeKey());
            taskReeditEntity.setSubmitNode(TaskReeditEnum.EndEvent.getNodeKey());
            taskReeditEntity.setCurrentState(Integer.toString(TaskReeditEnum.EndEvent.getNodeSort()));
            TbTaskEntity task = taskDao.selectByPrimaryKey(taskReeditEntity.getTaskId());
            task.setIsSubmitReedit(IS_NOT_SUBMIT_REEDIT);
            taskReeditDao.updateByPrimaryKeySelective(taskReeditEntity);
            taskDao.updateByPrimaryKey(task);
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }

}
