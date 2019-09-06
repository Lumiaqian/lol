package com.caoyuqian.lol.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

/**
 * @author qian
 * @version V1.0
 * @Title: HttpUtil
 * @Package: com.caoyuqian.lol.utils
 * @Description: TOTO
 * @date 2019-08-22 10:50
 **/
public class HttpUtil {
    public static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    public static String USER_AGENT = "User-Agent";
    public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";
    public static Document get(String url) throws IOException {

        Connection connection = Jsoup.connect(url);
        connection.header(USER_AGENT,USER_AGENT_VALUE);
        connection.header("accept-language", "zh-cn")
        .timeout(50000);
        return connection.get();
    }
    public static Document getByHtmlUnit(String url) throws IOException {
        long start = System.currentTimeMillis();
        //请求超时时间,默认2秒
        int timeout = 2000;
        //等待异步JS执行时间,默认0.5秒
        int waitForBackgroundJavaScript = 500;
        String result = "";

        //设置日志级别，原页面js异常不打印
        // LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
                .setLevel(Level.OFF);

        java.util.logging.Logger.getLogger("org.apache.commons.httpclient")
                .setLevel(Level.OFF);


        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);

        //当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        //是否启用CSS
        webClient.getOptions().setCssEnabled(false);
        //很重要，启用JS
        webClient.getOptions().setJavaScriptEnabled(false);
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        //设置“浏览器”的请求超时时间
        webClient.getOptions().setTimeout(timeout);
        //设置JS执行的超时时间
        webClient.setJavaScriptTimeout(timeout);
        //设置请求头
        webClient.addRequestHeader("accept-language", "zh-cn");
        //设置SSL
        webClient.getOptions().setUseInsecureSSL(true);
        HtmlPage page;
        try {
            page = webClient.getPage(url);
        } catch (Exception e) {
            webClient.close();
            throw e;
        }
        //该方法阻塞线程
        webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
        long end = System.currentTimeMillis();

        result = page.asXml();
        webClient.close();
        log.info("WebClient执行时间：{}秒",(end-start)/1000);

        // Jsoup解析处理
        return Jsoup.parse(result, url);
    }

    public static String connect(String url) throws IOException {
        Connection connection = Jsoup.connect(url).ignoreContentType(true);
        connection.header("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
        connection.header("accept","*/*");
        connection.header("accept-language","zh-CN,zh;q=0.9");
        connection.header("referer","https://lol.qq.com/data/info-spell.shtml");
        byte[] bytes = connection.execute().bodyAsBytes();
        return new String(bytes,"UTF8");
    }



}
