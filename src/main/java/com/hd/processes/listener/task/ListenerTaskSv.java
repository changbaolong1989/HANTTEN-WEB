package com.hd.processes.listener.task;

import com.hd.base.enums.TaskEnum;
import com.hd.common.dao.TbTaskEntityMapper;
import com.hd.common.dao.TbTaskUserRelationEntityMapper;
import com.hd.common.entity.TbTaskEntity;
import com.hd.common.entity.TbTaskUserRelationEntity;
import com.hd.common.entity.TbTaskUserRelationEntityExample;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTTaskVO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 任务_成果文件审批监听
 * 提交审核节点监听
 * SubmitVerify
 */
@Service
public class ListenerTaskSv extends ListenerTaskSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -1302783414885351171L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TbTaskUserRelationEntityMapper taskUserDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        //更新业务数据
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
            taskVO.setApplyUserId(taskVO.getLoginUserInfo().getLoginUserId());
            taskVO.setApplyUserName(taskVO.getLoginUserInfo().getUserName());
            taskVO.setApplyTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            taskVO.setVerifyState(Constant.APPROVING);
            taskVO.setIsCanRecall(IS_RECALL);
            updateBizData(taskVO, TbTaskEntity.class, TbTaskEntityMapper.class);
            //更新执行人数据
            TbTaskUserRelationEntity taskUserRelation = new TbTaskUserRelationEntity();
            taskUserRelation.setIsDone(IS_DONE);
            TbTaskUserRelationEntityExample example = new TbTaskUserRelationEntityExample();
            example.createCriteria().andTaskIdEqualTo(taskVO.getTaskId());
            taskUserDao.updateByExampleSelective(taskUserRelation, example);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        //委托任务的节点ID
        String delegateTaskId = delegateTask.getId();
        Map<String, Object> variables = taskService.getVariables(delegateTaskId);
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            //流程节点Key
            String nodeKey = delegateTask.getTaskDefinitionKey();
            String delegateUser = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_DELEGATE_USER));
            delegateTask.addCandidateUser(delegateUser);
            //更新业务数据
            HTTaskVO taskVO = (HTTaskVO) variables.get(WorkFlowConstant.KEY_TASK_VO);
            taskVO.setProcessInstId(delegateTask.getProcessInstanceId());
            taskVO.setDelegateTaskKey(nodeKey);
            taskVO.setDelegateTaskId(delegateTaskId);
            taskVO.setCurrentState(StringToolUtils.convertNullObjectToString(TaskEnum.SubmitVerify.getNodeSort()));
            //2019.12.18 edit 重新编辑申请在提交归档及后续节点均可申请
            //如果流程结束以后重新编辑申请通过，需要将verifyState改为审批中
            if (Constant.APPROVED.equals(taskVO.getVerifyState()) && StringUtils.isBlank(taskVO.getApproveTime())) {
                taskVO.setVerifyState(Constant.APPROVING);
            }
            updateBizData(taskVO, TbTaskEntity.class, TbTaskEntityMapper.class);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

}
