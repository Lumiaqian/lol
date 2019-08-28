package com.caoyuqian.lol.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

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
    public static Document getByHtmlUnit(String url) throws IOException {
        //请求超时时间,默认20秒
        int timeout = 20000;
        //等待异步JS执行时间,默认20秒
        int waitForBackgroundJavaScript = 20000;
        String result = "";

        final WebClient webClient = new WebClient(BrowserVersion.CHROME);

        //当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        //是否启用CSS
        webClient.getOptions().setCssEnabled(false);
        //很重要，启用JS
        webClient.getOptions().setJavaScriptEnabled(true);
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        //设置“浏览器”的请求超时时间
        webClient.getOptions().setTimeout(timeout);
        //设置JS执行的超时时间
        webClient.setJavaScriptTimeout(timeout);
        //设置请求头
        webClient.addRequestHeader("accept-language", "zh-cn");
        HtmlPage page;
        try {
            page = webClient.getPage(url);
        } catch (Exception e) {
            webClient.close();
            throw e;
        }
        //该方法阻塞线程
        webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);

        result = page.asXml();
        webClient.close();


        // Jsoup解析处理
        Document doc = Jsoup.parse(result, url);
        return doc;
    }
}
