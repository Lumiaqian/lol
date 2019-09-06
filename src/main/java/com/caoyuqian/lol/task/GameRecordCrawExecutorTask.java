package com.caoyuqian.lol.task;

import com.caoyuqian.lol.craw.GameRecordCraw;
import com.caoyuqian.lol.entity.Game;
import com.caoyuqian.lol.entity.GameParams;
import com.caoyuqian.lol.entity.Summoner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author qian
 * @version V1.0
 * @Title: GameRecordCraw
 * @Package: com.caoyuqian.lol.task
 * @Description: TOTO
 * @date 2019/9/5 2:22 下午
 **/
@Component
public class GameRecordCrawExecutorTask {

    @Autowired
    private GameRecordCraw gameRecordCraw;
    private static final Logger log = LoggerFactory.getLogger(GameRecordCrawExecutorTask.class);

    @Async("gameRecordCrawExecutor")
    public Future<List<Game>> gameRecordCrawTask(int index,List<GameParams> params){
        long start = System.currentTimeMillis();
        log.info("开始爬取第{}组", index);
        String url = "https://www.op.gg/summoner/matches/ajax/detail/";
        List<Game> gameList = new ArrayList<>();
        params.forEach(e -> {
            try {
                Game game = gameRecordCraw.get(url,e);
                gameList.add(game);
            } catch (IOException ex) {
                log.error("GameRecordCrawExecutorTask:出现异常:{}",ex.getMessage());
                //ex.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        log.info("------GameRecordCrawTask任务耗时：{}毫秒------",(end-start));
        return new AsyncResult<>(gameList);
    }
    @Async("gameRecordCrawExecutor")
    public Future<List<Game>> gameRecordCrawTask(int level,int index,List<GameParams> params){
        long start = System.currentTimeMillis();
        log.info("开始爬取第{}层-第{}", level,index);
        String url = "https://www.op.gg/summoner/matches/ajax/detail/";
        List<Game> gameList = new ArrayList<>();
        params.forEach(e -> {
            try {
                Game game = gameRecordCraw.get(url,e);
                gameList.add(game);
            } catch (IOException ex) {
                log.error("GameRecordCrawExecutorTask:出现异常:{}",ex.getMessage());
                //ex.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        log.info("------GameRecordCrawTask任务耗时：{}毫秒------",(end-start));
        return new AsyncResult<>(gameList);
    }
}
