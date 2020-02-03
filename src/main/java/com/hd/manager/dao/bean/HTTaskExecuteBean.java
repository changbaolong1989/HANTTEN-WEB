package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * 任务执行Bean
 *
 * @author Dongzx
 * Created in 2019/8/13 10:48
 */
public class HTTaskExecuteBean extends BaseBean {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 合同类型id
     */
    private String contractTypeId;

    /**
     * 咨询类型id
     */
    private String counselTypeId;

    /**
     * 关联id
     */
    private String relationId;

    /**
     * 人员id
     */
    private String userId;

    /**
     * 是否是主负责人  0：否   1：是
     */
    private String isMainDuty;

    /**
     * 任务说明
     */
    private String taskContent;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 完成时间
     */
    private Date endDate;

    /**
     * 所需工时
     */
    private Integer needHour;

    /**
     * 阶段id
     */
    private String periodId;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 合同类型名称
     */
    private String contractTypeName;

    /**
     * 咨询类型名称
     */
    private String counselTypeName;

    /**
     * 当前时间
     */
    private Date nowDate;

    /**
     * 实际工时
     */
    private Integer actualHour;

    /**
     * 实际工时总和
     */
    private Integer actualHourSum;

    /**
     * 任务和用户关联ID
     */
    private String taskUserId;

    /**
     * 项目编号
     */
    private String projectNum;

    /*** todo  start

    /**
     *  项目类型
     */
    private String counselBusinessType;

    /**
     *  项目名称
     */
    private String projectName;

    /**
     *  合同签订时间
     */
    private Date contractDate;

    /**
     *  委托人
     */
    private String trusterName;

    /**
     *  负责人
     */
    private String dutyPeople;

    /**
     *  委托方负责人
     */
    private String trusterDutyUser;

    /**
     *  投资额
     */
    private String investedAmount;

    /**
     *  合同金额
     */
    private String contractAmount;

    /**
     *  A1 成果文件编号
     */
    private String reportNumA1;

    /**
     *  A1 成果文件份数
     */
    private String fileNum;

    /**
     * 成果文件份数
     */
    private String reportCopies;

    /**
     *  编制人签名
     */
    private String signPathBZ;

    /**
     *  编制人签名时间
     */
    private Date applyTimeBZ;

    /**
     *  审核人签名
     */
    private String signPathSH;

    /**
     *  审核人签名时间
     */
    private Date approveTimeSH;


    /**
     *  质量控制人签名
     */
    private String signPathZL;

    /**
     *  质量控制人签名时间
     */
    private Date approveTimeZL;

    /**
     *  审定人签名
     */
    private String signPathSD;

    /**
     *  审定人签名时间
     */
    private Date approveTimeSD;

    /**
     *  总经理签名
     */
    private String signPathZJL;

    /**
     *  总经理签名时间
     */
    private Date approveTimeZJL;

    /**
     *  A2 成果文件编号
     */
    private String reportNumA2;

    /**
     *  项目概况
     */
    private String projectCondition;

    /**
     *  A2 发包人
     */
    private String employerA2;

    /**
     *  项目中标人
     */
    private String bidPerson;

    /**
     *  计划开始时间
     */
    private Date planStartDate;

    /**
     *  计划结束时间
     */
    private Date planEndDate;

    /**
     *  实际开始时间
     */
    private Date actualStartDate;

    /**
     *  实际结束时间
     */
    private Date actualEndDate;

    /**
     *  延迟原因
     */
    private String delayReason;

    /**
     *  咨询主要结论
     */
    private String mainResult;

    /**
     *  签收方经手人
     */
    private String operatorName;

    /**
     *  咨询业务简述
     */
    private String counselDesc;

    /**
     *  建设规模
     */
    private String constructScale;

    /**
     *  咨询内容与重点
     */
    private String counselContent;

    /**
     *  项目负责人（组长）
     */
    private String counselGroup;

    /**
     *  专业负责人
     */
    private String counselDuty;

    /**
     *  咨询业务小组分工
     */
    private String divideWork;

    /**
     *  成员
     */
    private String memberList;

    /**
     *  达成意见一致时间
     */
    private Date consensusTime;

    /**
     *  发包人
     */
    private String employerA8;

    /**
     *  承包人
     */
    private String contractor;

    /**
     *  监理人
     */
    private String supervisor;

    /**
     *  现场勘探记录
     */
    private String prospectRecord;

    /**
     *  文件名
     */
    private String materialName;

    /**
     *  文件路径
     */
    private String materialPath;

    /**
     *  排序
     */
    private Integer sortNum;

    /**
     *  咨询意见
     */
    private String counselAdvise;

    /**
     *  承包人回复意见
     */
    private String responseAdvise;

    /**
     *  工程名称
     */
    private String engineeringName;

    /**
     *  招标方式
     */
    private String tenderMethod;

