package com.example.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
  *  @Author Liu Haonan
  *  @Date 2020/9/11 11:05
  *  @Description 自定义局部过滤器，获取请求中带有的参数
 *
  */
@Component
public class MyParamGatewayFilterFactory extends AbstractGatewayFilterFactory<MyParamGatewayFilterFactory.Config> {

    //定义参数名称
    public static final String PARAM_NAME= "param";

    //配置的参数的成员内部类

    public static class Config{

        //对应需要过滤的请求中指定的参数名
        private String param;

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

    }
    //初始化配置
    public MyParamGatewayFilterFactory(){
        super(Config.class);
    }

    public List<String> shortcutFieldOrder(){
        return Arrays.asList(PARAM_NAME);
    }

    //逻辑实现
    @Override
    public GatewayFilter apply(Config config) {//这里config.param就是我们所关心的参数name

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if(request.getQueryParams().containsKey(config.param)){
                request.getQueryParams().get(config.param).forEach(value -> System.out.println("通过局部过滤器___"+"参数名："+config.param+"参数值："+value));
            }


            return chain.filter(exchange);
        };
    }


}
