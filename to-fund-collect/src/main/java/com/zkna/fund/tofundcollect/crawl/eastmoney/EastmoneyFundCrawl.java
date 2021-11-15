package com.zkna.fund.tofundcollect.crawl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.gyx.entity.common.FundConstant;
import com.gyx.entity.fund.eastmoney.EastmoneyCompanyTO;
import com.gyx.entity.fund.eastmoney.EastmoneyFundOverviewTO;
import com.gyx.entity.fund.eastmoney.EastmoneyFundTO;
import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import com.zkna.fund.tofundcollect.producer.IEastmoneyCompanyListProducer;
import com.zkna.fund.tofundcollect.producer.IEastmoneyFundListProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gyx.entity.common.FundConstant.COMPANY_DETAIL;

@Component
@Slf4j
public class EastmoneyFundCrawl {

    private final static Pattern pat1 = Pattern.compile("\\d{1,}");

    @Autowired
    private IEastmoneyCompanyListProducer IEastmoneyCompanyListProducer;

    @Autowired
    private IEastmoneyFundListProducer iEastmoneyFundListProducer;

    CountDownLatch cdl = null;
    AtomicLong al = new AtomicLong(0);

    public EastmoneyFundOverviewTO crawlCompanyOverview() throws IOException, InterruptedException, ParseException {
        List<String> argList = new ArrayList<String>();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false).build();
        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        page.goTo(FundConstant.COMPANYOVERVIEW);
        List<ElementHandle> ehs = page.$$("body > div.outer_all > div.ttjj-grid-row > div.main-content.ttjj-grid-21 > div.third-block.jcommon-block > div.common-block-con > table > tbody");
        ElementHandle e2 = ehs.get(0).$$("tr:nth-child(2)").get(0);
        EastmoneyFundOverviewTO co = new EastmoneyFundOverviewTO();
        String fundCompanys = StrUtil.trim(page.$eval("body > div.outer_all > div.ttjj-grid-row > div.main-content.ttjj-grid-21 > div.third-block.jcommon-block > div.common-sub-title.clearfix > div.pull-left > p",
                "p => p.textContent", new ArrayList()).toString());
        Matcher mat = pat1.matcher(fundCompanys);
        while (mat.find()) {
            co.setFundCompanyNums(mat.group());
        }
        String expiryDate = StrUtil.trim((String) page.$eval("body > div.outer_all > div.ttjj-grid-row > div.main-content.ttjj-grid-21 > div.third-block.jcommon-block > div.common-sub-title.clearfix > div.pull-right > p",
                "p => p.textContent", new ArrayList())).replace("截止日期：", "");
        co.setExpiryDate(DateUtil.parseDate(expiryDate));
        co.setTotalAmount(StrUtil.trim((e2.$eval("td:nth-child(2)", "v => v.textContent", new ArrayList()).toString())));
        co.setStockAmount(StrUtil.trim((e2.$eval("td:nth-child(3)", "v => v.textContent", new ArrayList()).toString())));
        co.setMixedAmount(StrUtil.trim((e2.$eval("td:nth-child(4)", "v => v.textContent", new ArrayList()).toString())));
        co.setBondsAmount(StrUtil.trim((e2.$eval("td:nth-child(5)", "v => v.textContent", new ArrayList()).toString())));
        co.setIndexAmount(StrUtil.trim((e2.$eval("td:nth-child(6)", "v => v.textContent", new ArrayList()).toString())));
        co.setQdiiAmount(StrUtil.trim((e2.$eval("td:nth-child(7)", "v => v.textContent", new ArrayList()).toString())));
        co.setCurrencyAmount(StrUtil.trim((e2.$eval("td:nth-child(8)", "v => v.textContent", new ArrayList()).toString())));

