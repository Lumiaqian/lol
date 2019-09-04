package com.caoyuqian.lol.task;

import com.caoyuqian.lol.craw.SummonerCraw;
import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.entity.Summoner;
import com.caoyuqian.lol.service.LadderService;
import com.caoyuqian.lol.service.SummonerService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Collector;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author qian
 * @version V1.0
 * @Title: SummonerCrawJob
 * @Package: com.caoyuqian.lol.task
 * @Description: TOTO
 * @date 2019/9/4 9:48 上午
 **/
public class SummonerCrawJob extends QuartzJobBean {

    private final static Logger log = LoggerFactory.getLogger(SummonerCrawJob.class);
    @Autowired
    private SummonerCraw summonerCraw;
    @Autowired
    private SummonerService summonerService;
    @Autowired
    private LadderService ladderService;
    @Autowired
    private AsyncExecutorTask task;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = "https://www.op.gg/summoner/userName=";
        List<Future<List<Summoner>>> futures = new ArrayList<>();
        List<Summoner> summoners = new ArrayList<>();
        ladderService.findAll().collectList()
                .flatMap(ladders -> {
                    if (ladders.size() != 0) {
                        long start = System.currentTimeMillis();
                        Map<Integer, List<Ladder>> map = ladders.stream()
                                .collect(Collectors.groupingBy(ladder -> {
                                    return (ladder.getRanking() - 1) / 50;
                                }));
                        log.info("分组：{}", map.size());
                       /* map.forEach((k,v) -> {
                            Future<List<Summoner>> future = task.doSummonerCrawTask(k,v,cdl);
                            futures.add(future);
                        });*/
                        Future<List<Summoner>> future = task.doSummonerCrawTask(10,map.get(10));
                        futures.add(future);
                        futures.forEach(future1 -> {
                            try {
                                summoners.addAll(future1.get());
                            } catch (InterruptedException | ExecutionException e) {
                                log.error(e.getMessage());
                            }
                        });
                        log.info("size:{}",summoners.size());
                        long end = System.currentTimeMillis();
                        log.info("SummonerCrawJob任务总耗时：{}秒", (end - start) / 1000);
                        // task.doSummonerCrawTask(0,map.get(0));
                       /* try {
                            Summoner summoner = summonerCraw.get(url,map.get(20).get(0).getName());
                            log.info(summoner.toString());
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }*/
                    }
                    return Mono.just("查询数据库中的ladder:" + ladders.toString());
                }).subscribe(log::info);


    }
}
