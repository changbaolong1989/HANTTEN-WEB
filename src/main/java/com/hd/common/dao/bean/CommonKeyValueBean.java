package com.hd.common.dao.bean;

/**
 * 通用键值Bean
 */
public class CommonKeyValueBean {

    /** 键 (用于提交项)*/
    private String key;
    /** 值 (用于展示项)*/
    private String value;

    /**
     * key
     *
     * @return key 项目描述（略）
     */
    public String getKey() {
        return key;
    }

    /**
     * key
     *
     * @param key 项目描述（略）
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * value
     *
     * @return value 项目描述（略）
     */
    public String getValue() {
        return value;
    }

    /**
     * value
     *
     * @param value 项目描述（略）
     */
    public void setValue(String value) {
        this.value = value;
    }
}