    /**
     *  招标形式
     */
    private String tenderType;

    /**
     *  招标平台
     */
    private String tenderPlatform;

    /**
     *  投标人资格要求
     */
    private String investorRequire;

    /**
     *  工程类别
     */
    private String engineeingType;

    /**
     *  结算价格
     */
    private Double settlePrice;

    /**
     *  结算价超出合同价的原因
     */
    private String settleReason;

    /**
     *  单位工程名称
     */
    private String unitName;

    /**
     *  造价
     */
    private Double constructCost;

    /**
     *  单价
     */
    private Double unitCost;

    /**
     *  造价比例
     */
    private Double costRatio;

    /**
     *  记录id
     */
    private String expandId;

    /**
     *  招标类型
     */
    private String tenderTypeContent;

    /**
     *  工作流程
     */
    private String workflow;

    /**
     *  时间
     */
    private Integer days;

    /**
     *  涉及部门
     */
    private String involvedDepartment;

    /**
     *  备注
     */
    private String remark;

    /**
     *  委托方回复意见
     */
    private String answerAdvise;

    /**
     *  A3 成果文件编号
     */
    private String reportNumA3;

    /**
     *  是否为成果文件flg（0：否 1：是）
     */
    private String isResultFile;

    /**
     *  集合
     */
    private List<HTTaskExecuteBean> htTaskExecuteList;

    /**
     * 完成时间
     */
    private Date applyTimeCD;

    /**
     * 归档人路径
     */
    private String signPathCD;

    /**
     * 完成时间
     */
    private Date approveTimeZJ;

    /**
     * 归档人路径
     */
    private String signPathZJ;

    /**
     * A7-合同价格
     */
    private Double contractPrice;

    /**
     * A2主提审人
     */
    private String applyUserNameA2;

    /**
     * A2创建人签名
     */
    private String createUserSignPathA2;

    /**
     * 文件类型
     */
    private String fileTypeName;

    /**
     * 附件/复印件
     */
    private String copyFile;

    /**
     * 份数
     */
    private String num;

    /**
     * 接收人
     */
    private String recipient;

    /**
     * 接收时间
     */
    private Date recipientDate;

    /**
     * copyFile
     *
     * @return copyFile 项目描述（略）
     */
    public String getCopyFile() {
        return copyFile;
    }

    /**
     * copyFile
     *
     * @param copyFile 项目描述（略）
     */
    public void setCopyFile(String copyFile) {
        this.copyFile = copyFile;
    }

    /**
     * num
     *
     * @return num 项目描述（略）
     */
    public String getNum() {
        return num;
    }

    /**
     * num
     *
     * @param num 项目描述（略）
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * recipient
     *
     * @return recipient 项目描述（略）
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * recipient
     *
     * @param recipient 项目描述（略）
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * recipientDate
     *
     * @return recipientDate 项目描述（略）
     */
    public Date getRecipientDate() {
        return recipientDate;
    }

    /**
     * recipientDate
     *
     * @param recipientDate 项目描述（略）
     */
    public void setRecipientDate(Date recipientDate) {
        this.recipientDate = recipientDate;
    }

    /**
     * fileTypeName
     *
     * @return fileTypeName 项目描述（略）
     */
    public String getFileTypeName() {
        return fileTypeName;
    }

    /**
     * fileTypeName
     *
     * @param fileTypeName 项目描述（略）
     */
    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    /**
     * createUserSignPathA2
     *
     * @return createUserSignPathA2 项目描述（略）
     */
    public String getCreateUserSignPathA2() {
        return createUserSignPathA2;
    }

    /**
     * createUserSignPathA2
     *
     * @param createUserSignPathA2 项目描述（略）
     */
    public void setCreateUserSignPathA2(String createUserSignPathA2) {
        this.createUserSignPathA2 = createUserSignPathA2;
    }

    /**
     * applyUserNameA2
     *
     * @return applyUserNameA2 项目描述（略）
     */
    public String getApplyUserNameA2() {
        return applyUserNameA2;
    }

    /**
     * applyUserNameA2
     *
     * @param applyUserNameA2 项目描述（略）
     */
    public void setApplyUserNameA2(String applyUserNameA2) {
        this.applyUserNameA2 = applyUserNameA2;
    }

    /**
     * reportCopies
     *
     * @return reportCopies 项目描述（略）
     */
    public String getReportCopies() {
        return reportCopies;
    }

    /**
     * reportCopies
     *
     * @param reportCopies 项目描述（略）
     */
    public void setReportCopies(String reportCopies) {
        this.reportCopies = reportCopies;
    }

    /**
     * approveTimeZJ
     *
     * @return approveTimeZJ 项目描述（略）
     */
    public Date getApproveTimeZJ() {
        return approveTimeZJ;
    }

