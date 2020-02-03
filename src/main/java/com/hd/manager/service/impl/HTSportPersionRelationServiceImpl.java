package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.common.dao.TbTaskPeriodRelationEntityMapper;
import com.hd.common.dao.TbTaskSpotPersonRelationEntityMapper;
import com.hd.common.entity.TbTaskPeriodRelationEntity;
import com.hd.common.entity.TbTaskSpotPersonRelationEntity;
import com.hd.common.util.*;
import com.hd.manager.dao.HTTbTaskSpotPersonRelationDao;
import com.hd.manager.dao.bean.TbTaskSpotPersonRelationBean;
import com.hd.manager.service.HTSportPersionRelationService;
import com.hd.manager.vo.TbTaskSpotPersonRelationVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

@Service
public class HTSportPersionRelationServiceImpl implements HTSportPersionRelationService {

    @Resource
    private HTTbTaskSpotPersonRelationDao htTbTaskSpotPersonRelationDao;

    @Resource
    private TbTaskSpotPersonRelationEntityMapper tbTaskSpotPersonRelationEntityMapper;

    @Resource
    private TbTaskPeriodRelationEntityMapper tbTaskPeriodRelationEntityMapper;

    @Override
    public Map<String, Object> queryAll(TbTaskSpotPersonRelationVO paramVO) {
        try {
            Map<String,Object> resultMap = new HashMap<>();
            TbTaskSpotPersonRelationBean paramBean = new TbTaskSpotPersonRelationBean();

            // 根据任务阶段id查询任务id
            TbTaskPeriodRelationEntity retEntity =
                    tbTaskPeriodRelationEntityMapper.selectByPrimaryKey(paramVO.getRelationId());

            // 任务id
            paramBean.setTaskId(StringToolUtils
                    .convertNullObjectToString(retEntity.getTaskId()));
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //查询列表
            List<TbTaskSpotPersonRelationBean> list = htTbTaskSpotPersonRelationDao.searchList(paramBean);
            Integer count = htTbTaskSpotPersonRelationDao.searchCount(paramBean);
            List<TbTaskSpotPersonRelationVO> returnVO = new ArrayList<>();
            //格式转换
           for (TbTaskSpotPersonRelationBean tempBean : list) {
                TbTaskSpotPersonRelationVO vo = new TbTaskSpotPersonRelationVO();
                vo.setRelationId(tempBean.getRelationId());
                vo.setUserName(tempBean.getUserName());
                vo.setWorkContent(tempBean.getWorkContent());
                vo.setStartDate(DateToolUtils.convertDateFormat(tempBean.getStartDate(), DateToolUtils.yyyy_MM_dd));
                vo.setEndDate(DateToolUtils.convertDateFormat(tempBean.getEndDate(), DateToolUtils.yyyy_MM_dd));
                returnVO.add(vo);
            }
            resultMap.put(Constant.DATA_LIST_KEY,returnVO);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    @Override
    public void add(TbTaskSpotPersonRelationVO tbTaskSpotPersonRelationVO) {
        try {
            TbTaskSpotPersonRelationEntity entity = new TbTaskSpotPersonRelationEntity();

            // 根据任务阶段id查询任务id
            TbTaskPeriodRelationEntity retEntity =
                    tbTaskPeriodRelationEntityMapper.selectByPrimaryKey(tbTaskSpotPersonRelationVO.getRelationId());
            //任务ID
            entity.setTaskId(retEntity.getTaskId());
            entity.setUserName(tbTaskSpotPersonRelationVO.getUserName());
            entity.setStartDate(DateToolUtils.convertDateFormat(tbTaskSpotPersonRelationVO.getStartDate(), DateToolUtils.yyyy_MM_dd));
            entity.setWorkContent(tbTaskSpotPersonRelationVO.getWorkContent());
            //创建人
            entity.setCreateUserId(tbTaskSpotPersonRelationVO.getLoginUserInfo().getLoginUserId());
            //修改人
            entity.setUpdateUserId(tbTaskSpotPersonRelationVO.getLoginUserInfo().getLoginUserId());
            //创建时间
            entity.setCreateDate(new Date());
            //修改时间
            entity.setUpdateDate(new Date());
            //是否删除
            entity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
            tbTaskSpotPersonRelationEntityMapper.insertSelective(entity);
        } catch (LogicException lex) {
            throw  lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }


    @Override
    public void updateLeaveTime(TbTaskSpotPersonRelationVO paramVO) {
        TbTaskSpotPersonRelationEntity entity = new TbTaskSpotPersonRelationEntity();
        entity.setRelationId(paramVO.getRelationId());
        entity.setUpdateDate(new Date());
        entity.setEndDate(DateToolUtils.convertDateFormat(paramVO.getEndDate(), DateToolUtils.yyyy_MM_dd));
        tbTaskSpotPersonRelationEntityMapper
                .updateByPrimaryKeySelective(entity);
    }
}
