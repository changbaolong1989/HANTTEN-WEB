package com.hd.common.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.service.CommonDictContractTypeService;
import com.hd.common.util.MessageConstant;
import com.hd.manager.dao.HTDictContractTypeDao;
import com.hd.manager.dao.HTDictCounselTypeDao;
import com.hd.manager.dao.bean.HTDictContractTypeBean;
import com.hd.manager.vo.HTDictContractTypeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonDictContractTypeServiceImpl extends BaseServiceImpl implements CommonDictContractTypeService {

    /**
     * 合同类型DAO层
     */
    @Resource
    private HTDictContractTypeDao htDictContractTypeDao ;

    /**
     * 根据部门ID查询合同类型列表
     * @param departmentId 部门ID
     * @return 合同类型列表
     */
    @Override
    public List<HTDictContractTypeVO> findDictContractTypeListByDepartmienId(String departmentId) throws LogicException, SystemException {
        try {
            //合同类型列表
            List<HTDictContractTypeBean> htDictContractTypeBeanList = htDictContractTypeDao.findDictContractTypeListByDepartmentId(departmentId);
            List<HTDictContractTypeVO> htDictContractTypeVOList = new ArrayList<>();
            //格式转换
            for (HTDictContractTypeBean htDictContractTypeBean:htDictContractTypeBeanList){
                HTDictContractTypeVO htDictContractTypeVO = new HTDictContractTypeVO();
                //合同类型ID
                htDictContractTypeVO.setContractTypeId(htDictContractTypeBean.getContractTypeId());
                //合同类型名称
                htDictContractTypeVO.setContractTypeName(htDictContractTypeBean.getContractTypeName());
                //部门ID
                htDictContractTypeVO.setDepartmentId(htDictContractTypeBean.getDepartmentId());
                htDictContractTypeVOList.add(htDictContractTypeVO);
            }
            return htDictContractTypeVOList;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }
}
