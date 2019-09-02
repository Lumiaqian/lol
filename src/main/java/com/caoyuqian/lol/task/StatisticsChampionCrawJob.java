package com.caoyuqian.lol.task;


import com.caoyuqian.lol.craw.StatisticsChampionCraw;
import com.caoyuqian.lol.model.StatisticsChampion;
import com.caoyuqian.lol.service.StatisticsChampionService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: StatisticsChampionCrawJob
 * @Package: com.caoyuqian.lol.task
 * @Description: 英雄数据排行爬取
 * @date 2019-08-30 10:43
 **/
@Slf4j
public class StatisticsChampionCrawJob extends QuartzJobBean {

    @Autowired
    private StatisticsChampionCraw statisticsChampionCraw;
    @Autowired
    private StatisticsChampionService statisticsChampionService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            long start = System.currentTimeMillis();
            log.info("执行任务: StatisticsChampionCrawJob");
            statisticsChampionService.saveAll(statisticsChampionCraw.get()).subscribe();
            long end = System.currentTimeMillis();
            log.info("StatisticsChampionCrawJob任务耗时：{}秒",(end-start)/1000);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


}
