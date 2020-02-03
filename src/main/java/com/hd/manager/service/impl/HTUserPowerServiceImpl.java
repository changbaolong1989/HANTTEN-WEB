package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.util.Constant;
import com.hd.common.util.ListToolUtils;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.StringToolUtils;
import com.hd.manager.dao.*;
import com.hd.manager.dao.bean.*;
import com.hd.manager.service.HTUserPowerService;
import com.hd.manager.vo.HTUserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HTUserPowerServiceImpl extends BaseServiceImpl implements HTUserPowerService {



    /**
     * 权限dao 持久层资源
     */
    @Resource
    private HTPowerDao htPowerDao;

    /**
     * 合同dao 持久层资源
     */
    @Resource
    private HTContractDao htContractDao;

    /**
     * 任务dao 持久层资源
     */
    @Resource
    private HTTaskDao htTaskDao;

    /**
     * 投标任务dao 持久层资源
     */
    @Resource
    private HTTenderTaskDao htTenderTaskDao;

    /**
     * 请款单dao 持久层资源
     */
    @Resource
    private HTRequestMenuDao htRequestMenuDao;





    /**
     * 登录人职位集合
     */
    private List<HTJobBean> htLoginUserJobBeans;

    /**
     * 编撰人职位集合
     */
    private List<HTJobBean> htApplyUserJobBeans;

    /**
     * 创建人职位集合
     */
    private List<HTJobBean> htCreateUserJobBeans;




    /**
     * 得到登录人职位集合
     */
    private List<HTJobBean> getHtLoginUserJobBeans(String loginUserId, String departmentId) {
        if (StringToolUtils.isNull(loginUserId)){
            this.htLoginUserJobBeans = new ArrayList<>();
        } else if (ListToolUtils.isEmpty(this.htLoginUserJobBeans)){
            HTJobBean paramBean = new HTJobBean();
            paramBean.setDepartmentId(departmentId);
            paramBean.setUserId(loginUserId);
            this.htLoginUserJobBeans = htPowerDao.searchJobInfoByProperties(paramBean);
        }
        return htLoginUserJobBeans;
    }

    /**
     * 得到编撰人职位集合
     */
    private List<HTJobBean> getHtApplyUserJobBeans(String applyUserId, String departmentId) {
        if (StringToolUtils.isNull(applyUserId)){
            this.htApplyUserJobBeans = new ArrayList<>();
        } else if (ListToolUtils.isEmpty(this.htApplyUserJobBeans)){
            HTJobBean paramBean = new HTJobBean();
            paramBean.setDepartmentId(departmentId);
            paramBean.setUserId(applyUserId);
            this.htApplyUserJobBeans = htPowerDao.searchJobInfoByProperties(paramBean);
        }
        return this.htApplyUserJobBeans;
    }


    /**
     * 得到组长职位集合
     */
    private List<HTJobBean> getHtGroupJobBeans(String applyUserId, String departmentId) {
        if (StringToolUtils.isNull(applyUserId)){
            this.htApplyUserJobBeans = new ArrayList<>();
        } else if (ListToolUtils.isEmpty(this.htApplyUserJobBeans)){
            HTJobBean paramBean = new HTJobBean();
            paramBean.setDepartmentId(departmentId);
            paramBean.setUserId(applyUserId);
            this.htLoginUserJobBeans = htPowerDao.searchJobInfoByProperties(paramBean);
        }
        return htApplyUserJobBeans;
    }

    /**
     * 得到创建人职位集合
     */
    private List<HTJobBean> getHtCreateUserJobBeans(String createUserId, String departmentId) {
        if (StringToolUtils.isNull(createUserId)){
            this.htCreateUserJobBeans = new ArrayList<>();
        } else if (ListToolUtils.isEmpty(this.htCreateUserJobBeans)){
            HTJobBean paramBean = new HTJobBean();
            paramBean.setDepartmentId(departmentId);
            paramBean.setUserId(createUserId);
            this.htCreateUserJobBeans = htPowerDao.searchJobInfoByProperties(paramBean);
        }
        return htCreateUserJobBeans;
    }

    /**
     * 清空登录人职位集合
     */
    private void setHtLoginUserJobBeansEmpty() {
        this.htLoginUserJobBeans = null;
    }

    /**
     * 清空编撰人职位集合
     */
    private void setHtApplyUserJobBeansEmpty() {
        this.htApplyUserJobBeans = null;
    }

    /**
     * 清空创建人职位集合
     */
    private void setHtCreateUserJobBeansEmpty() {
        this.htCreateUserJobBeans = null;
    }

    /**
     * 清空缓存
     */
    public void cleanCacheData(){
        //清空登录人职位集合
        this.setHtLoginUserJobBeansEmpty();
        //清空编撰人职位集合
        this.setHtApplyUserJobBeansEmpty();
        //清空创建人职位集合
        this.setHtCreateUserJobBeansEmpty();
    }



    /**
     * 查询用户所拥有权限
     *
     * @return 权限信息
     */
    @Override
    public Map<String, Object> excSearchLoginUserPowerMap(List<String> jobIds) throws LogicException, SystemException  {
        Map<String, Object> resultMap = new HashMap<>();
        try{
            //查询用户已知所拥有权限
            List<HTPowerBean> htPowerBeans = htPowerDao.excSearchLoginUserPowerList(jobIds);
            //转换格式返回到前端
            for (HTPowerBean htPowerBean : htPowerBeans){
                resultMap.put(htPowerBean.getPowerKey(), Constant.POWER_ABLE);
            }
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex,MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }




    /**
     * 查询用户当前工作所含权限
     *
     * @return 权限信息
     */
    @Override
    public Map<String,Object> excSearchUserWorkPowerMap(HTUserVO loginUserInfo,String businessId,
                                                        String businessType) throws LogicException, SystemException {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            switch (businessType){
                //权限模块所属：合同
                case Constant.POWER_MODULE_BELONG_CONTRACT:
                    resultMap.putAll(this.excSearchUserContractPowerMap(loginUserInfo, businessId, businessType));
                    break;
                //权限模块所属：任务
                case Constant.POWER_MODULE_BELONG_TASK:
                    resultMap.putAll(this.excSearchUserTaskPowerMap(loginUserInfo, businessId, businessType));
                    break;
                //权限模块所属：投标任务
                case Constant.POWER_MODULE_BELONG_TENDER_TASK:
                    resultMap.putAll(this.excSearchUserTenderTaskPowerMap(loginUserInfo, businessId, businessType));
                    break;
                //权限模块所属：请款单
                case Constant.POWER_MODULE_BELONG_REQUEST_MENU:
                    resultMap.putAll(this.excSearchUserRequestMenuPowerMap(loginUserInfo, businessId, businessType));
                    break;
                default:
                    break;
            }
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }

        return resultMap;
    }


    /**
     * 查询用户当前合同所含权限
     *
     * @return 权限信息
     */
    public Map<String,Object> excSearchUserContractPowerMap(HTUserVO loginUserInfo,String businessId,
                                                            String businessType) throws LogicException, SystemException {

        Map<String,Object> resultMap = new HashMap<>();
        try{
            HTPowerBean paramBean = new HTPowerBean();
            //权限模块所属
            paramBean.setModuleBelong(businessType);
            //合同实体信息
            HTContractBean contractBean = htContractDao.getContractInfoByContractId(businessId);
            //合同模块所属的所用权限
            List<HTPowerBean> htPowerBeans = htPowerDao.excSearchUserWorkPowerList(paramBean);
            //遍历筛查规则
            resultMap.putAll(this.excSearchUserPower(htPowerBeans,loginUserInfo,contractBean));
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }


    /**
     * 查询用户当前任务所含权限
     *
     * @return 权限信息
     */
    public Map<String,Object> excSearchUserTaskPowerMap(HTUserVO loginUserInfo,String businessId,
                                                        String businessType) throws LogicException, SystemException {
        Map<String,Object> resultMap = new HashMap<>();
        try{
            HTPowerBean paramBean = new HTPowerBean();
            //权限模块所属
            paramBean.setModuleBelong(businessType);
            //任务实体信息
            HTTaskBean taskBean = htTaskDao.excSearchTaskDetailInfoByTaskId(businessId);
            //任务模块所属的所用权限
            List<HTPowerBean> htPowerBeans = htPowerDao.excSearchUserWorkPowerList(paramBean);
            //遍历筛查规则
            resultMap.putAll(this.excSearchUserPower(htPowerBeans, loginUserInfo, taskBean));
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }


    /**
     * 查询用户当前投标任务所含权限
     *
     * @return 权限信息
     */
    public Map<String,Object> excSearchUserTenderTaskPowerMap(HTUserVO loginUserInfo,String businessId,
                                                              String businessType) throws LogicException, SystemException {
        Map<String,Object> resultMap = new HashMap<>();
        try{
            HTPowerBean paramBean = new HTPowerBean();
            //权限模块所属
            paramBean.setModuleBelong(businessType);
            //投标任务实体信息
            HTTenderTaskBean TenderTaskBean = htTenderTaskDao.excSearchTenderTaskInfoById(businessId);
            //投标任务模块所属的所用权限
            List<HTPowerBean> htPowerBeans = htPowerDao.excSearchUserWorkPowerList(paramBean);
            //遍历筛查规则
            resultMap.putAll(this.excSearchUserPower(htPowerBeans,loginUserInfo,TenderTaskBean));
        } catch (LogicException lex) {
            throw lex;
        } catch (SystemException sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }



    /**
     * 查询用户当前请款单所含权限
     *
     * @return 权限信息
     */
    public Map<String,Object> excSearchUserRequestMenuPowerMap(HTUserVO loginUserInfo,String businessId
            ,String businessType) throws LogicException, SystemException {

        Map<String,Object> resultMap = new HashMap<>();
        try{
            HTPowerBean paramBean = new HTPowerBean();
            //权限模块所属
            paramBean.setModuleBelong(businessType);
            HTRequestMenuBean requestMenuParamBean = new HTRequestMenuBean();
            //请款单ID
            requestMenuParamBean.setRequestMenuId(businessId);
            //请款单实体信息
            HTRequestMenuBean requestMenuBean = htRequestMenuDao.excSearchRequestMenuInfoById(requestMenuParamBean);
            //请款单模块所属的所用权限
            List<HTPowerBean> htPowerBeans = htPowerDao.excSearchUserWorkPowerList(paramBean);
            //遍历筛查规则
            resultMap.putAll(this.excSearchUserPower(htPowerBeans,loginUserInfo,requestMenuBean));
        } catch (LogicException lex) {
            throw lex;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    /**
     * 遍历筛查规则
     */
    private Map<String,Object> excSearchUserPower(List<HTPowerBean> htPowerBeans
            ,HTUserVO loginUserInfo ,Object object) throws LogicException, SystemException {

        Map<String,Object> resultMap = new HashMap<>();
        try{
            //遍历模块涉及权限
            for (HTPowerBean htPowerBean : htPowerBeans){
                //使用反射得到getDepartmentId方法(获取部门ID方法)
                Method getDepartmentId = object.getClass().getMethod("getDepartmentId");
                //获取部门ID
                String departmentId = (String) getDepartmentId.invoke(object);
                switch (htPowerBean.getPowerRuleKey()){
                    //编撰人及以上规则
                    case Constant.POWER_ROLE_KEY_APPLY_USER_OR_BOSS:
                        //使用反射得到getApplyUserId方法(获取编撰人ID方法)
                        Method getApplyUserId = object.getClass().getMethod("getApplyUserId");
                        //获取编撰人ID
                        String applyUserId = (String) getApplyUserId.invoke(object);
                        if (this.checkUserPower(loginUserInfo.getLoginUserId(),applyUserId,
                                Constant.POWER_ROLE_KEY_APPLY_USER_OR_BOSS,departmentId)){
                            resultMap.put(htPowerBean.getPowerKey(),Constant.POWER_ABLE);
                        }
                        break;
                    //创建人及以上规则
                    case Constant.POWER_ROLE_KEY_CREATE_USER_OR_BOSS:
                        //使用反射得到getCreateUserId方法(获取创建人ID方法)
                        Method getCreateUserId = object.getClass().getSuperclass().getMethod("getCreateUserId");
                        //获取创建人ID
                        String createUserId = (String) getCreateUserId.invoke(object);
                        if (this.checkUserPower(loginUserInfo.getLoginUserId(),createUserId,
                                Constant.POWER_ROLE_KEY_CREATE_USER_OR_BOSS,departmentId)){
                            resultMap.put(htPowerBean.getPowerKey(),Constant.POWER_ABLE);
                        }
                        break;
                    //部门副总经理
                    case Constant.POWER_ROLE_KEY_NMG:
                        if (this.checkUserPower(loginUserInfo.getLoginUserId(),null,
                                Constant.POWER_ROLE_KEY_NMG,departmentId)){
                            resultMap.put(htPowerBean.getPowerKey(),Constant.POWER_ABLE);
                        }
                        break;
                    //组长及上级
                    case Constant.POWER_ROLE_KEY_GROUP_OR_BOSS:
                        if (this.checkUserPower(loginUserInfo.getLoginUserId(),null,
                                Constant.POWER_ROLE_KEY_GROUP_OR_BOSS,departmentId)){
                            resultMap.put(htPowerBean.getPowerKey(),Constant.POWER_ABLE);
                        }
                        break;
                    default:
                        break;
                }
            }
            this.cleanCacheData();
        } catch (LogicException lex) {
            throw lex;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    /**
     *规则校验
     */
    private Boolean checkUserPower(String loginUserId,String checkUserId,String powerRoleKey,
                                   String departmentId) throws LogicException, SystemException {

        try{
            //同一人时
            if (loginUserId.equals(checkUserId)){
                return true;
            }
            //得到登录人所有职位信息
            List<HTJobBean> loginUserJobBeans = this.getHtLoginUserJobBeans(loginUserId,departmentId);
            List<HTJobBean> checkUserJobBeans = new ArrayList<>();
            switch (powerRoleKey){
                case Constant.POWER_ROLE_KEY_APPLY_USER_OR_BOSS:
                    checkUserJobBeans = this.getHtApplyUserJobBeans(checkUserId,departmentId);
                    break;
                case Constant.POWER_ROLE_KEY_CREATE_USER_OR_BOSS:
                    checkUserJobBeans = this.getHtCreateUserJobBeans(checkUserId,departmentId);
                    break;
                case Constant.POWER_ROLE_KEY_NMG:
                    for (HTJobBean loginUserJobBean:loginUserJobBeans){
                        if (Constant.POWER_ROLE_PARAM_NMG.equals(loginUserJobBean.getJobLevel())){
                            return true;
                        }
                    }
                    break;
                case Constant.POWER_ROLE_KEY_GROUP_OR_BOSS:
                    for (HTJobBean loginUserJobBean:loginUserJobBeans){
                        if (Integer.parseInt(Constant.POWER_ROLE_PARAM_GROUP)>=
                                        Integer.parseInt(loginUserJobBean.getJobLevel())){
                            return true;
                        }
                    }
                    break;
                default:
                    break;
            }
            for (HTJobBean loginUserJobBean:loginUserJobBeans){
                for (HTJobBean checkUserJobBean:checkUserJobBeans){
                    if (Integer.parseInt(loginUserJobBean.getJobLevel())<Integer.parseInt(checkUserJobBean.getJobLevel())){
                        return true;
                    }
                }
            }
        } catch (LogicException lex) {
            throw lex;
        } catch (Exception sex) {
            throw new SystemException(sex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return false;
    }
}
