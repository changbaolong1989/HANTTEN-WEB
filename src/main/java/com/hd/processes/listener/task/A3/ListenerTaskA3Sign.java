package com.hd.processes.listener.task.A3;

import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.processes.listener.factory.VerifyObjectFactory;
import com.hd.processes.listener.factory.TaskA3Factory;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 任务A3审核监听
 * 签发人签发节点监听
 * Signer
 */
@Service
public class ListenerTaskA3Sign extends ListenerTaskA3Super implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 6521208961240817492L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource(type = TaskA3Factory.class)
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
