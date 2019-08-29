package com.caoyuqian.lol.task;

import com.caoyuqian.lol.craw.StatisticsTierCraw;
import com.caoyuqian.lol.service.StatisticsTierService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qian
 * @version V1.0
 * @Title: StatisticsTierCrawJob
 * @Package: com.caoyuqian.lol.task
 * @Description: TOTO
 * @date 2019-08-28 19:14
 **/
@Slf4j
public class StatisticsTierCrawJob extends QuartzJobBean {
    @Autowired
    private StatisticsTierCraw statisticsTierCraw;
    @Autowired
    private StatisticsTierService statisticsTierService;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            statisticsTierService.saveAll(statisticsTierCraw.get()).subscribe();
            //获取JobDetail中关联的数据
            String msg = (String) context.getJobDetail().getJobDataMap().get("msg");
            log.info("执行任务: StatisticsTierCrawJob current time :{}----{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), msg);
        } catch (IOException e) {
            log.info("出现异常：{}",e.toString());
        }
    }
}
