package com.caoyuqian.lol.craw;

import com.caoyuqian.lol.entity.Ladder;
import com.caoyuqian.lol.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LadderCraw {
    private final static Logger log = LoggerFactory.getLogger(LadderCraw.class);
    /**
     * 爬取排行榜内容
     *
     * @param url
     * @param page
     * @return
     * @throws IOException
     */
    public List<Ladder> ladderCraw(String url, int page) throws IOException {
        String imgUrl = "Https:";
        //排名
        List<Integer> rankingList = new ArrayList<>();
        //召唤师名称
        List<String> nameList = new ArrayList<>();
        //段位
        List<String> levelList = new ArrayList<>();
        //分数
        List<String> lpList = new ArrayList<>();
        //等级
        List<String> lvList = new ArrayList<>();
        //胜率
        List<String> winRatioList = new ArrayList<>();
        //头像
        List<String> imgList = new ArrayList<>();
        //数据封装
        List<Ladder> ladders = new ArrayList<>();
        Document document = HttpUtil.get(url + page);
        //爬取前5名信息
        if (page == 1) {
            Elements elements = document.getElementsByClass("ranking-highest__item");
            Elements rankings = elements.select("div.ranking-highest__rank");
            Elements names = elements.select("a.ranking-highest__name");
            Elements levels = elements.select("div.ranking-highest__tierrank span");
            Elements lps = elements.select("div.ranking-highest__tierrank b");
            Elements winRatios = elements.select("span.winratio__text");
            Elements lvs = elements.select("div.ranking-highest__level");
            Elements imgs = elements.select("img.ranking-highest__image");

            rankingList = rankings.stream().map(element -> Integer.parseInt(element.text())).collect(Collectors.toList());
            nameList = names.stream().map(Element::text).collect(Collectors.toList());
            levelList = levels.stream().map(Element::text).collect(Collectors.toList());
            lpList = lps.stream().map(element -> {
                return StringUtils.substringBefore(element.text(), " LP").replaceAll(",", "");
            }).collect(Collectors.toList());
            winRatioList = winRatios.stream().map(Element::text).collect(Collectors.toList());
            lvList = lvs.stream().map(element -> {
                if (element.text().startsWith("Lv.")) {
                    return StringUtils.substringAfter(element.text(), "Lv.");
                }
                return element.text();
            }).collect(Collectors.toList());
            imgList = imgs.stream().map(img -> imgUrl+img.attr("src")).collect(Collectors.toList());
            ladders = new ArrayList<>(rankingList.size());

            for (int i = 0; i < rankingList.size(); i++) {
                Ladder ladder = Ladder.builder().name(nameList.get(i))
                        .ranking(rankingList.get(i))
                        .tier(levelList.get(i))
                        .lp(Integer.parseInt(lpList.get(i)))
                        .lv(Integer.parseInt(lvList.get(i)))
                        .winRatio(winRatioList.get(i))
                        .borderImage(imgList.get(i))
                        .build();

                ladders.add(ladder);
            }
            log.info(ladders.size()+"条数据");
        }

        //爬取第6名之后的信息

        //爬取排名信息
        Elements eles = document.getElementsByClass("ranking-table__row");
        List<Ladder> finalLadders = ladders;
        // log.info(ladders.size()+"wei kai  shi ");
        eles.forEach(element -> {
            Ladder ladder = Ladder.builder()
                    .ranking(Integer.parseInt(element.select("td.ranking-table__cell--rank").text()))
                    .name(element.select("td.ranking-table__cell--summoner span").text()
                            .replace("<span>", "")
                            .replace("</span>", ""))
                    .tier(element.select("td.ranking-table__cell--tier").text())
                    .lp(Integer.parseInt(StringUtils.substringBefore(element.select("td.ranking-table__cell--lp").text(),
                            " LP").replaceAll(",", "")))
                    .lv(Integer.parseInt(element.select("td.ranking-table__cell--level").text()))
                    .winRatio(element.select("td.ranking-table__cell--winratio span").text())
                    .borderImage(imgUrl+element.select("td.ranking-table__cell--summoner img").attr("src"))
                    .build();
            finalLadders.add(ladder);
            // log.info(finalLadders.size()+" 一爬取");
        });

        log.info(ladders.toString());

        return ladders;
    }

}

