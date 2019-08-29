package com.caoyuqian.lol.task;

import com.caoyuqian.lol.craw.LadderCraw;
import com.caoyuqian.lol.entity.Ladder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;


/**
 * @author qian
 * @version V1.0
 * @Title: AsyncExecutorTask
 * @Package: com.caoyuqian.lol.task
 * @Description: 异步线程池任务
 * @date 2019-08-29 11:08
 **/
@Component
@Slf4j
public class AsyncExecutorTask {
    @Autowired
    private LadderCraw ladderCraw;

    @Async("taskExecutor")
    public Future<List<Ladder>> doLadderCrawTask(String url, int page, CountDownLatch cdl) throws Exception {
        log.info("开始爬取排行榜第{}页的数据",page);
        List<Ladder> ladders = ladderCraw.ladderCraw(url,page);
        cdl.countDown();
        return new AsyncResult<>(ladders);
    }
    @Async("taskExecutor")
    public Future<List<Ladder>> doLadderCrawTask(String url, int page) throws Exception {
        log.info("开始爬取排行榜第{}页的数据",page);
        List<Ladder> ladders = ladderCraw.ladderCraw(url,page);
        return new AsyncResult<>(ladders);
    }

}
