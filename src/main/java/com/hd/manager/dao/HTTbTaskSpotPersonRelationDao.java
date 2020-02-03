package com.hd.manager.dao;

import com.hd.manager.dao.bean.TbTaskSpotPersonRelationBean;

import java.util.List;

public interface HTTbTaskSpotPersonRelationDao {

    List<TbTaskSpotPersonRelationBean> searchList(TbTaskSpotPersonRelationBean paramBean);

    /**
     * 查询消息信息列表数据的总条数
     * @return 总条数
     */
    Integer searchCount(TbTaskSpotPersonRelationBean paramBean);
}
