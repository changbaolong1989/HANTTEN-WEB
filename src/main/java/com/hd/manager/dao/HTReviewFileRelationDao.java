package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTReviewFileRelationBean;

import java.util.List;

/**
 * 审核意见文件Dao层
 */
public interface HTReviewFileRelationDao extends BaseDao {


    /**
     * 查询审核意见文件信息
     * @return 审核意见文件列表信息
     */
    List<HTReviewFileRelationBean> excSearchReviewFileRelationInfoByBusinessId(HTReviewFileRelationBean paramBean);


    /**
     * 删除审核意见文件信息
     * @return 影响条数
     */
    Integer excDeleteReviewFileRelationInfoByBusinessId(HTReviewFileRelationBean paramBean);
}
