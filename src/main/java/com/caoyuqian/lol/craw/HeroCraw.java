package com.caoyuqian.lol.craw;

import com.caoyuqian.lol.entity.Hero;
import com.caoyuqian.lol.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HeroCraw {

    public List<Hero> craw(String url) throws IOException {
        String urls = "https://www.op.gg/champion/statistics";
        //辅助
        List<Hero> sups = crawSupportHero(url);
        //adc
        List<Hero> adcs = crawAdcHero(url);
        //打野
        List<Hero> jugs = crawJungleHero(url);
        //中单
        List<Hero> mids = crawMidHero(url);
        //上单
        List<Hero> tops = crawTopHero(url);
        sups.removeAll(adcs);
        sups.addAll(adcs);

        sups.removeAll(jugs);
        sups.addAll(jugs);

        sups.removeAll(mids);
        sups.addAll(mids);

        sups.removeAll(tops);
        sups.addAll(tops);
        return sups;
    }

    public static void main(String[] args) throws IOException {
        String url = "https://www.op.gg/statistics/champion/";
        Document document = HttpUtil.get(url);
        Element select = document.getElementById("ChampionStatsTable");
        System.out.println(select);

    }


    /**
     * 爬取所有辅助英雄
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static List<Hero> crawSupportHero(String url) throws IOException {
        Document document = HttpUtil.get(url);
        List<Hero> heroes = new ArrayList<>();

        Element tbody = document.select("tbody.champion-trend-tier-SUPPORT").get(0);
        Elements nameEs = tbody.select("div.champion-index-table__name");
        Elements posEs = tbody.select("div.champion-index-table__position");

        for (int i = 0; i < nameEs.size(); i++) {
            Element element = nameEs.get(i);
            String name = element.text();
            Element pose = posEs.get(i);
            String pos = pose.text();
            String[] poss = pos.split(", ");

            Hero hero = new Hero();
            hero.setHName(name);
            for (int j = 0; j < poss.length; j++
            ) {
                if (poss[j].equals("辅助")) {
                    hero.setAssist(true);
                } else if (poss[j].equals("Bottom")) {
                    hero.setAd(true);
                } else if (poss[j].equals("中单")) {
                    hero.setMid(true);
                } else if (poss[j].equals("打野")) {
                    hero.setJungle(true);
                } else if (poss[j].equals("上单")) {
                    hero.setTop(true);
                }
            }
            heroes.add(hero);
        }
        return heroes;
    }

    /**
     * 爬取所有ADC英雄
     *
     * @param url
     * @return
     */
    public static List<Hero> crawAdcHero(String url) throws IOException {
        Document document = HttpUtil.get(url);
        List<Hero> heroes = new ArrayList<>();

        Element adcEs = document.select("tbody.champion-trend-tier-ADC").get(0);
        Elements nameEs = adcEs.select("div.champion-index-table__name");
        Elements posEs = adcEs.select("div.champion-index-table__position");

        for (int i = 0; i < nameEs.size(); i++) {
            Element element = nameEs.get(i);
            String name = element.text();
            Element pose = posEs.get(i);
            String pos = pose.text();
            String[] poss = pos.split(", ");

            Hero hero = new Hero();
            hero.setHName(name);
            for (int j = 0; j < poss.length; j++
            ) {
                if (poss[j].equals("辅助")) {
                    hero.setAssist(true);
                } else if (poss[j].equals("Bottom")) {
                    hero.setAd(true);
                } else if (poss[j].equals("中单")) {
                    hero.setMid(true);
                } else if (poss[j].equals("打野")) {
                    hero.setJungle(true);
                } else if (poss[j].equals("上单")) {
                    hero.setTop(true);
                }
            }
            heroes.add(hero);
        }
        return heroes;
    }

    /**
     * 爬取所有中单英雄
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static List<Hero> crawMidHero(String url) throws IOException {

        Document document = HttpUtil.get(url);
        List<Hero> heroes = new ArrayList<>();

        //爬取中单位置的英雄
        Element midEs = document.select("tbody.champion-trend-tier-MID").get(0);
        Elements nameEs = midEs.select("div.champion-index-table__name");
        Elements posEs = midEs.select("div.champion-index-table__position");

        for (int i = 0; i < nameEs.size(); i++) {
            Element element = nameEs.get(i);
            String name = element.text();
            Element pose = posEs.get(i);
            String pos = pose.text();
            String[] poss = pos.split(", ");

            Hero hero = new Hero();
            hero.setHName(name);
            for (int j = 0; j < poss.length; j++
            ) {
                if (poss[j].equals("辅助")) {
                    hero.setAssist(true);
                } else if (poss[j].equals("Bottom")) {
                    hero.setAd(true);
                } else if (poss[j].equals("中单")) {
                    hero.setMid(true);
                } else if (poss[j].equals("打野")) {
                    hero.setJungle(true);
                } else if (poss[j].equals("上单")) {
                    hero.setTop(true);
                }
            }
            heroes.add(hero);
        }
        return heroes;
    }

    /**
     * 爬取所有打野英雄
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static List<Hero> crawJungleHero(String url) throws IOException {
        Document document = HttpUtil.get(url);
        List<Hero> heroes = new ArrayList<>();
        //爬取打野位置的英雄
        Element junglesE = document.select("tbody.champion-trend-tier-JUNGLE").get(0);
        Elements nameEs = junglesE.select("div.champion-index-table__name");
        Elements posEs = junglesE.select("div.champion-index-table__position");

        for (int i = 0; i < nameEs.size(); i++) {
            Element element = nameEs.get(i);
            String name = element.text();
            Element pose = posEs.get(i);
            String pos = pose.text();
            String[] poss = pos.split(", ");

            Hero hero = new Hero();
            hero.setHName(name);
            for (int j = 0; j < poss.length; j++
            ) {
                if (poss[j].equals("辅助")) {
                    hero.setAssist(true);
                } else if (poss[j].equals("Bottom")) {
                    hero.setAd(true);
                } else if (poss[j].equals("中单")) {
                    hero.setMid(true);
                } else if (poss[j].equals("打野")) {
                    hero.setJungle(true);
                } else if (poss[j].equals("上单")) {
                    hero.setTop(true);
                }
            }
            heroes.add(hero);
        }
        return heroes;
    }

    /**
     * 爬取所有上单位置的英雄
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static List<Hero> crawTopHero(String url) throws IOException {
        Document document = HttpUtil.get(url);
        List<Hero> heroes = new ArrayList<>();
        //上单英雄
        Elements select = document.select("tbody.champion-trend-tier-TOP");
        Element tope = select.get(0);
        Elements nameEs = tope.select("div.champion-index-table__name");
        Elements posEs = tope.select("div.champion-index-table__position");

        for (int i = 0; i < nameEs.size(); i++) {
            Element element = nameEs.get(i);
            String name = element.text();
            Element pose = posEs.get(i);
            String pos = pose.text();
            String[] poss = pos.split(", ");

            Hero hero = new Hero();
            hero.setHName(name);
            for (int j = 0; j < poss.length; j++
            ) {
                if (poss[j].equals("辅助")) {
                    hero.setAssist(true);
                } else if (poss[j].equals("Bottom")) {
                    hero.setAd(true);
                } else if (poss[j].equals("中单")) {
                    hero.setMid(true);
                } else if (poss[j].equals("打野")) {
                    hero.setJungle(true);
                } else if (poss[j].equals("上单")) {
                    hero.setTop(true);
                }
            }
            heroes.add(hero);
        }
        return heroes;
    }

    /**
     * 爬取英雄名称
     *
     * @return
     * @throws IOException
     */
    public static List<String> crawHeroName(String url) throws IOException {
        Document document = HttpUtil.get(url);
        Elements namesElement = document.select("div.champion-index__champion-item__name");
        List<String> names = namesElement.stream().map(Element::text).collect(Collectors.toList());
        return names;
    }

    /**
     * 获取所有英雄详情页的url
     *
     * @return
     */
    public static List<String> getAllHeroDetilUrl() throws IOException {
        List<String> urls = new ArrayList<>();
        String baseUrl = "https://www.op.gg";
        Document document = HttpUtil.get("https://www.op.gg/champion/statistics");
        Elements lists = document.getElementsByClass("champion-index__champion-list");
        Element list = lists.get(0);
        Elements a = list.getElementsByTag("a");

        for (Element e : a
        ) {
            String href = e.attr("href");
            String url = baseUrl + href;
            urls.add(url);
        }
        return urls;
    }
}
