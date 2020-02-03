package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.MessageConstant;
import com.hd.manager.dao.HTTenderTaskHisTaskInstDao;
import com.hd.manager.dao.bean.HTTenderTaskHisTaskInstBean;
import com.hd.manager.dao.bean.HTUserQuestionRecordRelationBean;
import com.hd.manager.service.HTTenderTaskHisTaskInstService;
import com.hd.manager.vo.HTTenderTaskHisTaskInstVO;
import com.hd.manager.vo.HTUserQuestionRecordRelationVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class HTTenderTaskHisTaskInstServiceImpl extends BaseServiceImpl implements HTTenderTaskHisTaskInstService {


    @Resource
    private HTTenderTaskHisTaskInstDao htTenderTaskHisTaskInstDao;

    /**
     * 查询审核意见信息
     * @return 审核意见信息
     */
    @Override
    public List<HTTenderTaskHisTaskInstVO> excSearchHTTenderTaskHisTaskInst(HTTenderTaskHisTaskInstVO paramVO) throws LogicException, SystemException {
        try {

            HTTenderTaskHisTaskInstBean paramBean = new HTTenderTaskHisTaskInstBean();
            paramBean.setTenderTaskId(paramVO.getTenderTaskId());
            //查询审核意见信息
            List<HTTenderTaskHisTaskInstBean> htTenderTaskHisTaskInstBeanList = htTenderTaskHisTaskInstDao.excSearchHTTenderTaskHisTaskInstInfoList(paramBean);
            List<HTTenderTaskHisTaskInstVO> resultList = new ArrayList<>();
            /*格式转换*/
            for (HTTenderTaskHisTaskInstBean htTenderTaskHisTaskInstBean:htTenderTaskHisTaskInstBeanList){
                HTTenderTaskHisTaskInstVO htTenderTaskHisTaskInstVO = new HTTenderTaskHisTaskInstVO();

                //任务Key
                htTenderTaskHisTaskInstVO.setTaskKey(htTenderTaskHisTaskInstBean.getTaskKey());
                //唯一标识
                htTenderTaskHisTaskInstVO.setHistoryId(htTenderTaskHisTaskInstBean.getHistoryId());
                //投标任务ID
                htTenderTaskHisTaskInstVO.setTenderTaskId(htTenderTaskHisTaskInstBean.getTenderTaskId());
                //审批人
                htTenderTaskHisTaskInstVO.setApproveUserId(htTenderTaskHisTaskInstBean.getApproveUserId());
                //审批人名称
                htTenderTaskHisTaskInstVO.setApproveUserName(htTenderTaskHisTaskInstBean.getApproveUserName());
                List<HTTenderTaskHisTaskInstBean> childHisTaskInstBeanList = htTenderTaskHisTaskInstBean.getHTTenderTaskHisTaskInstBeanList();
                List<HTTenderTaskHisTaskInstVO> childHisTaskInstVOList = new ArrayList<>();
                for (HTTenderTaskHisTaskInstBean childHisTaskInstBean:childHisTaskInstBeanList){
                    HTTenderTaskHisTaskInstVO childHisTaskInstVO = new HTTenderTaskHisTaskInstVO();
                    //任务Key
                    childHisTaskInstVO.setTaskKey(childHisTaskInstBean.getTaskKey());
                    //唯一标识
                    childHisTaskInstVO.setHistoryId(childHisTaskInstBean.getHistoryId());
                    //投标任务ID
                    childHisTaskInstVO.setTenderTaskId(childHisTaskInstBean.getTenderTaskId());
                    //拒绝原因
                    childHisTaskInstVO.setRejectReason(childHisTaskInstBean.getRejectReason());
                    //审批人
                    childHisTaskInstVO.setApproveUserId(childHisTaskInstBean.getApproveUserId());
                    //审批人签名路径
                    childHisTaskInstVO.setSignPath(childHisTaskInstBean.getSignPath());
                    //审批人名称
                    childHisTaskInstVO.setApproveUserName(childHisTaskInstBean.getApproveUserName());
                    //审批时间
                    childHisTaskInstVO.setApproveTime(DateToolUtils.convertDateFormat(childHisTaskInstBean.getApproveTime(),DateToolUtils.yyyy_MM_dd));
                    //审批人名称
                    childHisTaskInstVO.setIsPass(childHisTaskInstBean.getIsPass());
                    //成员问题关联数据
                    List<HTUserQuestionRecordRelationBean> htUserQuestionRecordRelationBeanList = childHisTaskInstBean.getHtUserQuestionRecordRelationBeanList();
                    List<HTUserQuestionRecordRelationVO> htUserQuestionRecordRelationVOList = new ArrayList<>();
                    for (HTUserQuestionRecordRelationBean htUserQuestionRecordRelationBean:htUserQuestionRecordRelationBeanList){
                        HTUserQuestionRecordRelationVO htUserQuestionRecordRelationVO = new HTUserQuestionRecordRelationVO();
                        //唯一标识
                        htUserQuestionRecordRelationVO.setRelationId(htUserQuestionRecordRelationBean.getRelationId());
                        //审核记录ID
                        htUserQuestionRecordRelationVO.setRecordId(htUserQuestionRecordRelationBean.getRecordId());
                        //问题类型ID
                        htUserQuestionRecordRelationVO.setQuestionId(htUserQuestionRecordRelationBean.getQuestionId());
                        //问题名称
                        htUserQuestionRecordRelationVO.setQuestionName(htUserQuestionRecordRelationBean.getQuestionName());
                        //错误次数
                        htUserQuestionRecordRelationVO.setQuestionNum(htUserQuestionRecordRelationBean.getQuestionNum());
                        //来源类型（0：合同 1：普通任务 2：投标任务 ）
                        htUserQuestionRecordRelationVO.setSourceType(htUserQuestionRecordRelationBean.getSourceType());
                        //人员ID
                        htUserQuestionRecordRelationVO.setUserId(htUserQuestionRecordRelationBean.getUserId());
                        //人员名称
                        htUserQuestionRecordRelationVO.setUserName(htUserQuestionRecordRelationBean.getUserName());
                        htUserQuestionRecordRelationVOList.add(htUserQuestionRecordRelationVO);
                    }
                    childHisTaskInstVO.setHtUserQuestionRecordRelationVOList(htUserQuestionRecordRelationVOList);
                    childHisTaskInstVOList.add(childHisTaskInstVO);
                }
                htTenderTaskHisTaskInstVO.setHTTenderTaskHisTaskInstVOList(childHisTaskInstVOList);
                resultList.add(htTenderTaskHisTaskInstVO);
            }
            return resultList;
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex,MessageConstant.MSG_ERROR_COMMON_0002);
        }

    }
}
