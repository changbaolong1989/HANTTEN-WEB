package com.hd.processes.listener.task;

import com.hd.base.enums.TaskEnum;
import com.hd.common.dao.TbTaskEntityMapper;
import com.hd.common.entity.TbTaskEntity;
import com.hd.common.util.*;
import com.hd.manager.vo.HTTaskVO;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 任务_成果文件审批监听
 * 结束节点监听
 * End
 */
@Service
public class ListenerTaskEnd implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 8669139938769703408L;

    private static final TaskEnum EVENT_ENUM = TaskEnum.EndEvent;

    @Resource
    private TbTaskEntityMapper taskDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        //更新业务数据
        HTTaskVO taskVO = (HTTaskVO) delegateExecution.getVariable(WorkFlowConstant.KEY_TASK_VO);
        taskVO.setCompleteTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
        taskVO.setVerifyState(Constant.APPROVED);
        taskVO.setDelegateTaskKey(EVENT_ENUM.getNodeKey());
        taskVO.setSubmitNode(null);
        taskVO.setCurrentState(StringToolUtils.convertNullObjectToString(EVENT_ENUM.getNodeSort()));
        TbTaskEntity taskEntity = (TbTaskEntity) ObjectReflectUtils.convertObj(taskVO, TbTaskEntity.class);
        taskDao.updateByPrimaryKeySelective(taskEntity);
    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
