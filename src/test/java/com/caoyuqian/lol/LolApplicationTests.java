package com.caoyuqian.lol;

import com.caoyuqian.lol.craw.LadderCraw;
import com.caoyuqian.lol.craw.StatisticsChampionCraw;
import com.caoyuqian.lol.craw.StatisticsTierCraw;
import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.model.Response;
import com.caoyuqian.lol.model.StatisticsTier;
import com.caoyuqian.lol.service.StatisticsChampionService;
import com.caoyuqian.lol.service.StatisticsTierService;
import com.caoyuqian.lol.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LolApplicationTests {

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




    @Test
    public void test1() throws IOException {
        List<String> rankingList = new ArrayList<>();

        String url = "https://www.op.gg/ranking/ladder/page=1";
        Document document = HttpUtil.get(url);
        Elements eles = document.getElementsByClass("ranking-table__row");

        //排名
        Elements ranks = eles.select("td.ranking-table__cell--rank");
        rankingList = ranks.stream().map(Element::text).collect(Collectors.toList());

        //召唤师名称
        Elements names = eles.select("td.ranking-table__cell--summoner span");
        List<String> namess = names.stream().map(Element::text).collect(Collectors.toList());
        namess = namess.stream().map(name -> {
            name = name.replace("<span>", "");
            name = name.replace("</span>", "");
            return name;
        }).collect(Collectors.toList());

        //召唤师段位
        Elements dws = eles.select("td.ranking-table__cell--tier");
        List<String> dwss = dws.stream().map(Element::text).collect(Collectors.toList());

        //召唤师分数
        Elements lps = eles.select("td.ranking-table__cell--lp");
        List<String> lpss = lps.stream().map(element -> {
            return StringUtils.substringBefore(element.text(), " LP").replaceAll(",", "");
        }).collect(Collectors.toList());

        //召唤师等级
        Elements levs = eles.select("td.ranking-table__cell--level");
        List<String> levss = levs.stream().map(Element::text).collect(Collectors.toList());

        //爬取召唤师胜率
        Elements winRates = eles.select("td.ranking-table__cell--winratio span");
        List<String> winRatess = winRates.stream().map(Element::text).collect(Collectors.toList());

        System.out.println(winRatess);
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
