package com.yong.security.controller;

import com.yong.security.model.AuthenticationBean;
import com.yong.security.model.ResponseBean;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * Created by LiangYong on 2017/10/1.
 */
@RestController
@CrossOrigin
@AllArgsConstructor
public class IndexController {
//
//    private final UserDetailsService userDetailsService;
//    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    @GetMapping("/index")
    public ResponseBean getHelloWorld(){
        return ResponseBean.success("test success!");
    }

    @PostMapping("/login")
    public ResponseBean getHelloWorldPost(@RequestBody AuthenticationBean authenticationBean){
        return ResponseBean.success("test success!",authenticationBean);
//        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
//        OAuth2AccessToken accessToken = new DefaultOAuth2AccessToken("test");

    }

}
