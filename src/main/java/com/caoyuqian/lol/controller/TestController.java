package com.caoyuqian.lol.controller;

import com.caoyuqian.lol.craw.HeroCraw;
import com.caoyuqian.lol.craw.StatisticsChampionCraw;
import com.caoyuqian.lol.craw.StatisticsTierCraw;
import com.caoyuqian.lol.craw.SummonerChampionsCraw;
import com.caoyuqian.lol.entity.ChampionsData;
import com.caoyuqian.lol.entity.Game;
import com.caoyuqian.lol.entity.GameParams;
import com.caoyuqian.lol.model.StatisticsChampion;
import com.caoyuqian.lol.model.StatisticsTier;
import com.caoyuqian.lol.service.HeroService;
import com.caoyuqian.lol.service.StatisticsChampionService;
import com.caoyuqian.lol.service.StatisticsTierService;
import com.caoyuqian.lol.service.SummonerService;
import com.caoyuqian.lol.task.AsyncExecutorTask;
import com.caoyuqian.lol.task.GameRecordCrawExecutorTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author qian
 * @version V1.0
 * @Title: TestController
 * @Package: com.caoyuqian.lol.controller
 * @Description: TOTO
 * @date 2019-08-27 19:57
 **/
@RestController
public class TestController {
    @Autowired
    private StatisticsTierCraw statisticsTierCraw;
    @Autowired
    private StatisticsTierService statisticsTierService;
    @Autowired
    private StatisticsChampionService statisticsChampionService;
    @Autowired
    private StatisticsChampionCraw statisticsChampionCraw;
    @Autowired
    private HeroCraw heroCraw;
    @Autowired
    private HeroService heroService;
    @Autowired
    private SummonerService summonerService;
    @Autowired
    private GameRecordCrawExecutorTask task;
    @Autowired
    private AsyncExecutorTask asyncExecutorTask;
    @Autowired
    private SummonerChampionsCraw championsCraw;

    private final static Logger log = LoggerFactory.getLogger(TestController.class);


    @GetMapping("/st/save")
    public Flux<StatisticsTier> saveall() throws IOException {
        return statisticsTierService.saveAll(statisticsTierCraw.get());
    }

    @GetMapping("/sc/save")
    public Flux<StatisticsChampion> save() throws IOException {
        return statisticsChampionService.saveAll(statisticsChampionCraw.get());
    }

    @GetMapping("/hero/save")
    public Mono<String> saveAllHero() throws IOException {
        return heroService.saveAllHero(heroCraw.craw());
    }

    @GetMapping("test")
    public Mono<String> test() {
        return summonerService.findAll().collectList()
                .flatMap(summoners -> {
                    if (summoners.size() != 0) {
                        long start = System.currentTimeMillis();
                        List<GameParams> gameParams = new ArrayList<>();
                        summoners.forEach(summoner -> {
                            gameParams.addAll(summoner.getParams());
                        });
                        log.info("共有{}场游戏记录",gameParams.size());
                        List<GameParams> finalGameParams = gameParams.
                                stream()
                                .collect(Collectors.collectingAndThen
                                        (Collectors.toCollection(
                                                () -> new TreeSet<>(
                                                        Comparator.comparing(
                                                                GameParams::getGameId)))
                                                ,ArrayList::new));
                        log.info("去重还剩：{}场游戏记录",finalGameParams.size());

                        AtomicInteger index = new AtomicInteger(1);
                        finalGameParams.forEach(gameParam ->{
                            gameParam.setIndex(index.get());
                            index.getAndIncrement();
                        });
                        Map<Integer,List<GameParams>> hashMap = finalGameParams.
                                stream().
                                collect(Collectors.groupingBy
                                        (o -> (o.getIndex()-1)/300));
                        log.info("共有{}组",hashMap.size());
                        log.info(hashMap.keySet().toString());
                        Map<Integer,List<GameParams>> map = hashMap.get(0).stream()
                                .collect(Collectors.groupingBy(o -> (o.getIndex()-1)/30));
                        log.info("共有{}组",map.size());
                        log.info(map.keySet().toString());
                        log.info(map.get(0).stream().map(GameParams::getIndex).collect(Collectors.toList()).toString());
                        //爬取游戏记录
                        List<Future<List<Game>>> futures = new ArrayList<>();
                        hashMap.forEach((k,v) ->{
                            Future<List<Game>> future = asyncExecutorTask.doGameRecordCrawTask(k,v);
                            futures.add(future);
                        });
                        List<Game> games = new ArrayList<>();
                        futures.forEach(f->{
                            try {
                                games.addAll(f.get());
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        });
                        log.info("一共爬取{}场游戏记录",games.size());
                        long end = System.currentTimeMillis();
                        log.info("------GameRecord任务耗时：{}秒------",(end-start)/1000);
                    }
                    return Mono.just("test  查询数据库中的ladder:" + summoners.toString());
                });
    }
    @GetMapping("champions/{name}")
    public Flux<List<ChampionsData>> championsDataFlux(@PathVariable("name")String name) throws IOException {
        String url = "https://www.op.gg/summoner/champions/userName=";
        return Flux.just(championsCraw.get(url,name));
    }
}
