package com.hd.manager.dao.bean;

import com.hd.base.bean.BaseBean;
import com.hd.common.entity.TbCompanyEntity;
import com.hd.common.entity.TbEducationEntity;
import com.hd.common.entity.TbJobEntity;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户Bean
 *
 * @author zjy
 * Created in 2019/7/1 17:12
 */
public class HTUserBean extends BaseBean {

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 是否为合作公司
     */
    private String isCooperateCompany;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 专业ID
     */
    private String professionalId;

    /**
     * 专业名称
     */
    private String professionalName;

    /**
     * 文化程度ID
     */
    private String educationId;

    /**
     * 文化程度名称
     */
    private String educationName;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门层级
     */
    private String deptLevel;

    /**
     * 职务ID
     */
    private String jobId;

    /**
     * 职务名称
     */
    private String jobName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 登陆账户
     */
    private String userAccount;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 入职时间
     */
    private Date entryDate;

    /**
     * 是否在职
     */
    private String isIncumbent;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 个性签名
     */
    private String signPathFile;

    /**
     * 资质文件
     */
    private String resourceFilePath;

    /**
     * 关联ID
     */
    private String relationId;

    /**
     * 是否在职
     */
    private String condIsIncumbent;


    /**
     * 学历
     */
    private TbEducationEntity educationEntity;

    /**
     * 公司
     */
    private TbCompanyEntity companyEntity;

    /**
     * 职位
     */
    private List<TbJobEntity> jobEntity;

    private Set<String> jobIds ;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * openid
     */
    private String openId;

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * saveAccessTokenDate
     */
    private Date saveAccessTokenDate;

    /**
     * accessToken
     *
     * @return accessToken 项目描述（略）
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * accessToken
     *
     * @param accessToken 项目描述（略）
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * saveAccessTokenDate
     *
     * @return saveAccessTokenDate 项目描述（略）
     */
    public Date getSaveAccessTokenDate() {
        return saveAccessTokenDate;
    }

    /**
     * saveAccessTokenDate
     *
     * @param saveAccessTokenDate 项目描述（略）
     */
    public void setSaveAccessTokenDate(Date saveAccessTokenDate) {
        this.saveAccessTokenDate = saveAccessTokenDate;
    }

    /**
     * openId
     *
     * @return openId 项目描述（略）
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * openId
     *
     * @param openId 项目描述（略）
     */
    public void setOpenId(String openId) {
        this.openId = openId;
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
     * companyId
     *
     * @return companyId 项目描述（略）
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * companyId
     *
     * @param companyId 项目描述（略）
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * companyName
     *
     * @return companyName 项目描述（略）
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * companyName
     *
     * @param companyName 项目描述（略）
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * isCooperateCompany
     *
     * @return isCooperateCompany 项目描述（略）
     */
    public String getIsCooperateCompany() {
        return isCooperateCompany;
    }

    /**
     * isCooperateCompany
     *
     * @param isCooperateCompany 项目描述（略）
     */
    public void setIsCooperateCompany(String isCooperateCompany) {
        this.isCooperateCompany = isCooperateCompany;
    }

    /**
     * roleId
     *
     * @return roleId 项目描述（略）
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * roleId
     *
     * @param roleId 项目描述（略）
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * roleName
     *
     * @return roleName 项目描述（略）
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * roleName
     *
     * @param roleName 项目描述（略）
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * professionalId
     *
     * @return professionalId 项目描述（略）
     */
    public String getProfessionalId() {
        return professionalId;
    }

    /**
     * professionalId
     *
     * @param professionalId 项目描述（略）
     */
    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    /**
     * professionalName
     *
     * @return professionalName 项目描述（略）
     */
    public String getProfessionalName() {
        return professionalName;
    }

    /**
     * professionalName
     *
     * @param professionalName 项目描述（略）
     */
    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    /**
     * educationId
     *
     * @return educationId 项目描述（略）
     */
    public String getEducationId() {
        return educationId;
    }

    /**
     * educationId
     *
     * @param educationId 项目描述（略）
     */
    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }

    /**
     * educationName
     *
     * @return educationName 项目描述（略）
     */
    public String getEducationName() {
        return educationName;
    }

    /**
     * educationName
     *
     * @param educationName 项目描述（略）
     */
    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    /**
     * deptId
     *
     * @return deptId 项目描述（略）
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * deptId
     *
     * @param deptId 项目描述（略）
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /**
     * deptName
     *
     * @return deptName 项目描述（略）
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * deptName
     *
     * @param deptName 项目描述（略）
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * jobId
     *
     * @return jobId 项目描述（略）
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * jobId
     *
     * @param jobId 项目描述（略）
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * jobName
     *
     * @return jobName 项目描述（略）
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * jobName
     *
     * @param jobName 项目描述（略）
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
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
     * deptLevel
     *
     * @return deptLevel 项目描述（略）
     */
    public String getDeptLevel() {
        return deptLevel;
    }

    /**
     * deptLevel
     *
     * @param deptLevel 项目描述（略）
     */
    public void setDeptLevel(String deptLevel) {
        this.deptLevel = deptLevel;
    }

    /**
     * userAccount
     *
     * @return userAccount 项目描述（略）
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * userAccount
     *
     * @param userAccount 项目描述（略）
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * password
     *
     * @return password 项目描述（略）
     */
    public String getPassword() {
        return password;
    }

    /**
     * password
     *
     * @param password 项目描述（略）
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * entryDate
     *
     * @return entryDate 项目描述（略）
     */
    public Date getEntryDate() {
        return entryDate;
    }

    /**
     * entryDate
     *
     * @param entryDate 项目描述（略）
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * isIncumbent
     *
     * @return isIncumbent 项目描述（略）
     */
    public String getIsIncumbent() {
        return isIncumbent;
    }

    /**
     * isIncumbent
     *
     * @param isIncumbent 项目描述（略）
     */
    public void setIsIncumbent(String isIncumbent) {
        this.isIncumbent = isIncumbent;
    }

    /**
     * signPathFile
     *
     * @return signPathFile 项目描述（略）
     */
    public String getSignPathFile() {
        return signPathFile;
    }

    /**
     * signPathFile
     *
     * @param signPathFile 项目描述（略）
     */
    public void setSignPathFile(String signPathFile) {
        this.signPathFile = signPathFile;
    }

    /**
     * resourceFilePath
     *
     * @return resourceFilePath 项目描述（略）
     */
    public String getResourceFilePath() {
        return resourceFilePath;
    }

    /**
     * resourceFilePath
     *
     * @param resourceFilePath 项目描述（略）
     */
    public void setResourceFilePath(String resourceFilePath) {
        this.resourceFilePath = resourceFilePath;
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
     * condIsIncumbent
     *
     * @return condIsIncumbent 项目描述（略）
     */
    public String getCondIsIncumbent() {
        return condIsIncumbent;
    }

    /**
     * condIsIncumbent
     *
     * @param condIsIncumbent 项目描述（略）
     */
    public void setCondIsIncumbent(String condIsIncumbent) {
        this.condIsIncumbent = condIsIncumbent;
    }


    public TbEducationEntity getEducationEntity() {
        return educationEntity;
    }

    public void setEducationEntity(TbEducationEntity educationEntity) {
        this.educationEntity = educationEntity;
    }

    public TbCompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(TbCompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public List<TbJobEntity> getJobEntity() {
        return jobEntity;
    }

    public void setJobEntity(List<TbJobEntity> jobEntity) {
        this.jobEntity = jobEntity;
    }

    public Set<String> getJobIds() {
        return jobIds;
    }

    public void setJobIds(Set<String> jobIds) {
        this.jobIds = jobIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
