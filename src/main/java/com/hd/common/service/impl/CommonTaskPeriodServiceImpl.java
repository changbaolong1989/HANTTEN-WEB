package com.hd.common.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.service.CommonTaskPeriodService;
import com.hd.common.util.MessageConstant;
import com.hd.manager.dao.HTTaskPeriodDao;
import com.hd.manager.dao.bean.HTTaskPeriodBean;
import com.hd.manager.vo.HTTaskPeriodVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class CommonTaskPeriodServiceImpl extends BaseServiceImpl implements CommonTaskPeriodService {



    /**
     * 工作内容 DAO层
     */
    @Resource
    private HTTaskPeriodDao htTaskPeriodDao;

    @Override
    public List<HTTaskPeriodVO> searchTaskPeriodListByCounselTypeId(String counselTypeId) throws LogicException,SystemException{
        try {
            //合同类型列表
            List<HTTaskPeriodBean> htTaskPeriodBeanList =
                    htTaskPeriodDao.excSearchTaskPeriodListByCounselTypeId(counselTypeId);
            List<HTTaskPeriodVO> htTaskPeriodVOList = new ArrayList<>();
            //格式转换
            for (HTTaskPeriodBean htTaskPeriodBean:htTaskPeriodBeanList){
                HTTaskPeriodVO htTaskPeriodVO = new HTTaskPeriodVO();
                //阶段ID
                htTaskPeriodVO.setPeriodId(htTaskPeriodBean.getPeriodId());
                //父级阶段ID
                htTaskPeriodVO.setParentPeriodId(htTaskPeriodBean.getParentPeriodId());
                //阶段名称
                htTaskPeriodVO.setPeriodName(htTaskPeriodBean.getPeriodName());
                //合同类型
                htTaskPeriodVO.setContractTypeId(htTaskPeriodBean.getContractTypeId());
                //咨询类型
                htTaskPeriodVO.setCounselTypeId(htTaskPeriodBean.getCounselTypeId());
                //部门ID
                htTaskPeriodVO.setDictDepartmentId(htTaskPeriodBean.getDictDepartmentId());
                //级别
                htTaskPeriodVO.setPeriodLevel(htTaskPeriodBean.getPeriodLevel());
                //工作内容说明
                htTaskPeriodVO.setPeriodDesc(htTaskPeriodBean.getPeriodDesc());
                //页面展示内容(A1,A2,A3...A12)
                htTaskPeriodVO.setPageContent(htTaskPeriodBean.getPageContent());
                htTaskPeriodVOList.add(htTaskPeriodVO);
            }
            return htTaskPeriodVOList;
        } catch (LogicException ex) {
            throw ex;
        } catch (SystemException ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }
}
