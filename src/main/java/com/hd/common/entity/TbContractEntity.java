package com.hd.common.entity;

import java.util.Date;

public class TbContractEntity {
    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 流程实例ID
     */
    private String processInstId;

    /**
     * 委托任务Key
     */
    private String delegateTaskKey;

    /**
     * 委托任务ID
     */
    private String delegateTaskId;

    /**
     * 提交节点
     */
    private String submitNode;

    /**
     * 提审人ID
     */
    private String applyUserId;

    /**
     * 提审人
     */
    private String applyUserName;

    /**
     * 提审时间
     */
    private Date applyTime;

    /**
     * 审批人ID
     */
    private String approveUserId;

    /**
     * 审批人
     */
    private String approveUserName;

    /**
     * 审批时间
     */
    private Date approveTime;

    /**
     * 是否可以撤回（0：否 1：是）
     */
    private String isCanRecall;

    /**
     * 审批状态 (0.未通过 1.通过 2.审核中 3,已分配)
     */
    private String verifyState;

    /**
     * 当前流程状态
     */
    private String currentState;

    /**
     * 流程完成时间
     */
    private Date completeTime;

    /**
     * 已收入
     */
    private String incomeReceived;

    /**
     * 合同金额
     */
    private String contractAmount;

    /**
     * 负责人
     */
    private String dutyPeople;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 职位ID
     */
    private String jobId;

    /**
     * 部门ID(根据部门ID可以查询签发人)
     */
    private String departmentId;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 咨询业务类型（0：造价咨询 1：招标代理 2：项目管理(监理)）
     */
    private String counselBusinessType;

    /**
     * 咨询类型阶段
     */
    private String counselTypePeriod;

    /**
     * 项目地点
     */
    private String projectAddress;

    /**
     * 项目性质（@0@：新建 @1@：扩展 @2@：改建）
     */
    private String projectType;

    /**
     * 委托人
     */
    private String trusterName;

    /**
     * 委托人性质（@0@：政府部门 @1@：国企 @2@：上市公司 @3@：私企 @4@：其他）
     */
    private String trusterType;

    /**
     * 投资金额
     */
    private String investedAmount;

    /**
     * 是否暂定（0：否 1：是）
     */
    private String isProvisional;

    /**
     * 委托方负责人
     */
    private String trusterDutyUser;

    /**
     * 委托方负责人电话
     */
    private String trusterDutyPhone;

    /**
     * 咨询人
     */
    private String consultUser;

    /**
     * 第三方
     */
    private String thirdParty;

    /**
     * 拟定人
     */
    private String planner;

    /**
     * 合同性质（@0@ 双方合同 @1@ 三方合同 @2@ 补充合同）
     */
    private String contractNature;

    /**
     * 确定方式（@0@ 招标 @1@ 财政委托 @2@ 商务洽谈 @3@ 战略合作 @4@ 法院委托 @5@ 其他）
     */
    private String determinateMethod;

    /**
     * 咨询合同签订日期
     */
    private Date contractDate;

    /**
     * 拟定合同金额是否暂定（0:否 1：是）
     */
    private String isValueProvisional;

    /**
     * 其他情况
     */
    private String otherThings;

    /**
     * 合同类型(0:自定义合同 1：模板合同)
     */
    private String contractType;

    /**
     * 模板合同（0：造价咨询合同 1：建设工程招标代理合同 2：工程管理咨询服务合同 3：监理合同）
     */
    private String formworkType;

    /**
     * 自定义合同路径
     */
    private String contractFilePath;

    /**
     * 自定义合同真实名字
     */
    private String contractFileName;

    /**
     * 档案编号
     */
    private String fileNumber;

    /**
     * 预计合同总金额
     */
    private Double predictTotal;

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
     * 扉页咨询人
     */
    private String titlePageConsultant;

    /**
     * 法务意见
     */
    private String legalOpinion;

    /**
     * 合同盖章类型(1.委托代理人章2.法定代表人章)
     */
    private String contractSealType;

    /**
     * 合作公司
     */
    private String cooperateCompany;

    /**
     * (合作合同 0：否 1：是)
     */
    private String cooperateContract;

    /**
     * 作废标识
     */
    private String isInvalidFlag;

