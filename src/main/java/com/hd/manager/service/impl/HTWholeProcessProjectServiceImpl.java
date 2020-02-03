package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.util.*;
import com.hd.manager.dao.HTContractDao;
import com.hd.manager.dao.HTProjectDao;
import com.hd.manager.dao.HTWholeProcessProjectDao;
import com.hd.manager.dao.bean.HTContractBean;
import com.hd.manager.dao.bean.HTProPeriodBean;
import com.hd.manager.dao.bean.HTProjectBean;
import com.hd.manager.service.HTWholeProcessProjectService;
import com.hd.manager.vo.ElementTreeVO;
import com.hd.manager.vo.HTContractVO;
import com.hd.manager.vo.HTProPeriodVO;
import com.hd.manager.vo.HTProjectVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 全过程项目列表 业务层 实现类
 *
 * @author LH
 * Created in 2019/9/3 15:31
 */
@Service
public class HTWholeProcessProjectServiceImpl extends BaseServiceImpl implements HTWholeProcessProjectService {


    /**
     * 合同dao 持久层资源
     */
    @Resource
    private HTProjectDao htProjectDao;

    /**
     * 合同dao 持久层资源
     */
    @Resource
    private HTWholeProcessProjectDao htWholeProcessProjectDao;

    /**
     *  查询合同列表信息(供全过程使用)
     * @param paramVO 合同编码
     */
    @Override
    public Map<String, Object> excSearchContractList(HTProjectVO paramVO) throws LogicException, SystemException {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            HTProjectBean paramBean = new HTProjectBean();

            // 设置部门id
            paramBean.setDepartmentId(StringToolUtils.convertNullObjectToString(paramVO.getDepartmentId()));

            // 设置项目状态
            paramBean.setProjectSts(StringToolUtils.convertNullObjectToString(paramVO.getProjectSts()));

            // 设置项目编号
            paramBean.setProjectNum(StringToolUtils.convertNullObjectToString(paramVO.getProjectNum()));

            // 设置项目名称
            paramBean.setProjectName(StringToolUtils.convertNullObjectToString(paramVO.getProjectName()));

            //咨询业务类型:造价咨询
            paramBean.setCounselBusinessType(Constant.WHOLE_PROCESS_PROPERTY_BUSINESS_TYPE);

            //咨询类型阶段:全过程
            paramBean.setCounselTypePeriod(Constant.WHOLE_PROCESS_PROPERTY_TYPE_PERIOD);

            // 如果时间不为空，则设置值
            if(!ListToolUtils.isEmpty(paramVO.getTimeList())){
                // 如果检索条件开始时间不为空
                if (!StringToolUtils.isNull(paramVO.getTimeList().get(0))) {
                    //将开始时间赋值给bean
                    paramBean.setStartDate(DateToolUtils.convertDateFormat(
                            paramVO.getTimeList().get(0), DateToolUtils.yyyy_MM_dd));
                }

                // 如果检索条件结束时间不为空
                if (!StringToolUtils.isNull(paramVO.getTimeList().get(1))) {
                    //将结束时间赋值给bean
                    paramBean.setEndDate(DateToolUtils.convertDateFormat(
                            paramVO.getTimeList().get(1), DateToolUtils.yyyy_MM_dd));
                }
            }

            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            // 查询项目总条数
            int retCount = htProjectDao.excSearchProjectWholeListCount(paramBean);

            // 查询项目列表数据
            List<HTProjectBean> projectList = htProjectDao.excSearchProjectWholeList(paramBean);

            // 集合
            List<HTProjectVO> retProjectList = new ArrayList<>();

            // 循环项目信息
            for (HTProjectBean project : projectList) {

                HTProjectVO projectVO = new HTProjectVO();

                // 设置项目id
                projectVO.setProjectId(StringToolUtils.convertNullObjectToString(
                        project.getProjectId()));

                // 设置项目编号
                projectVO.setProjectNum(StringToolUtils.convertNullObjectToString(
                        project.getProjectNum()));
                // 设置项目名称
                projectVO.setProjectName(
                        project.getProjectName());

                // 设置合同数
                projectVO.setContractCount(StringToolUtils.convertNullObjectToString(
                        project.getContractCount() == null ? StringToolUtils.STRING_ZERO : project.getContractCount()));

                // 设置项目状态
                if (null != project.getProjectSts()){
                    projectVO.setProjectSts(StringToolUtils.convertNullObjectToString(
                            project.getProjectSts()));
                }
                // 设置合同编号
                if (null != project.getContractNum()){
                    projectVO.setContractNum(StringToolUtils.convertNullObjectToString(
                            project.getProjectNum() + "-" + project.getContractNum()));
                }
                // 设置部门名称
                if (null != project.getDepartmentName()){
                    projectVO.setDepartmentName(StringToolUtils.convertNullObjectToString(
                            project.getDepartmentName()));
                }
                // 设置负责人
                if (null != project.getDutyPeople()){
                    projectVO.setDutyPeople(StringToolUtils.convertNullObjectToString(
                            project.getDutyPeople()));
                }
                // 设置合同开始时间
                if (null != project.getContractDate()){
                    projectVO.setContractDate(DateToolUtils.convertDateFormat(
                            project.getContractDate(), DateToolUtils.yyyy_MM_dd));
                }
                // 已收入
                if (null != project.getIncomeReceived()){
                    projectVO.setIncomeReceived(NumberToolUtils.convertNumberToFormatStringWithHalfUp(
                            project.getIncomeReceived(), NumberToolUtils.INT_SCALE_FLOAT));
                }
                // 项目金额
                if (null != project.getContractAmount()){
                    projectVO.setContractAmount(NumberToolUtils.convertNumberToFormatStringWithHalfUp(
                            project.getContractAmount(), NumberToolUtils.INT_SCALE_FLOAT));
                }
                retProjectList.add(projectVO);
            }

            resultMap.put(Constant.DATA_LIST_KEY, retProjectList);

            resultMap.put(Constant.DATA_COUNT_KEY, retCount);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    /**
     * 查询项目阶段列表
     *
     * @return
     */
    @Override
    public List<ElementTreeVO> excSearchProjectPeriodList(HTProjectVO paramVO) throws LogicException, SystemException  {
        try {
            List<ElementTreeVO> elementTreeParentVOList = new ArrayList<>();
            List<ElementTreeVO> elementTreeVOList = new ArrayList<>();
            //查询合同详情
            List<HTProPeriodBean> htProPeriodBeanList = htWholeProcessProjectDao.excSearchProPeriodList();
            for (HTProPeriodBean htProPeriodBean:htProPeriodBeanList){
                //格式转换(转换成前端elementTreeUI对应格式)
                ElementTreeVO elementTreeVO = new ElementTreeVO();
                if (htProPeriodBean.getProjectPeriodLevel().equals(Constant.FIRST_LEVEL_MENU_FLAG)){
                    //一级唯一标识
                    elementTreeVO.setId(htProPeriodBean.getProjectPeriodId());
                    //一级展示标签
                    elementTreeVO.setLabel(htProPeriodBean.getProjectPeriodName());
                    //是否可点击Flag
                    elementTreeVO.setPeriodType(htProPeriodBean.getIsClick());
                    //一级跳转页面
                    elementTreeVO.setShowTool(htProPeriodBean.getProjectPeriodUrl());
                    List<ElementTreeVO> elementTreeVOChildList = new ArrayList<>();
                    for (HTProPeriodBean htProPeriodChildBean:htProPeriodBeanList){
                        ElementTreeVO elementTreeChildVO = new ElementTreeVO();

                        if (!StringToolUtils.isNull(htProPeriodChildBean.getProjectPeriodParentId())&&htProPeriodChildBean.getProjectPeriodParentId().equals(elementTreeVO.getId())){
                            //子集唯一标识
                            elementTreeChildVO.setId(htProPeriodChildBean.getProjectPeriodId());
                            //子集展示标签
                            elementTreeChildVO.setLabel(htProPeriodChildBean.getProjectPeriodName());
                            //是否可点击Flag
                            elementTreeChildVO.setPeriodType(htProPeriodChildBean.getIsClick());
                            //子集跳转页面
                            elementTreeChildVO.setShowTool(htProPeriodChildBean.getProjectPeriodUrl());
                            elementTreeVOChildList.add(elementTreeChildVO);
                        }
                    }
                    //子集添加
                    elementTreeVO.setChildren(elementTreeVOChildList);
                    elementTreeVOList.add(elementTreeVO);
                }
            }
            ElementTreeVO elementTreeVO = new ElementTreeVO();
            elementTreeVO.setId(UUID.randomUUID().toString());
            elementTreeVO.setLabel(paramVO.getProjectNum());
            elementTreeVO.setChildren(elementTreeVOList);
            elementTreeParentVOList.add(elementTreeVO);
            return elementTreeParentVOList;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

}
