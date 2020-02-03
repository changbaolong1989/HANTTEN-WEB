package com.hd.base.enums;

import com.hd.common.util.Constant;

public enum WriteContractEnum implements ProcessEnum {

    StartEvent("StartEvent", "开始", "", 0, ""),
    SubmitVerify("SubmitVerify", "提交审核", "", 1, ""),
    ManagerVerify("ManagerVerify", "部门经理审核", "", 2, "部门经理"),
    QualityVerify("QualityVerify", "质检部审核", Constant.DEPT_ID_QUALITY, 3, "质控部组员"),
    GenerManagerVerify("GenerManagerVerify", "质检部经理审核", Constant.DEPT_ID_QUALITY, 4, "质控部副总经理"),
    Signer("Signer", "签发人签发", "", 5, "签发人"),
    Seal("Seal", "盖章", Constant.DEPT_ID_XZ, 6, ""),
    Archive("Archive", "归档", Constant.DEPT_ID_XZ, 7, ""),
    EndEvent("EndEvent", "结束", "", 8, "");

    private String nodeKey;
    private String nodeName;
    private String departmentId;
    private int nodeSort;
    private String jobName;

    WriteContractEnum(String nodeKey, String nodeName, String departmentId, int nodeSort, String jobName) {
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
        for (WriteContractEnum e : WriteContractEnum.values()) {
            if (e.getNodeKey().equals(enumKey)) {
                name = e.getNodeName();
                break;
            }
        }
        return name;
    }

    public static String getDeptId(String enumKey) {
        String deptId = null;
        for (WriteContractEnum e : WriteContractEnum.values()) {
            if (e.getNodeKey().equals(enumKey)) {
                deptId = e.getDepartmentId();
                break;
            }
        }
        return deptId;
    }

    public static WriteContractEnum getEnum(String enumKey) {
        WriteContractEnum enu = null;
        for (WriteContractEnum e : WriteContractEnum.values()) {
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

    public WriteContractEnum next() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}
