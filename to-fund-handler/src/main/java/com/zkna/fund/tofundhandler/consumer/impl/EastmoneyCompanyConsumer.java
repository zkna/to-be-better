package com.zkna.fund.tofundhandler.consumer.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.gyx.entity.fund.eastmoney.EastmoneyCompanyTO;
import com.zkna.fund.tofundhandler.consumer.IEastmoneyCompanyListConsumer;
import com.zkna.fund.tofundhandler.entity.TCompanyListEntity;
import com.zkna.fund.tofundhandler.mapper.TCompanyListMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EastmoneyCompanyConsumer {

    @Qualifier("fmTCompanyListMapper")
    @Autowired
    TCompanyListMapper tCompanyListMapper;

    @StreamListener(IEastmoneyCompanyListConsumer.EASTMONEY_COMPANY_LIST_INPUT)
    public void onMessage(@Payload EastmoneyCompanyTO eastmoneyCompanyTO) {
        log.info("[Company]线程编号：{},消息内容:{}", Thread.currentThread().getId(), JSON.toJSONString(eastmoneyCompanyTO));
        TCompanyListEntity tCompanyListEntity = new TCompanyListEntity();
        tCompanyListEntity.setCompanyCode(eastmoneyCompanyTO.getCompanyCode());
        tCompanyListEntity.setCompanyName(eastmoneyCompanyTO.getCompanyName());
        tCompanyListEntity.setCompanyDetails(eastmoneyCompanyTO.getCompanyDetail());
        tCompanyListEntity.setCompanyBa(eastmoneyCompanyTO.getCompanyBa());
        tCompanyListEntity.setCompanySetDate(eastmoneyCompanyTO.getCompanySetDate());
        tCompanyListEntity.setCompanyTianxiangLevel(eastmoneyCompanyTO.getCompanyTianXiangLevel());
        tCompanyListEntity.setCompanyScale(eastmoneyCompanyTO.getCompanyScale() == null ? "":eastmoneyCompanyTO.getCompanyScale().toString());
        tCompanyListEntity.setCompanyScaleDate(eastmoneyCompanyTO.getCompanyScaleDate());
        tCompanyListEntity.setCompanyFundSum(eastmoneyCompanyTO.getCompanyFundSum());
        tCompanyListEntity.setCompanyManagerSum(eastmoneyCompanyTO.getCompanyManagerSum());
        tCompanyListEntity.setCompanyManagerUrl(eastmoneyCompanyTO.getCompanyManagerUrl());
        tCompanyListEntity.setCrawlDate(DateUtil.today());
        tCompanyListMapper.insert(tCompanyListEntity);
    }
}
