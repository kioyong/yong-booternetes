package com.yong.security.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class DemoController {


    @GetMapping("/hello")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String hello(){
        return "hello Security";
    }

    @PostMapping("/hello")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String postHello(){
        return "hello Security";
    }

}
