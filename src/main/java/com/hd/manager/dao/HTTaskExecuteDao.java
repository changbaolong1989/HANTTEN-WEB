package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTTaskExecuteBean;
import com.hd.manager.dao.bean.HTTaskPeriodBean;

import java.util.List;


/**
 * 任务执行 持久层接口
 *
 * @author Dongzx
 * Created in 2019/8/13 10:48
 */
public interface HTTaskExecuteDao extends BaseDao {


    /**
     *  根据任务id 查询任务详细信息
     * @param paramBean 任务id
     * @return 任务详细信息
     */
    HTTaskExecuteBean getTaskExecuteDetailInfo(HTTaskExecuteBean paramBean);

    /**
     * 查询A4 收集资料文件列表
     * @param paramBean 任务id
     * @return 收集资料文件列表
     */
    List<HTTaskExecuteBean> searchA4MaterialInfo(HTTaskExecuteBean paramBean);

    /**
     *  查询A6 造价 咨询记录列表
     * @param paramBean 任务id
     * @return A6 造价 咨询记录列表
     */
    List<HTTaskExecuteBean> searchA6ConsultRecordInfoZJ(HTTaskExecuteBean paramBean);

    /**
     *  查询A6 招标 咨询记录列表
     * @param paramBean 任务id
     * @return A6 招标 咨询记录列表
     */
    List<HTTaskExecuteBean> searchA6ConsultRecordInfoZB(HTTaskExecuteBean paramBean);


    /**
     *  查询A7 造价 成果文件 单元工程造价指标 信息
     * @param paramBean 任务id
     * @return A7 造价 成果文件 单元工程造价指标 信息
     */
    HTTaskExecuteBean searchA7AchievementInfoZJ(HTTaskExecuteBean paramBean);


    /**
     *  查询A7 招标 成果文件 咨询记录 集合
     * @param paramBean 任务id
     * @return A7 招标 成果文件 咨询记录 集合
     */
    List<HTTaskExecuteBean> searchA7ConsultRecordInfoZB(HTTaskExecuteBean paramBean);

    /**
     *  查询A8 招标 踏勘现场 咨询记录列表
     * @param paramBean 任务阶段id
     * @return A8 招标 踏勘现场 咨询记录列表
     */
    List<HTTaskExecuteBean> searchA8ConsultRecordZB(HTTaskExecuteBean paramBean);

    /**
     *  查询A6列表
     * @param paramBean 任务阶段id
     * @return A6 列表
     */
    List<HTTaskExecuteBean> searchA6Record(HTTaskExecuteBean paramBean);


    /**
     *  查询A3 审核记录
     * @param paramBean 任务id
     * @return A3 审核记录
     */
    List<HTTaskPeriodBean> excSearchHTTaskHisTaskInstInfoList(HTTaskExecuteBean paramBean);

    /**
     * 通过relationId获取taskId
     *
     * @param paramBean relationId
     * @return taskId
     */
    HTTaskExecuteBean searchTaskIdByRelationId(HTTaskExecuteBean paramBean);

    /**
     * 通过taskId删除任务阶段和A7的关联表
     *
     * @param paramBean taskId
     */
    void deleteSevenRelationByTaskId(HTTaskExecuteBean paramBean);

    /**
     * 通过taskId删除任务阶段和A8的关联表
     *
     * @param paramBean taskId
     */
    void deleteEightRelationByTaskId(HTTaskExecuteBean paramBean);

    /**
     * 通过taskId删除招标代理部A7的扩展表
     *
     * @param paramBean taskId
     */
    void deleteSevenZbdlRelationByTaskId(HTTaskExecuteBean paramBean);

    /**
     * 通过taskId删除招标代理部A8的扩展表
     *
     * @param paramBean taskId
     */
    void deleteEightZbdlRelationByTaskId(HTTaskExecuteBean paramBean);

    /**
     * 通过taskId删除A5关联表
     *
     * @param paramBean taskId
     */
    void deleteFiveRelationByTaskId(HTTaskExecuteBean paramBean);

    /**
     * 通过taskId删除A6关联表
     *
     * @param paramBean taskId
     */
    void deleteSixRelationByTaskId(HTTaskExecuteBean paramBean);

    /**
     * 批量插入任务阶段和A7的关联表
     *
     * @param paramBeanList
     */
    void insertSevenRelationList(List<HTTaskExecuteBean> paramBeanList);

    /**
     * 批量插入招标代理部A7的扩展表
     *
     * @param paramBeanList
     */
    void insertSevenZbdlRelationList(List<HTTaskExecuteBean> paramBeanList);

    /**
     * 批量插入招标代理部A8的扩展表
     *
     * @param paramBeanList
     */
    void insertEightZbdlRelationList(List<HTTaskExecuteBean> paramBeanList);

    /**
     * 批量插入A6关联表
     *
     * @param paramBeanList
     */
    void insertSixRelationList(List<HTTaskExecuteBean> paramBeanList);

    /**
     * 批量插入A6关联表
     *
     * @param paramBeanList
     */
    void insertSixZbdlRelationList(List<HTTaskExecuteBean> paramBeanList);

    /**
     * 批量插入招标代理部A8的扩展表
     *
     * @param paramBean
     */
    void insertEightRelation(HTTaskExecuteBean paramBean);

    /**
     * 批量插入成果文件A7的扩展表
     *
     * @param paramBeanList
     */
    void insertSevenZJRelationList(List<HTTaskExecuteBean> paramBeanList);

    /**
     * 查询A7咨询记录集合
     *
     * @param paramBean 任务id
     * @return A7咨询记录集合
     */
    List<HTTaskExecuteBean> searchA7ZbdlInfo(HTTaskExecuteBean paramBean);

    /**
     * 插入成果文件A7表
     *
     * @param paramBean
     */
    void insertSevenRelationInfo(HTTaskExecuteBean paramBean);

    /**
     * 修改合同金额
     *
     * @param paramBean
     */
    void updateContractAmount(HTTaskExecuteBean paramBean);

    /**
     * 根据taskid 和 收集资料id 获取 该任务的阶段关联id
     *
     * @param paramBean
     */
    HTTaskExecuteBean getRelationByTaskId(HTTaskExecuteBean paramBean);
}
