package com.hd.common.entity;

import java.util.Date;

public class TbUserEntity {
    /**
     * 主键ID
     */
    private String userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 文化程度
     */
    private String education;

    /**
     * 所属公司ID
     */
    private String companyId;

    /**
     * 入职日期
     */
    private Date entryDate;

    /**
     * 是否在职   1:在职/0:离职
     */
    private String isIncumbent;

    /**
     * 签名路径
     */
    private String signPath;

    /**
     * 所属专业
     */
    private String professionalName;

    /**
     * 微信openid
     */
    private String openId;

    /**
     * 微信token
     */
    private String accessToken;

    /**
     * 保存access_token的时间
     */
    private Date saveAccessTokenDate;

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
     * 主键ID
     * @return user_id 主键ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 主键ID
     * @param userId 主键ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 用户账号
     * @return user_account 用户账号
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 用户账号
     * @param userAccount 用户账号
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    /**
     * 用户姓名
     * @return user_name 用户姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户姓名
     * @param userName 用户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 手机号码
     * @return telephone 手机号码
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 手机号码
     * @param telephone 手机号码
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
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
     * 文化程度
     * @return education 文化程度
     */
    public String getEducation() {
        return education;
    }

    /**
     * 文化程度
     * @param education 文化程度
     */
    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    /**
     * 所属公司ID
     * @return company_id 所属公司ID
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 所属公司ID
     * @param companyId 所属公司ID
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    /**
     * 入职日期
     * @return entry_date 入职日期
     */
    public Date getEntryDate() {
        return entryDate;
    }

    /**
     * 入职日期
     * @param entryDate 入职日期
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * 是否在职   1:在职/0:离职
     * @return is_incumbent 是否在职   1:在职/0:离职
     */
    public String getIsIncumbent() {
        return isIncumbent;
    }

    /**
     * 是否在职   1:在职/0:离职
     * @param isIncumbent 是否在职   1:在职/0:离职
     */
    public void setIsIncumbent(String isIncumbent) {
        this.isIncumbent = isIncumbent == null ? null : isIncumbent.trim();
    }

    /**
     * 签名路径
     * @return sign_path 签名路径
     */
    public String getSignPath() {
        return signPath;
    }

    /**
     * 签名路径
     * @param signPath 签名路径
     */
    public void setSignPath(String signPath) {
        this.signPath = signPath == null ? null : signPath.trim();
    }

    /**
     * 所属专业
     * @return professional_name 所属专业
     */
    public String getProfessionalName() {
        return professionalName;
    }

    /**
     * 所属专业
     * @param professionalName 所属专业
     */
    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName == null ? null : professionalName.trim();
    }

    /**
     * 微信openid
     * @return open_id 微信openid
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 微信openid
     * @param openId 微信openid
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 微信token
     * @return access_token 微信token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * 微信token
     * @param accessToken 微信token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    /**
     * 保存access_token的时间
     * @return save_access_token_date 保存access_token的时间
     */
    public Date getSaveAccessTokenDate() {
        return saveAccessTokenDate;
    }

    /**
     * 保存access_token的时间
     * @param saveAccessTokenDate 保存access_token的时间
     */
    public void setSaveAccessTokenDate(Date saveAccessTokenDate) {
        this.saveAccessTokenDate = saveAccessTokenDate;
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