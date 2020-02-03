package com.hd.processes.listener.archive;

import com.hd.processes.listener.abs.AbsActTaskListener;
import org.activiti.engine.delegate.DelegateExecution;

import java.util.Date;
import java.util.Map;

public class ListenerArchiveSuper extends AbsActTaskListener {

    static final String IS_DONE = "1";

    /**
     * 保存审批历史记录
     */
    @Override
    public void saveApproveRecord(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables) {

    }
}
