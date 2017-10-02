package com.yong.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LiangYong on 2017/10/1.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public String getUserDetail(@PathVariable("id")String id){
        return "user :"+id;
    }
    @GetMapping
    public String getUserName(){
        return "hello User";
    }
}
