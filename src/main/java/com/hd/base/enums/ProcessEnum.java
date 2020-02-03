package com.hd.base.enums;

public interface ProcessEnum {

    String getNodeKey();

    int getNodeSort();

    String getNodeName();

    String getDepartmentId();

    ProcessEnum next();

    String getJobName();

}
