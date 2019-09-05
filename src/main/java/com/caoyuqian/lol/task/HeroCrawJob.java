package com.caoyuqian.lol.task;

import com.caoyuqian.lol.craw.HeroCraw;
import com.caoyuqian.lol.service.HeroService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeroCrawJob extends QuartzJobBean {

    private final static Logger log = LoggerFactory.getLogger(HeroCrawJob.class);
    @Autowired
    private HeroCraw heroCraw;

    @Autowired
    private HeroService heroService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("开始爬取英雄数据");
        try {
            heroService.saveAllHero(heroCraw.craw()).subscribe();
            //获取JobDetail中关联的数据
            String msg = (String) context.getJobDetail().getJobDataMap().get("msg");
            log.info("执行任务: StatisticsTierCrawJob current time :{}----{}",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date()), msg);
        } catch (IOException e) {
            log.info("出现异常：{}", e.toString());

        }


    }
}
