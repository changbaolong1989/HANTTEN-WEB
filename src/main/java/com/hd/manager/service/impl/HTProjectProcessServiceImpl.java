package com.hd.manager.service.impl;

import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.base.vo.BaseVO;
import com.hd.common.dao.TbContractEntityMapper;
import com.hd.common.dao.TbProjectProcessRelationEntityMapper;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.entity.TbContractEntityExample;
import com.hd.common.entity.TbProjectProcessRelationEntity;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.service.HTProjectProcessService;
import com.hd.manager.vo.HTContractVO;
import com.hd.manager.vo.HTProjectVO;
import com.hd.manager.vo.HTUserVO;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class HTProjectProcessServiceImpl extends BaseServiceImpl implements HTProjectProcessService {

    //创建项目枚举
    private static final ProcessDictEnum CREATE_PROJECT_ENUM = ProcessDictEnum.CreateProjectProcess;

    //创建项目流程Key
    private static final String CREATE_PROJECT_PROCESS_KEY = CREATE_PROJECT_ENUM.getProcessKey();

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private TbContractEntityMapper contractDao;

    @Resource
    private TbProjectProcessRelationEntityMapper projectProcessDao;


    /**
     * 启动工作流
     *
     * @param variables 流程变量
     * @return 流程实例对象
     * @throws SystemException 系统异常
     */
    @Override
    public ProcessInstance excStart(Map<String, Object> variables) throws LogicException, SystemException {
        HTProjectVO projectVO = (HTProjectVO) variables.get("projectVO");//获取变量
        HTContractVO contractVO = (HTContractVO) variables.get(WorkFlowConstant.KEY_CONTRACT_VO);
        String bizKey = projectVO.getProjectId();//业务主键
        variables.put(WorkFlowConstant.KEY_JOB_ID, contractVO.getJobId());
        //1.启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(CREATE_PROJECT_PROCESS_KEY, bizKey);//启动创建项目流程
        //2.创建项目
        String processInstanceId = processInstance.getId();
        contractVO.setProcessInstId(processInstanceId);//设置合同的流程实例ID，当刚创建项目时，这个值为项目的流程ID
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();//创建项目
        String taskId = task.getId();
        commonActivitiService.excCompleteTask(taskId, variables);
        //更新项目业务数据
        TbProjectProcessRelationEntity projectProcessRelation = new TbProjectProcessRelationEntity();
        projectProcessRelation.setProcessInstId(processInstanceId);
        projectProcessRelation.setProjectId(projectVO.getProjectId());
        projectProcessRelation.setContractId(contractVO.getContractId());
        projectProcessDao.insert(projectProcessRelation);
        logger.info("创建项目流程已经启动。流程实例ID为：" + processInstanceId + "。任务ID为：" + taskId);
        return processInstance;
    }

    /**
     * 办理任务
     *
     * @param taskId    任务ID
     * @param variables 流程变量
     * @throws SystemException 系统异常
     */
    @Override
    public void excHandleTask(String userId, String taskId, Map<String, Object> variables) throws LogicException, SystemException {
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, CREATE_PROJECT_ENUM.getProcessDictId());
        variables.put(WorkFlowConstant.KEY_LISTENER_VO, new CommonActivitiListenVO());
        commonActivitiService.excCompleteTask(userId, taskId, variables);
        logger.info("分配合同编撰人完成。任务ID为：" + taskId);
    }

    /**
     * 办理任务
     *
     * @param variables 变量
     * @return 历史ID
     * @throws SystemException 系统异常
     */
    @Override
    public String excHandleTask(String userId, Map<String, Object> variables) throws SystemException {
        return null;
    }

    /**
     * 查询页面类型
     *
     * @param userVO        用户VO
     * @param bizDeptId     业务线部门ID
     * @param processDictId 流程字典ID
     * @param vo            业务VO
     * @param executorUsers 执行人
     * @return 类型
     */
    @Override
    public String excQueryPageType(HTUserVO userVO, String bizDeptId, String processDictId, BaseVO vo, List<String> executorUsers) {
        return null;
    }

    /**
     * 关闭项目
     *
     * @param projectId 项目ID
     * @throws SystemException 系统异常
     */
    @Override
    public void closeProject(String projectId) throws SystemException {
        //关闭合同
        TbContractEntityExample contractExample = new TbContractEntityExample();
        contractExample.createCriteria().andProjectIdEqualTo(projectId);
        List<TbContractEntity> contractList = contractDao.selectByExample(contractExample);
        try {
            for (TbContractEntity contract : contractList) {
                if (!Constant.APPROVED.equals(contract.getVerifyState())) {
                    runtimeService.suspendProcessInstanceById(contract.getProcessInstId());
                }
            }
        } catch (ActivitiException e) {
        }
    }
}
