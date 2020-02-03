package com.hd.common.service;

import com.hd.base.service.BaseService;
import com.hd.manager.vo.ElementTreeVO;
import com.hd.manager.vo.HTTenderTaskPeriodVO;
import com.hd.manager.vo.HTTenderTaskVO;

import java.util.List;

public interface CommonTenderTaskService extends BaseService {

    /**
     * 批量添加投标任务和阶段关联信息
     */
    public void saveTenderTaskPeriodInfo(HTTenderTaskPeriodVO paramVO);

    /**
     * 查询阶段列表
     */
    public List<ElementTreeVO> searchTaskPeriodByTenderTaskId(HTTenderTaskVO paramVO);
}
