package com.hd.manager.service;

import com.hd.base.service.BaseService;
import com.hd.manager.dao.bean.HTJobProcessNodeRelationBean;
import com.hd.manager.vo.HTJobProcessNodeRelationVo;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Map;

public interface HTProcessService extends BaseService {

    Map<String, Object> getProcessNodeRelation(HTJobProcessNodeRelationVo paramVO);

    void updateProcessNodeRelation(HTJobProcessNodeRelationVo paramVO);

    void delProcessNodeRelation(HTJobProcessNodeRelationVo paramVO);

    List<Map<String, Object>> searchProcessSelect();

    List<Map<String, Object>> getDepartmentSelect();

    List<Map<String, Object>> getJobSelect(HTJobProcessNodeRelationVo paramVO);

    List<Map<String, Object>> getNodeSelect(HTJobProcessNodeRelationVo paramVO);

    List<HTJobProcessNodeRelationBean> searchSelectByExample(HTJobProcessNodeRelationVo paramVO);

    void saveProcessNodeRelation(HTJobProcessNodeRelationVo paramVO) throws DuplicateKeyException;

}
