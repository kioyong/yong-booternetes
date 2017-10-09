package com.yong.security.controller;

import com.yong.security.model.ResponseBean;
import com.yong.security.model.UserBean;
import com.yong.security.service.impl.UserDetailServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.security.Principal;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * Created by LiangYong on 2017/10/1.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserDetailServiceImpl userDetailService;

    @GetMapping("/{username}")
    public Mono<ResponseBean> getUserDetail(@PathVariable("username")String username){
        return this.userDetailService.findUserByUsername(username).map(
                t->{
                    if( t==null ){ return ResponseBean.error(new Exception("user not found"));}
                    else{return ResponseBean.success(t);}
                }
        );
    }

    @GetMapping("/me")
    @ApiOperation(value = "")
    public Mono<Principal> me(@AuthenticationPrincipal Principal principal){
        return Mono.just(principal);
    }

    @PostMapping("/register")
    public Mono<ResponseBean> registerUser(@RequestBody UserBean user){
        //TODO duplicate key check and null point check not impl yet. current is demo version.
        return userDetailService.registerUser(user).map(ResponseBean::success);
    }
}
