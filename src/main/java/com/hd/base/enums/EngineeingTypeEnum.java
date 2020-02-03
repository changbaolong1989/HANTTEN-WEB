package com.hd.base.enums;

public enum EngineeingTypeEnum {

    ONE("0", "房建"),
    TWO("1", "工业建筑"),
    THREE("2", "公共建筑"),
    FOUR("3", "园林绿化"),
    FIVE("4", "安装"),
    SEX("5", "市政"),
    SEVEN("6", "交通");

    /**
     * 类别id
     */
    private String typeId;

    /**
     * 类别名称
     */
    private String typeName;

    EngineeingTypeEnum(String typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static String getTypeName(String typeIdStr) {

        String retVal = "";

        String[] strArr = typeIdStr.split(",");

        if (strArr.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (EngineeingTypeEnum value : EngineeingTypeEnum.values()) {
                for (String str : strArr) {
                    if (value.getTypeId().equals(str)) {
                        sb.append(value.getTypeName()).append(",");
                        break;
                    }
                }
            }
            retVal = sb.toString();
            // 如果末尾包含“，”，删除
            if (retVal.endsWith(",")) {
                retVal = retVal.substring(0, retVal.length() - 1);
            }
        }
        return retVal;
    }

}