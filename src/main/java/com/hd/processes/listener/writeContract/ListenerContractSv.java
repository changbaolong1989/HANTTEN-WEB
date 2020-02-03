package com.hd.processes.listener.writeContract;

import com.hd.base.enums.WriteContractEnum;
import com.hd.common.dao.TbContractEntityMapper;
import com.hd.common.dao.TbUserEntityMapper;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTContractVO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 合同编撰任务监听
 * 提交审核节点监听
 * SubmitVerify
 */
@Service
public class ListenerContractSv extends ListenerContractSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -6392559064340461171L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TbUserEntityMapper userDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            //更新业务数据
            HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
            contractVO.setApplyUserId(contractVO.getLoginUserInfo().getLoginUserId());
            contractVO.setApplyUserName(contractVO.getLoginUserInfo().getUserName());
            contractVO.setApplyTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            contractVO.setIsCanRecall(IS_RECALL);
            contractVO.setVerifyState(Constant.APPROVING);
            updateBizData(contractVO, TbContractEntity.class, TbContractEntityMapper.class);
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
            HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
            contractVO.setProcessInstId(delegateTask.getProcessInstanceId());
            contractVO.setDelegateTaskKey(nodeKey);
            contractVO.setDelegateTaskId(delegateTaskId);
            contractVO.setApplyUserId(delegateUser);
            contractVO.setApplyUserName(userDao.selectByPrimaryKey(delegateUser).getUserName());
            contractVO.setCurrentState(StringToolUtils.convertNullObjectToString(WriteContractEnum.SubmitVerify.getNodeSort()));
            updateBizData(contractVO, TbContractEntity.class, TbContractEntityMapper.class);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

}
