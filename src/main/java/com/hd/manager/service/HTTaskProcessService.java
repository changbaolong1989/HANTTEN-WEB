package com.hd.manager.service;

import com.hd.base.exception.SystemException;
import com.hd.base.vo.BaseVO;

/**
 * 任务流程Service
 */
public interface HTTaskProcessService extends HTWorkFlowService {

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
     * @throws SystemException 系统异常
     */
    void excReassignment(BaseVO baseVO) throws SystemException;

}
