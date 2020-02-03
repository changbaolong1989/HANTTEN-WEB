package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.util.BusinessUtils;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.MessageConstant;
import com.hd.manager.dao.HTTaskPeriodDao;
import com.hd.manager.dao.bean.HTTaskPeriodBean;
import com.hd.manager.service.HTTaskPeriodService;
import com.hd.manager.vo.HTTaskPeriodVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class HTTaskPeriodServiceImpl extends BaseServiceImpl implements HTTaskPeriodService {


    /**
     * 工作内容 DAO层
     */
    @Resource
    private HTTaskPeriodDao htTaskPeriodDao;

    /**
     * 查询工作内容列表
     */
    @Override
    public Map<String, Object> excSearchTaskPeriodList(HTTaskPeriodVO paramVO) throws LogicException, SystemException {

        try {
            Map<String,Object> resultMap = new HashMap<>();
            HTTaskPeriodBean paramBean = new HTTaskPeriodBean();
            paramBean.setDictDepartmentId(paramVO.getDictDepartmentId());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //查询工作内容列表
            List<HTTaskPeriodBean> taskPeriodList = htTaskPeriodDao.excSearchTaskPeriodList(paramBean);
            Integer count = htTaskPeriodDao.excSearchTaskPeriodListCount(paramBean);
            List<HTTaskPeriodVO> taskPeriodVOList = new ArrayList<>();
            //格式转换
            for (HTTaskPeriodBean htTaskPeriodBean:taskPeriodList){
                HTTaskPeriodVO htTaskPeriodVO = new HTTaskPeriodVO();
                //阶段ID
                htTaskPeriodVO.setPeriodId(htTaskPeriodBean.getPeriodId());
                //阶段名称
                htTaskPeriodVO.setPeriodName(htTaskPeriodBean.getPeriodName());
                //部门类别ID
                htTaskPeriodVO.setDictDepartmentId(htTaskPeriodBean.getDictDepartmentId());
                //部门类别名称
                htTaskPeriodVO.setDepartmentName(htTaskPeriodBean.getDepartmentName());
                //工作内容说明
                htTaskPeriodVO.setPeriodDesc(htTaskPeriodBean.getPeriodDesc());
                //修改时间
                htTaskPeriodVO.setUpdateDate(DateToolUtils.convertDateFormat(htTaskPeriodBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                taskPeriodVOList.add(htTaskPeriodVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY,taskPeriodVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }
    //修改工作内容信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excUpdateTaskPeriodInfo(HTTaskPeriodVO paramVO) throws LogicException, SystemException {
        try {
            HTTaskPeriodBean paramBean = new HTTaskPeriodBean();
            Date currentDate = new Date();
            //阶段ID
            paramBean.setPeriodId(paramVO.getPeriodId());
            //原数据
            HTTaskPeriodBean htTaskPeriodBean = htTaskPeriodDao.excSearchTaskPeriodInfo(paramBean);
            //原数据最后修改时间
            String checkDate = DateToolUtils.convertDateFormat(htTaskPeriodBean.getUpdateDate(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO);
            //检查数据是否以被修改
            if (!checkDate.equals(paramVO.getUpdateDate())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            //工作内容说明
            paramBean.setPeriodDesc(paramVO.getPeriodDesc());
            //修改日期
            paramBean.setUpdateDate(currentDate);
            //用户ID
            paramBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            htTaskPeriodDao.excUpdateTaskPeriodInfo(paramBean);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }


    //查询所有部门类别
    @Override
    public Map<String, Object> excSearchAllDictDepartmentList() throws LogicException, SystemException {
        try {
            Map<String,Object> resultMap = new HashMap<>();
            //所有部门类别集合
            List<HTTaskPeriodBean> allDictDepartmentList = htTaskPeriodDao.excSearchAllDictDepartmentList();
            resultMap.put("allDictDepartmentList",allDictDepartmentList);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }
}
