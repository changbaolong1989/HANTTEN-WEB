package com.hd.processes.listener.archive;

import com.hd.base.enums.ArchiveEnum;
import com.hd.common.dao.TbTaskArchiveEntityMapper;
import com.hd.common.dao.TbTaskUserRelationEntityMapper;
import com.hd.common.entity.TbTaskArchiveEntity;
import com.hd.common.entity.TbTaskUserRelationEntity;
import com.hd.common.entity.TbTaskUserRelationEntityExample;
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
 * 归档流程监听
 * 提交归档节点监听
 * SubmitArchive
 */
@Service
public class ListenerArchiveSa extends ListenerArchiveSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 3185775659651163038L;

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
            HTTaskArchiveVO taskArchiveVO = (HTTaskArchiveVO) variables.get(WorkFlowConstant.KEY_ARCHIVE_VO);
            taskArchiveVO.setApplyUserId(taskArchiveVO.getLoginUserInfo().getLoginUserId());
            taskArchiveVO.setApplyUserName(taskArchiveVO.getLoginUserInfo().getUserName());
            taskArchiveVO.setVerifyState(Constant.APPROVING);
            taskArchiveVO.setApplyTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            updateBizData(taskArchiveVO, TbTaskArchiveEntity.class, TbTaskArchiveEntityMapper.class);
            //更新执行人数据
            TbTaskUserRelationEntity taskUserRelation = new TbTaskUserRelationEntity();
            taskUserRelation.setIsDone(IS_DONE);
            TbTaskUserRelationEntityExample example = new TbTaskUserRelationEntityExample();
            example.createCriteria().andTaskIdEqualTo(taskArchiveVO.getTaskId());
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
            String nodeKey = delegateTask.getTaskDefinitionKey();
            HTTaskArchiveVO archiveVO = (HTTaskArchiveVO) variables.get(WorkFlowConstant.KEY_ARCHIVE_VO);
            String delegateUser = StringToolUtils.convertNullObjectToString(variables.get(WorkFlowConstant.KEY_DELEGATE_USER));
            delegateTask.addCandidateUser(delegateUser);
            //更新业务数据
            archiveVO.setDelegateTaskKey(nodeKey);
            archiveVO.setDelegateTaskId(delegateTaskId);
            archiveVO.setApplyUserId(delegateUser);
            archiveVO.setSubmitNode(ArchiveEnum.AffirmArchive.getNodeKey());
            archiveVO.setCurrentState(StringToolUtils.convertNullObjectToString(ArchiveEnum.SubmitArchive.getNodeSort()));
            updateBizData(archiveVO, TbTaskArchiveEntity.class, TbTaskArchiveEntityMapper.class);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

}
