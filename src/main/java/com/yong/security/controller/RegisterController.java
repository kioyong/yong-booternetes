package com.yong.security.controller;

import com.yong.security.model.ResponseBean;
import com.yong.security.model.User;
import com.yong.security.service.impl.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;


import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by LiangYong on 2017/10/1.
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/register")
public class RegisterController {

    private final UserDetailServiceImpl userDetailService;

    @PostMapping
    public Mono registerUser(@RequestBody User user){
        Mono<User> dbUser = userDetailService.findUserByUsername(user.getUsername());
        if(dbUser.block() != null){
            log.debug("already user {}",dbUser.block().getUsername());
            return dbUser.map(t -> ResponseBean.error(new Exception("username already exists!")));
        }

        return userDetailService.registerUser(user)
                .map(t ->t.getUsername())
                .map(ResponseBean::success);

    }
}