    /**
     * approveTimeZJ
     *
     * @param approveTimeZJ 项目描述（略）
     */
    public void setApproveTimeZJ(Date approveTimeZJ) {
        this.approveTimeZJ = approveTimeZJ;
    }

    /**
     * signPathZJ
     *
     * @return signPathZJ 项目描述（略）
     */
    public String getSignPathZJ() {
        return signPathZJ;
    }

    /**
     * signPathZJ
     *
     * @param signPathZJ 项目描述（略）
     */
    public void setSignPathZJ(String signPathZJ) {
        this.signPathZJ = signPathZJ;
    }

    /**
     * applyTimeCD
     *
     * @return applyTimeCD 项目描述（略）
     */
    public Date getApplyTimeCD() {
        return applyTimeCD;
    }

    /**
     * applyTimeCD
     *
     * @param applyTimeCD 项目描述（略）
     */
    public void setApplyTimeCD(Date applyTimeCD) {
        this.applyTimeCD = applyTimeCD;
    }

    /**
     * signPathCD
     *
     * @return signPathCD 项目描述（略）
     */
    public String getSignPathCD() {
        return signPathCD;
    }

    /**
     * signPathCD
     *
     * @param signPathCD 项目描述（略）
     */
    public void setSignPathCD(String signPathCD) {
        this.signPathCD = signPathCD;
    }

    /**
     * projectNum
     *
     * @return projectNum 项目描述（略）
     */
    public String getProjectNum() {
        return projectNum;
    }

    /**
     * projectNum
     *
     * @param projectNum 项目描述（略）
     */
    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    /**
     * taskUserId
     *
     * @return taskUserId 项目描述（略）
     */
    public String getTaskUserId() {
        return taskUserId;
    }

    /**
     * taskUserId
     *
     * @param taskUserId 项目描述（略）
     */
    public void setTaskUserId(String taskUserId) {
        this.taskUserId = taskUserId;
    }

    /**
     * actualHour
     *
     * @return actualHour 项目描述（略）
     */
    public Integer getActualHour() {
        return actualHour;
    }

    /**
     * actualHour
     *
     * @param actualHour 项目描述（略）
     */
    public void setActualHour(Integer actualHour) {
        this.actualHour = actualHour;
    }

    /**
     * actualHourSum
     *
     * @return actualHourSum 项目描述（略）
     */
    public Integer getActualHourSum() {
        return actualHourSum;
    }

    /**
     * actualHourSum
     *
     * @param actualHourSum 项目描述（略）
     */
    public void setActualHourSum(Integer actualHourSum) {
        this.actualHourSum = actualHourSum;
    }

    /**
     * nowDate
     *
     * @return nowDate 项目描述（略）
     */
    public Date getNowDate() {
        return nowDate;
    }

    /**
     * nowDate
     *
     * @param nowDate 项目描述（略）
     */
    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    /**
     * contractNum
     *
     * @return contractNum 项目描述（略）
     */
    public String getContractNum() {
        return contractNum;
    }

    /**
     * contractNum
     *
     * @param contractNum 项目描述（略）
     */
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    /**
     * contractTypeName
     *
     * @return contractTypeName 项目描述（略）
     */
    public String getContractTypeName() {
        return contractTypeName;
    }

    /**
     * contractTypeName
     *
     * @param contractTypeName 项目描述（略）
     */
    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    /**
     * counselTypeName
     *
     * @return counselTypeName 项目描述（略）
     */
    public String getCounselTypeName() {
        return counselTypeName;
    }

    /**
     * counselTypeName
     *
     * @param counselTypeName 项目描述（略）
     */
    public void setCounselTypeName(String counselTypeName) {
        this.counselTypeName = counselTypeName;
    }

    /**
     * userName
     *
     * @return userName 项目描述（略）
     */
    public String getUserName() {
        return userName;
    }

    /**
     * userName
     *
     * @param userName 项目描述（略）
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * departmentId
     *
     * @return departmentId 项目描述（略）
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * departmentId
     *
     * @param departmentId 项目描述（略）
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * needHour
     *
     * @return needHour 项目描述（略）
     */
    public Integer getNeedHour() {
        return needHour;
    }

    /**
     * needHour
     *
     * @param needHour 项目描述（略）
     */
    public void setNeedHour(Integer needHour) {
        this.needHour = needHour;
    }

    /**
     * startDate
     *
     * @return startDate 项目描述（略）
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * startDate
     *
     * @param startDate 项目描述（略）
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * endDate
     *
     * @return endDate 项目描述（略）
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * endDate
     *
     * @param endDate 项目描述（略）
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * taskId
     *
     * @return taskId 项目描述（略）
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * taskId
     *
     * @param taskId 项目描述（略）
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * taskName
     *
     * @return taskName 项目描述（略）
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * taskName
     *
     * @param taskName 项目描述（略）
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * contractId
     *
     * @return contractId 项目描述（略）
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * contractId
     *
     * @param contractId 项目描述（略）
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    /**
     * contractTypeId
     *
     * @return contractTypeId 项目描述（略）
     */
    public String getContractTypeId() {
        return contractTypeId;
    }

