package com.caoyuqian.lol.craw;

import com.alibaba.fastjson.JSONObject;
import com.caoyuqian.lol.entity.*;
import com.caoyuqian.lol.utils.HttpUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: SummonerCraw
 * @Package: com.caoyuqian.lol.craw
 * @Description: 爬取召唤师详细数据
 * @date 2019/9/2 6:58 下午
 **/
@Component
@Slf4j
public class SummonerCraw {

    public Summoner get(String url, String name) throws IOException {
        url = url + name;
        Summoner summoner = new Summoner();
        Document document = HttpUtil.getByHtmlUnit(url);
        Elements mostChampionElement = document.select("table.GameAverageStats td.MostChampion li div.Content");
        Elements positionElement = document.select("table.GameAverageStats td.PositionStats ul.Content li");
        Elements gameItemWrap = document.select("div.GameItemWrap");

        //查询游戏记录需要的参数
        List<GameParams> params = new ArrayList<>();
        //召唤师Id
        String id = document.select("div.GameListContainer").attr("data-summoner-id");
        //最近20场发挥最好的英雄
        List<MostChampion> champions = new ArrayList<>();
        //最近打的位置
        List<PreferredPosition> positions = new ArrayList<>();
        //头像
        String borderImage = "Https:" + document.select("img.ProfileImage").attr("src");
        //等级
        int lv = Integer.parseInt(document.select("div.ProfileIcon span").text());
        //战队
        String team = StringUtils.substringBefore(document.select("div.Profile div.Team").text().replaceAll(" ", ""),"[");
        //职业ID
        String careerId = StringUtils.substringBetween(document.select("div.Profile div.Team span").text(),"[","]");
        //rank排名
        int rank = Integer.parseInt(document.select("div.Rank div.LadderRank a span.ranking").text());
        //段位
        String tier = document.select("div.SummonerRatingMedium div.TierRank").text();
        //段位icon
        String tierIcon = "Https:" + document.select("div.SummonerRatingMedium img.Image").attr("src");
        //rank分数
        int lp = Integer.parseInt(StringUtils.substringBefore(document.select("div.SummonerRatingMedium div.TierInfo span.LeaguePoints").text(),
                " LP").replaceAll(",", ""));
        //本赛季总胜场
        int totalWin = Integer.parseInt(document.select("div.SummonerRatingMedium div.TierInfo span.wins").text().replaceAll("胜",""));
        //本赛季总负场
        int totalLose = Integer.parseInt(document.select("div.SummonerRatingMedium div.TierInfo span.losses").text().replaceAll("负",""));
        //本赛季胜率
        String winRatio = document.select("div.SummonerRatingMedium div.TierInfo span.winratio").text().replaceAll("胜率 ","");
        Kda kda = Kda.builder()
                .kill(Double.parseDouble(document.select("table.GameAverageStats td.KDA div.KDA span.Kill").text()))
                .death(Double.parseDouble(document.select("table.GameAverageStats td.KDA div.KDA span.Death").text()))
                .assist(Double.parseDouble(document.select("table.GameAverageStats td.KDA div.KDA span.Assist").text()))
                .kda(Double.parseDouble(StringUtils.substringBefore(document.select("table.GameAverageStats td.KDA div.KDARatio span.KDARatio").text(),":")))
                .ckRate(document.select("table.GameAverageStats td.KDA div.KDARatio span.CKRate span").text())
                .build();
        GameAverageStats gas = GameAverageStats.builder()
                .win(Integer.parseInt(document.select("table.GameAverageStats td.Title span.win").text()))
                .lose(Integer.parseInt(document.select("table.GameAverageStats td.Title span.lose").text()))
                .winRatio(document.select("table.GameAverageStats td.Summary div.Text").text())
                .kda(kda)
                .build();
        mostChampionElement.forEach(element -> {
            MostChampion champion = MostChampion.builder()
                    .image("Https:"+element.select("div.Image img").attr("src"))
                    .name(element.select("div.Name").text())
                    .win(Integer.parseInt(element.select("div.WonLose span.win").text()))
                    .lose(Integer.parseInt(element.select("div.WonLose span.lose").text()))
                    .kda(Double.parseDouble(element.select("div.KDA span").text()))
                    .winRatio(element.select("div.WonLose b").text())
                    .build();
            champions.add(champion);
        });
        //擅长的位置
        positionElement.forEach(element -> {
            if ("?".equals(element.select("div.Name").text())){
                return;
            }
            PreferredPosition position = PreferredPosition.builder()
                    .position(element.select("div.Name").text())
                    .roleRate(Integer.parseInt(element.select("span.RoleRate b").text()))
                    .winRatio(Integer.parseInt(element.select("span.WinRatio span b").text()))
                    .build();
            positions.add(position);
        });
        gameItemWrap.forEach(element -> {
            GameParams param = GameParams.builder()
                    .gameId(element.select("div.GameItem").attr("data-game-id"))
                    .summonerId(id)
                    .gameTime(Long.parseLong(element.select("div.GameItem").attr("data-game-time")))
                    .gameLength(element.select("div.GameLength").text())
                    .build();
            params.add(param);
        });
        summoner = new Summoner.Builder().name(name)
                .summonerId(id)
                .borderImage(borderImage)
                .lv(lv)
                .lp(lp)
                .team(team)
                .careerId(careerId)
                .ranking(rank)
                .tier(tier)
                .tierIcon(tierIcon)
                .totalWin(totalWin)
                .totalLose(totalLose)
                .winRatio(winRatio)
                .gas(gas)
                .champions(champions)
                .positions(positions)
                .params(params)
                .build();
        return summoner;
    }

    public static void main(String[] args) throws IOException {
        SummonerCraw summonerCraw = new SummonerCraw();
        String url = "https://www.op.gg/summoner/userName=";
        String name = "타 잔";
        log.info(summonerCraw.get(url,name).toString());

    }
}
