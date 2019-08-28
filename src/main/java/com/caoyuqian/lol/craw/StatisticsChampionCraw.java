package com.caoyuqian.lol.craw;

import com.caoyuqian.lol.model.StatisticsChampion;
import com.caoyuqian.lol.service.StatisticsChampionService;
import com.caoyuqian.lol.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: StatisticsChampion
 * @Package: com.caoyuqian.lol.craw
 * @Description: 英雄统计
 * @date 2019-08-27 09:59
 **/
@Slf4j
public class StatisticsChampionCraw {
    private String url = "https://www.op.gg/statistics/champion/";
    private Document document;
    @Autowired
    private StatisticsChampionService statisticsChampionService;
    private void get() throws IOException {
        List<StatisticsChampion> statisticsChampions = new ArrayList<>();
        document = HttpUtil.getByHtmlUnit(url);
        Elements elements = document.select("div.ContentWrap div.Box div.Content");

        Elements tr = elements.select("tr.Row");
//        championNameList = championNames.stream().map()
        log.info(tr.get(1).html());
        tr.forEach(element -> {
            Elements td = element.select("td.Cell");
            if (td.hasText()){
                StatisticsChampion statisticsChampion = StatisticsChampion.builder()
                        .rank(Integer.parseInt(StringUtils.strip(td.get(0).text())))
                        .championName(StringUtils.strip(td.select("td.Cell.ChampionName a").text()))
                        .lp(StringUtils.strip(td.get(3).getElementsByClass("Value").text()))
                        .games(Integer.parseInt(StringUtils.strip(td.get(4).text().replaceAll(",",""))))
                        .kda(Double.parseDouble(StringUtils.strip(td.select("td.Cell.KDARatio").attr("data-value"))))
                        .cs(Double.parseDouble(StringUtils.strip(td.select("span.Value.Green").text())))
                        .gold(Integer.parseInt(StringUtils.strip(td.select("span.Value.Orange").text()).replaceAll(",","")))
                        .build();
                statisticsChampions.add(statisticsChampion);
            }
        });
        log.info(statisticsChampions.toString());
        log.info(String.valueOf(statisticsChampions.size()));
    }

    public static void main(String[] args) throws IOException {
        StatisticsChampionCraw statisticsChampionCraw = new StatisticsChampionCraw();
        statisticsChampionCraw.get();
    }
}
