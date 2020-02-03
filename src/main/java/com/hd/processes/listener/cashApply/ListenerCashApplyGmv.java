package com.hd.processes.listener.cashApply;

import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.processes.listener.factory.CashApplyFactory;
import com.hd.processes.listener.factory.VerifyObjectFactory;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 请款任务监听
 * 张总审核节点监听
 * GenerManagerVerify
 */
@Service
public class ListenerCashApplyGmv extends ListenerCashApplySuper implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 4909311537024335034L;

    private CommonActivitiListenVO activitiListenVO;

    @Resource(type = CashApplyFactory.class)
    private VerifyObjectFactory factory;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        try {
            activitiListenVO = qualityExeListener(factory.produceGmvExe(delegateExecution));
        } catch (Exception e) {
            eHandle(activitiListenVO);
            e.printStackTrace();
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        activitiListenVO = qualityTaskListener(factory.produceGmvTask(delegateTask));
    }
}
