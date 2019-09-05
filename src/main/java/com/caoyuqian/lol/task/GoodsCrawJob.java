package com.caoyuqian.lol.task;

import com.caoyuqian.lol.entity.goods.Goods;
import com.caoyuqian.lol.service.GoodsCrawService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class GoodsCrawJob extends QuartzJobBean {

    @Autowired
    private  AsyncExecutorTask asyncExecutorTask;
    @Autowired
    private GoodsCrawService goodsCrawService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        int[] nums = new int[12];

        for (int i = 0; i <12; i++) {
            nums[i] =i*22 ;
        }
        List<Future<List<Goods>>> futures = new ArrayList<>();
        for (int i = 0; i <nums.length ; i++) {
            try {
                Future<List<Goods>> listFuture = asyncExecutorTask.doGoodsCrawTask(nums[i], nums[i + 1] - 1);

                futures.add(listFuture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<Goods> goodsList = new ArrayList<>();
        futures.forEach(future ->{
            try {
                List<Goods> goods = future.get();
                goodsList.addAll(goods);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        goodsCrawService.saveAllGoods(goodsList).subscribe();

    }
}
