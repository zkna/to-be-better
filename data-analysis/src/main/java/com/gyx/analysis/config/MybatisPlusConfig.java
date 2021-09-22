package com.gyx.analysis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

/**
 * @author gyx
 */
@Component
@MapperScan("com.gyx.analysis.mapper")
public class MybatisPlusConfig {
}
