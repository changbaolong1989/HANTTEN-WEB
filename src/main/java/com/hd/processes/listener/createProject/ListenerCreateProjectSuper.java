package com.hd.processes.listener.createProject;

import com.hd.processes.listener.abs.AbsActTaskListener;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public abstract class ListenerCreateProjectSuper extends AbsActTaskListener {

    static final String STATE_ASSIGNED = "3";

    /**
     * 保存审批历史记录
     */
    @Override
    public void saveApproveRecord(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables) {

    }

}
