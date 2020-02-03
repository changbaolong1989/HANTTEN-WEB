package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbUserEntityMapper;
import com.hd.common.entity.TbUserEntity;
import com.hd.common.entity.TbUserEntityExample;
import com.hd.common.service.CommonCacheService;
import com.hd.common.util.*;
import com.hd.manager.dao.HTUserDao;
import com.hd.manager.dao.bean.HTUserBean;
import com.hd.manager.dao.bean.HTUserFileRelationBean;
import com.hd.manager.dao.bean.HTJobBean;
import com.hd.manager.service.HTUserService;
import com.hd.manager.vo.HTUserFileRelationVO;
import com.hd.manager.vo.HTUserJobVO;
import com.hd.manager.vo.HTUserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 设置-用户管理 业务层 实现类
 *
 * @author zjy
 * Created in 2019/7/1 17:08
 */
@Service
public class HTUserServiceImpl extends BaseServiceImpl implements HTUserService {

    /**
     * 用户dao 持久层资源
     */
    @Resource
    private HTUserDao htUserDao;

    /**
     * 用户entity 持久层资源
     */
    @Resource
    private TbUserEntityMapper tbUserEntityMapper;

    /**
     * 清除缓存
     */
    @Resource
    private CommonCacheService commonCacheService;

    /**
     * 添加openid
     * @return
     * @throws SystemException
     */
    @Override
    @Transactional
    public void excUpdateOpenIdByUserId(HTUserVO paramVO) throws SystemException{

        try {

            TbUserEntity tbUserEntity = new TbUserEntity();

            // 用户id
            tbUserEntity.setUserId(paramVO.getLoginUserId());

            // openid
            tbUserEntity.setOpenId(paramVO.getOpenId());

            // 修改openid
            tbUserEntityMapper.updateByPrimaryKeySelective(tbUserEntity);

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }

    }

