package com.hd.processes.listener.sealApply;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

/**
 * 请章流程结束监听
 * 结束节点监听
 * End
 */
@Service
public class ListenerSealApplyEnd extends ListenerSealApplySuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 2462591859969613655L;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        updateTaskStatus(delegateExecution);
    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
