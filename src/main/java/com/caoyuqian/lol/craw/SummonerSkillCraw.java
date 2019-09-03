package com.caoyuqian.lol.craw;

import com.caoyuqian.lol.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 召唤师技能爬取
 */
@Component
@Slf4j
public class SummonerSkillCraw {

    public static void main(String[] args) throws IOException {
        Document document = HttpUtil.get("https://lol.qq.com/data/info-spell.shtml#Navi");
        Element summonerBarrier = document.getElementById("SummonerBarrier");

        System.out.println(summonerBarrier.text());


    }

}
