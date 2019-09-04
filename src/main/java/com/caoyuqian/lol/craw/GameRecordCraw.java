package com.caoyuqian.lol.craw;

import com.caoyuqian.lol.entity.*;
import com.caoyuqian.lol.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: GameRecordCraw
 * @Package: com.caoyuqian.lol.craw
 * @Description: 爬取游戏记录
 * @date 2019/9/3 4:04 下午
 **/
@Component
public class GameRecordCraw {
    private final static Logger log = LoggerFactory.getLogger(GameRecordCraw.class);

    public Game get(String url, GameParams params) throws IOException {
        Document document = HttpUtil.get(url + "gameId=" + params.getGameId()
                + "&summonerId=" + params.getSummonerId()
                + "&gameTime=" + params.getGameTime());
        //log.info(document.html());
        Elements winner = document.select("table.Result-WIN");
        Elements loser = document.select("table.Result-LOSE");
        //胜利方
        List<Gamer> winGamers = new ArrayList<>();
        //失败方
        List<Gamer> lossGamers = new ArrayList<>();
        //log.info(winner.select("th.HeaderCell").first().text());
        HashMap<String, Elements> map = new HashMap<>(16);
        //胜利方数据
        Elements win = winner.select("tbody.Content tr.Row");
        //失利方数据
        Elements loss = loser.select("tbody.Content tr.Row");

        Elements winMapData = document.select("div.Summary div.Result-WIN");
        Elements lossMapData = document.select("div.Summary div.summary-graph");
        //胜利方团队数据
        GameMapData winData = GameMapData.builder()
                .baron(Integer.parseInt(winMapData.select("div.ObjectScore").first().text()))
                .dragon(Integer.parseInt(winMapData.select("div.ObjectScore").get(1).text()))
                .tower(Integer.parseInt(winMapData.select("div.ObjectScore").last().text()))
                .totalKill(Integer.parseInt(lossMapData.select("div.graph--data__left").first().text()))
                .totalGold(Integer.parseInt(lossMapData.select("div.graph--data__left").get(1).text()))
                .build();
        //失利方团队数据
        GameMapData lossData = GameMapData.builder()
                .baron(Integer.parseInt(document.select("div.Result-LOSE").select("div.ObjectScore").first().text()))
                .dragon(Integer.parseInt(document.select("div.Result-LOSE").select("div.ObjectScore").get(1).text()))
                .tower(Integer.parseInt(document.select("div.Result-LOSE").select("div.ObjectScore").last().text()))
                .totalKill(Integer.parseInt(lossMapData.select("div.graph--data__right").first().text()))
                .totalGold(Integer.parseInt(lossMapData.select("div.graph--data__right").get(1).text()))
                .build();
        winGamers = getGamer(win);
        lossGamers = getGamer(loss);
        HashMap<String, Object> winDir = new HashMap<>(16);
        HashMap<String, Object> lossDir = new HashMap<>(16);
        winDir.put("direction", StringUtils.substringBetween(winner.select("th.HeaderCell").first().text(), "(", ")"));
        lossDir.put("direction", StringUtils.substringBetween(loser.select("th.HeaderCell").first().text(), "(", ")"));
        winDir.put("gamer", winGamers);
        lossDir.put("gamer", lossGamers);


        return Game.builder()
                .win(winDir)
                .loss(lossDir)
                .gameId(params.getGameId())
                .gameLength(params.getGameLength())
                .loseData(lossData)
                .winData(winData)
                .build();
    }

