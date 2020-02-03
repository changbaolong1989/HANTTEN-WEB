package com.hd.processes.listener.task;

import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.processes.listener.factory.VerifyObjectFactory;
import com.hd.processes.listener.factory.TaskFactory;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 任务_成果文件审批监听
 * 质检部审核节点监听
 * QualityVerify
 */
@Service
public class ListenerTaskQv extends ListenerTaskSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 3357158983481287663L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource(type = TaskFactory.class)
    private VerifyObjectFactory factory;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        try {
            activitiListenVO = qualityExeListener(factory.produceQvExe(delegateExecution));
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        activitiListenVO = qualityTaskListener(factory.produceQvTask(delegateTask));
    }
}
