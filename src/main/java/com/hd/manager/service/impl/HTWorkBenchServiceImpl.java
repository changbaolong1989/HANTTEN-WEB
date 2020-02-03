package com.hd.manager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.service.CommonActivitiService;
import com.hd.manager.dao.bean.HTWorkBean;
import com.hd.manager.service.HTWorkBenchService;
import com.hd.processes.bench.WorkFlow;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class HTWorkBenchServiceImpl extends BaseServiceImpl implements HTWorkBenchService {

    @Resource
    private CommonActivitiService commonActivitiService;

    /**
     * 查询待办
     *
     * @param userId 用户ID
     * @return 待办任务
     * @throws SystemException 系统异常
     */
    @Override
    public List<?> excQueryTask(String userId) throws SystemException {
        return commonActivitiService.excQueryTask(userId);
    }

    /**
     * 查询待办
     *
     * @param userId       用户ID
     * @param departmentId 部门ID
     * @return 当前流程/部门下的待办
     * @throws SystemException 系统异常
     */
    @Override
    public Map<String, Set<String>> excQueryTask(String userId, String departmentId) throws SystemException {
        return commonActivitiService.excQueryTask(userId, departmentId);
    }

    @Override
    public List<WorkFlow> hasTodoTask(String userId) throws SystemException {
        return commonActivitiService.hasTodoTask(userId);
    }

    /**
     * 查询待办V2.0版
     */
    @Override
    public List<WorkFlow> excQueryTodo(String userId, String departmentId, List<HTWorkBean> exeWorks) throws SystemException {
        return commonActivitiService.excQueryTodo(userId, departmentId, exeWorks);
    }

    /**
     * 查询已办经手
     *
     * @param userId 用户ID
     * @return 当前流程/部门下的待办
     * @throws SystemException 系统异常
     */
    @Override
    public Map<String, Set<String>> excQueryTaskCompleted(String userId) throws SystemException {
        return commonActivitiService.excQueryTaskCompleted(userId);
    }

    @Override
    public List<WorkFlow> excQueryTaskCompleted(String userId, List<HTWorkBean> exeWorks) throws SystemException {
        return commonActivitiService.excQueryTaskCompleted(userId, exeWorks);
    }

    /**
     * 查询流程信息
     */
    @Override
    public JSONObject excQueryProcessInfo() throws SystemException {
        return commonActivitiService.excQueryProcessInfo();
    }

}
