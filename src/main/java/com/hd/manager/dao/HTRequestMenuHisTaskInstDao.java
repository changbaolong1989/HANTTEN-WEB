package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTRequestMenuHisTaskInstBean;

import java.util.List;

public interface HTRequestMenuHisTaskInstDao extends BaseDao {
    //查询流程列表
    public List<HTRequestMenuHisTaskInstBean> excSearchHTRequestMenuHisTaskInstInfoList(HTRequestMenuHisTaskInstBean paramBean);

}
