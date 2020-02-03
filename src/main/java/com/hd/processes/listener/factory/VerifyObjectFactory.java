package com.hd.processes.listener.factory;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;

public interface VerifyObjectFactory {

    /**
     * 一级审批任务监听
     */
    VerifyObject produceMvTask(DelegateTask delegateTask);

    /**
     * 一级审批执行监听
     */
    VerifyObject produceMvExe(DelegateExecution delegateExecution);

    /**
     * 二级审批任务监听
     */
    VerifyObject produceQvTask(DelegateTask delegateTask);

    /**
     * 二级审批执行监听
     */
    VerifyObject produceQvExe(DelegateExecution delegateExecution);


    /**
     * 三级审批任务监听
     */
    VerifyObject produceGmvTask(DelegateTask delegateTask);

    /**
     * 三级审批执行监听
     */
    VerifyObject produceGmvExe(DelegateExecution delegateExecution);

    /**
     * 签发任务监听
     */
    VerifyObject produceSignTask(DelegateTask delegateTask);

    /**
     * 签发执行监听
     */
    VerifyObject produceSignExe(DelegateExecution delegateExecution);

}
