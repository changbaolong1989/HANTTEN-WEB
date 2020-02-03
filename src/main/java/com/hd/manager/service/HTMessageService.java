package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTMessageVO;
import java.util.Map;

public interface HTMessageService extends BaseService {

    /**
     * 查询消息列表
     * @return 部门列表
     */
    public Map<String,Object> searchMessageList(HTMessageVO paramVO) throws LogicException, SystemException;

    /**
     * 修改消息信息已读状态
     *
     */
    public void updateMessageIsReadType(HTMessageVO paramVO) throws LogicException, SystemException;

    /**
     * 删除消息信息
     *
     */
    public void deleteMessageInfo(HTMessageVO paramVO) throws LogicException, SystemException;

    /**
     * 查询消息集合中的最大创建时间的数据信息
     * @return 消息集合中的最大创建时间的数据信息
     */
    HTMessageVO excSearchMessageDetailInfo(HTMessageVO paramVO) throws SystemException;

    /**
     * 添加消息数据
     * @throws SystemException 不可预估的异常
     */
    int excInsertMessageInfo() throws SystemException;

}
