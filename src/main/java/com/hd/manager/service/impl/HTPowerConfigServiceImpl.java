package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.util.BusinessUtils;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.manager.dao.HTPowerConfigDao;
import com.hd.manager.dao.bean.HTJobBean;
import com.hd.manager.dao.bean.HTJobPowerRelationBean;
import com.hd.manager.dao.bean.HTPowerBean;
import com.hd.manager.service.HTPowerConfigService;
import com.hd.manager.vo.HTJobPowerRelationVO;
import com.hd.manager.vo.HTJobVO;
import com.hd.manager.vo.HTPowerVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class HTPowerConfigServiceImpl extends BaseServiceImpl implements HTPowerConfigService {

    /**
     *职位权限Dao
     */
    @Resource
    private HTPowerConfigDao htPowerConfigDao;

    /**
     * 查询职位列表
     * @return 职位列表
     */
    @Override
    public Map<String,Object> excSearchJobList(HTJobVO paramVO) throws LogicException, SystemException {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            HTJobBean paramBean = new HTJobBean();
            //部门ID
            paramBean.setDepartmentId(paramVO.getDepartmentId());
            //职位名称
            paramBean.setJobName(paramVO.getJobName());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //职位和权限列表
            List<HTJobBean> htJobBeans = htPowerConfigDao.excSearchJobList(paramBean);
            //职位和权限列表总个数
            Integer count = htPowerConfigDao.excSearchJobListCount(paramBean);
            List<HTJobVO> htJobVOS = new ArrayList<>();
            //格式转换
            for (HTJobBean htJobBean:htJobBeans){
                HTJobVO htJobVO = new HTJobVO();
                //职位ID
                htJobVO.setJobId(htJobBean.getJobId());
                //职位名称
                htJobVO.setJobName(htJobBean.getJobName());
                //部门ID
                htJobVO.setDepartmentId(htJobBean.getDepartmentId());
                //部门名称
                htJobVO.setDepartmentName(htJobBean.getDepartmentName());
                //关联权限名称集合
                htJobVO.setPowerNames(htJobBean.getPowerNames());
                //职位级
                htJobVO.setJobLevel(htJobBean.getJobLevel());
                htJobVOS.add(htJobVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY,htJobVOS);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询职位权限信息
     * @return 职位权限详情
     */
    @Override
    public HTJobVO excSearchJobInfo(HTJobVO paramVO) throws LogicException, SystemException {
        try {
            HTJobBean paramBean = new HTJobBean();
            //部门ID
            paramBean.setJobId(paramVO.getJobId());
            HTJobBean htJobBean = htPowerConfigDao.excSearchJobInfo(paramBean);
            //格式转换
            HTJobVO  htJobVO = new HTJobVO();
            //职位ID
            htJobVO.setJobId(htJobBean.getJobId());
            //职位名称
            htJobVO.setJobName(htJobBean.getJobName());
            //部门名称
            htJobVO.setDepartmentName(htJobBean.getDepartmentName());
            //部门ID
            htJobVO.setDepartmentId(htJobBean.getDepartmentId());
            //职位关联权限
            List<HTJobPowerRelationBean> relationBeanList = htJobBean.getHtJobPowerRelationBeanList();
            List<HTJobPowerRelationVO> relationVOList = new ArrayList<>();
            for (HTJobPowerRelationBean htJobPowerRelationBean:relationBeanList){
                HTJobPowerRelationVO htJobPowerRelationVO = new HTJobPowerRelationVO();
                //唯一标识
                htJobPowerRelationVO.setRelationId(htJobPowerRelationBean.getRelationId());
                //权限ID
                htJobPowerRelationVO.setPowerId(htJobPowerRelationBean.getPowerId());
                relationVOList.add(htJobPowerRelationVO);
            }
            htJobVO.setHtJobPowerRelationVOList(relationVOList);
            return htJobVO;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询所有权限列表
     * @return 所有权限列表
     */
    @Override
    public List<HTPowerVO> excSearchAllPowerList() throws LogicException, SystemException {
        try {
            List<HTPowerBean> htPowerBeans = htPowerConfigDao.excSearchAllPowerList();
            List<HTPowerVO> htPowerVOS = new ArrayList<>();
            //格式转换
            for (HTPowerBean htPowerBean:htPowerBeans){
                HTPowerVO htPowerVO = new HTPowerVO();
                //权限ID
                htPowerVO.setPowerId(htPowerBean.getPowerId());
                //权限名称
                htPowerVO.setPowerName(htPowerBean.getPowerName());
                //权限标识
                htPowerVO.setPowerKey(htPowerBean.getPowerKey());
                //权限规则标识
                htPowerVO.setPowerRuleKey(htPowerBean.getPowerRuleKey());
                //权限规则名称
                htPowerVO.setPowerRuleName(htPowerBean.getPowerRuleName());
                //权限模块所属
                htPowerVO.setModuleBelong(htPowerBean.getModuleBelong());
                //权限所属模块名称
                htPowerVO.setModuleBelongName(htPowerBean.getModuleBelongName());
                htPowerVOS.add(htPowerVO);
            }
            return htPowerVOS;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 批量修改职位权限
     */
    @Override
    @Transactional
    public void excUpdatePowers(HTJobVO paramVO) throws LogicException, SystemException {
        try {
            Date currentDate = new Date();
            //删除原关联
            htPowerConfigDao.excDeletePowers(paramVO.getJobId());
            List<HTJobPowerRelationVO> htJobPowerRelationVOList = paramVO.getHtJobPowerRelationVOList();
            List<HTJobPowerRelationBean> htJobPowerRelationBeanList = new ArrayList<>();
            //格式转换
            for (HTJobPowerRelationVO htJobPowerRelationVO:htJobPowerRelationVOList){
                HTJobPowerRelationBean htJobPowerRelationBean = new HTJobPowerRelationBean();
                //关联标识
                htJobPowerRelationBean.setRelationId(UUID.randomUUID().toString());
                //职位ID
                htJobPowerRelationBean.setJobId(paramVO.getJobId());
                //权限ID
                htJobPowerRelationBean.setPowerId(htJobPowerRelationVO.getPowerId());
                //创建日期
                htJobPowerRelationBean.setCreateDate(currentDate);
                //创建人
                htJobPowerRelationBean.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                //修改日期
                htJobPowerRelationBean.setUpdateDate(currentDate);
                //修改人
                htJobPowerRelationBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                //是否删除标识
                htJobPowerRelationBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
                htJobPowerRelationBeanList.add(htJobPowerRelationBean);
            }
            //批量添加新关联
            htPowerConfigDao.excInsertPowers(htJobPowerRelationBeanList);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询所有部门
     * @return 所有部门列表
     */
    @Override
    public List<HTJobVO> excSearchAllDepartments() throws LogicException, SystemException {
        try {
            List<HTJobBean> htJobBeans = htPowerConfigDao.excSearchAllDepartments();
            List<HTJobVO> htJobVOS = new ArrayList<>();
            //格式转换
            for (HTJobBean htJobBean:htJobBeans){
                HTJobVO htJobVO = new HTJobVO();
                //部门ID
                htJobVO.setDepartmentId(htJobBean.getDepartmentId());
                //部门名称
                htJobVO.setDepartmentName(htJobBean.getDepartmentName());
                htJobVOS.add(htJobVO);
            }
            return htJobVOS;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }
}
