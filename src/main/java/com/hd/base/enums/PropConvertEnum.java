package com.hd.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 属性转换枚举类
 */
public enum PropConvertEnum {

    isPagingSeal("isPagingSeal", getIsNotMap()),
    verifyState("verifyState", getVerifyStateMap());

    private String propertyName;
    private Map<String, String> map;
    private static Map<String, String> isNotMap = null;
    private static Map<String, String> verifyStateMap = null;

    PropConvertEnum(String propertyName, Map<String, String> map) {
        this.propertyName = propertyName;
        this.map = map;
    }

    //是否map 1是 0否
    private static Map<String, String> getIsNotMap() {
        if (isNotMap == null) {
            isNotMap = new HashMap<>();
            isNotMap.put("1", "是");
            isNotMap.put("0", "否");
        }
        return isNotMap;
    }

    //审核状态map 1已通过 0未通过 2审批中
    private static Map<String, String> getVerifyStateMap() {
        if (verifyStateMap == null) {
            verifyStateMap = new HashMap<>();
            verifyStateMap.put("1", "已通过");
            verifyStateMap.put("0", "未通过");
            verifyStateMap.put("2", "审批中");
        }
        return verifyStateMap;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public static String getPropText(String enumKey, String mapKey) {
        String text = null;
        for (PropConvertEnum e : PropConvertEnum.values()) {
            if (e.getPropertyName().equals(enumKey)) {
                text = e.getMap().get(mapKey);
                break;
            }
        }
        return text;
    }

}
