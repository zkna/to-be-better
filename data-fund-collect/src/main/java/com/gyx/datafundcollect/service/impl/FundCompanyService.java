package com.gyx.datafundcollect.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.gyx.datafundcollect.service.IFundCompany;
import com.gyx.entity.common.FundConstant;
import com.gyx.entity.fund.FundCompany;
import com.gyx.entity.fund.FundCompanyOverview;
import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class FundCompanyService implements IFundCompany {

    private final static Pattern pat1 = Pattern.compile("\\d{1,}");

    private final StreamBridge streamBridge;

    /**
     * 基金概览
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public FundCompanyOverview crawlCompanyOverview() throws IOException, InterruptedException {
        List<String> argList = new ArrayList<>();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false).build();
        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        page.goTo(FundConstant.COMPANYOVERVIEW);
        List<ElementHandle> ehs = page.$$("body > div.outer_all > div.ttjj-grid-row > div.main-content.ttjj-grid-21 > div.third-block.jcommon-block > div.common-block-con > table > tbody");
        ElementHandle e2 = ehs.get(0).$$("tr:nth-child(2)").get(0);
        FundCompanyOverview co = new FundCompanyOverview();
        var fundCompanys = StrUtil.trim(page.$eval("body > div.outer_all > div.ttjj-grid-row > div.main-content.ttjj-grid-21 > div.third-block.jcommon-block > div.common-sub-title.clearfix > div.pull-left > p",
                "p => p.textContent", new ArrayList()).toString());
        Matcher mat = pat1.matcher(fundCompanys.toString());
        while (mat.find()) {
            co.setFundCompanyNums(Integer.valueOf(mat.group()));
        }
        var expiryDate = StrUtil.trim((String)page.$eval("body > div.outer_all > div.ttjj-grid-row > div.main-content.ttjj-grid-21 > div.third-block.jcommon-block > div.common-sub-title.clearfix > div.pull-right > p",
                "p => p.textContent",new ArrayList())).replace("截止日期：","");
        co.setExpiryDate(expiryDate);

        co.setTotalAmount(new BigDecimal(StrUtil.trim((e2.$eval("td:nth-child(2)","v => v.textContent",new ArrayList()).toString()))));
        co.setStockAmount(new BigDecimal(StrUtil.trim((e2.$eval("td:nth-child(3)","v => v.textContent",new ArrayList()).toString()))));
        co.setMixedAmount(new BigDecimal(StrUtil.trim((e2.$eval("td:nth-child(4)","v => v.textContent",new ArrayList()).toString()))));
        co.setBondsAmount(new BigDecimal(StrUtil.trim((e2.$eval("td:nth-child(5)","v => v.textContent",new ArrayList()).toString()))));
        co.setIndexAmount(new BigDecimal(StrUtil.trim((e2.$eval("td:nth-child(6)","v => v.textContent",new ArrayList()).toString()))));
        co.setQDIIAmount(new BigDecimal(StrUtil.trim((e2.$eval("td:nth-child(7)","v => v.textContent",new ArrayList()).toString()))));
        co.setCurrencyAmount(new BigDecimal(StrUtil.trim((e2.$eval("td:nth-child(8)","v => v.textContent",new ArrayList()).toString()))));

        ElementHandle e3 = ehs.get(0).$$("tr:nth-child(3)").get(0);
        co.setTotalNum(Integer.valueOf(StrUtil.trim((e3.$eval("td:nth-child(2)","v => v.textContent",new ArrayList())).toString())));
        co.setStockNum(Integer.valueOf(StrUtil.trim((e3.$eval("td:nth-child(3)","v => v.textContent",new ArrayList())).toString())));
        co.setMixedNum(Integer.valueOf(StrUtil.trim((e3.$eval("td:nth-child(4)","v => v.textContent",new ArrayList())).toString())));
        co.setBondsNum(Integer.valueOf(StrUtil.trim((e3.$eval("td:nth-child(5)","v => v.textContent",new ArrayList())).toString())));
        co.setIndexNum(Integer.valueOf(StrUtil.trim((e3.$eval("td:nth-child(6)","v => v.textContent",new ArrayList())).toString())));
        co.setQDIINum(Integer.valueOf(StrUtil.trim((e3.$eval("td:nth-child(7)","v => v.textContent",new ArrayList())).toString())));
        co.setCurrencyNum(Integer.valueOf(StrUtil.trim((e3.$eval("td:nth-child(8)","v => v.textContent",new ArrayList())).toString())));
        co.setCrawlDateTime(new Date());
        browser.close();
        //Objeto avro gerado a partir do schema na pasta 'avro'
        log.info("Message<CustomerAvro> = {}", co);
        streamBridge.send("producer-customer-topic-avro", JSON.toJSONString(co));
//        iDataSendService.sendMsg(JSON.toJSONString(co));
        return co;
    }

    /**
     * 爬取基金公司记录
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public List<FundCompany> crawlFundCompany() throws IOException, InterruptedException {
        List<String> argList = new ArrayList<String>();
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false).build();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        page.goTo(FundConstant.COMPANYLIST);
        List<FundCompany> cls = new ArrayList<FundCompany>();
        FundCompany cl =new FundCompany();
        List<ElementHandle> trs = page.$$("#gspmTbl > tbody > tr");
        for (ElementHandle td : trs){
            cl = new FundCompany();
            cl.setCompanyId(StrUtil.trim(td.$eval("td.td-align-left","v => v.attributes['data-sortvalue'].nodeValue", new ArrayList()).toString()));
            cl.setCompanyName(StrUtil.trim(td.$eval("td.td-align-left > a","v => v.textContent", new ArrayList()).toString()));
            cl.setCompanyDetail(StrUtil.trim(td.$eval("td.menu-link > a:nth-child(1)","v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
            cl.setCompanyBa(StrUtil.trim(td.$eval("td.menu-link > a:nth-child(2)","v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
            cl.setCompanySetDate(DateUtil.parse(StrUtil.trim(td.$eval("td:nth-child(4)","v => v.textContent", new ArrayList()).toString()),"yyyy-MM-dd"));
            cl.setCompanyTianXiang(StrUtil.trim(td.$eval("td.td-pj > div","v => v.textContent", new ArrayList()).toString()));
            if ("暂无评级".equals(cl.getCompanyTianXiang())){
                cl.setCompanyTianXiang("0");
            }else{
                Integer t1 = td.$$("td.td-pj > div > label.sprite-star1").size();
                Integer t5 = td.$$("td.td-pj > div > label.sprite-star5").size();;
                cl.setCompanyTianXiang(String.valueOf(t1+t5));
            }
            cl.setScaleDate(StrUtil.trim(td.$eval("td.scale.number > p > span","v => v.textContent", new ArrayList()).toString()));
            if (StrUtil.isNotBlank(cl.getScaleDate())){
                cl.setScaleNumber(new BigDecimal(StrUtil.trim(td.$eval("td.scale.number > p","v => v.textContent",new ArrayList()).toString().replace(cl.getScaleDate(),"").replace(StrUtil.COMMA,""))));
            }
            cl.setFundNums(Integer.valueOf(StrUtil.trim(td.$eval("td:nth-child(7) > a","v => v.textContent", new ArrayList()).toString())));
            cl.setManagerNums(Integer.valueOf(StrUtil.trim(td.$eval("td:nth-child(8) > a","v => v.textContent", new ArrayList()).toString())));
            cl.setManagerUrl(StrUtil.trim(td.$eval("td:nth-child(8) > a","v => v.attributes['href'].nodeValue", new ArrayList()).toString()));
            log.info(JSON.toJSONString(cl));
            cls.add(cl);
        }
        return cls;

    }

}
