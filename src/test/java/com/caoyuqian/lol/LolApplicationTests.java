package com.caoyuqian.lol;

import com.caoyuqian.lol.craw.LadderCraw;
import com.caoyuqian.lol.craw.StatisticsChampionCraw;
import com.caoyuqian.lol.craw.StatisticsTierCraw;
import com.caoyuqian.lol.craw.SummonerCraw;
import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.entity.Summoner;
import com.caoyuqian.lol.model.Response;
import com.caoyuqian.lol.model.StatisticsTier;
import com.caoyuqian.lol.service.LadderService;
import com.caoyuqian.lol.service.StatisticsChampionService;
import com.caoyuqian.lol.service.StatisticsTierService;
import com.caoyuqian.lol.service.SummonerService;
import com.caoyuqian.lol.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest

public class LolApplicationTests {

    private final static Logger log = LoggerFactory.getLogger(LolApplicationTests.class);
    @Autowired
    private LadderCraw ladderCraw;
    @Autowired
    private StatisticsTierCraw statisticsTierCraw;
    @Autowired
    private StatisticsTierService statisticsTierService;
    @Autowired
    private StatisticsChampionCraw statisticsChampionCraw;
    @Autowired
    private StatisticsChampionService statisticsChampionService;
    @Autowired
    private SummonerCraw summonerCraw;
    @Autowired
    private SummonerService summonerService;
    @Autowired
    private LadderService ladderService;



    @Test
    public void testSummoner(){
        String url = "https://www.op.gg/summoner/userName=";
        ladderService.findAll().collectList()
                .flatMap(ladders -> {
                    if (ladders.size()!=0){
                        Map<Integer,List<Ladder>> map = ladders.stream()
                                .collect(Collectors.groupingBy(ladder -> {
                                    return ladder.getRanking()/50;
                                }));
                        log.info("test  分组：{}",map.size());
                        log.info(map.get(20).get(0).toString());
                        try {
                            Summoner summoner = summonerCraw.get(url,map.get(20).get(0).getName());
                            log.info(summoner.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return Mono.just("test  查询数据库中的ladder:"+ladders.toString());
                }).subscribe(log::info);
    }


    @Test
    public void test1() throws IOException {
        List<String> rankingList = new ArrayList<>();

        String url = "https://www.op.gg/summoner/userName=타+잔 ";
        String championUrl = "https://www.op.gg/statistics/ajax2/champion/";
        Document document = HttpUtil.get(championUrl);
        Elements eles = document.getElementsByClass("Content");
        log.info(eles.html());

    }


    @Test
    public void testCraw() throws IOException {
        String url = "https://www.op.gg/ranking/ladder/page=";
        int page = 1;
//        while (page <= 10) {
//            ladderCraw.ladderCraw(url, page);
//            page++;
//        }
        List<Ladder> ladders = ladderCraw.ladderCraw(url, page);


    }

    @Test
    public void testMongo() throws IOException {
       statisticsChampionService.saveAll(statisticsChampionCraw.get()).subscribe();
    }

    @Test
    public void testRESTClient(){
        WebClient webClient =WebClient.create("http://localhost:8080/api/statistics/tier");
        Mono<Response> responseMono = webClient
                .get()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Response.class);
        responseMono.subscribe(System.out::println);
    }
}
