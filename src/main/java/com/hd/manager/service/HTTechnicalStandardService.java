package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTTechnicalStandardVO;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

/**
 * 设置-技术标准管理 业务层 接口
 *
 * @author jwl
 * Created in 2019/7/5 15:31
 */
public interface HTTechnicalStandardService extends BaseService {

    /**
     * 查询技术标准列表数据
     *
     * @return 技术标准列表数据
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchTechnicalStandardList(HTTechnicalStandardVO paramVO) throws SystemException;

    /**
     * 添加技术标准信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excSaveTechnicalStandardInfo(HTTechnicalStandardVO paramVO, MultipartFile signPathFile) throws LogicException, SystemException;

    /**
     * 查询技术标准详细信息
     *
     * @throws SystemException 不可预估的异常
     */
    HTTechnicalStandardVO excSearchTechnicalStandardInfo(HTTechnicalStandardVO paramVO) throws SystemException;

    /**
     * 更改技术标准信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excUpdateTechnicalStandardInfo(HTTechnicalStandardVO paramVO, MultipartFile signPathFile) throws LogicException, SystemException;

    /**
     * 删除技术标准信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excDeleteTechnicalStandardInfo(HTTechnicalStandardVO paramVO) throws SystemException;
}
