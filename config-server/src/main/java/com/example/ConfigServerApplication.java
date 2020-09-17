package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
  *  @Author Liu Haonan
  *  @Date 2020/9/16 19:04
  *  @Description 微服务配置中心
  */

@EnableConfigServer//开启配置中心服务
@SpringBootApplication
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
