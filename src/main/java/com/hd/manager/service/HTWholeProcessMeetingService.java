package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTWholeProcessMeetingVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 全过程（会议纪要） 业务层
 *
 * @author zjy
 * Created in 2019/9/9 11:27
 */
public interface HTWholeProcessMeetingService extends BaseService {

    /**
     * 查询列表
     *
     * @return 列表信息
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchMeetingPeriodList(HTWholeProcessMeetingVO paramVO) throws SystemException;

    /**
     * 查询详情
     *
     * @return 发出信函登记表 详情
     * @throws SystemException 不可预估的异常
     */
    HTWholeProcessMeetingVO excSearchProjectMeetingPeriodInfo(HTWholeProcessMeetingVO paramVO) throws SystemException;

    /**
     * 新增信息
     *
     * @param paramVO 新增信息
     * @throws SystemException 不可预估的异常
     */
    void excSaveWholeProcessMeeting(HTWholeProcessMeetingVO paramVO, MultipartFile file) throws LogicException, SystemException;

    /**
     * 修改信息
     *
     * @param paramVO 新增信息
     * @throws SystemException 不可预估的异常
     */
    void excUpdateWholeProcessMeeting(HTWholeProcessMeetingVO paramVO, MultipartFile file) throws LogicException, SystemException;

    /**
     * 查询信息（导出）
     *
     * @param paramVO 参数
     * @throws SystemException 不可预估的异常
     */
    List<HTWholeProcessMeetingVO> excSearchProjectMeetingPeriodListForExport(HTWholeProcessMeetingVO paramVO) throws SystemException;
}
