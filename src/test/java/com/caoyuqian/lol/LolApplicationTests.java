package com.caoyuqian.lol;

import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LolApplicationTests {

    @Test
    public void contextLoads() throws IOException {
        String url = "https://www.op.gg/ranking/ladder/";
        Document document = HttpUtil.get(url);

        Elements elements = document.getElementsByClass("ranking-highest__item");
        //log.info(elements.html());
        Elements rankings = elements.select("div.ranking-highest__rank");
        Elements names = elements.select("a.ranking-highest__name");
        Elements levels = elements.select("div.ranking-highest__tierrank span");
        Elements lps = elements.select("div.ranking-highest__tierrank b");
        Elements winRatios = elements.select("span.winratio__text");
        Elements lvs = elements.select("div.ranking-highest__level");

        List<String> rankingList = rankings.stream().map(Element::text).collect(Collectors.toList());
        List<String> nameList = names.stream().map(Element::text).collect(Collectors.toList());
        List<String> levelList = levels.stream().map(Element::text).collect(Collectors.toList());
        List<String> lpList = lps.stream().map(element -> {
            return StringUtils.substringBefore(element.text()," LP").replaceAll(",","");
        }).collect(Collectors.toList());
        List<String> winRatioList = winRatios.stream().map(Element::text).collect(Collectors.toList());
        List<String> lvList = lvs.stream().map(element -> {
            if (element.text().startsWith("Lv.")){
                return StringUtils.substringAfter(element.text(),"Lv.");
            }
            return element.text();
        }).collect(Collectors.toList());
        List<Ladder> ladders = new ArrayList<>(rankingList.size());
        log.info(rankingList.toString());
        log.info(nameList.toString());
        log.info(levelList.toString());
        log.info(lpList.toString());
        log.info(winRatioList.toString());
        log.info(lvList.toString());
        for (int i = 0; i < rankingList.size(); i++) {
            Ladder ladder = Ladder.builder().name(nameList.get(i))
                    .ranking(rankingList.get(i))
                    .level(levelList.get(i))
                    .lp(lpList.get(i))
                    .lv(lvList.get(i))
                    .winRatio(winRatioList.get(i))
                    .build();
            //log.info(ladder.toString());
            ladders.add(ladder);
        }
        log.info(ladders.toString());
    }

}
