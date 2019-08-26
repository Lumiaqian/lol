package com.caoyuqian.lol.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author qian
 * @version V1.0
 * @Title: HttpUtil
 * @Package: com.caoyuqian.lol.utils
 * @Description: TOTO
 * @date 2019-08-22 10:50
 **/
public class HttpUtil {
    public static String USER_AGENT = "User-Agent";
    public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";
    public static Document get(String url) throws IOException {

        Connection connection = Jsoup.connect(url);
        connection.header(USER_AGENT,USER_AGENT_VALUE);
        connection.header("accept-language", "zh-cn");
        Connection.Response rs = connection.execute();
        return Jsoup.parse(rs.body());
    }
}
