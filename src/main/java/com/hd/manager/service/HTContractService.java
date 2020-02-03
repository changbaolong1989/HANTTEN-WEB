package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTContractTaskHisTaskInstVO;
import com.hd.manager.vo.HTContractVO;
import com.hd.manager.vo.HTProjectVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * 设置-合同 业务层 接口
 *
 * @author jwl
 * Created in 2019/7/17 15:31
 */
public interface HTContractService extends BaseService {

    /**
     * 转换数据格式为json
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    String mapConvertToJson(HTContractVO paramVO) throws LogicException, SystemException;

    /**
     * 查询合同详细信息
     * @return 合同详细信息
     * @throws SystemException 不可预估的异常
     */
    HTContractVO excSearchContractDetailInfo(HTContractVO paramVO) throws SystemException;

    /**
     * 修改合同信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void updateContractInfo(HTContractVO paramVO, MultipartFile signPathFile) throws LogicException, SystemException;

    /**
     * 添加和修改项目基本情况信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    String updateBaseContractInfo(HTContractVO paramVO) throws SystemException;

    /**
     * 查询项目基本情况信息
     * @return 合同详细信息
     * @throws SystemException 不可预估的异常
     */
    HTContractVO excSearchBaseContractDetailInfo(HTContractVO paramVO) throws SystemException;

    /**
     *  查询合同详细信息
     * @param paramVO 合同id
     * @return 合同信息
     */
    Map<String,Object> getContractDetailInfoByContractId(HTContractVO paramVO);

    /**
     * 查询合同意见信息
     * @return 审核合同意见信息
     */
    List<HTContractTaskHisTaskInstVO> excSearchHTContractTaskHisTaskInst(HTContractTaskHisTaskInstVO paramVO) throws LogicException, SystemException;

    /**
     * 审核合同
     * @param paramVO 合同信息
     * @throws SystemException 系统异常
     */
    void excAuditingHTContract(HTContractVO paramVO) throws SystemException,LogicException;

    /**
     *  查询合同信息
     * @param paramVO 合同id
     * @return 合同信息
     */
    HTContractVO excSearchContractDetailInfoById(HTContractVO paramVO);

    /**
     *  合同确认盖章
     * @param paramVO 合同id
     */
    void excContractConfirmSeal(HTContractVO paramVO)throws  LogicException,SystemException;

    /**
     *  合同确认归档
     * @param paramVO 合同id
     */
    void excContractSubmitArchive(HTContractVO paramVO)throws LogicException,SystemException;


    /**
     * 添加归档文件信息
     */
    void excInsertContractArchiveFileInfo(
            HTContractVO paramVO, MultipartFile[] file) throws SystemException;

    /**
     *  删除归档文件
     * @param paramVO 文件路径
     */
    void excDeleteContractArchiveFileInfo(HTContractVO paramVO);

    /**
     *  查询归档文件集合
     * @param paramVO 合同id
     * @return 归档文件集合
     */
    List<HTContractVO> excSearchContractArchiveFileListList(HTContractVO paramVO);

    /**
     * 查询合同到达哪一步了
     * @param paramVO 合同id
     * @throws SystemException 系统异常
     */
    String excGetHTContractSts(HTContractVO paramVO) throws SystemException;

    /**
     *  撤回
     * @param paramVO 合同id
     */
    void excRevokeContractInfo(HTContractVO paramVO) throws LogicException,SystemException;

    /**
     *  查询合同列表信息(供法务使用)
     * @param paramVO 合同编码
     */
    Map<String,Object> excSearchContractList(HTContractVO paramVO) throws LogicException,SystemException;


    /**
     *  添加法务信息
     * @param paramVO 合同编码
     */
    void excInsertLegalOpinion(HTContractVO paramVO) throws LogicException,SystemException;

    /**
     *  查询合同对应的法务信息
     * @param paramVO 合同id
     * @return 合同信息
     */
    public HTContractVO excSearchLegalOpinionInfoById(HTContractVO paramVO)  throws LogicException, SystemException;


    /**
     * 依据合同id 查询并生成 模板合同文件
     *
     * @param paramVO 合同信息
     * @param paramOS 输出流
     * @throws LogicException
     * @throws SystemException
     */
    void writeContractWordStreamById(HTContractVO paramVO, ByteArrayOutputStream paramOS) throws LogicException,SystemException;


    /**
     * 合同作废
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excInvalidContract(HTContractVO paramVO) throws LogicException, SystemException ;

}
