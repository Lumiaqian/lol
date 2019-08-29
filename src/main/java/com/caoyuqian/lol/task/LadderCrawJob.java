package com.caoyuqian.lol.task;

import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.service.LadderService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qian
 * @version V1.0
 * @Title: LadderCrawJob
 * @Package: com.caoyuqian.lol.task
 * @Description: 爬取排行榜数据Task
 * @date 2019-08-29 10:28
 **/
@Slf4j
public class LadderCrawJob extends QuartzJobBean {

    @Autowired
    private AsyncExecutorTask task;
    @Autowired
    private LadderService ladderService;

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
                Future<List<Ladder>> future = task.doLadderCrawTask(url,page,cdl);
                futureList.add(future);
                page++;
            }
            cdl.await();
            futureList.forEach(listFuture -> {
                if (listFuture.isDone()){
                    try {
                        ladders.addAll(listFuture.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
            log.info("10页数据总数为：{}",ladders.size());
            //存入数据库
            ladderService.saveAll(ladders).subscribe();
            long end = System.currentTimeMillis();
            log.info("LadderCrawJob任务耗时：{}秒",(end-start)/1000);
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }
}
