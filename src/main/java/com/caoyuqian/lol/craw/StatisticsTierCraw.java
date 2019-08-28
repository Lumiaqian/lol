package com.caoyuqian.lol.craw;

import com.caoyuqian.lol.model.StatisticsTier;
import com.caoyuqian.lol.service.StatisticsTierService;
import com.caoyuqian.lol.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: StatisticsTierCraw
 * @Package: com.caoyuqian.lol.craw
 * @Description: TOTO
 * @date 2019-08-27 16:07
 **/
@Slf4j
@Component
public class StatisticsTierCraw {
    private String url = "https://www.op.gg/statistics/tier/";
    private Document document;
    public List<StatisticsTier> get() throws IOException {
        List<StatisticsTier> statisticsTiers = new ArrayList<>();
        document = HttpUtil.get(url);
        Elements elements = document.select("div.SideContent");
        Elements tr = elements.select("tbody.Content tr.Row");

        tr.forEach(element -> {
            Elements td = element.select("td");
            StatisticsTier statisticsTier = StatisticsTier.builder()
                    .level(StringUtils.strip(td.get(1).text()))
                    .numberOfSummoners(Integer.parseInt(StringUtils.substringBefore(StringUtils.strip(td.get(2).text().replaceAll(",","")),"(").replaceAll(" ","")))
                    .summonerPercentage(StringUtils.substringBetween(StringUtils.strip(td.get(2).text().replaceAll(",","")),"(",")"))
                    .build();
            statisticsTiers.add(statisticsTier);
        });
        log.info(statisticsTiers.toString());
        return statisticsTiers;
    }

}
