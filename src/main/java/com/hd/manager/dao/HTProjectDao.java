package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.common.entity.TbProjectEntity;
import com.hd.manager.dao.bean.HTDictProjectCounselTypeBean;
import com.hd.manager.dao.bean.HTProjectBean;
import java.util.List;

/**
 * 项目管理 持久层接口
 *
 * @author jwl
 * Created in 2019/7/9 10:40
 */
public interface HTProjectDao extends BaseDao {

    /**
     * 查询已收入汇总和合同金额汇总
     * @return 已收入汇总和合同金额汇总
     */
    HTProjectBean excSearchIncomeReceivedSumAndContractAmountSum(HTProjectBean paramBean);

    /**
     * 查询公司缩写下拉列表
     * @return 公司缩写下拉列表
     */
    List<HTProjectBean> excSearchCondenseList(TbProjectEntity tbProjectEntity);

    /**
     * 查询部门人员下拉列表
     * @return 部门人员下拉列表
     */
    List<HTProjectBean> excSearchPersonList(HTProjectBean paramBean);

    /**
     * 批量插入合同列表
     */
    void excInsertHTProjectVOList(List<HTProjectBean> paramBean);

    /**
     * 查询项目总条数
     * @return 项目总条数
     */
    Integer excSearchProjectListCount(HTProjectBean paramBean);

    /**
     * 查询项目列表数据
     * @return 项目列表数据
     */
    List<HTProjectBean> excSearchProjectList(HTProjectBean paramBean);

    /**
     * 通过当前登录人id查询所属部门id集合
     * @return 当前登录人id查询所属部门id集合
     */
    List<String> excSearchDepartmentIdByCurrentUserId(String currentUserId);

    /**
     * 查询最大的项目编号
     * @return 最大的项目编号
     */
    HTProjectBean excSearchMaxProjectNum();

    /**
     * 查询项目编号是否重复
     * @return 项目数
     */
    Integer excSearchProjectNumInfo(TbProjectEntity paramBean);

    /**
     * 查询项目名称是否重复
     * @return 项目数
     */
    Integer excSearchProjectNameInfo(TbProjectEntity paramBean);

    /**
     * 查询合同编号是否重复
     * @return 合同数
     */
    Integer excSearchContractNumInfo(HTProjectBean paramBean);

    /**
     * 查询指定项目下的合同信息
     * @return 指定项目下的合同信息
     */
    HTProjectBean excSearchProjectTree(HTProjectBean paramBean);

    /**
     * 查询指定项目下的合同信息
     * @return 指定项目下的合同信息
     */
    HTProjectBean excSearchProjectTreeV2(HTProjectBean paramBean);

    /**
     * 查询当前登录人是否所属财务部门
     * @return 部门id
     */
    List<String> excSearchDepartmentIdByUserId(HTProjectBean paramBean);

    /**
     * 查询每个合同下的任务
     * @return 任务
     */
    List<HTProjectBean> excSearchTaskByContractId(HTProjectBean paramBean);

    /**
     * 查询每个合同下的任务的阶段
     * @return 任务的阶段
     */
    List<HTProjectBean> excSearchTaskPeriodByContractId(HTProjectBean paramBean);

    /**
     * 查询每个任务下的任务阶段
     * @return 任务阶段
     */
    List<HTProjectBean> excSearchTaskListByTaskId(HTProjectBean paramBean);

    /**
     * 查询每个任务下的归档任务
     * @return 归档任务
     */
    List<HTProjectBean> excSearchArchiveTaskListByTaskId(HTProjectBean paramBean);

    /**
     * 查询被分配的合同数
     * @return 合同数
     */
    Integer excSearchContractCount(HTProjectBean paramBean);

    /**
     * 查询该合同下的任务数
     * @return 该合同下的任务数
     */
    Integer excSearchTaskCountByContractId(HTProjectBean paramBean);

    /**
     * 查询该合同下的请款单数
     * @return 该合同下的请款单数
     */
    Integer excSearchRequestMenuCountByContractId(HTProjectBean paramBean);

    /**
     * 删除合同信息
     * @return 合同数
     */
    Integer excDeleteContractInfo(HTProjectBean paramBean);

    /**
     * 通过项目id和部门id查询合同数
     * @return 项目id和部门id查询合同数
     */
    Integer excSearchContractCountByProjectIdAndDepartmentId(HTProjectBean paramBean);

    /**
     * 通过项目id和部门id查询合同数
     * @return 合同信息
     */
    HTProjectBean excSearchContractInfo(HTProjectBean paramBean);

    /**
     * 通过部门查询部门人员下拉列表
     * @return 查询部门人员下拉列表
     */
    List<HTProjectBean> excSearchPersonListByDepartmentId(HTProjectBean paramBean);

    /**
     * 查询项目参与人员信息
     */
    List<HTProjectBean> excSearchParticipantsInfoList(HTProjectBean paramBean);

    /**
     * 导出项目参与人员信息
     * @return 项目参与人员信息
     */
    List<HTProjectBean> excExportParticipantsInfoList(HTProjectBean paramBean);

    /**
     * 查询项目参与人员信息总条数
     * @return 查询项目参与人员信息总条数
     */
    Integer excSearchParticipantsInfoCount(HTProjectBean paramBean);

    /**
     * 查询成果文件台账列表
     * @return 查询成果文件台账列表
     */
    List<HTProjectBean> excSearchResultFileLedgerInfoList(HTProjectBean paramBean);

    /**
     * 查询成果文件台账总条数
     * @return 成果文件台账总条数
     */
    Integer excSearchResultFileLedgerInfoListCount(HTProjectBean paramBean);

    /**
     * 通过任务id查询负责人名称
     */
    List<HTProjectBean> excSearchUserNameByTaskId(HTProjectBean paramBean);

    /**
     * 导出成果文件台账列表
     * @return 导出成果文件台账列表
     */
    List<HTProjectBean> excExportResultFileLedgerInfoList(HTProjectBean paramBean);

    /**
     * 查询咨询类别列表
     * @return
     */
    List<HTDictProjectCounselTypeBean> excSearchCounselTypePeriodList();

    /**
     * 查询项目总条数(供全过程使用)
     * @return 项目总条数
     */
    Integer excSearchProjectWholeListCount(HTProjectBean paramBean);

    /**
     * 查询项目列表数据(供全过程使用)
     * @return 项目列表数据
     */
    List<HTProjectBean> excSearchProjectWholeList(HTProjectBean paramBean);
}
