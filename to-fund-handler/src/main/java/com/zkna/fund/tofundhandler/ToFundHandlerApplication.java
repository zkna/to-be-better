package com.zkna.fund.tofundhandler;

import cn.org.atool.fluent.mybatis.spring.MapperFactory;
import cn.org.atool.generator.FileGenerator;
import com.zkna.fund.tofundhandler.consumer.IEastmoneyCompanyListConsumer;
import com.zkna.fund.tofundhandler.consumer.IEastmoneyFundListConsumer;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@SpringBootApplication
@EnableBinding({IEastmoneyCompanyListConsumer.class, IEastmoneyFundListConsumer.class})
public class ToFundHandlerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ToFundHandlerApplication.class, args);
    }

}

@Configuration
@MapperScan("com.zkna.fund.tofundhandler.mapper")
class MybatisConfig {
//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new Resource[]{new ClassPathResource("mapper/MyStudentScoreMapper.xml")});
//        return bean;
//    }

    @Bean
    public MapperFactory mapperFactory() {
        return new MapperFactory();
    }
}
