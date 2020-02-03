package com.hd.common.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.service.CommonDictCounselTypeService;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.StringToolUtils;
import com.hd.manager.dao.HTDictCounselTypeDao;
import com.hd.manager.dao.bean.HTDictCounselTypeBean;
import com.hd.manager.vo.HTDictCounselTypeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class CommonDictCounselTypeServiceImpl extends BaseServiceImpl implements CommonDictCounselTypeService {


    /**
     * 咨询类别状态DAO
     */
    @Resource
    private HTDictCounselTypeDao htDictCounselTypeDao ;

    /**
     * 根据合同类型ID查询咨询类别状态列表
     * @param contractTypeId 合同类型ID
     * @return 咨询类别状态列表
     */
    @Override
    public List<HTDictCounselTypeVO> findDictCounselTypeListByContractTypeId(String contractTypeId) throws LogicException, SystemException  {
        try {
            //咨询类别状态列表
            List<HTDictCounselTypeBean> htDictCounselTypeBeanList = htDictCounselTypeDao.findDictCounselTypeListByContractTypeId(contractTypeId);
            List<HTDictCounselTypeVO> htDictCounselTypeVOList = new ArrayList<>();
            //格式转换
            for (HTDictCounselTypeBean htDictCounselTypeBean:htDictCounselTypeBeanList){
                HTDictCounselTypeVO htDictCounselTypeVO = new HTDictCounselTypeVO();
                //咨询类别ID
                htDictCounselTypeVO.setCounselTypeId(htDictCounselTypeBean.getCounselTypeId());
                //咨询类别名称
                htDictCounselTypeVO.setCounselTypeName(htDictCounselTypeBean.getCounselTypeName());
                //合同类型ID
                htDictCounselTypeVO.setContractTypeId(htDictCounselTypeBean.getContractTypeId());
                htDictCounselTypeVOList.add(htDictCounselTypeVO);
            }
            return htDictCounselTypeVOList;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 根据部门ID查询任务阶段信息
     * @param htDictCounselTypeBean 部门ID、所属高管
     * @return 任务阶段信息列表
     * @throws SystemException 系统异常
     */
    @Override
    @Transactional
    public List<HTDictCounselTypeVO> excSearchTaskPeriodListByDepartmentId(HTDictCounselTypeBean htDictCounselTypeBean) throws SystemException  {

        try {

            // 根据部门ID查询任务阶段信息
            List<HTDictCounselTypeBean> taskPeriodBeanList =
                    htDictCounselTypeDao.excSearchTaskPeriodListByDepartmentId(htDictCounselTypeBean);

            List<HTDictCounselTypeVO> taskPeriodVOList = new ArrayList<>();

            //格式转换
            for (HTDictCounselTypeBean htTaskPeriodBean:taskPeriodBeanList){

                HTDictCounselTypeVO htDictCounselTypeVO = new HTDictCounselTypeVO();

                // 阶段id
                htDictCounselTypeVO.setPeriodId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getPeriodId()));
                // 父阶段id
                htDictCounselTypeVO.setParentPeriodId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getParentPeriodId()));
                // 阶段名称
                htDictCounselTypeVO.setPeriodName(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getPeriodName()));
                // 合同类型id
                htDictCounselTypeVO.setContractTypeId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getContractTypeId()));
                // 咨询类型id
                htDictCounselTypeVO.setCounselTypeId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getCounselTypeId()));
                // 部门id
                htDictCounselTypeVO.setDepartmentId(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getDictDepartmentId()));
                // 级别
                htDictCounselTypeVO.setPeriodLevel(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getPeriodLevel()));
                // 工作内容说明
                htDictCounselTypeVO.setPeriodDesc(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getPeriodDesc()));
                // 阶段类型
                htDictCounselTypeVO.setPeriodType(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getPeriodType()));
                // 阶段排序
                htDictCounselTypeVO.setSort(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getSort()));
                // 页面显示内容
                htDictCounselTypeVO.setPageContent(StringToolUtils
                        .convertNullObjectToString(htTaskPeriodBean.getPageContent()));

                taskPeriodVOList.add(htDictCounselTypeVO);
            }
            return taskPeriodVOList;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }

    }
}
