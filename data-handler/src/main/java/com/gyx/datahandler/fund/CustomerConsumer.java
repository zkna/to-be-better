package com.gyx.datahandler.fund;

import cn.hutool.extra.cglib.CglibUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyx.datahandler.entity.TEastmoneyCompanyTO;
import com.gyx.datahandler.entity.TEastmoneyFundOverviewTO;
import com.gyx.datahandler.entity.TFundArchives;
import com.gyx.datahandler.entity.TEastmoneyFundTO;
import com.gyx.datahandler.service.ITCompanyListService;
import com.gyx.datahandler.service.ITCompanyOverviewService;
import com.gyx.datahandler.service.ITFundArchivesService;
import com.gyx.datahandler.service.ITFundListService;
import com.gyx.entity.fund.eastmoney.EastmoneyCompanyTO;
import com.gyx.entity.fund.eastmoney.EastmoneyFundOverviewTO;
import com.gyx.entity.fund.FundArchives;
import com.gyx.entity.fund.eastmoney.EastmoneyFundTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class CustomerConsumer {

    @Autowired
    ITCompanyOverviewService itCompanyOverviewService;

    @Autowired
    ITCompanyListService itCompanyListService;

    @Autowired
    ITFundListService itFundListService;

    @Autowired
    ITFundArchivesService itFundArchivesService;

    @Bean
    public Consumer<EastmoneyFundOverviewTO> receiveCompanyOverview() {
        return co -> {
            TEastmoneyFundOverviewTO tco = new TEastmoneyFundOverviewTO();
            CglibUtil.copy(co, tco);
            tco.setCreateDateTime(new Date());
            log.info("CustomerConsumer.CompanyOverview={}", JSON.toJSONString(tco));
            itCompanyOverviewService.save(tco);
        };
    }

    @Bean
    public Consumer<EastmoneyCompanyTO> receiveCompanyList() {
        return cl -> {
            TEastmoneyCompanyTO tcl = new TEastmoneyCompanyTO();
            CglibUtil.copy(cl,tcl);
            tcl.setCreateDateTime(new Date());
            log.info("CustomerConsumer.CompanyList={}", JSON.toJSONString(tcl));
            QueryWrapper<TEastmoneyCompanyTO> tcluw = new QueryWrapper<>();
            tcluw.eq("company_id",tcl.getCompanyId());
            itCompanyListService.saveOrUpdate(tcl,tcluw);
        };
    }

    @Bean
    public Consumer<EastmoneyFundTO> receiveFundList() {
        return fl -> {
            TEastmoneyFundTO tfl = new TEastmoneyFundTO();
            CglibUtil.copy(fl ,tfl);
            tfl.setCreateDateTime(new Date());
            log.info("CustomerConsumer.FundList={}", JSON.toJSONString(tfl));
            QueryWrapper<TEastmoneyFundTO> tfluw = new QueryWrapper<>();
            tfluw.eq("fund_code",tfl.getFundCode());
            itFundListService.saveOrUpdate(tfl,tfluw);
        };
    }

    private ExecutorService executorService = new ThreadPoolExecutor(1, 100, 5L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    @Bean
    public Consumer<List<FundArchives>> receiveFundArchives() {
        return fas-> {
            log.info("CustomerConsumer.FundArchives={}", JSON.toJSONString(fas));
            if (Objects.isNull(fas) || fas.isEmpty()){
                return;
            }
            FundArchives fa = fas.get(0);
            QueryWrapper<TFundArchives> tfluw = new QueryWrapper<>();
            tfluw.eq("fund_code",fa.getFundCode());
            tfluw.eq("net_date_time",fa.getNetDateTime());
            int c = itFundArchivesService.count(tfluw);
            if (c < 1) {
                List<TFundArchives> tfs = fas.stream().map(v-> {
                    TFundArchives tfa = new TFundArchives();
                    tfa.setCrawlDateTime(v.getCrawlDateTime());
                    tfa.setFundConcept(v.getFundConcept());
                    tfa.setFundCode(v.getFundCode());
                    tfa.setNetDateTime(v.getNetDateTime());
                    tfa.setUnitNet(v.getUnitNet());
                    tfa.setAccumulatedNet(v.getAccumulatedNet());
                    tfa.setDayGrowRate(v.getDayGrowRate());
                    tfa.setSubscriptionStatus(v.getSubscriptionStatus());
                    tfa.setRedemptionStatus(v.getRedemptionStatus());
                    tfa.setDividendDistribution(v.getDividendDistribution());
                    tfa.setPerMillionFund(v.getPerMillionFund());
                    tfa.setAnnualized7Income(v.getAnnualized7Income());
                    tfa.setCreateDateTime(new Date());
                    return tfa;
                }).collect(Collectors.toList());
//                for (TFundArchives ta :tfs){
//                    executorService.execute(new excuteSaveRunnable(itFundArchivesService,ta));
//                }
                itFundArchivesService.insertBatch(tfs);
            }

        };
    }

    class excuteSaveRunnable implements Runnable {
        ITFundArchivesService itFundArchivesService;

        TFundArchives ta;

        public excuteSaveRunnable(ITFundArchivesService itFundArchivesService, TFundArchives ta) {
            this.itFundArchivesService = itFundArchivesService;
            this.ta = ta;
        }

        @Override
        public void run() {
            itFundArchivesService.save(ta);
        }
    }
    public static void main(String[] args) {
        List<FundArchives> tfa = new ArrayList<>();
        FundArchives fa = new FundArchives();
        fa.setFundCode("1");
        tfa.add(fa);
        fa = new FundArchives();
        fa.setFundCode("2");
        tfa.add(fa);
        fa = new FundArchives();
        fa.setFundCode("3");
        tfa.add(fa);

        TFundArchives ta = new TFundArchives();
        List<TFundArchives> tfs = tfa.stream().map(v-> {
            TFundArchives t = new TFundArchives();
            CglibUtil.copy(v ,t);
            t.setCreateDateTime(new Date());
            return t;
        }).collect(Collectors.toList());
        for (TFundArchives ts:tfs){
            System.out.println(ts.getFundCode());
        }

    }



}
