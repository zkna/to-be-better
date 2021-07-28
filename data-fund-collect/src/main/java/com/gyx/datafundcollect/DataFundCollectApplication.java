package com.gyx.datafundcollect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DataFundCollectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataFundCollectApplication.class, args);
    }

}
