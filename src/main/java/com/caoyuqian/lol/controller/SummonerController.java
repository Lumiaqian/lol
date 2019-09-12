package com.caoyuqian.lol.controller;

import com.caoyuqian.lol.craw.SummonerChampionsCraw;
import com.caoyuqian.lol.entity.ChampionsData;
import com.caoyuqian.lol.model.Response;
import com.caoyuqian.lol.service.SummonerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: SummonerController
 * @Package: com.caoyuqian.lol.controller
 * @Description: 查询召唤师信息api
 * @date 2019/9/10 10:37 上午
 **/
@RestController
@RequestMapping("api")
public class SummonerController {

    private final static Logger log = LoggerFactory.getLogger(SummonerController.class);

    @Autowired
    private SummonerService summonerService;
    @Autowired
    private SummonerChampionsCraw championsCraw;

    @GetMapping("summoner/{name}")
    public Mono<Response<Object>> getSummonerByName(@PathVariable("name") String name) {
        return summonerService
                .findLatelyVersionSummonerByName(name)
                .flatMap(summoner -> Mono.just(Response.builder()
                        .data(summoner)
                        .code(0)
                        .msg("查询成功！")
                        .build()))
                .defaultIfEmpty(Response.builder().msg("无数据！").code(1).build());
    }

    @GetMapping("summoner/page")
    public Mono<Response> getSummonersByPage(@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
      return summonerService.findLatelyVersionSummoners(pageNum,pageSize)
              .collectList()
              .flatMap(summoners -> {
                  if (summoners.size() == 0){
                      return Mono.just(Response.builder()
                      .code(1)
                      .msg("无数据！")
                      .build());
                  }
                  return Mono.just(Response.builder()
                          .code(0)
                          .msg("获取成功！")
                          .data(summoners)
                          .build());
              });
    }
    @GetMapping("summoner/champions/{name}")
    public Mono<Response> championsDataFlux(@PathVariable("name")String name) throws IOException {
        String url = "https://www.op.gg/summoner/champions/userName=";
        List<ChampionsData> championsDataList = championsCraw.get(url,name);
        if (championsDataList==null || championsDataList.size()==0){
            return Mono.just(Response.builder().msg("无数据！").code(1).build());
        }
        return Mono.just(Response.builder().code(0).msg("获取该召唤师的英雄数据成功！").data(championsDataList).build());
    }
}
