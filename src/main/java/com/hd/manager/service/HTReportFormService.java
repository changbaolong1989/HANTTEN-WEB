package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTAchievementReportFormVO;
import com.hd.manager.vo.HTSyntheticalReportFormVO;
import com.hd.manager.vo.HTUserQuestionRecordRelationVO;

import java.util.Map;

public interface HTReportFormService extends BaseService {

    /**
     * 查询绩效报表
     */
    Map<String,Object> excSearchUserAchievementsReportForm(HTAchievementReportFormVO paramVO) throws LogicException,SystemException;

    /**
     *  成员扣分记录列表信息查询
     */
    public Map<String, Object> excSearchHTUserQuestionRecordRelationList(HTAchievementReportFormVO paramVO) throws LogicException,SystemException;

    /**
     *  综合统计记录列表信息
     */
    public Map<String, Object> excSearchSyntheticalReportFormList(HTSyntheticalReportFormVO paramVO) throws LogicException,SystemException;
}
