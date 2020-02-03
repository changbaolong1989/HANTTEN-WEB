package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;


/**
 * 权限VO
 *
 * @author LH
 * Created in 2019/9/23 10:31
 */
public class HTUserPowerVO extends BaseVO {

    /**
     * 权限ID
     */
    private String powerId;

    /**
     * 权限标识
     */
    private String powerKey;

    /**
     * 权限名称
     */
    private String powerName;

    /**
     * 权限规则标识
     */
    private String powerRuleKey;

    /**
     * 权限规则名称
     */
    private String powerRuleName;

    /**
     * 模块所属
     */
    private String moduleBelong;


    /*-----------------------------get/set-----------------------------------*/
    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId;
    }

    public String getPowerKey() {
        return powerKey;
    }

    public void setPowerKey(String powerKey) {
        this.powerKey = powerKey;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerRuleKey() {
        return powerRuleKey;
    }

    public void setPowerRuleKey(String powerRuleKey) {
        this.powerRuleKey = powerRuleKey;
    }

    public String getPowerRuleName() {
        return powerRuleName;
    }

    public void setPowerRuleName(String powerRuleName) {
        this.powerRuleName = powerRuleName;
    }

    public String getModuleBelong() {
        return moduleBelong;
    }

    public void setModuleBelong(String moduleBelong) {
        this.moduleBelong = moduleBelong;
    }
}
