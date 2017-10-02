package com.yong.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LiangYong on 2017/10/1.
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public String getHelloWorld(){
        return "Hello Security!";
    }
}
