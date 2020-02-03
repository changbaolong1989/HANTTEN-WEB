package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTDictQuestionBean;
import java.util.List;

public interface HTDictQuestionDao extends BaseDao {

    /**
     * 通过部门id查询问题类型
     * @return 问题类型
     */
    List<HTDictQuestionBean> excSearchQuestionTypeByDeptId(HTDictQuestionBean paramBean);

    /**
     * 审核记录和人员和问题类型关联表
     */
    void excInsertUserQuestionRecordRelations(List<HTDictQuestionBean> paramList);

}
