package com.hd.manager.service;

import com.hd.base.service.BaseService;
import com.hd.manager.vo.ElementTreeVO;
import com.hd.manager.vo.HTProPeriodVO;
import com.hd.manager.vo.HTProjectVO;

import java.util.List;
import java.util.Map;


/**
 * 全过程项目列表 业务层
 *
 * @author LH
 * Created in 2019/9/3 15:31
 */
public interface HTWholeProcessProjectService extends BaseService {

    /**
     * 查询合同列表
     *
     * @return 请求参数
     */
    Map<String,Object> excSearchContractList(HTProjectVO paramVO);

    /**
     * 查询项目阶段列表
     *
     * @return
     */
    List<ElementTreeVO> excSearchProjectPeriodList(HTProjectVO paramVO);
}
