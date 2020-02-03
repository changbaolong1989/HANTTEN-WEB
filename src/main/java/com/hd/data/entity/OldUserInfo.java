package com.hd.data.entity;

public class OldUserInfo {
    /**
     * 用户主键ID
     */
    private Integer uid;

    /**
     * 所属部门
     */
    private Integer deid;

    /**
     * 职位
     */
    private Integer pid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 
     */
    private String password;

    /**
     * 手机
     */
    private String phone;

    /**
     * 座机
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 注册时间
     */
    private Integer time;

    /**
     * 最后登陆时间
     */
    private Integer upTime;

    /**
     * 状态,默认1.正常；0.待审核；-1.删除或禁用
     */
    private Boolean status;

    /**
     * 用户主键ID
     * @return uid 用户主键ID
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 用户主键ID
     * @param uid 用户主键ID
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 所属部门
     * @return deid 所属部门
     */
    public Integer getDeid() {
        return deid;
    }

    /**
     * 所属部门
     * @param deid 所属部门
     */
    public void setDeid(Integer deid) {
        this.deid = deid;
    }

    /**
     * 职位
     * @return pid 职位
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 职位
     * @param pid 职位
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * @return password 
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 手机
     * @return phone 手机
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 手机
     * @param phone 手机
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 座机
     * @return telephone 座机
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 座机
     * @param telephone 座机
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
     * 注册时间
     * @return time 注册时间
     */
    public Integer getTime() {
        return time;
    }

    /**
     * 注册时间
     * @param time 注册时间
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * 最后登陆时间
     * @return up_time 最后登陆时间
     */
    public Integer getUpTime() {
        return upTime;
    }

    /**
     * 最后登陆时间
     * @param upTime 最后登陆时间
     */
    public void setUpTime(Integer upTime) {
        this.upTime = upTime;
    }

    /**
     * 状态,默认1.正常；0.待审核；-1.删除或禁用
     * @return status 状态,默认1.正常；0.待审核；-1.删除或禁用
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 状态,默认1.正常；0.待审核；-1.删除或禁用
     * @param status 状态,默认1.正常；0.待审核；-1.删除或禁用
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
}