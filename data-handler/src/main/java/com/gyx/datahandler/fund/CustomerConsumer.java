package com.gyx.datahandler.fund;

import com.gyx.entity.fund.FundCompanyOverview;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class CustomerConsumer {

    @Bean
    public Consumer<FundCompanyOverview> receiveKafkaAvro() {
        return customerAvro -> {
            log.info("Received Type={}", customerAvro.getClass().getCanonicalName());
            log.info("CustomerConsumer.receiveKafkaAvro={}", customerAvro);
        };
    }
}