        ElementHandle e3 = ehs.get(0).$$("tr:nth-child(3)").get(0);
        co.setTotalNum(StrUtil.trim((e3.$eval("td:nth-child(2)", "v => v.textContent", new ArrayList())).toString()));
        co.setStockNum(StrUtil.trim((e3.$eval("td:nth-child(3)", "v => v.textContent", new ArrayList())).toString()));
        co.setMixedNum(StrUtil.trim((e3.$eval("td:nth-child(4)", "v => v.textContent", new ArrayList())).toString()));
        co.setBondsNum(StrUtil.trim((e3.$eval("td:nth-child(5)", "v => v.textContent", new ArrayList())).toString()));
        co.setIndexNum(StrUtil.trim((e3.$eval("td:nth-child(6)", "v => v.textContent", new ArrayList())).toString()));
        co.setQdiiNum(StrUtil.trim((e3.$eval("td:nth-child(7)", "v => v.textContent", new ArrayList())).toString()));
        co.setCurrencyNum(StrUtil.trim((e3.$eval("td:nth-child(8)", "v => v.textContent", new ArrayList())).toString()));
        co.setCrawlDateTime(new Date());
        browser.close();
        log.info("Message<PRODUCE_COMPANY_OVERVIEW> = {}", JSON.toJSONString(co));
//        streamBridge.send(PRODUCE_COMPANY_OVERVIEW, co);
        return co;
    }

    public List<EastmoneyCompanyTO> crawlCompanyList() throws Exception {
        List<String> argList = new ArrayList<String>();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        argList.add("--disable-extensions");
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false).build();
        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        page.goTo(FundConstant.COMPANYLIST);
        List<EastmoneyCompanyTO> cls = new ArrayList<EastmoneyCompanyTO>();
        EastmoneyCompanyTO eastmoneyCompanyTO = null;
        List<ElementHandle> trs = page.$$("#gspmTbl > tbody > tr");
        cdl = new CountDownLatch(trs.size());
        Integer fundSum = 0;
        for (ElementHandle td : trs) {
            eastmoneyCompanyTO = new EastmoneyCompanyTO();
            eastmoneyCompanyTO.setCompanyId(StrUtil.trim(td.$eval("td.td-align-left", "v => v.attributes['data-sortvalue'].nodeValue", new ArrayList()).toString()));
            eastmoneyCompanyTO.setCompanyName(StrUtil.trim(td.$eval("td.td-align-left > a", "v => v.textContent", new ArrayList()).toString()));
            eastmoneyCompanyTO.setCompanyDetail(StrUtil.trim(td.$eval("td.menu-link > a:nth-child(1)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
            eastmoneyCompanyTO.setCompanyBa(StrUtil.trim(td.$eval("td.menu-link > a:nth-child(2)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
            eastmoneyCompanyTO.setCompanySetDate(DateUtil.parseDate(StrUtil.trim(td.$eval("td:nth-child(4)", "v => v.textContent", new ArrayList()).toString())));
            eastmoneyCompanyTO.setCompanyTianXiangLevel(StrUtil.trim(td.$eval("td.td-pj > div", "v => v.textContent", new ArrayList()).toString()));
            if ("暂无评级".equals(eastmoneyCompanyTO.getCompanyTianXiangLevel())) {
                eastmoneyCompanyTO.setCompanyTianXiangLevel("0");
            } else {
                Integer t1 = td.$$("td.td-pj > div > label.sprite-star1").size();
                Integer t5 = td.$$("td.td-pj > div > label.sprite-star5").size();
                ;
                eastmoneyCompanyTO.setCompanyTianXiangLevel(String.valueOf(t1 + t5));
            }
            eastmoneyCompanyTO.setCompanyScaleDate(StrUtil.trim(td.$eval("td.scale.number > p > span", "v => v.textContent", new ArrayList()).toString()));
            if (StrUtil.isNotBlank(eastmoneyCompanyTO.getCompanyScaleDate())) {
                eastmoneyCompanyTO.setCompanyScaleSum(new BigDecimal(StrUtil.trim(td.$eval("td.scale.number > p", "v => v.textContent", new ArrayList()).toString().replace(eastmoneyCompanyTO.getCompanyScaleDate(), "").replace(",", ""))));
            }
            eastmoneyCompanyTO.setCompanyFundSum(Integer.valueOf(StrUtil.trim(td.$eval("td:nth-child(7) > a", "v => v.textContent", new ArrayList()).toString())));
            eastmoneyCompanyTO.setCompanyManagerSum(Integer.valueOf(StrUtil.trim(td.$eval("td:nth-child(8) > a", "v => v.textContent", new ArrayList()).toString())));
            eastmoneyCompanyTO.setCompanyManagerUrl(StrUtil.trim(td.$eval("td:nth-child(8) > a", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
            eastmoneyCompanyTO.setCrawlDateTime(new Date());
            log.info("Message[CrawlCompanyList]{}", eastmoneyCompanyTO);
            //发送Kafka
            IEastmoneyCompanyListProducer.eastmoneyCompanyListOutput().send(MessageBuilder.withPayload(eastmoneyCompanyTO).build());
            cls.add(eastmoneyCompanyTO);
            fundSum += eastmoneyCompanyTO.getCompanyFundSum();
        }

        for (EastmoneyCompanyTO e: cls) {
            doFundList(browser,COMPANY_DETAIL+ e.getCompanyDetail());
        }
        cdl.await();
        log.error(""+fundSum);
        return cls;
    }

    public void doFundList(Browser browser,String url) throws Exception {
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
                    eastmoneyFundTO.setFundBaUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(1)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundArchiveUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(2)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
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
                    eastmoneyFundTO.setFundBaUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(1)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundArchiveUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(2)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
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
                    eastmoneyFundTO.setFundBaUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(1)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    eastmoneyFundTO.setFundArchiveUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(2)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
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
