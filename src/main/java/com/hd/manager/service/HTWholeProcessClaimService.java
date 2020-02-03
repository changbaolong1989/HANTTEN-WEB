package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.dao.bean.HTWholeProcessContractBean;
import com.hd.manager.vo.HTWholeProcessClaimVO;
import com.hd.manager.vo.HTWholeProcessContractVO;

import java.util.List;
import java.util.Map;

/**
 * 全过程项目索赔台账阶段列表 业务层
 *
 * @author LH
 * Created in 2019/9/11 10:16
 */
public interface HTWholeProcessClaimService extends BaseService {


    /**
     * 查询项目索赔台账阶段列表
     *
     * @return
     */
    Map<String,Object> excSearchProjectClaimPeriodList(HTWholeProcessClaimVO paramVO)  throws LogicException,SystemException ;


    /**
     * 查询项目索赔台账信息详情
     *
     * @return HTWholeProcessClaimVO
     */
    HTWholeProcessClaimVO excSearchProjectClaimPeriodInfo(HTWholeProcessClaimVO paramVO)  throws LogicException,SystemException;


    /**
     * 修改索赔台账信息
     *
     * @return
     */
    void excUpdateWholeProcessClaim(HTWholeProcessClaimVO paramVO)  throws LogicException,SystemException;


    /**
     * 新增索赔台账信息
     *
     * @return
     */
    void excInsertWholeProcessClaim(HTWholeProcessClaimVO paramVO)  throws LogicException,SystemException;


    /**
     * 查询合同列表信息
     *
     * @return
     */
    List<HTWholeProcessContractVO> excSearchContractList(HTWholeProcessContractVO paramVO)  throws LogicException,SystemException;


    /**
     *
     */
    List<HTWholeProcessClaimVO> excSearchProjectClaimPeriodListForExport(HTWholeProcessClaimVO paramVO) throws LogicException,SystemException;
}