    /**
     * contractTypeId
     *
     * @param contractTypeId 项目描述（略）
     */
    public void setContractTypeId(String contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    /**
     * counselTypeId
     *
     * @return counselTypeId 项目描述（略）
     */
    public String getCounselTypeId() {
        return counselTypeId;
    }

    /**
     * counselTypeId
     *
     * @param counselTypeId 项目描述（略）
     */
    public void setCounselTypeId(String counselTypeId) {
        this.counselTypeId = counselTypeId;
    }

    /**
     * relationId
     *
     * @return relationId 项目描述（略）
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * relationId
     *
     * @param relationId 项目描述（略）
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    /**
     * userId
     *
     * @return userId 项目描述（略）
     */
    public String getUserId() {
        return userId;
    }

    /**
     * userId
     *
     * @param userId 项目描述（略）
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * isMainDuty
     *
     * @return isMainDuty 项目描述（略）
     */
    public String getIsMainDuty() {
        return isMainDuty;
    }

    /**
     * isMainDuty
     *
     * @param isMainDuty 项目描述（略）
     */
    public void setIsMainDuty(String isMainDuty) {
        this.isMainDuty = isMainDuty;
    }

    /**
     * taskContent
     *
     * @return taskContent 项目描述（略）
     */
    public String getTaskContent() {
        return taskContent;
    }

    /**
     * taskContent
     *
     * @param taskContent 项目描述（略）
     */
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    /**
     * periodId
     *
     * @return periodId 项目描述（略）
     */
    public String getPeriodId() {
        return periodId;
    }

    /**
     * periodId
     *
     * @param periodId 项目描述（略）
     */
    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    /**
     * counselBusinessType
     *
     * @return counselBusinessType 项目描述（略）
     */
    public String getCounselBusinessType() {
        return counselBusinessType;
    }

    /**
     * counselBusinessType
     *
     * @param counselBusinessType 项目描述（略）
     */
    public void setCounselBusinessType(String counselBusinessType) {
        this.counselBusinessType = counselBusinessType;
    }

    /**
     * projectName
     *
     * @return projectName 项目描述（略）
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * projectName
     *
     * @param projectName 项目描述（略）
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * contractDate
     *
     * @return contractDate 项目描述（略）
     */
    public Date getContractDate() {
        return contractDate;
    }

    /**
     * contractDate
     *
     * @param contractDate 项目描述（略）
     */
    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    /**
     * trusterName
     *
     * @return trusterName 项目描述（略）
     */
    public String getTrusterName() {
        return trusterName;
    }

    /**
     * trusterName
     *
     * @param trusterName 项目描述（略）
     */
    public void setTrusterName(String trusterName) {
        this.trusterName = trusterName;
    }

    /**
     * dutyPeople
     *
     * @return dutyPeople 项目描述（略）
     */
    public String getDutyPeople() {
        return dutyPeople;
    }

    /**
     * dutyPeople
     *
     * @param dutyPeople 项目描述（略）
     */
    public void setDutyPeople(String dutyPeople) {
        this.dutyPeople = dutyPeople;
    }

    /**
     * trusterDutyUser
     *
     * @return trusterDutyUser 项目描述（略）
     */
    public String getTrusterDutyUser() {
        return trusterDutyUser;
    }

    /**
     * trusterDutyUser
     *
     * @param trusterDutyUser 项目描述（略）
     */
    public void setTrusterDutyUser(String trusterDutyUser) {
        this.trusterDutyUser = trusterDutyUser;
    }

    /**
     * investedAmount
     *
     * @return investedAmount 项目描述（略）
     */
    public String getInvestedAmount() {
        return investedAmount;
    }

    /**
     * investedAmount
     *
     * @param investedAmount 项目描述（略）
     */
    public void setInvestedAmount(String investedAmount) {
        this.investedAmount = investedAmount;
    }

    /**
     * contractAmount
     *
     * @return contractAmount 项目描述（略）
     */
    public String getContractAmount() {
        return contractAmount;
    }

    /**
     * contractAmount
     *
     * @param contractAmount 项目描述（略）
     */
    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    /**
     * reportNumA1
     *
     * @return reportNumA1 项目描述（略）
     */
    public String getReportNumA1() {
        return reportNumA1;
    }

    /**
     * reportNumA1
     *
     * @param reportNumA1 项目描述（略）
     */
    public void setReportNumA1(String reportNumA1) {
        this.reportNumA1 = reportNumA1;
    }

    /**
     * fileNum
     *
     * @return fileNum 项目描述（略）
     */
    public String getFileNum() {
        return fileNum;
    }

    /**
     * fileNum
     *
     * @param fileNum 项目描述（略）
     */
    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }

