package com.hd.manager.service.impl;

import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.common.dao.*;
import com.hd.common.entity.*;
import com.hd.common.service.CommonCacheService;
import com.hd.common.service.CommonService;
import com.hd.common.util.*;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.dao.HTContractDao;
import com.hd.manager.dao.HTProjectDao;
import com.hd.manager.dao.HTRequestMenuDao;
import com.hd.manager.dao.bean.HTContractBean;
import com.hd.manager.dao.bean.HTDictProjectCounselTypeBean;
import com.hd.manager.dao.bean.HTProjectBean;
import com.hd.manager.dao.bean.HTRequestMenuBean;
import com.hd.manager.service.*;
import com.hd.manager.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 项目管理 业务层 实现类
 *
 * @author jwl
 * Created in 2019/7/9 15:31
 */
@Service
public class HTProjectServiceImpl extends BaseServiceImpl implements HTProjectService {

    /**
     * 项目dao 持久层资源
     */
    @Resource
    private HTProjectDao hTProjectDao;

    /**
     * 用户权限 业务层
     */
    @Resource
    private HTUserPowerService htUserPowerService;

    /**
     * 项目mapper 持久层资源
     */
    @Resource
    private TbProjectEntityMapper tbProjectEntityMapper;

    /**
     * 合同mapper 持久层资源
     */
    @Resource
    private TbContractEntityMapper tbContractEntityMapper;

    @Resource
    private HTProjectProcessService projectProcessService;

    @Resource
    private HTContractProcessService contractProcessService;

    @Resource
    private CommonCacheService commonCacheService;


    @Resource
    private CommonService commonService;

    /**
     * 请款单Dao对象
     */
    @Resource
    private HTRequestMenuDao htRequestMenuDao;

    /**
     * 任务人员关联mapper
     */
    @Resource
    private TbTaskUserRelationEntityMapper tbTaskUserRelationEntityMapper;

    /**
     * 任务人员关联mapper
     */
    @Resource
    private TbContractBaseInfoEntityMapper tbContractBaseInfoEntityMapper;

    /**
     * 合同dao 持久层资源
     */
    @Resource
    private HTContractDao htContractDao;

    /**
     * 合同service 持久层资源
     */
    @Resource
    private HTContractServiceImpl htContractServiceImpl;

    /**
     * 任务归档mapper 持久层资源
     */
    @Resource
    private TbTaskArchiveEntityMapper tbTaskArchiveEntityMapper;

