package com.hd.data.entity;

public class OldProjectInfoEntity {
    /**
     * 项目主键ID
     */
    private Integer id;

    /**
     * 创建人ID
     */
    private Integer uid;

    /**
     * 服务分类主键ID
     */
    private Integer clsid;

    /**
     * 委托人分类主键ID
     */
    private Integer clcid;

    /**
     * 项目分类主键ID
     */
    private Integer clpid;

    /**
     * 合同性质分类主键ID
     */
    private Integer clnid;

    /**
     * 合同确定方式分类主键ID
     */
    private Integer clmid;

    /**
     * 项目性质主键ID
     */
    private Integer paid;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 委托人
     */
    private String client;

    /**
     * 委托方负责人
     */
    private String clientManager;

    /**
     * 委托方负责人电话
     */
    private String clientManagerPhone;

    /**
     * 咨询人
     */
    private String consultant;

    /**
     * 第三方
     */
    private String thrid;

    /**
     * 项目规模/范围
     */
    private String scale;

    /**
     * 项目地点
     */
    private String place;

    /**
     * 投资金额
     */
    private String investSum;

    /**
     * 拟定合同金额
     */
    private String contractSum;

    /**
     * 拟定合同负责人
     */
    private String contractManager;

    /**
     * 咨询合同签订日期
     */
    private Integer contractTime;

    /**
     * 其他情况说明
     */
    private String remark;

    /**
     * 拟定人
     */
    private String manager;

    /**
     * 部门主键ID
     */
    private Integer deid;

    /**
     * 合同编号(审批通过生成)
     */
    private String number;

    /**
     * 创建时间
     */
    private Integer time;

    /**
     * 最后修改时间
     */
    private Integer upTime;

    /**
     * 状态；默认1,正常；-1，删除
     */
    private Boolean status;

    /**
     * 台账状态,默认0,待分解,1.账目归档
     */
    private Boolean financeStatus;

    /**
     * 导入类型，0为系统流程，1为批量导入
     */
    private Integer type;

    /**
     * 导入目标：1 归档, 2 提审
     */
    private String targetFlag;

    /**
     * 是否作废
     */
    private String isCancel;


    private String oldDeptId;
    private String oldDeptName;
    private String newDeptId;
    private String newDeptName;
    private String jobId;
    private String jobName;
    private String fuManagerUserId;
    private String fuManagerUserName;
    private String userId;
    private String applyUserId;
    private String applyUserName;
    private String projectNature;
    private String confrimMethod;
    private String projectContractNum;



    /**
     * 项目主键ID
     *
     * @return id 项目主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 项目主键ID
     *
     * @param id 项目主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 创建人ID
     *
     * @return uid 创建人ID
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 创建人ID
     *
     * @param uid 创建人ID
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 服务分类主键ID
     *
     * @return clsid 服务分类主键ID
     */
    public Integer getClsid() {
        return clsid;
    }

    /**
     * 服务分类主键ID
     *
     * @param clsid 服务分类主键ID
     */
    public void setClsid(Integer clsid) {
        this.clsid = clsid;
    }

    /**
     * 委托人分类主键ID
     *
     * @return clcid 委托人分类主键ID
     */
    public Integer getClcid() {
        return clcid;
    }

    /**
     * 委托人分类主键ID
     *
     * @param clcid 委托人分类主键ID
     */
    public void setClcid(Integer clcid) {
        this.clcid = clcid;
    }

    /**
     * 项目分类主键ID
     *
     * @return clpid 项目分类主键ID
     */
    public Integer getClpid() {
        return clpid;
    }

    /**
     * 项目分类主键ID
     *
     * @param clpid 项目分类主键ID
     */
    public void setClpid(Integer clpid) {
        this.clpid = clpid;
    }

    /**
     * 合同性质分类主键ID
     *
     * @return clnid 合同性质分类主键ID
     */
    public Integer getClnid() {
        return clnid;
    }

    /**
     * 合同性质分类主键ID
     *
     * @param clnid 合同性质分类主键ID
     */
    public void setClnid(Integer clnid) {
        this.clnid = clnid;
    }

    /**
     * 合同确定方式分类主键ID
     *
     * @return clmid 合同确定方式分类主键ID
     */
    public Integer getClmid() {
        return clmid;
    }

