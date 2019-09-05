package com.caoyuqian.lol.task;

import com.caoyuqian.lol.craw.SummonerCraw;
import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.entity.Summoner;
import com.caoyuqian.lol.service.SummonerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @author qian
 * @version V1.0
 * @Title: SummonerCrawExecutorTask
 * @Package: com.caoyuqian.lol.task
 * @Description: TOTO
 * @date 2019/9/4 5:26 下午
 **/
@Component
public class SummonerCrawExecutorTask {
    @Autowired
    private SummonerService summonerService;
    @Autowired
    private SummonerCraw summonerCraw;
    private final static Logger log = LoggerFactory.getLogger(SummonerCrawExecutorTask.class);

    @Async("summonerCrawExecutor")
    public Future<List<Summoner>> doSummonerCrawTask(int level, int index, List<Ladder> ladders){
        List<Summoner> summoners = new ArrayList<>();
        String url = "https://www.op.gg/summoner/userName=";
        long start = System.currentTimeMillis();
        log.info("开始爬取第{}层，{}组",level+1,index+1);
        log.info("执行任务: SummonerCrawJob-{}-{}",level+1,index+1);
        ladders.forEach(ladder -> {
            try {
                Summoner summoner = summonerCraw.get(url,ladder.getName());
                summoners.add(summoner);
            } catch (IOException e) {
                log.error("出现异常：{}",e.getMessage());
            }
        });
        log.info(summoners.toString());
        //summonerService.saveAll(summoners).subscribe();
        long end = System.currentTimeMillis();
        log.info("SummonerCrawJob-{}-{}任务耗时：{}秒",level+1,index+1,(end-start)/1000);
        // cdl.countDown();
        return new AsyncResult<>(summoners);
    }
}
