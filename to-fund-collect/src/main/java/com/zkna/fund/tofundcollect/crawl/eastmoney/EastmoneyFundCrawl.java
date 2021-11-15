package com.zkna.fund.tofundcollect.crawl.eastmoney;

import cn.hutool.core.util.StrUtil;
import com.gyx.entity.fund.eastmoney.EastmoneyCompanyTO;
import com.gyx.entity.fund.eastmoney.EastmoneyFundTO;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import com.zkna.fund.tofundcollect.producer.IEastmoneyFundListProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import static com.gyx.entity.common.FundConstant.COMPANY_DETAIL;

@Component
@Slf4j
public class EastmoneyFundCrawl {

    @Autowired
    private IEastmoneyFundListProducer iEastmoneyFundListProducer;

    AtomicLong al = new AtomicLong(0);

    public void doFundList(CountDownLatch cdl, Browser browser, EastmoneyCompanyTO eastmoneyCompanyTO) throws Exception {
        String url = COMPANY_DETAIL+ eastmoneyCompanyTO.getCompanyDetail();
        Page page = browser.newPage();
        page.goTo(url);
        page.keyboard().down("PageDown",null);
        Thread.sleep(1000);
        page.keyboard().down("PageDown",null);
        List<ElementHandle> divs = page.$$("body > div.outer_all > div.ttjj-grid-row > div.main-content.ttjj-grid-21 > div.third-block");
        for (ElementHandle div : divs) {
            log.info("al:"+al.get());
            String h3 = div.$eval("h3", "v => v.textContent", new ArrayList()).toString();
            List<ElementHandle> trs = div.$$("table > tbody > tr");
            if ("开放式基金".equals(h3)) {
                for (ElementHandle tr : trs) {
                    EastmoneyFundTO eastmoneyFundTO = new EastmoneyFundTO();
                    eastmoneyFundTO.setFundConcept(h3);
                    String content = StrUtil.trim(tr.$eval("td", "v => v.textContent", new ArrayList()).toString());
                    if ("暂无数据".equals(content)) {
                        continue;
                    }
                    eastmoneyFundTO.setFundCode(StrUtil.trim(tr.$eval("td.fund-name-code > a.code", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundName(StrUtil.trim(tr.$eval("td.fund-name-code > a.name", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundBa(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(1)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundArchive(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(2)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundType(StrUtil.trim(tr.$eval("td:nth-child(3)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.setFundDate(Date(StrUtil.trim(tr.$eval("td:nth-child(4)","v => v.textContent",new ArrayList()).toString())));
                    eastmoneyFundTO.setFundNetVal(StrUtil.trim(tr.$eval("td:nth-child(5)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundAccumulatedNetVal(StrUtil.trim(tr.$eval("td:nth-child(6)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundDailyGrowthRate(StrUtil.trim(tr.$eval("td:nth-child(7)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundScale(StrUtil.trim(tr.$eval("td:nth-child(10)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundManager(StrUtil.trim(tr.$eval("td:nth-child(11)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.setFundManagerUrl(StrUtil.trim(tr.$eval("td:nth-child(11)","v => v.attributes['href'].nodeValue",new ArrayList()).toString()));
                    eastmoneyFundTO.setFundSubscriptionStatus(StrUtil.trim(tr.$eval("td:nth-child(12)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundHandFee(StrUtil.trim(tr.$eval("td:nth-child(13)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.purchase =StrUtil.trim(tr.$eval("td:nth-child(14)","v => v.attributes['title'].nodeValue",new ArrayList()).toString())
//                    log.info(JSON.toJSONString(fa));
                    eastmoneyFundTO.setCrawlDateTime(new Date());
                    eastmoneyFundTO.setCompanyCode(eastmoneyCompanyTO.getCompanyCode());
                    eastmoneyFundTO.setCompanyName(eastmoneyCompanyTO.getCompanyName());
                    al.addAndGet(1);
//                    ras.add(fundList);
//                        log.warn(JSON.toJSONString(fundList));
                    iEastmoneyFundListProducer.eastmoneyFundListOutput().send(MessageBuilder.withPayload(eastmoneyFundTO).build());
                }
            } else if ("货币/理财型基金".equals(h3)) {
                for (ElementHandle tr : trs) {
                    EastmoneyFundTO eastmoneyFundTO = new EastmoneyFundTO();
                    eastmoneyFundTO.setFundConcept(h3);
                    String content = StrUtil.trim(tr.$eval("td", "v => v.textContent", new ArrayList()).toString());
                    if ("暂无数据".equals(content)) {
                        continue;
                    }
                    eastmoneyFundTO.setFundCode(StrUtil.trim(tr.$eval("td.fund-name-code > a.code", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundName(StrUtil.trim(tr.$eval("td.fund-name-code > a.name", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundBa(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(1)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundArchive(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(2)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundType(StrUtil.trim(tr.$eval("td:nth-child(3)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.setFundDate(ate(StrUtil.trim(tr.$eval("td:nth-child(4)","v => v.textContent",new ArrayList()).toString())));
                    //万分收益
                    eastmoneyFundTO.setFundNetVal(StrUtil.trim(tr.$eval("td:nth-child(5)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundDailyGrowthRate(StrUtil.trim(tr.$eval("td:nth-child(6)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundScale(StrUtil.trim(tr.$eval("td:nth-child(10)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundManager(StrUtil.trim(tr.$eval("td:nth-child(11)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.setFundManagerUrl(r.$eval("td:nth-child(11)","v => v.attributes['href'].nodeValue",new ArrayList()).toString());
                    eastmoneyFundTO.setFundSubscriptionStatus(StrUtil.trim(tr.$eval("td:nth-child(12)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundHandFee(StrUtil.trim(tr.$eval("td:nth-child(13)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.setPurchase($eval("td:nth-child(14)","v => v.attributes['title'].nodeValue",new ArrayList()).toString());
//                    log.info(JSON.toJSONString(fa));
                    eastmoneyFundTO.setCrawlDateTime(new Date());
                    eastmoneyFundTO.setCompanyCode(eastmoneyCompanyTO.getCompanyCode());
                    eastmoneyFundTO.setCompanyName(eastmoneyCompanyTO.getCompanyName());
//                        log.warn(JSON.toJSONString(eastmoneyFundList));
                    al.addAndGet(1);
                    iEastmoneyFundListProducer.eastmoneyFundListOutput().send(MessageBuilder.withPayload(eastmoneyFundTO).build());
//                    ras.add(eastmoneyFundList);
                }
            } else if ("场内基金".equals(h3)) {
                for (ElementHandle tr : trs) {
                    EastmoneyFundTO eastmoneyFundTO = new EastmoneyFundTO();
                    eastmoneyFundTO.setFundConcept(h3);
                    String content = StrUtil.trim(tr.$eval("td", "v => v.textContent", new ArrayList()).toString());
                    if ("暂无数据".equals(content)) {
                        continue;
                    }
                    eastmoneyFundTO.setFundCode(StrUtil.trim(tr.$eval("td.fund-name-code > a.code", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundName(StrUtil.trim(tr.$eval("td.fund-name-code > a.name", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundBa(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(1)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundArchive(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(2)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundType(StrUtil.trim(tr.$eval("td:nth-child(3)", "v => v.textContent", new ArrayList()).toString()));
                    //                eastmoneyFundList.setFundDate(Date(StrUtil.trim(tr.$eval("td:nth-child(4)","v => v.textContent",new ArrayList()).toString())));
                    eastmoneyFundTO.setFundNetVal(StrUtil.trim(tr.$eval("td:nth-child(5)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundAccumulatedNetVal(StrUtil.trim(tr.$eval("td:nth-child(6)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundDailyGrowthRate(StrUtil.trim(tr.$eval("td:nth-child(7)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundMarketVal(StrUtil.trim(tr.$eval("td:nth-child(8)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundHairCut(StrUtil.trim(tr.$eval("td:nth-child(9)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundScale(StrUtil.trim(tr.$eval("td:nth-child(10)", "v => v.textContent", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundManager(StrUtil.trim(tr.$eval("td:nth-child(11)", "v => v.textContent", new ArrayList()).toString()));
                    //                eastmoneyFundList.fundManagerUrl = StrUtil.trim(tr.$eval("td:nth-child(11)","v => v.attributes['href'].nodeValue",new ArrayList()).toString())
//                    log.info(JSON.toJSONString(eastmoneyFundList));
                    eastmoneyFundTO.setCrawlDateTime(new Date());
                    eastmoneyFundTO.setCompanyCode(eastmoneyCompanyTO.getCompanyCode());
                    eastmoneyFundTO.setCompanyName(eastmoneyCompanyTO.getCompanyName());
//                        log.warn(JSON.toJSONString(eastmoneyFundList));
                    al.addAndGet(1);
                    iEastmoneyFundListProducer.eastmoneyFundListOutput().send(MessageBuilder.withPayload(eastmoneyFundTO).build());
//                    ras.add(eastmoneyFundList);
                }
            }
        }
        page.close();
        cdl.countDown();
    }

//    private ExecutorService fixedThreadPool = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
//
//    class FundListRunnable implements Runnable{
//        private Browser browser;
//        private String url;
//
//        public FundListRunnable(Browser browser, String url) {
//            this.browser = browser;
//            this.url = url;
//        }
//        @Override
//        public void run() {
//            try {
//                crawlFundList(browser,url);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        public void crawlFundList(Browser browser, String url) throws InterruptedException {
//
//        }
//
//
//    }
}
