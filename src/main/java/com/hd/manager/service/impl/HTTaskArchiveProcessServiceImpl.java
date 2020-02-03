package com.hd.manager.service.impl;

import com.hd.base.enums.ProcessDictEnum;
import com.hd.base.exception.LogicException;
import com.hd.base.exception.SystemException;
import com.hd.base.service.impl.BaseServiceImpl;
import com.hd.base.vo.BaseVO;
import com.hd.common.dao.TbTaskArchiveEntityMapper;
import com.hd.common.dao.TbUserEntityMapper;
import com.hd.common.entity.TbTaskArchiveEntity;
import com.hd.common.entity.TbTaskArchiveEntityExample;
import com.hd.common.entity.TbUserEntity;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.ObjectReflectUtils;
import com.hd.common.util.WorkFlowConstant;
import com.hd.common.vo.CommonActivitiListenVO;
import com.hd.manager.service.HTTaskArchiveProcessService;
import com.hd.manager.vo.HTTaskArchiveVO;
import com.hd.manager.vo.HTUserVO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HTTaskArchiveProcessServiceImpl extends BaseServiceImpl implements HTTaskArchiveProcessService {

    //枚举
    private static final ProcessDictEnum ARCHIVE_ENUM = ProcessDictEnum.Archive;

    //流程Key
    private static final String ARCHIVE_PROCESS_KEY = ARCHIVE_ENUM.getProcessKey();

    @Resource
    private CommonActivitiService commonActivitiService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private TbTaskArchiveEntityMapper archiveDao;

    @Resource
    private TbUserEntityMapper userDao;

    /**
     * 启动工作流
     *
     * @param variables 流程变量
     * @return 流程实例对象
     * @throws SystemException 系统异常
     */
    @Override
    public ProcessInstance excStart(Map<String, Object> variables) throws SystemException {
        HTTaskArchiveVO taskArchiveVO = (HTTaskArchiveVO) variables.get(WorkFlowConstant.KEY_ARCHIVE_VO);
        String bizKey = taskArchiveVO.getTaskArchiveId();//业务主键
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ARCHIVE_PROCESS_KEY, bizKey, variables);//启动流程
        //转换流程ID
        String processInstanceId = processInstance.getId();
        taskArchiveVO.setProcessInstId(processInstanceId);
        TbTaskArchiveEntity taskEntity = (TbTaskArchiveEntity) ObjectReflectUtils.convertObj(taskArchiveVO, TbTaskArchiveEntity.class);
        archiveDao.updateByPrimaryKeySelective(taskEntity);
        logger.info("项目监理部任务归档流程已经启动。流程实例ID为：" + processInstance.getId());
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
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, ARCHIVE_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, taskId, variables);
        logger.info("任务_项目监理部归档流程任务办理完成。任务ID为：" + taskId);
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
        HTTaskArchiveVO archiveVO = (HTTaskArchiveVO) variables.get(WorkFlowConstant.KEY_ARCHIVE_VO);
        variables.put(WorkFlowConstant.KEY_PROCESS_DICT_TYPE, ARCHIVE_ENUM.getProcessDictId());
        commonActivitiService.excCompleteTask(userId, archiveVO.getDelegateTaskId(), variables);
        logger.info("任务_项目监理部归档流程任务办理完成。任务ID为：" + archiveVO.getDelegateTaskId());
        return ((CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO)).getHistoryRecordId();
    }

    /**
     * 查询页面类型
     *
     * @param userVO        用户VO
     * @param bizDeptId     业务线
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
     * 删除归档信息
     *
     * @param taskId 任务ID
     */
    @Override
    public void delArchive(String taskId) {
        TbTaskArchiveEntityExample example = new TbTaskArchiveEntityExample();
        example.createCriteria().andTaskIdEqualTo(taskId);
        List<TbTaskArchiveEntity> archives = archiveDao.selectByExample(example);
        for (TbTaskArchiveEntity archive : archives) {
            String processInstId = archive.getProcessInstId();
            commonActivitiService.excDeleteProcess(processInstId);
        }
    }

    /**
     * 改派
     *
     * @throws SystemException 系统异常
     */
    @Override
    public void excReassignment(BaseVO baseVO) throws SystemException {
        if (baseVO instanceof HTTaskArchiveVO) {
            HTTaskArchiveVO taskArchiveVO = (HTTaskArchiveVO) baseVO;
            String taskArchiveId = taskArchiveVO.getTaskArchiveId();
            TbTaskArchiveEntity taskArchiveEntity = archiveDao.selectByPrimaryKey(taskArchiveId);
            String delegateTaskId = taskArchiveEntity.getDelegateTaskId();
            String oldUser = taskArchiveEntity.getApplyUserId();
            String newUser = taskArchiveVO.getApplyUserId();
            if (oldUser.equals(newUser)) return;
            if (StringUtils.isBlank(newUser)) throw new SystemException();
            taskService.deleteCandidateUser(delegateTaskId, oldUser);
            taskService.addCandidateUser(delegateTaskId, newUser);
            Map<String, Object> variables = taskService.getVariables(delegateTaskId);
            CommonActivitiListenVO commonActivitiListen = (CommonActivitiListenVO) variables.get(WorkFlowConstant.KEY_LISTENER_VO);
            commonActivitiListen.setDepartmentId(taskArchiveVO.getDepartmentId());
            taskService.setVariable(delegateTaskId, WorkFlowConstant.KEY_LISTENER_VO, commonActivitiListen);
            TbUserEntity user = userDao.selectByPrimaryKey(newUser);
            taskArchiveEntity.setApplyUserId(user.getUserId());
            taskArchiveEntity.setApplyUserName(user.getUserName());
            TbTaskArchiveEntityExample example = new TbTaskArchiveEntityExample();
            example.createCriteria().andTaskArchiveIdEqualTo(taskArchiveId);
            archiveDao.updateByExampleSelective(taskArchiveEntity, example);
            taskService.setVariable(delegateTaskId, WorkFlowConstant.KEY_MAIN_LEADER_USER, newUser);
            taskService.setVariable(delegateTaskId, WorkFlowConstant.KEY_DELEGATE_USER, newUser);
            taskService.setVariable(delegateTaskId, WorkFlowConstant.KEY_PRE_HANDLER, taskArchiveVO.getLoginUserInfo());
            taskService.setVariable(delegateTaskId, WorkFlowConstant.KEY_HANDLE_TIME, new Date());
            logger.info("任务改派完成。从用户:[ " + oldUser + " ] 改派到用户：[ " + newUser + " ] ");
        }
    }
}
