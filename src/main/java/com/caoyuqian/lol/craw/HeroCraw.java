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

    public void craw(String url) throws IOException {


    }

    public static void main(String[] args) throws IOException {
//        String url = "https://www.op.gg/champion/aatrox/statistics";
//        Document document = HttpUtil.get(url);
//        Elements names = document.getElementsByClass("champion-stats-header-info__name");
//        System.out.println(names);
//        Elements jns = document.getElementsByClass("champion-stat__skill tip");
//        System.out.println(jns);

    }

    /**
     * 爬取所有辅助英雄
     * @param url
     * @return
     * @throws IOException
     */
    public List<Hero> crawSupportHero(String url) throws IOException {
        Document document = HttpUtil.get(url);
        List<Hero> heroes = new ArrayList<>();
        Element supE = document.select("tbody.champion-trend-tier-SUPPORT").get(0);
        Elements supes = supE.select("div.champion-index-table__name");

        List<String> sups = supes.stream().map(Element::text).collect(Collectors.toList());

        for (String s: sups
        ) {
            Hero hero = new Hero();
            hero.setHName(s);
            hero.setAssist(true);
            heroes.add(hero);
        }
        return heroes;
    }
    /**
     * 爬取所有ADC英雄
     * @param url
     * @return
     */
    public List<Hero> crawAdcHero(String url) throws IOException {
        Document document = HttpUtil.get(url);
        List<Hero> heroes = new ArrayList<>();

        Element adcEs = document.select("tbody.champion-trend-tier-ADC").get(0);
        Elements adcE = adcEs.select("div.champion-index-table__name");
        List<String> adcs = adcE.stream().map(Element::text).collect(Collectors.toList());

        for (String s :adcs
        ) {
            Hero hero = new Hero();
            hero.setHName(s);
            hero.setAd(true);
            heroes.add(hero);
        }
        return heroes;
    }
    /**
     * 爬取所有中单英雄
     * @param url
     * @return
     * @throws IOException
     */
    public List<Hero> crawMidHero(String url) throws IOException {

        Document document = HttpUtil.get(url);
        List<Hero> heroes = new ArrayList<>();

        //爬取中单位置的英雄
        Elements midEs = document.select("tbody.champion-trend-tier-MID");
        Element mide = midEs.get(0);
        Elements mides = mide.select("div.champion-index-table__name");
        List<String> mids = mides.stream().map(Element::text).collect(Collectors.toList());

        for (String s: mids
        ) {
            Hero hero = new Hero();
            hero.setHName(s);
            hero.setMid(true);
            heroes.add(hero);

        }
        return heroes;
    }
    /**
     * 爬取所有打野英雄
     * @param url
     * @return
     * @throws IOException
     */
    public List<Hero> crawJungleHero(String url) throws IOException {
        Document document = HttpUtil.get(url);
        List<Hero> heroes = new ArrayList<>();
        //爬取打野位置的英雄
        Elements junglesE = document.select("tbody.champion-trend-tier-JUNGLE");
        Element tbody_j = junglesE.get(0);
        Elements jnames = tbody_j.select("div.champion-index-table__name");
        List<String> jugs = jnames.stream().map(Element::text).collect(Collectors.toList());
        for (String s : jugs
        ) {
            Hero hero = new Hero();
            hero.setHName(s);
            hero.setJungle(true);
            heroes.add(hero);
        }
        return heroes;
    }
    /**
     * 爬取所有上单位置的英雄
     * @param url
     * @return
     * @throws IOException
     */
    public List<Hero> crawTopHero(String url) throws IOException {
        Document document = HttpUtil.get(url);
        List<Hero> heroes = new ArrayList<>();
        //上单英雄
        Elements select = document.select("tbody.champion-trend-tier-TOP");
        Element element = select.get(0);
        //爬取英雄名称
        Elements select1 = element.select("div.champion-index-table__name");
        List<String> tops = select1.stream().map(Element::text).collect(Collectors.toList());
        for (String name: tops
        ) {
            Hero hero = new Hero();
            hero.setHName(name);
            hero.setTop(true);
            heroes.add(hero);
        }
        return heroes;
    }
    /**
     * 爬取英雄名称
     * @return
     * @throws IOException
     */
    public List<String> crawHeroName(String url) throws IOException {
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
    public List<String> getAllHeroDetilUrl() throws IOException {
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
