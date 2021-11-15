package com.zkna.fund.tofundhandler.consumer.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.gyx.entity.fund.eastmoney.EastmoneyFundTO;
import com.zkna.fund.tofundhandler.consumer.IEastmoneyFundListConsumer;
import com.zkna.fund.tofundhandler.entity.TFundListEntity;
import com.zkna.fund.tofundhandler.mapper.TFundListMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class EastmoneyFundConsumer {

    @Qualifier("fmTFundListMapper")
    @Autowired
    TFundListMapper tFundListMapper;

    @StreamListener(IEastmoneyFundListConsumer.EASTMONEY_FUND_LIST_INPUT)
    public void onMessage(@Payload EastmoneyFundTO fundTo) {
        log.info("[Fund]线程编号：{},内容：{}", Thread.currentThread().getId(),JSON.toJSONString(fundTo));
//        List<TFundListEntity> tfs = new ArrayList<>();
//        for (EastmoneyFundTO ft : fundTo) {
            TFundListEntity tf = new TFundListEntity();
             // 累计净值
            tf.setFundAccumulateNetVal(fundTo.getFundAccumulatedNetVal());
             // 基金档案地址
            tf.setFundArchive(fundTo.getFundArchive());
             // 基金吧地址
            tf.setFundBa(fundTo.getFundBa());
             // 基金代码
            tf.setFundCode(fundTo.getFundCode());
             // 基金大类
            tf.setFundConcept(fundTo.getFundConcept());
             // 日增长率
            tf.setFundDailyGrowthRate(fundTo.getFundDailyGrowthRate());
             // 基金日期(这条数据的日期)
            tf.setFundDate(fundTo.getFundDate());
             // 折价率
            tf.setFundHairCut(fundTo.getFundHairCut());
             // 手续费
            tf.setFundHandFee(fundTo.getFundHandFee());
             // 基金经理
            tf.setFundManager(fundTo.getFundManager());
             // 基金经理url
            tf.setFundManagerUrl(fundTo.getFundManagerUrl());
             // 市价
            tf.setFundMarketVal(fundTo.getFundMarketVal());
             // 基金名称
            tf.setFundName(fundTo.getFundName());
             // 万份收益
            tf.setFundNetVal(fundTo.getFundNetVal());
             // 购买状态
            tf.setFundPurchaseStatus(fundTo.getFundSubscriptionStatus());
             // 基金规模
            tf.setFundScale(fundTo.getFundScale());
             // 申购状态
            tf.setFundSubscriptionStatus(fundTo.getFundSubscriptionStatus());
             // 基金类型
            tf.setFundType(fundTo.getFundType());
             // 基金url
            tf.setFundUrl(fundTo.getFundUrl());
            tf.setCompanyCode(fundTo.getCompanyCode());
            tf.setCompanyName(fundTo.getCompanyName());
            tf.setCrawlDate(DateUtil.today());
//            tfs.add(tf);
//        }
//
        tFundListMapper.insert(tf);
    }
}
