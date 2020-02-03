package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTUserVO;

import java.util.List;
import java.util.Map;


/**
 * 用户权限 service
 *
 * @author LH
 * Created in 2019/9/23  10:50
 */
public interface HTUserPowerService extends BaseService {



    /**
     * 查询用户所拥有权限
     *
     * @return 权限信息
     */
    public Map<String,Object> excSearchLoginUserPowerMap(List<String> jobIds)  throws LogicException, SystemException;


    /**
     * 查询用户当前合同下所含权限
     *
     * @return 权限信息
     */
    public Map<String,Object> excSearchUserWorkPowerMap(HTUserVO loginUserInfo,String businessId,String businessType)  throws LogicException, SystemException;
}
