package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTCompanyVO;
import java.util.List;
import java.util.Map;

/**
 * 设置-公司管理 业务层 接口
 *
 * @author jwl
 * Created in 2019/7/3 15:31
 */
public interface HTCompanyService extends BaseService {

    /**
     * 查询公司列表数据
     *
     * @return 公司列表数据
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchCompanyList(HTCompanyVO paramVO) throws SystemException;

    /**
     * 添加公司信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excSaveCompanyInfo(HTCompanyVO paramVO) throws LogicException, SystemException;

    /**
     * 查询公司详细信息
     *
     * @throws SystemException 不可预估的异常
     */
    HTCompanyVO excSearchCompanyInfo(HTCompanyVO paramVO) throws SystemException;

    /**
     * 更改公司信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excUpdateCompanyInfo(HTCompanyVO paramVO) throws LogicException, SystemException;
}
