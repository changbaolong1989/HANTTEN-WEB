package com.hd.common.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.common.entity.TbMessageEntity;
import com.hd.manager.dao.bean.HTTaskBean;
import com.hd.manager.vo.HTDictQuestionVO;
import com.hd.manager.vo.HTReviewFileRelationVO;
import com.hd.manager.vo.HTUserVO;
import java.util.List;
import java.util.Map;

/**
 * 共通Service
 * @author JLF
 */
public interface CommonService extends BaseService {

    /**
     * 不重新获取token，直接用转入的参数token，去推送消息
     * @param token
     * @param openId 用户唯一标识
     * @param acceptUserName 接收人名称
     * @param messageContent 消息内容
     */
    void sendTemplateMessageToWeChatV2(String token,String openId,String acceptUserName,String messageContent) throws SystemException;

    /**
     * 微信公众号推送模板消息
     * @param loginUserId
     * @param openId 用户唯一标识
     * @param acceptUserName 接收人名称
     * @param messageContent 消息内容
     * @throws SystemException
     */
    void sendTemplateMessageToWeChat(String loginUserId,String openId,String acceptUserName,String messageContent) throws SystemException;

    /**
     * 创建token
     * @return string
     * @throws SystemException
     */
    String createToken() throws SystemException;

    /**
     * 为消息中心查询用户id
     * @param submitNode
     * @param departmentId
     * @return
     * @throws SystemException
     */
    HTTaskBean excSearchUserIdForMessage(String submitNode,String departmentId) throws SystemException;

    /**
     * 根据部门查人员列表
     *
     * @param paramVO
     * @return
     */
    List<HTUserVO> excSearchUserListByDepartmentId(HTUserVO paramVO) throws SystemException;

    /**
     * 通过部门id查询问题类型
     * @return 问题类型
     */
    List<HTDictQuestionVO> excSearchQuestionTypeByDeptId(HTDictQuestionVO paramVO) throws SystemException;

    /**
     * 审核记录和人员和问题类型关联表
     */
    void excInsertUserQuestionRecordRelations(HTDictQuestionVO paramVO) throws SystemException;

    /**
     * 批量查询离职人员
     */
    Integer excSearchQuitUserByIds(List<String> userIds);

    /**
     * 插入并保存批注文件数据，并保存批注文件
     *
     * @param paramJsonStr 回调参数
     * @return 回调响应
     */
    Map<String,Object> excInsertReviewFile(String paramJsonStr);

    /**
     * 查询审核意见文件信息
     *
     * @param paramVO 回调参数
     * @return 回调响应
     */
    List<HTReviewFileRelationVO> excSearchReviewFileRelationList(HTReviewFileRelationVO paramVO);

    /**
     * 插入消息信息
     *
     * @param tbMessageEntity 消息信息
     */
    void excInsertMessageInfo(TbMessageEntity tbMessageEntity) throws SystemException ;

    /**
     * 判断项目是否结束
     */
    void excIsOverProject(String projectId) throws LogicException,SystemException ;

    /**
     * 判断合同是否作废
     */
    void excIsInvalidContract(String projectId) throws LogicException,SystemException ;

    /**
     * 判断项目是否作废
     */
    void excIsInvalidProject(String projectId) throws LogicException,SystemException ;

}
