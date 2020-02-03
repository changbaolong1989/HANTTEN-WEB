package com.hd.processes.listener.archive;

import com.hd.base.enums.ArchiveEnum;
import com.hd.common.dao.TbTaskArchiveEntityMapper;
import com.hd.common.entity.TbTaskArchiveEntity;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.vo.HTTaskArchiveVO;
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
 * 任务_归档流程监听
 * 确认归档节点监听
 * AffirmArchive
 */
@Service
public class ListenerArchiveAa extends ListenerArchiveSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -107219207866595412L;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    private CommonActivitiListenVO activitiListenVO;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String exeId = delegateExecution.getId();
        Map<String, Object> variables = runtimeService.getVariables(exeId);
        activitiListenVO = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
        try {
            //更新业务数据
            HTTaskArchiveVO archiveVO = (HTTaskArchiveVO) variables.get(WorkFlowConstant.KEY_ARCHIVE_VO);
            archiveVO.setApproveUserId(archiveVO.getLoginUserInfo().getLoginUserId());
            archiveVO.setApproveUserName(archiveVO.getLoginUserInfo().getUserName());
            archiveVO.setApproveTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            archiveVO.setVerifyState(Constant.APPROVING);
            updateBizData(archiveVO, TbTaskArchiveEntity.class, TbTaskArchiveEntityMapper.class);
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
        String nodeKey = delegateTask.getTaskDefinitionKey();
        String userDeptId = ArchiveEnum.AffirmArchive.getDepartmentId();//行政部部门ID
        String processDictId = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_PROCESS_DICT_TYPE));//流程字典ID
        //更新业务数据
        HTTaskArchiveVO archiveVO = (HTTaskArchiveVO) variables.get(WorkFlowConstant.KEY_ARCHIVE_VO);
        archiveVO.setDelegateTaskKey(nodeKey);
        archiveVO.setDelegateTaskId(delegateTaskId);
        archiveVO.setSubmitNode(ArchiveEnum.EndEvent.getNodeKey());
        archiveVO.setCurrentState(StringToolUtils.convertNullObjectToString(ArchiveEnum.AffirmArchive.getNodeSort()));
        setDelegation(delegateTask, activitiListenVO, archiveVO, TbTaskArchiveEntity.class, TbTaskArchiveEntityMapper.class, userDeptId, processDictId);
    }

}
