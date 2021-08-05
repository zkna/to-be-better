package com.gyx.datafundcollect;

import com.gyx.datafundcollect.service.IFundArchives;
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

    @Autowired
    IFundArchives iFundArchives;

    @Test
    void crawlCompanyOverview() throws IOException, InterruptedException {
        iFundCompany.crawlCompanyOverview();
    }
    @Test
    void crawlFundCompany() throws IOException, InterruptedException {
        iFundCompany.crawlCompanyList();
    }
    @Test
    void crawlFundArchives() throws IOException, InterruptedException, ExecutionException {
//        iFundArchives.crawlFundList("https://fund.eastmoney.com//Company/80672691.html");
//        iFundArchives.crawlFundArchives("502003","http://fundf10.eastmoney.com/jjjz_502003.html","开放式基金");
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
