package com.hd.common.util.word.XWPFHandler;

import java.util.List;

/**
 * Created by EDZ on 2019/7/16.
 */
public class ServiceRangeModel implements Comparable<ServiceRangeModel>{


    /**
     *  服务阶段id TODO 待定
     */
    private String servicePhaseId;

    /**
     *  服务阶段
     */
    private String servicePhase;

    /**
     * 服务范围
     */
    private String serviceRange;

    /**
     * 工作内容
     */
    private String workContent;

    /**
     * 收费基数
     */
    private String chargeRadix;

    /**
     *  收费基数是否暂定
     */
    private String isChargeRadixProvisional;

    /**
     *  收费标准
     */
    private String chargeStandard;

    /**
     *  收费基数是否暂定
     */
    private String isChargeStandardProvisional;

    /**
     *  酬金
     */
    private String remuneration;

    /**
     *  收费基数是否暂定
     */
    private String isRemunerationProvisional;
    /**
     *  备注
     */
    private String remark;

    /**
     *  判断工作内容部分显示文字还是checkBox(0:显示checkBox,1:显示文字) TODO 待定
     */
    private String workContentFlg;

    /**
     *  工作内容类型(0:编制,1:审核,2:调整) TODO 待定
     */
    private String workContentType;

    /**
     *  是否选中(0:未选中，1：选中) TODO 待定
     */
    private String isCheckedFlg;

    /**
     *  工作内容checkBox集合 TODO 待定
     */
    private List<ServiceRangeModel> workContentList;


    /**
     *  成功文件名称 附录B
     */
    private String achievementFileName;

    /**
     *  成果文件组成 附录B
     */
    private String achievementFileCompose;

    /**
     * 提交时间 附录B
     */
    private String submitDate;

    /**
     * 文件份数 附录B
     */
    private String fileCount;

    /**
     *  名称
     */
    private String trusterName;

    /**
     *  份数 附录C
     */
    private String dataCount;

    /**
     * 提供时间 附录C
     */
    private String provideDate;

    /**
     *  质量标准 附录C
     */
    private String qualityStandard;

    /**
     * servicePhaseId
     *
     * @return servicePhaseId 项目描述（略）
     */
    public String getServicePhaseId() {
        return servicePhaseId;
    }

    /**
     * servicePhaseId
     *
     * @param servicePhaseId 项目描述（略）
     */
    public void setServicePhaseId(String servicePhaseId) {
        this.servicePhaseId = servicePhaseId;
    }

    /**
     * servicePhase
     *
     * @return servicePhase 项目描述（略）
     */
    public String getServicePhase() {
        return servicePhase;
    }

    /**
     * servicePhase
     *
     * @param servicePhase 项目描述（略）
     */
    public void setServicePhase(String servicePhase) {
        this.servicePhase = servicePhase;
    }

    /**
     * serviceRange
     *
     * @return serviceRange 项目描述（略）
     */
    public String getServiceRange() {
        return serviceRange;
    }

    /**
     * serviceRange
     *
     * @param serviceRange 项目描述（略）
     */
    public void setServiceRange(String serviceRange) {
        this.serviceRange = serviceRange;
    }

    /**
     * workContent
     *
     * @return workContent 项目描述（略）
     */
    public String getWorkContent() {
        return workContent;
    }

    /**
     * workContent
     *
     * @param workContent 项目描述（略）
     */
    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    /**
     * chargeRadix
     *
     * @return chargeRadix 项目描述（略）
     */
    public String getChargeRadix() {
        return chargeRadix;
    }

    /**
     * chargeRadix
     *
     * @param chargeRadix 项目描述（略）
     */
    public void setChargeRadix(String chargeRadix) {
        this.chargeRadix = chargeRadix;
    }

    /**
     * chargeStandard
     *
     * @return chargeStandard 项目描述（略）
     */
    public String getChargeStandard() {
        return chargeStandard;
    }

    /**
     * chargeStandard
     *
     * @param chargeStandard 项目描述（略）
     */
    public void setChargeStandard(String chargeStandard) {
        this.chargeStandard = chargeStandard;
    }

    /**
     * remuneration
     *
     * @return remuneration 项目描述（略）
     */
    public String getRemuneration() {
        return remuneration;
    }

