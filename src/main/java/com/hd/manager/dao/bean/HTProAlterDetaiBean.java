package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;

public class HTProAlterDetaiBean extends BaseBean {

    /**
     * 变更ID
     */
    private String alterId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 合同ID
     */
    private String contractId;

    private String contractNum;

    private String contractName;

    /**
     * 变更名称
     */
    private String alterName;

    /**
     * 专业（1：图纸会审、2：设计变更、3：变更洽商、4：现场签证）
     */
    private String professionalId;

    /**
     * 内容摘要
     */
    private String contentSummary;

    /**
     * 洽商日期
     */
    private Date talkDate;

    /**
     * 接收日期
     */
    private Date receiveDate;

    /**
     * 状态
     * A - 业主审批金额
     * B - 汉腾与承包商已核对且无争议的变更洽商金额
     * C - 汉腾与承包商已核对且有争议的变更洽商金额
     * D - 汉腾已审核完毕尚未与申报单位核对金额
     * E - 汉腾正在审核的变更金额
     * F - 承包商申报资料不全或尚未申报费用
     * G - 预计会发生，但无变更资料
     */
    private String sts;

    private Double addSubAmount;

    /**
     * 申报金额
     */
    private Double declareAmount;

    /**
     * 监理审核金额
     */
    private Double auditAmount;

    /**
     * 汉腾审核金额
     */
    private Double hanttenAmount;

    /**
     * 业主审批金额
     */
    private Double ownerAmount;

    /**
     * 批准文号
     */
    private String symbol;

    /**
     * 变更洽商费用分析(1：调差，2：业主要求，3：设计要求，4：法规要求，5：设计/合同错误或不足，6：现场不可预见得情况，7：深化设计/协调，8：一般修改，9：其他）
     */
    private String alterAnalyse;

    /**
     * 单体
     */
    private String singel;

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

    private String orderByClause;

    /**
     * 变更ID
     *
     * @return alter_id 变更ID
     */
    public String getAlterId() {
        return alterId;
    }

    /**
     * 变更ID
     *
     * @param alterId 变更ID
     */
    public void setAlterId(String alterId) {
        this.alterId = alterId == null ? null : alterId.trim();
    }

    /**
     * 项目ID
     *
     * @return project_id 项目ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 项目ID
     *
     * @param projectId 项目ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * 合同ID
     *
     * @return contract_id 合同ID
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * 合同ID
     *
     * @param contractId 合同ID
     */
    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    /**
     * 变更名称
     *
     * @return alter_name 变更名称
     */
    public String getAlterName() {
        return alterName;
    }

    /**
     * 变更名称
     *
     * @param alterName 变更名称
     */
    public void setAlterName(String alterName) {
        this.alterName = alterName == null ? null : alterName.trim();
    }

    /**
     * 专业（1：图纸会审、2：设计变更、3：变更洽商、4：现场签证）
     *
     * @return professional_id 专业（1：图纸会审、2：设计变更、3：变更洽商、4：现场签证）
     */
    public String getProfessionalId() {
        return professionalId;
    }

