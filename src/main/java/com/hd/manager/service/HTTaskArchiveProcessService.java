package com.hd.manager.service;

import com.hd.base.exception.SystemException;
import com.hd.base.vo.BaseVO;

/**
 * 项目监理部归档流程Service
 */
public interface HTTaskArchiveProcessService extends HTWorkFlowService {


    /**
     * 删除归档信息
     *
     * @param taskId 任务ID
     */
    void delArchive(String taskId);

    /**
     * 改派
     *
     * @throws SystemException 系统异常
     */
    void excReassignment(BaseVO baseVO) throws SystemException;

}
