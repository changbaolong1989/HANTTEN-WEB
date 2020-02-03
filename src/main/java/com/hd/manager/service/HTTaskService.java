package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

/**
 * 任务 业务层 接口
 *
 * @author jwl
 * Created in 2019/8/8 15:31
 */
public interface HTTaskService extends BaseService {

    /**
     * 功能展示
     */
    String excQueryShowBtnType(HTTaskPeriodVO paramVO) throws SystemException;

    /**
     * 添加落标通知书和任务关联表信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excInsertFailTenderNoticeRelationInfo(HTFailTenderNoticeRelationVO paramVO) throws SystemException;

    /**
     * 查询落标通知书和任务关联表详情
     *
     * @throws SystemException 不可预估的异常
     */
    HTFailTenderNoticeRelationVO excSearchFailTenderNoticeRelationInfoDetail(HTFailTenderNoticeRelationVO paramVO) throws SystemException;


    /**
     * 添加招标文件确认意见表和任务的关联表信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excInsertFileConfirmOpinionRelationInfo(HTFileConfirmOpinionRelationVO paramVO) throws SystemException;

    /**
     * 查询招标文件确认意见表和任务的关联表详情
     *
     * @throws SystemException 不可预估的异常
     */
    HTFileConfirmOpinionRelationVO excSearchFileConfirmOpinionRelationDetail(HTFileConfirmOpinionRelationVO paramVO) throws SystemException;

    /**
     * 添加专家论证意见表信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excInsertExpertOpinionRelationInfo(HTExpertOpinionRelationVO paramVO) throws SystemException;

    /**
     * 查询专家论证意见表详情
     *
     * @throws SystemException 不可预估的异常
     */
    HTExpertOpinionRelationVO excSearchExpertOpinionRelationDetail(HTExpertOpinionRelationVO paramVO) throws SystemException;

    /**
     * 任务阶段是否完成
     *
     * @throws SystemException 不可预估的异常
     */
    void updateIsFinish(HTTaskPeriodVO paramVO) throws SystemException;

    /**
     * 任务阶段是否忽略
     *
     * @throws SystemException 不可预估的异常
     */
    void updateIsIgnore(HTTaskPeriodVO paramVO) throws SystemException;

    /**
     * 项目监理归档信息列表
     *
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchArchiveListInfo(HTTaskArchiveVO paramVO) throws SystemException;

    /**
     * 通过任务id查询审核阶段文件
     */
    HTTaskPeriodMaterialVO excSearchMaterialByTaskPeriodId(HTTaskPeriodMaterialVO paramVO) throws SystemException;

    /**
     * 发起归档(归档阶段 项目监理部)
     */
    void excInitiateArchive(HTTaskVO paramVO) throws LogicException,SystemException;

    /**
     * 确认归档(归档阶段 项目监理部)
     */
    void excConfirmArchiveXM(HTTaskVO paramVO) throws LogicException,SystemException;

    /**
     * 根据阶段ID查询阶段关联文件(成果文件阶段)
     *
     * @return
     */
    HTTaskVO searchTaskMaterialInfo(HTTaskVO paramVO) throws SystemException;

    /**
     * 提交重新编辑申请
     */
    void excSubmitReedit(HTTaskReeditVO paramVO) throws LogicException,SystemException;

    /**
     * 查询重新编辑审核列表
     * @return 重新编辑审核列表
     */
    Map<String, Object> excSearchTaskReeditHisTaskInst(HTTaskPeriodVO paramVO) throws SystemException;

    /**
     * 签发(成果文件阶段 重新编辑申请)
     */
    void excReeditApplicationIssue(HTTaskReeditVO paramVO) throws LogicException,SystemException;

    /**
     * 审核任务(成果文件阶段 重新编辑申请)
     */
    void excAuditingOutcomeDocumentPeriodReeditApplication(HTTaskReeditVO paramVO) throws LogicException,SystemException;

    /**
     * 审核任务(成果文件阶段 归档文件)
     */
    void excAuditingTaskOutcomeDocumentPeriod(HTTaskVO paramVO) throws LogicException,SystemException;

    /**
     * 撤回任务(成果文件阶段)
     */
    void excRecallTaskOutcomeDocumentPeriod(HTTaskVO paramVO) throws LogicException,SystemException;

    /**
     * 提交审核(成果文件阶段)
     */
    void excSubmitAuditOutcomeDocumentPeriod(HTTaskVO paramVO, MultipartFile signPathFile,MultipartFile[] fileList) throws LogicException,SystemException;

