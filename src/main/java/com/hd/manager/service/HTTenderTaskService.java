package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 投标任务 service
 *
 * @author LH
 * Created in 2019/7/20 16:00
 */
public interface HTTenderTaskService extends BaseService {

    /**
     * 添加投标计划
     * @param paramVO
     * @throws LogicException
     * @throws SystemException
     */
    public void excInsertTenderTaskInfo(HTTenderTaskVO paramVO) throws LogicException, SystemException;

    /**
     * 查询投标计划列表信息
     * @param paramVO
     * @return 投标计划列表
     * @throws LogicException
     * @throws SystemException
     */
    public Map<String,Object> searchTenderTaskList (HTTenderTaskVO paramVO) throws LogicException, SystemException;


    /**
     * 修改投标计划
     * @param paramVO
     * @throws LogicException
     * @throws SystemException
     */
    public void updateTenderTask(HTTenderTaskVO paramVO) throws LogicException, SystemException;

    /**
     * 修改投标计划状态
     * @param paramVO
     * @throws LogicException
     * @throws SystemException
     */
    public void updateTenderTaskSts(HTTenderTaskVO paramVO) throws LogicException, SystemException;

    /**
     * 删除投标计划
     * @param paramVO
     * @throws LogicException
     * @throws SystemException
     */
    public void deleteTenderTask(HTTenderTaskVO paramVO) throws LogicException, SystemException;

    /**
     * 查询投标计划详情
     * @param paramVO
     * @return 投标计划详情
     * @throws LogicException
     * @throws SystemException
     */
    public HTTenderTaskVO excSearchTenderTaskInfoById(HTTenderTaskVO paramVO) throws LogicException, SystemException;

    /**
     * 根据阶段ID查询阶段关联
     * @param paramVO
     * @return 关联投标阶段数据
     * @throws LogicException
     * @throws SystemException
     */
    public List<HTTenderTaskPeriodMaterialVO> searchTenderTaskPeriodMaterialList(
            HTTenderTaskPeriodVO paramVO) throws LogicException, SystemException;

    /**
     * 查询文件类别列表
     * @param paramVO
     * @return 文件类别列表
     * @throws LogicException
     * @throws SystemException
     */
    public List<HTDictFileTypeVO> searchMaterialTypeList(HTDictFileTypeVO paramVO) throws LogicException, SystemException;

    /**
     * 添加文件信息
     * @param paramVO
     * @param fileList
     * @throws LogicException
     * @throws SystemException
     */
    public void saveTenderTaskPeriodMaterialInfo(HTTenderTaskPeriodMaterialVO paramVO,
                                          MultipartFile[] fileList) throws LogicException, SystemException;

    /**
     * 删除投标阶段文件信息
     * @param paramVO
     * @throws LogicException
     * @throws SystemException
     */
    public void deleteTenderTaskPeriodMaterialInfo(HTTenderTaskPeriodMaterialVO paramVO) throws LogicException, SystemException;

    /**
     * 投标任务相关所有文件类型以级相关文件类型的文件列表信息
     * @param paramVO
     * @return 相关文件类型的文件列表信息及文件类型
     * @throws LogicException
     * @throws SystemException
     */
    public List<ElementTreeVO> excSearchAllMaterialTypeAndMaterialList(HTTenderTaskVO paramVO) throws LogicException,
            SystemException;

    /**
     * 修改阶段文件信息提审状态
     * @param paramVO
     * @throws LogicException
     * @throws SystemException
     */
    public void excUpdateTenderTaskPeriodMaterialInfoIsReviewType(HTTenderTaskPeriodVO paramVO) throws LogicException,
            SystemException;

    /**
     * 查询投标任务以提审文件列表
     * @param paramVO
     * @return 以提审文件列表
     * @throws LogicException
     * @throws SystemException
     */
    public List<HTTenderTaskPeriodMaterialVO> excSearchIsReviewTenderTaskPeriodMaterialList(
            HTTenderTaskPeriodMaterialVO paramVO) throws LogicException, SystemException;

    /**
     * 撤回投标任务
     * @param paramVO
     * @throws LogicException
     * @throws SystemException
     */
    public void excRecallTask(HTTenderTaskVO paramVO) throws LogicException,
            SystemException;

    /**
     * 审核投标任务
     * @param paramVO
     * @throws LogicException
     * @throws SystemException
     */
    public void excAuditingHTTenderTask(HTTenderTaskVO paramVO) throws LogicException,
            SystemException;

    /**
     * 功能展示
     * @param paramVO
     * @return 当前登录人在当前投标状态下身份
     */
    public String excQueryShowBtnType(HTTenderTaskVO paramVO);

    /**
     * 查询投标任务投标阶段文件信息
     * @param paramVO
     * @return 投标任务投标阶段文件信息
     * @throws LogicException
     * @throws SystemException
     */
    public HTTenderTaskPeriodMaterialVO excSearchTenderStageMaterialByTenderTaskId(
            HTTenderTaskPeriodMaterialVO paramVO) throws LogicException, SystemException;

}
