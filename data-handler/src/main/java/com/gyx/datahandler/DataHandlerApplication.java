package com.gyx.datahandler;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class})
public class DataHandlerApplication {

    public static void main(String[] args) {

        SpringApplication.run(DataHandlerApplication.class, args);
    }

    //> grant alter, alter routine, create, create role, create routine, create temporary tables, create view, delete, drop, event, execute, grant option, index, insert, lock tables, references, select, show view, trigger, update on fund.* to ''
}
