package com.caoyuqian.lol.craw;

import com.caoyuqian.lol.entity.*;
import com.caoyuqian.lol.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
        long start = System.currentTimeMillis();
        Document document = HttpUtil.get(url + "gameId=" + params.getGameId()
                + "&summonerId=" + params.getSummonerId()
                + "&gameTime=" + params.getGameTime());
        //log.info(document.html());
        Elements winner = document.select("table.Result-WIN");
        Elements loser = document.select("table.Result-LOSE");
        Elements remake = document.select("table.Result-REMAKE");
        //胜利方
        List<Gamer> oneGamers = new ArrayList<>();
        //失败方
        List<Gamer> otherGamers = new ArrayList<>();
        //log.info(winner.select("th.HeaderCell").first().text());
        HashMap<String, Elements> map = new HashMap<>(16);


        Elements winMapData = document.select("div.Summary div.Result-WIN");
        Elements lossMapData = document.select("div.Summary div.summary-graph");
        GameMapData winData = new GameMapData();
        GameMapData lossData = new GameMapData();
        HashMap<String, Object> oneDir;
        HashMap<String, Object> otherDir;
        oneDir = new HashMap<>(16);
        otherDir = new HashMap<>(16);
        try {

            if (!remake.isEmpty()){
                //重开局的情况
                Elements one = remake.first().select("tbody.Content tr.Row");
                Elements other = remake.last().select("tbody.Content tr.Row");
                //log.info(one.html());
                oneGamers = getGamer(one);
                otherGamers = getGamer(other);
                oneDir.put("direction", StringUtils.substringBetween(remake.first().select("th.HeaderCell").first().text(), "(", ")"));
                otherDir.put("direction", StringUtils.substringBetween(remake.last().select("th.HeaderCell").first().text(), "(", ")"));
                oneDir.put("gamer", oneGamers);
                otherDir.put("gamer", otherGamers);

            }else {
                //非三分钟重开局才有团队数据

                    //胜利方团队数据
                    winData = GameMapData.builder()
                            .baron(Integer.parseInt(winMapData.select("div.ObjectScore").first().text()))
                            .dragon(Integer.parseInt(winMapData.select("div.ObjectScore").get(1).text()))
                            .tower(Integer.parseInt(winMapData.select("div.ObjectScore").last().text()))
                            .totalKill(Integer.parseInt(lossMapData.select("div.graph--data__left").first().text()))
                            .totalGold(Integer.parseInt(lossMapData.select("div.graph--data__left").get(1).text()))
                            .build();
                    //失利方团队数据
                    lossData = GameMapData.builder()
                            .baron(Integer.parseInt(document.select("div.Result-LOSE").select("div.ObjectScore").first().text()))
                            .dragon(Integer.parseInt(document.select("div.Result-LOSE").select("div.ObjectScore").get(1).text()))
                            .tower(Integer.parseInt(document.select("div.Result-LOSE").select("div.ObjectScore").last().text()))
                            .totalKill(Integer.parseInt(lossMapData.select("div.graph--data__right").first().text()))
                            .totalGold(Integer.parseInt(lossMapData.select("div.graph--data__right").get(1).text()))
                            .build();

                //胜利方数据
                Elements win = winner.select("tbody.Content tr.Row");
                //失利方数据
                Elements loss = loser.select("tbody.Content tr.Row");
                oneGamers = getGamer(win);
                otherGamers = getGamer(loss);
                oneDir.put("direction", StringUtils.substringBetween(winner.select("th.HeaderCell").first().text(), "(", ")"));
                otherDir.put("direction", StringUtils.substringBetween(loser.select("th.HeaderCell").first().text(), "(", ")"));
                oneDir.put("result","win");
                otherDir.put("result","loss");
                oneDir.put("gamer", oneGamers);
                otherDir.put("gamer", otherGamers);
            }


        } catch (Exception e) {
            log.error("出现异常：{}",e.getMessage());
            log.error(params.toString());
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        log.info("GameRecordCraw执行时间：{}毫秒",(end-start));
        return Game.builder()
                .gameType(params.getGameType())
                .one(oneDir)
                .other(otherDir)
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
            itemElements.select("div.noItem").remove();
            //log.info(itemElements.html());
            String itemsHtml = itemElements.html();
            List<String> itemHtmls = Arrays.asList(StringUtils.splitByWholeSeparator(itemsHtml, "<img"));
            //log.info(itemHtmls.toString());
            itemHtmls.forEach(h -> {
                h = StringUtils.substringBetween(h, "title=\"", "\">");
                Document d = Jsoup.parse(h);
                // log.info(d.html());
                HashMap<String, String> item = new HashMap<>(16);
//                item.put(,d.select("span").text());
                item.put("name", d.select("b").text());
                item.put("description", d.select("span").first().text());
                if (d.select("span").size()>1){
                    item.put("stats", d.select("span").get(1).text());
                    item.put("Cost", d.select("span").last().text());
                }
                items.add(item);
            });
            //log.info(element.select("td.Tier").attr("title"));
            int lp;
            if (element.select("td.Tier").attr("title").isEmpty()){
                log.info("没有排位分数");
                lp = 0;
            }else {
                //log.info(element.select("td.Tier").attr("title"));
                if (StringUtils.substringAfter(StringUtils.substringBetween(element.select("td.Tier").attr("title"), "<br>", " 胜点"),"<br>")==null){
                    lp = 0;
                }else {
                    lp = Integer.parseInt(StringUtils.substringAfter(StringUtils.substringBetween(element.select("td.Tier").attr("title"), "<br>", " 胜点"),"<br>"));
                }

            }
            //log.info();
            //极地大乱斗没有评分
            double op = 0.0;
            if (!element.select("td.OPScore div.Text").isEmpty()){
                op = Double.parseDouble(element.select("td.OPScore div.Text").text());
            }
            Gamer gamer = Gamer.builder()
                    .summonerSpells(summonerSpell)
                    .runes(rune)
                    .championName(element.select("td.ChampionImage div.tip").text())
                    .level(Integer.parseInt(element.select("td.ChampionImage div.Level").text()))
                    .summonerName(element.select("td.SummonerName a").text())
                    .tier(element.select("td.Tier").text())
                    .lp(lp)
                    .opScore(op)
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
        // www.op.gg/summoner/matches/ajax/detail/gameId=3845546634&summonerId=3323260&gameTime=1567615232
        GameParams params = GameParams.builder()
                .summonerId("3323260")
                .gameTime(1567615232)
                .gameId("3845546634")
                .gameLength("29分 12秒")
                .build();
        GameRecordCraw gameRecordCraw = new GameRecordCraw();
        Game game = gameRecordCraw.get(url, params);
        log.info(game.toString());
    }
}
