package com.hd.common.vo;

import com.hd.base.vo.BaseVO;

/**
 * 页面通用键值
 *
 * @author JLF
 * Created in 2019/1/24 16:12
 */
public class CommonKeyValueVO extends BaseVO {

    /**
     * 键（用于提交项）
     */
    private String key;

    /**
     * 值（用于展示项）
     */
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
