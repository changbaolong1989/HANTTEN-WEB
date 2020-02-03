package com.hd.manager.service;

import com.hd.base.exception.SystemException;

/**
 * 请款流程Service
 */
public interface HTCashProcessService extends HTWorkFlowService {

    /**
     * 撤回任务
     *
     * @param vo VO
     * @throws SystemException 系统异常
     */
    void excRecallTask(Object vo) throws SystemException;

}
