package com.hd.manager.vo;

import com.hd.base.vo.BaseVO;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 用户VO
 *
 * @author zjy
 * Created in 2019/7/1 17:12
 */
public class HTUserVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -485728124658093183L;

    /**
     * 查询条件：用户名称
     */
    private String condUserName;

    /**
     * 查询条件：部门ID
     */
    private String condDeptId;

    /**
     * 查询条件：在职状态
     */
    private String condIsIncumbent;

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
     * 部门名称
     */
    private String telephone;

    private String email;

    /**
     * 部门和职位的关联ID
     */
    private String deptJobId;

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
     * 原密码
     */
    private String oldPwd;
    /**
     * 入职时间
     */
    private String entryDate;

    /**
     * 个性签名路径
     */
    private String signPath;

    /**
     * 专业list
     */
    private List<String> professionalIds;

    /**
     * 是否在职
     */
    private String isIncumbent;

    /**
     * 是否重置密码(0:否 1:是)
     */
    private String isResetPwd;

    /**
     * 资质文件List
     */
    private List<String> resourceRelationList;

    /**
     * 删除的资质文件List
     */
    private List<String> deleteResourceList;

    /**
     * 登陆用户ID
     */
    private String loginUserId;

    /**
     * 用户所属部门ID集合
     */
    private List<String> departmentIds;

    /**
     * 用户所属职位ID集合
     */
    private Set<String> jobIds;

    /**
     * 用户所有菜单功能
     */
    private List<HTMenuVO> menuList;

    /**
     * 用户文件列表
     */
    private List<HTUserFileRelationVO> flieList;

    private List<HTUserJobVO> jobList;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 邮箱单点登录访问标识
     */
    private String token;

    /**
     * 微信的openid
     */
    private String openId;

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
    public String getEntryDate() {
        return entryDate;
    }

    /**
     * entryDate
     *
     * @param entryDate 项目描述（略）
     */
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * signPath
     *
     * @return signPath 项目描述（略）
     */
    public String getSignPath() {
        return signPath;
    }

    /**
     * signPath
     *
     * @param signPath 项目描述（略）
     */
    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }

    /**
     * deptJobId
     *
     * @return deptJobId 项目描述（略）
     */
    public String getDeptJobId() {
        return deptJobId;
    }

    /**
     * deptJobId
     *
     * @param deptJobId 项目描述（略）
     */
    public void setDeptJobId(String deptJobId) {
        this.deptJobId = deptJobId;
    }

    /**
     * professionalIds
     *
     * @return professionalIds 项目描述（略）
     */
    public List<String> getProfessionalIds() {
        return professionalIds;
    }

    /**
     * professionalIds
     *
     * @param professionalIds 项目描述（略）
     */
    public void setProfessionalIds(List<String> professionalIds) {
        this.professionalIds = professionalIds;
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
     * resourceRelationList
     *
     * @return resourceRelationList 项目描述（略）
     */
    public List<String> getResourceRelationList() {
        return resourceRelationList;
    }

    /**
     * resourceRelationList
     *
     * @param resourceRelationList 项目描述（略）
     */
    public void setResourceRelationList(List<String> resourceRelationList) {
        this.resourceRelationList = resourceRelationList;
    }

    /**
     * isResetPwd
     *
     * @return isResetPwd 项目描述（略）
     */
    public String getIsResetPwd() {
        return isResetPwd;
    }

    /**
     * isResetPwd
     *
     * @param isResetPwd 项目描述（略）
     */
    public void setIsResetPwd(String isResetPwd) {
        this.isResetPwd = isResetPwd;
    }

    /**
     * condUserName
     *
     * @return condUserName 项目描述（略）
     */
    public String getCondUserName() {
        return condUserName;
    }

    /**
     * condUserName
     *
     * @param condUserName 项目描述（略）
     */
    public void setCondUserName(String condUserName) {
        this.condUserName = condUserName;
    }

    /**
     * condDeptId
     *
     * @return condDeptId 项目描述（略）
     */
    public String getCondDeptId() {
        return condDeptId;
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

    /**
     * condDeptId
     *
     * @param condDeptId 项目描述（略）
     */
    public void setCondDeptId(String condDeptId) {
        this.condDeptId = condDeptId;
    }


    /**
     * telephone
     *
     * @return telephone 项目描述（略）
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * telephone
     *
     * @param telephone 项目描述（略）
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * email
     *
     * @return email 项目描述（略）
     */
    public String getEmail() {
        return email;
    }

    /**
     * email
     *
     * @param email 项目描述（略）
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * deleteResourceList
     *
     * @return deleteResourceList 项目描述（略）
     */
    public List<String> getDeleteResourceList() {
        return deleteResourceList;
    }

    /**
     * deleteResourceList
     *
     * @param deleteResourceList 项目描述（略）
     */
    public void setDeleteResourceList(List<String> deleteResourceList) {
        this.deleteResourceList = deleteResourceList;
    }

    /**
     * loginUserId
     *
     * @return loginUserId 项目描述（略）
     */
    public String getLoginUserId() {
        return loginUserId;
    }

    /**
     * loginUserId
     *
     * @param loginUserId 项目描述（略）
     */
    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }


    public List<String> getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(List<String> departmentIds) {
        this.departmentIds = departmentIds;
    }

    public Set<String> getJobIds() {
        return jobIds;
    }

    public void setJobIds(Set<String> jobIds) {
        this.jobIds = jobIds;
    }

    public List<HTMenuVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<HTMenuVO> menuList) {
        this.menuList = menuList;
    }

    public List<HTUserFileRelationVO> getFlieList() {
        return flieList;
    }

    public void setFlieList(List<HTUserFileRelationVO> flieList) {
        this.flieList = flieList;
    }

    public List<HTUserJobVO> getJobList() {
        return jobList;
    }

    public void setJobList(List<HTUserJobVO> jobList) {
        this.jobList = jobList;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
