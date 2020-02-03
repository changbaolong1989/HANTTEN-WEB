package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTWholeProcessAssessVO;
import com.hd.manager.vo.HTWholeProcessEstimateVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 全过程项目概算阶段列表 业务层
 *
 * @author LH
 * Created in 2019/9/5 10:16
 */
public interface HTWholeProcessEstimateService extends BaseService {


    /**
     * 查询项目概算阶段列表
     *
     * @return
     */
    Map<String,Object> excSearchProjectEstimatePeriodList(HTWholeProcessEstimateVO paramVO)  throws LogicException,SystemException ;


    /**
     * 查询项目概算信息详情
     *
     * @return HTWholeProcessAssessVO
     */
    HTWholeProcessEstimateVO excSearchProjectEstimatePeriodInfo(HTWholeProcessEstimateVO paramVO)  throws LogicException,SystemException;


    /**
     * 修改概算信息
     *
     * @return
     */
    void excUpdateWholeProcessEstimate(HTWholeProcessEstimateVO paramVO, MultipartFile file)  throws LogicException,SystemException;


    /**
     * 新增概算信息
     *
     * @return
     */
    void excInsertWholeProcessEstimate(HTWholeProcessEstimateVO paramVO, MultipartFile file)  throws LogicException,SystemException;

    List<HTWholeProcessEstimateVO> excSearchProjectEstimatePeriodListForExport(HTWholeProcessEstimateVO paramVO) throws SystemException;
}
