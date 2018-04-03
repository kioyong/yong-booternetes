package com.yong.security.controller;

import com.yong.security.model.AuthenticationVo;
import com.yong.security.model.ResponseVo;
import com.yong.security.model.UserEntity;
import com.yong.security.service.impl.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;


/**
 * @author yong.a.liang
 * createdDate 2017/10/1.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserDetailServiceImpl userDetailService;


    /**
     * 注册功能，Body上需要输入用户名和密码，
     * 当前只添加了用户名密码不能为空，且用户名没有被注册过
     * restController Post request 默认的RequestBody也是 application/json格式的
     * **/
    @PostMapping(path = "/register",consumes = "application/json")
    public ResponseVo registerUser(@RequestBody AuthenticationVo auth){
        Optional<UserEntity> user = this.userDetailService.findUserByUsername(auth.getUsername());
        checkArgument(!user.isPresent(),"User "+ auth.getUsername() + " already exists!");
        UserEntity userEntity = this.userDetailService.registerUser(
            UserEntity.builder().username(auth.getUsername()).password(auth.getPassword()).build()
        );
        return ResponseVo.success(userEntity);
    }

    @GetMapping("/me")
    public Principal me(@AuthenticationPrincipal Principal principal){
        return principal;
    }

    @GetMapping("/test")
    public ResponseEntity test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(authentication);
    }



}
