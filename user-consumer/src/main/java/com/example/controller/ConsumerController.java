package com.example.controller;

import com.example.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
  *  @Author Liu Haonan
  *  @Date 2020/9/8 16:22
  *  @Description SpringCloud入门案例，消费端调用服务端
 *                 先启动eureka，然后启动service，最后启动consumer
 *                 1、拉取服务  discoveryClient.getInstances.get
 *                 2、拼接地址
 *                 3、调用服务  restTemplate.getForObject
  */
@Slf4j
@RestController
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "defaultFallback")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/{id}")
    public User queryById(@PathVariable  Long id){
//        String url = "http://localhost:8080/user/1";//指定服务地址
//        User user = restTemplate.getForObject(url, User.class);
        //获取所有的服务
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        //获取user服务
        ServiceInstance serviceInstance = instances.get(0);
        //拼接动态地址
        String url = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/user/"+id;
        //调用
        User user = restTemplate.getForObject(url, User.class);
        return user;
    }
    /**
      *  @Author Liu Haonan
      *  @Date 2020/9/9 16:45
      *  @Description 测试使用Ribbon负载均衡，使用服务名user-service调用服务
      */
    @RequestMapping("/ribbon/{id}")
    public User queryByIdWithRibbon(@PathVariable Long id){
        String url = "http://user-service/user/"+id;
        User user = restTemplate.getForObject(url, User.class);

        return user;
    }
    /**
      *  @Author Liu Haonan
      *  @Date 2020/9/9 18:12
      *  @Description 测试Hystrix熔断处理
      */
    @RequestMapping("hystrix/{id}")
    @HystrixCommand(fallbackMethod = "queryByIdWithHytrixFallback")
    public String queryByIdWithHystrix(@PathVariable Long id) throws InterruptedException {
        String url = "http://user-service/user/"+id;
        String userString = restTemplate.getForObject(url,String.class);
//        让线程休眠2s，让其超时，对应超时配置应该调用服务降级方法
//        Thread.sleep(2000);
        return userString;

    }

    /**
      *  @Author Liu Haonan
      *  @Date 2020/9/9 19:16
      *  @Description 测试熔断器，当不停的访问localhost:8082/hystrix/testCircuitBreaker/1时，会触发熔断
     *                  触发熔断后，其他所有的访问会短暂服务降级
      */
    @RequestMapping("hystrix/testCircuitBreaker/{id}")
    public String testCircuitBreaker(@PathVariable Long id) {
        if(id==1){
            throw new RuntimeException("太忙了");
        }
        String url = "http://user-service/user/"+id;
        String userString = restTemplate.getForObject(url,String.class);
        return userString;

    }




    /**
      *  @Author Liu Haonan
      *  @Date 2020/9/9 18:25
      *  @Description 服务调用失败后的服务降级方法
      */
    public String queryByIdWithHytrixFallback(Long id){
        log.error("查询用户信息失败。id:{}",id);
        return "网络拥挤";
    }

    /**
      *  @Author Liu Haonan
      *  @Date 2020/9/9 19:13
      *  @Description 默认服务降级方法，针对类中所有请求服务的方法
      */
    public String defaultFallback(){
        return "默认服务降级方法";
    }

}
