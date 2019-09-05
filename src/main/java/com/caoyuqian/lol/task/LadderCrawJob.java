package com.caoyuqian.lol.task;

import com.caoyuqian.lol.entity.Game;
import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.entity.Summoner;
import com.caoyuqian.lol.service.LadderService;
import com.caoyuqian.lol.service.SummonerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author qian
 * @version V1.0
 * @Title: LadderCrawJob
 * @Package: com.caoyuqian.lol.task
 * @Description: 爬取排行榜数据Task
 * @date 2019-08-29 10:28
 **/
public class LadderCrawJob extends QuartzJobBean {

    @Autowired
    private AsyncExecutorTask task;
    @Autowired
    private LadderService ladderService;
    @Autowired
    private SummonerService summonerService;
    private final static Logger log = LoggerFactory.getLogger(LadderCrawJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long start = System.currentTimeMillis();
        String url = "https://www.op.gg/ranking/ladder/page=";
        List<Ladder> ladders = new ArrayList<>();
        List<Future<List<Ladder>>> futureList = new ArrayList<>();
        CountDownLatch cdl = new CountDownLatch(10);
        try {
            int page = 1;
            int size = 10;
            while (page <= size) {
                Future<List<Ladder>> future = task.doLadderCrawTask(url,page);
                futureList.add(future);
                page++;
            }
            //cdl.await();
            futureList.forEach(listFuture -> {

                try {
                    ladders.addAll(listFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

            });


            log.info("10页数据总数为：{}",ladders.size());
            //存入数据库
            ladderService.saveAll(ladders).subscribe(log::info);
            long endOne = System.currentTimeMillis();
            log.info("LadderCrawJob任务耗时：{}秒",(endOne-start)/1000);


            List<Summoner> summoners = summonerCraw(ladders);
            log.info("召唤师数量：{}",summoners.size());
            //存入数据库
            summonerService.saveAll(summoners).subscribe(log::info);

            long end = System.currentTimeMillis();
            log.info("LadderCrawJob+SummonerCrawJob任务耗时：{}秒",(end-start)/1000);
        } catch (Exception e) {
            log.error("出现异常：{}",e.getMessage());

        }
    }
     /**
       * @Param:
       * @return:
       * @Author: qian
       * @Description: 爬取召唤师信息
       * @Date: 2019/9/5 2:43 下午
      **/
    private List<Summoner> summonerCraw(List<Ladder> ladders){
        long start = System.currentTimeMillis();
        List<Future<List<Summoner>>> futures = new ArrayList<>();
        List<Summoner> summoners = new ArrayList<>();
        Map<Integer, List<Ladder>> map = ladders.stream()
                .collect(Collectors.groupingBy(ladder -> {
                    return (ladder.getRanking() - 1) / 50;
                }));
        log.info("分组：{}", map.size());
         map.forEach((k,v) -> {
                            Future<List<Summoner>> future = task.doSummonerCrawTask(k,v);
                            futures.add(future);
                        });
        futures.forEach(future1 -> {
            try {
                summoners.addAll(future1.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error(e.getMessage());
            }
        });
        long end = System.currentTimeMillis();
        log.info("SummonerCrawJob任务总耗时：{}秒", (end - start) / 1000);
        return summoners;
    }
    private List<Game> GameRecordCraw(List<Summoner> summoners){
        List<Game> gameList = new ArrayList<>();
        return gameList;
    }
}
