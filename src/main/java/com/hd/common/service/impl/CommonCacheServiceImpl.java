package com.hd.common.service.impl;

import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.entity.*;
import com.hd.common.service.CommonCacheService;
import com.hd.common.util.CacheUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CommonCacheServiceImpl extends BaseServiceImpl implements CommonCacheService {

    @Resource
    private CacheUtils cacheUtils;

    /**
     * 章类型缓存
     *
     * @return 章类型缓存
     */
    @Override
    public List<TbDictSealEntity> getSealTypeCache() {
        return cacheUtils.getSealTypeCache();
    }

    /**
     * 章类型缓存
     *
     * @return 章类型缓存
     */
    @Override
    public Map<String, TbDictSealEntity> getSealTypeCacheMap() {
        return cacheUtils.getSealTypeCacheMap();
    }

    /**
     * 流程节点缓存
     *
     * @return 流程节点缓存
     */
    @Override
    public Map<String, List<TbJobProcessNodeRelationEntity>> getProcessNodeCache() {
        return cacheUtils.getProcessNodeCache();
    }

    /**
     * 部门缓存
     *
     * @return 部门缓存
     */
    @Override
    public List<TbDictDepartmentEntity> getDepartmentCache() {
        return cacheUtils.getDepartmentCache();
    }

    /**
     * 流程节点缓存
     *
     * @return 流程节点缓存
     */
    @Override
    public List<TbJobProcessNodeRelationEntity> getProcessNodeCache(String processDictId, String nodeKey, String departmentId) {
        return cacheUtils.getProcessNodeCache(processDictId, nodeKey, departmentId);
    }

    /**
     * 流程节点缓存
     *
     * @return 流程节点缓存
     */
    @Override
    public List<TbJobProcessNodeRelationEntity> getProcessNodeCache(String processDictId, String nodeKey, List<String> departmentIds) {
        return cacheUtils.getProcessNodeCache(processDictId, nodeKey, departmentIds);
    }

    /**
     * 流程节点缓存
     *
     * @return 流程节点缓存
     */
    @Override
    public List<TbJobProcessNodeRelationEntity> getProcessNodeCache(String processDictId, String nodeKey) {
        return cacheUtils.getProcessNodeCache(processDictId, nodeKey);
    }

    /**
     * 根据职位获取用户信息缓存
     *
     * @param jobId 职位ID
     * @return 用户信息缓存
     */
    @Override
    public List<TbUserJobRelation> getUserByJob(List<String> jobId) {
        return cacheUtils.getUserByJob(jobId);
    }

    /**
     * 根据职位获取用户信息缓存
     *
     * @param jobId 职位ID
     * @return 用户信息缓存
     */
    @Override
    public List<TbUserJobRelation> getUserByJob(String jobId) {
        return cacheUtils.getUserByJob(jobId);
    }

    /**
     * 查询投标执行人缓存信息
     *
     * @param tenderTaskId 投标ID
     * @return 执行人缓存
     */
    @Override
    public List<TbTenderTaskUser> getTenderUsers(String tenderTaskId) {
        return cacheUtils.getTenderUsers(tenderTaskId);
    }

    /**
     * 查询合同缓存信息
     *
     * @param projectId 项目ID
     */
    @Override
    public List<TbContractEntity> getContracts(String projectId) {
        return cacheUtils.getContracts(projectId);
    }

    /**
     * 查询A3缓存信息
     */
    @Override
    public TbTaskEntity getTaskPeriod(String taskPeriodId) {
        return cacheUtils.getTaskPeriod(taskPeriodId);
    }

    /**
     * 查询任务缓存
     */
    @Override
    public TbTaskEntity getTaskCache(String taskId) {
        return cacheUtils.getTaskCache(taskId);
    }

    /**
     * 查询任务执行人缓存
     */
    @Override
    public List<TbTaskUserRelationEntity> getTaskUsers(String taskId) {
        return cacheUtils.getTaskUsers(taskId);
    }

    /**
     * 查询用户缓存
     */
    @Override
    public List<Map<String, Object>> getUserInfoCache(String userId) {
        return cacheUtils.getUserInfoCache(userId);
    }

    /**
     * 更新用户缓存信息
     */
    @Override
    public void updateUserInfoCache(String userId) {
        cacheUtils.updateUserInfoCache(userId);
    }

    /**
     * 清除用户职位缓存
     */
    @Override
    public void clearUserJobCache() {
        cacheUtils.clearUserJobCache();
    }

    /**
     * 清除投标执行人缓存信息
     *
     * @param tenderTaskId 市场部投标任务ID
     */
    @Override
    public void clearTenderUserCache(String tenderTaskId) {
        cacheUtils.clearTenderUserCache(tenderTaskId);
    }

    /**
     * 清除合同缓存
     *
     * @param projectId 项目ID
     */
    @Override
    public void clearContractCache(String projectId) {
        cacheUtils.clearContractCache(projectId);
    }

    /**
     * 清除A3缓存
     */
    @Override
    public void clearTaskPeriodCache(String taskId) {
        cacheUtils.clearTaskPeriodCache(taskId);
    }

    /**
     * 清除任务缓存
     */
    @Override
    public void clearTaskCache(String taskId) {
        cacheUtils.clearTaskCache(taskId);
    }

    /**
     * 清除任务执行人缓存
     */
    @Override
    public void clearTaskUserCache(String taskId) {
        cacheUtils.clearTaskUserCache(taskId);
    }

}