    private List<Gamer> getGamer(Elements elements) {
        List<Gamer> gamers = new ArrayList<>();
        elements.forEach(element -> {
            String summonerSpellHtml = element.select("td.SummonerSpell").html();
            String runesHtml = element.select("td.Rune").html();
            List<String> runesHtmls = Arrays.asList(StringUtils.splitByWholeSeparator(runesHtml, "<img"));
            List<String> summonerSpellHtmls = Arrays.asList(StringUtils.splitByWholeSeparator(summonerSpellHtml, "<img"));

            //爬取召唤师技能信息
            HashMap<String, String> summonerSpell = new HashMap<>(16);
            summonerSpellHtmls.forEach(h -> {
                h = StringUtils.substringBetween(h, "title=\"", "\">");
                Document d = Jsoup.parse(h);
                summonerSpell.put(d.select("b").text(), d.select("span").text());

            });
            //爬取天赋信息
            HashMap<String, String> rune = new HashMap<>(16);
            runesHtmls.forEach(h -> {
                h = StringUtils.substringBetween(h, "title=\"", "\">");
                Document d = Jsoup.parse(h);

                rune.put(d.select("b").text(), d.select("span").text())
                ;
            });
            //爬取KDA信息
            Kda kda = Kda.builder()
                    .kill(Double.parseDouble(element.select("td.KDA div.KDA span.Kill").text()))
                    .death(Double.parseDouble(element.select("td.KDA div.KDA span.Death").text()))
                    .assist(Double.parseDouble(element.select("td.KDA div.KDA span.Assist").text()))
                    .ckRate(StringUtils.substringBetween(element.select("td.KDA div.KDA span.CKRate").text(), "(", ")"))
                    .build();
//            log.info(element.select("td.Damage").attr("title").replaceAll(",","").replaceAll(" ",""));
            // 爬取视野信息
            ControlWard controlWard = ControlWard.builder()
                    .buy(Integer.parseInt(element.select("td.Ward div.Buy span").text()))
                    .place(Integer.parseInt(element.select("td.Ward div.Stats span").first().text()))
                    .clear(Integer.parseInt(element.select("td.Ward div.Stats span").last().text()))
                    .build();
            //爬取装备
            List<HashMap> items = new ArrayList<>();
            Elements itemElements = element.select("td.Items div.Item");
            String itemsHtml = itemElements.html();
            List<String> itemHtmls = Arrays.asList(StringUtils.splitByWholeSeparator(itemsHtml, "<img"));
            itemHtmls.forEach(h -> {
                h = StringUtils.substringBetween(h, "title=\"", "\">");
                Document d = Jsoup.parse(h);
                // log.info(d.html());
                HashMap<String, String> item = new HashMap<>(16);
//                item.put(,d.select("span").text());
                item.put("name", d.select("b").text());
                item.put("description", d.select("span").first().text());
                item.put("stats", d.select("span").get(1).text());
                item.put("Cost", d.select("span").last().text());
                items.add(item);
            });
            // log.info(items.toString());
            Gamer gamer = Gamer.builder()
                    .summonerSpells(summonerSpell)
                    .runes(rune)
                    .championName(element.select("td.ChampionImage div.tip").text())
                    .level(Integer.parseInt(element.select("td.ChampionImage div.Level").text()))
                    .summonerName(element.select("td.SummonerName a").text())
                    .tier(element.select("td.Tier").text())
                    .lp(Integer.parseInt(StringUtils.substringBetween(element.select("td.Tier").attr("title"), "r<br>", " 胜点")))
                    .opScore(Double.parseDouble(element.select("td.OPScore div.Text").text()))
                    .kda(kda)
                    .championInjury(Integer.parseInt(StringUtils.substringBetween(element.select("td.Damage").attr("title").replaceAll(",", "").replaceAll(" ", ""), "伤害：", "<br>")))
                    .championDamage(Integer.parseInt(StringUtils.substringAfter(element.select("td.Damage").attr("title").replaceAll(",", "").replaceAll(" ", ""), "总额:")))
                    .controlWard(controlWard)
                    .cs(Integer.parseInt(element.select("td.CS div.CS").text()))
                    .csPerMinute(Double.parseDouble(StringUtils.substringBetween(element.select("td.CS div.CSPerMinute").text(), " ", " ")))
                    .items(items)
                    .build();
            gamers.add(gamer);
        });
        return gamers;
    }

    public static void main(String[] args) throws IOException {
        String url = "https://www.op.gg/summoner/matches/ajax/detail/";
        // www.op.gg/summoner/matches/ajax/detail/gameId=3835331879&summonerId=26670323&gameTime=1567103260
        GameParams params = GameParams.builder()
                .summonerId("26670323")
                .gameTime(1567103260)
                .gameId("3835331879")
                .gameLength("20分 12秒")
                .build();
        GameRecordCraw gameRecordCraw = new GameRecordCraw();
        Game game = gameRecordCraw.get(url, params);
        log.info(game.toString());
    }
}
