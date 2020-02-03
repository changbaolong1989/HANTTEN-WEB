package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTWholeProcessAssessVO;
import com.hd.manager.vo.HTWholeProcessTreatyVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 全过程项目合约规划阶段列表 业务层
 *
 * @author LH
 * Created in 2019/9/5 10:16
 */
public interface HTWholeProcessTreatyService extends BaseService {


    /**
     * 查询项目合约规划阶段列表
     *
     * @return
     */
    Map<String,Object> excSearchProjectTreatyPeriodList(HTWholeProcessTreatyVO paramVO)  throws LogicException,SystemException ;


    /**
     * 查询项目合约规划信息详情
     *
     * @return HTWholeProcessTreatyVO
     */
    HTWholeProcessTreatyVO excSearchProjectTreatyPeriodInfo(HTWholeProcessTreatyVO paramVO)  throws LogicException,SystemException;


    /**
     * 修改合约规划信息
     *
     * @return
     */
    void excUpdateWholeProcessTreaty(HTWholeProcessTreatyVO paramVO)  throws LogicException,SystemException;


    /**
     * 新增合约规划信息
     *
     * @return
     */
    void excInsertWholeProcessTreaty(HTWholeProcessTreatyVO paramVO)  throws LogicException,SystemException;

    List<HTWholeProcessTreatyVO> excSearchProjectTreatyPeriodListForExport(HTWholeProcessTreatyVO paramVO);
}
