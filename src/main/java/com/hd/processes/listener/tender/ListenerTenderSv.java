package com.hd.processes.listener.tender;

import com.hd.base.enums.TenderEnum;
import com.hd.base.exception.LogicException;
import com.hd.common.dao.TbTenderTaskEntityMapper;
import com.hd.common.dao.TbTenderTaskUserMapper;
import com.hd.common.entity.TbTenderTaskEntity;
import com.hd.common.entity.TbTenderTaskUser;
import com.hd.common.entity.TbTenderTaskUserExample;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTTenderTaskVO;
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
 * 投标任务监听
 * 提交审核节点监听
 * SubmitVerify
 */
@Service
public class ListenerTenderSv extends ListenerTenderSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 8796874712266282360L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TbTenderTaskUserMapper tenderTaskUserDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            //更新业务数据
            HTTenderTaskVO tenderTaskVo = (HTTenderTaskVO) variables.get(WorkFlowConstant.KEY_TENDER_VO);
            tenderTaskVo.setApplyUserId(tenderTaskVo.getLoginUserInfo().getLoginUserId());
            tenderTaskVo.setApplyUserName(tenderTaskVo.getLoginUserInfo().getUserName());
            tenderTaskVo.setApplyTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            tenderTaskVo.setIsCanRecall(IS_RECALL);
            tenderTaskVo.setVerifyState(Constant.APPROVING);
            updateBizData(tenderTaskVo, TbTenderTaskEntity.class, TbTenderTaskEntityMapper.class);
            //更新执行人数据
            TbTenderTaskUser tbTenderTaskUser = new TbTenderTaskUser();
            tbTenderTaskUser.setIsDone(IS_DONE);
            TbTenderTaskUserExample example = new TbTenderTaskUserExample();
            example.createCriteria().andTenderTaskIdEqualTo(tenderTaskVo.getTenderTaskId());
            tenderTaskUserDao.updateByExampleSelective(tbTenderTaskUser, example);
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
            //分配执行人
            String mainLeaderUser = StringToolUtils.convertNullObjectToString(taskService.getVariable(delegateTaskId, WorkFlowConstant.KEY_MAIN_LEADER_USER));
            if (StringUtils.isBlank(mainLeaderUser)) throw new LogicException("无委托人异常");
            delegateTask.addCandidateUser(mainLeaderUser);
            //更新业务数据
            HTTenderTaskVO tenderTaskVo = (HTTenderTaskVO) variables.get(WorkFlowConstant.KEY_TENDER_VO);
            tenderTaskVo.setProcessInstId(delegateTask.getProcessInstanceId());
            tenderTaskVo.setDelegateTaskKey(nodeKey);
            tenderTaskVo.setDelegateTaskId(delegateTaskId);
            tenderTaskVo.setApplyUserId(mainLeaderUser);
            tenderTaskVo.setCurrentState(StringToolUtils.convertNullObjectToString(TenderEnum.SubmitVerify.getNodeSort()));
            updateBizData(tenderTaskVo, TbTenderTaskEntity.class, TbTenderTaskEntityMapper.class);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }
}
