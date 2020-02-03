package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.entity.TbMessageEntity;
import com.hd.common.service.CommonService;
import com.hd.common.util.*;
import com.hd.manager.dao.HTMessageDao;
import com.hd.manager.dao.HTUserDao;
import com.hd.manager.dao.bean.HTContractBean;
import com.hd.manager.dao.bean.HTMessageBean;
import com.hd.manager.dao.bean.HTUserBean;
import com.hd.manager.service.HTMessageService;
import com.hd.manager.vo.HTMessageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;

@Service
public class HTMessageServiceImpl extends BaseServiceImpl implements HTMessageService {

    /**
     * 消息 DAO层
     */
    @Resource
    private HTMessageDao htMessageDao;

    /**
     * 消息mapper
     */
    @Resource
    private CommonService commonService;

    /**
     * 用户dao
     */
    @Resource
    private HTUserDao htUserDao;

    /**
     * 查询消息列表
     * @return 消息列表
     */
    @Override
    public Map<String, Object> searchMessageList(HTMessageVO paramVO) throws LogicException, SystemException {

        try {
            Map<String,Object> resultMap = new HashMap<>();
            HTMessageBean paramBean = new HTMessageBean();
            //接收消息人
            paramBean.setAcceptUserId(paramVO.getLoginUserInfo().getLoginUserId());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //查询消息列表
            List<HTMessageBean> messageBeanList = htMessageDao.searchMessageList(paramBean);
            Integer count = htMessageDao.searchMessageListCount(paramBean);
            List<HTMessageVO> messageVOList = new ArrayList<>();
            //格式转换
            for (HTMessageBean htMessageBean:messageBeanList){
                HTMessageVO htMessageVO = new HTMessageVO();
                //信息ID
                htMessageVO.setMessageId(htMessageBean.getMessageId());
                //消息内容
                htMessageVO.setMessageContent(htMessageBean.getMessageContent());
                //接收消息人
                htMessageVO.setAcceptUserId(htMessageBean.getAcceptUserId());
                //创建时间
                htMessageVO.setCreateDate(DateToolUtils.convertDateFormat(htMessageBean.getCreateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                //已读状态
                htMessageVO.setIsRead(htMessageBean.getIsRead());
                messageVOList.add(htMessageVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY,messageVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 修改消息信息已读状态
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMessageIsReadType(HTMessageVO paramVO) throws LogicException, SystemException {
        try {
            HTMessageBean paramBean = new HTMessageBean();
            //信息ID
            paramBean.setMessageId(paramVO.getMessageId());
            Date currentDate = new Date();
            //修改日期
            paramBean.setUpdateDate(currentDate);
            //用户ID
            paramBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            htMessageDao.updateMessageIsReadType(paramBean);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }



    /**
     * 删除消息信息
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMessageInfo(HTMessageVO paramVO) throws LogicException, SystemException {
        try {
            HTMessageBean paramBean = new HTMessageBean();
            //信息ID
            paramBean.setMessageId(paramVO.getMessageId());
            Date currentDate = new Date();
            //修改日期
            paramBean.setUpdateDate(currentDate);
            //用户ID
            paramBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            htMessageDao.deleteMessageInfo(paramBean);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询消息集合中的最大创建时间的数据信息
     * @return 消息集合中的最大创建时间的数据信息
     */
    @Override
    @Transactional(readOnly = true)
    public HTMessageVO excSearchMessageDetailInfo(HTMessageVO paramVO) throws LogicException, SystemException {

        HTMessageVO htMessageVO = new HTMessageVO();

        try {

            HTMessageBean htMessageBean = new HTMessageBean();

            // 设置登录人id
            htMessageBean.setAcceptUserId(paramVO.getLoginUserInfo().getLoginUserId());

            //查询消息列表
            List<HTMessageBean> resultBeanList = htMessageDao.excSearchMessageListInfo(htMessageBean);

            //格式转换
            if (!ListToolUtils.isEmpty(resultBeanList)){

                //创建时间
                htMessageVO.setCreateDate(DateToolUtils.convertDateFormat(
                        resultBeanList.get(0).getCreateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            }

        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }

        return htMessageVO;

    }

    /**
     * 添加消息数据
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public int excInsertMessageInfo() throws SystemException {

        // 设置变量添加消息的条数
        int resultInsertCount = NumberToolUtils.INT_ZERO;

        try {

            Date currentDate = getCurrentDate();

            HTContractBean htContractBean = new HTContractBean();

            // 设置当前时间
            htContractBean.setCurrentDate(currentDate);

            // 查询即将到期的请款信息
            List<HTContractBean> resultBeanList = htMessageDao.excSearchContractPredictRequestListInfoByCurrentDate(htContractBean);

            resultInsertCount = resultBeanList.size();

            //格式转换
            if (!ListToolUtils.isEmpty(resultBeanList)){

                // token ""
                String token = StringToolUtils.STRING_EMPTY;

                // 查询用户信息，目的是获取token
                HTUserBean htUserBean = htUserDao.excSearchAccessToken();

                // 如果查询到的用户不为空
                if(null != htUserBean){
                    // 设置token
                    token = StringToolUtils.convertNullObjectToString(htUserBean.getAccessToken());
                }

                // 如果没有token，则获取一个token，并存入数据库
                if(StringToolUtils.STRING_EMPTY.equals(token)){

                    // 获取token
                    token = commonService.createToken();

                } else {

                    // 是否有效
                    boolean isValid = (getCurrentDate().getTime() - htUserBean.getSaveAccessTokenDate().getTime()) < 1000*60*60*2;

                    // 如果失效，则重新创建token
                    if(!isValid){

                        // 获取token
                        token = commonService.createToken();

                    }

                }


                // BeanList -> VOList
                for (HTContractBean tempBean : resultBeanList){

                    // ================================================ 插入消息信息 start =====================================================

                    TbMessageEntity tbMessageEntity = new TbMessageEntity();

                    String tempMessageContentStr = "" +
                            tempBean.getProjectNum() + " 项目 " +
                            tempBean.getProjectNum() + "-" + tempBean.getContractNum() + " 合同 预计 " +
                            DateToolUtils.convertDateFormat(tempBean.getStartDate(),DateToolUtils.yyyy_MM_dd) + " 日进行请款，请款金额为 " +
                            NumberToolUtils.convertNumberToFormatStringWithHalfUp(tempBean.getReceivedAmount(),NumberToolUtils.INT_SCALE_FLOAT) + " 元。";

                    // 设置消息内容
                    tbMessageEntity.setMessageContent(tempMessageContentStr);

                    // 设置接收消息人
                    tbMessageEntity.setAcceptUserId(tempBean.getCreateUserId());

                    // 设置是否阅读 0：否    1：是
                    tbMessageEntity.setIsRead(StringToolUtils.STRING_ZERO);

                    // 设置创建时间
                    tbMessageEntity.setCreateDate(currentDate);

                    // 设置修改时间
                    tbMessageEntity.setUpdateDate(currentDate);

                    // 设置是否删除  0：否    1：是
                    tbMessageEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);

                    // 插入消息信息
                    commonService.excInsertMessageInfo(tbMessageEntity);

                    // 微信公众号推送模板消息
                    // loginUserId 当前登录用户id
                    // openId 用户唯一标识
                    // acceptUserName 接收人名称
                    // messageContent 消息内容
                    commonService.sendTemplateMessageToWeChatV2(token
                            ,tempBean.getOpenId(),tempBean.getApplyUserName(),tempMessageContentStr);

                    // ================================================ 插入消息信息 end =====================================================

                }

            }

        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }

        return resultInsertCount;

    }

}
