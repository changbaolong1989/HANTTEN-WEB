package com.hd.common.service;

import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTTaskPeriodVO;

import java.util.List;

public interface CommonTaskPeriodService  extends BaseService {

    /**
     * 通过咨询类型ID查询任务阶段列表
     * @return 任务阶段列表
     */
    List<HTTaskPeriodVO> searchTaskPeriodListByCounselTypeId(String counselTypeId);
}
