package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTWholeProcessMeetingBean;

import java.util.List;

/**
 * 全过程 会议纪要 阶段 业务层
 *
 * @author zjy
 * Created in 2019/9/9 11:27
 */
public interface HTWholeProcessMeetingDao extends BaseDao {

    /**
     * 查询列表信息
     * @return 列表信息
     */
    List<HTWholeProcessMeetingBean> excSearchMeetingPeriodList(HTWholeProcessMeetingBean paramBean);

    /**
     * 查询列表信息条数
     * @return 列表信息条数
     */
    int excSearchMeetingPeriodCount(HTWholeProcessMeetingBean paramBean);

    /**
     * 查询信息（导出）
     *
     * @return 列表信息
     */
    List<HTWholeProcessMeetingBean> excSearchProMeetingPeriodListForExport(HTWholeProcessMeetingBean paramBean);
}
