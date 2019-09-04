package com.caoyuqian.lol.task;

import com.caoyuqian.lol.craw.SummonerSkillCraw;
import com.caoyuqian.lol.entity.SummonerSkill;
import com.caoyuqian.lol.service.SummonerSkillService;
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
import java.util.List;


public class SummonerSkillCrawJob extends QuartzJobBean {

    private final static Logger log = LoggerFactory.getLogger(SummonerSkillCrawJob.class);
    @Autowired
    private SummonerSkillCraw summonerSkillCraw;
    @Autowired
    private SummonerSkillService summonerSkillService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("*****召唤师技能爬取开启*****");
        try {
            List<SummonerSkill> summonerSkills =
                    summonerSkillCraw.craw();
            summonerSkillService.saveAll(summonerSkills).subscribe();
            String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
            log.info("执行任务: StatisticsTierCrawJob current time :{}----{}",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date()), msg);


        } catch (IOException e) {
            log.info("出现异常：{}", e.toString());
        }

        log.info("*****爬取完成*****");
    }
}
