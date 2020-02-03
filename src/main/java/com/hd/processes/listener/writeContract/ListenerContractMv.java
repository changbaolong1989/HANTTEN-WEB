package com.hd.processes.listener.writeContract;

import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.processes.listener.factory.ContractFactory;
import com.hd.processes.listener.factory.VerifyObjectFactory;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 合同编撰任务监听
 * 经理审核节点监听
 * ManagerVerify
 */
@Service
public class ListenerContractMv extends ListenerContractSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 8042479500695952166L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource(type = ContractFactory.class)
    private VerifyObjectFactory factory;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        try {
            activitiListenVO = managerExeListener(factory.produceMvExe(delegateExecution));
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        activitiListenVO = managerTaskListener(factory.produceMvTask(delegateTask));
    }
}
