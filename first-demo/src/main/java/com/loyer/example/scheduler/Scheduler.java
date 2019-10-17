package com.loyer.example.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kuangq
 * @projectName example
 * @title Timer
 * @description 定时任务
 * @date 2019-08-01 17:23
 */
@Component
public class Scheduler {

    private static Logger logger = LoggerFactory.getLogger(Scheduler.class);

    /**
     * @param
     * @return void
     * @author kuangq
     * @description 计时器：cron表达式生成地址：http://cron.qqe2.com/
     * @date 2019-10-16 15:23
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void timer() {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS").format(new Date());
        logger.info("【当前时间】{}", currentTime);
    }
}
