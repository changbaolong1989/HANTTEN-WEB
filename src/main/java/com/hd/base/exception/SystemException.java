package com.hd.base.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hd.common.util.SpringUtils;
/**
 * 系统异常处理类
 * @author JLF
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = -8673806156373941988L;
    /** log对象 */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /** 异常对应编码 */
    private String msgCd;
    /** 异常消息 */
    private String msgDes;
    /** 构造函数 */
    public SystemException() {
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
     * 系统异常处理方法
     * @param ex
     *         异常对象
     * @param msgCd
     *         异常编码
     * @param msgParam
     *         异常参数
     * */
    public SystemException(Exception ex, String msgCd, Object... msgParam) {
        super(SpringUtils.getMessage(msgCd, msgParam));
        this.msgCd = msgCd;
        // 根据消息编码取得消息
        this.msgDes = SpringUtils.getMessage(this.msgCd, msgParam);
        // 画面显示的错误消息log
        logger.error(this.msgDes);
        // 错误原因log
        logger.error(ex.getMessage());
        // 创建临时字符串
        StringBuilder tempStringBuilder = null;
        // 取得堆栈信息
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
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