    /**
     * 合同确定方式分类主键ID
     *
     * @param clmid 合同确定方式分类主键ID
     */
    public void setClmid(Integer clmid) {
        this.clmid = clmid;
    }

    /**
     * 项目性质主键ID
     *
     * @return paid 项目性质主键ID
     */
    public Integer getPaid() {
        return paid;
    }

    /**
     * 项目性质主键ID
     *
     * @param paid 项目性质主键ID
     */
    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    /**
     * 项目名称
     *
     * @return name 项目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 项目名称
     *
     * @param name 项目名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 委托人
     *
     * @return client 委托人
     */
    public String getClient() {
        return client;
    }

    /**
     * 委托人
     *
     * @param client 委托人
     */
    public void setClient(String client) {
        this.client = client == null ? null : client.trim();
    }

    /**
     * 委托方负责人
     *
     * @return client_manager 委托方负责人
     */
    public String getClientManager() {
        return clientManager;
    }

    /**
     * 委托方负责人
     *
     * @param clientManager 委托方负责人
     */
    public void setClientManager(String clientManager) {
        this.clientManager = clientManager == null ? null : clientManager.trim();
    }

    /**
     * 委托方负责人电话
     *
     * @return client_manager_phone 委托方负责人电话
     */
    public String getClientManagerPhone() {
        return clientManagerPhone;
    }

    /**
     * 委托方负责人电话
     *
     * @param clientManagerPhone 委托方负责人电话
     */
    public void setClientManagerPhone(String clientManagerPhone) {
        this.clientManagerPhone = clientManagerPhone == null ? null : clientManagerPhone.trim();
    }

    /**
     * 咨询人
     *
     * @return consultant 咨询人
     */
    public String getConsultant() {
        return consultant;
    }

    /**
     * 咨询人
     *
     * @param consultant 咨询人
     */
    public void setConsultant(String consultant) {
        this.consultant = consultant == null ? null : consultant.trim();
    }

    /**
     * 第三方
     *
     * @return thrid 第三方
     */
    public String getThrid() {
        return thrid;
    }

    /**
     * 第三方
     *
     * @param thrid 第三方
     */
    public void setThrid(String thrid) {
        this.thrid = thrid == null ? null : thrid.trim();
    }

    /**
     * 项目规模/范围
     *
     * @return scale 项目规模/范围
     */
    public String getScale() {
        return scale;
    }

    /**
     * 项目规模/范围
     *
     * @param scale 项目规模/范围
     */
    public void setScale(String scale) {
        this.scale = scale == null ? null : scale.trim();
    }

    /**
     * 项目地点
     *
     * @return place 项目地点
     */
    public String getPlace() {
        return place;
    }

    /**
     * 项目地点
     *
     * @param place 项目地点
     */
    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    /**
     * 投资金额
     *
     * @return invest_sum 投资金额
     */
    public String getInvestSum() {
        return investSum;
    }

    /**
     * 投资金额
     *
     * @param investSum 投资金额
     */
    public void setInvestSum(String investSum) {
        this.investSum = investSum == null ? null : investSum.trim();
    }

    /**
     * 拟定合同金额
     *
     * @return contract_sum 拟定合同金额
     */
    public String getContractSum() {
        return contractSum;
    }

    /**
     * 拟定合同金额
     *
     * @param contractSum 拟定合同金额
     */
    public void setContractSum(String contractSum) {
        this.contractSum = contractSum == null ? null : contractSum.trim();
    }

    /**
     * 拟定合同负责人
     *
     * @return contract_manager 拟定合同负责人
     */
    public String getContractManager() {
        return contractManager;
    }

    /**
     * 拟定合同负责人
     *
     * @param contractManager 拟定合同负责人
     */
    public void setContractManager(String contractManager) {
        this.contractManager = contractManager == null ? null : contractManager.trim();
    }

    /**
     * 咨询合同签订日期
     *
     * @return contract_time 咨询合同签订日期
     */
    public Integer getContractTime() {
        return contractTime;
    }

    /**
     * 咨询合同签订日期
     *
     * @param contractTime 咨询合同签订日期
     */
    public void setContractTime(Integer contractTime) {
        this.contractTime = contractTime;
    }

