package com.hd.common.entity;

import java.util.Date;

public class TbProTreatyEntity {
    /**
     * 合约ID
     */
    private String treatyId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 合约编号
     */
    private String treatyNum;

    /**
     * 合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
     */
    private String treatyType;

    /**
     * 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    private String undertakeType;

    /**
     * 合约名称
     */
    private String treatyName;

    /**
     * 合约内容及范围
     */
    private String treatyContent;

    /**
     * 目标成本
     */
    private Double targetCost;

    /**
     * 合同类型（1：单价合同，2：总价合同，3：其他）
     */
    private String contractType;

    /**
     * 合同确定方式（1：总承包，2：指定供货，3：指定分包，4：甲供，5：直接发包，6：三方协议）
     */
    private String contractWay;

    /**
     * 采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
     */
    private String purchaserWay;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改人
     */
    private String updateUserId;

    /**
     * 是否删除
     */
    private String isDelFlg;

    /**
     * 合约ID
     * @return treaty_id 合约ID
     */
    public String getTreatyId() {
        return treatyId;
    }

    /**
     * 合约ID
     * @param treatyId 合约ID
     */
    public void setTreatyId(String treatyId) {
        this.treatyId = treatyId == null ? null : treatyId.trim();
    }

    /**
     * 项目ID
     * @return project_id 项目ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 项目ID
     * @param projectId 项目ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * 合约编号
     * @return treaty_num 合约编号
     */
    public String getTreatyNum() {
        return treatyNum;
    }

    /**
     * 合约编号
     * @param treatyNum 合约编号
     */
    public void setTreatyNum(String treatyNum) {
        this.treatyNum = treatyNum == null ? null : treatyNum.trim();
    }

    /**
     * 合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
     * @return treaty_type 合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
     */
    public String getTreatyType() {
        return treatyType;
    }

    /**
     * 合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
     * @param treatyType 合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
     */
    public void setTreatyType(String treatyType) {
        this.treatyType = treatyType == null ? null : treatyType.trim();
    }

    /**
     * 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     * @return undertake_type 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    public String getUndertakeType() {
        return undertakeType;
    }

    /**
     * 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     * @param undertakeType 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    public void setUndertakeType(String undertakeType) {
        this.undertakeType = undertakeType == null ? null : undertakeType.trim();
    }

    /**
     * 合约名称
     * @return treaty_name 合约名称
     */
    public String getTreatyName() {
        return treatyName;
    }

    /**
     * 合约名称
     * @param treatyName 合约名称
     */
    public void setTreatyName(String treatyName) {
        this.treatyName = treatyName == null ? null : treatyName.trim();
    }

    /**
     * 合约内容及范围
     * @return treaty_content 合约内容及范围
     */
    public String getTreatyContent() {
        return treatyContent;
    }

    /**
     * 合约内容及范围
     * @param treatyContent 合约内容及范围
     */
    public void setTreatyContent(String treatyContent) {
        this.treatyContent = treatyContent == null ? null : treatyContent.trim();
    }

    /**
     * 目标成本
     * @return target_cost 目标成本
     */
    public Double getTargetCost() {
        return targetCost;
    }

    /**
     * 目标成本
     * @param targetCost 目标成本
     */
    public void setTargetCost(Double targetCost) {
        this.targetCost = targetCost;
    }

    /**
     * 合同类型（1：单价合同，2：总价合同，3：其他）
     * @return contract_type 合同类型（1：单价合同，2：总价合同，3：其他）
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * 合同类型（1：单价合同，2：总价合同，3：其他）
     * @param contractType 合同类型（1：单价合同，2：总价合同，3：其他）
     */
    public void setContractType(String contractType) {
        this.contractType = contractType == null ? null : contractType.trim();
    }

    /**
     * 合同确定方式（1：总承包，2：指定供货，3：指定分包，4：甲供，5：直接发包，6：三方协议）
     * @return contract_way 合同确定方式（1：总承包，2：指定供货，3：指定分包，4：甲供，5：直接发包，6：三方协议）
     */
    public String getContractWay() {
        return contractWay;
    }

    /**
     * 合同确定方式（1：总承包，2：指定供货，3：指定分包，4：甲供，5：直接发包，6：三方协议）
     * @param contractWay 合同确定方式（1：总承包，2：指定供货，3：指定分包，4：甲供，5：直接发包，6：三方协议）
     */
    public void setContractWay(String contractWay) {
        this.contractWay = contractWay == null ? null : contractWay.trim();
    }

    /**
     * 采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
     * @return purchaser_way 采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
     */
    public String getPurchaserWay() {
        return purchaserWay;
    }

    /**
     * 采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
     * @param purchaserWay 采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
     */
    public void setPurchaserWay(String purchaserWay) {
        this.purchaserWay = purchaserWay == null ? null : purchaserWay.trim();
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 创建时间
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 创建人
     * @return create_user_id 创建人
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     * @param createUserId 创建人
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    /**
     * 修改时间
     * @return update_date 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 修改时间
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 修改人
     * @return update_user_id 修改人
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 修改人
     * @param updateUserId 修改人
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * 是否删除
     * @return is_del_flg 是否删除
     */
    public String getIsDelFlg() {
        return isDelFlg;
    }

    /**
     * 是否删除
     * @param isDelFlg 是否删除
     */
    public void setIsDelFlg(String isDelFlg) {
        this.isDelFlg = isDelFlg == null ? null : isDelFlg.trim();
    }
}