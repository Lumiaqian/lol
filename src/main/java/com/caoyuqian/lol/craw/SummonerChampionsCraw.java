package com.caoyuqian.lol.craw;

import com.caoyuqian.lol.entity.ChampionsData;
import com.caoyuqian.lol.entity.Kda;
import com.caoyuqian.lol.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: SummonerChampionsCraw
 * @Package: com.caoyuqian.lol.craw
 * @Description: 爬取召唤师本赛季的英雄数据
 * @date 2019/9/9 1:49 下午
 **/
@Component
public class SummonerChampionsCraw {

    private final static Logger log = LoggerFactory.getLogger(SummonerChampionsCraw.class);

    public List<ChampionsData> get(String url, String userName) throws IOException {
        List<ChampionsData> championsDataList = new ArrayList<>();
        url = url + userName;
        Document document = HttpUtil.get(url);
        Elements tr = document.select("tbody.Body tr");
        //log.info(tr.html());
        log.info(String.valueOf(tr.size()));
        tr.forEach(element -> {
            String champion = element.select("td.ChampionName").text();
            HashMap<String, Object> gameData = new HashMap<>(16);
            if (!element.select("td.RatioGraph div.Left").text().isEmpty()){
                gameData.put("win", element.select("td.RatioGraph div.Left").last().text());
            }
            if (!element.select("td.RatioGraph div.Right").text().isEmpty()) {
                gameData.put("loss", element.select("td.RatioGraph div.Right").last().text());
            }

            Kda kda = Kda.builder()
                    .kill(Double.parseDouble(element.select("td.KDA span.Kill").text()))
                    .death(Double.parseDouble(element.select("td.KDA span.Death").text()))
                    .assist(Double.parseDouble(element.select("td.KDA span.Assist").text()))
                    .build();
            if (StringUtils.substringBefore(element.select("td.KDA").attr("data-value"), ":").equals("Perfect")){
                kda.setKda(kda.getKill()+kda.getAssist());
            }else {
                kda.setKda(Double.parseDouble(StringUtils.substringBefore(element.select("td.KDA").attr("data-value"), ":")));
            }
            Elements td = element.select("td.value");
            int gold = Integer.parseInt(td.first().text().replaceAll(",", ""));
            double cs = Double.parseDouble(td.get(1).text());
            int highestKill = Integer.parseInt(td.get(2).text());
            int highestDeath = Integer.parseInt(td.get(3).text());
            int dps = Integer.parseInt(td.get(4).text().replaceAll(",", ""));
            int bear = Integer.parseInt(td.get(5).text().replaceAll(",", ""));
            int doubleKill = 0;
            int tripleKill = 0;
            int quadraKill = 0;
            int pentaKill = 0;
            if (!td.get(6).text().isEmpty()) {
                doubleKill = Integer.parseInt(td.get(6).text());
            }
            if (!td.get(7).text().isEmpty()) {
                tripleKill = Integer.parseInt(td.get(7).text());
            }
            if (!td.get(8).text().isEmpty()) {
                quadraKill = Integer.parseInt(td.get(8).text());
            }
            if (!td.get(9).text().isEmpty()) {
                pentaKill = Integer.parseInt(td.get(9).text());
            }
            ChampionsData championsData = ChampionsData.builder()
                    .summoner(userName)
                    .champion(champion)
                    .gameData(gameData)
                    .kda(kda)
                    .gold(gold)
                    .cs(cs)
                    .highestKill(highestKill)
                    .highestDeath(highestDeath)
                    .dps(dps)
                    .bear(bear)
                    .doubleKill(doubleKill)
                    .tripleKill(tripleKill)
                    .quadraKill(quadraKill)
                    .pentaKill(pentaKill)
                    .build();
            championsDataList.add(championsData);
        });
        return championsDataList;
    }

    public static void main(String[] args) throws IOException {
        String url = "https://www.op.gg/summoner/champions/userName=";
        String userName = "타 잔";
        SummonerChampionsCraw craw = new SummonerChampionsCraw();
        List<ChampionsData> championsDataList = craw.get(url, userName);
        log.info(championsDataList.toString());
    }
}
