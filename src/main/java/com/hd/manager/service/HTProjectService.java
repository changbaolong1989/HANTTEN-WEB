package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.ElementTreeVO;
import com.hd.manager.vo.HTContractVO;
import com.hd.manager.vo.HTDictProjectCounselTypeVO;
import com.hd.manager.vo.HTProjectVO;
import java.util.List;
import java.util.Map;

/**
 * 项目管理 业务层 接口
 *
 * @author jwl
 * Created in 2019/7/9 15:31
 */
public interface HTProjectService extends BaseService {

    /**
     * 查询已收入汇总和合同金额汇总
     *
     * @return 已收入汇总和合同金额汇总
     * @throws SystemException 不可预估的异常
     */
    HTProjectVO excSearchIncomeReceivedSumAndContractAmountSum(HTProjectVO paramVO) throws SystemException;

    /**
     * 查询公司缩写下拉列表
     *
     * @return 公司缩写下拉列表
     * @throws SystemException 不可预估的异常
     */
    List<HTProjectVO> excSearchCondenseList() throws SystemException;

    /**
     * 查询部门人员下拉列表
     * @return 部门人员下拉列表
     */
    List<HTProjectVO> excSearchPersonList() throws SystemException;

    /**
     * 查询项目编号
     * @return 项目编号
     */
    HTProjectVO excSearchMaxProjectNum() throws SystemException;

    /**
     * 添加项目信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    String excSaveProjectInfo(HTProjectVO paramVO) throws LogicException, SystemException;

    /**
     * 查询项目列表数据
     *
     * @return 项目列表数据
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchProjectList(HTProjectVO paramVO) throws SystemException;

    /**
     * 通过该项目的id查询项目下的合同（树形）
     *
     * @throws SystemException 不可预估的异常
     */
    ElementTreeVO excSearchProjectTree(HTProjectVO paramVO) throws SystemException;

    /**
     * 查询项目详情
     *
     * @throws SystemException 不可预估的异常
     */
    HTProjectVO excSearchProjectInfo(HTProjectVO paramVO) throws SystemException;

    /**
     * 修改项目信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excUpdateProjectInfo(HTProjectVO paramVO) throws LogicException, SystemException;

    /**
     * 删除项目信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excDeleteProjectInfo(HTProjectVO paramVO) throws LogicException, SystemException;

    /**
     * 添加合同信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    String excSaveContractInfo(HTProjectVO paramVO) throws LogicException, SystemException;

    /**
     * 修改合同信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excUpdateContractInfo(HTProjectVO paramVO) throws LogicException, SystemException;

    /**
     * 删除合同信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excDeleteContractInfo(HTProjectVO paramVO) throws LogicException, SystemException;

    /**
     * 通过项目id和部门id查询合同数
     * @return 项目id和部门id查询合同数
     */
    HTProjectVO excSearchContractCountByProjectIdAndDepartmentId(HTProjectVO paramVO) throws SystemException;

    /**
     * 查询合同信息
     * @return 合同信息
     * @throws SystemException 不可预估的异常
     */
    HTProjectVO excSearchContractInfo(HTProjectVO paramVO) throws SystemException;

    /**
     *  关闭项目
     * @param paramVO 项目id
     * @throws SystemException 系统异常
     */
    void excCloseProject(HTProjectVO paramVO) throws SystemException;

    /**
     * 取得合同关联部门下的人员下拉列表
     * @return 部门人员下拉列表
     */
    List<HTProjectVO> excSearchPersonListByInvolvedDepartment(HTProjectVO paramVO) throws SystemException;

    /**
     * 分配合同编撰人
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excUpdateContractUserInfo(HTProjectVO paramVO) throws LogicException, SystemException;

    /**
     * 查询合同编撰人
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    HTContractVO queryContractWriter(String contractId) throws LogicException, SystemException;

    /**
     * 查询项目参与人员信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchParticipantsInfoList(HTProjectVO paramVO) throws SystemException;

    /**
     * 导出项目参与人员信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    List<HTProjectVO> excExportParticipantsInfoList(HTProjectVO paramVO) throws SystemException;

    /**
     * 查询成果文件台账列表
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchResultFileLedgerInfoList(HTProjectVO paramVO) throws SystemException;

    /**
     * 导出成果文件台账列表
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    List<HTProjectVO> excExportResultFileLedgerInfoList(HTProjectVO paramVO) throws SystemException;

    /**
     * 查询咨询类别列表
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    List<HTDictProjectCounselTypeVO> excSearchCounselTypePeriodList();

}
