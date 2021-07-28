package com.gyx.datafundcollect.service.impl;

import com.gyx.datafundcollect.service.IFundArchives;
import com.gyx.entity.fund.FundArchives;
import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FundArchivesService implements IFundArchives {



    /**
     * 爬取T-1净值
     */
    public void crawlTb1NetFundsVal() throws IOException, InterruptedException {
        List<String> argList = new ArrayList<String>();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false).build /*withExecutablePath(path).*/();
        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        page.goTo("https://fund.eastmoney.com/Company/80000229.html");
        List<ElementHandle> divs = page.$$("body > div.outer_all > div.ttjj-grid-row > div.main-content.ttjj-grid-21 > div.third-block");
        for (ElementHandle div : divs) {
            String h3 = div.$eval("h3","v => v.textContent",new ArrayList()).toString();
            List<ElementHandle> trs = div.$$("table > tbody > tr");
            if("开放式基金".equals(h3)){
                for(ElementHandle tr : trs){
                    FundArchives fa = new FundArchives();
                    fa.setFundConcept(h3);
                    fa.setFundCode(tr.$eval("td.fund-name-code > a.code","v => v.textContent",new ArrayList()).toString());
                    fa.setFundName(tr.$eval("td.fund-name-code > a.name","v => v.textContent",new ArrayList()).toString());
                    fa.setFundBaUrl(tr.$eval("td:nth-child(2) > a:nth-child(1)","v => v.attributes['href'].nodeValue",new ArrayList()).toString());
                    fa.setFundArchiveUrl(tr.$eval("td:nth-child(2) > a:nth-child(2)","v => v.attributes['href'].nodeValue",new ArrayList()).toString());
                    fa.setFundType(tr.$eval("td:nth-child(3)","v => v.textContent",new ArrayList()).toString());
                    //                fa.setFundDate(Date(tr.$eval("td:nth-child(4)","v => v.textContent",new ArrayList()).toString()));
                    fa.setNetVal(tr.$eval("td:nth-child(5)","v => v.textContent",new ArrayList()).toString());
                    fa.setAccumulatedNetVal(tr.$eval("td:nth-child(6)","v => v.textContent",new ArrayList()).toString());
                    fa.setDailyGrowthRate(tr.$eval("td:nth-child(7)","v => v.textContent",new ArrayList()).toString());
                    fa.setFundScale(tr.$eval("td:nth-child(10)","v => v.textContent",new ArrayList()).toString());
                    fa.setFundManager(tr.$eval("td:nth-child(11)","v => v.textContent",new ArrayList()).toString());
                    //                fa.setFundManagerUrl(tr.$eval("td:nth-child(11)","v => v.attributes['href'].nodeValue",new ArrayList()).toString());
                    fa.setSubscriptionStatus(tr.$eval("td:nth-child(12)","v => v.textContent",new ArrayList()).toString());
                    fa.setHandFee(tr.$eval("td:nth-child(13)","v => v.textContent",new ArrayList()).toString());
                    //                fa.purchase =tr.$eval("td:nth-child(14)","v => v.attributes['title'].nodeValue",new ArrayList()).toString()
                }
            }else if(h3 == "货币/理财型基金"){
                for(ElementHandle tr : trs){
                    FundArchives fa = new FundArchives();
                    fa.setFundConcept(h3);
                    fa.setFundCode(tr.$eval("td.fund-name-code > a.code","v => v.textContent",new ArrayList()).toString());
                    fa.setFundName(tr.$eval("td.fund-name-code > a.name","v => v.textContent",new ArrayList()).toString());
                    fa.setFundBaUrl(tr.$eval("td:nth-child(2) > a:nth-child(1)","v => v.attributes['href'].nodeValue",new ArrayList()).toString());
                    fa.setFundArchiveUrl(tr.$eval("td:nth-child(2) > a:nth-child(2)","v => v.attributes['href'].nodeValue",new ArrayList()).toString());
                    fa.setFundType(tr.$eval("td:nth-child(3)","v => v.textContent",new ArrayList()).toString());
                    //                fa.setFundDate(ate(tr.$eval("td:nth-child(4)","v => v.textContent",new ArrayList()).toString()));
                    //万分收益
                    fa.setNetVal(tr.$eval("td:nth-child(5)","v => v.textContent",new ArrayList()).toString());
                    fa.setDailyGrowthRate(tr.$eval("td:nth-child(6)","v => v.textContent",new ArrayList()).toString());
                    fa.setFundScale(tr.$eval("td:nth-child(10)","v => v.textContent",new ArrayList()).toString());
                    fa.setFundManager(tr.$eval("td:nth-child(11)","v => v.textContent",new ArrayList()).toString());
                    //                fa.setFundManagerUrl(r.$eval("td:nth-child(11)","v => v.attributes['href'].nodeValue",new ArrayList()).toString());
                    fa.setSubscriptionStatus(tr.$eval("td:nth-child(12)","v => v.textContent",new ArrayList()).toString());
                    fa.setHandFee(tr.$eval("td:nth-child(13)","v => v.textContent",new ArrayList()).toString());
                    //                fa.setPurchase($eval("td:nth-child(14)","v => v.attributes['title'].nodeValue",new ArrayList()).toString());
                }
            }else if(h3 == "场内基金"){
                for(ElementHandle tr : trs){
                    FundArchives fa = new FundArchives();
                    fa.setFundConcept(h3);
                    fa.setFundCode(tr.$eval("td.fund-name-code > a.code","v => v.textContent",new ArrayList()).toString());
                    fa.setFundName(tr.$eval("td.fund-name-code > a.name","v => v.textContent",new ArrayList()).toString());
                    fa.setFundBaUrl(tr.$eval("td:nth-child(2) > a:nth-child(1)","v => v.attributes['href'].nodeValue",new ArrayList()).toString());
                    fa.setFundArchiveUrl(tr.$eval("td:nth-child(2) > a:nth-child(2)","v => v.attributes['href'].nodeValue",new ArrayList()).toString());
                    fa.setFundType(tr.$eval("td:nth-child(3)","v => v.textContent",new ArrayList()).toString());
                    //                fa.setFundDate(Date(tr.$eval("td:nth-child(4)","v => v.textContent",new ArrayList()).toString()));
                    fa.setNetVal(tr.$eval("td:nth-child(5)","v => v.textContent",new ArrayList()).toString());
                    fa.setAccumulatedNetVal(tr.$eval("td:nth-child(6)","v => v.textContent",new ArrayList()).toString());
                    fa.setDailyGrowthRate(tr.$eval("td:nth-child(7)","v => v.textContent",new ArrayList()).toString());
                    fa.setMarketVal(tr.$eval("td:nth-child(8)","v => v.textContent",new ArrayList()).toString());
                    fa.setHairCut(tr.$eval("td:nth-child(9)","v => v.textContent",new ArrayList()).toString());
                    fa.setFundScale(tr.$eval("td:nth-child(10)","v => v.textContent",new ArrayList()).toString());
                    fa.setFundManager(tr.$eval("td:nth-child(11)","v => v.textContent",new ArrayList()).toString());
                    //                fa.fundManagerUrl = tr.$eval("td:nth-child(11)","v => v.attributes['href'].nodeValue",new ArrayList()).toString()
                }
            }
        }
        browser.close();
    }


}
