package com.hd.manager.service.impl;

import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.base.vo.BaseVO;
import com.hd.common.dao.TbRequestMenuEntityMapper;
import com.hd.common.entity.TbRequestMenuEntity;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.Constant;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.service.HTCashProcessService;
import com.hd.manager.vo.HTRequestMenuVO;
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
public class HTCashApplyProcessServiceImpl extends BaseServiceImpl implements HTCashProcessService {

    //枚举
    private static final ProcessDictEnum CASH_APPLY_ENUM = ProcessDictEnum.CashApply;

    //流程Key
    private static final String CASH_APPLY_PROCESS_KEY = CASH_APPLY_ENUM.getProcessKey();

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private HistoryService historyService;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private TbRequestMenuEntityMapper requestMenuDao;

    /**
     * 启动工作流
     *
     * @param variables 流程变量
     * @return 流程实例对象
     * @throws SystemException 系统异常
     */
    @Override
    public ProcessInstance excStart(Map<String, Object> variables) throws SystemException {
        HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
        String bizKey = requestMenuVO.getRequestMenuId();//业务主键
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(CASH_APPLY_PROCESS_KEY, bizKey, variables);//启动流程
        //转换流程ID
        String processInstanceId = processInstance.getId();
        requestMenuVO.setProcessInstId(processInstanceId);
        TbRequestMenuEntity taskEntity = (TbRequestMenuEntity) ObjectReflectUtils.convertObj(requestMenuVO, TbRequestMenuEntity.class);
        requestMenuDao.updateByPrimaryKeySelective(taskEntity);
        logger.info("任务_请款流程已经启动。流程实例ID为：" + processInstance.getId());
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
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, CASH_APPLY_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, taskId, variables);
        logger.info("请款流程任务办理完成。任务ID为：" + taskId);
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
        HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) variables.get(WorkFlowConstant.KEY_CASH_APPLY_VO);
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, CASH_APPLY_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, requestMenuVO.getDelegateTaskId(), variables);
        logger.info("请款流程任务办理完成。任务ID为：" + requestMenuVO.getDelegateTaskId());
        return ((CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO)).getHistoryRecordId();
    }

    /**
     * 查询页面类型
     *
     * @param userVO        用户VO
     * @param bizDeptId     业务部门ID
     * @param processDictId 流程字典ID
     * @param vo            业务VO
     * @param executorUsers 执行人
     * @return 类型
     */
    @Override
    public String excQueryPageType(HTUserVO userVO, String bizDeptId, String processDictId, BaseVO vo, List<String> executorUsers) {
        return commonActivitiService.excQueryPageType(userVO, bizDeptId, processDictId, vo, executorUsers);
    }

    /**
     * 撤回任务
     *
     * @param vo VO
     * @throws SystemException 系统异常
     */
    @Override
    public void excRecallTask(Object vo) throws SystemException {
        if (vo instanceof HTRequestMenuVO) {
            HTRequestMenuVO requestMenuVO = (HTRequestMenuVO) vo;
            String taskId = requestMenuVO.getDelegateTaskId();
            Map<String, Object> variables = new HashMap<>();
            variables.put(WorkFlowConstant.KEY_CASH_APPLY_VO, requestMenuVO);
            variables.put(WorkFlowConstant.KEY_APPROVAL_STATUS, Constant.NOT_APPROVED);
            variables.put(WorkFlowConstant.KEY_SUBMIT_NODE, requestMenuVO.getDelegateTaskKey());
            CommonActivitiListenVO commonActivitiListenVO = new CommonActivitiListenVO();
            commonActivitiListenVO.setDepartmentId(requestMenuVO.getDepartmentId());
            commonActivitiListenVO.setIsRecall(Constant.IS_CAN_RECALL_ENABLE);
            variables.put(WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListenVO);
            variables.put("cashApplyRecall", "1");
            variables.put(WorkFlowConstant.KEY_PRE_HANDLER, requestMenuVO.getLoginUserInfo());
            variables.put(WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            this.excHandleTask(((HTRequestMenuVO) vo).getLoginUserInfo().getLoginUserId(), variables);
            //删除历史
            historyService.deleteHistoricTaskInstance(taskId);
        } else {
            logger.error("类型异常");
            throw new SystemException();
        }
    }
}
