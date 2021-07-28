package com.gyx.datahandler.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan("com.gyx.datahandler.mapper")
public class MybatisPlusConfig {

}
