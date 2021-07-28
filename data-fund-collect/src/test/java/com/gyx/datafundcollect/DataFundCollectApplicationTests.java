package com.gyx.datafundcollect;

import com.gyx.datafundcollect.service.IFundCompany;
import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class DataFundCollectApplicationTests {

    @Autowired
    IFundCompany iFundCompany;

    @Test
    void crawlCompanyOverview() throws IOException, InterruptedException {
        iFundCompany.crawlCompanyOverview();
    }
    @Test
    void crawlFundCompany() throws IOException, InterruptedException {
        iFundCompany.crawlFundCompany();
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
