package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTCompanyVO;
import com.hd.manager.vo.HTPublicNoticeVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 设置-公告管理 业务层 接口
 *
 * @author jwl
 * Created in 2019/7/3 15:31
 */
public interface HTPublicNoticeService extends BaseService {

    /**
     * 查询公告列表数据
     *
     * @return 公告列表数据
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchPublicNoticeList(HTPublicNoticeVO paramVO) throws SystemException;

    /**
     * 添加公告信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excSavePublicNoticeInfo(HTPublicNoticeVO paramVO, MultipartFile signPathFile) throws LogicException, SystemException;

    /**
     * 查询公告详细信息
     *
     * @throws SystemException 不可预估的异常
     */
    HTPublicNoticeVO excSearchPublicNoticeInfo(HTPublicNoticeVO paramVO) throws SystemException;

    /**
     * 更改公告信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excUpdatePublicNoticeInfo(HTPublicNoticeVO paramVO, MultipartFile signPathFile) throws LogicException, SystemException;

    /**
     * 删除公告信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excDeletePublicNoticeInfo(HTPublicNoticeVO paramVO) throws SystemException;
}
