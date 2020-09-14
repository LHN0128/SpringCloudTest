package com.example.feignCilent;

import com.example.feignConfig.FeignConfig;
import com.example.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
  *  @Author Liu Haonan
  *  @Date 2020/9/10 11:36
  *  @Description  Feign的客户端类，用于简化服务地址的构建
  *                请求地址会被构建为http://user-service/user/{id}
  *                之后在Controller中注入FeignClient并调用该方法即可
  */
@FeignClient(value = "user-service",configuration = FeignConfig.class)
public interface UserFeignClient {
    @GetMapping("/user/{id}")
    User queryById(@PathVariable Long id);




}
