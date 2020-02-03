package com.hd.processes.bench;

import java.util.Date;
import java.util.Set;

public class WorkFlow {

    //流程定义key
    private String processDefinitionKey;

    //业务主键
    private Set<String> bizKey;

    //前一节点办理人ID
    private String preNodeHandlerId;

    //前一节点办理人Name
    private String preNodeHandlerName;

    //最新办理时间
    private Date handleDate;

    //部门ID
    private String departmentId;

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public Set<String> getBizKey() {
        return bizKey;
    }

    public void setBizKey(Set<String> bizKey) {
        this.bizKey = bizKey;
    }

    public String getPreNodeHandlerId() {
        return preNodeHandlerId;
    }

    public void setPreNodeHandlerId(String preNodeHandlerId) {
        this.preNodeHandlerId = preNodeHandlerId;
    }

    public String getPreNodeHandlerName() {
        return preNodeHandlerName;
    }

    public void setPreNodeHandlerName(String preNodeHandlerName) {
        this.preNodeHandlerName = preNodeHandlerName;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
