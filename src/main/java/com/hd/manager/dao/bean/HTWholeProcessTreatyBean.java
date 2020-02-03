package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;
import jdk.internal.dynalink.linker.LinkerServices;

import java.util.Date;
import java.util.List;

public class HTWholeProcessTreatyBean extends BaseBean {

    /**
     * 合约ID
     */
    private String treatyId;

    /**
     *项目ID
     */
    private String projectId;

    /**
     *合约编号
     */
    private String treatyNum;

    /**
     *合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
     */
    private String treatyType;

    /**
     *承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    private String undertakeType;

    /**
     *合约名称
     */
    private String treatyName;

    /**
     * 合约内容及范围
     */
    private String treatyContent;

    /**
     *目标成本
     */
    private Double targetCost;

    /**
     *合同类型（1：单价合同，2：总价合同，3：其他）
     */
    private String contractType;

    /**
     *合同确定方式（1：总承包，2：指定供货，3：指定分包，4：甲供，5：直接发包，6：三方协议）
     */
    private String contractWay;

    /**
     *采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
     */
    private String purchaserWay;

    /**
     *备注
     */
    private String remark;

    /**
     *子集(供Excel导出使用)
     */
    private List<HTWholeProcessTreatyBean> childList;



    /*------------------------get/set方法--------------------------*/

    public String getTreatyId() {
        return treatyId;
    }

    public void setTreatyId(String treatyId) {
        this.treatyId = treatyId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTreatyNum() {
        return treatyNum;
    }

    public void setTreatyNum(String treatyNum) {
        this.treatyNum = treatyNum;
    }

    public String getTreatyType() {
        return treatyType;
    }

    public void setTreatyType(String treatyType) {
        this.treatyType = treatyType;
    }

    public String getUndertakeType() {
        return undertakeType;
    }

    public void setUndertakeType(String undertakeType) {
        this.undertakeType = undertakeType;
    }

    public String getTreatyName() {
        return treatyName;
    }

    public void setTreatyName(String treatyName) {
        this.treatyName = treatyName;
    }

    public String getTreatyContent() {
        return treatyContent;
    }

    public void setTreatyContent(String treatyContent) {
        this.treatyContent = treatyContent;
    }

    public Double getTargetCost() {
        return targetCost;
    }

    public void setTargetCost(Double targetCost) {
        this.targetCost = targetCost;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractWay() {
        return contractWay;
    }

    public void setContractWay(String contractWay) {
        this.contractWay = contractWay;
    }

    public String getPurchaserWay() {
        return purchaserWay;
    }

    public void setPurchaserWay(String purchaserWay) {
        this.purchaserWay = purchaserWay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<HTWholeProcessTreatyBean> getChildList() {
        return childList;
    }

    public void setChildList(List<HTWholeProcessTreatyBean> childList) {
        this.childList = childList;
    }
}
