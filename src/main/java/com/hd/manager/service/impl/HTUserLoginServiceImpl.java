package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.util.MD5Utils;
import com.hd.common.util.MessageConstant;
import com.hd.manager.dao.HTRoleDao;
import com.hd.manager.dao.HTUserDao;
import com.hd.manager.dao.HTUserLoginDao;
import com.hd.manager.dao.bean.HTMenuBean;
import com.hd.manager.dao.bean.HTUserBean;
import com.hd.manager.service.HTUserLoginService;
import com.hd.manager.vo.HTMenuVO;
import com.hd.manager.vo.HTUserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HTUserLoginServiceImpl extends BaseServiceImpl implements HTUserLoginService {

    @Resource
    private HTUserLoginDao htUserLoginDao;

    /**
    * 查询登录用户信息
    */
    @Override
    public HTUserVO userLogin(HTUserVO paramVo)  throws LogicException, SystemException{
        try {
            HTUserBean paramBean = new HTUserBean();
            paramBean.setUserAccount(paramVo.getUserAccount());
            //查询登录用户信息
            List<HTUserBean> userBeanList = htUserLoginDao.checkUserLogin(paramBean);
            if(StringUtils.isBlank(paramVo.getUserAccount())){
                throw new LogicException(MessageConstant.MSG_ERROR_LOGIN_0008);
            }
            if(StringUtils.isBlank(paramVo.getPassword())){
                throw new LogicException(MessageConstant.MSG_ERROR_LOGIN_0009);
            }
            //判断是否存在
            if (null==userBeanList||userBeanList.size()== 0){
                throw new LogicException(MessageConstant.MSG_ERROR_LOGIN_0001);
            }
            HTUserBean userBean = userBeanList.get(0);
            //判断是否停职
            if (userBean.getIsIncumbent().equals("0")){
                throw new LogicException(MessageConstant.MSG_ERROR_LOGIN_0007);
            }
            //判断密码是否正确
            if (!userBean.getPassword().equals(MD5Utils.encrypt(paramVo.getPassword()))){
                throw new LogicException(MessageConstant.MSG_ERROR_LOGIN_0003);
            }
            HTUserVO userVO = new HTUserVO();
            userVO.setLoginUserId(userBean.getUserId());
            userVO.setUserName(userBean.getUserName());
            userVO.setRoleName(userBean.getRoleName());
            userVO.setEmail(userBean.getEmail());
            userVO.setUserAccount(userBean.getUserAccount());
            //返回用户信息
            return userVO;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询角色下菜单列表
     */
    @Override
    public List<HTMenuVO> searchMenuList(HTUserVO paramVo)  throws LogicException, SystemException{
        try {
            //查询角色下菜单列表
            List<HTMenuBean> menuList = htUserLoginDao.searchMenuListByUserId(paramVo.getLoginUserId());
            List<HTMenuVO> htMenuVOList = new ArrayList<>();
            //格式转换
            for (HTMenuBean htMenuBean:menuList){
                HTMenuVO htMenuVO = new HTMenuVO();
                //功能清单ID
                htMenuVO.setMenuId(htMenuBean.getMenuId());
                //功能清单名称
                htMenuVO.setMenuName(htMenuBean.getMenuName());
                //菜单类型：1业务请求菜单；2中间节点菜单
                htMenuVO.setMenuType(htMenuBean.getMenuType().toString());
                //菜单位置：1上；2下；3左；4右
                htMenuVO.setMenuPosition(htMenuBean.getMenuPosition().toString());
                //排序号
                htMenuVO.setOrderNum(htMenuBean.getOrderNum());
                //菜单图标URL
                htMenuVO.setMenuIconUrl(htMenuBean.getMenuIconUrl());
                //请求URL
                htMenuVO.setUrl(htMenuBean.getUrl());
                //父级菜单ID
                htMenuVO.setParentMenuId(htMenuBean.getParentMenuId());
                htMenuVOList.add(htMenuVO);
            }
            return htMenuVOList;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询用户所属部门ID集合
     */
    @Override
    public List<String> searchDepartmentIds(HTUserVO paramVo)  throws LogicException, SystemException{
        try {
            //查询用户所属部门ID集合
            List<String> departmentIds = htUserLoginDao.searchDepartmentIds(paramVo.getLoginUserId());
            return departmentIds;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 查询用户所属职位ID集合
     */
    @Override
    public List<String> searchJobIds(HTUserVO paramVo)  throws LogicException, SystemException{
        try {
            //查询用户所属职位ID集合
            List<String> jobIds = htUserLoginDao.searchJobIds(paramVo.getLoginUserId());
            return jobIds;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }
}
