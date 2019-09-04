package com.caoyuqian.lol.craw;
import com.alibaba.fastjson.JSONObject;
import com.caoyuqian.lol.entity.Summoner;
import com.caoyuqian.lol.entity.SummonerSkill;
import com.caoyuqian.lol.entity.json.SummonerSkillOne;
import com.caoyuqian.lol.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 召唤师技能爬取
 */
@Component
public class SummonerSkillCraw {

    private final static Logger log = LoggerFactory.getLogger(SummonerSkillCraw.class);
    /**
     * 爬取召唤师技能
     * @return
     */
    public List<SummonerSkill> craw() throws IOException {
       String[] images = {"https://ossweb-img.qq.com/images/lol/img/spell/SummonerBarrier.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerBarrier.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerDot.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerExhaust.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerFlash.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerHaste.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerHeal.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerMana.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerPoroRecall.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerPoroThrow.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerSmite.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerSnowball.png",
               "https://ossweb-img.qq.com/images/lol/img/spell/SummonerTeleport.png",
       };

        List<SummonerSkill> summonerSkills = crawSummonerSkill();

       List<SummonerSkill> ss = new ArrayList<>();


        for (int i = 0; i < images.length ; i++) {
            SummonerSkill summonerSkill = summonerSkills.get(i);
            summonerSkill.setImageURL(images[i]);
            ss.add(summonerSkill);

        }
        System.out.println(ss);
        return ss;
    }


    public List<SummonerSkill> crawSummonerSkill() throws IOException {


        String url = "https://lol.qq.com/biz/hero/summoner.js";

        String connect = HttpUtil.connect(url);
        String replace = connect.replace("if(!LOLsummonerjs)var ", "");
        String replace1 = replace.replace("LOLsummonerjs=", "");
        String replace2 = replace1.replace(";", "");

        StringBuilder sb = new StringBuilder(replace2);
        StringBuilder replace3 = sb.replace(27, 28, "[");
        replace3 = sb.replace(1,28,"");
        replace3 = replace3.replace(replace3.length() - 25,replace3.length(),"");
        replace3 = replace3.insert(replace3.length(),"}}");
        replace3 = replace3.insert(0,"{\"data\" :");

        String re = replace3.toString();
        JSONObject jsonObject = JSONObject.parseObject(re);
        List<SummonerSkillOne> summonerSkillOnes = new ArrayList<>();
        JSONObject summonerBarrier = jsonObject.getJSONObject("data").getJSONObject("SummonerBarrier");
        JSONObject summonerBoost = jsonObject.getJSONObject("data").getJSONObject("SummonerBoost");
        JSONObject summonerDot = jsonObject.getJSONObject("data").getJSONObject("SummonerDot");
        JSONObject summonerExhaust = jsonObject.getJSONObject("data").getJSONObject("SummonerExhaust");
        JSONObject summonerFlash = jsonObject.getJSONObject("data").getJSONObject("SummonerFlash");
        JSONObject summonerHaste = jsonObject.getJSONObject("data").getJSONObject("SummonerHaste");
        JSONObject summonerHeal = jsonObject.getJSONObject("data").getJSONObject("SummonerHeal");
        JSONObject summonerMana = jsonObject.getJSONObject("data").getJSONObject("SummonerMana");
        JSONObject summonerPoroRecall = jsonObject.getJSONObject("data").getJSONObject("SummonerPoroRecall");
        JSONObject summonerPoroThrow = jsonObject.getJSONObject("data").getJSONObject("SummonerPoroThrow");
        JSONObject summonerSmite = jsonObject.getJSONObject("data").getJSONObject("SummonerSmite");
        JSONObject summonerSnowball = jsonObject.getJSONObject("data").getJSONObject("SummonerSnowball");
        JSONObject summonerTeleport = jsonObject.getJSONObject("data").getJSONObject("SummonerTeleport");

        SummonerSkillOne summonerSkillOne = summonerBarrier.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne1 = summonerBoost.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne2 = summonerDot.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne3 = summonerExhaust.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne4 = summonerFlash.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne5 = summonerHaste.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne6 = summonerHeal.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne7 = summonerMana.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne8 = summonerPoroRecall.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne9 = summonerPoroThrow.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne10 = summonerSmite.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne11 = summonerSnowball.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne12 = summonerSnowball.toJavaObject(SummonerSkillOne.class);
        SummonerSkillOne summonerSkillOne13 = summonerTeleport.toJavaObject(SummonerSkillOne.class);
        summonerSkillOnes.add(summonerSkillOne);
        summonerSkillOnes.add(summonerSkillOne1);
        summonerSkillOnes.add(summonerSkillOne2);
        summonerSkillOnes.add(summonerSkillOne3);
        summonerSkillOnes.add(summonerSkillOne4);
        summonerSkillOnes.add(summonerSkillOne5);
        summonerSkillOnes.add(summonerSkillOne6);
        summonerSkillOnes.add(summonerSkillOne7);
        summonerSkillOnes.add(summonerSkillOne8);
        summonerSkillOnes.add(summonerSkillOne9);
        summonerSkillOnes.add(summonerSkillOne10);
        summonerSkillOnes.add(summonerSkillOne11);
        summonerSkillOnes.add(summonerSkillOne12);
        summonerSkillOnes.add(summonerSkillOne13);


        List<SummonerSkill> summonerSkills  = new ArrayList<>();
        summonerSkillOnes.stream().forEach(s ->{
            SummonerSkill s1 = SummonerSkill.builder()
                    .sSname(s.getName())
                    .sScontent(s.getDescription())
                    .build();
            summonerSkills.add(s1);
        });

        return  summonerSkills;


    }

}
