package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.TbRoleEntityMapper;
import com.hd.common.entity.TbRoleEntity;
import com.hd.common.util.BusinessUtils;
import com.hd.common.util.Constant;
import com.hd.common.util.DateToolUtils;
import com.hd.common.util.MessageConstant;
import com.hd.manager.dao.HTMenuDao;
import com.hd.manager.dao.HTRoleDao;
import com.hd.manager.dao.bean.HTMenuBean;
import com.hd.manager.dao.bean.HTRoleBean;
import com.hd.manager.service.HTRoleService;
import com.hd.manager.vo.HTMenuVO;
import com.hd.manager.vo.HTRoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class HTRoleServiceImpl extends BaseServiceImpl implements HTRoleService {

    @Resource
    private HTRoleDao htRoleDao;
    @Resource
    private TbRoleEntityMapper tbRoleEntityMapper;
    @Resource
    private HTMenuDao htMenuDao;


    /**
     * 添加角色信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRoleInfo(HTRoleVO paramVO) throws LogicException, SystemException {
        try {
            HTRoleBean paramBean = new HTRoleBean();
            paramBean.setRoleName(paramVO.getRoleName());
            //查询重复角色名个数
            Integer resultCount = htRoleDao.findRoleCountByRoleName(paramBean);
            //角色名重复校验
            if (resultCount>0){
                throw new LogicException(MessageConstant.MSG_INFO_ROLE_0001);
            }
            Date currentDate = new Date();
            TbRoleEntity roleEntity = new TbRoleEntity();
            //角色名称
            roleEntity.setRoleName(paramVO.getRoleName());
            //创建日期
            roleEntity.setCreateDate(currentDate);
            //修改日期
            roleEntity.setUpdateDate(currentDate);
            //是否删除
            roleEntity.setIsDelFlg("0");
            //添加创建人
            roleEntity.setCreateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改人
            roleEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //角色功能菜单信息
            List<String> menuVOList = paramVO.getMenuVOList();
            List<HTMenuBean> htMenuBeanList = new ArrayList<>();
            tbRoleEntityMapper.insertSelective(roleEntity);
            //格式转换
            for(String menuId:menuVOList){
                HTMenuBean tempHtMenuBean = new HTMenuBean();
                //功能清单ID
                tempHtMenuBean.setMenuId(menuId);
                //角色ID
                tempHtMenuBean.setRoleId(roleEntity.getRoleId());
                //创建日期
                tempHtMenuBean.setCreateDate(roleEntity.getCreateDate());
                //修改日期
                tempHtMenuBean.setUpdateDate(roleEntity.getCreateDate());
                //添加创建人
                tempHtMenuBean.setCreateUserId(roleEntity.getCreateUserId());
                //修改人
                tempHtMenuBean.setUpdateUserId(roleEntity.getCreateUserId());
                //是否删除Flag
                tempHtMenuBean.setIsDelFlg("0");
                htMenuBeanList.add(tempHtMenuBean);
            }
            //添加角色的功能菜单信息关联
            htMenuDao.insertRoleMenuRelationList(htMenuBeanList);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 修改角色信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleInfo(HTRoleVO paramVO) throws LogicException, SystemException  {
        try {
            Date currentDate = new Date();
            HTRoleVO roleInfo = this.findRoleInfo(paramVO);
            //校验数据是否被修改
            if (!roleInfo.getUpdateDate().equals(paramVO.getUpdateDate())){
                throw new LogicException(MessageConstant.MSG_ERROR_COMMON_0005);
            }
            TbRoleEntity roleEntity = new TbRoleEntity();
            //角色ID
            roleEntity.setRoleId(paramVO.getRoleId());
            HTRoleBean paramBean = new HTRoleBean();
            paramBean.setRoleId(paramVO.getRoleId());
            paramBean.setRoleName(paramVO.getRoleName());
            //查询重复角色名个数
            Integer resultCount = htRoleDao.findRoleCountByRoleName(paramBean);
            //角色名重复校验
            if (resultCount>0){
                throw new LogicException(MessageConstant.MSG_INFO_ROLE_0001);
            }
            //角色名称
            roleEntity.setRoleName(paramVO.getRoleName());
            //修改日期
            roleEntity.setUpdateDate(currentDate);
            //添加修改人
            roleEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //修改角色信息
            tbRoleEntityMapper.updateByPrimaryKeySelective(roleEntity);
            //删除所有该角色与功能菜单的关联信息
            htMenuDao.deleteRoleMenuRelationListByRoleId(paramBean);
            List<String> menuVOList = paramVO.getMenuVOList();
            List<HTMenuBean> htMenuBeanList = new ArrayList<>();
            //添加该角色与功能菜单的关联信息
            for(String menuId:menuVOList){
                HTMenuBean tempHtMenuBean = new HTMenuBean();
                //功能清单ID
                tempHtMenuBean.setMenuId(menuId);
                //角色ID
                tempHtMenuBean.setRoleId(roleEntity.getRoleId());
                //创建日期
                tempHtMenuBean.setCreateDate(roleEntity.getUpdateDate());
                //修改日期
                tempHtMenuBean.setUpdateDate(roleEntity.getUpdateDate());
                //添加创建人
                tempHtMenuBean.setCreateUserId(roleEntity.getUpdateUserId());
                //修改人
                tempHtMenuBean.setUpdateUserId(roleEntity.getUpdateUserId());
                //是否删除Flag
                tempHtMenuBean.setIsDelFlg("0");
                htMenuBeanList.add(tempHtMenuBean);
            }
            //添加角色的功能菜单信息关联
            htMenuDao.insertRoleMenuRelationList(htMenuBeanList);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }


    /**
     * 删除角色信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleInfo(HTRoleVO paramVO) throws LogicException, SystemException {
        try {
            Date currentDate = new Date();
            TbRoleEntity roleEntity = new TbRoleEntity();
            //添加修角色ID
            roleEntity.setRoleId(paramVO.getRoleId());
            //修改时间
            roleEntity.setUpdateDate(currentDate);
            //添加修改人
            roleEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //是否删除Flag
            roleEntity.setIsDelFlg("1");
            HTRoleBean paramBean = new HTRoleBean();
            //角色ID
            paramBean.setRoleId(paramVO.getRoleId());
            Integer RoleAndUserRelationCount = htRoleDao.searchRoleAndUserRelationCountByRoleId(paramBean);
            //查询角色下用户关联数量
            if (RoleAndUserRelationCount>0){
                throw new LogicException(MessageConstant.MSG_INFO_ROLE_0002);
            }
            //逻辑删除角色信息
            tbRoleEntityMapper.updateByPrimaryKeySelective(roleEntity);
            //删除所有该角色与功能菜单的关联信息
            htMenuDao.deleteRoleMenuRelationListByRoleId(paramBean);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }



    /**
     * 查询角色列表
     */
    @Override
    public Map<String,Object> findRoleList(HTRoleVO paramVO) throws LogicException, SystemException  {
        try {
            Map<String,Object> resultMap = new HashMap<>();
            List<HTRoleVO> roleVOList = new ArrayList<>();
            HTRoleBean paramBean = new HTRoleBean();
            //放置参数角色ID
            paramBean.setRoleId(paramVO.getRoleId());
            //放置参数角色名称
            paramBean.setRoleName(paramVO.getRoleName());
            //放置参数用户名称
            paramBean.setCreateUserName(paramVO.getCreateUserName());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            //角色列表
            List<HTRoleBean> roleBeanList = htRoleDao.findRoleList(paramBean);
            Integer count = htRoleDao.findAllRoleListCount(paramBean);
            for (HTRoleBean tempHTRoleBean:roleBeanList){
                HTRoleVO htRoleVO = new HTRoleVO();
                //角色ID
                htRoleVO.setRoleId(tempHTRoleBean.getRoleId());
                //角色名称
                htRoleVO.setRoleName(tempHTRoleBean.getRoleName());
                //创建人姓名
                htRoleVO.setCreateUserName(tempHTRoleBean.getCreateUserName());
                //创建人姓名
                htRoleVO.setMenuNames(tempHTRoleBean.getMenuNames());
                roleVOList.add(htRoleVO);
            }
            resultMap.put(Constant.DATA_LIST_KEY,roleVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,count);
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 查询角色信息
     */
    @Override
    public HTRoleVO findRoleInfo(HTRoleVO paramVO) throws LogicException, SystemException {
        try {
            HTRoleVO result = new HTRoleVO();
            HTRoleBean paramBean = new HTRoleBean();
            //放置参数角色ID
            paramBean.setRoleId(paramVO.getRoleId());
            //角色详情信息
            HTRoleBean roleBean = htRoleDao.findRoleInfo(paramBean);
            //角色关联功能菜单集合
            List<HTMenuBean> roleMenuBeanList = htMenuDao.findMenuListByRoleId(paramBean);
            List<String> roleMenuVOList = new ArrayList<>();
            //格式转换
            for(HTMenuBean htMenuBean:roleMenuBeanList){
                HTMenuVO htMenuVO = new HTMenuVO();
                //功能清单ID
                roleMenuVOList.add(htMenuBean.getMenuId());
            }
            //角色ID
            result.setRoleId(roleBean.getRoleId());
            //角色名称
            result.setRoleName(roleBean.getRoleName());
            //创建用户名称
            result.setCreateUserName(roleBean.getCreateUserName());
            //修改时间
            result.setUpdateDate(DateToolUtils.convertDateFormat(roleBean.getUpdateDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
            //角色功能菜单关联
            result.setMenuVOList(roleMenuVOList);
            return result;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }
    /**
     * 查询所有菜单列表
     */
    @Override
    public Map<String, Object> findAllMenuList() throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<HTMenuBean> allMenuList = htMenuDao.findAllMenuList();
            List<HTMenuVO> allMenuVOList = new ArrayList<>();
            List<HTMenuVO> childMenuVOList = new ArrayList<>();

            for (HTMenuBean htMenuBean:allMenuList){
                HTMenuVO htMenuVO = new HTMenuVO();
                if (null == htMenuBean.getParentMenuId()||htMenuBean.getParentMenuId().isEmpty()){
                    htMenuVO.setMenuId(htMenuBean.getMenuId());
                    htMenuVO.setMenuName(htMenuBean.getMenuName());
                    allMenuVOList.add(htMenuVO);
                }else {
                    htMenuVO.setMenuId(htMenuBean.getMenuId());
                    htMenuVO.setMenuName(htMenuBean.getMenuName());
                    htMenuVO.setParentMenuId(htMenuBean.getParentMenuId());
                    childMenuVOList.add(htMenuVO);
                }
            }
            for (HTMenuVO menuVO:allMenuVOList){
                List<HTMenuVO> childrenList = new ArrayList<>();
                for (HTMenuVO chileMenuVO:childMenuVOList){
                    if (menuVO.getMenuId().equals(chileMenuVO.getParentMenuId())){
                        childrenList.add(chileMenuVO);
                    }
                }
                menuVO.setChildrenList(childrenList);
            }
            resultMap.put(Constant.DATA_LIST_KEY,allMenuVOList);
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }


}