    /**
     * signPathBZ
     *
     * @return signPathBZ 项目描述（略）
     */
    public String getSignPathBZ() {
        return signPathBZ;
    }

    /**
     * signPathBZ
     *
     * @param signPathBZ 项目描述（略）
     */
    public void setSignPathBZ(String signPathBZ) {
        this.signPathBZ = signPathBZ;
    }

    /**
     * applyTimeBZ
     *
     * @return applyTimeBZ 项目描述（略）
     */
    public Date getApplyTimeBZ() {
        return applyTimeBZ;
    }

    /**
     * applyTimeBZ
     *
     * @param applyTimeBZ 项目描述（略）
     */
    public void setApplyTimeBZ(Date applyTimeBZ) {
        this.applyTimeBZ = applyTimeBZ;
    }

    /**
     * signPathSH
     *
     * @return signPathSH 项目描述（略）
     */
    public String getSignPathSH() {
        return signPathSH;
    }

    /**
     * signPathSH
     *
     * @param signPathSH 项目描述（略）
     */
    public void setSignPathSH(String signPathSH) {
        this.signPathSH = signPathSH;
    }

    /**
     * approveTimeSH
     *
     * @return approveTimeSH 项目描述（略）
     */
    public Date getApproveTimeSH() {
        return approveTimeSH;
    }

    /**
     * approveTimeSH
     *
     * @param approveTimeSH 项目描述（略）
     */
    public void setApproveTimeSH(Date approveTimeSH) {
        this.approveTimeSH = approveTimeSH;
    }

    /**
     * signPathZL
     *
     * @return signPathZL 项目描述（略）
     */
    public String getSignPathZL() {
        return signPathZL;
    }

    /**
     * signPathZL
     *
     * @param signPathZL 项目描述（略）
     */
    public void setSignPathZL(String signPathZL) {
        this.signPathZL = signPathZL;
    }

    /**
     * approveTimeZL
     *
     * @return approveTimeZL 项目描述（略）
     */
    public Date getApproveTimeZL() {
        return approveTimeZL;
    }

    /**
     * approveTimeZL
     *
     * @param approveTimeZL 项目描述（略）
     */
    public void setApproveTimeZL(Date approveTimeZL) {
        this.approveTimeZL = approveTimeZL;
    }

    /**
     * signPathSD
     *
     * @return signPathSD 项目描述（略）
     */
    public String getSignPathSD() {
        return signPathSD;
    }

    /**
     * signPathSD
     *
     * @param signPathSD 项目描述（略）
     */
    public void setSignPathSD(String signPathSD) {
        this.signPathSD = signPathSD;
    }

    /**
     * approveTimeSD
     *
     * @return approveTimeSD 项目描述（略）
     */
    public Date getApproveTimeSD() {
        return approveTimeSD;
    }

    /**
     * approveTimeSD
     *
     * @param approveTimeSD 项目描述（略）
     */
    public void setApproveTimeSD(Date approveTimeSD) {
        this.approveTimeSD = approveTimeSD;
    }

    /**
     * signPathZJL
     *
     * @return signPathZJL 项目描述（略）
     */
    public String getSignPathZJL() {
        return signPathZJL;
    }

    /**
     * signPathZJL
     *
     * @param signPathZJL 项目描述（略）
     */
    public void setSignPathZJL(String signPathZJL) {
        this.signPathZJL = signPathZJL;
    }

    /**
     * approveTimeZJL
     *
     * @return approveTimeZJL 项目描述（略）
     */
    public Date getApproveTimeZJL() {
        return approveTimeZJL;
    }

    /**
     * approveTimeZJL
     *
     * @param approveTimeZJL 项目描述（略）
     */
    public void setApproveTimeZJL(Date approveTimeZJL) {
        this.approveTimeZJL = approveTimeZJL;
    }

    /**
     * reportNumA2
     *
     * @return reportNumA2 项目描述（略）
     */
    public String getReportNumA2() {
        return reportNumA2;
    }

    /**
     * reportNumA2
     *
     * @param reportNumA2 项目描述（略）
     */
    public void setReportNumA2(String reportNumA2) {
        this.reportNumA2 = reportNumA2;
    }

    /**
     * projectCondition
     *
     * @return projectCondition 项目描述（略）
     */
    public String getProjectCondition() {
        return projectCondition;
    }

    /**
     * projectCondition
     *
     * @param projectCondition 项目描述（略）
     */
    public void setProjectCondition(String projectCondition) {
        this.projectCondition = projectCondition;
    }

    /**
     * employerA2
     *
     * @return employerA2 项目描述（略）
     */
    public String getEmployerA2() {
        return employerA2;
    }

