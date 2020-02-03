package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTUserBean;
import com.hd.manager.dao.bean.HTUserFileRelationBean;
import com.hd.manager.dao.bean.HTJobBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 设置-用户 持久层接口
 *
 * @author zjy
 * Created in 2019/7/1 17:12
 */
public interface HTUserDao extends BaseDao {

    /**
     * 查询token
     * @return token
     */
    HTUserBean excSearchAccessToken();

    /**
     * 查询公司和合作公司的信息下拉列表
     * @return 公司和合作公司的信息下拉列表
     */
    List<HTUserBean> excSearchCompanyList();

    /**
     * 查询角色权限信息下拉列表
     * @return 角色权限信息下拉列表
     */
    List<HTUserBean> excSearchRoleList();

    /**
     * 查询专业信息下拉列表
     * @return 专业信息下拉列表
     */
    List<HTUserBean> excSearchProfessionalList();

    /**
     * 查询文化程度信息下拉列表
     * @return 文化程度信息下拉列表
     */
    List<HTUserBean> excSearchEducationList();

    /**
     * 查询部门树信息下拉列表
     * @return 部门树信息下拉列表
     */
    List<HTUserBean> excSearchDeptList();

    /**
     * 查询职务信息下拉列表
     * @return 职务树信息下拉列表
     */
    List<HTUserBean> excSearchJobList(HTUserBean paramBean);

    /**
     * 查询用户列表信息
     * @return 用户列表
     */
    List<HTUserBean> excSearchUserList(HTUserBean paramBean);

    /**
     * 查询用户列表的总条数
     * @return 用户列表
     */
    int getUserCount(HTUserBean paramBean);

    /**
     * 校验登陆账号是否重复
     * @return 用户信息
     */
    int getUserByUserAccount(HTUserBean paramBean);

    /**
     * 批量插入用户和专业的关联
     * @return 用户信息
     */
    void batchInsertUserProfessional(List<HTUserBean> userProfessionalList);

    /**
     * 插入用户和角色的关联
     * @return 用户信息
     */
    void insertUserRoleInfo(HTUserBean paramBean);

    /**
     * 查询用户详细信息
     * @return 用户信息
     */
    HTUserBean getUserInfo(HTUserBean paramBean);

    /**
     * 查询用户专业Id List
     * @return 用户信息
     */
    List<String> searchProfessionalIdList(HTUserBean paramBean);

    /**
     * 查询用户资质文件 List
     * @return 用户信息
     */
    List<HTUserFileRelationBean> searchResourceRelationList(HTUserBean paramBean);

    /**
     * 检索是否更新同一条信息
     * @param paramBean
     * @return  数量
     */
    int getUserDataCountByUpdateDate(HTUserBean paramBean);

    /**
     * 删除用户和角色的关联信息
     * @param paramBean
     * @return  数量
     */
    void deleteUserRoleInfo(HTUserBean paramBean);

    /**
     * 删除用户和专业的关联信息
     * @param paramBean
     * @return  数量
     */
    void deleteUserProfessionalInfo(HTUserBean paramBean);

    /**
     * 批量插入用户和资质文件的关联
     * @return 用户信息
     */
    void batchInsertUserResourceFiles(List<HTUserBean> fileList);

    /**
     * 批量插入用户和职位关联
     * @return 用户信息
     */
    void insertUserJobRelations(HTUserBean paramBean);

    /**
     * 查询当前用户关联职位信息
     * @return 用户关联职位信息
     */
    List<HTJobBean> searchUserJobList(String userId);

    /**
     * 删除当前用户关联职位信息
     */
    void deleteUserJobRelation(String userId);
    /**
     * 查询所需删除文件的真实路径
     * @return 删除文件的真实路径
     */
    List<String> searchDeleteFilePath(List<String> deleteResourceList);
    /**
     * 删除文件信息
     */
    void deleteFileData(List<String> deleteResourceList);

    /**
     * 根据部门查人员列表
     * @return 用户信息
     */
    List<HTUserBean> excSearchUserListByDepartmentId(HTUserBean paramBean);


    /**
     * 查询是否有离职人员
     * @return 离职人员个数
     */
    Integer excSearchQuitUserByIds(List<String> userIds);

    /**
     * 查询用户缓存信息
     *
     * @return 用户信息
     */
    List<Map<String, Object>> excQueryUserCache(@Param(value = "userId") String userId);
}
