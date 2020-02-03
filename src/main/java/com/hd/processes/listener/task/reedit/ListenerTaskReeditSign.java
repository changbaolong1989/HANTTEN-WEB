package com.hd.processes.listener.task.reedit;

import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.processes.listener.factory.TaskReeditFactory;
import com.hd.processes.listener.factory.VerifyObjectFactory;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 任务重新编辑审核监听
 * 签发人签发节点监听
 * Signer
 */
@Service
public class ListenerTaskReeditSign extends ListenerTaskReeditSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -7335335902334117959L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource(type = TaskReeditFactory.class)
    private VerifyObjectFactory factory;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        try {
            activitiListenVO = signExeListener(factory.produceSignExe(delegateExecution));
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        activitiListenVO = signTaskListener(factory.produceSignTask(delegateTask));
    }
}
