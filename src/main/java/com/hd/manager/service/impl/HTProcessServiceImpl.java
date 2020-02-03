package com.hd.manager.service.impl;

import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbJobProcessNodeRelationEntityMapper;
import com.hd.common.entity.TbJobProcessNodeRelationEntity;
import com.hd.common.entity.TbJobProcessNodeRelationEntityExample;
import com.hd.common.util.BusinessUtils;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.manager.dao.HTProcessNodeRelationDao;
import com.hd.manager.dao.bean.HTJobProcessNodeRelationBean;
import com.hd.manager.service.HTProcessService;
import com.hd.manager.vo.HTJobProcessNodeRelationVo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HTProcessServiceImpl extends BaseServiceImpl implements HTProcessService {

    @Resource
    private HTProcessNodeRelationDao processNodeDao;

    @Resource
    private TbJobProcessNodeRelationEntityMapper processNodeRelationMapper;

    @Override
    public Map<String, Object> getProcessNodeRelation(HTJobProcessNodeRelationVo paramVO) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HTJobProcessNodeRelationBean paramBean = (HTJobProcessNodeRelationBean) ObjectReflectUtils.convertObj(paramVO, HTJobProcessNodeRelationBean.class);
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            long count = processNodeDao.countProcessNodeRelation(paramBean);
            paramBean.setOrderByClause(" process_dict_id , department_id, job_id ");
            List<HTJobProcessNodeRelationBean> list = processNodeDao.getProcessNodeRelation(paramBean);
            resultMap.put(Constant.DATA_LIST_KEY, list);
            resultMap.put(Constant.DATA_COUNT_KEY, count);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    @Override
    public void updateProcessNodeRelation(HTJobProcessNodeRelationVo paramVO) throws DuplicateKeyException {
        TbJobProcessNodeRelationEntity jobProcessNode = (TbJobProcessNodeRelationEntity) ObjectReflectUtils.convertObj(paramVO, TbJobProcessNodeRelationEntity.class);
        TbJobProcessNodeRelationEntityExample example = new TbJobProcessNodeRelationEntityExample();
        example.createCriteria().andUuidEqualTo(jobProcessNode.getUuid());
        processNodeRelationMapper.updateByExampleSelective(jobProcessNode, example);
    }

    @Override
    public void delProcessNodeRelation(HTJobProcessNodeRelationVo paramVO) {
        TbJobProcessNodeRelationEntityExample example = new TbJobProcessNodeRelationEntityExample();
        example.createCriteria().andUuidEqualTo(paramVO.getUuid());
        processNodeRelationMapper.deleteByExample(example);
    }

    @Override
    public List<HTJobProcessNodeRelationBean> searchSelectByExample(HTJobProcessNodeRelationVo paramVO) {
        List<HTJobProcessNodeRelationBean> list;
        try {
            HTJobProcessNodeRelationBean paramBean = (HTJobProcessNodeRelationBean) ObjectReflectUtils.convertObj(paramVO, HTJobProcessNodeRelationBean.class);
            list = processNodeDao.getProcessNodeRelation(paramBean);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return list;
    }

    @Override
    public void saveProcessNodeRelation(HTJobProcessNodeRelationVo paramVO) throws DuplicateKeyException {
        TbJobProcessNodeRelationEntity paramBean = (TbJobProcessNodeRelationEntity) ObjectReflectUtils.convertObj(paramVO, TbJobProcessNodeRelationEntity.class);
        paramBean.setUuid(UUID.randomUUID().toString());
        processNodeRelationMapper.insertSelective(paramBean);
    }

    @Override
    public List<Map<String, Object>> searchProcessSelect() {
        List<Map<String, Object>> result;
        try {
            result = processNodeDao.getProcessSelect();
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return result;

    }

    @Override
    public List<Map<String, Object>> getDepartmentSelect() {
        List<Map<String, Object>> result;
        try {
            result = processNodeDao.getDepartmentSelect();
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getJobSelect(HTJobProcessNodeRelationVo paramVO) {
        List<Map<String, Object>> result;
        try {
            HTJobProcessNodeRelationBean paramBean = (HTJobProcessNodeRelationBean) ObjectReflectUtils.convertObj(paramVO, HTJobProcessNodeRelationBean.class);
            result = processNodeDao.getJobSelect(paramBean);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getNodeSelect(HTJobProcessNodeRelationVo paramVO) {
        List<Map<String, Object>> result;
        try {
            HTJobProcessNodeRelationBean paramBean = (HTJobProcessNodeRelationBean) ObjectReflectUtils.convertObj(paramVO, HTJobProcessNodeRelationBean.class);
            result = processNodeDao.getNodeSelect(paramBean);
        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return result;
    }


}
