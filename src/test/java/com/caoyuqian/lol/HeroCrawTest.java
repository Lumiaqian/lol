package com.caoyuqian.lol;

import com.caoyuqian.lol.craw.HeroCraw;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HeroCrawTest {

    @Autowired
    private HeroCraw heroCraw;

    @Test
    public void hero() throws IOException {

        heroCraw.craw("https://www.op.gg/champion/statistics");

    }

}
