package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.dao.bean.HTTenderTaskHisTaskInstBean;
import com.hd.manager.dao.bean.HTUserQuestionRecordRelationBean;
import com.hd.manager.vo.HTTenderTaskHisTaskInstVO;
import com.hd.manager.vo.HTTenderTaskVO;
import com.hd.manager.vo.HTUserQuestionRecordRelationVO;

import java.util.List;

/**
 * 投标任务历史流程任务 service
 *
 * @author LH
 * Created in 2019/7/20 16:00
 */
public interface HTTenderTaskHisTaskInstService extends BaseService {


    /**
     * 查询审核意见信息
     * @return 审核意见信息
     */
    public List<HTTenderTaskHisTaskInstVO> excSearchHTTenderTaskHisTaskInst(HTTenderTaskHisTaskInstVO paramVO) throws LogicException, SystemException;

}
