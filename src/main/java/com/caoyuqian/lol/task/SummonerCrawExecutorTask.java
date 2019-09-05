package com.caoyuqian.lol.task;

import com.caoyuqian.lol.craw.SummonerCraw;
import com.caoyuqian.lol.entity.Game;
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
import java.util.concurrent.ExecutionException;
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
    @Autowired
    private GameRecordCrawExecutorTask gameRecordCrawExecutorTask;

    private final static Logger log = LoggerFactory.getLogger(SummonerCrawExecutorTask.class);

    @Async("summonerCrawExecutor")
    public Future<List<Summoner>> doSummonerCrawTask(int level, int index, List<Ladder> ladders){
        List<Summoner> summoners = new ArrayList<>();
        String url = "https://www.op.gg/summoner/userName=";
        long start = System.currentTimeMillis();
        log.info("------开始爬取第{}层，{}组------",level+1,index+1);
        log.info("执行任务: SummonerCrawJob-{}-{}",level+1,index+1);
        ladders.forEach(ladder -> {
            try {
                Summoner summoner = summonerCraw.get(url,ladder.getName());
                summoners.add(summoner);
            } catch (IOException e) {
                log.error("出现异常：{}",e.getMessage());
            }
        });
        //开始爬取游戏记录
        log.info("需要爬取的游戏记录的次数：{}",summoners.size()*20);
        List<Future<List<Game>>> futures = new ArrayList<>();
        summoners.forEach(summoner -> {
            Future<List<Game>> future = gameRecordCrawExecutorTask.gameRecordCrawTask(summoner);
            futures.add(future);
        });
        // log.info(summoners.toString());
        //summonerService.saveAll(summoners).subscribe();
        List<Game> gameList = new ArrayList<>();
        futures.forEach(future->{
            try {
                List<Game> games = future.get();
                gameList.addAll(games);
            } catch (InterruptedException | ExecutionException e) {
                log.error("出现异常：{}",e.getMessage());
                e.printStackTrace();
            }
        });
        log.info("总共爬取游戏记录：{}",gameList.size());
        long end = System.currentTimeMillis();
        log.info("------SummonerCrawTask-{}-{}+GameRecordCrawTask任务耗时：{}秒------",level+1,index+1,(end-start)/1000);
        // cdl.countDown();
        return new AsyncResult<>(summoners);
    }
}
