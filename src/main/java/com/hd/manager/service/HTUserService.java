package com.hd.manager.service;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.BaseService;
import com.hd.manager.vo.HTUserVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 设置-用户管理 业务层 接口
 *
 * @author zjy
 * Created in 2019/7/1 17:08
 */
public interface HTUserService extends BaseService {

    /**
     * 添加openid
     * @return
     * @throws SystemException
     */
    void excUpdateOpenIdByUserId(HTUserVO paramVO) throws SystemException;

    /**
     * 查询用户详情信息
     * @return
     * @throws SystemException
     */
    HTUserVO excSearchUserDetailInfo(HTUserVO paramVO) throws SystemException;

    /**
     * 查询公司和合作公司的信息下拉列表
     *
     * @return 公司和合作公司的信息下拉列表
     * @throws SystemException 不可预估的异常
     */
    List<HTUserVO> excSearchCompanyList() throws SystemException;

    /**
     * 查询角色权限信息下拉列表
     *
     * @return 角色权限信息下拉列表
     * @throws SystemException 不可预估的异常
     */
    List<HTUserVO> excSearchRoleList() throws SystemException;

    /**
     * 查询专业下拉列表
     *
     * @return 专业下拉列表
     * @throws SystemException 不可预估的异常
     */
    List<HTUserVO> excSearchProfessionalList() throws SystemException;

    /**
     * 查询文化程度下拉列表
     *
     * @return 文化程度下拉列表
     * @throws SystemException 不可预估的异常
     */
    List<HTUserVO> excSearchEducationList() throws SystemException;

    /**
     * 查询部门信息下拉列表
     *
     * @return 部门信息下拉列表
     * @throws SystemException 不可预估的异常
     */
    List<HTUserVO> excSearchDeptTree() throws SystemException;

    /**
     * 根据部门ID联动职位信息
     *
     * @return 职位下拉列表
     * @throws SystemException 不可预估的异常
     */
    List<HTUserVO> excSearchJobListByDeptId(HTUserVO paramVO) throws SystemException;

    /**
     * 查询用户列表
     *
     * @return 用户列表
     * @throws SystemException 不可预估的异常
     */
    Map<String, Object> excSearchUserList(HTUserVO paramVO) throws SystemException;

    /**
     * 添加用户信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    void excSaveUserInfo(HTUserVO paramVO, MultipartFile[] resourceFiles,MultipartFile signPathFile) throws LogicException, SystemException;

    /**
     * 查询用户详细信息
     *
     * @throws SystemException 不可预估的异常
     */
    HTUserVO excSearchUserInfo(HTUserVO paramVO) throws SystemException;

    /**
     * 更改用户信息
     *
     * @throws SystemException 不可预估的异常
     */
    void excUpdateUserInfo(HttpSession session, HTUserVO paramVO, MultipartFile[] resourceFiles, MultipartFile signPathFile) throws LogicException, SystemException;


    /**
     * 获取用户信息
     *
     * @return 用户信息
     * @throws SystemException 系统异常
     */
    void excUpdateUserPwd(HTUserVO paramVO) throws SystemException,LogicException;
}
