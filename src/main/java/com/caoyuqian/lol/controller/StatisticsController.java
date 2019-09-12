package com.caoyuqian.lol.controller;

import com.caoyuqian.lol.model.Response;
import com.caoyuqian.lol.service.LadderService;
import com.caoyuqian.lol.service.StatisticsChampionService;
import com.caoyuqian.lol.service.StatisticsTierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author qian
 * @version V1.0
 * @Title: ApiController
 * @Package: com.caoyuqian.lol.controller
 * @Description: 查询统计信息api
 * @date 2019-08-30 11:31
 **/
@RestController
@RequestMapping("api")
public class StatisticsController {

    @Autowired
    private LadderService ladderService;
    @Autowired
    private StatisticsTierService statisticsTierService;
    @Autowired
    private StatisticsChampionService statisticsChampionService;

    private final static Logger log = LoggerFactory.getLogger(StatisticsController.class);

     /**
       * @Param:
       * @return: Mono
       * @Author: qian
       * @Description: 获取最新段位统计数据
       * @Date: 2019/9/10 10:35 上午
      **/
    @GetMapping("statistics/tier")
    public Mono<Response> getTiers() {
        return statisticsTierService.findLatelyVersionStatisticsTier()
                .collectList().flatMap(statisticsTiers -> {
                    if (statisticsTiers.size() == 0){
                        return Mono.just(Response.builder().msg("无数据！").code(1).build());
                    }
                    return Mono.just(Response.builder().code(0).msg("获取成功！").data(statisticsTiers).build());
                });
    }

    @GetMapping("statistics/champion")
    public Mono<Response> getChampion() {
        return statisticsChampionService
                .findAll()
                .collectList()
                .flatMap(statisticsChampions -> {
                    if (statisticsChampions.size() == 0) {
                        return Mono.just(Response.builder().code(1).msg("数据为空！").build());
                    }
                    return Mono.just(Response.builder().code(0).msg("获取成功！").data(statisticsChampions).build());
                })
                ;
    }
     /**
       * @Param: pageNum pageSize
       * @return: Mono
       * @Author: qian
       * @Description: 获取英雄统计数据
       * @Date: 2019/9/10 10:34 上午
      **/
    @GetMapping("statistics/champion/page")
    public Mono<Response> getChampions(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){



        return statisticsChampionService.findAllByPage(pageNum,pageSize)
                .collectList()
                .flatMap(statisticsChampions -> {
                    if (statisticsChampions.size() == 0) {
                        return Mono.just(Response.builder().code(1).msg("数据为空！").build());
                    }
                    return Mono.just(Response.builder().code(0).msg("获取成功！").data(statisticsChampions).build());
                })
                ;
    }
    @GetMapping("ladder")
    public Mono<Response> getLadder(){
        return ladderService
                .findAll()
                .collectList()
                .flatMap(ladders -> {
                    if (ladders.size() == 0) {
                        return Mono
                                .just(Response.builder().code(1).msg("数据为空！").build());
                    }
                    return Mono
                            .just(Response.builder().code(0).msg("获取成功！").data(ladders).build());
                })
                ;
    }
     /**
       * @Param: pageNum pageSize
       * @return: Mono
       * @Author: qian
       * @Description: 获取天梯排行榜数据
       * @Date: 2019/9/10 10:33 上午
      **/
    @GetMapping("ladder/page")
    public Mono<Response> getLadderByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return ladderService
                .findByPage(pageNum,pageSize)
                .collectList()
                .flatMap(ladders -> {
                    if (ladders.size() == 0) {
                        return Mono
                                .just(Response.builder().code(1).msg("数据为空！").build());
                    }
                    return Mono
                            .just(Response.builder().code(0).msg("获取成功！").data(ladders).build());
                })
                ;
    }



}
