package com.zkna.fund.tofundhandler.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 *
 */
public interface IEastmoneyCompanyListConsumer {

    /**
     * 天天基金公司List
     */
    String EASTMONEY_COMPANY_LIST_INPUT = "eastmoney-company-list-input";

    @Input(EASTMONEY_COMPANY_LIST_INPUT)
    SubscribableChannel eastmoneyCompanyListInput();

}
