package com.hd.processes.listener.archive;

import com.hd.base.enums.ArchiveEnum;
import com.hd.common.dao.TbTaskArchiveEntityMapper;
import com.hd.common.entity.TbTaskArchiveEntity;
import com.hd.common.util.*;
import com.hd.manager.vo.HTTaskArchiveVO;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ListenerArchiveEnd implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = -436398626530298389L;

    private static final ArchiveEnum EVENT_ENUM = ArchiveEnum.EndEvent;

    @Resource
    private TbTaskArchiveEntityMapper archiveDao;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        //更新业务数据
        HTTaskArchiveVO archiveVO = (HTTaskArchiveVO) delegateExecution.getVariable(WorkFlowConstant.KEY_ARCHIVE_VO);
        archiveVO.setCompleteTime(DateToolUtils.convertDateFormat(new Date(), DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
        archiveVO.setVerifyState(Constant.APPROVED);
        archiveVO.setDelegateTaskKey(EVENT_ENUM.getNodeKey());
        archiveVO.setSubmitNode(null);
        archiveVO.setCurrentState(StringToolUtils.convertNullObjectToString(EVENT_ENUM.getNodeSort()));
        TbTaskArchiveEntity archiveEntity = (TbTaskArchiveEntity) ObjectReflectUtils.convertObj(archiveVO, TbTaskArchiveEntity.class);
        archiveDao.updateByPrimaryKeySelective(archiveEntity);
    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }

}
