package com.hd.manager.service;

import com.hd.base.exception.SystemException;

/**
 * 项目流程相关Service
 */
public interface HTProjectProcessService extends HTWorkFlowService {

    /**
     * 关闭项目
     *
     * @param projectId 项目ID
     * @throws SystemException 系统异常
     */
    void closeProject(String projectId) throws SystemException;


}
