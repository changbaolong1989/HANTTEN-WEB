package com.hd.common.entity;

public class TbJobProcessNodeRelationEntity {
    /**
     * 节点Key
     */
    private String nodeId;

    /**
     * 
     */
    private String departmentId;

    /**
     * 
     */
    private String jobId;

    /**
     * 流程字典ID
     */
    private String processDictId;

    /**
     * 
     */
    private String uuid;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点Key
     * @return node_id 节点Key
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * 节点Key
     * @param nodeId 节点Key
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    /**
     * 
     * @return department_id 
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * 
     * @param departmentId 
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    /**
     * 
     * @return job_id 
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 
     * @param jobId 
     */
    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    /**
     * 流程字典ID
     * @return process_dict_id 流程字典ID
     */
    public String getProcessDictId() {
        return processDictId;
    }

    /**
     * 流程字典ID
     * @param processDictId 流程字典ID
     */
    public void setProcessDictId(String processDictId) {
        this.processDictId = processDictId == null ? null : processDictId.trim();
    }

    /**
     * 
     * @return uuid 
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 
     * @param uuid 
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * 流程名称
     * @return process_name 流程名称
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * 流程名称
     * @param processName 流程名称
     */
    public void setProcessName(String processName) {
        this.processName = processName == null ? null : processName.trim();
    }

    /**
     * 节点名称
     * @return node_name 节点名称
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * 节点名称
     * @param nodeName 节点名称
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }
}