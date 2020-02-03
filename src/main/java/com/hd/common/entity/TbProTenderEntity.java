package com.hd.common.entity;

import java.util.Date;

public class TbProTenderEntity {
    /**
     * 招标ID
     */
    private String tenderId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 招标编号
     */
    private String tenderNum;

    /**
     * 合约分类（1：工程类合约、2：采购类合约、3：服务类合约、4：土地类合约、5：其他类合约）
     */
    private String treatyType;

    /**
     * 承包方式（1：施工总承包、2：发包人发包专业合同、3：发包人自行发包专业）
     */
    private String undertakeType;

    /**
     * 招标名称
     */
    private String tenderName;

    /**
     * 计划开始时间
     */
    private Date planStartTime;

    /**
     * 计划结束时间
     */
    private Date planEndTime;

    /**
     * 合同类型（1：单价合同，2：总价合同，3：其他）
     */
    private String contractType;

    /**
     * 采购方式（1：公开招标，2：内部组织招标，3：联合招标，4：直接委托，5：竞争性谈判）
     */
    private String purchaserWay;

    /**
     * 目标成本
     */
    private Double targetCost;

    /**
     * 文件类型（1：招标控制价 2：招标标的 3：其他）
     */
    private String fileType;

    /**
     * 除税金额（%）
     */
    private Double exciseMoney;

    /**
     * 税率（%）
     */
    private Double taxRatio;

    /**
     * 税金
     */
    private Double taxMoney;

    /**
     * 含税金额
     */
    private Double withTaxMoney;

    /**
     * 备注
     */
    private String remark;

    /**
     * 招标文件路径
     */
    private String tenderFilePath;

    /**
     * 招标文件真实名称
     */
    private String tenderFileName;

    /**
     * 接受资料时间
     */
    private Date acceptFileTime;

    /**
     * 发送报告时间
     */
    private Date sendReportTime;

    /**
     * 报告真实路径
     */
    private String reportPath;

    /**
     * 报告真实名称
     */
    private String reportName;

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
     * 招标ID
     * @return tender_id 招标ID
     */
    public String getTenderId() {
        return tenderId;
    }

    /**
     * 招标ID
     * @param tenderId 招标ID
     */
    public void setTenderId(String tenderId) {
        this.tenderId = tenderId == null ? null : tenderId.trim();
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
     * 招标编号
     * @return tender_num 招标编号
     */
    public String getTenderNum() {
        return tenderNum;
    }

    /**
     * 招标编号
     * @param tenderNum 招标编号
     */
    public void setTenderNum(String tenderNum) {
        this.tenderNum = tenderNum == null ? null : tenderNum.trim();
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
     * 招标名称
     * @return tender_name 招标名称
     */
    public String getTenderName() {
        return tenderName;
    }

    /**
     * 招标名称
     * @param tenderName 招标名称
     */
    public void setTenderName(String tenderName) {
        this.tenderName = tenderName == null ? null : tenderName.trim();
    }

    /**
     * 计划开始时间
     * @return plan_start_time 计划开始时间
     */
    public Date getPlanStartTime() {
        return planStartTime;
    }

    /**
     * 计划开始时间
     * @param planStartTime 计划开始时间
     */
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    /**
     * 计划结束时间
     * @return plan_end_time 计划结束时间
     */
    public Date getPlanEndTime() {
        return planEndTime;
    }

    /**
     * 计划结束时间
     * @param planEndTime 计划结束时间
     */
    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
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
     * 文件类型（1：招标控制价 2：招标标的 3：其他）
     * @return file_type 文件类型（1：招标控制价 2：招标标的 3：其他）
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 文件类型（1：招标控制价 2：招标标的 3：其他）
     * @param fileType 文件类型（1：招标控制价 2：招标标的 3：其他）
     */
    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    /**
     * 除税金额（%）
     * @return excise_money 除税金额（%）
     */
    public Double getExciseMoney() {
        return exciseMoney;
    }

    /**
     * 除税金额（%）
     * @param exciseMoney 除税金额（%）
     */
    public void setExciseMoney(Double exciseMoney) {
        this.exciseMoney = exciseMoney;
    }

    /**
     * 税率（%）
     * @return tax_ratio 税率（%）
     */
    public Double getTaxRatio() {
        return taxRatio;
    }

    /**
     * 税率（%）
     * @param taxRatio 税率（%）
     */
    public void setTaxRatio(Double taxRatio) {
        this.taxRatio = taxRatio;
    }

    /**
     * 税金
     * @return tax_money 税金
     */
    public Double getTaxMoney() {
        return taxMoney;
    }

    /**
     * 税金
     * @param taxMoney 税金
     */
    public void setTaxMoney(Double taxMoney) {
        this.taxMoney = taxMoney;
    }

    /**
     * 含税金额
     * @return with_tax_money 含税金额
     */
    public Double getWithTaxMoney() {
        return withTaxMoney;
    }

    /**
     * 含税金额
     * @param withTaxMoney 含税金额
     */
    public void setWithTaxMoney(Double withTaxMoney) {
        this.withTaxMoney = withTaxMoney;
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
     * 招标文件路径
     * @return tender_file_path 招标文件路径
     */
    public String getTenderFilePath() {
        return tenderFilePath;
    }

    /**
     * 招标文件路径
     * @param tenderFilePath 招标文件路径
     */
    public void setTenderFilePath(String tenderFilePath) {
        this.tenderFilePath = tenderFilePath == null ? null : tenderFilePath.trim();
    }

    /**
     * 招标文件真实名称
     * @return tender_file_name 招标文件真实名称
     */
    public String getTenderFileName() {
        return tenderFileName;
    }

    /**
     * 招标文件真实名称
     * @param tenderFileName 招标文件真实名称
     */
    public void setTenderFileName(String tenderFileName) {
        this.tenderFileName = tenderFileName == null ? null : tenderFileName.trim();
    }

    /**
     * 接受资料时间
     * @return accept_file_time 接受资料时间
     */
    public Date getAcceptFileTime() {
        return acceptFileTime;
    }

    /**
     * 接受资料时间
     * @param acceptFileTime 接受资料时间
     */
    public void setAcceptFileTime(Date acceptFileTime) {
        this.acceptFileTime = acceptFileTime;
    }

    /**
     * 发送报告时间
     * @return send_report_time 发送报告时间
     */
    public Date getSendReportTime() {
        return sendReportTime;
    }

    /**
     * 发送报告时间
     * @param sendReportTime 发送报告时间
     */
    public void setSendReportTime(Date sendReportTime) {
        this.sendReportTime = sendReportTime;
    }

    /**
     * 报告真实路径
     * @return report_path 报告真实路径
     */
    public String getReportPath() {
        return reportPath;
    }

    /**
     * 报告真实路径
     * @param reportPath 报告真实路径
     */
    public void setReportPath(String reportPath) {
        this.reportPath = reportPath == null ? null : reportPath.trim();
    }

    /**
     * 报告真实名称
     * @return report_name 报告真实名称
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * 报告真实名称
     * @param reportName 报告真实名称
     */
    public void setReportName(String reportName) {
        this.reportName = reportName == null ? null : reportName.trim();
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