    /**
     * 作废原因
     */
    private String invalidReason;

    /**
     * 作废人ID
     */
    private String invalidUserId;

    /**
     * 模板内容
     */
    private String formworkContent;

    /**
     * 合同ID
     * @return contract_id 合同ID
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * 合同ID
     * @param contractId 合同ID
     */
    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    /**
     * 合同编号
     * @return contract_num 合同编号
     */
    public String getContractNum() {
        return contractNum;
    }

    /**
     * 合同编号
     * @param contractNum 合同编号
     */
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum == null ? null : contractNum.trim();
    }

    /**
     * 流程实例ID
     * @return process_inst_id 流程实例ID
     */
    public String getProcessInstId() {
        return processInstId;
    }

    /**
     * 流程实例ID
     * @param processInstId 流程实例ID
     */
    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId == null ? null : processInstId.trim();
    }

    /**
     * 委托任务Key
     * @return delegate_task_key 委托任务Key
     */
    public String getDelegateTaskKey() {
        return delegateTaskKey;
    }

    /**
     * 委托任务Key
     * @param delegateTaskKey 委托任务Key
     */
    public void setDelegateTaskKey(String delegateTaskKey) {
        this.delegateTaskKey = delegateTaskKey == null ? null : delegateTaskKey.trim();
    }

    /**
     * 委托任务ID
     * @return delegate_task_id 委托任务ID
     */
    public String getDelegateTaskId() {
        return delegateTaskId;
    }

    /**
     * 委托任务ID
     * @param delegateTaskId 委托任务ID
     */
    public void setDelegateTaskId(String delegateTaskId) {
        this.delegateTaskId = delegateTaskId == null ? null : delegateTaskId.trim();
    }

    /**
     * 提交节点
     * @return submit_node 提交节点
     */
    public String getSubmitNode() {
        return submitNode;
    }

    /**
     * 提交节点
     * @param submitNode 提交节点
     */
    public void setSubmitNode(String submitNode) {
        this.submitNode = submitNode == null ? null : submitNode.trim();
    }

    /**
     * 提审人ID
     * @return apply_user_id 提审人ID
     */
    public String getApplyUserId() {
        return applyUserId;
    }

    /**
     * 提审人ID
     * @param applyUserId 提审人ID
     */
    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId == null ? null : applyUserId.trim();
    }

    /**
     * 提审人
     * @return apply_user_name 提审人
     */
    public String getApplyUserName() {
        return applyUserName;
    }

    /**
     * 提审人
     * @param applyUserName 提审人
     */
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName == null ? null : applyUserName.trim();
    }

    /**
     * 提审时间
     * @return apply_time 提审时间
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * 提审时间
     * @param applyTime 提审时间
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 审批人ID
     * @return approve_user_id 审批人ID
     */
    public String getApproveUserId() {
        return approveUserId;
    }

    /**
     * 审批人ID
     * @param approveUserId 审批人ID
     */
    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId == null ? null : approveUserId.trim();
    }

    /**
     * 审批人
     * @return approve_user_name 审批人
     */
    public String getApproveUserName() {
        return approveUserName;
    }

    /**
     * 审批人
     * @param approveUserName 审批人
     */
    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName == null ? null : approveUserName.trim();
    }

    /**
     * 审批时间
     * @return approve_time 审批时间
     */
    public Date getApproveTime() {
        return approveTime;
    }

    /**
     * 审批时间
     * @param approveTime 审批时间
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * 是否可以撤回（0：否 1：是）
     * @return is_can_recall 是否可以撤回（0：否 1：是）
     */
    public String getIsCanRecall() {
        return isCanRecall;
    }

    /**
     * 是否可以撤回（0：否 1：是）
     * @param isCanRecall 是否可以撤回（0：否 1：是）
     */
    public void setIsCanRecall(String isCanRecall) {
        this.isCanRecall = isCanRecall == null ? null : isCanRecall.trim();
    }

    /**
     * 审批状态 (0.未通过 1.通过 2.审核中 3,已分配)
     * @return verify_state 审批状态 (0.未通过 1.通过 2.审核中 3,已分配)
     */
    public String getVerifyState() {
        return verifyState;
    }

    /**
     * 审批状态 (0.未通过 1.通过 2.审核中 3,已分配)
     * @param verifyState 审批状态 (0.未通过 1.通过 2.审核中 3,已分配)
     */
    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState == null ? null : verifyState.trim();
    }

    /**
     * 当前流程状态
     * @return current_state 当前流程状态
     */
    public String getCurrentState() {
        return currentState;
    }

    /**
     * 当前流程状态
     * @param currentState 当前流程状态
     */
    public void setCurrentState(String currentState) {
        this.currentState = currentState == null ? null : currentState.trim();
    }

    /**
     * 流程完成时间
     * @return complete_time 流程完成时间
     */
    public Date getCompleteTime() {
        return completeTime;
    }

    /**
     * 流程完成时间
     * @param completeTime 流程完成时间
     */
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * 已收入
     * @return income_received 已收入
     */
    public String getIncomeReceived() {
        return incomeReceived;
    }

    /**
     * 已收入
     * @param incomeReceived 已收入
     */
    public void setIncomeReceived(String incomeReceived) {
        this.incomeReceived = incomeReceived == null ? null : incomeReceived.trim();
    }

    /**
     * 合同金额
     * @return contract_amount 合同金额
     */
    public String getContractAmount() {
        return contractAmount;
    }

    /**
     * 合同金额
     * @param contractAmount 合同金额
     */
    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount == null ? null : contractAmount.trim();
    }

    /**
     * 负责人
     * @return duty_people 负责人
     */
    public String getDutyPeople() {
        return dutyPeople;
    }

    /**
     * 负责人
     * @param dutyPeople 负责人
     */
    public void setDutyPeople(String dutyPeople) {
        this.dutyPeople = dutyPeople == null ? null : dutyPeople.trim();
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
     * 职位ID
     * @return job_id 职位ID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 职位ID
     * @param jobId 职位ID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    /**
     * 部门ID(根据部门ID可以查询签发人)
     * @return department_id 部门ID(根据部门ID可以查询签发人)
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * 部门ID(根据部门ID可以查询签发人)
     * @param departmentId 部门ID(根据部门ID可以查询签发人)
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    /**
     * 开始时间
     * @return start_date 开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 开始时间
     * @param startDate 开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 结束时间
     * @return end_date 结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 结束时间
     * @param endDate 结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 项目名称
     * @return project_name 项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 项目名称
     * @param projectName 项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * 咨询业务类型（0：造价咨询 1：招标代理 2：项目管理(监理)）
     * @return counsel_business_type 咨询业务类型（0：造价咨询 1：招标代理 2：项目管理(监理)）
     */
    public String getCounselBusinessType() {
        return counselBusinessType;
    }

    /**
     * 咨询业务类型（0：造价咨询 1：招标代理 2：项目管理(监理)）
     * @param counselBusinessType 咨询业务类型（0：造价咨询 1：招标代理 2：项目管理(监理)）
     */
    public void setCounselBusinessType(String counselBusinessType) {
        this.counselBusinessType = counselBusinessType == null ? null : counselBusinessType.trim();
    }

    /**
     * 咨询类型阶段
     * @return counsel_type_period 咨询类型阶段
     */
    public String getCounselTypePeriod() {
        return counselTypePeriod;
    }

    /**
     * 咨询类型阶段
     * @param counselTypePeriod 咨询类型阶段
     */
    public void setCounselTypePeriod(String counselTypePeriod) {
        this.counselTypePeriod = counselTypePeriod == null ? null : counselTypePeriod.trim();
    }

    /**
     * 项目地点
     * @return project_address 项目地点
     */
    public String getProjectAddress() {
        return projectAddress;
    }

    /**
     * 项目地点
     * @param projectAddress 项目地点
     */
    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress == null ? null : projectAddress.trim();
    }

    /**
     * 项目性质（@0@：新建 @1@：扩展 @2@：改建）
     * @return project_type 项目性质（@0@：新建 @1@：扩展 @2@：改建）
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * 项目性质（@0@：新建 @1@：扩展 @2@：改建）
     * @param projectType 项目性质（@0@：新建 @1@：扩展 @2@：改建）
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    /**
     * 委托人
     * @return truster_name 委托人
     */
    public String getTrusterName() {
        return trusterName;
    }

    /**
     * 委托人
     * @param trusterName 委托人
     */
    public void setTrusterName(String trusterName) {
        this.trusterName = trusterName == null ? null : trusterName.trim();
    }

    /**
     * 委托人性质（@0@：政府部门 @1@：国企 @2@：上市公司 @3@：私企 @4@：其他）
     * @return truster_type 委托人性质（@0@：政府部门 @1@：国企 @2@：上市公司 @3@：私企 @4@：其他）
     */
    public String getTrusterType() {
        return trusterType;
    }

    /**
     * 委托人性质（@0@：政府部门 @1@：国企 @2@：上市公司 @3@：私企 @4@：其他）
     * @param trusterType 委托人性质（@0@：政府部门 @1@：国企 @2@：上市公司 @3@：私企 @4@：其他）
     */
    public void setTrusterType(String trusterType) {
        this.trusterType = trusterType == null ? null : trusterType.trim();
    }

    /**
     * 投资金额
     * @return invested_amount 投资金额
     */
    public String getInvestedAmount() {
        return investedAmount;
    }

    /**
     * 投资金额
     * @param investedAmount 投资金额
     */
    public void setInvestedAmount(String investedAmount) {
        this.investedAmount = investedAmount == null ? null : investedAmount.trim();
    }

    /**
     * 是否暂定（0：否 1：是）
     * @return is_provisional 是否暂定（0：否 1：是）
     */
    public String getIsProvisional() {
        return isProvisional;
    }

    /**
     * 是否暂定（0：否 1：是）
     * @param isProvisional 是否暂定（0：否 1：是）
     */
    public void setIsProvisional(String isProvisional) {
        this.isProvisional = isProvisional == null ? null : isProvisional.trim();
    }

    /**
     * 委托方负责人
     * @return truster_duty_user 委托方负责人
     */
    public String getTrusterDutyUser() {
        return trusterDutyUser;
    }

    /**
     * 委托方负责人
     * @param trusterDutyUser 委托方负责人
     */
    public void setTrusterDutyUser(String trusterDutyUser) {
        this.trusterDutyUser = trusterDutyUser == null ? null : trusterDutyUser.trim();
    }

    /**
     * 委托方负责人电话
     * @return truster_duty_phone 委托方负责人电话
     */
    public String getTrusterDutyPhone() {
        return trusterDutyPhone;
    }

    /**
     * 委托方负责人电话
     * @param trusterDutyPhone 委托方负责人电话
     */
    public void setTrusterDutyPhone(String trusterDutyPhone) {
        this.trusterDutyPhone = trusterDutyPhone == null ? null : trusterDutyPhone.trim();
    }

    /**
     * 咨询人
     * @return consult_user 咨询人
     */
    public String getConsultUser() {
        return consultUser;
    }

    /**
     * 咨询人
     * @param consultUser 咨询人
     */
    public void setConsultUser(String consultUser) {
        this.consultUser = consultUser == null ? null : consultUser.trim();
    }

    /**
     * 第三方
     * @return third_party 第三方
     */
    public String getThirdParty() {
        return thirdParty;
    }

    /**
     * 第三方
     * @param thirdParty 第三方
     */
    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty == null ? null : thirdParty.trim();
    }

    /**
     * 拟定人
     * @return planner 拟定人
     */
    public String getPlanner() {
        return planner;
    }

    /**
     * 拟定人
     * @param planner 拟定人
     */
    public void setPlanner(String planner) {
        this.planner = planner == null ? null : planner.trim();
    }

    /**
     * 合同性质（@0@ 双方合同 @1@ 三方合同 @2@ 补充合同）
     * @return contract_nature 合同性质（@0@ 双方合同 @1@ 三方合同 @2@ 补充合同）
     */
    public String getContractNature() {
        return contractNature;
    }

    /**
     * 合同性质（@0@ 双方合同 @1@ 三方合同 @2@ 补充合同）
     * @param contractNature 合同性质（@0@ 双方合同 @1@ 三方合同 @2@ 补充合同）
     */
    public void setContractNature(String contractNature) {
        this.contractNature = contractNature == null ? null : contractNature.trim();
    }

    /**
     * 确定方式（@0@ 招标 @1@ 财政委托 @2@ 商务洽谈 @3@ 战略合作 @4@ 法院委托 @5@ 其他）
     * @return determinate_method 确定方式（@0@ 招标 @1@ 财政委托 @2@ 商务洽谈 @3@ 战略合作 @4@ 法院委托 @5@ 其他）
     */
    public String getDeterminateMethod() {
        return determinateMethod;
    }

    /**
     * 确定方式（@0@ 招标 @1@ 财政委托 @2@ 商务洽谈 @3@ 战略合作 @4@ 法院委托 @5@ 其他）
     * @param determinateMethod 确定方式（@0@ 招标 @1@ 财政委托 @2@ 商务洽谈 @3@ 战略合作 @4@ 法院委托 @5@ 其他）
     */
    public void setDeterminateMethod(String determinateMethod) {
        this.determinateMethod = determinateMethod == null ? null : determinateMethod.trim();
    }

    /**
     * 咨询合同签订日期
     * @return contract_date 咨询合同签订日期
     */
    public Date getContractDate() {
        return contractDate;
    }

    /**
     * 咨询合同签订日期
     * @param contractDate 咨询合同签订日期
     */
    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    /**
     * 拟定合同金额是否暂定（0:否 1：是）
     * @return is_value_provisional 拟定合同金额是否暂定（0:否 1：是）
     */
    public String getIsValueProvisional() {
        return isValueProvisional;
    }

    /**
     * 拟定合同金额是否暂定（0:否 1：是）
     * @param isValueProvisional 拟定合同金额是否暂定（0:否 1：是）
     */
    public void setIsValueProvisional(String isValueProvisional) {
        this.isValueProvisional = isValueProvisional == null ? null : isValueProvisional.trim();
    }

    /**
     * 其他情况
     * @return other_things 其他情况
     */
    public String getOtherThings() {
        return otherThings;
    }

    /**
     * 其他情况
     * @param otherThings 其他情况
     */
    public void setOtherThings(String otherThings) {
        this.otherThings = otherThings == null ? null : otherThings.trim();
    }

    /**
     * 合同类型(0:自定义合同 1：模板合同)
     * @return contract_type 合同类型(0:自定义合同 1：模板合同)
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * 合同类型(0:自定义合同 1：模板合同)
     * @param contractType 合同类型(0:自定义合同 1：模板合同)
     */
    public void setContractType(String contractType) {
        this.contractType = contractType == null ? null : contractType.trim();
    }

    /**
     * 模板合同（0：造价咨询合同 1：建设工程招标代理合同 2：工程管理咨询服务合同 3：监理合同）
     * @return formwork_type 模板合同（0：造价咨询合同 1：建设工程招标代理合同 2：工程管理咨询服务合同 3：监理合同）
     */
    public String getFormworkType() {
        return formworkType;
    }

    /**
     * 模板合同（0：造价咨询合同 1：建设工程招标代理合同 2：工程管理咨询服务合同 3：监理合同）
     * @param formworkType 模板合同（0：造价咨询合同 1：建设工程招标代理合同 2：工程管理咨询服务合同 3：监理合同）
     */
    public void setFormworkType(String formworkType) {
        this.formworkType = formworkType == null ? null : formworkType.trim();
    }

    /**
     * 自定义合同路径
     * @return contract_file_path 自定义合同路径
     */
    public String getContractFilePath() {
        return contractFilePath;
    }

    /**
     * 自定义合同路径
     * @param contractFilePath 自定义合同路径
     */
    public void setContractFilePath(String contractFilePath) {
        this.contractFilePath = contractFilePath == null ? null : contractFilePath.trim();
    }

    /**
     * 自定义合同真实名字
     * @return contract_file_name 自定义合同真实名字
     */
    public String getContractFileName() {
        return contractFileName;
    }

    /**
     * 自定义合同真实名字
     * @param contractFileName 自定义合同真实名字
     */
    public void setContractFileName(String contractFileName) {
        this.contractFileName = contractFileName == null ? null : contractFileName.trim();
    }

    /**
     * 档案编号
     * @return file_number 档案编号
     */
    public String getFileNumber() {
        return fileNumber;
    }

    /**
     * 档案编号
     * @param fileNumber 档案编号
     */
    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber == null ? null : fileNumber.trim();
    }

    /**
     * 预计合同总金额
     * @return predict_total 预计合同总金额
     */
    public Double getPredictTotal() {
        return predictTotal;
    }

    /**
     * 预计合同总金额
     * @param predictTotal 预计合同总金额
     */
    public void setPredictTotal(Double predictTotal) {
        this.predictTotal = predictTotal;
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

    /**
     * 扉页咨询人
     * @return title_page_consultant 扉页咨询人
     */
    public String getTitlePageConsultant() {
        return titlePageConsultant;
    }

    /**
     * 扉页咨询人
     * @param titlePageConsultant 扉页咨询人
     */
    public void setTitlePageConsultant(String titlePageConsultant) {
        this.titlePageConsultant = titlePageConsultant == null ? null : titlePageConsultant.trim();
    }

    /**
     * 法务意见
     * @return legal_opinion 法务意见
     */
    public String getLegalOpinion() {
        return legalOpinion;
    }

    /**
     * 法务意见
     * @param legalOpinion 法务意见
     */
    public void setLegalOpinion(String legalOpinion) {
        this.legalOpinion = legalOpinion == null ? null : legalOpinion.trim();
    }

    /**
     * 合同盖章类型(1.委托代理人章2.法定代表人章)
     * @return contract_seal_type 合同盖章类型(1.委托代理人章2.法定代表人章)
     */
    public String getContractSealType() {
        return contractSealType;
    }

    /**
     * 合同盖章类型(1.委托代理人章2.法定代表人章)
     * @param contractSealType 合同盖章类型(1.委托代理人章2.法定代表人章)
     */
    public void setContractSealType(String contractSealType) {
        this.contractSealType = contractSealType == null ? null : contractSealType.trim();
    }

    /**
     * 合作公司
     * @return cooperate_company 合作公司
     */
    public String getCooperateCompany() {
        return cooperateCompany;
    }

    /**
     * 合作公司
     * @param cooperateCompany 合作公司
     */
    public void setCooperateCompany(String cooperateCompany) {
        this.cooperateCompany = cooperateCompany == null ? null : cooperateCompany.trim();
    }

    /**
     * (合作合同 0：否 1：是)
     * @return cooperate_contract (合作合同 0：否 1：是)
     */
    public String getCooperateContract() {
        return cooperateContract;
    }

    /**
     * (合作合同 0：否 1：是)
     * @param cooperateContract (合作合同 0：否 1：是)
     */
    public void setCooperateContract(String cooperateContract) {
        this.cooperateContract = cooperateContract == null ? null : cooperateContract.trim();
    }

    /**
     * 作废标识
     * @return is_invalid_flag 作废标识
     */
    public String getIsInvalidFlag() {
        return isInvalidFlag;
    }

    /**
     * 作废标识
     * @param isInvalidFlag 作废标识
     */
    public void setIsInvalidFlag(String isInvalidFlag) {
        this.isInvalidFlag = isInvalidFlag == null ? null : isInvalidFlag.trim();
    }

    /**
     * 作废原因
     * @return invalid_reason 作废原因
     */
    public String getInvalidReason() {
        return invalidReason;
    }

    /**
     * 作废原因
     * @param invalidReason 作废原因
     */
    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason == null ? null : invalidReason.trim();
    }

    /**
     * 作废人ID
     * @return invalid_user_id 作废人ID
     */
    public String getInvalidUserId() {
        return invalidUserId;
    }

    /**
     * 作废人ID
     * @param invalidUserId 作废人ID
     */
    public void setInvalidUserId(String invalidUserId) {
        this.invalidUserId = invalidUserId == null ? null : invalidUserId.trim();
    }

    /**
     * 模板内容
     * @return formwork_content 模板内容
     */
    public String getFormworkContent() {
        return formworkContent;
    }

    /**
     * 模板内容
     * @param formworkContent 模板内容
     */
    public void setFormworkContent(String formworkContent) {
        this.formworkContent = formworkContent == null ? null : formworkContent.trim();
    }
}