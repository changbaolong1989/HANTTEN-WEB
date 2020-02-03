package com.hd.common.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.service.CommonTenderTaskService;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.MessageConstant;
import com.hd.manager.dao.HTTaskPeriodDao;
import com.hd.manager.dao.HTTenderTaskDao;
import com.hd.manager.dao.bean.HTTenderTaskBean;
import com.hd.manager.dao.bean.HTTenderTaskPeriodBean;
import com.hd.manager.service.HTUserPowerService;
import com.hd.manager.vo.ElementTreeVO;
import com.hd.manager.vo.HTTenderTaskPeriodVO;
import com.hd.manager.vo.HTTenderTaskVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class CommonTenderTaskServiceImpl extends BaseServiceImpl implements CommonTenderTaskService {

    /**
     * 投标任务阶段Dao
     */
    @Resource
    private HTTaskPeriodDao htTaskPeriodDao;

    /**
     * 投标任务Dao
     */
    @Resource
    private HTTenderTaskDao htTenderTaskDao;

    /**
     * 用户权限Dao
     */
    @Resource
    private HTUserPowerService htUserPowerService;

    /**
     * 批量添加投标任务和阶段关联信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTenderTaskPeriodInfo(HTTenderTaskPeriodVO paramVO) throws LogicException,SystemException {
        try {
            Date currentDate = new Date();
            //根据部门类型ID查询任务阶段ID列表
            List<String> periodIds = htTaskPeriodDao.excSearchPeriodIdsByDepartmentId(paramVO.getDepartmentId());
            List<HTTenderTaskPeriodBean> htTenderTaskPeriodBeanList = new ArrayList<>();
            for (String periodId:periodIds){
                HTTenderTaskPeriodBean htTenderTaskPeriodBean = new HTTenderTaskPeriodBean();
                //阶段ID
                htTenderTaskPeriodBean.setPeriodId(periodId);
                //投标任务ID
                htTenderTaskPeriodBean.setTenderTaskId(paramVO.getTenderTaskId());
                //关联ID
                htTenderTaskPeriodBean.setRelationId(UUID.randomUUID().toString());
                //创建时间
                htTenderTaskPeriodBean.setCreateDate(currentDate);
                //创建人
                htTenderTaskPeriodBean.setCreateUserId(paramVO.getCreateUserId());
                //修改时间
                htTenderTaskPeriodBean.setUpdateDate(currentDate);
                //修改人
                htTenderTaskPeriodBean.setUpdateUserId(paramVO.getCreateUserId());
                //是否删除
                htTenderTaskPeriodBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
                htTenderTaskPeriodBeanList.add(htTenderTaskPeriodBean);
            }
            if (htTenderTaskPeriodBeanList.size()>0){
                //批量添加投标任务和阶段关联信息
                htTenderTaskDao.excInsertHTTenderTaskPeriods(htTenderTaskPeriodBeanList);
            }
        } catch (LogicException ex) {
            throw ex;
        } catch (SystemException ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }

    }

    /**
     * 查询阶段列表
     */
    @Override
    public List<ElementTreeVO> searchTaskPeriodByTenderTaskId(HTTenderTaskVO paramVO) {
        try {
            //查询阶段列表
            List<HTTenderTaskPeriodBean> htTenderTaskPeriodBeanList = htTenderTaskDao.
                    excSearchTaskPeriodByTenderTaskId(paramVO.getTenderTaskId());
            /*--------------格式转换------------------*/
            //查询投标任务详情
            HTTenderTaskBean htTenderTaskBean = htTenderTaskDao.excSearchTenderTaskInfoById(paramVO.getTenderTaskId());
            ElementTreeVO treeVO = new ElementTreeVO();
            //唯一标识
            treeVO.setId(htTenderTaskBean.getTenderTaskId());
            //展示名称
            treeVO.setLabel(htTenderTaskBean.getTenderTaskName());
            //是否可以撤回（0：否 1：是）
            treeVO.setIsCanRecall(htTenderTaskBean.getIsCanRecall());
            //是否展示功能菜单
            treeVO.setShowTool(Constant.SHOW_FIRST_TYPE_MENU_FLAG);
            //当前登录人是否是主提审人
            if (paramVO.getLoginUserInfo().getLoginUserId().equals(htTenderTaskBean.getApplyUserId())){
                treeVO.setIsMainHeadFlag(Constant.TENDER_TASK_LOGIN_USER_TYPE);
            }
            treeVO.setPowerInfo(htUserPowerService.excSearchUserWorkPowerMap(paramVO.getLoginUserInfo()
                    ,htTenderTaskBean.getTenderTaskId(),Constant.POWER_MODULE_BELONG_TENDER_TASK));
            //节点等级
            treeVO.setTreeNodeLevel(Constant.FIRST_LEVEL_MENU_FLAG);
            //审核状态
            treeVO.setVerifyState(htTenderTaskBean.getVerifyState());
            //任务概述
            treeVO.setTaskContent(htTenderTaskBean.getTenderTaskContent());
            //投标任务状态(0：未开标 1；未中标 2：已中标)
            treeVO.setTenderTaskSts(htTenderTaskBean.getTenderTaskSts());
            //开标时间
            treeVO.setStartDate(DateToolUtils.convertDateFormat(htTenderTaskBean.getStartDate(),
                    DateToolUtils.yyyy_MM_dd));
            List<ElementTreeVO> resultList = new ArrayList<>();
            List<ElementTreeVO> childrenList = new ArrayList<>();
            for (HTTenderTaskPeriodBean htTenderTaskPeriodBean:htTenderTaskPeriodBeanList){
                ElementTreeVO elementTreeVO = new ElementTreeVO();
                //唯一标识
                elementTreeVO.setId(htTenderTaskPeriodBean.getRelationId());
                //展示名称
                elementTreeVO.setLabel(htTenderTaskPeriodBean.getPeriodName());
                //工作步骤说明
                elementTreeVO.setPeriodDesc(htTenderTaskPeriodBean.getPeriodDesc());
                //阶段类型（0：普通阶段 1：收集资料 2：A3审核阶段  3：成果阶段）
                elementTreeVO.setPeriodType(htTenderTaskPeriodBean.getPeriodType());
                //是否展示功能菜单
                elementTreeVO.setShowTool(Constant.HIDDEN_MENU_FLAG);
                //节点等级
                elementTreeVO.setTreeNodeLevel(Constant.SECOND_LEVEL_MENU_FLAG);
                childrenList.add(elementTreeVO);
            }
            //添加到子集
            treeVO.setChildren(childrenList);
            resultList.add(treeVO);
            return resultList;
        } catch (LogicException ex) {
            throw ex;
        } catch (SystemException ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }
}
