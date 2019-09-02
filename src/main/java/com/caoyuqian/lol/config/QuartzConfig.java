package com.caoyuqian.lol.config;

import com.caoyuqian.lol.task.LadderCrawJob;
import com.caoyuqian.lol.task.PrintTimeJob;
import com.caoyuqian.lol.task.StatisticsChampionCrawJob;
import com.caoyuqian.lol.task.StatisticsTierCrawJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qian
 * @version V1.0
 * @Title: QuartzConfig
 * @Package: com.caoyuqian.lol.config
 * @Description: Quartz配置
 * @date 2019-08-28 18:53
 **/
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail printTimeJobDetail() {
        return JobBuilder
                //PrintTimeJob我们的业务类
                .newJob(PrintTimeJob.class)
                //可以给该JobDetail起一个id
                .withIdentity("PrintTimeJob")
                //每个JobDetail内都有一个Map，包含了关联到这个Job的数据，在Job类中可以通过context获取
                //关联键值对
                .usingJobData("msg", "Hello Quartz")
                //即使没有Trigger关联时，也不需要删除该JobDetail
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger printTimeJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                //设置时间周期单位秒
                .withIntervalInSeconds(10)
                .repeatForever();
        return TriggerBuilder.newTrigger()
                //关联上述的JobDetail
                .forJob(printTimeJobDetail())
                //给Trigger起个名字
                .withIdentity("quartzTaskService")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail statisticsTierCrawJobDetail(){
        return JobBuilder.newJob(StatisticsTierCrawJob.class)
                .withIdentity("StatisticsTierCrawJob")
                .usingJobData("msg","开始StatisticsTierCrawJob")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger statisticsTierCrawJobTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                //设置时间周期单位秒
                .withIntervalInSeconds(60*30)
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(statisticsTierCrawJobDetail())
                .withIdentity("StatisticsTierCrawTask")
                .withSchedule(scheduleBuilder)
                .build();
    }
    @Bean
    public JobDetail statisticsChampionCrawJobDetail(){
        return JobBuilder.newJob(StatisticsChampionCrawJob.class)
                .withIdentity("StatisticsChampionCrawJob")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger statisticsChampionCrawJobTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                //设置时间周期单位秒
                .withIntervalInSeconds(60*30)
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(statisticsChampionCrawJobDetail())
                .withIdentity("StatisticsChampionCrawTask")
                .withSchedule(scheduleBuilder)
                .build();
    }
    @Bean
    public JobDetail ladderCrawJobDetail(){
        return JobBuilder.newJob(LadderCrawJob.class)
                .withIdentity("LadderCrawJob")
                .usingJobData("msg","开始LadderCrawJob")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger ladderCrawJobTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                //设置时间周期单位秒
                .withIntervalInSeconds(60*30)
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(ladderCrawJobDetail())
                .withIdentity("LadderCrawJobTask")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
