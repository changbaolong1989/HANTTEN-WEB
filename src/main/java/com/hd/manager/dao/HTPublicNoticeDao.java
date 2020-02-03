package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.common.entity.TbCompanyEntity;
import com.hd.common.entity.TbPublicNoticeEntity;
import com.hd.manager.dao.bean.HTCompanyBean;
import com.hd.manager.dao.bean.HTPublicNoticeBean;

import java.util.List;

/**
 * 设置-公告管理 持久层接口
 *
 * @author jwl
 * Created in 2019/7/3 15:31
 */
public interface HTPublicNoticeDao extends BaseDao {

    /**
     * 查询公告列表数据的总条数
     * @return 公告列表数据
     */
    Integer excSearchPublicNoticeListCount(HTPublicNoticeBean paramBean);

    /**
     * 查询公告列表数据
     * @return 公告列表数据
     */
    List<HTPublicNoticeBean> excSearchPublicNoticeList(HTPublicNoticeBean paramBean);

    /**
     * 添加公告信息
     */
    void excInsertPublicNoticeInfo(TbPublicNoticeEntity paramBean);

    /**
     * 查询公告详情信息
     * @return
     */
    HTPublicNoticeBean excSearchPublicNoticeInfo(HTPublicNoticeBean paramBean);

}
