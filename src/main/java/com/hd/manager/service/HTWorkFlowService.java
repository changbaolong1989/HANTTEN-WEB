package com.hd.manager.service;

import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.base.vo.BaseVO;
import com.hd.manager.vo.HTUserVO;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;
import java.util.Map;

/**
 * 工作流Service
 */
public interface HTWorkFlowService extends BaseService {

    /**
     * 启动工作流
     *
     * @param variables 流程变量
     * @return 流程实例对象
     * @throws SystemException 系统异常
     */
    ProcessInstance excStart(Map<String, Object> variables) throws SystemException;
    //ProcessInstance excStart(String userId, Map<String, Object> variables) throws SystemException;

    /**
     * 办理任务
     *
     * @param taskId    任务ID
     * @param variables 流程变量
     * @throws SystemException 系统异常
     */
    //void excHandleTask(String taskId, Map<String, Object> variables) throws SystemException;
    void excHandleTask(String userId, String taskId, Map<String, Object> variables) throws SystemException;

    /**
     * 办理任务
     *
     * @param variables 变量
     * @return 历史ID
     * @throws SystemException 系统异常
     */
    //String excHandleTask(Map<String, Object> variables) throws SystemException;
    String excHandleTask(String userId, Map<String, Object> variables) throws SystemException;

    /**
     * 查询页面类型
     *
     * @param userVO        用户VO
     * @param processDictId 流程字典ID
     * @param vo            业务VO
     * @param executorUsers 执行人
     * @return 类型
     */
    String excQueryPageType(HTUserVO userVO, String bizDeptId, String processDictId, BaseVO vo, List<String> executorUsers);

}
