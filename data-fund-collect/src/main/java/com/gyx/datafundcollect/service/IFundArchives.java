package com.gyx.datafundcollect.service;

import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.page.Page;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface IFundArchives {

    public void crawlFundList(Browser browser,String url) throws IOException, InterruptedException;

    public void crawlFundArchives(Page browser, String fundCode, String url, String type) throws IOException, InterruptedException, ExecutionException;

    void crawlFundArchivesByAPI(String fundCode, String type);
}
