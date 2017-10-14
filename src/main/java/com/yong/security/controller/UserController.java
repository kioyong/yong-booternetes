package com.yong.security.controller;

import com.yong.security.model.ResponseBean;
import com.yong.security.model.UserBean;
import com.yong.security.service.impl.UserDetailServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.security.Principal;

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
        return this.userDetailService.findUserByUsername(username).flatMap(
            t->{
                if( t == null ){
                    return Mono.just(ResponseBean.error("user not found"));
                }
                else{
                    return Mono.just(ResponseBean.success(t));
                }
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
