package com.hd.manager.service.impl;

import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.base.vo.BaseVO;
import com.hd.common.dao.TbTaskReeditEntityMapper;
import com.hd.common.entity.TbTaskReeditEntity;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.service.HTTaskProcessService;
import com.hd.manager.vo.HTTaskReeditVO;
import com.hd.manager.vo.HTUserVO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class HTTaskReeditProcessServiceImpl extends BaseServiceImpl implements HTTaskProcessService {

    //枚举
    private static final ProcessDictEnum TASK_REEDIT_ENUM = ProcessDictEnum.Task_Reedit;

    //流程Key
    private static final String TASK_REEDIT_PROCESS_KEY = TASK_REEDIT_ENUM.getProcessKey();

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private TbTaskReeditEntityMapper taskReeditDao;

    /**
     * 办理任务
     *
     * @param variables 变量
     * @return 历史ID
     * @throws SystemException 系统异常
     */
    @Override
    public String excHandleTask(String userId, Map<String, Object> variables) throws LogicException, SystemException {
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, TASK_REEDIT_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, taskReeditVO.getDelegateTaskId(), variables);
        logger.info("任务_重新编辑申请审批流程任务办理完成。任务ID为：" + taskReeditVO.getDelegateTaskId());
        return ((CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO)).getHistoryRecordId();
    }

    /**
     * 改派
     */
    @Override
    public void excReassignment(BaseVO baseVO) throws SystemException {

    }

    /**
     * 查询页面类型
     */
    @Override
    public String excQueryPageType(HTUserVO userVO, String bizDeptId, String processDictId, BaseVO vo, List<String> executorUsers) {
        return commonActivitiService.excQueryPageType(userVO, bizDeptId, processDictId, vo, executorUsers);
    }

    /**
     * 启动工作流
     *
     * @param variables 流程变量
     * @return 流程实例对象
     * @throws SystemException 系统异常
     */
    @Override
    public ProcessInstance excStart(Map<String, Object> variables) throws SystemException {
        HTTaskReeditVO taskReeditVO = (HTTaskReeditVO) variables.get(WorkFlowConstant.KEY_TASK_REEDIT_VO);
        String bizKey = taskReeditVO.getReeditId();//业务主键
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TASK_REEDIT_PROCESS_KEY, bizKey, variables);//启动流程
        //转换流程ID
        String processInstanceId = processInstance.getId();
        taskReeditVO.setProcessInstId(processInstanceId);
        TbTaskReeditEntity taskReeditEntity = (TbTaskReeditEntity) ObjectReflectUtils.convertObj(taskReeditVO, TbTaskReeditEntity.class);
        taskReeditDao.updateByPrimaryKeySelective(taskReeditEntity);
        logger.info("任务_重新编辑申请审批流程已经启动。流程实例ID为：" + processInstance.getId());
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
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, TASK_REEDIT_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, taskId, variables);
        logger.info("任务_重新编辑申请审批流程任务办理完成。任务ID为：" + taskId);
    }

    /**
     * 撤回任务
     *
     * @param vo VO
     * @throws SystemException 系统异常
     */
    @Override
    public void excRecallTask(Object vo) throws SystemException {

    }

}