    /**
     * 保存成果文件阶段信息
     */
    void excSaveDraftAuditOutcomeDocumentPeriod(HTTaskVO paramVO, MultipartFile signPathFile,MultipartFile[] fileList) throws LogicException,SystemException;

    /**
     * 转换数据格式为json
     */
    String mapConvertToJson(HTTaskVO paramVO) throws LogicException,SystemException;

    /**
     * 查询当前任务下的所有的相关资料文件列表
     */
    List<HTTaskPeriodMaterialVO> excSearchIsReviewTaskPeriodMaterialList(
            HTTaskPeriodMaterialVO paramVO) throws SystemException;

    /**
     * 查询相关资料文件列表(详情)
     */
    List<HTTaskPeriodMaterialVO> excSearchRelationMaterial(HTTaskPeriodMaterialVO paramVO) throws SystemException;

    /**
     * 根据阶段ID查询归档文件列表
     *
     * @return
     */
    List<HTTaskSaveMaterialVO> searchTaskSaveMaterialList(
            HTTaskSaveMaterialVO paramVO) throws SystemException;

    /**
     * 删除归档文件信息
     *
     */
    void excDeleteTaskSaveMaterialInfo(HTTaskSaveMaterialVO paramVO) throws SystemException;

    /**
     * 确认归档
     */
    void excConfirmArchive(HTTaskVO paramVO) throws LogicException,SystemException;

    /**
     * 提交归档
     */
    void excSubmitArchive(HTTaskVO paramVO,MultipartFile[] resourceFiles) throws LogicException,SystemException;

    /**
     * 查询任务阶段相关所有文件类型以级相关文件类型的文件
     */
    List<ElementTreeVO> excSearchAllMaterialTypeAndMaterialList(HTTaskPeriodVO paramVO) throws SystemException;

    /**
     * 通过任务id查询归档任务详情
     * @return 归档任务详情
     */
    HTTaskArchiveVO excSearchArchiveTaskDetailInfoByTaskId(HTTaskArchiveVO paramVO) throws SystemException;

    /**
     * 通过任务id查询任务详情
     * @return 任务详情
     */
    HTTaskVO excSearchTaskDetailInfoByTaskId(HTTaskPeriodVO paramVO) throws SystemException;

    /**
     * 通过任务阶段id查询任务详情
     * @return 任务详情
     */
    HTTaskVO excSearchTaskDetailInfoByRelationId(HTTaskVO paramVO) throws SystemException;

    /**
     * 通过任务阶段id查询任务阶段详情
     * @return 任务阶段详情
     */
    HTTaskPeriodVO excSearchTaskPeriodDetailInfoByRelationId(HTTaskPeriodVO paramVO) throws SystemException;

    /**
     * 查询审核意见列表
     * @return 审核意见信息
     */
    List<HTTaskPeriodVO> excSearchHTTaskHisTaskInst(HTTaskPeriodVO paramVO) throws SystemException;

    /**
     * 审核任务(A3阶段)
     */
    void auditingTaskA3Period(HTTaskPeriodVO paramVO) throws LogicException,SystemException;

    /**
     * 撤回任务(A3阶段)
     */
    void excRecallTaskA3Period(HTTaskPeriodVO paramVO) throws LogicException,SystemException;

    /**
     * 提交审核(A3阶段)
     */
    void excSubmitAuditA3Period(HTTaskPeriodVO paramVO) throws LogicException,SystemException;

    /**
     * 删除阶段文件信息
     *
     */
    void excDeleteTaskPeriodMaterialInfo(HTTaskPeriodMaterialVO paramVO) throws SystemException;

    /**
     * 删除成果文件信息
     *
     */
    void excDeleteResultFileMaterialInfo(HTTaskPeriodMaterialVO paramVO) throws SystemException;

    /**
     * 根据阶段ID查询阶段关联文件
     *
     * @return
     */
    List<HTTaskPeriodMaterialVO> searchTaskPeriodMaterialList(HTTaskPeriodMaterialVO paramVO) throws SystemException;

    /**
     * 根据任务阶段id查询项目编号、合同编号和成果文件编号
     * @return 项目编号、合同编号和成果文件编号
     */
    HTTaskVO excSearchProjectNumContractNumFileNumberByTaskPeriodId(HTTaskVO paramBean) throws SystemException;

    /**
     * 添加文件信息
     */
    void excInsertTaskPeriodMaterialInfo(HTTaskPeriodMaterialVO paramVO,MultipartFile[] file) throws LogicException, SystemException;

