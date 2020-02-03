package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTTenderTaskBean;
import com.hd.manager.dao.bean.HTWorkBean;
import com.hd.manager.vo.HTTenderTaskVO;
import com.hd.manager.vo.HTWorkVO;

import java.util.List;
import java.util.Map;

public interface HTWorkDao extends BaseDao  {

    //合同经手列表
    List<HTWorkBean> excSearchContractHandleList(HTWorkBean paramBean);
    //合同经手列表总数
    Integer excSearchContractHandleCount(HTWorkBean paramBean);
    //合同已办列表
    List<HTWorkBean> excSearchContractCompletedList(HTWorkBean paramBean);
    //合同已办列表总数
    Integer excSearchContractCompletedCount(HTWorkBean paramBean);
    //合同待办列表
    List<HTWorkBean> excSearchContractListInfo (HTWorkBean paramBean);
    //合同待办列表总数
    Integer excSearchContractListCount(HTWorkBean paramBean);
    //普通执行人和主负责人待办列表
    List<HTWorkBean> excSearchTenderTaskPendingList (HTWorkBean paramBean);
    //普通执行人和主负责人待办列表总数
    Integer excSearchTenderTaskPendingCount(HTWorkBean paramBean);
    //普通执行人和主负责人待办列表总数
    Integer excSearchTenderTaskPendingAllCount();
    //普通执行人和主负责人已完成列表
    List<HTWorkBean> excSearchTenderTaskCompletedList (HTWorkBean paramBean);
    //普通执行人和主负责人已办列表总数
    Integer excSearchTenderTaskCompletedCount(HTWorkBean paramBean);
    //普通执行人和主负责人经手列表
    List<HTWorkBean> excSearchTenderTaskHandleList(HTWorkBean paramBean);
    //普通执行人和主负责人待办列表总数
    Integer excSearchTenderTaskHandleCount(HTWorkBean paramBean);

    //    List<HTWorkBean> excNewPendingList(Map<String,Object> pendingProp);
    //普通执行人和主负责人待办列表
    List<HTWorkBean> excSearchNewTenderTaskPendingList (Map<String,Object> pendingProp);
    //普通执行人和主负责人待办列表总数
    Integer excSearchNewTenderTaskPendingCount(Map<String,Object> pendingProp);
    //合同待办列表
    List<HTWorkBean> excSearchNewContractListInfo (Map<String,Object> pendingProp);
    //合同待办列表总数
    Integer excSearchNewContractListCount(Map<String,Object> pendingProp);
    //查询普通执行人任务待办总个数
    List<HTWorkBean> excSearchPendingCount (Map<String,Object> pendingProp);
    //查询普通执行人投标任务待办列表
    List<HTWorkBean> excSearchExecutorTenderTaskPendingList (HTWorkBean paramBean);
    //查询普通执行人投标任务待办个数
    Integer excSearchExecutorTenderTaskPendingCount (HTWorkBean paramBean);
    //查询普通执行人任务阶段待办列表
    List<HTWorkBean> excSearchExecutortaskArchivePendingList (HTWorkBean paramBean);
    //查询普通执行人任务阶段待办个数
    Integer excSearchExecutortaskArchivePendingCount (HTWorkBean paramBean);
    //查询普通执行人任务待办列表
    List<HTWorkBean> excSearchExecutorTaskPendingList (HTWorkBean paramBean);
    //查询普通执行人任务待办个数
    Integer excSearchExecutorTaskPendingCount (HTWorkBean paramBean);
    //查询普通执行人任务阶段已办列表
    List<HTWorkBean> excSearchExecutorTaskArchiveDoneList (HTWorkBean paramBean);
    //查询普通执行人任务阶段已办个数
    Integer excSearchExecutorTaskArchiveDoneCount (HTWorkBean paramBean);
    //查询普通执行人任务已办列表
    List<HTWorkBean> excSearchExecutorTaskDoneList (HTWorkBean paramBean);
    //查询普通执行人任务已办个数
    Integer excSearchExecutorTaskDoneCount (HTWorkBean paramBean);
    //(新)查询合同已办列表
    List<HTWorkBean> excSearchNewContractCompleteList (Map<String,Object> completeProp);
    //(新)查询合同已办列表总数
    Integer excSearchNewContractCompleteCount(Map<String,Object> completeProp);
    // 查询登陆人当周审批的信息
    List<HTWorkBean> excSearchWeekWorkList(HTWorkBean paramVO);
    // 查询登陆人选择时间段内审批任务的信息
    List<HTWorkBean> excSearchWeekTaskList(HTWorkBean paramVO);

}
