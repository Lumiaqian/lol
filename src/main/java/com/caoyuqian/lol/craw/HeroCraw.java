package com.caoyuqian.lol.craw;

import com.caoyuqian.lol.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HeroCraw {

    public void craw(String url) throws IOException {
        Document document = HttpUtil.get(url);

        Elements list = document.select("champion-index__champion-list");
//        List<Elements> collect = list.stream().map(element -> {
//            return element.select("a[href]");
//
//        }).collect(Collectors.toList());


        System.out.println(list);




    }


}
