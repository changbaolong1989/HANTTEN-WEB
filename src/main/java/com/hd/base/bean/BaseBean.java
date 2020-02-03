package com.hd.base.bean;

import java.util.Date;
/**
 * 基础Bean
 *
 * @author JLF
 *
 */
public class BaseBean {

    /**
     * 检索起始位置
     */
    private int startRowNum;

    /**
     * 检索结束位置
     */
    private int endRowNum;

    /**
     * 检索起始位置
     */
    private int pageNumber;

    /**
     * 检索结束位置
     */
    private int pageSize;

    /**
     * 总记录条数
     */
    private long total;

    /**
     * 逻辑删除标识位
     */
    private String isDelFlg;

    /**
     * 创建用户编号
     */
    private String createUserId;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 更新用户编号
     */
    private String updateUserId;

    /**
     * 更新日期
     */
    private Date updateDate;

    /**
     *  当前登陆用户id
     */
    private String currentUserId;

    /**
     * 项目是否关闭
     */
    private String projectIsClosed;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * projectIsClosed
     *
     * @return projectIsClosed 项目描述（略）
     */
    public String getProjectIsClosed() {
        return projectIsClosed;
    }

    /**
     * projectIsClosed
     *
     * @param projectIsClosed 项目描述（略）
     */
    public void setProjectIsClosed(String projectIsClosed) {
        this.projectIsClosed = projectIsClosed;
    }

    /**
     * startRowNum
     *
     * @return startRowNum 项目描述（略）
     */
    public int getStartRowNum() {
        return startRowNum;
    }

    /**
     * startRowNum
     *
     * @param startRowNum 项目描述（略）
     */
    public void setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
    }

    /**
     * endRowNum
     *
     * @return endRowNum 项目描述（略）
     */
    public int getEndRowNum() {
        return endRowNum;
    }

    /**
     * endRowNum
     *
     * @param endRowNum 项目描述（略）
     */
    public void setEndRowNum(int endRowNum) {
        this.endRowNum = endRowNum;
    }

    /**
     * total
     *
     * @return total 项目描述（略）
     */
    public long getTotal() {
        return total;
    }

    /**
     * total
     *
     * @param total 项目描述（略）
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * isDelFlg
     *
     * @return isDelFlg 项目描述（略）
     */
    public String getIsDelFlg() {
        return isDelFlg;
    }

    /**
     * isDelFlg
     *
     * @param isDelFlg 项目描述（略）
     */
    public void setIsDelFlg(String isDelFlg) {
        this.isDelFlg = isDelFlg;
    }

    /**
     * createUserId
     *
     * @return createUserId 项目描述（略）
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * createUserId
     *
     * @param createUserId 项目描述（略）
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * createDate
     *
     * @return createDate 项目描述（略）
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * createDate
     *
     * @param createDate 项目描述（略）
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * updateUserId
     *
     * @return updateUserId 项目描述（略）
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * updateUserId
     *
     * @param updateUserId 项目描述（略）
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * updateDate
     *
     * @return updateDate 项目描述（略）
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * updateDate
     *
     * @param updateDate 项目描述（略）
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * currentUserId
     *
     * @return currentUserId 项目描述（略）
     */
    public String getCurrentUserId() {
        return currentUserId;
    }

    /**
     * currentUserId
     *
     * @param currentUserId 项目描述（略）
     */
    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    /**
     * pageNumber
     *
     * @return pageNumber 项目描述（略）
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * pageNumber
     *
     * @param pageNumber 项目描述（略）
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * pageSize
     *
     * @return pageSize 项目描述（略）
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * pageSize
     *
     * @param pageSize 项目描述（略）
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * businessId
     *
     * @return businessId 项目描述（略）
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * businessId
     *
     * @param businessId 项目描述（略）
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
