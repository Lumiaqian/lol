package com.caoyuqian.lol.craw;

import com.caoyuqian.lol.entity.Summoner;
import com.caoyuqian.lol.utils.HttpUtil;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qian
 * @version V1.0
 * @Title: SummonerCraw
 * @Package: com.caoyuqian.lol.craw
 * @Description: 爬取召唤师详细数据
 * @date 2019/9/2 6:58 下午
 **/
@Component
public class SummonerCraw {

    public List<Summoner> get(String url,String name,int start,int end) throws IOException {
        url = "https://www.op.gg/summoner/userName=" + name;
        List<Summoner> summoners = new ArrayList<>();
        Document document = HttpUtil.get(url);

        return summoners;
    }
}
