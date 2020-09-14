package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
@EnableDiscoveryClient//开启客户端发现功能
@SpringBootApplication
@EnableCircuitBreaker//开启熔断
//@SpringCloudApplication可代替以上的三个注解
@EnableFeignClients//开启feign
public class UserConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserConsumerApplication.class, args);
	}

	@Bean
	@LoadBalanced//采用Ribbon负载均衡
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
