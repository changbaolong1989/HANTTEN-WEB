package com.hd.common.entity;

import java.util.Date;

public class TbCompanyEntity {
    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司名称缩写
     */
    private String condense;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 账号
     */
    private String bankAccount;

    /**
     * 电话（开票用电话）
     */
    private String phoneNum;

    /**
     * 电话（对外联系用）
     */
    private String phoneForeign;

    /**
     * 传真
     */
    private String faxNum;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 负责人
     */
    private String chargePeople;

    /**
     * 序号
     */
    private String serialNum;

    /**
     * 是否是合作公司（否：0，是：1）
     */
    private String isCooperateCompany;

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
     * 公司ID
     * @return company_id 公司ID
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 公司ID
     * @param companyId 公司ID
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    /**
     * 公司名称
     * @return company_name 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 公司名称
     * @param companyName 公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * 公司名称缩写
     * @return condense 公司名称缩写
     */
    public String getCondense() {
        return condense;
    }

    /**
     * 公司名称缩写
     * @param condense 公司名称缩写
     */
    public void setCondense(String condense) {
        this.condense = condense == null ? null : condense.trim();
    }

    /**
     * 公司地址
     * @return company_address 公司地址
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * 公司地址
     * @param companyAddress 公司地址
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    /**
     * 开户行
     * @return bank_name 开户行
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 开户行
     * @param bankName 开户行
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * 账号
     * @return bank_account 账号
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 账号
     * @param bankAccount 账号
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    /**
     * 电话（开票用电话）
     * @return phone_num 电话（开票用电话）
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * 电话（开票用电话）
     * @param phoneNum 电话（开票用电话）
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    /**
     * 电话（对外联系用）
     * @return phone_foreign 电话（对外联系用）
     */
    public String getPhoneForeign() {
        return phoneForeign;
    }

    /**
     * 电话（对外联系用）
     * @param phoneForeign 电话（对外联系用）
     */
    public void setPhoneForeign(String phoneForeign) {
        this.phoneForeign = phoneForeign == null ? null : phoneForeign.trim();
    }

    /**
     * 传真
     * @return fax_num 传真
     */
    public String getFaxNum() {
        return faxNum;
    }

    /**
     * 传真
     * @param faxNum 传真
     */
    public void setFaxNum(String faxNum) {
        this.faxNum = faxNum == null ? null : faxNum.trim();
    }

    /**
     * 邮编
     * @return postcode 邮编
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * 邮编
     * @param postcode 邮编
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    /**
     * 邮箱
     * @return email 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 负责人
     * @return charge_people 负责人
     */
    public String getChargePeople() {
        return chargePeople;
    }

    /**
     * 负责人
     * @param chargePeople 负责人
     */
    public void setChargePeople(String chargePeople) {
        this.chargePeople = chargePeople == null ? null : chargePeople.trim();
    }

    /**
     * 序号
     * @return serial_num 序号
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * 序号
     * @param serialNum 序号
     */
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum == null ? null : serialNum.trim();
    }

    /**
     * 是否是合作公司（否：0，是：1）
     * @return is_cooperate_company 是否是合作公司（否：0，是：1）
     */
    public String getIsCooperateCompany() {
        return isCooperateCompany;
    }

    /**
     * 是否是合作公司（否：0，是：1）
     * @param isCooperateCompany 是否是合作公司（否：0，是：1）
     */
    public void setIsCooperateCompany(String isCooperateCompany) {
        this.isCooperateCompany = isCooperateCompany == null ? null : isCooperateCompany.trim();
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