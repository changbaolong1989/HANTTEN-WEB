package com.hd.manager.service;

import com.alibaba.fastjson.JSONObject;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.dao.bean.HTWorkBean;
import com.hd.processes.bench.WorkFlow;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 工作台Servicce
 * 查待办,已办，经手通用Service
 */
public interface HTWorkBenchService extends BaseService {

    /**
     * 查询待办
     *
     * @param userId 用户ID
     * @return 待办任务
     * @throws SystemException 系统异常
     */
    List<?> excQueryTask(String userId) throws SystemException;

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
     * 查询已办经手
     */
    List<WorkFlow> excQueryTaskCompleted(String userId, List<HTWorkBean> exeWorks) throws SystemException;

    /**
     * 查询流程信息
     */
    JSONObject excQueryProcessInfo() throws SystemException;

}
