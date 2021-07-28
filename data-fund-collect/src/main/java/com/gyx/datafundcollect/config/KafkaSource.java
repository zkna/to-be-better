package com.gyx.datafundcollect.config;

import com.gyx.entity.fund.FundCompanyOverview;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.DirectProcessor;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@Slf4j
public class KafkaSource {

    @Bean
    public Supplier<String> companyOverviewSource() {
        return () -> {
            String str = "";
            return str;
        };
    }

    static List<String> list = new LinkedList<>();

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        Class cache = Integer.class.getDeclaredClasses()[0];
        Field c = cache.getDeclaredField("cache");
        c.setAccessible(true);
        Integer[] array = (Integer[]) c.get(cache);
// array[129] is 1
        array[130] = array[129];
// Set 2 to be 1
        array[131] = array[129];
// Set 3 to be 1
        Integer a = 1;
        if(a == (Integer)1 && a == (Integer)2 && a == (Integer)3){
            System.out.println("Success");
        }
    }
}
