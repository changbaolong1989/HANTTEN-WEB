package com.hd.manager.service;

import com.hd.base.exception.SystemException;
import com.hd.manager.vo.HTContractVO;
import com.hd.manager.vo.HTProjectVO;

/**
 * 合同流程Service
 */
public interface HTContractProcessService extends HTWorkFlowService {

    /**
     * 撤回任务
     *
     * @param vo VO
     * @throws SystemException 系统异常
     */
    void excRecallTask(Object vo) throws SystemException;

    /**
     * 改派
     *
     * @param contractVO 合同VO
     * @throws SystemException 系统异常
     */
    void excReassignment(HTContractVO contractVO) throws SystemException;

    /**
     * 修改导入的数据的合同编撰人
     */
    void excReassignment(String contractId, String newUserId, String newUserName) throws SystemException;

    /**
     * 添加合同
     *
     * @param projectVO 项目VO
     */
    void addContract(HTProjectVO projectVO, HTContractVO contractVO);

    /**
     * 删除合同
     *
     * @param contractId 合同ID
     */
    void delContract(String contractId);

}
