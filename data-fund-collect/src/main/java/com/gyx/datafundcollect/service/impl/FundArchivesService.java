package com.gyx.datafundcollect.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gyx.datafundcollect.entity.trans.Lsjz;
import com.gyx.datafundcollect.entity.trans.LsjzDataList;
import com.gyx.datafundcollect.service.IFundArchives;
import com.gyx.entity.fund.FundArchives;
import com.gyx.entity.fund.eastmoney.EastmoneyFundTO;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.gyx.entity.common.FundConstant.*;

@Service
@Slf4j
public class FundArchivesService implements IFundArchives {

    @Autowired
    StreamBridge streamBridge;

    @Autowired
    IFundArchives iFundArchives;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private ExecutorService executorService = new ThreadPoolExecutor(90, 100, 5L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    CountDownLatch cdl = null;

    /**
     * 爬取T-1净值
     */
    @Override
    public void crawlFundList(Browser browser, String url) throws IOException, InterruptedException {
        Page page = browser.newPage();
        page.goTo(url);
        List<ElementHandle> divs = page.$$("body > div.outer_all > div.ttjj-grid-row > div.main-content.ttjj-grid-21 > div.third-block");
        cdl = new CountDownLatch(divs.size());
        for (ElementHandle div : divs) {
            String h3 = div.$eval("h3", "v => v.textContent", new ArrayList()).toString();
            List<ElementHandle> trs = div.$$("table > tbody > tr");
            if ("开放式基金".equals(h3)) {
                for (ElementHandle tr : trs) {
                    EastmoneyFundTO fl = new EastmoneyFundTO();
                    fl.setFundConcept(h3);
                    String content = StrUtil.trim(tr.$eval("td", "v => v.textContent", new ArrayList()).toString());
                    if ("暂无数据".equals(content)) {
                        continue;
                    }
                    fl.setFundCode(StrUtil.trim(tr.$eval("td.fund-name-code > a.code", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundName(StrUtil.trim(tr.$eval("td.fund-name-code > a.name", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundBaUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(1)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    fl.setFundArchiveUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(2)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    fl.setFundType(StrUtil.trim(tr.$eval("td:nth-child(3)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.setFundDate(Date(StrUtil.trim(tr.$eval("td:nth-child(4)","v => v.textContent",new ArrayList()).toString())));
                    fl.setFundNetVal(StrUtil.trim(tr.$eval("td:nth-child(5)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundAccumulatedNetVal(StrUtil.trim(tr.$eval("td:nth-child(6)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundDailyGrowthRate(StrUtil.trim(tr.$eval("td:nth-child(7)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundScale(StrUtil.trim(tr.$eval("td:nth-child(10)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundManager(StrUtil.trim(tr.$eval("td:nth-child(11)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.setFundManagerUrl(StrUtil.trim(tr.$eval("td:nth-child(11)","v => v.attributes['href'].nodeValue",new ArrayList()).toString()));
                    fl.setFundSubscriptionStatus(StrUtil.trim(tr.$eval("td:nth-child(12)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundHandFee(StrUtil.trim(tr.$eval("td:nth-child(13)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.purchase =StrUtil.trim(tr.$eval("td:nth-child(14)","v => v.attributes['title'].nodeValue",new ArrayList()).toString())
//                    log.info(JSON.toJSONString(fa));
                    fl.setCrawlDateTime(new Date());
                    sendFundList(cdl, browser, fl);
//                    ras.add(fl);
                }
            } else if ("货币/理财型基金".equals(h3)) {
                for (ElementHandle tr : trs) {
                    EastmoneyFundTO fl = new EastmoneyFundTO();
                    fl.setFundConcept(h3);
                    String content = StrUtil.trim(tr.$eval("td", "v => v.textContent", new ArrayList()).toString());
                    if ("暂无数据".equals(content)) {
                        continue;
                    }
                    fl.setFundCode(StrUtil.trim(tr.$eval("td.fund-name-code > a.code", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundName(StrUtil.trim(tr.$eval("td.fund-name-code > a.name", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundBaUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(1)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    fl.setFundArchiveUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(2)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    fl.setFundType(StrUtil.trim(tr.$eval("td:nth-child(3)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.setFundDate(ate(StrUtil.trim(tr.$eval("td:nth-child(4)","v => v.textContent",new ArrayList()).toString())));
                    //万分收益
                    fl.setFundNetVal(StrUtil.trim(tr.$eval("td:nth-child(5)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundDailyGrowthRate(StrUtil.trim(tr.$eval("td:nth-child(6)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundScale(StrUtil.trim(tr.$eval("td:nth-child(10)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundManager(StrUtil.trim(tr.$eval("td:nth-child(11)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.setFundManagerUrl(r.$eval("td:nth-child(11)","v => v.attributes['href'].nodeValue",new ArrayList()).toString());
                    fl.setFundSubscriptionStatus(StrUtil.trim(tr.$eval("td:nth-child(12)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundHandFee(StrUtil.trim(tr.$eval("td:nth-child(13)", "v => v.textContent", new ArrayList()).toString()));
                    //                fa.setPurchase($eval("td:nth-child(14)","v => v.attributes['title'].nodeValue",new ArrayList()).toString());
//                    log.info(JSON.toJSONString(fa));
                    fl.setCrawlDateTime(new Date());
                    sendFundList(cdl, browser, fl);
//                    ras.add(fl);
                }
            } else if ("场内基金".equals(h3)) {
                for (ElementHandle tr : trs) {
                    EastmoneyFundTO fl = new EastmoneyFundTO();
                    fl.setFundConcept(h3);
                    String content = StrUtil.trim(tr.$eval("td", "v => v.textContent", new ArrayList()).toString());
                    if ("暂无数据".equals(content)) {
                        continue;
                    }
                    fl.setFundCode(StrUtil.trim(tr.$eval("td.fund-name-code > a.code", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundName(StrUtil.trim(tr.$eval("td.fund-name-code > a.name", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundBaUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(1)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    fl.setFundArchiveUrl(StrUtil.trim(tr.$eval("td:nth-child(2) > a:nth-child(2)", "v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
                    fl.setFundType(StrUtil.trim(tr.$eval("td:nth-child(3)", "v => v.textContent", new ArrayList()).toString()));
                    //                fl.setFundDate(Date(StrUtil.trim(tr.$eval("td:nth-child(4)","v => v.textContent",new ArrayList()).toString())));
                    fl.setFundNetVal(StrUtil.trim(tr.$eval("td:nth-child(5)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundAccumulatedNetVal(StrUtil.trim(tr.$eval("td:nth-child(6)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundDailyGrowthRate(StrUtil.trim(tr.$eval("td:nth-child(7)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundMarketVal(StrUtil.trim(tr.$eval("td:nth-child(8)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundHairCut(StrUtil.trim(tr.$eval("td:nth-child(9)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundScale(StrUtil.trim(tr.$eval("td:nth-child(10)", "v => v.textContent", new ArrayList()).toString()));
                    fl.setFundManager(StrUtil.trim(tr.$eval("td:nth-child(11)", "v => v.textContent", new ArrayList()).toString()));
                    //                fl.fundManagerUrl = StrUtil.trim(tr.$eval("td:nth-child(11)","v => v.attributes['href'].nodeValue",new ArrayList()).toString())
//                    log.info(JSON.toJSONString(fl));
                    fl.setCrawlDateTime(new Date());
                    sendFundList(cdl, browser, fl);
//                    ras.add(fl);
                }
            }
        }
        cdl.await();
        page.close();
    }

    @Override
    public void crawlFundArchives(Page page, String fundCode, String url, String type) throws IOException, InterruptedException, ExecutionException {
        try {
//        HISTORY_ARCHIVE
            page.goTo(url);
            Boolean isEnd = false;
            while (!isEnd) {
                Thread.sleep(1000);
                try {
                    //https://fundf10.eastmoney.com/jjjz_012842.html
                    List<ElementHandle> nextBtns = page.$$("#pagebar > div.pagebtns label.end");
                    if (nextBtns.size() == 2) {
                        isEnd = true;
                    } else {
                        String eh = page.$eval("#pagebar > div.pagebtns > label.end", "v => v.textContent", new ArrayList<>()).toString();
                        if (StrUtil.isNotBlank(eh) && "下一页".equals(eh)) {
                            isEnd = true;
                        }
                    }

                } catch (Exception e) {
//                e.printStackTrace();
                }
                List<FundArchives> fas = new ArrayList<>();
                List<ElementHandle> trs = page.$$("#jztable > table > tbody > tr");
                for (ElementHandle tr : trs) {
                    FundArchives fa = new FundArchives();
                    String content = StrUtil.trim(tr.$eval("td", "v => v.textContent", new ArrayList()).toString());
                    if ("暂无数据".equals(content)) {
                        continue;
                    }
                    fa.setFundConcept(type);
                    fa.setFundCode(fundCode);
                    if ("开放式基金".equals(type) || "场内基金".equals(type)) {
                        fa.setNetDateTime(StrUtil.trim(tr.$eval("td:nth-child(1)", "v => v.textContent", new ArrayList()).toString()));
                        fa.setUnitNet(StrUtil.trim(tr.$eval("td:nth-child(2)", "v => v.textContent", new ArrayList()).toString()));
                        fa.setAccumulatedNet(StrUtil.trim(tr.$eval("td:nth-child(3)", "v => v.textContent", new ArrayList()).toString()));
                        fa.setDayGrowRate(StrUtil.trim(tr.$eval("td:nth-child(4)", "v => v.textContent", new ArrayList()).toString()));
                        fa.setSubscriptionStatus(StrUtil.trim(tr.$eval("td:nth-child(5)", "v => v.textContent", new ArrayList()).toString()));
                        fa.setRedemptionStatus(StrUtil.trim(tr.$eval("td:nth-child(6)", "v => v.textContent", new ArrayList()).toString()));
                        fa.setDividendDistribution(StrUtil.trim(tr.$eval("td:nth-child(7)", "v => v.textContent", new ArrayList()).toString()));
                    } else if ("货币/理财型基金".equals(type)) {
                        fa.setNetDateTime(StrUtil.trim(tr.$eval("td:nth-child(1)", "v => v.textContent", new ArrayList()).toString()));
                        fa.setPerMillionFund(StrUtil.trim(tr.$eval("td:nth-child(2)", "v => v.textContent", new ArrayList()).toString()));
                        fa.setAnnualized7Income(StrUtil.trim(tr.$eval("td:nth-child(3)", "v => v.textContent", new ArrayList()).toString()));
                        fa.setSubscriptionStatus(StrUtil.trim(tr.$eval("td:nth-child(4)", "v => v.textContent", new ArrayList()).toString()));
                        fa.setRedemptionStatus(StrUtil.trim(tr.$eval("td:nth-child(5)", "v => v.textContent", new ArrayList()).toString()));
                        fa.setDividendDistribution(StrUtil.trim(tr.$eval("td:nth-child(6)", "v => v.textContent", new ArrayList()).toString()));
                    }
                    fa.setCrawlDateTime(new Date());
                    log.info(JSON.toJSONString(fa));
                    fas.add(fa);
//                    ras.add(fl);
                }
                //TODO:不会取，只能先这样了
                try {
                    if (!isEnd) {
                        List<ElementHandle> nextBtns = page.$$("#pagebar > div.pagebtns  label");
                        nextBtns.get(nextBtns.size() - 1).click();
                    }
                } catch (Exception e) {
                }
                streamBridge.send(PRODUCE_FUND_ARCHIVES, fas);
            }
        }catch (Exception e){

        }finally {
            try {
                page.close();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    @Override
    public void crawlFundArchivesByAPI(String fundCode, String type) {
        Integer pageIndex = 1;
        Integer pageSize = 100;
        Lsjz ls = doRequestSendWrap(fundCode,type,pageIndex,pageSize,10);
        int i = 0;
        pageIndex++;
        while (pageIndex * pageSize < ls.getTotalCount() || i >=100) {
            ls = doRequestSendWrap(fundCode,type,pageIndex,pageSize,ls.getTotalCount());
            pageIndex++;
            i++;
        }
        log.info("crawl page:{}",i);
    }


    public Lsjz doRequestSendWrap(String fundCode, String type, Integer pageIndex, Integer pageSize,Integer totalCount){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("fundCode", fundCode);
        paramMap.put("pageIndex", pageIndex);
        paramMap.put("pageSize", pageSize);
        HashMap<String, String> headMap = new HashMap<>();
        headMap.put("Referer"," https://fundf10.eastmoney.com/");
        String result = "";
        try {
            result = HttpRequest.get(PRODUCE_FUND_ARCHIVES_API).addHeaders(headMap).form(paramMap).timeout(20000).execute().body();
        }catch (Exception e){
            log.error("获取基金历史净值失败：{}",e.getMessage());
            stringRedisTemplate.opsForList().leftPush("catch",JSON.toJSONString(paramMap));
        }
        if (StrUtil.isEmpty(result)){
            return new Lsjz(totalCount);
        }
        Lsjz ls =JSONObject.parseObject(result, Lsjz.class);
        List<LsjzDataList> ldl = ls.getData().getLSJZList();
        Date now = new Date();
        if (!ldl.isEmpty()) {
            List<FundArchives> fas = ldl.stream().map(v -> {
                FundArchives fa = new FundArchives();
                fa.setCrawlDateTime(now);
                fa.setFundConcept(type);
                fa.setFundCode(fundCode);
                fa.setNetDateTime(v.getFSRQ());
                fa.setUnitNet(v.getDWJZ());
                fa.setAccumulatedNet(v.getLJJZ());
                fa.setDayGrowRate(v.getJZZZL());
                fa.setSubscriptionStatus(v.getSGZT());
                fa.setRedemptionStatus(v.getSHZT());
                fa.setDividendDistribution(v.getFHSP());
                fa.setPerMillionFund(v.getDWJZ());
                fa.setAnnualized7Income(v.getLJJZ());
                return fa;
            }).collect(Collectors.toList());
//            log.info(JSON.toJSONString(fas));
            streamBridge.send(PRODUCE_FUND_ARCHIVES, fas);
        }
        return ls;
    }

    public void sendFundList(CountDownLatch cdl, Browser browser, EastmoneyFundTO fl) {
//        log.warn(JSON.toJSONString(fl));
        try {
            streamBridge.send(PRODUCE_FUND_LIST, fl);
            executorService.execute(new excuteSendRunnable(cdl,  fl.getFundCode(), fl.getFundConcept()));
//            iFundArchives.crawlFundArchives(browser, fl.getFundCode(), StrUtil.format(HISTORY_ARCHIVE, fl.getFundCode()), fl.getFundConcept());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class excuteSendRunnable implements Runnable {

        private CountDownLatch cdl;

        private String fundCode;

        private String fundConcept;

        public excuteSendRunnable(CountDownLatch cdl,  String fundCode, String fundConcept) {
            this.cdl = cdl;
            this.fundCode = fundCode;
            this.fundConcept = fundConcept;
        }

        @SneakyThrows
        @Override
        public void run() {
//            iFundArchives.crawlFundArchives(browser.newPage(), fundCode, url, fundConcept);
            crawlFundArchivesByAPI(fundCode,fundConcept);
            cdl.countDown();
        }
    }
}
