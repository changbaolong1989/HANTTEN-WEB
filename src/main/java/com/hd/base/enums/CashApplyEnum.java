package com.hd.base.enums;

import com.hd.common.util.Constant;

public enum CashApplyEnum implements ProcessEnum {

    StartEvent("StartEvent", "开始", "", 0, ""),
    SubmitVerify("SubmitVerify", "提交审核", "", 1, ""),
    ManagerVerify("ManagerVerify", "经理审核", "", 2, "部门经理"),
    QualityVerify("QualityVerify", "质检部审核", Constant.DEPT_ID_QUALITY, 3, "质控部组员"),
    GenerManagerVerify("GenerManagerVerify", "质检部经理审核", Constant.DEPT_ID_QUALITY, 4, "质控部副总经理"),
    FinanceInvoice("FinanceInvoice", "财务开具发票", Constant.DEPT_ID_CW, 5, ""),
    IssueInvoice("IssueInvoice", "上传发票", Constant.DEPT_ID_CW, 56, ""),
    CheckCash("CheckCash", "核对到款", Constant.DEPT_ID_CW, 6, ""),
    EndEvent("EndEvent", "结束", "", 8, "");

    private String nodeKey;
    private String nodeName;
    private String departmentId;
    private int nodeSort;
    private String jobName;

    CashApplyEnum(String nodeKey, String nodeName, String departmentId, int nodeSort, String jobName) {
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
        for (CashApplyEnum e : CashApplyEnum.values()) {
            if (e.getNodeKey().equals(enumKey)) {
                name = e.getNodeName();
                break;
            }
        }
        return name;
    }

    public static String getDeptId(String enumKey) {
        String deptId = null;
        for (CashApplyEnum e : CashApplyEnum.values()) {
            if (e.getNodeKey().equals(enumKey)) {
                deptId = e.getDepartmentId();
                break;
            }
        }
        return deptId;
    }

    public static CashApplyEnum getEnum(String enumKey) {
        CashApplyEnum enu = null;
        for (CashApplyEnum e : CashApplyEnum.values()) {
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

    public CashApplyEnum next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

}
