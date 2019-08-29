package com.caoyuqian.lol.controller;

import com.caoyuqian.lol.craw.StatisticsChampionCraw;
import com.caoyuqian.lol.craw.StatisticsTierCraw;
import com.caoyuqian.lol.model.StatisticsChampion;
import com.caoyuqian.lol.model.StatisticsTier;
import com.caoyuqian.lol.service.StatisticsChampionService;
import com.caoyuqian.lol.service.StatisticsTierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

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

    @GetMapping("/st/save")
    public Flux<StatisticsTier> saveall() throws IOException {
        return statisticsTierService.saveAll(statisticsTierCraw.get());
    }

    @GetMapping("/sc/save")
    public Flux<StatisticsChampion> save() throws IOException {
        return statisticsChampionService.saveAll(statisticsChampionCraw.get());
    }
}
