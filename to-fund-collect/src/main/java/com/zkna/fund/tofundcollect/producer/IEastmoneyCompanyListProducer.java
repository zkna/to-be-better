package com.zkna.fund.tofundcollect.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EastmoneyCompanyListProducer {

    @Output("eastmoney-company-list-output")
    MessageChannel eastmoneyCompanyListOutput();

}
