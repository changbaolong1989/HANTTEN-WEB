package com.hd.processes.listener.writeContract;

import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.processes.listener.factory.ContractFactory;
import com.hd.processes.listener.factory.VerifyObjectFactory;
import com.hd.processes.listener.factory.VerifyObject;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 编撰合同任务监听
 * 签发人签发节点监听
 * Signer
 */
@Service
public class ListenerContractSign extends ListenerContractSuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -2348259426595480659L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource(type = ContractFactory.class)
    private VerifyObjectFactory factory;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        try {
            VerifyObject verifyObject = factory.produceSignExe(delegateExecution);
            activitiListenVO = signExeListener(verifyObject);
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        VerifyObject verifyObject = factory.produceSignTask(delegateTask);
        activitiListenVO = signTaskListener(verifyObject);
    }

}
