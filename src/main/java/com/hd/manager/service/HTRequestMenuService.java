package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTRequestMenuHisTaskInstVO;
import com.hd.manager.vo.HTRequestMenuVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 请款单Service
 *
 * @author lihai
 * Created in 2019/8/1 14:45
 **/
public interface HTRequestMenuService extends BaseService {

    /**
     * 添加请款单信息
     */
    String excInsertRequestMenuInfo(HTRequestMenuVO paramVO) throws LogicException,SystemException;

    /**
     * 修改请款单名称
     */
    void excUpdateRequestMenuName(HTRequestMenuVO paramVO) throws LogicException,SystemException;

    /**
     * 修改请款单信息
     */
    void excUpdateRequestMenuInfo(HTRequestMenuVO paramVO,MultipartFile file,MultipartFile demoFile) throws LogicException,SystemException;

    /**
     * 转换数据格式为json
     */
    String mapConvertToJson(HTRequestMenuVO paramVO) throws LogicException,SystemException;

    /**
     * 查询请款单信息
     */
    HTRequestMenuVO excSearchRequestMenuInfo(HTRequestMenuVO paramVO) throws LogicException,SystemException;

    /**
     * 删除请款单信息
     */
    void excDeleteRequestMenuInfo(HTRequestMenuVO paramVO) throws LogicException,SystemException;

    /**
     * 请款单撤回
     */
    void excRecallQuestionMenu(HTRequestMenuVO paramVO) throws LogicException,SystemException;


    /**
     * 请款单审核
     */
    void excAuditingQuestionMenu(HTRequestMenuVO paramVO) throws LogicException,SystemException;

    /**
     * 财务开具发票
     */
    void excUploadInvoiceFile(HTRequestMenuVO paramVO , MultipartFile file) throws LogicException,SystemException;

    /**
     * 上传发票
     */
    void excUploadBill(HTRequestMenuVO paramVO , MultipartFile file) throws LogicException,SystemException;

    /**
     * 上传发票申请单附件
     */
    HTRequestMenuVO excUploadInvoiceApplyFile(HTRequestMenuVO paramVO , MultipartFile[] files) throws LogicException,SystemException;

    /**
     * 删除发票申请单附件
     */
    void excDeleteInvoiceApplyFile(HTRequestMenuVO paramVO) throws LogicException,SystemException;

    /**
     * 提交发票
     */
    void excCommitBillStep(HTRequestMenuVO paramVO)  throws LogicException,SystemException;

    /**
     * 上传打款凭证
     */
    void excUploadPaymentVoucher(HTRequestMenuVO paramVO, MultipartFile file) throws LogicException,SystemException;

    /**
     * 确认到款
     */
    void excConfirmVoucherStep(HTRequestMenuVO paramVO)  throws LogicException,SystemException;

    /**
     * 查询审核意见信息
     * @return 审核意见信息
     */
    List<HTRequestMenuHisTaskInstVO> excSearchHTRequestMenuHisTaskInst(HTRequestMenuHisTaskInstVO paramVO) throws LogicException, SystemException;

    /**
     * 查询当前人类别(用以展示按钮)
     *  @return 审核意见信息
     */
    String excShowUserType(HTRequestMenuVO paramVO) throws LogicException,SystemException;
}
