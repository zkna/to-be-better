package com.zkna.fund.tofundcollect.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EastmoneyFundListProducer {

    @Output("eastmoney-fund-list-output")
    MessageChannel eastmoneyFundListOutput();

}