    /**
     * remuneration
     *
     * @param remuneration 项目描述（略）
     */
    public void setRemuneration(String remuneration) {
        this.remuneration = remuneration;
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
     * workContentFlg
     *
     * @return workContentFlg 项目描述（略）
     */
    public String getWorkContentFlg() {
        return workContentFlg;
    }

    /**
     * workContentFlg
     *
     * @param workContentFlg 项目描述（略）
     */
    public void setWorkContentFlg(String workContentFlg) {
        this.workContentFlg = workContentFlg;
    }

    /**
     * workContentType
     *
     * @return workContentType 项目描述（略）
     */
    public String getWorkContentType() {
        return workContentType;
    }

    /**
     * workContentType
     *
     * @param workContentType 项目描述（略）
     */
    public void setWorkContentType(String workContentType) {
        this.workContentType = workContentType;
    }

    /**
     * isCheckedFlg
     *
     * @return isCheckedFlg 项目描述（略）
     */
    public String getIsCheckedFlg() {
        return isCheckedFlg;
    }

    /**
     * isCheckedFlg
     *
     * @param isCheckedFlg 项目描述（略）
     */
    public void setIsCheckedFlg(String isCheckedFlg) {
        this.isCheckedFlg = isCheckedFlg;
    }

    /**
     * workContentList
     *
     * @return workContentList 项目描述（略）
     */
    public List<ServiceRangeModel> getWorkContentList() {
        return workContentList;
    }

    /**
     * workContentList
     *
     * @param workContentList 项目描述（略）
     */
    public void setWorkContentList(List<ServiceRangeModel> workContentList) {
        this.workContentList = workContentList;
    }


    public int compareTo(ServiceRangeModel serviceRangeModel) {
        return this.getServicePhaseId().compareTo(serviceRangeModel.getServicePhaseId());
    }

    /**
     * achievementFileName
     *
     * @return achievementFileName 项目描述（略）
     */
    public String getAchievementFileName() {
        return achievementFileName;
    }

    /**
     * achievementFileName
     *
     * @param achievementFileName 项目描述（略）
     */
    public void setAchievementFileName(String achievementFileName) {
        this.achievementFileName = achievementFileName;
    }

    /**
     * achievementFileCompose
     *
     * @return achievementFileCompose 项目描述（略）
     */
    public String getAchievementFileCompose() {
        return achievementFileCompose;
    }

    /**
     * achievementFileCompose
     *
     * @param achievementFileCompose 项目描述（略）
     */
    public void setAchievementFileCompose(String achievementFileCompose) {
        this.achievementFileCompose = achievementFileCompose;
    }

    /**
     * submitDate
     *
     * @return submitDate 项目描述（略）
     */
    public String getSubmitDate() {
        return submitDate;
    }

    /**
     * submitDate
     *
     * @param submitDate 项目描述（略）
     */
    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    /**
     * fileCount
     *
     * @return fileCount 项目描述（略）
     */
    public String getFileCount() {
        return fileCount;
    }

    /**
     * fileCount
     *
     * @param fileCount 项目描述（略）
     */
    public void setFileCount(String fileCount) {
        this.fileCount = fileCount;
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
     * dataCount
     *
     * @return dataCount 项目描述（略）
     */
    public String getDataCount() {
        return dataCount;
    }

    /**
     * dataCount
     *
     * @param dataCount 项目描述（略）
     */
    public void setDataCount(String dataCount) {
        this.dataCount = dataCount;
    }

    /**
     * provideDate
     *
     * @return provideDate 项目描述（略）
     */
    public String getProvideDate() {
        return provideDate;
    }

    /**
     * provideDate
     *
     * @param provideDate 项目描述（略）
     */
    public void setProvideDate(String provideDate) {
        this.provideDate = provideDate;
    }

    /**
     * qualityStandard
     *
     * @return qualityStandard 项目描述（略）
     */
    public String getQualityStandard() {
        return qualityStandard;
    }

    /**
     * qualityStandard
     *
     * @param qualityStandard 项目描述（略）
     */
    public void setQualityStandard(String qualityStandard) {
        this.qualityStandard = qualityStandard;
    }

    /**
     * isChargeRadixProvisional
     *
     * @return isChargeRadixProvisional 项目描述（略）
     */
    public String getIsChargeRadixProvisional() {
        return isChargeRadixProvisional;
    }

    /**
     * isChargeRadixProvisional
     *
     * @param isChargeRadixProvisional 项目描述（略）
     */
    public void setIsChargeRadixProvisional(String isChargeRadixProvisional) {
        this.isChargeRadixProvisional = isChargeRadixProvisional;
    }

    /**
     * isChargeStandardProvisional
     *
     * @return isChargeStandardProvisional 项目描述（略）
     */
    public String getIsChargeStandardProvisional() {
        return isChargeStandardProvisional;
    }

    /**
     * isChargeStandardProvisional
     *
     * @param isChargeStandardProvisional 项目描述（略）
     */
    public void setIsChargeStandardProvisional(String isChargeStandardProvisional) {
        this.isChargeStandardProvisional = isChargeStandardProvisional;
    }

    /**
     * isRemunerationProvisional
     *
     * @return isRemunerationProvisional 项目描述（略）
     */
    public String getIsRemunerationProvisional() {
        return isRemunerationProvisional;
    }

    /**
     * isRemunerationProvisional
     *
     * @param isRemunerationProvisional 项目描述（略）
     */
    public void setIsRemunerationProvisional(String isRemunerationProvisional) {
        this.isRemunerationProvisional = isRemunerationProvisional;
    }
}
