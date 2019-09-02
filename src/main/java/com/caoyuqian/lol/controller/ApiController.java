package com.caoyuqian.lol.controller;

import com.caoyuqian.lol.model.Response;
import com.caoyuqian.lol.service.LadderService;
import com.caoyuqian.lol.service.StatisticsChampionService;
import com.caoyuqian.lol.service.StatisticsTierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author qian
 * @version V1.0
 * @Title: ApiController
 * @Package: com.caoyuqian.lol.controller
 * @Description: TOTO
 * @date 2019-08-30 11:31
 **/
@RestController
@Slf4j
@RequestMapping("api")
public class ApiController {

    @Autowired
    private LadderService ladderService;
    @Autowired
    private StatisticsTierService statisticsTierService;
    @Autowired
    private StatisticsChampionService statisticsChampionService;

    @GetMapping("statistics/tier")
    public Mono<Response> getTiers() {
        return statisticsTierService
                .findAll()
                .collectList()
                .flatMap(statisticsTiers -> {
                    if (statisticsTiers.size() == 0) {
                        return Mono
                                .just(Response
                                        .builder()
                                        .code(1)
                                        .msg("数据为空！")
                                        .build());
                    }
                    return Mono
                            .just(Response
                                    .builder()
                                    .msg("获取成功！")
                                    .code(0)
                                    .data(statisticsTiers)
                                    .build());
                })
                ;
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
