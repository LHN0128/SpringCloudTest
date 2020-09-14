package com.example.controller;

import com.example.pojo.User;
import com.example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     *  @Author Liu Haonan
     *  @Date 2020/9/8 16:25
     *  @Description 服务提供方Spring Cloud
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable Long id){
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return userService.queryById(id);
    }
}
