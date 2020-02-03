package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTWholeProcessTenderVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 全过程招标/清标概算阶段列表 业务层
 *
 * @author LH
 * Created in 2019/9/5 10:16
 */
public interface HTWholeProcessTenderService extends BaseService {


    /**
     * 查询招标阶段列表
     *
     * @return
     */
    Map<String,Object> excSearchProjectTenderPeriodList(HTWholeProcessTenderVO paramVO)  throws LogicException,SystemException ;

    /**
     * 查询清标阶段列表
     *
     * @return
     */
    Map<String,Object> excSearchProjectCleanTenderPeriodList(HTWholeProcessTenderVO paramVO)  throws LogicException,SystemException ;


    /**
     * 查询招标/清标信息详情
     *
     * @return HTWholeProcessAssessVO
     */
    HTWholeProcessTenderVO excSearchProjectTenderPeriodInfo(HTWholeProcessTenderVO paramVO)  throws LogicException,SystemException;


    /**
     * 修改招标信息
     *
     * @return
     */
    void excUpdateWholeProcessTender(HTWholeProcessTenderVO paramVO, MultipartFile file)  throws LogicException,SystemException;

    /**
     * 修改清标信息
     *
     * @return
     */
    void excUpdateWholeProcessCleanTender(HTWholeProcessTenderVO paramVO, MultipartFile file)  throws LogicException,SystemException;


    /**
     * 新增招标信息
     *
     * @return
     */
    void excInsertWholeProcessTender(HTWholeProcessTenderVO paramVO, MultipartFile file)  throws LogicException,SystemException;



    List<HTWholeProcessTenderVO> excSearchProjectTenderPeriodListForExport(HTWholeProcessTenderVO paramVO);



    List<HTWholeProcessTenderVO> excSearchProjectCleanTenderPeriodListForExport(HTWholeProcessTenderVO paramVO);
}
