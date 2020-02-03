package com.hd.base.exception;

import com.hd.common.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hd.common.util.SpringUtils;

/**
 * 逻辑异常处理基类
 * @author JLF
 */
public class LogicException extends RuntimeException {

    private static final long serialVersionUID = -1975950600294184332L;
    /** log对象 */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /** 异常对应编码 */
    private String msgCd;
    /** 异常消息 */
    private String msgDes;
    /** 构造函数 */
    public LogicException() {
        super();
        // 创建临时字符串
        StringBuilder tempStringBuilder = null;
        // 取得堆栈信息
        StackTraceElement[] stackTraceElements = this.getStackTrace();
        if (stackTraceElements != null) {
            for (int i = 0; i < stackTraceElements.length; i++) {
                tempStringBuilder = new StringBuilder();
                tempStringBuilder.append("类:");
                tempStringBuilder.append(stackTraceElements[i].getClassName());
                tempStringBuilder.append("||方法名:");
                tempStringBuilder.append(stackTraceElements[i].getMethodName());
                tempStringBuilder.append("||所在行:");
                tempStringBuilder.append(stackTraceElements[i].getLineNumber());
                logger.error(tempStringBuilder.toString());
            }
        }
    }
    /**
     * 逻辑异常处理方法
     * @param msgCd
     *         异常编码
     * @param msgParam
     *         异常参数
     * */
    public LogicException(String msgCd, Object... msgParam) {
        super(SpringUtils.getMessage(msgCd, msgParam));
        // 取得消息编码
        this.msgCd = msgCd;
        // 根据消息编码取得消息
        this.msgDes = SpringUtils.getMessage(msgCd, msgParam);
        // 输出消息log信息
        logger.error(this.msgDes);

        // 创建临时字符串
        StringBuilder tempStringBuilder = null;
        // 显示堆栈信息
        StackTraceElement[] stackTraceElements = this.getStackTrace();
        if (stackTraceElements != null) {
            for (int i = 0; i < Constant.LOGIC_EXCEPTION_STACK_TRACE_COUNT; i++) {
                tempStringBuilder = new StringBuilder();
                tempStringBuilder.append("类:");
                tempStringBuilder.append(stackTraceElements[i].getClassName());
                tempStringBuilder.append("||方法名:");
                tempStringBuilder.append(stackTraceElements[i].getMethodName());
                tempStringBuilder.append("||所在行:");
                tempStringBuilder.append(stackTraceElements[i].getLineNumber());
                logger.error(tempStringBuilder.toString());
            }
        }
    }
    /**
     * 逻辑异常控制台输出
     * @param ex
     *         逻辑异常对象
     * */
    public LogicException(LogicException ex) {
        super(ex.getMessage());
        // 取得消息编码
        this.msgCd = ex.getMsgCd();
        // 根据消息编码取得消息
        this.msgDes = ex.getMsgDes();
        // 创建临时字符串
        StringBuilder tempStringBuilder = null;
        // 显示堆栈信息
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        if (stackTraceElements != null) {
            for (int i = 0; i < Constant.LOGIC_EXCEPTION_STACK_TRACE_COUNT; i++) {
                tempStringBuilder = new StringBuilder();
                tempStringBuilder.append("类:");
                tempStringBuilder.append(stackTraceElements[i].getClassName());
                tempStringBuilder.append("||方法名:");
                tempStringBuilder.append(stackTraceElements[i].getMethodName());
                tempStringBuilder.append("||所在行:");
                tempStringBuilder.append(stackTraceElements[i].getLineNumber());
                logger.error(tempStringBuilder.toString());
            }
        }
    }
    /**
     * msgCd
     * @return msgCd 异常编码
     */
    public String getMsgCd() {
        return msgCd;
    }
    /**
     * msgDes
     * @return msgDes 异常消息
     */
    public String getMsgDes() {
        return msgDes;
    }
}