    /**
     * 查询用户详情信息
     * @return
     * @throws SystemException
     */
    @Override
    @Transactional(readOnly = true)
    public HTUserVO excSearchUserDetailInfo(HTUserVO paramVO) throws SystemException{

        // 用户对象
        HTUserVO htUserVO = new HTUserVO();

        try {

            TbUserEntityExample example = new TbUserEntityExample();
            TbUserEntityExample.Criteria criteria = example.createCriteria();
            criteria.andOpenIdEqualTo(paramVO.getOpenId());

            // 查询用户信息
            List<TbUserEntity> tbUserEntityList = tbUserEntityMapper.selectByExample(example);

            // 如果存在此openid所属的用户，则设置用户信息
            if(!ListToolUtils.isEmpty(tbUserEntityList)){

                if(null != tbUserEntityList.get(0)){

                    // 设置用户id
                    htUserVO.setLoginUserId(tbUserEntityList.get(0).getUserId());

                    // 设置用户账户
                    htUserVO.setUserAccount(tbUserEntityList.get(0).getUserAccount());

                    // 设置用户密码
                    htUserVO.setPassword(tbUserEntityList.get(0).getPassword());

                    // 设置用户名称
                    htUserVO.setUserName(tbUserEntityList.get(0).getUserName());

                    // 设置email
                    htUserVO.setEmail(tbUserEntityList.get(0).getEmail());

                }else{

                    // 设置用户id
                    htUserVO.setLoginUserId(StringToolUtils.STRING_EMPTY);

                    // 设置用户账户
                    htUserVO.setUserAccount(StringToolUtils.STRING_EMPTY);

                    // 设置用户密码
                    htUserVO.setPassword(StringToolUtils.STRING_EMPTY);

                    // 设置用户名称
                    htUserVO.setUserName(StringToolUtils.STRING_EMPTY);

                    // 设置email
                    htUserVO.setEmail(StringToolUtils.STRING_EMPTY);

                }

            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }

        // 返回用户信息
        return htUserVO;

    }
    
    /**
     * 查询公司和合作公司的信息下拉列表
     *
     * @return 公司和合作公司的信息下拉列表
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTUserVO> excSearchCompanyList() throws SystemException {
        // 集合
        List<HTUserVO> retCompanyList = new ArrayList<>();

        try {

            // 查询公司和合作公司的信息下拉列表
            List<HTUserBean> companyList = htUserDao.excSearchCompanyList();

            HTUserVO htUserVO = null;

            // Bean 转化成 VO
            for (HTUserBean htUserBean : companyList) {

                htUserVO = new HTUserVO();

                // 设置公司ID
                htUserVO.setCompanyId(htUserBean.getCompanyId());

                // 设置公司名称
                htUserVO.setCompanyName(htUserBean.getCompanyName());

                // 设置是否是合作公司
                htUserVO.setIsCooperateCompany(htUserBean.getIsCooperateCompany());

                retCompanyList.add(htUserVO);
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retCompanyList;
    }

    /**
     * 查询角色权限信息下拉列表
     *
     * @return 角色权限信息下拉列表
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTUserVO> excSearchRoleList() throws SystemException {
        // 集合
        List<HTUserVO> retRoleList = new ArrayList<>();

        try {

            // 查询角色权限信息下拉列表
            List<HTUserBean> roleListList = htUserDao.excSearchRoleList();

            HTUserVO htUserVO = null;

            // Bean 转化成 VO
            for (HTUserBean htUserBean : roleListList) {

                htUserVO = new HTUserVO();

                // 设置角色ID
                htUserVO.setRoleId(htUserBean.getRoleId());

                // 设置角色名称
                htUserVO.setRoleName(htUserBean.getRoleName());

                retRoleList.add(htUserVO);
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retRoleList;
    }

    /**
     * 查询专业信息下拉列表
     *
     * @return 专业信息下拉列表
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTUserVO> excSearchProfessionalList() throws SystemException {
        // 集合
        List<HTUserVO> retProfessionalList = new ArrayList<>();

        try {

            // 查询专业信息下拉列表
            List<HTUserBean> professionalListList = htUserDao.excSearchProfessionalList();

            HTUserVO htUserVO = null;

            // Bean 转化成 VO
            for (HTUserBean htUserBean : professionalListList) {

                htUserVO = new HTUserVO();

                // 设置专业ID
                htUserVO.setProfessionalId(htUserBean.getProfessionalId());

                // 设置专业名称
                htUserVO.setProfessionalName(htUserBean.getProfessionalName());

                retProfessionalList.add(htUserVO);
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retProfessionalList;
    }

    /**
     * 查询文化程度下拉列表
     *
     * @return 文化程度下拉列表
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTUserVO> excSearchEducationList() {
        // 集合
        List<HTUserVO> retEducationList = new ArrayList<>();

        try {

            // 查询文化程度信息下拉列表
            List<HTUserBean> educationListList = htUserDao.excSearchEducationList();

            HTUserVO htUserVO = null;

            // Bean 转化成 VO
            for (HTUserBean htUserBean : educationListList) {

                htUserVO = new HTUserVO();

                // 设置文化程度ID
                htUserVO.setEducationId(htUserBean.getEducationId());

                // 设置文化程度名称
                htUserVO.setEducationName(htUserBean.getEducationName());

                retEducationList.add(htUserVO);
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retEducationList;
    }

    /**
     * 查询部门信息下拉列表
     *
     * @return 部门信息下拉列表
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTUserVO> excSearchDeptTree() {
        // 集合
        List<HTUserVO> retDeptTreeList = new ArrayList<>();

        try {

            // 查询部门信息下拉列表
            List<HTUserBean> deptListList = htUserDao.excSearchDeptList();

            HTUserVO htUserVO = null;

            // Bean 转化成 VO
            for (HTUserBean htUserBean : deptListList) {

                htUserVO = new HTUserVO();

                // 设置部门ID
                htUserVO.setDeptId(htUserBean.getDeptId());

                // 设置部门名称
                htUserVO.setDeptName(htUserBean.getDeptName());

                retDeptTreeList.add(htUserVO);
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retDeptTreeList;
    }

    /**
     * 根据部门ID联动职位信息
     *
     * @return 职位下拉列表
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTUserVO> excSearchJobListByDeptId(HTUserVO paramVO) {
        // 集合
        List<HTUserVO> retJobList = new ArrayList<>();

        try {

            HTUserBean paramBean = new HTUserBean();

            // 查询条件：部门ID
            paramBean.setDeptId(StringToolUtils.convertNullObjectToString(paramVO.getDeptId()));

            // 查询职务下拉列表
            List<HTUserBean> jobListList = htUserDao.excSearchJobList(paramBean);

            HTUserVO htUserVO = null;

            // Bean 转化成 VO
            for (HTUserBean htUserBean : jobListList) {

                htUserVO = new HTUserVO();

                // 设置职务ID(实际为部门和职位的关联ID)
                htUserVO.setJobId(htUserBean.getJobId());

                // 设置职务名称
                htUserVO.setJobName(htUserBean.getJobName());

                retJobList.add(htUserVO);
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retJobList;
    }

    /**
     * 查询用户列表
     *
     * @return 用户列表
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchUserList(HTUserVO paramVO) throws SystemException {

        // 结果Map
        Map<String, Object> resultPageMap = new HashMap<>();

        try {

            HTUserBean paramBean = new HTUserBean();

            // 查询条件：用户名称
            paramBean.setUserName(StringToolUtils.convertNullObjectToString(paramVO.getCondUserName()));

            // 查询条件：部门ID
            paramBean.setDeptId(StringToolUtils.convertNullObjectToString(paramVO.getCondDeptId()));

            // 查询条件：是否在职
            paramBean.setIsIncumbent(StringToolUtils.convertNullObjectToString(paramVO.getCondIsIncumbent()));

            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            // 查询用户列表
            List<HTUserBean> userList = htUserDao.excSearchUserList(paramBean);

            // 查询用户个数
            int userCount = htUserDao.getUserCount(paramBean);

            HTUserVO tmpVO = null;

            List<HTUserVO> retUserList = new ArrayList<>();

            // Bean 转化 VO
            for (HTUserBean tmpBean : userList) {
                tmpVO = new HTUserVO();
                // 设置用户ID
                tmpVO.setUserId(StringToolUtils.convertNullObjectToString(tmpBean.getUserId()));
                // 设置用户名称
                tmpVO.setUserName(StringToolUtils.convertNullObjectToString(tmpBean.getUserName()));
                // 设置登陆账号
                tmpVO.setUserAccount(StringToolUtils.convertNullObjectToString(tmpBean.getUserAccount()));
                // 设置文化程度
                tmpVO.setEducationName(StringToolUtils.convertNullObjectToString(tmpBean.getEducationName()));
                // 设置部门
                tmpVO.setDeptName(StringToolUtils.convertNullObjectToString(tmpBean.getDeptName()));
                // 设置入职时间
                tmpVO.setEntryDate(DateToolUtils.convertDateFormat(tmpBean.getEntryDate(), DateToolUtils.yyyy_MM_dd));
                // 设置是否在职
                tmpVO.setIsIncumbent(StringToolUtils.convertNullObjectToString(tmpBean.getIsIncumbent()).equals("0") ? "否" : "是");
                // 设置是否是合作公司
                tmpVO.setIsCooperateCompany(StringToolUtils.convertNullObjectToString(tmpBean.getIsCooperateCompany()).equals("0") ? "否" : "是");
                // 设置用户个人签名
                tmpVO.setSignPath(StringToolUtils.convertNullObjectToString(tmpBean.getSignPathFile())+"?t="+UUID.randomUUID().toString());

                retUserList.add(tmpVO);
            }

            resultPageMap.put(Constant.DATA_LIST_KEY, retUserList);

            resultPageMap.put(Constant.DATA_COUNT_KEY, userCount);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }


        return resultPageMap;
    }

    /**
     * 更改用户信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdateUserInfo(HttpSession session, HTUserVO paramVO, MultipartFile[] resourceFiles, MultipartFile signPathFile) throws LogicException, SystemException {

        // 临时变量：当前时间
        Date tmpCurrentDate = getCurrentDate();

        try {

            HTUserBean paramBean = new HTUserBean();

            String tmpUserId = StringToolUtils.convertNullObjectToString(paramVO.getUserId());
            // 用户ID
            paramBean.setUserId(tmpUserId);

            // 登陆名称
            paramBean.setUserAccount(StringToolUtils.convertNullObjectToString(paramVO.getUserAccount()));

            //更新时间
            paramBean.setUpdateDate(DateToolUtils.convertDateFormat(paramVO.getUpdateDate(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            paramBean.setJobIds(paramVO.getJobIds());

            //====================================检索是否更新同一条信息 start ===========================================//

            int dataCount = htUserDao.getUserDataCountByUpdateDate(paramBean);

            // 如果修改时间和数据库中的不一致，则抛出异常提示，此信息已更新，请重新获取信息！
//            if (dataCount == 0) {
//                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
//            }

            //====================================检索是否更新同一条信息 end ===========================================//

            //====================================校验登陆用户信息是否重复 start ===========================================//

            // 校验登陆账号是否重复
            int userCount = htUserDao.getUserByUserAccount(paramBean);

            // 有重复，抛出异常(该用户名已存在，请修改后重新提交！)
            if (userCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_USER_0001);
            }

            //====================================校验登陆用户信息是否重复 end ===========================================//


            TbUserEntity tbUserEntity = new TbUserEntity();

            // 设置用户ID
            tbUserEntity.setUserId(tmpUserId);

            // 设置用户名称
            tbUserEntity.setUserName(StringToolUtils.convertNullObjectToString(paramVO.getUserName()));

            // 设置登陆名称
            tbUserEntity.setUserAccount(StringToolUtils.convertNullObjectToString(paramVO.getUserAccount()));

            // 临时变量：是否重置密码
            String tmpIsResetPwd = paramVO.getIsResetPwd();

            // 选择重置密码，则密码替换成初始值
            if (Constant.IS_RESET_PWD_ENABLE.equals(tmpIsResetPwd)) {
                // 设置密码
                tbUserEntity.setPassword(MD5Utils.encrypt(Constant.INIT_PASSWORD));
                // 清除session
//                session.removeAttribute("loginUserInfo");
            }

            // 设置公司ID
            tbUserEntity.setCompanyId(StringToolUtils.convertNullObjectToString(paramVO.getCompanyId()));

            // 所属公司
            tbUserEntity.setCompanyId(StringToolUtils.convertNullObjectToString(paramVO.getCompanyId()));

            // 文化程度
            tbUserEntity.setEducation(StringToolUtils.convertNullObjectToString(paramVO.getEducationId()));

            // 邮箱
            tbUserEntity.setEmail(StringToolUtils.convertNullObjectToString(paramVO.getEmail()));

            Date tmpEntryDate = DateToolUtils.convertDateFormat(paramVO.getEntryDate(), DateToolUtils.yyyy_MM_dd);

            // 入职时间
            tbUserEntity.setEntryDate(tmpEntryDate);

            // 文件上传后的路径
            String signPathFilePath = "";

            // 当上传的个人签名为空，则不需要再
            if (null!= signPathFile && !signPathFile.isEmpty()) {

                // 个性签名文件名
                String signPathFileName = signPathFile.getOriginalFilename();

                // 获取文件的后缀名
                String suffixName = signPathFileName.substring(signPathFileName.lastIndexOf("."));

                // 获取个人签名文件路径
                signPathFileName = tmpUserId + suffixName;

                // 文件上传后的路径
                signPathFilePath = Constant.FILE_SEPARATE + "user" + Constant.FILE_SEPARATE + tmpUserId + Constant.FILE_SEPARATE + "signPath" + Constant.FILE_SEPARATE + signPathFileName;

                // 个人签名
                tbUserEntity.setSignPath(signPathFilePath);

            }

            // 临时变量: 是否在职
            String tmpIsIncumbent = StringToolUtils.convertNullObjectToString(paramVO.getIsIncumbent());

            // 是否在职
            tbUserEntity.setIsIncumbent(tmpIsIncumbent);

            // 专业
            tbUserEntity.setProfessionalName(StringToolUtils.convertNullObjectToString(paramVO.getProfessionalName()));

            // 更改为不在职 清除session
            if (Constant.IS_INCUMBENT_ENABLE.equals(tmpIsIncumbent)) {
                // 清除session
                //session.removeAttribute("loginUserInfo");
            }

            // 设置更新时间
            tbUserEntity.setUpdateDate(tmpCurrentDate);

            // 设置更新人(从session中获取)
            tbUserEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 更改用户信息
            tbUserEntityMapper.updateByPrimaryKeySelective(tbUserEntity);

            //========================= 添加用户和角色关联表  start ========================//
            // 删除用户和角色的关联信息
            htUserDao.deleteUserRoleInfo(paramBean);

            // 角色ID
            paramBean.setRoleId(StringToolUtils.convertNullObjectToString(paramVO.getRoleId()));

            // 创建时间
            paramBean.setCreateDate(tmpCurrentDate);

            // 创建人
            paramBean.setCreateUserId(paramVO.getCreateUserId());

            // 是否删除
            paramBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
            if (null != resourceFiles && resourceFiles.length > 0) {
                List<HTUserBean> fileList = new ArrayList<>();

                HTUserBean htUserBean = null;

                for (MultipartFile resourceFile : resourceFiles) {

                    // 资质文件名称
                    String resourceFileName = resourceFile.getOriginalFilename();

                    // 获取文件的后缀名
                    String suffixResName = resourceFileName.substring(resourceFileName.lastIndexOf("."));

                    String uuId = UUID.randomUUID().toString();

                    // 资质文件名称
                    resourceFileName = uuId + suffixResName;

                    // 文件上传后的路径
                    String resourceFilePath = Constant.FILE_SEPARATE + "user" + Constant.FILE_SEPARATE + tmpUserId + Constant.FILE_SEPARATE + "resourceFile" + Constant.FILE_SEPARATE + resourceFileName;

                    htUserBean = new HTUserBean();

                    // 设置关联ID
                    htUserBean.setRelationId(uuId);
                    // 设置用户ID
                    htUserBean.setUserId(tmpUserId);
                    // 设置资质文件
                    htUserBean.setResourceFilePath(resourceFilePath);
                    // 设置创建时间
                    htUserBean.setCreateDate(tmpCurrentDate);
                    // 设置创建人
                    htUserBean.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                    // 设置更新时间
                    htUserBean.setUpdateDate(tmpCurrentDate);
                    // 设置更新人
                    htUserBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                    // 是否删除
                    htUserBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

                    fileList.add(htUserBean);

                    // 上传文件
                    FileUtils.uploadFile(resourceFilePath, resourceFile);
                }
                // 批量插入用户信息
                htUserDao.batchInsertUserResourceFiles(fileList);

            }
            // 添加用户和角色关联表
            htUserDao.insertUserRoleInfo(paramBean);

            //========================= 添加用户和角色关联表  end ========================//

            //========================= 添加用户和专业关联表  start ========================//
            // 删除用户和专业的关联信息
//            htUserDao.deleteUserProfessionalInfo(paramBean);
//
//            List<HTUserBean> userProfessionalList = new ArrayList<>();
//
//            // 获取专业ID 的list
//            List<String> professionalIdList = paramVO.getProfessionalIds();
//
//            HTUserBean tmpBean = null;

//            for (String professionalId : professionalIdList) {
//
//                tmpBean = new HTUserBean();
//
//                // 设置用户ID
//                tmpBean.setUserId(paramBean.getUserId());
//
//                // 设置专业ID
//                tmpBean.setProfessionalId(professionalId);
//
//                // 创建时间
//                tmpBean.setCreateDate(tmpCurrentDate);
//
//                // 创建人
//                tmpBean.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
//
//                // 更新时间
//                tmpBean.setUpdateDate(tmpCurrentDate);
//
//                // 创建人
//                tmpBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
//
//                // 是否删除
//                tmpBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
//
//                userProfessionalList.add(tmpBean);
//
//            }

            // 批量添加用户和专业关联
            //htUserDao.batchInsertUserProfessional(userProfessionalList);

            //========================= 添加用户和专业关联表  end ========================//

            //========================= 删除并上传个人签名文件 start ===========================//

            // 当有图片重新上传时，上传图片，重新上传
            if (null!=signPathFile&&!signPathFile.isEmpty()) {
                // 删除原来已有文件
                FileUtils.deleteFile(signPathFilePath);

                // 重新上传新的文件
                boolean isUploadSuccess = FileUtils.uploadFile(signPathFilePath, signPathFile);

                if (!isUploadSuccess) {
                    // MSG_ERROR_COMMON_0006 上传失败!
                    throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0006);
                }
            }
            // ========================  删除并上传个人签名文件 end  =============================//

            //========================= 删除并上传资质文件 start ===========================//

            // ========================  删除用户职业关联 start =============================//
            htUserDao.deleteUserJobRelation(paramVO.getUserId());
            //========================= 删除用户职业关联 start ===========================//

            //========================= 用户与职位关联 start ==================================//
            //添加用户与职位关联
            htUserDao.insertUserJobRelations(paramBean);

            //========================= 用户与职位关联  end ==================================//

            //========================= 删除用户与资质文件关联 start ==================================//
            //判断是否有要删除的文件
            if(null!=paramVO.getDeleteResourceList()&&paramVO.getDeleteResourceList().size()>0){
                //查询所需删除文件的真实路径
                List<String> deleteFilePathList = htUserDao.searchDeleteFilePath(paramVO.getDeleteResourceList());
                //删除物理文件
                for (String deleteFilePath:deleteFilePathList){
                    FileUtils.deleteFile(deleteFilePath);
                }
                //删除文件信息
                htUserDao.deleteFileData(paramVO.getDeleteResourceList());
            }

            //========================= 删除用户与资质文件关联  end ==================================//

            //========================= 清除缓存 start  ========================================//

            commonCacheService.clearUserJobCache();
            commonCacheService.updateUserInfoCache(tmpUserId);
            //========================= 清除缓存   end  ========================================//


        } catch (LogicException lex) {

            throw lex;

        } catch (SystemException sex) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 添加用户信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excSaveUserInfo(HTUserVO paramVO, MultipartFile[] resourceFiles, MultipartFile signPathFile) throws LogicException, SystemException {
        // 临时变量：当前时间
        Date tmpCurrentDate = getCurrentDate();

        try {

            HTUserBean paramBean = new HTUserBean();

            // 设置登陆账号
            paramBean.setUserAccount(StringToolUtils.convertNullObjectToString(paramVO.getUserAccount()));

            //========================= 校验登陆用户信息是否重复  start ========================//

            // 校验登陆账号是否重复
            int userCount = htUserDao.getUserByUserAccount(paramBean);

            // 有重复，抛出异常(该用户名已存在，请修改后重新提交！)
            if (userCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_USER_0001);
            }
            //========================= 校验登陆用户信息是否重复  end ========================//

            //========================= 添加用户信息  start ========================//

            TbUserEntity tbUserEntity = new TbUserEntity();

            // 用户名称
            tbUserEntity.setUserName(StringToolUtils.convertNullObjectToString(paramVO.getUserName()));

            // 登陆账号
            tbUserEntity.setUserAccount(StringToolUtils.convertNullObjectToString(paramVO.getUserAccount()));

            // 登陆密码
            tbUserEntity.setPassword(MD5Utils.encrypt(paramVO.getPassword()));

            // 所属公司
            tbUserEntity.setCompanyId(StringToolUtils.convertNullObjectToString(paramVO.getCompanyId()));

            // 文化程度
            tbUserEntity.setEducation(StringToolUtils.convertNullObjectToString(paramVO.getEducationId()));

            // 邮箱
            tbUserEntity.setEmail(StringToolUtils.convertNullObjectToString(paramVO.getEmail()));

            // 临时变量:入职时间
            Date tmpEntryDate = DateToolUtils.convertDateFormat(paramVO.getEntryDate(), DateToolUtils.yyyy_MM_dd);

            // 入职时间
            tbUserEntity.setEntryDate(tmpEntryDate);

            // 是否在职
            tbUserEntity.setIsIncumbent(Constant.IS_INCUMBENT_ENABLE);

            // 专业
            tbUserEntity.setProfessionalName(paramVO.getProfessionalName());

            // 是否删除
            tbUserEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

            // 创建时间
            tbUserEntity.setCreateDate(tmpCurrentDate);

            // 创建人
            tbUserEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 更新时间
            tbUserEntity.setUpdateDate(tmpCurrentDate);

            // 更新人
            tbUserEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 添加用户信息
            tbUserEntityMapper.insertSelective(tbUserEntity);

            //========================= 添加用户信息  end ========================//

            // 获取用户ID
            String tmpUserId = tbUserEntity.getUserId();

            //========================= 添加用户和角色关联表  start ========================//

            // 用户ID
            paramBean.setUserId(tmpUserId);

            // 角色ID
            paramBean.setRoleId(StringToolUtils.convertNullObjectToString(paramVO.getRoleId()));

            // 创建时间
            paramBean.setCreateDate(tmpCurrentDate);

            // 创建人
            paramBean.setCreateUserId(paramVO.getCreateUserId());

            // 是否删除
            paramBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

            // 职位信息
            paramBean.setJobIds(paramVO.getJobIds());

            // 添加用户和角色关联表
            htUserDao.insertUserRoleInfo(paramBean);

            //========================= 添加用户和角色关联表  end ========================//

            //========================= 添加用户和专业关联表  start ========================//

//            if (!ListToolUtils.isEmpty(paramVO.getProfessionalIds())) {
//
//                List<HTUserBean> userProfessionalList = new ArrayList<>();
//
//                // 获取专业ID 的list
//                List<String> professionalIdList = paramVO.getProfessionalIds();
//
//                HTUserBean tmpBean = null;
//
//                for (String professionalId : professionalIdList) {
//
//                    tmpBean = new HTUserBean();
//
//                    // 设置用户ID
//                    tmpBean.setUserId(tmpUserId);
//
//                    // 设置专业ID
//                    tmpBean.setProfessionalId(professionalId);
//
//                    // 创建时间
//                    tmpBean.setCreateDate(tmpCurrentDate);
//
//                    // 创建人
//                    tmpBean.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
//
//                    // 更新时间
//                    tmpBean.setUpdateDate(tmpCurrentDate);
//
//                    // 更新人
//                    tmpBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
//
//                    // 是否删除
//                    tmpBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);
//
//                    userProfessionalList.add(tmpBean);
//
//                }
//
//                // 批量添加用户和专业关联
//                htUserDao.batchInsertUserProfessional(userProfessionalList);
//            }


            //========================= 添加用户和专业关联表  end ========================//

            //========================= 添加用户的个性签名路径  start ========================//

            // 防止个人签名的文件传空
            if (signPathFile.isEmpty()) {
                throw new LogicException(MessageConstant.MSG_ERROR_USER_0002);
            }

            // 个性签名文件名
            String signPathFileName = signPathFile.getOriginalFilename();

            // 获取文件的后缀名
            String suffixName = signPathFileName.substring(signPathFileName.lastIndexOf("."));

            // 文件名
            signPathFileName = tmpUserId + suffixName;

            // 文件上传后的路径
            String signPathFilePath = Constant.FILE_SEPARATE + "user" + Constant.FILE_SEPARATE + tmpUserId + Constant.FILE_SEPARATE + "signPath" + Constant.FILE_SEPARATE;

            TbUserEntity updateUserEntity = new TbUserEntity();

            // 设置用户ID
            updateUserEntity.setUserId(tmpUserId);

            // 设置用户ID
            updateUserEntity.setSignPath(signPathFilePath + signPathFileName);

            // 更新时间
            updateUserEntity.setUpdateDate(tmpCurrentDate);

            // 更新人
            updateUserEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 更新用户个性签名文件
            tbUserEntityMapper.updateByPrimaryKeySelective(updateUserEntity);

            //========================= 添加用户的个性签名路径  end ========================//

            //========================= 添加用户和资质文件关联表  start ========================//

            if (null != resourceFiles && resourceFiles.length > 0) {
                List<HTUserBean> fileList = new ArrayList<>();

                HTUserBean htUserBean = null;

                for (MultipartFile resourceFile : resourceFiles) {

                    // 资质文件名称
                    String resourceFileName = resourceFile.getOriginalFilename();

                    // 获取文件的后缀名
                    String suffixResName = resourceFileName.substring(resourceFileName.lastIndexOf("."));

                    String uuId = UUID.randomUUID().toString();

                    // 资质文件名称
                    resourceFileName = uuId + suffixResName;

                    // 文件上传后的路径
                    String resourceFilePath = Constant.FILE_SEPARATE + "user" + Constant.FILE_SEPARATE + tmpUserId + Constant.FILE_SEPARATE + "resourceFile" + Constant.FILE_SEPARATE + resourceFileName;

                    htUserBean = new HTUserBean();

                    // 设置关联ID
                    htUserBean.setRelationId(uuId);
                    // 设置用户ID
                    htUserBean.setUserId(tmpUserId);
                    // 设置资质文件
                    htUserBean.setResourceFilePath(resourceFilePath);
                    // 设置创建时间
                    htUserBean.setCreateDate(tmpCurrentDate);
                    // 设置创建人
                    htUserBean.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                    // 设置更新时间
                    htUserBean.setUpdateDate(tmpCurrentDate);
                    // 设置更新人
                    htUserBean.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
                    // 是否删除
                    htUserBean.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);

                    fileList.add(htUserBean);

                    // 上传文件
                    FileUtils.uploadFile(resourceFilePath, resourceFile);
                }
                // 批量插入用户信息
                htUserDao.batchInsertUserResourceFiles(fileList);

            }

            //========================= 添加用户和资质文件关联表  end ========================//

            //========================= 上传个人签名 start ==================================//

            // 删除已有文件
            FileUtils.deleteFile(signPathFilePath);

            // 本地上传个性签名文件
            boolean isUploadSignSuccess = FileUtils.uploadFile(signPathFilePath + signPathFileName, signPathFile);

            // 判断上传是否成功
            if (!isUploadSignSuccess) {

                // MSG_ERROR_COMMON_0006 上传文件 失败！
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0006);
            }

            //========================= 上传个人签名 end ===================================//

            //========================= 上传资质文件 start ==================================//


            //========================= 上传资质文件 end ==================================//

            //========================= 用户与职位关联 start ==================================//
            //添加用户与职位关联
            htUserDao.insertUserJobRelations(paramBean);

            //========================= 用户与职位关联  end ==================================//

            //========================= 清除缓存 start  ========================================//

            commonCacheService.clearUserJobCache();
            commonCacheService.updateUserInfoCache(tmpUserId);
            //========================= 清除缓存   end  ========================================//

        } catch (LogicException lex) {

            throw lex;

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询用户详细信息
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTUserVO excSearchUserInfo(HTUserVO paramVO) throws SystemException {

        HTUserVO retUserVO = new HTUserVO();

        try {

            HTUserBean paramBean = new HTUserBean();

            // 用户ID
            paramBean.setUserId(StringToolUtils.convertNullObjectToString(paramVO.getUserId()));

            // 查询用户详情
            HTUserBean userBean = htUserDao.getUserInfo(paramBean);

            if (null != userBean) {
                // 用户名称
                retUserVO.setUserName(StringToolUtils.convertNullObjectToString(userBean.getUserName()));
                // 登陆账号
                retUserVO.setUserAccount(StringToolUtils.convertNullObjectToString(userBean.getUserAccount()));
                // 登陆密码
                retUserVO.setPassword(StringToolUtils.convertNullObjectToString(userBean.getPassword()));
                // 公司ID
                retUserVO.setCompanyId(StringToolUtils.convertNullObjectToString(userBean.getCompanyId()));
                // 公司名称
                retUserVO.setCompanyName(StringToolUtils.convertNullObjectToString(userBean.getCompanyName()));
                // 角色权限ID
                retUserVO.setRoleId(StringToolUtils.convertNullObjectToString(userBean.getRoleId()));
                // 邮箱
                retUserVO.setEmail(StringToolUtils.convertNullObjectToString(userBean.getEmail()));
                // 文化程度
                retUserVO.setEducationId(StringToolUtils.convertNullObjectToString(userBean.getEducationId()));
                // 文化程度名称
                retUserVO.setEducationName(StringToolUtils.convertNullObjectToString(userBean.getEducationName()));
                // 部门ID
                retUserVO.setDeptId(StringToolUtils.convertNullObjectToString(userBean.getDeptId()));
                // 职务ID
                retUserVO.setJobId(StringToolUtils.convertNullObjectToString(userBean.getJobId()));
                // 入职日期
                retUserVO.setEntryDate(DateToolUtils.convertDateFormat(userBean.getEntryDate(), DateToolUtils.yyyy_MM_dd));
                // 个性签名
                retUserVO.setSignPath(StringToolUtils.convertNullObjectToString(userBean.getSignPathFile()));
                // 是否在职
                retUserVO.setIsIncumbent(StringToolUtils.convertNullObjectToString(userBean.getIsIncumbent()));

                // 查询专业ID
                //List<String> professionalIdList = htUserDao.searchProfessionalIdList(paramBean);
                //retUserVO.setProfessionalIds(professionalIdList);
                retUserVO.setProfessionalName(StringToolUtils.convertNullObjectToString(userBean.getProfessionalName()));

                // 查询资质文件
                List<HTUserFileRelationBean> UserFileList = htUserDao.searchResourceRelationList(paramBean);
                List<HTUserFileRelationVO> userFileRelationVOList = new ArrayList<>();
                //格式转换
                for (HTUserFileRelationBean htUserFileRelationBean:UserFileList){
                    HTUserFileRelationVO htUserFileRelationVO = new HTUserFileRelationVO();
                    //关联文件ID
                    htUserFileRelationVO.setFileRelationId(htUserFileRelationBean.getFileRelationId());
                    //关联文件地址
                    htUserFileRelationVO.setFileUrl(htUserFileRelationBean.getFileUrl());
                    userFileRelationVOList.add(htUserFileRelationVO);
                }
                // 资质文件
                retUserVO.setFlieList(userFileRelationVOList);

                // 更新时间
                retUserVO.setUpdateDate(DateToolUtils.convertDateFormat(userBean.getUpdateDate(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                //查询用户的职位关联
                List<HTJobBean> jobBeanList = htUserDao.searchUserJobList(paramBean.getUserId());
                List<HTUserJobVO> jobVOList = new ArrayList<>();
                //格式转换
                for (HTJobBean htUserJobRelationBean:jobBeanList){
                    HTUserJobVO htUserJobRelationVO = new HTUserJobVO();
                    //职位ID
                    htUserJobRelationVO.setJobId(htUserJobRelationBean.getJobId());
                    //部门-职位名称
                    htUserJobRelationVO.setDepartmentJobName(htUserJobRelationBean.getDepartmentJobName());
                    jobVOList.add(htUserJobRelationVO);
                }

                retUserVO.setJobList(jobVOList);
            }

        } catch (Exception e) {
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retUserVO;
    }

    /**
     * 更改用户密码
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdateUserPwd(HTUserVO paramVO) throws SystemException ,LogicException{
        // 临时变量：当前时间
        Date tmpCurrentDate = getCurrentDate();

        try {
            TbUserEntity tbUserEntity = new TbUserEntity();

            String loginUserId = StringToolUtils.convertNullObjectToString(paramVO.getLoginUserInfo().getLoginUserId());

            TbUserEntity tbUserEntity1 = tbUserEntityMapper.selectByPrimaryKey(loginUserId);

            if (!tbUserEntity1.getPassword().equals(MD5Utils.encrypt(paramVO.getOldPwd()))){
                throw  new LogicException(MessageConstant.MSG_ERROR_LOGIN_0010);
            }

            // 设置用户ID
            tbUserEntity.setUserId(StringToolUtils.convertNullObjectToString(loginUserId));

            // 设置密码
            tbUserEntity.setPassword(MD5Utils.encrypt(paramVO.getPassword()));

            // 设置更新时间
            tbUserEntity.setUpdateDate(tmpCurrentDate);

            // 设置更新人
            tbUserEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 更改用户信息
            tbUserEntityMapper.updateByPrimaryKeySelective(tbUserEntity);


        } catch (LogicException ex) {
            throw ex;
        } catch (SystemException sex) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }


}