    /**
     * 查询已收入汇总和合同金额汇总
     *
     * @return 已收入汇总和合同金额汇总
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTProjectVO excSearchIncomeReceivedSumAndContractAmountSum(HTProjectVO paramVO) throws SystemException {

        HTProjectVO htProjectVO = new HTProjectVO();

        try {

            // 当前时间
            Date currentDate = getCurrentDate();

            HTProjectBean paramBean = new HTProjectBean();

            // 存放部门id的集合
            List<String> departmentIdList = new ArrayList<>();

            // 通过当前登录人id查询所属部门id集合
            List<String> returnDepartmentIdList = hTProjectDao
                    .excSearchDepartmentIdByCurrentUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 招标部
            if(returnDepartmentIdList.contains(Constant.DEPT_ID_ZBDL)){
                // 把招标部id集合中
                departmentIdList.add(Constant.DEPT_ID_ZBDL);
            }

            // 造价部
            if(returnDepartmentIdList.contains(Constant.DEPT_ID_ZJ)){
                // 把造价部id集合中
                departmentIdList.add(Constant.DEPT_ID_ZJ);
            }

            // 监理部
            if(returnDepartmentIdList.contains(Constant.DEPT_ID_XMJL)){
                // 把监理部id集合中
                departmentIdList.add(Constant.DEPT_ID_XMJL);
            }

            // 设置部门id集合
            paramBean.setDepartmentIdList(departmentIdList);

            // 当前年1月1日
            String startDate = DateToolUtils.convertDateFormat(currentDate, DateToolUtils.yyyy)+"-01-01";

            // 当前年12月31日
            String endDate = DateToolUtils.convertDateFormat(currentDate, DateToolUtils.yyyy)+"-12-31";

            // 开始时间
            paramBean.setStartDate(DateToolUtils.convertDateFormat(startDate,DateToolUtils.yyyy_MM_dd));

            // 结束时间
            paramBean.setEndDate(DateToolUtils.convertDateFormat(endDate,DateToolUtils.yyyy_MM_dd));

            // 查询已收入汇总和合同金额汇总
            HTProjectBean htProjectBean = hTProjectDao.excSearchIncomeReceivedSumAndContractAmountSum(paramBean);

            if (null != htProjectBean) {

                // 已收入汇总
                htProjectVO.setIncomeReceivedSum(NumberToolUtils.convertNumberToFormatStringWithHalfUp(
                        htProjectBean.getIncomeReceivedSum(), NumberToolUtils.INT_SCALE_FLOAT));

                // 项目金额汇总
                htProjectVO.setContractAmountSum(NumberToolUtils.convertNumberToFormatStringWithHalfUp(
                        htProjectBean.getContractAmountSum(), NumberToolUtils.INT_SCALE_FLOAT));

            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htProjectVO;
    }

    /**
     * 查询公司缩写下拉列表
     *
     * @return 公司缩写下拉列表
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTProjectVO> excSearchCondenseList() throws SystemException {

        // 集合
        List<HTProjectVO> retProjectList = new ArrayList<>();

        try {

            TbProjectEntity tbProjectEntity = new TbProjectEntity();

            // 查询公司缩写下拉列表
            List<HTProjectBean> condenseList = hTProjectDao.excSearchCondenseList(tbProjectEntity);

            HTProjectVO htProjectVO = null;

            // Bean 转化成 VO
            for (HTProjectBean htProjectBean : condenseList) {

                htProjectVO = new HTProjectVO();

                // 设置公司ID
                htProjectVO.setCompanyId(StringToolUtils.convertNullObjectToString(
                        htProjectBean.getCompanyId()));

                // 设置公司名称缩写
                htProjectVO.setCondense(StringToolUtils.convertNullObjectToString(
                        htProjectBean.getCondense()));

                retProjectList.add(htProjectVO);

            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retProjectList;
    }

    /**
     * 查询部门人员下拉列表
     *
     * @return 部门人员下拉列表
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTProjectVO> excSearchPersonList() throws SystemException {

        // 集合
        List<HTProjectVO> retPersonList = new ArrayList<>();

        try {

            HTProjectBean paramBean = new HTProjectBean();

            // 设置职位名称
            paramBean.setJobName(Constant.JOB_NAME);

            // 设置造价部id
            paramBean.setDeptIdZj(Constant.DEPT_ID_ZJ);

            // 设置招标代理部id
            paramBean.setDeptIdZbdl(Constant.DEPT_ID_ZBDL);

            // 设置项目监理部id
            paramBean.setDeptIdXmjl(Constant.DEPT_ID_XMJL);

            // 查询公司缩写下拉列表
            List<HTProjectBean> personList = hTProjectDao.excSearchPersonList(paramBean);

            HTProjectVO htProjectVO = null;

            // Bean 转化成 VO
            for (HTProjectBean htProjectBean : personList) {

                htProjectVO = new HTProjectVO();

                // 设置用户名
                htProjectVO.setUserName(StringToolUtils.convertNullObjectToString(
                        htProjectBean.getDepartmentName() + "-" + htProjectBean.getJobName()));

                // 设置部门ID
                htProjectVO.setDepartmentId(StringToolUtils.convertNullObjectToString(
                        htProjectBean.getDepartmentId()));

                // 设置职位ID
                htProjectVO.setJobId(StringToolUtils.convertNullObjectToString(
                        htProjectBean.getJobId()));

                // 设置部门简写
                htProjectVO.setAbbr(StringToolUtils.convertNullObjectToString(
                        htProjectBean.getAbbr()));

                retPersonList.add(htProjectVO);

            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retPersonList;
    }

    /**
     * 查询项目编号
     *
     * @return 项目编号
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTProjectVO excSearchMaxProjectNum() throws SystemException {

        HTProjectVO htProjectVO = new HTProjectVO();

        try {

            // 查询公司缩写下拉列表
            HTProjectBean maxProjectNum = hTProjectDao.excSearchMaxProjectNum();

            // 如果最大的项目编号大于0，则代表有项目，否则项目编号前缀为HT10000
            if (null != maxProjectNum) {
                // 返回项目编号+1
                htProjectVO.setProjectNumPrefix("HT" + StringToolUtils.convertNullObjectToString(
                        Integer.parseInt(maxProjectNum.getProjectNum()) + 1));
            } else {
                // 返回原始项目编号
                htProjectVO.setProjectNumPrefix("HT1317");
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htProjectVO;
    }

    /**
     * 添加项目信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public String excSaveProjectInfo(HTProjectVO paramVO) throws LogicException, SystemException {

        String contractId = "";// 合同id

        try {

            Date tempCurrentDate = getCurrentDate();// 临时变量：当前时间
            String operationUserId = StringToolUtils.convertNullObjectToString(paramVO.getLoginUserInfo().getLoginUserId());// 创建人和修改人id
            String projectNum = StringToolUtils.convertNullObjectToString(paramVO.getProjectNum());// 项目编号
            String contractNum = StringToolUtils.convertNullObjectToString(paramVO.getContractNum());// 合同编号
            String companyId = StringToolUtils.convertNullObjectToString(paramVO.getCompanyId());// 公司id
            String projectName = StringToolUtils.convertNullObjectToString(paramVO.getProjectName());// 项目名称

            TbProjectEntity tbProjectEntity = new TbProjectEntity();
            tbProjectEntity.setCompanyId(companyId);// 设置公司id
            tbProjectEntity.setProjectNum(projectNum);// 设置项目编号

            //========================= 查询项目编号是否重复  start ========================
            int count = hTProjectDao.excSearchProjectNumInfo(tbProjectEntity);// 查询是否有相同的项目编号
            if (count > NumberToolUtils.INT_ZERO) {// 有重复，抛出异常(该项目编号已存在，请修改后重新提交！)
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0001);
            }
            //========================= 查询项目编号是否重复  end ========================

            //========================= 查询项目名称是否重复  start ========================
            tbProjectEntity.setProjectName(projectName);// 设置项目名称
            int nameCount = hTProjectDao.excSearchProjectNameInfo(tbProjectEntity);// 查询是否有相同的项目名称
            if (nameCount > NumberToolUtils.INT_ZERO) {// 有重复，抛出异常(该项目名称已存在，请修改后重新提交！)
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0011);
            }
            //========================= 查询项目名称是否重复  end ========================

            //========================= 添加项目信息  start ========================
            tbProjectEntity.setProjectName(projectName);// 设置项目名称
            tbProjectEntity.setCounselTypePeriod(StringToolUtils.convertNullObjectToString(paramVO.getCounselTypePeriod()));// 设置项目咨询类别
            tbProjectEntity.setProjectNum(projectNum);// 设置项目编号
            tbProjectEntity.setCompanyId(companyId);// 设置公司id
            tbProjectEntity.setStartDate(tempCurrentDate);// 设置开始时间
            tbProjectEntity.setProjectSts(StringToolUtils.STRING_ZERO);// 设置项目状态 0:未完成
            tbProjectEntity.setCreateDate(tempCurrentDate);// 设置创建时间
            tbProjectEntity.setUpdateDate(tempCurrentDate);// 设置修改时间
            tbProjectEntity.setCreateUserId(operationUserId);// 设置创建人id
            tbProjectEntity.setUpdateUserId(operationUserId);// 设置修改人id
            tbProjectEntity.setIsDelFlg(Constant.IS_DEL_FLG_ENABLE);// 是否删除
            tbProjectEntityMapper.insertSelective(tbProjectEntity);// 添加项目信息
            //========================= 添加项目信息  end ========================

            HTProjectBean htProjectBean = new HTProjectBean();
            htProjectBean.setContractNum(contractNum);// 设置合同编号
            htProjectBean.setProjectNum(projectNum);// 设置项目编号

            //========================= 查询合同编号是否重复  start ========================
            int contractCount = hTProjectDao.excSearchContractNumInfo(htProjectBean);// 查询是否有相同的合同编号
            if (contractCount > NumberToolUtils.INT_ZERO) {// 有重复，抛出异常(合同编号已存在，请修改后重新提交！)
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0002);
            }
            //========================= 查询合同编号是否重复  end ========================

            //========================= 添加合同信息  start ========================
            TbContractEntity tbContractEntity = new TbContractEntity();
            tbContractEntity.setContractNum(contractNum);// 设置合同编号
            tbContractEntity.setVerifyState(StringToolUtils.STRING_ZERO);// 设置合同状态
            tbContractEntity.setStartDate(tempCurrentDate);// 设置开始时间
            tbContractEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);//是否删除
            tbContractEntity.setCreateDate(tempCurrentDate);//创建时间
            tbContractEntity.setCreateUserId(operationUserId);//创建人
            tbContractEntity.setUpdateDate(tempCurrentDate);//更新时间(新建时创建时间默认为更新时间)
            tbContractEntity.setUpdateUserId(operationUserId);//更新人(新建时创建人默认为更新人)
            tbContractEntity.setProjectId(StringToolUtils.convertNullObjectToString(tbProjectEntity.getProjectId()));// 设置项目id
            tbContractEntity.setCooperateContract(UUID.randomUUID().toString());// 设置合作合同uuid

            for(HTProjectVO htProjectVO:paramVO.getContractList()){// 循环添加合同
                tbContractEntity.setJobId(StringToolUtils.convertNullObjectToString(htProjectVO.getJobId()));// 设置职位id
                tbContractEntity.setDepartmentId(StringToolUtils.convertNullObjectToString(htProjectVO.getDepartmentId()));// 设置部门id
                tbContractEntityMapper.insertSelective(tbContractEntity);// 添加合同信息

                //启动工作流
                Map<String, Object> variables = new HashMap<>();
                HTProjectVO projectVO = (HTProjectVO) ObjectReflectUtils.convertObj(tbProjectEntity, HTProjectVO.class);
                HTContractVO contractVO = (HTContractVO) ObjectReflectUtils.convertObj(tbContractEntity, HTContractVO.class);
                variables.put(WorkFlowConstant.KEY_PROJECT_VO, projectVO);
                variables.put(WorkFlowConstant.KEY_CONTRACT_VO, contractVO);
                CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
                commonActivitiListenVO.setDepartmentId(contractVO.getDepartmentId());
                variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
                variables.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
                variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
                projectProcessService.excStart(variables);
                contractId = contractVO.getContractId();
            }
            //========================= 添加合同信息  end ========================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
        return contractId;
    }

    /**
     * 查询项目列表数据
     *
     * @return 项目列表数据
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchProjectList(HTProjectVO paramVO) throws SystemException {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // 项目对象
            HTProjectBean paramBean = new HTProjectBean();

            // 存放部门id的集合
            List<String> departmentIdList = new ArrayList<>();

            // 通过当前登录人id查询所属部门id集合
            List<String> returnDepartmentIdList = hTProjectDao
                    .excSearchDepartmentIdByCurrentUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 行政部
            if(returnDepartmentIdList.contains(Constant.DEPT_ID_XZ)){
                departmentIdList.add(Constant.DEPT_ID_ZBDL);// 把招标部id集合中
                departmentIdList.add(Constant.DEPT_ID_ZJ);// 把造价部id集合中
                departmentIdList.add(Constant.DEPT_ID_XMJL);// 把监理部id集合中
            } else {
                if(returnDepartmentIdList.contains(Constant.DEPT_ID_ZBDL)){// 招标部
                    departmentIdList.add(Constant.DEPT_ID_ZBDL);// 把招标部id集合中
                }

                if(returnDepartmentIdList.contains(Constant.DEPT_ID_ZJ)){// 造价部
                    departmentIdList.add(Constant.DEPT_ID_ZJ);// 把造价部id集合中
                }

                if(returnDepartmentIdList.contains(Constant.DEPT_ID_XMJL)){// 监理部
                    departmentIdList.add(Constant.DEPT_ID_XMJL);// 把监理部id集合中
                }
            }

            // 设置部门id集合
            paramBean.setDepartmentIdList(departmentIdList);

            // 设置检索条件部门id
            paramBean.setDepartmentId(paramVO.getDepartmentId());

            // 设置项目状态
            paramBean.setProjectSts(paramVO.getProjectSts());

            // 设置项目编号
            paramBean.setProjectNum(paramVO.getProjectNum());

            // 设置项目编号
            paramBean.setProjectName(paramVO.getProjectName());

            // 如果时间不为空，则设置值
            if(!ListToolUtils.isEmpty(paramVO.getTimeList())){
                // 如果检索条件开始时间不为空
                if (!StringToolUtils.isNull(paramVO.getTimeList().get(0))) {
                    //将开始时间赋值给bean
                    paramBean.setStartDate(DateToolUtils.convertDateFormat(
                            paramVO.getTimeList().get(0), DateToolUtils.yyyy_MM_dd));
                }

                // 如果检索条件结束时间不为空
                if (!StringToolUtils.isNull(paramVO.getTimeList().get(1))) {
                    //将结束时间赋值给bean
                    paramBean.setEndDate(DateToolUtils.convertDateFormat(
                            paramVO.getTimeList().get(1), DateToolUtils.yyyy_MM_dd));
                }
            }

            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);

            // 查询项目总条数
            int retCount = hTProjectDao.excSearchProjectListCount(paramBean);

            // 查询项目列表数据
            List<HTProjectBean> projectList = hTProjectDao.excSearchProjectList(paramBean);

            // 集合
            List<HTProjectVO> retProjectList = new ArrayList<>();

            // 循环项目信息
            for (HTProjectBean project : projectList) {

                HTProjectVO projectVO = new HTProjectVO();

                // 设置项目id
                projectVO.setProjectId(StringToolUtils.convertNullObjectToString(
                        project.getProjectId()));

                // 设置项目编号
                projectVO.setProjectNum(StringToolUtils.convertNullObjectToString(
                        project.getProjectNum()));

                // 设置项目名称
                projectVO.setProjectName(StringToolUtils.convertNullObjectToString(
                        project.getProjectName()));

                // 设置咨询类别
                projectVO.setCounselTypePeriod(StringToolUtils.convertNullObjectToString(
                        project.getCounselTypePeriod()));

                // 设置项目作废标识
                projectVO.setProjectIsInvalidFlag(StringToolUtils.convertNullObjectToString(
                        project.getProjectIsInvalidFlag()));

                // 设置作废标识
                projectVO.setIsInvalidFlag(StringToolUtils.convertNullObjectToString(
                        project.getIsInvalidFlag()));

                // 设置作废原因
                projectVO.setInvalidReason(StringToolUtils.convertNullObjectToString(
                        project.getInvalidReason()));

                // 设置合同数
                projectVO.setContractCount(StringToolUtils.convertNullObjectToString(
                        project.getContractCount() == null ? StringToolUtils.STRING_ZERO : project.getContractCount()));

                // 设置项目状态
                if (null != project.getProjectSts())
                    projectVO.setProjectSts(StringToolUtils.convertNullObjectToString(
                            project.getProjectSts()));

                // 合同编号
                String contractNum = StringToolUtils.convertNullObjectToString(
                        project.getProjectNum() + "-" + project.getContractNum());

                // 当导入老数据的时候，有合同编号为空的情况，所以当合同编号为空的时候，页面上的合同编号就只显示项目编号
                if(StringToolUtils.isNull(project.getContractNum())){
                    contractNum = StringToolUtils.convertNullObjectToString(project.getProjectNum());
                }

                // 设置合同编号
                if (null != project.getContractNum())
                    projectVO.setContractNum(contractNum);

                // 设置合同名称
                if (null != project.getContractName())
                    projectVO.setContractName(StringToolUtils.convertNullObjectToString(project.getContractName()));
                // 设置合同名称
                if (null != project.getCooperateCompany())
                    projectVO.setCooperateCompany(StringToolUtils.convertNullObjectToString(project.getCooperateCompany()));

                // 设置部门名称
                if (null != project.getDepartmentName())
                    projectVO.setDepartmentName(StringToolUtils.convertNullObjectToString(
                            project.getDepartmentName()));

                // 设置负责人
                if (null != project.getDutyPeople())
                    projectVO.setDutyPeople(StringToolUtils.convertNullObjectToString(
                            project.getDutyPeople()));

                // 设置合同开始时间
                if (null != project.getContractDate())
                    projectVO.setContractDate(DateToolUtils.convertDateFormat(
                            project.getContractDate(), DateToolUtils.yyyy_MM_dd));

                // 已收入
                if (null != project.getIncomeReceived())
                    projectVO.setIncomeReceived(String.valueOf(
                            project.getIncomeReceived()));

                // 项目金额
                if (null != project.getContractAmount())
                    projectVO.setContractAmount(String.valueOf(
                            project.getContractAmount()));

                // 业务ID
                projectVO.setBusinessId(project.getBusinessId());
                // 合同ID
                projectVO.setContractId(project.getContractId());

                retProjectList.add(projectVO);

            }

            resultMap.put(Constant.DATA_LIST_KEY, retProjectList);

            resultMap.put(Constant.DATA_COUNT_KEY, retCount);

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    /**
     * 通过该项目的id查询项目下的合同（树形）
     *
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public ElementTreeVO excSearchProjectTree(HTProjectVO paramVO) throws SystemException {

        ElementTreeVO elementTreeVO = new ElementTreeVO();

        try {

            String loginUserId = paramVO.getLoginUserInfo().getLoginUserId();// 临时变量：当前登录用户id
            String projectId = paramVO.getProjectId();// 临时变量：项目id

            HTProjectBean paramBean = new HTProjectBean();
            // 设置项目id
            paramBean.setProjectId(projectId);
            // 通过该项目的id查询项目下的合同
            HTProjectBean project = hTProjectDao.excSearchProjectTreeV2(paramBean);

            String projectNum = project.getProjectNum();// 项目编号
            String projectSts = project.getProjectSts();// 项目状态(0.未完成 1.已完成(项目关闭也视为已完成))
            String isInvalidFlag = project.getIsInvalidFlag();// 作废状态(1.已作废/其他.未作废)
            String tempHideMenuFlgStr = StringToolUtils.STRING_ZERO;// 隐藏菜单标识 0：不隐藏  1：隐藏

            elementTreeVO.setId(projectId);// 设置项目id
            elementTreeVO.setLabel(projectNum);// 设置项目label(固定为字符串："项目编号")
            elementTreeVO.setShowTool(Constant.SHOW_FIRST_TYPE_MENU_FLAG);// 显示三个小点（1：一类菜单）
            elementTreeVO.setTreeNodeLevel(Constant.FIRST_LEVEL_MENU_FLAG);// 设置节点等级（1：第一级）
            elementTreeVO.setProjectSts(projectSts);// 设置项目状态(0.未完成 1.已完成(项目关闭也视为已完成))
            elementTreeVO.setIsInvalidFlag(isInvalidFlag);// 设置作废状态(1.已作废/其他.未作废)

            List<ElementTreeVO> oneLevelChildren = new ArrayList<>();

            if (!ListToolUtils.isEmpty(project.getHtContractBeanList())) {// 如果有合同，则把合同信息添加到一级树的children中

                // ===================================== 第二级 合同 start ===================================

                ElementTreeVO elementTreeVO2 = new ElementTreeVO();
                elementTreeVO2.setId(UUID.randomUUID().toString());// 设置合同id
                elementTreeVO2.setLabel(Constant.TWO_LEVEL_MENU_NAME_ONE);// 设置合同label
                elementTreeVO2.setTreeNodeLevel(Constant.SECOND_LEVEL_MENU_FLAG);// 设置节点等级
                elementTreeVO2.setObjType(Constant.SHOW_SECOND_TYPE_CONTRACT_FLAG);// 数据类型为合同
                List<ElementTreeVO> twoLevelChildren = new ArrayList<>();

                boolean applyUserFlag = true;// 设置主提审人标识  true:未设置主提审人  false:设置了主提审人
                boolean hasContractApplyFlag = true;// 设置主提审人标识  true:未设置主提审人  false:设置了主提审人

                for (HTProjectBean contract : project.getHtContractBeanList()) {// 循环项目合同信息

                    // 如果合同已经分配编撰人，并且没有设置过隐藏标识的情况下，则设置隐藏标识
                    if("3".equals(contract.getVerifyState()) && !StringToolUtils.STRING_ONE.equals(tempHideMenuFlgStr)){
                        tempHideMenuFlgStr = StringToolUtils.STRING_ONE;
                    }

                    // 临时变量：合同id
                    String contractId = StringToolUtils.convertNullObjectToString(contract.getContractId());
                    // 临时变量：作废标识
                    String contractIsInvalidFlag = StringToolUtils.convertNullObjectToString(contract.getIsInvalidFlag());
                    // 临时变量：合同编号
                    String contractNum = StringToolUtils.convertNullObjectToString(contract.getContractNum());
                    // 临时变量：部门id
                    String departmentId = StringToolUtils.convertNullObjectToString(contract.getDepartmentId());

                    if(StringToolUtils.isNull(contractNum)){// 如果合同编号为空，则合同编号就为项目编号
                        contractNum = projectNum;
                    } else {// 否则，合同编号就为项目编号-合同编号
                        contractNum = projectNum + "-" +contractNum;
                    }

                    ElementTreeVO elementTreeVO3 = new ElementTreeVO();
                    elementTreeVO3.setId(contractId);// 设置合同id
                    elementTreeVO3.setLabel(contractNum);// 设置合同label
                    elementTreeVO3.setDepartmentId(departmentId);// 设置部门id
                    elementTreeVO3.setShowTool(Constant.SHOW_SECOND_TYPE_MENU_FLAG);// 显示三个小点
                    elementTreeVO3.setTreeNodeLevel(Constant.THIRD_LEVEL_MENU_FLAG);// 设置节点等级
                    elementTreeVO3.setProjectSts(projectSts);// 设置项目状态
                    elementTreeVO3.setIsInvalidFlag(contractIsInvalidFlag);
                    elementTreeVO3.setPowerInfo(htUserPowerService.excSearchUserWorkPowerMap(paramVO.getLoginUserInfo(),
                            contractId,Constant.POWER_MODULE_BELONG_CONTRACT));// 权限

                    // ===================================== 第四级 基本信息、任务、请款单、补充协议 start ===================================

                    HTProjectBean htProjectBean = new HTProjectBean();
                    htProjectBean.setContractId(contractId);// 设置合同id

                    HTRequestMenuBean htRequestMenuBean = new HTRequestMenuBean();
                    htRequestMenuBean.setContractId(contractId);// 设置合同id

                    List<ElementTreeVO> threeLevelChildren = new ArrayList<>();

                    // ===================================== 项目基本信息 start ===================================
                    // 根据合同id,查询是否存在项目基本信息数据
                    TbContractBaseInfoEntityExample baseInfoEntityExample = new TbContractBaseInfoEntityExample();
                    TbContractBaseInfoEntityExample.Criteria baseInfoCriteria = baseInfoEntityExample.createCriteria();
                    baseInfoCriteria.andContractIdEqualTo(contractId);
                    List<TbContractBaseInfoEntity> baseInfoList = tbContractBaseInfoEntityMapper.selectByExample(baseInfoEntityExample);

                    ElementTreeVO baseInfo = new ElementTreeVO();
                    baseInfo.setLabel(Constant.TWO_LEVEL_MENU_NAME_BASE_INFO);// 设置"项目基本情况表"label
                    baseInfo.setTreeNodeLevel(Constant.FOURTH_LEVEL_MENU_FLAG);// 设置节点等级
                    baseInfo.setObjType(Constant.SHOW_FOUR_TYPE_BASE_INFO_FLAG);// 基本情况表类型标识
                    baseInfo.setContractId(contractId);// 合同id
                    baseInfo.setIsMainHeadFlag(StringToolUtils.STRING_ZERO);// 主提审人标识
                    baseInfo.setHasContractApply(StringToolUtils.STRING_ZERO);// 有合同已被提审
                    baseInfo.setIsInvalidFlag(isInvalidFlag);// 设置作废状态(1.已作废/其他.未作废)

                    if(ListToolUtils.isEmpty(baseInfoList)){// 如果没有项目基本信息，则设置uuid
                        baseInfo.setId(UUID.randomUUID().toString());// 设置baseId
                    }else{// 否则，设置项目基本信息的id
                        baseInfo.setId(baseInfoList.get(0).getBaseId());// 设置baseId
                    }

                    // 如果当前登录人为主提审人，则设置标识
                    if(applyUserFlag && (loginUserId.equals(contract.getApplyUserId()))){
                        baseInfo.setIsMainHeadFlag(StringToolUtils.STRING_ONE);// 设置主提审人
                        applyUserFlag = false;
                    }

                    // 如果有提审时间，则设置标识
                    if(hasContractApplyFlag && (null != contract.getApplyTime())){
                        baseInfo.setHasContractApply(StringToolUtils.STRING_ONE);// 设置有合同提审标识
                        hasContractApplyFlag = false;
                    }

                    // 把项目基本信息放入合同对象中
                    threeLevelChildren.add(baseInfo);
                    // ===================================== 项目基本信息 end ===================================

                    // ===================================== 任务 start ===================================
                    paramBean.setCurrentUserId(loginUserId);// 设置当前登录人id
                    paramBean.setDepartmentId(Constant.DEPT_ID_CW);// 设置财务的部门id
                    paramBean.setGgDepartmentId(Constant.DEPT_ID_GG);// 设置高管的部门id

                    // 查询当前登录人的所属部门
                    List<String> departmentIdList = hTProjectDao.excSearchDepartmentIdByUserId(paramBean);

                    // 如果是财务，则只展示合同和请款单的信息
                    if(!departmentIdList.contains(Constant.DEPT_ID_CW) ||
                            (departmentIdList.contains(Constant.DEPT_ID_CW) && departmentIdList.contains(Constant.DEPT_ID_GG))){

                        // 查询每个合同下的任务列表
                        List<HTProjectBean> retTaskList = contract.getHtTaskBeanList();

                        // 如果有任务，则添加任务对象
                        if (!ListToolUtils.isEmpty(retTaskList)) {

                            ElementTreeVO elementTreeVO4 = new ElementTreeVO();// 第四级：任务字段
                            elementTreeVO4.setId(UUID.randomUUID().toString());// 设置任务字段id(uuid)
                            elementTreeVO4.setLabel(Constant.FOUR_LEVEL_MENU_NAME_TASK);// 设置任务label(常量)
                            elementTreeVO4.setTreeNodeLevel(Constant.FOURTH_LEVEL_MENU_FLAG);// 设置节点等级("4")

                            List<ElementTreeVO> fourLevelChildren = new ArrayList<>();

                            // 循环每个合同下的任务
                            for (HTProjectBean retTask : retTaskList) {

                                // 如果任务已经被提审，并且没有设置过隐藏标识的情况下，则设置隐藏标识
                                if(null != retTask.getVerifyState() && !StringToolUtils.STRING_ONE.equals(tempHideMenuFlgStr)){
                                    tempHideMenuFlgStr = StringToolUtils.STRING_ONE;
                                }

                                // 查询每个合同下的任务的阶段
                                List<HTProjectBean> retTaskPeriodList = retTask.getHtTaskPeriodBeanList();

                                String taskId = StringToolUtils.convertNullObjectToString(retTask.getTaskId());// 任务id(主键)
                                String taskName = StringToolUtils.convertNullObjectToString(retTask.getTaskName());// 任务label(任务名称)
                                String counselTypeId = StringToolUtils.convertNullObjectToString(retTask.getCounselTypeId());// 咨询类别id
                                String counselTypeName = StringToolUtils.convertNullObjectToString(retTask.getCounselTypeName());// 咨询类别名称
                                String isSubmitReedit = StringToolUtils.convertNullObjectToString(retTask.getIsSubmitReedit());// 是否提了重新编辑申请
                                String approveTime = StringToolUtils.convertNullObjectToString(retTask.getApproveTime());// 审核时间

                                ElementTreeVO elementTreeVO5 = new ElementTreeVO();// 第五级：任务对象
                                elementTreeVO5.setId(taskId);// 设置任务id(主键)
                                elementTreeVO5.setLabel(taskName);// 设置任务label(任务名称)
                                elementTreeVO5.setShowTool(Constant.SHOW_THIRD_TYPE_MENU_FLAG);// 显示三个小点
                                elementTreeVO5.setContractId(contractId);// 合同id
                                elementTreeVO5.setTreeNodeLevel(Constant.FIFTH_LEVEL_MENU_FLAG);// 设置节点等级("5")
                                elementTreeVO5.setProjectSts(projectSts);// 设置项目状态
                                elementTreeVO5.setIsInvalidFlag(contractIsInvalidFlag);// 设置作废状态
                                elementTreeVO5.setDepartmentId(departmentId);// 部门id
                                elementTreeVO5.setCounselTypeId(counselTypeId);// 设置咨询类别id
                                elementTreeVO5.setCounselTypeName(counselTypeName);// 设置咨询类别名称
                                elementTreeVO5.setApproveTime(approveTime);// 设置审核时间
                                elementTreeVO5.setObjType("task");
                                elementTreeVO5.setPowerInfo(htUserPowerService.excSearchUserWorkPowerMap(
                                        paramVO.getLoginUserInfo(),retTask.getTaskId(),Constant.POWER_MODULE_BELONG_TASK));//任务所含权限

                                // 如果任务的审核状态为空,则继续判断该任务下的任务阶段和归档任务的审核状态
                                if(StringToolUtils.isNull(retTask.getVerifyState())){
                                    // 循环每个任务下的任务阶段
                                    for (HTProjectBean verifyStateBean : retTaskPeriodList) {
                                        // 如果任务阶段的审核状态为空,则继续判断该任务下的归档任务的审核状态
                                        if(StringToolUtils.isNull(verifyStateBean.getVerifyState())){
                                            // 查询每个任务下的归档任务
                                            List<HTProjectBean> retArchiveTaskList = hTProjectDao.excSearchArchiveTaskListByTaskId(retTask);
                                            // 循环每个任务下的归档任务
                                            for (HTProjectBean archiveTask : retArchiveTaskList) {
                                                // 如果归档任务的审核状态为空，则设置空值
                                                if(StringToolUtils.isNull(archiveTask.getVerifyState())){
                                                    // 设置审核状态
                                                    elementTreeVO5.setVerifyState(StringToolUtils.STRING_EMPTY);
                                                } else {
                                                    // 设置审核状态
                                                    elementTreeVO5.setVerifyState(archiveTask.getVerifyState());
                                                    break;
                                                }
                                            }
                                        } else {
                                            elementTreeVO5.setVerifyState(verifyStateBean.getVerifyState());// 设置审核状态
                                            break;
                                        }
                                    }
                                } else {
                                    // 设置审核状态
                                    elementTreeVO5.setVerifyState(retTask.getVerifyState());
                                }

                                // 如果任务的审核时间为空,则继续判断该任务下的任务阶段和归档任务的审核时间
                                if (null == retTask.getApproveTime()) {
                                    // 循环每个任务下的任务阶段
                                    for (HTProjectBean verifyStateBean : retTaskPeriodList) {
                                        // 如果任务阶段的审核时间为空,则继续判断该任务下的归档任务的审核时间是否为空
                                        if(null == verifyStateBean.getApproveTime()){
                                            // 查询每个任务下的归档任务
                                            List<HTProjectBean> retArchiveTaskList = hTProjectDao.excSearchArchiveTaskListByTaskId(retTask);
                                            // 循环每个任务下的归档任务
                                            for (HTProjectBean archiveTask : retArchiveTaskList) {
                                                // 如果归档任务的审核时间为空，则设置空值
                                                if(null == archiveTask.getApproveTime()){
                                                    // 设置审核时间
                                                    elementTreeVO5.setApproveTime(StringToolUtils.STRING_EMPTY);
                                                } else {
                                                    // 设置审核时间
                                                    elementTreeVO5.setApproveTime(DateToolUtils.convertDateFormat(
                                                            archiveTask.getApproveTime(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                                                    break;
                                                }
                                            }
                                        } else {
                                            // 设置审核时间
                                            elementTreeVO5.setApproveTime(DateToolUtils.convertDateFormat(
                                                    verifyStateBean.getApproveTime(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                                            break;
                                        }
                                    }
                                } else {
                                    // 设置审核时间
                                    elementTreeVO5.setApproveTime(DateToolUtils.convertDateFormat(
                                            retTask.getApproveTime(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
                                }

                                TbTaskUserRelationEntityExample tbTaskUserRelationEntityExample = new TbTaskUserRelationEntityExample();
                                TbTaskUserRelationEntityExample.Criteria criteria2 = tbTaskUserRelationEntityExample.createCriteria();
                                criteria2.andTaskIdEqualTo(retTask.getTaskId());// 任务id
                                criteria2.andIsMainDutyEqualTo(StringToolUtils.STRING_ONE);// 主提审人标识
                                // 查询主提审人信息
                                List<TbTaskUserRelationEntity> tbTaskUserRelationEntityList =  tbTaskUserRelationEntityMapper.selectByExample(tbTaskUserRelationEntityExample);

                                if (!ListToolUtils.isEmpty(retTaskPeriodList)) {// 如果查询到任务阶段，则设置到第六级数据中
                                    List<ElementTreeVO> fiveLevelChildren = new ArrayList<>();
                                    for (HTProjectBean retTaskPeriod : retTaskPeriodList) {// 循环每个合同下的任务阶段

                                        String relationId = StringToolUtils.convertNullObjectToString(retTaskPeriod.getRelationId());// 临时变量：任务阶段id(主键)
                                        String periodName = StringToolUtils.convertNullObjectToString(retTaskPeriod.getPeriodName());// 临时变量：阶段label(阶段名称)
                                        String periodType = StringToolUtils.convertNullObjectToString(retTaskPeriod.getPeriodType());// 临时变量：阶段类型
                                        String pageContent = StringToolUtils.convertNullObjectToString(retTaskPeriod.getPageContent());// 临时变量：页面显示内容
                                        String periodDesc = StringToolUtils.convertNullObjectToString(retTaskPeriod.getPeriodDesc());// 临时变量：阶段描述
                                        String currentState = StringToolUtils.convertNullObjectToString(retTask.getCurrentState()); // 临时变量：任务流程状态
                                        String isIgnore = StringToolUtils.isNull(retTaskPeriod.getIsIgnore())?"0":retTaskPeriod.getIsIgnore();// 临时变量：是否忽略
                                        String isFinish = StringToolUtils.isNull(retTaskPeriod.getIsFinish())?"0":retTaskPeriod.getIsFinish();// 临时变量：是否完成

                                        ElementTreeVO elementTreeVO6 = new ElementTreeVO();// 第六级：任务阶段对象
                                        elementTreeVO6.setId(relationId);// 设置任务阶段id(主键)
                                        elementTreeVO6.setLabel(periodName);// 设置阶段label(阶段名称)
                                        elementTreeVO6.setTreeNodeLevel(Constant.SIXTH_LEVEL_MENU_FLAG);// 设置节点等级("6")
                                        elementTreeVO6.setPeriodType(periodType);// 设置阶段类型
                                        elementTreeVO6.setPageContent(pageContent);// 设置页面显示内容
                                        elementTreeVO6.setPeriodDesc(periodDesc);// 设置阶段描述
                                        elementTreeVO6.setCurrentState(currentState);// 设置任务流程状态
                                        elementTreeVO6.setRelationId(relationId);// 设置任务阶段关联id
                                        elementTreeVO6.setDepartmentId(departmentId);// 设置所属部门id
                                        elementTreeVO6.setTaskId(taskId);// 设置所属任务id
                                        elementTreeVO6.setIsIgnore(isIgnore);// 设置是否忽略
                                        elementTreeVO6.setIsFinish(isFinish);// 设置是否完成
                                        elementTreeVO6.setProjectSts(projectSts);// 设置项目状态(0.未完成 1.已完成(项目关闭也视为已完成))
                                        elementTreeVO6.setIsInvalidFlag(contractIsInvalidFlag);// 设置作废状态
                                        elementTreeVO6.setCounselTypeId(counselTypeId);// 设置咨询类别id
                                        elementTreeVO6.setIsSubmitReedit(isSubmitReedit);// 设置是否提了重新编辑申请

                                        String isMainDuty = StringToolUtils.STRING_ZERO;// 临时变量：不是主提审人
                                        if(loginUserId.equals(tbTaskUserRelationEntityList.get(0).getUserId())){// 如果当前登录人是主提审人
                                            isMainDuty = StringToolUtils.STRING_ONE;// 是主提审人
                                        }

                                        elementTreeVO6.setIsMainHeadFlag(isMainDuty);// 是否是主提审人
                                        fiveLevelChildren.add(elementTreeVO6);// 把六级对象添加到list中
                                    }
                                    elementTreeVO5.setChildren(fiveLevelChildren); // 设置五级节点的children
                                }
                                fourLevelChildren.add(elementTreeVO5);// 把五级对象添加到list中
                            }
                            elementTreeVO4.setChildren(fourLevelChildren);// 设置四级节点的children
                            threeLevelChildren.add(elementTreeVO4);// 把四级对象添加到list中
                        }

                    }
                    // ===================================== 任务 end ===================================

                    // ===================================== 请款单 start ===================================
                    // 查询每个合同下的请款单
                    List<HTProjectBean> requestMenuBeanList = contract.getHtRequestMenuBeanList();

                    // 如果有请款单，则添加请款单对象
                    if (!ListToolUtils.isEmpty(requestMenuBeanList)) {

                        ElementTreeVO elementTreeVO4 = new ElementTreeVO();// 第四级：请款单字段
                        elementTreeVO4.setId(UUID.randomUUID().toString());// 设置请款单字段id(uuid)
                        elementTreeVO4.setLabel(Constant.FOUR_LEVEL_MENU_NAME_REQUEST_MENU);// 设置请款单label(常量)
                        elementTreeVO4.setTreeNodeLevel(Constant.FOURTH_LEVEL_MENU_FLAG);// 设置节点等级("4")

                        List<ElementTreeVO> fourLevelChildren = new ArrayList<>();

                        // 循环每个合同下的请款单
                        for (HTProjectBean retRequestMenu : requestMenuBeanList) {

                            // 如果任务已经被提审，并且没有设置过隐藏标识的情况下，则设置隐藏标识
                            if(null != retRequestMenu.getVerifyState() && !StringToolUtils.STRING_ONE.equals(tempHideMenuFlgStr)){
                                tempHideMenuFlgStr = StringToolUtils.STRING_ONE;
                            }

                            ElementTreeVO elementTreeVO5 = new ElementTreeVO();// 第五级：请款单对象
                            elementTreeVO5.setId(retRequestMenu.getRequestMenuId());//设置请款单ID
                            elementTreeVO5.setLabel(retRequestMenu.getRequestMenuName());//设置请款单名称
                            elementTreeVO5.setObjType(Constant.SHOW_THIRD_TYPE_REQUEST_MENU_FLAG);//数据类型为请款单
                            elementTreeVO5.setProjectSts(projectSts);//项目状态
                            elementTreeVO5.setIsInvalidFlag(contractIsInvalidFlag);// 设置作废状态
                            elementTreeVO5.setTreeNodeLevel(Constant.FIFTH_LEVEL_MENU_FLAG);// 设置节点等级("5")
                            elementTreeVO5.setDepartmentId(departmentId);// 设置部门ID
                            elementTreeVO5.setContractId(contractId);// 设置合同ID
                            //请款单所含权限
                            elementTreeVO5.setPowerInfo(htUserPowerService.excSearchUserWorkPowerMap(paramVO.getLoginUserInfo()
                                    ,retRequestMenu.getRequestMenuId(),Constant.POWER_MODULE_BELONG_REQUEST_MENU));
                            //判断是否是主提审人
                            if (retRequestMenu.getApplyUserId().equals(loginUserId)){
                                elementTreeVO5.setIsMainHeadFlag(Constant.REQUEST_MENU_LOGIN_USER_TYPE);
                            }
                            // 显示工具操作项
                            if (null == retRequestMenu.getVerifyState()&&!elementTreeVO5.getPowerInfo().isEmpty()){
                                elementTreeVO5.setShowTool(Constant.SHOW_THIRD_TYPE_REQUEST_MENU_FLAG);
                            }
                            fourLevelChildren.add(elementTreeVO5);
                        }
                        elementTreeVO4.setChildren(fourLevelChildren);
                        threeLevelChildren.add(elementTreeVO4);
                    }
                    // ===================================== 请款单 end ===================================

                    // ===================================== 补充协议 start ===================================
                    // todo 暂时固定数据，等需求确认之后再做修改，if条件届时也会修改
                    // todo 20200119 09:42 注释掉补充协议代码，等节后在上
//                    if (true) {
//                        // 补充协议对象
//                        ElementTreeVO elementTreeVO4 = new ElementTreeVO();
//                        elementTreeVO4.setId(UUID.randomUUID().toString());
//                        elementTreeVO4.setLabel("补充协议");
//                        elementTreeVO4.setTreeNodeLevel(Constant.FOURTH_LEVEL_MENU_FLAG);
//
//                        // 补充协议子对象
//                        ElementTreeVO elementTreeVO5 = new ElementTreeVO();
//                        elementTreeVO5.setId(UUID.randomUUID().toString());
//                        elementTreeVO5.setLabel("协议1");
//                        elementTreeVO5.setTreeNodeLevel(Constant.FIFTH_LEVEL_MENU_FLAG);
//                        elementTreeVO5.setObjType("supplementAgreement");
//
//                        // 把补充协议子对象添加到补充协议集合中
//                        List<ElementTreeVO> fourLevelChildren = new ArrayList<>();
//                        fourLevelChildren.add(elementTreeVO5);
//                        elementTreeVO4.setChildren(fourLevelChildren);
//
//                        // 把补充协议对象放入合同对象中
//                        threeLevelChildren.add(elementTreeVO4);
//                    }
                    // ===================================== 补充协议 end ===================================

                    // 如果合同的子集不为空，则设置合同的子集
                    if (!ListToolUtils.isEmpty(threeLevelChildren)) {
                        elementTreeVO3.setChildren(threeLevelChildren);// 设置三级节点的children
                    }

                    // ===================================== 基本信息、任务、请款单、补充协议 end ===================================

                    // 第三级：合同设置隐藏标识
                    elementTreeVO3.setHideMenuFlg(tempHideMenuFlgStr);

                    twoLevelChildren.add(elementTreeVO3);

                }

                elementTreeVO2.setChildren(twoLevelChildren);// 设置children
                oneLevelChildren.add(elementTreeVO2);// 添加合同字段对象到child中

                // ===================================== 第二级 合同 end ===================================
            }

            if(StringToolUtils.STRING_ONE.equals(tempHideMenuFlgStr)){// 如果有隐藏标识，则向项目中添加隐藏标识
                elementTreeVO.setHideMenuFlg(StringToolUtils.STRING_ONE);// 第一级：项目设置隐藏标识
            }
            elementTreeVO.setChildren(oneLevelChildren);// 设置children

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }

        return elementTreeVO;
    }

    /**
     * 查询项目详情
     *
     * @return 项目详情
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public HTProjectVO excSearchProjectInfo(HTProjectVO paramVO) throws SystemException {

        HTProjectVO projectVO = new HTProjectVO();

        try {

            // 查询项目列表数据
            TbProjectEntity tbProjectEntity = tbProjectEntityMapper.selectByPrimaryKey(paramVO.getProjectId());


            if (null != tbProjectEntity) {

                projectVO.setProjectId(tbProjectEntity.getProjectId());// 设置项目id
                projectVO.setProjectNum(tbProjectEntity.getProjectNum());// 设置项目编号
                projectVO.setProjectName(tbProjectEntity.getProjectName());// 设置项目名称
                projectVO.setCounselTypePeriod(tbProjectEntity.getCounselTypePeriod());// 设置咨询类别
                projectVO.setProjectNumPrefix(StringToolUtils.convertNullObjectToString(tbProjectEntity.getProjectNum().substring(0, 6)));// 设置项目编号前缀
                projectVO.setCompanyId(StringToolUtils.convertNullObjectToString(tbProjectEntity.getCompanyId()));// 设置公司id
                projectVO.setCondense(StringToolUtils.convertNullObjectToString(tbProjectEntity.getProjectNum().substring(6)));// 设置公司缩写
                projectVO.setIsInvalidFlag(StringToolUtils.convertNullObjectToString(tbProjectEntity.getIsInvalidFlag()));//作废标识
                projectVO.setInvalidReason(StringToolUtils.convertNullObjectToString(tbProjectEntity.getInvalidReason()));//作废原因

            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return projectVO;
    }

    /**
     * 修改项目信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdateProjectInfo(HTProjectVO paramVO) throws LogicException, SystemException {

        try {

            String projectId = StringToolUtils.convertNullObjectToString(paramVO.getProjectId());// 项目id
            String projectNum = StringToolUtils.convertNullObjectToString(paramVO.getProjectNum());// 项目编号
            String projectName = StringToolUtils.convertNullObjectToString(paramVO.getProjectName());// 项目名称
            String counselTypePeriod = StringToolUtils.convertNullObjectToString(paramVO.getCounselTypePeriod());// 咨询类别
            String companyId = StringToolUtils.convertNullObjectToString(paramVO.getCompanyId());// 公司id

            //========================= 查询被分配的合同数  start ========================
            HTProjectBean htProjectBean = new HTProjectBean();
            htProjectBean.setProjectId(projectId);// 设置项目id
            int contractCount = hTProjectDao.excSearchContractCount(htProjectBean);
            if (contractCount > NumberToolUtils.INT_ZERO) {// 有合同，抛出异常(有合同已被分配，不能修改项目编号！)
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0003);
            }
            //========================= 查询被分配的合同数  end ========================

            //========================= 查询项目编号是否重复  start ========================
            TbProjectEntity tbProjectEntity = new TbProjectEntity();
            tbProjectEntity.setProjectId(projectId);// 设置项目id
            tbProjectEntity.setProjectNum(projectNum);// 设置项目编号
            tbProjectEntity.setProjectName(projectName);// 设置项目名称
            int nameCount = hTProjectDao.excSearchProjectNameInfo(tbProjectEntity);
            if (nameCount > NumberToolUtils.INT_ZERO) {// 有重复，抛出异常(该项目名称已存在，请修改后重新提交！)
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0011);
            }
            tbProjectEntity.setCounselTypePeriod(counselTypePeriod);// 设置咨询类别
            tbProjectEntity.setCompanyId(companyId);// 设置公司id
            int projectNumCount = hTProjectDao.excSearchProjectNumInfo(tbProjectEntity);
            if (projectNumCount > NumberToolUtils.INT_ZERO) {// 有重复，抛出异常(该项目编号已存在，请修改后重新提交！)
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0001);
            }
            //========================= 查询项目编号是否重复  end ========================

            tbProjectEntity.setUpdateDate(getCurrentDate());// 设置修改时间
            tbProjectEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());// 设置修改人id
            tbProjectEntityMapper.updateByPrimaryKeySelective(tbProjectEntity);// 修改项目信息

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 删除项目信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excDeleteProjectInfo(HTProjectVO paramVO) throws LogicException, SystemException {

        try {

            //========================= 查询被分配的合同数  start ========================

            HTProjectBean htProjectBean = new HTProjectBean();

            // 设置项目id
            htProjectBean.setProjectId(StringToolUtils.convertNullObjectToString(
                    paramVO.getProjectId()));

            int contractCount = hTProjectDao.excSearchContractCount(htProjectBean);

            // 有合同，抛出异常(有合同已被分配，不能删除项目！)
            if (contractCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0004);
            }

            //========================= 查询被分配的合同数  end ========================

            //========================= 删除项目信息  start ========================

            // 删除项目信息
            tbProjectEntityMapper.deleteByPrimaryKey(
                    StringToolUtils.convertNullObjectToString(paramVO.getProjectId()));

            //========================= 删除项目信息  end ========================

            //========================= 删除合同信息  start ========================

            // 删除合同信息
            hTProjectDao.excDeleteContractInfo(htProjectBean);

            //========================= 删除合同信息  end ========================

            //projectProcessService.excDelete(paramVO.getProjectId());

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0009 删除失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0009);
        }
    }

    /**
     * 添加合同信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public String excSaveContractInfo(HTProjectVO paramVO) throws LogicException, SystemException {

        String contractId = StringToolUtils.STRING_EMPTY;

        try {

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(paramVO.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            // =====================================判断项目是否作废 start=========================================

            // 判断项目是否作废
            commonService.excIsInvalidProject(paramVO.getProjectId());

            // =====================================判断项目是否作废 end=========================================
            Date tempCurrentDate = getCurrentDate();// 临时变量：当前时间
            String contractNum = StringToolUtils.convertNullObjectToString(paramVO.getContractNum()); //合同编号
            String projectId = StringToolUtils.convertNullObjectToString(paramVO.getProjectId()); //项目id
            String tempOperationUserIdStr = StringToolUtils.convertNullObjectToString(paramVO.getLoginUserInfo().getLoginUserId());// 创建人和修改人id
            String cooperateContract = StringToolUtils.convertNullObjectToString(paramVO.getCooperateContract());// 合作合同

            //========================= 查询合同编号是否重复  start ========================
            HTProjectBean htProjectBean = new HTProjectBean();
            htProjectBean.setContractNum(contractNum);// 设置合同编号
            htProjectBean.setProjectId(projectId);// 设置项目id
            int count = hTProjectDao.excSearchContractNumInfo(htProjectBean);// 查询该项目下是否存在相同的合同
            if (count > NumberToolUtils.INT_ZERO) {// 有重复，抛出异常(合同编号已存在，请修改后重新提交！)
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0002);
            }
            //========================= 查询合同编号是否重复  end ========================

            //========================= 添加合同并启动工作流  start ========================
            TbContractEntity tbContractEntity = new TbContractEntity();

            TbContractBaseInfoEntityExample tbContractBaseInfoEntityExample = new TbContractBaseInfoEntityExample();
            TbContractBaseInfoEntityExample.Criteria criteria = tbContractBaseInfoEntityExample.createCriteria();
            criteria.andProjectIdEqualTo(projectId);
            List<TbContractBaseInfoEntity> tbContractBaseInfoEntityList = tbContractBaseInfoEntityMapper.selectByExample(tbContractBaseInfoEntityExample);

            // 添加 如果没有这个项目的数据，则添加一条，如果有，则修改
            if(!ListToolUtils.isEmpty(tbContractBaseInfoEntityList)){

                TbContractBaseInfoEntity tbContractBaseInfoEntity = tbContractBaseInfoEntityList.get(0);

                // 格式转换 TbContractEntity->TbContractBaseInfoEntity
                tbContractEntity = (TbContractEntity) ObjectReflectUtils.convertObj(tbContractBaseInfoEntity, TbContractEntity.class);

            }

            tbContractEntity.setContractNum(contractNum);// 设置合同编号
            tbContractEntity.setVerifyState(StringToolUtils.STRING_ZERO);// 设置合同状态
            tbContractEntity.setProjectId(projectId); // 设置项目id
            tbContractEntity.setStartDate(tempCurrentDate);// 设置开始时间
            tbContractEntity.setIsDelFlg(StringToolUtils.STRING_ZERO);//是否删除
            tbContractEntity.setCreateDate(tempCurrentDate);//创建时间
            tbContractEntity.setCreateUserId(tempOperationUserIdStr);//创建人
            tbContractEntity.setUpdateDate(tempCurrentDate);//更新时间(新建时创建时间默认为更新时间)
            tbContractEntity.setUpdateUserId(tempOperationUserIdStr);//更新人(新建时创建人默认为更新人)
            tbContractEntity.setCooperateContract(UUID.randomUUID().toString());// 设置合作合同uuid

            for(HTProjectVO htProjectVO:paramVO.getContractList()){// 循环添加合同
                tbContractEntity.setJobId(StringToolUtils.convertNullObjectToString(htProjectVO.getJobId()));// 设置职位id
                tbContractEntity.setDepartmentId(StringToolUtils.convertNullObjectToString(htProjectVO.getDepartmentId()));// 设置部门id
                tbContractEntityMapper.insertSelective(tbContractEntity);// 添加合同信息
                contractId = tbContractEntity.getContractId(); //获取合同id

                HTContractVO htContractVO = new HTContractVO();
                HTContractBean paramSearchPredictRequestBean = new HTContractBean();
                paramSearchPredictRequestBean.setContractId(projectId);// 合同id（项目id代替）// 预计收款时间（多条数据）
                List<HTContractBean> tmpPredictRequestRelationsList =
                        htContractDao.excSelectPredictRequestRelations(paramSearchPredictRequestBean);// 检索预计收款时间列表
                if(!ListToolUtils.isEmpty(tmpPredictRequestRelationsList)){// 如果基础项目信息表中有预计请款时间，则刚刚添加的合同也岩添加预计请款时间的数据
                    List<HTContractVO> retPredictRequestRelationsList = new ArrayList<>();
                    for(HTContractBean tempHTContractBean :tmpPredictRequestRelationsList){// 循环预计请款时间的数据，逐条插入新建的合同关联表中
                        HTContractVO tempVO = new HTContractVO();// 创建合同对象
                        tempVO.setStartDate(DateToolUtils.convertDateFormat(tempHTContractBean.getStartDate(), DateToolUtils.yyyy_MM_dd)); // 设置收款日期
                        tempVO.setReceivedAmount(StringToolUtils.convertNullObjectToString(tempHTContractBean.getReceivedAmount()));// 设置收款金额
                        tempVO.setIsProvisional(StringToolUtils.convertNullObjectToString(tempHTContractBean.getIsProvisional()));// 设置是否暂定
                        retPredictRequestRelationsList.add(tempVO);
                    }
                    htContractVO.setPredictRequestDateList(retPredictRequestRelationsList);// 设置预计收款时间列表
                    htContractVO.setLoginUserInfo(paramVO.getLoginUserInfo());// 设置登录人信息
                    htContractVO.setContractId(contractId);// 设置合同id
                    htContractServiceImpl.excInsertContractPredictRequestRelations(htContractVO);// 批量插入预计收款时间
                }

                // 项目类别(多checkbox)
                List<HTContractVO> tempProjectTypeList = new ArrayList<>();
                HTContractVO tempHTContractProjectTypeVO = null;
                List<HTContractVO> tempProjectFeatureList = new ArrayList<>();
                HTContractVO tempHTContractFeatureVO = null;

                HTContractBean paramSearchBean = new HTContractBean();
                paramSearchBean.setContractId(projectId);// 合同id（那项目id代替）
                // 检索项目类别列表
                List<HTContractBean> tmpHTProjectTypeRelationsList = htContractDao.excSelectContractProjectTypeRelations(paramSearchBean);
                // 如果项目类别不为空，项目属性json转为对象
                if (!ListToolUtils.isEmpty(tmpHTProjectTypeRelationsList)) {
                    //遍历 委托人性质
                    for (HTContractBean htContractBean : tmpHTProjectTypeRelationsList) {
                        // 初始化项目类别VO 并设置 项目类别
                        tempHTContractProjectTypeVO = new HTContractVO();
                        tempHTContractProjectTypeVO.setProjectType(htContractBean.getProjectType());

                        // 初始化项目属性VO 并设置 项目类别
                        tempHTContractFeatureVO = new HTContractVO();
                        tempHTContractFeatureVO =  InterfaceUtils.jsonToObject (htContractBean.getProjectTarget(), HTContractVO.class);// 初始化项目属性VO 并设置 项目类别
                        tempProjectFeatureList.add(tempHTContractFeatureVO);// 保存项目属性列表数据
                        tempHTContractProjectTypeVO.setProjectFeatureList(tempProjectFeatureList);// 设置项目类别具体数值
                        tempProjectTypeList.add(tempHTContractProjectTypeVO);// 保存项目类别列表数据
                    }
                    htContractVO.setProjectTypeList(tempProjectTypeList);// 设置项目类别数据
                    htContractServiceImpl.excInsertContractProjectTypeRelations(htContractVO);
                }

                //启动工作流
                HTContractVO contractVO = (HTContractVO) ObjectReflectUtils.convertObj(tbContractEntity, HTContractVO.class);
                contractVO.setLoginUserInfo(paramVO.getLoginUserInfo());
                contractProcessService.addContract(paramVO, contractVO);
                commonCacheService.clearContractCache(paramVO.getProjectId());
            }
            //========================= 添加合同并启动工作流  end ========================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }

        return contractId;
    }

    /**
     * 修改合同信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdateContractInfo(HTProjectVO paramVO) throws LogicException, SystemException {

        try {


            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(paramVO.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            // =====================================判断项目是否作废 start=========================================

            // 判断项目是否作废
            commonService.excIsInvalidProject(paramVO.getProjectId());

            // =====================================判断项目是否作废 end=========================================

            // =====================================判断合同是否作废 start=========================================

            // 判断合同是否作废
            commonService.excIsInvalidContract(paramVO.getContractId());

            // =====================================判断合同是否作废 end=========================================
            //========================= 修改合同信息  start ========================

            // 临时变量：当前时间
            Date tempCurrentDate = getCurrentDate();

            HTProjectBean htProjectBean = new HTProjectBean();

            //========================= 查询被分配的合同数  start ========================

            htProjectBean.setContractId(StringToolUtils.convertNullObjectToString(paramVO.getContractId()));// 设置合同id

            int contractCount = hTProjectDao.excSearchContractCount(htProjectBean);

            // 有合同，抛出异常(有合同已被分配，不能修改项目编号！)
            if (contractCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0003);
            }

            //========================= 查询被分配的合同数  end ========================

            //========================= 查询合同编号是否重复  start ========================

            htProjectBean.setContractNum(StringToolUtils.convertNullObjectToString(paramVO.getContractNum())); // 设置合同编号
            htProjectBean.setProjectId(StringToolUtils.convertNullObjectToString(paramVO.getProjectId()));// 设置项目id

            int count = hTProjectDao.excSearchContractNumInfo(htProjectBean);

            // 有重复，抛出异常(合同编号已存在，请修改后重新提交！)
            if (count > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0002);
            }

            //========================= 查询合同编号是否重复  end ========================

            //========================= 查询合同下是否有任务  start ========================

            int taskCount = hTProjectDao.excSearchTaskCountByContractId(htProjectBean);

            // 有任务，抛出异常(该合同下已创建任务，不能修改！)
            if (taskCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0006);
            }

            //========================= 查询合同下是否有任务  end ========================

            //========================= 查询合同下是否有请款单  start ========================

            int requestMenuCount = hTProjectDao.excSearchRequestMenuCountByContractId(htProjectBean);

            // 有任务，抛出异常(该合同下已创建请款单，不能修改！)
            if (requestMenuCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0007);
            }

            //========================= 查询合同下是否有请款单  end ========================

            TbContractEntity tbContractEntity = new TbContractEntity();
            tbContractEntity.setContractId(StringToolUtils.convertNullObjectToString(paramVO.getContractId()));// 设置合同id
            tbContractEntity.setContractNum(StringToolUtils.convertNullObjectToString(paramVO.getContractNum()));// 设置合同编号
            tbContractEntity.setJobId(StringToolUtils.convertNullObjectToString(paramVO.getJobId()));// 设置职位id
            tbContractEntity.setDepartmentId(StringToolUtils.convertNullObjectToString(paramVO.getDepartmentId()));// 设置部门id
            tbContractEntity.setUpdateDate(tempCurrentDate);// 设置修改时间
            tbContractEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());// 设置修改人id

            // =================================== 查询合同表中流程相关 start=========================================

            TbContractEntity retEntity = tbContractEntityMapper.selectByPrimaryKey(paramVO.getContractId());
            tbContractEntity.setProcessInstId(StringToolUtils.convertNullObjectToString(retEntity.getProcessInstId()));// 流程实例ID
            tbContractEntity.setDelegateTaskKey(StringToolUtils.convertNullObjectToString(retEntity.getDelegateTaskKey()));// 委托任务Key
            tbContractEntity.setDelegateTaskId(StringToolUtils.convertNullObjectToString(retEntity.getDelegateTaskId()));// 委托任务ID
            tbContractEntity.setSubmitNode(StringToolUtils.convertNullObjectToString(retEntity.getSubmitNode()));// 提交节点

            // =================================== 查询合同表中流程相关 end =========================================


            //启动工作流
            HTContractVO contractVO = (HTContractVO) ObjectReflectUtils.convertObj(tbContractEntity, HTContractVO.class);
            contractProcessService.excReassignment(contractVO);

            // 修改项目信息
            tbContractEntityMapper.updateByPrimaryKeySelective(tbContractEntity);

            //========================= 修改合同信息  end ========================

        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 删除合同信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excDeleteContractInfo(HTProjectVO paramVO) throws LogicException, SystemException {

        try {

            //========================= 查询被分配的合同数  start ========================

            HTProjectBean htProjectBean = new HTProjectBean();

            // 设置合同id
            htProjectBean.setContractId(StringToolUtils
                    .convertNullObjectToString(paramVO.getContractId()));

            int contractCount = hTProjectDao.excSearchContractCount(htProjectBean);

            // 有合同，抛出异常(该合同已被分配，不能删除！)
            if (contractCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0005);
            }

            //========================= 查询被分配的合同数  end ========================

            //========================= 查询合同下是否有任务  start ========================

            int taskCount = hTProjectDao.excSearchTaskCountByContractId(htProjectBean);

            // 有任务，抛出异常(该合同下已创建任务，不能删除！)
            if (taskCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0008);
            }

            //========================= 查询合同下是否有任务  end ========================

            //========================= 查询合同下是否有请款单  start ========================

            int requestMenuCount = hTProjectDao.excSearchRequestMenuCountByContractId(htProjectBean);

            // 有任务，抛出异常(该合同下已创建请款单，不能删除！)
            if (requestMenuCount > NumberToolUtils.INT_ZERO) {
                throw new LogicException(MessageConstant.MSG_ERROR_PROJECT_0009);
            }

            //========================= 查询合同下是否有请款单  end ========================

            //========================= 删除合同信息  start ========================

            //启动工作流
            contractProcessService.delContract(paramVO.getContractId());

            // 删除合同信息
            hTProjectDao.excDeleteContractInfo(htProjectBean);

            //========================= 删除合同信息  end ========================


        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0009 删除失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0009);
        }
    }

    /**
     * 通过项目id和部门id查询合同数
     *
     * @return 项目id和部门id查询合同数
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public HTProjectVO excSearchContractCountByProjectIdAndDepartmentId(HTProjectVO paramVO) throws SystemException {

        HTProjectVO htProjectVO = new HTProjectVO();

        try {

            HTProjectBean htProjectBean = new HTProjectBean();

            // 设置项目id
            htProjectBean.setProjectId(StringToolUtils.convertNullObjectToString(paramVO.getProjectId()));

            // 设置部门id
            htProjectBean.setDepartmentId(StringToolUtils.convertNullObjectToString(paramVO.getDepartmentId()));

            // 通过项目id和部门id查询合同数
            int count = hTProjectDao.excSearchContractCountByProjectIdAndDepartmentId(htProjectBean);

            if (count >= NumberToolUtils.INT_ZERO && count < 9) {
                // 设置合同数
                htProjectVO.setContractCount(StringToolUtils.STRING_ZERO
                        + StringToolUtils.convertNullObjectToString(count + 1));
            } else {
                // 设置合同数
                htProjectVO.setContractCount(StringToolUtils.convertNullObjectToString(count + 1));
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htProjectVO;
    }

    /**
     * 查询合同信息
     *
     * @return 合同信息
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public HTProjectVO excSearchContractInfo(HTProjectVO paramVO) throws SystemException {

        HTProjectVO htProjectVO = new HTProjectVO();

        try {

            HTProjectBean htProjectBean = new HTProjectBean();

            // 设置合同id
            htProjectBean.setContractId(StringToolUtils.convertNullObjectToString(paramVO.getContractId()));

            // 通过项目id和部门id查询合同数
            HTProjectBean retBean = hTProjectDao.excSearchContractInfo(htProjectBean);

            if (null != retBean) {
                // 用户名称
                htProjectVO.setUserName(StringToolUtils.convertNullObjectToString(
                        retBean.getDepartmentName() + "-" + retBean.getJobName()));

                // 职位id
                htProjectVO.setJobId(StringToolUtils
                        .convertNullObjectToString(retBean.getJobId()));

                // 部门id
                htProjectVO.setDepartmentId(StringToolUtils
                        .convertNullObjectToString(retBean.getDepartmentId()));
            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htProjectVO;
    }

    /**
     * 关闭项目
     *
     * @param paramVO 项目id
     * @throws SystemException 系统异常
     */
    @Override
    @Transactional
    public void excCloseProject(HTProjectVO paramVO) throws SystemException {

        // 临时变量：当前时间
        Date tempCurrentDate = getCurrentDate();

        try {

            TbProjectEntity tbProjectEntity = new TbProjectEntity();

            String projectId = StringToolUtils
                    .convertNullObjectToString(paramVO.getProjectId());

            // 设置项目id
            tbProjectEntity.setProjectId(projectId);

            // 设置项目状态 关闭
            tbProjectEntity.setProjectSts(StringToolUtils.STRING_ONE);

            // 设置项目关闭时间
            tbProjectEntity.setEndDate(tempCurrentDate);

            // 设置项目关闭人员
            tbProjectEntity.setEndProjectUserId(paramVO.getLoginUserInfo().getLoginUserId());

            // 设置修改时间
            tbProjectEntity.setUpdateDate(tempCurrentDate);

            // 设置修改人id
            tbProjectEntity.setUpdateUserId(paramVO.getLoginUserInfo().getLoginUserId());

            //启动工作流
            projectProcessService.closeProject(projectId);


            // 关闭项目
            tbProjectEntityMapper.updateByPrimaryKeySelective(tbProjectEntity);


            TbProjectEntity tbProjectEasdasdntity = tbProjectEntityMapper.selectByPrimaryKey(paramVO.getProjectId());


            System.out.println(tbProjectEasdasdntity.getProjectSts());

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 取得合同关联部门下的人员下拉列表
     *
     * @return 部门人员下拉列表
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTProjectVO> excSearchPersonListByInvolvedDepartment(HTProjectVO paramVO) throws SystemException {

        // 集合
        List<HTProjectVO> retPersonList = new ArrayList<>();

        try {

            HTProjectBean paramBean = new HTProjectBean();

            //设置所属部门ID
            paramBean.setDepartmentId(StringToolUtils.convertNullObjectToString(paramVO.getDepartmentId()));

            // 查询人员下拉列表
            List<HTProjectBean> personList = hTProjectDao.excSearchPersonListByDepartmentId(paramBean);

            HTProjectVO htProjectVO = null;

            // Bean 转化成 VO
            for (HTProjectBean htProjectBean : personList) {

                htProjectVO = new HTProjectVO();

                // 设置用户名
                htProjectVO.setUserName(StringToolUtils.convertNullObjectToString(htProjectBean.getUserName()));

                // 设置用户ID
                htProjectVO.setJobId(StringToolUtils.convertNullObjectToString(htProjectBean.getUserId()));

                retPersonList.add(htProjectVO);

            }

        } catch (Exception e) {
            // MSG_ERROR_COMMON_0002 检索失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return retPersonList;
    }

    /**
     * 分配合同编撰人
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional
    public void excUpdateContractUserInfo(HTProjectVO paramVO) throws LogicException, SystemException {
        try {

            // =====================================判断项目是否结束 start=========================================

            // 判断项目是否结束
            commonService.excIsOverProject(paramVO.getProjectId());

            // =====================================判断项目是否结束 end=========================================

            // =====================================判断项目是否作废 start=========================================

            // 判断项目是否作废
            commonService.excIsInvalidProject(paramVO.getProjectId());

            // =====================================判断项目是否作废 end=========================================

            // =====================================判断合同是否作废 start=========================================

            // 判断合同是否作废
            commonService.excIsInvalidContract(paramVO.getContractId());

            // =====================================判断合同是否作废 end=========================================
            if (paramVO.getUserId() == null || StringUtils.isBlank(paramVO.getUserId()))
                throw new LogicException(MessageConstant.MSG_INFO_PROCESS_NO_USER);
            TbContractEntity contract = tbContractEntityMapper.selectByPrimaryKey(paramVO.getContractId());
            HTContractVO contractVO = (HTContractVO) ObjectReflectUtils.convertObj(contract, HTContractVO.class);
            contractVO.setLoginUserInfo(paramVO.getLoginUserInfo());
            Map<String, Object> variable = new HashMap<>();
            variable.put(WorkFlowConstant.KEY_CONTRACT_VO, contractVO);
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            commonActivitiListenVO.setDepartmentId(contractVO.getDepartmentId());
            variable.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variable.put(WorkFlowConstant.KEY_DELEGATE_USER, paramVO.getUserId());
            variable.put(WorkFlowConstant.KEY_PRE_HANDLER, paramVO.getLoginUserInfo());
            variable.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            contractProcessService.excHandleTask(contractVO.getLoginUserInfo().getLoginUserId(),contract.getDelegateTaskId(), variable);
        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
    }

    /**
     * 查询合同编撰人
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    public HTContractVO queryContractWriter(String contractId) throws LogicException, SystemException {
        HTContractVO contract;
        try {
            TbContractEntity tbContractEntity = tbContractEntityMapper.selectByPrimaryKey(contractId);
            contract = (HTContractVO) ObjectReflectUtils.convertObj(tbContractEntity, HTContractVO.class);
        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0001);
        }
        return contract;
    }


    /**
     * 查询项目参与人员信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    public Map<String, Object> excSearchParticipantsInfoList(HTProjectVO paramVO) throws LogicException, SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HTProjectBean paramBean = new HTProjectBean();
            //项目ID
            paramBean.setProjectId(paramVO.getProjectId());
            // 分页
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);
            List<HTProjectBean> htProjectBeanList = hTProjectDao.excSearchParticipantsInfoList(paramBean);
            Integer dataCount = hTProjectDao.excSearchParticipantsInfoCount(paramBean);
            List<HTProjectVO> htProjectVOList = this.convertBeanToVOParticipants(htProjectBeanList);
            resultMap.put(Constant.DATA_LIST_KEY,htProjectVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,dataCount);
            return resultMap;
        } catch (LogicException lex) {
            throw lex;
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
    }

    /**
     * 导出项目参与人员信息
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTProjectVO> excExportParticipantsInfoList(HTProjectVO paramVO) throws SystemException {
        List<HTProjectVO> htProjectVOList = new ArrayList<>();
        try {
            HTProjectBean paramBean = new HTProjectBean();//创建项目bean对象
            paramBean.setProjectId(paramVO.getProjectId());// 设置项目id
            List<HTProjectBean> htProjectBeanList = hTProjectDao.excExportParticipantsInfoList(paramBean);// 查询项目参与人员信息
            htProjectVOList = this.convertBeanToVOParticipants(htProjectBeanList);//bean->vo
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htProjectVOList;
    }

    /**
     * 项目参与人员信息bean->vo
     * @throws SystemException 不可预估的异常
     */
    private List<HTProjectVO> convertBeanToVOParticipants(List<HTProjectBean> htProjectBeanList) throws SystemException {
        List<HTProjectVO> htProjectVOList = new ArrayList<>();
        try {
            for (HTProjectBean htProjectBean:htProjectBeanList){// bean->vo
                HTProjectVO htProjectVO = new HTProjectVO();
                //项目ID
                htProjectVO.setProjectId(htProjectBean.getProjectId());
                //合同编码
                htProjectVO.setContractNum(htProjectBean.getContractNum());
                //部门ID
                htProjectVO.setDepartmentId(htProjectBean.getDepartmentId());
                //部门名称
                htProjectVO.setDepartmentName(htProjectBean.getDepartmentName());
                //任务名
                htProjectVO.setTaskName(htProjectBean.getTaskName());
                //任务ID
                htProjectVO.setTaskId(htProjectBean.getTaskId());

                //工作类型
                //合同拟定
                if (htProjectBean.getJobType().equals(Constant.CONTRACT_TYPE_FLAG)){
                    htProjectVO.setJobType(Constant.CONTRACT_TYPE);
                    //执行任务
                }else if (htProjectBean.getJobType().equals(Constant.TASK_TYPE_FLAG)){
                    htProjectVO.setJobType(Constant.TASK_TYPE);
                    //请款单
                }else if (htProjectBean.getJobType().equals(Constant.REQUEST_MENU_TYPE_FLAG)){
                    htProjectVO.setJobType(Constant.REQUEST_MENU_TYPE);
                }

                htProjectVO.setUserId(htProjectBean.getUserId());//人员ID
                htProjectVO.setUserName(htProjectBean.getUserName());//人员名称
                htProjectVO.setUserType(Constant.LEADER);//人员身份
                htProjectVO.setProcessInstId(htProjectBean.getProcessInstId());//流程实例id

                // 如果是项目监理部并且任务id不为空，则查询归档表中的流程实例id
                if(Constant.DEPT_ID_XMJL.equals(htProjectBean.getDepartmentId())
                        && !StringToolUtils.STRING_EMPTY.equals(htProjectBean.getTaskId())){
                    TbTaskArchiveEntityExample tbTaskArchiveEntityExample = new TbTaskArchiveEntityExample();
                    TbTaskArchiveEntityExample.Criteria criteria = tbTaskArchiveEntityExample.createCriteria();
                    criteria.andTaskIdEqualTo(htProjectBean.getTaskId());
                    criteria.andCompleteTimeIsNull();
                    List<TbTaskArchiveEntity> tbTaskArchiveEntityList = tbTaskArchiveEntityMapper.selectByExample(tbTaskArchiveEntityExample);
                    // 如果查询所得数据不为空，则设置任务流程实例id
                    if(!ListToolUtils.isEmpty(tbTaskArchiveEntityList) && null != tbTaskArchiveEntityList.get(0)){
                        htProjectVO.setProcessInstId(tbTaskArchiveEntityList.get(0).getProcessInstId());//流程实例id
                    }
                }

                //当人员身份为执行人时
                if (htProjectBean.getUserType().equals(Constant.EXECUTOR_FLAG)){
                    htProjectVO.setUserType(Constant.EXECUTOR);
                }
                htProjectVOList.add(htProjectVO);
            }
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htProjectVOList;
    }

    /**
     * 查询成果文件台账列表
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> excSearchResultFileLedgerInfoList(HTProjectVO paramVO) throws SystemException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HTProjectBean paramBean = new HTProjectBean();
            paramBean.setProjectId(paramVO.getProjectId());// 设置项目ID
            paramBean = BusinessUtils.computeAndPutRowNumFromVOToBean(paramVO, paramBean);// 设置分页参数
            List<HTProjectBean> htProjectBeanList = hTProjectDao.excSearchResultFileLedgerInfoList(paramBean);// 查询分页数据
            Integer dataCount = hTProjectDao.excSearchResultFileLedgerInfoListCount(paramBean);// 查询总条数
            List<HTProjectVO> htProjectVOList = this.convertBeanToVOResultFile(htProjectBeanList);//bean->vo
            resultMap.put(Constant.DATA_LIST_KEY,htProjectVOList);
            resultMap.put(Constant.DATA_COUNT_KEY,dataCount);
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return resultMap;
    }

    /**
     * 导出成果文件台账列表
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    @Transactional(readOnly = true)
    public List<HTProjectVO> excExportResultFileLedgerInfoList(HTProjectVO paramVO) throws SystemException {
        List<HTProjectVO> htProjectVOList = new ArrayList<>();
        try {
            HTProjectBean paramBean = new HTProjectBean();//创建项目bean对象
            paramBean.setProjectId(paramVO.getProjectId());// 设置项目id
            List<HTProjectBean> htProjectBeanList = hTProjectDao.excExportResultFileLedgerInfoList(paramBean);// 查询成果文件列表数据
            htProjectVOList = this.convertBeanToVOResultFile(htProjectBeanList);//bean->vo
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htProjectVOList;
    }

    /**
     * 成果文件bean->vo
     * @throws SystemException 不可预估的异常
     */
    private List<HTProjectVO> convertBeanToVOResultFile(List<HTProjectBean> htProjectBeanList) throws SystemException {
        List<HTProjectVO> htProjectVOList = new ArrayList<>();
        try {
            for (HTProjectBean htProjectBean:htProjectBeanList){// bean->vo
                HTProjectVO htProjectVO = new HTProjectVO();

                String approveTime = DateToolUtils.convertDateFormat(
                        htProjectBean.getApproveTime(),DateToolUtils.YYYY_MM_DD_HH_MM);// 签发时间

                String taskMaterialName = htProjectBean.getTaskMaterialName();// 临时变量：成果文件名称

                String reportNum = StringToolUtils.convertNullObjectToString(htProjectBean.getReportNum());// 临时变量：成果文件编号

                // 如果是任务类型为模板类型，则设置成果文件名称的为模板文件名称
                if(StringToolUtils.STRING_ONE.equals(htProjectBean.getTaskType())){
                    taskMaterialName = BusinessUtils.convertTaskTemplateTypeToStateStr(
                            NumberToolUtils.convertNullToInteger(htProjectBean.getFormworkType()));// 任务文件模板名称
                }

                htProjectVO.setProjectNum(htProjectBean.getProjectNum()==null?"-":htProjectBean.getProjectNum());//设置项目编号
                htProjectVO.setProjectName(htProjectBean.getProjectName()==null?"-":htProjectBean.getProjectName());//设置项目名称
                String contractNum = htProjectBean.getContractNum();// 临时变量：合同编号
                if(StringToolUtils.isNull(contractNum)){// 如果没有合同编号，则用项目编号当做合同编号
                    contractNum = htProjectBean.getProjectNum();
                } else {// 否则，用"项目编号-合同编号"当做合同编号
                    contractNum = htProjectBean.getProjectNum() + "-" + contractNum;
                }
                htProjectVO.setContractNum(contractNum==null?"-":contractNum);//设置合同编码
                htProjectVO.setContractName(htProjectBean.getContractName()==null?"-":htProjectBean.getContractName());//设置合同名称
                htProjectVO.setReportNum(StringToolUtils.STRING_EMPTY.equals(reportNum)?"-":reportNum);//设置咨询报告编号
                htProjectVO.setCreateDate(htProjectBean.getApproveTime()==null?"-":approveTime);//设置签发时间
                htProjectVO.setFileNumber(htProjectBean.getFileNumber()==null?"-":htProjectBean.getFileNumber());//设置归档编号
                htProjectVO.setTaskMaterialName(taskMaterialName==null?"-":taskMaterialName);//设置成果文件名称

                HTProjectBean bean = new HTProjectBean();
                bean.setTaskId(htProjectBean.getTaskId());// 设置任务id
                List<HTProjectBean> htProjectBeans = hTProjectDao.excSearchUserNameByTaskId(bean);// 根据任务id查询负责人名称列表
                String userName = StringToolUtils.STRING_EMPTY;// 初始化负责人为空
                if(!ListToolUtils.isEmpty(htProjectBeans)){// 如果有负责人，则遍历赋值
                    for(int i = 0;i<htProjectBeans.size();i++){
                        if(htProjectBeans.size()-1 == i){// 如果当前数据为集合中的最后一条数据，则不在负责人名称后添加、
                            userName = userName.concat(htProjectBeans.get(i).getUserName());
                        }else{// 否则在负责人名称后添加、
                            userName = userName.concat(htProjectBeans.get(i).getUserName())+"、";
                        }
                    }
                }
                htProjectVO.setUserName(StringToolUtils.STRING_EMPTY.equals(userName)?"-":userName);//设置成果文件负责人
                htProjectVOList.add(htProjectVO);
            }
        } catch (Exception e) {
            // MSG_ERROR_COMMON_0001 提交失败！
            throw new SystemException(e, MessageConstant.MSG_ERROR_COMMON_0002);
        }
        return htProjectVOList;
    }

    /**
     * 查询咨询类别列表
     *
     * @throws LogicException  逻辑异常
     * @throws SystemException 不可预估的异常
     */
    @Override
    public List<HTDictProjectCounselTypeVO> excSearchCounselTypePeriodList() {
        //咨询类别列表
        List<HTDictProjectCounselTypeBean> htDictProjectCounselTypeBeanList = hTProjectDao.excSearchCounselTypePeriodList();

        List<HTDictProjectCounselTypeVO> htDictProjectCounselTypeVOList = new ArrayList<>();
        //遍历转换格式
        for (HTDictProjectCounselTypeBean htDictProjectCounselTypeBean :
                htDictProjectCounselTypeBeanList) {
            //转换格式
            HTDictProjectCounselTypeVO htDictProjectCounselTypeVO = (HTDictProjectCounselTypeVO) ObjectReflectUtils.convertObj(htDictProjectCounselTypeBean, HTDictProjectCounselTypeVO.class);
            htDictProjectCounselTypeVOList.add(htDictProjectCounselTypeVO);
        }
        return htDictProjectCounselTypeVOList;
    }

}
