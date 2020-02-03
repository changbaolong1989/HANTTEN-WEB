package com.hd.base.enums;

import com.hd.common.util.Constant;

public enum ArchiveEnum implements ProcessEnum {

    StartEvent("StartEvent", "开始", "", 0, ""),
    SubmitArchive("SubmitArchive", "提交归档", "", 1, ""),
    AffirmArchive("AffirmArchive", "确认归档", Constant.DEPT_ID_XZ, 2, ""),
    EndEvent("EndEvent", "结束", "", 8, "");

    private String nodeKey;
    private String nodeName;
    private String departmentId;
    private int nodeSort;
    private String jobName;

    ArchiveEnum(String nodeKey, String nodeName, String departmentId, int nodeSort, String jobName) {
        this.nodeKey = nodeKey;
        this.nodeName = nodeName;
        this.departmentId = departmentId;
        this.nodeSort = nodeSort;
        this.jobName = jobName;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public static String getValue(String enumKey) {
        String name = null;
        for (ArchiveEnum e : ArchiveEnum.values()) {
            if (e.getNodeKey().equals(enumKey)) {
                name = e.getNodeName();
                break;
            }
        }
        return name;
    }

    public static String getDeptId(String enumKey) {
        String deptId = null;
        for (ArchiveEnum e : ArchiveEnum.values()) {
            if (e.getNodeKey().equals(enumKey)) {
                deptId = e.getDepartmentId();
                break;
            }
        }
        return deptId;
    }

    public static ArchiveEnum getEnum(String enumKey) {
        ArchiveEnum enu = null;
        for (ArchiveEnum e : ArchiveEnum.values()) {
            if (e.getNodeKey().equals(enumKey)) {
                enu = e;
                break;
            }
        }
        return enu;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public int getNodeSort() {
        return nodeSort;
    }

    public void setNodeSort(int nodeSort) {
        this.nodeSort = nodeSort;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public ArchiveEnum next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

}
