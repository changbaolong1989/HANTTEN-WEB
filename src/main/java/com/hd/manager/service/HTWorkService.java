package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.dao.bean.HTDepartmentBean;
import com.hd.manager.vo.HTWorkVO;

import java.util.List;
import java.util.Map;

public interface HTWorkService extends BaseService {

    /**
     * 查询待办列表
     */
    Map<String, Object> excSearchPendingList(HTWorkVO paramVO) throws LogicException, SystemException;

    /**
     * 查询所有部门
     */
    Map<String, Object> excSearchAllDepartmentList(HTWorkVO paramVO) throws LogicException, SystemException;

    /**
     * 查询已办列表
     */
    Map<String, Object> excSearchCompletedList(HTWorkVO paramVO) throws LogicException, SystemException;

    /**
     * 查询经手列表
     */
    Map<String, Object> excSearchHandleList(HTWorkVO paramVO) throws LogicException, SystemException;

    /**
     * 查询待办列表（2019.10.23 新）
     */
    Map<String, Object> excSearchNewPendingList2(HTWorkVO paramVO) throws LogicException, SystemException;

    /**
     * 查询待办列表（2019.10.23 新）
     */
    Map<String, Object> excSearchNewPendingList(HTWorkVO paramVO) throws LogicException, SystemException;

    /**
     * 批量通过业务（2019.11.08 新）
     *
     * @param paramVO     当前登录人信息
     * @param paramVOList (批量通过数据)
     * @throws LogicException
     * @throws SystemException
     */
    void excBatchPassCheckBusinessList(HTWorkVO paramVO, List<HTWorkVO> paramVOList) throws LogicException, SystemException;

    /**
     * 查询已办列表(2019.1.28 新)
     */
    Map<String, Object> excSearchNewCompletedList(HTWorkVO paramVO) throws LogicException, SystemException;

    /**
     * 查询周报数据
     */
    List<HTWorkVO> excSearchWeekWorkList(HTWorkVO paramVO) throws LogicException, SystemException;

}
