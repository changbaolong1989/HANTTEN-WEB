package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTTenderTaskHisTaskInstBean;

import java.util.List;

/**
 * 投标任务历史流程任务实例表 DAO
 *
 * @author LH
 * Created in 2019/7/20 16:00
 */
public interface HTTenderTaskHisTaskInstDao extends BaseDao {

    //查询流程列表
    public List<HTTenderTaskHisTaskInstBean> excSearchHTTenderTaskHisTaskInstList(HTTenderTaskHisTaskInstBean paramBean);
    //查询当前投标任务进度
    public List<HTTenderTaskHisTaskInstBean> excSearchTaskKeysByTenderTaskId(HTTenderTaskHisTaskInstBean paramBean);
    //查询流程列表
    public List<HTTenderTaskHisTaskInstBean> excSearchHTTenderTaskHisTaskInstInfoList(HTTenderTaskHisTaskInstBean paramBean);


}
