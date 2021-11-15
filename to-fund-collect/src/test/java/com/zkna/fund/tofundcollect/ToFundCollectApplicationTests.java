package com.zkna.tofundcollect;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import com.zkna.tofundcollect.crawl.FundCompanyCrawl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest(classes = ToFundCollectApplication.class)
class ToFundCollectApplicationTests {


    @Autowired
    FundCompanyCrawl fundCompanyCrawl;

    @Test
    void contextLoads() throws IOException, ParseException, InterruptedException {
        fundCompanyCrawl.crawlCompanyOverview();
    }

    @Test
    void crawlCompanyList() throws IOException, ParseException, InterruptedException {
        fundCompanyCrawl.crawlCompanyList();
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
