package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTAchievementReportFormBean;
import com.hd.manager.dao.bean.HTSyntheticalReportFormBean;
import com.hd.manager.dao.bean.HTUserQuestionRecordRelationBean;

import java.util.List;

public interface HTReportFormDao extends BaseDao {

    /**
     * 绩效报表列表信息查询
     */
    List<HTAchievementReportFormBean> excSearchUserAchievementsReportForm(HTAchievementReportFormBean paramBean);

    /**
     * 绩效报表总条数查询
     */
    Integer excSearchUserAchievementsReportFormCount(HTAchievementReportFormBean paramBean);

    /**
     *  成员扣分记录列表信息查询
     */
    List<HTUserQuestionRecordRelationBean> excSearchHTUserQuestionRecordRelationList(HTAchievementReportFormBean paramBean);

    /**
     *  成员扣分记录总数查询
     */
    Integer excSearchHTUserQuestionRecordRelationCount(HTAchievementReportFormBean paramBean);

    /**
     *  综合统计记录列表信息
     */
    List<HTSyntheticalReportFormBean> excSearchSyntheticalReportFormList(HTSyntheticalReportFormBean paramBean);
}
