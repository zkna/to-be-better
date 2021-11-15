package com.zkna.fund.tofundhandler.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 *
 */
public interface IEastmoneyFundListConsumer {
    /**
     * 天天基金基金列表
     */
    String EASTMONEY_FUND_LIST_INPUT = "eastmoney-fund-list-input";

    @Input(EASTMONEY_FUND_LIST_INPUT)
    SubscribableChannel eastmoneyFundListInput();
}
