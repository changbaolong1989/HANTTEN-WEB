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
 * 任务_重新编辑流程监听
 * 经理审核节点监听
 * ManagerVerify
 */
@Service
public class ListenerTaskReeditMv extends ListenerTaskReeditSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 7218960183993424831L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource(type = TaskReeditFactory.class)
    private VerifyObjectFactory factory;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        try {
            activitiListenVO = managerExeListener(factory.produceMvExe(delegateExecution));
        } catch (Exception e) {
            e.printStackTrace();
            eHandle(activitiListenVO);
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        activitiListenVO = managerTaskListener(factory.produceMvTask(delegateTask));
    }

}
