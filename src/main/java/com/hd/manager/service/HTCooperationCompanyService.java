package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.dao.bean.HTCooperationCompanyBean;
import com.hd.manager.vo.HTCompanyVO;
import com.hd.manager.vo.HTCooperationCompanyVO;

import java.util.Map;

/**
 * 设置-合作公司管理 业务层 接口
 *
 * @author jwl
 * Created in 2019/7/3 15:31
 */
public interface HTCooperationCompanyService extends BaseService {

    /**
     * 查询合作公司列表数据
     *
     * @return 合作公司列表数据
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchCooperationCompanyList(HTCooperationCompanyVO paramVO) throws SystemException;

    /**
     * 添加合作公司信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excSaveCooperationCompanyInfo(HTCooperationCompanyVO paramVO) throws LogicException, SystemException;

    /**
     * 查询合作公司详细信息
     *
     * @throws SystemException 不可预估的异常
     */
    HTCooperationCompanyVO excSearchCooperationCompanyInfo(HTCooperationCompanyVO paramVO) throws SystemException;

    /**
     * 更改合作公司信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excUpdateCooperationCompanyInfo(HTCooperationCompanyVO paramVO) throws LogicException, SystemException;
}
