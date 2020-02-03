package com.hd.manager.service.impl;

import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.base.vo.BaseVO;
import com.hd.common.dao.TbTaskPeriodRelationEntityMapper;
import com.hd.common.entity.TbTaskPeriodRelationEntity;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.Constant;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.service.HTTaskProcessService;
import com.hd.manager.vo.HTTaskPeriodVO;
import com.hd.manager.vo.HTUserVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HTTaskA3ProcessServiceImpl extends BaseServiceImpl implements HTTaskProcessService {

    //枚举
    private static final ProcessDictEnum TASK_A3_ENUM = ProcessDictEnum.Task_A3;

    //流程Key
    private static final String TASK_A3_PROCESS_KEY = TASK_A3_ENUM.getProcessKey();

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private TbTaskPeriodRelationEntityMapper taskPeriodDao;

    @Resource
    private HistoryService historyService;

    /**
     * 启动工作流
     *
     * @param variables 流程变量
     * @return 流程实例对象
     * @throws SystemException 系统异常
     */
    @Override
    public ProcessInstance excStart(Map<String, Object> variables) throws SystemException {
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        String bizKey = taskPeriodVO.getRelationId();//业务主键
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TASK_A3_PROCESS_KEY, bizKey, variables);//启动流程
        //转换流程ID
        String processInstanceId = processInstance.getId();
        taskPeriodVO.setProcessInstId(processInstanceId);
        TbTaskPeriodRelationEntity taskPeriodEntity = (TbTaskPeriodRelationEntity) ObjectReflectUtils.convertObj(taskPeriodVO, TbTaskPeriodRelationEntity.class);
        taskPeriodDao.updateByPrimaryKeySelective(taskPeriodEntity);
        logger.info("任务_A3审批流程已经启动。流程实例ID为：" + processInstance.getId());
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
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, TASK_A3_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, taskId, variables);
        logger.info("任务_A3审批流程任务办理完成。任务ID为：" + taskId);
    }

    /**
     * 撤回任务
     *
     * @param vo VO
     * @throws SystemException 系统异常
     */
    @Override
    public void excRecallTask(Object vo) throws SystemException {
        if (vo instanceof HTTaskPeriodVO) {
            HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) vo;
            String taskId = taskPeriodVO.getDelegateTaskId();
            Map<String, Object> variables = new HashMap<>();
            variables.put(WorkFlowConstant.KEY_TASK_PERIOD_VO, taskPeriodVO);
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, Constant.NOT_APPROVED);
            variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, taskPeriodVO.getDelegateTaskKey());
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            commonActivitiListenVO.setDepartmentId(taskPeriodVO.getDepartmentId());
            commonActivitiListenVO.setIsRecall(Constant.IS_CAN_RECALL_ENABLE);
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, taskPeriodVO.getLoginUserInfo());
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            this.excHandleTask(((HTTaskPeriodVO) vo).getLoginUserInfo().getLoginUserId(), variables);
            //删除历史
            historyService.deleteHistoricTaskInstance(taskId);
        } else {
            logger.error("类型异常");
            throw new SystemException();
        }
    }

    /**
     * 办理任务
     *
     * @param variables 变量
     * @return 历史ID
     * @throws SystemException 系统异常
     */
    @Override
    public String excHandleTask(String userId, Map<String, Object> variables) throws LogicException, SystemException {
        HTTaskPeriodVO taskPeriodVO = (HTTaskPeriodVO) variables.get(WorkFlowConstant.KEY_TASK_PERIOD_VO);
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, TASK_A3_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, taskPeriodVO.getDelegateTaskId(), variables);
        logger.info("任务_A3审批流程任务办理完成。任务ID为：" + taskPeriodVO.getDelegateTaskId());
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
}
