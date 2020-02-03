package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.util.BusinessUtils;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.manager.dao.HTDepartmentDao;
import com.hd.manager.dao.bean.HTDepartmentBean;
import com.hd.manager.service.HTDepartmentService;
import com.hd.manager.vo.HTDepartmentVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class HTDepartmentServiceImpl extends BaseServiceImpl implements HTDepartmentService {


    @Resource
    private HTDepartmentDao htDepartmentDao;

    /**
    * 查询部门列表
    */
    @Override
    public Map<String,Object> searchDepartMentList(HTDepartmentVO paramVO) throws LogicException, SystemException {
        try {
            Map<String,Object> resultMap = new HashMap<>();
            HTDepartmentBean paramBean = new HTDepartmentBean();
            //查询参数部门名称
            paramBean.setDepartmentName(paramVO.getDepartmentName());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            List<HTDepartmentBean> departmentList = htDepartmentDao.findDepartmentList(paramBean);
            List<HTDepartmentVO> departmentVOList = new ArrayList<>();
            for (HTDepartmentBean htDepartmentBean:departmentList){
                HTDepartmentVO htDepartmentVO = new HTDepartmentVO();
                //部门ID
                htDepartmentVO.setDepartmentId(htDepartmentBean.getDepartmentId());
                //部门名称
                htDepartmentVO.setDepartmentName(htDepartmentBean.getDepartmentName());
                //父级部门名称
                htDepartmentVO.setParentDepartmentName(htDepartmentBean.getParentDepartmentName());
                //部门下职位
                htDepartmentVO.setDepartmentJob(htDepartmentBean.getDepartmentJob());
                departmentVOList.add(htDepartmentVO);
            }
            Integer count = htDepartmentDao.searchDepartmentListCount(paramBean);
            resultMap.put(Constant.DATA_LIST_KEY,departmentVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 修改部门信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDepartmentInfo(HTDepartmentVO paramVO) throws LogicException, SystemException  {
        try {
            HTDepartmentBean paramBean = new HTDepartmentBean();
            Date currentDate = new Date();
            paramBean.setDepartmentId(paramVO.getDepartmentId());
            paramBean.setDepartmentName(paramVO.getDepartmentName());
            paramBean.setUpdateDate(currentDate);
            //TODO 死值用户ID
            paramBean.setUpdateUserId("1");
            htDepartmentDao.updateDepartmentInfo(paramBean);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }
}
