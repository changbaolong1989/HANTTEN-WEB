package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTWholeProcessPayVO;

import java.util.List;
import java.util.Map;

/**
 * 全过程项目付款台账阶段列表 业务层
 *
 * @author LH
 * Created in 2019/9/11 10:16
 */
public interface HTWholeProcessPayService extends BaseService {


    /**
     * 查询项目付款台账阶段列表
     *
     * @return
     */
    Map<String,Object> excSearchProjectPayPeriodList(HTWholeProcessPayVO paramVO)  throws LogicException,SystemException ;


    /**
     * 查询项目付款台账信息详情
     *
     * @return HTWholeProcessPayVO
     */
    HTWholeProcessPayVO excSearchProjectPayPeriodInfo(HTWholeProcessPayVO paramVO)  throws LogicException,SystemException;


    /**
     * 修改付款台账信息
     *
     * @return
     */
    void excUpdateWholeProcessPay(HTWholeProcessPayVO paramVO)  throws LogicException,SystemException;

    /**
     * 查询所含期数（导出）
     *
     * @return
     */
    List<String> excSearchProPayPeriodNumListForExport(HTWholeProcessPayVO paramVO)  throws LogicException,SystemException;


    /**
     * 新增付款台账信息
     *
     * @return
     */
    void excInsertWholeProcessPay(HTWholeProcessPayVO paramVO)  throws LogicException,SystemException;

    /**
     * 查询列表（导出）
     *
     * @return
     */
    List<HTWholeProcessPayVO> excSearchProjectPayPeriodListForExport(HTWholeProcessPayVO paramVO) throws SystemException;
}
