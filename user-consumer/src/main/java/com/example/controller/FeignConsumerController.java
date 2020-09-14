package com.example.controller;

import com.example.feignCilent.UserFeignClient;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feignclient")
public class FeignConsumerController {
    @Autowired
    private UserFeignClient userFeignClient;


    @RequestMapping("/{id}")
    public User queryByIdWithFeign(@PathVariable Long id){

        return userFeignClient.queryById(id);
    }
}
