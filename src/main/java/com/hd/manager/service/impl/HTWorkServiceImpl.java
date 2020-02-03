package com.hd.manager.service.impl;

import com.hd.base.enums.*;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.service.CommonCacheService;
import com.hd.common.util.*;
import com.hd.manager.dao.HTTenderTaskDao;
import com.hd.manager.dao.HTWorkDao;
import com.hd.manager.dao.bean.HTDepartmentBean;
import com.hd.manager.dao.bean.HTTenderTaskBean;
import com.hd.manager.dao.bean.HTWorkBean;
import com.hd.manager.service.*;
import com.hd.manager.vo.*;
import com.hd.processes.bench.WorkFlow;
import org.activiti.engine.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class HTWorkServiceImpl extends BaseServiceImpl implements HTWorkService {


    @Resource
    HTWorkBenchService htWorkBenchService;

    @Resource
    TaskService taskService;

    @Resource
    HTTenderTaskDao htTenderTaskDao;

    @Resource
    HTWorkDao htWorkDao;

    @Resource
    HTTenderTaskService htTenderTaskService;

    @Resource
    HTRequestMenuService htRequestMenuService;

    @Resource
    HTTaskService htTaskService;

    @Resource
    HTContractService htContractService;

    @Resource
    CommonCacheService commonCacheService;


    /**
     * 查询待办列表
     */
    @Override
    public Map<String, Object> excSearchPendingList(HTWorkVO paramVO) throws LogicException, SystemException {
        try {
            Map<String, Object> resultMap = new HashMap<>();

            Map<String, Set<String>> listMap = htWorkBenchService.excQueryTask(paramVO.getLoginUserInfo().getLoginUserId(),
                    paramVO.getDepartmentId());

            List<String> htIdList = new ArrayList<>();
            //通用
            if (listMap != null && !listMap.isEmpty()) {
                // 循环遍历map数据
                for (String key : listMap.keySet()) {
                    htIdList.addAll(listMap.get(key));
                }
            }

            //给与前台用以字段展示(要展示的数据列)
            Map<String, Boolean> paramKeyMap = new HashMap<>();

            // 接收返回值的map
            Map<String, Object> retMap = new HashMap<>();

            //业务数据创建时间已传
            paramKeyMap.put("createDate", true);

            // 市场部
            if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_SC)) {

                // 获取map
                paramKeyMap.putAll(this.getSCParamKeyMap());

                // 获取投标任务map
                retMap = this.getTenderTaskPendingListInfo(paramVO, htIdList);

                // 质检部
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_QUALITY)) {

                // 获取map
                paramKeyMap.putAll(this.getQualityParamKeyMap());

                // 获取投标任务map
                retMap = this.getTenderTaskPendingListInfo(paramVO, htIdList);

                // 造价部、招标代理部、项目监理部
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_ZJ) ||
                    paramVO.getDepartmentId().equals(Constant.DEPT_ID_ZBDL) ||
                    paramVO.getDepartmentId().equals(Constant.DEPT_ID_XMJL)) {

                // 获取map
                paramKeyMap.putAll(this.getZjZbdlXmjlParamKeyMap(paramVO));

                // 获取合同map
                retMap = this.getContractListInfo(paramVO, htIdList);

                // 行政部
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_XZ)) {

                // 获取map
                paramKeyMap.putAll(this.getXZParamKeyMap());

                // 获取合同map
                retMap = this.getContractListInfo(paramVO, htIdList);
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_CW)) {//财务

                // 获取map
                paramKeyMap.putAll(this.getCwParamKeyMap());

                // 获取合同map
                retMap = this.getContractListInfo(paramVO, htIdList);
            }

            //设置总条数
            resultMap.put(Constant.DATA_COUNT_KEY, retMap.get(Constant.DATA_COUNT_KEY));
            //设置分页数据
            resultMap.put(Constant.DATA_LIST_KEY, retMap.get(Constant.DATA_LIST_KEY));
            //设置要展示的数据列map
            resultMap.put(Constant.DATA_VO_PARAM_KEY_MAP, paramKeyMap);

            return resultMap;

        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 投标任务待办列表
     *
     * @param paramVO
     * @param htIdList
     * @return
     * @throws SystemException
     */
    private Map<String, Object> getTenderTaskPendingListInfo(HTWorkVO paramVO, List<String> htIdList) {

        Map<String, Object> resultMap = new HashMap<>();

        List<HTWorkVO> htWorkVOList = new ArrayList<>();

        HTWorkBean paramBean = new HTWorkBean();

        //提审人
        paramBean.setUserName(paramVO.getUserName());
        //投标任务名称
        paramBean.setTenderTaskName(paramVO.getTenderTaskName());
        //登录人ID
        paramBean.setUserId(paramVO.getLoginUserInfo().getLoginUserId());
        //当前节点
        paramBean.setDelegateTaskKey(TenderEnum.SubmitVerify.getNodeKey());
        //投标任务ID
        paramBean.setHtIdList(htIdList);
        //部门ID
        paramBean.setDepartmentId(Constant.DEPT_ID_SC);
        // 分页
        paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
        //投标任务待办列表
        List<HTWorkBean> htWorkBeans = htWorkDao.excSearchTenderTaskPendingList(paramBean);

        // 格式转换
        for (HTWorkBean htWorkBean : htWorkBeans) {
            HTWorkVO htWorkVO = new HTWorkVO();
            //投标任务ID
            htWorkVO.setTenderTaskId(htWorkBean.getTenderTaskId());
            //投标任务名称
            htWorkVO.setTenderTaskName(htWorkBean.getTenderTaskName());
            //合同id
            htWorkVO.setContractId(htWorkBean.getContractId());
            //合同编号
            htWorkVO.setContractNum(htWorkBean.getProjectNum() + "-" + htWorkBean.getContractNum());
            //开标时间
            htWorkVO.setStartDate(DateToolUtils.convertDateFormat(htWorkBean.getStartDate(), DateToolUtils.yyyy_MM_dd));
            //序号
            htWorkVO.setSortNum(htWorkBean.getSortNum());
            //部门ID
            htWorkVO.setDepartmentId(htWorkBean.getDepartmentId());
            //主提审人名称
            htWorkVO.setUserName(htWorkBean.getUserName());
            //业务线
            htWorkVO.setDepartmentName(htWorkBean.getDepartmentName());
            //项目id
            htWorkVO.setProjectId(htWorkBean.getProjectId());

            //委托任务id
            htWorkVO.setDelegateTaskId(htWorkBean.getDelegateTaskId());
            //委托任务Key
            htWorkVO.setDelegateTaskKey(htWorkBean.getCurrentStateName());

            //数据创建时间
            htWorkVO.setCreateDate(DateToolUtils.convertDateFormat(htWorkBean.getCreateDate(), DateToolUtils.yyyy_MM_dd));

            //业务id
            htWorkVO.setBusinessId(htWorkBean.getBusinessId());

            //查询标识
            htWorkVO.setCheckSign(htWorkBean.getCheckSign());

            // 根据当前流程节点，获取当前的待办信息是否需要通过
            String isPass = "0";
            if (TenderEnum.ManagerVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TenderEnum.QualityVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TenderEnum.GenerManagerVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || WriteContractEnum.Signer.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TaskEnum.ArchiveVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())) {
                isPass = "1";
            }
            htWorkVO.setIsPass(isPass);

            // 数据来源（“0”合同 “1-1”成果文件(包括归档审核) “1-2”A3阶段 “1-3”重新编辑 “2”投标任务 “3”请款单）
            htWorkVO.setSourceType(htWorkBean.getSourceType());

            // 提交节点
            htWorkVO.setSubmitNode(htWorkBean.getSubmitNode());

            // 更新时间
            htWorkVO.setUpdateDate(DateToolUtils.convertDateFormat(htWorkBean.getUpdateDate(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            htWorkVOList.add(htWorkVO);
        }

        // 待办投标任务总数
        Integer count = htWorkDao.excSearchTenderTaskPendingCount(paramBean);

        // 设置总条数
        resultMap.put(Constant.DATA_COUNT_KEY, count);

        // 设置分页数据
        resultMap.put(Constant.DATA_LIST_KEY, htWorkVOList);

        return resultMap;

    }

    /**
     * 查询合同列表信息
     *
     * @param paramVO
     * @param htIdList
     * @return
     * @throws SystemException
     */
    private Map<String, Object> getContractListInfo(HTWorkVO paramVO, List<String> htIdList) {

        Map<String, Object> resultMap = new HashMap<>();

        List<HTWorkVO> htWorkVOList = new ArrayList<>();

        HTWorkBean paramBean = new HTWorkBean();

        // 项目编号
        paramBean.setProjectNum(StringToolUtils
                .convertNullObjectToString(paramVO.getProjectNum()));

        // 用户名
        paramBean.setUserName(StringToolUtils
                .convertNullObjectToString(paramVO.getUserName()));

        //登录人ID
        paramBean.setUserId(paramVO.getLoginUserInfo().getLoginUserId());

        //当前节点 项目监理
        if (Constant.DEPT_ID_XMJL.equals(paramVO.getDepartmentId())) {
            paramBean.setDelegateTaskKey(ArchiveEnum.SubmitArchive.getNodeKey());
        } else {
            paramBean.setDelegateTaskKey(WriteContractEnum.SubmitVerify.getNodeKey());
        }

        // 合同ID list
        paramBean.setHtIdList(htIdList);

        // 部门ID 不需要传入此参数了
        paramBean.setDepartmentId(paramVO.getDepartmentId());

        // 分页
        paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

        // 合同列表
        List<HTWorkBean> htWorkBeans = htWorkDao.excSearchContractListInfo(paramBean);

        // 格式转换
        for (HTWorkBean htWorkBean : htWorkBeans) {
            HTWorkVO htWorkVO = new HTWorkVO();

            // 所属项目id
            htWorkVO.setProjectId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getProjectId()));

            // 项目编号
            htWorkVO.setProjectNum(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getProjectNum()));

            // 合同ID
            htWorkVO.setContractId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getContractId()));

            // 合同编号(项目编号-合同编号)
            htWorkVO.setContractNum(StringToolUtils.convertNullObjectToString(
                    htWorkBean.getProjectNum() + "-" + htWorkBean.getContractNum()));

            // 创建人名称
            htWorkVO.setCreateUserName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getCreateUserName()));

            // 提审人
            htWorkVO.setUserName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getUserName()));

            // 任务名称
            htWorkVO.setTenderTaskName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getTenderTaskName()));

            // 部门ID
            htWorkVO.setDepartmentId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getDepartmentId()));

            // 部门名称
            htWorkVO.setDepartmentName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getDepartmentName()));

            // 所属高管ID
            htWorkVO.setSeniorManagerId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getSeniorManagerId()));

            // 所属高管名称
            htWorkVO.setSeniorManagerName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getSeniorManagerName()));

            // 委托任务id
            htWorkVO.setDelegateTaskId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getDelegateTaskId()));

            // 委托任务Key
            htWorkVO.setDelegateTaskKey(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getCurrentStateName()));

            // 数据创建时间
            htWorkVO.setCreateDate(DateToolUtils.convertDateFormat(
                    htWorkBean.getCreateDate(), DateToolUtils.yyyy_MM_dd));

            //业务id
            htWorkVO.setBusinessId(htWorkBean.getBusinessId());

            //查询标识
            htWorkVO.setCheckSign(htWorkBean.getCheckSign());

            // 根据当前流程节点，获取当前的待办信息是否需要通过
            String isPass = "0";
            if (TenderEnum.ManagerVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TenderEnum.QualityVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TenderEnum.GenerManagerVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || WriteContractEnum.Signer.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TaskEnum.ArchiveVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())) {
                isPass = "1";
            }
            htWorkVO.setIsPass(isPass);

            // 数据来源
            htWorkVO.setSourceType(htWorkBean.getSourceType());

            // 提交节点
            htWorkVO.setSubmitNode(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getSubmitNode()));

            // 更新时间
            htWorkVO.setUpdateDate(DateToolUtils.convertDateFormat(htWorkBean.getUpdateDate(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            htWorkVOList.add(htWorkVO);
        }

        // 合同总数
        Integer count = htWorkDao.excSearchContractListCount(paramBean);

        // 设置总条数
        resultMap.put(Constant.DATA_COUNT_KEY, count);

        // 设置分页数据
        resultMap.put(Constant.DATA_LIST_KEY, htWorkVOList);

        return resultMap;

    }

    /**
     * 要展示的数据列(质检部)
     */
    private Map<String, Boolean> getQualityParamKeyMap() {
        //给与前台用以字段展示(要展示的数据列)
        Map<String, Boolean> paramKeyMap = new HashMap<>();

        //合同ID已传
        paramKeyMap.put("contractId", true);
        //合同编号已传
        paramKeyMap.put("contractNum", true);
        //部门名称已传
        paramKeyMap.put("departmentName", true);
        //文件类型已传
        paramKeyMap.put("docType", true);
        //投标任务ID已传
        paramKeyMap.put("tenderTaskId", true);
        //投标任务名称已传
        paramKeyMap.put("tenderTaskName", true);
        //发起人已传
        paramKeyMap.put("userName", true);
        //待办跳转详情已传
        paramKeyMap.put("pending", true);
        //项目编号已传
        paramKeyMap.put("projectNum", true);
        //项目名称已传
        paramKeyMap.put("projectName", true);
        //合同名称已传
        paramKeyMap.put("contractName", true);
        //合作公司已传
        paramKeyMap.put("cooperateCompany", true);
        //流转时间已传
        paramKeyMap.put("handleDate", true);
        //流转人已传
        paramKeyMap.put("contractCreateUserName", true);
        //流转时间已传
        paramKeyMap.put("contractCreateDate", true);
        //业务类型已传
        paramKeyMap.put("counselTypePeriod", true);
        //咨询类别已传
        paramKeyMap.put("counselType", true);

        return paramKeyMap;
    }

    /**
     * 要展示的数据列(市场部)
     */
    private Map<String, Boolean> getSCParamKeyMap() {
        //给与前台用以字段展示(要展示的数据列)
        Map<String, Boolean> paramKeyMap = new HashMap<>();

        //投标任务ID已传
        paramKeyMap.put("tenderTaskId", true);
        //任务名称已传
        paramKeyMap.put("tenderTaskName", true);
        //开标时间已传
        paramKeyMap.put("startDate", true);
        //序号已传
        paramKeyMap.put("sortNum", true);
        //发起人已传
        paramKeyMap.put("userName", true);
        //待办跳转详情已传
        paramKeyMap.put("pending", true);

        return paramKeyMap;
    }

    /**
     * 要展示的数据列(行政部)
     */
    private Map<String, Boolean> getXZParamKeyMap() {
        //给与前台用以字段展示(要展示的数据列)
        Map<String, Boolean> paramKeyMap = new HashMap<>();

        // 项目id
        paramKeyMap.put("projectId", true);
        //合同ID已传
        paramKeyMap.put("contractId", true);
        //合同编号已传
        paramKeyMap.put("contractNum", true);
        //任务名称已传
        paramKeyMap.put("tenderTaskName", true);
        //部门已传
        paramKeyMap.put("departmentName", true);
        //提审人已传
        paramKeyMap.put("userName", true);
        //任务已传
        paramKeyMap.put("pending", true);
        //项目编号已传
        paramKeyMap.put("projectNum", true);
        //项目名称已传
        paramKeyMap.put("projectName", true);
        //合同名称已传
        paramKeyMap.put("contractName", true);
        //合作公司已传
        paramKeyMap.put("cooperateCompany", true);
        //流转时间已传
        paramKeyMap.put("handleDate", true);
        //合同创建人已传
        paramKeyMap.put("contractCreateUserName", true);
        //合同创建时间已传
        paramKeyMap.put("contractCreateDate", true);
        //业务类型已传
        paramKeyMap.put("counselTypePeriod", true);
        //咨询类别已传
        paramKeyMap.put("counselType", true);

        return paramKeyMap;
    }

    /**
     * 要展示的数据列（造价部、招标代理部、项目监理部）
     */
    private Map<String, Boolean> getZjZbdlXmjlParamKeyMap(HTWorkVO paramVO) {
        //给与前台用以字段展示(要展示的数据列)
        Map<String, Boolean> paramKeyMap = new HashMap<>();
        //项目id
        paramKeyMap.put("projectId", true);
        //项目编号已传
        paramKeyMap.put("projectNum", true);
        //项目名称已传
        paramKeyMap.put("projectName", true);
        //合同ID已传
        paramKeyMap.put("contractId", true);
        //合同编号已传
        paramKeyMap.put("contractNum", true);
        //合同名称已传
        paramKeyMap.put("contractName", true);
        //合同创建人已传
        paramKeyMap.put("contractCreateUserName", true);
        //合同创建时间已传
        paramKeyMap.put("contractCreateDate", true);
        //合作公司已传
        paramKeyMap.put("cooperateCompany", true);
        //流转时间已传
        paramKeyMap.put("handleDate", true);
        //创建人
        paramKeyMap.put("createUserName", true);
        //任务名称已传
        paramKeyMap.put("tenderTaskName", true);
        //业务类型已传
        paramKeyMap.put("counselTypePeriod", true);
        //咨询类别已传
        paramKeyMap.put("counselType", true);
        //任务已传
        paramKeyMap.put("pending", true);

        return paramKeyMap;
    }

    /**
     * 要展示的数据列（财务）
     */
    private Map<String, Boolean> getCwParamKeyMap() {
        //给与前台用以字段展示(要展示的数据列)
        Map<String, Boolean> paramKeyMap = new HashMap<>();

        //合同ID已传
        paramKeyMap.put("contractId", true);
        //合同编号已传
        paramKeyMap.put("contractNum", true);
        //所属高管ID已传
        paramKeyMap.put("seniorManagerId", true);
        //所属高管名称已传
        paramKeyMap.put("seniorManagerName", true);
        //提审人ID已传
        paramKeyMap.put("applyUserId", true);
        //提审人名称已传
        paramKeyMap.put("userName", true);
        //流程状态名称已传
        paramKeyMap.put("pending", true);
        //创建时间已传
        paramKeyMap.put("createDate", true);
        //项目编号已传
        paramKeyMap.put("projectNum", true);
        //项目名称已传
        paramKeyMap.put("projectName", true);
        //合同名称已传
        paramKeyMap.put("contractName", true);
        //合作公司已传
        paramKeyMap.put("cooperateCompany", true);
        //流转时间已传
        paramKeyMap.put("handleDate", true);
        //合同创建人已传
        paramKeyMap.put("contractCreateUserName", true);
        //合同创建时间已传
        paramKeyMap.put("contractCreateDate", true);
        //业务类型已传
        paramKeyMap.put("counselTypePeriod", true);
        //咨询类别已传
        paramKeyMap.put("counselType", true);

        return paramKeyMap;
    }

    /**
     * 查询所有部门
     */
    @Override
    public Map<String, Object> excSearchAllDepartmentList(HTWorkVO paramVO) throws LogicException, SystemException {

        Map<String, Object> resultMap = new HashMap<>();
        try {

            HTDepartmentBean paramBean = new HTDepartmentBean();

            // 设置部门ID（高管业务线ID，待办列表时需排除）
            paramBean.setDepartmentId(Constant.DEPT_ID_GG);
            // 设置人员ID
            paramBean.setUserId(paramVO.getLoginUserInfo().getLoginUserId());
//            List<HTDepartmentBean> htDepartmentBeans = htTenderTaskDao.excSearchAllDepartmentList(paramBean);

            String loginUserId  = paramVO.getLoginUserInfo().getLoginUserId();
            //查询用户所属业务线有哪些
            List<Map<String, Object>> userInfoCache = commonCacheService.getUserInfoCache(loginUserId);
            //用户所属业务线(部门)
            List<HTDepartmentBean> htDepartmentBeans = new ArrayList<>();


            for (Map<String, Object> map :
                    userInfoCache) {
                // 当前用户为肖继伟时，只显示造价、招标、项目监理
                if(("0091dbf2-bfe4-11e9-b448-64006a07180f").equals(loginUserId)){
                    //没有/不展示高管业务线、市场、行政、财务
                    if (Constant.DEPT_ID_GG.equals((String) map.get("involved_department"))
                            ||Constant.DEPT_ID_SC.equals((String) map.get("involved_department"))
                            ||Constant.DEPT_ID_XZ.equals((String) map.get("involved_department"))
                            ||Constant.DEPT_ID_CW.equals((String) map.get("involved_department"))
                            ||Constant.DEPT_ID_QUALITY.equals((String) map.get("involved_department"))) {
                        continue;
                    }
                }
                if (Constant.DEPT_ID_GG.equals((String) map.get("involved_department"))) {
                    continue;
                }
                HTDepartmentBean htDepartmentBean = new HTDepartmentBean();
                //部门ID
                htDepartmentBean.setDepartmentId((String) map.get("involved_department"));
                //部门名称
                htDepartmentBean.setDepartmentName((String) map.get("department_name"));
                htDepartmentBeans.add(htDepartmentBean);
            }

            Integer pendingCount = 0;
            //查询部门未解决待办标识
            Map<String, Object> deptPropMap = this.excSearchAllDeptPendingFlag(paramVO, htDepartmentBeans, pendingCount);
            Map<String, Boolean> booleanMap = (Map<String, Boolean>) deptPropMap.get("deptFlagMap");
            for (HTDepartmentBean htDepartmentBean :
                    htDepartmentBeans) {
                if (booleanMap.get(htDepartmentBean.getDepartmentId())) {
                    htDepartmentBean.setDepartmentBtnFlag(Constant.APPROVED);
                }
            }
            //查询所有部门
            resultMap.put(Constant.DATA_LIST_KEY, htDepartmentBeans);
            //待办个数
            resultMap.put(Constant.DATA_COUNT_KEY, deptPropMap.get(Constant.DATA_COUNT_KEY));
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 查询已完成列表
     */
    @Override
    public Map<String, Object> excSearchCompletedList(HTWorkVO paramVO) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            //用以告诉前台哪些属性有值用于展示
            Map<String, Boolean> paramKeyMap = new HashMap<>();
            if (null != paramVO.getSearchType() && !paramVO.getSearchType().isEmpty()) {
                // 投标任务
                if (paramVO.getSearchType().equals(Constant.SEARCH_TYPE_TENDER_TASK)) {
                    //投标任务主键已传
                    paramKeyMap.put("tenderTaskId", true);
                    //投标任务名称已传
                    paramKeyMap.put("tenderTaskName", true);
                    //开标时间已传
                    paramKeyMap.put("startDate", true);
                    //序号已传
                    paramKeyMap.put("sortNum", true);
                    //用户名称已传
                    paramKeyMap.put("userName", true);
                    //创建时间已传
                    paramKeyMap.put("createDate", true);
                    List<HTTenderTaskVO> htTenderTaskVOList = new ArrayList<>();
                    Map<String, Set<String>> listMap = htWorkBenchService.excQueryTaskCompleted(paramVO.getLoginUserInfo().getLoginUserId());
                    List<String> htTenderTaskIdList = new ArrayList<>();
                    if (null != listMap && !listMap.isEmpty()) {
                        if (listMap.containsKey("Tender")) {
                            //取出投标任务相关ID
                            htTenderTaskIdList.addAll(listMap.get("Tender"));
                        }
                    }
                    HTWorkBean paramBean = new HTWorkBean();
                    //投标任务时间
                    paramBean.setTenderTaskName(paramVO.getTenderTaskName());
                    //用户ID
                    paramBean.setUserId(paramVO.getLoginUserInfo().getLoginUserId());
                    //当前节点
                    paramBean.setDelegateTaskKey(TenderEnum.SubmitVerify.getNodeKey());
                    //投标任务ID集合
                    if (null != htTenderTaskIdList && htTenderTaskIdList.size() > 0) {
                        paramBean.setTenderTaskIds(htTenderTaskIdList);
                    }
                    // 分页
                    paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
                    //已办投标任务列表
                    List<HTWorkBean> htWorkBeans = htWorkDao.excSearchTenderTaskCompletedList(paramBean);
                    //格式转换
                    for (HTWorkBean htWorkBean : htWorkBeans) {
                        HTTenderTaskVO htTenderTaskVO = new HTTenderTaskVO();
                        //投标任务ID
                        htTenderTaskVO.setTenderTaskId(htWorkBean.getTenderTaskId());
                        //投标任务名称
                        htTenderTaskVO.setTenderTaskName(htWorkBean.getTenderTaskName());
                        //开标时间
                        htTenderTaskVO.setStartDate(DateToolUtils.convertDateFormat(htWorkBean.getStartDate(), DateToolUtils.yyyy_MM_dd));
                        //序号
                        htTenderTaskVO.setSortNum(htWorkBean.getSortNum());
                        //用户名称
                        htTenderTaskVO.setUserName(htWorkBean.getUserName());
                        //创建时间
                        htTenderTaskVO.setCreateDate(DateToolUtils.convertDateFormat(htWorkBean.getCreateDate(), DateToolUtils.yyyy_MM_dd));
                        htTenderTaskVOList.add(htTenderTaskVO);
                    }
                    Integer count = htWorkDao.excSearchTenderTaskCompletedCount(paramBean);
                    resultMap.put(Constant.DATA_COUNT_KEY, count);
                    resultMap.put(Constant.DATA_LIST_KEY, htTenderTaskVOList);
                    resultMap.put(Constant.DATA_VO_PARAM_KEY_MAP, paramKeyMap);

                    // 合同
                } else if (paramVO.getSearchType().equals(Constant.SEARCH_TYPE_CONTRACT)) {

                    //项目id已传
                    paramKeyMap.put("projectId", true);
                    //项目编号已传
                    paramKeyMap.put("projectNum", true);
                    //项目名称已传
                    paramKeyMap.put("projectName", true);
                    //合同ID已传
                    paramKeyMap.put("contractId", true);
                    //合同编号已传
                    paramKeyMap.put("contractNum", true);
                    //合同名称已传
                    paramKeyMap.put("contractName", true);
                    //部门已传
                    paramKeyMap.put("departmentName", true);
                    //合同创建人已传
                    paramKeyMap.put("contractCreateUserName", true);
                    //合同创建时间已传
                    paramKeyMap.put("contractCreateDate", true);

                    List<HTWorkVO> htWorkVOList = new ArrayList<>();

                    // 包含合同id list
                    Map<String, Set<String>> listMap = htWorkBenchService.excQueryTaskCompleted(paramVO.getLoginUserInfo().getLoginUserId());

                    List<String> htContractIdList = new ArrayList<>();

                    HTWorkBean paramBean = new HTWorkBean();

                    if (null != listMap && !listMap.isEmpty()) {

                        // 遍历mao，将map中返回的合同ID，放入集合中
                        for (String key : listMap.keySet()) {
                            htContractIdList.addAll(listMap.get(key));
                        }

                        //合同ID集合
                        if (!ListToolUtils.isEmpty(htContractIdList)) {
                            paramBean.setContractIdList(htContractIdList);
                        }

                    }

                    //项目编号
                    paramBean.setProjectNum(StringToolUtils
                            .convertNullObjectToString(paramVO.getProjectNum()));

                    //项目名称
                    paramBean.setProjectName(StringToolUtils
                            .convertNullObjectToString(paramVO.getProjectName()));

                    //合同编号
                    paramBean.setContractNum(StringToolUtils
                            .convertNullObjectToString(paramVO.getContractNum()));

                    //用户ID
                    paramBean.setUserId(StringToolUtils.convertNullObjectToString(
                            paramVO.getLoginUserInfo().getLoginUserId()));

                    //当前节点
                    paramBean.setDelegateTaskKey(TenderEnum.SubmitVerify.getNodeKey());

                    // 分页
                    paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

                    //已办合同列表
                    List<HTWorkBean> htWorkBeans = htWorkDao.excSearchContractCompletedList(paramBean);

                    //已办合同列表总数
                    Integer count = htWorkDao.excSearchContractCompletedCount(paramBean);

                    //格式转换
                    for (HTWorkBean htWorkBean : htWorkBeans) {
                        HTWorkVO htWorkVO = new HTWorkVO();

                        // 项目id
                        htWorkVO.setProjectId(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getProjectId()));

                        // 项目编号
                        htWorkVO.setProjectNum(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getProjectNum()));

                        // 项目名称
                        htWorkVO.setProjectName(StringToolUtils
                                .convertNullObjectToString(StringToolUtils.isNull(paramVO.getProjectName()) ? "-" : paramVO.getProjectName()));

                        // 合同id
                        htWorkVO.setContractId(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getContractId()));

                        if (StringToolUtils.isNull(htWorkBean.getContractNum()) || StringToolUtils.isNull(htWorkBean.getProjectNum())) {
                            // 合同编号(项目编号-合同编号)
                            htWorkVO.setContractNum(StringToolUtils.convertNullObjectToString(
                                    htWorkBean.getProjectNum() + htWorkBean.getContractNum()));
                        } else {
                            // 合同编号(项目编号-合同编号)
                            htWorkVO.setContractNum(StringToolUtils.convertNullObjectToString(
                                    htWorkBean.getProjectNum() + "-" + htWorkBean.getContractNum()));
                        }
                        // 合同名称
                        htWorkVO.setContractName(StringToolUtils.convertNullObjectToString(StringToolUtils.isNull(paramVO.getContractName()) ? "-" : paramVO.getContractName()));

                        // 部门名称
                        htWorkVO.setDepartmentName(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getDepartmentName()));

                        //合同创建人
                        htWorkVO.setContractCreateUserName(htWorkBean.getContractCreateUserName());

                        // 合同创建时间
                        htWorkVO.setContractCreateDate(DateToolUtils.convertDateFormat(
                                htWorkBean.getContractCreateDate(), DateToolUtils.YYYY_MM_DD_HH_MM));

                        htWorkVOList.add(htWorkVO);
                    }

                    resultMap.put(Constant.DATA_COUNT_KEY, count);
                    resultMap.put(Constant.DATA_LIST_KEY, htWorkVOList);
                    resultMap.put(Constant.DATA_VO_PARAM_KEY_MAP, paramKeyMap);

                }
            }
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 查询经手列表
     */
    @Override
    public Map<String, Object> excSearchHandleList(HTWorkVO paramVO) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            //用以告诉前台哪些属性有值用于展示
            Map<String, Boolean> paramKeyMap = new HashMap<>();
            if (null != paramVO.getSearchType() && !paramVO.getSearchType().isEmpty()) {
                if (paramVO.getSearchType().equals(Constant.SEARCH_TYPE_TENDER_TASK)) {
                    //投标任务主键已传
                    paramKeyMap.put("tenderTaskId", true);
                    //投标任务名称已传
                    paramKeyMap.put("tenderTaskName", true);
                    //投标任务状态已传
                    paramKeyMap.put("tenderTaskSts", true);
                    //开标时间已传
                    paramKeyMap.put("startDate", true);
                    //序号已传
                    paramKeyMap.put("sortNum", true);
                    //用户名称已传
                    paramKeyMap.put("userName", true);
                    //创建时间已传
                    paramKeyMap.put("createDate", true);
                    List<HTTenderTaskVO> htTenderTaskVOList = new ArrayList<>();
                    Map<String, Set<String>> listMap = htWorkBenchService.excQueryTaskCompleted(paramVO.getLoginUserInfo().getLoginUserId());
                    List<String> htTenderTaskIdList = new ArrayList<>();
                    if (null != listMap && !listMap.isEmpty()) {
                        // 遍历mao，将map中返回的合同ID，放入集合中
                        for (String key : listMap.keySet()) {
                            htTenderTaskIdList.addAll(listMap.get(key));
                        }

                    }
                    HTWorkBean paramBean = new HTWorkBean();
                    //投标任务时间
                    paramBean.setTenderTaskName(paramVO.getTenderTaskName());
                    //用户ID
                    paramBean.setUserId(paramVO.getLoginUserInfo().getLoginUserId());
                    //当前节点
                    paramBean.setDelegateTaskKey(TenderEnum.SubmitVerify.getNodeKey());
                    //判断投标任务ID集合是否为空
                    if (null != htTenderTaskIdList && htTenderTaskIdList.size() > 0) {
                        paramBean.setTenderTaskIds(htTenderTaskIdList);
                    }
                    // 分页
                    paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
                    //经手投标任务列表
                    List<HTWorkBean> htWorkBeanList = htWorkDao.excSearchTenderTaskHandleList(paramBean);
                    //格式转换
                    for (HTWorkBean htWorkBean : htWorkBeanList) {
                        HTTenderTaskVO htTenderTaskVO = new HTTenderTaskVO();
                        //投标任务ID
                        htTenderTaskVO.setTenderTaskId(htWorkBean.getTenderTaskId());
                        //投标任务名称
                        htTenderTaskVO.setTenderTaskName(htWorkBean.getTenderTaskName());
                        //投标任务状态(0：未开标 1：未中标 2：已中标)
                        htTenderTaskVO.setTenderTaskSts(htWorkBean.getTenderTaskSts());
                        //开标时间
                        htTenderTaskVO.setStartDate(DateToolUtils.convertDateFormat(htWorkBean.getStartDate(), DateToolUtils.yyyy_MM_dd));
                        //序号
                        htTenderTaskVO.setSortNum(htWorkBean.getSortNum());
                        //用户名称
                        htTenderTaskVO.setUserName(htWorkBean.getUserName());
                        //创建时间
                        htTenderTaskVO.setCreateDate(DateToolUtils.convertDateFormat(htWorkBean.getCreateDate(), DateToolUtils.yyyy_MM_dd));
                        htTenderTaskVOList.add(htTenderTaskVO);
                    }
                    //经手投标任务总数
                    Integer count = htWorkDao.excSearchTenderTaskHandleCount(paramBean);
                    resultMap.put(Constant.DATA_COUNT_KEY, count);
                    resultMap.put(Constant.DATA_LIST_KEY, htTenderTaskVOList);
                    resultMap.put(Constant.DATA_VO_PARAM_KEY_MAP, paramKeyMap);

                    // 合同
                } else if (paramVO.getSearchType().equals(Constant.SEARCH_TYPE_CONTRACT)) {

                    //项目id
                    paramKeyMap.put("projectId", true);
                    //项目编号
                    paramKeyMap.put("projectNum", true);
                    //项目名称
                    paramKeyMap.put("projectName", true);
                    //合同ID已传
                    paramKeyMap.put("contractId", true);
                    //合同编号已传
                    paramKeyMap.put("contractNum", true);
                    //项目名称
                    paramKeyMap.put("contractName", true);
                    //部门已传
                    paramKeyMap.put("departmentName", true);
                    //项目结束人
                    paramKeyMap.put("endProjectUserName", true);
                    //项目结束时间
                    paramKeyMap.put("endDate", true);
                    //合同创建人已传
                    paramKeyMap.put("contractCreateUserName", true);
                    //合同创建时间已传
                    paramKeyMap.put("contractCreateDate", true);

                    List<HTWorkVO> htWorkVOList = new ArrayList<>();

                    // 包含合同id list
                    Map<String, Set<String>> listMap = htWorkBenchService
                            .excQueryTaskCompleted(paramVO.getLoginUserInfo().getLoginUserId());

                    List<String> htContractIdList = new ArrayList<>();

                    HTWorkBean paramBean = new HTWorkBean();

                    if (null != listMap && !listMap.isEmpty()) {
                        if (listMap.containsKey("WriteContract")) {
                            //取出合同ID
                            htContractIdList.addAll(listMap.get("WriteContract"));
                        }

                        //合同ID集合
                        if (!ListToolUtils.isEmpty(htContractIdList)) {
                            paramBean.setContractIdList(htContractIdList);
                        }

                    }

                    //项目编号
                    paramBean.setProjectNum(StringToolUtils
                            .convertNullObjectToString(paramVO.getProjectNum()));

                    //项目名称
                    paramBean.setProjectName(StringToolUtils
                            .convertNullObjectToString(paramVO.getProjectName()));

                    //合同编号
                    paramBean.setContractNum(StringToolUtils
                            .convertNullObjectToString(paramVO.getContractNum()));

                    //用户ID
                    paramBean.setUserId(StringToolUtils.convertNullObjectToString(
                            paramVO.getLoginUserInfo().getLoginUserId()));

                    //当前节点
                    paramBean.setDelegateTaskKey(TenderEnum.SubmitVerify.getNodeKey());

                    // 分页
                    paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

                    //经手合同列表
                    List<HTWorkBean> htWorkBeans = htWorkDao.excSearchContractHandleList(paramBean);

                    //经手合同列表总数
                    Integer count = htWorkDao.excSearchContractHandleCount(paramBean);

                    //格式转换
                    for (HTWorkBean htWorkBean : htWorkBeans) {
                        HTWorkVO htWorkVO = new HTWorkVO();

                        // 项目id
                        htWorkVO.setProjectId(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getProjectId()));

                        // 项目编号
                        htWorkVO.setProjectNum(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getProjectNum()));

                        // 项目名称
                        htWorkVO.setProjectName(StringToolUtils
                                .convertNullObjectToString(StringToolUtils.isNull(htWorkBean.getProjectName()) ? "-" : htWorkBean.getProjectName()));

                        // 合同id
                        htWorkVO.setContractId(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getContractId()));

                        // 合同编号
                        htWorkVO.setContractNum(StringToolUtils.convertNullObjectToString(
                                htWorkBean.getProjectNum() + "-" + htWorkBean.getContractNum()));

                        // 合同名称
                        htWorkVO.setContractName(StringToolUtils
                                .convertNullObjectToString(StringToolUtils.isNull(htWorkBean.getContractName()) ? "-" : htWorkBean.getContractName()));

                        // 部门名称
                        htWorkVO.setDepartmentName(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getDepartmentName()));

                        // 项目结束人
                        htWorkVO.setEndProjectUserName(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getEndProjectUserName()));

                        // 项目结束时间
                        htWorkVO.setEndDate(DateToolUtils.convertDateFormat(
                                htWorkBean.getEndDate(), DateToolUtils.yyyy_MM_dd));

                        //合同创建人
                        htWorkVO.setContractCreateUserName(htWorkBean.getContractCreateUserName());

                        // 合同创建时间
                        htWorkVO.setContractCreateDate(DateToolUtils.convertDateFormat(
                                htWorkBean.getContractCreateDate(), DateToolUtils.YYYY_MM_DD_HH_MM));

                        htWorkVOList.add(htWorkVO);
                    }

                    resultMap.put(Constant.DATA_COUNT_KEY, count);
                    resultMap.put(Constant.DATA_LIST_KEY, htWorkVOList);
                    resultMap.put(Constant.DATA_VO_PARAM_KEY_MAP, paramKeyMap);
                }
            }
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询待办列表（2019.11.03 新）
     *
     * @param paramVO
     * @return 待办列表
     * @throws LogicException
     * @throws SystemException
     */
    @Override
    public Map<String, Object> excSearchNewPendingList2(HTWorkVO paramVO) throws LogicException, SystemException {
        try {
            Map<String, Object> resultMap = new HashMap<>();

            HTWorkBean paramBean = new HTWorkBean();
            if (StringToolUtils.isNull(paramVO.getDepartmentId())) {
                HTDepartmentBean departmentBean = new HTDepartmentBean();
                departmentBean.setDepartmentId(Constant.DEPT_ID_GG);
                // 设置人员ID
                departmentBean.setUserId(paramVO.getLoginUserInfo().getLoginUserId());

                List<Map<String, Object>> userInfoCache = commonCacheService.getUserInfoCache(paramVO.getLoginUserInfo().getLoginUserId());
                Map<String, Object> map = userInfoCache.get(0);

                paramVO.setDepartmentId((String) map.get("involved_department"));
            }
            paramBean.setDepartmentId(paramVO.getDepartmentId());
            paramBean.setUserId(paramVO.getLoginUserInfo().getLoginUserId());
            //普通执行人待办列表
            List<HTWorkBean> executorPendingList = new ArrayList<>();
            //节点
            paramBean.setDelegateTaskKey(TenderEnum.SubmitVerify.getNodeKey());
            //查询普通执行人投标任务待办列表
            List<HTWorkBean> executorTenderTaskPendingList = htWorkDao.excSearchExecutorTenderTaskPendingList(paramBean);
            for (HTWorkBean htWorkBean :
                    executorTenderTaskPendingList) {
                htWorkBean.setBusinessType("Tender");
            }
            executorPendingList.addAll(executorTenderTaskPendingList);
            //当前节点 项目监理
            if (Constant.DEPT_ID_XMJL.equals(paramBean.getDepartmentId())) {
                paramBean.setDelegateTaskKey(ArchiveEnum.SubmitArchive.getNodeKey());
            } else {
                paramBean.setDelegateTaskKey(WriteContractEnum.SubmitVerify.getNodeKey());
            }
            //查询普通执行人任务阶段待办列表
            List<HTWorkBean> executorTaskArchivePendingList = htWorkDao.excSearchExecutortaskArchivePendingList(paramBean);
            for (HTWorkBean htWorkBean :
                    executorTaskArchivePendingList) {
                htWorkBean.setBusinessType("Archive");
            }
            executorPendingList.addAll(executorTaskArchivePendingList);
            //查询普通执行人任务待办列表
            List<HTWorkBean> executorTaskPendingList = htWorkDao.excSearchExecutorTaskPendingList(paramBean);
            for (HTWorkBean htWorkBean :
                    executorTaskPendingList) {
                htWorkBean.setBusinessType("Task");
            }
            executorPendingList.addAll(executorTaskPendingList);
            // 根据工作流获取待办信息
            List<WorkFlow> dataList = htWorkBenchService.excQueryTodo(paramVO.getLoginUserInfo().getLoginUserId(), paramVO.getDepartmentId(), executorPendingList);

            // 将待办类别分组
            Set<Object> tmpSet = new HashSet<Object>();
            List<String> ids = new ArrayList<>();
            List<String> tenderTaskIds = new ArrayList<>();
            List<String> contractIds = new ArrayList<>();
            List<String> taskIds = new ArrayList<>();
            List<String> taskPeriodIds = new ArrayList<>();
            List<String> taskReEditIds = new ArrayList<>();
            List<String> taskArchiveIds = new ArrayList<>();
            List<String> requestMenuIds = new ArrayList<>();
            Map<String, Object> pendingProp = new HashMap<>();
            Map<String, Object> handleDateMap = new HashMap<>();
            pendingProp.put("contractIdsFlag", false);
            pendingProp.put("TenderFlag", false);
            pendingProp.put("TaskFlag", false);
            pendingProp.put("Task_A3Flag", false);
            pendingProp.put("CashApplyFlag", false);
            // 遍历
            for (WorkFlow workFlow : dataList) {
                Set<String> bizKey = workFlow.getBizKey();
                ids.addAll(bizKey);
                for (String id :
                        bizKey) {
                    handleDateMap.put(id, workFlow.getHandleDate());
                }
                // 待办类别
                tmpSet.add(workFlow.getProcessDefinitionKey());
                switch (workFlow.getProcessDefinitionKey()) {
                    //合同编撰(使用合同ID)
                    case "WriteContract":
                        //创建项目(使用合同ID)
                    case "CreateProjectProcess":
                        contractIds.addAll(workFlow.getBizKey());
                        pendingProp.put("contractIdsFlag", true);
                        break;
                    //投标(使用投标任务ID)
                    case "Tender":
                        tenderTaskIds.addAll(workFlow.getBizKey());
                        pendingProp.put("TenderFlag", true);
                        break;
                    //任务_成果文件审批(使用任务ID)
                    case "Task":
                        taskIds.addAll(workFlow.getBizKey());
                        pendingProp.put("TaskFlag", true);
                        break;
                    //任务_A3审批(使用阶段ID)
                    case "Task_A3":
                        taskPeriodIds.addAll(workFlow.getBizKey());
                        pendingProp.put("Task_A3Flag", true);
                        break;
                    //任务_重新编辑(使用重新编辑ID)
                    case "Task_Reedit":
                        taskReEditIds.addAll(workFlow.getBizKey());
                        pendingProp.put("Task_ReeditFlag", true);
                        break;
                    //归档(使用归档ID)
                    case "Archive":
                        taskArchiveIds.addAll(workFlow.getBizKey());
                        pendingProp.put("ArchiveFlag", true);
                        break;
                    //请款(使用请款单ID)
                    case "CashApply":
                        requestMenuIds.addAll(workFlow.getBizKey());
                        pendingProp.put("CashApplyFlag", true);
                        break;
                    default:
                        break;
                }
            }

            pendingProp.put("orderList", ids);
            pendingProp.put("contractIds", contractIds);
            pendingProp.put("Tender", tenderTaskIds);
            pendingProp.put("Task", taskIds);
            pendingProp.put("Task_A3", taskPeriodIds);
            pendingProp.put("Task_Reedit", taskReEditIds);
            pendingProp.put("Archive", taskArchiveIds);
            pendingProp.put("CashApply", requestMenuIds);
            pendingProp.put("userId", paramVO.getLoginUserInfo().getLoginUserId());
            pendingProp.put("departmentId", paramVO.getDepartmentId());
            pendingProp.put("tenderTaskName", paramVO.getTenderTaskName());
            pendingProp.put("delegateTaskKey", TenderEnum.SubmitVerify.getNodeKey());
            pendingProp.put("projectNum", paramVO.getProjectNum());
            pendingProp.put("projectName", paramVO.getProjectName());
            pendingProp.put("userName", paramVO.getUserName());
            // 临时变量：页码
            Integer tmpPageNumberInt = NumberToolUtils.convertNullToInteger(paramVO.getPageNumber());
            // 临时变量：单页数据条数
            Integer tmpPageSizeInt = NumberToolUtils.convertNullToInteger(paramVO.getPageSize());
            // 设置分页数据
            pendingProp.put("pageNumber", (tmpPageNumberInt - 1) * tmpPageSizeInt);
            // 设置分页数据
            pendingProp.put("pageSize", tmpPageSizeInt);
            //给与前台用以字段展示(要展示的数据列)
            Map<String, Boolean> paramKeyMap = new HashMap<>();

            // 接收返回值的map
            Map<String, Object> retMap = new HashMap<>();

            //业务数据创建时间已传
            paramKeyMap.put("createDate", true);

            // 市场部
            if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_SC)) {

                // 获取map
                paramKeyMap.putAll(this.getSCParamKeyMap());

                // 获取投标任务map
                retMap = this.getNewTenderTaskPendingListInfo(pendingProp);

                // 质检部
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_QUALITY)) {

                // 获取map
                paramKeyMap.putAll(this.getQualityParamKeyMap());

                // 获取投标任务map
                retMap = this.getNewTenderTaskPendingListInfo(pendingProp);

                // 造价部、招标代理部、项目监理部
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_ZJ) ||
                    paramVO.getDepartmentId().equals(Constant.DEPT_ID_ZBDL) ||
                    paramVO.getDepartmentId().equals(Constant.DEPT_ID_XMJL)) {

                // 获取map
                paramKeyMap.putAll(this.getZjZbdlXmjlParamKeyMap(paramVO));

                // 获取合同map
                retMap = this.getNewContractListInfo(pendingProp);

                // 行政部
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_XZ)) {

                // 获取map
                paramKeyMap.putAll(this.getXZParamKeyMap());

                // 获取合同map
                retMap = this.getNewContractListInfo(pendingProp);
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_CW)) {//财务

                // 获取map
                paramKeyMap.putAll(this.getCwParamKeyMap());

                // 获取合同map
                retMap = this.getNewContractListInfo(pendingProp);
            }
            //设置总条数
            resultMap.put(Constant.DATA_COUNT_KEY, retMap.get(Constant.DATA_COUNT_KEY));

            List<HTWorkVO> htWorkVOList = (List<HTWorkVO>) retMap.get(Constant.DATA_LIST_KEY);

            if (!ListToolUtils.isEmpty(htWorkVOList)) {
                for (HTWorkVO htWorkVO :
                        htWorkVOList) {
                    HTUserVO userVO = (HTUserVO) taskService.getVariable(htWorkVO.getDelegateTaskId(), WorkFlowConstant.KEY_PRE_HANDLER);
                    if (null != userVO) {
                        htWorkVO.setCompilationUserId(userVO.getLoginUserId());
                        htWorkVO.setCompilationUserName(userVO.getUserName());
                    }
                    //流转时间
                    htWorkVO.setHandleDate(DateToolUtils.convertDateFormat((Date) handleDateMap.get(htWorkVO.getCheckSign()), DateToolUtils.YYYY_MM_DD_HH_MM));
                }
            }
            //设置分页数据
            resultMap.put(Constant.DATA_LIST_KEY, htWorkVOList);
            //设置要展示的数据列map
            resultMap.put(Constant.DATA_VO_PARAM_KEY_MAP, paramKeyMap);

            return resultMap;

        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 投标任务待办列表(新)
     *
     * @param pendingProp
     * @return
     * @throws SystemException
     */
    private Map<String, Object> getNewTenderTaskPendingListInfo(Map<String, Object> pendingProp) {

        Map<String, Object> resultMap = new HashMap<>();

        List<HTWorkVO> htWorkVOList = new ArrayList<>();

        //部门ID
        pendingProp.put("departmentId", Constant.DEPT_ID_SC);
        //当前节点
        pendingProp.put("delegateTaskKey", TenderEnum.SubmitVerify.getNodeKey());
        //投标任务待办列表
        List<HTWorkBean> htWorkBeans = htWorkDao.excSearchNewTenderTaskPendingList(pendingProp);

        // 格式转换
        for (HTWorkBean htWorkBean : htWorkBeans) {
            HTWorkVO htWorkVO = new HTWorkVO();
            //投标任务ID
            htWorkVO.setTenderTaskId(htWorkBean.getTenderTaskId());
            //投标任务名称
            htWorkVO.setTenderTaskName(htWorkBean.getTenderTaskName());
            //合同id
            htWorkVO.setContractId(htWorkBean.getContractId());
            //合同编号
            htWorkVO.setContractNum(htWorkBean.getProjectNum() + "-" + htWorkBean.getContractNum());
            // 流程实例ID
            htWorkVO.setProcessInstId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getProcessInstId()));
            //开标时间
            htWorkVO.setStartDate(DateToolUtils.convertDateFormat(htWorkBean.getStartDate(), DateToolUtils.yyyy_MM_dd));
            //序号
            htWorkVO.setSortNum(htWorkBean.getSortNum());
            //部门ID
            htWorkVO.setDepartmentId(htWorkBean.getDepartmentId());
            //主提审人名称
            htWorkVO.setUserName(htWorkBean.getUserName());
            //业务线
            htWorkVO.setDepartmentName(htWorkBean.getDepartmentName());
            //项目id
            htWorkVO.setProjectId(htWorkBean.getProjectId());
            //项目编号
            htWorkVO.setProjectNum(htWorkBean.getProjectNum());
            //项目名称
            htWorkVO.setProjectName(StringToolUtils.isNull(htWorkBean.getProjectName()) ? "-" : htWorkBean.getProjectName());

            // 业务类型
            htWorkVO.setCounselTypePeriod(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getCounselTypePeriod()));

            // 咨询类别
            htWorkVO.setCounselType(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getCounselType()));
            //合同id
            htWorkVO.setContractId(htWorkBean.getContractId());
            if (StringToolUtils.isNull(htWorkBean.getContractNum()) || StringToolUtils.isNull(htWorkBean.getProjectNum())) {
                // 合同编号(项目编号-合同编号)
                htWorkVO.setContractNum(StringToolUtils.convertNullObjectToString(
                        htWorkBean.getProjectNum() + htWorkBean.getContractNum()));
            } else {
                // 合同编号(项目编号-合同编号)
                htWorkVO.setContractNum(StringToolUtils.convertNullObjectToString(
                        htWorkBean.getProjectNum() + "-" + htWorkBean.getContractNum()));
            }
            //合同名称
            htWorkVO.setContractName(StringToolUtils.isNull(htWorkBean.getContractName()) ? "-" : htWorkBean.getContractName());

            // 合作公司
            htWorkVO.setCooperateCompany(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getCooperateCompany()));
            //合同创建人
            htWorkVO.setContractCreateUserName(htWorkBean.getContractCreateUserName());

            if (null != htWorkBean.getContractCreateDate()) {
                // 合同创建时间
                htWorkVO.setContractCreateDate(DateToolUtils.convertDateFormat(
                        htWorkBean.getContractCreateDate(), DateToolUtils.yyyy_MM_dd));
            } else {
                htWorkVO.setContractCreateDate("-");
            }

            //委托任务id
            htWorkVO.setDelegateTaskId(htWorkBean.getDelegateTaskId());

            //委托任务Key
            htWorkVO.setDelegateTaskKey(htWorkBean.getCurrentStateName());

            //数据创建时间
            htWorkVO.setCreateDate(DateToolUtils.convertDateFormat(htWorkBean.getCreateDate(), DateToolUtils.yyyy_MM_dd));

            //业务id
            htWorkVO.setBusinessId(htWorkBean.getBusinessId());

            //查询标识
            htWorkVO.setCheckSign(htWorkBean.getCheckSign());

            // 根据当前流程节点，获取当前的待办信息是否需要通过
            String isPass = "0";
            if (TenderEnum.ManagerVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TenderEnum.QualityVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TenderEnum.GenerManagerVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || WriteContractEnum.Signer.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TaskEnum.ArchiveVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())) {
                isPass = "1";
            }
            htWorkVO.setIsPass(isPass);

            // 数据来源（“0”合同 “1-1”成果文件(包括归档审核) “1-2”A3阶段 “1-3”重新编辑 “2”投标任务 “3”请款单）
            htWorkVO.setSourceType(htWorkBean.getSourceType());

            // 提交节点
            htWorkVO.setSubmitNode(htWorkBean.getSubmitNode());

            // 更新时间
            htWorkVO.setUpdateDate(DateToolUtils.convertDateFormat(htWorkBean.getUpdateDate(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            htWorkVOList.add(htWorkVO);
        }

        // 待办投标任务总数
        Integer count = htWorkDao.excSearchNewTenderTaskPendingCount(pendingProp);

        // 设置总条数
        resultMap.put(Constant.DATA_COUNT_KEY, count);

        // 设置分页数据
        resultMap.put(Constant.DATA_LIST_KEY, htWorkVOList);

        return resultMap;
    }


    /**
     * 合同待办列表(新)
     *
     * @param pendingProp
     * @return
     * @throws SystemException
     */
    private Map<String, Object> getNewContractListInfo(Map<String, Object> pendingProp) {
        Map<String, Object> resultMap = new HashMap<>();

        List<HTWorkVO> htWorkVOList = new ArrayList<>();

        //当前节点 项目监理
        if (Constant.DEPT_ID_XMJL.equals(pendingProp.get("departmentId"))) {
            pendingProp.put("delegateTaskKey", ArchiveEnum.SubmitArchive.getNodeKey());
        } else {
            pendingProp.put("delegateTaskKey", WriteContractEnum.SubmitVerify.getNodeKey());
        }


        // 合同列表
        List<HTWorkBean> htWorkBeans = htWorkDao.excSearchNewContractListInfo(pendingProp);

        // 格式转换
        for (HTWorkBean htWorkBean : htWorkBeans) {
            HTWorkVO htWorkVO = new HTWorkVO();

            // 所属项目id
            htWorkVO.setProjectId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getProjectId()));

            // 流程实例ID
            htWorkVO.setProcessInstId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getProcessInstId()));

            // 项目编号
            htWorkVO.setProjectNum(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getProjectNum()));

            // 合同ID
            htWorkVO.setContractId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getContractId()));
            if (StringToolUtils.isNull(htWorkBean.getContractNum()) || StringToolUtils.isNull(htWorkBean.getProjectNum())) {
                // 合同编号(项目编号-合同编号)
                htWorkVO.setContractNum(StringToolUtils.convertNullObjectToString(
                        htWorkBean.getProjectNum() + htWorkBean.getContractNum()));
            } else {
                // 合同编号(项目编号-合同编号)
                htWorkVO.setContractNum(StringToolUtils.convertNullObjectToString(
                        htWorkBean.getProjectNum() + "-" + htWorkBean.getContractNum()));
            }

            // 合作公司
            htWorkVO.setCooperateCompany(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getCooperateCompany()));

            // 创建人名称
            htWorkVO.setCreateUserName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getCreateUserName()));

            // 提审人
            htWorkVO.setUserName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getUserName()));

            // 任务名称
            htWorkVO.setTenderTaskName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getTenderTaskName()));

            // 业务类型
            htWorkVO.setCounselTypePeriod(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getCounselTypePeriod()));

            // 咨询类别
            htWorkVO.setCounselType(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getCounselType()));

            // 部门ID
            htWorkVO.setDepartmentId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getDepartmentId()));

            // 部门名称
            htWorkVO.setDepartmentName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getDepartmentName()));

            // 所属高管ID
            htWorkVO.setSeniorManagerId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getSeniorManagerId()));

            // 所属高管名称
            htWorkVO.setSeniorManagerName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getSeniorManagerName()));

            // 委托任务id
            htWorkVO.setDelegateTaskId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getDelegateTaskId()));

            // 委托任务Key
            htWorkVO.setDelegateTaskKey(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getCurrentStateName()));

            // 委托任务Key
            htWorkVO.setCurrentStateName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getDelegateTaskKey()));

            // 数据创建时间
            htWorkVO.setCreateDate(DateToolUtils.convertDateFormat(
                    htWorkBean.getCreateDate(), DateToolUtils.yyyy_MM_dd));
            //项目id
            htWorkVO.setProjectId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getProjectId()));
            //项目编号
            htWorkVO.setProjectNum(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getProjectNum()));
            //项目名称
            htWorkVO.setProjectName(StringToolUtils
                    .convertNullObjectToString(StringToolUtils.isNull(htWorkBean.getProjectName()) ? "-" : htWorkBean.getProjectName()));
            //合同id
            htWorkVO.setContractId(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getContractId()));
            //合同名称
            htWorkVO.setContractName(StringToolUtils
                    .convertNullObjectToString(StringToolUtils.isNull(htWorkBean.getContractName()) ? "-" : htWorkBean.getContractName()));
            //合同创建人
            htWorkVO.setContractCreateUserName(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getContractCreateUserName()));
            //合同创建时间
            htWorkVO.setContractCreateDate(DateToolUtils.convertDateFormat(
                    htWorkBean.getContractCreateDate(), DateToolUtils.YYYY_MM_DD_HH_MM));

            //业务id
            htWorkVO.setBusinessId(htWorkBean.getBusinessId());

            //查询标识
            htWorkVO.setCheckSign(htWorkBean.getCheckSign());

            // 根据当前流程节点，获取当前的待办信息是否需要通过
            String isPass = "0";
            if (TenderEnum.ManagerVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TenderEnum.QualityVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TenderEnum.GenerManagerVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || WriteContractEnum.Signer.getNodeKey().equals(htWorkBean.getDelegateTaskKey())
                    || TaskEnum.ArchiveVerify.getNodeKey().equals(htWorkBean.getDelegateTaskKey())) {
                isPass = "1";
            }
            htWorkVO.setIsPass(isPass);

            // 数据来源
            htWorkVO.setSourceType(htWorkBean.getSourceType());

            // 提交节点
            htWorkVO.setSubmitNode(StringToolUtils
                    .convertNullObjectToString(htWorkBean.getSubmitNode()));

            // 更新时间
            htWorkVO.setUpdateDate(DateToolUtils.convertDateFormat(htWorkBean.getUpdateDate(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

            htWorkVOList.add(htWorkVO);
        }

        // 合同总数
        Integer count = htWorkDao.excSearchNewContractListCount(pendingProp);

        // 设置总条数
        resultMap.put(Constant.DATA_COUNT_KEY, count);

        // 设置分页数据
        resultMap.put(Constant.DATA_LIST_KEY, htWorkVOList);

        return resultMap;
    }


    /**
     * 查询待办列表（2019.10.23 新）
     */
    @Override
    public Map<String, Object> excSearchNewPendingList(HTWorkVO paramVO) throws LogicException, SystemException {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            // 根据工作流获取待办信息
            List<WorkFlow> dataList = htWorkBenchService.excQueryTodo(paramVO.getLoginUserInfo().getLoginUserId(), paramVO.getDepartmentId(), null);
            List<String> ids = new ArrayList<>();
            Map<String, Object> pendingProp = new HashMap<>();
            // 遍历
            for (WorkFlow workFlow : dataList) {
                Iterator idsIt = workFlow.getBizKey().iterator();
                while (idsIt.hasNext()) {                            //集合中的迭代方法(遍历)
                    String bizKey = (String) idsIt.next();
                    pendingProp.put(bizKey, workFlow.getPreNodeHandlerId());
                    pendingProp.put(workFlow.getPreNodeHandlerId(), workFlow.getPreNodeHandlerName());
                }
                ids.addAll(workFlow.getBizKey());
            }
            //给与前台用以字段展示(要展示的数据列)
            Map<String, Boolean> paramKeyMap = new HashMap<>();

            // 接收返回值的map
            Map<String, Object> retMap = new HashMap<>();

            //业务数据创建时间已传
            paramKeyMap.put("createDate", true);

            // 市场部
            if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_SC)) {

                // 获取map
                paramKeyMap.putAll(this.getSCParamKeyMap());

                // 获取投标任务map
                retMap = this.getTenderTaskPendingListInfo(paramVO, ids);

                // 质检部
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_QUALITY)) {

                // 获取map
                paramKeyMap.putAll(this.getQualityParamKeyMap());

                // 获取投标任务map
                retMap = this.getTenderTaskPendingListInfo(paramVO, ids);

                // 造价部、招标代理部、项目监理部
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_ZJ) ||
                    paramVO.getDepartmentId().equals(Constant.DEPT_ID_ZBDL) ||
                    paramVO.getDepartmentId().equals(Constant.DEPT_ID_XMJL)) {

                // 获取map
                paramKeyMap.putAll(this.getZjZbdlXmjlParamKeyMap(paramVO));

                // 获取合同map
                retMap = this.getContractListInfo(paramVO, ids);

                // 行政部
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_XZ)) {

                // 获取map
                paramKeyMap.putAll(this.getXZParamKeyMap());

                // 获取合同map
                retMap = this.getContractListInfo(paramVO, ids);
            } else if (paramVO.getDepartmentId().equals(Constant.DEPT_ID_CW)) {//财务

                // 获取map
                paramKeyMap.putAll(this.getCwParamKeyMap());

                // 获取合同map
                retMap = this.getContractListInfo(paramVO, ids);
            }
            List<HTWorkVO> htWorkVOList = (List<HTWorkVO>) retMap.get(Constant.DATA_LIST_KEY);

            if (!ListToolUtils.isEmpty(htWorkVOList)) {
                for (HTWorkVO htWorkVO : htWorkVOList) {
                    htWorkVO.setCompilationUserId((String) pendingProp.get(htWorkVO.getCheckSign()));
                    htWorkVO.setCompilationUserName((String) pendingProp.get(htWorkVO.getCompilationUserId()));
                }
            }

            //设置总条数
            resultMap.put(Constant.DATA_COUNT_KEY, retMap.get(Constant.DATA_COUNT_KEY));
            //设置分页数据
            resultMap.put(Constant.DATA_LIST_KEY, htWorkVOList);
            //设置要展示的数据列map
            resultMap.put(Constant.DATA_VO_PARAM_KEY_MAP, paramKeyMap);

            return resultMap;

        } catch (LogicException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 查询所有部门状态
     */
    public Map<String, Object> excSearchAllDeptPendingFlag(HTWorkVO paramVO, List<HTDepartmentBean> htDepartmentBeans, Integer dataCount) throws LogicException, SystemException {
        try {
            //返回结果集
            Map<String, Object> resultMap = new HashMap<>();
            //返回部门红点标识结果集
            Map<String, Boolean> deptFlagMap = new HashMap<>();
            //部门校验依照
            Map<String, Object> departmentCheckMap = new HashMap<>();
            List<String> ids = new ArrayList<>();
            //部门ID保存集合
            List<String> departmentIds = new ArrayList<>();
            //调用接口查询当前登录人所包含待办信息
            List<WorkFlow> workFlows = htWorkBenchService.hasTodoTask(paramVO.getLoginUserInfo().getLoginUserId());
            for (WorkFlow workFlow : workFlows) {
                if (!ProcessDictEnum.SealApply.getProcessKey().equals(workFlow.getProcessDefinitionKey())) {
                    ids.addAll(workFlow.getBizKey());
                    for (String id : workFlow.getBizKey()) {
                        departmentCheckMap.put(id, workFlow.getDepartmentId());
                    }
                }
            }
            for (HTDepartmentBean department :
                    htDepartmentBeans) {
                departmentIds.add(department.getDepartmentId());
                deptFlagMap.put(department.getDepartmentId(), false);
            }
            dataCount = this.getContractListCount(paramVO, departmentIds, ids, deptFlagMap, departmentCheckMap, dataCount);

            for (HTDepartmentBean htDepartmentBean :
                    htDepartmentBeans) {
                HTWorkBean paramBean = new HTWorkBean();

                paramBean.setDepartmentId(htDepartmentBean.getDepartmentId());

                paramBean.setUserId(paramVO.getLoginUserInfo().getLoginUserId());

                Integer checkCount = 0;
                if (Constant.DEPT_ID_SC.equals(paramBean.getDepartmentId())) {
                    //节点
                    paramBean.setDelegateTaskKey(TenderEnum.SubmitVerify.getNodeKey());
                    //查询普通执行人任务待办个数
                    checkCount += htWorkDao.excSearchExecutorTenderTaskPendingCount(paramBean);
                } else {
                    //当前节点 项目监理
                    if (Constant.DEPT_ID_XMJL.equals(paramBean.getDepartmentId())) {
                        paramBean.setDelegateTaskKey(ArchiveEnum.SubmitArchive.getNodeKey());
                    } else {
                        paramBean.setDelegateTaskKey(WriteContractEnum.SubmitVerify.getNodeKey());
                    }
                    //查询普通执行人任务阶段待办个数
                    checkCount += htWorkDao.excSearchExecutortaskArchivePendingCount(paramBean);
                    //查询普通执行人任务待办个数
                    checkCount += htWorkDao.excSearchExecutorTaskPendingCount(paramBean);
                }
                if (checkCount > 0) {
                    deptFlagMap.put(htDepartmentBean.getDepartmentId(), true);
                }
                dataCount += checkCount;
            }
            resultMap.put("deptFlagMap", deptFlagMap);
            resultMap.put("dataCount", dataCount);
            return resultMap;

        } catch (LogicException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    private Integer getContractListCount(HTWorkVO paramVO, List<String> departmentIds, List<String> ids,
                                         Map<String, Boolean> resultMap, Map<String, Object> departmentCheckMap,
                                         Integer pendingCount) {

        Map<String, Object> pendingProp = new HashMap<>();
        pendingProp.put("departmentIds", departmentIds);
        pendingProp.put("ids", ids);
        pendingProp.put("userId", paramVO.getLoginUserInfo().getLoginUserId());
        //查询普通执行人任务待办个数
        List<HTWorkBean> htWorkBeanList = htWorkDao.excSearchPendingCount(pendingProp);
        for (HTWorkBean htWorkBean :
                htWorkBeanList) {
            resultMap.put((String) departmentCheckMap.get(htWorkBean.getBusinessId()), true);
        }
        pendingCount += htWorkBeanList.size();
        return pendingCount;
    }

    @Override
    @Transactional
    public void excBatchPassCheckBusinessList(HTWorkVO paramVO, List<HTWorkVO> paramVOList) throws LogicException, SystemException {
        try {
            for (HTWorkVO htWorkVO :
                    paramVOList) {
                switch (htWorkVO.getSourceType()) {
                    //合同
                    case Constant.BATCH_PASS_BUSINESS_TYPE_WRITE_CONTRACT:
                        HTContractVO contractVO = new HTContractVO();
                        //当前登录人信息
                        contractVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                        //业务Id
                        contractVO.setContractId(htWorkVO.getContractId());
                        //通过标识
                        contractVO.setIsPass(htWorkVO.getIsPass());
                        //委托任务ID
                        contractVO.setDelegateTaskId(htWorkVO.getDelegateTaskId());
                        //提交节点
                        contractVO.setSubmitNode(htWorkVO.getSubmitNode());
                        //修改时间
                        contractVO.setUpdateDate(htWorkVO.getUpdateDate());
                        //执行合同审核方法
                        htContractService.excAuditingHTContract(contractVO);
                        break;
                    //归档(包含成果文件)
                    case Constant.BATCH_PASS_BUSINESS_TYPE_ARCHIVE:
                        HTTaskVO taskVO = new HTTaskVO();
                        //当前登录人信息
                        taskVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                        //业务Id
                        taskVO.setRelationId(htWorkVO.getBusinessId());
                        //通过标识
                        taskVO.setIsPass(htWorkVO.getIsPass());
                        //委托任务ID
                        taskVO.setDelegateTaskId(htWorkVO.getDelegateTaskId());
                        //提交节点
                        taskVO.setSubmitNode(htWorkVO.getSubmitNode());
                        //修改时间
                        taskVO.setUpdateDate(htWorkVO.getUpdateDate());
                        //执行任务审核方法
                        htTaskService.excAuditingTaskOutcomeDocumentPeriod(taskVO);
                        break;
                    //任务-A3阶段
                    case Constant.BATCH_PASS_BUSINESS_TYPE_TASK_A3:
                        HTTaskPeriodVO taskPeriodVO = new HTTaskPeriodVO();
                        //当前登录人信息
                        taskPeriodVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                        //业务Id
                        taskPeriodVO.setRelationId(htWorkVO.getBusinessId());
                        //通过标识
                        taskPeriodVO.setIsPass(htWorkVO.getIsPass());
                        //委托任务ID
                        taskPeriodVO.setDelegateTaskId(htWorkVO.getDelegateTaskId());
                        //提交节点
                        taskPeriodVO.setSubmitNode(htWorkVO.getSubmitNode());
                        //修改时间
                        taskPeriodVO.setUpdateDate(htWorkVO.getUpdateDate());
                        //执行任务A3阶段审核方法
                        htTaskService.auditingTaskA3Period(taskPeriodVO);
                        break;
                    //重新编辑(包含重新编辑待签发)
                    case Constant.BATCH_PASS_BUSINESS_TASK_REEDIT:
                        HTTaskReeditVO taskReeditVO = new HTTaskReeditVO();
                        //当前登录人信息
                        taskReeditVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                        //业务Id
                        taskReeditVO.setRelationId(htWorkVO.getBusinessId());
                        //通过标识
                        taskReeditVO.setIsPass(htWorkVO.getIsPass());
                        //委托任务ID
                        taskReeditVO.setDelegateTaskId(htWorkVO.getDelegateTaskId());
                        //提交节点
                        taskReeditVO.setSubmitNode(htWorkVO.getSubmitNode());
                        //修改时间
                        taskReeditVO.setUpdateDate(htWorkVO.getUpdateDate());
                        if ("Signer".equals(htWorkVO.getCurrentStateName())) {
                            //执行任务重新编辑待签发阶段审核方法
                            htTaskService.excReeditApplicationIssue(taskReeditVO);
                        } else {
                            //执行任务重新编辑阶段审核方法
                            htTaskService.excAuditingOutcomeDocumentPeriodReeditApplication(taskReeditVO);
                        }
                        break;
                    //投标任务
                    case Constant.BATCH_PASS_BUSINESS_TYPE_TENDER:
                        HTTenderTaskVO tenderTaskVO = new HTTenderTaskVO();
                        //当前登录人信息
                        tenderTaskVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                        //业务Id
                        tenderTaskVO.setTenderTaskId(htWorkVO.getTenderTaskId());
                        //通过标识
                        tenderTaskVO.setIsPass(htWorkVO.getIsPass());
                        //委托任务ID
                        tenderTaskVO.setDelegateTaskId(htWorkVO.getDelegateTaskId());
                        //提交节点
                        tenderTaskVO.setSubmitNode(htWorkVO.getSubmitNode());
                        //修改时间
                        tenderTaskVO.setUpdateDate(htWorkVO.getUpdateDate());
                        //执行投标任务审核方法
                        htTenderTaskService.excAuditingHTTenderTask(tenderTaskVO);
                        break;
                    //请款单
                    case Constant.BATCH_PASS_BUSINESS_TYPE_CASH_APPLY:
                        HTRequestMenuVO requestMenuVO = new HTRequestMenuVO();
                        //当前登录人信息
                        requestMenuVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                        //业务Id
                        requestMenuVO.setRequestMenuId(htWorkVO.getBusinessId());
                        //通过标识
                        requestMenuVO.setIsPass(htWorkVO.getIsPass());
                        //委托任务ID
                        requestMenuVO.setDelegateTaskId(htWorkVO.getDelegateTaskId());
                        //提交节点
                        requestMenuVO.setSubmitNode(htWorkVO.getSubmitNode());
                        //修改时间
                        requestMenuVO.setUpdateDate(htWorkVO.getUpdateDate());
                        //执行请款单审核方法
                        htRequestMenuService.excAuditingQuestionMenu(requestMenuVO);
                        break;
                    //其他(直接跳过)
                    default:
                        break;
                }
            }

        } catch (LogicException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


    /**
     * 查询已完成列表
     */
    @Override
    public Map<String, Object> excSearchNewCompletedList(HTWorkVO paramVO) {
        try {
            // 将待办类别分组
            Set<Object> tmpSet = new HashSet<Object>();
            //所查询业务id集合
            List<String> ids = new ArrayList<>();
            List<String> tenderTaskIds = new ArrayList<>();
            List<String> contractIds = new ArrayList<>();
            List<String> taskIds = new ArrayList<>();
            List<String> taskPeriodIds = new ArrayList<>();
            List<String> taskReEditIds = new ArrayList<>();
            List<String> taskArchiveIds = new ArrayList<>();
            List<String> requestMenuIds = new ArrayList<>();
            Map<String, Object> completeProp = new HashMap<>();
            Map<String, Object> handleDateMap = new HashMap<>();
            Map<String, String> compilationUserMap = new HashMap<>();
            completeProp.put("contractIdsFlag", false);
            completeProp.put("TenderFlag", false);
            completeProp.put("TaskFlag", false);
            completeProp.put("Task_A3Flag", false);
            completeProp.put("CashApplyFlag", false);
            Map<String, Object> resultMap = new HashMap<>();// 遍历

            HTWorkBean paramBean = new HTWorkBean();
            paramBean.setUserId(paramVO.getLoginUserInfo().getLoginUserId());
            paramBean.setDepartmentId(paramVO.getDepartmentId());
            //当前节点 项目监理
            if (Constant.DEPT_ID_XMJL.equals(paramBean.getDepartmentId())) {
                paramBean.setDelegateTaskKey(ArchiveEnum.SubmitArchive.getNodeKey());
            } else {
                paramBean.setDelegateTaskKey(WriteContractEnum.SubmitVerify.getNodeKey());
            }
            List<HTWorkBean> executorCompleteList = new ArrayList<>();
            //查询普通执行人任务已办列表
            executorCompleteList.addAll(htWorkDao.excSearchExecutorTaskDoneList(paramBean));
            //查询普通执行人任务归档已办列表
            executorCompleteList.addAll(htWorkDao.excSearchExecutorTaskArchiveDoneList(paramBean));
            // 根据工作流获取已办办信息
            List<WorkFlow> dataList = htWorkBenchService.excQueryTaskCompleted(paramVO.getLoginUserInfo().getLoginUserId(), executorCompleteList);
            for (WorkFlow workFlow : dataList) {
                Set<String> bizKey = workFlow.getBizKey();
                ids.addAll(bizKey);
                for (String id :
                        bizKey) {
                    handleDateMap.put(id, workFlow.getHandleDate());
                }
                for (String id :
                        bizKey) {
                    compilationUserMap.put(id, workFlow.getPreNodeHandlerName());
                }
                // 待办类别
                tmpSet.add(workFlow.getProcessDefinitionKey());
                switch (workFlow.getProcessDefinitionKey()) {
                    //合同编撰(使用合同ID)
                    case "WriteContract":
                        //创建项目(使用合同ID)
                    case "CreateProjectProcess":
                        contractIds.addAll(workFlow.getBizKey());
                        completeProp.put("contractIdsFlag", true);
                        break;
                    //投标(使用投标任务ID)
                    case "Tender":
                        tenderTaskIds.addAll(workFlow.getBizKey());
                        break;
                    //任务_成果文件审批(使用任务ID)
                    case "Task":
                        taskIds.addAll(workFlow.getBizKey());
                        completeProp.put("TaskFlag", true);
                        break;
                    //任务_A3审批(使用阶段ID)
                    case "Task_A3":
                        taskPeriodIds.addAll(workFlow.getBizKey());
                        completeProp.put("Task_A3Flag", true);
                        break;
                    //任务_重新编辑(使用重新编辑ID)
                    case "Task_Reedit":
                        taskReEditIds.addAll(workFlow.getBizKey());
                        completeProp.put("Task_ReeditFlag", true);
                        break;
                    //归档(使用归档ID)
                    case "Archive":
                        taskArchiveIds.addAll(workFlow.getBizKey());
                        completeProp.put("ArchiveFlag", true);
                        break;
                    //请款(使用请款单ID)
                    case "CashApply":
                        requestMenuIds.addAll(workFlow.getBizKey());
                        completeProp.put("CashApplyFlag", true);
                        break;
                    default:
                        break;
                }
            }
            //用以告诉前台哪些属性有值用于展示
            Map<String, Boolean> paramKeyMap = new HashMap<>();
            if (null != paramVO.getSearchType() && !paramVO.getSearchType().isEmpty()) {
                // 投标任务
                if (paramVO.getSearchType().equals(Constant.SEARCH_TYPE_TENDER_TASK)) {
                    //投标任务主键已传
                    paramKeyMap.put("tenderTaskId", true);
                    //投标任务名称已传
                    paramKeyMap.put("tenderTaskName", true);
                    //开标时间已传
                    paramKeyMap.put("startDate", true);
                    //序号已传
                    paramKeyMap.put("sortNum", true);
                    //用户名称已传
                    paramKeyMap.put("userName", true);
                    //创建时间已传
                    paramKeyMap.put("createDate", true);
                    List<HTTenderTaskVO> htTenderTaskVOList = new ArrayList<>();
                    //投标任务时间
                    paramBean.setTenderTaskName(paramVO.getTenderTaskName());
                    //当前节点
                    paramBean.setDelegateTaskKey(TenderEnum.SubmitVerify.getNodeKey());
                    //投标任务ID集合
                    if (null != tenderTaskIds && tenderTaskIds.size() > 0) {
                        paramBean.setTenderTaskIds(tenderTaskIds);
                    }
                    // 分页
                    paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
                    //已办投标任务列表
                    List<HTWorkBean> htWorkBeans = htWorkDao.excSearchTenderTaskCompletedList(paramBean);
                    //格式转换
                    for (HTWorkBean htWorkBean : htWorkBeans) {
                        HTTenderTaskVO htTenderTaskVO = new HTTenderTaskVO();
                        //投标任务ID
                        htTenderTaskVO.setTenderTaskId(htWorkBean.getTenderTaskId());
                        //投标任务名称
                        htTenderTaskVO.setTenderTaskName(htWorkBean.getTenderTaskName());
                        //开标时间
                        htTenderTaskVO.setStartDate(DateToolUtils.convertDateFormat(htWorkBean.getStartDate(), DateToolUtils.yyyy_MM_dd));
                        //序号
                        htTenderTaskVO.setSortNum(htWorkBean.getSortNum());
                        //用户名称
                        htTenderTaskVO.setUserName(htWorkBean.getUserName());
                        //创建时间
                        htTenderTaskVO.setCreateDate(DateToolUtils.convertDateFormat(htWorkBean.getCreateDate(), DateToolUtils.yyyy_MM_dd));
                        htTenderTaskVOList.add(htTenderTaskVO);
                    }
                    Integer count = htWorkDao.excSearchTenderTaskCompletedCount(paramBean);
                    resultMap.put(Constant.DATA_COUNT_KEY, count);
                    resultMap.put(Constant.DATA_LIST_KEY, htTenderTaskVOList);
                    resultMap.put(Constant.DATA_VO_PARAM_KEY_MAP, paramKeyMap);

                    // 合同
                } else if (paramVO.getSearchType().equals(Constant.SEARCH_TYPE_CONTRACT)) {

                    //项目id已传
                    paramKeyMap.put("projectId", true);
                    //项目编号已传
                    paramKeyMap.put("projectNum", true);
                    //项目名称已传
                    paramKeyMap.put("projectName", true);
                    //合同ID已传
                    paramKeyMap.put("contractId", true);
                    //合同编号已传
                    paramKeyMap.put("contractNum", true);
                    //合同名称已传
                    paramKeyMap.put("contractName", true);
                    //任务名称已传
                    paramKeyMap.put("tenderTaskName", true);
                    //业务类型已传
                    paramKeyMap.put("counselTypePeriod", true);
                    //合作公司已传
                    paramKeyMap.put("cooperateCompany", true);
                    //流转人已传
                    paramKeyMap.put("compilationUserName", true);
                    //流转时间已传
                    paramKeyMap.put("handleDate", true);

                    List<HTWorkVO> htWorkVOList = new ArrayList<>();
                    completeProp.put("orderList", ids);
                    completeProp.put("contractIds", contractIds);
                    completeProp.put("Task", taskIds);
                    completeProp.put("Task_A3", taskPeriodIds);
                    completeProp.put("Task_Reedit", taskReEditIds);
                    completeProp.put("Archive", taskArchiveIds);
                    completeProp.put("CashApply", requestMenuIds);
                    completeProp.put("userId", paramVO.getLoginUserInfo().getLoginUserId());
                    completeProp.put("departmentId", paramVO.getDepartmentId());
                    completeProp.put("delegateTaskKey", TenderEnum.SubmitVerify.getNodeKey());
                    completeProp.put("projectNum", paramVO.getProjectNum());
                    completeProp.put("projectName", paramVO.getProjectName());
                    completeProp.put("contractNum", paramVO.getContractNum());
                    completeProp.put("userName", paramVO.getUserName());
                    // 临时变量：页码
                    Integer tmpPageNumberInt = NumberToolUtils.convertNullToInteger(paramVO.getPageNumber());
                    // 临时变量：单页数据条数
                    Integer tmpPageSizeInt = NumberToolUtils.convertNullToInteger(paramVO.getPageSize());
                    // 设置当前页开始rownum编号
                    completeProp.put("pageNumber", (tmpPageNumberInt - 1) * tmpPageSizeInt);
                    // 设置当前页结束rownum编号
                    completeProp.put("pageSize", tmpPageSizeInt);
                    //已办合同列表
                    List<HTWorkBean> completeList = htWorkDao.excSearchNewContractCompleteList(completeProp);
                    //已办合同列表总数
                    Integer count = htWorkDao.excSearchNewContractCompleteCount(completeProp);

                    //格式转换
                    for (HTWorkBean htWorkBean : completeList) {
                        HTWorkVO htWorkVO = new HTWorkVO();
                        // 所属项目id
                        htWorkVO.setProjectId(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getProjectId()));

                        // 项目编号
                        htWorkVO.setProjectNum(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getProjectNum()));

                        // 项目名称
                        htWorkVO.setProjectName(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getProjectName()));

                        // 合同ID
                        htWorkVO.setContractId(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getContractId()));

                        if (StringToolUtils.isNull(htWorkBean.getContractNum()) || StringToolUtils.isNull(htWorkBean.getProjectNum())) {
                            // 合同编号(项目编号-合同编号)
                            htWorkVO.setContractNum(StringToolUtils.convertNullObjectToString(
                                    htWorkBean.getProjectNum() + htWorkBean.getContractNum()));
                        } else {
                            // 合同编号(项目编号-合同编号)
                            htWorkVO.setContractNum(StringToolUtils.convertNullObjectToString(
                                    htWorkBean.getProjectNum() + "-" + htWorkBean.getContractNum()));
                        }

                        // 合同名称
                        htWorkVO.setContractName(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getContractName()));

                        // 创建人名称
                        htWorkVO.setCreateUserName(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getCreateUserName()));

                        // 提审人
                        htWorkVO.setUserName(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getUserName()));

                        // 任务名称
                        htWorkVO.setTenderTaskName(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getTenderTaskName()));

                        // 部门ID
                        htWorkVO.setDepartmentId(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getDepartmentId()));

                        // 部门名称
                        htWorkVO.setDepartmentName(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getDepartmentName()));

                        // 所属高管ID
                        htWorkVO.setSeniorManagerId(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getSeniorManagerId()));

                        // 所属高管名称
                        htWorkVO.setSeniorManagerName(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getSeniorManagerName()));

                        // 委托任务id
                        htWorkVO.setDelegateTaskId(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getDelegateTaskId()));

                        // 委托任务Key
                        htWorkVO.setDelegateTaskKey(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getCurrentStateName()));

                        // 合作公司
                        htWorkVO.setCooperateCompany(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getCooperateCompany()));

                        // 数据创建时间
                        htWorkVO.setCreateDate(DateToolUtils.convertDateFormat(
                                htWorkBean.getCreateDate(), DateToolUtils.yyyy_MM_dd));

                        //业务id
                        htWorkVO.setBusinessId(htWorkBean.getBusinessId());

                        //查询标识
                        htWorkVO.setCheckSign(htWorkBean.getCheckSign());

                        // 数据来源
                        htWorkVO.setSourceType(htWorkBean.getSourceType());

                        // 提交节点
                        htWorkVO.setSubmitNode(StringToolUtils
                                .convertNullObjectToString(htWorkBean.getSubmitNode()));

                        // 更新时间
                        htWorkVO.setUpdateDate(DateToolUtils.convertDateFormat(htWorkBean.getUpdateDate(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));

                        htWorkVOList.add(htWorkVO);
                    }

                    if (!ListToolUtils.isEmpty(htWorkVOList)) {
                        for (HTWorkVO htWorkVO :
                                htWorkVOList) {
                            for (HTWorkBean htWorkBean :
                                    executorCompleteList) {
                                if (htWorkBean.getBusinessId().equals(htWorkVO.getCheckSign())) {
                                    htWorkVO.setBusinessId(htWorkVO.getCheckSign());
                                }
                            }
                            //流转人
                            htWorkVO.setCompilationUserName(compilationUserMap.get(htWorkVO.getCheckSign()));

                            //流转时间
                            htWorkVO.setHandleDate(DateToolUtils.convertDateFormat((Date) handleDateMap.get(htWorkVO.getCheckSign()), DateToolUtils.YYYY_MM_DD_HH_MM));
                        }
                    }
                    resultMap.put(Constant.DATA_COUNT_KEY, count);
                    resultMap.put(Constant.DATA_LIST_KEY, htWorkVOList);
                    resultMap.put(Constant.DATA_VO_PARAM_KEY_MAP, paramKeyMap);

                }
            }
            return resultMap;
        } catch (LogicException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    @Override
    public List<HTWorkVO> excSearchWeekWorkList(HTWorkVO paramVO) throws LogicException, SystemException {
        try {

            HTWorkBean paramBean = new HTWorkBean();
            // 设置登陆人ID
            paramBean.setUserId(StringToolUtils.convertNullObjectToString(paramVO.getApplyUserId()));
            // 设置开始时间
            paramBean.setStartDate(DateToolUtils.convertDateFormat(paramVO.getStartDate(),DateToolUtils.yyyy_MM_dd));
            // 设置结束时间
            paramBean.setEndDate(DateToolUtils.convertDateFormat(paramVO.getEndDate(),DateToolUtils.yyyy_MM_dd));

            List<HTWorkVO> weekVOList = new ArrayList<>();

            //List<HTWorkBean> weekList = htWorkDao.excSearchWeekWorkList(paramBean);
            List<HTWorkBean> weekList = htWorkDao.excSearchWeekTaskList(paramBean);

            if (!ListToolUtils.isEmpty(weekList)) {
                HTWorkVO workVO = null;
                int sortNum = 0;
                for (HTWorkBean workBean : weekList) {
                    workVO = new HTWorkVO();
                    // 序号(自增)
                    workVO.setSortNum(StringToolUtils.convertNullObjectToString(++sortNum));
                    // 合同编号
                    workVO.setContractNum(StringToolUtils.convertNullObjectToString(workBean.getContractNum()));
                    // 合同名称
                    workVO.setContractName(StringToolUtils.convertNullObjectToString(workBean.getContractName()));
                    // 业务类型
                    workVO.setCounselTypePeriod(StringToolUtils.convertNullObjectToString(workBean.getCounselTypePeriod()));
                    // 咨询类别
                    workVO.setCounselType(StringToolUtils.convertNullObjectToString(workBean.getCounselType()));
                    // 合作公司
                    workVO.setCooperateCompany(StringToolUtils.convertNullObjectToString(workBean.getCooperateCompany()));
                    // 任务名称
                    workVO.setTenderTaskName(StringToolUtils.convertNullObjectToString(workBean.getTenderTaskName()));
                    // 流转时间
                    workVO.setStartDate(DateToolUtils.convertDateFormat(workBean.getStartDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                    // 审批结束时间
                    workVO.setEndDate(DateToolUtils.convertDateFormat(workBean.getEndDate(),DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                    // 审批时长
                    workVO.setDuration(BusinessUtils.convertDuration(workBean.getDuration()));
                    // 审批时长
                    workVO.setIsPass(StringToolUtils.convertNullObjectToString(workBean.getIsPass()));

                    weekVOList.add(workVO);
                }

            }
            return weekVOList;


        } catch (LogicException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SystemException(ex, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }


}
