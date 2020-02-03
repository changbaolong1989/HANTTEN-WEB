package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.*;
import com.hd.manager.vo.HTTenderTaskPeriodVO;
import com.hd.manager.vo.HTTenderTaskVO;

import java.util.List;

/**
 * 投标任务表 DAO
 *
 * @author LH
 * Created in 2019/7/12 16:00
 */
public interface HTTenderTaskDao extends BaseDao {

    /**
     * 批量添加投标任务和阶段关联信息
     *
     */
    void excInsertHTTenderTaskPeriods(List<HTTenderTaskPeriodBean> list);

    /**
     * 查询投标任务最大序号
     */
    Integer excGetMaxSortNum();

    /**
     * 批量新增投标任务成员
     */
    void excInsertTenderTaskUsers(List<HTTenderTaskUserBean> list);

    /**
     * 查询投标任务列表
     */
    List<HTTenderTaskBean> excSearchTenderTaskList(HTTenderTaskBean param);

    /**
     * 查询投标任务列表
     */
    Integer excSearchTenderTaskListCount(HTTenderTaskBean param);

    /**
     * 查询同名数据个数
     */
    Integer excSearchTenderTaskCountByName(HTTenderTaskBean param);

    /**
     * 删除投标任务和阶段关联
     */
    void excDeleteHTTenderTaskPeriodsByTenderTaskId(String tenderTaskId);

    /**
     * 删除投标任务和成员关联
     */
    void excDeleteHTTenderTaskUsersByTenderTaskId(String tenderTaskId);

    /**
     * 查询阶段列表
     */
    List<HTTenderTaskPeriodBean> excSearchTaskPeriodByTenderTaskId(String tenderTaskId);

    /**
     * 查询投标任务详情
     */
    HTTenderTaskBean excSearchTenderTaskInfoById(String tenderTaskId);

    /**
     * 查询投标任务关联成员
     */
    List<HTTenderTaskUserBean> excSearchTenderTaskUserListByTenderTaskId(String tenderTaskId);

    /**
     * 查询文件类别列表
     */
    List<HTDictFileTypeBean> excSearchMaterialTypeList(HTDictFileTypeBean paramBean);

    /**
     * 批量修改投标文件类型
     */
    void excUpdateTenderTaskPeriodMaterialInfoIsReviewType(List<String> tenderTaskPeriodMaterialIds);

    /**
     * 查询阶段关联文件信息
     */
    List<HTTenderTaskPeriodMaterialBean> excSearchTenderTaskPeriodMaterialListByProperty(HTTenderTaskPeriodMaterialBean paramBean);

    /**
     * 普通和执行人待办列表
     */
    List<HTTenderTaskBean> excSearchTenderTaskPendingList (HTTenderTaskBean paramVO);

    /**
     * 查询当前人业务线相关部门
     */
    List<HTDepartmentBean> excSearchAllDepartmentList(HTDepartmentBean paramBean);

    /*
    * 重置提审文件状态
    */
    void excCleanTenderTaskPeriodMaterialInfoIsReviewType(String tenderTaskId);

    /**
     * 查询审核阶段文件
     */
    HTTenderTaskPeriodMaterialBean excSearchTenderStageMaterialByTenderTaskId(HTTenderTaskPeriodMaterialBean paramBean);
}
