package com.example.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
/**
  *  @Author Liu Haonan
  *  @Date 2020/9/14 19:09
  *  @Description 定义全局过滤器，过滤参数中的token是否为空。增加该过滤器后必须携带token
  */
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("通过全局过滤器");
        String token = exchange.getRequest().getQueryParams().getFirst("token");//获取请求中的参数值
        if(StringUtils.isBlank(token)){
            //如果token为空，设置未授权
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();//完成执行，不再执行之后的过滤请求
        }
        return chain.filter(exchange);//如果正常，继续往后执行
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
