package com.hd.base.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoDelegationException extends RuntimeException {

    private static final long serialVersionUID = -8673806156373941988L;
    /**
     * log对象
     */
    private static final Logger logger = LoggerFactory.getLogger(NoDelegationException.class);

    /**
     * 构造函数
     */
    public NoDelegationException(String reason) {
        super(reason);
        // 取得堆栈信息
        StackTraceElement[] stackTraceElements = this.getStackTrace();
        if (stackTraceElements != null) {
            logger.error(reason);
        }
    }

}
