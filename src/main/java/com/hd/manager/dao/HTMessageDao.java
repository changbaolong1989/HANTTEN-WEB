package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTContractBean;
import com.hd.manager.dao.bean.HTMessageBean;
import java.util.List;

public interface HTMessageDao extends BaseDao {

    /**
     * 查询消息信息列表
     * @return 部门列表
     */
    List<HTMessageBean> searchMessageList(HTMessageBean paramBean);

    /**
     * 查询消息信息列表数据的总条数
     * @return 总条数
     */
    Integer searchMessageListCount(HTMessageBean paramBean);

    /**
     * 修改消息信息已读状态
     *
     */
    void updateMessageIsReadType(HTMessageBean paramBean);

    /**
     * 删除消息信息
     *
     */
    void deleteMessageInfo(HTMessageBean paramBean);

    /**
     * 查询消息集合中的最大创建时间的数据信息
     * @return 消息集合中的最大创建时间的数据信息
     */
    List<HTMessageBean> excSearchMessageListInfo(HTMessageBean paramBean);

    /**
     *  查询即将到期的请款信息
     * @param paramBean 查询参数
     * @return 即将到期的请款信息
     */
    List<HTContractBean> excSearchContractPredictRequestListInfoByCurrentDate(HTContractBean paramBean);

}
