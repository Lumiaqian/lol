package com.caoyuqian.lol;

import com.caoyuqian.lol.craw.HeroCraw;
import com.caoyuqian.lol.craw.SummonerSkillCraw;
import com.caoyuqian.lol.entity.Hero;
import com.caoyuqian.lol.entity.SummonerSkill;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeroCrawTest {

    private final static Logger log = LoggerFactory.getLogger(HeroCrawTest.class);

    @Autowired
    private HeroCraw heroCraw;

    @Autowired
    private SummonerSkillCraw summonerSkillCraw;

    @Test
    public void hero() throws IOException {

        List<Hero> craw = heroCraw.craw();

        log.info(craw.toString());


    }

    @Test
    public void so() throws Exception{
        List<SummonerSkill> summonerSkills = summonerSkillCraw.craw();


        log.info(summonerSkills.toString());
    }

}
