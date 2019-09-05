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
    public Future<List<Game>> gameRecordCrawTask(Summoner summoner){
        long start = System.currentTimeMillis();
        log.info("------开始爬取{}的游戏记录------",summoner.getName());
        String gameRecordUrl = "https://www.op.gg/summoner/matches/ajax/detail/";
        List<GameParams> params = summoner.getParams();
        List<Game> gameList = new ArrayList<>();
        params.forEach(param -> {
            log.info(param.toString());
            try {
                Game game = gameRecordCraw.get(gameRecordUrl,param);
                gameList.add(game);
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        });
        long end = System.currentTimeMillis();
        log.info("------GameRecordCrawTask任务耗时：{}毫秒------",(end-start));
        return new AsyncResult<>(gameList);
    }
}
