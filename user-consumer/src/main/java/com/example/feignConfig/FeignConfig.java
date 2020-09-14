package com.example.feignConfig;


import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
  *  @Author Liu Haonan
  *  @Date 2020/9/10 15:09
  *  @Description Feign的配置类，使用FeignClient注解时，需要添加属性configuration=FeignConfig.class
  */
@Configuration
public class FeignConfig {

    /**
      *  @Author Liu Haonan
      *  @Date 2020/9/10 15:10
      *  @Description 日志记录配置，返回全部的日志，FULL
      */
    @Bean
    public Logger.Level feignLoggerLevel(){

        return  Logger.Level.FULL;
    }
}
