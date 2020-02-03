package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.io.Serializable;
import java.util.List;

/**
 * 合同VO
 *
 * @author jwl
 * Created in 2019/7/17 15:31
 */
public class HTContractVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 3963297304371899436L;
    
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
     * 项目类型
     */
    private String projectType;

    /**
     * 职位ID
     */
    private String jobId;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 提审人
     */
    private String applyUserName;

    /**
     * 流程完成时间
     */
    private String completeTime;

    /**
     * 其他属性
     */
    private String projectTarget;

    /**
     * 项目类型列表
     */
    private List<HTContractVO> projectTypeList;

    /**
     * 项目特征列表
     */
    private List<HTContractVO> projectFeatureList;

    /**
     * 占地面积
     */
    private String landArea;

    /**
     * 建筑面积
     */
    private String floorArea;

    /**
     * 人容量
     */
    private String persionCapacity;

    /**
     * 檐高
     */
    private String eavesHeight;

    /**
     * 建筑控制高度
     */
    private String buildingControlHeight;

    /**
     * 容积率
     */
    private String volumeRatio;

    /**
     * 住宅类别
     */
    private String homeType;

    /**
     * 房间数量
     */
    private String roomNum;

    /**
     * 层数
     */
    private String level;

    /**
     * 跨度
     */
    private String span;

    /**
     * 生产状况
     */
    private String produceCondition;

    /**
     * 绿化率
     */
    private String makeGreenRatio;

    /**
     * 绿色等级
     */
    private String greenLevel;

    /**
     * 装机容量
     */
    private String installationCapacity;

    /**
     * 发电机变压器参数数量
     */
    private String transformersNum;

    /**
     * 线缆敷设长度
     */
    private String cableLayingLength;

    /**
     * 线缆敷设方式
     */
    private String cableLayingMethod;

    /**
     * 输电量
     */
    private String transmissionCapacity;

    /**
     * 等级
     */
    private String grade;

    /**
     * 全线（桥）长度
     */
    private String bridgeLength;

    /**
     * 设计速度
     */
    private String designSpeed;

    /**
     * 轨道类型
     */
    private String orbitalType;

    /**
     * 桥梁类型
     */
    private String bridgeType;

    /**
     * 正常运用水位
     */
    private String normalOperatingWaterLevel;

    /**
     * 总库容
     */
    private String totalStorageCapacity;

    /**
     * 最大坝高
     */
    private String maximumDamHeight;

    /**
     * 电站装机容量
     */
    private String installedCapacityOfPowerStation;

    /**
     * 其他（长度/规模/范围等）
     */
    private String others;

    /**
     * 港口（码头）吞吐量
     */
    private String portThroughput;

    /**
     * 航道（闸机）通过量
     */
    private String channelThroughput;

    /**
     * 主要货物的流量流向
     */
    private String flowDirection;

    /**
     * 分航线运量
     */
    private String branchLineVolume;

    /**
     * 船舶流量
     */
    private String shipFlow;

    /**
     * 集疏方式
     */
    private String gatheringAndDistributingWays;

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
     * 投资金额
     */
    private String investedAmount;

    /**
     * 是否暂定(投资金额)
     */
    private String isProvisional;

    /**
     * 项目性质
     */
    private List<String> projectNatureList;

    /**
     * 委托人
     */
    private String trusterName;

    /**
     * 委托人性质
     */
    private List<String> trusterTypeList;

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
     * 第三方（合作单位）
     */
    private String thirdParty;

    /**
     * 扉页咨询人
     */
    private String titlePageConsultant;

    /**
     * 拟定合同负责人
     */
    private String dutyPeople;

    /**
     * 拟定人
     */
    private String planner;

    /**
     * 合同性质
     */
    private List<String> contractNatureList;

    /**
     * 确定方式
     */
    private List<String> determinateMethodList;

    /**
     * 拟定合同金额
     */
    private String contractAmount;

    /**
     * 咨询合同签订日期
     */
    private String contractDate;

    /**
     * 拟定合同金额暂定
     */
    private String isValueProvisional;

    /**
     * 其他情况
     */
    private String otherThings;

    /**
     * 合同类型
     */
    private String contractType;

    /**
     * 模板合同类型
     */
    private String formworkType;

    /**
     * 预计收款id
     */
    private String predictRequestId;

    /**
     * 收款金额
     */
    private String receivedAmount;

    /**
     * 预计收款时间列表
     */
    private List<HTContractVO> predictRequestDateList;

    /**
     * 预计收款时间
     */
    private String startDate;

    /**
     * 合同信息
     */
    private String formworkContent;


    /**
     *  自定义合同真实名字
     */
    private String contractFileName;

    /**
     * 自定义合同路径
     */
    private String contractFilePath;

    /**
     * 是否通过
     * 1：已通过
     * 0：未通过
     */
    private String isPass;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     *  项目id
     */
    private String projectId;

    /**
     * 成员ID
     */
    private String userId;

    /**
     * 成员名称
     */
    private String userName;

    private List<HTContractVO> userList;

    /**
     * 文件路径
     */
    private String materialPath;

    /**
     * 关联ID
     */
    private String relationId;

    /**
     *  文件名称
     */
    private String materialName;

    /**
     *  档案编号
     */
    private String fileNumber;

    /**
     *  项目编号
     */
    private String projectNum;

    /**
     * 审核记录和人员和问题类型关联
     */
    private List<HTUserQuestionRecordRelationVO> userQuestionRecordRelationList;

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
     * 作废原因
     */
    private String invalidReason;

    /**
     * 作废人ID
     */
    private String invalidUserId;

    private String cooperateContract;

    /*--------------------------------get/set---------------------------------------*/


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getProjectTarget() {
        return projectTarget;
    }

    public void setProjectTarget(String projectTarget) {
        this.projectTarget = projectTarget;
    }

    public List<HTContractVO> getProjectTypeList() {
        return projectTypeList;
    }

    public void setProjectTypeList(List<HTContractVO> projectTypeList) {
        this.projectTypeList = projectTypeList;
    }

    public List<HTContractVO> getProjectFeatureList() {
        return projectFeatureList;
    }

    public void setProjectFeatureList(List<HTContractVO> projectFeatureList) {
        this.projectFeatureList = projectFeatureList;
    }

    public String getLandArea() {
        return landArea;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    public String getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(String floorArea) {
        this.floorArea = floorArea;
    }

    public String getPersionCapacity() {
        return persionCapacity;
    }

    public void setPersionCapacity(String persionCapacity) {
        this.persionCapacity = persionCapacity;
    }

    public String getEavesHeight() {
        return eavesHeight;
    }

    public void setEavesHeight(String eavesHeight) {
        this.eavesHeight = eavesHeight;
    }

    public String getBuildingControlHeight() {
        return buildingControlHeight;
    }

    public void setBuildingControlHeight(String buildingControlHeight) {
        this.buildingControlHeight = buildingControlHeight;
    }

    public String getVolumeRatio() {
        return volumeRatio;
    }

    public void setVolumeRatio(String volumeRatio) {
        this.volumeRatio = volumeRatio;
    }

    public String getHomeType() {
        return homeType;
    }

    public void setHomeType(String homeType) {
        this.homeType = homeType;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

    public String getProduceCondition() {
        return produceCondition;
    }

    public void setProduceCondition(String produceCondition) {
        this.produceCondition = produceCondition;
    }

    public String getMakeGreenRatio() {
        return makeGreenRatio;
    }

    public void setMakeGreenRatio(String makeGreenRatio) {
        this.makeGreenRatio = makeGreenRatio;
    }

    public String getGreenLevel() {
        return greenLevel;
    }

    public void setGreenLevel(String greenLevel) {
        this.greenLevel = greenLevel;
    }

    public String getInstallationCapacity() {
        return installationCapacity;
    }

    public void setInstallationCapacity(String installationCapacity) {
        this.installationCapacity = installationCapacity;
    }

    public String getTransformersNum() {
        return transformersNum;
    }

    public void setTransformersNum(String transformersNum) {
        this.transformersNum = transformersNum;
    }

    public String getCableLayingLength() {
        return cableLayingLength;
    }

    public void setCableLayingLength(String cableLayingLength) {
        this.cableLayingLength = cableLayingLength;
    }

    public String getCableLayingMethod() {
        return cableLayingMethod;
    }

    public void setCableLayingMethod(String cableLayingMethod) {
        this.cableLayingMethod = cableLayingMethod;
    }

    public String getTransmissionCapacity() {
        return transmissionCapacity;
    }

    public void setTransmissionCapacity(String transmissionCapacity) {
        this.transmissionCapacity = transmissionCapacity;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBridgeLength() {
        return bridgeLength;
    }

    public void setBridgeLength(String bridgeLength) {
        this.bridgeLength = bridgeLength;
    }

    public String getDesignSpeed() {
        return designSpeed;
    }

    public void setDesignSpeed(String designSpeed) {
        this.designSpeed = designSpeed;
    }

    public String getOrbitalType() {
        return orbitalType;
    }

    public void setOrbitalType(String orbitalType) {
        this.orbitalType = orbitalType;
    }

    public String getBridgeType() {
        return bridgeType;
    }

    public void setBridgeType(String bridgeType) {
        this.bridgeType = bridgeType;
    }

    public String getNormalOperatingWaterLevel() {
        return normalOperatingWaterLevel;
    }

    public void setNormalOperatingWaterLevel(String normalOperatingWaterLevel) {
        this.normalOperatingWaterLevel = normalOperatingWaterLevel;
    }

    public String getTotalStorageCapacity() {
        return totalStorageCapacity;
    }

    public void setTotalStorageCapacity(String totalStorageCapacity) {
        this.totalStorageCapacity = totalStorageCapacity;
    }

    public String getMaximumDamHeight() {
        return maximumDamHeight;
    }

    public void setMaximumDamHeight(String maximumDamHeight) {
        this.maximumDamHeight = maximumDamHeight;
    }

    public String getInstalledCapacityOfPowerStation() {
        return installedCapacityOfPowerStation;
    }

    public void setInstalledCapacityOfPowerStation(String installedCapacityOfPowerStation) {
        this.installedCapacityOfPowerStation = installedCapacityOfPowerStation;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getPortThroughput() {
        return portThroughput;
    }

    public void setPortThroughput(String portThroughput) {
        this.portThroughput = portThroughput;
    }

    public String getChannelThroughput() {
        return channelThroughput;
    }

    public void setChannelThroughput(String channelThroughput) {
        this.channelThroughput = channelThroughput;
    }

    public String getFlowDirection() {
        return flowDirection;
    }

    public void setFlowDirection(String flowDirection) {
        this.flowDirection = flowDirection;
    }

    public String getBranchLineVolume() {
        return branchLineVolume;
    }

    public void setBranchLineVolume(String branchLineVolume) {
        this.branchLineVolume = branchLineVolume;
    }

    public String getShipFlow() {
        return shipFlow;
    }

    public void setShipFlow(String shipFlow) {
        this.shipFlow = shipFlow;
    }

    public String getGatheringAndDistributingWays() {
        return gatheringAndDistributingWays;
    }

    public void setGatheringAndDistributingWays(String gatheringAndDistributingWays) {
        this.gatheringAndDistributingWays = gatheringAndDistributingWays;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCounselBusinessType() {
        return counselBusinessType;
    }

    public void setCounselBusinessType(String counselBusinessType) {
        this.counselBusinessType = counselBusinessType;
    }

    public String getCounselTypePeriod() {
        return counselTypePeriod;
    }

    public void setCounselTypePeriod(String counselTypePeriod) {
        this.counselTypePeriod = counselTypePeriod;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(String investedAmount) {
        this.investedAmount = investedAmount;
    }

    public String getIsProvisional() {
        return isProvisional;
    }

    public void setIsProvisional(String isProvisional) {
        this.isProvisional = isProvisional;
    }

    public List<String> getProjectNatureList() {
        return projectNatureList;
    }

    public void setProjectNatureList(List<String> projectNatureList) {
        this.projectNatureList = projectNatureList;
    }

    public String getTrusterName() {
        return trusterName;
    }

    public void setTrusterName(String trusterName) {
        this.trusterName = trusterName;
    }

    public List<String> getTrusterTypeList() {
        return trusterTypeList;
    }

    public void setTrusterTypeList(List<String> trusterTypeList) {
        this.trusterTypeList = trusterTypeList;
    }

    public String getTrusterDutyUser() {
        return trusterDutyUser;
    }

    public void setTrusterDutyUser(String trusterDutyUser) {
        this.trusterDutyUser = trusterDutyUser;
    }

    public String getTrusterDutyPhone() {
        return trusterDutyPhone;
    }

    public void setTrusterDutyPhone(String trusterDutyPhone) {
        this.trusterDutyPhone = trusterDutyPhone;
    }

    public String getConsultUser() {
        return consultUser;
    }

    public void setConsultUser(String consultUser) {
        this.consultUser = consultUser;
    }

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }

    public String getDutyPeople() {
        return dutyPeople;
    }

    public void setDutyPeople(String dutyPeople) {
        this.dutyPeople = dutyPeople;
    }

    public String getPlanner() {
        return planner;
    }

    public void setPlanner(String planner) {
        this.planner = planner;
    }

    public List<String> getContractNatureList() {
        return contractNatureList;
    }

    public void setContractNatureList(List<String> contractNatureList) {
        this.contractNatureList = contractNatureList;
    }

    public List<String> getDeterminateMethodList() {
        return determinateMethodList;
    }

    public void setDeterminateMethodList(List<String> determinateMethodList) {
        this.determinateMethodList = determinateMethodList;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getIsValueProvisional() {
        return isValueProvisional;
    }

    public void setIsValueProvisional(String isValueProvisional) {
        this.isValueProvisional = isValueProvisional;
    }

    public String getOtherThings() {
        return otherThings;
    }

    public void setOtherThings(String otherThings) {
        this.otherThings = otherThings;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getFormworkType() {
        return formworkType;
    }

    public void setFormworkType(String formworkType) {
        this.formworkType = formworkType;
    }

    public String getPredictRequestId() {
        return predictRequestId;
    }

    public void setPredictRequestId(String predictRequestId) {
        this.predictRequestId = predictRequestId;
    }

    public String getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(String receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public List<HTContractVO> getPredictRequestDateList() {
        return predictRequestDateList;
    }

    public void setPredictRequestDateList(List<HTContractVO> predictRequestDateList) {
        this.predictRequestDateList = predictRequestDateList;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFormworkContent() {
        return formworkContent;
    }

    public void setFormworkContent(String formworkContent) {
        this.formworkContent = formworkContent;
    }

    public String getContractFileName() {
        return contractFileName;
    }

    public void setContractFileName(String contractFileName) {
        this.contractFileName = contractFileName;
    }

    public String getContractFilePath() {
        return contractFilePath;
    }

    public void setContractFilePath(String contractFilePath) {
        this.contractFilePath = contractFilePath;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<HTContractVO> getUserList() {
        return userList;
    }

    public void setUserList(List<HTContractVO> userList) {
        this.userList = userList;
    }

    public String getMaterialPath() {
        return materialPath;
    }

    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public List<HTUserQuestionRecordRelationVO> getUserQuestionRecordRelationList() {
        return userQuestionRecordRelationList;
    }

    public void setUserQuestionRecordRelationList(List<HTUserQuestionRecordRelationVO> userQuestionRecordRelationList) {
        this.userQuestionRecordRelationList = userQuestionRecordRelationList;
    }

    /**
     * titlePageConsultant
     *
     * @return titlePageConsultant 项目描述（略）
     */
    public String getTitlePageConsultant() {
        return titlePageConsultant;
    }

    /**
     * titlePageConsultant
     *
     * @param titlePageConsultant 项目描述（略）
     */
    public void setTitlePageConsultant(String titlePageConsultant) {
        this.titlePageConsultant = titlePageConsultant;
    }

    public String getLegalOpinion() {
        return legalOpinion;
    }

    public void setLegalOpinion(String legalOpinion) {
        this.legalOpinion = legalOpinion;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getContractSealType() {
        return contractSealType;
    }

    public void setContractSealType(String contractSealType) {
        this.contractSealType = contractSealType;
    }

    public String getCooperateCompany() {
        return cooperateCompany;
    }

    public void setCooperateCompany(String cooperateCompany) {
        this.cooperateCompany = cooperateCompany;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public String getInvalidUserId() {
        return invalidUserId;
    }

    public void setInvalidUserId(String invalidUserId) {
        this.invalidUserId = invalidUserId;
    }

    public String getCooperateContract() {
        return cooperateContract;
    }

    public void setCooperateContract(String cooperateContract) {
        this.cooperateContract = cooperateContract;
    }
}
