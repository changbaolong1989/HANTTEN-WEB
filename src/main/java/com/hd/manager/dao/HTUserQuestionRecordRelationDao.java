package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTUserQuestionRecordRelationBean;
import com.hd.manager.vo.HTTenderTaskVO;

import java.util.List;

/**
 * 审核记录和人员和问题类型关联表 DAO
 *
 * @author LH
 * Created in 2019/7/20 16:00
 */
public interface HTUserQuestionRecordRelationDao extends BaseDao {

    //查询人员问题列表
    public List<HTUserQuestionRecordRelationBean> excSearchHTUserQuestionRecordRelationList(HTUserQuestionRecordRelationBean paramBean);
    //批量插入审核记录和人员和问题类型关联
    void excInsertUserQuestionRecordRelations(List<HTUserQuestionRecordRelationBean> paramBeanList);

    /**
     *
     * @param businessIds
     */
    void deleteUserQuestionRecord(List<String> businessIds);
}
