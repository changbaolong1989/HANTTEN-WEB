package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTWholeProcessAssessVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 全过程项目估算阶段列表 业务层
 *
 * @author LH
 * Created in 2019/9/5 10:16
 */
public interface HTWholeProcessAssessService extends BaseService {


    /**
     * 查询项目估算阶段列表
     *
     * @return
     */
    Map<String,Object> excSearchProjectAssessPeriodList(HTWholeProcessAssessVO paramVO)  throws LogicException,SystemException ;

    /**
     * 查询项目估算阶段列表
     *
     * @return
     */
    List<HTWholeProcessAssessVO> excSearchProjectAssessPeriodListForExport(HTWholeProcessAssessVO paramVO)  throws LogicException,SystemException ;


    /**
     * 查询项目估算信息详情
     *
     * @return HTWholeProcessAssessVO
     */
    HTWholeProcessAssessVO excSearchProjectAssessPeriodInfo(HTWholeProcessAssessVO paramVO)  throws LogicException,SystemException;


    /**
     * 修改估算信息
     *
     * @return
     */
    void excUpdateWholeProcessAssess(HTWholeProcessAssessVO paramVO , MultipartFile file)  throws LogicException,SystemException;


    /**
     * 新增估算信息
     *
     * @return
     */
    void excInsertWholeProcessAssess(HTWholeProcessAssessVO paramVO , MultipartFile file)  throws LogicException,SystemException;
}
