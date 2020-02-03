package com.hd.base.enums;

import com.hd.common.util.SpringUtils;
import com.hd.processes.bench.WorkBench;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程字典枚举类
 */
public enum ProcessDictEnum {

    SealApply("1", "请章", "SealApply", ""),
    Tender("2", "投标", "Tender", "com.hd.processes.bench.WbTender"),
    CreateProjectProcess("3", "创建项目", "CreateProjectProcess", "com.hd.processes.bench.WbCreateProject"),
    WriteContract("4", "合同编撰", "WriteContract", "com.hd.processes.bench.WbWriteContract"),
    Task_Reedit("5", "任务_重新编辑", "Task_Reedit", "com.hd.processes.bench.WbTaskReedit"),
    CashApply("6", "请款", "CashApply", "com.hd.processes.bench.WbCashApply"),
    Task_A3("7", "任务_A3审批", "Task_A3", "com.hd.processes.bench.WbTaskA3"),
    Task("8", "任务_成果文件审批", "Task", "com.hd.processes.bench.WbTask"),
    Archive("9", "归档", "Archive", "com.hd.processes.bench.WbArchive");

    private String processDictId;
    private String processDictName;
    private String processKey;
    private String className;
    private static Logger logger = LoggerFactory.getLogger(ProcessDictEnum.class);

    ProcessDictEnum(String processDictId, String processDictName, String processKey, String className) {
        this.processDictId = processDictId;
        this.processDictName = processDictName;
        this.processKey = processKey;
        this.className = className;
    }

    public static WorkBench getInstance(String processKey) {
        WorkBench workBench = null;
        try {
            ProcessDictEnum processDictEnum = ProcessDictEnum.valueOf(processKey);
            String clazz = processDictEnum.getClassName();
            if(StringUtils.isNotBlank(clazz)){
                workBench = (WorkBench) SpringUtils.getBean(Class.forName(clazz));
            }
        } catch (IllegalArgumentException e) {
            logger.error("没有对应的流程数据");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return workBench;
    }

    public String getProcessDictId() {
        return processDictId;
    }

    public void setProcessDictId(String processDictId) {
        this.processDictId = processDictId;
    }

    public String getProcessDictName() {
        return processDictName;
    }

    public void setProcessDictName(String processDictName) {
        this.processDictName = processDictName;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
