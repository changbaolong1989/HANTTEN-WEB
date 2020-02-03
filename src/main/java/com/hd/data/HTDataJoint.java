package com.hd.data;

import com.hd.base.enums.WriteContractEnum;
import com.hd.common.dao.TbContractEntityMapper;
import com.hd.common.dao.TbProjectEntityMapper;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.entity.TbContractEntityExample;
import com.hd.common.entity.TbProjectEntity;
import com.hd.common.entity.TbProjectEntityExample;
import com.hd.common.util.Constant;
import com.hd.data.dao.OldDataJointRecordMapper;
import com.hd.data.entity.OldDataJointRecord;
import com.hd.data.entity.OldProjectInfoEntity;
import com.hd.manager.service.HTContractService;
import com.hd.manager.service.HTProjectService;
import com.hd.manager.vo.HTContractVO;
import com.hd.manager.vo.HTProjectVO;
import com.hd.manager.vo.HTUserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HTDataJoint {

    private static final Logger logger = LoggerFactory.getLogger(HTDataJoint.class);

    private static final String CANCELED = "A";

    private static final String FLAG_ARCHIVE = "A";

    private static final String FLAG_SUBMIT = "B";

    @Resource
    private HTProjectService projectService;

    @Resource
    private HTContractService contractService;

    @Resource
    private OldDataJointRecordMapper recordMapper;

    @Resource
    private TbContractEntityMapper contractEntityMapper;

    @Resource
    private TbProjectEntityMapper projectEntityMapper;

    @Transactional
    public void handle(Map<String, OldProjectInfoEntity> projectMap, HTProjectVO projectVO, String deptId, String deptName) throws Exception {
        int oldProjectId = -1;
        String projectContractNum = "";
        try {
            if (!projectMap.isEmpty()) {
                Date importDate = new Date();
                for (String num : projectMap.keySet()) {
                    OldProjectInfoEntity projectInfo = projectMap.get(num);
                    oldProjectId = projectInfo.getId();
                    projectContractNum = projectInfo.getProjectContractNum();
                    String contractId = this.createProject(projectInfo, projectVO, num);//创建项目
                    if (CANCELED.equals(projectInfo.getIsCancel())) {
                        TbContractEntity contractEntity = new TbContractEntity();
                        contractEntity.setContractId(contractId);
                        contractEntity.setIsInvalidFlag("1");
                        contractEntityMapper.updateByPrimaryKeySelective(contractEntity);
                    } else if (FLAG_SUBMIT.equals(projectInfo.getTargetFlag())) {
                        //allotWriter(projectInfo, contractId, num);//分配合同编撰人
                    } else if (FLAG_ARCHIVE.equals(projectInfo.getTargetFlag())) {
                        allotWriter(projectInfo, contractId, num);//分配合同编撰人
                        submitVerify(contractId, projectInfo, num);//提审
//                        approve(contractId, WriteContractEnum.QualityVerify.getNodeKey(), "0091dbf2-bfe4-11e9-b448-64006a07184f", "孟冉");//造价部门经理审批
                        approve(contractId, WriteContractEnum.QualityVerify.getNodeKey(), "0091dbf2-bfe4-11e9-b448-64006a07237f", "李超群");//招标部门经理审批
                        approve(contractId, WriteContractEnum.GenerManagerVerify.getNodeKey(), "0091dbf2-bfe4-11e9-b448-64006a07218f", "韩爽月");//质检部组员审批
                        approve(contractId, WriteContractEnum.Signer.getNodeKey(), "0091dbf2-bfe4-11e9-b448-64006a07182f", "张菁");//质检部副总审批
                        approve(contractId, WriteContractEnum.Seal.getNodeKey(), "0091dbf2-bfe4-11e9-b448-64006a07180f", "肖继伟");//签发人审批
                        seal(contractId, "0091dbf2-bfe4-11e9-b448-64006a07231f", "张嘉琪");//盖章
                    }
                    saveHistory(contractId, importDate, deptId, deptName, Integer.toString(projectInfo.getId()));
                }
            }
        } catch (Exception e) {
            logger.error("数据对接出现异常，可能老系统中项目ID为：" + oldProjectId + "，项目合同号为：" + projectContractNum + "的数据存在问题。");
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * 将1100-1399的部分数据提交到待归档
     */
    @Transactional
    public void handle(Map<String, OldProjectInfoEntity> projectMap, String taskKey) throws Exception {
        int oldProjectId = -1;
        String projectContractNum = "";
        try {
            if (!projectMap.isEmpty()) {
                for (String num : projectMap.keySet()) {
                    OldProjectInfoEntity projectInfo = projectMap.get(num);
                    oldProjectId = projectInfo.getId();
                    projectContractNum = projectInfo.getProjectContractNum();
                    TbProjectEntityExample projectEntityExample = new TbProjectEntityExample();
                    projectEntityExample.createCriteria().andProjectNumEqualTo(num);
                    List<TbProjectEntity> projectEntityList = projectEntityMapper.selectByExample(projectEntityExample);
                    for (TbProjectEntity projectEntity : projectEntityList) {
                        TbContractEntityExample contractEntityExample = new TbContractEntityExample();
                        contractEntityExample.createCriteria().andProjectIdEqualTo(projectEntity.getProjectId());
                        List<TbContractEntity> contractList = contractEntityMapper.selectByExample(contractEntityExample);
                        for (TbContractEntity contract : contractList) {
                            if (contract.getDelegateTaskKey().equals(taskKey)) {
                                String contractId = contract.getContractId();
                                //allotWriter(projectInfo, contractId, num);//分配合同编撰人
                                submitVerify(contractId, projectInfo, num);//提审
//                                approve(contractId, WriteContractEnum.QualityVerify.getNodeKey(), "0091dbf2-bfe4-11e9-b448-64006a07184f", "孟冉");//部门经理审批
                                approve(contractId, WriteContractEnum.QualityVerify.getNodeKey(), "0091dbf2-bfe4-11e9-b448-64006a07237f", "李超群");//部门经理审批
                                approve(contractId, WriteContractEnum.GenerManagerVerify.getNodeKey(), "0091dbf2-bfe4-11e9-b448-64006a07218f", "韩爽月");//质检部组员审批
                                approve(contractId, WriteContractEnum.Signer.getNodeKey(), "0091dbf2-bfe4-11e9-b448-64006a07182f", "张菁");//质检部副总审批
                                approve(contractId, WriteContractEnum.Seal.getNodeKey(), "0091dbf2-bfe4-11e9-b448-64006a07180f", "肖继伟");//签发人审批
                                seal(contractId, "0091dbf2-bfe4-11e9-b448-64006a07231f", "张嘉琪");//盖章
                                //saveHistory(contractId, importDate, deptId, deptName, Integer.toString(projectInfo.getId()));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("数据对接出现异常，可能老系统中项目ID为：" + oldProjectId + "，项目合同号为：" + projectContractNum + "的数据存在问题。");
            e.printStackTrace();
            throw new Exception();
        }
    }

    private String createProject(OldProjectInfoEntity projectInfo, HTProjectVO projectVO, String projectContractNum) throws Exception {
        if (projectContractNum.contains("HT")) {
            projectContractNum = projectContractNum.substring(0, projectContractNum.indexOf("HT") + 2) + "0" + projectContractNum.substring(projectContractNum.indexOf("HT") + 2);
        }

        String contractId;
        try {
            String projectNum;
            String contractNum;//
            if (projectContractNum.contains("—")) {
                projectNum = projectContractNum.split("—")[0];//项目编号
                contractNum = projectContractNum.split("—")[1];//合同编号
            } else if (projectContractNum.contains("-")) {
                projectNum = projectContractNum.split("-")[0];//项目编号
                contractNum = projectContractNum.split("-")[1];//合同编号
            } else {
                projectNum = projectContractNum;//项目编号
                contractNum = "";
            }
            String jobId = projectInfo.getJobId();
            String deptId = projectInfo.getNewDeptId();
            projectVO.setProjectNum(projectNum);
            projectVO.setContractNum(contractNum);
            projectVO.setJobId(jobId);
            projectVO.setDepartmentId(deptId);
            contractId = projectService.excSaveProjectInfo(projectVO);
            logger.info("《" + projectContractNum + "》" + "项目创建完成，合同创建完成。当前合同ID为：" + contractId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        return contractId;
    }

    private void allotWriter(OldProjectInfoEntity projectInfo, String contractId, String projectContractNum) throws Exception {
        HTProjectVO param = new HTProjectVO();
        param.setUserId(projectInfo.getApplyUserId());
        param.setContractId(contractId);
        HTUserVO loginUser = new HTUserVO();
        loginUser.setLoginUserId(projectInfo.getFuManagerUserId());
        loginUser.setUserName(projectInfo.getFuManagerUserName());
        param.setLoginUserInfo(loginUser);
        projectService.excUpdateContractUserInfo(param);
        logger.info("《" + projectContractNum + "》" + "合同分配编撰人完成。当前合同ID为：" + contractId);
    }

    private void submitVerify(String contractId, OldProjectInfoEntity projectInfo, String projectContractNum) throws Exception {
        HTContractVO contractVO = new HTContractVO();
        contractVO.setContractId(contractId);
        contractVO.setProjectName(projectInfo.getName());//项目名称
        contractVO.setProjectAddress(projectInfo.getPlace());//项目地点
        contractVO.setInvestedAmount(projectInfo.getInvestSum());//投资金额
        String projectNature = StringUtils.isBlank(projectInfo.getProjectNature()) ? null : projectInfo.getProjectNature();
        contractVO.setProjectNatureList(Collections.singletonList(projectNature));//项目性质
        contractVO.setTrusterName(projectInfo.getClient());//委托人
        contractVO.setTrusterDutyUser(projectInfo.getClientManager());//委托方负责人
        contractVO.setTrusterDutyPhone(projectInfo.getClientManagerPhone());//委托方负责人电话
        contractVO.setConsultUser(projectInfo.getConsultant());//咨询人
        contractVO.setThirdParty(projectInfo.getThrid());//第三方
        contractVO.setDutyPeople(projectInfo.getContractManager());//拟定合同负责人
        contractVO.setPlanner(projectInfo.getManager());//拟定人
        String confirmMethod = StringUtils.isBlank(projectInfo.getConfrimMethod()) ? null : projectInfo.getConfrimMethod();
        contractVO.setDeterminateMethodList(Collections.singletonList(confirmMethod));//确定方式
        contractVO.setContractAmount(projectInfo.getContractSum());//拟订合同金额
        if (projectInfo.getContractTime() == null) {
            contractVO.setContractDate(null);//咨询合同签订日期
        } else {
            contractVO.setContractDate(convertDate(projectInfo.getContractTime()));//咨询合同签订日期
        }
        contractVO.setOtherThings(projectInfo.getRemark());//其他情况
        contractVO.setSubmitNode(WriteContractEnum.ManagerVerify.getNodeKey());
        HTUserVO loginUser = new HTUserVO();//用户信息
        loginUser.setLoginUserId(projectInfo.getApplyUserId());
        loginUser.setUserName(projectInfo.getApplyUserName());
        contractVO.setLoginUserInfo(loginUser);
        String projectNum = projectContractNum.split("-")[0];
        contractVO.setProjectNum(projectNum);//项目编号
        contractVO.setContractType("1");
        contractVO.setFormworkType("1");
        contractVO.setFormworkContent("{\"provinceName\":\"\",\"districtName\":\"\",\"scale\":\"\",\"biddingScale\":\"\",\"agentWorkContent\":\"\",\"signAddress\":\"\",\"agentRange\":\"\",\"agentContent\":\"\",\"paymentMethod\":\"\"}");//
        contractService.updateContractInfo(contractVO, null);
        logger.info("《" + projectContractNum + "》" + "合同提审完成。当前合同ID为：" + contractId);
    }

    private void approve(String contractId, String submitNode, String userId, String userName) {
        HTContractVO contractVO = new HTContractVO();
        contractVO.setContractId(contractId);
        contractVO.setIsPass(Constant.APPROVED);
        contractVO.setSubmitNode(submitNode);
        HTUserVO userVO = new HTUserVO();
        userVO.setLoginUserId(userId);
        userVO.setUserName(userName);
        contractVO.setLoginUserInfo(userVO);
        contractService.excAuditingHTContract(contractVO);
    }

    private void seal(String contractId, String userId, String userName) {
        HTContractVO contractVO = new HTContractVO();
        contractVO.setContractId(contractId);
        HTUserVO userVO = new HTUserVO();
        userVO.setLoginUserId(userId);
        userVO.setUserName(userName);
        contractVO.setLoginUserInfo(userVO);
        contractService.excContractConfirmSeal(contractVO);
    }

    private void saveHistory(String contractId, Date importDate, String deptId, String deptName, String oldProjectId) throws Exception {
        OldDataJointRecord dataJointRecord = new OldDataJointRecord();
        dataJointRecord.setCreateDate(importDate);
        dataJointRecord.setContractId(contractId);
        dataJointRecord.setOldDeptId(deptId);
        dataJointRecord.setDeptName(deptName);
        dataJointRecord.setOldProjectId(oldProjectId);
        recordMapper.insert(dataJointRecord);
    }

    private static String convertDate(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = null;
        if (seconds > 0) {
            seconds *= 1000;
            Date date = new Date(seconds);
            result = sdf.format(date);
        }
        return result;
    }

}