    /**
     * 修改工时列表中的备注
     *
     * @throws SystemException 不可预估的异常
     */
    void excUpdateRemarksInfo(HTTaskVO paramVO) throws SystemException;

    /**
     * 查询备注
     *
     * @throws SystemException 不可预估的异常
     */
    HTTaskVO excSearchRemarksByRelationId(HTTaskVO paramVO) throws SystemException;

    /**
     * 工时列表和总条数
     *
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchHourListInfo(HTTaskVO paramVO) throws SystemException;

    /**
     * 修改我的工时
     *
     * @throws SystemException 不可预估的异常
     */
    void excUpdateMyHourListInfo(HTTaskVO paramVO) throws SystemException,LogicException ;

    /**
     * 查询我的工时
     *
     * @throws SystemException 不可预估的异常
     */
    Map<String,Object> excSearchMyHourListInfo(HTTaskVO paramVO) throws SystemException;

    /**
     * 查询我的自定义工时
     *
     * @throws SystemException 不可预估的异常
     */
    Map<String,Object> excQueryCustomTimeList(HTTaskVO paramVO) throws SystemException;

    /**
     * 保存自定义工时
     */
    void excSaveCustomTime(HTUserCustomTimeVO userCustomTime) throws SystemException;

    /**
     * 查询任务内容
     * @return 任务内容
     */
    HTTaskVO excSearchTaskContent(HTTaskVO paramBean) throws SystemException;

    /**
     * 修改任务信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excUpdateTaskInfo(HTTaskVO paramVO) throws LogicException,SystemException;

    /**
     * 查询任务详情
     *
     * @throws SystemException 不可预估的异常
     */
    Map<String,Object> excSearchTaskInfo(HTTaskVO paramVO) throws SystemException;

    /**
     * 删除任务信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excDeleteTaskInfo(HTTaskVO paramVO) throws LogicException,SystemException;

    /**
     * 根据部门查人员列表
     *
     * @throws SystemException 不可预估的异常
     */
    List<HTUserVO> excSearchUserListByDepartmentId(HTTaskVO paramVO) throws SystemException;

    /**
     * 根据合同类型ID查询咨询类别状态列表
     *
     * @return 查询咨询类别状态列表
     * @throws SystemException 不可预估的异常
     */
    List<HTDictCounselTypeVO> excSearchDictCounselTypeListByContractTypeId(HTTaskVO paramVO) throws SystemException;

    /**
     * 根据部门ID查询合同类型列表
     *
     * @return 请求合同类型列表参数
     * @throws SystemException 不可预估的异常
     */
    List<HTDictContractTypeVO> excSearchDictContractTypeListByDepartmentId(HTTaskVO paramVO) throws SystemException;

    /**
     * 添加任务信息信息
     *
     * @throws SystemException 不可预估的异常
     */
    String excInsertTaskInfo(HTTaskVO paramVO) throws LogicException,SystemException;

    /**
     * 查询人员信息
     *
     * @throws SystemException 不可预估的异常
     */
    List<HTTaskVO> excSearchUserInfo(HTTaskVO paramVO) throws SystemException;

    /**
     * 查询拟定工作计划信息
     * @param paramVO 检索条件
     * @return SystemException 不可预估的异常
     */
    HTTableFiveRelationVO excSearchTaskWorkPlanInfo(HTTableFiveRelationVO paramVO) throws SystemException;

    /**
     * 添加拟定工作计划信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excInsertTaskWorkPlan(HTTableFiveRelationVO paramVO) throws SystemException;

    /**
     * 查询编制成果文件-A2信息
     * @param paramVO 检索条件
     * @return SystemException 不可预估的异常
     */
    HTTableTwoRelationVO excSearchTaskCompileResultsFileA2(HTTableTwoRelationVO paramVO) throws SystemException;

    /**
     * 添加编制成果文件-A2信息
     * @throws SystemException 不可预估的异常
     */
    void excInsertTaskCompileResultsFileA2(HTTableTwoRelationVO paramVO) throws SystemException;

    /**
     * 添加开标、评标信息
     * @throws SystemException 不可预估的异常
     */
    void excInsertOpenBidInfo(HTOpenBidVO paramVO)throws SystemException,LogicException;

    /**
     * 查询开标、评标信息详情
     * @param paramVO 检索条件
     * @return SystemException 不可预估的异常
     */
    HTOpenBidVO excSearchOpenBidInfoDetail(HTOpenBidVO paramVO)throws SystemException;
}