    /**
     * employerA2
     *
     * @param employerA2 项目描述（略）
     */
    public void setEmployerA2(String employerA2) {
        this.employerA2 = employerA2;
    }

    /**
     * bidPerson
     *
     * @return bidPerson 项目描述（略）
     */
    public String getBidPerson() {
        return bidPerson;
    }

    /**
     * bidPerson
     *
     * @param bidPerson 项目描述（略）
     */
    public void setBidPerson(String bidPerson) {
        this.bidPerson = bidPerson;
    }

    /**
     * planStartDate
     *
     * @return planStartDate 项目描述（略）
     */
    public Date getPlanStartDate() {
        return planStartDate;
    }

    /**
     * planStartDate
     *
     * @param planStartDate 项目描述（略）
     */
    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    /**
     * planEndDate
     *
     * @return planEndDate 项目描述（略）
     */
    public Date getPlanEndDate() {
        return planEndDate;
    }

    /**
     * planEndDate
     *
     * @param planEndDate 项目描述（略）
     */
    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    /**
     * actualStartDate
     *
     * @return actualStartDate 项目描述（略）
     */
    public Date getActualStartDate() {
        return actualStartDate;
    }

    /**
     * actualStartDate
     *
     * @param actualStartDate 项目描述（略）
     */
    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    /**
     * actualEndDate
     *
     * @return actualEndDate 项目描述（略）
     */
    public Date getActualEndDate() {
        return actualEndDate;
    }

    /**
     * actualEndDate
     *
     * @param actualEndDate 项目描述（略）
     */
    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    /**
     * delayReason
     *
     * @return delayReason 项目描述（略）
     */
    public String getDelayReason() {
        return delayReason;
    }

