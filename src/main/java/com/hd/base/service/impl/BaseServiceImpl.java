package com.hd.base.service.impl;

import java.util.Date;



import com.hd.base.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service基类
 * @author JLF
 */
public class BaseServiceImpl implements BaseService {

    /** log对象 */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 取得当前时间
     *
     * @return
     */
    @Override
    public Date getCurrentDate() {
        return new Date();
    }
}
