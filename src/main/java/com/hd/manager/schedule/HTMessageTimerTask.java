package com.hd.manager.schedule;

import com.hd.base.exception.SystemException;
import com.hd.common.util.NumberToolUtils;
import com.hd.manager.service.HTMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;

/**
 * 消息中心定时任务
 *
 * @author jwl
 * Created in 2019/9/10 16:12
 */
@Controller
// 开启定时任务
@EnableScheduling
public class HTMessageTimerTask {

    private static final Logger logger = LoggerFactory.getLogger(HTMessageTimerTask.class);

    /**
     * 消息数据 业务层资源
     */
    @Resource
    private HTMessageService htMessageService;

    // 添加定时任务
    // 凌晨5分 TODO
    @Scheduled(cron = "0 5 0 * * ?")
    // 间隔五秒执行
    // @Scheduled(fixedRate=5000)
    private void configureTasks() {

        // 开始提示语
        logger.info("自动插入请款到期消息开始.......");

        // 设置变量自动插入请款到期消息的条数
        int tmpInsertCount = NumberToolUtils.INT_SCALE_INTEGER;

        try {

            // 插入请款到期消息
            tmpInsertCount = htMessageService.excInsertMessageInfo();

        }catch (SystemException sex){
            // 打印失败响应信息
            logger.info(sex.getMsgDes());
        }

        // 打印插入请款到期消息的条数
        logger.info("自动插入请款到期消息的条数：" + tmpInsertCount);

    }

}
