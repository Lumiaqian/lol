package com.caoyuqian.lol.task;

import com.caoyuqian.lol.craw.GoodsCraw;
import com.caoyuqian.lol.craw.LadderCraw;
import com.caoyuqian.lol.craw.SummonerCraw;
import com.caoyuqian.lol.entity.Game;
import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.entity.Summoner;
import com.caoyuqian.lol.entity.goods.Goods;
import com.caoyuqian.lol.service.SummonerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

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
 * @Title: AsyncExecutorTask
 * @Package: com.caoyuqian.lol.task
 * @Description: 异步线程池任务
 * @date 2019-08-29 11:08
 **/
@Component
public class AsyncExecutorTask {
    @Autowired
    private LadderCraw ladderCraw;
    @Autowired
    private SummonerCrawExecutorTask summonerCrawExecutorTask;

    @Autowired
    private GoodsCraw goodsCraw;

    private final static Logger log = LoggerFactory.getLogger(AsyncExecutorTask.class);

    @Async("taskExecutor")
    public Future<List<Ladder>> doLadderCrawTask(String url, int page, CountDownLatch cdl) throws Exception {
        log.info("开始爬取排行榜第{}页的数据", page);
        List<Ladder> ladders = ladderCraw.ladderCraw(url, page);
        cdl.countDown();
        return new AsyncResult<>(ladders);
    }

    @Async("taskExecutor")
    public Future<List<Ladder>> doLadderCrawTask(String url, int page) throws Exception {
        log.info("开始爬取排行榜第{}页的数据", page);
        List<Ladder> ladders = ladderCraw.ladderCraw(url, page);
        return new AsyncResult<>(ladders);
    }

    @Async("taskExecutor")
    public Future<List<Summoner>> doSummonerCrawTask(int index, List<Ladder> ladders) {
        List<Summoner> summoners = new ArrayList<>();
        List<Future<List<Summoner>>> futures = new ArrayList<>();
        long start = System.currentTimeMillis();
        log.info("开始爬取第{}组", index + 1);
        log.info("执行任务: SummonerCrawJob-{}", index + 1);
        Map<Integer, List<Ladder>> map = ladders.stream()
                .collect(Collectors.groupingBy(ladder -> (ladder.getRanking() - 1) / 5));

        //log.info(map.keySet().toString());
        map.forEach((k,v) ->{
            Future<List<Summoner>> future = summonerCrawExecutorTask.doSummonerCrawTask(index,k,v);
            futures.add(future);
        });
        futures.forEach(future -> {
            try {
                summoners.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error(e.getMessage());
            }
        });
        long end = System.currentTimeMillis();

        log.info("SummonerCrawJob-{}任务summoner的数量：{}",index+1,summoners.size());
        log.info("SummonerCrawJob-{}任务耗时：{}秒", index+1,(end - start) / 1000);

        return new AsyncResult<>(summoners);
    }


    @Async("taskExecutorGoods")
    public Future<List<Goods>> doGoodsCrawTask(int index, int end) throws IOException {
        log.info("开始爬取第{}页到第{}页的装备物品数据",index,end);
        List<Goods> goods = goodsCraw.crawByPage(index, end);

        return  new AsyncResult<>(goods);

    }
}
