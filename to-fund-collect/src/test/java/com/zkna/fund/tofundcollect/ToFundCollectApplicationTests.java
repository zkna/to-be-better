package com.zkna.fund.tofundcollect;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.zkna.fund.tofundcollect.crawl.eastmoney.EastmoneyCompanyCrawl;
import com.zkna.fund.tofundcollect.crawl.eastmoney.EastmoneyFundCrawl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@SpringBootTest(classes = ToFundCollectApplication.class)
class ToFundCollectApplicationTests {

    @Autowired
    EastmoneyCompanyCrawl eastmoneyCompanyCrawl;

    @Autowired
    EastmoneyFundCrawl eastmoneyFundCrawl;

    @Test
    void contextLoads() throws IOException, ParseException, InterruptedException {
        eastmoneyCompanyCrawl.crawlCompanyOverview();
    }

    @Test
    void crawlCompanyList() throws Exception {
        eastmoneyCompanyCrawl.crawlCompanyList();
    }

    @Test
    void crawlFundList(){

    }

    @Test
    void downLoadBrowser() throws IOException, ExecutionException, InterruptedException {
        Puppeteer puppeteer = new Puppeteer();
        //创建下载实例
        BrowserFetcher browserFetcher = puppeteer.createBrowserFetcher();
        //下载最新版本的chromuim
        browserFetcher.download();
        Browser browser = Puppeteer.launch(false);
        String version = browser.version();
        System.out.println(version);
    }

}