    /**
     * delayReason
     *
     * @param delayReason 项目描述（略）
     */
    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason;
    }

    /**
     * mainResult
     *
     * @return mainResult 项目描述（略）
     */
    public String getMainResult() {
        return mainResult;
    }

    /**
     * mainResult
     *
     * @param mainResult 项目描述（略）
     */
    public void setMainResult(String mainResult) {
        this.mainResult = mainResult;
    }

    /**
     * operatorName
     *
     * @return operatorName 项目描述（略）
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * operatorName
     *
     * @param operatorName 项目描述（略）
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * counselDesc
     *
     * @return counselDesc 项目描述（略）
     */
    public String getCounselDesc() {
        return counselDesc;
    }

    /**
     * counselDesc
     *
     * @param counselDesc 项目描述（略）
     */
    public void setCounselDesc(String counselDesc) {
        this.counselDesc = counselDesc;
    }

    /**
     * constructScale
     *
     * @return constructScale 项目描述（略）
     */
    public String getConstructScale() {
        return constructScale;
    }

    /**
     * constructScale
     *
     * @param constructScale 项目描述（略）
     */
    public void setConstructScale(String constructScale) {
        this.constructScale = constructScale;
    }

    /**
     * counselContent
     *
     * @return counselContent 项目描述（略）
     */
    public String getCounselContent() {
        return counselContent;
    }

    /**
     * counselContent
     *
     * @param counselContent 项目描述（略）
     */
    public void setCounselContent(String counselContent) {
        this.counselContent = counselContent;
    }

    /**
     * counselGroup
     *
     * @return counselGroup 项目描述（略）
     */
    public String getCounselGroup() {
        return counselGroup;
    }

    /**
     * counselGroup
     *
     * @param counselGroup 项目描述（略）
     */
    public void setCounselGroup(String counselGroup) {
        this.counselGroup = counselGroup;
    }

    /**
     * counselDuty
     *
     * @return counselDuty 项目描述（略）
     */
    public String getCounselDuty() {
        return counselDuty;
    }

    /**
     * counselDuty
     *
     * @param counselDuty 项目描述（略）
     */
    public void setCounselDuty(String counselDuty) {
        this.counselDuty = counselDuty;
    }

    /**
     * divideWork
     *
     * @return divideWork 项目描述（略）
     */
    public String getDivideWork() {
        return divideWork;
    }

    /**
     * divideWork
     *
     * @param divideWork 项目描述（略）
     */
    public void setDivideWork(String divideWork) {
        this.divideWork = divideWork;
    }

    /**
     * memberList
     *
     * @return memberList 项目描述（略）
     */
    public String getMemberList() {
        return memberList;
    }

    /**
     * memberList
     *
     * @param memberList 项目描述（略）
     */
    public void setMemberList(String memberList) {
        this.memberList = memberList;
    }

    /**
     * consensusTime
     *
     * @return consensusTime 项目描述（略）
     */
    public Date getConsensusTime() {
        return consensusTime;
    }

    /**
     * consensusTime
     *
     * @param consensusTime 项目描述（略）
     */
    public void setConsensusTime(Date consensusTime) {
        this.consensusTime = consensusTime;
    }

    /**
     * employerA8
     *
     * @return employerA8 项目描述（略）
     */
    public String getEmployerA8() {
        return employerA8;
    }

    /**
     * employerA8
     *
     * @param employerA8 项目描述（略）
     */
    public void setEmployerA8(String employerA8) {
        this.employerA8 = employerA8;
    }

    /**
     * contractor
     *
     * @return contractor 项目描述（略）
     */
    public String getContractor() {
        return contractor;
    }

    /**
     * contractor
     *
     * @param contractor 项目描述（略）
     */
    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    /**
     * supervisor
     *
     * @return supervisor 项目描述（略）
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * supervisor
     *
     * @param supervisor 项目描述（略）
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * prospectRecord
     *
     * @return prospectRecord 项目描述（略）
     */
    public String getProspectRecord() {
        return prospectRecord;
    }

    /**
     * prospectRecord
     *
     * @param prospectRecord 项目描述（略）
     */
    public void setProspectRecord(String prospectRecord) {
        this.prospectRecord = prospectRecord;
    }

    /**
     * materialName
     *
     * @return materialName 项目描述（略）
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * materialName
     *
     * @param materialName 项目描述（略）
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    /**
     * materialPath
     *
     * @return materialPath 项目描述（略）
     */
    public String getMaterialPath() {
        return materialPath;
    }

    /**
     * materialPath
     *
     * @param materialPath 项目描述（略）
     */
    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
    }

    /**
     * sortNum
     *
     * @return sortNum 项目描述（略）
     */
    public Integer getSortNum() {
        return sortNum;
    }

    /**
     * sortNum
     *
     * @param sortNum 项目描述（略）
     */
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * counselAdvise
     *
     * @return counselAdvise 项目描述（略）
     */
    public String getCounselAdvise() {
        return counselAdvise;
    }

    /**
     * counselAdvise
     *
     * @param counselAdvise 项目描述（略）
     */
    public void setCounselAdvise(String counselAdvise) {
        this.counselAdvise = counselAdvise;
    }

    /**
     * responseAdvise
     *
     * @return responseAdvise 项目描述（略）
     */
    public String getResponseAdvise() {
        return responseAdvise;
    }

    /**
     * responseAdvise
     *
     * @param responseAdvise 项目描述（略）
     */
    public void setResponseAdvise(String responseAdvise) {
        this.responseAdvise = responseAdvise;
    }

    /**
     * engineeringName
     *
     * @return engineeringName 项目描述（略）
     */
    public String getEngineeringName() {
        return engineeringName;
    }

    /**
     * engineeringName
     *
     * @param engineeringName 项目描述（略）
     */
    public void setEngineeringName(String engineeringName) {
        this.engineeringName = engineeringName;
    }

    /**
     * tenderMethod
     *
     * @return tenderMethod 项目描述（略）
     */
    public String getTenderMethod() {
        return tenderMethod;
    }

    /**
     * tenderMethod
     *
     * @param tenderMethod 项目描述（略）
     */
    public void setTenderMethod(String tenderMethod) {
        this.tenderMethod = tenderMethod;
    }

    /**
     * tenderType
     *
     * @return tenderType 项目描述（略）
     */
    public String getTenderType() {
        return tenderType;
    }

    /**
     * tenderType
     *
     * @param tenderType 项目描述（略）
     */
    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    /**
     * tenderPlatform
     *
     * @return tenderPlatform 项目描述（略）
     */
    public String getTenderPlatform() {
        return tenderPlatform;
    }

    /**
     * tenderPlatform
     *
     * @param tenderPlatform 项目描述（略）
     */
    public void setTenderPlatform(String tenderPlatform) {
        this.tenderPlatform = tenderPlatform;
    }

    /**
     * investorRequire
     *
     * @return investorRequire 项目描述（略）
     */
    public String getInvestorRequire() {
        return investorRequire;
    }

    /**
     * investorRequire
     *
     * @param investorRequire 项目描述（略）
     */
    public void setInvestorRequire(String investorRequire) {
        this.investorRequire = investorRequire;
    }

    /**
     * engineeingType
     *
     * @return engineeingType 项目描述（略）
     */
    public String getEngineeingType() {
        return engineeingType;
    }

    /**
     * engineeingType
     *
     * @param engineeingType 项目描述（略）
     */
    public void setEngineeingType(String engineeingType) {
        this.engineeingType = engineeingType;
    }

    /**
     * settlePrice
     *
     * @return settlePrice 项目描述（略）
     */
    public Double getSettlePrice() {
        return settlePrice;
    }

    /**
     * settlePrice
     *
     * @param settlePrice 项目描述（略）
     */
    public void setSettlePrice(Double settlePrice) {
        this.settlePrice = settlePrice;
    }

    /**
     * settleReason
     *
     * @return settleReason 项目描述（略）
     */
    public String getSettleReason() {
        return settleReason;
    }

    /**
     * settleReason
     *
     * @param settleReason 项目描述（略）
     */
    public void setSettleReason(String settleReason) {
        this.settleReason = settleReason;
    }

    /**
     * unitName
     *
     * @return unitName 项目描述（略）
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * unitName
     *
     * @param unitName 项目描述（略）
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * constructCost
     *
     * @return constructCost 项目描述（略）
     */
    public Double getConstructCost() {
        return constructCost;
    }

    /**
     * constructCost
     *
     * @param constructCost 项目描述（略）
     */
    public void setConstructCost(Double constructCost) {
        this.constructCost = constructCost;
    }

    /**
     * unitCost
     *
     * @return unitCost 项目描述（略）
     */
    public Double getUnitCost() {
        return unitCost;
    }

    /**
     * unitCost
     *
     * @param unitCost 项目描述（略）
     */
    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    /**
     * costRatio
     *
     * @return costRatio 项目描述（略）
     */
    public Double getCostRatio() {
        return costRatio;
    }

    /**
     * costRatio
     *
     * @param costRatio 项目描述（略）
     */
    public void setCostRatio(Double costRatio) {
        this.costRatio = costRatio;
    }

    /**
     * expandId
     *
     * @return expandId 项目描述（略）
     */
    public String getExpandId() {
        return expandId;
    }

    /**
     * expandId
     *
     * @param expandId 项目描述（略）
     */
    public void setExpandId(String expandId) {
        this.expandId = expandId;
    }

    /**
     * tenderTypeContent
     *
     * @return tenderTypeContent 项目描述（略）
     */
    public String getTenderTypeContent() {
        return tenderTypeContent;
    }

    /**
     * tenderTypeContent
     *
     * @param tenderTypeContent 项目描述（略）
     */
    public void setTenderTypeContent(String tenderTypeContent) {
        this.tenderTypeContent = tenderTypeContent;
    }

    /**
     * workflow
     *
     * @return workflow 项目描述（略）
     */
    public String getWorkflow() {
        return workflow;
    }

    /**
     * workflow
     *
     * @param workflow 项目描述（略）
     */
    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    /**
     * days
     *
     * @return days 项目描述（略）
     */
    public Integer getDays() {
        return days;
    }

    /**
     * days
     *
     * @param days 项目描述（略）
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    /**
     * involvedDepartment
     *
     * @return involvedDepartment 项目描述（略）
     */
    public String getInvolvedDepartment() {
        return involvedDepartment;
    }

    /**
     * involvedDepartment
     *
     * @param involvedDepartment 项目描述（略）
     */
    public void setInvolvedDepartment(String involvedDepartment) {
        this.involvedDepartment = involvedDepartment;
    }

    /**
     * remark
     *
     * @return remark 项目描述（略）
     */
    public String getRemark() {
        return remark;
    }

    /**
     * remark
     *
     * @param remark 项目描述（略）
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * answerAdvise
     *
     * @return answerAdvise 项目描述（略）
     */
    public String getAnswerAdvise() {
        return answerAdvise;
    }

    /**
     * answerAdvise
     *
     * @param answerAdvise 项目描述（略）
     */
    public void setAnswerAdvise(String answerAdvise) {
        this.answerAdvise = answerAdvise;
    }

    /**
     * htTaskExecuteList
     *
     * @return htTaskExecuteList 项目描述（略）
     */
    public List<HTTaskExecuteBean> getHtTaskExecuteList() {
        return htTaskExecuteList;
    }

    /**
     * htTaskExecuteList
     *
     * @param htTaskExecuteList 项目描述（略）
     */
    public void setHtTaskExecuteList(List<HTTaskExecuteBean> htTaskExecuteList) {
        this.htTaskExecuteList = htTaskExecuteList;
    }

    /**
     * reportNumA3
     *
     * @return reportNumA3 项目描述（略）
     */
    public String getReportNumA3() {
        return reportNumA3;
    }

    /**
     * reportNumA3
     *
     * @param reportNumA3 项目描述（略）
     */
    public void setReportNumA3(String reportNumA3) {
        this.reportNumA3 = reportNumA3;
    }

    /**
     * isResultFile
     *
     * @return isResultFile 项目描述（略）
     */
    public String getIsResultFile() {
        return isResultFile;
    }

    /**
     * isResultFile
     *
     * @param isResultFile 项目描述（略）
     */
    public void setIsResultFile(String isResultFile) {
        this.isResultFile = isResultFile;
    }

    /**
     * contractPrice
     *
     * @return contractPrice 项目描述（略）
     */
    public Double getContractPrice() {
        return contractPrice;
    }

    /**
     * contractPrice
     *
     * @param contractPrice 项目描述（略）
     */
    public void setContractPrice(Double contractPrice) {
        this.contractPrice = contractPrice;
    }
}
