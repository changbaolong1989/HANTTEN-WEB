package com.hd.common.util;

import com.hd.common.dao.*;
import com.hd.common.entity.*;
import com.hd.manager.dao.HTUserDao;
import com.hd.manager.dao.HTUserJobRelationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据缓存通用类
 */
@Lazy(false)
@Component
public class CacheUtils implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    //流程节点配置缓存
    private Map<String, List<TbJobProcessNodeRelationEntity>> processNodeCache = null;

    //用户缓存
    private Map<String, List<Map<String, Object>>> userCache = new HashMap<>();

    @Resource
    private CacheManager cacheManager;

    @Resource
    private TbDictSealEntityMapper tbDictSealEntityMapper;

    @Resource
    private TbJobProcessNodeRelationEntityMapper processNodeMapper;

    @Resource
    private TbDictDepartmentEntityMapper departmentMapper;

    @Resource
    private HTUserJobRelationDao userJobRelationDao;

    @Resource
    private TbTenderTaskUserMapper tenderTaskUserDao;

    @Resource
    private TbTaskUserRelationEntityMapper taskUserDao;

    @Resource
    private TbContractEntityMapper contractDao;

    @Resource
    private TbTaskPeriodRelationEntityMapper taskPeriodDao;

    @Resource
    private TbTaskEntityMapper taskDao;

    @Resource
    private HTUserDao userDao;

    /**
     * 获取章类型缓存
     *
     * @return 章类型缓存
     */
    @Cacheable(value = "allSealTypeCache")
    public List<TbDictSealEntity> getSealTypeCache() {
        return tbDictSealEntityMapper.selectByExample(null);
    }

    /**
     * 获取章类型缓存
     *
     * @return 章类型缓存
     */
    @Cacheable(value = "allSealTypeCacheMap")
    public Map<String, TbDictSealEntity> getSealTypeCacheMap() {
        return getSealTypeCache().stream().collect(Collectors.toMap(TbDictSealEntity::getSealTypeId, a -> a));
    }

    /**
     * 获取流程节点缓存
     *
     * @return 流程节点缓存
     */
    public Map<String, List<TbJobProcessNodeRelationEntity>> getProcessNodeCache() {
        if (processNodeCache == null) {
            List<TbJobProcessNodeRelationEntity> processNodeList = processNodeMapper.selectByExample(null);
            processNodeCache = processNodeList.stream().collect(Collectors.groupingBy(TbJobProcessNodeRelationEntity::getProcessDictId));
        }
        return processNodeCache;
    }

    /**
     * 根据部门ID，节点Key获取节点职位配置信息
     *
     * @param processDictId 流程字典ID
     * @param nodeKey       节点Key
     * @param departmentId  部门ID
     * @return 节点职位配置信息
     */
    @Cacheable(value = "processNodeCacheByDeptId")
    public List<TbJobProcessNodeRelationEntity> getProcessNodeCache(String processDictId, String nodeKey, String departmentId) {
        TbJobProcessNodeRelationEntityExample example = new TbJobProcessNodeRelationEntityExample();
        TbJobProcessNodeRelationEntityExample.Criteria criteria = example.createCriteria();
        criteria.andProcessDictIdEqualTo(processDictId);
        criteria.andNodeIdEqualTo(nodeKey);
        criteria.andDepartmentIdEqualTo(departmentId);
        return processNodeMapper.selectByExample(example);
    }

    /**
     * 根据部门ID，节点Key获取节点职位配置信息
     *
     * @param processDictId 流程字典ID
     * @param nodeKey       节点Key
     * @param departmentIds 部门ID
     * @return 节点职位配置信息
     */
    @Cacheable(value = "processNodeCacheByDeptIds")
    public List<TbJobProcessNodeRelationEntity> getProcessNodeCache(String processDictId, String nodeKey, List<String> departmentIds) {
        TbJobProcessNodeRelationEntityExample example = new TbJobProcessNodeRelationEntityExample();
        TbJobProcessNodeRelationEntityExample.Criteria criteria = example.createCriteria();
        criteria.andProcessDictIdEqualTo(processDictId);
        criteria.andNodeIdEqualTo(nodeKey);
        criteria.andDepartmentIdIn(departmentIds);
        return processNodeMapper.selectByExample(example);
    }

    /**
     * 根据节点Key获取节点职位配置信息
     *
     * @param processDictId 流程字典ID
     * @param nodeKey       节点Key
     * @return 节点职位配置信息
     */
    @Cacheable(value = "processNodeCache")
    public List<TbJobProcessNodeRelationEntity> getProcessNodeCache(String processDictId, String nodeKey) {
        TbJobProcessNodeRelationEntityExample example = new TbJobProcessNodeRelationEntityExample();
        TbJobProcessNodeRelationEntityExample.Criteria criteria = example.createCriteria();
        criteria.andProcessDictIdEqualTo(processDictId);
        criteria.andNodeIdEqualTo(nodeKey);
        return processNodeMapper.selectByExample(example);
    }

    /**
     * 获取部门缓存
     *
     * @return 部门缓存
     */
    @Cacheable(value = "allDepartmentCache")
    public List<TbDictDepartmentEntity> getDepartmentCache() {
        return departmentMapper.selectByExample(null);
    }

    /**
     * 根据职位获取职位用户关系缓存
     *
     * @param jobId 职位ID
     * @return 职位用户关系
     */
    @Cacheable(value = "userInfoByJob")
    public List<TbUserJobRelation> getUserByJob(List<String> jobId) {
        TbUserJobRelationExample jobRelationExample = new TbUserJobRelationExample();
        jobRelationExample.createCriteria().andJobIdIn(jobId);
        return userJobRelationDao.selectIncumbentUser(jobRelationExample);
    }

    /**
     * 根据职位获取职位用户关系缓存
     *
     * @param jobId 职位ID
     * @return 职位用户关系
     */
    @Cacheable(value = "userInfoByJobId")
    public List<TbUserJobRelation> getUserByJob(String jobId) {
        TbUserJobRelationExample jobRelationExample = new TbUserJobRelationExample();
        jobRelationExample.createCriteria().andJobIdEqualTo(jobId);
        return userJobRelationDao.selectIncumbentUser(jobRelationExample);
    }

    /**
     * 查询投标执行人缓存信息
     *
     * @param tenderTaskId 投标ID
     * @return 执行人缓存
     */
    @Cacheable(value = "tenderTaskUsers", key = "#tenderTaskId")
    public List<TbTenderTaskUser> getTenderUsers(String tenderTaskId) {
        TbTenderTaskUserExample example = new TbTenderTaskUserExample();
        TbTenderTaskUserExample.Criteria criteria = example.createCriteria();
        criteria.andTenderTaskIdEqualTo(tenderTaskId);
        criteria.andIsMainDutyEqualTo("0");
        return tenderTaskUserDao.selectByExample(example);
    }

    /**
     * 查询任务执行人缓存信息
     *
     * @param taskId ID
     * @return 执行人缓存
     */
    @Cacheable(value = "taskUsers", key = "#taskId")
    public List<TbTaskUserRelationEntity> getTaskUsers(String taskId) {
        TbTaskUserRelationEntityExample example = new TbTaskUserRelationEntityExample();
        TbTaskUserRelationEntityExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(taskId);
        criteria.andIsMainDutyEqualTo("0");
        return taskUserDao.selectByExample(example);
    }

    /**
     * 查询合同缓存信息
     */
    @Cacheable(value = "contractsByPorjectId", key = "#projectId")
    public List<TbContractEntity> getContracts(String projectId) {
        TbContractEntityExample example = new TbContractEntityExample();
        TbContractEntityExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        return contractDao.selectByExample(example);
    }

    /**
     * 查询A3缓存信息
     */
    @Cacheable(value = "taskBytaskPeriodId", key = "#taskPeriodId")
    public TbTaskEntity getTaskPeriod(String taskPeriodId) {
        TbTaskPeriodRelationEntity taskPeriod = taskPeriodDao.selectByPrimaryKey(taskPeriodId);
        if (taskPeriod == null) {
            return null;
        }
        return taskDao.selectByPrimaryKey(taskPeriod.getTaskId());
    }

    /**
     * 查询任务缓存信息
     */
    @Cacheable(value = "tasks", key = "#taskId")
    public TbTaskEntity getTaskCache(String taskId) {
        return taskDao.selectByPrimaryKey(taskId);
    }

    /**
     * 查询用户信息缓存
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    public List<Map<String, Object>> getUserInfoCache(String userId) {
        return userCache.get(userId);
    }

    /**
     * 更新用户缓存信息
     */
    public void updateUserInfoCache(String userId) {
        userCache.remove(userId);
        List<Map<String, Object>> list = userDao.excQueryUserCache(userId);
        if (list != null && !list.isEmpty()) {
            userCache.put(userId, list);
        }
    }

    /**
     * 清除用户职位缓存
     */
    @Caching(evict = {
            @CacheEvict(value = "userInfoByJob", allEntries = true),
            @CacheEvict(value = "userInfoByJobId", allEntries = true)
    })
    public void clearUserJobCache() {
        logger.info("-----------------------------------用户职位关系缓存已经成功清除-------------------------------");
    }

    /**
     * 清除投标执行人缓存信息
     */
    @CacheEvict(value = "tenderTaskUsers", key = "#tenderTaskId")
    public void clearTenderUserCache(String tenderTaskId) {
        logger.info("----------------------------市场部投标任务缓存：" + tenderTaskId + "已经成功清除-------------------------------");
    }

    /**
     * 清除任务执行人缓存信息
     */
    @CacheEvict(value = "taskUsers", key = "#taskId")
    public void clearTaskUserCache(String taskId) {
        logger.info("----------------------------任务执行人缓存：" + taskId + "已经成功清除-------------------------------");
    }

    /**
     * 清除合同缓存
     */
    @CacheEvict(value = "contractsByPorjectId", key = "#projectId")
    public void clearContractCache(String projectId) {
        logger.info("----------------------------项目ID：" + projectId + "下的所有合同缓存已经成功清除-------------------------------");
    }

    /**
     * 清除A3缓存
     */
    @SuppressWarnings("unchecked")
    public void clearTaskPeriodCache(String taskId) {
        Cache cache = cacheManager.getCache("taskBytaskPeriodId");
        Map<String, Object> map = (Map<String, Object>) cache.getNativeCache();
        for (String key : map.keySet()) {
            TbTaskEntity taskEntity = (TbTaskEntity) map.get(key);
            if (taskId.equals(taskEntity.getTaskId())) {
                map.remove(key);
            }
        }
        logger.info("----------------------------任务ID：" + taskId + "下的所有A3缓存已经成功清除-------------------------------");
    }

    /**
     * 清除任务缓存
     */
    @CacheEvict(value = "tasks", key = "#taskId")
    public void clearTaskCache(String taskId) {
        logger.info("----------------------------任务ID：" + taskId + "缓存已经成功清除-------------------------------");
    }

    @Override
    public void run(String... args) {
        if (userCache == null || userCache.isEmpty()) {
            List<Map<String, Object>> userList = userDao.excQueryUserCache(null);
            String userId = "";
            for (Map<String, Object> map : userList) {
                if (userId.equals(map.get("user_id"))) {
                    userCache.get(userId).add(map);
                } else {
                    List<Map<String, Object>> list = new ArrayList<>();
                    list.add(map);
                    userCache.put(map.get("user_id").toString(), list);
                }
                userId = map.get("user_id").toString();
            }
        }
    }
}