    /**
     * 专业（1：图纸会审、2：设计变更、3：变更洽商、4：现场签证）
     *
     * @param professionalId 专业（1：图纸会审、2：设计变更、3：变更洽商、4：现场签证）
     */
    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId == null ? null : professionalId.trim();
    }

    /**
     * 内容摘要
     *
     * @return content_summary 内容摘要
     */
    public String getContentSummary() {
        return contentSummary;
    }

    /**
     * 内容摘要
     *
     * @param contentSummary 内容摘要
     */
    public void setContentSummary(String contentSummary) {
        this.contentSummary = contentSummary == null ? null : contentSummary.trim();
    }

    /**
     * 洽商日期
     *
     * @return talk_date 洽商日期
     */
    public Date getTalkDate() {
        return talkDate;
    }

    /**
     * 洽商日期
     *
     * @param talkDate 洽商日期
     */
    public void setTalkDate(Date talkDate) {
        this.talkDate = talkDate;
    }

    /**
     * 接收日期
     *
     * @return receive_date 接收日期
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * 接收日期
     *
     * @param receiveDate 接收日期
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 状态
     * A - 业主审批金额
     * B - 汉腾与承包商已核对且无争议的变更洽商金额
     * C - 汉腾与承包商已核对且有争议的变更洽商金额
     * D - 汉腾已审核完毕尚未与申报单位核对金额
     * E - 汉腾正在审核的变更金额
     * F - 承包商申报资料不全或尚未申报费用
     * G - 预计会发生，但无变更资料
     *
     * @return sts 状态
     * A - 业主审批金额
     * B - 汉腾与承包商已核对且无争议的变更洽商金额
     * C - 汉腾与承包商已核对且有争议的变更洽商金额
     * D - 汉腾已审核完毕尚未与申报单位核对金额
     * E - 汉腾正在审核的变更金额
     * F - 承包商申报资料不全或尚未申报费用
     * G - 预计会发生，但无变更资料
     */
    public String getSts() {
        return sts;
    }

    /**
     * 状态
     * A - 业主审批金额
     * B - 汉腾与承包商已核对且无争议的变更洽商金额
     * C - 汉腾与承包商已核对且有争议的变更洽商金额
     * D - 汉腾已审核完毕尚未与申报单位核对金额
     * E - 汉腾正在审核的变更金额
     * F - 承包商申报资料不全或尚未申报费用
     * G - 预计会发生，但无变更资料
     *
     * @param sts 状态
     *            A - 业主审批金额
     *            B - 汉腾与承包商已核对且无争议的变更洽商金额
     *            C - 汉腾与承包商已核对且有争议的变更洽商金额
     *            D - 汉腾已审核完毕尚未与申报单位核对金额
     *            E - 汉腾正在审核的变更金额
     *            F - 承包商申报资料不全或尚未申报费用
     *            G - 预计会发生，但无变更资料
     */
    public void setSts(String sts) {
        this.sts = sts == null ? null : sts.trim();
    }

    /**
     * 申报金额
     *
     * @return declare_amount 申报金额
     */
    public Double getDeclareAmount() {
        return declareAmount;
    }

    /**
     * 申报金额
     *
     * @param declareAmount 申报金额
     */
    public void setDeclareAmount(Double declareAmount) {
        this.declareAmount = declareAmount;
    }

    /**
     * 监理审核金额
     *
     * @return audit_amount 监理审核金额
     */
    public Double getAuditAmount() {
        return auditAmount;
    }

    /**
     * 监理审核金额
     *
     * @param auditAmount 监理审核金额
     */
    public void setAuditAmount(Double auditAmount) {
        this.auditAmount = auditAmount;
    }

    /**
     * 汉腾审核金额
     *
     * @return hantten_amount 汉腾审核金额
     */
    public Double getHanttenAmount() {
        return hanttenAmount;
    }

    /**
     * 汉腾审核金额
     *
     * @param hanttenAmount 汉腾审核金额
     */
    public void setHanttenAmount(Double hanttenAmount) {
        this.hanttenAmount = hanttenAmount;
    }

    /**
     * 业主审批金额
     *
     * @return owner_amount 业主审批金额
     */
    public Double getOwnerAmount() {
        return ownerAmount;
    }

    /**
     * 业主审批金额
     *
     * @param ownerAmount 业主审批金额
     */
    public void setOwnerAmount(Double ownerAmount) {
        this.ownerAmount = ownerAmount;
    }

    /**
     * 批准文号
     *
     * @return symbol 批准文号
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 批准文号
     *
     * @param symbol 批准文号
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    /**
     * 变更洽商费用分析(1：调差，2：业主要求，3：设计要求，4：法规要求，5：设计/合同错误或不足，6：现场不可预见得情况，7：深化设计/协调，8：一般修改，9：其他）
     *
     * @return alter_analyse 变更洽商费用分析(1：调差，2：业主要求，3：设计要求，4：法规要求，5：设计/合同错误或不足，6：现场不可预见得情况，7：深化设计/协调，8：一般修改，9：其他）
     */
    public String getAlterAnalyse() {
        return alterAnalyse;
    }

    /**
     * 变更洽商费用分析(1：调差，2：业主要求，3：设计要求，4：法规要求，5：设计/合同错误或不足，6：现场不可预见得情况，7：深化设计/协调，8：一般修改，9：其他）
     *
     * @param alterAnalyse 变更洽商费用分析(1：调差，2：业主要求，3：设计要求，4：法规要求，5：设计/合同错误或不足，6：现场不可预见得情况，7：深化设计/协调，8：一般修改，9：其他）
     */
    public void setAlterAnalyse(String alterAnalyse) {
        this.alterAnalyse = alterAnalyse == null ? null : alterAnalyse.trim();
    }

    /**
     * 单体
     *
     * @return singel 单体
     */
    public String getSingel() {
        return singel;
    }

    /**
     * 单体
     *
     * @param singel 单体
     */
    public void setSingel(String singel) {
        this.singel = singel == null ? null : singel.trim();
    }

    /**
     * 备注
     *
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 创建时间
     *
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 创建人
     *
     * @return create_user_id 创建人
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     *
     * @param createUserId 创建人
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    /**
     * 修改时间
     *
     * @return update_date 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 修改人
     *
     * @return update_user_id 修改人
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 修改人
     *
     * @param updateUserId 修改人
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * 是否删除
     *
     * @return is_del_flg 是否删除
     */
    public String getIsDelFlg() {
        return isDelFlg;
    }

    /**
     * 是否删除
     *
     * @param isDelFlg 是否删除
     */
    public void setIsDelFlg(String isDelFlg) {
        this.isDelFlg = isDelFlg == null ? null : isDelFlg.trim();
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public Double getAddSubAmount() {
        return addSubAmount;
    }

    public void setAddSubAmount(Double addSubAmount) {
        this.addSubAmount = addSubAmount;
    }
}
