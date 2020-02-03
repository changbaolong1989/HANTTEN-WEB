package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.common.entity.TbApplySealRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 请章申请 持久层接口
 */
public interface HTSealApplyDao extends BaseDao {

    /**
     * 保存更新请章记录
     */
    void saveOrUpdateRecord(TbApplySealRecordEntity applySealRecordEntity);

    /**
     * 查询指定办理人
     *
     * @param nodeId 节点ID
     * @return 结果集
     */
    List<Map<String, Object>> queryAssignUsers(@Param("nodeId") String nodeId, @Param("processNodeId") String processNodeId, @Param("departmentId") String departmentId);


    List<Map<String, Object>> querySealApplyRecord();
}
