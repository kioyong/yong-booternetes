package com.yong.security.controller;

import com.yong.security.model.ResponseBean;
import com.yong.security.model.UserBean;
import com.yong.security.service.impl.UserDetailServiceImpl;
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
        return userDetailService.findUserByUsername(username).map(
                t->{
                    if( t==null ){ return ResponseBean.error(new Exception("user not found"));}
                    else{return ResponseBean.success(t);}
                }
        );
    }

    @GetMapping("/me")
    public Mono<Principal> me(@AuthenticationPrincipal Principal principal){
        return Mono.just(principal);
    }

    @PostMapping("/register")
    public Mono registerUser(@RequestBody UserBean user){
        return userDetailService.findUserByUsername(user.getUsername())
                .map(t -> {
                            if (t != null) {
                                return ResponseBean.error(new Exception("username already exists!"));
                            } else {
                                return userDetailService.registerUser(user).map(ResponseBean::success);
                            }
                        }
                );
    }
}
