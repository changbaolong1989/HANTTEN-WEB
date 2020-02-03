package com.hd.manager.service;

import com.hd.base.exception.SystemException;
import com.hd.common.entity.TbApplySealRecordEntity;
import com.hd.common.entity.TbApplySealRecordEntityExample;
import com.hd.manager.vo.HTSealApplyVO;
import com.hd.manager.vo.HTUserVO;
import org.activiti.engine.history.HistoricTaskInstance;

import java.util.List;
import java.util.Map;

/**
 * 请章相关Service
 */
public interface HTSealApplyService extends HTWorkFlowService {

    /**
     * 查询我的请章记录
     */
    Map<String, Object> querySealApplyRecords(HTSealApplyVO sealApplyVO, String userId, TbApplySealRecordEntityExample example) throws SystemException;

    /**
     * 查询所有已通过的请章
     */
    Map<String, Object> queryAlreadyPassRecords(HTSealApplyVO sealApplyVO, TbApplySealRecordEntityExample example) throws SystemException;

    /**
     * 根据业务键查询业务数据
     *
     * @param bussinessKeys 业务键
     * @return 请章记录数据
     */
    List<TbApplySealRecordEntity> queryBizData(List<String> bussinessKeys);

    /**
     * 标记盖章
     *
     * @throws SystemException 系统异常
     */
    void signSeal(HTSealApplyVO sealApplyVO) throws SystemException;

    /**
     * 查询指定办理人
     *
     * @param nodeId 节点ID
     * @return 结果集
     */
    List<Map<String, Object>> queryAssignUsers(String nodeId, String processDictId, String departmentId);

    /**
     * 请章模块查询权限
     *
     * @param userVO 当前用户
     * @return 权限
     */
    Map<String, Object> queryAuthority(HTUserVO userVO);


    /**
     * Bean转VO
     */
    List<HTSealApplyVO> beanToVo(List<TbApplySealRecordEntity> list, String isHistory) throws SystemException;

    /**
     * 查询已完成的历史任务
     *
     * @param userId        用户ID
     * @param processDefKey 流程定义Key
     * @return 历史任务实例
     * @throws SystemException 系统异常
     */
    List<HistoricTaskInstance> excQueryHisTask(String userId, String processDefKey) throws SystemException;

    boolean isOperated(HTSealApplyVO sealApplyVO);
}
