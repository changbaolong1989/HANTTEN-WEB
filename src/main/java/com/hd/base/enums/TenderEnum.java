package com.hd.base.enums;

import com.hd.common.util.Constant;

/**
 * 投标流程节点枚举
 */
public enum TenderEnum implements ProcessEnum {

    StartEvent("StartEvent", "开始", "", 0, ""),
    SubmitVerify("SubmitVerify", "提交审核", Constant.DEPT_ID_SC, 1, ""),
    ManagerVerify("ManagerVerify", "一级审核", Constant.DEPT_ID_SC, 2, "部门经理"),
    QualityVerify("QualityVerify", "二级审核", Constant.DEPT_ID_QUALITY, 3, "质控部组员"),
    GenerManagerVerify("GenerManagerVerify", "三级审核", Constant.DEPT_ID_QUALITY, 4, "质控部副总经理"),
    EndEvent("EndEvent", "结束", "", 8, "");

    private String nodeKey;
    private String nodeName;
    private String departmentId;
    private int nodeSort;
    private String jobName;

    TenderEnum(String nodeKey, String nodeName, String departmentId, int nodeSort, String jobName) {
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
        for (TenderEnum e : TenderEnum.values()) {
            if (e.getNodeKey().equals(enumKey)) {
                name = e.getNodeName();
                break;
            }
        }
        return name;
    }

    public static String getDeptId(String enumKey) {
        String deptId = null;
        for (TenderEnum e : TenderEnum.values()) {
            if (e.getNodeKey().equals(enumKey)) {
                deptId = e.getDepartmentId();
                break;
            }
        }
        return deptId;
    }

    public static TenderEnum getEnum(String enumKey) {
        TenderEnum enu = null;
        for (TenderEnum e : TenderEnum.values()) {
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

    public TenderEnum next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

}
