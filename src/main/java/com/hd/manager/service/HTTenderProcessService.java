package com.hd.manager.service;

import com.hd.base.exception.SystemException;
import com.hd.manager.vo.HTTenderTaskVO;

/**
 * 投标流程Service
 */
public interface HTTenderProcessService extends HTWorkFlowService {

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
     * @param tenderTaskVO 合同VO
     * @throws SystemException 系统异常
     */
    void excReassignment(HTTenderTaskVO tenderTaskVO) throws SystemException;

}
