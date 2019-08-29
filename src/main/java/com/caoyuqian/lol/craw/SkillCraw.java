package com.caoyuqian.lol.craw;

import com.caoyuqian.lol.entity.Skill;
import com.caoyuqian.lol.utils.HttpUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SkillCraw {

    public List<Skill> crawSkill(){

        return null;
    }


    public static void main(String[] args) throws IOException {
        Document document = HttpUtil.get("https://www.op.gg/champion/neeko/statistics");
        Element skillE = document.getElementsByClass("champion-stats-header-info__skill").get(0);

        Element select = skillE.select("div").get(0);
//        System.out.println(select);
        System.out.println(select.attr("title"));

    }
}