    /**
     * 其他情况说明
     *
     * @return remark 其他情况说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 其他情况说明
     *
     * @param remark 其他情况说明
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 拟定人
     *
     * @return manager 拟定人
     */
    public String getManager() {
        return manager;
    }

    /**
     * 拟定人
     *
     * @param manager 拟定人
     */
    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    /**
     * 部门主键ID
     *
     * @return deid 部门主键ID
     */
    public Integer getDeid() {
        return deid;
    }

    /**
     * 部门主键ID
     *
     * @param deid 部门主键ID
     */
    public void setDeid(Integer deid) {
        this.deid = deid;
    }

    /**
     * 合同编号(审批通过生成)
     *
     * @return number 合同编号(审批通过生成)
     */
    public String getNumber() {
        return number;
    }

    /**
     * 合同编号(审批通过生成)
     *
     * @param number 合同编号(审批通过生成)
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * 创建时间
     *
     * @return time 创建时间
     */
    public Integer getTime() {
        return time;
    }

    /**
     * 创建时间
     *
     * @param time 创建时间
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * 最后修改时间
     *
     * @return up_time 最后修改时间
     */
    public Integer getUpTime() {
        return upTime;
    }

    /**
     * 最后修改时间
     *
     * @param upTime 最后修改时间
     */
    public void setUpTime(Integer upTime) {
        this.upTime = upTime;
    }

    /**
     * 状态；默认1,正常；-1，删除
     *
     * @return status 状态；默认1,正常；-1，删除
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 状态；默认1,正常；-1，删除
     *
     * @param status 状态；默认1,正常；-1，删除
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 台账状态,默认0,待分解,1.账目归档
     *
     * @return finance_status 台账状态,默认0,待分解,1.账目归档
     */
    public Boolean getFinanceStatus() {
        return financeStatus;
    }

    /**
     * 台账状态,默认0,待分解,1.账目归档
     *
     * @param financeStatus 台账状态,默认0,待分解,1.账目归档
     */
    public void setFinanceStatus(Boolean financeStatus) {
        this.financeStatus = financeStatus;
    }

    /**
     * 导入类型，0为系统流程，1为批量导入
     *
     * @return type 导入类型，0为系统流程，1为批量导入
     */
    public Integer getType() {
        return type;
    }

    /**
     * 导入类型，0为系统流程，1为批量导入
     *
     * @param type 导入类型，0为系统流程，1为批量导入
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public String getOldDeptName() {
        return oldDeptName;
    }

    public void setOldDeptName(String oldDeptName) {
        this.oldDeptName = oldDeptName;
    }

    public String getOldDeptId() {
        return oldDeptId;
    }

    public void setOldDeptId(String oldDeptId) {
        this.oldDeptId = oldDeptId;
    }

    public String getNewDeptId() {
        return newDeptId;
    }

    public void setNewDeptId(String newDeptId) {
        this.newDeptId = newDeptId;
    }

    public String getNewDeptName() {
        return newDeptName;
    }

    public void setNewDeptName(String newDeptName) {
        this.newDeptName = newDeptName;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getFuManagerUserId() {
        return fuManagerUserId;
    }

    public void setFuManagerUserId(String fuManagerUserId) {
        this.fuManagerUserId = fuManagerUserId;
    }

    public String getFuManagerUserName() {
        return fuManagerUserName;
    }

    public void setFuManagerUserName(String fuManagerUserName) {
        this.fuManagerUserName = fuManagerUserName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getProjectNature() {
        return projectNature;
    }

    public void setProjectNature(String projectNature) {
        this.projectNature = projectNature;
    }

    public String getConfrimMethod() {
        return confrimMethod;
    }

    public void setConfrimMethod(String confrimMethod) {
        this.confrimMethod = confrimMethod;
    }

    public String getProjectContractNum() {
        return projectContractNum;
    }

    public void setProjectContractNum(String projectContractNum) {
        this.projectContractNum = projectContractNum;
    }

    public String getTargetFlag() {
        return targetFlag;
    }

    public void setTargetFlag(String targetFlag) {
        this.targetFlag = targetFlag;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }
}