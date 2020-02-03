package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTWholeProcessLetterVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 全过程（发出信函登记表）业务层
 *
 * @author zjy
 * Created in 2019/9/9 11:27
 */
public interface HTWholeProcessLetterService extends BaseService {

    /**
     * 查询列表
     *
     * @return 发出信函登记表 列表信息
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchLetterPeriodList(HTWholeProcessLetterVO paramVO) throws SystemException;

    /**
     * 查询详情
     *
     * @return 发出信函登记表 详情
     * @throws SystemException 不可预估的异常
     */
    HTWholeProcessLetterVO excSearchProjectLetterPeriodInfo(HTWholeProcessLetterVO paramVO) throws SystemException;

    /**
     * 新增信息
     *
     * @param paramVO 新增信息
     * @throws SystemException 不可预估的异常
     */
    void excSaveWholeProcessLetter(HTWholeProcessLetterVO paramVO, MultipartFile file) throws LogicException, SystemException;

    /**
     * 修改信息
     *
     * @param paramVO 新增信息
     * @throws SystemException 不可预估的异常
     */
    void excUpdateWholeProcessLetter(HTWholeProcessLetterVO paramVO, MultipartFile file) throws LogicException, SystemException;

    /**
     * 查询项目估算阶段列表
     *
     * @return
     */
    List<HTWholeProcessLetterVO> excSearchProjectLetterPeriodListForExport(HTWholeProcessLetterVO paramVO) throws SystemException;
}
