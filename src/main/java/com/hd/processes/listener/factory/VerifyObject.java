package com.hd.processes.listener.factory;

import com.hd.base.vo.BaseVO;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;

import java.io.Serializable;

public class VerifyObject implements Serializable {

    private static final long serialVersionUID = 906874263987336358L;

    private BaseVO baseVO;

    private String departmentId;

    private String submitNode;

    private String recallSubmitNode;

    private String currentState;

    private DelegateTask delegateTask;

    private DelegateExecution delegateExecution;

    private String verifyState;

    private Class<?> entityClass;

    private Class<?> daoClass;

    private Class<?> reflectMethodClass;

    public BaseVO getBaseVO() {
        return baseVO;
    }

    public void setBaseVO(BaseVO baseVO) {
        this.baseVO = baseVO;
    }

    public String getSubmitNode() {
        return submitNode;
    }

    public void setSubmitNode(String submitNode) {
        this.submitNode = submitNode;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<?> getDaoClass() {
        return daoClass;
    }

    public void setDaoClass(Class<?> daoClass) {
        this.daoClass = daoClass;
    }

    public String getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState;
    }

    public DelegateTask getDelegateTask() {
        return delegateTask;
    }

    public void setDelegateTask(DelegateTask delegateTask) {
        this.delegateTask = delegateTask;
    }

    public Class<?> getReflectMethodClass() {
        return reflectMethodClass;
    }

    public void setReflectMethodClass(Class<?> reflectMethodClass) {
        this.reflectMethodClass = reflectMethodClass;
    }

    public DelegateExecution getDelegateExecution() {
        return delegateExecution;
    }

    public void setDelegateExecution(DelegateExecution delegateExecution) {
        this.delegateExecution = delegateExecution;
    }

    public String getRecallSubmitNode() {
        return recallSubmitNode;
    }

    public void setRecallSubmitNode(String recallSubmitNode) {
        this.recallSubmitNode = recallSubmitNode;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
