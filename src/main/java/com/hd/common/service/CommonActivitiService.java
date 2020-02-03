package com.hd.common.service;

import com.alibaba.fastjson.JSONObject;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.base.vo.BaseVO;
import com.hd.common.entity.TbJobProcessNodeRelationEntity;
import com.hd.manager.dao.bean.HTWorkBean;
import com.hd.manager.vo.HTUserVO;
import com.hd.processes.bench.WorkFlow;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CommonActivitiService
 */
public interface CommonActivitiService extends BaseService {

    /**
     * 获取流程图
     */
    void getFlowDiagram(HttpServletResponse response, String processInstanceId);

    /**
     * 通过TaskId获取bussinessKey
     *
     * @param taskId 任务ID
     * @return bussinessKey
     */
    String getBizKeyByTaskId(String taskId);

    /**
     * 通过Task获取bussinessKey
     *
     * @param tasks 任务
     * @return bussinessKey
     */
    List<String> getBizKeyByTask(List<? extends Task> tasks);

    /**
     * 通过hisTasks获取bussinessKey
     *
     * @param hisTasks 历史任务
     * @return bussinessKey
     */
    List<String> getBizKeyByHisTasks(List<HistoricTaskInstance> hisTasks);

    /**
     * 通过processInstanceId获取bussinessKey
     *
     * @param processInstanceId 任务ID
     * @return bussinessKey
     */
    String getBizKeyByProInstId(String processInstanceId);


    /**
     * 通过历史InstanceId获取bussinessKey
     *
     * @param proInstId 流程实例
     * @return bussinessKey
     */
    String getBizKeyByHisProInstId(String proInstId);

    /**
     * 部署
     *
     * @param fileName   文件名
     * @param deployName 部署名
     */
    void excDeploy(String fileName, String deployName);

    /**
     * 办理任务，带监听器对象，根据状态回滚
     *
     * @param taskId    任务ID
     * @param variables 变量，监听器对象
     */
    void excCompleteTask(String taskId, Map<String, Object> variables) throws SystemException;

    void excCompleteTask(String userId, String taskId, Map<String, Object> variables) throws SystemException;

    /**
     * 查询待办
     *
     * @param userId 用户ID
     * @return 待办任务
     * @throws SystemException 系统异常
     */
    List<Task> excQueryTask(String userId) throws SystemException;

    /**
     * 查询待办
     *
     * @param userId   用户ID
     * @param nodeList 节点集合
     * @return 待办任务
     * @throws SystemException 系统异常
     */
    List<Task> excQueryTask(String userId, List<TbJobProcessNodeRelationEntity> nodeList) throws SystemException;

    /**
     * 查询待办
     *
     * @param userId        用户ID
     * @param nodeList      节点集合
     * @param processDefKey 流程定义Key
     * @return 待办任务
     * @throws SystemException 系统异常
     */
    List<Task> excQueryTask(String userId, String processDefKey, List<TbJobProcessNodeRelationEntity> nodeList) throws SystemException;

    /**
     * 查询待办
     *
     * @param userId       用户ID
     * @param departmentId 部门ID
     * @return 当前流程/部门下的待办
     * @throws SystemException 系统异常
     */
    Map<String, Set<String>> excQueryTask(String userId, String departmentId) throws SystemException;

    /**
     * 查询待办V2.0版
     */
    List<WorkFlow> excQueryTodo(String userId, String departmentId, List<HTWorkBean> exeWorks) throws SystemException;

    /**
     * 各部门下是否有待办
     */
    List<WorkFlow> hasTodoTask(String userId) throws SystemException;

    /**
     * 查询已办经手
     *
     * @param userId 用户ID
     * @return 当前流程/部门下的待办
     * @throws SystemException 系统异常
     */
    Map<String, Set<String>> excQueryTaskCompleted(String userId) throws SystemException;

    /**
     * 查询已办经手V2.0版
     */
    List<WorkFlow> excQueryTaskCompleted(String userId, List<HTWorkBean> exeWorks) throws SystemException;

    /**
     * 删除流程
     *
     * @param processInstanceId 流程实例ID
     * @throws SystemException 系统异常
     */
    void excDeleteProcess(String processInstanceId) throws SystemException;

    /**
     * 查询已完成的历史
     *
     * @param userId        用户ID
     * @param processDefKey 流程定义KEY
     * @return 历史
     * @throws SystemException 系统异常
     */
    List<HistoricTaskInstance> excQueryHisTask(String userId, String processDefKey) throws SystemException;

    /**
     * 查询已完成的历史
     *
     * @param userId 用户ID
     * @return 历史
     * @throws SystemException 系统异常
     */
    List<HistoricTaskInstance> excQueryHisTask(String userId) throws SystemException;

    /**
     * 查询页面显示类型
     */
    String excQueryPageType(HTUserVO user, String bizDeptId, String processDictId, BaseVO vo, List<String> executorUsers);

    /**
     * 查询流程信息
     */
    JSONObject excQueryProcessInfo() throws SystemException;

}
