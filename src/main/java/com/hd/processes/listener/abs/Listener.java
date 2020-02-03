package com.hd.processes.listener.abs;

import com.hd.base.exception.NoDelegationException;
import com.hd.base.vo.BaseVO;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.processes.listener.factory.VerifyObject;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;

import java.util.Date;
import java.util.Map;

/**
 * 工作流监听接口
 */
public interface Listener {

    /**
     * 根据部门设置委托人
     */
    void setDelegationUsersByDept(DelegateTask delegateTask, String departmentId, String processDictId) throws NoDelegationException;

    /**
     * 异常处理
     *
     * @param activitiListenVO 监听VO
     */
    void eHandle(CommonActivitiListenVO activitiListenVO);

    /**
     * 更新业务数据
     *
     * @param baseVO baseVO
     * @param clz    entity类
     * @param daoClz Dao类
     */
    <T> void updateBizData(BaseVO baseVO, Class<T> clz, Class<?> daoClz) throws Exception;

    /**
     * 设置部门ID
     *
     * @param variables        变量
     * @param baseVO           baseVo
     * @param activitiListenVO 监听vo
     * @throws Exception 异常
     */
    void setDepartmentId(Map<String, Object> variables, BaseVO baseVO, CommonActivitiListenVO activitiListenVO) throws Exception;

    /**
     * 一级审核_执行
     */
    CommonActivitiListenVO managerExeListener(VerifyObject vo) throws Exception;

    /**
     * 一级审核_监听
     */
    CommonActivitiListenVO managerTaskListener(VerifyObject vo) throws Exception;

    /**
     * 二三级审核_监听
     */
    CommonActivitiListenVO qualityTaskListener(VerifyObject vo) throws Exception;

    /**
     * 二三级审核_执行
     */
    CommonActivitiListenVO qualityExeListener(VerifyObject vo) throws Exception;

    /**
     * 签发_执行
     */
    CommonActivitiListenVO signExeListener(VerifyObject vo) throws Exception;

    /**
     * 签发_监听
     */
    CommonActivitiListenVO signTaskListener(VerifyObject vo) throws Exception;

    /**
     * 保存审批历史记录
     */
    void saveApproveRecord(DelegateExecution delegateExecution, Date approveTime, Map<String, Object> variables);


}
