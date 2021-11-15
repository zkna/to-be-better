package com.zkna.fund.tofundcollect;

import com.zkna.fund.tofundcollect.producer.IEastmoneyCompanyListProducer;
import com.zkna.fund.tofundcollect.producer.IEastmoneyFundListProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding({IEastmoneyCompanyListProducer.class,IEastmoneyFundListProducer.class})
@SpringBootApplication
public class ToFundCollectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToFundCollectApplication.class, args);
    }

}
