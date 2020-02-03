package com.hd.manager.service;


import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTTaskExecuteVO;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.util.Map;


/**
 * 任务执行 业务层 接口
 *
 * @author Dongzx
 * Created in 2019/8/13 10:48
 */
public interface HTTaskExecuteService extends BaseService {

    /**
     *  execl模板 赋值
     * @param paramVO 任务id
     * @return execl 文件
     */
    Workbook excGenerateTaskExecuteExeclInfo(HTTaskExecuteVO paramVO);

    /**
     * 查询A4
     *
     * @throws SystemException 不可预估的异常
     */
    HTTaskExecuteVO excSearchA4Info(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 查询A5信息
     * @param paramVO 检索条件
     * @return SystemException 不可预估的异常
     */
    HTTaskExecuteVO excSearchA5Info(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 添加招标工作进度计划A7信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excInsertA7ZbdlInfo(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 添加【招标部-执行任务(咨询记录A8)】信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excInsertA8ZbdlInfo(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 添加【A6】信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excInsertA5Info(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 添加【造价部-执行任务(修改完善A6)】信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excInsertA6Info(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 添加【招标部-执行任务(拟定招标规划A6)】信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excInsertA6ZbdlInfo(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 添加【造价部-执行任务(踏勘现场A8)】信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excInsertA8Info(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 查询招标工作进度计划A7信息
     *
     * @throws SystemException 不可预估的异常
     */
    HTTaskExecuteVO excSearchA7ZbdlInfo(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 检索【A8】信息
     *
     * @throws SystemException 不可预估的异常
     */
    HTTaskExecuteVO excSearchA8Info(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 检索【A6】信息
     *
     * @throws SystemException 不可预估的异常
     */
    HTTaskExecuteVO excSearchA6Info(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 添加编制成果文件A7信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excInsertA7ZJInfo(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 查询A7造价信息
     *
     * @throws SystemException 不可预估的异常
     */
    HTTaskExecuteVO excSearchA7ZJInfo(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 查询任务模板信息
     * @param paramVO 任务id
     * @throws SystemException 不可预估的异常
     */
    Map<String,Object> getTaskExecuteTemplateInfo(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 查询请款单模板信息
     * @param paramVO 请款单id
     * @throws SystemException 不可预估的异常
     */
    Map<String,Object> getRequestMenuTemplateInfo(HTTaskExecuteVO paramVO) throws SystemException;

    /**
     * 依据成果文件id 查询并生成 模板成果文件
     *
     * @param paramVO 成果信息
     * @param paramOS 输出流
     * @throws LogicException
     * @throws SystemException
     */
    void writeTaskExecuteWordStreamById(HTTaskExecuteVO paramVO, ByteArrayOutputStream paramOS) throws LogicException,SystemException;

    /**
     * 依据请款单id 查询并生成 模板请款单文件
     *
     * @param paramVO 请款单信息
     * @param paramOS 输出流
     * @throws LogicException
     * @throws SystemException
     */
    void writeRequestMenuWordStreamById(HTTaskExecuteVO paramVO, ByteArrayOutputStream paramOS) throws LogicException,SystemException;

}
