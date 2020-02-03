package com.hd.common.service;

import com.hd.base.service.BaseService;
import com.hd.common.entity.*;

import java.util.List;
import java.util.Map;

public interface CommonCacheService extends BaseService {

    /**
     * 章类型缓存
     *
     * @return 章类型缓存
     */
    List<TbDictSealEntity> getSealTypeCache();


    /**
     * 章类型缓存
     *
     * @return 章类型缓存
     */
    Map<String, TbDictSealEntity> getSealTypeCacheMap();

    /**
     * 流程节点缓存
     *
     * @return 流程节点缓存
     */
    Map<String, List<TbJobProcessNodeRelationEntity>> getProcessNodeCache();

    /**
     * 部门缓存
     *
     * @return 部门缓存
     */
    List<TbDictDepartmentEntity> getDepartmentCache();

    /**
     * 流程节点缓存
     *
     * @return 流程节点缓存
     */
    List<TbJobProcessNodeRelationEntity> getProcessNodeCache(String processDictId, String nodeKey, String departmentId);

    /**
     * 流程节点缓存
     *
     * @return 流程节点缓存
     */
    List<TbJobProcessNodeRelationEntity> getProcessNodeCache(String processDictId, String nodeKey, List<String> departmentIds);

    /**
     * 流程节点缓存
     *
     * @return 流程节点缓存
     */
    List<TbJobProcessNodeRelationEntity> getProcessNodeCache(String processDictId, String nodeKey);

    /**
     * 根据职位获取用户信息缓存
     *
     * @param jobId 职位ID
     * @return 用户信息缓存
     */
    List<TbUserJobRelation> getUserByJob(List<String> jobId);

    /**
     * 根据职位获取用户信息缓存
     *
     * @param jobId 职位ID
     * @return 用户信息缓存
     */
    List<TbUserJobRelation> getUserByJob(String jobId);

    /**
     * 查询投标执行人缓存信息
     *
     * @param tenderTaskId 投标ID
     * @return 执行人缓存
     */
    List<TbTenderTaskUser> getTenderUsers(String tenderTaskId);

    /**
     * 查询合同缓存信息
     */
    List<TbContractEntity> getContracts(String projectId);

    /**
     * 查询A3缓存信息
     */
    TbTaskEntity getTaskPeriod(String taskPeriodId);

    /**
     * 查询任务缓存
     */
    TbTaskEntity getTaskCache(String taskId);

    /**
     * 查询任务执行人缓存
     */
    List<TbTaskUserRelationEntity> getTaskUsers(String taskId);

    /**
     * 查询用户缓存
     */
    List<Map<String, Object>> getUserInfoCache(String userId);

    /**
     * 更新用户缓存信息
     */
    void updateUserInfoCache(String userId);

    /**
     * 清除用户职位缓存
     */
    void clearUserJobCache();

    /**
     * 清除投标执行人缓存信息
     */
    void clearTenderUserCache(String tenderTaskId);

    /**
     * 清除合同缓存
     */
    void clearContractCache(String projectId);

    /**
     * 清除A3缓存
     */
    void clearTaskPeriodCache(String taskId);

    /**
     * 清除任务缓存
     */
    void clearTaskCache(String taskId);

    /**
     * 清除任务执行人缓存
     */
    void clearTaskUserCache(String taskId);

}
