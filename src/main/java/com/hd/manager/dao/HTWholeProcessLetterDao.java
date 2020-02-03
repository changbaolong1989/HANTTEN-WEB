package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTWholeProcessLetterBean;

import java.util.List;

/**
 * 全过程发出信函登记表阶段 业务层
 *
 * @author zjy
 * Created in 2019/9/9 11:27
 */
public interface HTWholeProcessLetterDao extends BaseDao {

    /**
     * 查询列表信息
     * @return 列表信息
     */
    List<HTWholeProcessLetterBean> excSearchLetterPeriodList(HTWholeProcessLetterBean paramBean);

    /**
     * 查询列表信息条数
     * @return 列表信息条数
     */
    int excSearchLetterPeriodCount(HTWholeProcessLetterBean paramBean);

    /**
     * 查询项目发出信函登记表列表
     *
     * @return 列表信息
     */
    List<HTWholeProcessLetterBean> excSearchProLetterPeriodListForExport(HTWholeProcessLetterBean paramBean);
}
